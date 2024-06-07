package com.voie.project.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "devises")
public class Devise {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    
	@NotBlank(message = "Devise est obligatoire !!!!!")
    @Column(name = "nom")
    private String nom;
    
	@NotNull(message = "Le taux est obligatoire")
    @Column(name = "taux")
    private Long taux;

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

	public Long getTaux() {
		return taux;
	}

	public void setTaux(Long taux) {
		this.taux = taux;
	}
    
    
}