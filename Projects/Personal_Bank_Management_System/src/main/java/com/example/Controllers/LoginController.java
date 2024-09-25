package com.example.Controllers;

import com.example.Controllers.Admin.AdminLoginController;
import com.example.Controllers.Client.ClientLoginController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import java.io.IOException;

public class LoginController {

    @FXML
    private Button clientButton, adminButton;

    public void clientButton(ActionEvent e) throws IOException {

        ClientLoginController clientLoginController = new ClientLoginController();
        clientLoginController.clientLoginPage(e);

    }

    public void adminButton(ActionEvent e) throws IOException {

        AdminLoginController adminLoginController = new AdminLoginController();
        adminLoginController.clientLoginPage(e);

    }


}
