package info.andrefelipelaus.megasenabackend.io;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @author André Felipe Laus
 *
 * Converte um arquivo html em String
 *
 */

public class ConverterFileToList {

	private static final Logger logger = LoggerFactory.getLogger(ConverterFileToList.class);
	
	private File fileHtml;
	private List<List<String>> listValues = new ArrayList<List<String>>();
	
	public ConverterFileToList(String fileName) {
		
		if (fileName == null) {
			throw new IllegalArgumentException("File name can't null");
		}
		
		this.fileHtml= new File(fileName);
	}
	
	public ConverterFileToList(File file) {
		this.fileHtml = file;
	}
	
	
	public void convertTrAndTdInList() {
		
		logger.info("Converter started");
		
		try {
			Document doc = Jsoup.parse(this.fileHtml, "ISO-8859-1");
			
			Elements lines = doc.getElementsByTag("tr");
			
			ArrayList<String> listColumns;
			
			//logger.info("Reader line");
			for (Element line : lines) {
				Elements columns = line.getElementsByTag("td");
				listColumns = new ArrayList<String>();
				for (Element column : columns) {
					listColumns.add(column.text());
				}
				listValues.add(listColumns);
			}
			
			logger.info("size: "+listValues.size());
			
		} catch (IOException e) {
			logger.error("Error in converter html in object", e);
			e.printStackTrace();
		}
		
	}
	
	public List<List<String>> getListValues() {
		return listValues;
	}
	
}
