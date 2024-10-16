package org.example.membershipapp.view;

import org.example.membershipapp.manager.SessionManager;
import java.io.IOException;
import java.util.Objects;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class switchScenesController {

    private Stage stage;
    private Scene scene;
    private Parent root;
    

    public void switchToLoginPage(ActionEvent event) throws IOException {
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("LoginAccount.fxml")));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void switchToRegisterPage(ActionEvent event) throws IOException {
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("RegisterAccount.fxml")));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    
    public void switchToAdminPage(ActionEvent event) throws IOException {
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("adminDashboard.fxml")));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Admin Dashboard");
        stage.show();
    }

    public void switchToMenuPage(Event event) throws IOException {
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Menu.fxml")));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Menu");
        stage.show();
    }
    

    public void switchToAccount(ActionEvent event) throws IOException {
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("accountInformation.fxml")));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Account");
        stage.show();
    }

    
    public void switchToCreateMembershipPage(ActionEvent event) throws IOException {
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("createMembership.fxml")));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Create Membership");
        stage.show();
    }

    public void switchToPayment(ActionEvent event) throws IOException {
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("paymentMethod.fxml")));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Payment");
        stage.show();
    }


    public void switchToLogOut(ActionEvent event) throws IOException {
        SessionManager.getInstance().logout();
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("loginAccount.fxml")));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Login");
        stage.show();
    }

    public void switchToDetailInformationPage(ActionEvent event) throws IOException {
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("DetailInformation.fxml")));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Information");
        stage.show();
    }

    public void switchToListActivityPage(ActionEvent event) throws IOException {
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("List Activity.fxml")));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Activity");
        stage.show();
    }

    public void switchToRekomendasiPage(ActionEvent event) throws IOException {
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Rekomendasi.fxml")));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Reccomendation");
        stage.show();
    }

    public void switchToReminderPage(ActionEvent event) throws IOException {
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Reminder.fxml")));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Reminder");
        stage.show();
    }

    public void switchToStopMembershipPage(ActionEvent event) throws IOException {
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Stop the Membership.fxml")));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Stop Membership");
        stage.show();
    }

    public void switchToTotalOutcomePage(ActionEvent event) throws IOException {
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Total Outcome.fxml")));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Outcome");
        stage.show();
    }

    public void switchToForgotPasswordPage(ActionEvent event) throws IOException {
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("ForgotPassword.fxml")));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Forgot Password");
        stage.show();
    }

    public void switchToConfirmationLoginPage(ActionEvent event) throws IOException {
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("ConfirmationLoginCode.fxml")));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Confirmation");
        stage.show();
        showLoginCode();
    }

    public void showLoginCode() throws IOException {
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("LoginCodePopUp.fxml")));
        stage = new Stage();
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setScene(new Scene(root));
        stage.setTitle("Login Code");
        stage.show();
    }
    
        public void createMembership() throws IOException {
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("createMembership.fxml")));
        stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setTitle("Create Membership");
        stage.show();
    }

    public void closePage(ActionEvent event) throws IOException {
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.close();
    }
}