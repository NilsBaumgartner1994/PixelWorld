package com.gentlemenssoftware.easyServer;

import com.google.api.services.sheets.v4.model.BatchUpdateValuesResponse;

public class EasyGoogleUpdateAction {

	private String column;
	private String row;
	private BatchUpdateValuesResponse response;

	public EasyGoogleUpdateAction(String column, String row, BatchUpdateValuesResponse response) {
		this.column = column;
		this.row = row;
		this.response = response;
	}

	public String getColumn() {
		return this.column;
	}

	public String getRow() {
		return this.row;
	}

	public BatchUpdateValuesResponse getResponse() {
		return this.response;
	}

}