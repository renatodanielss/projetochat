package br.edu.fatec.ui;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

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

public class ClientServerWindowMain {
	protected Shell shlChat;
    private Text txtSendMessage;
    private Socket clienteSocket;
    private DataOutputStream outToServer;
    private Text txtMessages;
    private List list;

	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
		try {
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
		shlChat = new Shell();
        shlChat.setSize(714, 300);
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
        
        txtSendMessage.setBounds(45, 197, 334, 21);
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
        
        btnSendMessage.setBounds(45, 222, 75, 25);
        btnSendMessage.setText("&Enviar");
        txtMessages = new Text(shlChat, 2626);
        txtMessages.setEditable(false);
        txtMessages.setBounds(45, 10, 335, 171);
        
        list = new List(shlChat, SWT.BORDER);
        
        User user = new User();
		user.setNickname("Renato");
		user.setAddress("223.139.219.104");
		
		User user2 = new User();
		user2.setNickname("Mayara");
		user2.setAddress("223.139.219.104");
		
		JSONObject jsonUser = new JSONObject(user);
		JSONObject jsonUser2 = new JSONObject(user2);
		
        JSONArray users = new JSONArray();
        
        users.put(jsonUser);
        users.put(jsonUser2);
        
        System.out.println(users);
        
        for (int i = 0; i < users.length(); i++){
        	JSONObject jsonUsers = users.getJSONObject(i);
        	//System.out.println(jsonSays);
        	list.add(jsonUsers.get("nickname").toString() + " - " + jsonUsers.get("address").toString());
        }
        
        //list.setItems(new String[] {"Teste 1", "Teste 2", "Teste 3"});
        list.setBounds(398, 10, 278, 171);
        list.addSelectionListener(new SelectionAdapter() {
        	@Override
        	public void widgetSelected(SelectionEvent e) {
        		
        		
        		txtMessages.setText(list.getItem(list.getSelectionIndex()).toString());
        	}
        });
        
        Thread servidorThread = new Thread(new Runnable() {

            public void run()
            {
                try
                {
                    String resposta1 = "";
                    ServerSocket welcomeSocket1 = new ServerSocket(4445);
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
        servidorThread.start();
	}
	
	private void enviarMensagem(JSONObject jsonObject)
    {
        if(txtSendMessage.getText().length() > 0)
        {
            String sentence = "";
            try
            {
                clienteSocket = new Socket(jsonObject.get("target").toString(), 4445);
                outToServer = new DataOutputStream(clienteSocket.getOutputStream());
                sentence = txtSendMessage.getText().toString();
                txtSendMessage.setText("");
                outToServer.writeBytes((new StringBuilder(String.valueOf(sentence))).append('\n').toString());
                txtMessages.append((new StringBuilder("Notebook: ")).append(sentence).append("\n\n").toString());
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
}