/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package purchase;

import entity.Customer;
import entity.Product;
import entity.Purchase;
import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javax.persistence.EntityManager;

/**
 * FXML Controller class
 *
 * @author nikit
 */
public class PurchaseController implements Initializable {
    
    private Purchase purchase;
    @FXML private Label lbInfo;
    @FXML private TextField tfModel;
    @FXML private TextField tfQuantity;
    @FXML private TextField tfType;
    @FXML private TextField tfBrand;
    @FXML private TextField tfPrice;
    @FXML private TextField tfQuantityToBuy;
    @FXML private Button btUpdateProduct;
    private EntityManager em;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        lbInfo.setText("");
        tfModel.setText(javafxstore.JavaFXStore.productToBuy.getModel());
        tfType.setText(javafxstore.JavaFXStore.productToBuy.getType());
        tfBrand.setText(javafxstore.JavaFXStore.productToEdit.getBrand());
        tfPrice.setText(String.valueOf(javafxstore.JavaFXStore.productToBuy.getPrice()));
        tfQuantity.setText(String.valueOf(javafxstore.JavaFXStore.productToBuy.getQuantity())); 
        tfModel.setDisable(true);
        tfType.setDisable(true);
        tfBrand.setDisable(true);
        tfPrice.setDisable(true);
        tfQuantity.setDisable(true);
        btUpdateProduct.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                clickBuyProduct();
            }
        });
        btUpdateProduct.setOnMouseClicked(event -> {
           if (event.getButton() == MouseButton.PRIMARY) {
               clickBuyProduct();
           }
       });
    }    
    
    public void clickBuyProduct(){
        Product product = em.find(Product.class,javafxstore.JavaFXStore.productToBuy.getId());
        Customer customer = em.find(Customer.class,javafxstore.JavaFXStore.currentCustomer.getId());
        if (javafxstore.JavaFXStore.productToBuy!=null
                &&javafxstore.JavaFXStore.currentCustomer!=null){
            int quantityToBuy=(Integer.parseInt(tfQuantityToBuy.getText()));
            if (product.getQuantity() >= quantityToBuy) { 
                Purchase purchase = new Purchase();
                purchase.setProduct(product);
                purchase.setCustomer(customer);
                purchase.setQuantity(quantityToBuy);
                purchase.setDate(new Date()); 
                try{
                    em.getTransaction().begin();
                    em.merge(purchase);
                    em.getTransaction().commit();
                    product.setQuantity(product.getQuantity()-quantityToBuy); 
                    em.getTransaction().begin();
                    em.merge(product);
                    em.getTransaction().commit();
                    
                    lbInfo.setText("Товар успешно куплен");
                    lbInfo.getStyleClass().remove("error-text");
                    lbInfo.getStyleClass().add("info-text");
                }catch(Exception e){                   
                    if (!em.getTransaction().isActive()) {
                        em.getTransaction().begin();
                    }
                    em.getTransaction().rollback();

                    lbInfo.setText("Товар купить не удалось");
                    lbInfo.getStyleClass().remove("info-text");
                    lbInfo.getStyleClass().add("error-text");
                }    
            }
        }
       
    }

    public void setEntityManager(EntityManager entityManager) {
        this.em = entityManager;
    }
    
}
