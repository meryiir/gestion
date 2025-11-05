package ma.projet.service;

import ma.projet.classes.LigneCommandeProduit;
import ma.projet.dao.IDao;
import ma.projet.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class LigneCommandeService implements IDao<LigneCommandeProduit> {
	
	@Override
	public boolean create(LigneCommandeProduit o) {
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
	public boolean update(LigneCommandeProduit o) {
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
	public boolean delete(LigneCommandeProduit o) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			LigneCommandeProduit ligne = session.get(LigneCommandeProduit.class, o.getId());
			if (ligne != null) {
				session.remove(ligne);
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
	public LigneCommandeProduit findById(int id) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			return session.get(LigneCommandeProduit.class, id);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			session.close();
		}
	}
	
	@Override
	public List<LigneCommandeProduit> findAll() {
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			return session.createQuery("FROM LigneCommandeProduit", LigneCommandeProduit.class).list();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			session.close();
		}
	}
}

