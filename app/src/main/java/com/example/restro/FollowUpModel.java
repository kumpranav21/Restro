package com.example.restro;

public class FollowUpModel {
    int Img;
    String HotelName;
    String name;
    String HotelDesc;
    String desc;
    String price;
    String HotelPrice;
    int FoodImg;
    String Foodname;

    String Foodprice;
    FollowUpModel(String name, int Img, String desc,String price)
    {

        this.name = name;
        this.Img = Img;
        this.desc = desc;
        this.price = price;


    }

    FollowUpModel(String Foodname,String Foodprice, int FoodImg,String HotelName, String HotelPrice, String HotelDesc)
    {
        this.Foodname = Foodname;
        this.HotelName = HotelName;
        this.FoodImg = FoodImg;
        this.HotelDesc = HotelDesc;

        this.Foodprice = Foodprice;
        this.HotelPrice = HotelPrice;


    }

    public int getImg() {
        return Img;
    }

    public String getName() {
        return name;
    }

    public String getDesc() {
        return desc;
    }
    public String getPrice() {
        return price;
    }

    public int getFoodImg() {
        return FoodImg;
    }

    public String getFoodName() {
        return Foodname;
    }

    public String getFoodPrice() {
        return Foodprice;
    }

    public String getHotelame() {
        return HotelName;
    }

    public String getHotelPrice() {
        return HotelPrice;
    }

    public String getHotelDesc() {
        return HotelDesc;
    }
}
