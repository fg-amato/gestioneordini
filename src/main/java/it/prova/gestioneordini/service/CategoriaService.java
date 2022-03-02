package it.prova.gestioneordini.service;

import java.util.List;

import it.prova.gestioneordini.dao.ArticoloDAO;
import it.prova.gestioneordini.dao.CategoriaDAO;
import it.prova.gestioneordini.model.Articolo;
import it.prova.gestioneordini.model.Categoria;

public interface CategoriaService {
	public List<Categoria> listAll() throws Exception;

	public Categoria caricaSingoloElemento(Long id) throws Exception;

	public void aggiorna(Categoria categoriaInstance) throws Exception;

	public void inserisciNuovo(Categoria categoriaInstance) throws Exception;

	public void rimuovi(Categoria categoriaInstance) throws Exception;

	public Categoria caricaCategoriaConArticoli(Long id) throws Exception;

	// per injection
	public void setCategoriaDAO(CategoriaDAO categoriaDAO);

	public void aggiungiArticoloACategoria(Categoria categoriaInstance, Articolo articoloInstance) throws Exception;

	void rimuoviArticoloDaCategoria(Categoria categoriaInstance, Articolo articoloInstance) throws Exception;

	public void setArticoloDAO(ArticoloDAO articoloDAO);
}
