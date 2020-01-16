package info.andrefelipelaus.megasenabackend.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * 
 * @author André Felipe Laus
 *
 * Transformação das properties para objeto
 *
 */
@ConfigurationProperties(prefix = "megasena.config")
@Configuration("megasenaProperties")
@Component
public class MegasenaConfigProperties {

	private String linkDownload;
	private String zipFileName;
	private String workFolder;

	/**
	 * 
	 * @return link para download dos resultados
	 */
	public String getLinkDownload() {
		return linkDownload;
	}

	public void setLinkDownload(String linkDownload) {
		this.linkDownload = linkDownload;
	}

	/**
	 * 
	 * @return nome dado ao arquivo zip que será salvo no disco
	 */
	public String getZipFileName() {
		return zipFileName;
	}

	public void setZipFileName(String zipFileName) {
		this.zipFileName = zipFileName;
	}
	
	/**
	 * 
	 * @return pasta destinada 
	 */
	public String getWorkFolder() {
		
		return workFolder;
	}

	public void setWorkFolder(String workFolder) {
		this.workFolder = workFolder;
	}

}
