package br.edu.fatec.server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

import org.json.JSONObject;

public class ServidorThread extends Thread
{

	public static void main(String[] args){
		ServidorThread servidorThread = new ServidorThread();
		servidorThread.start();
	}
	
    public ServidorThread()
    {
    }

    public void run()
    {
    	try
        {
            String resposta1 = "";
            ServerSocket welcomeSocket1 = new ServerSocket(9000);
          
            //int i = 1;
        	
            while(!resposta1.equals("null\n")){
            //while(i == 1){
            	Socket connectionSocket1 = welcomeSocket1.accept();
            	BufferedReader inFromClient1 = new BufferedReader(new InputStreamReader(connectionSocket1.getInputStream()));
                //resposta1 = (new StringBuilder(String.valueOf(inFromClient1.readLine()))).append('\n').toString();
            	JSONObject jsonSay = new JSONObject((new StringBuilder(String.valueOf(inFromClient1.readLine()))).append('\n').toString());
            	
                System.out.println(jsonSay);
                System.out.println(connectionSocket1.getInetAddress());
            }

            System.out.println("Notebook saiu da conversa");
            welcomeSocket1.close();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        System.out.println("Conversa encerrada!");
    }
}
