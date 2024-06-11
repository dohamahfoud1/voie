package com.voie.project.controller;

import java.util.List;

import javax.transaction.Transactional;
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

import com.voie.project.models.CompteBancaire;
import com.voie.project.repository.BanqueRepository;
import com.voie.project.repository.CompteBancaireRepository;


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
	        List<CompteBancaire> comptes = compteRepo.findAll();
	        model.addAttribute("comptes", comptes);
	        return "listeCompteBancaire";
	    }
	
	  
	  
	   @GetMapping("/deleteCompte/{id}")
	    public String deleteCompte(@PathVariable Long id, RedirectAttributes redirectAttributes) {
		   compteRepo.deleteById(id);
	        redirectAttributes.addFlashAttribute("success", "Compte supprimé avec succès.");
	        return "redirect:/listeCompteBancaire";
	    }
}
