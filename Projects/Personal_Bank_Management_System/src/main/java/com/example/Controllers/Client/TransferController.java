package com.example.Controllers.Client;

import com.example.DatabaseConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class TransferController extends DatabaseConnection implements Initializable {

    @FXML
    private Stage stage;
    @FXML
    private Scene scene;
    @FXML
    private Label topCardBalance, bottomCardBalance, topCardAccountNumber, bottomCardAccountNumber, topCardLabel, bottomCardLabel, warningLabel;
    @FXML
    private ChoiceBox<String> accountSelector;
    @FXML
    private TextField beneficiary, myReference, theirReference, email, phoneNumber, accountNumber, amount;
    private String[] accounts = {"Credit", "Savings"};
    private String loggedInUsername;

    public void setLoggedInUsername(String username){

        this.loggedInUsername = username;

    }
   public void transferPage(ActionEvent e) throws IOException {

       Parent root = FXMLLoader.load(getClass().getResource("/Inventory/FXML/User/transfer.fxml"));
       stage = (Stage)((Node)e.getSource()).getScene().getWindow();
       Scene scene = new Scene(root);
       stage.setScene(scene);
       stage.show();
   }

    public void logoutButtonOnAction(ActionEvent e) throws IOException {

        ClientLoginController clientLoginController = new ClientLoginController();
        clientLoginController.clientLoginPage(e);

    }

    public void dashboardButtonOnAction(ActionEvent e) throws IOException, SQLException {

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Inventory/FXML/User/Welcome.fxml"));
        Parent root = fxmlLoader.load();

        WelcomeController welcomeController = fxmlLoader.getController();
        welcomeController.setLoggedInUsername(UserSession.getInstance().getUsername());
        welcomeController.dashboardData();

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

    public void loadCardData() throws SQLException {

        Connection connection = getConnection();

        String fetchDetails = "SELECT FirstName, AccountNumber, Balance, AccountType FROM UserProfile INNER JOIN Account ON UserProfile.UserID = Account.UserID WHERE Username = '"+ loggedInUsername +"'";

        try {

            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(fetchDetails);

            int count = 0;
            while(resultSet.next()){

                count++;
                if(count == 1) {
                    topCardAccountNumber.setText(resultSet.getString(2));
                    topCardBalance.setText(resultSet.getString(3));
                    topCardLabel.setText(resultSet.getString(4));
                }
                else if (count == 2) {
                    bottomCardAccountNumber.setText(resultSet.getString(2));
                    bottomCardBalance.setText(resultSet.getString(3));
                    bottomCardLabel.setText(resultSet.getString(4));

                }



            }

        }catch (Exception ex){

            ex.printStackTrace();

        }

    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        accountSelector.getItems().addAll(accounts);
        accountSelector.setOnAction(this::getAccountSelected);


    }

    private String selectedAccount;
    public void getAccountSelected(ActionEvent e){

        selectedAccount = accountSelector.getValue();

    }

    public void payButtonOnAction() throws SQLException {

        if (!isAccountValid()){

            failureMessage();
            return;

        }

        if(!isMoneySufficient()){

            failureMessage();
            return;

        }
        if(!isAmountAboveMargin()){

            failureMessage();
            return;

        }

        Connection connection = getConnection();

        Statement statement = connection.createStatement();

        int userID = fetchUserId();
        int accountID = fetchAccountID();

        String validateTransaction = "INSERT INTO Transaction (UserID, AccountID, Beneficiary, MyReference, TheirReference, Email, PhoneNumber, Amount, AccountNumber, Status)\n" +
                "VALUES ("+ userID +", "+ accountID +", '"+ beneficiary.getText() +"', '"+ myReference.getText() +"', '"+ theirReference.getText() +"', '"+ email.getText() +"', '"+ phoneNumber.getText() +"', '"+ amount.getText() +"', '"+ accountNumber.getText() +"', 'Completed');";

        try {

            updateUserBalance();
            updateBeneficiaryBalance();
            statement.execute(validateTransaction);
            successMessage();
            loadCardData();

        }catch (Exception ex){

            ex.printStackTrace();

        }


    }

    public int fetchUserId() throws SQLException {

        Connection connection = getConnection();

        String userId = "SELECT UserID FROM UserProfile WHERE Username = '"+ loggedInUsername +"';";

        Statement statement = connection.createStatement();

        ResultSet resultSet = statement.executeQuery(userId);

        String fetchedUserId = "";
        while (resultSet.next()){

            fetchedUserId = resultSet.getString(1);

        }

        return Integer.parseInt(fetchedUserId);

    }

    public int fetchAccountID() throws SQLException {

        int userID = fetchUserId();
        Connection connection = getConnection();

        String userId = "SELECT AccountID FROM Account WHERE UserID = '"+ userID +"' AND AccountType = '"+ selectedAccount +"';";

        Statement statement = connection.createStatement();

        ResultSet resultSet = statement.executeQuery(userId);

        int accountID = 0;
        while (resultSet.next()){

            accountID = resultSet.getInt(1);

        }

        return accountID;

    }

    public boolean isAccountValid() throws SQLException {

        Connection connection = getConnection();
        Statement statement = connection.createStatement();

        String isAccountValid = "SELECT COUNT(1) FROM Account WHERE AccountNumber = '"+ accountNumber.getText() +"';";

        ResultSet resultSet = statement.executeQuery(isAccountValid);

        while(resultSet.next()){

            if(resultSet.getInt(1) == 1){

                return true;

            }

        }

        return false;

    }

    public void updateUserBalance() throws SQLException {

        if(isMoneySufficient() == false){

            failureMessage();
            return;

        }
        if(isAmountAboveMargin() == false){

            failureMessage();
            return;

        }

        System.out.print("It was called");
        double updatedAmount = Double.parseDouble(userPaymentCalculation());

        Connection connection = getConnection();
        Statement statement = connection.createStatement();

        try {
            String updateBalance = "UPDATE Account SET Balance = " + updatedAmount + " WHERE AccountType = '" + selectedAccount + "' AND UserID = (SELECT UserID FROM UserProfile WHERE Username = '" + loggedInUsername + "');";


            statement.execute(updateBalance);

        }catch (Exception e){

            e.printStackTrace();
            warningLabel.setVisible(true);


        }



    }

    public String userPaymentCalculation() throws SQLException {

        Connection connection = getConnection();

        double amountOnDatabase = 0;
        try {

            String fetchBalance = "SELECT Balance FROM Account WHERE AccountType = '" + selectedAccount + "' AND UserID = (SELECT UserID FROM UserProfile WHERE Username = '" + loggedInUsername + "');";


            Statement statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery(fetchBalance);

            while (resultSet.next()) {

                amountOnDatabase = resultSet.getInt(1);

            }
        } catch (Exception e) {

            e.printStackTrace();
            warningLabel.setVisible(true);

        }

        double updatedAmount = amountOnDatabase - Double.parseDouble(amount.getText());

        return String.valueOf(updatedAmount);

    }

    public void updateBeneficiaryBalance() throws SQLException {

        if(isMoneySufficient() == false){

            failureMessage();
            return;

        }
        if(isAmountAboveMargin() == false){

            failureMessage();
            return;

        }

        System.out.print("It was calleddd");
        double updatedAmount = Double.parseDouble(beneficiaryPaymentCalculation());

        Connection connection = getConnection();
        Statement statement = connection.createStatement();

        try {
            String updateBalance = "UPDATE Account SET Balance = '" + updatedAmount + "' WHERE AccountNumber = '" + accountNumber.getText() + "'";

            statement.execute(updateBalance);

        }catch (Exception e){

            e.printStackTrace();
            warningLabel.setVisible(true);


        }




    }

    public String beneficiaryPaymentCalculation() throws SQLException {

        Connection connection = getConnection();

        double amountOnDatabase = 0;
        try {

            String fetchBalance = "SELECT Balance FROM Account WHERE AccountNumber = '"+ accountNumber.getText() +"'";

            Statement statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery(fetchBalance);

            while (resultSet.next()) {

                amountOnDatabase = resultSet.getInt(1);

            }
        } catch (Exception e) {

            e.printStackTrace();
            warningLabel.setVisible(true);

        }

        double updatedAmount = amountOnDatabase + Double.parseDouble(amount.getText());

        return String.valueOf(updatedAmount);

    }


    public boolean isAmountAboveMargin(){

        double number = Double.parseDouble(amount.getText());

        return number >= 10;

    }

    public boolean isMoneySufficient() throws SQLException {

        Connection connection = getConnection();

        String fetchBalance = "SELECT Balance FROM UserProfile INNER JOIN Account ON UserProfile.UserID = Account.UserID WHERE AccountType = '"+ selectedAccount +"' AND Username = '"+ loggedInUsername +"';";

        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(fetchBalance);

        int balance = 0;
        int requestedAmount = Integer.parseInt(amount.getText());

        if (resultSet.next()) {
            balance = resultSet.getInt(1);  // Get balance from the result
        }

        return balance >= requestedAmount;
    }

    public void resetButtonOnAction(ActionEvent e){

        accountNumber.clear();
        beneficiary.clear();
        myReference.clear();
        email.clear();
        phoneNumber.clear();
        theirReference.clear();
        amount.clear();

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


}
