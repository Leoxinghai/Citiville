package Display;

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

import Engine.Managers.*;
import com.greensock.*;
//import flash.display.*;
//import flash.events.*;
//import flash.utils.*;

    public class ScrollingImageObject extends Sprite
    {
        protected Array m_items ;
        protected Array m_sprites ;
        protected Timer m_timer ;
        protected int m_totalLoaded =0;
        protected int m_curItem =0;
        protected int m_width ;
        protected int m_height ;
        protected String m_bg ;
public static  double TWEEN_TIME =0.5;

        public  ScrollingImageObject (Array param1 ,int param2 ,int param3 ,String param4 ="")
        {
            this.m_sprites = new Array();
            this.m_timer = new Timer(2000);
            this.m_width = param2;
            this.m_height = param3;
            this.m_bg = param4;
            this.m_items = param1;
            this.init();
            return;
        }//end

        protected void  init ()
        {
            Loader _loc_2 =null ;
            if (this.m_bg != "")
            {
                LoadingManager.load(Global.getAssetURL(this.m_bg), this.addBG);
            }
            this.m_timer.addEventListener(TimerEvent.TIMER, this.shiftItems, false, 0, true);
            int _loc_1 =0;
            while (_loc_1 < this.m_items.length())
            {

                _loc_2 = LoadingManager.load(this.m_items.get(_loc_1), this.loadItems);
                _loc_1++;
            }
            return;
        }//end

        protected void  addBG (Event event )
        {
            _loc_2 = event.target.content ;
            _loc_2.x = (this.m_width - _loc_2.width) / 2;
            _loc_2.y = (this.m_height - _loc_2.height) / 2;
            addChildAt(_loc_2, 0);
            return;
        }//end

        protected void  loadItems (Event event )
        {
            Sprite _loc_2 =new Sprite ();
            _loc_3 = event.target.content ;
            _loc_2.addChild(_loc_3);
            _loc_3.x = (this.m_width - _loc_3.width) / 2;
            _loc_3.y = (this.m_height - _loc_3.height) / 2;
            this.m_sprites.push(_loc_2);
            this.m_totalLoaded++;
            if (this.m_totalLoaded == this.m_items.length())
            {
                this.setupItems();
            }
            return;
        }//end

        public void  setupItems ()
        {
            int _loc_1 =0;
            while (_loc_1 < this.m_sprites.length())
            {

                if (_loc_1 != 0)
                {
                    this.m_sprites.get(_loc_1).x = this.m_width;
                    this.m_sprites.get(_loc_1).alpha = 0;
                }
                addChild(this.m_sprites.get(_loc_1));
                _loc_1++;
            }
            this.m_timer.start();
            return;
        }//end

        protected void  shiftItems (TimerEvent event )
        {
            int nextItem ;
            e = event;
            nextItem = (this.m_curItem + 1);
            if (nextItem >= this.m_sprites.length())
            {
                nextItem;
            }
            curItem = this.m_curItem;
            prevItem = this(.m_curItem-1);
            if (prevItem < 0)
            {
                prevItem = (this.m_sprites.length - 1);
            }
            this.m_sprites.get(nextItem).x = this.m_width;
            TweenLite.to(this.m_sprites.get(curItem), TWEEN_TIME, {x:-this.m_width, alpha:0});
void             TweenLite .to (this .m_sprites.get(nextItem) ,TWEEN_TIME ,{0x ,1alpha , onComplete ()
            {
                m_curItem = nextItem;
                return;
            }//end
            });
            return;
        }//end

    }



