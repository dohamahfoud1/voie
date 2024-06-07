package com.voie.project.repository;

import com.voie.project.models.Boutique;
import com.voie.project.models.Devis;
import com.voie.project.models.LigneDevis;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface LigneDevisRepository extends JpaRepository<LigneDevis, Long> {

    List<LigneDevis> findByDevis(Devis devis);

}