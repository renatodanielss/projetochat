package br.edu.fatec.server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Enumeration;

import org.json.JSONObject;

import br.edu.fatec.actions.Search;
import br.edu.fatec.ui.ClientServerWindowMain;

public class SearchBroadcastAddress extends Thread{
	private int countUsers;
	
	/*public static void main(String[] args){
		SearchBroadcastAddress teste = new SearchBroadcastAddress(0);
		teste.start();
	}*/
	
	public static void main(String[] args) throws Exception // Just for simplicity
	    {
	        for (Enumeration<NetworkInterface> ifaces = 
	               NetworkInterface.getNetworkInterfaces();
	             ifaces.hasMoreElements(); )
	        {
	            NetworkInterface iface = ifaces.nextElement();
	            System.out.println(iface.getName() + ":");
	            for (Enumeration<InetAddress> addresses =
	                   iface.getInetAddresses();
	                 addresses.hasMoreElements(); )
	            {
	                InetAddress address = addresses.nextElement();
	                System.out.println("  " + address);
	            }
	        }
	    }
	
	/*public static void main(String[] args){
		SearchBroadcastAddress teste = new SearchBroadcastAddress(0);
		teste.start();
		
		while (true){
			try {
				BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
				String s = bufferRead.readLine();
				teste.setCountUsers(Integer.valueOf(s));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}*/
	
	public SearchBroadcastAddress(int countUsers) {
		this.countUsers = countUsers;
	}
	
	@Override
	public void run() {
		try {
			//Scanner teclado = new Scanner(System.in);
			
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
					//System.out.println(broadcast);
					
					while(true){
						DatagramSocket socket = new DatagramSocket();
						socket.setBroadcast(true);
						// send request
						//String message = teclado.nextLine();
						
						Date date= new Date();
						Timestamp dateHour = new Timestamp(date.getTime());
						
						Search search = new Search();
						search.setAction("search");
						search.setNickname(ClientServerWindowMain.getNickname());
						search.setDateHour(dateHour);
						JSONObject jsonSearch = new JSONObject(search);
						System.out.println("Date: " + date);
						System.out.println("TimeStamp: " + dateHour);
						
						String str = String.valueOf(jsonSearch);
						
						byte[] buf = str.getBytes();
					
						DatagramPacket packet = new DatagramPacket(buf, buf.length,broadcast, 9000);
						socket.send(packet);
						socket.close();
						
						if (this.countUsers > 0){
							System.out.println("10");
							Thread.sleep(10000);
						}else{
							System.out.println("2");
							Thread.sleep(2000);
						}
					}	
				}
			}
		} catch (IOException ioe) {
			ioe.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public int getCountUsers() {
		return countUsers;
	}

	public void setCountUsers(int countUsers) {
		this.countUsers = countUsers;
	}
}