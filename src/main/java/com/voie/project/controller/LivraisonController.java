package com.voie.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.voie.project.repository.LivraisonRepository;
import com.voie.project.service.LivraisonService;

@Controller
public class LivraisonController {

    @Autowired
    private LivraisonService livraisonService;
    @Autowired
    private LivraisonRepository livraisonRepo;

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


}
