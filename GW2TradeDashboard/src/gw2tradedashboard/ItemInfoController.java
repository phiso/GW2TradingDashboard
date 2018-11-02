/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gw2tradedashboard;

import GW2Api.GW2Objects.GW2InventoryItem;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

/**
 * FXML Controller class
 *
 * @author Philipp
 */
public class ItemInfoController implements Initializable {
    
    @FXML
    private Label itemNameLabel;
    @FXML
    private Label itemInfoLabel;
    @FXML
    private Label buyCostLabel;
    @FXML
    private Label sellValueLabel;
    @FXML
    private Label profitLabel;
    @FXML
    private Label countLabel;
    @FXML
    private CheckBox monitorCheckBox;
    @FXML
    private Pane overlayPane;
    @FXML
    private ImageView itemImage;
    @FXML
    private Label chargesLabel;
    @FXML
    private Spinner<Integer> amountSpinner;
    @FXML
    private Spinner<Integer> goldSpinner;
    @FXML
    private Spinner<Integer> silverSpinner;
    @FXML
    private Spinner<Integer> copperSpinner;
    @FXML
    private Button diagramButton;
    
    private GW2InventoryItem item;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        SpinnerValueFactory<Integer> amountValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 10000, 1);
        SpinnerValueFactory<Integer> goldValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 1000, 0);
        SpinnerValueFactory<Integer> silverValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100, 0);
        SpinnerValueFactory<Integer> copperValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100, 0);
        
        amountSpinner.setValueFactory(amountValueFactory);
        goldSpinner.setValueFactory(goldValueFactory);
        silverSpinner.setValueFactory(silverValueFactory);
        copperSpinner.setValueFactory(copperValueFactory);
        
        copperSpinner.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == 100) {
                copperSpinner.getValueFactory().setValue(0);
                silverSpinner.getValueFactory().setValue(silverSpinner.getValue() + 1);
            }
        });
        
        silverSpinner.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == 100) {                
                silverSpinner.getValueFactory().setValue(0);
                goldSpinner.getValueFactory().setValue(goldSpinner.getValue() + 1);
            }
        });
    }
    
    public void hanleMonitorCheckBox(ActionEvent event) {
      
    }
    
    public void handleOverlayPaneClick(MouseEvent event) {
       
    }
    
    public void setItem(GW2InventoryItem item) throws URISyntaxException, IOException {
       
    }
    
    public void handleDiagramButton(ActionEvent event) {
        
    }
    
}
