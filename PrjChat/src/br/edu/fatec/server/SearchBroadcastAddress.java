package br.edu.fatec.server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;
import java.util.Scanner;

import org.json.JSONObject;

import br.edu.fatec.actions.Say;

public class SearchBroadcastAddress extends Thread{

	/**
	 * @param args
	 * @throws IOException
	 */
	/*public static void main(String[] args){
		SearchBroadcastAddress sBroadcastAdress = new SearchBroadcastAddress();
		sBroadcastAdress.start();
	}*/
	
	@Override
	public void run() {
		try {
			@SuppressWarnings("resource")
			Scanner teclado = new Scanner(System.in);
			
			Enumeration<NetworkInterface> interfaces = NetworkInterface
					.getNetworkInterfaces();
			while (interfaces.hasMoreElements()) {
				NetworkInterface networkInterface = interfaces.nextElement();
				if (networkInterface.isLoopback())
					continue; // Don't want to broadcast to the loopback interface
				for (InterfaceAddress interfaceAddress : networkInterface
						.getInterfaceAddresses()) {
					InetAddress broadcast = interfaceAddress.getBroadcast();
					if (broadcast == null)
						continue;
					System.out.println(broadcast);
					
					while(true){
						DatagramSocket socket = new DatagramSocket();
						socket.setBroadcast(true);
						// send request
						String message = teclado.nextLine();
						
						Say say = new Say();
						say.setAction("say");
						say.setTarget("223.139.219.100");
						say.setContent(message);
						JSONObject jsonSay = new JSONObject(say);
						String str = String.valueOf(jsonSay);
						
						byte[] buf = str.getBytes();
					
						DatagramPacket packet = new DatagramPacket(buf, buf.length,broadcast, 4445);
						socket.send(packet);
						socket.close();
					}	
				}
			}
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}
}