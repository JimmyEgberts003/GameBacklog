package com.egberts.jimmy.gamebacklog;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;

@Entity(tableName = "game")
public class Game implements Parcelable {

    @PrimaryKey(autoGenerate = true)
    private long id;

    @ColumnInfo(name = "title")
    private String title;
    @ColumnInfo(name = "platform")
    private String platform;
    @ColumnInfo(name = "notes")
    private String notes;
    @ColumnInfo(name = "status")
    private String status;
    @ColumnInfo(name = "date")
    private String date;


    public Game(String title, String platform, String notes, String status, String date) {
        this.title = title;
        this.platform = platform;
        this.notes = notes;
        this.status = status;
        this.date = date;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.id);
        dest.writeString(this.title);
        dest.writeString(this.platform);
        dest.writeString(this.notes);
        dest.writeString(this.status);
        dest.writeString(this.date);
    }

    protected Game(Parcel in) {
        this.id = in.readLong();
        this.title = in.readString();
        this.platform = in.readString();
        this.notes = in.readString();
        this.status = in.readString();
        this.date = in.readString();
    }

    public static final Creator<Game> CREATOR = new Creator<Game>() {
        @Override
        public Game createFromParcel(Parcel source) {
            return new Game(source);
        }

        @Override
        public Game[] newArray(int size) {
            return new Game[size];
        }
    };

}
