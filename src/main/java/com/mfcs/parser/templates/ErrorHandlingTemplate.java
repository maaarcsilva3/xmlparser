package com.mfcs.parser.templates;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import com.mfcs.parser.utils.GeneralUtil;

public abstract class ErrorHandlingTemplate {

	public final void errorHandling(File xmlFile, Map<String, Object> mappedXml) throws IOException {

		// 1. move xml to failed
		GeneralUtil.logInfo("Moving XML to Failed");
		moveXmlToFailed(xmlFile);

		if (mappedXml != null) {
			// 2. check if image available
			String filePath = imageFinder(mappedXml);

			if (filePath == null) {
				// 3. if image is available, move image to failed
				GeneralUtil.logInfo("Moving File to Failed");
				moveFileToFailed(filePath);

			}
		}

	}

	protected abstract Map<String, Object> parseXML(File xmlFile);

	protected abstract void moveXmlToFailed(File file) throws IOException;

	protected abstract String imageFinder(Map<String, Object> xmlValues) throws IOException;

	protected abstract void moveFileToFailed(String fillePath) throws IOException;
}
