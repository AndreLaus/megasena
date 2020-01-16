package info.andrefelipelaus.megasenabackend.controller;

import java.net.URI;

import javax.transaction.Transactional;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import info.andrefelipelaus.megasenabackend.controller.dto.SituacaoCargaDadosDto;
import info.andrefelipelaus.megasenabackend.controller.service.CarregaArquivoServices;

/**
 * 
 * @author André Felipe Laus
 * 
 * Controller responsável pelos endpoints de administração
 *
 */

@RestController
@RequestMapping("/admin")
public class AdministracaoController {
	
	@Autowired
	private CarregaArquivoServices carregaArquivoServices;

	@GetMapping("/carregaDados")
	@Transactional
	public Response carregaResultados() {
		URI uri = URI.create("/admin/verificaSituacaoCarga");
		carregaArquivoServices.carregaDadosLoterias();
		return Response.created(uri).build();
	}
	
	@GetMapping("/verificaSituacaoCarga")
	public SituacaoCargaDadosDto verificaSituacaoCargaDados() {
		return carregaArquivoServices.getSituacaoCarga();
	}
	
}
