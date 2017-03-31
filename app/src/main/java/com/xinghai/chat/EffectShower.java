package com.xinghai.chat;

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
    public class EffectShower
    {
        public boolean autoHide =true ;
        private Function _func ;
        public MovieClip mc ;

        public  EffectShower (Function param1)
        {
            this._func = param1;
            return;
        }//end

        public void  stop ()
        {
            if (this._func != null)
            {
                this._func(this);
            }
            if (this.autoHide)
            {
                this.removeShower();
            }
            return;
        }//end

        public void  removeShower ()
        {
            if (this.mc && this.mc.parent)
            {
                this.mc.parent.removeChild(this.mc);
            }
            this.mc = null;
            return;
        }//end

    }

