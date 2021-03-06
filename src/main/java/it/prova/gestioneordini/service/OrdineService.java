package it.prova.gestioneordini.service;

import java.util.List;

import it.prova.gestioneordini.dao.ArticoloDAO;
import it.prova.gestioneordini.dao.OrdineDAO;
import it.prova.gestioneordini.model.Articolo;
import it.prova.gestioneordini.model.Categoria;
import it.prova.gestioneordini.model.Ordine;

public interface OrdineService {
	public List<Ordine> listAll() throws Exception;

	public Ordine caricaSingoloElemento(Long id) throws Exception;

	public void aggiorna(Ordine ordineInstance) throws Exception;

	public void inserisciNuovo(Ordine ordineInstance) throws Exception;

	public void rimuovi(Ordine ordineInstance) throws Exception;

	public Ordine caricaOrdineConArticoli(Long id) throws Exception;

	public void aggiungiArticoloAdOrdine(Ordine ordineEsistente, Articolo articoloInstance) throws Exception;

	public void rimuoviArticoloDaOrdine(Ordine ordineEsistente, Articolo articoloInstance) throws Exception;

	public void rimozioneForzata(Ordine ordineEsistente) throws Exception;

	public List<Categoria> trovaCategorieDistinteDegliArticoliDiUnOrdine(Ordine ordineEsistente) throws Exception;

	public Ordine trovaIlPiuRecenteOrdineDiUnaCategoria(Categoria categoriaInput) throws Exception;

	public List<Ordine> trovaTuttiGliOrdiniChePossiedonoUnArticoloAventeCategoria(Categoria categoriaInput)
			throws Exception;

	// per injection
	public void setOrdineDAO(OrdineDAO ordineDAO);

	public void setArticoloDAO(ArticoloDAO articoloDAO);
}
