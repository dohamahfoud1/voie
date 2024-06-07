package com.voie.project.service;

import org.springframework.stereotype.Service;

import com.voie.project.models.Client;
import com.voie.project.models.Facturation;
import com.voie.project.models.Livraison;
import com.voie.project.repository.ClientRepository;
import com.voie.project.repository.FacturationRepository;
import com.voie.project.repository.LivraisonRepository;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.Date;
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

	    @Transactional
	    public boolean payerFacture(Long facturationId, BigDecimal montantPaye) {
	        Facturation facturation = facturationRepository.findById(facturationId).orElse(null);
	        if (facturation == null) {
	            return false;
	        }

	        Client client = facturation.getClient();
	        BigDecimal nouveauSolde = client.getSolde().subtract(montantPaye);
	        client.setSolde(nouveauSolde);
	        
	        clientRepository.save(client);

	        // Mise à jour du prix restant à payer sur la facture
	        BigDecimal nouveauPrixRestant = facturation.getPrixRestant().subtract(montantPaye);
	        facturation.setPrixRestant(nouveauPrixRestant);
	        facturationRepository.save(facturation);

	        return true;
	    }
}
