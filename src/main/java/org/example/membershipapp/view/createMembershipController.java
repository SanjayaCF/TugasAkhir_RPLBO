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
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class createMembershipController {
    private static Connection connection;
    private Runnable onMembershipSaved;

    public createMembershipController() {
        connection = databaseConnect.getConnection();
    }
    
    public void setOnMembershipSaved(Runnable onMembershipSaved) {
        this.onMembershipSaved = onMembershipSaved;
    }

    @FXML
    private ComboBox<String> membershipCategoryComboBox;
    @FXML
    private TextField newCategoryTextField;
    @FXML
    private TextField membershipNameTextField;
    @FXML
    private TextField priceTextField;
    @FXML
    private TextField membershipLinkTextField;
    @FXML
    private RadioButton autoPaymentYesRadioButton;
    @FXML
    private DatePicker startDatePicker;
    @FXML
    private DatePicker expirationDatePicker;
    @FXML
    private TextField benefitTextField;
    @FXML
    private TextArea benefitTextArea;
    @FXML
    private Button btnCreateMembership;
     
    

    public void initialize() {
        ObservableList<String> categoryOptions = FXCollections.observableArrayList();
        categoryOptions.add("Add New Category");
        try {
            String query = "SELECT membershipCategory FROM memberships_category";
            ResultSet rs = connection.createStatement().executeQuery(query);
            while (rs.next()) {
                categoryOptions.add(rs.getString("membershipCategory"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        membershipCategoryComboBox.setItems(categoryOptions);

        membershipCategoryComboBox.setOnAction(event -> {
            if ("Add New Category".equals(membershipCategoryComboBox.getValue())) {
                newCategoryTextField.setVisible(true);
            } else {
                newCategoryTextField.setVisible(false);
            }
        });
        benefitTextArea.setEditable(false);
    }

    @FXML
    protected void onSaveButtonClick(ActionEvent event) throws IOException {
        String membershipCategory;
        if ("Add New Category".equals(membershipCategoryComboBox.getValue())) {
            membershipCategory = newCategoryTextField.getText();
        } else {
            membershipCategory = membershipCategoryComboBox.getValue();
        }
        String membershipName = membershipNameTextField.getText();
        String price = priceTextField.getText();
        String membershipLink = membershipLinkTextField.getText();
        boolean autoPayment = autoPaymentYesRadioButton.isSelected();
        String startDate = startDatePicker.getValue().toString();
        String expirationDate = expirationDatePicker.getValue().toString();
        String benefit = benefitTextArea.getText();

        Alert a;
        final String SQL = "INSERT INTO memberships VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)";
        final String SQL2 = "SELECT * FROM memberships_category WHERE membershipCategory = ?";
        final String SQL3 = "INSERT INTO memberships_category VALUES(?,?)";
        try (PreparedStatement ps = connection.prepareStatement(SQL);
             PreparedStatement ps2 = connection.prepareStatement(SQL2);
             PreparedStatement ps3 = connection.prepareStatement(SQL3)
                ) {
            
            if (databaseConnect.countCondition("memberships_category", String.format("membershipCategory = '%s'",membershipCategory)) == 0) {
                ps3.setInt(1, databaseConnect.countLen("memberships_category") + 1);
                ps3.setString(2, membershipCategory);
                ps3.executeUpdate();
            }
            
            ps2.setString(1, membershipCategory);
            ResultSet rs2 = ps2.executeQuery();
            
            if (rs2.next()) {
                ps.setInt(1, databaseConnect.countLen("memberships") + 1);
                ps.setInt(2, rs2.getInt("membershipCategoryId"));
                ps.setString(3, membershipName);
                ps.setFloat(4, Float.parseFloat(price));
                ps.setString(5, membershipLink);
                ps.setBoolean(6, autoPayment);
                ps.setString(7, startDate);
                ps.setString(8, expirationDate);
                ps.setString(9, benefit);
                ps.executeUpdate();

                a = new Alert(Alert.AlertType.CONFIRMATION);
                a.setHeaderText("Success");
                a.setContentText("Membership Added");
                a.showAndWait();
                if (onMembershipSaved != null) {
                    onMembershipSaved.run();
                }
                onSaveAndBackButtonClick();
                //this.refreshMemberships();
                
            }
        } catch (SQLException e) {
            e.printStackTrace();
            a = new Alert(Alert.AlertType.ERROR);
            a.setHeaderText("Error");
            a.setContentText("Failed to add membership");
            a.showAndWait();
        }
    }
    
    @FXML
    protected void onAddBenefitButton() {
        String benefit = benefitTextField.getText();
        String benefits = benefitTextArea.getText();
        if (!benefit.isBlank()) {
            benefits += (benefits.isBlank()?"• ":"\n• ")+benefit;
            benefitTextArea.setText(benefits);
            benefitTextField.clear();
        }
    }
    
    @FXML
    protected void onDeleteBenefitButton() {
        String[] benefits = benefitTextArea.getText().split("\n");
        String afterDelete = "";
        for (int i = 0; i<benefits.length-1; i++) {
            afterDelete += (afterDelete.isBlank()?"":"\n")+benefits[i];
        }    
        benefitTextArea.setText(afterDelete);
    }
    
    @FXML
    protected void onSaveAndBackButtonClick() {
        Stage stage = (Stage) btnCreateMembership.getScene().getWindow();
        stage.close();
    }
}


