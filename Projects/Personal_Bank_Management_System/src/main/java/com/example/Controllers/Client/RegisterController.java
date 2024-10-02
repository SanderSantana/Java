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


import java.awt.geom.RectangularShape;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

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


    public void registerButtonOnAction() throws SQLException {

        int isPasswordValid = validatePassword();

        boolean isRegistered = isUserRegistered();

        if (isPasswordValid == 0) {

            failureMessage();
            return;

        }

        if(isRegistered){

            failureMessage();
            return;
        }
        Connection connection = getConnection();

        try {

            String registerUser = "UPDATE UserProfile SET Username = '" + usernameField.getText() + "', Password = '"+ passwordField.getText() +"' WHERE Token = '"+ tokenField.getText() +"';";

            Statement statement = connection.createStatement();

            statement.execute(registerUser);

            successMessage();
            clearFields();

        }catch (Exception e){

            e.printStackTrace();
            failureMessage();

        }
    }

    public int validatePassword() {

        if(passwordField.getText().equals(reEnterPasswordField.getText())) {

            return 1;

        }
        else return 0;

    }

    public boolean isUserRegistered() throws SQLException {

        Connection connection = getConnection();

        String checkUsername = "SELECT Username FROM UserProfile WHERE Token = '"+ tokenField.getText() +"';";

        Statement statement = connection.createStatement();

        ResultSet resultSet = statement.executeQuery(checkUsername);

        String username = "";
        while (resultSet.next()){

            username = resultSet.getString(1);

        }

        if (username == null){

            return false;

        }
        else return true;

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
        reEnterPasswordField.clear();
        tokenField.clear();

    }


}
