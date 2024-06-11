package com.voie.project.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.voie.project.models.ModePaiement;

import com.voie.project.repository.ModePaiementRepository;


@Controller
@RequestMapping
public class ModePaiementController {

	
	@Autowired 
	private ModePaiementRepository modeRepo;
	@GetMapping("/modepaiement")
	public String mode(Model model) {
		model.addAttribute("mode", new ModePaiement());
		List<ModePaiement> mode = modeRepo.findAll();
		model.addAttribute("mode", mode);
		return "/modePaiement";
	}
	
	
	@PostMapping("/modepaiement")
	public String processMode(@Valid ModePaiement mode, BindingResult result) {
	    if (result.hasErrors()) {
	        return "/modePaiement"; 
	    }
	    modeRepo.save(mode);
        return "redirect:/modepaiement"; 
    }	
	
	
	
	
	
	
}
