package Client;

import java.io.IOException;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;

public abstract class AbstractNavi {
	void switchMainPanel(String Sfxml) {
		Object o = ClientService.getController(mainController.class);
		if (!o.getClass().equals(String.class)) {
			Platform.runLater(() -> {
				((mainController) o).setMainPanel(Sfxml);
			});
		}
	}
	
    @FXML
    void logout(MouseEvent event) {
    	ClientMain.removeAllControllers();
    	try {
			App.changeStage("loginController", "login");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}
