package br.edu.fatec.server;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class ClienteThread extends Thread
{

	public static void main (String[] args){
		try {
			Socket clienteSocket = new Socket("localhost", 4445);
			ClienteThread clienteThread = new ClienteThread(clienteSocket);
			clienteThread.start();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
    public ClienteThread(Socket clienteSocket)
    {
        this.clienteSocket = clienteSocket;
    }

    public void run()
    {
        String sentence = "";
        try
        {
            while(!sentence.equals("exit")) 
            {
                BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
                DataOutputStream outToServer = new DataOutputStream(clienteSocket.getOutputStream());
                sentence = inFromUser.readLine();
                outToServer.writeBytes((new StringBuilder(String.valueOf(sentence))).append('\n').toString());
            }
            clienteSocket.close();
            interrupt();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    Socket clienteSocket;
}
