package Client;

import java.io.IOException;

public class ClientMain {

	private static ClientService clientS;
	private boolean isRunning;
	private Thread loopThread;

	public ClientMain(ClientService client) {
		ClientMain.clientS = client;
		this.isRunning = false;
	}

	public void mainLoopThread() throws IOException {
		loopThread = new Thread(new Runnable() {
			@Override
			public void run() {
			}
		});
		loopThread.start();
		this.isRunning = true;
	}

	public static int sendMessageToServer(Object message) throws IOException {
		try {
			clientS.sendToServer(message);
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
		return 1;
	}

	public void displayMessageOnConsole(Object message) {
		if (isRunning) {
			System.out.print("(Interrupted)\n");
		}
		System.out.println("Received message from server: " + message.toString());
	}

	public void closeConnection() {
		System.out.println("Connection closed.");
		System.exit(0);
	}

	public static void main(String[] args) throws IOException {
		if (args.length != 2) {
			System.out.println("Required arguments: <host> <port>");
		} else {
			String host = args[0];
			int port = Integer.parseInt(args[1]);

			ClientService client = new ClientService(host, port);
			try {
				client.openConnection();
				//App.main(args);
				System.out.println("Client up");
			} catch (Exception e) {
				System.out.println(e.getMessage());
			} finally {
				client.closeConnection();
			}
			

			
		}
	}
}