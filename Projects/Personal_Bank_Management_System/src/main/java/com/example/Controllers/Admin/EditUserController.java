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

import javax.imageio.ImageIO;
import javax.swing.plaf.nimbus.State;
import javax.swing.undo.StateEdit;
import javax.xml.crypto.Data;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.Optional;

public class EditUserController extends DatabaseConnection{

    @FXML
    private Stage stage;
    @FXML
    private Scene scene;



    public void EditUserPage(ActionEvent e) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("/Inventory/FXML/Admin/EditUser.fxml"));
        stage = (Stage)((Node)e.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();


    }

    public void CreateButtonOnAction(ActionEvent e) throws IOException, SQLException {

        CreateUsersController createUsersController = new CreateUsersController();
        createUsersController.CreateUsersPage(e);

    }

    public void logoutButtonOnAction(ActionEvent e)throws IOException {

        AdminLoginController adminLoginController = new AdminLoginController();
        adminLoginController.AdminLoginPage(e);

    }

    public void depositButtonOnAction(ActionEvent e) throws IOException {

        DepositController depositController = new DepositController();
        depositController.DepositPage(e);

    }

    public void usersButtonOnAction(ActionEvent e) throws IOException{

        UsersController usersController = new UsersController();
        usersController.UsersPage(e);

    }

    @FXML
    private TextField firstName, lastName, email, phoneNumber, accountNumber, warnings;
    @FXML
    private RadioButton savings, credit, male, female;
    @FXML
    private DatePicker date;
    @FXML
    private ToggleGroup gender;
    @FXML
    private Label warning;

    public void enableFields() {

        firstName.setDisable(false);
        lastName.setDisable(false);
        email.setDisable(false);
        phoneNumber.setDisable(false);
        male.setDisable(false);
        female.setDisable(false);
        date.setDisable(false);
        firstName.setEditable(true);
        lastName.setEditable(true);
        email.setEditable(true);
        phoneNumber.setEditable(true);
        warning.setVisible(false);

    }

    public void resetButtonOnAction(){

        accountNumber.clear();
        firstName.clear();
        lastName.clear();
        email.clear();
        phoneNumber.clear();
        savings.setSelected(false);
        credit.setSelected(false);
        gender.getSelectedToggle().setSelected(false);
        date.getEditor().clear();
        warning.setVisible(false);


    }

    public void successMessage(){

        warning.setVisible(true);
        warning.setText("All set!");
        warning.setStyle("-fx-text-fill: #00ef00");

    }

    public int fetchUserId() throws SQLException {

        DatabaseConnection databaseConnection = new DatabaseConnection();
        Connection connection = databaseConnection.getConnection();

        String fetchUserIdQuery = "SELECT UserID FROM Account WHERE AccountNumber = '"+ accountNumber.getText() +"';";

        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(fetchUserIdQuery);

        int userId = 0;
        if(resultSet.next()) {

            userId = resultSet.getInt(1);

        }

        return userId;

    }

    public void UserDetails() throws SQLException {

        Connection connection = getConnection();
        enableFields();
        int userID = fetchUserId();
        String gender = "";

        String fetchPersonalDetails= "SELECT FirstName, LastName,  Email, PhoneNumber, Gender, DateOfBirth FROM UserProfile WHERE UserID = '"+ userID +"';";

        String fetchAccountDetails = "SELECT AccountType FROM Account WHERE UserID = " + userID;

        Statement personalDetailsStatement = connection.createStatement();

        ResultSet personalDetails = personalDetailsStatement.executeQuery(fetchPersonalDetails);

    try {

        while (personalDetails.next()) {

            firstName.setText(personalDetails.getString(1));
            lastName.setText(personalDetails.getString(2));
            email.setText(personalDetails.getString(3));
            phoneNumber.setText(personalDetails.getString(4));
            gender = personalDetails.getString(5);
            date.setValue(LocalDate.parse(personalDetails.getString(6)));
        }

        if (gender.equals("Female")) {

            female.setSelected(true);

        } else if (gender.equals("Male")) {

            male.setSelected(true);

        }

        personalDetails.close();
        personalDetails.close();

        Statement accountDetailsStatement = connection.createStatement();
        ResultSet accountDetails = accountDetailsStatement.executeQuery(fetchAccountDetails);

        String accountType1 = null;
        String accountType2 = null;

        int count = 0;
        while (accountDetails.next()) {

            count++;
            if (count == 1) {

                accountType1 = accountDetails.getString(1);

            } else if (count == 2) {

                accountType2 = accountDetails.getString(1);

            }

        }

        accountDetails.close();
        accountDetailsStatement.close();

        if (accountType1 != null && accountType2 != null) {
            if (accountType1.equals("Savings") || accountType2.equals("Savings")) {
                savings.setSelected(true);
            }
            if (accountType1.equals("Credit") || accountType2.equals("Credit")) {
                credit.setSelected(true);
            }
        } else {

            if (accountType1.equals("Credit") && accountType2 == null) {

                credit.setSelected(true);
                savings.setSelected(false);

            } else if (accountType1.equals("Savings") && accountType2 == null) {

                credit.setSelected(false);
                savings.setSelected(true);

            }


        }


        System.out.println(accountType2 + " " + accountType1);

    }catch (Exception ex){
        ex.printStackTrace();
        resetButtonOnAction();
        failureMessage();

    }
    }

    public void updateUserDetails() throws SQLException {

        Connection connection = getConnection();

        ToggleButton selectedGender = (ToggleButton) gender.getSelectedToggle();
        int userId = fetchUserId();

        try {

            String updateUserDetailsQuery = "UPDATE UserProfile SET FirstName = '"+ firstName.getText() +"', LastName = '"+ lastName.getText() +"', Email = '"+ email.getText() +"', PhoneNumber = '"+ phoneNumber.getText() +"', Gender = '"+ selectedGender.getText() +"', DateOfBirth = '"+ date.getValue() +"' WHERE UserID = "+ userId +";";

            Statement statement = connection.createStatement();

            statement.execute(updateUserDetailsQuery);

            successMessage();

        }catch (Exception e) {

            e.printStackTrace();
            warning.setVisible(true);

        }
    }

    public void alertButtonOnAction(ActionEvent e) throws Exception {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.getDialogPane().getStylesheets().add(getClass().getResource("/Inventory/CSS/Style.css").toExternalForm());
        alert.setTitle("Delete?");
        alert.setContentText("Do you wish to delete this user?");

        Optional<ButtonType> result = alert.showAndWait();

        if (result.get() == ButtonType.OK) {

            deleteUserButtonOnAction(e);
        }

    }

    public void failureMessage(){

        warning.setVisible(true);
        warning.setText("Error Occurred");
        warning.setStyle("-fx-text-fill: #ff0000");

    }


    public void deleteUserButtonOnAction(ActionEvent e) throws SQLException{

        Connection connection = getConnection();
        int UserID = fetchUserId();
        String deleteUserProfile = "DELETE FROM UserProfile WHERE UserID = '"+ UserID +"';";
        String deleteUserAccount = "DELETE FROM Account WHERE UserID = '"+ UserID +"';";
        String deleteUserTransactionHistory = "DELETE FROM Transaction WHERE UserID = '"+ UserID +"';";

        Statement statement = connection.createStatement();

        try {
            statement.execute(deleteUserProfile);
            resetButtonOnAction();
            successMessage();
//            statement.execute(deleteUserAccount);
//            statement.execute(deleteUserTransactionHistory);
        }catch (Exception ex){

            ex.printStackTrace();
            failureMessage();


        }

    }


}
