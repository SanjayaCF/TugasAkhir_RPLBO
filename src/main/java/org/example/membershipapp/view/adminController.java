package org.example.membershipapp.view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import org.example.membershipapp.manager.databaseConnect;

public class adminController extends switchScenesController implements Initializable {
    private static Connection connection;

    public adminController() {
        connection = databaseConnect.getConnection();
    }

    @FXML
    private VBox userListScrollPanel;
    @FXML
    private VBox membershipsScrollPanel;
    @FXML
    private Button btnMenu;
    @FXML
    private Button btnMemberships;
    @FXML
    private Button btnEditPrivilege;
    @FXML
    private Pane pnlMenu;
    @FXML
    private Pane pnlMemberships;
    @FXML
    private Label totalUser;
    @FXML
    private Label totalAdmin;
    @FXML
    private Label totalMembership;
    @FXML
    private Label nameLabel;

    private Button currentActiveButton;

    @Override
    public void initialize(URL location, ResourceBundle resources)  {
        try {
            btnMenu.setStyle("-fx-background-color: white; -fx-text-alignment: center; -fx-text-fill: black; -fx-font-weight: bold; -fx-border-color: white;");
            totalUser.setText(Integer.toString(databaseConnect.countCondition("users","not privilege")));
            totalAdmin.setText(Integer.toString(databaseConnect.countCondition("users","privilege")));
            loadUsers();
            loadMemberships();
            currentActiveButton = btnMenu;
        } catch (SQLException | IOException ex) {
            Logger.getLogger(adminController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void loadUsers() throws SQLException, IOException {
        final String SQL = "SELECT * FROM users WHERE not id = ?";
        try (PreparedStatement ps = connection.prepareStatement(SQL)) {
            ps.setInt(1, menuController.userID);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                nameLabel.setText(menuController.userName);
                FXMLLoader loader = new FXMLLoader(getClass().getResource("userList.fxml"));
                HBox userBox = loader.load();      

                userBox.setOnMouseEntered(event -> userBox.setStyle("-fx-background-color: #ddddff"));
                userBox.setOnMouseExited(event -> userBox.setStyle("-fx-background-color: white"));

                userListController itemController = loader.getController();
                String idUser = rs.getString("id");
                itemController.setNo(idUser);
                itemController.setUsername(rs.getString("username"));
                itemController.setName(rs.getString("name"));
                int userTotalMemberships = databaseConnect.countCondition("users_membership", "userID = "+idUser);
                itemController.setMembership(Integer.toString(userTotalMemberships));
                itemController.setRole(rs.getBoolean("privilege") ? "Admin" : "User");
                

                userListScrollPanel.getChildren().add(userBox);
            }
        }
    }
    
    private void loadMemberships() throws SQLException, IOException {
        totalMembership.setText(Integer.toString(databaseConnect.countLen("memberships")));
        final String SQL = "SELECT * FROM memberships NATURAL JOIN memberships_category";
        try (PreparedStatement ps = connection.prepareStatement(SQL)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("membershipItems.fxml"));
                AnchorPane userMembership = loader.load();      

                userMembership.setOnMouseEntered(event -> userMembership.setStyle("-fx-background-color: #ddddff"));
                userMembership.setOnMouseExited(event -> userMembership.setStyle("-fx-background-color: white"));

                membershipItemsController itemController = loader.getController();

                itemController.setCategory(rs.getString("membershipCategory"));
                itemController.setName(rs.getString("membershipName"));
                itemController.setBenefit(rs.getString("benefit"));
                itemController.setExpired(rs.getString("endDate"));
                itemController.setPrice("IDR "+formatPrice(rs.getFloat("price")));
                itemController.setInterval("Monthly/Yearly");
                itemController.setPayType(rs.getBoolean("autoPayment")?"Auto Paid":"Paid");
                
//                userMembership.setOnMouseClicked(event -> {
//                    try {
//                        itemController.setChosenId(rs.getInt("membershipId"));
//                    } catch (SQLException ex) {
//                        Logger.getLogger(adminController.class.getName()).log(Level.SEVERE, null, ex);
//                    }
//                });
                
                membershipsScrollPanel.getChildren().add(userMembership);
            }
        }
    }
    
    public void refreshMemberships() {
        membershipsScrollPanel.getChildren().clear();
        try {
            loadMemberships();
        } catch (SQLException | IOException ex) {
            ex.printStackTrace();
        }
    }
    
    
    @FXML
    protected void displayMembership(int selectedId) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("createMembership.fxml"));
        Pane newMembershipPane = loader.load();

        createMembershipController controller = loader.getController();
        controller.setOnMembershipSaved(this::refreshMemberships);
        //controller.setChosen("WHERE membershipId = "+Integer.toString(selectedId));

        Stage stage = new Stage();
        stage.setScene(new Scene(newMembershipPane));
        stage.show(); 
    }
    
    public static String formatPrice(float value) {
        double formattedValue = value / 1000.0;

        DecimalFormatSymbols symbols = new DecimalFormatSymbols();
        symbols.setGroupingSeparator('.');
        symbols.setDecimalSeparator(',');

        DecimalFormat df = new DecimalFormat("#,##0.000", symbols);

        return df.format(formattedValue);
    }
    

    @FXML
    public void handleClicks(ActionEvent actionEvent) throws IOException {
        if (actionEvent.getSource() == btnMenu) {
            pnlMenu.toFront();
        } else if (actionEvent.getSource() == btnMemberships) {
            pnlMemberships.toFront();
        }

        if (actionEvent.getSource() instanceof Button clickedButton) {
            switchButtonStyles(clickedButton);
        }
    }
    
    @FXML
    protected void SwitchToMenuPage(ActionEvent event) throws IOException {
        switchToMenuPage(event);
    }
    
    @FXML
    protected void popUpCreateMembershipPage() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("createMembership.fxml"));
        Pane newMembershipPane = loader.load();

        createMembershipController controller = loader.getController();
        controller.setOnMembershipSaved(this::refreshMemberships);

        Stage stage = new Stage();
        stage.setScene(new Scene(newMembershipPane));
        stage.show();
    }
    

    private void switchButtonStyles(Button clickedButton) {
        currentActiveButton.setStyle("-fx-background-color: linear-gradient(to top, #05071F, #303A5C, #60698B); -fx-text-fill: white; -fx-text-alignment: center; -fx-font-weight: bold; -fx-border-color: white;");
        clickedButton.setStyle("-fx-background-color: white; -fx-text-fill: black; -fx-text-alignment: center; -fx-font-weight: bold; -fx-border-color: white;");
        currentActiveButton = clickedButton;
    }
}
