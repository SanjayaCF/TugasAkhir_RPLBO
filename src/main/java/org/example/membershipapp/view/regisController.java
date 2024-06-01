package org.example.membershipapp.view;

import org.example.membershipapp.manager.databaseConnect;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.mindrot.jbcrypt.BCrypt;

import java.io.IOException;
import java.sql.*;

public class regisController extends switchScenesController {
    private static Connection connection;

    public regisController() {
        connection = databaseConnect.getConnection();
    }

    @FXML private TextField txtUsername;
    @FXML private TextField txtName;
    @FXML private PasswordField txtPassword;
    @FXML private PasswordField txtConfirmPassword;

    private boolean validate(String username, String name) {
        boolean valid = false;
        final String SQL = "SELECT * FROM users WHERE username = ? OR name = ?";
        try (PreparedStatement ps = connection.prepareStatement(SQL)) {
            ps.setString(1, username);
            ps.setString(2, name);
            ResultSet rs = ps.executeQuery();

            if (!rs.next()) {
                valid = true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return valid;
    }

    @FXML
    protected void btnRegisClick(ActionEvent event) throws IOException, SQLException {
        Alert a;
        boolean checkPass = txtPassword.getText().equals(txtConfirmPassword.getText());

        if (checkPass && this.validate(txtUsername.getText(), txtName.getText())) {
            final String SQL2 = "INSERT INTO users(id, username, password, name, privilege) VALUES(?, ?, ?, ?, ?)";
            try (PreparedStatement ps = connection.prepareStatement(SQL2)) {
                String hashedPassword = BCrypt.hashpw(txtPassword.getText(), BCrypt.gensalt());

                ps.setInt(1, databaseConnect.countLen("users") + 1);
                ps.setString(2, txtUsername.getText());
                ps.setString(3, hashedPassword);
                ps.setString(4, txtName.getText());
                ps.setBoolean(5, false);
                ps.executeUpdate();

                a = new Alert(Alert.AlertType.CONFIRMATION);
                a.setHeaderText("Success");
                a.setContentText("Account Registered.");
                a.showAndWait();
                txtUsername.getParent().getScene().getWindow().hide();
                switchToLoginPage(event);

            } catch (SQLException e) {
                e.printStackTrace();
            }

        } else {
            a = new Alert(Alert.AlertType.ERROR);
            a.setHeaderText("Error");
            String ctx = (!checkPass) ? "Passwords do not match!" : "Username or Name already exists, try again!";
            a.setContentText(ctx);
            a.showAndWait();
            txtUsername.requestFocus();
        }
    }
}
