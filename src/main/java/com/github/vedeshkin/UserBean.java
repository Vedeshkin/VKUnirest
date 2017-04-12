package com.github.vedeshkin;

/**
 * Created by vvedeshkin on 4/12/2017.
 */
public class UserBean {
    private final int UID;
    private final String userName;

    public UserBean(int UID, String userName) {
        this.UID = UID;
        this.userName = userName;
    }

    public int getUID() {
        return UID;
    }

    public String getUserName() {
        return userName;
    }
}
