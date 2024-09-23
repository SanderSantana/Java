package com.example.Controllers.Admin;

import com.example.Controllers.LoginController;
import com.example.DatabaseConnection;
//import com.mysql.cj.protocol.a.SqlTimestampValueEncoder;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import javafx.event.ActionEvent;
import javafx.stage.StageStyle;

import javax.swing.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.Optional;

public class updateDeleteMemberController {

    @FXML
    private Stage stage;
    @FXML
    private Scene scene;

    //Contains the edit page scene
    public void updateDeleteMember(ActionEvent e) throws Exception{

        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("Inventory/FXML/Admin/updateDeleteMember.fxml"));
        stage = (Stage) ((Node)e.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();


    }

    @FXML
    private Button EditexitButton;


    public void exitButtonOnAction(ActionEvent e) throws Exception {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.getDialogPane().getStylesheets().add(getClass().getResource("/Inventory/Styles/Admin/dialog.css").toExternalForm());
        alert.setTitle("LogOut");
        alert.setContentText("Do you wish to exit the application?");

        Optional<ButtonType> result = alert.showAndWait();

        if (result.get() == ButtonType.OK) {

            Stage stage = (Stage) EditexitButton.getScene().getWindow();

            stage.close();

        }

    }

    @FXML
    private Button logoButton ,editButton, paymentButton, membersButton;

    public void logoButtonOnAction(ActionEvent e) throws Exception {

        WelcomeController welcomeController = new WelcomeController();
        welcomeController.WelcomePage(e);

    }


    public void paymentButtonOnAction(ActionEvent e) throws Exception {

        paymentController paymentController = new paymentController();
        paymentController.payment(e);

    }

    public void membersButtonOnAction(ActionEvent e) throws Exception {

        membersController membersController = new membersController();
        membersController.members(e);

    }

    public void newMemberButtonOnAction(ActionEvent e) throws Exception {


        NewMemberController newMemberController = new NewMemberController();
        newMemberController.newMember(e);

    }

    public void logOutButtonOnAction(ActionEvent e) throws Exception {

        LoginController loginController = new LoginController();

        loginController.backToLogInPage(e);

    }

    @FXML
    private Label warningTextLabel;
    @FXML
    private TextField EditmemberID, EditFnameField, EditMnameField, EditLnameField, EditemailField, EditPnumberField;
    @FXML
    private TextArea EditAddress;
    @FXML
    private ToggleGroup Editgender, EditgymSubscription;
    @FXML
    private Button searchButton, EditsaveButton, EditclearFormButton, deleteButton;
    @FXML
    private RadioButton EditgenderFemaleButton, EditgenderMaleButton, EditsubType1Button, EditsubType2Button, EditsubType3Button;
    @FXML
    private DatePicker EditDatePicker;

    //initially all fields are disable, when this method is called it enables all fields
    public void setEnable(ActionEvent e){

        EditFnameField.setDisable(false);
        EditMnameField.setDisable(false);
        EditLnameField.setDisable(false);
        EditemailField.setDisable(false);
        EditPnumberField.setDisable(false);
        EditAddress.setDisable(false);
        EditgenderMaleButton.setDisable(false);
        EditgenderFemaleButton.setDisable(false);
        EditDatePicker.setDisable(false);
        EditsubType1Button.setDisable(false);
        EditsubType2Button.setDisable(false);
        EditsubType3Button.setDisable(false);

    }

    //This method fetches details of a user on the database based on the ID given
    public void searchButtonOnAction(ActionEvent e) throws SQLException {



        try {

            //Establishing connection with the database
            DatabaseConnection databaseConnection = new DatabaseConnection();
            Connection connection = databaseConnection.getConnection();

            String id = EditmemberID.getText(); //getting id from user

            String verifyId = " SELECT COUNT(1) FROM GymMembers\n" +
                    "WHERE Member_ID = "+ id +";  "; //query to check if ID given exists

            String selectDetails = "SELECT First_Name, Middle_Name, Last_Name, Email, Gender_Name, Phone_Number, Date_Of_Birth, Street_Address, Subscription_Type FROM GymMembers \n" +
                    "INNER JOIN Gender ON Gender.Gender_ID = GymMembers.Gender_ID\n" +
                    "INNER JOIN Address ON Address.Address_ID = GymMembers.Address_ID\n" +
                    "INNER JOIN Subscription ON Subscription.Subscription_ID = GymMembers.Subscription_ID\n" +
                    "WHERE Member_ID = " + id + " ; "; //query to fetch member's details

            //making two statements to run the two queries
            Statement outerstatement = connection.createStatement();
            Statement innerstatement = connection.createStatement();
            //ResultSet objects are going to hold the result of our queries
            ResultSet verify = outerstatement.executeQuery(verifyId);
            ResultSet queryResult = innerstatement.executeQuery(selectDetails);

            while(verify.next()) {

                //checking if ID exists
                if(verify.getInt(1) == 1) {


                    //iterating over each row
                    while (queryResult.next()) {

                        setEnable(e); //enabling all fields that we once disable

                        //storing results in the fields
                        EditFnameField.setText(queryResult.getString(1));
                        EditMnameField.setText(queryResult.getString(2));
                        EditLnameField.setText(queryResult.getString(3));
                        EditemailField.setText(queryResult.getString(4));
                        String gender = queryResult.getString(5);

                        //checks gender of person and assigns it to the respective toggle button
                        if (gender.equals("Male")) {

                            EditgenderMaleButton.setSelected(true);

                        } else {

                            EditgenderFemaleButton.setSelected(true);

                        }

                        EditPnumberField.setText(queryResult.getString(6));

                        LocalDate localDate = LocalDate.parse(queryResult.getString(7));
                        EditDatePicker.setValue(localDate);

                        EditAddress.setText(queryResult.getString(8));
                        String subType = queryResult.getString(9);

                        //checks subscription of person and assigns it to the respective toggle button
                        switch (subType) {
                            case "Absolute Unit" -> EditsubType1Button.setSelected(true);
                            case "Broken Hearted" -> EditsubType2Button.setSelected(true);
                            case "Gym Bro" -> EditsubType3Button.setSelected(true);
                        }

                        //making the clear and the delete button visible
                        makeVisibleClearFormButtonInEdit(e);
                        makeDeleteButtonVisible(e);
                        warningTextLabel.setStyle("-fx-opacity: 0");

                    }

                }
                else {


                    warningTextLabel.setText("Invalid ID");
                    warningTextLabel.setStyle("-fx-text-fill: red;" + "-fx-font-weight: bold;" + "-fx-font-size: 14;" + "-fx-opacity: 1");
                    clearFormInEdit(e);

                }
            }


        }catch (Exception ev) {

            warningTextLabel.setText("An Error Occurred");
            warningTextLabel.setStyle("-fx-text-fill: red;" + "-fx-font-weight: bold;" + "-fx-font-size: 14;" + "-fx-opacity: 1");
            clearFormInEdit(e);

        }
    }

    public void makeVisibleClearFormButtonInEdit(ActionEvent e) {

        EditclearFormButton.setStyle("-fx-background-color: rgba(183, 180, 180, 0.82);" + "-fx-font-family: Arial;" + "-fx-opacity: 1; " );

    }

    //This method clears and disables all fields
    public void clearFormInEdit(ActionEvent e) {

        EditmemberID.clear();
        EditFnameField.clear();
        EditLnameField.clear();
        EditMnameField.clear();
        EditAddress.clear();
        EditPnumberField.clear();
        EditemailField.clear();
        EditgenderMaleButton.setSelected(false);
        EditgenderFemaleButton.setSelected(false);
        EditDatePicker.getEditor().clear();
        EditsubType1Button.setSelected(false);
        EditsubType2Button.setSelected(false);
        EditsubType3Button.setSelected(false);
        EditclearFormButton.setStyle("-fx-opacity: 0");
        EditsaveButton.setText("save");
        EditsaveButton.setStyle("-fx-font-family: Arial");

        makeDeleteButtonDisappear(e);
        EditFnameField.setDisable(true);
        EditMnameField.setDisable(true);
        EditLnameField.setDisable(true);
        EditemailField.setDisable(true);
        EditPnumberField.setDisable(true);
        EditAddress.setDisable(true);
        EditgenderMaleButton.setDisable(true);
        EditgenderFemaleButton.setDisable(true);
        EditDatePicker.setDisable(true);
        EditsubType1Button.setDisable(true);
        EditsubType2Button.setDisable(true);
        EditsubType3Button.setDisable(true);




    }

    public void makeDeleteButtonVisible(ActionEvent e) {

        deleteButton.setStyle(" -fx-font-family: Arial;" + "-fx-background-color: red;" + "-fx-text-fill: white;" + " -fx-opacity: 1 ");

    }

    //changes delete button style
    public void deleteButtonAfterDeletion(ActionEvent e) {

        deleteButton.setText("Deleted");
        deleteButton.setStyle(" -fx-font-family: Arial;" + "-fx-background-color: #fcc1c1;" + "-fx-text-fill: #e50000;" + " -fx-opacity: 1 ");

    }


    public void makeDeleteButtonDisappear(ActionEvent e) {

        deleteButton.setStyle("-fx-opacity: 0");

    }

    //This method updates the information of the user in the database
    public void saveButtonOnAction(ActionEvent e) throws SQLException {

        try {

            DatabaseConnection databaseConnection = new DatabaseConnection();
            Connection connection = databaseConnection.getConnection();

            LocalDate selectedDate = EditDatePicker.getValue();
            ToggleButton selectedToggle = (ToggleButton) Editgender.getSelectedToggle();
            ToggleButton selectedToggle2 = (ToggleButton) EditgymSubscription.getSelectedToggle();

            String fName = EditFnameField.getText();
            String mName = EditMnameField.getText();
            String lName = EditLnameField.getText();
            String email = EditemailField.getText();
            String pNumber = EditPnumberField.getText();
            String stringAddress = EditAddress.getText();
            //        String stringSelectedDate = selectedDate.toString();
            String selectedGender = selectedToggle.getText();
            String selectedSubscription = selectedToggle2.getText();
            String id = EditmemberID.getText();
            String subTypeId1 = "S1";
            String subTypeId2 = "S2";
            String subscription = "S3";
            String genderIdForMale = "G1";
            String genderIdForFemale = "G2";
            String sub1 = "800";
            String sub2 = "300";
            String sub3 = "450";

            //queries to update values into the address and payment page
            String Payment = " UPDATE Payment SET Outstanding = '" + sub1 + "'" + "WHERE Payment_ID = '" + id + "' ; ";
            String Payment2 = "UPDATE Payment SET Outstanding = '" + sub2 + "'" + "WHERE Payment_ID = '" + id + "' ;  ";
            String Payment3 = " UPDATE Payment SET Outstanding = '" + sub3 + "'" + "WHERE Payment_ID = '" + id + "' ;  ";
            String Address = "UPDATE Address SET street_Address = '" + stringAddress + "' WHERE Address_ID = '" + id + "';";

            //Several queries created that are executed depending on the gender of the member and subscription chosen.
            String updateMember1 = "UPDATE GymMembers \n" +
                    "SET First_Name = '" + fName + "', Middle_Name = '" + mName + "', Last_Name = '" + lName + "', Email = '" + email + "' , Subscription_ID = '" + subTypeId1 + "', Gender_ID = '" + genderIdForFemale + "', Phone_Number = '" + pNumber + "', Date_Of_Birth = '" + selectedDate + "'\n" +
                    "WHERE Member_ID = '" + id + "' ; ";

            String updateMember2 = "UPDATE GymMembers \n" +
                    "SET First_Name = '" + fName + "', Middle_Name = '" + mName + "', Last_Name = '" + lName + "', Email = '" + email + "' , Subscription_ID = '" + subTypeId2 + "', Gender_ID = '" + genderIdForFemale + "', Phone_Number = '" + pNumber + "', Date_Of_Birth = '" + selectedDate + "'\n" +
                    "WHERE Member_ID = '" + id + "' ; ";

            String updateMember3 = "UPDATE GymMembers \n" +
                    "SET First_Name = '" + fName + "', Middle_Name = '" + mName + "', Last_Name = '" + lName + "', Email = '" + email + "' , Subscription_ID = '" + subscription + "', Gender_ID = '" + genderIdForFemale + "', Phone_Number = '" + pNumber + "', Date_Of_Birth = '" + selectedDate + "'\n" +
                    "WHERE Member_ID = '" + id + "' ; ";

            String updateMember4 = "UPDATE GymMembers \n" +
                    "SET First_Name = '" + fName + "', Middle_Name = '" + mName + "', Last_Name = '" + lName + "', Email = '" + email + "' , Subscription_ID = '" + subTypeId1 + "', Gender_ID = '" + genderIdForMale + "', Phone_Number = '" + pNumber + "', Date_Of_Birth = '" + selectedDate + "'\n" +
                    "WHERE Member_ID = '" + id + "' ; ";

            String updateMember5 = "UPDATE GymMembers \n" +
                    "SET First_Name = '" + fName + "', Middle_Name = '" + mName + "', Last_Name = '" + lName + "', Email = '" + email + "' , Subscription_ID = '" + subTypeId2 + "', Gender_ID = '" + genderIdForMale + "', Phone_Number = '" + pNumber + "', Date_Of_Birth = '" + selectedDate + "'\n" +
                    "WHERE Member_ID = '" + id + "' ; ";

            String updateMember6 = "UPDATE GymMembers \n" +
                    "SET First_Name = '" + fName + "', Middle_Name = '" + mName + "', Last_Name = '" + lName + "', Email = '" + email + "' , Subscription_ID = '" + subscription + "', Gender_ID = '" + genderIdForMale + "', Phone_Number = '" + pNumber + "', Date_Of_Birth = '" + selectedDate + "'\n" +
                    "WHERE Member_ID = '" + id + "' ; ";

            String checkIfIdAlreadyExists = " SELECT COUNT(1) FROM GymMembers WHERE Member_ID = " + id + "; ";

            //we need statements to run our queries
            Statement statement2 = connection.createStatement();
            Statement outerStatement = connection.createStatement();

            //storing result of our query in ResultSet object
            ResultSet queryResult = outerStatement.executeQuery(checkIfIdAlreadyExists);

            while (queryResult.next()) {

                //if id does exist in the database new member will be inserted depending on the condition below
                if (queryResult.getInt(1) == 1) {

                    if (selectedGender.equals("Female") && selectedSubscription.equals("Absolute Unit")) {

                        statement2.execute(updateMember1);
                        statement2.execute(Payment);
                        statement2.execute(Address);


                    } else if (selectedGender.equals("Female") && selectedSubscription.equals("Broken Hearted")) {

                        statement2.execute(updateMember2);
                        statement2.execute(Payment2);
                        statement2.execute(Address);


                    } else if (selectedGender.equals("Female") && selectedSubscription.equals("Gym Bro")) {

                        statement2.execute(updateMember3);
                        statement2.execute(Payment3);
                        statement2.execute(Address);


                    } else if (selectedGender.equals("Male") && selectedSubscription.equals("Absolute Unit")) {

                        statement2.execute(updateMember4);
                        statement2.execute(Payment);
                        statement2.execute(Address);


                    } else if (selectedGender.equals("Male") && selectedSubscription.equals("Broken Hearted")) {

                        statement2.execute(updateMember5);
                        statement2.execute(Payment2);
                        statement2.execute(Address);


                    } else if (selectedGender.equals("Male") && selectedSubscription.equals("Gym Bro")) {

                        statement2.execute(updateMember6);
                        statement2.execute(Payment3);
                        statement2.execute(Address);


                    }
                    else {

                        warningTextLabel.setStyle("-fx-text-fill: red");
                        warningTextLabel.setText("Invalid ID");

                    }
                }
            }

            statement2.close();


        }catch (Exception ev) {

            warningTextLabel.setText("An Error Occurred");
            warningTextLabel.setStyle("-fx-text-fill: red;" + "-fx-font-weight: bold;" + "-fx-font-size: 14");

        }
    }



    //This method ensures that no unwanted values are entered in the database
    public void updateCheck(ActionEvent e) throws Exception {


        if (EditFnameField.getText().isBlank() || EditMnameField.getText().isBlank() || EditLnameField.getText().isBlank() || EditemailField.getText().isBlank() || EditPnumberField.getText().isBlank() || EditAddress.getText().isBlank()) {

            warningTextLabel.setStyle("-fx-text-fill: red;" + "-fx-font-weight: bold;" + "-fx-font-size: 14");
            warningTextLabel.setText("Fill In Everything!");
            EditsaveButton.setStyle("-fx-font-family: Arial;");
            EditsaveButton.setText("save");


        } else if (EditFnameField.getText().matches(".*\\s.*") || EditMnameField.getText().matches(".*\\s.*") || EditLnameField.getText().matches(".*\\s.*") || EditemailField.getText().matches(".*\\s.*") || EditPnumberField.getText().matches(".*\\s.*")) {

            warningTextLabel.setStyle("-fx-text-fill: red;" + "-fx-font-weight: bold;" + "-fx-font-size: 14");
            warningTextLabel.setText("Remove Spaces From Text Fields");
            EditsaveButton.setStyle("-fx-font-family: Arial;");
            EditsaveButton.setText("save");

        } else if (EditFnameField.getText().matches(".*\\d.*") || EditMnameField.getText().matches(".*\\d.*") || EditLnameField.getText().matches(".*\\d.*")) {

            warningTextLabel.setStyle("-fx-text-fill: red;" + "-fx-font-weight: bold;" + "-fx-font-size: 14");
            warningTextLabel.setText("Only Letters in Text Fields Allowed");
            EditsaveButton.setStyle("-fx-font-family: Arial;");
            EditsaveButton.setText("save");

        } else if (EditFnameField.getText().matches(".*[!@#$%^&*()\\[\\]{};':\",.<>?/\\\\|_+=-].*") || EditMnameField.getText().matches(".*[!@#$%^&*()\\[\\]{};':\",.<>?/\\\\|_+=-].*") || EditLnameField.getText().matches(".*[!@#$%^&*()\\[\\]{};':\",.<>?/\\\\|_+=-].*") || EditPnumberField.getText().matches(".*[!@#$%^&*()\\[\\]{};':\",.<>?/\\\\|_+=-].*")) {

            warningTextLabel.setStyle("-fx-text-fill: red");
            warningTextLabel.setText("Please remove specials Characters(not @ from email)");
            EditsaveButton.setStyle("-fx-font-family: Arial;");
            EditsaveButton.setText("save");

        } else if (EditemailField.getText().matches(".*[!#$%^&*()\\[\\]{};':\",<>?/\\\\|_+=-].*")) {

            warningTextLabel.setStyle("-fx-text-fill: red;" + "-fx-font-weight: bold;" + "-fx-font-size: 14");
            warningTextLabel.setText("Remove Specials Characters(not @ from email)");
            EditsaveButton.setStyle("-fx-font-family: Arial;");
            EditsaveButton.setText("save");

        } else if (EditPnumberField.getText().matches("\\d{10}") == false && EditPnumberField.getText().matches("[0-9]") == false) {

            warningTextLabel.setStyle("-fx-text-fill: red;" + "-fx-font-weight: bold;" + "-fx-font-size: 14");
            warningTextLabel.setText("Invalid Phone Number");
            EditsaveButton.setStyle("-fx-font-family: Arial;");
            EditsaveButton.setText("save");

        } else if (EditgenderFemaleButton.isSelected() == false && EditgenderMaleButton.isSelected() == false) {

            warningTextLabel.setStyle("-fx-text-fill: red;" + "-fx-font-weight: bold;" + "-fx-font-size: 14");
            warningTextLabel.setText("Are You Genderless?");
            EditsaveButton.setStyle("-fx-font-family: Arial;");
            EditsaveButton.setText("save");

        } else if (EditsubType1Button.isSelected() == false && EditsubType2Button.isSelected() == false && EditsubType3Button.isSelected() == false) {

            warningTextLabel.setStyle("-fx-text-fill: red;" + "-fx-font-weight: bold;" + "-fx-font-size: 14");
            warningTextLabel.setText("Please Choose A Subscription");
            EditsaveButton.setStyle("-fx-font-family: Arial;");
            EditsaveButton.setText("save");

        } else if (EditDatePicker.getValue() == null) {

            warningTextLabel.setStyle("-fx-text-fill: red;" + "-fx-font-weight: bold;" + "-fx-font-size: 14");
            warningTextLabel.setText("DOB Missing");
            EditsaveButton.setStyle("-fx-font-family: Arial;");
            EditsaveButton.setText("save");

        } else {

            warningTextLabel.setStyle("-fx-text-fill: green;" + "-fx-font-weight: bold;" + "-fx-font-size: 14");
            warningTextLabel.setText("ALL GOOD!");
            EditsaveButton.setText("Saved");
            EditsaveButton.setStyle("-fx-font-weight: bold;" + "-fx-background-color: #004901; " + "-fx-text-fill: #00ee01;");
            saveButtonOnAction(e); //Calls method to update data into the database


        }
    }

    //This method deletes user from database when called
    public void deleteButtonOnAction (ActionEvent e) throws SQLException {

        try {

            //creating database connection
            DatabaseConnection databaseConnection = new DatabaseConnection();
            Connection connection = databaseConnection.getConnection();

            //statement object to run query
            Statement statement = connection.createStatement();

            String id = EditmemberID.getText();

            //String to delete in all tables where that id is present
            String delete = "DELETE FROM GymMembers WHERE Member_ID = '"+ id +"';";
            String delete2 =  "DELETE FROM Payment WHERE Payment_ID = '"+ id +"';";
            String delete3 =  "DELETE FROM Address WHERE Address_ID = '"+ id +"';";
            String checkIfIdAlreadyExists = " SELECT COUNT(1) FROM GymMembers WHERE Member_ID = " + id +"; ";

            //ResultSet object to store the result of our query
            ResultSet queryResult = statement.executeQuery(checkIfIdAlreadyExists);

            while(queryResult.next()) {

                Statement innerstatement = connection.createStatement(); //we need another statement to ru the queries inside the if statements

                //Checking if ID exists
                if (queryResult.getInt(1) == 1) {

                    innerstatement.execute(delete);
                    innerstatement.execute(delete2);
                    innerstatement.execute(delete3);
                    deleteButtonAfterDeletion(e);
                    clearFormInEdit(e);
                    warningTextLabel.setText("Member Deleted Successfully!");
                    warningTextLabel.setStyle("-fx-text-fill: green;" + "-fx-font-weight: bold;" + "-fx-font-size: 14");

                } else {

                    warningTextLabel.setText("Invalid ID");
                    warningTextLabel.setStyle("-fx-text-fill: red;" + "-fx-font-weight: bold;" + "-fx-font-size: 14");


                }

            }
        }catch (Exception ev) {

            warningTextLabel.setText("An Error Occurred");
            warningTextLabel.setStyle("-fx-text-fill: red;" + "-fx-font-weight: bold;" + "-fx-font-size: 14");

        }




    }







}
