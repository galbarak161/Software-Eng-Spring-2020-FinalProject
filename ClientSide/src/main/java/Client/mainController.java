package Client;

import java.io.IOException;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

public class mainController {

	@FXML
	private AnchorPane mainPanel;

	@FXML
	private AnchorPane navigatePanel;

	@FXML
	private BorderPane borderPane;

	public void initialize() {
		ClientMain.addController(this.getClass().toString().split("Client.")[1], this);
		switch (ClientMain.getUser().getUserType()) {
		case Student:
			setMainPanel("studentController.fxml");
			setNaviPanel("studentNavi.fxml");
			break;
		case Teacher:
			setMainPanel("teacherController.fxml");
			setNaviPanel("teacherNavi.fxml");
			break;
		case Principal:
			setMainPanel("principalController.fxml");
			setNaviPanel("principalNavi.fxml");
			break;
		}
	}

	public void setMainPanel(String fxml) {
		Platform.runLater(() -> {
			try {
				mainPanel.getChildren().setAll((Node) FXMLLoader.load(getClass().getResource(fxml)));
			} catch (IOException e) {
				e.printStackTrace();
			}
		});
	}

	public void setNaviPanel(String fxml) {
		Platform.runLater(() -> {
			navigatePanel.getChildren().clear();
			try {
				navigatePanel.getChildren().setAll((Node) FXMLLoader.load(getClass().getResource(fxml)));
			} catch (IOException e) {
				e.printStackTrace();
			}
		});
	}

}
