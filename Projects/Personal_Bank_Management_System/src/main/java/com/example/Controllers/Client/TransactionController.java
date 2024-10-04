package com.example.Controllers.Client;

import com.example.Controllers.Admin.UserSearch;
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

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

public class TransactionController  extends DatabaseConnection implements Initializable {

    @FXML
    private Stage stage;
    @FXML
    private Scene scene;
    @FXML
    private TableView<TransactionSearch> transactionTable;
    @FXML
    private TableColumn<TransactionSearch,String> beneficiary, myReference, theirReference, status, date;
    @FXML
    private TableColumn<TransactionSearch, Integer> amount, accountNumber;
    @FXML
    private TextField searchTextField;

    private String loggedInUsername;

    public void setLoggedInUsername(String username){

        this.loggedInUsername = username;

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

    ObservableList<TransactionSearch> transactionList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        Connection connection = getConnection();

        String getMembers = "SELECT Transaction.Beneficiary, Account.AccountNumber, Transaction.Amount, Transaction.Status, Transaction.MyReference, Transaction.TheirReference, Transaction.CreatedAt\n" +
                "FROM Transaction\n" +
                "JOIN Account ON Transaction.AccountID = Account.AccountID\n" +
                "JOIN UserProfile ON Transaction.UserID = UserProfile.UserID\n" +
                "WHERE UserProfile.Username = '"+ loggedInUsername +"';";

        try {

            Statement statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery(getMembers);

            while(resultSet.next()){

                String beneficiary = resultSet.getString("Beneficiary");
                String myReference = resultSet.getString("MyReference");
                String theirReference = resultSet.getString("TheirReference");
                String status = resultSet.getString("Status");
                String date = resultSet.getString("DateOfBirth");
                int amount = resultSet.getInt("PhoneNumber");
                int accountNumber = resultSet.getInt("AccountNumber");

                transactionList.add(new TransactionSearch(amount,accountNumber, theirReference, status, date, beneficiary,  myReference));


            }




            beneficiary.setCellValueFactory(new PropertyValueFactory<>("beneficiary"));
            myReference.setCellValueFactory(new PropertyValueFactory<>("myReference"));
            theirReference.setCellValueFactory(new PropertyValueFactory<>("theirReference"));
            status.setCellValueFactory(new PropertyValueFactory<>("status"));
            date.setCellValueFactory(new PropertyValueFactory<>("date"));
            amount.setCellValueFactory(new PropertyValueFactory<>("amount"));
            accountNumber.setCellValueFactory(new PropertyValueFactory<>("accountNumber"));


            transactionTable.setItems(transactionList);
            FilteredList<TransactionSearch> filteredData = new FilteredList<>(transactionList, b -> true);

            searchTextField.textProperty().addListener((observable, oldValue, newValue) -> {

                filteredData.setPredicate(TransactionSearch -> {

                    if (newValue.isEmpty() || newValue.isBlank()) {

                        return true;

                    }

                    String searchKeyword = newValue.toLowerCase();

                    if (TransactionSearch.getBeneficiary().toLowerCase().contains(searchKeyword))  {

                        return true;

                    }

                    else if (TransactionSearch.getMyReference().toLowerCase().contains(searchKeyword)) {
                        return true;

                    }

                    else if (TransactionSearch.getTheirReference().toLowerCase().contains(searchKeyword)) {

                        return true;

                    }

                    else if (TransactionSearch.getStatus().toLowerCase().contains(searchKeyword)) {

                        return true;

                    }

                    else if (TransactionSearch.getDate().toLowerCase().contains(searchKeyword)) {

                        return true;

                    }

                    else if (String.valueOf(TransactionSearch.getAmount()).contains(searchKeyword)) {

                        return true;

                    }

                    else if (String.valueOf(TransactionSearch.getAccountNumber()).contains(searchKeyword)) {

                        return true;

                    }

                    else
                        return false;


                });

            });

            SortedList<TransactionSearch> sortedList = new SortedList<>(filteredData);

            sortedList.comparatorProperty().bind(transactionTable.comparatorProperty());

            transactionTable.setItems(sortedList);

        }catch (Exception ex){

            ex.printStackTrace();

        }

    }
}
