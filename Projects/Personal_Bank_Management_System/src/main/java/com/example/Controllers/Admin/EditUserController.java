package com.example.Controllers.Admin;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.io.IOException;

public class EditUserController {

    @FXML
    private Stage stage;
    @FXML
    private Scene scene;

    public void EditUserPage(ActionEvent e) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("/Inventory/FXML/Admin/EditUser.fxml"));
        stage = (Stage)((Node)e.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();


    }

    public void CreateButtonOnAction(ActionEvent e) throws IOException {

        CreateUsersController createUsersController = new CreateUsersController();
        createUsersController.CreateUsersPage(e);

    }

    public void logoutButtonOnAction(ActionEvent e)throws IOException {

        AdminLoginController adminLoginController = new AdminLoginController();
        adminLoginController.AdminLoginPage(e);

    }

    public void depositButtonOnAction(ActionEvent e) throws IOException {

        DepositController depositController = new DepositController();
        depositController.DepositPage(e);

    }

}
