package com.voie.project.service;



import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.voie.project.models.Article;
import com.voie.project.models.Commande;
import com.voie.project.models.LigneCommande;
import com.voie.project.models.LigneLivraison;
import com.voie.project.models.Livraison;
import com.voie.project.repository.ArticleRepository;
import com.voie.project.repository.CommandeRepository;
import com.voie.project.repository.EntrepotRepository;
import com.voie.project.repository.LigneLivraisonRepository;
import com.voie.project.repository.LivraisonRepository;
import com.voie.project.models.Entrepot;

@Service
public class LivraisonService {

    @Autowired
    private CommandeRepository commandeRepository;

    @Autowired
    private LivraisonRepository livraisonRepository;

    @Autowired
    private LigneLivraisonRepository ligneLivraisonRepository;

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private EntrepotRepository entrepotRepository;

    @Transactional
    public boolean transfertEnLivraison(Long commandeId) {
        Commande commande = commandeRepository.findById(commandeId).orElse(null);
        if (commande == null) {
            return false;
        }

        // Vérifier si la commande a déjà été transférée
        if (commande.isTransfereEnLivraison()) {
            return false;
        }

        // Création d'une nouvelle livraison avec les informations de la commande
        Livraison livraison = new Livraison();
        livraison.setDateCreation(new Date()); // Date de création de la livraison
        livraison.setClient(commande.getClient());
        livraison.setBoutique(commande.getBoutique());
        
        
        if (commande.getReferance() != null) {
        	livraison.setReferance(commande.getReferance());
        }
        if (commande.getTotalHT() != null) {
        	livraison.setTotalHT(commande.getTotalHT());
        }
        if (commande.getTotalTTC() != null) {
        	livraison.setTotalTTC(commande.getTotalTTC());
        }
        
        
        livraisonRepository.save(livraison);

        // Création des lignes de livraison à partir des lignes de commande
        for (LigneCommande ligneCommande : commande.getLignesCommande()) {
            LigneLivraison ligneLivraison = new LigneLivraison();
            ligneLivraison.setLivraison(livraison);
            ligneLivraison.setArticle(ligneCommande.getArticle());
            ligneLivraison.setQuantite(ligneCommande.getQuantite());
            
            
            
            ligneLivraisonRepository.save(ligneLivraison);

            // Mise à jour du stock total de l'article
            Article article = ligneCommande.getArticle();
            article.setStockTotale(article.getStockTotale() - ligneCommande.getQuantite());
            articleRepository.save(article);

            // Mise à jour du stock de l'article dans l'entrepôt de la boutique
            List<Entrepot> entrepots = entrepotRepository.findByArticleAndBoutique(article, commande.getBoutique());
            if (!entrepots.isEmpty()) {
                Entrepot entrepot = entrepots.get(0);
                entrepot.setStock(entrepot.getStock() - ligneCommande.getQuantite());
                entrepotRepository.save(entrepot);
            }
        }

        // Marquer la commande comme transférée en livraison
        commande.setTransfereEnLivraison(true);
        commandeRepository.save(commande);

        return true;
    }

    public List<Commande> getAllCommandes() {
        return commandeRepository.findAll();
    }
}
