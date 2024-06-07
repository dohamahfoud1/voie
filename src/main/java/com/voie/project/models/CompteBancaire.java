package com.voie.project.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name="comptes_bancaire")
public class CompteBancaire {

	  @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;
	    
	    @NotBlank(message = "Le nom est obligatoire !!!!!")
	    @Column(name = "nom")
	    private String nom;

	    @Column(name = "adresse")
	    private String adresse;
	 
	    @NotBlank(message = "Le compte est obligatoire !!!!!")
	    private String compte;
	    
	    @NotBlank(message = "Agence est obligatoire !!!!!")
        private String agence;
	    
	    private String ville;
	    private String telephone;

		@ManyToOne
		@JoinColumn(name = "banque_id")
		private Banque banque;

		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

		public String getNom() {
			return nom;
		}

		public void setNom(String nom) {
			this.nom = nom;
		}

		public String getAdresse() {
			return adresse;
		}

		public void setAdresse(String adresse) {
			this.adresse = adresse;
		}

		public String getCompte() {
			return compte;
		}

		public void setCompte(String compte) {
			this.compte = compte;
		}

		public String getAgence() {
			return agence;
		}

		public void setAgence(String agence) {
			this.agence = agence;
		}

		public String getVille() {
			return ville;
		}

		public void setVille(String ville) {
			this.ville = ville;
		}

		public String getTelephone() {
			return telephone;
		}

		public void setTelephone(String telephone) {
			this.telephone = telephone;
		}

		public Banque getBanque() {
			return banque;
		}

		public void setBanque(Banque banque) {
			this.banque = banque;
		}	

		
		
		
}
