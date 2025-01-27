package edu.ncsu.csc216.app_manager.model.io;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

import edu.ncsu.csc216.app_manager.model.application.Application;
/**
 * AppReader class handles the I/O functionality of the Application Manager
 * system
 * @author Emilie Athanasenas
 */
public class AppReader {
	
	/**
	 * Reads in a file and creates an ArrayList of applications
	 * @param fileName name of the file
	 * @return an ArrayList of applications
	 * @throws FileNotFoundException if the file cannot be found
	 * @throws IllegalArgumentException if there are any errors processing the file
	 */
	public static ArrayList<Application> readAppsFromFile(String fileName){
		ArrayList<Application> applications = new ArrayList<Application>();
		StringBuilder fileContent = new StringBuilder();
		
		try(Scanner fileReader = new Scanner(new File(fileName))){
			while(fileReader.hasNextLine()) {
				fileContent.append(fileReader.nextLine()).append("\n");
			}	
		}
		catch(FileNotFoundException e) {
			throw new IllegalArgumentException("Unable to load file.");
		}
		String fileString = fileContent.toString();
		String[] appStrings = fileString.split("\\r?\\n?[*]");
		
		for(String appString: appStrings) {
			if(!appString.isEmpty()) {
				applications.add(processApp(appString));
			}
			
		}
		return applications;
		
	}
	/**
	 * Reads in a String value and processes it as an Application
	 * @param app to be created as an Application object
	 * @return an Application object 
	 * @throws IllegalArgumentException if there are any problems processing the application
	 */
	private static  Application processApp(String app) {
		String[] lines = app.split("\\r?\\n");
		String applicationLine = lines[0];
		String[] parse = applicationLine.split(",");
		
		if(parse.length < 6) {
			throw new IllegalArgumentException("Invalid application data.");
		}
		
		int appId = Integer.parseInt(parse[0]);
		String state = parse[1];
		String appType = parse[2];
		String summary = parse[3];
		String reviewer = parse.length > 5 ? parse[4] : "";
		boolean processPaperwork = Boolean.parseBoolean(parse[5]);
		
		String resolution = "";
		ArrayList<String> notes = new ArrayList<>();
		
		if(parse.length > 6) {
			resolution = parse[6];
		}
		
		notes = new ArrayList<>(Arrays.asList(app.substring(app.indexOf("-") + 1).split("-")));
		for(int i = 0; i < notes.size(); i++) {
			notes.set(i, notes.get(i).trim());
			
		}
		return new Application(appId, state, appType, summary, reviewer, processPaperwork, resolution, notes);
	}

}
