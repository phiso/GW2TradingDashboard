/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gw2tradedashboard;

/**
 *
 * @author Philipp
 */
public class MainControllerWrapper {
    private static MainControllerWrapper instance;
    private static MainController controller;
    
    private MainControllerWrapper(MainController controller){
        MainControllerWrapper.controller = controller;
    }
    
    public static MainControllerWrapper getInstance(MainController controller){
        if (MainControllerWrapper.instance == null){
            MainControllerWrapper.instance = new MainControllerWrapper(controller);
        }
        return MainControllerWrapper.instance;
    }
    
    public static MainController getController(){
        return MainControllerWrapper.controller;
    }
}
