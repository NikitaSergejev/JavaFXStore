/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sale;

import entity.Sale;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javax.persistence.EntityManager;
import javax.persistence.Query;

public class SaleController implements Initializable {

    @FXML private Label lbInfo;
    @FXML private Button btAllSale;
    @FXML private Button btPastSale;
    @FXML private Button btActiveSale;
    @FXML private Button btFutureSale;
    @FXML private ListView<String> saleListView;

    private EntityManager em;

    public EntityManager getEntityManager() {
        return em;
    }
    
    public void setEntityManager(EntityManager em) {
        this.em = em;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Проинициализируем saleListView
        saleListView = new ListView<>();
         // Устанавливаем cellFactory для ListView
        saleListView.setCellFactory(param -> new ListCell<String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item);
                }
            }
        });
        
        // Обновляем список акций
        refreshSalesList();

        // Добавляем обработчик событий для выбора элемента из ListView
        saleListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            // Получаем выбранную акцию из базы данных
            Sale selectedSale = getSaleByName(newValue);

            // Отображаем информацию о выбранной акции
            if (selectedSale != null) {
                lbInfo.setText("Выбранная акция: " + selectedSale.getName() +
                        "\nНачало акции: " + selectedSale.getDateStart() +
                        "\nКонец акции: " + selectedSale.getDateEnd());
            } else {
                lbInfo.setText("Не удалось найти информацию о выбранной акции.");
            }
        });
    }
    
    private void refreshSalesList() {
        // Очищаем ListView перед добавлением новых элементов
        saleListView.getItems().clear();

        // Получаем список всех акций из базы данных
        List<Sale> sales = getAllSalesFromDatabase();

        // Отображаем список акций в ListView
        for (Sale sale : sales) {
            saleListView.getItems().add(sale.getName());
        }
    }
    
   /**
    * Получает список всех акций из базы данных.
    * @return Список всех акций.
    */
   private List<Sale> getAllSalesFromDatabase() {
       // Создание запроса к базе данных для выбора всех акций
        Query query = em.createQuery("SELECT s FROM Sale s");
       // Возвращаем список результатов запроса
       return query.getResultList();
   }

   /**
    * Получает акцию по ее имени из базы данных.
    * @param name Имя акции.
    * @return Объект акции, если найден, иначе null.
    */
   private Sale getSaleByName(String name) {
       // Создание запроса к базе данных для выбора акции по имени
       Query query = em.createQuery("SELECT s FROM Sale s WHERE s.name = :name");
       // Установка параметра имени в запросе
       query.setParameter("name", name);
       // Возвращаем результат запроса, ожидая один результат или ничего
       return (Sale) query.getSingleResult();
   }
   

  
}
