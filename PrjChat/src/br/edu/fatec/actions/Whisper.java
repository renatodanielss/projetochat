package br.edu.fatec.actions;

public class Whisper {
	private String action;
	private String content;
	
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
	
	@Override
	public String toString() {
		return "[action=" + this.action + ", content=" + this.content + "]";
    }
}