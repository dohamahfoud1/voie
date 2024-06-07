package com.voie.project.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.voie.project.models.LigneLivraison;
import com.voie.project.models.Livraison;

public interface LigneLivraisonRepository extends JpaRepository<LigneLivraison, Long> {

	List<LigneLivraison> findByLivraison(Livraison livrasion);

}
