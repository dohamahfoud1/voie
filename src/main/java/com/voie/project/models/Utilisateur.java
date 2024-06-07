package com.voie.project.models;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;

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
	
	
}