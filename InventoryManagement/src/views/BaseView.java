package views;

import java.io.IOException;

import controllers.HomeController;
import interfaces.ViewMaker;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class BaseView implements ViewMaker {

	private Stage st;
	private String lbl;
	
	public BaseView(Stage st, String lbl) {
		
		if(st == null) {
			throw new IllegalArgumentException("Stage can't be found or null");
		}
		this.st = st;
		this.lbl = lbl;
	}
	
	@Override
	public Scene getScene(HomeController homeController) {
		BorderPane home = null;
		try {
			home = (BorderPane)FXMLLoader.load(getClass().getResource("/views/Home.fxml"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return new Scene(home);
	}
}
