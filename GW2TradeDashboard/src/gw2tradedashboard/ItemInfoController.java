/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gw2tradedashboard;

import GW2Objects.GW2InvItem;
import GW2Objects.GW2Price;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
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
    
    private GW2InvItem item;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    public void hanleMonitorCheckBox(ActionEvent event) {
        if (monitorCheckBox.isSelected()) {
            item.setDisplayLabels(sellValueLabel, buyCostLabel);
            item.startMonitoring(5000);
        } else {
            item.stopMonitoring();
        }
    }

    public void handleOverlayPaneClick(MouseEvent event) {
        System.out.println("TEST");
    }
    
    public void setItem(GW2InvItem item) throws URISyntaxException, IOException{
        this.item = item;
        itemNameLabel.setText(item.getName() + " ("+item.getId()+")");
        itemInfoLabel.setText(item.getRarity()+", lvl"+item.getLevel());
        countLabel.setText(Integer.toString(item.getCount()));
        chargesLabel.setText(item.getCharges() == 0 ? "" : Integer.toString(item.getCharges()));
        itemImage.setImage(new Image(item.getIconPath()));
        GW2Price buying = new GW2Price(item.getTradingBuyPrice());
        GW2Price selling = new GW2Price(item.getTradingSellPrice());
        sellValueLabel.setText(selling.toString());
        buyCostLabel.setText(buying.toString());
    }

}
