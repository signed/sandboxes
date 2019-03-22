package com.github.signed.tryanderror.resources.todo;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Todo {
    public String text;
    public boolean done;
    public int id;
    public String received;

    public Todo() {
        this(2010, "default text", false);
    }

    public Todo(int id, String text, boolean done) {
        this(id, new LocalDate(0).toDateTimeAtStartOfDay(), text, done);
    }

    public Todo(int id, DateTime receptionTime, String text, boolean done) {
        this.text = text;
        this.id = id;
        this.done = done;
        this.setReceptionTime(receptionTime);
    }
    
    public void setReceptionTime(DateTime dateTime){
        this.received = dateTime.toString("yyyyMMddHHmmss");
    }
}
