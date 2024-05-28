package org.example.membershipapp.view;

import org.example.membershipapp.manager.databaseConnect;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class addMembershipController extends switchScenesController {
    private static Connection connection;
    private Runnable onMembershipSaved;
    private float originalPrice;

    public addMembershipController() {
        connection = databaseConnect.getConnection();
    }

    public void setOnMembershipSaved(Runnable onMembershipSaved) {
        this.onMembershipSaved = onMembershipSaved;
    }

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
    private Label autoPayment;
    @FXML
    private TextField startDatePicker;
    @FXML
    private TextField expirationDatePicker;
    @FXML
    private TextArea benefitTextArea;
    @FXML
    private ComboBox<String> membershipCategoryComboBox;
    @FXML
    private ComboBox<String> membershipsComboBox;
    @FXML
    private Button btnBack;
    @FXML
    private Button btnSave;

    public void initialize() {
        priceTextField.setEditable(false);
        membershipLinkTextField.setEditable(false);
        startDatePicker.setEditable(false);
        expirationDatePicker.setEditable(false);
        benefitTextArea.setEditable(false);

        ObservableList<String> currencyOptions = FXCollections.observableArrayList("IDR", "USD", "EUR", "GBP");
        currencyComboBox.setItems(currencyOptions);

        ObservableList<String> paymentIntervalOptions = FXCollections.observableArrayList("Monthly", "Yearly");
        paymentIntervalComboBox.setItems(paymentIntervalOptions);

        ObservableList<String> categoryOptions = FXCollections.observableArrayList();
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
            clearForm();
            String membershipCategory = membershipCategoryComboBox.getValue();
            ObservableList<String> membershipOption = FXCollections.observableArrayList();
            try {
                String query2 = "SELECT * FROM memberships NATURAL JOIN memberships_category WHERE membershipCategory = ?";
                PreparedStatement ps = connection.prepareStatement(query2);
                ps.setString(1, membershipCategory);
                ResultSet rs2 = ps.executeQuery();
                while (rs2.next()) {
                    membershipOption.add(rs2.getString("membershipName"));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            membershipsComboBox.setItems(membershipOption);
        });

        membershipsComboBox.setOnAction(event -> {
            String membershipsName = membershipsComboBox.getValue();
            try {
                String query3 = "SELECT * FROM memberships WHERE membershipName = ?";
                PreparedStatement ps3 = connection.prepareStatement(query3);
                ps3.setString(1, membershipsName);
                ResultSet rs3 = ps3.executeQuery();
                if (rs3.next()) {
                    originalPrice = rs3.getFloat("price");
                    updatePrice();
                    membershipLinkTextField.setText(rs3.getString("membershipLink"));

                    String payment = rs3.getBoolean("autoPayment") ? "YES" : "NO";
                    String color = rs3.getBoolean("autoPayment") ? "GREEN" : "RED";
                    autoPayment.setText(payment);
                    autoPayment.setStyle(String.format("-fx-background-color: %s; -fx-background-radius: 5px; -fx-font-weight: bold;", color));

                    startDatePicker.setText(rs3.getString("startDate"));
                    expirationDatePicker.setText(rs3.getString("endDate"));
                    benefitTextArea.setText(rs3.getString("benefit"));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });

        paymentIntervalComboBox.setOnAction(event -> updatePrice());
        currencyComboBox.setOnAction(event -> updatePrice());
    }

    @FXML
    protected void onSaveButtonClick(ActionEvent event) throws IOException {
        String membershipCategory = membershipCategoryComboBox.getValue();
        String membershipName = membershipsComboBox.getValue();
        String price = priceTextField.getText();
        String currency = currencyComboBox.getValue();
        currency = (currency == null) ? "IDR" : currency;
        String paymentInterval = paymentIntervalComboBox.getValue();
        paymentInterval = (paymentInterval == null) ? "Monthly" : paymentInterval;
        String membershipLink = membershipLinkTextField.getText();
        String note = noteTextArea.getText();
        boolean isAutoPayment = autoPayment.getText().equals("YES");
        String startDate = startDatePicker.getText();
        String expirationDate = expirationDatePicker.getText();
        String benefit = benefitTextArea.getText();

        Alert a;
        final String SQL = "INSERT INTO users_membership VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(SQL)) {
            ps.setInt(1, databaseConnect.countLen("memberships") + 1);
            ps.setInt(2, menuController.userID);
            ps.setString(3, membershipName);
            ps.setString(4, price);
            ps.setString(5, membershipLink);
            ps.setBoolean(6, isAutoPayment);
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
            onMembershipSaved.run();
            onCloseAddMembership(btnSave);
        } catch (SQLException e) {
            e.printStackTrace();
            a = new Alert(Alert.AlertType.ERROR);
            a.setHeaderText("Error");
            a.setContentText("Failed to add membership");
            a.showAndWait();
        }
    }

    @FXML
    protected void onBackButtonClick() throws IOException {
        onCloseAddMembership(btnBack);
    }

    private void onCloseAddMembership(Button button) throws IOException {
        Stage stage = (Stage) button.getScene().getWindow();
        stage.close();
    }

    public static String formatPrice(float value) {
        DecimalFormatSymbols symbols = new DecimalFormatSymbols();
        symbols.setGroupingSeparator('.');
        symbols.setDecimalSeparator(',');

        DecimalFormat df = new DecimalFormat("#,##0.00", symbols);

        return df.format(value);
    }

    private void updatePrice() {
        float price = originalPrice;
        String paymentInterval = paymentIntervalComboBox.getValue();
        if ("Yearly".equals(paymentInterval)) {
            price *= 12;
        }

        String currency = currencyComboBox.getValue();
        if (currency != null) {
            switch (currency) {
                case "USD" -> price *= 0.000062;
                case "EUR" -> price *= 0.0001143651/2;
                case "GBP" -> price *= 0.000049;
                default -> {
                //https://www.unitconverters.net/ sumber konversi mata uang dari idr ke usd,eur,gbp
                }
            }
        }

        priceTextField.setText(formatPrice(price));
    }
    
    private void clearForm() {
        priceTextField.clear();
        membershipLinkTextField.clear();
        noteTextArea.clear();
        autoPayment.setText("YES/NO");
        autoPayment.setStyle(String.format("-fx-background-color: gray; -fx-background-radius: 5px; -fx-font-weight: bold;"));
        startDatePicker.clear();
        expirationDatePicker.clear();
        benefitTextArea.clear();
        originalPrice = 0.0f;
    }
}
