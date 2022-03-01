package it.prova.gestioneordini.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import it.prova.gestioneordini.model.Categoria;

public class CategoriaDAOImpl implements CategoriaDAO {

	private EntityManager entityManager;

	@Override
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	@Override
	public List<Categoria> list() throws Exception {
		return entityManager.createQuery("from Categoria", Categoria.class).getResultList();
	}

	@Override
	public Categoria get(Long id) throws Exception {
		return entityManager.find(Categoria.class, id);
	}

	@Override
	public void update(Categoria categoriaInstance) throws Exception {
		if (categoriaInstance == null) {
			throw new Exception("Problema valore in input");
		}
		categoriaInstance = entityManager.merge(categoriaInstance);
	}

	@Override
	public void insert(Categoria categoriaInstance) throws Exception {
		if (categoriaInstance == null) {
			throw new Exception("Problema valore in input");
		}

		entityManager.persist(categoriaInstance);
	}

	@Override
	public void delete(Categoria categoriaInstance) throws Exception {
		if (categoriaInstance == null) {
			throw new Exception("Problema valore in input");
		}
		entityManager.remove(entityManager.merge(categoriaInstance));
	}

	@Override
	public Categoria findByIdFetchingArticoli(Long id) throws Exception {
		TypedQuery<Categoria> query = entityManager.createQuery(
				"select c FROM Categoria c left join fetch c.articoli a where c.id = :idCategoria", Categoria.class);
		query.setParameter("idCategoria", id);
		return query.getResultList().stream().findFirst().orElse(null);
	}

}
