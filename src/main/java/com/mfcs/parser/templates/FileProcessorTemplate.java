package com.mfcs.parser.templates;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import com.mfcs.parser.exceptions.JsonException;
import com.mfcs.parser.implementations.SqlImplementation;
import com.mfcs.parser.objects.RecordData;
import com.mfcs.parser.utils.GeneralUtil;

public abstract class FileProcessorTemplate {

	private static Map<String, Object> mappedXml;

	public final void processFile(File file) throws Exception {

		// 1 .get xml values assign to map
		GeneralUtil.logInfo("Parsing XML...");
		Map<String, Object> xmlValues = parseXML(file);
		setMappedXml(xmlValues);

		// 2. find the file
		GeneralUtil.logInfo("Finding file...");
		String filePath = imageFinder(xmlValues);

		if (filePath == null) {
			throw new Exception("Image of file " + file.getName() + " was not found");
		}

		// 3. check DB Connection
		GeneralUtil.logInfo("Checking database connection...");
		SqlImplementation sql = new SqlImplementation();
		sql.checkConnection();

		// 4. convert to json file
		GeneralUtil.logInfo("Creating JSON file...");
		String jsonLoc = convertJson(xmlValues);

		// 4. move to archive
		GeneralUtil.logInfo("Moving XML to Archive");
		moveXmlToArchive(file);

		// 5. move file to archive
		GeneralUtil.logInfo("Moving File to Archive");
		moveFileToArchive(filePath);

		// 6. insert to DB
		RecordData recod = new RecordData(file.getName(), filePath, file.getAbsolutePath().toString(), jsonLoc);
		sql.insertRecord(recod);

	}

	protected abstract Map<String, Object> parseXML(File file);

	protected abstract String imageFinder(Map<String, Object> xmlValues) throws IOException;

	protected abstract String convertJson(Map<String, Object> xmlValues) throws JsonException, IOException;

	protected abstract void moveXmlToArchive(File file) throws IOException;

	protected abstract void moveFileToArchive(String filePath) throws IOException;

	public static Map<String, Object> getMappedXml() {
		return mappedXml;
	}

	public void setMappedXml(Map<String, Object> mappedXml) {
		this.mappedXml = mappedXml;
	}

}
