/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxstore;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author pupil
 */
public class JavaFXStore extends Application {
    
    @FXML
    private Stage primaryStage;
    
    @Override
    public void start(Stage stage) throws Exception {
        setPrimaryStage(primaryStage);
        this.primaryStage.setTitle("JKTVFXLibrary");
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("home.fxml"));
        Parent root = loader.load();
        HomeController homeController = loader.getController();
        homeController.setApp(this);
        homeController.showAboutScene();
        Scene scene = new Scene(root, WIDTH,HEIGHT);
        //Подключаем каскадную таблицу стилей из пакета javafxlibrary
        //scene.getStylesheets().add(getClass().getResource("home.css").toExternalForm());
        //scene.getStylesheets().add(getClass().getResource("/javafxlibrary/home.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
