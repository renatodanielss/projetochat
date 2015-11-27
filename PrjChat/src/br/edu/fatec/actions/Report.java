package br.edu.fatec.actions;

public class Report {
	private String action;
	private String message;
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
	
	@Override
	public String toString() {
		return "[action=" + this.action + ", message=" + this.message + "]";
    }
}