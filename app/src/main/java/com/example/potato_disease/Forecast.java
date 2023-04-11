package com.example.potato_disease;

import java.util.Date;

public class Forecast {
    public String date;
    public String icon;
    public String text;
    public String max_temp;
    public String min_temp;

    Forecast(String date,String icon,String max_temp,String min_temp)
    {
        this.date=date;
        this.icon =icon;

        this.max_temp=max_temp;
        this.min_temp=min_temp;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getMax_temp() {
        return max_temp;
    }

    public void setMax_temp(String max_temp) {
        this.max_temp = max_temp;
    }

    public String getMin_temp() {
        return min_temp;
    }

    public void setMin_temp(String min_temp) {
        this.min_temp = min_temp;
    }
}
