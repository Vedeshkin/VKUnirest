package com.github.vedeshkin;

import java.util.HashMap;

/**
 * Created by vvedeshkin on 4/11/2017.
 */
public class StatusHolder {

    private HashMap<Integer, Status> statuses;
    private static StatusHolder instance;

    private StatusHolder() {
        statuses = new HashMap<>();
    }

    public static synchronized StatusHolder getInstance() {
        if (instance == null) {
            instance = new StatusHolder();
        }
        return instance;
    }
    public Status getStatusEntity (int UID)
    {
        return statuses.get(UID);
    }
    public void putStatusEntity(int UID,Status statusEntity)
    {
        statuses.put(UID,statusEntity);
    }

}
