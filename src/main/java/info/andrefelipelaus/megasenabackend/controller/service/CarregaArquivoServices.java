package info.andrefelipelaus.megasenabackend.controller.service;

import java.io.File;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import info.andrefelipelaus.megasenabackend.controller.dto.SituacaoCargaDadosDto;
import info.andrefelipelaus.megasenabackend.controller.dto.Situacao;
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
public class CarregaArquivoServices {

	@Autowired
	private MegasenaConfigProperties megasenaProperties;
	
	@Autowired
	private ConcursoRepository concursoRepository;
	
	private static SituacaoCargaDadosDto situacaoCarga;

	private CarregaArquivoServices() {}
	
	private static class CargaArquivoServicesHolder {
		private static final CarregaArquivoServices INSTANCE = new CarregaArquivoServices();
	}
	
	public static CarregaArquivoServices getInstance() {
		return CargaArquivoServicesHolder.INSTANCE;
	}
	
	@Async
	@Transactional
	public CompletableFuture<SituacaoCargaDadosDto> carregaDadosLoterias() {
		situacaoCarga = new SituacaoCargaDadosDto(Situacao.EXECUTANDO, 
										   Situacao.PENDENTE,  
										   Situacao.PENDENTE, 
										   Situacao.PENDENTE, 
										   Situacao.PENDENTE);
		
		log.info(String.format("Baixando arquivo %s", megasenaProperties.getLinkDownload()));
		
		try {
//			FileUtils.download(megasenaProperties.getLinkDownload(), 
//								megasenaProperties.getWorkFolder(),
//								megasenaProperties.getZipFileName());
			situacaoCarga.setDownload(Situacao.OK);
		} catch (Exception e) {
			situacaoCarga.setDownload(Situacao.FALHA);
			log.error("Erro ao baixar arquivo da Loterica", e);
			return CompletableFuture.completedFuture(situacaoCarga);
		}
		
		
		List<File> filesList;
		try {
			situacaoCarga.setUnzip(Situacao.EXECUTANDO);
			filesList = FileUtils.unZipFile(megasenaProperties.getZipFileName(),
											megasenaProperties.getWorkFolder(),
											megasenaProperties.getWorkFolder());
			
			situacaoCarga.setUnzip(Situacao.OK);
		} catch (Exception e) {
			situacaoCarga.setUnzip(Situacao.FALHA);
			log.error("Erro ao descompactar arquivo", e);
			return CompletableFuture.completedFuture(situacaoCarga);
		}

		
		List<List<String>> listValues = null;
		try {
			situacaoCarga.setConvertHtmlToList(Situacao.EXECUTANDO);
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

			situacaoCarga.setConvertHtmlToList(Situacao.OK);
		} catch (Exception e) {
			situacaoCarga.setConvertHtmlToList(Situacao.FALHA);
			log.error("Erro ao converter html em lista de valores", e);
			return CompletableFuture.completedFuture(situacaoCarga);
		}
		
		List<Concurso> listFromHtmlToListObject;
		
        try {
        	situacaoCarga.setConvertListToObject(Situacao.EXECUTANDO);
        	
			listFromHtmlToListObject = ConcursoBuilder.listFromHtmlToListObject(listValues);

			situacaoCarga.setConvertListToObject(Situacao.OK);
		} catch (Exception e) {
			situacaoCarga.setConvertListToObject(Situacao.FALHA);
			log.error("Erro ao converter a lista de valores em objetos", e);
			return CompletableFuture.completedFuture(situacaoCarga);
		}
        
        
        try {
        	situacaoCarga.setSaveInDatabase(Situacao.EXECUTANDO);
        	
        	concursoRepository.saveAll(listFromHtmlToListObject);
        	
        	situacaoCarga.setSaveInDatabase(Situacao.OK);
        } catch (Exception e) {
        	situacaoCarga.setSaveInDatabase(Situacao.FALHA);
        	log.error("Erro ao savar objetos na base de dados", e);
        	return CompletableFuture.completedFuture(situacaoCarga);
        }
        
		
        
        
		return CompletableFuture.completedFuture(situacaoCarga);
	}
	
	public SituacaoCargaDadosDto getSituacaoCarga() {
		if (situacaoCarga == null) {
			situacaoCarga = new SituacaoCargaDadosDto(Situacao.NAO_SE_APLICA, 
											   Situacao.NAO_SE_APLICA,  
											   Situacao.NAO_SE_APLICA, 
											   Situacao.NAO_SE_APLICA, 
											   Situacao.NAO_SE_APLICA);
		}
		return situacaoCarga;
	}
	
}
