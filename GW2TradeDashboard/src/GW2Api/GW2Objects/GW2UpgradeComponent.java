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
public class GW2UpgradeComponent extends GW2Item{
    
    private String description;
    private String componentType;
    private List<String> componentFlags;
    private List<String> infusionUpgradeFlags;
    private List<String> bonuses;
    private String suffix;
    private List<GW2ItemAttribute> attributes;
    
    public GW2UpgradeComponent(String itemId) throws ClientProtocolException, IOException, URISyntaxException {
        super(itemId);
        componentFlags = new ArrayList<>();
        infusionUpgradeFlags = new ArrayList<>();
        bonuses = new ArrayList<>();
        attributes = new ArrayList<>();
        parseJson();
    }
    
    private void parseJson(){
        JsonObject object = this.getDetails();
        description = this.getSrcObject().get("description").getAsString();
        JsonArray temp = object.getAsJsonObject("infix_upgrade").getAsJsonArray("attributes");
        for (JsonElement elem : temp) {
            JsonObject attr = elem.getAsJsonObject();
            attributes.add(new GW2ItemAttribute(attr));
        }
        componentType = object.get("type").getAsString();
        JsonArray compoArray = object.getAsJsonArray("flags");
        for (JsonElement elem : compoArray){
            componentFlags.add(elem.getAsString());
        }
        JsonArray bonArray = object.getAsJsonArray("bonuses");
        for (JsonElement elem : bonArray){
            bonuses.add(elem.getAsString());
        }
        JsonArray infArray = object.getAsJsonArray("infusion_upgrade_flags");
        for (JsonElement elem : infArray){
            infusionUpgradeFlags.add(elem.getAsString());
        }
        suffix = object.get("suffix").getAsString();
    }

    public String getDescription() {
        return description;
    }

    public String getComponentType() {
        return componentType;
    }

    public List<String> getComponentFlags() {
        return componentFlags;
    }

    public List<String> getInfusionUpgradeFlags() {
        return infusionUpgradeFlags;
    }

    public List<String> getBonuses() {
        return bonuses;
    }

    public String getSuffix() {
        return suffix;
    }

    public List<GW2ItemAttribute> getAttributes() {
        return attributes;
    }
    
    
    
}
