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
import javax.validation.constraints.NotNull;

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