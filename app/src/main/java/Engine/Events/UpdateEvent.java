package Engine.Events;

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
    public class UpdateEvent extends Event
    {
        private double m_delta ;
        public static  String UPDATE ="onUpdate";

        public  UpdateEvent (String param1 ,double param2 ,boolean param3 =false ,boolean param4 =false )
        {
            super(param1, param3, param4);
            this.m_delta = param2;
            return;
        }//end  

        public double  delta ()
        {
            return this.m_delta;
        }//end  

    }



