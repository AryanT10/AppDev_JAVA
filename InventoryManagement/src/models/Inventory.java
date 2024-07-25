package models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Inventory {
	private ObservableList<Part> allParts = FXCollections.observableArrayList();
	private ObservableList<Product> allProducts = FXCollections.observableArrayList();
	
	public void addPart(Part newPart) {
		allParts.add(newPart);
	}
	public void addProduct(Product newProduct) {
		allProducts.add(newProduct);
	}
	public Part searchPartByID(int partId) {
	    for (Part part : this.allParts) {
	        if (part.getId() == partId) {
	            return part;
	        }
	    }
	    
	    return null;
	}
	public Product searchProductByID(int productId) {
	    for (Product product : this.allProducts) {
	        if (product.getId() == productId) {
	            return product;
	        }
	    }
	    
	    return null;
	}
	public ObservableList<Part> searchPartByName(String name) {
	    ObservableList<Part> matchingParts = FXCollections.observableArrayList();
	    for (Part part : this.allParts) {
	        if (part.getName().contains(name)) {
	            matchingParts.add(part);
	        }
	    }
	    return matchingParts;
	}
	public ObservableList<Product> searchProductByName(String name) {
	    ObservableList<Product> matchingProducts = FXCollections.observableArrayList();
	    for (Product product : this.allProducts) {
	        if (product.getName().contains(name)) {
	            matchingProducts.add(product);
	        }
	    }
	    return matchingProducts;
	}
	public void updatePart(int index, Part selectedPart) {
		this.allParts.set(index, selectedPart);
	}
	public void updateProduct(int index, Product newProduct) {
		this.allProducts.set(index, newProduct);
	}
	public boolean deletePart(Part selectedPart) {
		return this.allParts.remove(selectedPart);
	}
	public boolean deleteProduct(Product selectedProduct) {
		return this.allProducts.remove(selectedProduct);
	}
	public ObservableList<Part> getAllParts(){
		return this.allParts;
	}
	public ObservableList<Product> getAllProducts(){
		return this.allProducts;
	}
}
