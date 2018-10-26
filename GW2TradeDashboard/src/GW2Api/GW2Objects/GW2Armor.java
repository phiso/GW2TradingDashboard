/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GW2Api.GW2Objects;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.client.ClientProtocolException;

/**
 *
 * @author Philipp
 */
public class GW2Armor extends GW2Item {

    private String armorType;
    private String weightClass;
    private Integer defense;
    private List<String> infusionSlots;
    private List<GW2ItemAttribute> attributes;
    private Integer suffixItemId;
    private Integer defaultSkin;
    private String description;

    public GW2Armor(String itemId) throws ClientProtocolException, IOException, URISyntaxException {
        super(itemId);
        infusionSlots = new ArrayList<>();
        attributes = new ArrayList<>();
        parseJson();
    }

    private void parseJson() {
        JsonObject object = this.getDetails();
        armorType = object.get("type").getAsString();
        weightClass = object.get("weight_class").getAsString();
        defense = object.get("defense").getAsInt();
        JsonArray tempInfusion = object.getAsJsonArray("infusion_slots");
        for (JsonElement elem : tempInfusion) {
            infusionSlots.add(elem.getAsString());
        }
        JsonArray temp = object.getAsJsonObject("infix_upgrade").getAsJsonArray("attributes");
        for (JsonElement elem : temp) {
            JsonObject attr = elem.getAsJsonObject();
            attributes.add(new GW2ItemAttribute(attr));
        }
        suffixItemId = object.get("suffix_item_id").getAsInt();
        defaultSkin = this.getSrcObject().get("default_skin").getAsInt();
        description = this.getSrcObject().get("description").getAsString();
    }

    public String getArmorType() {
        return armorType;
    }

    public String getWeightClass() {
        return weightClass;
    }

    public Integer getDefense() {
        return defense;
    }

    public List<String> getInfusionSlots() {
        return infusionSlots;
    }

    public List<GW2ItemAttribute> getAttributes() {
        return attributes;
    }

    public Integer getSuffixItemId() {
        return suffixItemId;
    }

}
