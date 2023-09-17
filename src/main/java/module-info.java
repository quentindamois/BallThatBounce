module com.example.assignment3_3 {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.assignment3_3 to javafx.fxml;
    exports com.example.assignment3_3;
}