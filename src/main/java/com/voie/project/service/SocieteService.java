package com.voie.project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.voie.project.models.Societe;
import com.voie.project.repository.DeviseRepository;
import com.voie.project.repository.SocieteRepository;

@Service
public class SocieteService {
	
	@Autowired
	private SocieteRepository societeRepo;
	
	@Autowired
	private DeviseRepository deviseRepo;
	
	
	 public void updateSociete(Long id, Societe updatedSociete) {
		    Societe existingSociete = societeRepo.findById(id)
		        .orElseThrow(() -> new RuntimeException("Societe not found"));

		    existingSociete.setNom(updatedSociete.getNom());
		    existingSociete.setResponsable(updatedSociete.getResponsable());
		    existingSociete.setAdresse(updatedSociete.getAdresse());
		    existingSociete.setVille(updatedSociete.getVille());
		    existingSociete.setTelephone(updatedSociete.getTelephone());
		    existingSociete.setEmail(updatedSociete.getEmail());
		    existingSociete.setCnss(updatedSociete.getCnss());

		    // Fetch the profile entity from the database
		    String devise = deviseRepo.findByNom(updatedSociete.getDevise());

		    existingSociete.setDevise(devise);

		    societeRepo.save(existingSociete);
		}

}
