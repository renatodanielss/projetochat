package br.edu.fatec.server;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.sql.Timestamp;
import java.util.Date;

import org.json.JSONObject;

import br.edu.fatec.actions.User;

public class UDPReceiveSearchBroadcast extends Thread{
	
	public static void main (String[] args){
		UDPReceiveSearchBroadcast udpReceive = new UDPReceiveSearchBroadcast();
		udpReceive.start();
	}
	
	@Override
	public void run() {
		try {
			int port = 9000;

			// Create a socket to listen on the port.
			@SuppressWarnings("resource")
			DatagramSocket dsocket = new DatagramSocket(port);

			// Create a buffer to read datagrams into. If a
			// packet is larger than this buffer, the
			// excess will simply be discarded!
			byte[] buffer = new byte[2048];

			// Create a packet to receive data into the buffer
			DatagramPacket packet = new DatagramPacket(buffer, buffer.length);

			// Now loop forever, waiting to receive packets and printing them.
			while (true) {
				// Wait to receive a datagram
				dsocket.receive(packet);
				
				String[] ipPacket = packet.getSocketAddress().toString().replace("/", "").split(":");
				String userIP = ipPacket[0];
				
				//Converter  o conteúdo do pacote do datagrama e exibir na tela para o usuário
				JSONObject jsonSearch = new JSONObject(new String(buffer, 0, packet.getLength()));
				
				Date date = new Date();
				Timestamp timestamp = new Timestamp(date.getTime());
				
				User user = new User();
				user.setAddress(userIP);
				user.setNickname(jsonSearch.get("nickname").toString());
				user.setDateHour(timestamp);
				System.out.println(jsonSearch);
				
				//Resetar o tamanho do pacote antes de reusá-lo
				packet.setLength(buffer.length);
				
				// Convert the contents to a string, and display them
				//String msg = new String(buffer, 0, packet.getLength());
				//System.out.println(packet.getAddress().getHostName() + ": " + msg);

				// Reset the length of the packet before reusing it.
				//packet.setLength(buffer.length);
			}
		} catch (Exception e) {
			System.err.println(e);
		}
	}
}