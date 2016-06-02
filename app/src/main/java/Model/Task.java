package Model;

import com.orm.dsl.Table;

/**
 * Created by saviola44 on 02.06.16.
 */
@Table
public class Task {
    private long id;
    String title;
    long created; //czas ustworzenie zadania
    long time_end; //czas do kiedy zadanie musimy zrealizowac
    String description;
    String url_to_icon;
    public Task(){}

    public Task(String title, long created, long id) {
        this.title = title;
        this.created = created;
        this.id = id;
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

    public long getId() {

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
}
