package it.prova.gestioneordini.dao;

import java.util.List;

import it.prova.gestioneordini.model.Categoria;
import it.prova.gestioneordini.model.Ordine;

public interface OrdineDAO extends IBaseDAO<Ordine> {

	Ordine findByIdFetchingArticolo(Long id) throws Exception;

	List<Categoria> findAllDistinctCategoriesFromArticoliPresentiInOrdine(Ordine ordineInstance) throws Exception;

	List<Ordine> findAllOrdiniByCategoriaArticoli(Categoria categoriaInput) throws Exception;
}
