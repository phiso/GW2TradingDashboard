/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GW2Objects;

import apiConnector.GW2ApiConnector;
import apiConnector.GW2ApiPriceListener;
import java.io.IOException;
import java.net.URISyntaxException;
import javafx.application.Platform;
import javafx.scene.control.Label;

/**
 *
 * @author Philipp
 */
public class GW2InvItem extends GW2Item {

    private Integer count;
    private Integer charges;
    private Boolean monitoring;
    private GW2ApiPriceListener monitoringThread;
    private Label sellPriceLabel;
    private Label buyPriceLabel;

    public GW2InvItem(String id, Integer count, Integer charges) throws URISyntaxException, IOException {
        super(id);
        this.count = count;
        this.charges = charges;
        monitoring = false;
        monitoringThread = new GW2ApiPriceListener(id);
    }

    public Integer getCount() {
        return count;
    }

    public Integer getCharges() {
        return charges;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public void setCharges(Integer charges) {
        this.charges = charges;
    }

    public void startMonitoring(int delay) {
        monitoringThread.setDelay(delay);
        monitoringThread.start();
        monitoring = true;

        if (!monitoringThread.sellPriceProperty().isBound()) {
            monitoringThread.buyPriceProperty().addListener((observable, oldValue, newValue) -> {
                this.setBuyPrice((Integer) newValue);
                Platform.runLater(() -> {
                    buyPriceLabel.setText(new GW2Price((Integer) newValue).toString());
                });
            });
            monitoringThread.sellPriceProperty().addListener((observable, oldValue, newValue) -> {
                this.setSellPrice((Integer) newValue);
                Platform.runLater(() -> {
                    sellPriceLabel.setText(new GW2Price((Integer) newValue).toString());
                });
            });
        }
    }

    public void stopMonitoring() {
        monitoringThread.stopThread();
        monitoring = false;
        monitoringThread = new GW2ApiPriceListener(getId());
    }

    public void setDisplayLabels(Label sellPriceLabel, Label buyPriceLabel) {
        this.sellPriceLabel = sellPriceLabel;
        this.buyPriceLabel = buyPriceLabel;
    }
}
