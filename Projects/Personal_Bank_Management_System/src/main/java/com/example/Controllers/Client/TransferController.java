package com.example.Controllers.Client;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class TransferController {

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

       WelcomeController welcomeController = new WelcomeController(loggedinUsername);
       welcomeController.welcomePage(e);

    }

    public void profileButtonOnAction(ActionEvent e) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Inventory/FXML/User/Profile.fxml"));
        Parent root = loader.load();

        ProfileController profileController = loader.getController();

        profileController.setLoggedInUsername(UserSession.getInstance().getUsername());

        profileController.userDetails();

        stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }

    public void sendMoneyButtonOnAction(ActionEvent e) throws IOException {

        TransferController transferController = new TransferController();
        transferController.transferPage(e);

    }


}
