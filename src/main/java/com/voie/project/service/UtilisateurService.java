package com.voie.project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import com.voie.project.models.Profiles;
import com.voie.project.models.Utilisateur;
import com.voie.project.repository.ProfileRepository;
import com.voie.project.repository.UtilisateurRepository;

@Service
public class UtilisateurService {
	@Autowired
	private ProfileRepository profileRepo;
	
	
	 public Utilisateur findById(Long id)
	 {
		 return utilisateurRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
	

	 }
	 
	 
	 @Autowired
	    private UtilisateurRepository utilisateurRepository;

	 public void updateUser(Long id, Utilisateur updatedUtilisateur) {
		    Utilisateur existingUtilisateur = utilisateurRepository.findById(id)
		        .orElseThrow(() -> new RuntimeException("User not found"));

		    existingUtilisateur.setNom(updatedUtilisateur.getNom());
		    existingUtilisateur.setAdresse(updatedUtilisateur.getAdresse());
		    existingUtilisateur.setTelephone(updatedUtilisateur.getTelephone());
		    existingUtilisateur.setCloturer(updatedUtilisateur.isCloturer());

		    // Fetch the profile entity from the database
		    Profiles profile = profileRepo.findById(updatedUtilisateur.getProfile().getId())
		        .orElseThrow(() -> new RuntimeException("Profile not found"));

		    existingUtilisateur.setProfile(profile);

		    utilisateurRepository.save(existingUtilisateur);
		}


}
