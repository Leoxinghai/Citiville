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

    public class StyleTune extends Object
    {
        public double cGradient ;
        public double bOffset ;
        public double bGradient ;
        public double shadowAlpha ;
        public double round ;
        private StyleTune mideAdjuster ;

        public  StyleTune (double param1 =0.2,double param2 =0.15,double param3 =0.35,double param4 =0.2,double param5 =0,StyleTune param6 =null )
        {
            this.cGradient = param1;
            this.bOffset = param2;
            this.bGradient = param3;
            this.shadowAlpha = param4;
            this.round = param5;
            if (param6 == null)
            {
                this.mideAdjuster = this;
            }
            else
            {
                this.mideAdjuster = param6;
            }
            return;
        }//end  

        public StyleTune  mide ()
        {
            return this.mideAdjuster;
        }//end  

        public void  mide (StyleTune param1 )
        {
            this.mideAdjuster = param1;
            return;
        }//end  

        public StyleTune  sharpen (double param1 )
        {
            return new StyleTune(this.cGradient * param1, this.bOffset * param1, this.bGradient * param1, this.shadowAlpha * param1, this.round, this.mide);
        }//end  

        public StyleTune  changeRound (double param1 )
        {
            return new StyleTune(this.cGradient, this.bOffset, this.bGradient, this.shadowAlpha, param1, this.mide);
        }//end  

        public StyleTune  clone ()
        {
            return new StyleTune(this.cGradient, this.bOffset, this.bGradient, this.shadowAlpha, this.round, this.mide);
        }//end  

        public ASColor  getCLight (ASColor param1 )
        {
            return param1.changeLuminance(param1.getLuminance() + this.cGradient / 2);
        }//end  

        public ASColor  getCDark (ASColor param1 )
        {
            return param1.changeLuminance(param1.getLuminance() - this.cGradient / 2);
        }//end  

        public ASColor  getBLight (ASColor param1 )
        {
            return param1.changeLuminance(param1.getLuminance() + this.bGradient / 2 + this.bOffset);
        }//end  

        public ASColor  getBDark (ASColor param1 )
        {
            return param1.changeLuminance(param1.getLuminance() - this.bGradient / 2 + this.bOffset);
        }//end  

        public double  getShadowAlpha ()
        {
            return this.shadowAlpha;
        }//end  

        public double  getRound ()
        {
            return this.round;
        }//end  

        public String  toString ()
        {
            return "StyleTune{cGradient:" + this.cGradient + ", bOffset:" + this.bOffset + ", bGradient:" + this.bGradient + ", shadowAlpha:" + this.shadowAlpha + ", round:" + this.round + (this.mideAdjuster != this ? ("mide:" + this.mideAdjuster.toString()) : ("")) + "}";
        }//end  

    }


