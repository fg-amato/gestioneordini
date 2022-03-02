package it.prova.gestioneordini.dao;

import it.prova.gestioneordini.model.Articolo;
import it.prova.gestioneordini.model.Categoria;

public interface ArticoloDAO extends IBaseDAO<Articolo> {

	public Articolo findByIdFetchingCategorie(Long id) throws Exception;

	public Long calculatePrezzoArticoliWithCategoria(Categoria categoriaInput) throws Exception;
}
