package com.voie.project.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.voie.project.models.Access;
import com.voie.project.models.Devise;
import com.voie.project.models.Permissions;
import com.voie.project.models.Profiles;
import com.voie.project.models.SpecificPermissions;
import com.voie.project.repository.AccessRepository;
import com.voie.project.repository.PermissionsRepository;
import com.voie.project.repository.ProfileRepository;
import com.voie.project.repository.specificPermissionRepository;
import com.voie.project.service.PermissionsService;
import com.voie.project.service.ProfileService;
import com.voie.project.service.SpecificPermissionsService;


@Controller
@RequestMapping("/")
public class ProfilesController {
	
	@Autowired
	private ProfileRepository profileRepo;
	
       	@Autowired
    	 private PermissionsRepository permissionRepository;
       	   	
	    @Autowired
	    private AccessRepository accessRepository;

	    
	//GestionProfile
	    @Autowired
	    private ProfileService profileService;

	    @Autowired
	    private PermissionsService permissionService;
	    
	    @Autowired
	    private SpecificPermissionsService specPermService;
	    @Autowired
	    private specificPermissionRepository SpecPermsRepo;
	
	  
	    @GetMapping("/gestionProfils")
	    public String afficherProfiles(@RequestParam(required = false) String search, Model model) {
	        List<Profiles> listeProfiles;
	        
	        if (search != null && !search.isEmpty()) {
	           
	            listeProfiles = profileRepo.findByProfileNameContainingIgnoreCase(search);
	        } else {
	      
	            listeProfiles = profileRepo.findAll();
	        }
	        
	        model.addAttribute("profiles", listeProfiles);
	        return "/gestionDesProfils";
	    }
			
		    @GetMapping("/ajouterprofils")
		    public String showCreateProfileForm(Model model) {
		        Profiles profile = new Profiles();
		        List<Permissions> permissions = permissionRepository.findAll();
		        model.addAttribute("profile", profile);
		        model.addAttribute("perms", permissions);
		        return "/ajouterProfile";
		    }
		    @GetMapping("/options")
		    public String getSpecificPermissions(Long permId, Model model) {
		        List<SpecificPermissions> specificPermissions = specPermService.findByPermissionId(permId);
		        model.addAttribute("options", specificPermissions);
		        return "optionsPermissions";
		    }

		    @PostMapping("/saveOptions")
		    public String saveOptions(@RequestParam List<Long> optionIds) {
		        List<SpecificPermissions> options = specPermService.getAllOptions();

		        return "redirect:/ajouterprofils";
		    }
		    @PostMapping("/saveProfile")
		    public String saveProfile(@RequestParam("profileName") String name,
		                              @RequestParam List<Long> id) {
		        Profiles profile = new Profiles();
		        profile.setProfileName(name);
		        profileService.saveProfileWithPermissions(profile, id);
		        return "redirect:/ajouterprofils";
		    }
		    
		    @Transactional
		    @GetMapping("/deleteProfile/{id}")
		    public String supprimerDevise(@PathVariable Long id) {
		    	accessRepository.deleteByProfileId(id);
		    	profileRepo.deleteById(id);
		        return "redirect:/gestionProfils"; 
		    }
		  		
		    
		    
		    @GetMapping("/visualiserProfile")
		    public String visualiserProfile(Long profileId, Model model) {
		       
		    	 Profiles profile = profileRepo.findById(profileId).orElse(null);
		         // Trouver tous les accès associés à ce profil
		         List<Access> accessList = accessRepository.findByProfile(profile);
		         
		         // Préparer une liste de permissions
		         List<String> permissions = new ArrayList<>();
		         for (Access access : accessList) {
		             permissions.add(access.getPermission().getPermission());
		         }
		         
		         // Ajouter les attributs au modèle pour les afficher dans la vue
		         model.addAttribute("profileName", profile.getProfileName());
		         model.addAttribute("permissions", permissions);
		         return "visualiserProfile";
		    }
		    
		    
		    
		    @GetMapping("/editProfile")
		    public String showEditForm(Long profileId, Model model) {
		        Profiles profile = profileRepo.findById(profileId)
		                .orElseThrow(() -> new IllegalArgumentException("Profil non trouvé avec id : " + profileId));
		        
		        List<Permissions> allPermissions = permissionRepository.findAll();
		        List<Long> profilePermissions = accessRepository.findByProfile(profile).stream()
		                .map(access -> access.getPermission().getId())
		                .collect(Collectors.toList());

		        model.addAttribute("profile", profile);
		        model.addAttribute("allPermissions", allPermissions);
		        model.addAttribute("profilePermissions", profilePermissions);
		        return "editeProfile";
		    }

		    @Transactional
		    @PostMapping("/updateProfile/{id}")
		    public String updateProfile(@PathVariable("id") Long id,
		                                @ModelAttribute Profiles profile,
		                                @RequestParam(required = false) List<Long> permissions) {
		        // Récupérer le profil existant par son ID
		        Profiles existingProfile = profileRepo.findById(id)
		                .orElseThrow(() -> new IllegalArgumentException("Profil non trouvé avec l'identifiant : " + id));
		        
		        // Mettre à jour les champs du profil existant avec les nouvelles valeurs
		        existingProfile.setProfileName(profile.getProfileName());
		        
		        // Sauvegarder les modifications apportées au profil existant
		        profileRepo.save(existingProfile);

		        // Supprimer les autorisations existantes pour ce profil
		        accessRepository.deleteByProfileId(existingProfile.getId());
		        
		        // Si des autorisations ont été fournies, les associer au profil
		        if (permissions != null) {
		            // Récupérer les objets de permission à partir de leurs IDs
		            List<Permissions> permissionList = permissionRepository.findAllById(permissions);
		            
		            // Associer chaque permission au profil
		            for (Permissions permission : permissionList) {
		                Access access = new Access();
		                access.setProfile(existingProfile);
		                access.setPermission(permission);
		                accessRepository.save(access);
		            }
		        }
		        
		        // Rediriger vers la liste des profils après la mise à jour
		        return "redirect:/gestionProfils";
		    }

}