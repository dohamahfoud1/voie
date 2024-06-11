package com.voie.project.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;


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
