package info.andrefelipelaus.megasenabackend.controller.dto;

/**
 * 
 * @author André Felipe Laus
 *
 * Enumeratuion representado estados
 *
 */
public enum Situacao {
	OK("Ok"),
	EXECUTANDO("Executando"),
	PENDENTE("Pendente"),
	FALHA("Falha"),
	NAO_SE_APLICA("N/A");

	private String name;

	Situacao(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
}
