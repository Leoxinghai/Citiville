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
//import flash.events.*;
import org.aswing.*;
import org.aswing.event.*;
import org.aswing.geom.*;
import org.aswing.graphics.*;
import org.aswing.plaf.*;

    public class CityScrollBarThumb implements GroundDecorator, UIResource
    {
        protected JScrollBar bar ;
        protected AWSprite thumb ;
        protected IntDimension size ;
        protected boolean verticle ;
        protected boolean rollover ;
        protected boolean pressed ;

        public  CityScrollBarThumb ()
        {
            this.thumb = new AWSprite();
            this.rollover = false;
            this.pressed = false;
            this.initSelfHandlers();
            return;
        }//end

        public void  updateDecorator (Component param1 ,Graphics2D param2 ,IntRectangle param3 )
        {
            this.thumb.x = param3.x;
            this.thumb.y = param3.y;
            this.size = param3.getSize();
            this.bar = JScrollBar(param1);
            this.verticle = this.bar.getOrientation() == JScrollBar.VERTICAL;
            this.paint();
            return;
        }//end

        private void  paint ()
        {
            IntRectangle _loc_6 =null ;
            double _loc_7 =0;
            int _loc_8 =0;
            double _loc_1 =0;
            double _loc_2 =0;
            _loc_3 = this.size.width;
            _loc_4 = this.size.height;
            this.thumb.graphics.clear();
            Graphics2D _loc_5 =new Graphics2D(this.thumb.graphics );
            while (this.thumb.numChildren > 0)
            {

                this.thumb.removeChildAt(0);
            }
            DisplayObject _loc_9 =new (DisplayObject)EmbeddedArt.scrollbarThumb ()as DisplayObject ;
            (new EmbeddedArt.scrollbarThumb()).width = _loc_3;
            _loc_9.height = _loc_4;
            this.thumb.addChild(_loc_9);
            return;
        }//end

        public DisplayObject  getDisplay (Component param1 )
        {
            return this.thumb;
        }//end

        private void  initSelfHandlers ()
        {
            this.thumb.addEventListener(MouseEvent.ROLL_OUT, this.__rollOutListener);
            this.thumb.addEventListener(MouseEvent.ROLL_OVER, this.__rollOverListener);
            this.thumb.addEventListener(MouseEvent.MOUSE_DOWN, this.__mouseDownListener);
            this.thumb.addEventListener(ReleaseEvent.RELEASE, this.__mouseUpListener);
            return;
        }//end

        private void  __rollOverListener (Event event )
        {
            this.rollover = true;
            this.paint();
            return;
        }//end

        private void  __rollOutListener (Event event )
        {
            this.rollover = false;
            if (!this.pressed)
            {
                this.paint();
            }
            return;
        }//end

        private void  __mouseDownListener (Event event )
        {
            this.pressed = true;
            this.paint();
            return;
        }//end

        private void  __mouseUpListener (Event event )
        {
            if (this.pressed)
            {
                this.pressed = false;
                this.paint();
            }
            return;
        }//end

    }



