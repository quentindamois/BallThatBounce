module com.example.assignment_3boncing_ball {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.assignment3_3 to javafx.fxml;
    exports com.example.assignment3_3;
}