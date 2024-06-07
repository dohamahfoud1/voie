package com.voie.project.repository;

import com.voie.project.models.Devis;
import org.springframework.data.jpa.repository.JpaRepository;
public interface DevisRepository extends JpaRepository<Devis, Long> {
}