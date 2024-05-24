/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.example.membershipapp.view;

/**
 *
 * @author SanjayaCF
 */

import org.example.membershipapp.manager.databaseConnect;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class addMembershipController extends switchScenesController{
    private static Connection connection;

    public addMembershipController() {
        connection = databaseConnect.getConnection();
    }

    @FXML
    private TextField membershipCategoryTextField;
    @FXML
    private TextField membershipNameTextField;
    @FXML
    private TextField priceTextField;
    @FXML
    private ComboBox<String> currencyComboBox;
    @FXML
    private ComboBox<String> paymentIntervalComboBox;
    @FXML
    private TextField membershipLinkTextField;
    @FXML
    private TextArea noteTextArea;
    @FXML
    private RadioButton autoPaymentYesRadioButton;
    @FXML
    private DatePicker startDatePicker;
    @FXML
    private DatePicker expirationDatePicker;
    @FXML
    private TextArea benefitTextArea;

    
    public void initialize() {
        ObservableList<String> currencyOptions = FXCollections.observableArrayList("IDR", "USD", "EUR", "GBP");
        currencyComboBox.setItems(currencyOptions);

        ObservableList<String> paymentIntervalOptions = FXCollections.observableArrayList("Monthly", "Yearly");
        paymentIntervalComboBox.setItems(paymentIntervalOptions);
    }
    
    @FXML
    protected void onSaveButtonClick(ActionEvent event) throws IOException {
        String membershipCategory = membershipCategoryTextField.getText();
        String membershipName = membershipNameTextField.getText();
        String price = priceTextField.getText();
        String currency = currencyComboBox.getValue();
        currency = (currency == null) ? "IDR" : currency;
        String paymentInterval = paymentIntervalComboBox.getValue();
        paymentInterval = (paymentInterval == null) ? "Monthly" : paymentInterval;
        String membershipLink = membershipLinkTextField.getText();
        String note = noteTextArea.getText();
        boolean autoPayment = autoPaymentYesRadioButton.isSelected();
        String startDate = startDatePicker.getValue().toString();
        String expirationDate = expirationDatePicker.getValue().toString();
        String benefit = benefitTextArea.getText();

        Alert a;
        final String SQL = "INSERT INTO memberships VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(SQL)) {
            ps.setInt(1, databaseConnect.countLen("memberships") + 1);
            ps.setInt(2, menuController.userID);
            ps.setString(3, membershipName);
            ps.setString(4, price);
            ps.setString(5, membershipLink);
            ps.setBoolean(6, autoPayment);
            ps.setString(7, startDate);
            ps.setString(8, expirationDate);
            ps.setString(9, benefit);
            ps.setString(10, note);
            ps.setString(11, membershipCategory);
            ps.setString(12, currency);
            ps.setString(13, paymentInterval);
            ps.executeUpdate();

            a = new Alert(Alert.AlertType.CONFIRMATION);
            a.setHeaderText("Success");
            a.setContentText("Membership Added");
            a.showAndWait();
            switchToMenuPage(event);
        } catch (SQLException e) {
            e.printStackTrace();
            a = new Alert(Alert.AlertType.ERROR);
            a.setHeaderText("Error");
            a.setContentText("Failed to add membership");
            a.showAndWait();
        }
    }
    
    @FXML
    protected void onBackButtonClick(ActionEvent event) throws IOException {
        switchToMenuPage(event);
    }

    
}

