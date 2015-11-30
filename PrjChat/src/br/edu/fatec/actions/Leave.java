package br.edu.fatec.actions;

import java.sql.Timestamp;

public class Leave {
	private String action;
	private Timestamp dateHour;

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}
	
	public Timestamp getDateHour() {
		return dateHour;
	}

	public void setDateHour(Timestamp dateHour) {
		this.dateHour = dateHour;
	}

	@Override
	public String toString() {
		return "[action=" + this.action + "]";
    }
}