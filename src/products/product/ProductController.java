/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package products.product;

import entity.Customer;
import entity.Product;
import java.io.ByteArrayInputStream;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafxstore.HomeController;
import javafxstore.JavaFXStore;

/**
 * FXML Controller class
 *
 * @author nikit
 */
public class ProductController implements Initializable {
    private JavaFXStore app;
    private Stage productWindow;
    private HomeController homeController;
    private Customer currentCustomer;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       
    }    
     public void showProduct(Product product) {
        this.productWindow = new Stage();
        productWindow.initModality(Modality.WINDOW_MODAL);
        productWindow.initOwner(getApp().getPrimaryStage());
        Image image = new Image(new ByteArrayInputStream(product.getPhoto()));
        ImageView ivPhoto = new ImageView(image);
        ivPhoto.setId("big_product_photo");
        VBox vbProduct = new VBox();
        vbProduct.setMinWidth(Double.MAX_VALUE);
        vbProduct.setAlignment(Pos.CENTER);
        vbProduct.getChildren().add(ivPhoto);
        HBox hbButtons = new HBox();
        hbButtons.setAlignment(Pos.CENTER);
        hbButtons.setSpacing(10);
        hbButtons.setPadding(new Insets(20,20,20,20));
        Button btEditProduct = new Button("Редактировать");
        Button btBuy = new Button("Купить");
        // Создаем элементы управления для отображения информации о товаре
        Label lbPrice = new Label("Цена: " + product.getPrice()+"€");
        Label lbQuantity = new Label("Количество: " + product.getQuantity());
        Label lbModel = new Label("Модель: " + product.getModel());
        Label lbBrand = new Label("Бренд: " + product.getBrand());
        // Добавляем элементы управления в контейнер
        vbProduct.getChildren().addAll(lbPrice, lbQuantity, lbModel, lbBrand);
       // Обработчик события для Button
       btBuy.setOnKeyPressed(event -> {
       if (event.getCode() == KeyCode.ENTER) {
            productWindow.close();
            JavaFXStore.productToBuy = product;
            // Устанавливаем текущего пользователя
            homeController.getCurrentCustomer();
            setHomeController(homeController);
            homeController.setApp(app);
            homeController.mbShowPucrhaseProductForm();
        }
       });
       btBuy.setOnMouseClicked(event -> {
       if (event.getButton() == MouseButton.PRIMARY) {
            productWindow.close();
            JavaFXStore.productToBuy = product;
            // Устанавливаем текущего пользователя
            homeController.getCurrentCustomer();
            setHomeController(homeController);
            homeController.setApp(app);
            homeController.mbShowPucrhaseProductForm();
       }
       });
       
       btEditProduct.setOnKeyPressed(event -> {
       if (event.getCode() == KeyCode.ENTER) {
           productWindow.close();
            JavaFXStore.productToEdit = product;
            setHomeController(homeController);
            homeController.setApp(app); 
            homeController.mbShowEditProductForm();
       }
       });
       btEditProduct.setOnMouseClicked(event -> {
       if (event.getButton() == MouseButton.PRIMARY) {
            productWindow.close();
            JavaFXStore.productToEdit = product;
            setHomeController(homeController);
            homeController.setApp(app); 
            homeController.mbShowEditProductForm();
}

       });
        hbButtons.getChildren().addAll(btEditProduct,btBuy);
        vbProduct.getChildren().add(hbButtons);
        Scene scene = new Scene(vbProduct,550,700);
        scene.getStylesheets().add(getClass().getResource("/products/product/product.css").toExternalForm());
        productWindow.setScene(scene);
        productWindow.show();
        
    }
    public void setHomeController(HomeController homeController) {
        this.homeController = homeController;
    }
     public void setCurrentCustomer(Customer customer) {
        this.currentCustomer = customer;
    }
    public JavaFXStore getApp() {
        return app;
    }
    public void setApp(JavaFXStore app) {
        this.app = app;
    }
    

    
    
}
