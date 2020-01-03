package info.andrefelipelaus.megasenabackend.controller.service;

import java.io.File;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import info.andrefelipelaus.megasenabackend.controller.dto.LoadDataStatusDto;
import info.andrefelipelaus.megasenabackend.controller.dto.Status;
import info.andrefelipelaus.megasenabackend.io.ConverterFileToList;
import info.andrefelipelaus.megasenabackend.io.FileUtils;
import info.andrefelipelaus.megasenabackend.model.Concurso;
import info.andrefelipelaus.megasenabackend.model.builder.ConcursoBuilder;
import info.andrefelipelaus.megasenabackend.model.repository.ConcursoRepository;
import info.andrefelipelaus.megasenabackend.properties.MegasenaConfigProperties;
import lombok.extern.slf4j.Slf4j;


/**
 * 
 * @author André Felipe Laus
 * 
 * Componente responsável pela carga das informações
 *
 */
@Slf4j
@Component
public class LoadFileServices {

	@Autowired
	private MegasenaConfigProperties megasenaProperties;
	
	@Autowired
	private ConcursoRepository concursoRepository;
	
	private static LoadDataStatusDto loadStatus;

	private LoadFileServices() {}
	
	private static class LoadFileServicesHolder {
		private static final LoadFileServices INSTANCE = new LoadFileServices();
	}
	
	public static LoadFileServices getInstance() {
		return LoadFileServicesHolder.INSTANCE;
	}
	
	@Async
	@Transactional
	public CompletableFuture<LoadDataStatusDto> loadDataFromLoterias() {
		loadStatus = new LoadDataStatusDto(Status.RUNNIG, 
										   Status.PENDING,  
										   Status.PENDING, 
										   Status.PENDING, 
										   Status.PENDING);
		
		log.info(String.format("Download file %s", megasenaProperties.getLinkDownload()));
		
		try {
			FileUtils.download(megasenaProperties.getLinkDownload(), 
								megasenaProperties.getWorkFolder(),
								megasenaProperties.getZipFileName());
			loadStatus.setDownload(Status.OK);
		} catch (Exception e) {
			loadStatus.setDownload(Status.FAILURE);
			log.error("Error in download file from Loterica", e);
			return CompletableFuture.completedFuture(loadStatus);
		}
		
		
		List<File> filesList;
		try {
			loadStatus.setUnzip(Status.RUNNIG);
			filesList = FileUtils.unZipFile(megasenaProperties.getZipFileName(),
											megasenaProperties.getWorkFolder(),
											megasenaProperties.getWorkFolder());
			
			loadStatus.setUnzip(Status.OK);
		} catch (Exception e) {
			loadStatus.setUnzip(Status.FAILURE);
			log.error("Error in unzip file", e);
			return CompletableFuture.completedFuture(loadStatus);
		}

		
		List<List<String>> listValues = null;
		try {
			loadStatus.setConvertHtmlToList(Status.RUNNIG);
			log.info("Nome dos arquivos devolvidos");
	        for (File file : filesList) {
	        	if (file.getName().endsWith(".htm")) {
	        		log.info(file.getPath());
	        		log.info("converte");
	        		ConverterFileToList convert = new ConverterFileToList(file);
	        		convert.convertTrAndTdInList();
	        		listValues = convert.getListValues();
				}
			}

			loadStatus.setConvertHtmlToList(Status.OK);
		} catch (Exception e) {
			loadStatus.setConvertHtmlToList(Status.FAILURE);
			log.error("Error in convert html in list of value", e);
			return CompletableFuture.completedFuture(loadStatus);
		}
		
		List<Concurso> listFromHtmlToListObject;
		
        try {
        	loadStatus.setConvertListToObject(Status.RUNNIG);
        	
			listFromHtmlToListObject = ConcursoBuilder.listFromHtmlToListObject(listValues);

			loadStatus.setConvertListToObject(Status.OK);
		} catch (Exception e) {
			loadStatus.setConvertListToObject(Status.FAILURE);
			log.error("Error in convert list of value in object", e);
			return CompletableFuture.completedFuture(loadStatus);
		}
        
        
        try {
        	loadStatus.setSaveInDatabase(Status.RUNNIG);
        	
        	concursoRepository.saveAll(listFromHtmlToListObject);
        	
        	loadStatus.setSaveInDatabase(Status.OK);
        } catch (Exception e) {
        	loadStatus.setSaveInDatabase(Status.FAILURE);
        	log.error("Error in save objects in database", e);
        	return CompletableFuture.completedFuture(loadStatus);
        }
        
		
        
        
		return CompletableFuture.completedFuture(loadStatus);
	}
	
	public LoadDataStatusDto getLoadStatus() {
		if (loadStatus == null) {
			loadStatus = new LoadDataStatusDto(Status.NOT_APPLICABLE, 
											   Status.NOT_APPLICABLE,  
											   Status.NOT_APPLICABLE, 
											   Status.NOT_APPLICABLE, 
											   Status.NOT_APPLICABLE);
		}
		return loadStatus;
	}
	
}
