package com.voie.project.models;


import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
@Entity
public class Devis {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String referance;
    private Date dateCreation;
    private String baseCalcul;

    @ManyToOne
    private Client client;

    @ManyToOne
    private Boutique boutique;

    @OneToMany(mappedBy = "devis", cascade = CascadeType.ALL)
    private List<LigneDevis> lignesDevis;

    private BigDecimal totalHT;
    private BigDecimal totalTTC;
    private boolean transfereEnCommande;
    
    

    
    
	
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





	public String getBaseCalcul() {
		return baseCalcul;
	}





	public void setBaseCalcul(String baseCalcul) {
		this.baseCalcul = baseCalcul;
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





	public List<LigneDevis> getLignesDevis() {
		return lignesDevis;
	}





	public void setLignesDevis(List<LigneDevis> lignesDevis) {
		this.lignesDevis = lignesDevis;
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


	public boolean isTransfereEnCommande() {
		return transfereEnCommande;
	}





	public void setTransfereEnCommande(boolean transfereEnCommande) {
		this.transfereEnCommande = transfereEnCommande;
	}





	@PrePersist
    public void generateReference() {
        // Générer une référence avec six chiffres en fonction de l'ID généré automatiquement
        String formattedId = String.format("%06d", id);
        this.referance = formattedId;
    }
   
}
