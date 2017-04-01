package com.example.parktaejun.remind.Server;

/**
 * Created by parktaejun on 2017. 4. 1..
 */

public class Remind {
    public String image;
    public String date;
    public String name;
    public String memo;
    public String weather;

    public Remind(String image, String date, String name, String memo, String weather){
        this.image = image;
        this.date = date;
        this.name = name;
        this.memo = memo;
        this.weather = weather;
    }
}
