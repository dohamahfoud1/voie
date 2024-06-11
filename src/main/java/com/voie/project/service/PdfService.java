package com.voie.project.service;

import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerHelper;
import com.voie.project.models.Commande;
import com.voie.project.models.Devis;
import com.voie.project.models.Facturation;
import com.voie.project.models.FacturesPayees;
import com.voie.project.models.LigneCommande;
import com.voie.project.models.LigneDevis;
import com.voie.project.models.LigneLivraison;
import com.voie.project.models.Livraison;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Service
public class PdfService {

    @Autowired
    private TemplateEngine templateEngine;

    public ByteArrayInputStream generatePdf(Devis devis, List<LigneDevis> lignes) {
        Context context = new Context();
        context.setVariable("devis", devis);
        context.setVariable("lignes", lignes); // Ajouter les lignes de devis au contexte

        String htmlContent = templateEngine.process("printDevis", context);

        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try {
            Document document = new Document();
            PdfWriter writer = PdfWriter.getInstance(document, out);
            document.open();
            XMLWorkerHelper.getInstance().parseXHtml(writer, document, 
                    new ByteArrayInputStream(htmlContent.getBytes(StandardCharsets.UTF_8)), 
                    StandardCharsets.UTF_8);
            document.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new ByteArrayInputStream(out.toByteArray());
    }
    
    
    
    
    
    public ByteArrayInputStream generatePdfCommande(Commande commande, List<LigneCommande> lignes) {
        Context context = new Context();
        context.setVariable("commande", commande);
        context.setVariable("lignes", lignes); // Ajouter les lignes de devis au contexte

        String htmlContent = templateEngine.process("printCommande", context);

        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try {
            Document document = new Document();
            PdfWriter writer = PdfWriter.getInstance(document, out);
            document.open();
            XMLWorkerHelper.getInstance().parseXHtml(writer, document, 
                    new ByteArrayInputStream(htmlContent.getBytes(StandardCharsets.UTF_8)), 
                    StandardCharsets.UTF_8);
            document.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new ByteArrayInputStream(out.toByteArray());
    }
    
    
    
    public ByteArrayInputStream generatePdfLivraison(Livraison livraison, List<LigneLivraison> lignes) {
        Context context = new Context();
        context.setVariable("livraison", livraison);
        context.setVariable("lignes", lignes); // Ajouter les lignes de devis au contexte

        String htmlContent = templateEngine.process("printLivraison", context);

        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try {
            Document document = new Document();
            PdfWriter writer = PdfWriter.getInstance(document, out);
            document.open();
            XMLWorkerHelper.getInstance().parseXHtml(writer, document, 
                    new ByteArrayInputStream(htmlContent.getBytes(StandardCharsets.UTF_8)), 
                    StandardCharsets.UTF_8);
            document.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new ByteArrayInputStream(out.toByteArray());
    }
    
    
    public ByteArrayInputStream generatePdfFacturation(Facturation facturation) {
        Context context = new Context();
        context.setVariable("facturation", facturation);

        String htmlContent = templateEngine.process("printFacture", context);

        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try {
            Document document = new Document();
            PdfWriter writer = PdfWriter.getInstance(document, out);
            document.open();
            XMLWorkerHelper.getInstance().parseXHtml(writer, document, 
                    new ByteArrayInputStream(htmlContent.getBytes(StandardCharsets.UTF_8)), 
                    StandardCharsets.UTF_8);
            document.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new ByteArrayInputStream(out.toByteArray());
    }
    
    
    public ByteArrayInputStream generatePdfFacturesPayees(FacturesPayees facturesPayees) {
        Context context = new Context();
        context.setVariable("facturesPayees", facturesPayees);

        String htmlContent = templateEngine.process("printFacturePayees", context);

        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try {
            Document document = new Document();
            PdfWriter writer = PdfWriter.getInstance(document, out);
            document.open();
            XMLWorkerHelper.getInstance().parseXHtml(writer, document, 
                    new ByteArrayInputStream(htmlContent.getBytes(StandardCharsets.UTF_8)), 
                    StandardCharsets.UTF_8);
            document.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new ByteArrayInputStream(out.toByteArray());
    }
    
}
