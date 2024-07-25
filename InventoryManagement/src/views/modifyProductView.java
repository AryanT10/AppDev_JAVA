package views;

import java.io.IOException;

import controllers.modifyPartController;
import controllers.modifyProductController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import models.Inventory;

public class modifyProductView extends BaseView {

	private Stage st;
	private Inventory inventory;
	public modifyProductView(Stage st, Inventory inventory) {
		super(st, "modifyProductForm");
		this.st = st;
		this.inventory = inventory;
	}
	
	public Scene getScene(modifyProductController modifyProductControl) {
		BorderPane addPart = null;
		try {
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/modifyProductForm.fxml"));
		    fxmlLoader.setController(modifyProductControl);
		    addPart = fxmlLoader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return new Scene(addPart);
	}
}
