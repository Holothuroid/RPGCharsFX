/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.pomp31us.rpgcharsfx;

import java.util.Optional;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

/**
 *
 * @author 1of3
 */
public class RPGCharsFX extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("MainFrame.fxml"));
        
        Scene scene = new Scene(root);
        
        stage.setScene(scene);
        stage.setTitle("RPGChars by 1of3");
        stage.show();
        
        stage.setOnCloseRequest(e -> {
        e.consume();
        
        Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION,"Save campaign first?",ButtonType.YES,ButtonType.NO,ButtonType.CANCEL);
        Optional<ButtonType> answer = confirmation.showAndWait();
        if (answer.get() == ButtonType.YES) {
            System.out.println("Hier fehlt Code!!!");
        }
        if (answer.get() == ButtonType.NO){
            stage.close();
        }
        
        
        
        });
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
