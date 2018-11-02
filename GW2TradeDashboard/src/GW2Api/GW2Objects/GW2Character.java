/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GW2Api.GW2Objects;

import com.google.gson.JsonObject;
import java.util.List;

/**
 *
 * @author Philipp
 */
public class GW2Character {
    
    private String name;
    private String race;
    private String gender;
    private List<String> flags;
    private String profession;
    private Integer level;
    private String guildId;
    private Integer age;
    private Integer deathCounter;
    private GW2Inventory inventory;
    private JsonObject srcObject;
    
    public GW2Character(JsonObject srcObject){
        this.srcObject = srcObject;
    }
}
