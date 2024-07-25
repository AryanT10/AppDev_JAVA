package views;

import java.io.IOException;

import controllers.addPartController;
import controllers.addProductController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import models.Inventory;

public class addProductView extends BaseView {

	private Stage st;
	private Inventory inventory;
	public addProductView(Stage st, Inventory inventory) {
		super(st, "addPartForm");
		this.st = st;
		this.inventory = inventory;
	}
	
	public Scene getScene(addProductController addProductControl) {
		BorderPane addProduct = null;
		try {
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/addProductForm.fxml"));
		    fxmlLoader.setController(addProductControl);
		    addProduct = fxmlLoader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return new Scene(addProduct);
	}
}
