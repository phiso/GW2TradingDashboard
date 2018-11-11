/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gw2tradedashboard;

import GW2Api.GW2Objects.GW2InventoryItem;
import GW2Api.GW2Objects.GW2Item;
import GW2Api.GW2Objects.GW2Price;
import GW2Api.LogMngr;
import GW2Api.Threads.GW2ItemPriceListener;
import GW2Api.Threads.GW2ThreadMngr;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TabPane;
import javafx.scene.control.TitledPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

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
    private Label countLabel;
    @FXML
    private CheckBox monitorCheckBox;
    @FXML
    private ImageView itemImage;
    @FXML
    private Label chargesLabel;
    @FXML
    private AnchorPane anchorPane;
    @FXML
    private TitledPane detailsPane;
    @FXML
    private Separator separator;
    @FXML
    private TabPane detailsTabPane;
    @FXML
    private LineChart<String, Number> itemLineChart;

    private GW2InventoryItem invItem;
    private GW2Item item;
    private Boolean selected = false;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        detailsPane.setExpanded(false);
        anchorPane.setPrefHeight(70);
        detailsPane.expandedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                anchorPane.setPrefHeight(310);
                separator.setPrefHeight(310);
            } else {
                anchorPane.setPrefHeight(70);
                separator.setPrefHeight(70);
            }
        });
    }

    public void hanleMonitorCheckBox(ActionEvent event) {
        Integer itemId = invItem.getId();
        GW2ItemPriceListener listener = null;
        if (monitorCheckBox.isSelected()) {
            try {
                listener = GW2ThreadMngr.addOrResumeListener(itemId);
                listener.getBuyPriceProperty().addListener((observable, oldValue, newValue) -> {
                    Platform.runLater(() -> {
                        GW2Price price = new GW2Price((Integer) newValue);
                        buyCostLabel.setText(price.toString());
                    });
                });
                listener.getSellpriceProperty().addListener((observable, oldValue, newValue) -> {
                    Platform.runLater(() -> {
                        GW2Price price = new GW2Price((Integer) newValue);
                        sellValueLabel.setText(price.toString());
                    });
                });
            } catch (URISyntaxException | IOException ex) {
                LogMngr.logError("Error while trying to start listener on item: " + itemId);
            }

        } else {
            GW2ThreadMngr.stopListening(itemId);
        }
    }

    public void setItem(GW2InventoryItem item) {
        this.invItem = item;
        this.item = item.getItem();
        itemNameLabel.setText(this.item.getName());
        String binding = "";
        if (!invItem.getBinding().equals("")) {
            binding += invItem.getBinding() + " bound";
            if (!invItem.getBoundTo().equals("")) {
                binding += " to " + invItem.getBoundTo();
            }
        }
        itemInfoLabel.setText(this.item.getType() + ", " + this.item.getRarity() + ", " + binding);
        itemImage.setImage(new Image(this.item.getIconUrl()));
        chargesLabel.setText(invItem.getCharges().toString());
        countLabel.setText(invItem.getCount().toString());
    }
}
