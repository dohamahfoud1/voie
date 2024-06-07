package com.voie.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.voie.project.models.Utilisateur;

public interface UtilisateurRepository extends JpaRepository<Utilisateur, Long> {

}
