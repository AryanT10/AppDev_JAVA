package org.example.pizzaordering;

import java.util.List;

public class Order {
    private Customer customer;
    private int quantity;
    private String pizzaSize;
    private String crustType;
    private List<String> toppings;
    private double totalPrice;

    public Order(Customer customer, int quantity, String pizzaSize, String crustType, List<String> toppings, double totalPrice) {
        this.customer = customer;
        this.quantity = quantity;
        this.pizzaSize = pizzaSize;
        this.crustType = crustType;
        this.toppings = toppings;
        this.totalPrice = totalPrice;
    }

    // Getters and setters for order attributes
    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getPizzaSize() {
        return pizzaSize;
    }

    public void setPizzaSize(String pizzaSize) {
        this.pizzaSize = pizzaSize;
    }

    public String getCrustType() {
        return crustType;
    }

    public void setCrustType(String crustType) {
        this.crustType = crustType;
    }

    public List<String> getToppings() {
        return toppings;
    }

    public void setToppings(List<String> toppings) {
        this.toppings = toppings;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }
}
