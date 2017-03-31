package Display.MarketUI;

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

import Display.*;
//import flash.events.*;
//import flash.geom.*;

    public class UICatalog extends Catalog
    {
        private double m_initialWidth =0;

        public  UICatalog (ItemCatalogUI param1 ,CatalogParams param2 )
        {
            super(param1, param2);
            return;
        }//end  

         protected void  onFullScreen (FullScreenEvent event =null )
        {
            this.centerPopup();
            return;
        }//end  

         public void  centerPopup ()
        {
            Point _loc_1 =null ;
            if (parent)
            {
                if (this.m_initialWidth == 0)
                {
                    this.m_initialWidth = m_content.width;
                }
                _loc_1 = new Point((UI.DEFAULT_WIDTH - this.m_initialWidth) / 2, (Global.ui.screenHeight - m_content.height) / 2);
                this.x = _loc_1.x;
                this.y = _loc_1.y;
            }
            return;
        }//end  

    }



