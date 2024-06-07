package com.voie.project.models;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;


@Entity
@Table(name = "exercices_mensuel")
public class ExerciceMensuel {

	
	 @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;

	    private LocalDate mois;
	    private boolean cloture;
	    private boolean archive;

	  

	    public Long getId() {
	        return id;
	    }

	    public void setId(Long id) {
	        this.id = id;
	    }

	    public LocalDate getMois() {
	        return mois;
	    }

	    public void setMois(LocalDate mois) {
	        this.mois = mois;
	    }

	    public boolean isCloture() {
	        return cloture;
	    }

	    public void setCloture(boolean cloture) {
	        this.cloture = cloture;
	    }

	    public boolean isArchive() {
	        return archive;
	    }

	    public void setArchive(boolean archive) {
	        this.archive = archive;
	    }
	}

