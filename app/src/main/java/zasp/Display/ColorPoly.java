package zasp.Display;

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
import android.graphics.drawable.shapes.Shape;
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

//import flash.display.*;
    public class ColorPoly extends Shape
    {
        private Array vecX ;
        private Array vecY ;

        public  ColorPoly ()
        {
            this.vecX = new Array();
            this.vecY = new Array();
            return;
        }//end

        public void  addPt (double param1 ,double param2 )
        {
            this.vecX.push(param1);
            this.vecY.push(param2);
            return;
        }//end

        public void  changeColor (int param1 )
        {
            if (this.vecX.length == 0 || this.vecY.length == 0)
            {
                return;
            }
            graphics.clear();
            graphics.lineStyle(1, param1);
            graphics.beginFill(param1);
            graphics.moveTo(this.vecX.get(0), this.vecY.get(0));
            int _loc_2 =1;
            while (_loc_2 < this.vecX.length())
            {

                graphics.lineTo(this.vecX.get(_loc_2), this.vecY.get(_loc_2));
                _loc_2++;
            }
            graphics.endFill();
            return;
        }//end

    }



