package com.voie.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.voie.project.models.Utilisateur;

public interface UtilisateurRepository extends JpaRepository<Utilisateur, Long> {

	@Query("SELECT u FROM Utilisateur u WHERE u.nom = ?1")
	public Utilisateur findByNom(String nom); 
}
