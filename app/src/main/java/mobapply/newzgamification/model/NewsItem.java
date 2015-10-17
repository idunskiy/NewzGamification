package mobapply.newzgamification.model;

import java.util.Date;

/**
 * Created by ${IvanDunskiy} on 10/17/15.
 * Copyright (c) 2015 ${MobApply}. All rights reserved.
 */
public class NewsItem {
    Date date;
    String summary;
    String url;
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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public NewsItem(Date date, String summary, String url, String image) {
        this.date = date;
        this.summary = summary;
        this.url = url;
        this.image = image;
    }

    @Override
    public String toString() {
        return "NewsItem{" +
                "date=" + date +
                ", summary='" + summary + '\'' +
                ", url='" + url + '\'' +
                ", image='" + image + '\'' +
                '}';
    }
}
