package com.example.Controllers.Client;

import com.example.DatabaseConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class WelcomeController extends DatabaseConnection {

    @FXML
    private Stage stage;
    @FXML
    private Scene scene;
    @FXML
    private Label name, rightCardBalance, leftCardBalance, rightCardAccountNumber, leftCardAccountNumber, totalBalance, leftCardLabel, rightCardLabel;

    private String loggedInUsername;

    public void setLoggedInUsername(String username) {

        this.loggedInUsername = username;

    }


    public void logoutButtonOnAction(ActionEvent e) throws IOException {

       ClientLoginController clientLoginController = new ClientLoginController();
       clientLoginController.clientLoginPage(e);

    }

    public void sendMoneyButtonOnAction(ActionEvent e) throws IOException, SQLException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Inventory/FXML/User/transfer.fxml"));
        Parent root = loader.load();

        TransferController transferController = loader.getController();
        transferController.setLoggedInUsername(UserSession.getInstance().getUsername());
        transferController.loadCardData();

        stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

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

    public void transactionButtonOnAction(ActionEvent e) throws IOException, SQLException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Inventory/FXML/User/transactions.fxml"));
        Parent root = loader.load();

        TransactionController transactionController = loader.getController();
        transactionController.setLoggedInUsername(UserSession.getInstance().getUsername());

        stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }


    public void dashboardData() throws SQLException {

        Connection connection = getConnection();

        String fetchDetails = "SELECT FirstName, AccountNumber, Balance, AccountType FROM UserProfile INNER JOIN Account ON UserProfile.UserID = Account.UserID WHERE Username = '"+ loggedInUsername +"'";

        try {

            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(fetchDetails);

            int count = 0;
            String accountBalance1 = "";
            String accountBalance2 = "";
            while(resultSet.next()){

                count++;
                name.setText(resultSet.getString(1));
                if(count == 1) {
                    leftCardAccountNumber.setText(resultSet.getString(2));
                    leftCardBalance.setText(resultSet.getString(3));
                    accountBalance1 = resultSet.getString(3);
                    leftCardLabel.setText(resultSet.getString(4));
                }
                else if (count == 2) {
                    rightCardAccountNumber.setText(resultSet.getString(2));
                    rightCardBalance.setText(resultSet.getString(3));
                    accountBalance2 = resultSet.getString(3);
                    rightCardLabel.setText(resultSet.getString(4));

                }



            }

            if(accountBalance1 == null || accountBalance1.isEmpty()){
                accountBalance1 = String.valueOf(0);
            }
            if (accountBalance2 == null || accountBalance2.isEmpty()) {
                accountBalance2 = String.valueOf(0);
            }

            double total = Double.parseDouble(accountBalance1) + Double.parseDouble(accountBalance2);
            totalBalance.setText(String.valueOf(total));

        }catch (Exception ex){

            ex.printStackTrace();

        }

    }

}
