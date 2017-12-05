package com.supermarket.Classes;

/**
 * Created by sam on 12/4/17.
 */

public class Product {
    public String ID;
    public String ProductName;
    public String Size;
    public String Price;
    public String Image;
    public String Details;
    public String SubCategoryID;
    public String BrandID;
    public String AVGRating;

    public Product(String ID, String productName, String size, String price, String image, String details, String subCategoryID, String brandID, String AVGRating) {
        this.ID = ID;
        this.ProductName = productName;
        this.Size = size;
        this.Price = price;
        this.Image = image;
        this.Details = details;
        this.SubCategoryID = subCategoryID;
        this.BrandID = brandID;
        this.AVGRating = AVGRating;
    }
}
