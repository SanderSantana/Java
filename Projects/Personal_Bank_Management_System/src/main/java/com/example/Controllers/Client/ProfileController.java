package com.example.Controllers.Client;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class ProfileController extends ClientLoginController {

    @FXML
    private Stage stage;
    @FXML
    private Scene scene;

    public void setLoggedInUsername(String username) {
        this.loggedInUsername = username;
    }

    public void logoutButtonOnAction(ActionEvent e) throws IOException {

        ClientLoginController clientLoginController = new ClientLoginController();
        clientLoginController.clientLoginPage(e);

    }

    public void dashboardButtonOnAction(ActionEvent e) throws IOException{

        WelcomeController welcomeController = new WelcomeController(loggedinUsername);
        welcomeController.welcomePage(e);

    }

    public void sendMoneyButtonOnAction(ActionEvent e) throws IOException {

        TransferController transferController = new TransferController();
        transferController.transferPage(e);

    }

    @FXML
    private TextField username, firstname, lastname, email, phoneNumber;
    @FXML
    private PasswordField currentPassword, newPassword, reEnterPassword;
    @FXML
    private Label warningLabel;

    private String loggedInUsername;

    public ProfileController() {
        this.loggedInUsername = UserSession.getInstance().getUsername();
    }


    public void userDetails(){


        Connection connection = getConnection();

        String userDetails = "SELECT FirstName, LastName, Email, PhoneNumber FROM UserProfile WHERE Username = '"+ loggedInUsername +"';";

        try {

            Statement statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery(userDetails);

            while (resultSet.next()) {

                username.setText(loggedInUsername);
                firstname.setText(resultSet.getString(1));
                lastname.setText(resultSet.getString(2));
                email.setText(resultSet.getString(3));
                phoneNumber.setText(resultSet.getString(4));


            }

        }catch (Exception ex) {

            ex.printStackTrace();
            failureMessage();

        }

    }

    public void updatePassword(){

        boolean isPasswordCorrect = isPasswordCorrect();
        int validateNewPassword = validatePassword();

        System.out.println(isPasswordCorrect + " " + validateNewPassword);

        if (!isPasswordCorrect || validateNewPassword != 1){

            failureMessage();
            return;

        }

        try {

            Connection connection = getConnection();

            Statement statement = connection.createStatement();

            String updatePassword = "UPDATE UserProfile SET Password = '"+ newPassword.getText() +"' WHERE Username = '"+ loggedInUsername +"';";

            statement.execute(updatePassword);

            successMessage();

        }catch (Exception ex){

            ex.printStackTrace();
            failureMessage();

        }



    }

    public boolean isPasswordCorrect(){



        Connection connection = getConnection();

        String password = "SELECT COUNT(1) FROM UserProfile WHERE Password = '"+ currentPassword.getText() +"' AND Username = '"+ loggedInUsername +"';";
        int num = 0;
        try{

            Statement statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery(password);


            while(resultSet.next()){

                num = resultSet.getInt(1);

            }


        }catch (Exception ex){

            ex.printStackTrace();
            failureMessage();
        }

        if (num == 1){

            return true;

        }else return false;


    }

    public int validatePassword() {

        if(newPassword.getText().equals(reEnterPassword.getText())) {

            return 1;

        }
        else { failureMessage(); return 0;}

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

        currentPassword.clear();
        newPassword.clear();
        reEnterPassword.clear();

    }






}
