package com.voie.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.voie.project.repository.ArticleRepository;
import com.voie.project.repository.BoutiqueRepository;
import com.voie.project.repository.CommandeRepository;
import com.voie.project.repository.FacturationRepository;
import com.voie.project.repository.FacturesPayeesRepository;
import com.voie.project.repository.UtilisateurRepository;

@Controller
public class LoginController {
	
	@Autowired
	private ArticleRepository articleRepo;
    @Autowired
    private CommandeRepository commandeRepo;
    @Autowired
    private FacturesPayeesRepository facturesPayeesRepo;
    @Autowired
	private UtilisateurRepository userRepo;
    @Autowired
	private BoutiqueRepository boutiqueRepo;
	    @GetMapping("/connexion")
	    public String showLoginForm() {
	        return "/connexion";
	    }

	    @PostMapping("/traiter_connexion")
	    public String performLogin(@RequestParam String nom, @RequestParam String mot_de_passe) {
	        
	        return "redirect:/tableau_de_bord"; 
	    }

	    @GetMapping("/tableau_de_bord")
	    public String dashboard() {
	        return "/createDevis"; 
	    }

	    @GetMapping("/admin")
	    public String showAdminPage(Model model) {
	    	  long totalArticles = articleRepo.count();
	    	  long totalComandesPayees = facturesPayeesRepo.count();
	    	  long totalCommandes = commandeRepo.count();
	    	  long totalUsers = userRepo.count();
	    	  long totalBoutiques = boutiqueRepo.count();
	          model.addAttribute("totalArticles", totalArticles);
	          model.addAttribute("totalComandesPayees", totalComandesPayees);
	          model.addAttribute("totalCommandes", totalCommandes);
	          model.addAttribute("totalUsers", totalUsers);
	          model.addAttribute("totalBoutiques", totalBoutiques);
	        return "admin"; 
	    }
	
}
