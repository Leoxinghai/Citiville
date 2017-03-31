package com.xiyu.logic;

import android.graphics.Canvas;

/**
 * Created by User on 2017-03-20.
 */
public class Sprite extends  DisplayObject {
    protected String name;
    public Sprite parent;
    public DisplayObject stage;
    public Canvas graphics;
    public int stageWidth;
    public int stageHeight;

    public Sprite(){
    }
}
