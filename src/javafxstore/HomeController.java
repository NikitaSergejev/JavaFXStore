/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxstore;

import entity.Customer;
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
import products.editproduct.EditproductController;
import products.newproduct.NewproductController;
import products.product.ProductController;
import purchase.PurchaseController;
import sale.SaleController;
import sale.addsale.AddsaleController;
import users.login.LoginController;
import users.newuser.NewuserController;
import users.profile.ProfileController;

/**
 *
 * @author pupil
 */
public class HomeController implements Initializable {
    
    @FXML
    private VBox vbContent;
    private JavaFXStore app;
    private String infoMessage;
    
   
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
                productController.setHomeController(this);
                productController.setApp(app);
                ivCoverProductRoot.setCursor(Cursor.OPEN_HAND);
                ivCoverProductRoot.setOnMouseClicked(event -> {
                    if (event.getButton() == MouseButton.PRIMARY) {
                        productController.setCurrentCustomer(JavaFXStore.currentCustomer);
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
     @FXML public void mbShowAddNewUser(){
        this.app.getPrimaryStage().setTitle("JKTVFXLibrary-регистрация пользователя");
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/users/newuser/newuser.fxml"));
        
        try {
            VBox vbNewUserRoot = loader.load();
            vbNewUserRoot.setPrefHeight(JavaFXStore.HEIGHT);
            vbNewUserRoot.setPrefWidth(JavaFXStore.WIDTH);
            NewuserController newuserController = loader.getController();
            newuserController.setEntityManager(getApp().getEntityManager());
            vbContent.getChildren().clear();
            vbContent.getChildren().add(vbNewUserRoot);
        } catch (IOException ex) {
            Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, "Невозможно заргузить vbNewUserRoot", ex);
        }
    }
     @FXML public void mbShowLonginForm(){
        this.app.getPrimaryStage().setTitle("JKTVFXLibrary-вход пользователя");
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/users/login/login.fxml"));
        
        try {
            VBox vbLoginRoot = loader.load();
            vbLoginRoot.setPrefHeight(JavaFXStore.HEIGHT);
            vbLoginRoot.setPrefWidth(JavaFXStore.WIDTH);
            LoginController loginController = loader.getController();
            loginController.setEntityManager(getApp().getEntityManager());
            loginController.setInfo(this.infoMessage);
            vbContent.getChildren().clear();
            vbContent.getChildren().add(vbLoginRoot);
        } catch (IOException ex) {
            Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, "Невозможно заргузить vbLoginRoot", ex);
        }
    }
    public void mbShowProfileForm(){
        if(javafxstore.JavaFXStore.currentCustomer == null){
            this.infoMessage="Авторизуйтесь";
            this.mbShowLonginForm();
            return;
        }
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/users/profile/profile.fxml"));
            VBox vbProfileRoot = loader.load();
            ProfileController profileController = loader.getController();
            profileController.setEntityManager(getApp().getEntityManager());
            vbProfileRoot.setPrefWidth(JavaFXStore.WIDTH);
            vbProfileRoot.setPrefHeight(JavaFXStore.HEIGHT);
            this.vbContent.getChildren().clear();
            vbContent.getChildren().add(vbProfileRoot);
        } catch (IOException ex) {
            Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, "Нет /users/profile/profile.fxml", ex);
        }
    } 
    public void mbShowSaleList(){
        /* if(javafxstore.JavaFXStore.currentCustomer == null){
        this.infoMessage="Авторизуйтесь";
        this.mbShowLonginForm();
        return;
        }*/
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/sale/sale.fxml"));
            VBox vbSaleRoot = loader.load();
            SaleController saleController = loader.getController();
            saleController.setEntityManager(getApp().getEntityManager());
            vbSaleRoot.setPrefWidth(JavaFXStore.WIDTH);
            vbSaleRoot.setPrefHeight(JavaFXStore.HEIGHT);
            this.vbContent.getChildren().clear();
            vbContent.getChildren().add(vbSaleRoot);
        } catch (IOException ex) {
            Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, "Нет /sale/sale.fxml", ex);
        }
    } 
    public void mbShowEditProductForm(){
        if(javafxstore.JavaFXStore.currentCustomer == null){
            this.infoMessage="Авторизуйтесь";
            this.mbShowLonginForm();
            return;
        }
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/products/editproduct/editproduct.fxml"));
            VBox vbEditProductRoot = loader.load();
            EditproductController editproductController = loader.getController();
            editproductController.setEntityManager(getApp().getEntityManager());
            vbEditProductRoot.setPrefWidth(JavaFXStore.WIDTH);
            vbEditProductRoot.setPrefHeight(JavaFXStore.HEIGHT);
            this.vbContent.getChildren().clear();
            vbContent.getChildren().add(vbEditProductRoot);
        } catch (IOException ex) {
            Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, "Нет /products/editproduct/editproduct.fxml", ex);
        }
    }
    public void mbShowPucrhaseProductForm(){
        if(javafxstore.JavaFXStore.currentCustomer == null){
            this.infoMessage="Авторизуйтесь";
            this.mbShowLonginForm();
            return;
        }
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/purchase/purchase.fxml"));
            VBox vbPurchaseProductRoot = loader.load();
            PurchaseController purchaseController = loader.getController();
            purchaseController.setEntityManager(getApp().getEntityManager());
            vbPurchaseProductRoot.setPrefWidth(JavaFXStore.WIDTH);
            vbPurchaseProductRoot.setPrefHeight(JavaFXStore.HEIGHT);
            this.vbContent.getChildren().clear();
            vbContent.getChildren().add(vbPurchaseProductRoot);
        } catch (IOException ex) {
            Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, "Нет /purchase/purchase.fxml", ex);
        }
    }
    public void mbShowAddSaleForm(){
        if(javafxstore.JavaFXStore.currentCustomer == null){
            this.infoMessage="Авторизуйтесь";
            this.mbShowLonginForm();
            return;
        }
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/sale/addsale/addsale.fxml"));
            VBox vbAddSaleRoot = loader.load();
            AddsaleController addSaleController = loader.getController();
            addSaleController.setEntityManager(getApp().getEntityManager());
            vbAddSaleRoot.setPrefWidth(JavaFXStore.WIDTH);
            vbAddSaleRoot.setPrefHeight(JavaFXStore.HEIGHT);
            this.vbContent.getChildren().clear();
            vbContent.getChildren().add(vbAddSaleRoot);
        } catch (IOException ex) {
            Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, "Нет /sale/addsale/addsale.fxml", ex);
        }
    }
    public Customer getCurrentCustomer() {
        return JavaFXStore.currentCustomer;
    }
    public JavaFXStore getApp() {
        return app;
    }
     public void setApp(JavaFXStore app) {
        this.app = app;
       // this.vbContent = new VBox(); // инициализируем vbContent
    }
}
