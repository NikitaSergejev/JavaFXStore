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
import tools.PassEncrypt;

/**
 *
 * @author pupil
 */
public class JavaFXStore extends Application {
    public static enum roles {ADMINISTRATOR, MANAGER, USER};
    public static Customer currentCustomer;
    public static Product productToEdit;
    public static Product productToBuy;
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
        createSuperUser();
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
    private void createSuperUser(){
        try {
            getEntityManager().createQuery("SELECT u FROM Customer u WHERE u.login = :login")
                    .setParameter("login", "admin")
                    .getSingleResult();
        } catch (Exception e) {
             PassEncrypt pe = new PassEncrypt();
             Customer userAdmin = new Customer();
             userAdmin.setFirstname("Nikita");
             userAdmin.setLastname("Sergejev");
             userAdmin.setLogin("admin");
             userAdmin.setMoney(10000);
             userAdmin.setPassword(pe.getEncryptPassword("12345",pe.getSalt()));
             userAdmin.getRoles().add(roles.ADMINISTRATOR.toString());
             userAdmin.getRoles().add(roles.MANAGER.toString());
             userAdmin.getRoles().add(roles.USER.toString());
             getEntityManager().getTransaction().begin();
             getEntityManager().persist(userAdmin);
             getEntityManager().getTransaction().commit();
             
        }
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
    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }
}
