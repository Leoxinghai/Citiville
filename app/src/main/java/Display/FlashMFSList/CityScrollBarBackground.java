package Display.FlashMFSList;

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

import Classes.*;
//import flash.display.*;
import org.aswing.*;
import org.aswing.geom.*;
import org.aswing.graphics.*;
import org.aswing.plaf.basic.background.*;

    public class CityScrollBarBackground extends ScrollBarBackground
    {
        protected Sprite bg ;

        public  CityScrollBarBackground ()
        {
            this.bg = new Sprite();
            return;
        }//end

         public DisplayObject  getDisplay (Component param1 )
        {
            return this.bg;
        }//end

         public void  updateDecorator (Component param1 ,Graphics2D param2 ,IntRectangle param3 )
        {
            DisplayObject _loc_4 =null ;
            if (param1 instanceof Orientable && param1.isOpaque())
            {
                while (this.bg.numChildren > 0)
                {

                    this.bg.removeChildAt(0);
                }
                _loc_4 = new EmbeddedArt.scrollbarBg();
                _loc_4.width = param3.width;
                _loc_4.height = Math.max(1, param3.height - param3.width * 2);
                _loc_4.y = param3.width;
                this.bg.addChild(_loc_4);
            }
            return;
        }//end

    }


