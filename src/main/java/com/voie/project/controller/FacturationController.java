package com.voie.project.controller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.voie.project.models.Article;
import com.voie.project.models.Boutique;
import com.voie.project.models.Client;
import com.voie.project.models.Entrepot;
import com.voie.project.models.Facturation;
import com.voie.project.models.FacturesPayees;
import com.voie.project.models.LigneLivraison;
import com.voie.project.models.Livraison;
import com.voie.project.repository.ClientRepository;
import com.voie.project.repository.FacturationRepository;
import com.voie.project.repository.FacturesPayeesRepository;
import com.voie.project.repository.LivraisonRepository;
import com.voie.project.service.FacturationService;

@Controller
public class FacturationController {

    @Autowired
    private FacturationService facturationService;
    
    @Autowired
    private FacturationRepository facturationRepo;
    @Autowired
    private FacturesPayeesRepository facturesPayeesRepo;
    @Autowired
    private ClientRepository clientRepository;
    
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));
    }
    
    
    private static final Logger logger = LoggerFactory.getLogger(FacturationController.class);

    @GetMapping("/transfertFacturation/{id}")
    public String transfertFacturation(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        boolean transfertReussi = facturationService.transfertEnFacturation(id);
        if (transfertReussi) {
            redirectAttributes.addFlashAttribute("successMessage", "La livraison a été transférée en facturation avec succès !");
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "Échec du transfert de la livraison en facturation.");
        }
        return "redirect:/listLivraisons";
    }

    @PostMapping("/payementFacture")
    public String payerFacture(@RequestParam("facturationId") Long facturationId,
                               @RequestParam("montantPaye") BigDecimal montantPaye, 
                               RedirectAttributes redirectAttributes) {

        Facturation facturation = facturationRepo.findById(facturationId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid facturation Id: " + facturationId));

        Client client = facturation.getClient();

        if (facturation.getPrixRestant().compareTo(montantPaye) >= 0) {
            facturation.setPrixRestant(facturation.getPrixRestant().subtract(montantPaye));

            // Mettre à jour le solde du client
            client.setSolde(client.getSolde().subtract(montantPaye));
            clientRepository.save(client);

            facturationRepo.save(facturation);

            if (facturation.getPrixRestant().compareTo(BigDecimal.ZERO) == 0) {
                transfererFacturePayee(facturation);
                facturationRepo.delete(facturation);
                redirectAttributes.addFlashAttribute("successMessage", "Le paiement de la facture a été effectué avec succès et la facture a été transférée.");
            } else {
                redirectAttributes.addFlashAttribute("successMessage", "Le paiement de la facture a été effectué avec succès !");
            }
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "Le montant payé dépasse le montant restant.");
        }

        return "redirect:/listFactures";
    }

    private void transfererFacturePayee(Facturation facturation) {
        FacturesPayees facturePayee = new FacturesPayees();
        facturePayee.setDateCreation(facturation.getDateCreation());
        facturePayee.setClient(facturation.getClient());
        facturePayee.setLivraison(facturation.getLivraison());
        facturePayee.setReferance(facturation.getReferance());
        facturePayee.setTotalHT(facturation.getTotalHT());
        facturePayee.setTotalTTC(facturation.getTotalTTC());
        facturePayee.setPrixTotal(facturation.getPrixTotal());

        facturesPayeesRepo.save(facturePayee);
    }


    @GetMapping("/listFactures")
    public String listFactures(Model model) {
        model.addAttribute("factures", facturationRepo.findAll());
        return "listFactures";
    }
    @GetMapping("/listFacturesPayees")
    public String listFacturesPayees(Model model) {
        model.addAttribute("factures", facturesPayeesRepo.findAll());
        return "listFacturesPayees";
    }
    
    
    @GetMapping("/deleteFacture/{id}")
    public String deleteFactures(@PathVariable Long id, RedirectAttributes redirectAttributes) {
    	facturationRepo.deleteById(id);
        redirectAttributes.addFlashAttribute("success", "Commande supprimé avec succès.");
        return "redirect:/listFactures";
    }
    
    @GetMapping("/deleteFacturesPayees/{id}")
    public String deleteFacturesPayees(@PathVariable Long id, RedirectAttributes redirectAttributes) {
    	facturesPayeesRepo.deleteById(id);
        redirectAttributes.addFlashAttribute("success", "facture supprimé avec succès.");
        return "redirect:/listFacturesPayees";
    }
    
    @GetMapping("/createFacture")
    public String createFacturationForm(Model model) {
        model.addAttribute("facture", new Facturation());
        model.addAttribute("clients", clientRepository.findAll());
        return "createFacture";
    }
    @PostMapping("/createFacture")
    public String saveFacture(@ModelAttribute Facturation facture, Model model, BindingResult result, HttpServletRequest request, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            model.addAttribute("clients", clientRepository.findAll());
            return "createFacture";
        }

  
        Facturation savedFacture = facturationRepo.save(facture);    
        String formattedIdFacture = String.format("%06d", savedFacture.getId());
        savedFacture.setReferance(formattedIdFacture);
        facturationRepo.save(savedFacture);
   
        redirectAttributes.addFlashAttribute("success", "facture ajouté avec succès.");
        return "redirect:/createFacture";
    }


}