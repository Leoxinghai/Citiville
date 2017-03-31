package com.xiyu.logic;

import java.util.Vector;

/**
 * Created by User on 2017-03-20.
 */
public class DisplayObject {
    public DisplayObject parent;
    public int width;
    public int height;

    private Vector<DisplayObject> children;

    public DisplayObject() {
    }

    public DisplayObject getChildAt(int index) {
        return children.get(index);
    }

    public void removeChild(int index) {
        children.remove(index);
    }

    public void removeChild(Object obj) {
        children.remove(obj);
    }
}
