package edu.ncsu.csc216.app_manager.model.manager;

import java.util.ArrayList;
import java.util.List;

import edu.ncsu.csc216.app_manager.model.application.Application;
import edu.ncsu.csc216.app_manager.model.application.Application.AppType;
import edu.ncsu.csc216.app_manager.model.command.Command;
import edu.ncsu.csc216.app_manager.model.io.AppReader;
import edu.ncsu.csc216.app_manager.model.io.AppWriter;
/**
 * AppManager Class is a concrete class that maintains a current list of Applications in the 
 * Application Manager system. Implements the Singleton Design Pattern
 * @author Emilie Athanasenas
 */
public class AppManager {
	
	
	/** List of applications */
	private AppList appList;
	  
	/** Instance of AppManager */
	private static AppManager singleton;
	
	/**
	 * Constructs an instance of AppManager
	 */
	private AppManager() {
		createNewAppList();
		
	}
	
	/**
	 * Gets the current instance of AppManager
	 * @return the instance of AppManager
	 */
	public static AppManager getInstance() {
		if(singleton == null) {
			singleton = new AppManager();
		}
		return singleton;
	}
	
	/**
	 * Saves the list of applications to a file
	 * @param fileName name of the file
	 */
	public void saveAppsToFile(String fileName) {
		try {
			List<Application> apps = appList.getApps();
			AppWriter.writeAppsToFile(fileName, apps);
		}
		catch(IllegalArgumentException e) {
			throw new IllegalArgumentException("Unable to write file " + fileName);
		}
		
	}
	
	/**
	 * Loads a list of applications from a file
	 * @param fileName name of file
	 * @throws IllegalArgumentException if the file is invalid
	 */
	public void loadAppsFromFile(String fileName) {
		try {
			ArrayList<Application> apps = new ArrayList<Application>();
			apps = AppReader.readAppsFromFile(fileName);
			appList.addApps(apps);
			
		}
		catch(IllegalArgumentException e) {
			System.out.println(e.getMessage());
			throw new IllegalArgumentException("Unable to read file " + fileName);
			
			
		}
	}
	
	/**
	 * Creates a new application list
	 */
	public void createNewAppList() {
		appList = new AppList();
	}
	
	/**
	 * Returns the list of applications as a 2D array of objects
	 * @return a 2D array of objects

	 */
	public Object[][] getAppListAsArray(){
		Object[][] appListArr = new Object[appList.getApps().size()][4];
		for(int i = 0; i < appList.getApps().size(); i++) {
			appListArr[i][0] = appList.getApps().get(i).getAppId();
			appListArr[i][1] = appList.getApps().get(i).getStateName();
			appListArr[i][2] = appList.getApps().get(i).getAppType();
			appListArr[i][3] = appList.getApps().get(i).getSummary();
		}
		return appListArr;
	}
	
	/**
	 * Returns a list of applications based on a type in a 2D array of objects
	 * @param type of application
	 * @return a 2D array of objects
	 * @throws IllegalArgumentException if the type passed in is null
	 */
	public Object[][] getAppListAsArrayByAppType(String type){
		if(type == null) {
			throw new IllegalArgumentException("Invalid application type.");
		}
		else if("New".equals(type) || "Old".equals(type) || "Hired".equals(type)) {
			List<Application> appTypes = appList.getAppsByType(type);
			Object[][] appTypeArr = new Object[appTypes.size()][4];
			for(int i = 0; i < appTypes.size(); i++) {
				appTypeArr[i][0] = appTypes.get(i).getAppId();
				appTypeArr[i][1] = appTypes.get(i).getStateName();
				appTypeArr[i][2] = appTypes.get(i).getAppType();
				appTypeArr[i][3] = appTypes.get(i).getSummary();
			}
			return appTypeArr;	
		}
		
		return new Object[0][0];
	}
	
	/**
	 * Returns an application based on its id
	 * @param id of application
	 * @return application given its id
	 */
	public Application getAppById(int id) {
		return appList.getAppById(id);
	}
	
	/**
	 * Executes a command on an application
	 * @param id of application
	 * @param c command 
	 */
	public void executeCommand(int id, Command c) {
		appList.executeCommand(id, c);
		
	}
	
	/**
	 * Deletes an application by its id
	 * @param id of application to be deleted
	 */
	public void deleteAppById(int id) {
		appList.deleteAppById(id);
		
	}
	
	/**
	 * Adds an application to the list of applications
	 * @param type of application
	 * @param summary of application 
	 * @param note of application
	 */
	public void addAppToList(AppType type, String summary, String note) {
		appList.addApp(type, summary, note);
		
	}
}
