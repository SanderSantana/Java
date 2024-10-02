package com.example.Controllers.Client;

import com.example.DatabaseConnection;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.event.ActionEvent;



import java.io.IOException;
import java.sql.Connection;

public class RegisterController extends DatabaseConnection {

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



    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField, reEnterPasswordField, tokenField;
    @FXML
    private Label warningLabel;


    public void registerButtonOnAction() {

        int isPasswordValid = validatePassword();

        if (isPasswordValid == 0) {

            warningLabel.setVisible(true);
            return;

        }
        Connection connection = getConnection();

        String registerUser = "UPDATE UserProfile SET Username = 'DaVicent', Password = '123456' WHERE Token = '0000';";
        

    }

    public int validatePassword() {

        if(passwordField.getText().equals(reEnterPasswordField.getText())) {

            return 1;

        }
        else return 0;

    }


}
