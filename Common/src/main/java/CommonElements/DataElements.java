package CommonElements;

import java.io.Serializable;

public class DataElements implements Serializable {

	private static final long serialVersionUID = -2986035827318115652L;

	// Opcodes 0-9
	public enum ClientToServerOpcodes {
		GetAllStudies(0), GetAllCoursesInStudy(1), GetAllQuestionInCourse(2), UpdateQuestion(3), GetAllQuestion(4),
		UserLogin(5), Error(-1);

		public int value;

		private ClientToServerOpcodes(int value) {
			this.value = value;
		}
	}

	// Opcodes 10-19
	public enum ServerToClientOpcodes {
		SendAllStudies(10), SendAllCoursesInStudy(11), SendAllQuestionInCourse(12), UpdateQuestionResult(13),
		SendAllQuestion(14), UserLoggedIn(15), Error(-1);

		public int value;

		private ServerToClientOpcodes(int value) {
			this.value = value;
		}
	}

	private ClientToServerOpcodes opcodeFromClient;
	private ServerToClientOpcodes opCodeFromServer;
	private Object data;

	public DataElements() {
		this.opcodeFromClient = ClientToServerOpcodes.Error;
		this.opCodeFromServer = ServerToClientOpcodes.Error;
		this.data = null;
	}

	public DataElements(ClientToServerOpcodes opCodeFromClient, Object data) {
		this.opcodeFromClient = opCodeFromClient;
		this.data = data;
	}

	public DataElements(ServerToClientOpcodes opCodeFromServer, Object data) {
		this.opCodeFromServer = opCodeFromServer;
		this.data = data;
	}

	public ClientToServerOpcodes getOpcodeFromClient() {
		return opcodeFromClient;
	}

	public void setOpcodeFromClient(ClientToServerOpcodes opcodeFromClient) {
		this.opcodeFromClient = opcodeFromClient;
	}

	public ServerToClientOpcodes getOpCodeFromServer() {
		return opCodeFromServer;
	}

	public void setOpCodeFromServer(ServerToClientOpcodes opCodeFromServer) {
		this.opCodeFromServer = opCodeFromServer;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

}