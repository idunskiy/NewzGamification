package mobapply.newzgamification.model;

import java.util.Date;

/**
 * Created by ${IvanDunskiy} on 10/17/15.
 * Copyright (c) 2015 ${MobApply}. All rights reserved.
 */
public class NewsItem {
    Date date;
    String summary;
    String textFull;
    String image;

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getTextFull() {
        return textFull;
    }

    public void setTextFull(String textFull) {
        this.textFull = textFull;
    }

    public NewsItem(Date date, String summary, String textFull, String image) {
        this.date = date;
        this.summary = summary;
        this.textFull = textFull;
        this.image = image;
    }

    @Override
    public String toString() {
        return "NewsItem{" +
                "date=" + date +
                ", summary='" + summary + '\'' +
                ", textFull='" + textFull + '\'' +
                ", image='" + image + '\'' +
                '}';
    }
}
