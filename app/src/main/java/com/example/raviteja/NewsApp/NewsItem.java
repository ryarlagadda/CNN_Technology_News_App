package com.example.raviteja.NewsApp;

import java.io.Serializable;
import java.text.SimpleDateFormat;

/**
 * Created by Advait on 26-09-2016.
 */
public class NewsItem implements Serializable {
    String title, description, link, puD, url;
    SimpleDateFormat pubDate;

    public SimpleDateFormat getPubDate() {
        return pubDate;
    }

    public void setPubDate(SimpleDateFormat pubDate) {
        this.pubDate = pubDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getPuD() {
        return puD;
    }

    public void setPuD(String puD) {
        this.puD = puD;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "NewsItem{" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", link='" + link + '\'' +
                ", pubDate='" + puD + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
