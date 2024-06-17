package org.example.pizzaordering;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class FileHandler {
    public static void saveToFile(Order order) {
        // Text File
        String fileName = "orders.txt";
        // Path
        String filePath = fileName;

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
            // Generating random number
            int orderNumber = generateOrderNumber();
            writer.write("Order Number: " + orderNumber);
            writer.newLine();
            // Customer details
            writer.write("Customer Name: " + order.getCustomer().getName());
            writer.newLine();
            writer.write("Customer Number: " + order.getCustomer().getPhoneNumber());
            writer.newLine();

            // Order details
            writer.write("Pizza Quantity: " + order.getQuantity());
            writer.newLine();
            writer.write("Pizza Size: " + order.getPizzaSize());
            writer.newLine();
            writer.write("Crust Type: " + order.getCrustType());
            writer.newLine();
            writer.write("Total Price (Tax Included): " + String.format("$%.2f", order.getTotalPrice()));
            writer.newLine();
            writer.write("Toppings: " + String.join(", ", order.getToppings()));
            writer.newLine();
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static int generateOrderNumber() {
        Random random = new Random();
        // Generating a random number between 100000 and 999999
        return 100000 + random.nextInt(900000);
    }
}
