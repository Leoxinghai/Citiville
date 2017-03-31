package Classes.actions;

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

    public class ActionPlayAnimationOneLoop extends ActionPlayAnimation
    {
        protected boolean m_timeCalculationFinished =false ;
        public static  double FAILURE_TIME =0;

        public  ActionPlayAnimationOneLoop (NPC param1 ,String param2 )
        {
            super(param1, param2, 60 * 1000);
            return;
        }//end

         public void  update (double param1 )
        {
            NPC _loc_2 =null ;
            ItemImageInstance _loc_3 =null ;
            super.update(param1);
            if (!this.m_timeCalculationFinished)
            {
                _loc_2 =(NPC) m_mapResource;
                _loc_3 = _loc_2.getItem().getCachedImage(m_animation, _loc_2, _loc_2.getDirection());
                if (_loc_3 == null)
                {
                    return;
                }
                this.m_actionTime = this.extractTime((CompositeItemImage)_loc_3.image);
                this.m_timeCalculationFinished = true;
            }
            return;
        }//end

        protected double  extractTime (Object param1)
        {
            AnimatedBitmapRoundRobbin _loc_2 =null ;
            CompositeItemImage _loc_3 =null ;
            int _loc_4 =0;
            DisplayObject _loc_5 =null ;
            double _loc_6 =0;
            if (param1 instanceof AnimatedBitmapRoundRobbin)
            {
                _loc_2 =(AnimatedBitmapRoundRobbin) param1;
                return _loc_2.frameDelay * (_loc_2.numFrames - _loc_2.currentFrame);
            }
            if (param1 instanceof CompositeItemImage)
            {
                _loc_3 =(CompositeItemImage) param1;
                _loc_4 = 0;
                while (_loc_4 < _loc_3.numChildren)
                {

                    _loc_5 = _loc_3.getChildAt(_loc_4);
                    _loc_6 = this.extractTime(_loc_5);
                    if (_loc_6 != FAILURE_TIME)
                    {
                        return _loc_6;
                    }
                    _loc_4++;
                }
            }
            return FAILURE_TIME;
        }//end

         public void  exit ()
        {
            super.exit();
            return;
        }//end

    }



