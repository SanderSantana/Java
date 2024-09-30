package com.example.Controllers.Admin;

import com.example.DatabaseConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;

public class EditUserController {

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

    @FXML
    private TextField firstName, lastName, email, phoneNumber, accountNumber, warnings;
    @FXML
    private RadioButton savings, credit, male, female;
    @FXML
    private DatePicker date;
    @FXML
    private ToggleGroup gender;

    public void enableFields() {

        firstName.setDisable(false);
        lastName.setDisable(false);
        email.setDisable(false);
        phoneNumber.setDisable(false);
        savings.setDisable(false);
        credit.setDisable(false);
        male.setDisable(false);
        female.setDisable(false);
        date.setDisable(false);


        firstName.setEditable(true);
        lastName.setEditable(true);
        email.setEditable(true);
        phoneNumber.setEditable(true);

    }


    public void fetchInformation(ActionEvent e) throws IOException, SQLException {



        DatabaseConnection databaseConnection = new DatabaseConnection();
        Connection connection = databaseConnection.getConnection();

        try {


            String verifyAccount = " SELECT COUNT(1) FROM Account\n" +
                    "WHERE AccountNumber = " + Integer.parseInt(accountNumber.getText()) + ";";

            String fetchDetails = "SELECT FirstName, LastName, DateOfBirth, Gender, PhoneNumber, Email, Account.AccountType\n" +
                    "FROM UserProfile \n" +
                    "INNER JOIN Account ON Account.Username = UserProfile.Username\n" +
                    "WHERE AccountNumber = " + Integer.parseInt(accountNumber.getText());

            Statement outerstatement = connection.createStatement();
            Statement innerstatement = connection.createStatement();

            ResultSet verify = outerstatement.executeQuery(verifyAccount);
            ResultSet queryResult = innerstatement.executeQuery(fetchDetails);

            while (verify.next()) {

                if (verify.getInt(1) == 1) {

                    while (queryResult.next()) {

                        enableFields();

                        firstName.setText(queryResult.getString(1));
                        lastName.setText(queryResult.getString(2));
                        LocalDate localDate = LocalDate.parse(queryResult.getString(3));
                        date.setValue(localDate);

                        String gender = queryResult.getString(5);
                        if (gender.equals("Male")) {

                            male.setSelected(true);

                        } else {

                            female.setSelected(true);

                        }
                        phoneNumber.setText(queryResult.getString(5));

                        email.setText(queryResult.getString(6));

                        String account = queryResult.getString(7);
                        if (account.equals("Savings")) {

                            savings.setSelected(true);

                        } else if (account.equals("Credit")) {

                            credit.setSelected(true);

                        } else if (account.equals("Savings") && account.equals("Credit")) {

                            savings.setSelected(true);
                            credit.setSelected(true);

                        }


                    }

                }

            }
        }catch (Exception ex) {

            ex.printStackTrace();

            warnings.setVisible(true);

        }
    }

}
