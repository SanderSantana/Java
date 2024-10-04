package com.example.Controllers.Admin;

import com.example.DatabaseConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import javax.swing.plaf.nimbus.State;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;

public class UsersController extends DatabaseConnection implements Initializable {

    public void UsersPage(ActionEvent e) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("/Inventory/FXML/Admin/Users.fxml"));
        stage = (Stage)((Node)e.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }

    @FXML
    private Stage stage;
    @FXML
    private Scene scene;
    @FXML
    private TableView<UserSearch> usersTable;
    @FXML
    private TableColumn<UserSearch,String> username, firstname, lastname, email, date, gender, accountType;
    @FXML
    private TableColumn<UserSearch, Integer> phoneNumber, accountNumber, balance;
    @FXML
    private TextField searchTextField;

    ObservableList<UserSearch> usersList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        Connection connection = getConnection();

        String getMembers = "SELECT Username, FirstName, LastName, Email, DateOfBirth, Gender, PhoneNumber, AccountType, AccountNumber, Balance FROM UserProfile INNER JOIN Account ON Account.UserID = UserProfile.UserID";

        try {

            Statement statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery(getMembers);

            while(resultSet.next()){

                String username = resultSet.getString("Username");
                String firstname = resultSet.getString("FirstName");
                String lastname = resultSet.getString("LastName");
                String email = resultSet.getString("Email");
                String date = resultSet.getString("DateOfBirth");
                String gender = resultSet.getString("Gender");
                String accountType = resultSet.getString("AccountType");
                int phoneNumber = resultSet.getInt("PhoneNumber");
                int accountNumber = resultSet.getInt("AccountNumber");
                int balance = resultSet.getInt("Balance");

                usersList.add(new UserSearch(username, firstname, lastname, email, date, gender, accountType, phoneNumber, accountNumber, balance));


            }




            username.setCellValueFactory(new PropertyValueFactory<>("username"));
            firstname.setCellValueFactory(new PropertyValueFactory<>("firstname"));
            lastname.setCellValueFactory(new PropertyValueFactory<>("lastname"));
            email.setCellValueFactory(new PropertyValueFactory<>("email"));
            gender.setCellValueFactory(new PropertyValueFactory<>("gender"));
            date.setCellValueFactory(new PropertyValueFactory<>("date"));
            accountType.setCellValueFactory(new PropertyValueFactory<>("accountType"));
            phoneNumber.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
            accountNumber.setCellValueFactory(new PropertyValueFactory<>("accountNumber"));
            balance.setCellValueFactory(new PropertyValueFactory<>("balance"));

            usersTable.setItems(usersList);
            FilteredList<UserSearch> filteredData = new FilteredList<>(usersList, b -> true);

            searchTextField.textProperty().addListener((observable, oldValue, newValue) -> {

                filteredData.setPredicate(UserSearch -> {

                    if (newValue.isEmpty() || newValue.isBlank()) {

                        return true;

                    }

                    String searchKeyword = newValue.toLowerCase();

                    if (UserSearch.getFirstname().toLowerCase().contains(searchKeyword))  {

                        return true;

                    }

                    else if (UserSearch.getLastname().toLowerCase().contains(searchKeyword)) {
                        return true;

                    }

                    else if (UserSearch.getGender().toLowerCase().contains(searchKeyword)) {

                        return true;

                    }

                    else if (UserSearch.getEmail().toLowerCase().contains(searchKeyword)) {

                        return true;

                    }

                    else if (UserSearch.getDate().toLowerCase().contains(searchKeyword)) {

                        return true;

                    }

                    else if (UserSearch.getAccountType().toLowerCase().contains(searchKeyword)) {

                        return true;

                    }

                    else if (String.valueOf(UserSearch.getAccountNumber()).contains(searchKeyword)) {

                        return true;

                    }

                    else if (String.valueOf(UserSearch.getPhoneNumber()).contains(searchKeyword)) {

                        return true;

                    }

                    else if (String.valueOf(UserSearch.getBalance()).contains(searchKeyword)) {

                        return true;

                    }

                    else
                        return false;


                });

            });

            SortedList<UserSearch> sortedList = new SortedList<>(filteredData);

            sortedList.comparatorProperty().bind(usersTable.comparatorProperty());

            usersTable.setItems(sortedList);

        }catch (Exception ex){

            ex.printStackTrace();

        }
    }

    public void CreateUsersPage(ActionEvent e) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("/Inventory/FXML/Admin/CreateUsers.fxml"));
        stage = (Stage)((Node)e.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();




    }
    public void editButtonOnAction(ActionEvent e) throws IOException {

        EditUserController editUserController = new EditUserController();
        editUserController.EditUserPage(e);

    }

    public void depositButtonOnAction(ActionEvent e) throws IOException {

        DepositController depositController = new DepositController();
        depositController.DepositPage(e);

    }

    public void logoutButtonOnAction(ActionEvent e) throws IOException {

        AdminLoginController adminLoginController = new AdminLoginController();
        adminLoginController.AdminLoginPage(e);

    }

}
