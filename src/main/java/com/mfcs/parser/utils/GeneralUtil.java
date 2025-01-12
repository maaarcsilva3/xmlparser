package com.mfcs.parser.utils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import com.mfcs.parser.config.Config;

/**
 * This utility class provides methods for working with files, specifically 
 * for retrieving config value extension files from a specified directory. 
 */

public class GeneralUtil {

	
	public static void logInfo(String message) throws IOException {
		String level = "INFO";
		String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd_HH"));
        String logFilePath = Config.LOGS_LOCATION.getValue() + File.separator + timestamp + "_logs.txt";
		
        logMessage(level,logFilePath, message );
		
	}
	public static void logError(String message) throws IOException {
		String level = "ERROR";
		String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd_HH"));
        String logFilePath = Config.LOGS_LOCATION.getValue() + File.separator + timestamp + "_logs.txt";
		
        logMessage(level,logFilePath, message );
	}
	
	
	
	public static void logMessage(String level, String filePath, String message) throws IOException {
		String timestamp  = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss"));
		StringBuilder log = new StringBuilder();
		log.append(timestamp).append(" [" + level +"]").append(" | ").append(message);
		
        FileWriter fileWriter = new FileWriter(filePath, true); //append to next line
        PrintWriter printWriter = new PrintWriter(fileWriter);
        printWriter.println(log.toString()); 
        System.out.println(log.toString());
        printWriter.close(); 
    }
	
	public static ArrayList<File> getFiles() {
		File folder = new File(Config.XML_FILES_LOCATION.getValue());

		ArrayList<File> xmlFiles = new ArrayList<File>();

		findFiles(folder, Config.FILE_METADATA_EXTENSION.getValue(), xmlFiles);

		return xmlFiles;
	} 

	private static void findFiles(File folder, String xmlExtension, ArrayList<File> xmlFiles) {
		File[] files = folder.listFiles();
		if (files != null) {
			for (File file : files) {
				if (file.isDirectory()) { // Recursively search in subdirectory
					findFiles(file, xmlExtension, xmlFiles);
				} else if (file.getAbsolutePath().toLowerCase().endsWith(xmlExtension.toLowerCase())) {
					xmlFiles.add(file);
				}
			}
		}
	}
	
	public static String filenameParser(String filenameWithExtension) {
		String filename = filenameWithExtension.substring(0, filenameWithExtension.indexOf("."));
		
		return filename;
		
	}
	
	public static void directoryChecker(Path directory) throws IOException {
		  if (Files.notExists(directory)) {
              Files.createDirectories(directory); 
              
              System.out.println("Directory created: " + directory);
              
          }
	}
}
