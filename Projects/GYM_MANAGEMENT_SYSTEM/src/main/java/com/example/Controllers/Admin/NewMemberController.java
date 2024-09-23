package com.example.Controllers.Admin;

import com.example.Controllers.LoginController;
import com.example.DatabaseConnection;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.event.ActionEvent;
import java.sql.*;
import java.time.LocalDate;
import java.util.Optional;


public class NewMemberController {

    @FXML
    private Stage stage;
    @FXML
    private Scene scene;



    //Loads the New member scene
    public void newMember(ActionEvent e) throws Exception{

        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("Inventory/FXML/Admin/newMember.fxml"));
        stage = (Stage) ((Node)e.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();



    }



    @FXML
    private TextField FnameField, MnameField, LnameField, emailField, PnumberField;
    @FXML
    private TextArea address;
    @FXML
    private DatePicker DatePicker;
    @FXML
    private Label warningTextLabel;
    @FXML
    private Button saveButton;
    @FXML
    private RadioButton genderFemaleButton, genderMaleButton, subType1Button, subType2Button, subType3Button;
    @FXML
    private ToggleGroup gender, gymSubscription;

    //This method contains a series of if statements to prevent user from inserting unwanted information
    public void submissionCheck(ActionEvent e) throws Exception {


        try {

            if (FnameField.getText().isBlank() || MnameField.getText().isBlank() || LnameField.getText().isBlank() || emailField.getText().isBlank() || PnumberField.getText().isBlank() || address.getText().isBlank()) {

                warningTextLabel.setStyle("-fx-text-fill: red;" + "-fx-font-weight: bold;" + "-fx-font-size: 14");
                warningTextLabel.setText("Fill In Everything!");
                saveButton.setStyle("-fx-font-family: Arial;");
                saveButton.setText("save");


            } else if (FnameField.getText().matches(".*\\s.*") || MnameField.getText().matches(".*\\s.*") || LnameField.getText().matches(".*\\s.*") || emailField.getText().matches(".*\\s.*") || PnumberField.getText().matches(".*\\s.*")) {

                warningTextLabel.setStyle("-fx-text-fill: red;" + "-fx-font-weight: bold;" + "-fx-font-size: 14");
                warningTextLabel.setText("Remove Spaces From Text Fields");
                saveButton.setStyle("-fx-font-family: Arial;");
                saveButton.setText("save");

            } else if (FnameField.getText().matches(".*\\d.*") || MnameField.getText().matches(".*\\d.*") || LnameField.getText().matches(".*\\d.*")) {

                warningTextLabel.setStyle("-fx-text-fill: red;" + "-fx-font-weight: bold;" + "-fx-font-size: 14");
                warningTextLabel.setText("Only Letters in Text Fields Allowed");
                saveButton.setStyle("-fx-font-family: Arial;");
                saveButton.setText("save");

            } else if (FnameField.getText().matches(".*[!@#$%^&*()\\[\\]{};':\",.<>?/\\\\|_+=-].*") || MnameField.getText().matches(".*[!@#$%^&*()\\[\\]{};':\",.<>?/\\\\|_+=-].*") || LnameField.getText().matches(".*[!@#$%^&*()\\[\\]{};':\",.<>?/\\\\|_+=-].*") || PnumberField.getText().matches(".*[!@#$%^&*()\\[\\]{};':\",.<>?/\\\\|_+=-].*")) {

                warningTextLabel.setStyle("-fx-text-fill: red;" + "-fx-font-weight: bold;" + "-fx-font-size: 14");
                warningTextLabel.setText("Remove Specials Characters(not @ from email)");
                saveButton.setStyle("-fx-font-family: Arial;");
                saveButton.setText("save");

            } else if (emailField.getText().matches(".*[!#$%^&*()\\[\\]{};':\",<>?/\\\\|_+=-].*")) {

                warningTextLabel.setStyle("-fx-text-fill: red;" + "-fx-font-weight: bold;" + "-fx-font-size: 14");
                warningTextLabel.setText("Remove Special Characters From Email");
                saveButton.setStyle("-fx-font-family: Arial;");
                saveButton.setText("save");

            } else if (PnumberField.getText().matches("\\d{10}") == false && PnumberField.getText().matches("[0-9]") == false) {

                warningTextLabel.setStyle("-fx-text-fill: red;" + "-fx-font-weight: bold;" + "-fx-font-size: 14");
                warningTextLabel.setText("Invalid Phone Number");
                saveButton.setStyle("-fx-font-family: Arial;");
                saveButton.setText("save");

            } else if (genderFemaleButton.isSelected() == false && genderMaleButton.isSelected() == false) {

                warningTextLabel.setStyle("-fx-text-fill: red;" + "-fx-font-weight: bold;" + "-fx-font-size: 14");
                warningTextLabel.setText("Are You Genderless?");
                saveButton.setStyle("-fx-font-family: Arial;");
                saveButton.setText("save");

            } else if (subType1Button.isSelected() == false && subType2Button.isSelected() == false && subType3Button.isSelected() == false) {

                warningTextLabel.setStyle("-fx-text-fill: red;" + "-fx-font-weight: bold;" + "-fx-font-size: 14");
                warningTextLabel.setText("Please Choose A Subscription");
                saveButton.setStyle("-fx-font-family: Arial;");
                saveButton.setText("save");

            } else if (DatePicker.getValue() == null) {

                warningTextLabel.setStyle("-fx-text-fill: red;" + "-fx-font-weight: bold;" + "-fx-font-size: 14");
                warningTextLabel.setText("DOB Missing");
                saveButton.setStyle("-fx-font-family: Arial;");
                saveButton.setText("save");

            } else {

                warningTextLabel.setStyle("-fx-text-fill: green;" + "-fx-font-weight: bold;" + "-fx-font-size: 14");
                warningTextLabel.setText("ALL GOOD!");
                saveButton.setText("Saved");
                saveButton.setStyle("-fx-font-weight: bold;" + "-fx-background-color: #004901; " + "-fx-text-fill: #00ee01;");
                makeVisibleClearFormButton(e);
                addMembers(e); //Inserts member into the database

            }

        }catch (Exception ev) {

            warningTextLabel.setText("An Error Occurred");
            warningTextLabel.setStyle("-fx-text-fill: red;" + "-fx-font-weight: bold;" + "-fx-font-size: 14");

        }
    }

    @FXML
    private Button exitButton;

    //This method calls the welcome method from their class. When called it shows sends user back to the welcome page
    public void logoButtonOnAction(ActionEvent e) throws Exception {

        WelcomeController welcomeController = new WelcomeController();
        welcomeController.WelcomePage(e);

    }

    //This method calls the update/delete method from their class. When called it shows the edit page
    public void editButtonOnAction(ActionEvent e) throws Exception {

        updateDeleteMemberController editMember = new updateDeleteMemberController();
        editMember.updateDeleteMember(e);

    }

    //This method calls the payment method from their class. When called it shows the payment page
    public void paymentButtonOnAction(ActionEvent e) throws Exception {

        paymentController paymentController = new paymentController();
        paymentController.payment(e);

    }

    //This method calls the members method from their class. When called it shows the members page
    public void membersButtonOnAction(ActionEvent e) throws Exception {

        membersController membersController = new membersController();
        membersController.members(e);

    }

    //this method closes the program
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

    //This method calls the logOut method from their class. When called it shows sends user back to the logOut page
    public void logOutButtonOnAction(ActionEvent e) throws Exception {

        LoginController loginController = new LoginController();

        loginController.backToLogInPage(e);

    }

    @FXML
    private TextField memberID;

    //This method inserts members into the database
    public void addMembers(ActionEvent e) throws SQLException {

        try {

            //changing the date format
            LocalDate selectedDate = DatePicker.getValue();
            //we get the value of the selected subscription or gender
            ToggleButton selectedToggle = (ToggleButton) gender.getSelectedToggle();
            ToggleButton selectedToggle2 = (ToggleButton) gymSubscription.getSelectedToggle();

            //assigning values entered by user into Strings variables
            String fName = FnameField.getText();
            String mName = MnameField.getText();
            String lName = LnameField.getText();
            String email = emailField.getText();
            String pNumber = PnumberField.getText();
            String stringAddress = address.getText();
            String selectedGender = selectedToggle.getText();
            String selectedSubscription = selectedToggle2.getText();
            String id = memberID.getText();

            String sub1 = "800";
            String sub2 = "300";
            String sub3 = "450";

            //Establishing connection with the database
            DatabaseConnection databaseConnection = new DatabaseConnection();
            Connection connection = databaseConnection.getConnection();


            String checkIfIdAlreadyExists = " SELECT COUNT(1) FROM GymMembers WHERE Member_ID = " + id + "; ";

            //Several queries created that are executed depending on the gender of the member and subscription chosen.
            String female1 = "Insert into GymMembers Values ('" + id + "', '" + fName + "', '" + mName + "', '" + lName + "' , '" + email + "', '" + id + "' , 'S1', 'G2', '" + id + "' , '" + pNumber + "', '" + selectedDate + "'); ";
            String female2 = "Insert into GymMembers Values ('" + id + "', '" + fName + "', '" + mName + "', '" + lName + "' , '" + email + "', '" + id + "' , 'S2', 'G2', '" + id + "' , '" + pNumber + "', '" + selectedDate + "'); ";
            String female3 = "Insert into GymMembers Values ('" + id + "', '" + fName + "', '" + mName + "', '" + lName + "' , '" + email + "', '" + id + "' , 'S3', 'G2', '" + id + "' , '" + pNumber + "', '" + selectedDate + "'); ";

            String male1 = "Insert into GymMembers Values ('" + id + "', '" + fName + "', '" + mName + "', '" + lName + "' , '" + email + "', '" + id + "' , 'S1', 'G1', '" + id + "' , '" + pNumber + "', '" + selectedDate + "'); ";
            String male2 = "Insert into GymMembers Values ('" + id + "', '" + fName + "', '" + mName + "', '" + lName + "' , '" + email + "', '" + id + "' , 'S2', 'G1', '" + id + "' , '" + pNumber + "', '" + selectedDate + "'); ";
            String male3 = "Insert into GymMembers Values ('" + id + "', '" + fName + "', '" + mName + "', '" + lName + "' , '" + email + "', '" + id + "' , 'S3', 'G1', '" + id + "' , '" + pNumber + "', '" + selectedDate + "'); ";

            //queries to store values into the address and payment page
            String address = "Insert into Address Values ( '" + id + "', '" + stringAddress + "' );";
            String payment1 = "insert into Payment Values ('" + id + "', '" + sub1 + "');";
            String payment2 = "insert into Payment Values ('" + id + "', '" + sub2 + "');";
            String payment3 = "insert into Payment Values ('" + id + "', '" + sub3 + "');";


            Statement statement = connection.createStatement(); //statement object to run query
            ResultSet queryResult = statement.executeQuery(checkIfIdAlreadyExists); //resultset object to get the result of our query


            while (queryResult.next()) {

                //if id doesn't exist in the database new member will be inserted depending on the condition below
                if (queryResult.getInt(1) != 1) {

                    Statement innerStatement = connection.createStatement(); //we have another statement to run the queries to insert information

                    if (selectedGender.equals("Female") && selectedSubscription.equals("Absolute Unit")) {

                        innerStatement.execute(female1);
                        innerStatement.execute(address);
                        innerStatement.execute(payment1);

                    } else if (selectedGender.equals("Female") && selectedSubscription.equals("Broken Hearted")) {


                        innerStatement.execute(female2);
                        innerStatement.execute(address);
                        innerStatement.execute(payment2);

                    } else if (selectedGender.equals("Female") && selectedSubscription.equals("Gym Bro")) {


                        innerStatement.execute(female3);
                        innerStatement.execute(address);
                        innerStatement.execute(payment3);


                    } else if (selectedGender.equals("Male") && selectedSubscription.equals("Absolute Unit")) {

                        innerStatement.execute(male1);
                        innerStatement.execute(address);
                        innerStatement.execute(payment1);

                    } else if (selectedGender.equals("Male") && selectedSubscription.equals("Broken Hearted")) {

                        innerStatement.execute(male2);
                        innerStatement.execute(address);
                        innerStatement.execute(payment2);

                    } else if (selectedGender.equals("Male") && selectedSubscription.equals("Gym Bro")) {

                        innerStatement.execute(male3);
                        innerStatement.execute(address);
                        innerStatement.execute(payment3);


                    } else {

                        warningTextLabel.setStyle("-fx-text-fill: red");
                        warningTextLabel.setText("Something Went Wrong");

                    }

                    innerStatement.close();

                } else {

                    warningTextLabel.setText("ID Already Exists");
                    warningTextLabel.setStyle("-fx-text-fill: red;" + "-fx-font-weight: bold;" + "-fx-font-size: 14");
                    saveButton.setStyle("-fx-font-family:Arial;");
                    saveButton.setText("Save");


                }

            }

            queryResult.close();
            statement.close();
        }catch (Exception ev) {

            warningTextLabel.setText("An Error Occurred");
            warningTextLabel.setStyle("-fx-text-fill: red;" + "-fx-font-weight: bold;" + "-fx-font-size: 14");
            saveButton.setText("Save");
            saveButton.setStyle("-fx-background-color: rgba(183, 180, 180, 0.82);");


        }
    }

    @FXML
    private Button clearFormButton;

    //This method clears all fields in the new member page
    public void clearForm(ActionEvent e) {

        memberID.clear();
        FnameField.clear();
        LnameField.clear();
        MnameField.clear();
        address.clear();
        PnumberField.clear();
        emailField.clear();
        genderMaleButton.setSelected(false);
        genderFemaleButton.setSelected(false);
        DatePicker.getEditor().clear();
        subType1Button.setSelected(false);
        subType2Button.setSelected(false);
        subType3Button.setSelected(false);
        warningTextLabel.setText("");
        clearFormButton.setStyle("-fx-opacity: 0");
        saveButton.setText("Unsaved");
        saveButton.setStyle("-fx-font-family: Arial");




    }

    //This method makes the 'clear' button visible
    public void makeVisibleClearFormButton(ActionEvent e) {

        clearFormButton.setStyle("-fx-background-color: rgba(183, 180, 180, 0.82);" + "-fx-font-family: Arial;" + "-fx-opacity: 1; " );

    }

}
