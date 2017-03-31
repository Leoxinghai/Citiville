package com.xiyu.logic;

import com.xiyu.util.Array;

/**
 * Created by User on 2017-03-21.
 */
public class XML {
    private XMLList children;
    public XML() {
    }

    public XMLList children() {
        XMLList result = new XMLList("");
        return result;
    }

    public void setChildren(XMLList _children) {
        children = _children;
    }

    public XML get(int index) {
        return (XML)children.get(index);
    }
}
