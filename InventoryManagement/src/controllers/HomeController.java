package controllers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import application.Main;
import application.SceneName;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import models.InHouse;
import models.Inventory;
import models.Outsourced;
import models.Part;
import models.Product;

public class HomeController implements Initializable {
	private Stage st;
	private Inventory inventory;
	private modifyPartController modifyPartControl;
	private modifyProductController modifyProductControl;
    
	public void setPartList(ObservableList<Part> arrayList) {
		// Initialize columns
		partID.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getId()).asObject());
		partInventory.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getStock()).asObject());
		partName.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
		partPrice.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getPrice()).asObject());
		// Convert array to ObservableList
		if (arrayList != null) {
		      ObservableList<Part> dataList = FXCollections.observableArrayList(arrayList);
		      partTabel.setItems(dataList);
			  }
		else {
			System.out.println("The loanList is empty");
		}
	}
	public void setProductList(ObservableList<Product> allProducts) {
		// Initialize columns
		productIDCol.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getId()).asObject());
		productInvCol.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getStock()).asObject());
		productNameCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
		productPriceCol.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getPrice()).asObject());
		// Convert array to ObservableList
		if (allProducts != null) {
			ObservableList<Product> dataList = FXCollections.observableArrayList(allProducts);
			productTabel.setItems(dataList);
		}
		else {
			System.out.println("The loanList is empty");
		}
	}
	
	@FXML
	private TableView<Product> productTabel;
	
	@FXML
	private TableColumn<Product, Integer> productIDCol;
	
	@FXML
	private TableColumn<Product, String> productNameCol;
	
	@FXML
	private TableColumn<Product, Integer> productInvCol;
	
	@FXML
	private TableColumn<Product, Double> productPriceCol;
	
	@FXML
    private Button PartAddBtn;
	
	@FXML
    private Button PartModifyBtn;
	
	@FXML
	private Button deletePart;
	
	@FXML
	private Button deleteProduct;
	
	@FXML
    private Button ProductAddBtn;
	
	@FXML
    private Button ProductModifyBtn;
	
	@FXML
    private Button exitBtn;
	
	@FXML
    private Button saveInFile;
	
	@FXML
    private Button loadFromFile;
	
	@FXML
    private Button saveInDB;
	
	@FXML
    private Button loadFromDB;
	
	@FXML
    private TableView<Part> partTabel;
	
    @FXML
    private TableColumn<Part, Integer> partID;

    @FXML
    private TableColumn<Part, Integer> partInventory;

    @FXML
    private TableColumn<Part, String> partName;

    @FXML
    private TableColumn<Part, Double> partPrice;
    
    @FXML
    private TextField partSearch;
    
    @FXML
    private TextField productSearch;
    
	public HomeController(Stage st, Inventory inventory, modifyPartController modifyPartControl, modifyProductController modifyProductControl) {
		this.st = st;
		this.inventory = inventory;
		this.modifyPartControl = modifyPartControl;
		this.modifyProductControl = modifyProductControl;
	}
	private addProductController addProductControl;
	public void setAddProductController(addProductController addProductControl) {
		this.addProductControl = addProductControl;
	}
	private int selectedPartIndex = -1;
	private void selectedPartIndex(int selectedIndex) {
		this.selectedPartIndex = selectedIndex;
	}
	private int selectedProductIndex = -1;
	private void selectedProductIndex(int selectedIndex) {
		this.selectedProductIndex = selectedIndex;
	}
	private Part selectedPart() {
		return inventory.getAllParts().get(selectedPartIndex);
	}
	private Product selectedProduct() {
		return inventory.getAllProducts().get(selectedProductIndex);
	}
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		partSearch.setOnKeyPressed(event -> {
		    if (event.getCode() == KeyCode.ENTER) {
		    	String input = partSearch.getText();
		        try {
		            // Try parsing the input as an integer
		            int value = Integer.parseInt(input);
		            
		            if (inventory.searchPartByID(value) != null) {
		            	ObservableList<Part> arrayList = FXCollections.observableArrayList();
		            	arrayList.add(inventory.searchPartByID(value));
			    		setPartList(arrayList);
			    	}
			    	else if (inventory.searchPartByID(value) == null) {
			    		setPartList(inventory.getAllParts());
			    	}
		            // Here you can handle the case when input is an integer
		        } catch (NumberFormatException e) {
		        	if (inventory.searchPartByName(partSearch.getText()).size() != 0) {
			    		setPartList(inventory.searchPartByName(partSearch.getText()));
			    	}
			    	else if (inventory.searchPartByName(partSearch.getText()).size() == 0) {
			    		setPartList(inventory.getAllParts());
			    	}
		        }
		    }
		});
		productSearch.setOnKeyPressed(event -> {
		    if (event.getCode() == KeyCode.ENTER) {
		    	String input = productSearch.getText();
		        try {
		            // Try parsing the input as an integer
		            int value = Integer.parseInt(input);
		            
		            if (inventory.searchProductByID(value) != null) {
		            	ObservableList<Product> arrayList = FXCollections.observableArrayList();
		            	arrayList.add(inventory.searchProductByID(value));
			    		setProductList(arrayList);
			    	}
			    	else if (inventory.searchProductByID(value) == null) {
			    		setProductList(inventory.getAllProducts());
			    	}
		            // Here you can handle the case when input is an integer
		        } catch (NumberFormatException e) {
		        	if (inventory.searchProductByName(productSearch.getText()).size() != 0) {
			    		setProductList(inventory.searchProductByName(productSearch.getText()));
			    	}
			    	else if (inventory.searchProductByName(productSearch.getText()).size() == 0) {
			    		setProductList(inventory.getAllProducts());
			    	}
		        }
		    }
		});
		partTabel.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue)->{
			if (newValue.intValue() >= 0) {
				int selectedIndex = newValue.intValue();
				modifyPartControl.fillDataFromTabel(inventory.getAllParts().get(selectedIndex), selectedIndex);
				selectedPartIndex(selectedIndex);
			}
		});
		productTabel.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue)->{
			if (newValue.intValue() >= 0) {
				int selectedIndex = newValue.intValue();
				modifyProductControl.fillDataFromTabel(inventory.getAllProducts().get(selectedIndex), selectedIndex);
				modifyProductControl.fillAddPartsTabel(inventory.getAllParts());
				modifyProductControl.fillProductPartsTabel(inventory.getAllProducts().get(selectedIndex).getAllAssociatedPart());
				selectedProductIndex(selectedIndex);
			}
		});
		PartAddBtn.setOnAction(e->{
			st.setScene(Main.getScenes().get(SceneName.addPartForm));
		});
		PartModifyBtn.setOnAction(e->{
			if (inventory.getAllParts().size() > 0) {
			st.setScene(Main.getScenes().get(SceneName.modifyPartForm));
			}
		});
		deletePart.setOnAction(e->{
			if (this.selectedPartIndex != -1) {
				inventory.deletePart(selectedPart());
				this.setPartList(inventory.getAllParts());	
			}
		});
		deleteProduct.setOnAction(e->{
			if (this.selectedProductIndex != -1) {
				inventory.deleteProduct(selectedProduct());
				this.setProductList(inventory.getAllProducts());	
			}
		});
		ProductAddBtn.setOnAction(e->{
			addProductControl.fillAddPartsTabel(inventory.getAllParts());
			st.setScene(Main.getScenes().get(SceneName.addProductForm));
		});
		ProductModifyBtn.setOnAction(e->{
			if (inventory.getAllProducts().size() > 0) {
				st.setScene(Main.getScenes().get(SceneName.modifyProductForm));
			}
		});
		saveInFile.setOnAction(e->{
			String filePath = "/Users/aryantuwar/Documents/SEMESTER 5/APD/W4&5/ARYAN/src/inventoryParts.txt";
			try (FileOutputStream fileOut = new FileOutputStream(filePath);
			        ObjectOutputStream objectOut = new ObjectOutputStream(fileOut)) {

					List<Part> partsToSave = new ArrayList<>(inventory.getAllParts());
					objectOut.writeObject(partsToSave);
			        System.out.println("Parts saved to file successfully.");
		    } catch (IOException ex) {
		        System.err.println("Error writing parts to file: " + ex.getMessage());
		    }
			
			filePath = "/Users/aryantuwar/Documents/SEMESTER 5/APD/W4&5/ARYAN/src/inventoryProducts.txt";
			try (FileOutputStream fileOut = new FileOutputStream(filePath);
			        ObjectOutputStream objectOut = new ObjectOutputStream(fileOut)) {
			        
					List<Product> productsToSave = new ArrayList<>(inventory.getAllProducts());
					objectOut.writeObject(productsToSave);
					System.out.println("Products saved to file successfully.");
		    } catch (IOException ex) {
		        System.err.println("Error writing products to file: " + ex.getMessage());
		    }
		});
		loadFromFile.setOnAction(e->{
			String filePath = "/Users/aryantuwar/Documents/SEMESTER 5/APD/W4&5/ARYAN/src/inventoryParts.txt";
			inventory.getAllParts().clear();
			inventory.getAllProducts().clear();
		    try (FileInputStream fileIn = new FileInputStream(filePath);
		         ObjectInputStream objectIn = new ObjectInputStream(fileIn)) {

		    	List<Part> partsFromFile = (List<Part>) objectIn.readObject();

		        for (Part part : partsFromFile) {
		            inventory.addPart(part);
		        }
		        setPartList(inventory.getAllParts());
		        System.out.println("Parts read from file successfully.");
		    } catch (IOException | ClassNotFoundException ex) {
		        System.err.println("Error reading parts from file: " + ex.getMessage());
		    }
		    
		    filePath = "/Users/aryantuwar/Documents/SEMESTER 5/APD/W4&5/ARYAN/src/inventoryProducts.txt";

		    try (FileInputStream fileIn = new FileInputStream(filePath);
		         ObjectInputStream objectIn = new ObjectInputStream(fileIn)) {

		    	List<Product> productsFromFile = (List<Product>) objectIn.readObject();

		        for (Product product : productsFromFile) {
		            inventory.addProduct(product);
		        }
		        setProductList(inventory.getAllProducts());
		        System.out.println("Products read from file successfully.");
		    } catch (IOException | ClassNotFoundException ex) {
		        System.err.println("Error reading products from file: " + ex.getMessage());
		    }
		});
		loadFromDB.setOnAction(e->{
			inventory.getAllParts().clear();
			inventory.getAllProducts().clear();
			try(Connection conn = DriverManager.getConnection("jdbc:sqlite:/Users/aryantuwar/Documents/SEMESTER 5/APD/W4&5/ARYAN/src/inventory.db");
					Statement stm = conn.createStatement()) {
				
			    String query = "SELECT o.*, p.name AS partName, p.price AS partPrice, p.stock AS partStock, p.min AS partMin, p.max AS partMax " +
			                   "FROM Outsourced o " +
			                   "JOIN Part p ON o.partId = p.partId " +
			                   "WHERE p.productId IS NULL";
			    ResultSet resultSet = stm.executeQuery(query);
			    while (resultSet.next()) {
			        int partId = resultSet.getInt("partId");
			        String companyName = resultSet.getString("companyName");

			        String partName = resultSet.getString("partName");
			        double partPrice = resultSet.getDouble("partPrice");
			        int partStock = resultSet.getInt("partStock");
			        int partMin = resultSet.getInt("partMin");
			        int partMax = resultSet.getInt("partMax");

			        Outsourced OutsourcedPart = new Outsourced(partId, partName, partPrice, partStock, partMin, partMax, companyName);
			        inventory.addPart(OutsourcedPart);
			    }
			    
			    query = "SELECT i.*, p.name AS partName, p.price AS partPrice, p.stock AS partStock, p.min AS partMin, p.max AS partMax " +
		                   "FROM InHouse i " +
		                   "JOIN Part p ON i.partId = p.partId " +
		                   "WHERE p.productId IS NULL";
			    resultSet = stm.executeQuery(query);

			    while (resultSet.next()) {
			    	int partId = resultSet.getInt("partId");
			    	int machineId = resultSet.getInt("machineId");

			    	String partName = resultSet.getString("partName");
			    	double partPrice = resultSet.getDouble("partPrice");
			    	int partStock = resultSet.getInt("partStock");
			    	int partMin = resultSet.getInt("partMin");
			    	int partMax = resultSet.getInt("partMax");

			    	InHouse inHousePart = new InHouse(partId, partName, partPrice, partStock, partMin, partMax, machineId);
			    	inventory.addPart(inHousePart);
			    }
			    setPartList(inventory.getAllParts());
			    
			    ResultSet productResultSet = stm.executeQuery("SELECT * FROM Product");

			    while (productResultSet.next()) {
			        int productId = productResultSet.getInt("productId");
			        String name = productResultSet.getString("name");
			        double price = productResultSet.getDouble("price");
			        int stock = productResultSet.getInt("stock");
			        int min = productResultSet.getInt("min");
			        int max = productResultSet.getInt("max");

			        Product product = new Product(productId, name, price, stock, min, max);
			        
			        query = "SELECT i.*, p.name AS partName, p.price AS partPrice, p.stock AS partStock, p.min AS partMin, p.max AS partMax " +
			                   "FROM InHouse i " +
			                   "JOIN Part p ON i.partId = p.partId " +
			                   "WHERE p.productId IS NOT NULL " +
			                   "AND p.productId = " + productId;
			        ResultSet inHouseResultSet = stm.executeQuery(query);

			        while (inHouseResultSet.next()) {
			        	int inHousepartId = inHouseResultSet.getInt("partId");
				    	int machineId = inHouseResultSet.getInt("machineId");

				    	String partName = inHouseResultSet.getString("partName");
				    	double partPrice = inHouseResultSet.getDouble("partPrice");
				    	int partStock = inHouseResultSet.getInt("partStock");
				    	int partMin = inHouseResultSet.getInt("partMin");
				    	int partMax = inHouseResultSet.getInt("partMax");

				    	Part part = new InHouse(inHousepartId, partName, partPrice, partStock, partMin, partMax, machineId);
				    	product.addAssociatedPart(part);
			        }
			        
			        query = "SELECT o.*, p.name AS partName, p.price AS partPrice, p.stock AS partStock, p.min AS partMin, p.max AS partMax " +
			                   "FROM Outsourced o " +
			                   "JOIN Part p ON o.partId = p.partId " +
			                   "WHERE p.productId IS NOT NULL " +
			                   "AND p.productId = " + productId;
			        ResultSet outsourcedResultSet = stm.executeQuery(query);

			        while (outsourcedResultSet.next()) {
			        	int outsourcedpartId = outsourcedResultSet.getInt("partId");
			        	String companyName = resultSet.getString("companyName");

				    	String partName = outsourcedResultSet.getString("partName");
				    	double partPrice = outsourcedResultSet.getDouble("partPrice");
				    	int partStock = outsourcedResultSet.getInt("partStock");
				    	int partMin = outsourcedResultSet.getInt("partMin");
				    	int partMax = outsourcedResultSet.getInt("partMax");

				    	Part part = new Outsourced(outsourcedpartId, partName, partPrice, partStock, partMin, partMax, companyName);
				    	product.addAssociatedPart(part);
			        }
			        inventory.addProduct(product);
			    }
			    setProductList(inventory.getAllProducts());
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		});
		saveInDB.setOnAction(e->{
			try(Connection conn = DriverManager.getConnection("jdbc:sqlite:/Users/aryantuwar/Documents/SEMESTER 5/APD/W4&5/ARYAN/src/inventory.db");
					Statement stm = conn.createStatement()) {
				stm.executeUpdate("DELETE FROM Product");
			    stm.executeUpdate("DELETE FROM Part");
			    stm.executeUpdate("DELETE FROM InHouse");
			    stm.executeUpdate("DELETE FROM Outsourced");
			    
				if (inventory.getAllParts().size() > 0) {
					
					for (Part part : inventory.getAllParts()) {
						String insertQuery = "INSERT INTO Part (partId, name, price, stock, min, max) " +
                                "VALUES (?, ?, ?, ?, ?, ?)";
						PreparedStatement pstmt = conn.prepareStatement(insertQuery);
						pstmt = conn.prepareStatement(insertQuery);
						pstmt.setInt(1, part.getId());
						pstmt.setString(2, part.getName());
						pstmt.setDouble(3, part.getPrice());
						pstmt.setInt(4, part.getStock());
						pstmt.setInt(5, part.getMin());
						pstmt.setInt(6, part.getMax());
           
						pstmt.executeUpdate();
						pstmt.close();
						
						if (part instanceof InHouse) {
							InHouse inHousePart = (InHouse) part;
							insertQuery = "INSERT INTO InHouse (partId, machineId) " +
	                                "VALUES (?, ?)";
							pstmt = conn.prepareStatement(insertQuery);
							pstmt.setInt(1, inHousePart.getId());
							pstmt.setInt(2, inHousePart.getMachineId());
	           
							pstmt.executeUpdate();
							pstmt.close();
						}
						else {
							Outsourced OutsourcedPart = (Outsourced) part;
							insertQuery = "INSERT INTO Outsourced (partId, companyName) " +
	                                "VALUES (?, ?)";
							pstmt = conn.prepareStatement(insertQuery);
							pstmt.setInt(1, OutsourcedPart.getId());
							pstmt.setString(2, OutsourcedPart.getCompanyName());
	           
							pstmt.executeUpdate();
							pstmt.close();
						}
					}
				}
				if (inventory.getAllProducts().size() > 0) {
					
					for (Product product : inventory.getAllProducts()) {
				        
				        String insertQuery = "INSERT INTO Product (productId, name, price, stock, min, max) " +
				                             "VALUES (?, ?, ?, ?, ?, ?)";
				        
				        PreparedStatement pstmt = conn.prepareStatement(insertQuery);
				        pstmt.setInt(1, product.getId());
				        pstmt.setString(2, product.getName());
				        pstmt.setDouble(3, product.getPrice());
				        pstmt.setInt(4, product.getStock());
				        pstmt.setInt(5, product.getMin());
				        pstmt.setInt(6, product.getMax());
				        
				        pstmt.executeUpdate();
				        pstmt.close();
				        
				        for (Part part : product.getAllAssociatedPart()) {
				            
				            insertQuery = "INSERT INTO Part (partId, name, price, stock, min, max, productId) " +
				                                 "VALUES (?, ?, ?, ?, ?, ?, ?)";
				            
				            pstmt = conn.prepareStatement(insertQuery);
				            pstmt.setInt(1, part.getId());
				            pstmt.setString(2, part.getName());
				            pstmt.setDouble(3, part.getPrice());
				            pstmt.setInt(4, part.getStock());
				            pstmt.setInt(5, part.getMin());
				            pstmt.setInt(6, part.getMax());
				            pstmt.setInt(7, product.getId());
				            
				            pstmt.executeUpdate();
				            pstmt.close();
				        }
				    }
				}
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		});
		exitBtn.setOnAction(e->{
			st.close();
		});
	}
}
