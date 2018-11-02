/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gw2tradedashboard;

import GW2Api.GW2Api;
import GW2Api.GW2Objects.GW2InventoryItem;
import apiConnector.GW2ApiConnector;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
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

    private GW2Api api;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        MainControllerWrapper.getInstance(this);
        try {
            api = new GW2Api();
        } catch (ClientProtocolException ex) {
            LogMngr.logError("Unable to load GW2Api.", ex);
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

    }
}
