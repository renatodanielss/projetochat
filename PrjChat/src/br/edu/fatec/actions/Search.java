package br.edu.fatec.actions;

import java.sql.Timestamp;

public class Search {
	private String action;
	private String nickname;
	private Timestamp dateHour;
	
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

	public Timestamp getDateHour() {
		return dateHour;
	}

	public void setDateHour(Timestamp dateHour) {
		this.dateHour = dateHour;
	}

	@Override
	public String toString() {
		return "[action=" + this.action + ", nickname=" + this.nickname + "]";
    }
}