package it.prova.gestioneordini.test;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import it.prova.gestioneordini.dao.EntityManagerUtil;
import it.prova.gestioneordini.exceptions.OrdineConArticoliException;
import it.prova.gestioneordini.model.Articolo;
import it.prova.gestioneordini.model.Ordine;
import it.prova.gestioneordini.service.ArticoloService;
import it.prova.gestioneordini.service.CategoriaService;
import it.prova.gestioneordini.service.MyServiceFactory;
import it.prova.gestioneordini.service.OrdineService;

public class TestGestioneOrdini {

	public static void main(String[] args) {
		OrdineService ordineServiceInstance = MyServiceFactory.getOrdineServiceInstance();
		ArticoloService articoloServiceInstance = MyServiceFactory.getArticoloServiceInstance();
		CategoriaService categoriaServiceInstance = MyServiceFactory.getCategoriaServiceInstance();

		// ora passo alle operazioni CRUD
		try {
			System.out.println("Nella tabella ordine ci sono: " + ordineServiceInstance.listAll().size() + " elementi");
			testInserisciOrdine(ordineServiceInstance);

			System.out.println("Nella tabella ordine ci sono: " + ordineServiceInstance.listAll().size() + " elementi");

			System.out.println(
					"Nella tabella articolo ci sono: " + articoloServiceInstance.listAll().size() + " elementi");

			testRimozioneOrdine(ordineServiceInstance, articoloServiceInstance);

			System.out.println("Nella tabella ordine ci sono: " + ordineServiceInstance.listAll().size() + " elementi");

			System.out.println(
					"Nella tabella articolo ci sono: " + articoloServiceInstance.listAll().size() + " elementi");

		} catch (Throwable e) {
			e.printStackTrace();
		} finally {
			// questa è necessaria per chiudere tutte le connessioni quindi rilasciare il
			// main
			EntityManagerUtil.shutdown();
		}
	}

	private static void testInserisciOrdine(OrdineService ordineServiceInstance) throws Exception {
		System.out.println(".......testInserisciOrdine inizio.............");

		Ordine ordineNuovo = new Ordine("Piero Neri", "Via Roma 37",
				new SimpleDateFormat("dd-MM-yyyy").parse("20-01-2022"));
		ordineServiceInstance.inserisciNuovo(ordineNuovo);
		if (ordineNuovo.getId() == null)
			throw new RuntimeException("testInserisciOrdine FAILED ");

		System.out.println(".......testInserisciOrdine fine: PASSED.............");
	}

	private static void testRimozioneOrdine(OrdineService ordineServiceInstance,
			ArticoloService articoloServiceInstance) throws Exception {
		System.out.println(".......testRimozioneOrdine inizio.............");

		Ordine ordineNuovo = new Ordine("Luca Rossi", "Via Torino 59",
				new SimpleDateFormat("dd-MM-yyyy").parse("19-02-2022"));
		ordineServiceInstance.inserisciNuovo(ordineNuovo);
		if (ordineNuovo.getId() == null)
			throw new RuntimeException("testRimozioneOrdine FAILED ");

		Ordine ordineCheGeneraEccezione = new Ordine("Mister Eccezione", "Via delle eccezioni 123",
				new SimpleDateFormat("dd-MM-yyyy").parse("20-12-2021"));

		Long idOrdineNuovo = ordineNuovo.getId();

		ordineServiceInstance.rimuovi(ordineNuovo);
		if (ordineServiceInstance.caricaSingoloElemento(idOrdineNuovo) != null) {
			throw new RuntimeException("testRimozioneOrdine FAILED: ordine non rimosso");
		}

		ordineServiceInstance.inserisciNuovo(ordineCheGeneraEccezione);

		Articolo articolo = new Articolo("Oggetto eccezionale", "EXP123", 200,
				new SimpleDateFormat("dd-MM-yyyy").parse("18-06-2021"));
		articolo.setOrdine(ordineCheGeneraEccezione);

		articoloServiceInstance.inserisciNuovo(articolo);

		if (ordineCheGeneraEccezione.getId() == null)
			throw new RuntimeException("testRimozioneOrdine FAILED ");
		if (articolo.getId() == null)
			throw new RuntimeException("testRimozioneOrdine FAILED, articolo non inserito nel DB");
		try {
			ordineServiceInstance.rimuovi(ordineCheGeneraEccezione);
			throw new RuntimeException("Non doveva arrivare qui");
		} catch (OrdineConArticoliException exp) {

		}

		System.out.println(".......testRimozioneOrdine fine: PASSED.............");
	}
}
