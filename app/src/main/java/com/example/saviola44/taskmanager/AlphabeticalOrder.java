package com.example.saviola44.taskmanager;

import com.example.saviola44.taskmanager.Model.Task;

import java.util.Comparator;

/**
 * Created by saviola44 on 2016-06-05.
 */
public class AlphabeticalOrder implements Comparator<Task> {
    @Override
    public int compare(Task lhs, Task rhs) {
        if(lhs.getTitle().equals(rhs.getTitle())){
            TimeEndComparator c = new TimeEndComparator();
            return c.compare(lhs, rhs);
        }
        else {
            return lhs.getTitle().compareTo(rhs.getTitle());
        }
    }
}
