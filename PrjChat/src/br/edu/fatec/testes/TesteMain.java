package br.edu.fatec.testes;

import org.json.JSONObject;

import br.edu.fatec.actions.Leave;
import br.edu.fatec.actions.Report;
import br.edu.fatec.actions.Say;
import br.edu.fatec.actions.Search;
import br.edu.fatec.actions.Whisper;

public class TesteMain {
	public static void main(String[] args){
		executeSay("223.139.219.100", "say", "minha primeira mensagem");
		executeSearch("search", "renatodanielss");
		executeWhisper("whisper", "mais uma mensagem");
	}
	
	public static void executeSay(String target, String action, String content){
		Say say = new Say();
		say.setTarget(target);
		say.setAction(action);
		say.setContent(content);
		
		JSONObject jsonSay = new JSONObject(say);
		
		System.out.println(jsonSay);
	}
	
	public static void executeSearch(String action, String nickname){
		Search search = new Search();
		search.setAction(action);
		search.setNickname(nickname);
		
		JSONObject jsonSearch = new JSONObject(search);
		
		System.out.println(jsonSearch);
	}
	
	public static void executeWhisper(String action, String content){
		Whisper whisper = new Whisper();
		whisper.setAction(action);
		whisper.setContent(content);
		
		JSONObject jsonWhisper = new JSONObject(whisper);
		
		System.out.println(jsonWhisper);
	}
	
	public static void executeLeave(String action, String content){
		Leave leave = new Leave();
		leave.setAction(action);
		
		JSONObject jsonLeave = new JSONObject(leave);
		
		System.out.println(jsonLeave);
	}
	
	public static void executeReport(String action, String message){
		Report report = new Report();
		report.setAction(action);
		report.setMessage(message);
		
		JSONObject jsonReport = new JSONObject(report);
		
		System.out.println(jsonReport);
	}
}