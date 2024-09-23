package com.example.Controllers.Admin;

import com.example.Controllers.LoginController;
import com.example.DatabaseConnection;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.Node;

import javafx.event.ActionEvent;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;

public class paymentController {

    @FXML
    private Stage stage;
    @FXML
    private Scene scene;

    //Contains payment scene
    public void payment(ActionEvent e) throws Exception {

        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("Inventory/FXML/Admin/payment.fxml"));
        stage = (Stage) ((Node)e.getSource()).getScene().getWindow();
//        stage.initModality(Modality.APPLICATION_MODAL);
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();


    }

    //loads New member Page
    public void newMemberButtonOnAction(ActionEvent e) throws Exception {

        NewMemberController newMember = new NewMemberController();
        newMember.newMember(e);

    }

    //loads update/delete Page
    public void editMemberButtonOnAction(ActionEvent e) throws Exception {

        updateDeleteMemberController edit = new updateDeleteMemberController();
        edit.updateDeleteMember(e);

    }

    //loads list of members page
    public void membersButtonOnAction(ActionEvent e) throws Exception {

        membersController membersController = new membersController();
        membersController.members(e);

    }

    @FXML
    private Button exitButton;

    //exits application when called
    public void exitButtonOnAction(ActionEvent e) {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.getDialogPane().getStylesheets().add(getClass().getResource("/Inventory/Styles/Admin/dialog.css").toExternalForm());
        alert.setTitle("LogOut");
        alert.setContentText("Do you wish to exit the application?");

        Optional<ButtonType> result = alert.showAndWait();

        if (result.get() == ButtonType.OK) {

            Stage stage = (Stage) exitButton.getScene().getWindow();

            stage.close();

        }

    }

    //loads welcome or dashboard page
    public void logoButtonOnAction(ActionEvent e) throws Exception {

        WelcomeController welcome = new WelcomeController();
        welcome.WelcomePage(e);

    }

    //loads the logIn page
    public void logOutButtonOnAction(ActionEvent e) throws Exception {

        LoginController loginController = new LoginController();

        loginController.backToLogInPage(e);

    }

    @FXML
    private TextField idTextField, fNameTextField, lNameTextField, emailTextField, gymSubscriptionTextField, amountTextField, currencyTextField;

    @FXML
    private Label toPayLabel, currencyLabel, amountLabel, warningLabel;

    @FXML
    private Button payButton;


    //This method disables all fields in the payment page
    public void disable(ActionEvent e) {

        fNameTextField.setDisable(true);
        lNameTextField.setDisable(true);
        emailTextField.setDisable(true);
        gymSubscriptionTextField.setDisable(true);
        currencyLabel.setDisable(true);
        amountTextField.setDisable(true);
        payButton.setDisable(true);
        toPayLabel.setDisable(true);
        currencyLabel.setDisable(true);
        currencyTextField.setDisable(true);
        amountLabel.setDisable(true);

    }

    //The fields are initially disabled, this method enables all fields in the payment page
    public void enable(ActionEvent e) {

        fNameTextField.setDisable(false);
        lNameTextField.setDisable(false);
        emailTextField.setDisable(false);
        gymSubscriptionTextField.setDisable(false);
        currencyLabel.setDisable(false);
        amountTextField.setDisable(false);
        payButton.setDisable(false);
        toPayLabel.setDisable(false);
        currencyLabel.setDisable(false);
        currencyTextField.setDisable(false);
        amountLabel.setDisable(false);


    }

    //clears all fields
    public void clear(ActionEvent e) {

        fNameTextField.clear();
        lNameTextField.clear();
        emailTextField.clear();
        gymSubscriptionTextField.clear();
        amountTextField.clear();
        amountLabel.setText("000"); //sets text of the amount label to 000

    }

    //this method prevents users from editing information that shouldn't be edited
    public void editable(ActionEvent e) {

        fNameTextField.setEditable(false);
        lNameTextField.setEditable(false);
        emailTextField.setEditable(false);
        gymSubscriptionTextField.setEditable(false);
        currencyTextField.setEditable(false);


    }

    //This method fetches the details of the member and his/her outstanding based on the member id
    public void findPaymentStatus(ActionEvent e) throws SQLException {

        try {

            //Creates the database connection
            DatabaseConnection databaseConnection = new DatabaseConnection();
            Connection connection = databaseConnection.getConnection();

            String id = idTextField.getText();

            //query to get member's details
            String findMember = " SELECT First_Name, Last_Name, Email, Subscription_Type, Outstanding FROM GymMembers \n" +
                    "INNER JOIN Subscription ON Subscription.Subscription_ID = GymMembers.Subscription_ID\n" +
                    "INNER JOIN Payment ON Payment.Payment_ID = GymMembers.Payment_ID \n" +
                    "WHERE Member_ID = "+ id +";";

            String verifyId = " SELECT COUNT(1) FROM GymMembers\n" +
                    "WHERE Member_ID = "+ id +";  ";

            //statements objects to run our queries
            Statement statement = connection.createStatement();
            Statement innerStatement = connection.createStatement();

            //ResultSet object to hold the values the queries
            ResultSet queryResult = statement.executeQuery(verifyId);
            ResultSet innerQueryResult = innerStatement.executeQuery(findMember);

            while(queryResult.next()) {

                //check if id exists
                if (queryResult.getInt(1) == 1) {

                    while (innerQueryResult.next()) {

                        enable(e); //enables all fields
                        editable(e); //set fields to be not editable

                        warningLabel.setStyle("-fx-opacity: 0");
                        fNameTextField.setText(innerQueryResult.getString(1));
                        lNameTextField.setText(innerQueryResult.getString(2));
                        emailTextField.setText(innerQueryResult.getString(3));
                        gymSubscriptionTextField.setText(innerQueryResult.getString(4));
                        amountLabel.setText(innerQueryResult.getString(5));
                        amountTextField.clear();


                    }


                } else {

                    warningLabel.setText("Invalid ID");
                    warningLabel.setStyle("-fx-text-fill: red;" + "-fx-font-weight: bold;" + "-fx-font-size: 14");
                    disable(e);
                    clear(e);

                }

            }
        }catch (Exception ev) {

            warningLabel.setText("An error occurred");
            warningLabel.setStyle("-fx-text-fill: red;" + "-fx-font-weight: bold;" + "-fx-font-size: 14");
            disable(e);
            clear(e);

        }
    }

    //Method used to update the outstanding status of member
    public void updatePayment(ActionEvent e) throws SQLException {

        try {
            String id = idTextField.getText();
            String amount = amountTextField.getText();

            //converting the String value into to integer
            double amountInt = Integer.parseInt(amount);

            //This method return the outstanding of the member, and we are storing that value into an int variable
            double outStandingValue = getCurrentOustanding(e);

            //Creating connection with database
            DatabaseConnection databaseConnection = new DatabaseConnection();
            Connection connection = databaseConnection.getConnection();
            Statement statement = connection.createStatement();

            //query to update the outstanding of the member
            //in this query we substract the value (amountInt) in the database with the amount entered by the user(outStandingValue).
            String update = " UPDATE Payment \n" +
                    "SET Outstanding = '" + (outStandingValue - amountInt) + "'\n" +
                    "WHERE Payment_ID = '" + id + "';  ";

            //If statements to prevent unwanted values to be inserted
            if (amountTextField.getText().isEmpty()) {

                warningLabel.setStyle("-fx-text-fill: red;" + "-fx-font-weight: bold;" + "-fx-font-size: 14");
                warningLabel.setText("Please enter an amount");

            } else if (amountTextField.getText().matches(".*[!@#$%^&*()\\[\\]{};':\"<>?/\\\\|_+=].*")) {

                warningLabel.setStyle("-fx-text-fill: red;" + "-fx-font-weight: bold;" + "-fx-font-size: 14");
                warningLabel.setText("Please remove special characters");

            } else if (amountTextField.getText().matches("^[0-9.,-]+$") == false) {

                warningLabel.setStyle("-fx-text-fill: red;" + "-fx-font-weight: bold;" + "-fx-font-size: 14");
                warningLabel.setText("Please remove letters");

            } else {

                warningLabel.setStyle("-fx-text-fill: green;" + "-fx-font-weight: bold;" + "-fx-font-size: 14");
                warningLabel.setText("ALL GOOD!");
                statement.execute(update) ;//executing the query to update
                String newOutStanding = outStandingAfterUpdate(e); //assigning the new outstanding into a new string
                amountLabel.setText(newOutStanding); //Assigning the new value to the amount label to display to the user
                amountTextField.clear();


            }
        }catch (Exception ev) {

            warningLabel.setStyle("-fx-text-fill: red;" + "-fx-font-weight: bold;" + "-fx-font-size: 14");
            warningLabel.setText("An error occurred");
            disable(e);
            clear(e);

        }
    }


    //This method gets the outstanding of the member before it has been updated
    public int getCurrentOustanding(ActionEvent e) throws SQLException {

        String id = idTextField.getText();

        DatabaseConnection databaseConnection = new DatabaseConnection();
        Connection connection = databaseConnection.getConnection();
        Statement statement = connection.createStatement();

        String value = " SELECT Outstanding FROM Payment\n" +
                "WHERE Payment_ID = '"+ id +"';  ";

        ResultSet queryResult = statement.executeQuery(value);

        int outstanding = 0;

        while (queryResult.next()) {

           outstanding = queryResult.getInt("Outstanding");

        }

        return outstanding;

    }

    //This method gets the outstanding of the member, but only after the update
    public String outStandingAfterUpdate(ActionEvent e) throws SQLException {

        int newOutStanding = getCurrentOustanding(e); //assigning the new outstanding

        return Integer.toString(newOutStanding); //converting the value from int to String, so we can display in the amount label


    }





}
