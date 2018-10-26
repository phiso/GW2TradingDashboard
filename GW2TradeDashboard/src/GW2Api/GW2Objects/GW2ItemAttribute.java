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
public class GW2ItemAttribute {

    private final String name;
    private final Integer modifier;

    public GW2ItemAttribute(String name, Integer modifier) {
        this.name = name;
        this.modifier = modifier;
    }
    
    public GW2ItemAttribute(JsonObject object){
        this.name = object.get("attribute").getAsString();
        this.modifier = object.get("modifier").getAsInt();
    }

    public String getName() {
        return name;
    }

    public Integer getModifier() {
        return modifier;
    }
}
