package br.com.cts.ui;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

public class ClientServerWindowMain {
	protected Shell shlChat;
    private Text txtSendMessage;
    private Socket clienteSocket;
    private DataOutputStream outToServer;
    private Text txtMessages;

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
        shlChat.setSize(450, 300);
        shlChat.setText("Chat");
        txtSendMessage = new Text(shlChat, 2048);
        txtSendMessage.addKeyListener(new KeyAdapter() {

            public void keyReleased(KeyEvent e)
            {
                if(e.character == '\r')
                    enviarMensagem();
            }
        });
        
        txtSendMessage.setBounds(46, 147, 334, 21);
        Button btnSendMessage = new Button(shlChat, 0);
        btnSendMessage.addSelectionListener(new SelectionAdapter() {

            public void widgetSelected(SelectionEvent e)
            {
                enviarMensagem();
            }
        });
        
        btnSendMessage.setBounds(46, 172, 75, 25);
        btnSendMessage.setText("&Enviar");
        txtMessages = new Text(shlChat, 2626);
        txtMessages.setEditable(false);
        txtMessages.setBounds(45, 10, 335, 120);
        Thread servidorThread = new Thread(new Runnable() {

            public void run()
            {
                try
                {
                    String resposta1 = "";
                    ServerSocket welcomeSocket1 = new ServerSocket(4445);
                    Socket connectionSocket1 = welcomeSocket1.accept();
                    BufferedReader inFromClient1 = new BufferedReader(new InputStreamReader(connectionSocket1.getInputStream()));
                    Display display;
                    for(; !resposta1.equals("null\n"); ClientServerWindowMain.doUpdate(display, txtMessages, resposta1))
                    {
                        resposta1 = (new StringBuilder(String.valueOf(inFromClient1.readLine()))).append('\n').toString();
                        display = Display.getDefault();
                    }

                    display = Display.getDefault();
                    ClientServerWindowMain.doUpdate(display, txtMessages, "Notebook saiu da conversa");
                    welcomeSocket1.close();
                }
                catch(Exception e)
                {
                    e.printStackTrace();
                }
                System.out.println("Convesa encerrada!");
            }
        });
        servidorThread.start();
	}
	
	private void enviarMensagem()
    {
        if(txtSendMessage.getText().length() > 0)
        {
            String sentence = "";
            try
            {
                clienteSocket = new Socket("localhost", 4445);
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