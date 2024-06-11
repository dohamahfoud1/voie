package com.voie.project.controller;

import java.security.Principal;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.voie.project.models.Access;
import com.voie.project.models.PasswordChangeForm;
import com.voie.project.models.Permissions;
import com.voie.project.models.Utilisateur;
import com.voie.project.repository.ProfileRepository;
import com.voie.project.repository.UtilisateurRepository;
import com.voie.project.service.ProfileService;
import com.voie.project.service.UserDetailService;



@Controller
public class ProfileController {

	  
	    @Autowired
	    private UserDetailService userDetailService;
	    @Autowired
	    private ProfileRepository profilesRepository;
	    @Autowired
	    private ProfileService profileService;
	    @Autowired
	    private UtilisateurRepository utilisateurRepository;
	    
	    @Autowired
	    private PasswordEncoder passwordEncoder;
	    
	    @GetMapping("/profile")
	    public String showUserProfile(Model model) {
	        Utilisateur authenticatedUser = profileService.getAuthenticatedUserWithPermissions();

	        if (authenticatedUser != null) {
	            Set<Permissions> permissions = authenticatedUser.getProfile().getAccesses().stream()
	                    .map(Access::getPermission)
	                    .collect(Collectors.toSet());
	            model.addAttribute("user", authenticatedUser);
	            model.addAttribute("permissions", permissions);
	            return "profile"; // Assurez-vous que le chemin est correct
	        } else {
	            return "redirect:/connexion";
	        }
	    }

	    @GetMapping("/changerMotDePasse")
	    public String afficherPageChangementMotDePasse() {
	        return "changerMotDePasse"; // Le nom de la vue HTML pour le formulaire de changement de mot de passe
	    }
	
	    @PostMapping("/changerMotDePasse")
	    public String changerMotDePasse(@RequestParam("motDePasseActuel") String motDePasseActuel,
	                                    @RequestParam("nouveauMotDePasse") String nouveauMotDePasse,
	                                    Model model) {
	       
	        Utilisateur utilisateur = profileService.getAuthenticatedUserWithPermissions();

	        if (utilisateur != null) {
	            // Vérifier si le mot de passe actuel est null ou incorrect
	            if (!passwordEncoder.matches(motDePasseActuel, utilisateur.getMot_de_passe())) {
	                model.addAttribute("errorMessage", "Le mot de passe actuel est incorrect. Veuillez réessayer.");
	                return "redirect:/changerMotDePasse"; // Ou rediriger vers une page appropriée
	            }
	            
	            // Mettre à jour le mot de passe de l'utilisateur avec le nouveau mot de passe
	            utilisateur.setMot_de_passe(passwordEncoder.encode(nouveauMotDePasse));
	            utilisateurRepository.save(utilisateur);

	            // Afficher un message de succès
	            model.addAttribute("successMessage", "Le mot de passe a été changé avec succès.");
	        } else {
	            // Utilisateur non trouvé ou non authentifié
	            // Afficher un message d'erreur ou rediriger vers une page de connexion
	            model.addAttribute("errorMessage", "Utilisateur non trouvé ou non authentifié.");
	        }

	        // Rediriger l'utilisateur vers une page appropriée (par exemple, la même page de profil)
	        return "redirect:/profile";
	    }


	    
}
