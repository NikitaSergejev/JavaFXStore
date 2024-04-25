/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxstore;


import entity.Customer;
import entity.Product;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author pupil
 */
public class JavaFXStore extends Application {
    public static enum roles {ADMINISTRATOR, MANAGER, USER};
    public static Customer currentCustomer;
    public static Product productToEdit;
    public static final int WIDTH = 600;
    public static final int HEIGHT = 400;
    private Stage primaryStage;
    private final EntityManager em;

    public JavaFXStore() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("JavaFXStorePU");
        em = emf.createEntityManager();
    }
   
    
    @Override
    public void start(Stage primaryStage) throws Exception {
        setPrimaryStage(primaryStage);
        this.primaryStage.setTitle("JKTVFXStore");
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/javafxstore/home.fxml"));
        Parent root = loader.load();
        HomeController homeController = loader.getController();
        homeController.setApp(this);  
        // Создаем новую сцену, устанавливаем root в качестве корневого элемента
        Scene scene = new Scene(root, WIDTH, HEIGHT);
        scene.getStylesheets().add(getClass().getResource("/javafxstore/home.css").toExternalForm());
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
    public EntityManager getEntityManager() {
        return em;
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }
    
}
