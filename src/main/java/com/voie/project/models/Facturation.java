package com.voie.project.models;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;

@Entity
public class Facturation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Date dateCreation;

    @ManyToOne
    private Client client;

    @ManyToOne
    private Livraison livraison;
    private String referance;

    private BigDecimal totalHT;
    private BigDecimal totalTTC;
    private BigDecimal prixTotal;
    private BigDecimal prixRestant;

    @PrePersist
    public void prePersist() {
        prixRestant = prixTotal;
    }
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Date getDateCreation() {
		return dateCreation;
	}
	public void setDateCreation(Date dateCreation) {
		this.dateCreation = dateCreation;
	}
	public Client getClient() {
		return client;
	}
	public void setClient(Client client) {
		this.client = client;
	}
	public Livraison getLivraison() {
		return livraison;
	}
	public void setLivraison(Livraison livraison) {
		this.livraison = livraison;
	}
	public BigDecimal getTotalHT() {
		return totalHT;
	}
	public void setTotalHT(BigDecimal totalHT) {
		this.totalHT = totalHT;
	}
	public BigDecimal getTotalTTC() {
		return totalTTC;
	}
	public void setTotalTTC(BigDecimal totalTTC) {
		this.totalTTC = totalTTC;
	}
	public BigDecimal getPrixTotal() {
		return prixTotal;
	}
	public void setPrixTotal(BigDecimal prixTotal) {
		this.prixTotal = prixTotal;
	}
	public BigDecimal getPrixRestant() {
		return prixRestant;
	}
	public void setPrixRestant(BigDecimal prixRestant) {
		this.prixRestant = prixRestant;
	}
	public String getReferance() {
		return referance;
	}
	public void setReferance(String referance) {
		this.referance = referance;
	}

    
    
 
}
