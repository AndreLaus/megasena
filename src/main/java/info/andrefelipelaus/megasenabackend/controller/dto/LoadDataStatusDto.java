package info.andrefelipelaus.megasenabackend.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 
 * @author André Felipe Laus
 * 
 * Represanta o status das fazes da carga de dados.
 *
 */
@Data
@AllArgsConstructor
public class LoadDataStatusDto {

	private Status download;
	private Status unzip;
	private Status convertHtmlToList;
	private Status convertListToObject;
	private Status saveInDatabase;
	
}
