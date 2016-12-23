package serverMain;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

import com.main.Message;

public class Server implements Runnable {

	public ClientClass clients[];
	public ServerSocket server = null;
	public Thread thread = null;
	public int clientCount = 0;
	public int port;
	public DisplayServer ui;

	public Server(DisplayServer frame, int Port) {

		clients = new ClientClass[50];
		ui = frame;
		port = Port;

		try {
			server = new ServerSocket(port);
			ui.displayArea.append("Server startet. IP : "
					+ InetAddress.getLocalHost() + ", Port : "
					+ server.getLocalPort());

			thread = new Thread(this);
			thread.start();

		} catch (IOException ioe) {
			ui.displayArea.append("\nCan not bind to port " + port + ": "
					+ ioe.getMessage());
		}
	}

	public void run() {
		while (true) {
			try {
				ui.displayArea.append("\nWaiting for a client ...");
				addThread(server.accept());
			} catch (Exception ioe) {
				ui.displayArea.append("\nServer accept error: \n");
			}
		}
	}

	@SuppressWarnings("deprecation")
	public void stopServer() throws IOException {
		if (thread != null) {
			thread.stop();
			server.close();
			server = null;
			thread = null;
			ui.displayArea.append("\nServer has stopped.\n");
			
		}
	}

	private int findClient(int ID) {
		for (int i = 0; i < clientCount; i++) {
			if (clients[i].getID() == ID) {
				return i;
			}
		}
		return -1;
	}

	public synchronized void handle(int ID, Message msg) {
		if (msg.content.equals("bye")) {
			Announce("signout", "SERVER", msg.sender);
			remove(ID);
		} else {

			if (msg.type.equals("login")) {
				if (findUserThread(msg.sender) == null) {

					clients[findClient(ID)].username = msg.sender;
					clients[findClient(ID)].send(new Message("login", "SERVER",
							"TRUE", msg.sender));
					Announce("newuser", "SERVER", msg.sender);
					SendUserList(msg.sender);

				} else {
					clients[findClient(ID)].send(new Message("login", "SERVER",
							"FALSE", msg.sender));
				}
			} else if (msg.type.equals("message")) {
				if (msg.recipient.equals("All")) {
					Announce("message", msg.sender, msg.content);
				} else {

					findUserThread(msg.recipient).send(
							new Message(msg.type, msg.sender, msg.content,
									msg.recipient));
					clients[findClient(ID)].send(new Message(msg.type,
							msg.sender, msg.content, msg.recipient));
				}
			} else if (msg.type.equals("test")) {
				clients[findClient(ID)].send(new Message("test", "SERVER",
						"OK", msg.sender));
			} else if (msg.type.equals("signup")) {
				if (findUserThread(msg.sender) == null) {

					clients[findClient(ID)].username = msg.sender;
					clients[findClient(ID)].send(new Message("login", "SERVER",
							"TRUE", msg.sender));
					Announce("newuser", "SERVER", msg.sender);
					SendUserList(msg.sender);

				} else {
					clients[findClient(ID)].send(new Message("login", "SERVER",
							"FALSE", msg.sender));
				}

			} else if (msg.type.equals("upload_req")) {
				if (msg.recipient.equals("All")) {
					clients[findClient(ID)].send(new Message("message",
							"SERVER", "Uploading to 'All' forbidden",
							msg.sender));
				} else {
					findUserThread(msg.recipient).send(
							new Message("upload_req", msg.sender, msg.content,
									msg.recipient));
				}
			} else if (msg.type.equals("upload_res")) {
				if (!msg.content.equals("NO")) {
					String IP = findUserThread(msg.sender).socket
							.getInetAddress().getHostAddress();
					findUserThread(msg.recipient).send(
							new Message("upload_res", IP, msg.content,
									msg.recipient));
				} else {
					findUserThread(msg.recipient).send(
							new Message("upload_res", msg.sender, msg.content,
									msg.recipient));
				}
			}
		}
	}

	public void Announce(String type, String sender, String content) {
		Message msg = new Message(type, sender, content, "All");
		for (int i = 0; i < clientCount; i++) {
			clients[i].send(msg);
		}
	}

	public void SendUserList(String toWhom) {
		for (int i = 0; i < clientCount; i++) {
			findUserThread(toWhom).send(
					new Message("newuser", "SERVER", clients[i].username,
							toWhom));
		}
	}

	public ClientClass findUserThread(String usr) {
		for (int i = 0; i < clientCount; i++) {
			if (clients[i].username.equals(usr)) {
				return clients[i];
			}
		}
		return null;
	}

	@SuppressWarnings("deprecation")
	public synchronized void remove(int ID) {
		int pos = findClient(ID);
		if (pos >= 0) {
			ClientClass toTerminate = clients[pos];
			ui.displayArea.append("\nRemoving client thread " + ID + " at "
					+ pos+"\n");
			if (pos < clientCount - 1) {
				for (int i = pos + 1; i < clientCount; i++) {
					clients[i - 1] = clients[i];
				}
			}
			clientCount--;
			try {
				toTerminate.close();
			} catch (IOException ioe) {
				ui.displayArea.append("\nError closing thread: " + ioe);
			}
			toTerminate.stop();
		}
	}

	private void addThread(Socket socket) {
		if (clientCount < clients.length) {
			ui.displayArea.append("\nClient accepted: " + socket);
			clients[clientCount] = new ClientClass(this, socket);
			try {
				clients[clientCount].open();
				clients[clientCount].start();
				clientCount++;
			} catch (IOException ioe) {
				ui.displayArea.append("\nError opening thread: " + ioe);
			}
		} else {
			ui.displayArea.append("\nClient refused: maximum " + clients.length
					+ " reached.");
		}
	}

	/**
	 * Inner ServerThread Class
	 * 
	 * @author Pial
	 *
	 */
	class ClientClass extends Thread {

		public Server server = null;
		public Socket socket = null;
		public int ID = -1;
		public String username = "";
		public ObjectInputStream streamIn = null;
		public ObjectOutputStream streamOut = null;
		public DisplayServer ui;

		public ClientClass(Server _server, Socket _socket) {
			super();
			server = _server;
			socket = _socket;
			ID = socket.getPort();
			ui = _server.ui;
		}

		public void send(Message msg) {
			try {
				streamOut.writeObject(msg);
				streamOut.flush();
			} catch (IOException ex) {
				System.out.println("Exception [SocketClient : send(...)]");
			}
		}

		public int getID() {
			return ID;
		}

		@SuppressWarnings("deprecation")
		public void run() {
			ui.displayArea.append("\nServer Thread " + ID + " running.");
			while (true) {
				try {
					Message msg = (Message) streamIn.readObject();
					server.handle(ID, msg);
				} catch (Exception ioe) {
					ioe.printStackTrace();
					System.out.println(ID + " ERROR reading: "
							+ ioe.getMessage());
					System.out.println("problem in runServer");
					server.remove(ID);
					stop();
				}
			}
		}

		public void open() throws IOException {
			streamOut = new ObjectOutputStream(socket.getOutputStream());
			streamOut.flush();
			streamIn = new ObjectInputStream(socket.getInputStream());
		}

		public void close() throws IOException {
			if (socket != null)
				socket.close();
			if (streamIn != null)
				streamIn.close();
			if (streamOut != null)
				streamOut.close();
		}
	}
}
