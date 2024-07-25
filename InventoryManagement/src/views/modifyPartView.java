package views;

import java.io.IOException;

import controllers.addPartController;
import controllers.modifyPartController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import models.Inventory;

public class modifyPartView {

	private Stage st;
	private Inventory inventory;
	public modifyPartView(Stage st, Inventory inventory) {
		this.st = st;
		this.inventory = inventory;
	}
	
	public Scene getScene(modifyPartController modifyPartControl) {
		BorderPane addPart = null;
		try {
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/modifyPartForm.fxml"));
		    fxmlLoader.setController(modifyPartControl);
		    addPart = fxmlLoader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return new Scene(addPart);
	}
	
}
