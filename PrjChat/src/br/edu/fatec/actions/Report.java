package br.edu.fatec.actions;

import java.sql.Timestamp;

public class Report {
	private String action;
	private String message;
	private Timestamp dateHour;
	
	public String getAction() {
		return action;
	}
	
	public void setAction(String action) {
		this.action = action;
	}
	
	public String getMessage() {
		return message;
	}
	
	public void setMessage(String message) {
		this.message = message;
	}
	
	public Timestamp getDateHour() {
		return dateHour;
	}

	public void setDateHour(Timestamp dateHour) {
		this.dateHour = dateHour;
	}

	@Override
	public String toString() {
		return "[action=" + this.action + ", message=" + this.message + "]";
    }
}