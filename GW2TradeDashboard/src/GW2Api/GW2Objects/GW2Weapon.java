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
public class GW2Weapon extends GW2Item {

    private String weaponType;
    private String damageType;
    private Integer minPower;
    private Integer maxPower;
    private Integer defense;
    private List<String> infusionSlots;
    private List<GW2ItemAttribute> attributes;
    private Integer suffixItemId;
    private Integer secondarySuffixItemId;

    public GW2Weapon(JsonObject srcObject) throws ClientProtocolException, IOException, URISyntaxException {
        super(srcObject);
        infusionSlots = new ArrayList<>();
        attributes = new ArrayList<>();
        parseJson();
    }

    private void parseJson() {
        JsonObject object = this.getDetails();
        weaponType = object.get("type").getAsString();
        damageType = object.get("damage_type").getAsString();
        minPower = object.get("min_power").getAsInt();
        maxPower = object.get("max_power").getAsInt();
        JsonArray tempInfusion = object.getAsJsonArray("infusion_slots");
        for (JsonElement elem : tempInfusion) {
            infusionSlots.add(elem.getAsString());
        }
        JsonArray temp = object.getAsJsonObject("infix_upgrade").getAsJsonArray("attributes");
        for (JsonElement elem : temp) {
            JsonObject attr = elem.getAsJsonObject();
            attributes.add(new GW2ItemAttribute(attr));
        }
        defense = object.get("defense").getAsInt();
        suffixItemId = object.get("suffix_item_id").getAsInt();
        secondarySuffixItemId = object.get("secondary_suffix_item_id").getAsInt();
    }

    public String getWeaponType() {
        return weaponType;
    }

    public String getDamageType() {
        return damageType;
    }

    public Integer getMinPower() {
        return minPower;
    }

    public Integer getMaxPower() {
        return maxPower;
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

    public Integer getSecondarySuffixItemId() {
        return secondarySuffixItemId;
    }

}
