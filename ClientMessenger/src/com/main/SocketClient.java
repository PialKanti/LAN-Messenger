package com.main;

import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Date;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import com.window.*;
import com.sound.AleartSound;
import com.sound.ChatTime;
import com.logInOutSound.LogInAlert;
import com.logInOutSound.LogOutAlert;

public class SocketClient implements Runnable {

	public int port;
	public String serverAddr;
	public Socket socket;
	public ChatWindow ui;
	public ObjectInputStream In;
	public ObjectOutputStream Out;
	public AleartSound aleart=new AleartSound();
	public LogInAlert logal= new LogInAlert();
	public LogOutAlert log=new LogOutAlert();
	public ChatTime c=new ChatTime();
	String currentDate="<"+c.date()+">";
	String currentTime=c.time();

	public SocketClient(ChatWindow frame) throws IOException {
		ui = frame;
		this.serverAddr = ui.serverAddr;
		this.port = ui.port;
		socket = new Socket(InetAddress.getByName(serverAddr), port);

		Out = new ObjectOutputStream(socket.getOutputStream());
		Out.flush();
		In = new ObjectInputStream(socket.getInputStream());

	}

	@Override
	public void run() {
		boolean keepRunning = true;
		while (keepRunning) {
			try {
				Message msg = (Message) In.readObject();
				System.out.println("Incoming : " + msg.toString());

				if (msg.type.equals("message")) {
					if (msg.recipient.equals(ui.username)) {
						ui.textArea.append("[" + msg.sender + " > Me] : "
								+ msg.content + "\n");
						aleart=new AleartSound();
						aleart.start();
						System.out.println("sound running.");
            		}else if(msg.recipient.equals("All")){
            			ui.textArea.append("[" + msg.sender + " > "
								+ msg.recipient + "] : " + msg.content + "\n");
						aleart=new AleartSound();
						aleart.start();
						System.out.println("sound running.");
            			
            		}
					else {
						ui.textArea.append("[" + msg.sender + " > "
								+ msg.recipient + "] : " + msg.content + "\n");
						
					}

				} else if (msg.type.equals("login")) {
					if (msg.content.equals("TRUE")) {
//						ui.jButton2.setEnabled(false);
//						ui.jButton3.setEnabled(false);
//						ui.jButton4.setEnabled(true);
//						ui.jButton5.setEnabled(true);
						ui.textArea.append(currentDate+"\n");
						ui.textArea
								.append("[SERVER > Me] : Login Successful\n");
						logal=new LogInAlert();
						logal.start();
						//ui.jTextField3.setEnabled(false);
						//ui.jPasswordField1.setEnabled(false);
					} else {
						ui.textArea.append("[SERVER > Me] : Login Failed\n");
					}
				} else if (msg.type.equals("test")) {
//					ui.jButton1.setEnabled(false);
//					ui.jButton2.setEnabled(true);
//					ui.jButton3.setEnabled(true);
//					ui.jTextField3.setEnabled(true);
//					ui.jPasswordField1.setEnabled(true);
//					ui.jTextField1.setEditable(false);
//					ui.jTextField2.setEditable(false);
//					ui.jButton7.setEnabled(true);
				} else if (msg.type.equals("newuser")) {
					if (!msg.content.equals(ui.username)) {
						boolean exists = false;
						for (int i = 0; i < ui.model.getSize(); i++) {
							if (ui.model.getElementAt(i).equals(msg.content)) {
								exists = true;
								break;
							}
						}
						if (!exists) {
							ui.model.addElement(msg.content);
						}
					}
				} else if (msg.type.equals("signup")) {
					if (msg.content.equals("TRUE")) {
//						ui.jButton2.setEnabled(false);
//						ui.jButton3.setEnabled(false);
//						ui.jButton4.setEnabled(true);
//						ui.jButton5.setEnabled(true);
						ui.textArea
								.append("[SERVER > Me] : Singup Successful\n");
					} else {
						ui.textArea.append("[SERVER > Me] : Signup Failed\n");
					}
				} else if (msg.type.equals("signout")) {
					if (msg.content.equals(ui.username)) {
						ui.textArea.append("[" + msg.sender
								+ " > Me] : Bye\n");
						log=new LogOutAlert();
						log.start();
//						ui.jButton1.setEnabled(true);
//						ui.jButton4.setEnabled(false);
//						ui.jTextField1.setEditable(true);
//						ui.jTextField2.setEditable(true);

						for (int i = 0; i <=ui.model.size(); i++) {
							ui.model.removeElementAt(i);
						}

						ui.clientThread.stop();
					} else {
						ui.model.removeElement(msg.content);
						ui.textArea.append("[" + msg.sender + " > All] : "
								+ msg.content + " has signed out\n");
					}
				} else {
					ui.textArea
							.append("[SERVER > Me] : Unknown message type\n");
				}
			} catch (Exception ex) {
				keepRunning = false;
				ui.textArea
						.append("[Application > Me] : Connection Failure\n");
//				ui.jButton1.setEnabled(true);
//				ui.jTextField1.setEditable(true);
//				ui.jTextField2.setEditable(true);
//				ui.jButton4.setEnabled(false);
//				ui.jButton5.setEnabled(false);
//				ui.jButton5.setEnabled(false);

				for (int i = 0; i <= ui.model.size(); i++) {
					ui.model.removeElementAt(i);
				}

				ui.clientThread.stop();

				System.out.println("Exception SocketClient run()");
				ex.printStackTrace();
			}
		}
	}

	public void send(Message msg) {
		try {
			Out.writeObject(msg);
			Out.flush();
			System.out.println("Outgoing : " + msg.toString());

			if (msg.type.equals("message") && !msg.content.equals(".bye")) {
				String msgTime = (new Date()).toString();

			}
		} catch (Exception e) {
			System.out.println("Problem in SocketClient class send Method.");
			e.getMessage();

		}
	}

	public void closeThread(Thread t) {
		t = null;
	}
	
}
