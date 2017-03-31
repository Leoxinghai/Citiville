package com.facebook.data.photos;

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

    public class TagData
    {
        protected String _actualText ;
        protected double _actualX ;
        protected double _actualY ;
        public String pid ;
        public Date created ;
        public String tag_uid ;
        public String subject ;

        public  TagData ()
        {
            return;
        }//end

        public void  y (double param1 )
        {
            this._actualY = param1;
            return;
        }//end

        public void  text (String param1 )
        {
            this._actualText = param1;
            return;
        }//end

        public double  ycoord ()
        {
            return this._actualY;
        }//end

        public void  tag_text (String param1 )
        {
            this._actualText = param1;
            return;
        }//end

        public String  text ()
        {
            return this._actualText;
        }//end

        public void  x (double param1 )
        {
            this._actualX = param1;
            return;
        }//end

        public String  tag_text ()
        {
            return this._actualText;
        }//end

        public void  ycoord (double param1 )
        {
            this._actualY = param1;
            return;
        }//end

        public double  y ()
        {
            return this._actualY;
        }//end

        public void  xcoord (double param1 )
        {
            this._actualX = param1;
            return;
        }//end

        public double  xcoord ()
        {
            return this._actualX;
        }//end

        public double  x ()
        {
            return this._actualX;
        }//end

    }


