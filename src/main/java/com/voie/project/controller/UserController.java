package com.voie.project.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.voie.project.models.Profiles;
import com.voie.project.models.Utilisateur;
import com.voie.project.repository.ProfileRepository;
import com.voie.project.repository.UtilisateurRepository;

@Controller
@RequestMapping("/")
public class UserController {

	
	@Autowired
	private UtilisateurRepository userRepo;
	
	@Autowired
	private ProfileRepository profileRepo;
	
	  @GetMapping("/gestionUsers")
	    public String gestionUsers(Model model) {
	    	  List<Profiles> listeProfiles = profileRepo.findAll();
			    model.addAttribute("profiles", listeProfiles);
	        return "/gestionUtilisateurs";
	    }
	  
	  @PostMapping("/saveUser")
	    public String saveUser(
	                           @RequestParam("mot_de_passe") String mot_de_passe,
	                           @RequestParam("nom") String nom,
	                           @RequestParam("profileId") Long profileId,
	                           @RequestParam(value = "cloturer", required = false) boolean cloturer) {
	        // Créer un nouvel utilisateur
	        Utilisateur user = new Utilisateur();
	        user.setNom(nom);
	        user.setMot_de_passe(mot_de_passe);
	        user.setCloturer(cloturer);

	        // Récupérer le profil sélectionné par son ID
	        Profiles profile = profileRepo.findById(profileId)
	                .orElseThrow(() -> new IllegalArgumentException("Profil non trouvé avec l'identifiant : " + profileId));

	        // Associer le profil à l'utilisateur
	        user.setProfile(profile);

	        // Enregistrer l'utilisateur dans la base de données
	        userRepo.save(user);

	        // Rediriger vers une page de confirmation ou une autre page appropriée
	        return "redirect:/gestionUsers";
	    }
	  
	  @GetMapping("/listeUsers")
	  public String afficherListeUtilisateurs(Model model) {
	        List<Utilisateur> utilisateurs = userRepo.findAll();
	        model.addAttribute("utilisateurs", utilisateurs);
	        return "listeUsers";
	    }
	
	
	
}
