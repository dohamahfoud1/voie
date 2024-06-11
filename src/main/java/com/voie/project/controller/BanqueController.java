package com.voie.project.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.voie.project.models.Banque;
import com.voie.project.models.Utilisateur;
import com.voie.project.repository.BanqueRepository;




@Controller
@RequestMapping("/")
public class BanqueController {
	
	
	
	@Autowired
	private BanqueRepository banqueRepo;
	
	@GetMapping("/banque")
	public String societe(Model model) {
		model.addAttribute("banque", new Banque());
		
		return "/banque";
	}
	@PostMapping("/banque")
	public String processSociete(@Valid Banque banque, BindingResult result) {
	    if (result.hasErrors()) {
	        return "/banque"; 
	    }
     
        banqueRepo.save(banque);
        return "redirect:/banque"; 
    }
	
	
	  @GetMapping("/listeBanques")
	  public String afficherBanques(Model model) {
	        List<Banque> banques = banqueRepo.findAll();
	        model.addAttribute("banques", banques);
	        return "listeBanques";
	    }
	
	  
	  
	   @GetMapping("/deleteBanque/{id}")
	    public String deleteBanque(@PathVariable Long id, RedirectAttributes redirectAttributes) {
		   banqueRepo.deleteById(id);
	        redirectAttributes.addFlashAttribute("success", "Banque supprimé avec succès.");
	        return "redirect:/listeBanques";
	    }
	  


}
