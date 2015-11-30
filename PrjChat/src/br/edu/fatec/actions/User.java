package br.edu.fatec.actions;

import java.sql.Timestamp;

public class User {
	private String nickname;
	private String address;
	private Timestamp dateHour;
	
	public String getNickname() {
		return nickname;
	}
	
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	
	public String getAddress() {
		return address;
	}
	
	public void setAddress(String address) {
		this.address = address;
	}

	public Timestamp getDateHour() {
		return dateHour;
	}

	public void setDateHour(Timestamp dateHour) {
		this.dateHour = dateHour;
	}
}