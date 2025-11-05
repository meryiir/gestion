package ma.projet.util;

import ma.projet.classes.Categorie;
import ma.projet.classes.Commande;
import ma.projet.classes.LigneCommandeProduit;
import ma.projet.classes.Produit;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;

import java.util.Properties;

public class HibernateUtil {
	
	private static SessionFactory sessionFactory;
	
	public static SessionFactory getSessionFactory() {
		if (sessionFactory == null) {
			try {
				Configuration configuration = new Configuration();
				
				// Hibernate settings equivalent to hibernate.properties
				Properties settings = new Properties();
				settings.put("hibernate.connection.driver_class", "org.h2.Driver");
				settings.put("hibernate.connection.url", "jdbc:h2:mem:testdb");
				settings.put("hibernate.connection.username", "sa");
				settings.put("hibernate.connection.password", "");
				settings.put(Environment.DIALECT, "org.hibernate.dialect.H2Dialect");
				settings.put(Environment.SHOW_SQL, "true");
				settings.put(Environment.FORMAT_SQL, "true");
				settings.put(Environment.HBM2DDL_AUTO, "update");
				settings.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");
				
				configuration.setProperties(settings);
				
				// Add entity classes
				configuration.addAnnotatedClass(Categorie.class);
				configuration.addAnnotatedClass(Produit.class);
				configuration.addAnnotatedClass(Commande.class);
				configuration.addAnnotatedClass(LigneCommandeProduit.class);
				
				ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
					.applySettings(configuration.getProperties())
					.build();
				
				sessionFactory = configuration.buildSessionFactory(serviceRegistry);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return sessionFactory;
	}
	
	public static void shutdown() {
		if (sessionFactory != null) {
			sessionFactory.close();
		}
	}
}

