package org.example.membershipapp.view;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import org.example.membershipapp.manager.databaseConnect;

public class detailInformationController {

    @FXML
    private Label membershipLinkLabel;
    
    @FXML
    private Label categoryLabel;
    
    @FXML
    private Label noteLabel;

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
    private Label detailUsername;
    
    @FXML
    private Label detailMembershipName;

    @FXML
    private Button btnStopMembership;

    @FXML
    private Button btnBack;
    
    private static Connection connection;

    public detailInformationController() {
        connection = databaseConnect.getConnection();
    }

public void initialize() throws SQLException {
    String query = "SELECT * FROM users_membership NATURAL JOIN memberships_category WHERE membershipId = ?";
    try (PreparedStatement ps = connection.prepareStatement(query)) {
        ps.setInt(1, menuController.choosenMembershipId);
        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                detailUsername.setText(String.join(" ",menuController.userName.split("")));
                detailMembershipName.setText(rs.getString("membershipName"));
                categoryLabel.setText(rs.getString("membershipCategory"));
                noteLabel.setText(rs.getString("note"));
                membershipLinkLabel.setText(rs.getString("membershipLink"));
                priceLabel.setText(rs.getString("currency")+" "+rs.getString("price"));
                membershipPeriodLabel.setText(rs.getString("dateStart") + " - " + rs.getString("dateEnd"));
                paymentMethodLabel.setText(rs.getBoolean("autoPayment") ? "Auto Paid" : "Paid");
                autoPaymentLabel.setText("Active");
                nextPaymentLabel.setText(rs.getString("dateEnd"));
                benefitLabel.setText(rs.getString("benefit"));
            }
        }
    }
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


