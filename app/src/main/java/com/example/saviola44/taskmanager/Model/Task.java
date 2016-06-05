package com.example.saviola44.taskmanager.Model;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

import com.orm.SugarRecord;
import com.orm.dsl.Ignore;

/**
 * Created by saviola44 on 02.06.16.
 */
public class Task extends SugarRecord implements Parcelable {
    private long id;
    String title;
    long created; //czas ustworzenie zadania
    long time_end; //czas do kiedy zadanie musimy zrealizowac
    String description;
    String url_to_icon;
    public Task(){}

    public Task(long id) {
        this.id = id;
    }

    protected Task(Parcel in){
        id = in.readLong();
        title = in.readString();
        created = in.readLong();
        time_end = in.readLong();
        description = in.readString();
        url_to_icon = in.readString();
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setTime_end(long time_end) {
        this.time_end = time_end;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setUrl_to_icon(String url_to_icon) {
        this.url_to_icon = url_to_icon;
    }

    public void setCreated(long created) {
        this.created = created;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public long getCreated() {
        return created;
    }

    public String getUrl_to_icon() {
        return url_to_icon;
    }

    public String getDescription() {
        return description;
    }

    public long getTime_end() {
        return time_end;
    }


    @Ignore
    public static final Creator<Task> CREATOR = new Creator<Task>() {
        @Override
        public Task createFromParcel(Parcel in) {
            return new Task(in);
        }

        @Override
        public Task[] newArray(int size) {
            return new Task[size];
        }
    };



    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(title);
        dest.writeLong(created);
        dest.writeLong(time_end);
        dest.writeString(description);
        if(url_to_icon==null || url_to_icon.toString()==null){
            dest.writeString("");
        }else{
            dest.writeString(url_to_icon.toString());
        }
    }

}
