package info.andrefelipelaus.megasenabackend.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @author André Felipe Laus
 *
 * Classe de manipulação de arquivos
 *
 */
@Slf4j
public class FileUtils {


	/**
	 * Baixa um arquivo da URL e salva em uma pasta determinada
	 *  
	 * @param fromFileUrl URL de onde o arquivo será baixado
	 * @param outputFolder pasta onde o arquivo será salvo
	 * @param toFile nome do arquivo que será salvo
	 * 
	 * @throws IOException
	 */
	public static void download(String fromFileUrl, String outputFolder, String toFile) throws IOException {

		log.info(String.format("OutputFolder %s", outputFolder));

		CookieHandler.setDefault(new CookieManager(null, CookiePolicy.ACCEPT_ALL));
		
		if (outputFolder != null && !outputFolder.isEmpty()) {
			Path path = Paths.get(outputFolder);
			if (!Files.exists(path)) {
				Files.createDirectories(path);
			}
			toFile = outputFolder + File.separator + toFile;
		}
		
		File newFile = new File(toFile);
		log.info(String.format("Generate File %s", toFile));
		URL website = new URL(fromFileUrl);
		ReadableByteChannel rbc = Channels.newChannel(website.openStream());
		FileOutputStream fos = new FileOutputStream(newFile);
		
		log.info(String.format("Download file %s", fromFileUrl));
		
		fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
		fos.close();
		rbc.close();

	}

	
	/**
	 * 
	 * Descompacta os arquivos de um arquivo zip em uma dada pasta
	 * 
	 * @param zipFile nome do arquivo compactado
	 * @param inputFolder caminho onde se encontra o arquivo compactado 
	 * @param outputFolder pasta onde serão salvos os arquivos
	 * 
	 * @return Lista de arquivos que foram extraídos
	 * 
	 * @throws IOException
	 */
	public static List<File> unZipFile(String zipFile, String inputFolder, String outputFolder) throws IOException {
		byte[] buffer = new byte[1024];

		List<File> filesList = new ArrayList<>();

		log.info(String.format("Unzip it %s", zipFile));

		// create output directory is not exists
		Path path = Paths.get(outputFolder);
		if (!Files.exists(path)) {
			Files.createDirectories(path);
		}

		if (inputFolder != null && !inputFolder.isEmpty()) {
			zipFile = inputFolder + File.separator + zipFile;
		}
		
		// get the zip file content
		ZipInputStream zis = new ZipInputStream(new FileInputStream(zipFile));
		// get the zipped file list entry
		ZipEntry ze = zis.getNextEntry();

		while (ze != null) {

			String fileName = ze.getName();
			File newFile = new File(outputFolder + File.separator + fileName);
			log.info(String.format("File unzip: %s", newFile.getAbsoluteFile()));

			// create all non exists folders
			// else you will hit FileNotFoundException for compressed folder
			new File(newFile.getParent()).mkdirs();

			FileOutputStream fos = new FileOutputStream(newFile);

			int len;
			while ((len = zis.read(buffer)) > 0) {
				fos.write(buffer, 0, len);
			}

			filesList.add(newFile);
			fos.close();
			ze = zis.getNextEntry();
		}

		zis.closeEntry();
		zis.close();

		log.info(String.format("Done! Files in: %s", outputFolder));

		return filesList;
	}

}
