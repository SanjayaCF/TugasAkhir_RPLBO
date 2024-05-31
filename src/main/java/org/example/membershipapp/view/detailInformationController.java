package org.example.membershipapp.view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class detailInformationController {

    @FXML
    private Label membershipTypeLabel;

    @FXML
    private Label priceLabel;

    @FXML
    private Label membershipPeriodLabel;

    @FXML
    private Label paymentMethodLabel;

    @FXML
    private Label autoPaymentLabel;

    @FXML
    private Label nextPaymentLabel;

    @FXML
    private Label benefitLabel;

    @FXML
    private Button btnStopMembership;

    @FXML
    private Button btnBack;

    // Method to initialize the UI with membership details
    public void initialize() {
        // Sample data - you would normally get this from your application logic
        membershipTypeLabel.setText("Premium");
        priceLabel.setText("Rp52.490,00");
        membershipPeriodLabel.setText("4th February 2024 - 4th March 2024");
        paymentMethodLabel.setText("DANA");
        autoPaymentLabel.setText("Active");
        nextPaymentLabel.setText("4th March 2024");
        benefitLabel.setText("No Ads, Offline Content, Improved Audio Quality, No Shuffle Limit, Access to More Tracks, Organize Unlimited Queues, Full Unlock on Mobile Devices, Prioritized Customer Support");
    }

    @FXML
    private void onStopMembershipClick(ActionEvent event) {
        // Logic to stop the membership
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Membership Cancelled");
        alert.setHeaderText(null);
        alert.setContentText("Your membership has been cancelled.");
        alert.showAndWait();
    }

    @FXML
    private void onBackClick(ActionEvent event) {
        // Logic to go back to the previous screen
        Stage stage = (Stage) btnBack.getScene().getWindow();
        stage.close();
    }
}


