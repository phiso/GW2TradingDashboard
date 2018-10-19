/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GW2Objects;

import apiConnector.GW2ApiConnector;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author Philipp
 */
public class GW2Account {

    private List<String> characterNames;
    private HashMap<String, GW2Inventory> inventories;

    public GW2Account() throws URISyntaxException, IOException {
        GW2ApiConnector.getInstance();
        characterNames = GW2ApiConnector.getCharacters();
        inventories = new HashMap<>();
        /*for (String name : characterNames) {
            inventories.put(name, new GW2Inventory(name));
        }*/
    }
    
    public GW2Inventory getInventory(String name){
        return inventories.get(name);
    }
    
    public List<String> getCharacters(){
        return characterNames;
    }
}
