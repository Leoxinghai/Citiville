package com.xinghai.chat;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.Window;
import android.view.WindowManager;
import android.graphics.*;

import com.xiyu.util.Array;
import com.xiyu.util.Dictionary;

import mx.modules.*;
    public class SceneModuleBase extends Module
    {
        private int _moduleIndex ;
        public String url ;
        public boolean isOnly =true ;
        public boolean isRemeber =true ;

        public  SceneModuleBase ()
        {
            width = 1000;
            height = 600;
            layout = "absolute";
            verticalScrollPolicy = "off";
            horizontalScrollPolicy = "off";
            return;
        }//end  

        public void  hide ()
        {
            if (parent)
            {
                parent.removeChild(this);
            }
            return;
        }//end  

        public void  moduleIndex (int param1 )
        {
            this._moduleIndex = param1;
            return;
        }//end  

        public int  moduleIndex ()
        {
            return this._moduleIndex;
        }//end  

        public void  refresh ()
        {
            return;
        }//end  

        public void  show ()
        {
            return;
        }//end  

    }

