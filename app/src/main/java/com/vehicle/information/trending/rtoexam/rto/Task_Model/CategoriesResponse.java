package com.vehicle.information.trending.rtoexam.rto.Task_Model;

import java.io.Serializable;
import java.util.List;


public class CategoriesResponse implements Serializable {
    private List<Category> categories;

    public List<Category> getCategories() {
        return this.categories;
    }
}
