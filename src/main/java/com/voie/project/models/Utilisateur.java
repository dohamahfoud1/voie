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
@Table(name="utilisateurs")
public class Utilisateur {
    @Id  	
    @GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	@NotBlank(message = "Le nom est obligatoire !!!!!")
	private String nom;
	@NotBlank(message = "Le mot de passe est obligatoire !!!!!")
	private String mot_de_passe;
	@NotBlank(message = "L'adresse est obligatoire !!!!!")
	private String adresse;
	@NotBlank(message = "Le telephone est obligatoire !!!!!")
	private String telephone;
	
	@ManyToOne
	@JoinColumn(name = "profile_id")
	private Profiles profile;		
	
	private boolean cloturer ;
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
	public boolean isCloturer() {
		return cloturer;
	}
	public void setCloturer(boolean cloturer) {
		this.cloturer = cloturer;
	}
	public Profiles getProfile() {
		return profile;
	}
	public void setProfile(Profiles profile) {
		this.profile = profile;
	}
	public String getMot_de_passe() {
		return mot_de_passe;
	}
	public void setMot_de_passe(String mot_de_passe) {
		this.mot_de_passe = mot_de_passe;
	}
	public String getAdresse() {
		return adresse;
	}
	public void setAdresse(String adresse) {
		this.adresse = adresse;
	}
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	
	
}