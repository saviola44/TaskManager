package com.example.saviola44.taskmanager;

import com.example.saviola44.taskmanager.Model.Task;

import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;

/**
 * Created by saviola44 on 2016-06-05.
 */
public class NewestTask implements Comparator<Task> {
    @Override
    public int compare(Task lhs, Task rhs) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(lhs.getCreated());
        Date lCreated = cal.getTime();
        cal.setTimeInMillis(rhs.getCreated());
        Date rCreated = cal.getTime();
        if (lCreated.before(rCreated)) {
            return -1;
        }
        return 1;
    }
}
