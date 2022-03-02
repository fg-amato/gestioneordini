package it.prova.gestioneordini.service;

import java.util.List;

import it.prova.gestioneordini.dao.ArticoloDAO;
import it.prova.gestioneordini.dao.CategoriaDAO;
import it.prova.gestioneordini.model.Articolo;
import it.prova.gestioneordini.model.Categoria;

public interface ArticoloService {
	public List<Articolo> listAll() throws Exception;

	public Articolo caricaSingoloElemento(Long id) throws Exception;

	public void aggiorna(Articolo articoloInstance) throws Exception;

	public void inserisciNuovo(Articolo articoloInstance) throws Exception;

	public void rimuovi(Articolo articoloInstance) throws Exception;

	public Articolo caricaArticoloConCategorie(Long id) throws Exception;

	public void aggiungiCategoriaAdArticolo(Articolo articoloInstance, Categoria categoriaInstance) throws Exception;

	void rimuoviCategoriaDaArticolo(Categoria categoriaInstance, Articolo articoloInstance) throws Exception;

	// per injection
	public void setArticoloDAO(ArticoloDAO articoloDAO);

	public void setCategoriaDAO(CategoriaDAO categoriaDAO);
}
