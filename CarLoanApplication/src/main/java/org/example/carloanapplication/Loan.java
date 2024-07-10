package org.example.carloanapplication;

public class Loan {
    private int totalMonths;
    private double interestRate;
    private String paymentFrequency;

    public Loan(int periodMonths, double interestRate, String paymentFrequency) {
        this.totalMonths = periodMonths;
        this.interestRate = interestRate;
        this.paymentFrequency = paymentFrequency;
    }
}