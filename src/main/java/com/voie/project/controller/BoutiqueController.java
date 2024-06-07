package com.voie.project.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;


import com.voie.project.models.Boutique;
import com.voie.project.models.Entrepot;
import com.voie.project.repository.BoutiqueRepository;
import com.voie.project.repository.EntrepotRepository;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/")
public class BoutiqueController {

	
	@Autowired
	private BoutiqueRepository boutiqueRepo;
	
	 @Autowired
	    private EntrepotRepository entrepotRepository;
	
	@GetMapping("/boutique")
	public String boutique(Model model) {
		model.addAttribute("boutique", new Boutique());
		return "/boutique";
	}
	@PostMapping("/boutique")
	public String processBoutique(@Valid @ModelAttribute("boutique") Boutique boutique,BindingResult result) {
	    if (result.hasErrors()) {
	        return "/boutique"; 
	    }
     
        boutiqueRepo.save(boutique);
        return "redirect:/boutique"; 
    }
	
	
	
	@GetMapping("/listeBoutiques")
    public String listBoutiques(Model model) {
        List<Boutique> boutiques = boutiqueRepo.findAll();
        model.addAttribute("boutiques", boutiques);
        return "listeBoutiques";
    }

    @GetMapping("/boutiques")
    public String viewBoutique(Long boutiqueId, Model model) {
        Boutique boutique = boutiqueRepo.findById(boutiqueId).orElseThrow();
        List<Entrepot> entrepots = entrepotRepository.findByBoutiqueId(boutiqueId);
        model.addAttribute("boutique", boutique);
        model.addAttribute("entrepots", entrepots);
        return "boutiqueDetails";
    }
	
	
}
