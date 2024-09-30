package com.example.Controllers.Admin;

import com.sun.javafx.scene.control.behavior.ButtonBehavior;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class DepositController {

    @FXML
    private Stage stage;
    @FXML
    private Scene scene;
    public void DepositPage(ActionEvent e) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("/Inventory/FXML/Admin/Deposit.fxml"));
        stage = (Stage)((Node)e.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }

    public void createButtonOnAction(ActionEvent e) throws IOException, SQLException {

        CreateUsersController createUsersController = new CreateUsersController();
        createUsersController.CreateUsersPage(e);

    }

    public void editButtonOnAction(ActionEvent e) throws IOException {

        EditUserController editUserController = new EditUserController();
        editUserController.EditUserPage(e);

    }

    public void logoutButtonOnAction(ActionEvent e) throws IOException {

        AdminLoginController adminLoginController = new AdminLoginController();
        adminLoginController.AdminLoginPage(e);

    }

}
