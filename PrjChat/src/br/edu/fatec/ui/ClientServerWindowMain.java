package br.edu.fatec.ui;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Timestamp;
import java.util.Date;

import javax.swing.JOptionPane;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.json.JSONArray;
import org.json.JSONObject;

import br.edu.fatec.actions.Say;
import br.edu.fatec.actions.User;
import br.edu.fatec.server.SearchBroadcastAddress;

public class ClientServerWindowMain {
	protected Shell shlChat;
    private Text txtSendMessage;
    private Socket clienteSocket;
    private DataOutputStream outToServer;
    private Text txtMessages;
    private List list;
    private JSONArray users;
    private static String nickname;
    private Text nameListUer;
    private Button btnSusurrar;

	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			nickname = "";
			while(nickname.trim().equals("")){
				nickname = JOptionPane.showInputDialog("Digite o nickname: ");
			}
			ClientServerWindowMain window = new ClientServerWindowMain();
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Open the window.
	 */
	public void open() {
		Display display = Display.getDefault();
		createContents();
		shlChat.open();
		shlChat.layout();
		while (!shlChat.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		this.users = new JSONArray();
		SearchBroadcastAddress searchBroadcast = new SearchBroadcastAddress(0);
		searchBroadcast.start();
		
		Thread udpReceiveSearchBroadcast = new Thread(new Runnable() {
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
						
						System.out.println("userIP:" + userIP);
						Socket s = new Socket("localhost", 9000);
						System.out.println("Socket:" + s.getLocalAddress());
						
						//if (userIP.equals(InetAddress.getLocalHost()))
						//Converter  o conteúdo do pacote do datagrama e exibir na tela para o usuário
						JSONObject jsonSearch = new JSONObject(new String(buffer, 0, packet.getLength()));
						
						Date date = new Date();
						Timestamp timestamp = new Timestamp(date.getTime());
						
						User user = new User();
						user.setAddress(userIP);
						user.setNickname(jsonSearch.get("nickname").toString());
						user.setDateHour(timestamp);
						JSONObject jsonUser = new JSONObject(user);
						users.put(jsonUser);
						System.out.println("jsonSearch: " + jsonSearch);
						System.out.println("jsonUser: " + jsonUser);
						System.out.println("users: " + users);
						
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
		});
		udpReceiveSearchBroadcast.start();
		
		shlChat = new Shell();
        shlChat.setSize(714, 369);
        shlChat.setText("Chat");
        txtSendMessage = new Text(shlChat, 2048);
        txtSendMessage.addKeyListener(new KeyAdapter() {

            public void keyReleased(KeyEvent e)
            {
                if(e.character == '\r'){
                	Say say = new Say();
                	say.setAction("say");
                	say.setTarget("223.139.219.104");
                	say.setContent(txtSendMessage.getText());
                	JSONObject jsonSay = new JSONObject(say);
                    enviarMensagem(jsonSay);
                }
            }
        });
        
        txtSendMessage.setBounds(56, 255, 334, 31);
        Button btnSendMessage = new Button(shlChat, 0);
        btnSendMessage.addSelectionListener(new SelectionAdapter() {

            public void widgetSelected(SelectionEvent e)
            {
            	Say say = new Say();
            	say.setAction("say");
            	say.setTarget("223.139.219.104");
            	say.setContent(txtSendMessage.getText());
            	JSONObject jsonSay = new JSONObject(say);
                enviarMensagem(jsonSay);
            }
        });
        
        btnSendMessage.setBounds(56, 290, 75, 25);
        btnSendMessage.setText("&Enviar");
        txtMessages = new Text(shlChat, 2626);
        txtMessages.setEditable(false);
        txtMessages.setBounds(56, 78, 335, 171);
        
        list = new List(shlChat, SWT.BORDER);
        
        if (this.users.length() > 0){
	        for (int i = 0; i < users.length(); i++){
	        	JSONObject jsonUsers = users.getJSONObject(i);
	        	//System.out.println(jsonSays);
	        	list.add(jsonUsers.get("nickname").toString() + " - " + jsonUsers.get("address").toString());
	        }
        }
        
        //list.setItems(new String[] {"Teste 1", "Teste 2", "Teste 3"});
        list.setBounds(397, 84, 278, 202);
        
        nameListUer = new Text(shlChat, SWT.NONE);
        nameListUer.setFont(SWTResourceManager.getFont("Noto Sans", 10, SWT.BOLD));
        nameListUer.setText("Usuários");
        nameListUer.setForeground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_HIGHLIGHT_SHADOW));
        nameListUer.setBackground(SWTResourceManager.getColor(SWT.COLOR_DARK_CYAN));
        nameListUer.setBounds(397, 51, 278, 27);
        
        btnSusurrar = new Button(shlChat, SWT.CHECK);
        btnSusurrar.setBounds(155, 293, 98, 22);
        btnSusurrar.setText("Susurrar");
        list.addSelectionListener(new SelectionAdapter() {
        	@Override
        	public void widgetSelected(SelectionEvent e) {
        		
        		
        		txtMessages.setText(list.getItem(list.getSelectionIndex()).toString());
        	}
        });
        
        Thread receiveMessageThread = new Thread(new Runnable() {
        	@Override
            public void run()
            {
                try
                {
                    String resposta1 = "";
                    ServerSocket welcomeSocket1 = new ServerSocket(9000);
                    Display display = null;
                    while(!resposta1.equals("null\n")){
                    	Socket connectionSocket1 = welcomeSocket1.accept();
                        BufferedReader inFromClient1 = new BufferedReader(new InputStreamReader(connectionSocket1.getInputStream()));
                        resposta1 = (new StringBuilder(String.valueOf(inFromClient1.readLine()))).append('\n').toString();
                        display = Display.getDefault();
                        ClientServerWindowMain.doUpdate(display, txtMessages, resposta1);
                    }

                    display = Display.getDefault();
                    ClientServerWindowMain.doUpdate(display, txtMessages, "Notebook saiu da conversa");
                    welcomeSocket1.close();
                }
                catch(Exception e)
                {
                	System.out.println("Catch: ");
                    e.printStackTrace();
                }
                System.out.println("Conversa encerrada!");
            }
        });
        receiveMessageThread.start();
	}
	
	private void enviarMensagem(JSONObject jsonObject)
    {
        if(txtSendMessage.getText().length() > 0)
        {
            //String sentence = "";
            try
            {
                clienteSocket = new Socket(jsonObject.get("target").toString(), 9000);
                outToServer = new DataOutputStream(clienteSocket.getOutputStream());
                //sentence = txtSendMessage.getText().toString();
                txtSendMessage.setText("");
                outToServer.writeBytes((new StringBuilder(String.valueOf(jsonObject))).append('\n').toString());
                txtMessages.append((new StringBuilder("Notebook: ")).append(jsonObject.get("content").toString()).append("\n\n").toString());
                txtSendMessage.setFocus();
                clienteSocket.close();
            }
            catch(Exception e1)
            {
                txtMessages.append("Ningu\351m conectado\n\n");
                txtSendMessage.setText("");
                txtSendMessage.setFocus();
            }
        }
    }
	
	private static void doUpdate(Display display, final Text target, final String value){
		display.asyncExec(new Runnable() {

			public void run(){
				if(!target.isDisposed()){
					target.append((new StringBuilder(String.valueOf(value))).append('\n').toString());
					target.getParent().layout();
				}
			}
		});
	}

	public static String getNickname() {
		return nickname;
	}
}