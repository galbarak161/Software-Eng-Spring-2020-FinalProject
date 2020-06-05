package Client;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class App extends Application {

	private static Scene scene;
	private static Stage mainStage;

	@Override
	public void start(Stage stage) throws IOException {
		mainStage = stage;
		scene = new Scene(loadFXML("loginController"));
		mainStage.setTitle("Login");
		mainStage.setScene(scene);
		mainStage.getIcons().add(new Image(App.class.getResource("main_icon.png").toExternalForm()));
		mainStage.setResizable(false);
		mainStage.show();
	}

	static void changeStage(String fxml, String title) throws IOException {
		scene = new Scene(loadFXML(fxml));
    	Platform.runLater(() -> {
    		mainStage.close();
    		mainStage.setScene(scene);
    		mainStage.setTitle(title);
    		mainStage.show();
    	});
	}

	private static Parent loadFXML(String fxml) throws IOException {
		FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
		return fxmlLoader.load();
	}

	public static void main(String[] args) {
		launch();

	}

}