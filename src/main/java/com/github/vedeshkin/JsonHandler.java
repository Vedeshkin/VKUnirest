package com.github.vedeshkin;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by vvedeshkin on 4/11/2017.
 */
public   class JsonHandler {

    private JSONObject jsonObject;
    private JSONArray responses;

    public JsonHandler(JSONObject jsonObject) {
        this.jsonObject = jsonObject;
        parseJSON();
    }
    private void  parseJSON(){


        try
        {
           this.responses =  jsonObject.getJSONArray("response");
        }
        catch (JSONException exp)
        {
            exp.printStackTrace();
        }
    }
    public void getNames(){

        if (responses == null)
        {
            //add more logic here
            System.out.println("Responses is null");
            return;
        }
        for (int i = 0 ;i < responses.length(); i++)
        {
            String first_name = responses.getJSONObject(i).getString("first_name");
            String last_name = responses.getJSONObject(i).getString("last_name");
            int UID = responses.getJSONObject(i).getInt("uid");
            System.out.printf("UID: %10d| %s %s \n",UID,first_name,last_name);
        }

    }

    public void getStatuses()
    {
        if (responses == null)
        {
            //add more logic here
            System.out.println("Responses is null");
            return;
        }
        for (int i = 0 ;i < responses.length(); i++) {
        boolean isOnline = responses.getJSONObject(i).getInt("online") == 1;
        boolean isOnlineMobile = responses.getJSONObject(i).has("online_mobile");
        int UID = responses.getJSONObject(i).getInt("uid");
        String status = isOnlineMobile? "Online from mobile application": isOnline? "Online":"Offline";
        System.out.printf("UID %10d| Status :%s \n",UID,status);
        }

    }
    public void storeAllStatuses()
    {
        if (responses == null)
        {
            //add more logic here
            System.out.println("Responses is null");
            return;
        }
        StatusHolder sh = StatusHolder.getInstance();
        for (int i = 0 ;i < responses.length(); i++) {
            Status statusEntity = createStatusEntity(responses.getJSONObject(i));
            sh.putStatusEntity(statusEntity.getUID(),statusEntity);

        }
        System.out.printf("All statuses has been stored \n");
    }
    public void compareAndUpdate()
    {
        StatusHolder sh = StatusHolder.getInstance();
        if (responses == null)
        {
            //add more logic here
            System.out.println("Responses is null");
            return;
        }
        int countUpdate = 0;
        for (int i = 0 ;i < responses.length(); i++) {

            Status current = createStatusEntity(responses.getJSONObject(i));
            Status previous = sh.getStatusEntity(current.getUID());
            if(!current.equals(previous))
            {
                System.out.printf("Entity has changed status:\nWas: %s\nNow: %s\n",previous,current);
                //here we should store to database our changes e.g. UID STATUS Begin end(Instant.now())
                sh.putStatusEntity(current.getUID(),current);
                countUpdate++;
            }
        }
        System.out.printf("Processed:%d,Updated %d \n",responses.length(),countUpdate);

    }
    private Status createStatusEntity(JSONObject jo)
    {
        boolean isOnline = jo.getInt("online") == 1;
        boolean isOnlineMobile = jo.has("online_mobile");
        int UID = jo.getInt("uid");
        return new Status(UID,isOnline,isOnlineMobile);
    }

}
