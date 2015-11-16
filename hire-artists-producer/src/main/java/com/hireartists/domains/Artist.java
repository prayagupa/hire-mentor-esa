package com.hireartists.domains;

/**
 * Created by prayagupd
 * on 11/11/15.
 */

public class Artist {
    private String name;
    private String type;

    public Artist(String name, String type) {
        this.name = name;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
