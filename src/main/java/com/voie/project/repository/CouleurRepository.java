package com.voie.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.voie.project.models.Couleur;

public interface CouleurRepository  extends JpaRepository<Couleur, Long> {

}
