package br.edu.fatec.actions;

public class Search {
	private String action;
	private String nickname;
	
	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	@Override
	public String toString() {
		return "[action=" + this.action + ", nickname=" + this.nickname + "]";
    }
}