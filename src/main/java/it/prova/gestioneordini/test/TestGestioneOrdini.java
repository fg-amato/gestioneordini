package it.prova.gestioneordini.test;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import it.prova.gestioneordini.dao.EntityManagerUtil;
import it.prova.gestioneordini.exceptions.OrdineConArticoliException;
import it.prova.gestioneordini.model.Articolo;
import it.prova.gestioneordini.model.Categoria;
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
			testRimozioneOrdineConEccezione(ordineServiceInstance, articoloServiceInstance);

			System.out.println("Nella tabella ordine ci sono: " + ordineServiceInstance.listAll().size() + " elementi");

			System.out.println(
					"Nella tabella articolo ci sono: " + articoloServiceInstance.listAll().size() + " elementi");

			testCollegaArticoloAOrdineEsistente(articoloServiceInstance, ordineServiceInstance);

			System.out.println("Nella tabella ordine ci sono: " + ordineServiceInstance.listAll().size() + " elementi");

			System.out.println(
					"Nella tabella articolo ci sono: " + articoloServiceInstance.listAll().size() + " elementi");

			testUpdateOrdine(ordineServiceInstance);

			System.out.println("Nella tabella ordine ci sono: " + ordineServiceInstance.listAll().size() + " elementi");

			System.out.println(
					"Nella tabella categoria ci sono: " + categoriaServiceInstance.listAll().size() + " elementi");

			testInserisciCategoria(categoriaServiceInstance);

			System.out.println(
					"Nella tabella categoria ci sono: " + categoriaServiceInstance.listAll().size() + " elementi");

			testCollegaCategoriaAdArticolo(articoloServiceInstance, categoriaServiceInstance);

			System.out.println(
					"Nella tabella categoria ci sono: " + categoriaServiceInstance.listAll().size() + " elementi");

			System.out.println(
					"Nella tabella articolo ci sono: " + articoloServiceInstance.listAll().size() + " elementi");

			testCollegaArticoloACategoria(articoloServiceInstance, categoriaServiceInstance, ordineServiceInstance);

			System.out.println(
					"Nella tabella categoria ci sono: " + categoriaServiceInstance.listAll().size() + " elementi");

			System.out.println(
					"Nella tabella articolo ci sono: " + articoloServiceInstance.listAll().size() + " elementi");

			testScollegaArticoloACategoria(articoloServiceInstance, categoriaServiceInstance, ordineServiceInstance);

			System.out.println(
					"Nella tabella categoria ci sono: " + categoriaServiceInstance.listAll().size() + " elementi");

			System.out.println(
					"Nella tabella articolo ci sono: " + articoloServiceInstance.listAll().size() + " elementi");

			testScollegaCategoriaAdArticolo(articoloServiceInstance, categoriaServiceInstance);

			System.out.println(
					"Nella tabella categoria ci sono: " + categoriaServiceInstance.listAll().size() + " elementi");

			System.out.println(
					"Nella tabella articolo ci sono: " + articoloServiceInstance.listAll().size() + " elementi");

			testScollegaArticoloAdOrdine(articoloServiceInstance, ordineServiceInstance);

			System.out.println(
					"Nella tabella categoria ci sono: " + categoriaServiceInstance.listAll().size() + " elementi");

			System.out.println(
					"Nella tabella articolo ci sono: " + articoloServiceInstance.listAll().size() + " elementi");

			System.out.println("Nella tabella ordine ci sono: " + ordineServiceInstance.listAll().size() + " elementi");

			testRimozioneForzata(articoloServiceInstance, categoriaServiceInstance, ordineServiceInstance);

			System.out.println(
					"Nella tabella categoria ci sono: " + categoriaServiceInstance.listAll().size() + " elementi");

			System.out.println(
					"Nella tabella articolo ci sono: " + articoloServiceInstance.listAll().size() + " elementi");

			System.out.println("Nella tabella ordine ci sono: " + ordineServiceInstance.listAll().size() + " elementi");

			testFindAllCategories(articoloServiceInstance, categoriaServiceInstance, ordineServiceInstance);

			testFindAllOrdiniWithCategoriaInput(articoloServiceInstance, categoriaServiceInstance,
					ordineServiceInstance);

			testCalcolaCostoTotaleDataCategoria(articoloServiceInstance, categoriaServiceInstance,
					ordineServiceInstance);

			testOrdinePiuRecenteByCategoria(articoloServiceInstance, categoriaServiceInstance, ordineServiceInstance);

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
		if (ordineNuovo.getId() == null) {
			throw new RuntimeException("testRimozioneOrdine FAILED ");
		}

		Long idOrdineNuovo = ordineNuovo.getId();

		ordineServiceInstance.rimuovi(ordineNuovo);

		if (ordineServiceInstance.caricaSingoloElemento(idOrdineNuovo) != null) {
			throw new RuntimeException("testRimozioneOrdine FAILED: ordine non rimosso");
		}

		System.out.println(".......testRimozioneOrdine fine: PASSED.............");
	}

	private static void testRimozioneOrdineConEccezione(OrdineService ordineServiceInstance,
			ArticoloService articoloServiceInstance) throws Exception {
		System.out.println(".......testRimozioneOrdineConEccezione inizio.............");

		Ordine ordineCheGeneraEccezione = new Ordine("Mister Eccezione", "Via delle eccezioni 123",
				new SimpleDateFormat("dd-MM-yyyy").parse("20-12-2021"));
		ordineServiceInstance.inserisciNuovo(ordineCheGeneraEccezione);
		if (ordineCheGeneraEccezione.getId() == null)
			throw new RuntimeException("testRimozioneOrdineConEccezione FAILED: ordine non inserito ");

		Articolo articolo = new Articolo("Oggetto eccezionale", "EXP123", 200,
				new SimpleDateFormat("dd-MM-yyyy").parse("18-06-2021"), ordineCheGeneraEccezione);

		articoloServiceInstance.inserisciNuovo(articolo);
		if (articolo.getId() == null)
			throw new RuntimeException("testRimozioneOrdineConEccezione FAILED, articolo non inserito nel DB");

		Ordine ordineReloaded = ordineServiceInstance.caricaOrdineConArticoli(ordineCheGeneraEccezione.getId());

		System.out.println(ordineReloaded.getArticoli().size());

		try {
			ordineServiceInstance.rimuovi(ordineCheGeneraEccezione);
			throw new RuntimeException("Non doveva arrivare qui");
		} catch (OrdineConArticoliException exp) {

		}

		System.out.println(".......testRimozioneOrdineConEccezione fine: PASSED.............");
	}

	private static void testCollegaArticoloAOrdineEsistente(ArticoloService articoloServiceInstance,
			OrdineService ordineServiceInstance) throws Exception {
		System.out.println(".......testCollegaArticoloAOrdineEsistente inizio.............");

		// mi creo un ordine inserendolo direttamente su db
		Ordine ordineNuovo = new Ordine("mario.bianchi", "Via Bianchi 38",
				new SimpleDateFormat("dd-MM-yyyy").parse("18-07-2021"));
		ordineServiceInstance.inserisciNuovo(ordineNuovo);
		if (ordineNuovo.getId() == null)
			throw new RuntimeException("testCollegaArticoloAOrdineEsistente FAILED, ordine non inserito ");

		Articolo daAggiungere = new Articolo("Aggiunge", "444ADD", 250,
				new SimpleDateFormat("dd-MM-yyyy").parse("15-05-2019"), ordineNuovo);

		ordineServiceInstance.aggiungiArticoloAdOrdine(ordineNuovo, daAggiungere);
		// per fare il test ricarico interamente l'oggetto e la relazione
		Ordine ordineReloaded = ordineServiceInstance.caricaOrdineConArticoli(ordineNuovo.getId());
		if (ordineReloaded.getArticoli().size() != 1)
			throw new RuntimeException("testCollegaOrdineAArticoloEsistente FAILED, articolo non aggiunto");

		System.out.println(".......testCollegaOrdineAArticoloEsistente fine: PASSED.............");
	}

	private static void testUpdateOrdine(OrdineService ordineServiceInstance) throws Exception {
		System.out.println(".......testUpdateOrdine inizio.............");

		// mi creo un ordine inserendolo direttamente su db
		Ordine ordineNuovo = new Ordine("mario.bianchi", "Via Bianchi 38",
				new SimpleDateFormat("dd-MM-yyyy").parse("18-07-2021"));
		ordineServiceInstance.inserisciNuovo(ordineNuovo);
		if (ordineNuovo.getId() == null)
			throw new RuntimeException("testUpdateOrdine FAILED, ordine non inserito ");

		ordineNuovo.setDataSpedizione(new SimpleDateFormat("dd-MM-yyyy").parse("01-01-2001"));
		ordineNuovo.setNomeDestinatario("Ciccio Pasticcio");
		ordineServiceInstance.aggiorna(ordineNuovo);
		Ordine ordineReloaded = ordineServiceInstance.caricaOrdineConArticoli(ordineNuovo.getId());
		if (!ordineReloaded.equals(ordineNuovo))
			throw new RuntimeException("testUpdateOrdine FAILED, i due oggetti sono diversi");

		System.out.println(".......testUpdateOrdine fine: PASSED.............");
	}

	private static void testInserisciCategoria(CategoriaService categoriaServiceInstance) throws Exception {
		System.out.println(".......testInserisciCategoria inizio.............");

		Categoria daInserire = new Categoria("inserito", "INSERT");
		categoriaServiceInstance.inserisciNuovo(daInserire);
		if (daInserire.getId() == null)
			throw new RuntimeException("testInserisciCategoria FAILED ");

		System.out.println(".......testInserisciCategoria fine: PASSED.............");
	}

	private static void testCollegaCategoriaAdArticolo(ArticoloService articoloServiceInstance,
			CategoriaService categoriaServiceInstance) throws Exception {
		System.out.println(".......testAggiungiCategoriaAdArticolo inizio.............");

		List<Articolo> articoliAttualmenteNelDB = articoloServiceInstance.listAll();

		if (articoliAttualmenteNelDB.size() < 1) {
			throw new RuntimeException("testAggiungiCategoriaAdArticolo FAILED, non ci sono articoli nel DB");
		}
		Articolo primoInLista = articoliAttualmenteNelDB.get(0);
		Categoria daAggiungere = new Categoria("Latticini", "LTT");

		Articolo primoInListaConCategorieAssociate = articoloServiceInstance
				.caricaArticoloConCategorie(primoInLista.getId());
		categoriaServiceInstance.inserisciNuovo(daAggiungere);

		articoloServiceInstance.aggiungiCategoriaAdArticolo(primoInListaConCategorieAssociate, daAggiungere);

		if (daAggiungere.getId() == null)
			throw new RuntimeException("testAggiungiCategoriaAdArticolo FAILED, categoria non aggiunta nel DB");

		Articolo primoInListaConCategorieDopoAggiunzione = articoloServiceInstance
				.caricaArticoloConCategorie(primoInLista.getId());

		if (primoInListaConCategorieAssociate.getCategorie()
				.size() != primoInListaConCategorieDopoAggiunzione.getCategorie().size() - 1) {
			throw new RuntimeException("testAggiungiCategoriaAdArticolo FAILED, categoria non associata all'articolo");
		}

		System.out.println(".......testAggiungiCategoriaAdArticolo fine: PASSED.............");
	}

	private static void testCollegaArticoloACategoria(ArticoloService articoloServiceInstance,
			CategoriaService categoriaServiceInstance, OrdineService ordineServiceInstance) throws Exception {
		System.out.println(".......testCollegaArticoloACategoria inizio.............");

		List<Categoria> categorieAttualmenteNelDB = categoriaServiceInstance.listAll();
		List<Ordine> ordiniAttualmenteNelDB = ordineServiceInstance.listAll();
		if (categorieAttualmenteNelDB.size() < 1 || ordiniAttualmenteNelDB.size() < 1) {
			throw new RuntimeException("testCollegaArticoloACategoria FAILED, non ci sono categorie o ordini nel DB");
		}
		Categoria primoInLista = categorieAttualmenteNelDB.get(0);
		Articolo daAggiungere = new Articolo();
		daAggiungere.setOrdine(ordiniAttualmenteNelDB.get(0));

		Categoria primoInListaConArticoliAssociati = categoriaServiceInstance
				.caricaCategoriaConArticoli(primoInLista.getId());
		articoloServiceInstance.inserisciNuovo(daAggiungere);

		categoriaServiceInstance.aggiungiArticoloACategoria(primoInListaConArticoliAssociati, daAggiungere);

		if (daAggiungere.getId() == null)
			throw new RuntimeException("testCollegaArticoloACategoria FAILED, categoria non aggiunta nel DB");

		Categoria primoInListaConArticoliDopoAggiunzione = categoriaServiceInstance
				.caricaCategoriaConArticoli(primoInLista.getId());

		if (primoInListaConArticoliAssociati.getArticoli()
				.size() != primoInListaConArticoliDopoAggiunzione.getArticoli().size() - 1) {
			throw new RuntimeException("testCollegaArticoloACategoria FAILED, articolo non associato alla categoria");
		}

		System.out.println(".......testAggiungiArticoloACategoria fine: PASSED.............");
	}

	private static void testScollegaArticoloACategoria(ArticoloService articoloServiceInstance,
			CategoriaService categoriaServiceInstance, OrdineService ordineServiceInstance) throws Exception {
		System.out.println(".......testScollegaArticoloACategoria inizio.............");

		List<Categoria> categorieAttualmenteNelDB = categoriaServiceInstance.listAll();
		List<Ordine> ordiniAttualmenteNelDB = ordineServiceInstance.listAll();
		if (categorieAttualmenteNelDB.size() < 1 || ordiniAttualmenteNelDB.size() < 1) {
			throw new RuntimeException("testScollegaArticoloACategoria FAILED, non ci sono categorie o ordini nel DB");
		}
		Categoria primoInLista = categorieAttualmenteNelDB.get(0);
		Articolo daAggiungere = new Articolo();
		daAggiungere.setOrdine(ordiniAttualmenteNelDB.get(0));

		// aggiungo l'articolo al db
		articoloServiceInstance.inserisciNuovo(daAggiungere);

		// associo l'articolo alla categoria
		categoriaServiceInstance.aggiungiArticoloACategoria(primoInLista, daAggiungere);

		Categoria primoInListaConArticoliAssociati = categoriaServiceInstance
				.caricaCategoriaConArticoli(primoInLista.getId());

		if (daAggiungere.getId() == null)
			throw new RuntimeException("testScollegaArticoloACategoria FAILED, articolo non aggiunta nel DB");

		categoriaServiceInstance.rimuoviArticoloDaCategoria(primoInListaConArticoliAssociati, daAggiungere);
		Categoria primoInListaConArticoliDopoRimozione = categoriaServiceInstance
				.caricaCategoriaConArticoli(primoInLista.getId());

		if (primoInListaConArticoliAssociati.getArticoli()
				.size() != primoInListaConArticoliDopoRimozione.getArticoli().size() + 1) {
			throw new RuntimeException(
					"testScollegaArticoloACategoria FAILED, articolo non dissociato dalla categoria");
		}

		System.out.println(".......testScollegaArticoloACategoria fine: PASSED.............");
	}

	private static void testScollegaCategoriaAdArticolo(ArticoloService articoloServiceInstance,
			CategoriaService categoriaServiceInstance) throws Exception {
		System.out.println(".......testScollegaCategoriaAdArticolo inizio.............");

		List<Articolo> articoliAttualmenteNelDB = articoloServiceInstance.listAll();

		if (articoliAttualmenteNelDB.size() < 1) {
			throw new RuntimeException("testScollegaCategoriaAdArticolo FAILED, non ci sono articoli nel DB");
		}
		Articolo primoInLista = articoliAttualmenteNelDB.get(0);
		Categoria daAggiungere = new Categoria("Farinacei", "FRC");

		categoriaServiceInstance.inserisciNuovo(daAggiungere);

		articoloServiceInstance.aggiungiCategoriaAdArticolo(primoInLista, daAggiungere);

		Articolo primoInListaConCategorieAssociate = articoloServiceInstance
				.caricaArticoloConCategorie(primoInLista.getId());

		if (daAggiungere.getId() == null)
			throw new RuntimeException("testScollegaCategoriaAdArticolo FAILED, categoria non aggiunta nel DB");

		articoloServiceInstance.rimuoviCategoriaDaArticolo(daAggiungere, primoInListaConCategorieAssociate);
		Articolo primoInListaConCategorieDopoRimozione = articoloServiceInstance
				.caricaArticoloConCategorie(primoInLista.getId());

		if (primoInListaConCategorieAssociate.getCategorie()
				.size() != primoInListaConCategorieDopoRimozione.getCategorie().size() + 1) {
			throw new RuntimeException("testScollegaCategoriaAdArticolo FAILED, categoria non associata all'articolo");
		}

		System.out.println(".......testScollegaCategoriaAdArticolo fine: PASSED.............");
	}

	private static void testScollegaArticoloAdOrdine(ArticoloService articoloServiceInstance,
			OrdineService ordineServiceInstance) throws Exception {
		System.out.println(".......testScollegaArticoloAdOrdine inizio.............");

		// mi creo un ordine inserendolo direttamente su db
		Ordine ordineNuovo = new Ordine("mario.bianchi", "Via Bianchi 38",
				new SimpleDateFormat("dd-MM-yyyy").parse("18-07-2021"));
		ordineServiceInstance.inserisciNuovo(ordineNuovo);
		if (ordineNuovo.getId() == null)
			throw new RuntimeException("testScollegaArticoloAdOrdine FAILED, ordine non inserito ");

		Articolo daAggiungere = new Articolo("Aggiunge", "444ADD", 250,
				new SimpleDateFormat("dd-MM-yyyy").parse("15-05-2019"), ordineNuovo);

		articoloServiceInstance.inserisciNuovo(daAggiungere);
		ordineServiceInstance.aggiungiArticoloAdOrdine(ordineNuovo, daAggiungere);
		// per fare il test ricarico interamente l'oggetto e la relazione
		Ordine ordineReloaded = ordineServiceInstance.caricaOrdineConArticoli(ordineNuovo.getId());
		if (ordineReloaded.getArticoli().size() != 1)
			throw new RuntimeException("testScollegaArticoloAdOrdine FAILED, articolo non aggiunto");

		ordineServiceInstance.rimuoviArticoloDaOrdine(ordineNuovo, daAggiungere);

		ordineReloaded = ordineServiceInstance.caricaOrdineConArticoli(ordineNuovo.getId());
		if (ordineReloaded.getArticoli().size() != 0)
			throw new RuntimeException("testScollegaArticoloAdOrdine FAILED, articolo non rimosso");
		System.out.println(".......testScollegaArticoloAdOrdine fine: PASSED.............");
	}

	private static void testRimozioneForzata(ArticoloService articoloServiceInstance,
			CategoriaService categoriaServiceInstance, OrdineService ordineServiceInstance) throws Exception {
		System.out.println(".......testRimozioneForzata inizio.............");

		Ordine daRimuovere = new Ordine("Piero Neri", "Via Roma 37",
				new SimpleDateFormat("dd-MM-yyyy").parse("20-01-2022"));
		ordineServiceInstance.inserisciNuovo(daRimuovere);

		Articolo articoloDaRimuovere = new Articolo("meme", "32321", 10,
				new SimpleDateFormat("dd-MM-yyyy").parse("15-05-2019"), daRimuovere);

		articoloServiceInstance.inserisciNuovo(articoloDaRimuovere);

		ordineServiceInstance.aggiungiArticoloAdOrdine(daRimuovere, articoloDaRimuovere);

		Categoria categoriaArticoloDaRimuovere = new Categoria("Mi rimuovi", "RMV");

		categoriaServiceInstance.inserisciNuovo(categoriaArticoloDaRimuovere);

		articoloServiceInstance.aggiungiCategoriaAdArticolo(articoloDaRimuovere, categoriaArticoloDaRimuovere);
		Long idOrdineNuovo = daRimuovere.getId();
		ordineServiceInstance.rimozioneForzata(daRimuovere);

		if (ordineServiceInstance.caricaSingoloElemento(idOrdineNuovo) != null) {
			throw new RuntimeException("testRimozioneForzata FAILED, ordine non rimosso");
		}

		System.out.println(".......testRimozioneForzata fine: PASSED.............");
	}

	private static void testFindAllCategories(ArticoloService articoloServiceInstance,
			CategoriaService categoriaServiceInstance, OrdineService ordineServiceInstance) throws Exception {
		System.out.println(".......testFindAllCategories inizio.............");

		Ordine ordine = new Ordine("Piero Neri", "Via Roma 37", new SimpleDateFormat("dd-MM-yyyy").parse("20-01-2022"));
		ordineServiceInstance.inserisciNuovo(ordine);

		Articolo articoloConUnaCategoria = new Articolo("meme", "32321", 10,
				new SimpleDateFormat("dd-MM-yyyy").parse("15-05-2019"), ordine);
		Articolo articoloMultiCategoria = new Articolo("pippo", "2222", 15, new Date(), ordine);

		articoloServiceInstance.inserisciNuovo(articoloConUnaCategoria);
		articoloServiceInstance.inserisciNuovo(articoloMultiCategoria);

		ordineServiceInstance.aggiungiArticoloAdOrdine(ordine, articoloConUnaCategoria);
		ordineServiceInstance.aggiungiArticoloAdOrdine(ordine, articoloMultiCategoria);

		Categoria categoriaArticoloSingolaCategoria = new Categoria("Mi rimuovi", "RMV");

		Categoria categoriaArticoloMultiCategorie = new Categoria("Mi aggiungi", "ADD");

		categoriaServiceInstance.inserisciNuovo(categoriaArticoloSingolaCategoria);
		categoriaServiceInstance.inserisciNuovo(categoriaArticoloMultiCategorie);

		articoloServiceInstance.aggiungiCategoriaAdArticolo(articoloMultiCategoria, categoriaArticoloSingolaCategoria);
		articoloServiceInstance.aggiungiCategoriaAdArticolo(articoloMultiCategoria, categoriaArticoloMultiCategorie);
		articoloServiceInstance.aggiungiCategoriaAdArticolo(articoloConUnaCategoria, categoriaArticoloSingolaCategoria);

		if (ordineServiceInstance.trovaCategorieDistinteDegliArticoliDiUnOrdine(ordine).size() != 2) {
			throw new RuntimeException("testFindAllCategories FAILED, riscontro non valido");
		}

		System.out.println(".......testFindAllCategories fine: PASSED.............");
	}

	private static void testFindAllOrdiniWithCategoriaInput(ArticoloService articoloServiceInstance,
			CategoriaService categoriaServiceInstance, OrdineService ordineServiceInstance) throws Exception {
		System.out.println(".......testFindAllOrdiniWithCategoriaInput inizio.............");

		Ordine ordinePieroNeri = new Ordine("Piero Neri", "Via Roma 37",
				new SimpleDateFormat("dd-MM-yyyy").parse("20-01-2022"));
		ordineServiceInstance.inserisciNuovo(ordinePieroNeri);

		Ordine ordineMarioBianchi = new Ordine("mario.bianchi", "Via Bianchi 38",
				new SimpleDateFormat("dd-MM-yyyy").parse("18-07-2021"));
		ordineServiceInstance.inserisciNuovo(ordineMarioBianchi);

		Ordine ordineFrancoFranchi = new Ordine("Franco Franchi", "Via Rossi 38",
				new SimpleDateFormat("dd-MM-yyyy").parse("05-03-2021"));
		ordineServiceInstance.inserisciNuovo(ordineFrancoFranchi);

		Articolo articoloPieroNeri = new Articolo("meme", "32321", 10,
				new SimpleDateFormat("dd-MM-yyyy").parse("15-05-2019"), ordinePieroNeri);
		Articolo articoloMarioBianchi = new Articolo("pippo", "2222", 15, new Date(), ordineMarioBianchi);
		Articolo articoloFrancoFranchi = new Articolo("ciccio", "1252", 27, new Date(), ordineFrancoFranchi);

		articoloServiceInstance.inserisciNuovo(articoloPieroNeri);
		articoloServiceInstance.inserisciNuovo(articoloMarioBianchi);
		articoloServiceInstance.inserisciNuovo(articoloFrancoFranchi);

		ordineServiceInstance.aggiungiArticoloAdOrdine(ordinePieroNeri, articoloPieroNeri);
		ordineServiceInstance.aggiungiArticoloAdOrdine(ordineMarioBianchi, articoloMarioBianchi);
		ordineServiceInstance.aggiungiArticoloAdOrdine(ordineFrancoFranchi, articoloFrancoFranchi);

		Categoria categoriaArticoloPieroNeri = new Categoria("Mi rimuovi", "RMV");

		Categoria categoriaArticoloPieroNeriEMarioBianchi = new Categoria("Mi aggiungi", "ADD");

		categoriaServiceInstance.inserisciNuovo(categoriaArticoloPieroNeri);
		categoriaServiceInstance.inserisciNuovo(categoriaArticoloPieroNeriEMarioBianchi);

		articoloServiceInstance.aggiungiCategoriaAdArticolo(articoloPieroNeri, categoriaArticoloPieroNeri);
		articoloServiceInstance.aggiungiCategoriaAdArticolo(articoloPieroNeri, categoriaArticoloPieroNeriEMarioBianchi);
		articoloServiceInstance.aggiungiCategoriaAdArticolo(articoloMarioBianchi,
				categoriaArticoloPieroNeriEMarioBianchi);

		if (ordineServiceInstance
				.trovaTuttiGliOrdiniChePossiedonoUnArticoloAventeCategoria(categoriaArticoloPieroNeriEMarioBianchi)
				.size() != 2) {
			throw new RuntimeException("testFindAllOrdiniWithCategoriaInput FAILED, riscontro non valido");
		}

		System.out.println(".......testFindAllOrdiniWithCategoriaInput fine: PASSED.............");
	}

	private static void testCalcolaCostoTotaleDataCategoria(ArticoloService articoloService,
			CategoriaService categoriaService, OrdineService ordineService) throws Exception {

		System.out.println(".......testCalcolaCostoTotaleDataCategoria inizio.............");

		// Creo categoria
		Categoria categoriaPerCalcolo = new Categoria("trophy", "trophy07");
		if (categoriaPerCalcolo.getId() != null)
			throw new RuntimeException("testCalcolaCostoTotaleDataCategoria FAILED, categoria non inserita");

		// Inserisco categoria
		categoriaService.inserisciNuovo(categoriaPerCalcolo);

		// Creo ordine
		Ordine ordinePerCalcolo = new Ordine("irene", "via amico", new Date());
		if (ordinePerCalcolo.getId() != null)
			throw new RuntimeException("testCalcolaCostoTotaleDataCategoria FAILED, ordine già sul DB");

		// Inserisco ordine
		ordineService.inserisciNuovo(ordinePerCalcolo);
		if (ordinePerCalcolo.getId() == null)
			throw new RuntimeException("testCalcolaCostoTotaleDataCategoria FAILED, ordine non inserito");

		// Creo articoli
		Articolo articoloPerRicerca = new Articolo("videogioco", "halo", 70, new Date());
		if (articoloPerRicerca.getId() != null)
			throw new RuntimeException(
					"testCalcolaCostoTotaleDataCategoria FAILED, articolo già registrato su database");

		Articolo articoloPerRicerca1 = new Articolo("videogioco", "destiny", 60, new Date());
		if (articoloPerRicerca.getId() != null)
			throw new RuntimeException(
					"testCalcolaCostoTotaleDataCategoria FAILED, articolo già registrato su database");

		Articolo articoloPerRicerca2 = new Articolo("videogioco", "genshin", 20, new Date());
		if (articoloPerRicerca.getId() != null)
			throw new RuntimeException(
					"testCalcolaCostoTotaleDataCategoria FAILED, articolo già registrato su database");
		articoloPerRicerca.setOrdine(ordinePerCalcolo);
		articoloPerRicerca1.setOrdine(ordinePerCalcolo);
		articoloPerRicerca2.setOrdine(ordinePerCalcolo);

		// Inserisco articoli
		articoloService.inserisciNuovo(articoloPerRicerca);
		articoloService.inserisciNuovo(articoloPerRicerca1);
		articoloService.inserisciNuovo(articoloPerRicerca2);

		articoloService.aggiungiCategoriaAdArticolo(articoloPerRicerca, categoriaPerCalcolo);
		articoloService.aggiungiCategoriaAdArticolo(articoloPerRicerca1, categoriaPerCalcolo);
		articoloService.aggiungiCategoriaAdArticolo(articoloPerRicerca2, categoriaPerCalcolo);

		if (articoloPerRicerca.getId() == null || articoloPerRicerca1.getId() == null
				|| articoloPerRicerca2.getId() == null)
			throw new RuntimeException("testCalcolaCostoTotaleDataCategoria FAILED, articolo non inserito sul DB");

		Long totaleCosto = articoloService.calcolaPrezzoArticoli(categoriaPerCalcolo);
		if (totaleCosto % 150 != 0)
			throw new RuntimeException("testCalcolaCostoTotaleDataCategoria FAILED, calcolo fallito");

		System.out.println(".......testCalcolaCostoTotaleDataCategoria fine: PASSED.............");

	}

	private static void testOrdinePiuRecenteByCategoria(ArticoloService articoloService,
			CategoriaService categoriaService, OrdineService ordineService) throws Exception {

		System.out.println(".......testOrdinePiuRecenteByCategoria inizio.............");

		// Creo categoria
		Categoria categoriaPerCalcolo = new Categoria("Recente", "RCT");

		// Inserisco categoria
		categoriaService.inserisciNuovo(categoriaPerCalcolo);

		// Creo ordine
		Ordine ordinePiuRecenteSenzaArticoliDellaCategoria = new Ordine("frank", "via fold",
				new SimpleDateFormat("dd-MM-yyyy").parse("01-03-2022"));
		Ordine ordinePiuRecenteConArticoliDellaCategoria = new Ordine("Recente", "via dei recenti",
				new SimpleDateFormat("dd-MM-yyyy").parse("20-02-2022"));
		Ordine ordineGenericoConCuiConfrontare = new Ordine("Casual", "via random",
				new SimpleDateFormat("dd-MM-yyyy").parse("01-01-2022"));
		// Inserisco ordine
		ordineService.inserisciNuovo(ordinePiuRecenteSenzaArticoliDellaCategoria);
		ordineService.inserisciNuovo(ordinePiuRecenteConArticoliDellaCategoria);
		ordineService.inserisciNuovo(ordineGenericoConCuiConfrontare);
		if (ordinePiuRecenteSenzaArticoliDellaCategoria.getId() == null)
			throw new RuntimeException("testOrdinePiuRecenteByCategoria FAILED, ordine non inserito");

		// Creo articoli
		Articolo articoloCategoriaRicercata = new Articolo("telefono", "SamsungS21", 600, new Date());

		Articolo articoloPerCategoriaRicercata2 = new Articolo("telefono", "OnePlus9", 320, new Date());

		Articolo articoloPerCategoriaNonRicercata = new Articolo("videogioco", "genshin", 20, new Date());

		articoloCategoriaRicercata.setOrdine(ordineGenericoConCuiConfrontare);
		articoloPerCategoriaRicercata2.setOrdine(ordinePiuRecenteConArticoliDellaCategoria);
		articoloPerCategoriaNonRicercata.setOrdine(ordinePiuRecenteSenzaArticoliDellaCategoria);

		// Inserisco articoli
		articoloService.inserisciNuovo(articoloCategoriaRicercata);
		articoloService.inserisciNuovo(articoloPerCategoriaRicercata2);
		articoloService.inserisciNuovo(articoloPerCategoriaNonRicercata);

		articoloService.aggiungiCategoriaAdArticolo(articoloCategoriaRicercata, categoriaPerCalcolo);
		articoloService.aggiungiCategoriaAdArticolo(articoloPerCategoriaRicercata2, categoriaPerCalcolo);

		Ordine piuRecente = ordineService.trovaIlPiuRecenteOrdineDiUnaCategoria(categoriaPerCalcolo);

		if (!piuRecente.equals(ordinePiuRecenteConArticoliDellaCategoria)) {
			throw new RuntimeException("testOrdinePiuRecenteByCategoria FAILED, riscontro non valido");
		}

		System.out.println(".......testOrdinePiuRecenteByCategoria fine: PASSED.............");

	}
}
