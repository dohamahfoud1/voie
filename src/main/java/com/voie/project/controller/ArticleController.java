package com.voie.project.controller;

import com.voie.project.models.Article;
import com.voie.project.models.Boutique;
import com.voie.project.models.Entrepot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Map;

import com.voie.project.repository.ArticleRepository;
import com.voie.project.repository.BoutiqueRepository;
import com.voie.project.repository.CategorieRepository;
import com.voie.project.repository.CouleurRepository;
import com.voie.project.repository.EntrepotRepository;
import com.voie.project.repository.RayonRepository;
import com.voie.project.repository.SecteurRepository;
import com.voie.project.repository.TailleRepository;
import com.voie.project.repository.TvaRepository;
import com.voie.project.repository.familleRepository;
import com.voie.project.repository.marqueRepository;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import org.springframework.validation.BindingResult;

import org.springframework.web.bind.annotation.PathVariable;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;



import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.UUID;
@Controller
@RequestMapping("/")
public class ArticleController {

	@Autowired
	private ArticleRepository articleRepo;
	@Autowired
	private TailleRepository tailleRepo;
	@Autowired
	private CouleurRepository couleurRepo;
	@Autowired
	private CategorieRepository categorieRepo;
	@Autowired
	private marqueRepository marqueRepo;
	@Autowired
	private SecteurRepository secteurRepo;
	@Autowired
	private RayonRepository rayonRepo;
	@Autowired
	private familleRepository familleRepo;
	@Autowired
	private TvaRepository tvaRepo;
	@Autowired
	private BoutiqueRepository boutiqueRepository;
	@Autowired
	private EntrepotRepository entrepotRepository;
	 @GetMapping("/article")
	    public String showArticleForm(Model model) {
	        model.addAttribute("article", new Article());

	        model.addAttribute("categories", categorieRepo.findAll());
	        model.addAttribute("couleurs", couleurRepo.findAll());
	        model.addAttribute("marques", marqueRepo.findAll());
	        model.addAttribute("tailles", tailleRepo.findAll());
	        model.addAttribute("secteurs", secteurRepo.findAll());
	        model.addAttribute("rayons", rayonRepo.findAll());
	        model.addAttribute("familles", familleRepo.findAll());
	        model.addAttribute("tvas", tvaRepo.findAll());
	        List<Boutique> boutiques = boutiqueRepository.findAll();
	        model.addAttribute("boutiques", boutiques);
	        
	        return "newArticle";
	    }

	 @PostMapping("/generateBarcode")
	    public String generateBarcode(@RequestParam("prefix") String prefix, Model model) {
	        Article article = new Article();
	        String codeBarre = prefix + UUID.randomUUID().toString().substring(0, 9);
	        article.setCodeBarre(codeBarre);

	        try {
	            QRCodeWriter barcodeWriter = new QRCodeWriter();
	            BitMatrix bitMatrix = barcodeWriter.encode(codeBarre, BarcodeFormat.QR_CODE, 200, 200);

	            ByteArrayOutputStream pngOutputStream = new ByteArrayOutputStream();
	            MatrixToImageWriter.writeToStream(bitMatrix, "PNG", pngOutputStream);
	            byte[] pngData = pngOutputStream.toByteArray();
	            article.setBarcodeImage(pngData);
	        } catch (WriterException | IOException e) {
	            e.printStackTrace();
	        }

	        model.addAttribute("article", article);
	        
	        model.addAttribute("categories", categorieRepo.findAll());
	        model.addAttribute("couleurs", couleurRepo.findAll());
	        model.addAttribute("marques", marqueRepo.findAll());
	        model.addAttribute("tailles", tailleRepo.findAll());
	        model.addAttribute("secteurs", secteurRepo.findAll());
	        model.addAttribute("rayons", rayonRepo.findAll());
	        model.addAttribute("familles", familleRepo.findAll());
	        model.addAttribute("tvas", tvaRepo.findAll());
	        List<Boutique> boutiques = boutiqueRepository.findAll();
	        model.addAttribute("boutiques", boutiques);
	        return "newArticle";
	    }

	    
	    @PostMapping("/article")
	    public String saveArticle(@ModelAttribute Article article, Model model, BindingResult result,HttpServletRequest request) {
	        if (result.hasErrors()) {
	            model.addAttribute("categories", categorieRepo.findAll());
	            model.addAttribute("couleurs", couleurRepo.findAll());
	            model.addAttribute("marques", marqueRepo.findAll());
	            model.addAttribute("tailles", tailleRepo.findAll());
	            model.addAttribute("secteurs", secteurRepo.findAll());
	            model.addAttribute("rayons", rayonRepo.findAll());
	            model.addAttribute("familles", familleRepo.findAll());
	            model.addAttribute("tvas", tvaRepo.findAll());
	            return "newArticle";
	        }
	     
	        articleRepo.save(article);

	        List<Boutique> boutiques = boutiqueRepository.findAll();
	        for (Boutique boutique : boutiques) {
	            String stockParam = "stock_" + boutique.getId();
	            // Use request.getParameter(stockParam) instead of model.getAttribute(stockParam)
	            int stock = Integer.parseInt(request.getParameter(stockParam));

	            Entrepot entrepot = new Entrepot();
	            entrepot.setArticle(article);
	            entrepot.setBoutique(boutique);
	            entrepot.setStock(stock);
	            entrepotRepository.save(entrepot);
	        }

	        return "redirect:/article";
	    }
	
	
		 @GetMapping("/tarifsArticle")
		    public String tarifs() {
		        return "tarifsArticle";
		    }
	
		 @GetMapping("/stockArticle")
		    public String stock() {
		        return "stockArticle";
		    }
		 @GetMapping("/pesageArticle")
		    public String pesage() {
		        return "pesageArticle";
		    }
	
		 @GetMapping("/classementArticle")
		    public String classement(Model model) {
		        model.addAttribute("secteurs", secteurRepo.findAll());
		        model.addAttribute("rayons", rayonRepo.findAll());
		        model.addAttribute("familles", familleRepo.findAll());
		 
		        return "classementArticle";
		    }
		 
		 @GetMapping("/listeArticles")
		    public String afficherListeArticles(Model model) {
		        List<Article> articles = articleRepo.findAll();
		        model.addAttribute("articles", articles);
		        return "listeArticles";
		    }

		 @GetMapping("/listeArticles/barcode/{codeBarre}")
		 public ResponseEntity<byte[]> getBarcodeImage(@PathVariable String codeBarre) {
		     try {
		         QRCodeWriter barcodeWriter = new QRCodeWriter();
		         BitMatrix bitMatrix = barcodeWriter.encode(codeBarre, BarcodeFormat.QR_CODE, 200, 200);

		         ByteArrayOutputStream pngOutputStream = new ByteArrayOutputStream();
		         MatrixToImageWriter.writeToStream(bitMatrix, "PNG", pngOutputStream);
		         byte[] pngData = pngOutputStream.toByteArray();

		         return ResponseEntity.ok().contentType(MediaType.IMAGE_PNG).body(pngData);
		     } catch (WriterException | IOException e) {
		         e.printStackTrace(); 
		         return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		     }
		 }
	
}
