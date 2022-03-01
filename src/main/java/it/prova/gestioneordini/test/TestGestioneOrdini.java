package it.prova.gestioneordini.test;

import java.text.SimpleDateFormat;
import java.util.Date;

import it.prova.gestioneordini.dao.EntityManagerUtil;
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

}
