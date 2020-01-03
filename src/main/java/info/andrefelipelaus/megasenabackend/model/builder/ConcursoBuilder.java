package info.andrefelipelaus.megasenabackend.model.builder;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import info.andrefelipelaus.megasenabackend.model.Concurso;

/**
 * 
 * @author André Felipe Laus
 *
 * Converte uma lista de strings em Objetos do tipo {@link Concurso}
 *
 */

public class ConcursoBuilder {

	public static List<Concurso> listFromHtmlToListObject(List<List<String>> listValues) throws ParseException {
		
		List<Concurso> resultList = new ArrayList<>();
		if (listValues != null) {
			Concurso concurso = null;
			DecimalFormat decimalFormat = new DecimalFormat("##,###,###,##0.00", new DecimalFormatSymbols (new Locale ("pt", "BR")));
			decimalFormat.setMinimumFractionDigits(2); 
			decimalFormat.setParseBigDecimal (true);
			
			DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy");
			
			for (List<String> list : listValues) {
				if (list.size()>2) {
					
					concurso = new Concurso();
					
					//numero concurso
					concurso.setNumero(Integer.parseInt(list.get(0)));
					
					concurso.setDataSorteio(LocalDate.from(dateFormat.parse(list.get(1))));
					
					//dezenas
					concurso.addDezena(Short.parseShort(list.get(2)));
					concurso.addDezena(Short.parseShort(list.get(3)));
					concurso.addDezena(Short.parseShort(list.get(4)));
					concurso.addDezena(Short.parseShort(list.get(5)));
					concurso.addDezena(Short.parseShort(list.get(6)));
					concurso.addDezena(Short.parseShort(list.get(7)));
					
					
					concurso.setArrecadacaoTotal(decimalFormat.parse(list.get(8)).doubleValue());
					concurso.setNumeroGanhadoresSena(Integer.parseInt(list.get(9)));
					
					concurso.addCidade(list.get(10));
					concurso.addEstado(list.get(11));
					
					concurso.setRateioSena(decimalFormat.parse(list.get(12)).doubleValue());
					
					concurso.setNumeroGanhadoresQuina(Integer.parseInt(list.get(13)));
					concurso.setRateioQuina(decimalFormat.parse(list.get(14)).doubleValue());
					
					concurso.setNumeroGanhadoresQuadra(Integer.parseInt(list.get(15)));
					concurso.setRateioQuadra(decimalFormat.parse(list.get(16)).doubleValue());
					
					concurso.setAcumulado(list.get(17));
					concurso.setValorAcumulado(decimalFormat.parse(list.get(18)).doubleValue());
					concurso.setEstimativaPremio(decimalFormat.parse(list.get(19)).doubleValue());
					concurso.setAcumuladoMegaDaVirada(decimalFormat.parse(list.get(20)).doubleValue());
					
					resultList.add(concurso);
				} else {
						
//					log.info(String.format( "%s", concurso!=null? concurso.getNumero(): 0));
//					log.info("cidade? "+list);
					
					switch (list.size()) {
					case 1:
						concurso.addEstado(list.get(0));
						break;
					case 2:
						concurso.addCidade(list.get(0));
						concurso.addEstado(list.get(1));
						break;
					default:
						break;
					}
					
				}
			}
			
			
		}
		
		return resultList;
		
	}

}
