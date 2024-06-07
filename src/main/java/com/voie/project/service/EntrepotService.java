package com.voie.project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.voie.project.models.Article;
import com.voie.project.models.Boutique;
import com.voie.project.repository.ArticleRepository;
import com.voie.project.repository.BoutiqueRepository;
import com.voie.project.repository.EntrepotRepository;

@Service
public class EntrepotService {

	
	  @Autowired
	    private EntrepotRepository entrepotRepository;

	    @Autowired
	    private ArticleRepository articleRepository;

	    @Autowired
	    private BoutiqueRepository boutiqueRepository;

	 
}
