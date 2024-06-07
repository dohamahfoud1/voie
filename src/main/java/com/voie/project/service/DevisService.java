package com.voie.project.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.voie.project.models.Commande;
import com.voie.project.models.Devis;
import com.voie.project.models.LigneCommande;
import com.voie.project.models.LigneDevis;
import com.voie.project.models.Permissions;
import com.voie.project.models.Profiles;
import com.voie.project.repository.CommandeRepository;
import com.voie.project.repository.DevisRepository;
import com.voie.project.repository.LigneCommandeRepository;
import com.voie.project.repository.LigneDevisRepository;

import jakarta.transaction.Transactional;

@Service
public class DevisService {

    @Autowired
    private DevisRepository devisRepository;
    
    @Autowired
    private LigneDevisRepository devisArticleRepository;
    @Autowired
    private CommandeRepository commandeRepository;
    
    @Autowired
    private LigneCommandeRepository ligneCommandeRepository;




    @Transactional
    public boolean transfertEnCommande(Long devisId) {
        Devis devis = devisRepository.findById(devisId).orElse(null);
        if (devis == null) {
            return false;
        }

        // Vérifier si le devis a déjà été transféré en commande
        if (devis.isTransfereEnCommande()) {
            return false;
        }

        // Création d'une nouvelle commande avec les informations du devis
        Commande commande = new Commande();
        commande.setDateCreation(new Date()); // Date de création de la commande
        commande.setClient(devis.getClient());
        commande.setBoutique(devis.getBoutique());
        commande.setReferance(devis.getReferance());

        // Transférer les totaux en fonction de la base de calcul
        if ("HT".equalsIgnoreCase(devis.getBaseCalcul())) {
            commande.setTotalHT(devis.getTotalHT());
            commande.setTotalTTC(BigDecimal.ZERO); // ou un autre traitement approprié
        } else if ("TTC".equalsIgnoreCase(devis.getBaseCalcul())) {
            commande.setTotalTTC(devis.getTotalTTC());
            commande.setTotalHT(BigDecimal.ZERO); // ou un autre traitement approprié
        }

        commandeRepository.save(commande);

        // Création des lignes de commande à partir des lignes de devis
        for (LigneDevis ligneDevis : devis.getLignesDevis()) {
            LigneCommande ligneCommande = new LigneCommande();
            ligneCommande.setCommande(commande);
            ligneCommande.setArticle(ligneDevis.getArticle());
            ligneCommande.setQuantite(ligneDevis.getQuantite());
            ligneCommandeRepository.save(ligneCommande);
        }

        // Marquer le devis comme transféré en commande
        devis.setTransfereEnCommande(true);
        devisRepository.save(devis);

        return true;
    }

}