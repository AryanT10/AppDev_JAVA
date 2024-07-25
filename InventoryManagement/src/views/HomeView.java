package views;

import java.io.IOException;

import controllers.HomeController;
import interfaces.ViewMaker;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import models.Inventory;

public class HomeView implements ViewMaker {
	
	private Stage st;
	private Inventory inventory;
	public HomeView(Stage st, Inventory inventory) {
		this.st = st;
		this.inventory = inventory;
	}
	
	public Scene getScene(HomeController homeController) {
		BorderPane root = null;
		try {
		    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/Home.fxml"));
		    fxmlLoader.setController(homeController);
		    root = fxmlLoader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return new Scene(root);
	}
}
