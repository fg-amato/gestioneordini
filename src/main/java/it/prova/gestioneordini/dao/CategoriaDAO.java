package it.prova.gestioneordini.dao;

import it.prova.gestioneordini.model.Categoria;

public interface CategoriaDAO extends IBaseDAO<Categoria> {

	public Categoria findByIdFetchingArticoli(Long id) throws Exception;
}
