package com.example.Controllers;

import com.example.Controllers.Admin.WelcomeController;
import com.example.Controllers.Admin.adminRegisterPageController;
import com.example.DatabaseConnection;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Border;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import java.io.IOException;
import java.sql.*;
import java.util.Optional;

import static javafx.fxml.FXMLLoader.load;


public class LoginController {

    @FXML
    private Button cancelButton;
    @FXML
    private Label loginWarningLabel;
    @FXML
    private TextField usernameTextField;
    @FXML
    private PasswordField passwordPasswordField;


    private Scene scene ;
    private Stage stage;


    //This method runs a series of if statement to ensure that the user doesn't enter unwanted information.
    public void loginButtonOnAction(ActionEvent e) throws Exception {

        


        if (usernameTextField.getText().isBlank() == false && passwordPasswordField.getText().isBlank() == false) {

            validateLogin(e); //method call

            


        }

        else if (passwordPasswordField.getText().isBlank() == false && usernameTextField.getText().isBlank()) {

            loginWarningLabel.setText("Enter Username!");
            loginWarningLabel.setStyle("-fx-text-fill: red;" + "-fx-font-weight: bold;" + "-fx-font-size: 14");

        }

        else if (usernameTextField.getText().isBlank() == false && passwordPasswordField.getText().isBlank()) {

            loginWarningLabel.setText("Enter Password!");
            loginWarningLabel.setStyle("-fx-text-fill: red;" + "-fx-font-weight: bold;" + "-fx-font-size: 14");

        }

        else if (usernameTextField.getText().isBlank() == true && passwordPasswordField.getText().isBlank() == true) {

            loginWarningLabel.setText("Enter Username And Password!");
            loginWarningLabel.setStyle("-fx-text-fill: red;" + "-fx-font-weight: bold;" + "-fx-font-size: 14");

        }

        else {

            loginWarningLabel.setText("Invalid Login");
            loginWarningLabel.setStyle("-fx-text-fill: red;" + "-fx-font-weight: bold;" + "-fx-font-size: 14");

        }
    }

    public void cancelButtonOnAction(ActionEvent e) {

        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();


    }



    //This method checks if credentials used exist in the database, if yes, it lets the user through
    public void validateLogin(ActionEvent event) {

        WelcomeController welcome = new WelcomeController();

        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectionDB = connectNow.getConnection(); //Here we are establishing connection with the database from the getConnection method and assigning everything into 'connectionDB'

        String verifyLogin = "SELECT COUNT(1) FROM UserLogin WHERE Username = '" + usernameTextField.getText() + "' AND Password = '" + passwordPasswordField.getText() + "'"; //SQL query used to check if user exists or not.

        try {

            Statement statement = connectionDB.createStatement(); //we need a statement object to execute the query.
            ResultSet queryResult = statement.executeQuery(verifyLogin); //we need a ResultSet object to hold the result of our query.

            //The while statement is going to iterate over all rows
            while(queryResult.next())  {

                if (queryResult.getInt(1) == 1) { //checks if the result in the first column is 1

                    welcome.WelcomePage(event); //Calling method that has our new scene.


                }
                else {

                    loginWarningLabel.setText("Invalid Login");
                    loginWarningLabel.setStyle("-fx-text-fill: red;" + "-fx-font-weight: bold;" + "-fx-font-size: 14");

                }

            }

        }

        catch (Exception e) {

            e.printStackTrace();

        }

    }

    //The method contains the login page
    public void LogInPage(ActionEvent e) throws Exception {


        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();


        Parent root = FXMLLoader.load(getClass().getResource("/Inventory/FXML/Login.fxml"));


        Scene scene = new Scene(root, 578, 400);
        stage.setScene(scene);

    }

    //This method allows users to come back into the login page
    public void backToLogInPage(ActionEvent e) throws Exception {



        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.getDialogPane().getStylesheets().add(getClass().getResource("/Inventory/Styles/Admin/dialog.css").toExternalForm());
        alert.setTitle("LogOut");
        alert.setContentText("Do you wish to log out?");

        Optional<ButtonType> result = alert.showAndWait();

        if (result.get() == ButtonType.OK) {

            LogInPage(e);

        }


    }

    //This method is calls a method that has the registration scene. Meaning it loads the registration page.
    public void registerButtonOnAction(ActionEvent e) throws Exception {

        adminRegisterPageController adminRegisterPageController = new adminRegisterPageController();
        adminRegisterPageController.register(e);

    }





}