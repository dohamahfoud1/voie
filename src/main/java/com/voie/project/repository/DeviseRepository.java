package com.voie.project.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;


import com.voie.project.models.Devise;
import com.voie.project.models.Societe;

public interface DeviseRepository extends JpaRepository<Devise, Long> {
	 List<Devise> findByNomContainingIgnoreCase(String nom);
	    List<Devise> findByTaux(double taux);
		String findByNom(String devise);

}
