package Classes.effects;

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
    public class ShakeEffect extends MapResourceEffect
    {
        private double m_strength ;
        private double m_speed ;
        private double m_numFrames ;
        private boolean m_isActive ;
        private double m_frameCount ;
        private double m_directionX ;
        private double m_originalX ;
        private double m_offsetX ;
        private double m_minOffsetX ;
        private double m_maxOffsetX ;
        private boolean m_isInitialized ;
        private static  double SOURCE_SPEED =20;

        public  ShakeEffect (MapResource param1 ,double param2 =0.05,double param3 =1,double param4 =-1)
        {
            m_mapResource = param1;
            this.m_strength = param2;
            this.m_speed = SOURCE_SPEED * param3;
            this.m_numFrames = param4;
            this.m_isActive = true;
            this.m_isInitialized = false;
            if (m_mapResource.content)
            {
                this.init();
            }
            super(param1);
            return;
        }//end  

         public boolean  animate (int param1 )
        {
            if (!this.m_isActive)
            {
                return this.m_isActive;
            }
            if (m_mapResource.content)
            {
                if (!this.m_isInitialized)
                {
                    this.init();
                    return this.m_isActive;
                }
                if (this.m_numFrames >= 0)
                {
                    (this.m_frameCount + 1);
                    if (this.m_frameCount > this.m_numFrames)
                    {
                        this.cleanUp();
                        return this.m_isActive;
                    }
                }
                this.m_offsetX = this.m_offsetX + this.m_speed * this.m_directionX;
                if (this.m_offsetX > this.m_maxOffsetX)
                {
                    this.m_offsetX = this.m_maxOffsetX;
                    this.m_directionX = -1;
                }
                else if (this.m_offsetX < this.m_minOffsetX)
                {
                    this.m_offsetX = this.m_minOffsetX;
                    this.m_directionX = 1;
                }
                m_mapResource.content.x = this.m_originalX + this.m_offsetX;
            }
            return this.m_isActive;
        }//end  

         public void  cleanUp ()
        {
            m_mapResource.content.x = this.m_originalX;
            this.m_isActive = false;
            return;
        }//end  

        private void  init ()
        {
            this.m_isInitialized = true;
            this.m_frameCount = 0;
            this.m_directionX = 1;
            this.m_originalX = m_mapResource.content.x;
            this.m_maxOffsetX = (m_mapResource.content.width >> 1) * this.m_strength;
            this.m_minOffsetX = -this.m_maxOffsetX;
            this.m_offsetX = 0;
            m_mapResource.content.x = this.m_originalX + this.m_minOffsetX;
            return;
        }//end  

    }



