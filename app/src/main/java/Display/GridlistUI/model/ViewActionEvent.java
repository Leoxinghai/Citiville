package Display.GridlistUI.model;

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
    public class ViewActionEvent extends Event
    {
        protected String m_functionName ;
        public static  String EVENT_TYPE ="ViewActionEventTrigger";

        public  ViewActionEvent (String param1 ,boolean param2 =false ,boolean param3 =false )
        {
            this.m_functionName = param1;
            super(EVENT_TYPE, param2, param3);
            return;
        }//end  

        public String  action ()
        {
            return this.m_functionName;
        }//end  

    }


