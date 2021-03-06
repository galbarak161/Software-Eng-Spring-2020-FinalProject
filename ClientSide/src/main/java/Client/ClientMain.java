package Client;

import java.io.IOException;

import CloneEntities.*;

public class ClientMain {

	private static ClientService clientS;
	private static CloneUser current_user = null;
	

	public ClientMain(ClientService client) {
		ClientMain.clientS = client;
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

	public static void addController(String name, Object con) {
		if (!ClientService.controllers.containsKey(name)) {
			ClientService.controllers.put(name,con);
		}
			
	}
	
	public static void setCurrController(String name) {
			ClientService.controllers.put("curr",name);
			
	}
	
	public static void removeAllControllers() {
		ClientService.controllers.clear();
	}

	public void displayMessageOnConsole(Object message) {
		System.out.println("Received message from server: " + message.toString());
	}

	public void closeConnection() {
		System.out.println("Connection closed.");
		System.exit(0);
	}
	
	public static CloneUser getUser() {
		return current_user;
	}
	
	public static void setUser(CloneUser user) {
		current_user = user;
	}

	public static void main(String[] args) throws IOException {
		// TODO: change to args[] later (ref in ClientMain of Prototype)
		ClientService client;
		if (args.length != 2) {
			System.out.println("Required arguments: <host> <port>");
			return;
		} else {
			String host = args[0];
			int port = Integer.parseInt(args[1]);
			client = new ClientService(host, port);
		}
		try {
			client.openConnection();
			App.main(args);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			client.closeConnection();
		}

	}
}