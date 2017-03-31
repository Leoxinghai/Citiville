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

    public class AssetBackground extends Object implements GroundDecorator
    {
        protected DisplayObject asset ;
        protected boolean ignorBorderMargin ;
        protected Insets m_margin ;

        public  AssetBackground (DisplayObject param1 ,boolean param2 =false ,Insets param3 =null )
        {
            this.asset = param1;
            this.ignorBorderMargin = param2;
            this.m_margin = param3;
            if (this.m_margin == null)
            {
                this.m_margin = new Insets();
            }
            return;
        }//end  

        public void  updateDecorator (Component param1 ,Graphics2D param2 ,IntRectangle param3 )
        {
            if (this.ignorBorderMargin)
            {
                this.asset.x = this.m_margin.left;
                this.asset.y = this.m_margin.top;
                this.asset.width = param1.width - (this.m_margin.left + this.m_margin.right);
                this.asset.height = param1.height - (this.m_margin.top + this.m_margin.bottom);
            }
            else
            {
                this.asset.x = param3.x + this.m_margin.left;
                this.asset.y = param3.y + this.m_margin.top;
                this.asset.width = param3.width - (this.m_margin.left + this.m_margin.right);
                this.asset.height = param3.height - (this.m_margin.left + this.m_margin.right);
            }
            return;
        }//end  

        public DisplayObject  getDisplay (Component param1 )
        {
            return this.asset;
        }//end  

    }


