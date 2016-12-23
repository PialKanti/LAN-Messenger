package serverMain;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.activation.MailcapCommandMap;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;

import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Toolkit;
import java.io.IOException;

public class DisplayServer extends JFrame {
	protected JLabel label;;
	protected JButton start;
	private int port = 1234;
	private JScrollPane scrollPane;
	public JTextArea displayArea;
	public Server ss;
	public DisplayServer()
	{
		super("Server");
		setIconImage(Toolkit.getDefaultToolkit().getImage(DisplayServer.class.getResource("/serverMain/mzm.kldaoeyt.png")));
		try {
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

		label=new JLabel("Welcome to Server.");
		label.setFont(new Font("Jokerman", Font.PLAIN, 11));
		label.setBounds(0, 4, 600, 10);
		start=new JButton("Start Connection");
		start.setBounds(0, 24, 200, 30);
		start.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					 ss=new Server(DisplayServer.this, port);
				} catch (Exception e){
					e.printStackTrace();
				}
				//ss.startServer();
				
			}
		});
		
		scrollPane = new JScrollPane();
		
		JButton btnStop = new JButton("Stop");
		btnStop.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					ss.stopServer();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			    
			}
		});
		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(235)
					.addComponent(label)
					.addContainerGap())
				.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 588, Short.MAX_VALUE)
				.addGroup(Alignment.LEADING, groupLayout.createSequentialGroup()
					.addGap(146)
					.addComponent(start)
					.addPreferredGap(ComponentPlacement.RELATED, 144, Short.MAX_VALUE)
					.addComponent(btnStop)
					.addGap(130))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(5)
					.addComponent(label)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 296, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(start)
						.addComponent(btnStop))
					.addContainerGap())
		);
		
		displayArea = new JTextArea();
		displayArea.setEditable(false);
		scrollPane.setViewportView(displayArea);
		getContentPane().setLayout(groupLayout);
		setSize(600,400);
		setVisible(true);
		pack();
	}
	public void ServerWindow()
	{
	}
	public static void main(String[] args)
	{
		DisplayServer display=new DisplayServer();
		//display.ServerWindow();
		display.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
