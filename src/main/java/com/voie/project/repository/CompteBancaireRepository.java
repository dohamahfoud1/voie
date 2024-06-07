package com.voie.project.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.voie.project.models.CompteBancaire;

public interface CompteBancaireRepository extends JpaRepository<CompteBancaire, Long> {

	List<CompteBancaire> findAll();

}
