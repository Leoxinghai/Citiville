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

//import flash.events.*;
    public class GAWaitForClickAnywhere extends GuideAction
    {

        public  GAWaitForClickAnywhere ()
        {
            return;
        }//end  

        private void  stageClickHandler (MouseEvent event )
        {
            removeState(this);
            return;
        }//end  

         public boolean  createFromXml (XML param1 )
        {
            return true;
        }//end  

         public void  enter ()
        {
            super.enter();
            Global.stage.addEventListener(MouseEvent.CLICK, this.stageClickHandler);
            return;
        }//end  

         public void  reenter ()
        {
            super.reenter();
            Global.stage.addEventListener(MouseEvent.CLICK, this.stageClickHandler);
            return;
        }//end  

         public void  exit ()
        {
            super.exit();
            Global.stage.removeEventListener(MouseEvent.CLICK, this.stageClickHandler);
            return;
        }//end  

         public void  removed ()
        {
            super.removed();
            Global.stage.removeEventListener(MouseEvent.CLICK, this.stageClickHandler);
            return;
        }//end  

    }



