package Display;

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
//import flash.events.*;

    public class GenericProgressBar
    {
        private MovieClip m_progressBar ;
        private boolean m_playing ;
        private int m_percentage ;
        private int m_frame ;
        private int m_targetFrame ;
        private Array m_percentageQueue ;
        private SWFDialog m_dialog ;
        private String m_eventType ;
        public static  int PLAY_SPEED =1;

        public  GenericProgressBar (MovieClip param1 ,SWFDialog param2 )
        {
            this.m_percentageQueue = new Array();
            this.m_progressBar = param1;
            this.m_playing = false;
            this.m_percentage = 0;
            this.m_targetFrame = 0;
            this.m_dialog = param2;
            this.m_eventType = "";
            this.m_progressBar.addEventListener(Event.ENTER_FRAME, this.onUpdate);
            return;
        }//end

        public void  eventType (String param1 )
        {
            this.m_eventType = param1;
            return;
        }//end

        public void  setPercentage (int param1 )
        {
            if (param1 < 0 || param1 > 100)
            {
                return;
            }
            this.m_percentageQueue.push(this.m_progressBar.totalFrames * (param1 * 0.01));
            if (!this.m_playing)
            {
                this.m_playing = true;
                if (this.m_percentageQueue.get(0) < this.m_frame)
                {
                    this.m_targetFrame = this.m_progressBar.totalFrames;
                }
                else
                {
                    this.m_targetFrame = this.m_percentageQueue.shift();
                }
                if (this.m_percentageQueue.get(0) == 0)
                {
                    this.m_percentageQueue.shift();
                }
            }
            return;
        }//end

        public void  onUpdate (Event event )
        {
            if (!this.m_playing)
            {
                return;
            }
            this.m_frame = this.m_frame > this.m_progressBar.totalFrames ? (this.m_progressBar.totalFrames) : (this.m_frame);
            if (this.m_frame < this.m_targetFrame)
            {
                this.m_frame = this.m_frame + PLAY_SPEED;
                this.m_progressBar.gotoAndStop(this.m_frame);
            }
            else
            {
                this.m_playing = false;
                if (this.m_frame >= this.m_progressBar.totalFrames)
                {
                    this.m_frame = 0;
                    this.m_progressBar.gotoAndStop(this.m_frame);
                    if (this.m_dialog && this.m_eventType != "")
                    {
                        event = new Event(this.m_eventType);
                        this.m_dialog.dispatchEvent(event);
                    }
                }
                if (this.m_percentageQueue.length != 0)
                {
                    if (this.m_percentageQueue.get(0) < this.m_frame)
                    {
                        this.m_targetFrame = this.m_progressBar.totalFrames;
                    }
                    else
                    {
                        this.m_targetFrame = this.m_percentageQueue.shift();
                    }
                    if (this.m_percentageQueue.get(0) == 0)
                    {
                        this.m_percentageQueue.shift();
                    }
                    this.m_playing = true;
                }
            }
            return;
        }//end

    }



