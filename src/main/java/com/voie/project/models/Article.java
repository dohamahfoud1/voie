package com.voie.project.models;



import java.math.BigDecimal;
import java.util.Base64;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String codeBarre;
    private String designation;

    @ManyToOne
    @JoinColumn(name = "categorie_id")
    private Categorie categorie;

    @ManyToOne
    @JoinColumn(name = "couleur_id")
    private Couleur couleur;

    @ManyToOne
    @JoinColumn(name = "marque_id")
    private Marque marque;

    @ManyToOne
    @JoinColumn(name = "taille_id")
    private Taille taille;

    private BigDecimal prixAchatHT;
    private BigDecimal prixVenteHT;
    private BigDecimal tva1;
    private BigDecimal tva2;
    private BigDecimal prixAchatTTC;
    private BigDecimal prixVenteTTC;
    private Boolean stockable;
    private Integer minimale;
    private Integer maximale;
    private Integer stockTotale;
    private Boolean pesable;
    private String plu;

    @ManyToOne
    @JoinColumn(name = "secteur_id")
    private Secteur secteur;

    @ManyToOne
    @JoinColumn(name = "famille_id")
    private Famille famille;

    @ManyToOne
    @JoinColumn(name = "rayon_id")
    private Rayon rayon;

    private String segment;

    @Lob
    private byte[] barcodeImage;
    
    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "entrepot_id")
    private Entrepot entrepot;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCodeBarre() {
		return codeBarre;
	}

	public void setCodeBarre(String codeBarre) {
		this.codeBarre = codeBarre;
	}

	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	public Categorie getCategorie() {
		return categorie;
	}

	public void setCategorie(Categorie categorie) {
		this.categorie = categorie;
	}

	public Couleur getCouleur() {
		return couleur;
	}

	public void setCouleur(Couleur couleur) {
		this.couleur = couleur;
	}

	public Marque getMarque() {
		return marque;
	}

	public void setMarque(Marque marque) {
		this.marque = marque;
	}

	public Taille getTaille() {
		return taille;
	}

	public void setTaille(Taille taille) {
		this.taille = taille;
	}

	public BigDecimal getPrixAchatHT() {
		return prixAchatHT;
	}

	public void setPrixAchatHT(BigDecimal prixAchatHT) {
		this.prixAchatHT = prixAchatHT;
	}

	public BigDecimal getPrixVenteHT() {
		return prixVenteHT;
	}

	public void setPrixVenteHT(BigDecimal prixVenteHT) {
		this.prixVenteHT = prixVenteHT;
	}

	public BigDecimal getTva1() {
		return tva1;
	}

	public void setTva1(BigDecimal tva1) {
		this.tva1 = tva1;
	}

	public BigDecimal getTva2() {
		return tva2;
	}

	public void setTva2(BigDecimal tva2) {
		this.tva2 = tva2;
	}

	public BigDecimal getPrixAchatTTC() {
		return prixAchatTTC;
	}

	public void setPrixAchatTTC(BigDecimal prixAchatTTC) {
		this.prixAchatTTC = prixAchatTTC;
	}

	public BigDecimal getPrixVenteTTC() {
		return prixVenteTTC;
	}

	public void setPrixVenteTTC(BigDecimal prixVenteTTC) {
		this.prixVenteTTC = prixVenteTTC;
	}

	public Boolean getStockable() {
		return stockable;
	}

	public void setStockable(Boolean stockable) {
		this.stockable = stockable;
	}

	public Integer getMinimale() {
		return minimale;
	}

	public void setMinimale(Integer minimale) {
		this.minimale = minimale;
	}

	public Integer getMaximale() {
		return maximale;
	}

	public void setMaximale(Integer maximale) {
		this.maximale = maximale;
	}

	public Integer getStockTotale() {
		return stockTotale;
	}

	public void setStockTotale(Integer stockTotale) {
		this.stockTotale = stockTotale;
	}

	public Boolean getPesable() {
		return pesable;
	}

	public void setPesable(Boolean pesable) {
		this.pesable = pesable;
	}

	public String getPlu() {
		return plu;
	}

	public void setPlu(String plu) {
		this.plu = plu;
	}

	public Secteur getSecteur() {
		return secteur;
	}

	public void setSecteur(Secteur secteur) {
		this.secteur = secteur;
	}

	public Famille getFamille() {
		return famille;
	}

	public void setFamille(Famille famille) {
		this.famille = famille;
	}

	public Rayon getRayon() {
		return rayon;
	}

	public void setRayon(Rayon rayon) {
		this.rayon = rayon;
	}

	public String getSegment() {
		return segment;
	}

	public void setSegment(String segment) {
		this.segment = segment;
	}

	public byte[] getBarcodeImage() {
		return barcodeImage;
	}
  


	public void setBarcodeImage(byte[] barcodeImage) {
		this.barcodeImage = barcodeImage;
	}

  
	public Entrepot getEntrepot() {
		return entrepot;
	}

	public void setEntrepot(Entrepot entrepot) {
		this.entrepot = entrepot;
	}

	public String getBarcodeImageBase64() {
		  if (barcodeImage == null) {
	            return ""; // Retourne une chaîne vide si barcodeImage est null
	        }
        return Base64.getEncoder().encodeToString(this.barcodeImage);
    }
	
	

	
}
