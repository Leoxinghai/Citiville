package Engine.Helpers;

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

//import flash.geom.*;
    public class IntVector2
    {
        public int x ;
        public int y ;

        public  IntVector2 (int param1 =0,int param2)
        {
            this.x = param1;
            this.y = param2;
            return;
        }//end

        public IntVector2  clone ()
        {
            return new IntVector2(this.x, this.y);
        }//end

        public IntVector2  add (IntVector2 param1 )
        {
            IntVector2 _loc_2 =new IntVector2(this.x +param1.x ,this.y +param1.y );
            return _loc_2;
        }//end

        public IntVector2  subtract (IntVector2 param1 )
        {
            IntVector2 _loc_2 =new IntVector2(this.x -param1.x ,this.y -param1.y );
            return _loc_2;
        }//end

        public boolean  equals (IntVector2 param1 )
        {
            return this.x == param1.x && this.y == param1.y;
        }//end

        public String  toString ()
        {
            return String("(x: " + this.x + ", y: " + this.y + ")");
        }//end

        public Point  toPoint ()
        {
            return new Point(this.x, this.y);
        }//end

    }



