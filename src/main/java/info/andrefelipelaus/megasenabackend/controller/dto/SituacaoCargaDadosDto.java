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
public class SituacaoCargaDadosDto {

	private Situacao download;
	private Situacao unzip;
	private Situacao convertHtmlToList;
	private Situacao convertListToObject;
	private Situacao saveInDatabase;
	
}
