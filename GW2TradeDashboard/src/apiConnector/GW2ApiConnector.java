/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package apiConnector;

import GW2Objects.GW2Price;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.sql.SQLException;
import java.util.ArrayList;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.ini4j.Ini;

/**
 *
 * @author Philipp
 */
public class GW2ApiConnector {

    private static final String baseAddr = "api.guildwars2.com";
    private static final String keyFile = System.getProperty("user.dir").concat("\\data\\key.ini");
    private static final String dbFile = System.getProperty("user.dir").concat("\\data\\data.db");

    private static GW2ApiConnector instance;
    private static String apiKey = "";
    private static CloseableHttpClient httpClient;
    private static ResponseHandler<JsonObject> respObj;
    private static ResponseHandler<JsonArray> respArray;

    private GW2ApiConnector() throws URISyntaxException, IOException {
        File file = new File(keyFile);
        Ini ini = new Ini();
        ini.load(file);
        Ini.Section keySection = ini.get("Key");
        String key = keySection.get("api_key");
        GW2ApiConnector.apiKey = key;
        GW2ApiConnector.httpClient = HttpClients.createDefault();

        respObj = (HttpResponse hr) -> {
            StatusLine statusLine = hr.getStatusLine();
            HttpEntity entity = hr.getEntity();
            if (statusLine.getStatusCode() >= 300) {
                throw new HttpResponseException(statusLine.getStatusCode(), statusLine.getReasonPhrase());
            }
            if (entity == null) {
                throw new ClientProtocolException("Response contains no Content!");
            }
            Gson gson = new GsonBuilder().create();
            ContentType contentType = ContentType.getOrDefault(entity);
            Charset charset = contentType.getCharset();
            Reader reader = new InputStreamReader(entity.getContent(), charset);            
            return gson.fromJson(reader, JsonObject.class);
        };

        respArray = (HttpResponse hr) -> {
            StatusLine statusLine = hr.getStatusLine();
            HttpEntity entity = hr.getEntity();
            if (statusLine.getStatusCode() >= 300) {
                throw new HttpResponseException(statusLine.getStatusCode(), statusLine.getReasonPhrase());
            }
            if (entity == null) {
                throw new ClientProtocolException("Response contains no Content!");
            }
            Gson gson = new GsonBuilder().create();
            ContentType contentType = ContentType.getOrDefault(entity);
            Charset charset = contentType.getCharset();
            Reader reader = new InputStreamReader(entity.getContent(), charset);
            return gson.fromJson(reader, JsonArray.class);
        };
    }

    public static GW2ApiConnector getInstance() throws URISyntaxException, IOException {
        if (GW2ApiConnector.instance == null) {
            GW2ApiConnector.instance = new GW2ApiConnector();
        }
        return GW2ApiConnector.instance;
    }

    public static JsonObject getCharacterInventory(String characterName) throws URISyntaxException, IOException {
        String path = String.format("/v2/characters/%s/inventory", characterName);
        HttpGet httpGet = GW2ApiConnector.buildURI(path, true);
        return httpClient.execute(httpGet, respObj);
    }

    public static JsonArray getCharacters() throws IOException, URISyntaxException {
        HttpGet httpGet = GW2ApiConnector.buildURI("/v2/characters", true);
        System.out.println(httpGet.getURI());
        return httpClient.execute(httpGet, respArray);
    }

    public static HttpGet buildURI(String path, Boolean token) throws URISyntaxException {
        URI uri;
        if (!token) {
            uri = new URIBuilder()
                    .setScheme("https")
                    .setHost(baseAddr)
                    .setPath(path)
                    .build();
        } else {
            uri = new URIBuilder()
                    .setScheme("https")
                    .setHost(baseAddr)
                    .setPath(path)
                    .setParameter("access_token", apiKey)
                    .build();
        }
        return new HttpGet(uri);
    }

    private static ArrayList<String> getAllItems() throws URISyntaxException, IOException {
        ArrayList<String> result = new ArrayList<>();
        HttpGet httpGet = buildURI("/v2/items", false);
        JsonArray jArray = httpClient.execute(httpGet, respArray);
        for (JsonElement elem : jArray) {
            result.add(elem.getAsString());
        }
        return result;
    }

    /**
     *
     * @throws URISyntaxException
     * @throws IOException
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public static void reloadItemDB() throws URISyntaxException, IOException, ClassNotFoundException, SQLException {
        ArrayList<String> items = getAllItems();
        SqliteDatabase db = new SqliteDatabase(dbFile, true);
        for (String id : items) {

        }
    }
    
    public static JsonObject getItem(String itemId) throws URISyntaxException, IOException{
        HttpGet httpGet = buildURI("/v2/items/".concat(itemId), false);
        JsonObject itemObj = httpClient.execute(httpGet, respObj);
        return itemObj;
    }
    
    public static JsonObject getPrice(String itemId) throws URISyntaxException, IOException{
        HttpGet httpGet = buildURI("/v2/commerce/prices/".concat(itemId), true);
        JsonObject priceObj = httpClient.execute(httpGet, respObj);
        return priceObj;
    }

    public static JsonObject request(String path, Boolean auth) throws URISyntaxException, IOException {
        HttpGet httpGet = buildURI(path, auth);
        JsonObject obj = null;
        JsonArray arr = null;
        try {
            obj = httpClient.execute(httpGet, respObj);
        } catch (Exception e) {
            arr = httpClient.execute(httpGet, respArray);
        }
        if (obj == null){
            obj = arr.getAsJsonObject();
        }
        return obj;
    }
    
    public static GW2Price formatPrice(Integer price){
       Integer gold = Math.floorDiv(price, 10000);
       Integer tmp = price % 10000;
       Integer silver = Math.floorDiv(tmp, 100);
       Integer copper = tmp % 100;
       return new GW2Price(gold, silver, copper);
    }
}
