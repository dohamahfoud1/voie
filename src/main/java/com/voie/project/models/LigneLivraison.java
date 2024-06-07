package com.voie.project.models;

import java.math.BigDecimal;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class LigneLivraison {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int quantite;
    private BigDecimal prixHT;
    private BigDecimal prixTTC;


    @ManyToOne
    private Article article;

    @ManyToOne
    private Livraison livraison;

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

	public Article getArticle() {
		return article;
	}

	public void setArticle(Article article) {
		this.article = article;
	}

	public Livraison getLivraison() {
		return livraison;
	}

	public void setLivraison(Livraison livraison) {
		this.livraison = livraison;
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

    
}
