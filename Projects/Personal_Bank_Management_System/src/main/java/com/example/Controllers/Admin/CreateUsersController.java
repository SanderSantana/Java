package com.example.Controllers.Admin;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class CreateUsersController {

    @FXML
    private Stage stage;
    public void CreateUsersPage(ActionEvent e) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("/Inventory/FXML/Admin/CreateUsers.fxml"));
        stage = (Stage)((Node)e.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }

    public void logoutButtonOnAction(ActionEvent e) throws IOException {

        AdminLoginController adminLoginController = new AdminLoginController();
        adminLoginController.AdminLoginPage(e);

    }

}
