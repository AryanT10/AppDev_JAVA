package controllers;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.UUID;

import application.Main;
import application.SceneName;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import models.InHouse;
import models.Inventory;
import models.Outsourced;
import models.Part;
import models.Product;

public class modifyProductController implements Initializable {

	private Stage st;
	private Inventory inventory;
	private HomeController homeController;

	public modifyProductController(Stage st, Inventory inventory) {
		this.st = st;
		this.inventory = inventory;
	}

	@FXML
	private Button cancelProductBtn;

	@FXML
	private TableColumn<Part, Integer> AddPartID;

	@FXML
	private TableColumn<Part, Integer> AddPartInv;

	@FXML
	private TableColumn<Part, String> AddPartName;

	@FXML
	private TableColumn<Part, Double> AddPartPrice;

	@FXML
	private TableView<Part> AddPartTabel;

	@FXML
	private TableColumn<Part, Integer> ProductPartID;

	@FXML
	private TableColumn<Part, Integer> ProductPartInv;

	@FXML
	private TableColumn<Part, String> ProductPartName;

	@FXML
	private TableColumn<Part, Double> ProductPartPrice;

	@FXML
	private TableView<Part> ProductPartTabel;

	@FXML
	private Button ProductSave;

	@FXML
	private Button addPartBtn;

	@FXML
	private TextField productInv;

	@FXML
	private TextField productMax;

	@FXML
	private TextField productMin;

	@FXML
	private TextField productName;

	@FXML
	private TextField productPrice;

	@FXML
	private Button removePartBtn;

	public void setHomeController(HomeController homeController) {
		this.homeController = homeController;
	}

	private int selectedIndex;

	public void fillDataFromTabel(Product product, int selectedIndex) {
		this.selectedIndex = selectedIndex;
		productName.setText(product.getName());
		productInv.setText(String.valueOf(product.getStock()));
		productPrice.setText(String.valueOf(product.getPrice()));
		productMax.setText(String.valueOf(product.getMax()));
		productMin.setText(String.valueOf(product.getMin()));
	}

	private ObservableList<Part> allParts;
	private ObservableList<Part> allProductParts = FXCollections.observableArrayList();
	private Part transferPart;

	public void fillAddPartsTabel(ObservableList<Part> allParts) {
		ObservableList<Part> copiedPartsList = FXCollections.observableArrayList();

		for (Part part : allParts) {
			if (part instanceof InHouse) {
				copiedPartsList.add(new InHouse(part.getId(), part.getName(), part.getPrice(), part.getStock(), part.getMin(), part.getMax(), ((InHouse) part).getMachineId()));
			} else {
				copiedPartsList.add(new Outsourced(part.getId(), part.getName(), part.getPrice(), part.getStock(), part.getMin(), part.getMax(), ((Outsourced) part).getCompanyName()));
			}
		}

		this.allParts = copiedPartsList;
		AddPartID.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getId()).asObject());
		AddPartInv.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getStock()).asObject());
		AddPartName.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
		AddPartPrice.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getPrice()).asObject());

		if (this.allParts != null) {
			ObservableList<Part> dataList = FXCollections.observableArrayList(this.allParts);
			AddPartTabel.setItems(dataList);
		}
	}

	public void fillProductPartsTabel(ObservableList<Part> allParts) {
		ObservableList<Part> copiedPartsList = FXCollections.observableArrayList();

		for (Part part : allParts) {
			if (part instanceof InHouse) {
				copiedPartsList.add(new InHouse(part.getId(), part.getName(), part.getPrice(), part.getStock(), part.getMin(), part.getMax(), ((InHouse) part).getMachineId()));
			} else {
				copiedPartsList.add(new Outsourced(part.getId(), part.getName(), part.getPrice(), part.getStock(), part.getMin(), part.getMax(), ((Outsourced) part).getCompanyName()));
			}
		}

		this.allProductParts = copiedPartsList;
		ProductPartID.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getId()).asObject());
		ProductPartInv.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getStock()).asObject());
		ProductPartName.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
		ProductPartPrice.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getPrice()).asObject());

		if (this.allProductParts != null) {
			ObservableList<Part> dataList = FXCollections.observableArrayList(this.allProductParts);
			ProductPartTabel.setItems(dataList);
		}
	}

	public void selectedPart(int selectedIndex) {
		this.selectedIndex = selectedIndex;
		this.transferPart = allParts.get(selectedIndex);
	}

	public void selectedProductPart(int selectedIndex) {
		this.selectedIndex = selectedIndex;
		this.transferPart = allProductParts.get(selectedIndex);
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		AddPartTabel.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
			if (newValue.intValue() >= 0) {
				int selectedIndex = newValue.intValue();
				selectedPart(selectedIndex);
			}
		});
		ProductPartTabel.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
			if (newValue.intValue() >= 0) {
				int selectedIndex = newValue.intValue();
				selectedProductPart(selectedIndex);
			}
		});
		addPartBtn.setOnAction(e -> {
			allProductParts.add(transferPart);
			ProductPartID.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getId()).asObject());
			ProductPartInv.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getStock()).asObject());
			ProductPartName.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
			ProductPartPrice.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getPrice()).asObject());

			if (allProductParts != null) {
				ObservableList<Part> dataList = FXCollections.observableArrayList(allProductParts);
				ProductPartTabel.setItems(dataList);
				allParts.remove(selectedIndex);
				this.fillAddPartsTabel(allParts);
			}
		});
		removePartBtn.setOnAction(e -> {
			allParts.add(transferPart);
			AddPartID.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getId()).asObject());
			AddPartInv.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getStock()).asObject());
			AddPartName.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
			AddPartPrice.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getPrice()).asObject());

			if (allParts != null) {
				ObservableList<Part> dataList = FXCollections.observableArrayList(allParts);
				AddPartTabel.setItems(dataList);
				allProductParts.remove(selectedIndex);
				this.fillProductPartsTabel(allProductParts);
			}
		});
		cancelProductBtn.setOnAction(e -> {
			if (showConfirmationDialog("Cancel", "Are you sure you want to cancel?")) {
				st.setScene(Main.getScenes().get(SceneName.Home));
			}
		});
		ProductSave.setOnAction(e -> {
			try {
				validateProductData();

				Product product = new Product(inventory.getAllProducts().get(selectedIndex).getId(), productName.getText(), Double.parseDouble(productPrice.getText()), Integer.parseInt(productInv.getText()), Integer.parseInt(productMin.getText()), Integer.parseInt(productMax.getText()));

				for (Part part : allProductParts) {
					product.addAssociatedPart(part);
				}

				if (allParts.isEmpty()) {
					inventory.getAllParts().clear();
				} else {
					inventory.getAllParts().clear();
					inventory.getAllParts().addAll(this.allParts);
				}

				inventory.updateProduct(this.selectedIndex, product);
				homeController.setPartList(inventory.getAllParts());
				homeController.setProductList(inventory.getAllProducts());
				st.setScene(Main.getScenes().get(SceneName.Home));
			} catch (IllegalArgumentException ex) {
				showErrorDialog("Error", ex.getMessage());
			}
		});
	}

	private void validateProductData() {
		String name = productName.getText();
		if (name == null || name.isEmpty()) {
			throw new IllegalArgumentException("Product name is required.");
		}

		double price = Double.parseDouble(productPrice.getText());
		int stock = Integer.parseInt(productInv.getText());
		int min = Integer.parseInt(productMin.getText());
		int max = Integer.parseInt(productMax.getText());

		if (min >= max) {
			throw new IllegalArgumentException("Minimum value cannot be greater than or equal to maximum value.");
		}

		if (stock < min || stock > max) {
			throw new IllegalArgumentException("Inventory value must be between minimum and maximum values.");
		}

		if (allProductParts.isEmpty()) {
			throw new IllegalArgumentException("Product must have at least one part.");
		}

		double totalPartCost = allProductParts.stream().mapToDouble(Part::getPrice).sum();
		if (price < totalPartCost) {
			throw new IllegalArgumentException("Product price cannot be less than the total cost of its parts.");
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
