/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package apiConnector;

import com.google.gson.JsonObject;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleBooleanProperty;
import org.apache.http.client.methods.HttpGet;

/**
 *
 * @author Philipp
 */
public class ApiItemThread extends Thread{
    
//    private SimpleBooleanProperty done;
//    private ArrayList<String> itemIds;
//    
//    public void ApiItemThread(ArrayList<String> ids){
//        done = new SimpleBooleanProperty(false);
//        itemIds = new ArrayList<>();
//        itemIds = (ArrayList<String>) ids.clone();
//    }
//    
//    @Override
//    public void run(){
//        itemIds.forEach((id) -> {
//            try {
//                JsonObject obj = GW2ApiConnector.request("/v2/items/".concat(id), false);
//                
//            } catch (URISyntaxException | IOException ex) {
//                Logger.getLogger(ApiItemThread.class.getName()).log(Level.SEVERE, null, ex);
//            }
//        });
//        done.set(true);
//    }
//    
//    public SimpleBooleanProperty getDoneProperty(){
//        return done;
//    }
//    
//    public Boolean isDone(){
//        return done.get();
//    }
}
