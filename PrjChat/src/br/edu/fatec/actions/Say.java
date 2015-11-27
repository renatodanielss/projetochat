package br.edu.fatec.actions;

public class Say {
	private String target;
	private String action;
	private String content;
	
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

	@Override
	public String toString() {
		return "[target=" + this.target + ", action=" + this.action + ", content=" + this.content + "]";
    }
}