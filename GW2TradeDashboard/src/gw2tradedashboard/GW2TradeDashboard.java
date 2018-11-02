/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gw2tradedashboard;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.logging.Level;
import javafx.application.Application;
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
        LogMngr.getinstance("GWTLogger", String.format("%s%slog%slog.txt", System.getProperty("user.dir"), File.separator, File.separator), Level.parse(GWTSettings.getSetting("LOGGING.Loglevel")));
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

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
