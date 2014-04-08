package edu.harvard.analysis;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import cscie99.team2.lingolearn.shared.error.ConfuserException;

/**
 * This file is used to perform some basis analysis on the confuser algorithm
 * to determine it's effectiveness.
 */
public class ConfuserAnalysis {
	// The path to the directory of confusers
	private final static String JLPT_DIRECTORY = "/jlpt/";
	
	// The extension for the files
	private final static String JLPT_EXTENSION = ".jlpt";
	
	// The JLPT level we are working with
	private final static String JLPT_LEVEL = "5";

	/**
	 * 
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String [] args) throws IOException {
		// Open the resource file
		String path = JLPT_DIRECTORY + JLPT_LEVEL + JLPT_EXTENSION;
		InputStream jlpt = ConfuserAnalysis.class.getResourceAsStream(path);
		// Prepare the stream reader
		BufferedReader reader = null;
		InputStreamReader stream = null;
		try {
			// Open up the stream to be read
			stream = new InputStreamReader(jlpt);
			reader = new BufferedReader(stream);
			// Read and process the contents
			List<String> results = new ArrayList<String>();
			String data;
			while ((data = reader.readLine()) != null) {
				System.out.println(data);
			}
		} catch (FileNotFoundException ex) {
			System.out.println("Unable to open the JLPT level " + JLPT_LEVEL);
		} catch (IOException ex) {
			System.out.println("An error occured while reading the next line");
			System.out.println(ex.toString());
		} finally {
			if (reader != null) {
				reader.close();
			}
			if (stream != null) {
				stream.close();
			}
		}
	}
}
