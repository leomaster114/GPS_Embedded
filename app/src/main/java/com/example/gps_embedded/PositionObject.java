package com.example.gps_embedded;

public class PositionObject {
    private String lat;
    private String lng;
    private String time;

    public PositionObject(String lat, String lng, String time) {
        this.lat = lat;
        this.lng = lng;
        this.time = time;
    }

    public String getLat() {
        return lat;
    }

    public String getLng() {
        return lng;
    }

    public String getTime() {
        return time;
    }
}
