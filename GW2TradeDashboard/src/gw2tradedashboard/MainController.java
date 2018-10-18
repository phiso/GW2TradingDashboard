/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gw2tradedashboard;

import GW2Objects.GW2Account;
import GW2Objects.GW2Price;
import apiConnector.GW2ApiConnector;
import apiConnector.GW2ApiPriceListener;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author Philipp
 */
public class MainController implements Initializable {

    @FXML
    private Label buyingValueLabel;
    @FXML
    private Label sellingValueLabel;
    @FXML
    private Button monitoringButton;
    @FXML
    private TextField monitoringIdField;
    @FXML
    private Button loadItemButton;
    @FXML
    private TextArea itemArea;
    @FXML
    private Button stopMonitoringButton;
    @FXML
    private ComboBox<String> characterComboBox;

    private GW2Account account;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            GW2ApiConnector.getInstance();
        } catch (URISyntaxException | IOException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
        GW2PMM.getInstance();
        try {
            SqliteDriver.getInstance(System.getProperty("user.dir").concat("\\data\\data.db"));
        } catch (ClassNotFoundException | SQLException | FileNotFoundException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void initAfter() {
        try {
            account = new GW2Account();
            characterComboBox.getItems().setAll(account.getCharacters());
        } catch (URISyntaxException | IOException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void handleMonitoringButton(ActionEvent event) {
        String itemId = monitoringIdField.getText();
        GW2ApiPriceListener priceListener = new GW2ApiPriceListener(itemId);
        priceListener.getBuyPriceProperty().addListener((ObservableValue<? extends Number> observable, Number oldValue, Number newValue) -> {
            GW2Price price = GW2ApiConnector.formatPrice((Integer) newValue);
            Platform.runLater(() -> {
                buyingValueLabel.setText(price.toString());
            });
            System.out.println(price.toString());
        });
        priceListener.getSellPriceProperty().addListener((ObservableValue<? extends Number> observable, Number oldValue, Number newValue) -> {
            GW2Price price = GW2ApiConnector.formatPrice((Integer) newValue);
            Platform.runLater(() -> {
                sellingValueLabel.setText(price.toString());
            });
            System.out.println(price.toString());
        });
        priceListener.getAliveCounterProperty().addListener((observable, oldValue, newValue) -> {
            System.out.println(newValue);
        });
        priceListener.start();
    }

    public void handleLoadItemButton(ActionEvent event) {
        String itemId = monitoringIdField.getText();
        if (!itemId.equals("")) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            try {
                JsonObject obj = GW2ApiConnector.getItem(itemId);
                itemArea.setText(gson.toJson(obj));
            } catch (URISyntaxException | IOException ex) {
                Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void handleCharacterCombobox(ActionEvent event) {

    }

    public void handleStopMonitoringButton(ActionEvent event) {

    }
}
