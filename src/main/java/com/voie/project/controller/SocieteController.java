package com.voie.project.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.voie.project.models.Societe;
import com.voie.project.models.Utilisateur;
import com.voie.project.repository.DeviseRepository;
import com.voie.project.repository.SocieteRepository;
import com.voie.project.service.SocieteService;
import com.voie.project.models.Devise;
import com.voie.project.models.Profiles;


@Controller
@RequestMapping("/")
public class SocieteController {

	@Autowired
	private SocieteRepository societeRepo;
	
	@Autowired
	private SocieteService societeService;
	@Autowired
	private DeviseRepository deviseRepo;
	
	@GetMapping("/societe")
	public String societe(Model model) {
		model.addAttribute("societe", new Societe());
		List<Devise> devise = deviseRepo.findAll();
		model.addAttribute("devise", devise);
		return "/societe";
	}
	@PostMapping("/societe")
	public String processSociete(@Valid Societe societe, BindingResult result) {
	    if (result.hasErrors()) {
	        return "/societe"; 
	    }
     
        societeRepo.save(societe);
        return "redirect:/societe"; 
    }
	
	  @GetMapping("/listeSociete")
	  public String afficherListeSociete(Model model) {
	        List<Societe> societe = societeRepo.findAll();
	        model.addAttribute("societe", societe);
	        return "listeSociete";
	    }
	
	  
	  
	   @GetMapping("/deleteSociete/{id}")
	    public String deleteSociete(@PathVariable Long id, RedirectAttributes redirectAttributes) {
		   societeRepo.deleteById(id);
	        redirectAttributes.addFlashAttribute("success", "Societe supprimé avec succès.");
	        return "redirect:/listeSociete";
	    }
	  
	   
	   @GetMapping("/editSociete")
	   public String showEditForm(@RequestParam("SocieteId") Long SocieteId, Model model) {
	       Societe societe = societeRepo.findById(SocieteId).orElseThrow(() -> new IllegalArgumentException("Invalid societe Id:" + SocieteId));
	       model.addAttribute("societe", societe);
	       List<Devise> devise = deviseRepo.findAll();
			model.addAttribute("devise", devise);
	       return "editSociete";
	   }

	   @PostMapping("/editSociete")
	   public String updateSociete(@RequestParam("id") Long SocieteId, @ModelAttribute("societe") Societe societe) {
	       societeService.updateSociete(SocieteId, societe);
	       return "redirect:/listeSociete";
	   }
	   
	
	
	
	
	
	
	
}
