package org.example.membershipapp.view;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class stopMembershipController {

    @FXML
    private Button btnYes;

    @FXML
    private Button btnNo;

    @FXML
    private Button btnClose;

    @FXML
    private void onYesClick() {
        // Logic to stop the membership
        System.out.println("Membership stopped.");
        // Close the dialog
        Stage stage = (Stage) btnYes.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void onNoClick() {
        // Close the dialog without doing anything
        Stage stage = (Stage) btnNo.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void onCloseClick() {
        // Close the dialog without doing anything
        Stage stage = (Stage) btnClose.getScene().getWindow();
        stage.close();
    }
}
