/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxstore;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import products.newproduct.NewproductController;

/**
 *
 * @author pupil
 */
public class HomeController implements Initializable {
    
    @FXML
    private Label label;
    private JavaFXStore app;
    private VBox vbContent;
    
    

    void setApp(JavaFXStore app) {
        this.app = app;
       // this.vbContent = new VBox(); // инициализируем vbContent
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //showAboutScene();
        this.vbContent = new VBox(); // инициализируем vbContent
    }

    public void showAboutScene(){
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/about/about.fxml"));
            BorderPane bpAboutRoot = loader.load();
            bpAboutRoot.setPrefWidth(JavaFXStore.WIDTH);
            bpAboutRoot.setPrefHeight(JavaFXStore.HEIGHT);
            this.vbContent.getChildren().clear();
            vbContent.getChildren().add(bpAboutRoot);
            System.out.println("About scene is shown successfully!");
        } catch (IOException ex) {
            Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, "Нет /about/about.fxml", ex);
        }
    }
    
     @FXML public void mbShowAddNewProduct(){
         /*if(javafxlibrary.JavaFxLibrary.currentUser == null){
         this.infoMessage="Для этого действия вы должны быть менеджером. Авторизуйтесь";
         this.mbShowLonginForm();
         return;
         }
         if(!javafxlibrary.JavaFxLibrary.currentUser.getRoles()
         .contains(javafxlibrary.JavaFxLibrary.roles.MANAGER.toString())){
         this.infoMessage="Для этого действия вы должны быть менеджером. Авторизуйтесь";
         this.mbShowLonginForm();
         return;
         }*/
        this.app.getPrimaryStage().setTitle("JKTVFXStore-добавить новую книгу");
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
        } catch (IOException ex) {
            Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, "Невозможно заргузить newbookRoot", ex);
        }
    }
     
    public JavaFXStore getApp() {
        return app;
    }
    
}
