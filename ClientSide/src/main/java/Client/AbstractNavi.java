package Client;

import java.io.IOException;

import UtilClasses.DataElements;
import UtilClasses.DataElements.ClientToServerOpcodes;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;

public abstract class AbstractNavi {
	void switchMainPanel(String Sfxml) {
		((mainController) ClientService.getController("mainController")).setMainPanel(Sfxml);
	}

	@FXML
	void logout(MouseEvent event) {
		int status;
		ClientMain.removeAllControllers();
		try {
			App.changeStage("loginController", "login");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			status = ClientMain.sendMessageToServer(new DataElements(ClientToServerOpcodes.UserLogOut,ClientMain.getUser()));
		} catch (IOException e) {
			status = -1;
			e.printStackTrace();
		}
	}
}
