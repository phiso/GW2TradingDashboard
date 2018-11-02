/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GW2Api.GW2Objects;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Philipp
 */
public class GW2Account {

    private String id;
    private String name;
    private Integer age;
    private Integer worldId;
    private List<String> guildIds;
    private Date creationDate;
    private List<String> access;
    private Boolean commander;
    private List<String> characters;
    private GW2Inventory accountInventory;
    private GW2Inventory accountBank;

    private JsonObject srcObject;

    public GW2Account(JsonObject srcObject) throws ParseException {
        guildIds = new ArrayList<>();
        access = new ArrayList<>();
        characters = new ArrayList<>();
        this.srcObject = srcObject;
        if (srcObject != null) {
            parseJson();
        }
    }

    private void parseJson() throws ParseException {
        id = srcObject.get("id").getAsString();
        name = srcObject.get("name").getAsString();
        age = srcObject.get("age").getAsInt();
        worldId = srcObject.get("world").getAsInt();
        if (srcObject.get("guilds") != null) {
            for (JsonElement elem : srcObject.getAsJsonArray("guilds")) {
                guildIds.add(elem.getAsString());
            }
        }
        creationDate = new SimpleDateFormat("YYYY-MM-DDThh:mm:ssZ").parse(srcObject.get("created").getAsString());
        if (srcObject.get("access") != null) {
            for (JsonElement elem : srcObject.getAsJsonArray("access")) {
                access.add(elem.getAsString());
            }
        }
        commander = srcObject.get("commander").getAsBoolean();
    }

    public void setCharacters(ArrayList<String> chars) {
        characters = (List<String>) chars.clone();
    }

    public void setAccountInventory(GW2Inventory inv) {
        accountInventory = inv;
    }

    public void setAccountBank(GW2Inventory bank) {
        accountBank = bank;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Integer getAge() {
        return age;
    }

    public Integer getWorldId() {
        return worldId;
    }

    public List<String> getGuildIds() {
        return guildIds;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public List<String> getAccess() {
        return access;
    }

    public Boolean getCommander() {
        return commander;
    }

    public List<String> getCharacters() {
        return characters;
    }

    public GW2Inventory getAccountInventory() {
        return accountInventory;
    }

    public GW2Inventory getAccountBank() {
        return accountBank;
    }

    public JsonObject getSrcObject() {
        return srcObject;
    }
}
