package com.example.Controllers.Client;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class transferController {

    @FXML
    private Stage stage;
    @FXML
    private Scene scene;
   public void transferPage(ActionEvent e) throws IOException {

       Parent root = FXMLLoader.load(getClass().getResource("/Inventory/FXML/User/transfer.fxml"));
       stage = (Stage)((Node)e.getSource()).getScene().getWindow();
       Scene scene = new Scene(root);
       stage.setScene(scene);
       stage.show();
   }

    public void logoutButtonOnAction(ActionEvent e) throws IOException {

        ClientLoginController clientLoginController = new ClientLoginController();
        clientLoginController.clientLoginPage(e);

    }

    public void dashboardButtonOnAction(ActionEvent e) throws IOException{

       WelcomeController welcomeController = new WelcomeController();
       welcomeController.welcomePage(e);

    }


}
