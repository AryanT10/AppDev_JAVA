package org.example.pizzaordering;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;

import java.util.ArrayList;
import java.util.List;

import static org.example.pizzaordering.FileHandler.saveToFile;

public class HelloController {
    // Injecting FXML elements
    @FXML
    private TextField customerNameTextField;

    @FXML
    private TextField customerNumberTextField;

    @FXML
    private TextField pizzaQuantityTextField;

    @FXML
    private ChoiceBox<String> pizzaSizeChoiceBox;

    @FXML
    private ChoiceBox<String> crustChoiceBox;


    @FXML
    private CheckBox pineappleCheckBox;

    @FXML
    private CheckBox extraCheeseCheckBox;

    @FXML
    private CheckBox driedShrimpsCheckBox;

    @FXML
    private CheckBox mushroomsCheckBox;

    @FXML
    private CheckBox anchoviesCheckBox;

    @FXML
    private CheckBox sunDriedTomatoesCheckBox;

    @FXML
    private CheckBox daconCheckBox;
    @FXML
    private CheckBox spinachCheckBox;
    @FXML
    private CheckBox jalapenoCheckBox;

    @FXML
    private CheckBox roastedGarlicCheckBox;

    @FXML
    private CheckBox groundBeefCheckBox;

    @FXML
    private CheckBox shreddedChickenCheckBox;
    @FXML
    private CheckBox grilledChickenCheckBox;

    @FXML
    private CheckBox pepperoniCheckBox;
    @FXML
    private CheckBox hamCheckBox;

    @FXML
    private CheckBox baconCheckBox;

    @FXML
    private final List<CheckBox> normalToppingsCheckBoxes = new ArrayList<>();

    @FXML
    private final List<CheckBox> meatToppingsCheckBoxes = new ArrayList<>();

    // Injecting other UI elements
    @FXML
    private Button clearFieldsButton;

    @FXML
    private Button placeOrderButton;

    @FXML
    public void initialize() {
        // Populate the pizza size choice box
        pizzaSizeChoiceBox.getItems().addAll("Small - $7.00", "Medium - $10.00", "Large - $13.00", "Extra Large - $15.00");

        // Populate the crust choice box
        crustChoiceBox.getItems().addAll("Normal", "Thin", "Deep Dish");

        // Add normal toppings checkboxes to the list
        normalToppingsCheckBoxes.add(pineappleCheckBox);
        normalToppingsCheckBoxes.add(extraCheeseCheckBox);
        normalToppingsCheckBoxes.add(driedShrimpsCheckBox);
        normalToppingsCheckBoxes.add(mushroomsCheckBox);
        normalToppingsCheckBoxes.add(anchoviesCheckBox);
        normalToppingsCheckBoxes.add(sunDriedTomatoesCheckBox);
        normalToppingsCheckBoxes.add(daconCheckBox);
        normalToppingsCheckBoxes.add(spinachCheckBox);
        normalToppingsCheckBoxes.add(roastedGarlicCheckBox);
        normalToppingsCheckBoxes.add(jalapenoCheckBox);

        // Add meat toppings checkboxes to the list
        meatToppingsCheckBoxes.add(groundBeefCheckBox);
        meatToppingsCheckBoxes.add(shreddedChickenCheckBox);
        meatToppingsCheckBoxes.add(grilledChickenCheckBox);
        meatToppingsCheckBoxes.add(pepperoniCheckBox);
        meatToppingsCheckBoxes.add(hamCheckBox);
        meatToppingsCheckBoxes.add(baconCheckBox);
    }

    @FXML
    private void handleClearFields() {
        // Clear all the text fields and checkboxes
        customerNameTextField.clear();
        customerNumberTextField.clear();
        pizzaQuantityTextField.clear();
        pizzaSizeChoiceBox.getSelectionModel().clearSelection();
        crustChoiceBox.getSelectionModel().clearSelection();

        // Clear normal toppings checkboxes
        clearCheckboxes(normalToppingsCheckBoxes);

        // Clear meat toppings checkboxes
        clearCheckboxes(meatToppingsCheckBoxes);
    }

    @FXML
    private void handlePlaceOrder() {
        // Retrieve customer information from text fields
        String customerName = customerNameTextField.getText();
        if (!customerName.matches("[a-zA-Z ]+")) {
            showError("Enter a Valid Customer Name");
            return;
        }

        String customerNumber = customerNumberTextField.getText();
        if (!customerNumber.matches("\\d+")) {
            showError("Enter a valid cell number");
            return;
        }

        int pizzaQuantity;
        try {
            pizzaQuantity = Integer.parseInt(pizzaQuantityTextField.getText());
            if (pizzaQuantity > 50) {
                showError("Pizza quantity cannot exceed 50");
                return;
            }
        } catch (NumberFormatException e) {
            showError("Enter a valid pizza quantity");
            return;
        }

        // Customer Object
        Customer customer = new Customer(customerName, customerNumber);

        // Retrieving other pizza information from UI elements
        String pizzaSize = pizzaSizeChoiceBox.getValue();
        String crustType = crustChoiceBox.getValue();

        // Create a list to store selected toppings
        List<String> selectedToppings = new ArrayList<>();
        for (CheckBox checkBox : normalToppingsCheckBoxes) {
            if (checkBox.isSelected()) {
                selectedToppings.add(checkBox.getText());
            }
        }
        for (CheckBox checkBox : meatToppingsCheckBoxes) {
            if (checkBox.isSelected()) {
                selectedToppings.add(checkBox.getText());
            }
        }
        // Calculating Total Price
        double totalPrice = calculateTotalPrice(pizzaSize, crustType, selectedToppings);
        // Order Object
        Order order = new Order(customer, pizzaQuantity, pizzaSize, crustType, selectedToppings, totalPrice);

        saveToFile(order);
        // Displaying order details
        showOrderDetails(order);
    }

    private void showOrderDetails(Order order) {
        // Generate order details string
        String orderDetails = "Customer Name: " + order.getCustomer().getName() + "\n" +
                "Customer Number: " + order.getCustomer().getPhoneNumber() + "\n" +
                "Pizza Quantity: " + order.getQuantity() + "\n" +
                "Pizza Size: " + order.getPizzaSize() + "\n" +
                "Crust Type: " + order.getCrustType() + "\n" +
                "Total Price (Tax Included): " + String.format("$%.2f", order.getTotalPrice()) + "\n" +
                "Toppings: " + String.join(", ", order.getToppings());

        // Display an Alert dialog with the order details
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Order Details");
        alert.setHeaderText(null);
        alert.setContentText(orderDetails);
        alert.showAndWait();
    }

    private double calculateTotalPrice(String pizzaSize, String crustType, List<String> toppings) {
        double basePrice = switch (pizzaSize) {
            case "Small - $7.00" -> 7.00;
            case "Medium - $10.00" -> 10.00;
            case "Large - $13.00" -> 13.00;
            case "Extra Large - $15.00" -> 15.00;
            default -> 0.00; // Handle invalid input
        };

        double crustPrice = switch (crustType) {
            default -> 0.00; // No additional charge for crust types
        };

        // Calculate additional charges for toppings
        double toppingsPrice = 0.0;
        for (String topping : toppings) {
            if (isMeatTopping(topping)) {
                toppingsPrice += 2.20; // Each meat topping costs $2.20
            } else {
                toppingsPrice += 1.10; // Each normal topping costs $1.10
            }
        }

        return ((basePrice + crustPrice + toppingsPrice) * 1.13); // Including 13% tax
    }


    private void clearCheckboxes(List<CheckBox> checkboxes) {
        for (CheckBox checkBox : checkboxes) {
            if (checkBox != null) {
                checkBox.setSelected(false);
            }
        }
    }

    private void showError(String message) {
        // Display error message using an Alert dialog or any other appropriate UI component
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private boolean isMeatTopping(String toppingName) {
        // Define meat toppings here
        List<String> meatToppings = new ArrayList<>();
        meatToppings.add("Ground Beef");
        meatToppings.add("Shredded Chicken");
        meatToppings.add("Grilled Chicken");
        meatToppings.add("Pepperoni");
        meatToppings.add("Ham");
        meatToppings.add("Bacon");

        return meatToppings.contains(toppingName);
    }

}
