package com.vehicle.information.trending.rtoexam.rto.Task_Model;

import java.io.Serializable;
import java.util.List;


public class PopularCarBike implements Serializable {
    private List<BikeModel> bikes;
    private List<CarModel> cars;

    public List<CarModel> getCars() {
        return this.cars;
    }

    public List<BikeModel> getBikes() {
        return this.bikes;
    }

    public String toString() {
        return "PopularCarBike{cars=" + this.cars + ", bikes=" + this.bikes + '}';
    }
}
