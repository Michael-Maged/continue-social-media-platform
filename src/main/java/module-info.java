module com.example.saisoof {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.saisoof to javafx.fxml;
    exports com.example.saisoof;
}