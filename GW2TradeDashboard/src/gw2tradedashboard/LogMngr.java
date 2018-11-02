/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gw2tradedashboard;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/**
 *
 * @author Philipp
 */
public class LogMngr {
    private static LogMngr instance;
    private static Logger logger;
    private static FileHandler fileHandler;
    private static Level logLevel;
    
    private LogMngr(String name, String path) throws IOException{
        logger = Logger.getLogger(name);
        fileHandler = new FileHandler(path);
        logger.addHandler(fileHandler);
        SimpleFormatter formatter = new SimpleFormatter();
        fileHandler.setFormatter(formatter);
        logger.info("Logger initialized");
        logLevel = Level.WARNING;
    }
    
    public static LogMngr getinstance(String name, String path) throws IOException{
        if (LogMngr.instance == null){
            LogMngr.instance = new LogMngr(name, path);
        }
        return LogMngr.instance;
    }
    
    /**
     * Only logs whats fine with set loglevel or higher
     * @param level
     * @param msg 
     */
    private static void logAware(Level level, String msg, Throwable thrown){
        if (level.intValue() >= LogMngr.logLevel.intValue()){
            if (thrown != null){
                logger.log(level, msg, thrown);
            } else {
                logger.log(level, msg);
            }
        }
    }
    
    public static void setLevel(Level level){
        LogMngr.logLevel = level;
    }
    
    public static Level getLevel(){
        return LogMngr.logLevel;
    }
    
    public static void log(Level level, String msg){
        logAware(level, msg, null);
    }
        
    public static void log(Level level, String msg, Throwable thrown){
        logAware(level, msg, thrown);
    }
    
    public static void logInfo(String msg){
        logAware(Level.INFO, msg, null);
    }
    
    public static void logInfo(String msg, Throwable thrown){
        logAware(Level.INFO, msg, thrown);
    }
    
    public static void logWarning(String msg){
        logAware(Level.WARNING, msg, null);
    }
    
    public static void logWarning(String msg, Throwable thrown){
        logAware(Level.WARNING, msg, thrown);
    }
    
    public static void logError(String msg){
        logAware(Level.SEVERE, msg, null);
    }
    
    public static void logError(String msg, Throwable thrown){
        logAware(Level.SEVERE, msg, thrown);
    }
    
    public static void logConfig(String msg){
        logAware(Level.CONFIG, msg, null);
    }
    
    public static void logConfig(String msg, Throwable thrown){
        logAware(Level.CONFIG, msg, thrown);
    }
}
