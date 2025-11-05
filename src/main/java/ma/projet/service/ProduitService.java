package ma.projet.service;

import ma.projet.classes.Categorie;
import ma.projet.classes.Commande;
import ma.projet.classes.LigneCommandeProduit;
import ma.projet.classes.Produit;
import ma.projet.dao.IDao;
import ma.projet.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ProduitService implements IDao<Produit> {
	
	@Override
	public boolean create(Produit o) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			session.persist(o);
			tx.commit();
			return true;
		} catch (Exception e) {
			if (tx != null) tx.rollback();
			e.printStackTrace();
			return false;
		} finally {
			session.close();
		}
	}
	
	@Override
	public boolean update(Produit o) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			session.merge(o);
			tx.commit();
			return true;
		} catch (Exception e) {
			if (tx != null) tx.rollback();
			e.printStackTrace();
			return false;
		} finally {
			session.close();
		}
	}
	
	@Override
	public boolean delete(Produit o) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			Produit produit = session.get(Produit.class, o.getId());
			if (produit != null) {
				session.remove(produit);
			}
			tx.commit();
			return true;
		} catch (Exception e) {
			if (tx != null) tx.rollback();
			e.printStackTrace();
			return false;
		} finally {
			session.close();
		}
	}
	
	@Override
	public Produit findById(int id) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			return session.get(Produit.class, id);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			session.close();
		}
	}
	
	@Override
	public List<Produit> findAll() {
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			return session.createQuery("FROM Produit", Produit.class).list();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			session.close();
		}
	}
	
	// Méthode pour afficher la liste des produits par catégorie
	public List<Produit> findByCategorie(Categorie categorie) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			Query<Produit> query = session.createQuery(
				"FROM Produit p WHERE p.categorie = :categorie", 
				Produit.class
			);
			query.setParameter("categorie", categorie);
			return query.list();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			session.close();
		}
	}
	
	// Méthode pour afficher les produits commandés entre deux dates
	public List<Produit> getProduitsCommandesEntreDates(Date dateDebut, Date dateFin) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			Query<Produit> query = session.createQuery(
				"SELECT DISTINCT lcp.produit FROM LigneCommandeProduit lcp " +
				"WHERE lcp.commande.date BETWEEN :dateDebut AND :dateFin",
				Produit.class
			);
			query.setParameter("dateDebut", dateDebut);
			query.setParameter("dateFin", dateFin);
			return query.list();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			session.close();
		}
	}
	
	// Méthode pour afficher les produits commandés dans une commande donnée
	public void afficherProduitsCommande(Commande commande) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			Query<LigneCommandeProduit> query = session.createQuery(
				"FROM LigneCommandeProduit lcp WHERE lcp.commande = :commande",
				LigneCommandeProduit.class
			);
			query.setParameter("commande", commande);
			List<LigneCommandeProduit> lignes = query.list();
			
			// Formatage de la date en français
			SimpleDateFormat sdf = new SimpleDateFormat("dd MMMM yyyy", Locale.FRENCH);
			String dateFormatee = sdf.format(commande.getDate());
			
			System.out.println("Commande : " + commande.getId() + "\tDate : " + dateFormatee);
			System.out.println("Liste des produits :");
			System.out.println("Référence\tPrix\t\tQuantité");
			
			for (LigneCommandeProduit ligne : lignes) {
				Produit p = ligne.getProduit();
				System.out.println(p.getReference() + "\t\t" + 
					p.getPrix() + " DH\t" + 
					ligne.getQuantite());
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
	}
	
	// Méthode pour afficher les produits dont le prix est supérieur à 100 DH (requête nommée)
	public List<Produit> getProduitsPrixSuperieurA(float prix) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			Query<Produit> query = session.createNamedQuery("Produit.findByPriceGreaterThan", Produit.class);
			query.setParameter("price", prix);
			return query.list();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			session.close();
		}
	}
}

