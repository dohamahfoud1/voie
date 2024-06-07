package com.voie.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.voie.project.models.Categorie;

public interface CategorieRepository extends JpaRepository<Categorie, Long> {

}
