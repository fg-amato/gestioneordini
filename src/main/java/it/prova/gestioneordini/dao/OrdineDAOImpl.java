package it.prova.gestioneordini.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import it.prova.gestioneordini.model.Categoria;
import it.prova.gestioneordini.model.Ordine;

public class OrdineDAOImpl implements OrdineDAO {

	private EntityManager entityManager;

	@Override
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	@Override
	public List<Ordine> list() throws Exception {
		return entityManager.createQuery("from Ordine", Ordine.class).getResultList();
	}

	@Override
	public Ordine get(Long id) throws Exception {
		return entityManager.find(Ordine.class, id);
	}

	@Override
	public void update(Ordine ordineInstance) throws Exception {
		if (ordineInstance == null) {
			throw new Exception("Problema valore in input");
		}
		ordineInstance = entityManager.merge(ordineInstance);
	}

	@Override
	public void insert(Ordine ordineInstance) throws Exception {
		if (ordineInstance == null) {
			throw new Exception("Problema valore in input");
		}

		entityManager.persist(ordineInstance);
	}

	@Override
	public void delete(Ordine ordineInstance) throws Exception {
		if (ordineInstance == null) {
			throw new Exception("Problema valore in input");
		}
		entityManager.remove(entityManager.merge(ordineInstance));
	}

	@Override
	public Ordine findByIdFetchingArticolo(Long id) throws Exception {
		TypedQuery<Ordine> query = entityManager.createQuery(
				"select o FROM Ordine o left join fetch o.articoli a where o.id = :idOrdine", Ordine.class);
		query.setParameter("idOrdine", id);
		List<Ordine> result = query.getResultList();
		return result.size() == 1 ? result.get(0) : null;
	}

	@Override
	public List<Categoria> findAllDistinctCategoriesFromArticoliPresentiInOrdine(Ordine ordineInstance)
			throws Exception {
		TypedQuery<Categoria> query = entityManager.createQuery(
				"select distinct c from Ordine o left join o.articoli a left join a.categorie c where o.id = :idOrdine",
				Categoria.class);
		query.setParameter("idOrdine", ordineInstance.getId());
		return query.getResultList();
	}

	@Override
	public List<Ordine> findAllOrdiniByCategoriaArticoli(Categoria categoriaInput) throws Exception {
		TypedQuery<Ordine> query = entityManager.createQuery(
				"select distinct o from Ordine o left join o.articoli a left join a.categorie c where c.id = :idCategoria",
				Ordine.class);
		query.setParameter("idCategoria", categoriaInput.getId());
		return query.getResultList();
	}

	@Override
	public Ordine findOrdineByCategoriaArticoliWithDataSpedizioneMaggiore(Categoria categoriaInput) throws Exception {
		TypedQuery<Ordine> query = entityManager.createQuery(
				"select distinct o from Ordine o left join o.articoli a left join a.categorie c where c.id = :idCategoria order by o.dataSpedizione desc",
				Ordine.class);
		query.setParameter("idCategoria", categoriaInput.getId());
		return query.getResultList().stream().findFirst().orElse(null);
	}

}
