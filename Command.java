package edu.ncsu.csc216.app_manager.model.command;

/**
 * Command class creates objects that encapsulates user actions or transitions
 * that case the state of an Application to update.
 * 
 * @author Emilie Athanasenas
 */
public class Command {
	/** * The different actions you can perform on an application. */
	public enum CommandValue {	ACCEPT, REJECT, STANDBY, REOPEN }

	/*** The final states that an application can be in. */
	public enum Resolution { REVCOMPLETED, INTCOMPLETED, REFCHKCOMPLETED, OFFERCOMPLETED }

	/** A constant string for the Review Completed resolution. */
	public static final String R_REVCOMPLETED = "ReviewCompleted";

	/** A constant string for the Interview Completed resolution */
	public static final String R_INTCOMPLETED = "InterviewCompleted";

	/** A constant string for the Reference CHeck Completed resolution */
	public static final String R_REFCHKCOMPLETED = "ReferenceCheckCompleted";

	/** A constant string for the Offer Completed resolution */
	public static final String R_OFFERCOMPLETED = "OfferCompleted";

	/** Command value of the application */
	public CommandValue c;

	/** The resolution of the application */
	public Resolution resolution;

	/** Id of the reviewer of the application */
	private String reviewerId;

	/** Note on the application */
	private String note;

	/**
	 * Constructs
	 * 
	 * @param c CommandValue of application
	 * @param reviewerId id of reviewer
	 * @param r resolution of application
	 * @param note of the application
	 * @throws IllegalArgumentException if there are invalid parameters
	 */
	public Command(CommandValue c, String reviewerId, Resolution r, String note) {
		//checks for invalid parameters
		if(c == null) {
			throw new IllegalArgumentException("Invalid information.");
		}
		else if(c == CommandValue.ACCEPT && (reviewerId == null || reviewerId.length() == 0)) {
			throw new IllegalArgumentException("Invalid information.");
		}
		else if((c == CommandValue.STANDBY || c == CommandValue.REJECT) && r == null) {
			throw new IllegalArgumentException("Invalid information.");
		}
		else if(note == null || note.length() == 0) {
			throw new IllegalArgumentException("Invalid information.");
		}	
		this.c = c;
		resolution = r;
		this.reviewerId = reviewerId;
		this.note = note;
	}

	/**
	 * Gets and returns the reviewerId of the application
	 * 
	 * @return reviewId of the application
	 */
	public String getReviewerId() {
		return reviewerId;
		
	}

	/**
	 * Gets and returns the note of the application
	 * 
	 * @return note on the application
	 */
	public String getNote() {
		return note;
	}

	/**
	 * Gets and returns the resolution of the application
	 * 
	 * @return resolution of application
	 */
	public Resolution getResolution() {
		return resolution;
	}

	/**
	 * Gets and returns the command value of the application
	 * 
	 * @return command value of application
	 */
	public CommandValue getCommand() {
		return c;
	}

}
