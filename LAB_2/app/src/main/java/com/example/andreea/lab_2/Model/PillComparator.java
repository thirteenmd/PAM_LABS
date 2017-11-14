package com.example.andreea.lab_2.Model;

import java.util.Comparator;

/**
 * Created by Andreea on 11/12/2017.
 */

public class PillComparator implements Comparator<Pill> {
    @Override
    public int compare(Pill pill1, Pill pill2) {
        String firstName = pill1.getPillName();
        String secondName = pill2.getPillName();
        return firstName.compareTo(secondName);
    }
}
