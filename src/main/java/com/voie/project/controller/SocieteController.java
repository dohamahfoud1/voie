package com.voie.project.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.voie.project.models.Societe;
import com.voie.project.models.Utilisateur;
import com.voie.project.repository.DeviseRepository;
import com.voie.project.repository.SocieteRepository;
import com.voie.project.models.Devise;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/")
public class SocieteController {

	@Autowired
	private SocieteRepository societeRepo;
	
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
	
	
	
	
	
	
	
	
}
