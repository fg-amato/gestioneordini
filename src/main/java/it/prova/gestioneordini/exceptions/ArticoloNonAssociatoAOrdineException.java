package it.prova.gestioneordini.exceptions;

public class ArticoloNonAssociatoAOrdineException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public ArticoloNonAssociatoAOrdineException(String message) {
		super(message);
	}
}
