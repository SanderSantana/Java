package com.example.Controllers.Admin;

import com.example.Controllers.LoginController;
import com.example.DatabaseConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;


import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class adminRegisterPageController {

    @FXML
    private Stage stage;

    @FXML
    private Scene scene;

    @FXML
    private Button exitButton;

    //Register Page scene
    public void register(ActionEvent e) throws Exception {

        Parent root = FXMLLoader.load(getClass().getResource("/Inventory/FXML/Admin/adminRegisterPage.fxml")); //loading scene
        stage = (Stage) ((Node) e.getSource()).getScene().getWindow();

        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    //loads sign in page
    public void signInButtonOnAction(ActionEvent e) throws Exception {

        LoginController loginController = new LoginController();
        loginController.LogInPage(e);

    }

    //exits program
    public void exitButtonOnAction(ActionEvent e) {

        Stage stage = (Stage) exitButton.getScene().getWindow();

        stage.close();

    }

    //loads the welcome page
    public void welcomePage(ActionEvent e) throws Exception {

        WelcomeController welcomeController = new WelcomeController();
        welcomeController.WelcomePage(e);

    }

    @FXML
    private TextField usernameTextField, tokenTextField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button registerButton;

    @FXML
    private Label warningLabel;


    public void validateRegistration(ActionEvent e) throws Exception {

        //Fetches database connection
        DatabaseConnection databaseConnection = new DatabaseConnection();
        Connection connection = databaseConnection.getConnection();

        String token = tokenTextField.getText();
        String username = usernameTextField.getText();
        String password = passwordField.getText();

        String verifyToken = " SELECT COUNT(1) FROM Token \n" +
                "WHERE Token_id = '" + token + "'; ";

        String verifyUsername = " SELECT COUNT(1) FROM UserLogin \n" +
                "WHERE Username = '"+ username +"';  ";

        String insertAdmin = " INSERT INTO UserLogin VALUES ('"+ username +"', '"+ password +"'); \n";

        //statements objects are needed to run the queries
        Statement statement = connection.createStatement();
        Statement statementUsername = connection.createStatement();
        Statement statementInsertAdmin = connection.createStatement();

        //ResultSets objects hold the results of our query
        ResultSet verifyTokenResults = statement.executeQuery(verifyToken);
        ResultSet verifyUsernameResults = statementUsername.executeQuery(verifyUsername);

        try {

            //checks if token inserted exists in the database
            if (verifyTokenResults.next() && verifyTokenResults.getInt(1) == 1) {

                //checks if username inserted already exists in the database
                if (verifyUsernameResults.next() && verifyUsernameResults.getInt(1) == 1) {
                    warningLabel.setText("Username Already Exists");
                    warningLabel.setStyle("-fx-text-fill: red;" + "-fx-font-weight: bold;" + "-fx-font-size: 14");
                } else {
                    statementInsertAdmin.execute(insertAdmin); //inserted details into the database
                    warningLabel.setText("ALL GOOD!");
                    warningLabel.setStyle("-fx-text-fill: green;" + "-fx-font-weight: bold;" + "-fx-font-size: 14");
                    welcomePage(e); //sends user to the welcome page
                }
            } else {
                warningLabel.setText("Invalid Token");
                warningLabel.setStyle("-fx-text-fill: red;" + "-fx-font-weight: bold;" + "-fx-font-size: 14");
            }
        } catch (Exception ev) {
            connection.rollback(); // Rollback the transaction in case of an error
            warningLabel.setText("An Error Occurred!");
            warningLabel.setStyle("-fx-text-fill: red;" + "-fx-font-weight: bold;" + "-fx-font-size: 14");
            ev.printStackTrace();
        }

    }

    //Series of if statements a ran on this method to prevent user from entering unwanted values
    public void registerButtonOnAction(ActionEvent e) throws Exception {




        if (usernameTextField.getText().isBlank() == false && passwordField.getText().isBlank() == false && tokenTextField.getText().isBlank() == false) {

            validateRegistration(e); //method is called to validate the registration




        }

        else if (passwordField.getText().isBlank() == false && tokenTextField.getText().isBlank() == false && usernameTextField.getText().isBlank()) {

            warningLabel.setText("Enter Username!");
            warningLabel.setStyle("-fx-text-fill: red;" + "-fx-font-weight: bold;" + "-fx-font-size: 14");

        }

        else if (usernameTextField.getText().isBlank() == false && tokenTextField.getText().isBlank() == false && passwordField.getText().isBlank()) {

            warningLabel.setText("Enter Password!");
            warningLabel.setStyle("-fx-text-fill: red;" + "-fx-font-weight: bold;" + "-fx-font-size: 14");

        }

        else if (usernameTextField.getText().isBlank() == true && passwordField.getText().isBlank() == true && tokenTextField.getText().isBlank() == true) {

            warningLabel.setText("Fill In Everything!");
            warningLabel.setStyle("-fx-text-fill: red;" + "-fx-font-weight: bold;" + "-fx-font-size: 14");

        }

        else {

            warningLabel.setText("Error Occurred");
            warningLabel.setStyle("-fx-text-fill: red;" + "-fx-font-weight: bold;" + "-fx-font-size: 14");

        }
    }


}
