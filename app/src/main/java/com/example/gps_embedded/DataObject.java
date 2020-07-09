package com.example.gps_embedded;

import com.google.gson.annotations.SerializedName;

public class DataObject {
    @SerializedName("data")
    private PositionObject positionObject;


    public DataObject(PositionObject positionObject) {
        this.positionObject = positionObject;
    }

    public PositionObject getPositionObject() {
        return positionObject;
    }
}
