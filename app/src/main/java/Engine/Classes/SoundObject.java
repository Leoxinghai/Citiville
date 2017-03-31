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

//import flash.events.*;
//import flash.media.*;

    public class SoundObject
    {
        public Sound sound ;
        protected boolean m_reloop =false ;
        protected SoundChannel m_soundChannel ;
        protected double m_pausePoint =0;
        protected boolean m_isPaused =false ;
        protected double m_loops =0;
        protected SoundTransform m_transform =null ;
        public boolean isMusic =false ;

        public  SoundObject (Sound param1 ,SoundChannel param2 ,double param3 =0,SoundTransform param4 =null )
        {
            this.sound = param1;
            this.m_loops = param3;
            this.m_reloop = this.m_loops > 0;
            this.m_transform = param4;
            this.m_soundChannel = param2;
            this.m_soundChannel.addEventListener(Event.SOUND_COMPLETE, this.onComplete);
            return;
        }//end

        public double  loops ()
        {
            return this.m_loops;
        }//end

        public void  loops (double param1 )
        {
            this.m_loops = param1;
            return;
        }//end

        public void  volume (double param1 )
        {
            SoundTransform _loc_2 =new SoundTransform(param1 ,this.pan );
            this.m_soundChannel.soundTransform = _loc_2;
            return;
        }//end

        public double  volume ()
        {
            return this.m_soundChannel.soundTransform.volume;
        }//end

        public void  pan (double param1 )
        {
            SoundTransform _loc_2 =new SoundTransform(this.volume ,param1 );
            this.m_soundChannel.soundTransform = _loc_2;
            return;
        }//end

        public double  pan ()
        {
            return this.m_soundChannel.soundTransform.pan;
        }//end

        public double  position ()
        {
            return this.m_soundChannel.position;
        }//end

        public boolean  isPlaying ()
        {
            return this.m_isPaused == false;
        }//end

        public boolean  canReloop ()
        {
            _loc_1 = this.m_reloop ;
            this.m_reloop = false;
            return _loc_1;
        }//end

        public void  pause ()
        {
            if (this.m_isPaused == false)
            {
                this.m_pausePoint = this.m_soundChannel.position;
                this.m_soundChannel.stop();
                this.m_isPaused = true;
            }
            return;
        }//end

        public void  unpause ()
        {
            if (this.m_isPaused)
            {
                this.m_isPaused = false;
                this.m_soundChannel = this.sound.play(this.m_pausePoint, this.isMusic ? (999999) : (0), this.m_soundChannel.soundTransform);
                this.m_pausePoint = 0;
            }
            return;
        }//end

        public void  stop (boolean param1 =true )
        {
            this.m_soundChannel.stop();
            if (param1 !=null)
            {
                this.m_reloop = false;
                this.sound.dispatchEvent(new Event(Event.SOUND_COMPLETE));
            }
            return;
        }//end

        private void  onComplete (Event event )
        {
            this.sound.dispatchEvent(new Event(Event.SOUND_COMPLETE));
            return;
        }//end

    }



