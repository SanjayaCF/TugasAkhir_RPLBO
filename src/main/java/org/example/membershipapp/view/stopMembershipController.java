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
        System.out.println("Membership stopped.");
        // Close the dialog
        Stage stage = (Stage) btnYes.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void onNoClick() {
        Stage stage = (Stage) btnNo.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void onCloseClick() {
        Stage stage = (Stage) btnClose.getScene().getWindow();
        stage.close();
    }
}
