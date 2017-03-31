package Classes;

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
    public class Cloud extends Vehicle
    {
        private double m_ForcedScale =-1;
        private double m_originalHeight =-1;
        private double m_ForcedHeight =-1;
        private double m_originalWidth =-1;
        private double m_ForcedWidth =-1;
        private int m_ColorHue =16777215;

        public  Cloud (String param1 ,boolean param2 =false )
        {
            super(param1, param2);
            m_objectType = WorldObjectTypes.NPC;
            return;
        }//end  

         protected String  getLayerName ()
        {
            return "clouds";
        }//end  

         public boolean  isCloud ()
        {
            return true;
        }//end  

         public void  onUpdate (double param1 )
        {
            super.onUpdate(param1);
            return;
        }//end  

        public double  cloudScale ()
        {
            return this.m_ForcedScale;
        }//end  

        public void  cloudScale (double param1 )
        {
            this.m_ForcedScale = param1;
            return;
        }//end  

         public boolean  colorOverride ()
        {
            return true;
        }//end  

         public int  colorOverrideHue ()
        {
            return this.m_ColorHue;
        }//end  

        public void  setColorOverrideHue (int param1 )
        {
            this.m_ColorHue = param1;
            return;
        }//end  

         public double  colorOverrideAlpha ()
        {
            return 1;
        }//end  

         public double  widthOverride ()
        {
            if (m_itemImage == null)
            {
                return -1;
            }
            if (this.m_originalWidth == -1)
            {
                this.m_originalWidth = m_itemImage.width;
            }
            return this.m_originalWidth * this.m_ForcedScale;
        }//end  

         public double  heightOverride ()
        {
            if (m_itemImage == null)
            {
                return -1;
            }
            if (this.m_originalHeight == -1)
            {
                this.m_originalHeight = m_itemImage.height;
            }
            return this.m_originalHeight * this.m_ForcedScale;
        }//end  

        public Point  getStartCoordinatesBasedOnView (double param1 )
        {
            _loc_2 = m_displayObject.getRect(GlobalEngine.viewport);
            return new Point(0, 0);
        }//end  

    }



