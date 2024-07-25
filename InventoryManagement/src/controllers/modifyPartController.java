package controllers;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.UUID;

import application.Main;
import application.SceneName;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;
import models.InHouse;
import models.Inventory;
import models.Outsourced;
import models.Part;

public class modifyPartController implements Initializable {

	private final Stage st;
	private final Inventory inventory;
	private int selectedIndex;
	private HomeController homeController;

	public modifyPartController(Stage st, Inventory inventory) {
		this.st = st;
		this.inventory = inventory;
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

	@FXML
	private TextField modifyID;

	public void setHomeController(HomeController homeController) {
		this.homeController = homeController;
	}

	public void fillDataFromTabel(Part part, int selectedIndex) {
		this.selectedIndex = selectedIndex;
		addPartName.setText(part.getName());
		addInvName.setText(String.valueOf(part.getStock()));
		addPriceName.setText(String.valueOf(part.getPrice()));
		addMaxName.setText(String.valueOf(part.getMax()));
		addMinName.setText(String.valueOf(part.getMin()));
		if (part instanceof InHouse) {
			InHouse inHousePart = (InHouse) part;
			addMachineName.setText(String.valueOf(inHousePart.getMachineId()));
		} else {
			Outsourced outsourcedPart = (Outsourced) part;
			addMachineName.setText(outsourcedPart.getCompanyName());
		}
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		addPartCancel.setOnAction(e -> {
			if (showConfirmationDialog("Cancel", "Are you sure you want to cancel?")) {
				st.setScene(Main.getScenes().get(SceneName.Home));
			}
		});
		addPartSave.setOnAction(e -> {
			try {
				validatePartData();

				if (inHouseRadio.isSelected()) {
					InHouse house = new InHouse(inventory.getAllParts().get(selectedIndex).getId(), addPartName.getText(), Double.parseDouble(addPriceName.getText()), Integer.parseInt(addInvName.getText()), Integer.parseInt(addMinName.getText()), Integer.parseInt(addMaxName.getText()), Integer.parseInt(addMachineName.getText()));
					inventory.updatePart(this.selectedIndex, house);
				} else if (outsourcesRadio.isSelected()) {
					Outsourced outsourced = new Outsourced(Integer.valueOf(modifyID.getText()), addPartName.getText(), Double.parseDouble(addPriceName.getText()), Integer.parseInt(addInvName.getText()), Integer.parseInt(addMinName.getText()), Integer.parseInt(addMaxName.getText()), addMachineName.getText());
					inventory.updatePart(this.selectedIndex, outsourced);
				}
				homeController.setPartList(inventory.getAllParts());
				st.setScene(Main.getScenes().get(SceneName.Home));
			} catch (IllegalArgumentException ex) {
				showErrorDialog("Error", ex.getMessage());
			}
		});
	}

	private void validatePartData() {
		String name = addPartName.getText();
		if (name == null || name.isEmpty()) {
			throw new IllegalArgumentException("Part name is required.");
		}

		double price = Double.parseDouble(addPriceName.getText());
		int stock = Integer.parseInt(addInvName.getText());
		int min = Integer.parseInt(addMinName.getText());
		int max = Integer.parseInt(addMaxName.getText());

		if (min >= max) {
			throw new IllegalArgumentException("Minimum value cannot be greater than or equal to maximum value.");
		}

		if (stock < min || stock > max) {
			throw new IllegalArgumentException("Inventory value must be between minimum and maximum values.");
		}
	}

	private boolean showConfirmationDialog(String title, String message) {
		Alert alert = new Alert(Alert.AlertType.CONFIRMATION, message, ButtonType.YES, ButtonType.NO);
		alert.setTitle(title);
		alert.setHeaderText(null);
		alert.showAndWait();
		return alert.getResult() == ButtonType.YES;
	}

	private void showErrorDialog(String title, String message) {
		Alert alert = new Alert(Alert.AlertType.ERROR, message, ButtonType.OK);
		alert.setTitle(title);
		alert.setHeaderText(null);
		alert.showAndWait();
	}
}
