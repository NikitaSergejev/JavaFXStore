/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxstore;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.stage.Stage;

/**
 *
 * @author pupil
 */
public class HomeController implements Initializable {
    
    @FXML
    private Label label;

    private JavaFXStore app;
   
    
    

    void setApp(JavaFXStore app) {
        this.app = app;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
    }
    
}
