package org.example.membershipapp.view;

import org.example.membershipapp.manager.SessionManager;
import org.example.membershipapp.manager.databaseConnect;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.sql.*;
import javafx.event.Event;
import javafx.scene.control.CheckBox;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.shape.SVGPath;

public class loginController extends switchScenesController{
    private static Connection connection;

    public loginController() {
        connection = databaseConnect.getConnection();
    }

    @FXML private TextField txtUsername;
    @FXML private PasswordField txtPassword;
    @FXML private TextField passwordVisible;
    @FXML private SVGPath openEye;
    @FXML private SVGPath closeEye;
    @FXML private CheckBox rememberMe;
    
    public void initialize() {
    txtPassword.textProperty().bindBidirectional(passwordVisible.textProperty());

    closeEye.setVisible(false);
    passwordVisible.setVisible(false);
    txtPassword.setVisible(true);
    
    openEye.setOnMouseClicked(event -> {
        closeEye.setVisible(true);
        openEye.setVisible(false);
        passwordVisible.setText(txtPassword.getText());
        txtPassword.setVisible(false);
        passwordVisible.setVisible(true);
    });

    closeEye.setOnMouseClicked(event -> {
        closeEye.setVisible(false);
        openEye.setVisible(true);
        txtPassword.setText(passwordVisible.getText());
        passwordVisible.setVisible(false);
        txtPassword.setVisible(true);
    });
    }
    
    @FXML
    protected void onKeyPressEvent(KeyEvent event) throws IOException, SQLException {
        if( event.getCode() == KeyCode.ENTER ) {
            this.btnLoginClick(event);
        }
    }

    @FXML
    protected void btnLoginClick(Event event) throws IOException, SQLException {
        Alert a;
        final String SQL = "SELECT * FROM users WHERE username = ? AND password = ?";
        try (PreparedStatement ps = connection.prepareStatement(SQL);) {
            ps.setString(1, txtUsername.getText());
            ps.setString(2, txtPassword.getText());
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                if (rememberMe.isSelected()) {
                    SessionManager.getInstance().login(id, name);
                } else {
                    menuController.userID = id;
                    menuController.userName = name;
                }
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
    
    @FXML
    protected void SwitchToForgotPasswordPage(ActionEvent event) throws IOException {
        switchToForgotPasswordPage(event);
    }

}

