/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gw2tradedashboard;

import GW2Objects.GW2Account;
import GW2Objects.GW2InvItem;
import GW2Objects.GW2Inventory;
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
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.geometry.Orientation;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

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
    private ComboBox<String> characterComboBox;
    @FXML
    private AnchorPane inventoryTab;
    @FXML
    private TitledPane inventoryPane;
    @FXML
    private VBox itemsVBox;
    @FXML
    private Button clearInventoryButton;

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
        MainControllerWrapper.getInstance(this);
    }

    public void initAfter() {
        try {
            account = new GW2Account();
            characterComboBox.getItems().setAll(account.getCharacters());
        } catch (URISyntaxException | IOException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        characterComboBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            GW2Inventory inventory;
            Platform.runLater(() -> {
                inventoryPane.setText(newValue);
                LoadInventoryThread t = new LoadInventoryThread(newValue);
                t.start();               
            });
        });
    }
    
    public void fillInventory(GW2Inventory inventory) throws IOException{
        for (GW2InvItem item : inventory.getAllitems()){
            Platform.runLater(() -> {
                try {
                    loaditemFXML(item);
                } catch (IOException | URISyntaxException ex) {
                    Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
                }
            });            
        }
    }
    
    private void clearInventory(){
        itemsVBox.getChildren().clear();
    }

    public void loaditemFXML(GW2InvItem item) throws IOException, URISyntaxException {
        URL location = getClass().getResource("ItemInfo.fxml");
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(location);
        fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
        Parent root = fxmlLoader.load(location.openStream());
        ItemInfoController controller = fxmlLoader.getController();
        controller.setItem(item);
        itemsVBox.getChildren().add(root);
        itemsVBox.getChildren().add(new Separator(Orientation.HORIZONTAL));
    }

    public void handleCharacterCombobox(ActionEvent event) {

    }    
    
    public void handleClearInventoryButton(ActionEvent event){
        clearInventory();
    }
}
