package org.health.supplychain.webservice.entities;

/**
 * Created by aworkneh on 8/13/2018.
 */

public class ServerTimeResponse {
    private long currentDateTime;
    private String timeZone;

    public long getCurrentDateTime() {
        return currentDateTime;
    }

    public void setCurrentDateTime(long currentDateTime) {
        this.currentDateTime = currentDateTime;
    }

    public String getTimeZone() {
        return timeZone;
    }

    public void setTimeZone(String timeZone) {
        this.timeZone = timeZone;
    }
}
