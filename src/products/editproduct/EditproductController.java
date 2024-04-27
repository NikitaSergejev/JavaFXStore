/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package products.editproduct;

import entity.Product;
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

/**
 * FXML Controller class
 *
 * @author nikit
 */
public class EditproductController implements Initializable {
    
    
    @FXML private Label lbInfo;
    @FXML private TextField tfModel;
    @FXML private TextField tfQuantity;
    @FXML private TextField tfType;
    @FXML private TextField tfBrand;
    @FXML private TextField tfPrice;
    @FXML private Button btUpdateProduct;
    private EntityManager em;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        lbInfo.setText("");
        tfModel.setText(javafxstore.JavaFXStore.productToEdit.getModel());
        tfType.setText(javafxstore.JavaFXStore.productToEdit.getType());
        tfBrand.setText(javafxstore.JavaFXStore.productToEdit.getBrand());
        tfPrice.setText(String.valueOf(javafxstore.JavaFXStore.productToEdit.getPrice()));
        tfQuantity.setText(String.valueOf(javafxstore.JavaFXStore.productToEdit.getQuantity())); 
        btUpdateProduct.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                clickUpdateProduct();
            }
        });
        btUpdateProduct.setOnMouseClicked(event -> {
           if (event.getButton() == MouseButton.PRIMARY) {
               clickUpdateProduct();
           }
       });
    }
    
    public void clickUpdateProduct(){
        Product product = em.find(Product.class,javafxstore.JavaFXStore.productToEdit.getId());   
        product.setModel(tfModel.getText());
        product.setBrand(tfBrand.getText());
        product.setType(tfType.getText());
        int quantity = Integer.parseInt(tfQuantity.getText());
        product.setQuantity(quantity);
        int price = Integer.parseInt(tfPrice.getText());
        product.setPrice(price); 
     
        try{
           em.getTransaction().begin();
           em.merge(product);
           em.getTransaction().commit();
           javafxstore.JavaFXStore.productToEdit=product;
           tfType.setText(javafxstore.JavaFXStore.productToEdit.getType());
           tfBrand.setText(javafxstore.JavaFXStore.productToEdit.getBrand());
           tfModel.setText(javafxstore.JavaFXStore.productToEdit.getModel());
           tfPrice.setText(String.valueOf(javafxstore.JavaFXStore.productToEdit.getPrice()));
           tfQuantity.setText(String.valueOf(javafxstore.JavaFXStore.productToEdit.getQuantity())); 
           if(lbInfo.getStyleClass().contains("error-text")){
                       lbInfo.getStyleClass().remove("error-text");
                   }
                   if(!lbInfo.getStyleClass().contains("info-text")){
                       lbInfo.getStyleClass().add("info-text");
                   }
               lbInfo.setText("Информация обновлена");
               lbInfo.getStyleClass().add("info-text");

        }catch (Exception e) {
               if(!lbInfo.getStyleClass().contains("error-text")){
                       lbInfo.getStyleClass().add("error-text");
                   }
               if(!lbInfo.getStyleClass().contains("info-text")){
                   lbInfo.getStyleClass().remove("info-text");
               }
               lbInfo.setText("Информацию изменить не удалось");
               lbInfo.getStyleClass().add("error-text");

           }
    }
    
    public void setEntityManager(EntityManager entityManager) {
        this.em = entityManager;
    }
}
