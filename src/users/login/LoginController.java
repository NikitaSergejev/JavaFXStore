/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package users.login;

import entity.Customer;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javax.persistence.EntityManager;
import tools.PassEncrypt;

/**
 * FXML Controller class
 *
 * @author nikit
 */
public class LoginController implements Initializable {
    
    private EntityManager em;
    @FXML private TextField tfLogin;
    @FXML private PasswordField pfPassword;
    @FXML private Label lbInfo;
    @FXML private Button btLogin;
    
    @FXML void clickLogin(){
        PassEncrypt pe = new PassEncrypt();
        try {
            Customer customer = (Customer) em.createQuery("SELECT c FROM Customer c WHERE c.login = :login")
                    .setParameter("login", tfLogin.getText())
                    .getSingleResult();
            if(customer.getPassword().equals(pe.getEncryptPassword(pfPassword.getText(), pe.getSalt()))){
                if(lbInfo.getStyleClass().contains("error-text")){
                    lbInfo.getStyleClass().remove("error-text");
                }
                if(!lbInfo.getStyleClass().contains("info-text")){
                    lbInfo.getStyleClass().add("info-text");
                }
                lbInfo.setText(String.format("Привет %s %s, добро пожаловать!", customer.getFirstname(), customer.getLastname()));
                javafxstore.JavaFXStore.currentCustomer = customer;
                tfLogin.setText("");
                pfPassword.setText("");
            }else{
                throw new Exception();
            }
            
        } catch (Exception e) {
            if(!lbInfo.getStyleClass().contains("error-text")){
                    lbInfo.getStyleClass().add("error-text");
                }
            if(!lbInfo.getStyleClass().contains("info-text")){
                lbInfo.getStyleClass().remove("info-text");
            }
            lbInfo.setText("Нет такого кользователея или неправильный пароль");
            tfLogin.setText("");
            pfPassword.setText("");
        }
    }
    
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
         // Обработчик события для TextField
        pfPassword.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                btLogin.fire();
            }
        });

        // Обработчик события для Button
        btLogin.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
               btLogin.fire();
            }
        });
    }    
    
    
    
    public void setEntityManager(EntityManager entityManager) {
        this.em = entityManager;
    }

    public EntityManager getEntityManager() {
        return this.em;
    }
    public void setInfo(String message){
        this.lbInfo.setText(message);
    }
}
