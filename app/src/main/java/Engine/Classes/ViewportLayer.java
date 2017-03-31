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

import com.xiyu.logic.Sprite;
import com.xiyu.util.Array;
import com.xiyu.util.Dictionary;

//import flash.display.*;
    public class ViewportLayer extends Sprite
    {
        protected double m_priority ;

        public  ViewportLayer (String param1 ,double param2)
        {
            name = param1;
            this.setPriority(param2);
            return;
        }//end  

        public void  setPriority (double param1 )
        {
            this.m_priority = param1;
            return;
        }//end  

        public double  getPriority ()
        {
            return this.m_priority;
        }//end  

    }



