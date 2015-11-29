package br.edu.fatec.actions;

import org.json.JSONArray;

public class KeepAlive {
	private String action;
	private String nickname;
	private JSONArray users;
	
	public KeepAlive(){
		this.users = new JSONArray();
	}
	
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
	
	public JSONArray getUsers() {
		return users;
	}

	public void setUsers(JSONArray users) {
		this.users = users;
	}

	@Override
	public String toString() {
		return "[action=" + this.action + ", nickname=" + this.nickname + ", users=" + this.users + "]";
    }
}