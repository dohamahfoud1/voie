package com.voie.project.models;

import java.math.BigDecimal;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class LigneCommande {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int quantite;
    private BigDecimal prixHT;
    private BigDecimal prixTTC;

    @ManyToOne
    private Article article;

    @ManyToOne
    private Commande commande;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getQuantite() {
		return quantite;
	}

	public void setQuantite(int quantite) {
		this.quantite = quantite;
	}

	public BigDecimal getPrixHT() {
		return prixHT;
	}

	public void setPrixHT(BigDecimal prixHT) {
		this.prixHT = prixHT;
	}

	public BigDecimal getPrixTTC() {
		return prixTTC;
	}

	public void setPrixTTC(BigDecimal prixTTC) {
		this.prixTTC = prixTTC;
	}

	public Article getArticle() {
		return article;
	}

	public void setArticle(Article article) {
		this.article = article;
	}

	public Commande getCommande() {
		return commande;
	}

	public void setCommande(Commande commande) {
		this.commande = commande;
	}
    
    
}