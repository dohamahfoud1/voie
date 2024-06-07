package com.voie.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.voie.project.models.Commande;

public interface CommandeRepository  extends JpaRepository<Commande, Long> {

}
