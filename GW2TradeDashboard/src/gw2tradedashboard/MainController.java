/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gw2tradedashboard;

import GW2Api.LogMngr;
import GW2Api.GW2Api;
import GW2Api.GW2Objects.GW2Inventory;
import GW2Api.GW2Objects.GW2InventoryItem;
import GW2Api.GsonUtils;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.Separator;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import org.apache.http.client.ClientProtocolException;

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
    private Button refreshIventoryButton;
    @FXML
    private ProgressIndicator progressIndicator;

    private GW2Api api;
    private Boolean inventoryLoaded = false;
    private GW2Inventory curInventory = null;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        MainControllerWrapper.getInstance(this);
        try {
            api = new GW2Api();
            LogMngr.logInfo("GW2 Api loaded.");
        } catch (ClientProtocolException ex) {
            LogMngr.logError("Unable to load GW2Api.", ex);
        }
        if (GWTSettings.getSetting("API.ApiKey") != "" && GWTSettings.getSetting("API.ApiKey") != null) {
            api.setApiKey(GWTSettings.getSetting("API.ApiKey"));
        } else {
            LogMngr.logWarning("No ApiKey specified!");
        }
        ObservableList<String> characters = null;
        try {
            characters = FXCollections.observableArrayList(GsonUtils.toList(api.getCharacters().toString(), String.class));
        } catch (URISyntaxException | IOException ex) {
            LogMngr.logError("Error while filling character Combobox.", ex);
        } finally {
            characterComboBox.getItems().addAll(characters);
        }
    }

    public void initAfter() {

    }

    public void loaditemFXML(GW2InventoryItem item) throws IOException, URISyntaxException {
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
        LogMngr.logInfo("Selected Character: ".concat(characterComboBox.getValue()));
        inventoryLoaded = false;
    }

    public void handleRefreshInventoryButton(ActionEvent event) {
        progressIndicator.setVisible(true);
        if (inventoryLoaded) {
        } else {
            Thread t = new Thread(() -> {
                try {
                    loadCharacterInventory();
                } catch (URISyntaxException | IOException ex) {
                    LogMngr.logError("Failed loading Character Inventory for: ".concat(characterComboBox.getValue()), ex);
                }
                LogMngr.logInfo("Loaded " + curInventory.getItemCount() + " items in Inventory.");
                Platform.runLater(() -> {
                    progressIndicator.setVisible(false);
                });               
            });            
            t.start();            
        }
    }

    private void loadCharacterInventory() throws URISyntaxException, IOException {
        String character = characterComboBox.getValue();
        JsonObject jInventory = api.getCharacterInventory(character);
        GW2Inventory inventory = new GW2Inventory(jInventory);
        curInventory = inventory;
        inventoryLoaded = true;
    }
}
