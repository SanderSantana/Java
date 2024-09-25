package com.example.Controllers.Client;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.event.ActionEvent;



import java.io.IOException;

public class RegisterController {

    @FXML
    private Stage stage;
    @FXML
    private Scene scene;

    public void registerPage(ActionEvent e) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("/Inventory/FXML/User/Register.fxml"));
        stage = (Stage)((Node)e.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }

    public void loginButtonOnAction(ActionEvent e) throws IOException {

        ClientLoginController clientLoginController = new ClientLoginController();
        clientLoginController.clientLoginPage(e);

    }


}
