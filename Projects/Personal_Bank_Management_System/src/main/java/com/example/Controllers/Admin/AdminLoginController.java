package com.example.Controllers.Admin;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class AdminLoginController {

    @FXML
    private Scene scene;
    @FXML
    private Stage stage;

    public void AdminLoginPage(ActionEvent e) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("/Inventory/FXML/Admin/AdminLogin.fxml"));
        stage = (Stage)((Node)e.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 300, 400);
        stage.setScene(scene);
        stage.show();

    }

    public void loginButtonOnAction(ActionEvent e) throws IOException {

        CreateUsersController createUsersController = new CreateUsersController();
        createUsersController.CreateUsersPage(e);

    }

}
