package com.window;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultListModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.UnsupportedLookAndFeelException;

import com.main.Message;
import com.main.SocketClient;

import java.awt.Toolkit;

public class ChatWindow extends JFrame {
	public SocketClient client;
	public Thread clientThread;
	public String serverAddr, username, password;
	public int port;
	public DefaultListModel model;
	public Background background = new Background();

	public ChatWindow() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(ChatWindow.class.getResource("/com/window/MetroUI_Live_Messenger_Alt2.png")));
		try {
//			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
		        if ("Nimbus".equals(info.getName())) {
		            UIManager.setLookAndFeel(info.getClassName());
		            break;
		        }
			}
		} catch (ClassNotFoundException | InstantiationException
				| IllegalAccessException | UnsupportedLookAndFeelException e1) {
			e1.printStackTrace();
		}
		setContentPane(new Background());
		setResizable(false);
		getContentPane().setBackground(new Color(238, 232, 170));
		setSize(new Dimension(800, 650));
		setTitle("Messenger");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		scrollPane = new JScrollPane();

		scrollPane_1 = new JScrollPane();

		lblServerAddress = new JLabel("Server Address :");
		lblServerAddress.setFont(new Font("Comic Sans MS", Font.BOLD, 11));

		textField = new JTextField();
		textField.setColumns(10);
		textField.setText("192.168.2.100");

		lblPort = new JLabel("Port :");
		lblPort.setFont(new Font("Comic Sans MS", Font.BOLD, 11));

		textField_1 = new JTextField();
		textField_1.setColumns(10);
		textField_1.setText("1234");

		btnConnect = new JButton("Connect");
		btnConnect.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnConnect.setFont(new Font("Felix Titling", Font.PLAIN, 12));
		btnConnect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				ConnectButton_actionPerformed(event);
			}
		});

		lblUserName = new JLabel("User name :");
		lblUserName.setFont(new Font("Comic Sans MS", Font.BOLD, 11));

		textField_2 = new JTextField();
		textField_2.setEnabled(false);
		textField_2.setColumns(10);

		lblPassword = new JLabel("Password :");
		lblPassword.setFont(new Font("Comic Sans MS", Font.BOLD, 11));

		passwordField = new JPasswordField();
		passwordField.setEnabled(false);

		btnLogIn = new JButton("Log In");
		btnLogIn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				LogIn_ActionPerformed(arg0);
			}
		});
		btnLogIn.setEnabled(false);
		btnLogIn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnLogIn.setFont(new Font("Felix Titling", Font.PLAIN, 12));
		

		btnSignUp = new JButton("Sign Up");
		btnSignUp.setEnabled(false);
		btnSignUp.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnSignUp.setFont(new Font("Felix Titling", Font.PLAIN, 12));
		btnSignUp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SignUp_ActionPerformed(e);
			}
		});

		btnSend = new JButton("Send");
		btnSend.setEnabled(false);
		btnSend.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnSend.setFont(new Font("Felix Titling", Font.PLAIN, 12));
		btnSend.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SendButton_actionPerformed(e);
			}
		});

		btnLogOut = new JButton("Log Out");
		btnLogOut.setEnabled(false);
		btnLogOut.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		 btnLogOut.addActionListener(new ActionListener() {
		 public void actionPerformed(ActionEvent ev) {
			 Logout_actionPerformed(ev);
		 }
		 });
		btnLogOut.setFont(new Font("Felix Titling", Font.PLAIN, 12));

		lblMesage = new JLabel("Mesage :");
		lblMesage.setFont(new Font("Comic Sans MS", Font.BOLD, 11));

		textField_3 = new JTextField();
		textField_3.setEnabled(false);
		textField_3.setColumns(10);
		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout
				.setHorizontalGroup(groupLayout
						.createParallelGroup(Alignment.LEADING)
						.addGroup(
								groupLayout
										.createSequentialGroup()
										.addContainerGap()
										.addGroup(
												groupLayout
														.createParallelGroup(
																Alignment.TRAILING)
														.addGroup(
																groupLayout
																		.createSequentialGroup()
																		.addGroup(
																				groupLayout
																						.createParallelGroup(
																								Alignment.LEADING)
																						.addComponent(
																								scrollPane,
																								GroupLayout.PREFERRED_SIZE,
																								534,
																								GroupLayout.PREFERRED_SIZE)
																						.addGroup(
																								groupLayout
																										.createSequentialGroup()
																										.addGroup(
																												groupLayout
																														.createParallelGroup(
																																Alignment.LEADING)
																														.addGroup(
																																groupLayout
																																		.createSequentialGroup()
																																		.addComponent(
																																				lblUserName)
																																		.addPreferredGap(
																																				ComponentPlacement.RELATED)
																																		.addComponent(
																																				textField_2,
																																				GroupLayout.PREFERRED_SIZE,
																																				266,
																																				GroupLayout.PREFERRED_SIZE))
																														.addGroup(
																																groupLayout
																																		.createSequentialGroup()
																																		.addComponent(
																																				lblServerAddress)
																																		.addPreferredGap(
																																				ComponentPlacement.RELATED)
																																		.addComponent(
																																				textField,
																																				GroupLayout.PREFERRED_SIZE,
																																				252,
																																				GroupLayout.PREFERRED_SIZE)))
																										.addGroup(
																												groupLayout
																														.createParallelGroup(
																																Alignment.LEADING)
																														.addGroup(
																																groupLayout
																																		.createSequentialGroup()
																																		.addGap(51)
																																		.addComponent(
																																				lblPort)
																																		.addPreferredGap(
																																				ComponentPlacement.RELATED)
																																		.addComponent(
																																				textField_1,
																																				GroupLayout.PREFERRED_SIZE,
																																				150,
																																				GroupLayout.PREFERRED_SIZE)
																																		.addGap(34)
																																		.addComponent(
																																				btnConnect))
																														.addGroup(
																																groupLayout
																																		.createSequentialGroup()
																																		.addGap(10)
																																		.addGroup(
																																				groupLayout
																																						.createParallelGroup(
																																								Alignment.TRAILING)
																																						.addComponent(
																																								scrollPane_1,
																																								GroupLayout.PREFERRED_SIZE,
																																								179,
																																								GroupLayout.PREFERRED_SIZE)
																																						.addGroup(
																																								groupLayout
																																										.createSequentialGroup()
																																										.addComponent(
																																												lblPassword)
																																										.addPreferredGap(
																																												ComponentPlacement.RELATED)
																																										.addComponent(
																																												passwordField,
																																												GroupLayout.PREFERRED_SIZE,
																																												158,
																																												GroupLayout.PREFERRED_SIZE)
																																										.addPreferredGap(
																																												ComponentPlacement.UNRELATED)
																																										.addComponent(
																																												btnLogIn)
																																										.addPreferredGap(
																																												ComponentPlacement.UNRELATED)
																																										.addComponent(
																																												btnSignUp)))))))
																		.addContainerGap(
																				24,
																				Short.MAX_VALUE))
														.addGroup(
																groupLayout
																		.createSequentialGroup()
																		.addGap(15)
																		.addComponent(
																				lblMesage)
																		.addPreferredGap(
																				ComponentPlacement.RELATED)
																		.addComponent(
																				textField_3,
																				GroupLayout.PREFERRED_SIZE,
																				453,
																				GroupLayout.PREFERRED_SIZE)
																		.addGap(90)
																		.addComponent(
																				btnSend)
																		.addGap(97))
														.addGroup(
																groupLayout
																		.createSequentialGroup()
																		.addComponent(
																				btnLogOut)
																		.addGap(331)))));
		groupLayout
				.setVerticalGroup(groupLayout
						.createParallelGroup(Alignment.LEADING)
						.addGroup(
								groupLayout
										.createSequentialGroup()
										.addGap(24)
										.addGroup(
												groupLayout
														.createParallelGroup(
																Alignment.BASELINE)
														.addComponent(
																lblServerAddress)
														.addComponent(
																textField,
																GroupLayout.PREFERRED_SIZE,
																GroupLayout.DEFAULT_SIZE,
																GroupLayout.PREFERRED_SIZE)
														.addComponent(lblPort)
														.addComponent(
																textField_1,
																GroupLayout.PREFERRED_SIZE,
																GroupLayout.DEFAULT_SIZE,
																GroupLayout.PREFERRED_SIZE)
														.addComponent(
																btnConnect))
										.addGap(38)
										.addGroup(
												groupLayout
														.createParallelGroup(
																Alignment.BASELINE)
														.addComponent(
																lblUserName)
														.addComponent(
																textField_2,
																GroupLayout.PREFERRED_SIZE,
																GroupLayout.DEFAULT_SIZE,
																GroupLayout.PREFERRED_SIZE)
														.addComponent(btnLogIn)
														.addComponent(
																lblPassword)
														.addComponent(
																passwordField,
																GroupLayout.PREFERRED_SIZE,
																GroupLayout.DEFAULT_SIZE,
																GroupLayout.PREFERRED_SIZE)
														.addComponent(btnSignUp))
										.addGap(28)
										.addGroup(
												groupLayout
														.createParallelGroup(
																Alignment.TRAILING,
																false)
														.addComponent(
																scrollPane_1)
														.addComponent(
																scrollPane,
																GroupLayout.DEFAULT_SIZE,
																270,
																Short.MAX_VALUE))
										.addGroup(
												groupLayout
														.createParallelGroup(
																Alignment.LEADING)
														.addGroup(
																groupLayout
																		.createSequentialGroup()
																		.addGap(123)
																		.addComponent(
																				btnLogOut))
														.addGroup(
																groupLayout
																		.createSequentialGroup()
																		.addGap(63)
																		.addGroup(
																				groupLayout
																						.createParallelGroup(
																								Alignment.BASELINE)
																						.addComponent(
																								lblMesage)
																						.addComponent(
																								textField_3,
																								GroupLayout.PREFERRED_SIZE,
																								38,
																								GroupLayout.PREFERRED_SIZE)
																						.addComponent(
																								btnSend))))
										.addContainerGap(59, Short.MAX_VALUE)));

		list = new JList();
		list.setEnabled(false);
		list.setModel((model = new DefaultListModel()));
		model.addElement("All");
		list.setSelectedIndex(0);
		scrollPane_1.setViewportView(list);
		textArea = new JTextArea();
		textArea.setEnabled(false);
		textArea.setEditable(false);
		scrollPane.setViewportView(textArea);
		getContentPane().setLayout(groupLayout);
		pack();
	}

	public void ConnectButton_actionPerformed(ActionEvent event) {
		serverAddr = textField.getText();
		port = Integer.parseInt(textField_1.getText());
		textField_2.setEnabled(true);
		passwordField.setEnabled(true);
		btnLogIn.setEnabled(true);
		btnSignUp.setEnabled(true);
		textArea.setEnabled(true);
		list.setEnabled(true);
		textField_3.setEnabled(true);
		btnSend.setEnabled(true);
		btnLogOut.setEnabled(true);
		
		if (!serverAddr.isEmpty() && !textField_1.getText().isEmpty()) {
			try {
				client = new SocketClient(this);
				clientThread = new Thread(client);
				clientThread.start();
				client.send(new Message("test", "testUser", "testContent",
						"SERVER"));
				JOptionPane.showMessageDialog(null, "Connection Successful.");
			} catch (Exception ex) {
				textArea.append("[Application > Me] : Server not found\n");
			}
		}

	}
	public void LogIn_ActionPerformed(ActionEvent e)
	{
		username = textField_2.getText();
		password = passwordField.getText();

		if (!username.isEmpty() && !password.isEmpty()) {
			client.send(new Message("signup", username, password, "SERVER"));
		}
	}

	public void SignUp_ActionPerformed(ActionEvent e) {
		username = textField_2.getText();
		password = passwordField.getText();

		if (!username.isEmpty() && !password.isEmpty()) {
			client.send(new Message("signup", username, password, "SERVER"));
		}
	}

	public void SendButton_actionPerformed(ActionEvent e) {
		String msg = textField_3.getText();
		String target = list.getSelectedValue().toString();

		if (!msg.isEmpty() && !target.isEmpty()) {
			textField_3.setText(" ");
			client.send(new Message("message", username, msg, target));
		}
	}
	public void Logout_actionPerformed(ActionEvent ev)
	{
		client.send(new Message("signout", username, "bye", "SERVER"));
	}

	public static void main(String args[]) {

		try {
			new ChatWindow().setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Variable list
	private JLabel lblServerAddress;
	private JLabel lblPort;
	private JLabel lblUserName;
	private JLabel lblPassword;
	private JLabel lblMesage;
	public JTextField textField;
	public JTextField textField_1;
	public JTextField textField_2;
	public JPasswordField passwordField;
	public JTextArea textArea;
	public JButton btnConnect;
	public JButton btnLogIn;
	public JButton btnSignUp;
	public JButton btnLogOut;
	public JButton btnSend;
	private JTextField textField_3;
	private JScrollPane scrollPane;
	private JScrollPane scrollPane_1;
	public JList list;
}

class Background extends JPanel {
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage((new ImageIcon(ChatWindow.class.getResource("itunes7Bg.jpg")))
				.getImage(), 0, 0, this.getSize().width, this.getSize().height,
				this);
	}
}
