package com.voie.project.models;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;


@Entity
public class Livraison {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Date dateCreation;
    private String referance;
    private boolean transfereEnFacturation;

    @ManyToOne
    private Client client;

    @ManyToOne
    private Boutique boutique;
    
    private BigDecimal totalHT;
    private BigDecimal totalTTC;

    @OneToMany(mappedBy = "livraison", cascade = CascadeType.ALL)
    private List<LigneLivraison> lignesLivraison;

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

	public Boutique getBoutique() {
		return boutique;
	}

	public void setBoutique(Boutique boutique) {
		this.boutique = boutique;
	}

	public List<LigneLivraison> getLignesLivraison() {
		return lignesLivraison;
	}

	public void setLignesLivraison(List<LigneLivraison> lignesLivraison) {
		this.lignesLivraison = lignesLivraison;
	}

	public String getReferance() {
		return referance;
	}

	public void setReferance(String referance) {
		this.referance = referance;
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

	public boolean isTransfereEnFacturation() {
		return transfereEnFacturation;
	}

	public void setTransfereEnFacturation(boolean transfereEnFacturation) {
		this.transfereEnFacturation = transfereEnFacturation;
	}

  
    
    
    
}
