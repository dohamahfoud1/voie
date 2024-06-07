package com.voie.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.voie.project.models.Banque;
import com.voie.project.repository.BanqueRepository;


import jakarta.validation.Valid;

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


}
