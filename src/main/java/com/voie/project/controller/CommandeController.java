package com.voie.project.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.voie.project.models.Commande;
import com.voie.project.models.Devis;
import com.voie.project.models.LigneCommande;
import com.voie.project.repository.CommandeRepository;
import com.voie.project.repository.LigneCommandeRepository;

@Controller
public class CommandeController {
	
    @Autowired
    private CommandeRepository commandeRepo;

    @Autowired
    private LigneCommandeRepository ligneCommandeRepo;


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


}

