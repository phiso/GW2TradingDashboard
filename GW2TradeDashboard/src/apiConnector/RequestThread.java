/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package apiConnector;

import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;

/**
 *
 * @author Philipp
 */
public class RequestThread extends Thread {

    private ArrayList<String> ids;
    private SimpleBooleanProperty done;
    private SimpleDoubleProperty progress;
    private Integer itemCount;

    public RequestThread(ArrayList<String> ids, String name) {
        super(name);
        this.ids = (ArrayList<String>) ids.clone();
        done = new SimpleBooleanProperty(false);
        progress = new SimpleDoubleProperty(0.0);
        itemCount = ids.size();
    }

    @Override
    public void run() {
        int count = 0;
        for (String id : ids) {
            try {
                count++;
                GW2Item item;                
                item = new GW2Item(id);
                item.saveToDatabase();
                Thread.sleep(100);                
                progress.set((count/itemCount)*100);
            } catch (URISyntaxException ex) {
                Logger.getLogger(RequestThread.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(RequestThread.class.getName()).log(Level.SEVERE, null, ex);
            } catch (InterruptedException ex) {
                Logger.getLogger(RequestThread.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex) {
                Logger.getLogger(RequestThread.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        done.set(true);
    }

    public SimpleBooleanProperty doneProperty() {
        return done;
    }

    public Boolean isDone() {
        return done.get();
    }

    public SimpleDoubleProperty progressProperty() {
        return progress;
    }

    public Double getProgress() {
        return progress.get();
    }
}
