package com.vehicle.information.trending.rtoexam.rto.Task_Model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class Category implements Serializable {
    private String icon;
    private int id;
    private String name;
    private List<Store> stores = new ArrayList();

    public int getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public String getIcon() {
        return this.icon;
    }

    public List<Store> getStores() {
        return this.stores;
    }
}
