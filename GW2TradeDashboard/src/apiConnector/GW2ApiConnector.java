/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package apiConnector;

/**
 *
 * @author Philipp
 */
public class GW2ApiConnector {

    private static final String baseAddr = "https://api.guildwars2.com/v2/";

    //This schould not be stying here because its a personal account key
    // should be loaded from config later
    private String apiKey = "CCF07BF5-54B0-834F-857B-18903DB66C700F080C98-D91B-4B71-A836-4086CD6FADCC";

    public GW2ApiConnector(String key) {
        this.apiKey = key;
    }

}
