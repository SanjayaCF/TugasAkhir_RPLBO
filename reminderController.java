package org.example.membershipapp.view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class reminderController {

    @FXML
    private Label usernameLabel;

    @FXML
    private Label transactionIdLabel;

    @FXML
    private Label nameLabel;

    @FXML
    private Label statusLabel;

    @FXML
    private Label paymentMethodLabel;

    @FXML
    private Label endsInLabel;

    @FXML
    private Label membershipTypeLabel;

    @FXML
    private Label reminderMessageLabel;

    @FXML
    private Button btnBack;

    // Method to handle the "Back" button click event
    @FXML
    private void onBackClick(ActionEvent event) {
        // Close the current window
        Stage stage = (Stage) btnBack.getScene().getWindow();
        stage.close();
    }

    // Method to initialize the controller with data
    public void initialize() {
        // Set sample data - you would normally get this from your application logic
        usernameLabel.setText("U S E R N A M E");
        transactionIdLabel.setText("ID Transaction        : 202405031925309881");
        nameLabel.setText("Name                     : Username");
        statusLabel.setText("Status                     : Active");
        paymentMethodLabel.setText("Payment Method   : D A N A");
        endsInLabel.setText("Ends In                   : 5 April 2024, 07:25 PM");
        membershipTypeLabel.setText("Membership Type  : Subscriber");
        reminderMessageLabel.setText("Your membership is about to expire.\nRenew now to continue enjoying benefits.");
    }
}
