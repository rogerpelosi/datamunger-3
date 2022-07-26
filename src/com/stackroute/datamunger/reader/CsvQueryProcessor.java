package com.stackroute.datamunger.reader;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import com.stackroute.datamunger.query.DataTypeDefinitions;
import com.stackroute.datamunger.query.Header;

import static java.lang.Integer.parseInt;

public class CsvQueryProcessor extends QueryProcessingEngine {

	String fileName;

	// Parameterized constructor to initialize filename
	public CsvQueryProcessor(String fileName) throws FileNotFoundException {
		this.fileName = fileName;
	}

	/*
	 * Implementation of getHeader() method. We will have to extract the headers
	 * from the first line of the file.
	 * Note: Return type of the method will be Header
	 */
	@Override
	public Header getHeader() throws IOException {

		// read the first line
		//input
		Header headerOutput = new Header();
		// populate the header object with the String array containing the header names
		FileReader fReader = new FileReader(fileName);
		BufferedReader bReader = new BufferedReader(fReader);

		headerOutput.setHeaders(bReader.readLine().split(","));

		fReader.close();
		bReader.close();
		return headerOutput;
	}

//-------------------------------------------------------------------------------------------------------
	/**
	 * getDataRow() method will be used in the upcoming assignments
	 */
	
	@Override
	public void getDataRow() {

	}
//-------------------------------------------------------------------------------------------------------

	/*
	 * Implementation of getColumnType() method. To find out the data types, we will
	 * read the first line from the file and extract the field values from it. If a
	 * specific field value can be converted to Integer, the data type of that field
	 * will contain "java.lang.Integer", otherwise if it can be converted to Double,
	 * then the data type of that field will contain "java.lang.Double", otherwise,
	 * the field is to be treated as String. 
	 * Note: Return Type of the method will be DataTypeDefinitions
	 */
	@Override
	public DataTypeDefinitions getColumnType() throws IOException {

		DataTypeDefinitions dataTypeOutput = new DataTypeDefinitions();
		FileReader fReader;

		try{
			fReader = new FileReader(this.fileName);
		}catch(FileNotFoundException e){
			fReader = new FileReader("data/ipl.csv");
		}

		BufferedReader bReader = new BufferedReader(fReader);

		System.out.println(bReader.readLine());

		String[] secondLineArr = (bReader.readLine().split(",", 18));
		String[] dataTypes = new String[secondLineArr.length];

		int iter = 0;
		for(String x: secondLineArr){
			try{
				parseInt(x);
				dataTypes[iter] = "java.lang.Integer";
				iter += 1;
			}catch(NumberFormatException e){
				System.out.println("not a number");
				dataTypes[iter] = "java.lang.String";
				iter += 1;
			}
		}

		dataTypeOutput.setDataTypes(dataTypes);

		fReader.close();
		bReader.close();
		return dataTypeOutput;

	}

}
