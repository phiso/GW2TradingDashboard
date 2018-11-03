/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gw2tradedashboard;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import org.ini4j.Ini;

/**
 *
 * @author Philipp
 */
public class GWTSettings {

    private static GWTSettings instance;

    private static Ini ini;
    private static File settingsFile;

    private GWTSettings(String path) throws FileNotFoundException, IOException {
        settingsFile = new File(path);
        ini = new Ini();
        ini.load(new FileReader(settingsFile));
    }

    public static GWTSettings getInstance(String path) throws IOException {
        if (GWTSettings.instance == null) {
            GWTSettings.instance = new GWTSettings(path);
        }
        return GWTSettings.instance;
    }

    public static String getSetting(String setting) {
        String[] elems = setting.split("[.]");
        Ini.Section section = ini.get(elems[0]);
        Ini.Section tempSection = section;
        for (int i = 1; i < elems.length; i++) {
            if (i < elems.length - 1) {
                tempSection = tempSection.getChild(elems[i].toUpperCase());
            } else { // last string
                section = tempSection;
            }
        }
        return section.get(elems[elems.length - 1].toLowerCase());
    }

    public static void setSetting(String setting, String value) {
        String[] elems = setting.split("[.]");
        Ini.Section tempSection = ini.get(elems[0]);        
        for (int i = 1; i < elems.length; i++) {
            if (i < elems.length - 1) {
                tempSection = tempSection.addChild(elems[i].toUpperCase());                
            } else { // last string
                tempSection.add(setting, value);
            }
        }       
    }
    
    public static void saveSettings() throws IOException{
        ini.store();
    }
    
    public static void saveSettings(File file) throws IOException{
        ini.store(file);
    }
}
