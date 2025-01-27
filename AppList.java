package edu.ncsu.csc216.app_manager.model.manager;

import java.util.ArrayList;
import java.util.List;

import edu.ncsu.csc216.app_manager.model.application.Application;
import edu.ncsu.csc216.app_manager.model.application.Application.AppType;
import edu.ncsu.csc216.app_manager.model.command.Command;

/**
 * AppList class maintains a List of Applications.
 */
public class AppList {
	/** Counter for the number of applications */
	private int counter;
	/** Instane of application*/
	private ArrayList<Application> apps;
	

	/**
	 * Constructor for an AppList object
	 */
	public AppList() {
		apps = new ArrayList<Application>();
		counter = 0;
	}
	
	/**
	 * Adds an Application to the list of applications.
	 * @param type of application
	 * @param summary of application 
	 * @param note of application
	 * @return the id of the application 
	 */
	public int addApp(AppType type, String summary, String note) {
		counter++;
		Application newApp = new Application(counter, type, summary, note);
		addApp(newApp);
		return counter;
	}
	
	/**
	 * Adds a list of applications to the existing list of applications.
	 * @param apps list of applications to be added
	 */
	public void addApps(List<Application> apps) {
		this.apps = new ArrayList<Application>();
		for(int i = 0; i < apps.size(); i++) {
			addApp(apps.get(i));
		}
		
		counter = this.apps.get(this.apps.size() - 1).getAppId();

	}
	
	/**
	 * Adds an application to the list of applications
	 * @param a application to be added
	 */
	private void addApp(Application a) {
		//checks if there is a duplicate app id, skips adding if so
		for(int i = 0; i < apps.size(); i++) {
			if(apps.get(i).getAppId() == a.getAppId()) {
				return;
			}
		}
		
		//finds the index of where the application should be added
		int index = 0;
		while(index < apps.size() && apps.get(index).getAppId() < a.getAppId()) {
			index++;
		}
		
		apps.add(index, a);
	}
	
	/**
	 * Gets and returns the list of applications
	 * @return the list of applications
	 */
	public List<Application> getApps(){
		return apps;
	}
	
	/**
	 * Gets and returns the the applications specified by a type
	 * @param type of application
	 * @return the list of applications of a certain type
	 * @throws IllegalArgumentExcepetion if type is null
	 */
	public List<Application> getAppsByType(String type){
		if(type == null) {
			throw new IllegalArgumentException("Invalid type.");
		}
		ArrayList<Application> appTypes = new ArrayList<Application>();
		for(int i = 0; i < apps.size(); i++) {
			if(apps.get(i).getAppType().equals(type)) {
				appTypes.add(apps.get(i));
			}
		}
		return appTypes;
	}
	
	/**
	 * Gets the application given its id
	 * @param id of application 
	 * @return Application given by the id
	 */
	public Application getAppById(int id) {
		for(int i = 0; i < apps.size(); i++) {
			if(apps.get(i).getAppId() == id) {
				return apps.get(i);
			}
		}
		return null;
	}
	
	/**
	 * Executes a given command on an application
	 * @param id of application 
	 * @param c command 
	 */
	public void executeCommand(int id, Command c) {
		for(int i = 0; i < apps.size(); i++) {
			if(apps.get(i).getAppId() == id) {
				apps.get(i).update(c);
			}
		}
	
	}
	
	/**
	 * Deletes an application given its id
	 * @param id of application
	 */
	public void deleteAppById(int id) {
		for(int i = 0; i < apps.size(); i++) {
			if(apps.get(i).getAppId() == id) {
				apps.remove(i);
			}
		}
		
	}

}
