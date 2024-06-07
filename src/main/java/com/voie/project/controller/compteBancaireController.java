package com.voie.project.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.voie.project.models.CompteBancaire;
import com.voie.project.repository.BanqueRepository;
import com.voie.project.repository.CompteBancaireRepository;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

import com.voie.project.models.*;

@Controller
@RequestMapping("/")
public class compteBancaireController {

	@Autowired
	private CompteBancaireRepository compteRepo;
	
	@Autowired
	private BanqueRepository banqueRepo;
	@Transactional
	@GetMapping("/compteBancaire")
	public String compte(Model model) {
		model.addAttribute("compteBancaire", new CompteBancaire());
		List<Banque> banque = banqueRepo.findAll();
		model.addAttribute("banque", banque);
		return "/compteBancaire";
	}
	@Transactional
	@PostMapping("/compteBancaire")
	public String processSociete(@Valid CompteBancaire compte, BindingResult result) {
	    if (result.hasErrors()) {
	        return "/compteBancaire"; 
	    }
     
	    compteRepo.save(compte);
        return "redirect:/compteBancaire"; 
    }
	
	  @GetMapping("/listeCompteBancaire")
	  public String afficherListeSociete(Model model) {
	        List<CompteBancaire> compteBancaire = compteRepo.findAll();
	        model.addAttribute("compteBancaire", compteBancaire);
	        return "listeCompteBancaire";
	    }
	
}
