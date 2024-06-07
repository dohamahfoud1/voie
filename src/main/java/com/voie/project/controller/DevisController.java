package com.voie.project.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.voie.project.models.Article;
import com.voie.project.models.Boutique;
import com.voie.project.models.Devis;
import com.voie.project.models.Entrepot;
import com.voie.project.models.LigneDevis;
import com.voie.project.repository.ArticleRepository;
import com.voie.project.repository.BoutiqueRepository;
import com.voie.project.repository.ClientRepository;
import com.voie.project.repository.DevisRepository;
import com.voie.project.repository.EntrepotRepository;
import com.voie.project.repository.LigneDevisRepository;
import com.voie.project.service.DevisService;

import jakarta.servlet.http.HttpServletRequest;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping("/")
public class DevisController {

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private BoutiqueRepository boutiqueRepository;

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private DevisService devisService;
    
    @Autowired
    private DevisRepository devisRepo;
    @Autowired
    private LigneDevisRepository ligneDevisRepo;
    @Autowired
    private EntrepotRepository entrepotRepository;
    

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));
    }
    
    
    @GetMapping("/createDevis")
    public String createDevisForm(Model model) {
        model.addAttribute("devis", new Devis());
        model.addAttribute("clients", clientRepository.findAll());
        model.addAttribute("boutiques", boutiqueRepository.findAll());
        model.addAttribute("articles", articleRepository.findAll());
        return "devis";
    }
    @PostMapping("/createDevis")
    public String saveDevis(@ModelAttribute Devis devis, Model model, BindingResult result, HttpServletRequest request, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            model.addAttribute("clients", clientRepository.findAll());
            model.addAttribute("boutiques", boutiqueRepository.findAll());
            model.addAttribute("articles", articleRepository.findAll());
            return "devis";
        }

        // Get selected boutique
        Boutique selectedBoutique = boutiqueRepository.findById(devis.getBoutique().getId()).orElse(null);
        if (selectedBoutique == null) {
            result.rejectValue("boutique", "error.boutique", "Boutique non trouvée.");
            return "devis";
        }

        Map<Long, Integer> articleQuantities = new HashMap<>();
        for (Article article : articleRepository.findAll()) {
            String quantiteParam = "quantite_" + article.getId();
            String quantiteValue = request.getParameter(quantiteParam);

            if (quantiteValue != null && !quantiteValue.isEmpty()) {
                int quantite = Integer.parseInt(quantiteValue);
                articleQuantities.put(article.getId(), quantite);
            }
        }

        // Validate stock availability
        for (Map.Entry<Long, Integer> entry : articleQuantities.entrySet()) {
            Long articleId = entry.getKey();
            Integer quantite = entry.getValue();

            Entrepot entrepot = entrepotRepository.findByArticleIdAndBoutiqueId(articleId, selectedBoutique.getId());
            if (entrepot == null) {
                redirectAttributes.addFlashAttribute("error", "L'article avec ID " + articleId + " ne se trouve pas dans la boutique sélectionnée.");
                return "redirect:/createDevis";
            } else if (quantite > entrepot.getStock()) {
                redirectAttributes.addFlashAttribute("error", "Stock insuffisant pour l'article avec ID " + articleId + ". Stock disponible : " + entrepot.getStock());
                return "redirect:/createDevis";
            }
        }

        // Calculate totals
        BigDecimal totalHT = BigDecimal.ZERO;
        BigDecimal totalTTC = BigDecimal.ZERO;

        for (Map.Entry<Long, Integer> entry : articleQuantities.entrySet()) {
            Long articleId = entry.getKey();
            Integer quantite = entry.getValue();

            Article article = articleRepository.findById(articleId).orElse(null);
            if (article != null) {
                BigDecimal prixHT = article.getPrixVenteHT();
                BigDecimal prixTTC = article.getPrixVenteTTC();

                totalHT = totalHT.add(prixHT.multiply(BigDecimal.valueOf(quantite)));
                totalTTC = totalTTC.add(prixTTC.multiply(BigDecimal.valueOf(quantite)));
            }
        }

        devis.setTotalHT(totalHT);
        devis.setTotalTTC(totalTTC);

        // Save Devis
        Devis savedDevis = devisRepo.save(devis);

        // Save LigneDevis
        for (Map.Entry<Long, Integer> entry : articleQuantities.entrySet()) {
            Long articleId = entry.getKey();
            Integer quantite = entry.getValue();

            Article article = articleRepository.findById(articleId).orElse(null);
            if (article != null) {
                LigneDevis ligneDevis = new LigneDevis();
                ligneDevis.setArticle(article);
                ligneDevis.setDevis(savedDevis);
                ligneDevis.setQuantite(quantite);
                ligneDevisRepo.save(ligneDevis);
            }
        }

        redirectAttributes.addFlashAttribute("success", "Devis ajouté avec succès.");
        return "redirect:/createDevis";
    }

    
    
    
    @GetMapping("/listDevis")
    public String listDevis(Model model) {
        List<Devis> devisList = devisRepo.findAll();
        model.addAttribute("devisList", devisList);
        return "listDevis";
    }

    @GetMapping("/printDevis/{id}")
    public String printDevis(@PathVariable Long id, Model model) {
        Devis devis = devisRepo.findById(id).orElse(null);
        if (devis == null) {
            // Handle the case when the devis is not found
            return "redirect:/errorPage";
        }

        List<LigneDevis> lignes = ligneDevisRepo.findByDevis(devis);
        model.addAttribute("devis", devis);
        model.addAttribute("lignes", lignes);

        return "printDevis";
    }

    
    
    @GetMapping("/editDevis/{id}")
    public String editDevis(@PathVariable Long id, Model model) {
        Devis devis = devisRepo.findById(id).orElse(null);
        if (devis != null) {
            model.addAttribute("devis", devis);
            model.addAttribute("clients", clientRepository.findAll());
            model.addAttribute("boutiques", boutiqueRepository.findAll());
            model.addAttribute("articles", articleRepository.findAll());
            return "editDevis";
        }
        return "redirect:/listDevis";
    }

    @PostMapping("/updateDevis")
    public String updateDevis(@ModelAttribute Devis devis, BindingResult result, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "editDevis";
        }
        devisRepo.save(devis);
        redirectAttributes.addFlashAttribute("success", "Devis mis à jour avec succès.");
        return "redirect:/listDevis";
    }

    @GetMapping("/deleteDevis/{id}")
    public String deleteDevis(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        devisRepo.deleteById(id);
        redirectAttributes.addFlashAttribute("success", "Devis supprimé avec succès.");
        return "redirect:/listDevis";
    }

    
    @GetMapping("/transfertCommande/{id}")
    public String transfertCommande(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        Devis devis = devisRepo.findById(id).orElse(null);
        if (devis == null) {
            redirectAttributes.addFlashAttribute("errorMessage", "Le devis n'a pas été trouvé.");
            return "redirect:/listDevis";
        }
        
        if (devis.isTransfereEnCommande()) {
            redirectAttributes.addFlashAttribute("errorMessage", "Ce devis a déjà été transféré en commande.");
            return "redirect:/listDevis";
        }
        
        boolean transfertReussi = devisService.transfertEnCommande(id);
        if (transfertReussi) {
            redirectAttributes.addFlashAttribute("successMessage", "Le devis a été transféré en commande avec succès !");
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "Échec du transfert du devis en commande.");
        }
        return "redirect:/listDevis";
    }


}
