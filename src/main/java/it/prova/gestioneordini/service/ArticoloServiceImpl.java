package it.prova.gestioneordini.service;

import java.util.List;

import javax.persistence.EntityManager;

import it.prova.gestioneordini.dao.ArticoloDAO;
import it.prova.gestioneordini.dao.EntityManagerUtil;
import it.prova.gestioneordini.exceptions.ArticoloConCategorieException;
import it.prova.gestioneordini.exceptions.ArticoloNonAssociatoAOrdineException;
import it.prova.gestioneordini.model.Articolo;

public class ArticoloServiceImpl implements ArticoloService {
	private ArticoloDAO articoloDAO;

	@Override
	public List<Articolo> listAll() throws Exception {
		EntityManager entityManager = EntityManagerUtil.getEntityManager();

		try {
			// uso l'injection per il dao
			articoloDAO.setEntityManager(entityManager);

			// eseguo quello che realmente devo fare
			return articoloDAO.list();

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			EntityManagerUtil.closeEntityManager(entityManager);
		}
	}

	@Override
	public Articolo caricaSingoloElemento(Long id) throws Exception {
		// questo è come una connection
		EntityManager entityManager = EntityManagerUtil.getEntityManager();

		try {
			// uso l'injection per il dao
			articoloDAO.setEntityManager(entityManager);

			// eseguo quello che realmente devo fare
			return articoloDAO.get(id);

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			EntityManagerUtil.closeEntityManager(entityManager);
		}
	}

	@Override
	public void aggiorna(Articolo articoloInstance) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void inserisciNuovo(Articolo articoloInstance) throws Exception {
		// questo è come una connection
		EntityManager entityManager = EntityManagerUtil.getEntityManager();

		try {
			// questo è come il MyConnection.getConnection()
			entityManager.getTransaction().begin();

			// uso l'injection per il dao
			articoloDAO.setEntityManager(entityManager);

			if (articoloInstance.getOrdine() == null || articoloInstance.getOrdine().getId() == null) {
				throw new ArticoloNonAssociatoAOrdineException(
						"L'articolo che stai cercando di inserire non è associato ad un ordine presente nel DB");
			}

			// eseguo quello che realmente devo fare
			articoloDAO.insert(articoloInstance);

			entityManager.getTransaction().commit();
		} catch (Exception e) {
			entityManager.getTransaction().rollback();
			e.printStackTrace();
			throw e;
		} finally {
			EntityManagerUtil.closeEntityManager(entityManager);
		}
	}

	@Override
	public void rimuovi(Articolo articoloInstance) throws Exception {
		EntityManager entityManager = EntityManagerUtil.getEntityManager();
		try {

			entityManager.getTransaction().begin();

			// inietto la connection nel dao
			articoloDAO.setEntityManager(entityManager);

			Articolo articoloDaRimuovereConCategorie = articoloDAO.findByIdFetchingCategorie(articoloInstance.getId());

			if (articoloDaRimuovereConCategorie.getCategorie().size() > 0) {
				throw new ArticoloConCategorieException(
						"Stai provando ad eliminare un articolo che ha ancora categorie associate");
			}
			// eseguo quello che realmente devo fare
			articoloDAO.delete(articoloInstance);

			entityManager.getTransaction().commit();

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	@Override
	public void setArticoloDAO(ArticoloDAO articoloDAO) {
		this.articoloDAO = articoloDAO;
	}

	@Override
	public Articolo caricaArticoloConCategorie(Long id) throws Exception {
		// questo è come una connection
		EntityManager entityManager = EntityManagerUtil.getEntityManager();

		try {
			// uso l'injection per il dao
			articoloDAO.setEntityManager(entityManager);

			// eseguo quello che realmente devo fare
			return articoloDAO.findByIdFetchingCategorie(id);

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			EntityManagerUtil.closeEntityManager(entityManager);
		}
	}

}
