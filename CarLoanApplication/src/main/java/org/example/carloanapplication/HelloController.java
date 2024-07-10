package org.example.carloanapplication;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import java.util.HashMap;
import java.util.Map;

public class HelloController {

    @FXML
    private TextField customerName;

    @FXML
    private TextField customerNumber;

    @FXML
    private ChoiceBox<String> vehicleTypeChoice;

    @FXML
    private ChoiceBox<String> vehicleAgeChoice;

    @FXML
    private TextField vehiclePrice;

    @FXML
    private TextField downPayment;

    @FXML
    private TextField interestRate;

    @FXML
    private ChoiceBox<Integer> loanPeriodChoiceBox;

    @FXML
    private RadioButton weeklyRadioButton;

    @FXML
    private RadioButton biWeeklyRadioButton;

    @FXML
    private RadioButton monthlyRadioButton;

    @FXML
    private Button calculateButton;

    @FXML
    private Button saveRatesButton;

    @FXML
    private Button showSavedRatesButton;

    @FXML
    private Label paymentResultLabel;

    @FXML
    private final ToggleGroup paymentFrequencyToggleGroup = new ToggleGroup();

    @FXML
    private Map<String, Object> savedRatesData = new HashMap<>();

    @FXML
    public void initialize() {
        weeklyRadioButton.setToggleGroup(paymentFrequencyToggleGroup);
        biWeeklyRadioButton.setToggleGroup(paymentFrequencyToggleGroup);
        monthlyRadioButton.setToggleGroup(paymentFrequencyToggleGroup);

        // Populate the loan period choice box with values
        loanPeriodChoiceBox.getItems().addAll(12, 24, 36, 48, 60, 72, 84, 96);
        loanPeriodChoiceBox.setValue(12); // Set default value

        addListeners();

        calculateButton.setDisable(true);
        saveRatesButton.setDisable(true);
        showSavedRatesButton.setDisable(true);
    }

    @FXML
    private void addListeners() {
        loanPeriodChoiceBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            updateButtonsState();
        });

        weeklyRadioButton.selectedProperty().addListener((observable, oldValue, newValue) -> updateButtonsState());
        biWeeklyRadioButton.selectedProperty().addListener((observable, oldValue, newValue) -> updateButtonsState());
        monthlyRadioButton.selectedProperty().addListener((observable, oldValue, newValue) -> updateButtonsState());

        addInputListeners(customerName);
        addNumericInputListeners(customerNumber);
        addNumericInputListeners(vehiclePrice);
        addNumericInputListeners(downPayment);
        addNumericInputListeners(interestRate);
    }

    @FXML
    private void addInputListeners(TextField textField) {
        textField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("[\\sa-zA-Z]*")) {
                textField.setText(oldValue);
            }
            updateButtonsState();
        });
    }

    @FXML
    private void addNumericInputListeners(TextField textField) {
        textField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                textField.setText(newValue.replaceAll("[^\\d]", ""));
            }
            updateButtonsState();
        });
    }

    @FXML
    private void updateButtonsState() {
        boolean hasValidInput = validateInputFields();
        calculateButton.setDisable(!hasValidInput);
        saveRatesButton.setDisable(!hasValidInput);
        showSavedRatesButton.setDisable(!hasValidInput);
    }

    @FXML
    private boolean validateInputFields() {
        return isEmpty(customerName.getText()) &&
                isEmpty(customerNumber.getText()) &&
                vehicleTypeChoice.getValue() != null &&
                vehicleAgeChoice.getValue() != null &&
                isEmpty(vehiclePrice.getText()) &&
                isEmpty(downPayment.getText()) &&
                isEmpty(interestRate.getText()) &&
                loanPeriodChoiceBox.getValue() != null;
    }

    @FXML
    private boolean isEmpty(String str) {
        return str != null && !str.trim().isEmpty();
    }

    @FXML
    private void handleClearButton() {
        customerName.clear();
        customerNumber.clear();
        vehicleTypeChoice.setValue(null);
        vehicleAgeChoice.setValue(null);
        vehiclePrice.clear();
        downPayment.clear();
        interestRate.clear();
        loanPeriodChoiceBox.setValue(12); // Reset to default value
        weeklyRadioButton.setSelected(true);
        paymentResultLabel.setText("");
    }

    @FXML
    private void handleCalculateButton() {
        Map<String, Object> loanData = calculateLoan();
        String paymentFrequency = getPaymentFrequency();
        displayLoanData(loanData, paymentFrequency);
    }

    @FXML
    private Map<String, Object> calculateLoan() {
        Map<String, Object> loanData = new HashMap<>();

        double calculateVehiclePrice = Double.parseDouble(vehiclePrice.getText());
        double calculateDownPayment = Double.parseDouble(downPayment.getText());
        double calculateInterestRate = Double.parseDouble(interestRate.getText()) / 100.0; // convert percentage to decimal
        int loanPeriodMonths = loanPeriodChoiceBox.getValue();

        double loanAmount = calculateVehiclePrice - calculateDownPayment;
        double monthlyInterestRate = calculateInterestRate / 12.0;

        int paymentFrequency = 4;
        if (biWeeklyRadioButton.isSelected()) {
            paymentFrequency = 2;
        } else if (monthlyRadioButton.isSelected()) {
            paymentFrequency = 1;
        }

        double totalInterest = 0.0;
        double totalPayment;
        double paymentAmount;
        if (calculateInterestRate != 0) {
            double temp = Math.pow(1 + monthlyInterestRate / paymentFrequency, loanPeriodMonths * paymentFrequency);
            totalPayment = (loanAmount * monthlyInterestRate / paymentFrequency * temp) / (temp - 1);
            totalInterest = totalPayment * loanPeriodMonths * paymentFrequency - loanAmount;
            paymentAmount = totalPayment;
        } else {
            totalPayment = loanAmount / (loanPeriodMonths * paymentFrequency);
            paymentAmount = totalPayment;
        }

        loanData.put("Total Payment", totalPayment);
        loanData.put("Total Interest", totalInterest);
        loanData.put("Payment Amount", paymentAmount);

        return loanData;
    }

    @FXML
    private void handleSaveRatesButton() {
        Map<String, Object> loanData = calculateLoan();

        loanData.put("Customer Name", customerName.getText());
        loanData.put("Customer Number", customerNumber.getText());
        loanData.put("Vehicle Type", vehicleTypeChoice.getValue());
        loanData.put("Vehicle Age", vehicleAgeChoice.getValue());
        loanData.put("Payment Frequency", getPaymentFrequency());

        savedRatesData = loanData;
    }

    @FXML
    private void handleShowSavedRatesButton() {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Saved Rates");
        alert.setHeaderText("Saved Rates");

        StringBuilder contentText = new StringBuilder();
        appendIfPresent(contentText, "Customer Name", savedRatesData);
        appendIfPresent(contentText, "Customer Number", savedRatesData);
        appendIfPresent(contentText, "Vehicle Age", savedRatesData);
        appendIfPresent(contentText, "Vehicle Type", savedRatesData);
        appendIfPresent(contentText, "Payment Amount", savedRatesData);
        appendIfPresent(contentText, "Payment Frequency", savedRatesData);
        appendIfPresent(contentText, "Total Interest", savedRatesData);

        alert.setContentText(contentText.toString());

        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        stage.setAlwaysOnTop(true);
        alert.showAndWait();
    }

    @FXML
    private void appendIfPresent(StringBuilder contentText, String key, Map<String, Object> data) {
        if (data.containsKey(key)) {
            Object value = data.get(key);
            if (value instanceof Double) {
                double doubleValue = (double) value;
                contentText.append(key).append(": ").append(String.format("$%.2f", doubleValue)).append("\n");
            } else {
                contentText.append(key).append(": ").append(value).append("\n");
            }
        }
    }

    @FXML
    private void displayLoanData(Map<String, Object> loanData, String paymentFrequency) {
        double totalPayment = (double) loanData.get("Total Payment");
        double totalInterest = (double) loanData.get("Total Interest");
        double paymentAmount = (double) loanData.get("Payment Amount");

        String resultText = String.format("%s Payment: $%.2f%nTotal Interest: $%.2f",
                paymentFrequency, totalPayment, totalInterest, paymentAmount);

        paymentResultLabel.setText(resultText);
    }

    @FXML
    private String getPaymentFrequency() {
        if (weeklyRadioButton.isSelected()) {
            return "Weekly";
        } else if (biWeeklyRadioButton.isSelected()) {
            return "Bi-Weekly";
        } else {
            return "Monthly";
        }
    }
}
