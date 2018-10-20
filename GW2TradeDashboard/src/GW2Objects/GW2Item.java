/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GW2Objects;

import apiConnector.GW2ApiConnector;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import gw2tradedashboard.SqliteDriver;
import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.SQLException;

/**
 *
 * @author Philipp
 */
public class GW2Item {

    private String name;
    private String id;
    private String description;
    private String type;
    private String level;
    private String rarity;
    private String vendorValue;
    private String iconPath;
    private Boolean accountBound = false;
    private Boolean noSell = false;
    private Integer sellPrice = 0;
    private Integer buyPrice = 0;

    public GW2Item(String id) throws URISyntaxException, IOException {
        this.id = id;
        Gson gson = new Gson();
        GW2ApiConnector.getInstance();
        JsonObject obj = GW2ApiConnector.getItem(id);
        try {
            name = obj.get("name").getAsString();
        } catch (Exception e) {
            name = "Unknown";
        }
        try {
            description = obj.get("description").getAsString();
        } catch (Exception e) {
            description = "";
        }
        type = obj.get("type").getAsString();
        level = obj.get("level").getAsString();
        rarity = obj.get("rarity").getAsString();
        vendorValue = obj.get("vendor_value").getAsString();
        iconPath = obj.get("icon").getAsString();
        JsonArray flags = obj.getAsJsonArray("flags");
        for (JsonElement elem : flags) {
            switch (elem.getAsString()) {
                case "NoSell":
                    noSell = true;
                    break;
            }
        }

    }

    public Integer getTradingSellPrice() throws URISyntaxException, IOException {
        JsonObject obj = GW2ApiConnector.getPrice(id);
        Integer price = 0;
        if (obj != null) {
            try {
                price = obj.getAsJsonObject("sells").get("unit_price").getAsInt();
            } catch (Exception e) {
            }
        }
        return price;
    }

    public Integer getTradingBuyPrice() throws URISyntaxException, IOException {
        JsonObject obj = GW2ApiConnector.getPrice(id);
        Integer price = 0;
        if (obj != null) {
            try {
                price = obj.getAsJsonObject("buys").get("unit_price").getAsInt();
            } catch (Exception e) {
            }
        }
        return price;
    }

    public void saveToDatabase() throws SQLException {
        SqliteDriver.saveItem(this);
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public String getType() {
        return type;
    }

    public String getLevel() {
        return level;
    }

    public String getRarity() {
        return rarity;
    }

    public String getVendorValue() {
        return vendorValue;
    }

    public String getIconPath() {
        return iconPath;
    }

    public Boolean getAccountBound() {
        return accountBound;
    }

    public Boolean getNoSell() {
        return noSell;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setNoSell(Boolean noSell) {
        this.noSell = noSell;
    }

    /**
     * @return the sellPrice
     */
    public Integer getSellPrice() {
        return sellPrice;
    }

    /**
     * @param sellPrice the sellPrice to set
     */
    public void setSellPrice(Integer sellPrice) {
        this.sellPrice = sellPrice;
    }

    /**
     * @return the buyPrice
     */
    public Integer getBuyPrice() {
        return buyPrice;
    }

    /**
     * @param buyPrice the buyPrice to set
     */
    public void setBuyPrice(Integer buyPrice) {
        this.buyPrice = buyPrice;
    }
}
