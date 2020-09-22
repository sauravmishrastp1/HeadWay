package com.headwayagent.salesadviser_headwaygms.models;

public class Howto_earn_model {
    private String link;
    private String name;

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Howto_earn_model(String link, String name) {
        this.link = link;
        this.name = name;
    }
}
