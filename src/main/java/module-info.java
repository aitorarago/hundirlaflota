module com.example.trabajofinaluf3 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.logging;


    opens com.example.trabajofinaluf3 to javafx.fxml;
    exports com.example.trabajofinaluf3;
    exports com.example.trabajofinaluf3.Tcp;
    opens com.example.trabajofinaluf3.Tcp to javafx.fxml;
}