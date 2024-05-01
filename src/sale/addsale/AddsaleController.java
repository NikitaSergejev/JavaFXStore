/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sale.addsale;

import entity.Sale;
import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javax.persistence.EntityManager;

/**
 * FXML Controller class
 *
 * @author nikit
 */
public class AddsaleController implements Initializable {
    
    private EntityManager em;
    @FXML Label lbInfo;
    @FXML TextField tfSaleName;
    @FXML DatePicker dpStartDate;
    @FXML DatePicker dpEndDate;
    @FXML Button btAddSale;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
         // Обработчик события для Button
        btAddSale.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
               btAddSale.fire();
            }
        });
    }    
    public void clickAddNewSale(){
      // Проверяем, был ли установлен EntityManager
        if (em == null) {
            lbInfo.setText("EntityManager не был установлен");
            return;
        }
        Sale sale = new Sale();
        sale.setName(tfSaleName.getText());
        // Преобразование LocalDate в Date для начала акции
        LocalDate localDate = dpStartDate.getValue();
        Date date = Date.valueOf(localDate);
        sale.setDateStart(date); 
        // Преобразование LocalDate в Date для окончания акции
        LocalDate localEndDate = dpEndDate.getValue();
        Date endDate = Date.valueOf(localEndDate);
        sale.setDateEnd(endDate);
        if (tfSaleName!=null) {
            try {
            em.getTransaction().begin();
            em.persist(sale);
            em.getTransaction().commit();           
            lbInfo.setText(String.format("Скидочная компания добавлена"));
            } catch (Exception e) {
                lbInfo.setText("Не удалось добавить");
                System.out.println("Записать компанию в базу не удалось: "+e);
            }
        tfSaleName.setText(""); 
        }else{
            lbInfo.setText("Заполните поле названия");
        }
        
    }
    
    
    public EntityManager getEntityManager() {
        return em;
    }

    public void setEntityManager(EntityManager em) {
        this.em = em;
    }
    
}
