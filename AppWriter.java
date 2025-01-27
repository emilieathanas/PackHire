package edu.ncsu.csc216.app_manager.model.io;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.util.List;

import edu.ncsu.csc216.app_manager.model.application.Application;
/**
 * AppWriter class handles the I/O functionality of the Application Manager
 * system
 * @author Emilie Athanasenas
 */
public class AppWriter {
	
	/**
	 * Writes the list of applications to a given file
	 * @param fileName name of the file
	 * @param apps list of applications
	 * @throws illegalArgumentException if there are any problems 
	 */
	public static void writeAppsToFile(String fileName, List<Application> apps){
		PrintStream fileWriter = null;
		try {
			fileWriter = new PrintStream(new File(fileName));
			for(int i = 0; i < apps.size(); i++) {
				Application a = apps.get(i);
				fileWriter.print(a.toString());
				if (i + 1 < apps.size()) {
					fileWriter.print("\n");
				}
			}
			fileWriter.close();
		} catch (IOException e) {
			throw new IllegalArgumentException("Unable to save file");
		}
		
		
	}

}
