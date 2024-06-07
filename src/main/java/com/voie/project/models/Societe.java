package com.voie.project.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

@Entity
@Table(name = "societes")
public class Societe {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    
	@NotBlank(message = "Le nom est obligatoire !!!!!")
    @Column(name = "nom")
    private String nom;
    
	@NotBlank(message = "L'adresse est obligatoire !!!!!")
    @Column(name = "adresse")
    private String adresse;
    
	@NotBlank(message = "Le telephone est obligatoire !!!!!")
	@Pattern(regexp = "0[5-7][0-9]{8}", message = "Le numéro de téléphone invalide")
    private String telephone;
    
	@NotBlank(message = "Ville est obligatoire !!!!!")
	private String ville;
	@NotNull(message = "ICE est obligatoire")
	private Long ice;
    private String Responsable;
	
    @NotBlank(message = "La cnss est obligatoire !!!!!")
    private String cnss;
    @NotNull(message = "Patente est obligatoire")
    private Long patente;
    
    @NotBlank(message = "L'email est obligatoire !!!!!")
	@Pattern(regexp = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$", message = "Adresse e-mail invalide")
	private  String email ;
    
	@NotBlank(message = "Le devise est obligatoire !!!!!")

    private String devise;
    
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
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	public String getVille() {
		return ville;
	}
	public void setVille(String ville) {
		this.ville = ville;
	}
	public Long getIce() {
		return ice;
	}
	public void setIce(Long ice) {
		this.ice = ice;
	}
	public String getResponsable() {
		return Responsable;
	}
	public void setResponsable(String responsable) {
		Responsable = responsable;
	}
	public String getCnss() {
		return cnss;
	}
	public void setCnss(String cnss) {
		this.cnss = cnss;
	}
	public Long getPatente() {
		return patente;
	}
	public void setPatente(Long patente) {
		this.patente = patente;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getDevise() {
		return devise;
	}
	public void setDevise(String devise) {
		this.devise = devise;
	}
	
	
}