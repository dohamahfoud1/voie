package com.voie.project.service;

import org.springframework.stereotype.Service;

import com.voie.project.models.Client;
import com.voie.project.models.Facturation;
import com.voie.project.models.Livraison;
import com.voie.project.repository.ClientRepository;
import com.voie.project.repository.FacturationRepository;
import com.voie.project.repository.LivraisonRepository;


import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.Date;

import javax.transaction.Transactional;
@Service
public class FacturationService {

	
	   @Autowired
	    private LivraisonRepository livraisonRepository;

	    @Autowired
	    private FacturationRepository facturationRepository;

	    @Autowired
	    private ClientRepository clientRepository;
	
	    @Transactional
	    public boolean transfertEnFacturation(Long livraisonId) {
	        Livraison livraison = livraisonRepository.findById(livraisonId).orElse(null);
	        if (livraison == null) {
	            return false;
	        }
	        if (livraison.isTransfereEnFacturation()) {
	            return false;
	        }

	        // Création d'une nouvelle facturation avec les informations de la livraison
	        Facturation facturation = new Facturation();
	        facturation.setDateCreation(new Date()); // Date de création de la facturation
	        facturation.setClient(livraison.getClient());
	        facturation.setLivraison(livraison);
	        facturation.setTotalHT(livraison.getTotalHT());
	        facturation.setTotalTTC(livraison.getTotalTTC());
	      
	        if (livraison.getReferance() != null) {
	        	facturation.setReferance(livraison.getReferance());
	        }

	        // Calcul du prix total
	        BigDecimal prixTotal = livraison.getTotalHT().add(livraison.getTotalTTC());
	        facturation.setPrixTotal(prixTotal);
	        facturation.setPrixRestant(prixTotal);

	        facturationRepository.save(facturation);

	        // Mise à jour du solde du client
	        Client client = livraison.getClient();
	        client.setSolde(client.getSolde().add(prixTotal));
	        // Marquer la commande comme transférée en livraison
	        livraison.setTransfereEnFacturation(true);
	        clientRepository.save(client);

	        return true;
	    }


}