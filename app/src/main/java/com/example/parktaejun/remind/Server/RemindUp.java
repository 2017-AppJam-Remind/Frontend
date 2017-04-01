package com.example.parktaejun.remind.Server;

import java.io.File;

/**
 * Created by parktaejun on 2017. 4. 1..
 */

public class RemindUp {
    public File image;
    public String date;
    public String name;
    public String memo;
    public String weather;

    public RemindUp(File image, String date, String name, String memo, String weather){
        this.image = image;
        this.date = date;
        this.name = name;
        this.memo = memo;
        this.weather = weather;
    }
}
