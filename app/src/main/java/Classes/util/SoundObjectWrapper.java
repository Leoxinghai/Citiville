package Classes.util;

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

import Engine.Classes.*;
import Engine.Managers.*;
//import flash.media.*;

import com.xinghai.Debug;

    public class SoundObjectWrapper
    {
        private SoundObject m_soundObject =null ;
        private boolean m_isPaused ;
        private boolean m_isLoaded ;
        private String m_url ;
        private String m_id ;
        private double m_startTime ;
        private int m_loops ;
        private SoundTransform m_transform ;
        private boolean m_isMusic ;
        private boolean m_lazyLoad ;

        public  SoundObjectWrapper (String param1 ,String param2 ,boolean param3 =false )
        {
            Function onLoadComplete ;
            id = param1;
            url = param2;
            lazyLoad = param3;
            this.m_id = id;
            this.m_url = url;
            this.m_lazyLoad = lazyLoad;
            this.m_isPaused = true;
            this.m_isLoaded = false;
            if (!this.m_lazyLoad)
            {
                onLoadComplete =void  ()
            {
                m_isLoaded = true;
                if (m_soundObject == null && !m_isPaused)
                {
                    unpause();
                }
                return;
            }//end
            ;
                SoundManager.addSound(this.m_id, this.m_url, false, onLoadComplete);
            }
            return;
        }//end

        public void  play (double param1 =0,int param2 =0,SoundTransform param3 =null ,boolean param4 =false )
        {
            Function onLoadComplete ;
            startTime = param1;
            loops = param2;
            transform = param3;
            isMusic = param4;
            this.m_startTime = startTime;
            this.m_loops = loops;
            this.m_transform = transform;
            this.m_isMusic = isMusic;
            this.m_isPaused = false;
            if (this.m_lazyLoad)
            {
                onLoadComplete =void  ()
            {
                m_isLoaded = true;
                if (!m_isPaused)
                {
                    unpause();
                }
                return;
            }//end
            ;
                SoundManager.addSound(this.m_id, this.m_url, false, onLoadComplete);
            }
            else
            {
                this.m_soundObject = SoundManager.playSound(this.m_id, this.m_startTime, this.m_loops, this.m_transform, this.m_isMusic);
            }
            return;
        }//end

        public boolean  isPlaying ()
        {
            if (this.m_soundObject != null)
            {
                return this.m_soundObject.isPlaying();
            }
            return !this.m_isPaused;
        }//end

        public void  pause ()
        {
            if (this.m_soundObject != null)
            {
                this.m_soundObject.pause();
            }
            else
            {
                this.m_isPaused = true;
            }
            return;
        }//end

        public void  unpause ()
        {
            if (this.m_soundObject != null)
            {
                Debug.debug7("SoundObjectWrapper.unpause " + m_soundObject);
                this.m_soundObject.unpause();
            }
            else if (this.m_isLoaded)
            {
                this.m_soundObject = SoundManager.playSound(this.m_id, this.m_startTime, this.m_loops, this.m_transform, this.m_isMusic);
            }
            else
            {
                this.m_isPaused = false;
            }
            return;
        }//end

    }



