package com.supermarket.Classes;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sam on 12/2/17.
 */

public class Category {
    public String ID;
    public String CategoryName;
    public List<SubCategory> subCategories;

    public Category(String ID,String CategoryName,List<SubCategory> subCategories){
        this.ID = ID;
        this.CategoryName = CategoryName;
        this.subCategories = new ArrayList<>(subCategories);
    }
}
