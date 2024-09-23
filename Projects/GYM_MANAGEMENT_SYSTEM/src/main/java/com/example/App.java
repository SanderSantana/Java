package com.example;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class  App extends Application {


    @Override
    //This method creates the first and only stage of the application.
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/Inventory/FXML/Login.fxml")); //Loads fxml file into the stage
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setTitle("WhisperWeight Gym ApplicSanation");
        Scene scene = new Scene(root, 578, 400);
        Image icon = new Image("/Inventory/Images/wind (1).png");
        stage.getIcons().add(icon);
        stage.setScene(scene);
        stage.show();

    }
}
