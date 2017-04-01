package com.example.parktaejun.remind.Datas;

/**
 * Created by parktaejun on 2017. 4. 1..
 */

public class Data {

    private Object image;
    private Object date;
    private Object name;
    private Object weather;
    private Object memo;

    public Data(Object image, Object date, Object name, Object weather, Object memo){
        this.image = image;
        this.date = date;
        this.name = name;
        this.weather = weather;
        this.memo = memo;
    }

    public Object getImage(){
        return image;
    }
    public void setImage(Object image){
        this.image = image;
    }

    public Object getDate(){
        return date;
    }
    public void setDate(Object date){
        this.date = date;
    }

    public Object getName(){
        return name;
    }
    public void setName(Object name){
        this.name = name;
    }

    public Object getWeather(){
        return weather;
    }
    public void setWeather(Object weather){
        this.weather = weather;
    }

    public Object getMemo(){
        return memo;
    }
    public void setMemo(Object memo){
        this.memo = memo;
    }

}
