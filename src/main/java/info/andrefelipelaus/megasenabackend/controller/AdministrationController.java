package info.andrefelipelaus.megasenabackend.controller;

import java.net.URI;

import javax.transaction.Transactional;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import info.andrefelipelaus.megasenabackend.controller.dto.LoadDataStatusDto;
import info.andrefelipelaus.megasenabackend.controller.service.LoadFileServices;

/**
 * 
 * @author André Felipe Laus
 * 
 * Controller responsável pelos endpoints de administração
 *
 */

@RestController
@RequestMapping("/admin")
public class AdministrationController {
	
	@Autowired
	private LoadFileServices loadFileServices;

	@GetMapping("/loadData")
	@Transactional
	public Response loadResults() {
		URI uri = URI.create("/admin/verifyLoadData");
		loadFileServices.loadDataFromLoterias();
		return Response.created(uri).build();
	}
	
	@GetMapping("/verifyLoadData")
	public LoadDataStatusDto virifyLoadData() {
		return loadFileServices.getLoadStatus();
	}
	
}
