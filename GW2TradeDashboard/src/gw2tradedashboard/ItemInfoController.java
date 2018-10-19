/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gw2tradedashboard;

import GW2Objects.GW2InvItem;
import GW2Objects.GW2Price;
import apiConnector.GW2ApiConnector;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
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

        } else {

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
        GW2Price buying = GW2ApiConnector.formatPrice(item.getTradingBuyPrice());
        GW2Price selling = GW2ApiConnector.formatPrice(item.getTradingSellPrice());
        sellValueLabel.setText(selling.toString());
        buyCostLabel.setText(buying.toString());
    }

}
