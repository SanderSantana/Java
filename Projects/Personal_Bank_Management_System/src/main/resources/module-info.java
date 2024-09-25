module com.example.personal_bank_management_system {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.personal_bank_management_system to javafx.fxml;
    exports com.example.personal_bank_management_system;
}