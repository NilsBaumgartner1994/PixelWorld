package com.gentlemenssoftware.easyServer;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.json.GenericJson;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.sheets.v4.SheetsScopes;
import com.google.api.services.sheets.v4.model.*;

import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.Sheets.Spreadsheets.BatchUpdate;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map.Entry;
import java.util.Random;

public class EasyGoogleSheetsHandler {
	/** Application name. */
	private static final String APPLICATION_NAME = "Google Sheets API Java Quickstart";

	/** Directory to store user credentials for this application. */
	private final java.io.File DATA_STORE_DIR = new java.io.File(System.getProperty("user.home"),
			".credentials/sheets.googleapis.com-java-quickstart");

	/** Global instance of the {@link FileDataStoreFactory}. */
	private FileDataStoreFactory DATA_STORE_FACTORY;

	/** Global instance of the JSON factory. */
	private final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();

	String spreadSheetId = "1SkstzWkc2wZ-1iT0hSsgB2ADbxaWED1PRgCZBA8HF6U";

	/** Global instance of the HTTP transport. */
	private HttpTransport HTTP_TRANSPORT;

	/**
	 * Global instance of the scopes required by this quickstart.
	 *
	 * If modifying these scopes, delete your previously saved credentials at
	 * ~/.credentials/sheets.googleapis.com-java-quickstart
	 */
	private final List<String> SCOPES = Arrays.asList(SheetsScopes.SPREADSHEETS);

	public static int maxRetriesForConnectionReset = 10;
	
	private static int beginRows = 6;
	private static int endRows = 10;

	private boolean validSetup = false;

	private String clientID;

	public EasyGoogleSheetsHandler(String spreadSheetId) {
		this.spreadSheetId = spreadSheetId;

		System.out.println("EasyGoogleSheetsHandler setup statics");
		try {
			HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
			DATA_STORE_FACTORY = new FileDataStoreFactory(DATA_STORE_DIR);
		} catch (Throwable t) {
			t.printStackTrace();
			System.exit(1);
		}

		System.out.println("EasyGoogleSheetsHandler authorize API client service");

		// Build a new authorized API client service.
		int retries = maxRetriesForConnectionReset;
		while (retries > 0) {
			try {
				System.out.println("EasyGoogleSheetsHandler getting SheetsService");
				service = getSheetsService();
				retries = 0;
				System.out.println("EasyGoogleSheetsHandler success");
				validSetup = true;
			} catch (SocketException | SocketTimeoutException e) {
				String name = new Object() {
				}.getClass().getEnclosingMethod().getName();
				System.out.println(name + " | ConnectionResets: trying max " + retries + " other times!");
				retries--;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				retries = 0;
				validSetup = false;
			}
		}

		System.out.println("EasyGoogleSheetsHandler: finisched setup");
	}

	public boolean validSetup() {
		return validSetup;
	}

	/**
	 * Creates an authorized Credential object.
	 * 
	 * @return an authorized Credential object.
	 * @throws IOException
	 */
	public Credential authorize() throws IOException {
		// Load client secrets.
		InputStream in = EasyGoogleSheetsHandler.class.getResourceAsStream("/client_secret.json");
		GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));

		// Build flow and trigger user authorization request.
		GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(HTTP_TRANSPORT, JSON_FACTORY,
				clientSecrets, SCOPES).setDataStoreFactory(DATA_STORE_FACTORY).setAccessType("offline").build();

		clientID = flow.getClientId();

		Credential credential = new AuthorizationCodeInstalledApp(flow, new LocalServerReceiver()).authorize("user");
		System.out.println("Credentials saved to " + DATA_STORE_DIR.getAbsolutePath());

		return credential;
	}

	static Sheets service = null;

	/**
	 * Build and return an authorized Sheets API client service.
	 * 
	 * @return an authorized Sheets API client service
	 * @throws IOException
	 */
	public Sheets getSheetsService() throws IOException {
		if (service == null) {
			System.out.println("EasyGoogleSheetsHandler getService authorize");
			Credential credential = authorize();
			System.out.println("EasyGoogleSheetsHandler credentials authorized, Setting up Service");
			service = new Sheets.Builder(HTTP_TRANSPORT, JSON_FACTORY, credential).setApplicationName(APPLICATION_NAME)
					.build();
		}

		return service;
	}

	private static List<String> allRows;

	static {
		allRows = new ArrayList<String>();
		for (int i = beginRows; i <= endRows; i++) {
			allRows.add("" + i);
		}
	}

	public List<String> getFreeRows() {
		List<String> freeRows = new ArrayList<String>(allRows);
		List<String> occupiedRows = getOccupiedRows();
		freeRows.removeAll(occupiedRows);
		return freeRows;
	}

	public String getFreeRow() {
		List<String> freeRows = getFreeRows();
		if (freeRows.isEmpty())
			return null;
		return freeRows.get((new Random()).nextInt(freeRows.size()));
	}

	public static String delimeterOccupiedRows = " ";

	public List<String> getOccupiedRows() {
		String occupiedRowsRaw = this.readCell("A4");
		List<String> occupiedRows = Arrays.asList(occupiedRowsRaw.split(delimeterOccupiedRows));
		return occupiedRows;
	}

	public EasyGoogleUpdateAction addInRandomRowData(String... datas) {
		String row = getFreeRow();
		if (row == null)
			return null;
		BatchUpdateValuesResponse response = writeIntoRow("B" + row, datas);
		if (response == null)
			return null;
		return new EasyGoogleUpdateAction("B", row, response);
	}
	/**
	public EasyUpdateAction append(String... dataArr) {
		String range = "B6"; // TODO: Update placeholder value.

		// How the input data should be interpreted.
		String valueInputOption = "RAW"; // TODO: Update placeholder value.

		// How the input data should be inserted.
		String insertDataOption = "INSERT_ROWS"; // TODO: Update placeholder value.

		// TODO: Assign values to desired fields of `requestBody`:
		ValueRange requestBody = new ValueRange();
		
		List<Object> data1 = new ArrayList<Object>();
		data1.addAll(Arrays.asList(dataArr));

		List<List<Object>> data2 = new ArrayList<List<Object>>();
		data2.add(data1);
        
        requestBody.setValues(data2);

		Sheets sheetsService;
		try {
			sheetsService = getSheetsService();
			Sheets.Spreadsheets.Values.Append request = sheetsService.spreadsheets().values().append(spreadSheetId,
					range, requestBody);

			request.setValueInputOption(valueInputOption);
			request.setInsertDataOption(insertDataOption);

			AppendValuesResponse response = request.execute();

			// TODO: Change code below to process the `response` object:
			
			Logger.println(response.getTableRange());
			
			String startCell = response.getTableRange().split(":")[1];
			String colString = startCell.replaceAll("\\d", "");
			String row = startCell.replaceAll(colString, "");
			
			deleteRow(Integer.parseInt(row)+1,0);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	*/
	
	/**
	public void deleteRow(int row, int sheetID) {
		Logger.println("ID "+sheetID);
		BatchUpdateSpreadsheetRequest content = new BatchUpdateSpreadsheetRequest();
        Request request = new Request();
        DeleteDimensionRequest deleteDimensionRequest = new DeleteDimensionRequest();
        DimensionRange dimensionRange = new DimensionRange();
        dimensionRange.setDimension("ROWS");
        dimensionRange.setStartIndex(row);
        dimensionRange.setEndIndex(row+1);

        
        deleteDimensionRequest.setRange(dimensionRange);

        request.setDeleteDimension(deleteDimensionRequest);

        List<Request> requests = new ArrayList<Request>();
        requests.add(request);
        content.setRequests(requests);

        try {
            service.spreadsheets().batchUpdate(spreadSheetId, content).execute();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            dimensionRange = null;
            deleteDimensionRequest = null;
            request = null;
            requests = null;
            content = null;
        }
	}
	*/

	public static String getCellFromOffset(String startCell, int colInt) {
		String colString = startCell.replaceAll("\\d", "");
		String row = startCell.replaceAll(colString, "");

		return columnToLetter(letterToColumn(colString) + colInt) + row;
	}

	public static String columnToLetter(int column) {
		String letter = "";
		int temp = 0;
		while (column > 0) {
			temp = (column - 1) % 26;
			letter = Character.toChars(temp + 65)[0] + letter;
			column = (column - temp - 1) / 26;
		}
		return letter;
	}

	public static int letterToColumn(String letter) {
		int column = 0;
		int length = letter.length();
		for (int i = 0; i < length; i++) {
			column += (letter.charAt(i) - 64) * Math.pow(26, length - i - 1);
		}
		return column;
	}

	public String readCell(String cell) {
		List<List<Object>> values = readCells(cell);

		if (values == null) {
			return "";
		}
		if (values.isEmpty()) {
			return "";
		}
		if (values.get(0).isEmpty()) {
			return "";
		}

		return values.get(0).get(0).toString();
	}

	public List<List<Object>> readCells(String range) {

		ValueRange response = null;
		List<List<Object>> values = null;
		int retries = maxRetriesForConnectionReset;
		while (retries > 0) {
			try {
				response = getSheetsService().spreadsheets().values().get(spreadSheetId, range).execute();
				values = response.getValues();
				if (values == null || values.size() == 0) {
					return null;
				} else {
					return values;
				}
			} catch (SocketException | SocketTimeoutException e) {
				String name = new Object() {
				}.getClass().getEnclosingMethod().getName();
				System.out.println(name + " | ConnectionResets: trying max " + retries + " other times!");
				retries--;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;
			}
		}

		return null;
	}

	public BatchUpdateValuesResponse writeIntoCell(String cell, String data) {
		List<Object> data1 = new ArrayList<Object>();
		data1.add(data);

		List<List<Object>> data2 = new ArrayList<List<Object>>();
		data2.add(data1);

		return writeIntoCells(cell, data2);
	}

	public BatchUpdateValuesResponse writeIntoRow(String startCell, String... dataArr) {
		int cellsEffected = dataArr.length;

		String endCell = getCellFromOffset(startCell, cellsEffected);

		List<Object> data1 = new ArrayList<Object>();
		data1.addAll(Arrays.asList(dataArr));

		List<List<Object>> data2 = new ArrayList<List<Object>>();
		data2.add(data1);

		return writeIntoCells(getRange(startCell, endCell), data2);
	}

	private String getRange(String start, String end) {
		return start + ":" + end;
	}

	public BatchUpdateValuesResponse writeIntoCells(String range, List<List<Object>> arrData) {
		ValueRange oRange = new ValueRange();
		oRange.setRange(range); // I NEED THE NUMBER OF THE LAST ROW
		oRange.setValues(arrData);

		List<ValueRange> oList = new ArrayList<>();
		oList.add(oRange);

		BatchUpdateValuesRequest oRequest = new BatchUpdateValuesRequest();
		// USER_ENTERED or RAW
		oRequest.setValueInputOption("USER_ENTERED");
		oRequest.setData(oList);

		int retries = maxRetriesForConnectionReset;
		while (retries > 0) {
			try {
				BatchUpdateValuesResponse oResp1 = getSheetsService().spreadsheets().values()
						.batchUpdate(spreadSheetId, oRequest).execute();
				return oResp1;
			} catch (SocketException e) {
				String name = new Object() {
				}.getClass().getEnclosingMethod().getName();
				System.out.println(name + " | ConnectionResets: trying max " + retries + " other times!");
				retries--;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;
			}
		}

		// incase something realy mess up
		return null;
	}

}