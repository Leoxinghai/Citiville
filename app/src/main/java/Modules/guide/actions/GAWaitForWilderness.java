﻿package Modules.guide.actions;

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

    public class GAWaitForWilderness extends GuideAction
    {
        protected double m_amount ;

        public  GAWaitForWilderness ()
        {
            return;
        }//end  

         public boolean  createFromXml (XML param1 )
        {
            this.m_amount = int(param1.@percent) / 100;
            return true;
        }//end  

         public void  update (double param1 )
        {
            super.update(param1);
            if (Global.world.wildernessSim.gotTrees(this.m_amount))
            {
                removeState(this);
            }
            return;
        }//end  

    }



