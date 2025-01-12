package com.mfcs.parser.interfaces;

import com.mfcs.parser.exceptions.SqlException;
import com.mfcs.parser.objects.RecordData;

public interface RecordDao {

	void checkConnection() throws SqlException;

	void insertRecord(RecordData recod) throws Exception;
}
