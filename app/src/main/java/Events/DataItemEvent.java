package Events;

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
//import flash.events.*;
//import flash.geom.*;

    public class DataItemEvent extends Event
    {
        public Item item ;
        public Point pt ;
        private Point m_offset ;
        private Object m_params ;
        public static  String MARKET_BUY ="buyFromMarket";
        public static  String SHOW_OLD_TOOLTIP ="showOldItemToolTip";
        public static  String SHOW_TOOLTIP ="showItemToolTip";
        public static  String HIDE_TOOLTIP ="hideItemToolTip";
        public static  String WISHLIST_CHANGED ="wishlistChanged";
        public static  String CLOSE_DIALOG ="closeDialog";
        public static  String XPROMO_GIFT_SENT ="xpromoGiftSent";

        public  DataItemEvent (String param1 ,Item param2 ,Point param3 =null ,boolean param4 =false ,boolean param5 =false )
        {
            this.m_offset = new Point(0, 0);
            this.pt = param3;
            this.item = param2;
            super(param1, param4, param5);
            return;
        }//end  

        public void  setParams (Object param1 )
        {
            this.m_params = param1;
            return;
        }//end  

        public Object  getParams ()
        {
            return this.m_params;
        }//end  

        public void  setOffset (Point param1 )
        {
            this.m_offset = param1;
            return;
        }//end  

        public Point  getOffset ()
        {
            return this.m_offset;
        }//end  

    }



