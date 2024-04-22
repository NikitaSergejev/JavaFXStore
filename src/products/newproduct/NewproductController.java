/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package products.newproduct;

import entity.Product;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javax.imageio.ImageIO;
import javax.persistence.EntityManager;
import org.imgscalr.Scalr;

/**
 * FXML Controller class
 *
 * @author nikit
 */
public class NewproductController implements Initializable {
    
    private EntityManager em;
    private File selectedFile;
    @FXML private TextField tfType;
    @FXML private TextField tfBrand;
    @FXML private TextField tfModel;
    @FXML private TextField tfPrice;
    @FXML private TextField tfQuantity;
    @FXML private Button btSelectPhoto;
    @FXML private Label lbInfo;
    @FXML private Button btAddNewProduct;
     

    public NewproductController() {
    }
    
      @Override
    public void initialize(URL url, ResourceBundle rb) {
        tfQuantity.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                lbInfo.setText("");
            } 
        });
        
        // Обработчик события для Button
        btAddNewProduct.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
               btAddNewProduct.fire();
            }
        });
    }
    @FXML
    public void selectCover(){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Выбор изображения");
        selectedFile = fileChooser.showOpenDialog(new Stage());
        btSelectPhoto.setText("Выбран файл: "+ selectedFile.getName());
        btSelectPhoto.setDisable(true);
    }
    @FXML
    public void clickAddNewProduct(){
        // Проверяем, был ли установлен EntityManager
        if (em == null) {
            lbInfo.setText("EntityManager не был установлен");
            return;
        }
        Product product = new Product();
        product.setType(tfType.getText());
        product.setBrand(tfBrand.getText());
        product.setModel(tfModel.getText()); 
        
        // Преобразование строковых значений в целочисленный формат
        int price = Integer.parseInt(tfPrice.getText());
        int quantity = Integer.parseInt(tfQuantity.getText());

        product.setPrice(price);
        product.setQuantity(quantity); 
        try {
            //Добавляем в Library проекта библиотеку imgscalr-lib.jar (находим в Интернете)
            // Получаем нужный формат изображения из selectedFile
            // Преобразуем размер изображения к ширине в 400 px 
            // Преобразуем тип в byte[] и инициируем book.setCover(...);
            BufferedImage biBookCover = ImageIO.read(selectedFile);
            BufferedImage biScaledBookCover = Scalr.resize(biBookCover, Scalr.Mode.FIT_TO_WIDTH,400);
            ByteArrayOutputStream baos = new ByteArrayOutputStream ();
            ImageIO.write (biScaledBookCover, "png", baos);
            product.setPhoto(baos.toByteArray());
            em.getTransaction().begin();
            em.persist(product);
            em.getTransaction().commit();
            //сообщить об успешном добавлении книги
            lbInfo.setText("Товар успешно добавлен");
        } catch (IOException ex) {
            lbInfo.setText("Товар не удалось добавить");
            Logger.getLogger(NewproductController.class.getName()).log(Level.SEVERE, null, ex);
        }
        btSelectPhoto.setText("Выбрать обложку");
        btSelectPhoto.setDisable(false);
        tfType.setText("");
        tfBrand.setText("");
        tfModel.setText(""); 
        tfPrice.setText("");
        tfQuantity.setText("");
        
    }

    public EntityManager getEntityManager() {
        return em;
    }

    public void setEntityManager(EntityManager em) {
        this.em = em;
    }
}
