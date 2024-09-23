package com.example.Controllers.Admin;

import com.example.Controllers.LoginController;
import com.example.DatabaseConnection;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.event.ActionEvent;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class membersController implements Initializable {


    @FXML
    private Scene scene;
    @FXML
    private Stage stage;


    public void members(ActionEvent e) throws Exception {

        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("Inventory/FXML/Admin/members.fxml"));
        stage = (Stage) ((Node)e.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }

    public void newMemberButtonOnAction(ActionEvent e) throws Exception {

        NewMemberController newMemberController = new NewMemberController();
        newMemberController.newMember(e);


    }

    public void editMemberButtonOnAction(ActionEvent e) throws Exception {

        updateDeleteMemberController updateDeleteMemberController = new updateDeleteMemberController();
        updateDeleteMemberController.updateDeleteMember(e);

    }

    public void paymentButtonOnAction(ActionEvent e) throws Exception {

        paymentController paymentController = new paymentController();
        paymentController.payment(e);

    }

    @FXML
    private Button exitButton;

    public void exitButtonOnAction(ActionEvent e) throws Exception {

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

    public void logOutButtonOnAction(ActionEvent e) throws Exception {

        LoginController loginController = new LoginController();

        loginController.backToLogInPage(e);

    }

    public void logoButtonOnAction(ActionEvent e) throws Exception {

        WelcomeController welcomeController = new WelcomeController();
        welcomeController.WelcomePage(e);

    }

    @FXML
    private TableView<memberSearch> membersTable;
    @FXML
    private TableColumn<memberSearch, Integer> idColumn, phoneNumberColumn, outstandingColumn;
    @FXML
    private TableColumn<memberSearch, String> fNameColumn, lNameColumn, genderColumn, emailColumn, dobColumn, subscriptionColumn, addressColumn;
    @FXML
    private TextField searchTextField;

    ObservableList<memberSearch> memberSearchObservableList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){

        DatabaseConnection databaseConnection = new DatabaseConnection();
        Connection connection = databaseConnection.getConnection();

        String getMembers = " \n" +
                "SELECT Member_ID, First_Name, Last_Name, Gender_Name, Phone_Number, Email, Date_Of_Birth, Subscription_Type, Outstanding, Street_Address FROM GymMembers \n" +
                "INNER JOIN Gender ON Gender.Gender_ID = GymMembers.Gender_ID\n" +
                "INNER JOIN Subscription ON Subscription.Subscription_ID = GymMembers.Subscription_ID \n" +
                "INNER JOIN Payment ON Payment.Payment_ID = GymMembers.Payment_ID\n" +
                "INNER JOIN Address On Address.Address_ID = GymMembers.Address_ID ";

        try {

            Statement statement = connection.createStatement();
            ResultSet querryOutput = statement.executeQuery(getMembers);

            while(querryOutput.next()) {

                int memberID = querryOutput.getInt("Member_ID");
                int pNumber = querryOutput.getInt("Phone_Number");
                String fName = querryOutput.getString("First_Name");
                String lName = querryOutput.getString("Last_Name");
                String gender = querryOutput.getString("Gender_Name");
                String email = querryOutput.getString("Email");
                String dob = querryOutput.getString("Date_Of_Birth");
                String subType = querryOutput.getString("Subscription_Type");
                int outstanding = querryOutput.getInt("Outstanding");
                String address = querryOutput.getString("Street_Address");

                memberSearchObservableList.add(new memberSearch(fName, lName, gender, email, dob, subType, address , memberID, pNumber, outstanding));

            }

            idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
            fNameColumn.setCellValueFactory(new PropertyValueFactory<>("fName"));
            lNameColumn.setCellValueFactory(new PropertyValueFactory<>("lName"));
            genderColumn.setCellValueFactory(new PropertyValueFactory<>("gender"));
            phoneNumberColumn.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
            emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
            dobColumn.setCellValueFactory(new PropertyValueFactory<>("dob"));
            subscriptionColumn.setCellValueFactory(new PropertyValueFactory<>("subscription"));
            outstandingColumn.setCellValueFactory(new PropertyValueFactory<>("outstanding"));
            addressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));

            membersTable.setItems(memberSearchObservableList);
            FilteredList<memberSearch> filteredData = new FilteredList<>(memberSearchObservableList, b -> true);

            searchTextField.textProperty().addListener((observable, oldValue, newValue) -> {

                filteredData.setPredicate(memberSearch -> {

                    if (newValue.isEmpty() || newValue.isBlank()) {

                        return true;

                    }

                    String searchKeyword = newValue.toLowerCase();

                    if (memberSearch.getFName().toLowerCase().contains(searchKeyword))  {

                        return true;

                    } 
                    
                    else if (memberSearch.getLName().toLowerCase().contains(searchKeyword)) {
                        return true; 
                        
                    } 
                    
                    else if (memberSearch.getGender().toLowerCase().contains(searchKeyword)) {
                        
                        return true; 
                        
                    }

                    else if (memberSearch.getEmail().toLowerCase().contains(searchKeyword)) {

                        return true;

                    }

                    else if (memberSearch.getDob().toLowerCase().contains(searchKeyword)) {

                        return true;

                    }

                    else if (memberSearch.getSubscription().toLowerCase().contains(searchKeyword)) {

                        return true;

                    }

                    else if (memberSearch.getAddress().toLowerCase().contains(searchKeyword)) {

                        return true;

                    }

                    else
                        return false;


                });

            });

            SortedList<memberSearch> sortedList = new SortedList<>(filteredData);

            sortedList.comparatorProperty().bind(membersTable.comparatorProperty());

            membersTable.setItems(sortedList);

        }catch (SQLException e){

            Logger.getLogger(membersController.class.getName()).log(Level.SEVERE, null, e);
            e.printStackTrace();

        }

    }
}
