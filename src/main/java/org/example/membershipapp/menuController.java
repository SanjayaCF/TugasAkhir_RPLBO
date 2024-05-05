package org.example.membershipapp;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.event.ActionEvent;

public class menuController extends switchScenesController{

    private static Connection connection;

    public menuController() {
        connection = databaseConnect.getConnection();
    }
    public static int userID;
    
    @FXML
    private ScrollPane scrollPane;

    @FXML
    private VBox scrollPaneContent;

    public void initialize() throws SQLException {
        try {
            double totalHeight = 0.0;
            
            final String SQL = "SELECT * FROM memberships WHERE userID = ?";
            try (PreparedStatement ps = connection.prepareStatement(SQL);) {
            ps.setInt(1, menuController.userID);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("membershipItems.fxml"));
                AnchorPane membershipItem = loader.load();
                membershipItemsController itemController = loader.getController();

                itemController.setName(rs.getString("membershipName"));
                String currency = rs.getString("currency");
                itemController.setPrice(currency+" "+rs.getString("price"));
                itemController.setExpired("Expired "+ rs.getString("dateEnd"));
                itemController.setCategory(rs.getString("category"));
                itemController.setBenefit(rs.getString("benefit"));
                itemController.setPayType(rs.getBoolean("autoPayment")?"Auto Paid":"Paid");
                itemController.setInterval(rs.getString("paymentInterval"));
                scrollPaneContent.getChildren().add(membershipItem);

                totalHeight += membershipItem.getPrefHeight();
            }
            
            }

            scrollPaneContent.setPrefHeight(totalHeight);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    
    @FXML
    protected void SwitchToAddMembership(ActionEvent event) throws IOException {
        switchToAddMembershipPage(event);
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
}

