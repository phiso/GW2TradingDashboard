/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GW2Api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.util.List;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

/**
 *
 * @author Philipp providing connection to the official GW2 Api on
 * api.guildwars2.com enable requesting public and private parts of the api with
 * given api-key uisng Googles gson framework for Json Handling and as
 * returnvalues
 */
public class GW2Api {

    private final String apiUrl = "api.guildwars2.com";

    private String apiKey;
    private String language;
    private CloseableHttpClient httpClient;
    private ResponseHandler<JsonElement> apiResponse;

    public GW2Api() throws HttpResponseException, ClientProtocolException {
        this.apiKey = "";
        this.language = "en";
        httpClient = HttpClients.createDefault();

        apiResponse = ((HttpResponse hr) -> {
            StatusLine statusLine = hr.getStatusLine();
            HttpEntity entity = hr.getEntity();
            if (statusLine.getStatusCode() >= 300) { // Http Error Codes
                throw new HttpResponseException(statusLine.getStatusCode(), statusLine.getReasonPhrase());
            }
            if (entity == null) { // Response without Content
                throw new ClientProtocolException("Response without content.");
            }
            Gson gson = new GsonBuilder().create();
            ContentType contentType = ContentType.getOrDefault(entity);
            Charset charset = contentType.getCharset();
            Reader reader = new InputStreamReader(entity.getContent(), charset);
            return gson.fromJson(reader, JsonElement.class);
        });
    }

    public GW2Api(String apiKey) throws ClientProtocolException {
        this();
        this.apiKey = apiKey;
    }

    public GW2Api(String apiKey, String lang) throws ClientProtocolException {
        this();
        this.apiKey = apiKey;
        this.language = lang;
    }

    public HttpGet buildURI(String path, Boolean token) throws URISyntaxException {
        URI uri;
        if (!token) {
            uri = new URIBuilder()
                    .setScheme("https")
                    .setHost(apiUrl)
                    .setPath(path)
                    .build();
        } else {
            uri = new URIBuilder()
                    .setScheme("https")
                    .setHost(apiUrl)
                    .setPath(path)
                    .setParameter("access_token", apiKey)
                    .build();
        }
        return new HttpGet(uri);
    }

    public JsonElement apiRequest(String path, Boolean auth) throws URISyntaxException, IOException {
        HttpGet httpGet = buildURI(path, auth);
        return httpClient.execute(httpGet, apiResponse);
    }

    public JsonObject getTradeitem(String itemId) throws URISyntaxException, IOException {
        return apiRequest("/v2/commerce/prices/".concat(itemId), true).getAsJsonObject();
    }

    public JsonObject getItem(Integer itemId) throws URISyntaxException, IOException {
        return apiRequest("/v2/items/".concat(Integer.toString(itemId)), false).getAsJsonObject();
    }

    public JsonArray getAllItemIds() throws URISyntaxException, IOException {
        return apiRequest("/v2/items", false).getAsJsonArray();
    }

    public JsonArray getItems(List<String> itemIds) throws URISyntaxException, IOException {
        String itemIdString = "";
        for (String it : itemIds) {
            itemIdString = itemIdString.concat(it + ",");
        }
        itemIdString = itemIdString.substring(0, itemIdString.length() - 1);
        return apiRequest("/v2/items?ids=".concat(itemIdString), true).getAsJsonArray();
    }

    public JsonArray getCharacters() throws URISyntaxException, IOException {
        return apiRequest("/v2/characters", true).getAsJsonArray();
    }

    public JsonObject getCharacterInventory(String charName) throws URISyntaxException, IOException {
        return apiRequest(String.format("/v2/characters/%s/inventory", charName), true).getAsJsonObject();
    }

    public JsonObject getAccount() throws URISyntaxException, IOException {
        return apiRequest("/v2/account", true).getAsJsonObject();
    }

    public JsonObject getTokenInfo() throws URISyntaxException, IOException {
        return apiRequest("/v2/tokeninfo", true).getAsJsonObject();
    }

    public JsonObject getCharacterEquipment(String charName) throws URISyntaxException, IOException {
        return apiRequest(String.format("/v2/characters/%s/equipment", charName), true).getAsJsonObject();
    }

    public JsonObject getAccountInventory() throws URISyntaxException, IOException {
        return apiRequest("/v2/account/inventory", true).getAsJsonObject();
    }

    public void setApiKey(String key) {
        apiKey = key;
    }
}
