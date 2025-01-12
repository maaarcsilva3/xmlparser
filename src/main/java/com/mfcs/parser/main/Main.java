package com.mfcs.parser.main;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.mfcs.parser.config.Config;
import com.mfcs.parser.exceptions.SqlException;
import com.mfcs.parser.implementations.ErrorHandling;
import com.mfcs.parser.implementations.XmlProcessor;
import com.mfcs.parser.templates.ErrorHandlingTemplate;
import com.mfcs.parser.templates.FileProcessorTemplate;
import com.mfcs.parser.utils.GeneralUtil;

public class Main {

	public static void main(String[] args) throws IOException, SqlException {
		Config.initialize();
		ArrayList<File> inputFiles = GeneralUtil.getFiles();
		ExecutorService executorService = Executors.newFixedThreadPool(3);
		if (inputFiles.size() == 0) {
			System.out.println(
					"No files with extension " + Config.FILE_METADATA_EXTENSION.getValue() + " was retrieved.");
		} else {
			GeneralUtil.logInfo("Started processing Files");

			long startTime = System.currentTimeMillis(); // Capture the start time

			for (File file : inputFiles) {
				executorService.submit(() -> {
					try {
						start(file);
					} catch (IOException e) {
						e.printStackTrace();
					}
				});
			}
			// Shut down the executor service and wait for tasks to complete
			executorService.shutdown();
			try {
				if (!executorService.awaitTermination(60, java.util.concurrent.TimeUnit.SECONDS)) {
					executorService.shutdownNow();
				}
			} catch (InterruptedException e) {
				executorService.shutdownNow();
				Thread.currentThread().interrupt();
			}
			GeneralUtil.logInfo("Done processing files");
			long endTime = System.currentTimeMillis();
			long totalTime = endTime - startTime;
			GeneralUtil.logInfo("Total time taken for this cycle: " + totalTime + " milliseconds");

		}

	}

	private static void start(File file) throws IOException {
		try {
			FileProcessorTemplate processor = new XmlProcessor();
			GeneralUtil.logInfo("Started processing file " + file.getName());
			processor.processFile(file);
			GeneralUtil.logInfo("Done processing file " + file.getName());
			System.out.println();

		} catch (Exception e) {
			GeneralUtil.logError(e.getMessage());

			Map<String, Object> mappedXml = FileProcessorTemplate.getMappedXml();
			if (mappedXml == null) {
				GeneralUtil.logError("Error encountered while processing XML for file " + file.getName());
			}

			ErrorHandlingTemplate errorHandling = new ErrorHandling();
			errorHandling.errorHandling(file, mappedXml);

		}
	}

}
