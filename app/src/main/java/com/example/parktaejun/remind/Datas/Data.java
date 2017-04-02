package com.example.parktaejun.remind.Datas;

/**
 * Created by parktaejun on 2017. 4. 1..
 */

public class Data {

    private String image;
    private String date;
    private String name;
    private String weather;
    private String memo;

    public Data(String name, String image, String date, String weather, String memo){
        this.image = image;
        this.date = date;
        this.name = name;
        this.weather = weather;
        this.memo = memo;
    }

    public String getImage(){
        return image;
    }
    public void setImage(String image){
        this.image = image;
    }

    public String getDate(){
        return date;
    }
    public void setDate(String date){
        this.date = date;
    }

    public String getName(){
        return name;
    }
    public void setName(String name){
        this.name = name;
    }

    public String getWeather(){
        return weather;
    }
    public void setWeather(String weather){
        this.weather = weather;
    }

    public String getMemo(){
        return memo;
    }
    public void setMemo(String memo){
        this.memo = memo;
    }

}
