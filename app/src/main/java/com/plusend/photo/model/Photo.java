package com.plusend.photo.model;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

/**
 * Created by plusend on 16/1/24.
 */
public class Photo implements Parcelable {

    private int id;
    private Bitmap pic;
    private String note;
    private Date date;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Bitmap getPic() {
        return pic;
    }

    public void setPic(Bitmap pic) {
        this.pic = pic;
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

    public Photo() {
    }

    @Override
    public String toString() {
        return "Photo{" +
                "id=" + id +
                ", pic=" + pic +
                ", note='" + note + '\'' +
                ", date=" + date +
                '}';
    }
    
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeParcelable(this.pic, 0);
        dest.writeString(this.note);
        dest.writeLong(date != null ? date.getTime() : -1);
    }

    protected Photo(Parcel in) {
        this.id = in.readInt();
        this.pic = in.readParcelable(Bitmap.class.getClassLoader());
        this.note = in.readString();
        long tmpDate = in.readLong();
        this.date = tmpDate == -1 ? null : new Date(tmpDate);
    }

    public static final Creator<Photo> CREATOR = new Creator<Photo>() {
        public Photo createFromParcel(Parcel source) {
            return new Photo(source);
        }

        public Photo[] newArray(int size) {
            return new Photo[size];
        }
    };
}
