package Client;

import java.io.IOException;

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
		try {
			switch (ClientMain.getUser().getUserType()) {
			case Student:
				System.out.println("UserType is Student");
				mainPanel.getChildren()
						.setAll((Node) FXMLLoader.load(getClass().getResource("studentController.fxml")));
				navigatePanel.getChildren()
				.setAll((Node) FXMLLoader.load(getClass().getResource("studentNavi.fxml")));
				break;
			case Teacher:
				System.out.println("UserType is Teacher");
				mainPanel.getChildren()
						.setAll((Node) FXMLLoader.load(getClass().getResource("teacherController.fxml")));
				navigatePanel.getChildren()
				.setAll((Node) FXMLLoader.load(getClass().getResource("teacherNavi.fxml")));
				break;
			case Principal:
				System.out.println("UserType is Principal");
				mainPanel.getChildren()
						.setAll((Node) FXMLLoader.load(getClass().getResource("principalController.fxml")));
				navigatePanel.getChildren()
				.setAll((Node) FXMLLoader.load(getClass().getResource("principalNavi.fxml")));
				break;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	void changeMain() {
	}

}
