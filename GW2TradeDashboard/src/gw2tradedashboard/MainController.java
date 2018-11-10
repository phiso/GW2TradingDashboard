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
import GW2Api.Threads.GW2ThreadMngr;
import com.google.gson.JsonObject;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
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
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.Separator;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.SpinnerValueFactory.IntegerSpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import org.apache.http.client.ClientProtocolException;

/**
 * FXML Controller class
 *
 * @author Philipp
 */
public class MainController implements Initializable {

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
    @FXML
    private Button saveSettingsButton;
    @FXML
    private Button selectLogFileButton;
    @FXML
    private Spinner<Integer> refreshRateSpinner;
    @FXML
    private TextField apiKeyField;
    @FXML
    private TextField logFileField;
    @FXML
    private ComboBox<String> loglevelCombobox;

    private GW2Api api;
    private Boolean inventoryLoaded = false;
    private GW2Inventory curInventory = null;
    private List<ItemInfoController> itemFXMLControllers;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        MainControllerWrapper.getInstance(this);
        itemFXMLControllers = new ArrayList<>();
        try {
            api = new GW2Api();
            LogMngr.logInfo("GW2 Api loaded.");
        } catch (ClientProtocolException ex) {
            LogMngr.logError("Unable to load GW2Api.", ex);
        }
        if (GWTSettings.getSetting("API.ApiKey") != "" && GWTSettings.getSetting("API.api_key") != null) {
            api.setApiKey(GWTSettings.getSetting("API.api_key"));
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
        loglevelCombobox.getItems().addAll("INFO", "WARNING", "SEVERE");
        loglevelCombobox.getSelectionModel().select(GWTSettings.getSetting("LOGGING.loglevel"));

        Integer initialRefreshRate = Integer.decode(GWTSettings.getSetting("TRADING.refresh_rate"));
        Integer stepSize = Integer.decode(GWTSettings.getSetting("TRADING.refresh_step_size"));
        IntegerSpinnerValueFactory refreshRateValueFactory = new IntegerSpinnerValueFactory(1000, 300000, initialRefreshRate, stepSize);
        refreshRateSpinner.setValueFactory(refreshRateValueFactory);
        GW2ThreadMngr.getInstance();
    }

    public void initAfter() {

    }

    public ItemInfoController loaditemFXML(GW2InventoryItem item) throws IOException, URISyntaxException {
        URL location = getClass().getResource("ItemInfo.fxml");
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(location);
        fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
        Parent root = fxmlLoader.load(location.openStream());
        ItemInfoController controller = fxmlLoader.getController();
        controller.setItem(item);
        itemsVBox.getChildren().add(root);
        itemsVBox.getChildren().add(new Separator(Orientation.HORIZONTAL));
        return controller;
    }

    public void handleCharacterCombobox(ActionEvent event) {
        LogMngr.logInfo("Selected Character: ".concat(characterComboBox.getValue()));
        itemsVBox.getChildren().clear();
        inventoryLoaded = false;
    }

    public void handleRefreshInventoryButton(ActionEvent event) {
        if (!inventoryLoaded) {
            try {
                loadCharacterInventory();
            } catch (URISyntaxException | IOException ex) {
                LogMngr.logError("Error while loading inventory.", ex);
            }
        }
    }

    public void handleLoglevelCombobox(ActionEvent event) {
        //passively read on save
    }

    public void handleSaveSettingsButton(ActionEvent event) {
        GWTSettings.setSetting("API.api_key", apiKeyField.getText());
        GWTSettings.setSetting("TRADING.refresh_rate", refreshRateSpinner.getValue().toString());
        GWTSettings.setSetting("LOGGING.loglevel", loglevelCombobox.getValue());
        GWTSettings.setSetting("LOGGING.logfile", logFileField.getText());
    }

    public void handleSelectLogFileButton(ActionEvent event) {
        FileChooser fc = new FileChooser();
        fc.setTitle("Save Logfile...");
        fc.setInitialFileName("GWTD.log");
        fc.setInitialDirectory(new File(System.getProperty("user.dir")));
        File logFile = fc.showSaveDialog(null);

        if (logFile != null) {
            logFileField.setText(logFile.getAbsolutePath());
        } else {
            LogMngr.logWarning("No Logfile selected!");
        }
    }

    private void loadCharacterInventory() throws URISyntaxException, IOException {
        progressIndicator.setVisible(true);
        Thread t = new Thread(() -> {
            try {
                String character = characterComboBox.getValue();
                JsonObject jInventory = api.getCharacterInventory(character);
                GW2Inventory inventory = new GW2Inventory(jInventory);
                for (GW2InventoryItem thisItem : inventory.getAllItems()) {
                    Integer id = thisItem.getId();
                    thisItem.setCorrItem(api.getItem(id));
                }
                curInventory = inventory;
                inventoryLoaded = true;
            } catch (URISyntaxException | IOException ex) {
                LogMngr.logError("Failed loading Character Inventory for: ".concat(characterComboBox.getValue()), ex);
            }
            LogMngr.logInfo("Loaded " + curInventory.getItemCount() + " items in Inventory.");

            curInventory.getAllItems().forEach((item) -> {
                Platform.runLater(() -> {
                    try {
                        itemFXMLControllers.add(loaditemFXML(item));
                    } catch (IOException | URISyntaxException ex2) {
                        LogMngr.logError("Error loading item FXML.", ex2);
                    }
                });
            });
            Platform.runLater(() -> {
                progressIndicator.setVisible(false);
            });

        });
        t.start();
        inventoryPane.setText(characterComboBox.getValue());
    }
}
