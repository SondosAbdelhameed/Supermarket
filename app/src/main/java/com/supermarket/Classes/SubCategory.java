package com.supermarket.Classes;


/**
 * Created by sam on 12/3/17.
 */

public class SubCategory {
    public String ID;
    public String SubCategoryName;
    public String CategoryID;

    public SubCategory(String ID, String subCategoryName, String categoryID) {
        this.ID = ID;
        this.SubCategoryName = subCategoryName;
        this.CategoryID = categoryID;
    }
}
