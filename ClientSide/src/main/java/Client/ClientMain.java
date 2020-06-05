package Client;

import java.io.IOException;

import CloneEntities.*;

public class ClientMain {

	private static ClientService clientS;
	private static CloneUser current_user;

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

	public static void addController(Object con, String name) {
		if (!ClientService.controllers.containsKey(name)) {
			ClientService.controllers.keySet().add(name);
			ClientService.controllers.put(con,name);
		}
			
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
		// TODO: change to args[] later (ref in ClientMain of Prototype
		ClientService client = new ClientService("localhost", 3000);
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