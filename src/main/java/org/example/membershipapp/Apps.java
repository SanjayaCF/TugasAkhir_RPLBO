package org.example.membershipapp;

import org.example.membershipapp.manager.SessionManager;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Apps extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        String resource = SessionManager.getInstance().isLoggedIn()?"Menu":"loginAccount";
        FXMLLoader fxmlLoader = new FXMLLoader(Apps.class.getResource("view/"+resource+".fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle(resource);
        stage.setScene(scene);
        stage.show();
    }

    public static void start(String[] args) {
        launch();
    }
}