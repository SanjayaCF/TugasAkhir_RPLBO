module org.example.week10 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens org.example.membershipapp to javafx.fxml;
    exports org.example.membershipapp;
}

