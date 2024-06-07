package com.voie.project.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;


import com.voie.project.models.Devise;

public interface DeviseRepository extends JpaRepository<Devise, Long> {
	 List<Devise> findByNomContainingIgnoreCase(String nom);
	    List<Devise> findByTaux(double taux);

}
