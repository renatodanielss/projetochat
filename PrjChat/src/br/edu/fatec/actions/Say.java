package br.edu.fatec.actions;

import java.sql.Timestamp;

public class Say {
	private String target;
	private String action;
	private String content;
	private Timestamp dateHour;
	
	public String getTarget() {
		return target;
	}
	
	public void setTarget(String target) {
		this.target = target;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Timestamp getDateHour() {
		return dateHour;
	}

	public void setDateHour(Timestamp dateHour) {
		this.dateHour = dateHour;
	}

	@Override
	public String toString() {
		return "[target=" + this.target + ", action=" + this.action + ", content=" + this.content + "]";
    }
}