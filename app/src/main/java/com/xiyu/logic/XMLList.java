package com.xiyu.logic;

import java.util.Hashtable;
import java.util.Vector;

/**
 * Created by User on 2017-03-21.
 */
public class XMLList {
    Vector mXmlTables;

    public XMLList() {
    }

    public XMLList(String xml) {
    }

    public Object get(int index) {
        return mXmlTables.get(index);
    }

    public int size() {
        return mXmlTables.size();
    }
}
