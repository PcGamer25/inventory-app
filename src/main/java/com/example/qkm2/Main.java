package com.example.qkm2;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * @author David Huang
 */
public class Main extends Application {

    /**
     * This method loads and sets up the main page
     *
     * @param stage
     * @throws Exception
     */
    @Override
    public void start(Stage stage) throws Exception {

        Parent root = FXMLLoader.load(getClass().getResource("Main.fxml"));

        stage.setTitle("Inventory Manager");
        stage.setScene(new Scene(root));
        stage.setResizable(false);
        stage.show();
    }

    /**
     * This method launches the application
     *
     * @param args
     */
    public static void main(String[] args) {

        launch(args);
    }
}
