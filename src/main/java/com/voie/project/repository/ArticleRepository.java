package com.voie.project.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;


import com.voie.project.models.Article;
import com.voie.project.models.Boutique;

public interface ArticleRepository extends JpaRepository<Article, Long> {

    List<Article> findByEntrepot_Boutique_Id(Long boutiqueId);
}
