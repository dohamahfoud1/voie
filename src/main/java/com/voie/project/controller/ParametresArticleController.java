package com.voie.project.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.voie.project.models.Categorie;
import com.voie.project.models.Couleur;
import com.voie.project.models.Famille;
import com.voie.project.models.Marque;
import com.voie.project.models.Rayon;
import com.voie.project.models.Secteur;
import com.voie.project.models.Taille;
import com.voie.project.models.Tva;
import com.voie.project.repository.CategorieRepository;
import com.voie.project.repository.CouleurRepository;
import com.voie.project.repository.RayonRepository;
import com.voie.project.repository.SecteurRepository;
import com.voie.project.repository.TailleRepository;
import com.voie.project.repository.TvaRepository;
import com.voie.project.repository.familleRepository;
import com.voie.project.repository.marqueRepository;


@Controller
@RequestMapping("/")
public class ParametresArticleController {

	
	
	
	@Autowired 
	private TvaRepository tvaRepo;
	@GetMapping("/tva")
	public String mode(Model model) {
		model.addAttribute("tva", new Tva());
		List<Tva> tva = tvaRepo.findAll();
		model.addAttribute("tva", tva);
		return "/tva";
	}
	
	
	@PostMapping("/tva")
	public String processTva(@Valid Tva tva) {
	   
	    tvaRepo.save(tva);
        return "redirect:/tva"; 
    }	
	
	
	   @Autowired 
	   private SecteurRepository secteurRepo;

	    @GetMapping("/secteur")
	    public String secteur(Model model) {
	        model.addAttribute("newSecteur", new Secteur());  
	        List<Secteur> secteurs = secteurRepo.findAll();  
	        model.addAttribute("secteurs", secteurs);        
	        return "secteurs"; 
	    }
	    
	    @PostMapping("/secteur")
	    public String processSecteur(@Valid @ModelAttribute("newSecteur") Secteur secteur, BindingResult result, Model model) {
	        if (result.hasErrors()) {
	            List<Secteur> secteurs = secteurRepo.findAll();
	            model.addAttribute("secteurs", secteurs);
	            return "secteurs"; // Retourner au template avec les erreurs
	        }
	        secteurRepo.save(secteur);
	        return "redirect:/secteur"; 
	    }
	    
	    
	    
	    @Autowired 
	    private RayonRepository rayonRepo;

	    @GetMapping("/rayon")
	    public String rayon(Model model) {
	        model.addAttribute("newRayon", new Rayon());  
	        List<Rayon> rayons = rayonRepo.findAll();
	        List<Secteur> secteurs = secteurRepo.findAll();
	        model.addAttribute("secteurs", secteurs);        
	        model.addAttribute("rayons", rayons);   
	        return "rayon";
	    }
	    
	 
	       @PostMapping("/rayon")
	    public String processSeRayon(@Valid @ModelAttribute("newRayon") Rayon rayon, BindingResult result, Model model) {
	    	 if (result.hasErrors()) {
		            List<Rayon> rayons = rayonRepo.findAll();
		            model.addAttribute("rayons", rayons);
		            return "rayon"; 
		        }
	        // Convert the secteur ID to a Secteur object
	        Secteur secteur = secteurRepo.findById(rayon.getSecteur().getId()).orElse(null);
	        rayon.setSecteur(secteur);
	        
	        rayonRepo.save(rayon);
	        return "redirect:/rayon"; 
	    }
	       
	       
	      
	       
	       
	   	
		   @Autowired 
		    private familleRepository familleRepo;

		    @GetMapping("/famille")
		    public String famille(Model model) {
		        model.addAttribute("newFamille", new Famille()); 
		        List<Famille> familles = familleRepo.findAll();  
		        model.addAttribute("familles", familles);       
		        return "familles"; 
		    }
		    
		    @PostMapping("/famille")
		    public String processFamille(@Valid @ModelAttribute("newFamille") Famille famille, BindingResult result, Model model) {
		        if (result.hasErrors()) {
		            List<Famille> familles = familleRepo.findAll();
		            model.addAttribute("familles", familles);
		            return "familles"; 
		        }
		        familleRepo.save(famille);
		        return "redirect:/famille"; 
		    }
		    
		    
		    
		    
		    
			   @Autowired 
			    private marqueRepository marqueRepo;

			    @GetMapping("/marque")
			    public String marque(Model model) {
			        model.addAttribute("newMarque", new Marque());  
			        List<Marque> marques = marqueRepo.findAll();  
			        model.addAttribute("marques", marques);        
			        return "marque"; 
			    }
			    
			    @PostMapping("/marque")
			    public String processMarque(@Valid @ModelAttribute("newMarque") Marque marque, BindingResult result, Model model) {
			        if (result.hasErrors()) {
			            List<Marque> marques = marqueRepo.findAll();
			            model.addAttribute("marques", marques);
			            return "marques"; 
			        }
			        marqueRepo.save(marque);
			        return "redirect:/marque"; 
			    }
		    

			    
			    
			    
			    
				   @Autowired 
				    private CategorieRepository categorieRepo;

				    @GetMapping("/categorie")
				    public String categorie(Model model) {
				        model.addAttribute("newCategorie", new Categorie());  
				        List<Categorie> categories = categorieRepo.findAll();  
				        model.addAttribute("categories", categories);        
				        return "categorie"; 
				    }
				    
				    @PostMapping("/categorie")
				    public String processCategorie(@Valid @ModelAttribute("newCategorie") Categorie categorie, BindingResult result, Model model) {
				        if (result.hasErrors()) {
				            List<Categorie> categories = categorieRepo.findAll();
				            model.addAttribute("categories", categories);
				            return "categorie"; 
				        }
				        categorieRepo.save(categorie);
				        return "redirect:/categorie"; 
				    }
			    
			    
			    
			
				    
				    
					   @Autowired 
					    private TailleRepository tailleRepo;

					    @GetMapping("/taille")
					    public String taille(Model model) {
					        model.addAttribute("newTaille", new Taille());  
					        List<Taille> tailles = tailleRepo.findAll();  
					        model.addAttribute("tailles", tailles);        
					        return "taille"; 
					    }
					    
					    @PostMapping("/taille")
					    public String processTaille(@Valid @ModelAttribute("newTaille") Taille taille, BindingResult result, Model model) {
					        if (result.hasErrors()) {
					            List<Taille> tailles = tailleRepo.findAll();
					            model.addAttribute("tailles", tailles);
					            return "taille"; 
					        }
					        tailleRepo.save(taille);
					        return "redirect:/taille"; 
					    }
				    
					    
					    
					    
					    
					    
						   @Autowired 
						    private CouleurRepository couleurRepo;

						    @GetMapping("/couleur")
						    public String couleur(Model model) {
						        model.addAttribute("newCouleur", new Couleur());  
						        List<Couleur> couleurs = couleurRepo.findAll();  
						        model.addAttribute("couleurs", couleurs);        
						        return "couleur"; 
						    }
						    
						    @PostMapping("/couleur")
						    public String processCouleur(@Valid @ModelAttribute("newCouleur") Couleur couleur, BindingResult result, Model model) {
						        if (result.hasErrors()) {
						            List<Couleur> couleurs = couleurRepo.findAll();
						            model.addAttribute("couleurs", couleurs);
						            return "couleur"; 
						        }
						        couleurRepo.save(couleur);
						        return "redirect:/couleur"; 
						    }
					    
			    
	}
	
	
