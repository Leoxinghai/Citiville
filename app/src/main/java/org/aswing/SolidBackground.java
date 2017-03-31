package org.aswing;

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

//import flash.display.*;
import org.aswing.geom.*;
import org.aswing.graphics.*;

    public class SolidBackground extends Object implements GroundDecorator
    {
        protected ASColor color ;
        protected Shape shape ;
        protected boolean ignorBorderMargin ;
        protected Insets margin ;

        public  SolidBackground (ASColor param1 ,boolean param2 =false ,Insets param3 =null )
        {
            this.color = param1;
            this.ignorBorderMargin = param2;
            this.margin = param3;
            if (param3 == null)
            {
                this.margin = new Insets();
            }
            this.shape = new Shape();
            return;
        }//end  

        public void  updateDecorator (Component param1 ,Graphics2D param2 ,IntRectangle param3 )
        {
            this.shape.graphics.clear();
            param2 = new Graphics2D(this.shape.graphics);
            if (this.ignorBorderMargin)
            {
                param2.fillRectangle(new SolidBrush(this.color), this.margin.left, this.margin.top, param1.width - (this.margin.left + this.margin.right), param1.height - (this.margin.top + this.margin.bottom));
            }
            else
            {
                param2.fillRectangle(new SolidBrush(this.color), param3.x + this.margin.left, param3.y + this.margin.top, param3.width - (this.margin.left + this.margin.right), param3.height - (this.margin.top + this.margin.bottom));
            }
            return;
        }//end  

        public DisplayObject  getDisplay (Component param1 )
        {
            return this.shape;
        }//end  

    }


