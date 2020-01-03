package info.andrefelipelaus.megasenabackend.controller.dto;

/**
 * 
 * @author André Felipe Laus
 *
 * Enumeratuion representado estados
 *
 */
public enum Status {
	OK("Ok"),
	RUNNIG("Runnig"),
	PENDING("Pending"),
	FAILURE("Fail"),
	NOT_APPLICABLE("N/A");

	private String name;

	Status(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
}
