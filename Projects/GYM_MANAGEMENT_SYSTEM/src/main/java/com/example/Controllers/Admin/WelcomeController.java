package com.example.Controllers.Admin;


import com.example.App;
import com.example.Controllers.LoginController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.stage.StageStyle;

import java.util.Optional;

import static javafx.fxml.FXMLLoader.load;

public class WelcomeController {

    @FXML
    private Stage stage;
    @FXML
    private Scene scene;

    //This method loads the welcome page
    public void WelcomePage (ActionEvent e) throws Exception {

        Parent root = FXMLLoader.load(getClass().getResource("/Inventory/FXML/Admin/welcome.fxml"));
        stage = (Stage)((Node)e.getSource()).getScene().getWindow();
        scene = new Scene(root, 953, 589);
        stage.setScene(scene);
        stage.show();

    }

    @FXML
    private Button exitButton, AddMembersButton, updateDeleteButton, paymentButton, membersButton, logOutButton;

    //this method closes the program
    public void exitButtonOnAction (ActionEvent e) {

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

    //This method calls the newMember method from their class. When called it shows the new member page
    public void newMemberButtonOnAction(ActionEvent e) throws Exception {

        try {
            NewMemberController newMemberPage = new NewMemberController();
            newMemberPage.newMember(e);
        }
        catch (Exception ev) {

            ev.printStackTrace();

        }

    }

    //This method calls the update/delete method from their class. When called it shows the edit page
    public void updateDeleteButtonOnAction(ActionEvent e) throws Exception {

        updateDeleteMemberController updateDeleteMember = new updateDeleteMemberController();
        updateDeleteMember.updateDeleteMember(e);



    }

    //This method calls the payment method from their class. When called it shows the payment page
    public void paymentButtonOnAction(ActionEvent e) throws Exception {

        paymentController payment = new paymentController();
        payment.payment(e);

    }

    //This method calls the members method from their class. When called it shows the members page
    public void membersButtonOnAction(ActionEvent e) throws  Exception {

        membersController members = new membersController();
        members.members(e);

    }

    //This method calls the logOut method from their class. When called it shows sends user back to the logOut page
    public void logOutButtonOnAction(ActionEvent e) throws Exception {

        LoginController loginController = new LoginController();

        loginController.backToLogInPage(e);

    }





}



