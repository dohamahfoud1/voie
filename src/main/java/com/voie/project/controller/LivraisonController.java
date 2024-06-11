package com.voie.project.controller;

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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.voie.project.models.Article;
import com.voie.project.models.Boutique;
import com.voie.project.models.Commande;
import com.voie.project.models.Entrepot;
import com.voie.project.models.LigneCommande;
import com.voie.project.models.LigneLivraison;
import com.voie.project.models.Livraison;
import com.voie.project.repository.ArticleRepository;
import com.voie.project.repository.BoutiqueRepository;
import com.voie.project.repository.ClientRepository;
import com.voie.project.repository.EntrepotRepository;
import com.voie.project.repository.LigneLivraisonRepository;
import com.voie.project.repository.LivraisonRepository;
import com.voie.project.service.LivraisonService;

@Controller
public class LivraisonController {

    @Autowired
    private LivraisonService livraisonService;
    @Autowired
    private LivraisonRepository livraisonRepo;
    @Autowired
    private LigneLivraisonRepository lignelLivraisonRepo;
    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private BoutiqueRepository boutiqueRepository;

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private EntrepotRepository entrepotRepository;
    
    
    
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));
    }
    
    
    @GetMapping("/listLivraisons")
    public String listLivraisons(Model model) {
        model.addAttribute("livraisons", livraisonRepo.findAll());
        return "listLivraisons";
    }
   
    
    @GetMapping("/transfertLivraison/{id}")
    public String transfertLivraison(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        boolean transfertReussi = livraisonService.transfertEnLivraison(id);
        if (transfertReussi) {
            redirectAttributes.addFlashAttribute("successMessage", "La commande a été transférée en livraison avec succès !");
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "Échec du transfert de la commande en livraison ou déjà transférée.");
        }
        return "redirect:/listCommande";
    }

    @GetMapping("/deleteLivraison/{id}")
    public String deleteDevis(@PathVariable Long id, RedirectAttributes redirectAttributes) {
    	livraisonRepo.deleteById(id);
        redirectAttributes.addFlashAttribute("success", "Livraison supprimé avec succès.");
        return "redirect:/listLivraisons";
    }
    
    
    
    @GetMapping("/createLivraison")
    public String createLivraisonForm(Model model) {
        model.addAttribute("livraison", new Livraison());
        model.addAttribute("clients", clientRepository.findAll());
        model.addAttribute("boutiques", boutiqueRepository.findAll());
        model.addAttribute("articles", articleRepository.findAll());
        return "createLivraison";
    }
    @PostMapping("/createLivraison")
    public String saveLivraison(@ModelAttribute Livraison livraison, Model model, BindingResult result, HttpServletRequest request, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            model.addAttribute("clients", clientRepository.findAll());
            model.addAttribute("boutiques", boutiqueRepository.findAll());
            model.addAttribute("articles", articleRepository.findAll());
            return "createLivraison";
        }

        // Get selected boutique
        Boutique selectedBoutique = boutiqueRepository.findById(livraison.getBoutique().getId()).orElse(null);
        if (selectedBoutique == null) {
            result.rejectValue("boutique", "error.boutique", "Boutique non trouvée.");
            return "createLivraison";
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
                return "redirect:/createLivraison";
            } else if (quantite > entrepot.getStock()) {
                redirectAttributes.addFlashAttribute("error", "Stock insuffisant pour l'article avec ID " + articleId + ". Stock disponible : " + entrepot.getStock());
                return "redirect:/createLivraison";
            }
        }

        
        Livraison savedLivraison = livraisonRepo.save(livraison);    
        String formattedIdLivraison = String.format("%06d", savedLivraison.getId());
        savedLivraison.setReferance(formattedIdLivraison);
        livraisonRepo.save(savedLivraison);
        // Save LigneLivraison
        for (Map.Entry<Long, Integer> entry : articleQuantities.entrySet()) {
            Long articleId = entry.getKey();
            Integer quantite = entry.getValue();

            Article article = articleRepository.findById(articleId).orElse(null);
            if (article != null) {
                LigneLivraison ligneLivraison = new LigneLivraison();
                ligneLivraison.setArticle(article);
                ligneLivraison.setLivraison(savedLivraison);
                ligneLivraison.setQuantite(quantite);
                lignelLivraisonRepo.save(ligneLivraison);
            }
        }

        redirectAttributes.addFlashAttribute("success", "Livraison ajouté avec succès.");
        return "redirect:/createLivraison";
    }

}
