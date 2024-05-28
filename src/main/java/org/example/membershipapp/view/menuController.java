package org.example.membershipapp.view;

import org.example.membershipapp.manager.SessionManager;
import org.example.membershipapp.manager.databaseConnect;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class menuController extends switchScenesController {

    private static Connection connection;

    public menuController() {
        connection = databaseConnect.getConnection();
    }
    

    public static int userID = SessionManager.getInstance().getUserId();
    public static String userName = SessionManager.getInstance().getUserName();

    @FXML
    private VBox scrollPaneContent;
    
    @FXML
    private Button btnAdmin;
    
    @FXML
    private TextField searchField;
    
    @FXML
    private Label welcomeNama;

    public void initialize() throws SQLException, IOException {
        loadMemberships("");
        userInformation();
        
        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            try {
                loadMemberships(newValue);
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (IOException ex) {
                Logger.getLogger(menuController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }
    
    private void userInformation() throws SQLException {
        welcomeNama.setText("Welecome, "+ menuController.userName);
        final String SQL = "SELECT * FROM users WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(SQL);) {
                ps.setInt(1, menuController.userID);
                ResultSet rs = ps.executeQuery();
                
                btnAdmin.setVisible(rs.getBoolean("privilege"));
                               
        }
    }

//    private void loadMemberships(String filter) throws SQLException {
//        try {
//            
//
//            final String SQL = "SELECT * FROM users_membership WHERE userID = ? AND membershipName LIKE ?";
//            try (PreparedStatement ps = connection.prepareStatement(SQL);) {
//                ps.setInt(1, menuController.userID);
//                ps.setString(2, "%" + filter + "%");
//                ResultSet rs = ps.executeQuery();
//                while (rs.next()) {
//                    FXMLLoader loader = new FXMLLoader(getClass().getResource("userMembershipItems.fxml"));
//                    AnchorPane membershipItem = loader.load();
//                    membershipItemsController itemController = loader.getController();
//
//                    itemController.setName(rs.getString("membershipName"));
//                    String currency = rs.getString("currency");
//                    itemController.setPrice(currency + " " + rs.getString("price"));
//                    itemController.setExpired("Expired " + rs.getString("dateEnd"));
//                    itemController.setCategory(rs.getString("category"));
//                    itemController.setBenefit(rs.getString("benefit"));
//                    itemController.setPayType(rs.getBoolean("autoPayment") ? "Auto Paid" : "Paid");
//                    itemController.setInterval(rs.getString("paymentInterval"));
//                    scrollPaneContent.getChildren().add(membershipItem);
//
//                }
//            }
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
    
    private void loadMemberships(String filter) throws SQLException, IOException {
        try {
        scrollPaneContent.getChildren().clear();
        final String SQL = "SELECT * FROM users_membership WHERE userID = ? AND membershipName LIKE ?";
            try (PreparedStatement ps = connection.prepareStatement(SQL)) {
                ps.setInt(1, menuController.userID);
                ps.setString(2, "%" + filter + "%");
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("userMembershipItems.fxml"));
                    AnchorPane userBox = loader.load();      

                    userBox.setOnMouseEntered(event -> userBox.setStyle("-fx-background-color: #ddddff"));
                    userBox.setOnMouseExited(event -> userBox.setStyle("-fx-background-color: white"));

                    membershipItemsController itemController = loader.getController();

                    itemController.setName(rs.getString("membershipName"));
                    String currency = rs.getString("currency");
                    itemController.setPrice(currency + " " + rs.getString("price"));
                    itemController.setExpired("Expired " + rs.getString("dateEnd"));
                    itemController.setCategory(rs.getString("category"));
                    itemController.setBenefit(rs.getString("benefit"));
                    itemController.setPayType(rs.getBoolean("autoPayment") ? "Auto Paid" : "Paid");
                    itemController.setInterval(rs.getString("paymentInterval"));
                    scrollPaneContent.getChildren().add(userBox);
                }
            }
    } catch (IOException e) {
            e.printStackTrace();
        }
    }

    
    @FXML
    protected void SwitchToAddMembership() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("addMembership.fxml"));
        Pane newMembershipPane = loader.load();

        addMembershipController controller = loader.getController();
        controller.setOnMembershipSaved(this::refreshMemberships);
        Stage stage = new Stage();
        stage.setScene(new Scene(newMembershipPane));
        stage.setTitle("Add Membership");
        stage.show();
    }
    
    public void refreshMemberships() {
        try {
            loadMemberships("");
        } catch (SQLException | IOException ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    protected void SwitchToPayment(ActionEvent event) throws IOException {
        switchToPayment(event);
    }

    @FXML
    protected void SwitchToMenuPage(ActionEvent event) throws IOException {
        switchToMenuPage(event);
    }

    @FXML
    protected void SwitchToCustomerService(ActionEvent event) throws IOException {
        switchToCustomerService(event);
    }

    @FXML
    protected void SwitchToPrivacyPolicy(ActionEvent event) throws IOException {
        switchToPrivacyPolicy(event);
    }

    @FXML
    protected void SwitchToLogOut(ActionEvent event) throws IOException {
        switchToLogOut(event);
    }

    @FXML
    protected void SwitchToAccount(ActionEvent event) throws IOException {
        switchToAccount(event);
    }
    
    @FXML
    protected void SwitchToAdminPage(ActionEvent event) throws IOException {
        switchToAdminPage(event);
    }
    
}
