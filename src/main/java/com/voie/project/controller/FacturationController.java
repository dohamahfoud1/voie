package com.voie.project.controller;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.voie.project.repository.FacturationRepository;
import com.voie.project.repository.LivraisonRepository;
import com.voie.project.service.FacturationService;

@Controller
public class FacturationController {

    @Autowired
    private FacturationService facturationService;
    
    @Autowired
    private FacturationRepository facturationRepo;
    
    

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

    @PostMapping("/payerFacture")
    public String payerFacture(@RequestParam Long facturationId, @RequestParam BigDecimal montantPaye, RedirectAttributes redirectAttributes) {
        boolean paiementReussi = facturationService.payerFacture(facturationId, montantPaye);
        if (paiementReussi) {
            redirectAttributes.addFlashAttribute("successMessage", "Le paiement de la facture a été effectué avec succès !");
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "Échec du paiement de la facture.");
        }
        return "redirect:/listFactures";
    }

  

    @GetMapping("/listFactures")
    public String listFactures(Model model) {
        model.addAttribute("factures", facturationRepo.findAll());
        return "listFactures";
    }
    
    
    @GetMapping("/deleteFacture/{id}")
    public String deleteCommande(@PathVariable Long id, RedirectAttributes redirectAttributes) {
    	facturationRepo.deleteById(id);
        redirectAttributes.addFlashAttribute("success", "Commande supprimé avec succès.");
        return "redirect:/listFactures";
    }

}
