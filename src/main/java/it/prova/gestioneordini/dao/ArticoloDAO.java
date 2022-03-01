package it.prova.gestioneordini.dao;

import it.prova.gestioneordini.model.Articolo;

public interface ArticoloDAO extends IBaseDAO<Articolo> {

	public Articolo findByIdFetchingCategorie(Long id) throws Exception;
}
