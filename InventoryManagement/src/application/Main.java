package application;

import controllers.HomeController;
import controllers.addPartController;
import controllers.addProductController;
import controllers.modifyPartController;
import controllers.modifyProductController;
import javafx.application.Application;
import javafx.stage.Stage;
import models.InHouse;
import models.Inventory;
import models.Product;
import models.Outsourced;
import views.HomeView;
import views.addPartView;
import views.addProductView;
import views.modifyPartView;
import views.modifyProductView;
import javafx.scene.Scene;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Main extends Application {

	private static Map<SceneName, Scene> scenes = new HashMap<>();

	@Override
	public void start(Stage ps) {
		try {
			Inventory inventory = new Inventory();
			addSampleData(inventory);

			modifyPartController modifyPartControl = new modifyPartController(ps, inventory);
			modifyProductController modifyProductControl = new modifyProductController(ps, inventory);
			HomeController homeController = new HomeController(ps, inventory, modifyPartControl, modifyProductControl);
			addPartController addPartControl = new addPartController(ps, inventory, homeController);
			addProductController addProductControl = new addProductController(ps, inventory, homeController);
			modifyPartControl.setHomeController(homeController);
			modifyProductControl.setHomeController(homeController);
			homeController.setAddProductController(addProductControl);

			scenes.put(SceneName.Home, new HomeView(ps, inventory).getScene(homeController));
			scenes.put(SceneName.addPartForm, new addPartView(ps, inventory).getScene(addPartControl));
			scenes.put(SceneName.modifyPartForm, new modifyPartView(ps, inventory).getScene(modifyPartControl));
			scenes.put(SceneName.addProductForm, new addProductView(ps, inventory).getScene(addProductControl));
			scenes.put(SceneName.modifyProductForm, new modifyProductView(ps, inventory).getScene(modifyProductControl));
			ps.setScene(scenes.get(SceneName.Home));
			ps.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	private void addSampleData(Inventory inventory) {
		// Add sample parts
		InHouse part1 = new InHouse(UUID.randomUUID().hashCode(), "Part 1", 10.0, 5, 1, 10, 123);
		InHouse part2 = new InHouse(UUID.randomUUID().hashCode(), "Part 2", 20.0, 8, 2, 15, 456);
		Outsourced part3 = new Outsourced(UUID.randomUUID().hashCode(), "Part 3", 30.0, 12, 3, 20, "Company A");
		Outsourced part4 = new Outsourced(UUID.randomUUID().hashCode(), "Part 4", 40.0, 15, 4, 25, "Company B");

		inventory.addPart(part1);
		inventory.addPart(part2);
		inventory.addPart(part3);
		inventory.addPart(part4);

		// Add sample products
		Product product1 = new Product(UUID.randomUUID().hashCode(), "Product 1", 100.0, 5, 1, 10);
		product1.addAssociatedPart(part1);
		product1.addAssociatedPart(part2);
		inventory.addProduct(product1);

		Product product2 = new Product(UUID.randomUUID().hashCode(), "Product 2", 200.0, 8, 2, 15);
		product2.addAssociatedPart(part3);
		product2.addAssociatedPart(part4);
		inventory.addProduct(product2);
	}

	public static Map<SceneName, Scene> getScenes() {
		return scenes;
	}

	public static void main(String[] args) {
		launch(args);
	}
}
