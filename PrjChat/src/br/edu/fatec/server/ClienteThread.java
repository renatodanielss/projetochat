package br.edu.fatec.server;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.Socket;

import org.json.JSONObject;

import br.edu.fatec.actions.Say;

public class ClienteThread extends Thread
{
	//Socket clienteSocket;

	public static void main (String[] args){
		ClienteThread clienteThread = new ClienteThread();
		clienteThread.start();
	}
	
	public ClienteThread(){
	}
	
    public ClienteThread(Socket clienteSocket)
    {
        //this.clienteSocket = clienteSocket;
    }

    public void run()
    {
        String sentence = "";
        try
        {
            while(!sentence.equals("exit")) 
            {
            	Socket clienteSocket = new Socket("localhost", 4445);
                BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
                DataOutputStream outToServer = new DataOutputStream(clienteSocket.getOutputStream());
                sentence = inFromUser.readLine();
                
                Say say = new Say();
                say.setAction("say");
                say.setTarget("localhost");
                say.setContent(sentence);
                JSONObject jsonSay = new JSONObject(say);
                
                //outToServer.writeBytes((new StringBuilder(String.valueOf(sentence))).append('\n').toString());
                outToServer.writeBytes((new StringBuilder(String.valueOf(jsonSay))).append('\n').toString());
                clienteSocket.close();
            }
            //clienteSocket.close();
            interrupt();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
}