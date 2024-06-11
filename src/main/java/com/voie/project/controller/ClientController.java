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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.voie.project.models.Client;
import com.voie.project.models.Utilisateur;
import com.voie.project.repository.ClientRepository;
import com.voie.project.repository.ModePaiementRepository;


@Controller
@RequestMapping("/")
public class ClientController {

	@Autowired
	private ModePaiementRepository modePaiementRepo;

	@Autowired
	private ClientRepository clientRepo;
	
	
	@GetMapping("/client")
	    public String showClientForm(Model model) {
	        model.addAttribute("client", new Client());

	        model.addAttribute("modePaiements", modePaiementRepo.findAll());
	    	        
	        return "AjouterClient";
	    }
	 
	 
	 
	 @PostMapping("/client")
	    public String saveClient(@Valid @ModelAttribute("client") Client client, BindingResult result,Model model) {
	        if (result.hasErrors()) {
		        model.addAttribute("modePaiements", modePaiementRepo.findAll());
	           
	            return "AjouterClient";
	        }
	     
	        clientRepo.save(client);

	        return "redirect:/client";
	    }
	
	  @GetMapping("/listClients")
	  public String afficherListeClients(Model model) {
	        List<Client> clients = clientRepo.findAll();
	        model.addAttribute("clients", clients);
	        return "listClients";
	    }
	
	  
	  
	   @GetMapping("/deleteClient/{id}")
	    public String deleteClient(@PathVariable Long id, RedirectAttributes redirectAttributes) {
		   clientRepo.deleteById(id);
	        redirectAttributes.addFlashAttribute("success", "Client supprimé avec succès.");
	        return "redirect:/listClients";
	    }
	  

	
}
