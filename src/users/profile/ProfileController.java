/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package users.profile;

import entity.Customer;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javax.persistence.EntityManager;
import tools.PassEncrypt;

/**
 * FXML Controller class
 *
 * @author nikit
 */
public class ProfileController implements Initializable {
    
    @FXML private Label lbInfo;
    @FXML private TextField tfFirstname;
    @FXML private TextField tfLastname;
    @FXML private TextField tfLogin;
    @FXML private TextField tfPassword;
    @FXML private TextField tfMoney;
    @FXML private Button btUpdateProfile;
    private EntityManager em;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        lbInfo.setText("");
        tfFirstname.setText(javafxstore.JavaFXStore.currentCustomer.getFirstname());
        tfLastname.setText(javafxstore.JavaFXStore.currentCustomer.getLastname());
        tfLogin.setText(javafxstore.JavaFXStore.currentCustomer.getLogin());
        tfMoney.setText(String.valueOf(javafxstore.JavaFXStore.currentCustomer.getMoney()));
        tfPassword.setText("");
        tfLogin.setDisable(true);
        btUpdateProfile.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
               clickUpdateProfile();
            }
        });
        btUpdateProfile.setOnMouseClicked(event -> {
           if (event.getButton() == MouseButton.PRIMARY) {
               clickUpdateProfile();
           }
       });
    }  
    
     public void setEntityManager(EntityManager entityManager) {
        this.em = entityManager;
    }

    public void clickUpdateProfile() {
        Customer customer = em.find(Customer.class,javafxstore.JavaFXStore.currentCustomer.getId());
        customer.setFirstname(tfLastname.getText());
        customer.setLastname(tfFirstname.getText());
        int money = Integer.parseInt(tfMoney.getText());
        customer.setMoney(money);
        if(!tfPassword.getText().isEmpty()){
            PassEncrypt pe = new PassEncrypt();
            customer.setPassword(pe.getEncryptPassword(tfPassword.getText(),pe.getSalt()));
        }
        try {
            em.getTransaction().begin();
            em.merge(customer);
            em.getTransaction().commit();
            javafxstore.JavaFXStore.currentCustomer=customer;
            tfFirstname.setText(javafxstore.JavaFXStore.currentCustomer.getFirstname());
            tfLastname.setText(javafxstore.JavaFXStore.currentCustomer.getLastname());
            tfLogin.setText(javafxstore.JavaFXStore.currentCustomer.getLogin());
            tfMoney.setText(String.valueOf(javafxstore.JavaFXStore.currentCustomer.getMoney()));
            tfPassword.setText("");                            
            lbInfo.setText("Профиль пользователя изменен");           
        } catch (Exception e) {                     
            lbInfo.setText("Профиль изменить не удалось");           
            
        }
    }
    
}
