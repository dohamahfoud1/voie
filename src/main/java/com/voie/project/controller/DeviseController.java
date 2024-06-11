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
import com.voie.project.models.Devise;
import com.voie.project.repository.DeviseRepository;



@Controller
@RequestMapping("/")
public class DeviseController {
	
	@Autowired
	private DeviseRepository deviseRepo;
	
	
	  @GetMapping("/listeDevises")
			public String afficherDevises(@RequestParam(required = false) String search,Model model) {
		  List<Devise> listeDevises;

		    if (search != null && !search.trim().isEmpty()) {
		        try {
		            // Essayez de parser le paramètre en double
		            Double taux = Double.parseDouble(search.trim());
		            // Si cela réussit, effectuez une recherche par taux
		            listeDevises = deviseRepo.findByTaux(taux);
		        } catch (NumberFormatException e) {
		            // Si le parsing échoue, effectuez une recherche par nom
		        	listeDevises = deviseRepo.findByNomContainingIgnoreCase(search);
		        }
		    } else {
		        // Si le paramètre de recherche est vide ou null, retournez toutes les devises
		    	listeDevises = deviseRepo.findAll();
		    }
			    model.addAttribute("devise", new Devise());
			    model.addAttribute("listeDevises", listeDevises);
			    return "/listeDevise";
			}
			
	@PostMapping("/listeDevises")
	public String processSociete(@Valid Devise devise, BindingResult result,Model model) {
		 if (result.hasErrors()) {
		        List<Devise> listeDevises = deviseRepo.findAll();
		        model.addAttribute("listeDevises", listeDevises);
		        return "/listeDevise"; // Retourne à la page avec les erreurs affichées
		    }
     
        deviseRepo.save(devise);
        return "redirect:/listeDevises"; 
    }
	
	 @GetMapping("/delete/{id}")
	    public String supprimerDevise(@PathVariable Long id) {
	        deviseRepo.deleteById(id);
	        return "redirect:/listeDevises"; 
	    }
	 
	 @GetMapping("/edit")
	    public String showEditForm(Long deviseId, Model model) {
	        Devise devise = deviseRepo.findById(deviseId).orElseThrow(() -> new IllegalArgumentException("Invalid devise Id:" + deviseId));
	        model.addAttribute("devise", devise);
	        return "editDevise";
	    }
	 

	    @PostMapping("/saveDevis")
	    public String saveDevise(@ModelAttribute("devise") Devise devise) {
	        deviseRepo.save(devise);
	        return "redirect:/listeDevises";
	    }	

}
