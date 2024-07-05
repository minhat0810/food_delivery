package com.example.dacs3_fodr;

import java.io.Serializable;

public class Foods implements Serializable {
    private boolean BestFood;
    private int CategoryId;
    private String Description;
    private int Id;
    private int IdShop;
    private String ShopName;
    private String ImagePath;
    private int LocationId;

    private Double Price;

    private int PriceId;
    private double Star;
    private int TimeId;
    private String TimeValue;
    private String Title;
    private String address;
    public Foods(){}



//    public Foods(boolean bestFood, int categoryId, String description, int id, int idShop, String shopName, String imagePath, int locationId, Double price, int priceId, double star, int timeId, int timeValue, String title) {
//        BestFood = bestFood;
//        CategoryId = categoryId;
//        Description = description;
//        Id = id;
//        IdShop = idShop;
//        ShopName = shopName;
//        ImagePath = imagePath;
//        LocationId = locationId;
//        Price = price;
//        PriceId = priceId;
//        Star = star;
//        TimeId = timeId;
//        TimeValue = timeValue;
//        Title = title;
//    }

    public Foods(boolean bestFood, int categoryId, String description, int id, int idShop, String shopName, String imagePath, int locationId, Double price, int priceId, double star, int timeId, String timeValue, String title, String address) {
        BestFood = bestFood;
        CategoryId = categoryId;
        Description = description;
        Id = id;
        IdShop = idShop;
        ShopName = shopName;
        ImagePath = imagePath;
        LocationId = locationId;
        Price = price;
        PriceId = priceId;
        Star = star;
        TimeId = timeId;
        TimeValue = timeValue;
        Title = title;
        this.address = address;
    }

    @Override
    public String toString() {
        return "Foods{" +
                "Title='" + Title + '\'' +
                '}';
    }


    public boolean isBestFood() {
        return BestFood;
    }

    public void setBestFood(boolean bestFood) {
        BestFood = bestFood;
    }

    public int getCategoryId() {
        return CategoryId;
    }

    public void setCategoryId(int categoryId) {
        CategoryId = categoryId;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public int getIdShop() {
        return IdShop;
    }

    public void setIdShop(int idShop) {
        IdShop = idShop;
    }

    public String getShopName() {
        return ShopName;
    }

    public void setShopName(String shopName) {
        ShopName = shopName;
    }

    public String getImagePath() {
        return ImagePath;
    }

    public void setImagePath(String imagePath) {
        ImagePath = imagePath;
    }

    public int getLocationId() {
        return LocationId;
    }

    public void setLocationId(int locationId) {
        LocationId = locationId;
    }

    public Double getPrice() {
        return Price;
    }

    public void setPrice(Double price) {
        Price = price;
    }

    public int getPriceId() {
        return PriceId;
    }

    public void setPriceId(int priceId) {
        PriceId = priceId;
    }

    public double getStar() {
        return Star;
    }

    public void setStar(double star) {
        Star = star;
    }

    public int getTimeId() {
        return TimeId;
    }

    public void setTimeId(int timeId) {
        TimeId = timeId;
    }

    public String getTimeValue() {
        return TimeValue;
    }

    public void setTimeValue(String timeValue) {
        TimeValue = timeValue;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
