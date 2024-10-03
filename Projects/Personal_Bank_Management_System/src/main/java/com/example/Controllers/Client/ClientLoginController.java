package com.example.Controllers.Client;

import com.example.DatabaseConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class ClientLoginController extends DatabaseConnection {

    @FXML
    private Stage stage;
    @FXML
    private Scene scene;

    public void clientLoginPage(ActionEvent e) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("/Inventory/FXML/User/ClientLogin.fxml"));
        stage = (Stage)((Node)e.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 300, 400);
        stage.setScene(scene);
        stage.show();

    }

    public void registerButtonOnAction(ActionEvent e) throws IOException {

        RegisterController registerController = new RegisterController();
        registerController.registerPage(e);

    }

    @FXML
    private Label warningLabel;
    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    public void loginButtonOnAction(ActionEvent e) throws IOException {

        Connection connection = getConnection();
        WelcomeController welcomeController = new WelcomeController(loggedinUsername);

        try {


            Statement statement = connection.createStatement();



            String verifyLogin = "SELECT COUNT(1) FROM UserProfile WHERE Username = '"+ usernameField.getText() +"' AND Password = '"+ passwordField.getText() +"';";

            ResultSet queryResult = statement.executeQuery(verifyLogin);

            while(queryResult.next())  {

                if (queryResult.getInt(1) == 1) {

                    UserSession.getInstance().setUsername(usernameField.getText());
                    successMessage();
                    welcomeController();



                }
                else {

                    failureMessage();
                    clearFields();

                }

            }



        }catch (Exception ex){

            ex.printStackTrace();
            failureMessage();


        }

    }

    private void welcomeController() throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Inventory/FXML/User/Welcome.fxml"));
        Parent root = fxmlLoader.load();

    }

    public void successMessage(){

        warningLabel.setVisible(true);
        warningLabel.setText("All set!");
        warningLabel.setStyle("-fx-text-fill: #00ef00");

    }


    public void failureMessage(){

        warningLabel.setVisible(true);
        warningLabel.setText("Error Occurred");
        warningLabel.setStyle("-fx-text-fill: #ff0000");

    }

    public void clearFields(){

        usernameField.clear();
        passwordField.clear();


    }

}
