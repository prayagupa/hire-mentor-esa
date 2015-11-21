package com.hireartists.domains;

import java.util.Date;

/**
 * Created by prayagupd on 11/16/15.
 */
public class Concert {
    String name;
    Date date;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
