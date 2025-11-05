package ma.projet.test;

import ma.projet.classes.*;
import ma.projet.service.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class TestGestionStock {
	
	public static void main(String[] args) {
		System.out.println("=== Test Application de Gestion de Stock ===\n");
		
		// Initialisation des services
		CategorieService categorieService = new CategorieService();
		ProduitService produitService = new ProduitService();
		CommandeService commandeService = new CommandeService();
		LigneCommandeService ligneCommandeService = new LigneCommandeService();
		
		// Test 1: Création de catégories
		System.out.println("1. Test création de catégories:");
		Categorie cat1 = new Categorie("CAT001", "Ordinateurs");
		Categorie cat2 = new Categorie("CAT002", "Périphériques");
		categorieService.create(cat1);
		categorieService.create(cat2);
		System.out.println("Catégories créées avec succès!\n");
		
		// Test 2: Création de produits
		System.out.println("2. Test création de produits:");
		Produit p1 = new Produit("ES12", 120, cat1);
		Produit p2 = new Produit("ZR85", 100, cat1);
		Produit p3 = new Produit("EE85", 200, cat2);
		Produit p4 = new Produit("XX99", 150, cat2);
		Produit p5 = new Produit("YY50", 80, cat1);
		
		produitService.create(p1);
		produitService.create(p2);
		produitService.create(p3);
		produitService.create(p4);
		produitService.create(p5);
		System.out.println("Produits créés avec succès!\n");
		
		// Test 3: Création de commandes
		System.out.println("3. Test création de commandes:");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date1 = null;
		Date date2 = null;
		try {
			date1 = sdf.parse("2013-03-14");
			date2 = sdf.parse("2013-03-20");
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		Commande cmd1 = new Commande(date1);
		Commande cmd2 = new Commande(date2);
		commandeService.create(cmd1);
		commandeService.create(cmd2);
		System.out.println("Commandes créées avec succès!\n");
		
		// Test 4: Création de lignes de commande
		System.out.println("4. Test création de lignes de commande:");
		LigneCommandeProduit lcp1 = new LigneCommandeProduit(7, p1, cmd1);
		LigneCommandeProduit lcp2 = new LigneCommandeProduit(14, p2, cmd1);
		LigneCommandeProduit lcp3 = new LigneCommandeProduit(5, p3, cmd1);
		LigneCommandeProduit lcp4 = new LigneCommandeProduit(3, p4, cmd2);
		
		ligneCommandeService.create(lcp1);
		ligneCommandeService.create(lcp2);
		ligneCommandeService.create(lcp3);
		ligneCommandeService.create(lcp4);
		System.out.println("Lignes de commande créées avec succès!\n");
		
		// Test 5: Affichage des produits par catégorie
		System.out.println("5. Test - Produits par catégorie (Ordinateurs):");
		List<Produit> produitsCat1 = produitService.findByCategorie(cat1);
		if (produitsCat1 != null) {
			for (Produit p : produitsCat1) {
				System.out.println("- " + p.getReference() + " : " + p.getPrix() + " DH");
			}
		}
		System.out.println();
		
		// Test 6: Produits commandés entre deux dates
		System.out.println("6. Test - Produits commandés entre 2013-03-10 et 2013-03-15:");
		try {
			Date dateDebut = sdf.parse("2013-03-10");
			Date dateFin = sdf.parse("2013-03-15");
			List<Produit> produitsDates = produitService.getProduitsCommandesEntreDates(dateDebut, dateFin);
			if (produitsDates != null) {
				for (Produit p : produitsDates) {
					System.out.println("- " + p.getReference() + " : " + p.getPrix() + " DH");
				}
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		System.out.println();
		
		// Test 7: Affichage des produits d'une commande (format attendu)
		System.out.println("7. Test - Produits d'une commande donnée:");
		produitService.afficherProduitsCommande(cmd1);
		System.out.println();
		
		// Test 8: Produits avec prix > 100 DH (requête nommée)
		System.out.println("8. Test - Produits avec prix > 100 DH (requête nommée):");
		List<Produit> produitsChers = produitService.getProduitsPrixSuperieurA(100);
		if (produitsChers != null) {
			for (Produit p : produitsChers) {
				System.out.println("- " + p.getReference() + " : " + p.getPrix() + " DH");
			}
		}
		System.out.println();
		
		// Test 9: Affichage de toutes les catégories
		System.out.println("9. Test - Liste de toutes les catégories:");
		List<Categorie> categories = categorieService.findAll();
		if (categories != null) {
			for (Categorie c : categories) {
				System.out.println("- " + c.getCode() + " : " + c.getLibelle());
			}
		}
		System.out.println();
		
		// Test 10: Affichage de tous les produits
		System.out.println("10. Test - Liste de tous les produits:");
		List<Produit> produits = produitService.findAll();
		if (produits != null) {
			for (Produit p : produits) {
				System.out.println("- " + p.getReference() + " : " + p.getPrix() + " DH (" + 
					p.getCategorie().getLibelle() + ")");
			}
		}
		System.out.println();
		
		System.out.println("=== Tests terminés avec succès! ===");
	}
}

