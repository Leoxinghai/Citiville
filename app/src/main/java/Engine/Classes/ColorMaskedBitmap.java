package Engine.Classes;

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
//import flash.geom.*;

    public class ColorMaskedBitmap extends AnimatedBitmap
    {
        private int m_color =16711935;
        private BitmapData m_colorMask ;
        private ColorTransform m_colorTransform ;

        public  ColorMaskedBitmap (BitmapData param1 ,BitmapData param2 ,int param3 ,int param4 ,int param5 ,double param6 ,int param7 =16777215)
        {
            this.m_colorTransform = new ColorTransform();
            this.m_colorMask = param1;
            this.m_color = param7;
            if (param3 == 0)
            {
                param4 = param2.width;
                param5 = param2.height;
            }
            this.updateTransform();
            super(param2, param3, param4, param5, param6);
            return;
        }//end

         public AnimatedBitmap  clone ()
        {
            return new ColorMaskedBitmap(this.m_colorMask, spriteSheet, this.numFrames, m_frameWidth, m_frameHeight, this.fps, this.m_color);
        }//end

        protected void  updateTransform ()
        {
            _loc_1 = this.m_color >>16;
            _loc_2 = this.m_color >>8& 255;
            _loc_3 = this.m_color & 255;
            this.m_colorTransform.redMultiplier = _loc_1 / 255;
            this.m_colorTransform.greenMultiplier = _loc_2 / 255;
            this.m_colorTransform.blueMultiplier = _loc_3 / 255;
            return;
        }//end

        public void  color (int param1 )
        {
            this.m_color = param1;
            this.updateTransform();
            this.draw();
            return;
        }//end

         protected void  draw ()
        {
            super.draw();
            m_targetBitmapData.draw(this.m_colorMask, null, this.m_colorTransform);
            return;
        }//end

    }



