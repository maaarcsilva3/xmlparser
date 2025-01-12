package com.mfcs.parser.implementations;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mfcs.parser.config.Config;
import com.mfcs.parser.exceptions.JsonException;
import com.mfcs.parser.templates.FileProcessorTemplate;
import com.mfcs.parser.utils.GeneralUtil;

public class XmlProcessor extends FileProcessorTemplate {

	@Override
	protected Map<String, Object> parseXML(File xmlFile) {
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document document = builder.parse(xmlFile);

			// Normalize the XML structure
			document.getDocumentElement().normalize();

			// Create a map to store XML data
			Map<String, Object> xmlDataMap = new HashMap<>();

			// Convert XML to Map directly
			NodeList childNodes = document.getDocumentElement().getChildNodes();
			for (int i = 0; i < childNodes.getLength(); i++) {
				Node child = childNodes.item(i);

				if (child.getNodeType() == Node.ELEMENT_NODE) {
					Element element = (Element) child;

					if (element.getChildNodes().getLength() == 1
							&& element.getFirstChild().getNodeType() == Node.TEXT_NODE) {
						// Add leaf node (key-value pair)
						xmlDataMap.put(element.getTagName(), element.getTextContent());
					} else {
						// Handle nested elements
						Map<String, Object> nestedMap = new HashMap<>();
						NodeList nestedNodes = element.getChildNodes();
						for (int j = 0; j < nestedNodes.getLength(); j++) {
							Node nestedChild = nestedNodes.item(j);

							if (nestedChild.getNodeType() == Node.ELEMENT_NODE) {
								Element nestedElement = (Element) nestedChild;

								if (nestedElement.getChildNodes().getLength() == 1
										&& nestedElement.getFirstChild().getNodeType() == Node.TEXT_NODE) {
									nestedMap.put(nestedElement.getTagName(), nestedElement.getTextContent());
								}
							}
						}
						xmlDataMap.put(element.getTagName(), nestedMap);
					}
				}
			}

			return xmlDataMap;

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	protected String imageFinder(Map<String, Object> xmlValues) throws IOException {
		String filename = null;

		for (Map.Entry<String, Object> val : xmlValues.entrySet()) {
			String key = val.getKey();
			String value = val.getValue().toString();

			if (key.equalsIgnoreCase("Filename")) {
				filename = value;
				break;
			}
		}

		File[] fileList = new File(Config.XML_FILES_LOCATION.getValue()).listFiles();
		if (fileList != null) {
			Stack<File> stack = new Stack<>();
			stack.addAll(Arrays.asList(fileList));

			while (!stack.isEmpty()) {
				File file = stack.pop();
				if (file.isDirectory()) {
					// Add all files in the subdirectory to the stack
					File[] subFiles = file.listFiles();
					if (subFiles != null) {
						stack.addAll(Arrays.asList(subFiles));
					}
				} else if (file.getName().equalsIgnoreCase(filename)) {
					// File found
					GeneralUtil.logInfo("File found at: " + file.getAbsolutePath());
					return file.getAbsolutePath(); // Exit the loop if only one occurrence is needed
				}
			}
		}
		return null;

	}

	@Override
	protected String convertJson(Map<String, Object> xmlValues) throws JsonException, IOException {
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		
		String filenameVal = null;
		try {
			filenameVal = xmlValues.get("FileName").toString();
		} catch (Exception e) {
			throw new JsonException("FileName value was not found");
		}

		String fileName = GeneralUtil.filenameParser(filenameVal);
		
		Path filePath = Paths.get(Config.JSON_OUTPUT_LOCATION.getValue(), fileName + ".json");

		GeneralUtil.directoryChecker(filePath.getParent());
		try (FileWriter writer = new FileWriter(filePath.toString())) {
			gson.toJson(xmlValues, writer);
			GeneralUtil.logInfo("JSON file saved at: " + filePath.toString());
		//	System.out.println("JSON file saved at: " + filePath.toString());
		} catch (IOException e) {
			System.err.println("Error writing JSON to file: " + e.getMessage());
		}
		return filePath.toString();

	}

	
	@Override
	protected void moveXmlToArchive(File file) throws IOException {
		Path sourceFile = Paths.get(file.getAbsolutePath());
		Path targetDirectory = Paths.get(Config.ARCHIVE_LOCATION.getValue()+File.separator+file.getName());
		
		GeneralUtil.directoryChecker(Paths.get(Config.ARCHIVE_LOCATION.getValue()));
		
		Files.move(sourceFile, targetDirectory, StandardCopyOption.REPLACE_EXISTING);
		
		GeneralUtil.logInfo("Success moving " + file.getName() + " to "+targetDirectory);
		
		
		
		
	}

	@Override
	protected void moveFileToArchive(String filePath) throws IOException {
		Path path = Paths.get(filePath);
		Path sourceFile = Paths.get(filePath);
		Path targetDirectory = Paths.get(Config.ARCHIVE_LOCATION.getValue()+File.separator + path.getFileName());
		
		GeneralUtil.directoryChecker(Paths.get(Config.ARCHIVE_LOCATION.getValue()));
		
		Files.move(sourceFile, targetDirectory, StandardCopyOption.REPLACE_EXISTING);
		
		
		GeneralUtil.logInfo("Success moving " + path.getFileName() + " to "+targetDirectory);
		
	}

}
