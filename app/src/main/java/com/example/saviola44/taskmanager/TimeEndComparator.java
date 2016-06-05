package com.example.saviola44.taskmanager;

import com.example.saviola44.taskmanager.Model.Task;

import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;

/**
 * Created by saviola44 on 2016-06-05.
 */
public class TimeEndComparator implements Comparator<Task> {
    @Override
    public int compare(Task lhs, Task rhs) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(lhs.getCreated());
        Date lCreated = cal.getTime();
        cal.setTimeInMillis(rhs.getCreated());
        Date rCreated = cal.getTime();

        if(lhs.getTime_end()==rhs.getTime_end()){
            if(lCreated.before(rCreated)){
                return -1;
            }
            else{
                return 1;
            }
        }
        if(lhs.getTime_end()==0 && rhs.getTime_end()!=0){
            return 1;
        }
        if(lhs.getTime_end()!=0 && rhs.getTime_end()==0){
            return -1;
        }
        cal.setTimeInMillis(lhs.getTime_end());
        Date lTimeEnd = cal.getTime();
        cal.setTimeInMillis(rhs.getTime_end());
        Date rTimeEnd = cal.getTime();
        if(lTimeEnd.before(rTimeEnd)){
            return -1;
        }
        return 1;
    }
}
