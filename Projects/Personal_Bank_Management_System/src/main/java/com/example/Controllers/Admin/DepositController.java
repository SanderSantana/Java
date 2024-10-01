package com.example.Controllers.Admin;

import com.example.DatabaseConnection;
import com.sun.javafx.scene.control.behavior.ButtonBehavior;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.awt.geom.RectangularShape;
import java.io.IOException;
import java.lang.module.Configuration;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DepositController extends DatabaseConnection {

    @FXML
    private Stage stage;
    @FXML
    private Scene scene;
    @FXML
    private TextField firstName, lastName, availableBalance, amount, accountNumber;
    @FXML
    private ToggleGroup accountGroup;
    @FXML
    private Button searchButton, depositButton;
    @FXML
    private Label warning;



    public void DepositPage(ActionEvent e) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("/Inventory/FXML/Admin/Deposit.fxml"));
        stage = (Stage)((Node)e.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }

    public void createButtonOnAction(ActionEvent e) throws IOException, SQLException {

        CreateUsersController createUsersController = new CreateUsersController();
        createUsersController.CreateUsersPage(e);

    }

    public void editButtonOnAction(ActionEvent e) throws IOException {

        EditUserController editUserController = new EditUserController();
        editUserController.EditUserPage(e);

    }

    public void logoutButtonOnAction(ActionEvent e) throws IOException {

        AdminLoginController adminLoginController = new AdminLoginController();
        adminLoginController.AdminLoginPage(e);

    }

    public void enableFields() {

        firstName.setDisable(false);
        lastName.setDisable(false);
        availableBalance.setDisable(false);
        amount.setDisable(false);
        depositButton.setDisable(false);
        warning.setVisible(false);

    }
    public void disableFields() {

        firstName.setDisable(true);
        lastName.setDisable(true);
        availableBalance.setDisable(true);
        amount.setDisable(true);
        depositButton.setDisable(true);

        firstName.clear();
        lastName.clear();
        availableBalance.clear();
        amount.clear();

    }

    public void successMessage(){

        warning.setVisible(true);
        warning.setText("All set!");
        warning.setStyle("-fx-text-fill: #00ef00");

    }

    public void searchButtonOnAction() throws SQLException {

        enableFields();

        Connection connection = getConnection();
        Statement statement = connection.createStatement();

        ToggleButton selectedAccount = (ToggleButton) accountGroup.getSelectedToggle();

        try {

            String fetchDetails = "SELECT FirstName, LastName, Balance FROM Account INNER JOIN UserProfile ON Account.UserID = UserProfile.UserID WHERE AccountNumber = '" + accountNumber.getText() + "' AND AccountType = '" + selectedAccount.getText() + "';";

            ResultSet resultSet = statement.executeQuery(fetchDetails);

            while (resultSet.next()) {

                firstName.setText(resultSet.getString(1));
                lastName.setText(resultSet.getString(2));
                availableBalance.setText(String.valueOf(resultSet.getInt(3)));

            }

        }catch (Exception e) {

            e.printStackTrace();
            warning.setVisible(true);

        }
    }

    public String paymentCalculation() throws SQLException {

        Connection connection = getConnection();

        ToggleButton selectedAccount = (ToggleButton) accountGroup.getSelectedToggle();

        int amountOnDatabase = 0;
        try {

            String fetchDetails = "SELECT Balance FROM Account INNER JOIN UserProfile ON Account.UserID = UserProfile.UserID WHERE AccountNumber = '" + accountNumber.getText() + "' AND AccountType = '" + selectedAccount.getText() + "';";

            Statement statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery(fetchDetails);

            while (resultSet.next()) {

                amountOnDatabase = resultSet.getInt(1);

            }
        } catch (SQLException e) {

            e.printStackTrace();
            warning.setVisible(true);

        }

        int updatedAmount = amountOnDatabase + Integer.parseInt(amount.getText());

        return String.valueOf(updatedAmount);

    }

    public void depositButtonOnAction() throws SQLException {

        Connection connection = getConnection();

        ToggleButton selectedAccount = (ToggleButton) accountGroup.getSelectedToggle();

        Statement statement = connection.createStatement();

        String newBalance = paymentCalculation();

        try {
            String updateBalance = "UPDATE Account SET Balance = '" + newBalance + "' WHERE AccountNumber = '" + accountNumber.getText() + "' AND AccountType = '" + selectedAccount.getText() + "';";

            statement.execute(updateBalance);

        }catch (SQLException e){

            e.printStackTrace();
            warning.setVisible(true);


        }

        successMessage();
        disableFields();
    }
}
