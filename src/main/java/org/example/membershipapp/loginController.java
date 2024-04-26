package org.example.membershipapp;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;

public class loginController extends switchScenesController{
    private static Connection connection;

    public loginController() {
        connection = databaseConnect.getConnection();
    }

    @FXML private TextField txtUsername;
    @FXML private PasswordField txtPassword;

//    @FXML
//    protected void onKeyPressEvent(KeyEvent event) throws IOException, SQLException {
//        if( event.getCode() == KeyCode.ENTER ) {
//            btnLoginClick(event);
//        }
//    }

    @FXML
    protected void btnLoginClick(ActionEvent event) throws IOException, SQLException {
        Alert a;
        final String SQL = "SELECT * FROM users WHERE username = ? AND password = ?";
        try (PreparedStatement ps = connection.prepareStatement(SQL);) {
            ps.setString(1, txtUsername.getText());
            ps.setString(2, txtPassword.getText());
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                txtUsername.getParent().getScene().getWindow().hide();
                switchToMenuPage(event);

            } else {
                a = new Alert(Alert.AlertType.ERROR);
                a.setHeaderText("Error");
                a.setContentText("Login failed!! Please check again.");
                a.showAndWait();
                txtUsername.requestFocus();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @FXML
    protected void SwitchToRegisPage(ActionEvent event) throws IOException {
        switchToRegisterPage(event);
    }

}

