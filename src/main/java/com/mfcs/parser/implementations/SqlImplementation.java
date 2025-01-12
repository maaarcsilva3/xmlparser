package com.mfcs.parser.implementations;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import com.mfcs.parser.config.Config;
import com.mfcs.parser.exceptions.SqlException;
import com.mfcs.parser.interfaces.RecordDao;
import com.mfcs.parser.objects.RecordData;
import com.mfcs.parser.utils.GeneralUtil;

public class SqlImplementation implements RecordDao {

	private static final String DB_URL = "jdbc:mysql://" +Config.DB_IP.getValue() + ":" + Config.DB_PORT.getValue() + "/" + Config.DB_NAME.getValue();
	private static final String DB_USERNAME = Config.DB_USERNAME.getValue();
	private static final String DB_PASSWORD = Config.DB_PASSWORD.getValue();

	private static final String INSERT_SQL = "INSERT INTO records (file_name, image_location, xml_location, json_location) VALUES (?, ?, ?, ?)";

	@Override
	public void insertRecord(RecordData record)
			throws Exception {

		Connection conn = null;

		try {
			conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
			PreparedStatement stmt = conn.prepareStatement(INSERT_SQL);

			stmt.setString(1, record.getFileName());
			stmt.setString(2, record.getImageLocation());
			stmt.setString(3, record.getXmlLocation());
			stmt.setString(4, record.getJsonLocation());
			stmt.execute();

			GeneralUtil.logInfo("Success inserting record");
		} catch (Exception e) {
			throw new SqlException(e.getMessage());

		} finally {
			if (conn != null) {
				conn.close();
				GeneralUtil.logInfo("Connection closed");
			}

		}

	}

	@Override
	public void checkConnection() throws SqlException {
		try (Connection conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD)) {
			if (conn == null) {
				throw new SqlException("Can't connect to SQL Database");
			}
		} catch (Exception e) {
			throw new SqlException("Failed to connect to the database: " + e.getMessage());
		}

	}

}
