package com.example.Controllers.Admin;

import com.example.DatabaseConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;


import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class AdminLoginController {

    @FXML
    private Scene scene;
    @FXML
    private Stage stage;
    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;

    public void AdminLoginPage(ActionEvent e) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("/Inventory/FXML/Admin/AdminLogin.fxml"));
        stage = (Stage)((Node)e.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 300, 400);
        stage.setScene(scene);
        stage.show();

    }

    public void loginButtonOnAction(ActionEvent e) throws IOException {

        validateLogin(e);


    }
    public void validateLogin(ActionEvent e){

        DatabaseConnection connection = new DatabaseConnection();

        Connection connectionDb = connection.getConnection();

        CreateUsersController createUsersController = new CreateUsersController();


        String verifyLogin = "SELECT COUNT(1) FROM Admin WHERE Username = '" + usernameField.getText() + "' AND Password = '" + passwordField.getText() + "'";

        try {

            Statement statement = connectionDb.createStatement(); //we need a statement object to execute the query.
            ResultSet queryResult = statement.executeQuery(verifyLogin); //we need a ResultSet object to hold the result of our query.

            //The while statement is going to iterate over all rows
            while(queryResult.next())  {

                if (queryResult.getInt(1) == 1) { //checks if the result in the first column is 1

                    createUsersController.CreateUsersPage(e);

                }
                else {

//                    loginWarningLabel.setText("Invalid Login");
//                    loginWarningLabel.setStyle("-fx-text-fill: red;" + "-fx-font-weight: bold;" + "-fx-font-size: 14");

                }

            }

        }

        catch (Exception a) {

            a.printStackTrace();

        }

    }

}
