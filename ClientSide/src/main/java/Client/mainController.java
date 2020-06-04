package Client;

import java.io.IOException;
import com.jfoenix.controls.JFXListView;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;

public class mainController {

	@FXML
	private AnchorPane mainPanel;

	@FXML
	private AnchorPane navigatePanel;

	public void initialize() {
		ClientMain.addController(this);
		switch (ClientMain.getUser().getUserType()) {
		case Student:
			System.out.println("UserType is Student");
			setMainPanel("studentController.fxml");
			setNaviPanel("studentNavi.fxml");
			break;
		case Teacher:
			System.out.println("UserType is Teacher");
			setMainPanel("teacherController.fxml");
			setNaviPanel("teacherNavi.fxml");
			break;
		case Principal:
			System.out.println("UserType is Principal");
			setMainPanel("principalController.fxml");
			setNaviPanel("principalNavi.fxml");
			break;
		}
	}

	public void setMainPanel(String fxml) {
		try {
			mainPanel.getChildren().clear();
			mainPanel.getChildren().setAll((Node) FXMLLoader.load(getClass().getResource(fxml)));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void setNaviPanel(String fxml) {
		try {
			navigatePanel.getChildren().clear();
			navigatePanel.getChildren().setAll((Node) FXMLLoader.load(getClass().getResource(fxml)));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
