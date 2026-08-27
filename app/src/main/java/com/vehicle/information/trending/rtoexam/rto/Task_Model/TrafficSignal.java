package com.vehicle.information.trending.rtoexam.rto.Task_Model;

import java.io.Serializable;


public class TrafficSignal implements Serializable {
    private String description;
    private int id;
    private String imageUrl;
    private String name;

    public int getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public String getDescription() {
        return this.description;
    }

    public String getImageUrl() {
        return this.imageUrl;
    }

    public String toString() {
        return "TrafficSignal{id=" + this.id + ", name='" + this.name + "', description='" + this.description + "', imageUrl='" + this.imageUrl + "'}";
    }
}
