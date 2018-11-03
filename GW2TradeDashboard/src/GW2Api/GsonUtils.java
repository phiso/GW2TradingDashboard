/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GW2Api;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.util.List;

/**
 *
 * @author Philipp
 */
public class GsonUtils {
    private GsonUtils(){
        
    }
    
    public static <T> List<T> toList(String json, Class<T> clazz){
        if (json == null){
            return null;
        }
        Gson gson = new Gson();
        return gson.fromJson(json, new TypeToken<T>(){}.getType());
    }
}
