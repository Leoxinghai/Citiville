package com.zynga.skelly.animation;

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

import de.polygonal.ds.*;
    public class AnimationList
    {

        public int fps ;
        public int m_uIndex ;
        public int m_timeMs ;
        public int m_timeAccumulated ;
        public int m_lastProcessTimeMs ;
        public SLinkedList m_animations ;
        public AnimationList prevList ;
        public AnimationList nextList ;

        public  AnimationList (int param1 ,int param2 ,int param3 )
        {
            this.m_animations = new SLinkedList();
            this.fps = param1;
            this.m_timeMs = param2;
            this.m_uIndex = param3;
            return;
        }//end

        public void  process (int param1 )
        {
            int _loc_3 =0;
            SListIterator _loc_4 =null ;
            IAnimated _loc_5 =null ;
            if (!this.m_animations.head)
            {
                return;
            }
            _loc_2 = param1-this.m_lastProcessTimeMs ;
            this.m_lastProcessTimeMs = param1;
            this.m_timeAccumulated = this.m_timeAccumulated + _loc_2;
            if (this.m_timeAccumulated >= this.m_timeMs - 20)
            {
                if (this.m_timeAccumulated <= this.m_timeMs + 20 || _loc_2 <= this.m_timeMs + 5)
                {
                    _loc_3 = 1;
                    this.m_timeAccumulated = 0;
                }
                else
                {
                    _loc_3 = this.m_timeAccumulated / this.m_timeMs;
                    this.m_timeAccumulated = this.m_timeAccumulated - _loc_3 * this.m_timeMs;
                }
                _loc_4 = this.m_animations.getListIterator();
                _loc_4.start();
                while (_loc_4.data != null)
                {

                    _loc_5 = _loc_4.data;
                    if (_loc_5.animate(_loc_3))
                    {
                        _loc_4.forth();
                        continue;
                    }
                    _loc_4.remove();
                }
            }
            return;
        }//end

        public void  add (IAnimated param1 )
        {
            this.m_animations.append(param1);
            return;
        }//end

        public boolean  contains (IAnimated param1 )
        {
            _loc_2 = this.m_animations.nodeOf(param1 );
            if (_loc_2)
            {
                return true;
            }
            return false;
        }//end

        public void  remove (IAnimated param1 )
        {
            _loc_2 = this.m_animations.nodeOf(param1 );
            if (_loc_2)
            {
                _loc_2.remove();
            }
            else
            {
                throw new Error("AnimationList remove called with unfound anim:", param1);
            }
            return;
        }//end

    }


