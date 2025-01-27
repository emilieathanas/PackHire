package edu.ncsu.csc216.app_manager.model.application;

import java.util.ArrayList;

import edu.ncsu.csc216.app_manager.model.command.Command;
import edu.ncsu.csc216.app_manager.model.command.Command.Resolution;
/**
 * Application class holds information about an application.
 * @author Emilie Athanasenas
 */
public class Application {
	
	/** Types that an Application can be */
	public enum AppType { NEW, OLD, HIRED }
	
	/** A constant string for the “New” application type. */
	public static final String A_NEW = "New";
	
	/** A constant string for the “Old” application type */
	public static final String A_OLD = "Old";
	
	/** A constant string for the "Hired" application type */
	public static final String A_HIRED = "Hired";
	
	/** A constant string for the review state’s name with the value “Review”. */
	public static final String REVIEW_NAME = "Review";
	
	/** A constant string for the interview state’s name with the value “Interview”. */
	public static final String INTERVIEW_NAME = "Interview";
	
	/** A constant string for the waitlist state’s name with the value “Waitlist”. */
	public static final String WAITLIST_NAME = "Waitlist";
	
	/**  A constant string for the reference check state’s name with the value “RefCheck” */
	public static final String REFCHK_NAME = "RefCheck";
	
	/** A constant string for the offer state’s name with the value “Offer”. */
	public static final String OFFER_NAME = "Offer";
	
	/**A constant string for the closed state’s name with the value “Closed”. */
	public static final String CLOSED_NAME = "Closed";
	
	/** Unique application id for an application. */
	private int appId;
	
	/** Type of application */
	private AppType appType;
	
	/** Resolution of the application */
	private Resolution resolution;
	
	/** Current state for the application of type AppState. */
	private AppState state;
	
	/** Type of application - either a AppType.NEW or AppType.OLD. */
	private String summary;
	
	/** User id of the application reviewer */
	private String reviewer;
	
	/** Application’s summary information from when the application is created. */
	private boolean processPaperwork;
	
	/** User id of the application reviewer or null if there is not an assigned reviewer. */
	private ArrayList<String> notes;
	
	/** Instance of ReviewState */
	private final ReviewState reviewState = new ReviewState();
	
	/** Instance of InterviewState */
	private final InterviewState interviewState = new InterviewState();
	
	/** Instance of RefChkState */
	private final RefChkState refChkState = new RefChkState();
	
	/** Instance of OfferState */
	private final OfferState offerState = new OfferState();
	
	/** Instance of WaitlistState */
	private final WaitlistState waitlistState = new WaitlistState();
	
	/** Instance of ClosedState */
	private final ClosedState closedState = new ClosedState();
	
	
	/**
	 * Constructs a Application from the provided AppType, summary, and note
	 * @param id of application
	 * @param appType of application
	 * @param summary of application 
	 * @param note on application
	 * @throws IllegalArgumnetException if there are problems with any of the parameters
	 */
	public Application(int id, AppType appType, String summary, String note) {
		//checks if any parameters are null/empty strings
		if(id < 1 || appType == null || summary == null || summary.length() == 0  || note == null || note.length() == 0) {
			throw new IllegalArgumentException("Application cannot be created.");
		}
		setAppId(id);
		setState(REVIEW_NAME);
		this.appType = appType;
		setReviewer(null);
		setResolution(null);
		setProcessPaperwork(false);
		setSummary(summary);
		notes = new ArrayList<String>();
		addNote(note);
	}
	
	/**
	 * Second constructor for Application that have more parameters 
	 * @param id of application
	 * @param state of application
	 * @param appType of application
	 * @param summary of application
	 * @param reviewer of application
	 * @param confirmed of application
	 * @param resolution of application
	 * @param notes on application
	 * @throws IllegalArgumentException if there are problems with any of the parameters
	 */
	public Application(int id, String state, String appType, String summary, String reviewer, 
			boolean confirmed, String resolution, ArrayList<String> notes) {
		if(id < 1 || appType == null || summary == null || summary.length() == 0 || notes == null || notes.size() == 0) {
			throw new IllegalArgumentException("Application cannot be created.");
		}
		setAppId(id);
		if(state.equals(INTERVIEW_NAME) && appType.equals(A_OLD) && (reviewer == null || reviewer.length() == 0)) {
			throw new IllegalArgumentException("Application cannot be created.");
		}
		setState(state);
		setAppType(appType);
		if(resolution == null || "".equals(resolution)) {
			this.resolution = null;
		}
		else {
			setResolution(resolution);
		}
		
		setSummary(summary);
		if(state.equals(WAITLIST_NAME) && appType.equals(A_OLD) && (reviewer == null || reviewer.isEmpty())){
			throw new IllegalArgumentException("Appliation cannot be created.");
			
		}
		if(state.equals(REFCHK_NAME) && reviewer == null) {
			throw new IllegalArgumentException("Application cannot be created.");
		}
		if(state.equals(REVIEW_NAME) && !"".equals(resolution)) {
			throw new IllegalArgumentException("Application cannot be created.");
		}
		if(state.equals(INTERVIEW_NAME) && resolution.equals(Command.R_INTCOMPLETED)) {
			throw new IllegalArgumentException("Application cannot be created.");
		}
		if(state.equals(REFCHK_NAME) && resolution.equals(Command.R_REFCHKCOMPLETED)) {
			throw new IllegalArgumentException("Application cannot be created.");	
		}
		if(state.equals(OFFER_NAME) && resolution.equals(Command.R_OFFERCOMPLETED)) {
			throw new IllegalArgumentException("Application cannot be created.");
		}
		if((state.equals(INTERVIEW_NAME) || state.equals(REFCHK_NAME) || state.equals(OFFER_NAME)) && appType.equals(A_NEW)){
			throw new IllegalArgumentException("Application cannot be created.");	
		}
		if((state.equals(INTERVIEW_NAME) || state.equals(REVIEW_NAME)) && confirmed) {
			throw new IllegalArgumentException("Application cannot be created.");
		}
		if((state.equals(WAITLIST_NAME) || state.equals(CLOSED_NAME) || state.equals(OFFER_NAME)) && reviewer == null) {
			throw new IllegalArgumentException("Application cannot be created.");
		}
		if(state.equals(CLOSED_NAME) && getResolution() == null) {
			throw new IllegalArgumentException("Application cannot be created.");
		}
		if(state.equals(WAITLIST_NAME) && getResolution() == null || "".equals(getResolution())) {
			throw new IllegalArgumentException("Application cannot be created.");
		}
		if(state.equals(INTERVIEW_NAME) && (!"".equals(resolution))){
			throw new IllegalArgumentException("Application cannot be created.");
		}
		if(state.equals(REFCHK_NAME) && ("".equals(reviewer) || reviewer == null)) {
			throw new IllegalArgumentException("Application cannot be created.");
		}
		if(state.equals(OFFER_NAME) && ("".equals(reviewer) || reviewer == null)) {
			throw new IllegalArgumentException("Application cannot be created.");
		}
		if(state.equals(REFCHK_NAME) && !confirmed) {
			throw new IllegalArgumentException("Application cannot be created.");
		}
		if(state.equals(WAITLIST_NAME) && !resolution.equals(Command.R_REVCOMPLETED) && !resolution.equals(Command.R_INTCOMPLETED)){
			throw new IllegalArgumentException("Application cannot be created.");
		}
		if(appType.equals(A_NEW) && confirmed) {
			throw new IllegalArgumentException("Application cannot be created.");
		}
		if(appType.equals(A_NEW) && notes.isEmpty()) {
			throw new IllegalArgumentException("Application cannot be created.");
		}
		if(appType.equals(A_NEW) && resolution.equals(Command.R_INTCOMPLETED)) {
			throw new IllegalArgumentException("Application cannot be created.");
		}
		
		
		setReviewer(reviewer);
		setProcessPaperwork(confirmed);
		setNotes(notes);
	}
	
	/**
	 * Helper method that changes a String to an AppState.
	 * @param state as a string
	 * @return an AppState object
	 */
	private AppState parseState(String state) {
		switch(state) {
			case REVIEW_NAME:
				return reviewState;
			case INTERVIEW_NAME:
				return interviewState;
			case REFCHK_NAME:
				return refChkState;
			case OFFER_NAME:
				return offerState;
			case WAITLIST_NAME:
				return waitlistState;
			case CLOSED_NAME:
				return closedState;
			default:
				throw new IllegalArgumentException("Invalid application state.");
		}
	}
	
	/**
	 * Helper method that changes a String to an AppType
	 * @param type as a string
	 * @return an AppType enumeration value
	 */
	private AppType parseType(String type) {
		switch(type) {
			case "New":
				return AppType.NEW;
			case "Old":
				return AppType.OLD;
			case "Hired":
				return AppType.HIRED;
			default:
				throw new IllegalArgumentException("Invalid application type.");
		}
	}
	
	/**
	 * Helper method that changes a String to a Resolution
	 * @param resolution as a string
	 * @return a Resolution enumeration value
	 */
	private Resolution parseResolution(String resolution) {
		switch(resolution) {
		case Command.R_REVCOMPLETED:
			return Command.Resolution.REVCOMPLETED;
		case Command.R_INTCOMPLETED:
			return Command.Resolution.INTCOMPLETED;
		case Command.R_REFCHKCOMPLETED:
			return Command.Resolution.REFCHKCOMPLETED;
		case Command.R_OFFERCOMPLETED:
			return Command.Resolution.OFFERCOMPLETED;
		case null:
			return null;
		default:
			throw new IllegalArgumentException("Invalid application resolution");
		
		}
	}
	/**
	 * Sets the application id with the given parameter
	 * @param id of application
	 */
	private void setAppId(int id) {
		this.appId = id;

	}
	
	/**
	 * Sets the state with the given parameter
	 * @param state of application
	 */
	private void setState(String state) {
		try {
			this.state = parseState(state);
		}
		catch(IllegalArgumentException e) {
			throw new IllegalArgumentException("Application cannot be created.");
		}
	}
	
	/**
	 * Sets the application type with the given parameter
	 * @param appType of application
	 */
	private void setAppType(String appType) {
		try {
			this.appType = parseType(appType);
		}
		catch(IllegalArgumentException e) {
			throw new IllegalArgumentException("Application cannot be created.");
		}
		
	}
	
	/**
	 * Sets the summary with the given parameter
	 * @param summary of application
	 */
	private void setSummary(String summary) {
		this.summary = summary;
		
	}
	
	/**
	 * Sets the reviewer with the given parameter
	 * @param reviewer of application
	 */
	private void setReviewer(String reviewer) {
		if("".equals(reviewer)) {
			this.reviewer = null;
		}
		else {
			this.reviewer = reviewer;
		}
		
	}
	
	/**
	 * Sets whether the paperwork of an application has been processed
	 * @param isProcessed boolean represents if the paperwork is processed
	 */
	private void setProcessPaperwork(boolean isProcessed) {
		processPaperwork = isProcessed;
		
	}
	
	/**
	 * Sets the resolution of the application with the given parameter
	 * @param resolution of application
	 */
	private void setResolution(String resolution) {
		
		
		try {
			this.resolution = parseResolution(resolution);
		}
		catch(IllegalArgumentException e) {
			throw new IllegalArgumentException("Application cannot be created.");
		}
		
	}
	
	/**
	 * Sets the notes of the application with the given parameter
	 * @param notes arraylist of notes to set
	 */
	private void setNotes(ArrayList<String> notes) {
		this.notes = notes;
		
	}
	
	/**
	 * Gets and returns the application id
	 * @return application's id
	 */
	public int getAppId() {
		return appId;
	}
	
	/**
	 * Gets and returns the name of the state of the application
	 * @return name of the state as a string
	 */
	public String getStateName() {
		return state.getStateName();
	}
	
	/**
	 * Gets and returns the application type
	 * @return application type as string
	 */
	public String getAppType() {
		switch(appType) 
		{
			case AppType.NEW:
				return A_NEW;
			case AppType.OLD:
				return A_OLD;
			case AppType.HIRED:
				return A_HIRED;
			default:
				return "";
		}
		
	}
	
	/**
	 * Gets and returns the summary of the application
	 * @return summary of application
	 */
	public String getSummary() {
		return summary;
	}
	
	/**
	 * Gets and returns the reviewer of the application
	 * @return reviewer of application
	 */
	public String getReviewer() {
		if(reviewer == null) {
			return null;
		}
		return reviewer;
	}
	
	/**
	 * Gets and returns true or false if the paperwork is processed or not
	 * @return true if the paperwork is processed false if not
	 */
	public boolean isProcessed() {
		return processPaperwork;
	}
	
	/**
	 * Gets and returns the resolution of the application
	 * @return resolution of the application
	 */
	public String getResolution() {
		switch(resolution)
		{
			case Command.Resolution.REVCOMPLETED:
				return Command.R_REVCOMPLETED;
			case Command.Resolution.INTCOMPLETED:
				return Command.R_INTCOMPLETED;
			case Command.Resolution.REFCHKCOMPLETED:
				return Command.R_REFCHKCOMPLETED;
			case Command.Resolution.OFFERCOMPLETED:
				return Command.R_OFFERCOMPLETED;
			case null:
				return null;
			default:
				throw new IllegalArgumentException("Invalid resolution");
				
		}
	}
	
	/**
	 * Gets and returns the notes of an application
	 * @return the notes of the application
	 */
	public ArrayList<String> getNotes(){
		return notes;
	}
	
	/**
	 * Gets and returns the notes as a string
	 * @return the notes of the application as a string
	 */
	public String getNotesString() {
		String notesString = "";
		
		for(int i = 0; i < notes.size(); i++) {
			
				notesString += "-" + notes.get(i) + "\n";
			
		}
		return notesString;
	}
	
	/**
	 * Gets and returns an Application object as a string
	 * @return Application object as a string
	 */
	public String toString() {
		if(resolution == null) {
			return "*" + getAppId() + "," + getStateName() + "," + getAppType() + "," + getSummary() + "," + 
					getReviewer() + "," + isProcessed() + "," + "\n" + getNotesString();
			
		}
		return "*" + getAppId() + "," + getStateName() + "," + getAppType() + "," + getSummary() + "," + 
				getReviewer() + "," + isProcessed() + "," + getResolution() + "\n" + getNotesString();
	}
	
	/**
	 * Adds a note to the ArrayList of notes of the application
	 * @param note to be added
	 * @throws IllegalArgumentException if the note is null or empty string
	 */
	public void addNote(String note) {
		//checks if the note is null or empty string
		if(note.length() == 0) {
			throw new IllegalArgumentException("Note cannot be added");
		}
		else if(getStateName().equals(REVIEW_NAME)) {
			notes.add("[Review] " + note);
		}
		else if(getStateName().equals(INTERVIEW_NAME)) {
			notes.add("[Interview] " + note);
		}
		else if(getStateName().equals(REFCHK_NAME)) {
			notes.add("[RefCheck] " + note);
		}
		else if(getStateName().equals(WAITLIST_NAME)) {
			notes.add("[Waitlist] " + note);
		}
		else if(getStateName().equals(OFFER_NAME)) {
			notes.add("[Offer] " + note);
		}
		else if(getStateName().equals(CLOSED_NAME)) {
			notes.add("[Closed] " + note);
		}
		
	}
	
	/**
	 * Updates the action with the given command of the application
	 * @param c command to be updated to
	 * @throws UnsupportedOperationException if the command is not appropriate for the current state
	 */
	public void update(Command c) {
		try {
			state.updateState(c);
		}
		catch(UnsupportedOperationException e) {
			throw new UnsupportedOperationException("Invalid information.");
		}
		
	}

	
	/**
	 * Interface for states in the Application State Pattern.  All 
	 * concrete application states must implement the AppState interface.
	 * The AppState interface should be a private interface of the 
	 * Application class.
	 * 
	 * @author Dr. Sarah Heckman (sarah_heckman@ncsu.edu) 
	 * @author Dr. Chandrika Satyavolu (jsatyav@ncsu.edu)
	 */
	private interface AppState {
		
		/**
		 * Update the Application based on the given Command.
		 * An UnsupportedOperationException is thrown if the Command
		 * is not a valid action for the given state.  
		 * @param command Command describing the action that will update the Application's
		 * state.
		 * @throws UnsupportedOperationException if the Command is not a valid action
		 * for the given state.
		 */
		void updateState(Command command);
		
		/**
		 * Returns the name of the current state as a String.
		 * @return the name of the current state as a String.
		 */
		String getStateName();

	}
	
	/**
	 * Private class for the Review state.
	 */
	private class ReviewState implements AppState {

		/**
		 * Updates the state to Review state.
		 * @throws UnsupportedOperationException if the Command is not a valid action
		 * for the given state.
		 */
		@Override
		public void updateState(Command c) {
			if(c.getCommand() == Command.CommandValue.ACCEPT && c.getReviewerId() != null && !c.getReviewerId().isEmpty()) {
				reviewer = c.getReviewerId();
				setState(INTERVIEW_NAME);
				setAppType(A_OLD);
				
			}
			else if(c.getCommand() == Command.CommandValue.STANDBY && getAppType() != A_OLD) {
				setResolution(Command.R_REVCOMPLETED);
				reviewer = c.getReviewerId();
				setState(WAITLIST_NAME);
			}
			else if(c.getCommand() == Command.CommandValue.REJECT) {
				setResolution(Command.R_REVCOMPLETED);
				setState(CLOSED_NAME);
				reviewer = c.getReviewerId();
			}
			else if(c.getReviewerId() == null || c.getNote().length() == 0) {
				throw new UnsupportedOperationException("Invalid information.");
			}
			else {
				throw new UnsupportedOperationException("Invalid information.");
			}
			
			addNote(c.getNote());
		}

		/**
		 * Returns the state name
		 * @return state name
		 */
		@Override
		public String getStateName() {
			return REVIEW_NAME;
		}
		
	}
	
	/**
	 * Private class for the Interview State.
	 */
	private class InterviewState implements AppState {


		/**
		 * Updates the state to Interview state.
		 * @throws UnsupportedOperationException if the Command is not a valid action
		 * for the given state.
		 */
		@Override
		public void updateState(Command c) {
			if(c.getCommand() == Command.CommandValue.ACCEPT && c.getReviewerId() != null && !c.getReviewerId().isEmpty()) {
				setReviewer(c.getReviewerId());
				setState(REFCHK_NAME);
				setProcessPaperwork(true);
				
			}
			else if(c.getCommand() == Command.CommandValue.STANDBY) {
				reviewer = c.getReviewerId();
				setResolution(Command.R_INTCOMPLETED);
				setState(WAITLIST_NAME);
			}
			else if(c.getCommand() == Command.CommandValue.REJECT) {
				setResolution(Command.R_INTCOMPLETED);
				reviewer = c.getReviewerId();
				setState(CLOSED_NAME);
			}
			else {
				throw new UnsupportedOperationException("Invalid information.");
			}
			addNote(c.getNote());
		}

		/**
		 * Returns the state name
		 * @return state name
		 */
		@Override
		public String getStateName() {
			return INTERVIEW_NAME;
		}
		
	}
	
	/**
	 * Private class for the Reference Check State.
	 */
	private class RefChkState implements AppState {
		/**
		 * Updates the state to Reference check state
		 * @throws UnsupportedOperationException if the Command is not a valid action
		 * for the given state.
		 */
		@Override
		public void updateState(Command c) {
			if(c.getReviewerId() != null && !c.getReviewerId().isEmpty() && c.getCommand() == Command.CommandValue.ACCEPT) {
				setResolution(Command.R_REFCHKCOMPLETED);
				setProcessPaperwork(true);
				reviewer = c.getReviewerId();
				setState(OFFER_NAME);
				setResolution(null);
			}
			else if(c.getCommand() == Command.CommandValue.REJECT) {
				setResolution(Command.R_REFCHKCOMPLETED);
				setReviewer(c.getReviewerId());
				setState(CLOSED_NAME);
			}
			else {
				throw new UnsupportedOperationException("Invalid information.");
			}
			addNote(c.getNote());
			
		}

		/**
		 * Returns the state name
		 * @return state name 
		 */
		@Override
		public String getStateName() {
			return REFCHK_NAME;
		}
		
	}
	
	/**
	 * Private class for the Offer State.
	 */
	private class OfferState implements AppState {
		/**
		 * Updates the state to Offer state
		 * @throws UnsupportedOperationException if the Command is not a valid action
		 * for the given state.
		 */
		@Override
		public void updateState(Command c) {

			if(c.getReviewerId() != null && !c.getReviewerId().isEmpty() && c.getCommand() == Command.CommandValue.ACCEPT) {
				setResolution(Command.R_OFFERCOMPLETED);
				setProcessPaperwork(true);
				setAppType(A_HIRED);
				setState(CLOSED_NAME);
			}
			else if(c.getCommand() == Command.CommandValue.REJECT) {
				setResolution(Command.R_OFFERCOMPLETED);
				setReviewer(null);
				setState(CLOSED_NAME);
			}
			else {
				throw new UnsupportedOperationException("Invalid information.");
			}
			addNote(c.getNote());
		}

		/**
		 * Returns the state name
		 * @return state name
		 */
		@Override
		public String getStateName() {
			return OFFER_NAME;
		}
		
	}
	
	/**
	 * Private class for the Waitlist state
	 */
	private class WaitlistState implements AppState {
		
		/**
		 * Updates the state to Waitlist state
		 * @throws UnsupportedOperationException if the Command is not a valid action
		 * for the given state.
		 */
		@Override
		public void updateState(Command c) {
			if(resolution == Command.Resolution.INTCOMPLETED && c.getReviewerId() != null && !c.getReviewerId().isEmpty() && c.getCommand() == Command.CommandValue.REOPEN) {
				setProcessPaperwork(true);
				setState(REFCHK_NAME);
				setResolution(null);
				setReviewer(c.getReviewerId());
			}
			else if(resolution == Resolution.REVCOMPLETED && getAppType().equals(A_NEW) && c.getCommand() == Command.CommandValue.REOPEN) {
				setAppType(A_OLD);
				setState(REVIEW_NAME);
				setResolution(null);
				setProcessPaperwork(false);
				reviewer = c.getReviewerId();
			}
			else {
				throw new UnsupportedOperationException("Invalid information.");
			}
			addNote(c.getNote());
		}

		/**
		 * Returns the state name
		 * @return state name
		 */
		@Override
		public String getStateName() {
			return WAITLIST_NAME;
		}
		
	}
	
	/**
	 * Private class for the Closed state.
	 */
	private class ClosedState implements AppState {
		/**
		 * Updates the state to the Closed state
		 * @throws UnsupportedOperationException if the Command is not a valid action
		 * for the given state.
		 */
		@Override
		public void updateState(Command c) {
			if(c.getCommand() == Command.CommandValue.REOPEN) {
				if(getAppType().equals(A_NEW) && resolution == Command.Resolution.REVCOMPLETED) {
					setAppType(A_OLD);
					setState(REVIEW_NAME);
					setResolution(null);
				}
				else if(getAppType().equals(A_OLD) || resolution == Command.Resolution.REVCOMPLETED) {
					throw new UnsupportedOperationException("Invalid information.");
				}
			}
			else {
				throw new UnsupportedOperationException("Invalid information.");
				
			}
			addNote(c.getNote());
		}

		/**
		 * Returns the state name
		 * @return state name
		 */
		@Override
		public String getStateName() {
			return CLOSED_NAME;
		}
		
	}
}
