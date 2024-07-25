package controllers;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;
import models.InHouse;
import models.Inventory;
import models.Outsourced;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.UUID;

import application.Main;
import application.SceneName; 

public class addPartController implements Initializable {

	private Stage st;
	private Inventory inventory;
	private HomeController homeController;
	
	public addPartController(Stage st, Inventory inventory, HomeController homeController) {
		this.st = st;
		this.inventory = inventory;
		this.homeController = homeController;
	}
	
    @FXML
    private TextField addInvName;

    @FXML
    private TextField addMachineName;

    @FXML
    private TextField addMaxName;

    @FXML
    private TextField addMinName;

    @FXML
    private Button addPartCancel;

    @FXML
    private TextField addPartName;

    @FXML
    private ToggleGroup addPartRadio;

    @FXML
    private Button addPartSave;

    @FXML
    private TextField addPriceName;

    @FXML
    private RadioButton inHouseRadio;

    @FXML
    private RadioButton outsourcesRadio;

	public void handleMouse(Event e) {
		st.setScene(Main.getScenes().get(SceneName.Home));
	}
	
	public void clear() {
		addInvName.clear();
		addMachineName.clear();
		addMaxName.clear();
		addMinName.clear();
		addPartName.clear();
		addPriceName.clear();
		inHouseRadio.setSelected(true);
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		addPartCancel.setOnAction(e->{
			this.clear();
			st.setScene(Main.getScenes().get(SceneName.Home));
		});
		addPartSave.setOnAction(e->{
			// Generating a random UUID
	        UUID uuid = UUID.randomUUID();
	        // Convert UUID to int
	        int uuidAsInt = uuid.hashCode();
			if (inHouseRadio.isSelected()) {
				InHouse house = new InHouse(uuidAsInt, addPartName.getText(), Double.parseDouble(addPriceName.getText()), Integer.parseInt(addInvName.getText()), Integer.parseInt(addMinName.getText()), Integer.parseInt(addMaxName.getText()), Integer.parseInt(addMachineName.getText()));
				inventory.addPart(house);
			} else if (outsourcesRadio.isSelected()) {
				Outsourced outsourced = new Outsourced(uuidAsInt, addPartName.getText(), Double.parseDouble(addPriceName.getText()), Integer.parseInt(addInvName.getText()), Integer.parseInt(addMinName.getText()), Integer.parseInt(addMaxName.getText()), addMachineName.getText());
				inventory.addPart(outsourced);
			}
			this.clear();
			homeController.setPartList(inventory.getAllParts());
			st.setScene(Main.getScenes().get(SceneName.Home));
		});
	}
}
