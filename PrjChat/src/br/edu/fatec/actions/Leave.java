package br.edu.fatec.actions;

public class Leave {
	private String action;

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}
	
	@Override
	public String toString() {
		return "[action=" + this.action + "]";
    }
}