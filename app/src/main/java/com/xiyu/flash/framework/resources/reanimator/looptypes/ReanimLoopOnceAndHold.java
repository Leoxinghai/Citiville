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

    public class ReanimLoopOnceAndHold extends ReanimLoopType {

         public void  updatePositive (Reanimation reanim ){
            if (reanim.animTime() > 1)
            {
                reanim.animTime(1);
            };
/*            
            if ((((reanim.animTime() == 1)) && (!((this.mCallback == null)))))
            {
                this.mCallback();
                this.mCallback = null;
            };
*/            
        }

  //      private Function mCallback ;

         public void  updateNegative (Reanimation reanim ){
            if (reanim.animTime() < 0)
            {
                reanim.animTime(0);
            };
            /*
            if ((((reanim.animTime() == 0)) && (!((this.mCallback == null)))))
            {
                this.mCallback();
                this.mCallback = null;
            };
            */
        }
         public boolean  doHold (){
            return (true);
        }

        public  ReanimLoopOnceAndHold () {//Function callback){
    //        this.mCallback = callback;
        }
    }


