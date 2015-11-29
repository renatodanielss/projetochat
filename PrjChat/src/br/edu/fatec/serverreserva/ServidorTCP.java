package br.edu.fatec.serverreserva;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class ServidorTCP extends Thread{

	public static void main (String[] args){
		ServidorTCP servidorTCP = new ServidorTCP();
		servidorTCP.start();
	}
	
	/**
	 * @param args
	 * @throws IOException 
	 */
	
	@Override
	public void run() {
		try{
			@SuppressWarnings("resource")
			ServerSocket a = new ServerSocket(4445);
			while(true){
				Socket sk = a.accept();
				InputStream in = sk.getInputStream();
				int i = 0;
				
				do {
					i = in.read();
					System.out.print((char)i);				
				} while(i > 0);
			}
		} catch (IOException ioe){
			ioe.printStackTrace();
		} catch (Exception e){
			e.printStackTrace();
		}
	}
}