package com.voie.project.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
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
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.voie.project.models.Article;
import com.voie.project.models.Boutique;
import com.voie.project.models.Commande;
import com.voie.project.models.Devis;
import com.voie.project.models.Entrepot;
import com.voie.project.models.LigneCommande;
import com.voie.project.repository.ArticleRepository;
import com.voie.project.repository.BoutiqueRepository;
import com.voie.project.repository.ClientRepository;
import com.voie.project.repository.CommandeRepository;
import com.voie.project.repository.DevisRepository;
import com.voie.project.repository.EntrepotRepository;
import com.voie.project.repository.LigneCommandeRepository;
import com.voie.project.repository.LigneDevisRepository;
import com.voie.project.service.DevisService;

@Controller
public class CommandeController {
	
	
    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private BoutiqueRepository boutiqueRepository;

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private EntrepotRepository entrepotRepository;
    
    @Autowired
    private CommandeRepository commandeRepo;

    @Autowired
    private LigneCommandeRepository ligneCommandeRepo;


    
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));
    }
    
    
    @GetMapping("/listCommande")
    public String listComande(Model model) {
        List<Commande> commandeList = commandeRepo.findAll();
        model.addAttribute("commandeList", commandeList);
        return "listCommande";
    }
    

    @GetMapping("/deleteCommande/{id}")
    public String deleteCommande(@PathVariable Long id, RedirectAttributes redirectAttributes) {
    	commandeRepo.deleteById(id);
        redirectAttributes.addFlashAttribute("success", "Commande supprimé avec succès.");
        return "redirect:/listCommande";
    }

    
    
    @GetMapping("/printCommande/{id}")
    public String printCommande(@PathVariable Long id, Model model) {
        Commande commande = commandeRepo.findById(id).orElse(null);
        if (commande == null) {
            return "redirect:/errorPage";
        }

        List<LigneCommande> lignes = ligneCommandeRepo.findByCommande(commande);
        model.addAttribute("commande", commande);
        model.addAttribute("lignes", lignes);

        return "printCommande";
    }

    
    
    @GetMapping("/createCommande")
    public String createCommandeForm(Model model) {
        model.addAttribute("commande", new Commande());
        model.addAttribute("clients", clientRepository.findAll());
        model.addAttribute("boutiques", boutiqueRepository.findAll());
        model.addAttribute("articles", articleRepository.findAll());
        return "createCommande";
    }
    @PostMapping("/createCommande")
    public String saveComande(@ModelAttribute Commande commande, Model model, BindingResult result, HttpServletRequest request, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            model.addAttribute("clients", clientRepository.findAll());
            model.addAttribute("boutiques", boutiqueRepository.findAll());
            model.addAttribute("articles", articleRepository.findAll());
            return "createCommande";
        }

        // Get selected boutique
        Boutique selectedBoutique = boutiqueRepository.findById(commande.getBoutique().getId()).orElse(null);
        if (selectedBoutique == null) {
            result.rejectValue("boutique", "error.boutique", "Boutique non trouvée.");
            return "createCommande";
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
                return "redirect:/createCommande";
            } else if (quantite > entrepot.getStock()) {
                redirectAttributes.addFlashAttribute("error", "Stock insuffisant pour l'article avec ID " + articleId + ". Stock disponible : " + entrepot.getStock());
                return "redirect:/createCommande";
            }
        }

        
        Commande savedCommande = commandeRepo.save(commande);    
        String formattedIdCommande = String.format("%06d", savedCommande.getId());
        savedCommande.setReferance(formattedIdCommande);
        commandeRepo.save(savedCommande);
        // Save LigneDevis
        for (Map.Entry<Long, Integer> entry : articleQuantities.entrySet()) {
            Long articleId = entry.getKey();
            Integer quantite = entry.getValue();

            Article article = articleRepository.findById(articleId).orElse(null);
            if (article != null) {
                LigneCommande ligneCommande = new LigneCommande();
                ligneCommande.setArticle(article);
                ligneCommande.setCommande(savedCommande);
                ligneCommande.setQuantite(quantite);
                ligneCommandeRepo.save(ligneCommande);
            }
        }

        redirectAttributes.addFlashAttribute("success", "Commande ajouté avec succès.");
        return "redirect:/createCommande";
    }


}

