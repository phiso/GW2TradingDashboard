/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gw2tradedashboard;

import GW2Api.GW2Sql;
import GW2Api.LogMngr;
import GW2Api.Threads.GW2ThreadMngr;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author Philipp
 */
public class GW2TradeDashboard extends Application {

    private FXMLLoader fxmlLoader;
    private MainController controller;

    @Override
    public void start(Stage primaryStage) throws IOException {
        GWTSettings.getInstance(String.format("%s%sdata%sconf.ini", System.getProperty("user.dir"), File.separator, File.separator));
        LogMngr.getinstance("GWTLogger", System.getProperty("user.dir") + GWTSettings.getSetting("LOGGING.logfile"),
                Level.parse(GWTSettings.getSetting("LOGGING.loglevel")));
        try {
            GW2Sql.getInstance("//localhost/gw2tradingdashboard", 5);
        } catch (ClassNotFoundException | SQLException | InstantiationException | IllegalAccessException ex) {
            LogMngr.getLogger().log(Level.WARNING, "Could not get connection to sql database!", ex);
        }
        URL location = getClass().getResource("Main.fxml");
        fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(location);
        fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
        Parent root = (Parent) fxmlLoader.load(location.openStream());
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        controller = fxmlLoader.getController();
        controller.initAfter();
        primaryStage.show();
        LogMngr.logInfo("UI Loaded");
    }

    @Override
    public void stop() {
        LogMngr.log(Level.FINE, "Stopping Application");
        GW2ThreadMngr.killAllListeners();
        LogMngr.logConfig("Killed all existing Price-listeners.");
        Platform.exit();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
