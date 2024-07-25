package controllers;

import java.net.URL;
import java.util.Iterator;
import java.util.ResourceBundle;
import java.util.UUID;

import application.Main;
import application.SceneName;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import models.InHouse;
import models.Inventory;
import models.Outsourced;
import models.Part;
import models.Product;

public class addProductController implements Initializable {

	private Stage st;
	private Inventory inventory;
	private HomeController homeController;
	
	public addProductController(Stage st, Inventory inventory, HomeController homeController) {
		this.st = st;
		this.inventory = inventory;
		this.homeController = homeController;
	}
	
	@FXML
	private Button cancelProductBtn;
	
	@FXML
	private Button SaveProductBtn;
	
	@FXML
	private Button ProductAddBtn;
	
	@FXML
	private Button RemovePartBtn;
	
    @FXML
    private TableColumn<Part, Integer> InvPartID;

    @FXML
    private TableColumn<Part, Integer> InvPartInv;

    @FXML
    private TableColumn<Part, String> InvPartName;

    @FXML
    private TableColumn<Part, Double> InvPartPrice;

    @FXML
    private TableView<Part> InvPartTabel;

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
    private TextField productInv;

    @FXML
    private TextField productMax;

    @FXML
    private TextField productMin;

    @FXML
    private TextField productName;

    @FXML
    private TextField productPrice;

	public void handleMouse(Event e) {
		st.setScene(Main.getScenes().get(SceneName.Home));
	}

	public void clear() {
		productName.clear();
		productInv.clear();
		productPrice.clear();
		productMax.clear();
		productMin.clear();
		InvPartTabel.getItems().clear();
		ProductPartTabel.getItems().clear();
		allProductParts = FXCollections.observableArrayList();
	}
	
	public void fillAddPartsTabel(ObservableList<Part> allParts) {
		// Create a new ObservableList to store a copy of allParts
        ObservableList<Part> copiedPartsList = FXCollections.observableArrayList();

        // Copy all elements from allParts to copiedPartsList
        for (Part part : allParts) {
        	if (part instanceof InHouse) {
        		copiedPartsList.add(new InHouse(part.getId(), part.getName(), part.getPrice(), part.getStock(), part.getMin(), part.getMax(), ((InHouse) part).getMachineId()));
        	}
        	else {
        		copiedPartsList.add(new Outsourced(part.getId(), part.getName(), part.getPrice(), part.getStock(), part.getMin(), part.getMax(), ((Outsourced) part).getCompanyName()));
        	}
        }
        
        // Set this.allParts to reference the copied list
        this.allParts = copiedPartsList;
		// Initialize columns
		InvPartID.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getId()).asObject());
		InvPartInv.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getStock()).asObject());
		InvPartName.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
		InvPartPrice.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getPrice()).asObject());
		// Convert array to ObservableList
		if (this.allParts != null) {
			ObservableList<Part> dataList = FXCollections.observableArrayList(this.allParts);
			InvPartTabel.setItems(dataList);
		}
		else {
			System.out.println("The loanList is empty");
		}
    }
	public void fillProductPartsTabel(ObservableList<Part> allParts) {
		// Create a new ObservableList to store a copy of allParts
        ObservableList<Part> copiedPartsList = FXCollections.observableArrayList();

        // Copy all elements from allParts to copiedPartsList
        for (Part part : allParts) {
        	if (part instanceof InHouse) {
        		copiedPartsList.add(new InHouse(part.getId(), part.getName(), part.getPrice(), part.getStock(), part.getMin(), part.getMax(), ((InHouse) part).getMachineId()));
        	}
        	else {
        		copiedPartsList.add(new Outsourced(part.getId(), part.getName(), part.getPrice(), part.getStock(), part.getMin(), part.getMax(), ((Outsourced) part).getCompanyName()));
        	}
        }
        
        // Set this.allParts to reference the copied list
        this.allProductParts = copiedPartsList;
		// Initialize columns
        ProductPartID.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getId()).asObject());
        ProductPartInv.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getStock()).asObject());
        ProductPartName.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
        ProductPartPrice.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getPrice()).asObject());
		// Convert array to ObservableList
		if (this.allProductParts != null) {
			ObservableList<Part> dataList = FXCollections.observableArrayList(this.allProductParts);
			ProductPartTabel.setItems(dataList);
		}
		else {
			System.out.println("The loanList is empty");
		}
    }
	private Part transferPart;
	private ObservableList<Part> allParts;
	private ObservableList<Part> allProductParts = FXCollections.observableArrayList();
	private int selectedIndex = -1;
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
		InvPartTabel.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue)->{
			if (newValue.intValue() >= 0) {
				int selectedIndex = newValue.intValue();
				selectedPart(selectedIndex);
			}
		});
		ProductPartTabel.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue)->{
			if (newValue.intValue() >= 0) {
				int selectedIndex = newValue.intValue();
				selectedProductPart(selectedIndex);
			}
		});
		
		ProductAddBtn.setOnAction(e->{
			// Initialize columns
			allProductParts.add(transferPart);
			ProductPartID.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getId()).asObject());
			ProductPartInv.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getStock()).asObject());
			ProductPartName.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
			ProductPartPrice.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getPrice()).asObject());
			// Convert array to ObservableList
			if (allProductParts != null) {
				ObservableList<Part> dataList = FXCollections.observableArrayList(allProductParts);
				ProductPartTabel.setItems(dataList);
				allParts.remove(selectedIndex);
				this.fillAddPartsTabel(allParts);
			}
			else {
				System.out.println("The loanList is empty");
			}
		});
		RemovePartBtn.setOnAction(e->{
			// Initialize columns
			allParts.add(transferPart);
			InvPartID.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getId()).asObject());
			InvPartInv.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getStock()).asObject());
			InvPartName.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
			InvPartPrice.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getPrice()).asObject());
			// Convert array to ObservableList
			if (allParts != null) {
				ObservableList<Part> dataList = FXCollections.observableArrayList(allParts);
				InvPartTabel.setItems(dataList);
				allProductParts.remove(selectedIndex);
				this.fillProductPartsTabel(allProductParts);
			}
			else {
				System.out.println("The loanList is empty");
			}
		});
		cancelProductBtn.setOnAction(e->{
			this.clear();
			st.setScene(Main.getScenes().get(SceneName.Home));
		});
		SaveProductBtn.setOnAction(e->{
			// Generating a random UUID
	        UUID uuid = UUID.randomUUID();
	        // Convert UUID to int
	        int uuidAsInt = uuid.hashCode();
	        Product product = new Product(uuidAsInt, productName.getText(), Double.parseDouble(productPrice.getText()), Integer.parseInt(productInv.getText()), Integer.parseInt(productMin.getText()), Integer.parseInt(productMax.getText()));
			for (int i = 0; i < allProductParts.size(); i++) {
				product.addAssociatedPart(allProductParts.get(i));
			}
			if (allParts.isEmpty()) {
			    inventory.getAllParts().removeAll(inventory.getAllParts());
			} else {
				ObservableList<Part> partsToRemove = FXCollections.observableArrayList();

				// Iterate over inventory parts
				for (Part inventoryPart : inventory.getAllParts()) {
				    // Check if the ID of the inventory part exists in allParts
				    boolean idExistsInAllParts = allParts.stream().anyMatch(allPart -> allPart.getId() == inventoryPart.getId());
				    
				    // If the ID exists in allParts, add this part to the list of parts to remove
				    if (!idExistsInAllParts) {
				        partsToRemove.add(inventoryPart);
				    }
				}

				// Remove all parts found in the partsToRemove list
				inventory.getAllParts().removeAll(partsToRemove);
			}
	        inventory.addProduct(product);
	        homeController.setPartList(inventory.getAllParts());
			this.clear();
			homeController.setProductList(inventory.getAllProducts());
			st.setScene(Main.getScenes().get(SceneName.Home));
		});
	}
}
