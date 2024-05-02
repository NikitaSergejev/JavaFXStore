/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sale;

import entity.Sale;
import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 * FXML Controller class
 *
 * @author nikit
 */
public class SaleController implements Initializable {
    
    @FXML Label lbInfo;
    @FXML Button btAllSale;
    @FXML Button btPastSale;
    @FXML Button btActiveSale;
    @FXML Button btFutureSale;
    //@FXML ListView saleListView;
    private ListView<String> saleListView;
    private EntityManager em;
     // Перечисление для типов фильтрации
    private enum FilterType {
        ALL, ACTIVE, PAST, FUTURE
    }
     public EntityManager getEntityManager() {
        return em;
    }

    public void setEntityManager(EntityManager em) {
        this.em = em;
    }
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
         // Проинициализируем saleListView
        saleListView = new ListView<>();
        // Получаем список акций из базы данных с учетом выбранного фильтра
    List<Sale> sales = getSalesFromDatabase(FilterType.ALL); // По умолчанию показываем все акции

    // Отображаем список акций в ListView
    for (Sale sale : sales) {
        saleListView.getItems().add(sale.getName());
    }
    
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
    // Метод для получения выбранной акции по её имени
    private Sale getSaleByName(String name) {
        Query query = em.createQuery("SELECT s FROM Sale s WHERE s.name = :name");
        query.setParameter("name", name);
        return (Sale) query.getSingleResult();
    }
     // Метод для получения списка акций из базы данных
    private List<Sale> getSalesFromDatabase() {
    Query query = em.createQuery("SELECT s FROM Sale s");
    return query.getResultList();
    }
     // Метод для получения списка акций из базы данных с учетом фильтрации по сроку действия
    private List<Sale> getSalesFromDatabase(FilterType filterType) {
        Query query = null;
        switch (filterType) {
            case ACTIVE:
                query = em.createQuery("SELECT s FROM Sale s WHERE s.dateEnd >= :today");              
                break;
            case PAST:
                query = em.createQuery("SELECT s FROM Sale s WHERE s.dateEnd < :today");
                break;
            case FUTURE:
                query = em.createQuery("SELECT s FROM Sale s WHERE s.dateStart > :today");
                break;
            default:
                return getSalesFromDatabase();                
        }
        query.setParameter("today", new Date());
        return query.getResultList();
    }
    @FXML
    public void showAllSales() {
        refreshSalesList(FilterType.ALL);
    }

    @FXML
    public void showActiveSales() {
        refreshSalesList(FilterType.ACTIVE);
    }

    @FXML
    public void showPastSales() {
        refreshSalesList(FilterType.PAST);
    }

    @FXML
    public void showFutureSales() {
        refreshSalesList(FilterType.FUTURE);
    }

    private void refreshSalesList(FilterType filterType) {
        // Очищаем ListView перед добавлением новых элементов
        saleListView.getItems().clear();

        // Получаем список акций из базы данных с учетом выбранного фильтра
        List<Sale> sales = getSalesFromDatabase(filterType);

        // Отображаем список акций в ListView
        for (Sale sale : sales) {
            saleListView.getItems().add(sale.getName());
        }
    }

   
   
    
}
