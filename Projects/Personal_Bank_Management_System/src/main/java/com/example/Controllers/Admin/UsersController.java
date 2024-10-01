package com.example.Controllers.Admin;

import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class UsersController {

    @FXML
    private TableView<memberSearch> userTable;
    @FXML
    private TableColumn<memberSearch, Integer> idColumn, phoneNumberColumn, outstandingColumn;
    @FXML
    private TableColumn<memberSearch, String> fNameColumn, lNameColumn, genderColumn, emailColumn, dobColumn, subscriptionColumn, addressColumn;
    @FXML
    private TextField searchTextField;

}
