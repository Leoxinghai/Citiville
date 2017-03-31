package com.xiyu.flash.framework.resources.reanimator.looptypes;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
//import android.graphics.Color;
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
import com.xiyu.util.*;

import com.xiyu.flash.framework.resources.reanimator.ReanimLoopType;
import com.xiyu.flash.framework.resources.reanimator.Reanimation;

    public class ReanimLoopQueue extends ReanimLoopType {

         public void  updatePositive (Reanimation reanim ){
            String aNewTrack ;
            if (reanim.animTime() >= 1)
            {
                aNewTrack = (String)this.mTracks.shift();
                if (aNewTrack == null)
                {
                    reanim.animTime (0);
                    reanim.loopType(Reanimation.LOOP_TYPE_ALWAYS);
                }
                else
                {
                    reanim.animTime(0);
                    reanim.currentTrack(aNewTrack);
                };
            };
        }

        private Array mTracks ;

         public void  updateNegative (Reanimation reanim ){
            String aNewTrack ;
            if (reanim.animTime() <= 0)
            {
                aNewTrack = (String)this.mTracks.shift();
                if (aNewTrack == null)
                {
                    reanim.animTime(1);
                    reanim.loopType(Reanimation.LOOP_TYPE_ALWAYS);
                }
                else
                {
                    reanim.animTime(1);
                    reanim.currentTrack(aNewTrack);
                };
            };
        }

        public  ReanimLoopQueue (Array tracks ){
            this.mTracks = tracks;
        }
    }


