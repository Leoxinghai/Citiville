package com.xiyu.util;

/**
 * Created by User on 2017-03-19.
 */
public class Event {
    public static int COMPLETE = 0;
    public static int RESIZE = 1;
    public static int ADDED_TO_STAGE = 2;
    public static int ENTER_FRAME = 3;
    private int event;
    public Event(int _event) {
        event = _event;
    }
 }
