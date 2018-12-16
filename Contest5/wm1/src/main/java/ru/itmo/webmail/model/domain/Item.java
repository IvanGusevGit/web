package ru.itmo.webmail.model.domain;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

public class Item implements Serializable {
    private long id;
    private Date creationTime;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getIdString() {
        return Long.toString(id);
    }

    public void setIdString(String idString) {
        setId(Long.parseLong(idString));
    }

    public Date getCreationTime() {
        return creationTime;
    }

    public String getCreationTimeString() {
        return creationTime.toString();
    }

    public void setCreationTime(Date creationTime) {
        this.creationTime = creationTime;
    }

}
