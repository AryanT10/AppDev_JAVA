package views;

import java.io.IOException;

import controllers.addPartController;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import models.Inventory;

public class addPartView extends BaseView {

	private Stage st;
	private Inventory inventory;
	public addPartView(Stage st, Inventory inventory) {
		super(st, "addPartForm");
		this.st = st;
		this.inventory = inventory;
	}
	
	public Scene getScene(addPartController addPartControl) {
		BorderPane addPart = null;
		try {
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/addPartForm.fxml"));
		    fxmlLoader.setController(addPartControl);
		    addPart = fxmlLoader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return new Scene(addPart);
	}

}
