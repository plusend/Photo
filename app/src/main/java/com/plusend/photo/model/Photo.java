package com.plusend.photo.model;

import android.graphics.Bitmap;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by plusend on 16/1/24.
 */
public class Photo {
    
    private Bitmap pic;
    private int level;
    private String note;
    private Date date;

    public Bitmap getPic() {
        return pic;
    }

    public void setPic(Bitmap pic) {
        this.pic = pic;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Photo{" +
                "pic=" + pic +
                ", level=" + level +
                ", note='" + note + '\'' +
                ", date=" + date +
                '}';
    }
}
