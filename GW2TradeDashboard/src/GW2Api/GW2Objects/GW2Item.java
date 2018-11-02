/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GW2Api.GW2Objects;

import GW2Api.GW2Api;
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
public class GW2Item {

    private String name;
    private String type;
    private Integer level;
    private String rarity;
    private List<String> flags;
    private List<String> gameTypes;
    private List<String> restrictions;
    private Integer id;
    private String iconUrl;
    private Integer vendorValue;
    private JsonObject details;
    private JsonObject srcObject;

    public GW2Item(JsonObject srcObject) throws ClientProtocolException, IOException, URISyntaxException {
        this.srcObject = srcObject;
        flags = new ArrayList<>();
        gameTypes = new ArrayList<>();
        restrictions = new ArrayList<>();
        parseJson();
    }

    private void parseJson() {
        id = srcObject.get("id").getAsInt();
        name = srcObject.get("name").getAsString();
        type = srcObject.get("type").getAsString();
        level = srcObject.get("level").getAsInt();
        rarity = srcObject.get("rarity").getAsString();
        JsonArray jFlags = srcObject.getAsJsonArray("flags");
        for (JsonElement elem : jFlags) {
            flags.add(elem.getAsString());
        }
        JsonArray jGameTypes = srcObject.getAsJsonArray("game_types");
        for (JsonElement elem : jGameTypes) {
            gameTypes.add(elem.getAsString());
        }
        JsonArray jRestrictions = srcObject.getAsJsonArray("restrictions");
        for (JsonElement elem : jRestrictions) {
            restrictions.add(elem.getAsString());
        }
        iconUrl = srcObject.get("icon").getAsString();
        vendorValue = srcObject.get("vendor_value").getAsInt();
        details = srcObject.getAsJsonObject("details");
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public Integer getLevel() {
        return level;
    }

    public String getRarity() {
        return rarity;
    }

    public List<String> getFlags() {
        return flags;
    }

    public List<String> getGameTypes() {
        return gameTypes;
    }

    public List<String> getRestrictions() {
        return restrictions;
    }

    public Integer getId() {
        return id;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public Integer getVendorValue() {
        return vendorValue;
    }

    public JsonObject getDetails() {
        return details;
    }

    /**
     * @return the srcObject
     */
    public JsonObject getSrcObject() {
        return srcObject;
    }

}
