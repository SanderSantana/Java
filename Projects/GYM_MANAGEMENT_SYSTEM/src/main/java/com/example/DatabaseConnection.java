package com.example;

  

import java.sql.Connection;
import java.sql.DriverManager;


//This class establishes the connection with the database
public class DatabaseConnection {

    public Connection databaseLink;

    //This method is used to connect to the database whenever is necessary
    public Connection getConnection() {

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            databaseLink = DriverManager.getConnection("jdbc:mysql://localhost:3306/GMS", "root", "123456");

        }
        catch (Exception e) {

            e.printStackTrace();


        }

        return databaseLink;

    }

}
