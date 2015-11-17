package com.hireartists.domains;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by prayagupd
 * on 11/11/15.
 */

public class Artist {
    private String name;
    private String type;
    private List<Concert> concerts = new ArrayList<>();

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

    public List<Concert> getConcerts() {
        return concerts;
    }

    public void setConcerts(List<Concert> concerts) {
        this.concerts = concerts;
    }
}
