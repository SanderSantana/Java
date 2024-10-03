package com.example.Controllers.Admin;

import com.example.DatabaseConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;


import javax.swing.plaf.nimbus.State;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Random;

public class CreateUsersController {

    @FXML
    private Stage stage;

    public void CreateUsersPage(ActionEvent e) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("/Inventory/FXML/Admin/CreateUsers.fxml"));
        stage = (Stage)((Node)e.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();




    }

    public void logoutButtonOnAction(ActionEvent e) throws IOException {

        AdminLoginController adminLoginController = new AdminLoginController();
        adminLoginController.AdminLoginPage(e);

    }

    public void editButtonOnAction(ActionEvent e) throws IOException {

        EditUserController editUserController = new EditUserController();
        editUserController.EditUserPage(e);

    }

    public void depositButtonOnAction(ActionEvent e) throws IOException {

        DepositController depositController = new DepositController();
        depositController.DepositPage(e);

    }

    @FXML
    private TextField accountNumber, firstName, lastName, email, phoneNumber, token;
    @FXML
    private RadioButton savings, credit, male, female;
    @FXML
    private DatePicker dateOfBirth;
    @FXML
    private ToggleGroup gender;
    @FXML
    private Label warning;

    public String randomToken() {

        Random random = new Random();
        int randomNumber = 10000 + random .nextInt(90000);

        token.setText(String.valueOf(randomNumber));

        return String.valueOf(randomNumber);

    }


    public String fetchAccountNumber() throws SQLException {

        DatabaseConnection databaseConnection = new DatabaseConnection();
        Connection connection = databaseConnection.getConnection();

        String getLastAccountNumber = "SELECT AccountNumber FROM Account ORDER BY CreatedAt DESC, AccountNumber DESC LIMIT  1;";

        Statement statement = connection.createStatement();
        ResultSet queryResult = statement.executeQuery(getLastAccountNumber);

        int newAccountNumber = 0;
        if (queryResult.next()) {

            newAccountNumber = queryResult.getInt(1) + 1;
            accountNumber.setText(String.valueOf(newAccountNumber));

        }

        return String.valueOf(newAccountNumber);

    };

    public int fetchUserID() throws SQLException {

        DatabaseConnection databaseConnection = new DatabaseConnection();
        Connection connection = databaseConnection.getConnection();

            String userIdQuery = "SELECT UserID FROM UserProfile ORDER BY CreatedAt DESC, UserID DESC LIMIT 1;";

        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(userIdQuery);

        int userId = 0;
        if(resultSet.next()) {

            userId = resultSet.getInt(1) + 1;

        }

        return userId;

    }

    public void resetButtonOnAction(ActionEvent e){

        accountNumber.clear();
        firstName.clear();
        lastName.clear();
        email.clear();
        phoneNumber.clear();
        token.clear();
        savings.setSelected(false);
        credit.setSelected(false);
        gender.getSelectedToggle().setSelected(false);
        dateOfBirth.getEditor().clear();


    }

    public void successMessage(){

        warning.setVisible(true);
        warning.setText("All set!");
        warning.setStyle("-fx-text-fill: #00ef00");

    }

    public void createUser(ActionEvent e) {

        DatabaseConnection databaseConnection = new DatabaseConnection();

        try {
            Connection connection = databaseConnection.getConnection();
            Statement statement = connection.createStatement();

            int userId = fetchUserID();
            String accountNumber = fetchAccountNumber();
            String token = randomToken();
            ToggleButton selectedGender = (ToggleButton) gender.getSelectedToggle();


            if (selectedGender == null) {

                warning.setVisible(true);
                return;
            }

            String userProfile = " INSERT INTO UserProfile  (FirstName, LastName, DateOfBirth, Gender, PhoneNumber, Email, Password, Token) VALUES \n" +
                    "('"+ firstName.getText()+"', '"+ lastName.getText() +"', '"+ dateOfBirth.getValue() +"', '"+ selectedGender.getText() +"', '"+ phoneNumber.getText() +"', '"+ email.getText() +"', '123456', '"+ token +"');";

            String createUserAccountCredit = "INSERT INTO Account (UserID, AccountType, AccountNumber) VALUES \n" +
                    "('"+ userId +"', 'Credit', "+ accountNumber +");";

            String createUserAccountSavings = "INSERT INTO Account (UserID, AccountType, AccountNumber) VALUES \n" +
                    "('"+ userId +"', 'Savings',"+ accountNumber +");";



            if (!savings.isSelected() && credit.isSelected()) {

                statement.execute(userProfile);
                statement.execute(createUserAccountCredit);
                successMessage();


            } else if (savings.isSelected() && !credit.isSelected()) {


                statement.execute(userProfile);
                statement.execute(createUserAccountSavings);
                successMessage();
            }
            else if (savings.isSelected() && credit.isSelected()) {

                statement.execute(userProfile);
                statement.execute(createUserAccountCredit);

                int NewAccountNumber = Integer.parseInt(fetchAccountNumber());

                String createUserAccountSavingsNew = "INSERT INTO Account (UserID, AccountType, AccountNumber) VALUES \n" +
                        "('"+ userId +"', 'Savings',"+ NewAccountNumber +");";

                statement.execute(createUserAccountSavingsNew);

                successMessage();

            }

        } catch (Exception ex) {

            ex.printStackTrace();
            warning.setVisible(true);

        }
    }






}


