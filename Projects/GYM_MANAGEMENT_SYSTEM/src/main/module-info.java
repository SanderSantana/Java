    module com.example {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires java.sql;

    opens com.example to javafx.fxml;
    opens com.example.Controllers to javafx.fxml;
    exports com.example.Controllers ;
    exports com.example;
}