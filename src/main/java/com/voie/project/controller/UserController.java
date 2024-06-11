package com.voie.project.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.voie.project.models.Profiles;
import com.voie.project.models.Utilisateur;
import com.voie.project.repository.ProfileRepository;
import com.voie.project.repository.UtilisateurRepository;
import com.voie.project.service.UtilisateurService;

@Controller
@RequestMapping("/")
public class UserController {

	
	@Autowired
	private UtilisateurRepository userRepo;
	
	@Autowired
	private ProfileRepository profileRepo;
	
	
	@Autowired
	private UtilisateurService utilisateurService;
	
	  @GetMapping("/gestionUsers")
	    public String gestionUsers(Model model) {
	    	  List<Profiles> listeProfiles = profileRepo.findAll();
			    model.addAttribute("profiles", listeProfiles);
	        return "/gestionUtilisateurs";
	    }
	  
	  @PostMapping("/saveUser")
	  public String saveUser(@RequestParam("adresse") String adresse,
	                         @RequestParam("telephone") String telephone,
	                         @RequestParam("mot_de_passe") String mot_de_passe,
	                         @RequestParam("nom") String nom,
	                         @RequestParam("profileId") Long profileId,
	                         @RequestParam(value = "cloturer", required = false) boolean cloturer) {
	      // Créer un nouvel utilisateur
	      Utilisateur user = new Utilisateur();
	      user.setNom(nom);
	      BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
	      String encodedPassword = passwordEncoder.encode(mot_de_passe);
	      user.setMot_de_passe(encodedPassword);
	      
	      user.setCloturer(cloturer);
	      user.setAdresse(adresse);
	      user.setTelephone(telephone);

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
	
	  
	  
	   @GetMapping("/deleteUser/{id}")
	    public String deleteUser(@PathVariable Long id, RedirectAttributes redirectAttributes) {
		   userRepo.deleteById(id);
	        redirectAttributes.addFlashAttribute("success", "Utilisateur supprimé avec succès.");
	        return "redirect:/listeUsers";
	    }
	  
	   
	   @GetMapping("/editUser")
	   public String showEditForm(@RequestParam("UserId") Long UserId, Model model) {
	       Utilisateur utilisateur = userRepo.findById(UserId).orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + UserId));
	       model.addAttribute("utilisateur", utilisateur);
	       List<Profiles> profiles = profileRepo.findAll();
	       model.addAttribute("profiles", profiles);
	       return "editUser";
	   }

	   @PostMapping("/editUser")
	   public String updateUser(@RequestParam("id") Long UserId, @ModelAttribute("utilisateur") Utilisateur utilisateur) {
	       utilisateurService.updateUser(UserId, utilisateur);
	       return "redirect:/listeUsers";
	   }
	   
	   
	
	
}
