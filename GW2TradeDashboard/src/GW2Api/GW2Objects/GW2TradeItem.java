/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GW2Api.GW2Objects;

import com.google.gson.JsonObject;

/**
 *
 * @author Philipp
 */
public class GW2TradeItem {

    private Integer itemId;
    private GW2Price buyPrice;
    private GW2Price sellPrice;
    private Integer buyQuantity;
    private Integer sellQuantity;
    private Boolean whitelisted;
    private JsonObject srcObject;

    public GW2TradeItem(JsonObject srcObject) {
        this.srcObject = srcObject;
        parseJson();
    }

    private void parseJson() {
        itemId = srcObject.get("id").getAsInt();
        JsonObject buys = srcObject.getAsJsonObject("buys");
        JsonObject sells = srcObject.getAsJsonObject("sells");
        buyPrice = new GW2Price(buys.get("unit_price").getAsInt());
        sellPrice = new GW2Price(sells.get("unit_price").getAsInt());
        buyQuantity = buys.get("quantity").getAsInt();
        sellQuantity = sells.get("quantity").getAsInt();
        whitelisted = srcObject.get("whitelisted").getAsBoolean();
    }

    public Integer getItemId() {
        return itemId;
    }

    public GW2Price getBuyPrice() {
        return buyPrice;
    }

    public GW2Price getSellPrice() {
        return sellPrice;
    }

    public Integer getBuyQuantity() {
        return buyQuantity;
    }

    public Integer getSellQuantity() {
        return sellQuantity;
    }

    public Boolean getWhitelisted() {
        return whitelisted;
    }

    public JsonObject getSrcObject() {
        return srcObject;
    }
    
    public Integer getRawBuyprice(){
        return buyPrice.getRawPrice();
    }
    
    public Integer getRawSellPrice(){
        return sellPrice.getRawPrice();
    }
}
