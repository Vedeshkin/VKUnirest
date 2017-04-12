package com.github.vedeshkin;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClients;

import java.util.TimerTask;

/**
 * Created by vvedeshkin on 4/11/2017.
 */
public class Workflow  extends TimerTask{

 public void initialize()
 {
     HttpClient httpClient = HttpClients.custom()
             .disableCookieManagement()
             .build();
     Unirest.setHttpClient(httpClient);

     HttpResponse<JsonNode> response = null;
     try {
         response = Unirest.get("https://api.vk.com/method/friends.get")
                 .queryString("user_id", "1510962")
                 .queryString("fields", "online")
                 .asJson();
     } catch (UnirestException e) {
         e.printStackTrace();
     }
     if (response != null) {
         JsonHandler jh = new JsonHandler(response.getBody().getObject());
         jh.storeAllStatuses();
         UserDAO userDAO = new UserDaoImpl();
         userDAO.deleteAllUsers();
         userDAO.storeUsers(jh.getNames());
     }
     System.out.println("Initialized");
 }

    private int runCount = 0;
    @Override
    public void run() {
        HttpResponse<JsonNode> response = null;
        try {
            response = Unirest.get("https://api.vk.com/method/friends.get")
                    .queryString("user_id", "1510962")
                    .queryString("fields", "online")
                    .asJson();
        } catch (UnirestException e) {
            e.printStackTrace();
        }
        if (response != null) {
            JsonHandler jh = new JsonHandler(response.getBody().getObject());
            jh.compareAndUpdate();
            runCount++;
        }
        System.out.printf("Workflow %d has been processed \n",runCount);
    }
}
