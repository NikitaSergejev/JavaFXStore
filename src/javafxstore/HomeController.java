/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxstore;

import entity.Product;
import java.awt.geom.Rectangle2D;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import products.newproduct.NewproductController;
import products.product.ProductController;

/**
 *
 * @author pupil
 */
public class HomeController implements Initializable {
    
    @FXML
    private VBox vbContent;
    private JavaFXStore app;
    
    
   
     /*public VBox getVbContent() {
        return vbContent;
    }*/

    @Override
    public void initialize(URL location, ResourceBundle resources) {      
         showAboutScene();
        
     
    }

        public void showAboutScene() {         
        try {
             FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/about/about.fxml")); // Убедитесь, что путь указан правильно
            BorderPane bpAboutRoot = loader.load();
            bpAboutRoot.setPrefWidth(JavaFXStore.WIDTH);
            bpAboutRoot.setPrefHeight(JavaFXStore.HEIGHT);
            this.vbContent.getChildren().clear();
            vbContent.getChildren().add(bpAboutRoot);
            } catch (IOException ex) {
            Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, "Нет /about/about.fxml", ex);
            }
     }
    
     @FXML public void mbShowAddNewProduct(){
         this.app.getPrimaryStage().setTitle("JKTVFXStore - добавить новый товар");
         FXMLLoader loader = new FXMLLoader();
         loader.setLocation(getClass().getResource("/products/newproduct/newproduct.fxml"));
         
         try {
         VBox vbNewProductRoot = loader.load();
         vbNewProductRoot.setPrefHeight(JavaFXStore.HEIGHT);
         vbNewProductRoot.setPrefWidth(JavaFXStore.WIDTH);
         NewproductController newproductController = loader.getController();
         newproductController.setEntityManager(getApp().getEntityManager());
         vbContent.getChildren().clear();
         vbContent.getChildren().add(vbNewProductRoot);
         System.out.println("Add product scene is shown successfully!");
         } catch (IOException ex) {
         Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, "Невозможно заргузить newproductRoot", ex);
         }
    }
     
     @FXML public void mbShowListProduct(){
        this.app.getPrimaryStage().setTitle("JKTVFXStore - список товаров");
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/products/listproducts/listproduct.fxml"));
        
        try {
            HBox hbListProductRoot = loader.load();
            hbListProductRoot.getChildren().clear();
            List<Product> listProduct = getApp().getEntityManager().createQuery("SELECT p FROM Product p").getResultList();
            for (int i = 0; i < listProduct.size(); i++) {
                Product product = listProduct.get(i);
                FXMLLoader productLoader = new FXMLLoader();
                productLoader.setLocation(getClass().getResource("/products/product/product.fxml"));
                ImageView ivCoverProductRoot = productLoader.load();
                ProductController productController = productLoader.getController();
                productController.setApp(app);
                ivCoverProductRoot.setCursor(Cursor.OPEN_HAND);
                ivCoverProductRoot.setOnMouseClicked(event -> {
                    if (event.getButton() == MouseButton.PRIMARY) {
                        productController.showProduct(product);
                    }
                });
                Image image = new Image(new ByteArrayInputStream(product.getPhoto()));
                ivCoverProductRoot.setImage(image);
                hbListProductRoot.getChildren().add(ivCoverProductRoot);
            }
            vbContent.getChildren().clear();
            vbContent.getChildren().add(hbListProductRoot);
        } catch (IOException ex) {
            Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, "Невозможно заргузить /products/product/listproduct.fxml", ex);
        }
     }
     
     
     
     
    public JavaFXStore getApp() {
        return app;
    }
     void setApp(JavaFXStore app) {
        this.app = app;
       // this.vbContent = new VBox(); // инициализируем vbContent
    }
}
