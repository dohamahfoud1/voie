package com.voie.project.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


import com.voie.project.models.Article;
import com.voie.project.models.Boutique;
import com.voie.project.models.Entrepot;

public interface EntrepotRepository extends JpaRepository<Entrepot, Long> {
	
	 
    List<Entrepot> findByBoutiqueId(Long boutiqueId);

	
    List<Entrepot> findByArticleAndBoutique(Article article, Boutique boutique);


	Entrepot findByArticleIdAndBoutiqueId(Long articleId, Long id);
	

   


}
