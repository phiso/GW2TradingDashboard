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
public class GW2Inventory {

    private final String characterName;
    List<GW2InvItem> items;

    public GW2Inventory(String character) throws URISyntaxException, IOException {
        characterName = character;
        items = GW2ApiConnector.getCharacterInventory(characterName);
    }
    
    public GW2InvItem getItem(int index){
        return items.get(index);
    }
}
