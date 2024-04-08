module com.example.membershipapp {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.membershipapp to javafx.fxml;
    exports com.example.membershipapp;
}