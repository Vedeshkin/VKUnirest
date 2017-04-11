package com.github.vedeshkin;

import java.time.Instant;

/**
 * Created by vvedeshkin on 4/11/2017.
 */
public class Status {
    private final int UID;
    private final boolean isOnline;
    private final boolean isOnlineMobile;
    private final Instant timestamp;

    public Status(int UID, boolean isOnline, boolean isOnlineMobile) {
        this.UID = UID;
        this.isOnline = isOnline;
        this.isOnlineMobile = isOnlineMobile;
        this.timestamp = Instant.now();
    }

    public int getUID() {
        return UID;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public boolean isOnline() {
        return isOnline;
    }

    public boolean isOnlineMobile() {
        return isOnlineMobile;
    }

    @Override
    public String toString() {
        return isOnlineMobile()?"Online from mobile application":isOnline()?"Online":"Offline";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Status status = (Status) o;

        if (getUID() != status.getUID()) return false;
        if (isOnline() != status.isOnline()) return false;
        return isOnlineMobile() == status.isOnlineMobile();
    }

    @Override
    public int hashCode() {
        int result = getUID();
        result = 31 * result + (isOnline() ? 1 : 0);
        result = 31 * result + (isOnlineMobile() ? 1 : 0);
        return result;
    }
}

