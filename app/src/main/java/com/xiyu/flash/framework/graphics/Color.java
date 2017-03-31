package com.xiyu.flash.framework.graphics;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
////import android.graphics.Color;
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
import com.xiyu.util.*;

    public class Color {
        public static Color  add (Color a ,Color b ){
            int alpha =(a.alpha +b.alpha );
            int red =(a.red +b.red );
            int green =(a.green +b.green );
            int blue =(a.blue +b.blue );
            return (ARGB((((alpha > 1)) ? 1 : alpha), (((red > 1)) ? 1 : red), (((green > 1)) ? 1 : green), (((blue > 1)) ? 1 : blue)));
        }
        public static Color  Gray (int gray ){
            return (ARGB(1, gray, gray, gray));
        }
        public static Color  RGB (int red ,int green ,int blue ){
            return (ARGB(1, red, green, blue));
        }
        public static Color  fromInt (int value ){
            return (ARGB(((((value & 0xFF000000) >> 24) & 0xFF) / 0xFF), ((((value & 0xFF0000) >> 16) & 0xFF) / 0xFF), ((((value & 0xFF00) >> 8) & 0xFF) / 0xFF), ((((value & 0xFF) >> 0) & 0xFF) / 0xFF)));
        }
        public static Color  ARGB (int alpha ,int red ,int green ,int blue ){
            if ((((alpha < 0)) || ((alpha > 1))))
            {
                ////throw (new ArgumentError(("Alpha channel must be in the range .get(0.0, 1.0), was " + alpha)));
            };
            if ((((red < 0)) || ((red > 1))))
            {
                ////throw (new ArgumentError(("Red channel must be in the range .get(0.0, 1.0), was " + red)));
            };
            if ((((green < 0)) || ((green > 1))))
            {
                ////throw (new ArgumentError(("Green channel must be in the range .get(0.0, 1.0), was " + green)));
            };
            if ((((blue < 0)) || ((blue > 1))))
            {
                ////throw (new ArgumentError(("Blue channel must be in the range .get(0.0, 1.0), was " + blue)));
            };
            Color aColor =new Color();
            aColor.alpha = (int)alpha;
            aColor.red = (int)red;
            aColor.green = (int)green;
            aColor.blue = blue;
            return (aColor);
        }

        public int green =0;

        public String  toString (){
            return ((((((((("[" + this.alpha) + ", ") + this.red) + ", ") + this.green) + ", ") + this.blue) + "]"));
        }

        public int red =0;

        public void  copy (Color other ){
            this.alpha = other.alpha;
            this.red = other.red;
            this.green = other.green;
            this.blue = other.blue;
        }

        public int blue =0;

        public int  toInt (){
            return ((((((this.alpha & 0xFF) << 24) | ((this.red & 0xFF) << 16)) | ((this.green & 0xFF) << 8)) | ((this.blue & 0xFF) << 0)));
        }

        public int alpha =0;

    }


