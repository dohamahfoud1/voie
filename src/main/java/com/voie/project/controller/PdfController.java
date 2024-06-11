package com.voie.project.controller;

import java.io.ByteArrayInputStream;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.voie.project.models.Livraison;
import com.voie.project.models.Commande;
import com.voie.project.models.Devis;
import com.voie.project.models.Facturation;
import com.voie.project.models.FacturesPayees;
import com.voie.project.models.LigneCommande;
import com.voie.project.models.LigneDevis;
import com.voie.project.models.LigneLivraison;
import com.voie.project.repository.CommandeRepository;
import com.voie.project.repository.DevisRepository;
import com.voie.project.repository.FacturationRepository;
import com.voie.project.repository.FacturesPayeesRepository;
import com.voie.project.repository.LigneCommandeRepository;
import com.voie.project.repository.LigneDevisRepository;
import com.voie.project.repository.LigneLivraisonRepository;
import com.voie.project.repository.LivraisonRepository;
import com.voie.project.service.PdfService;

@RestController
public class PdfController {

    @Autowired
    private PdfService pdfService;

    @Autowired
    private DevisRepository devisRepo;
    
    @Autowired
    private LigneDevisRepository ligneDevisRepo;

    @Autowired
    private CommandeRepository commandeRepo;

    @Autowired
    private LigneCommandeRepository ligneCommandeRepo;
    
    @Autowired
    private LigneLivraisonRepository ligneLivraisonRepo;
    
    @Autowired
    private LivraisonRepository livraisonRepo;
    
    @Autowired
    private FacturationRepository facturationRepo;
    @Autowired
    private FacturesPayeesRepository facturesPayeesRepo;
    
    
    @GetMapping("/printDevis/{id}/pdf")
    public ResponseEntity<byte[]> generatePdf(@PathVariable Long id) {
        Devis devis = devisRepo.findById(id).orElse(null);

        if (devis == null) {
            return ResponseEntity.notFound().build();
        }

        List<LigneDevis> lignes = ligneDevisRepo.findByDevis(devis); // Récupération des lignes de devis

        ByteArrayInputStream pdf = pdfService.generatePdf(devis, lignes); // Envoyer les lignes de devis également

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "inline; filename=devis_" + id + ".pdf");

        return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdf.readAllBytes());
    }
    

    @GetMapping("/printCommande/{id}/pdf")   
    public ResponseEntity<byte[]> generatePdfCommande(@PathVariable Long id) {
        Commande commande = commandeRepo.findById(id).orElse(null);

        if (commande == null) {
            return ResponseEntity.notFound().build();
        }

        List<LigneCommande> lignes = ligneCommandeRepo.findByCommande(commande); // Récupération des lignes de devis

        ByteArrayInputStream pdf = pdfService.generatePdfCommande(commande, lignes); // Envoyer les lignes de devis également

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "inline; filename=devis_" + id + ".pdf");

        return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdf.readAllBytes());
    }
    
    
    @GetMapping("/printLivraison/{id}/pdf")   
    public ResponseEntity<byte[]> generatePdfLivraison(@PathVariable Long id) {
        Livraison livrasion = livraisonRepo.findById(id).orElse(null);

        if (livrasion == null) {
            return ResponseEntity.notFound().build();
        }

        List<LigneLivraison> lignes = ligneLivraisonRepo.findByLivraison(livrasion); // Récupération des lignes de devis

        ByteArrayInputStream pdf = pdfService.generatePdfLivraison(livrasion, lignes); // Envoyer les lignes de devis également

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "inline; filename=devis_" + id + ".pdf");

        return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdf.readAllBytes());
    }
    
    @GetMapping("/printFacture/{id}/pdf")   
    public ResponseEntity<byte[]> generatePdfFacturation(@PathVariable Long id) {
        Facturation facturation = facturationRepo.findById(id).orElse(null);

        if (facturation == null) {
            return ResponseEntity.notFound().build();
        }


        ByteArrayInputStream pdf = pdfService.generatePdfFacturation(facturation); // Envoyer les lignes de devis également

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "inline; filename=facture_" + id + ".pdf");

        return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdf.readAllBytes());
    }
    @GetMapping("/printFacturePayees/{id}/pdf")   
    public ResponseEntity<byte[]> generatePdfFacturesPayees(@PathVariable Long id) {
    	FacturesPayees facturesPayees = facturesPayeesRepo.findById(id).orElse(null);

        if (facturesPayees == null) {
            return ResponseEntity.notFound().build();
        }


        ByteArrayInputStream pdf = pdfService.generatePdfFacturesPayees(facturesPayees); // Envoyer les lignes de devis également

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "inline; filename=facturesPayees_" + id + ".pdf");

        return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdf.readAllBytes());
    }
}
