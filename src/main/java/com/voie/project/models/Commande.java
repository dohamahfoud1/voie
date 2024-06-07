package com.voie.project.models;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
public class Commande {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String referance;
    private Date dateCreation;
    private Date dateTransfert;

    @ManyToOne
    private Client client;

    @ManyToOne
    private Boutique boutique;

    @OneToMany(mappedBy = "commande", cascade = CascadeType.ALL)
    private List<LigneCommande> lignesCommande;

    private BigDecimal totalHT;
    private BigDecimal totalTTC;
    private boolean transfereEnLivraison;
    
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getReferance() {
		return referance;
	}
	public void setReferance(String referance) {
		this.referance = referance;
	}
	public Date getDateCreation() {
		return dateCreation;
	}
	public void setDateCreation(Date dateCreation) {
		this.dateCreation = dateCreation;
	}
	public Date getDateTransfert() {
		return dateTransfert;
	}
	public void setDateTransfert(Date dateTransfert) {
		this.dateTransfert = dateTransfert;
	}
	public Client getClient() {
		return client;
	}
	public void setClient(Client client) {
		this.client = client;
	}
	public Boutique getBoutique() {
		return boutique;
	}
	public void setBoutique(Boutique boutique) {
		this.boutique = boutique;
	}
	public List<LigneCommande> getLignesCommande() {
		return lignesCommande;
	}
	public void setLignesCommande(List<LigneCommande> lignesCommande) {
		this.lignesCommande = lignesCommande;
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
	public boolean isTransfereEnLivraison() {
		return transfereEnLivraison;
	}
	public void setTransfereEnLivraison(boolean transfereEnLivraison) {
		this.transfereEnLivraison = transfereEnLivraison;
	}

    
}
