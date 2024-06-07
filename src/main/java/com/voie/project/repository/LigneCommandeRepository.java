package com.voie.project.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.voie.project.models.Commande;
import com.voie.project.models.LigneCommande;

public interface LigneCommandeRepository  extends JpaRepository<LigneCommande, Long> {

	List<LigneCommande> findByCommande(Commande commande);

}
