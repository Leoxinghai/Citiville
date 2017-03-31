package Display.aswingui;

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

import org.aswing.*;
    public class CustomDataButton extends CustomButton
    {
        protected Object m_data ;

        public  CustomDataButton (String param1 ="",Icon param2 ,String param3 ="Button.",Object param4 =null )
        {
            super(param1, param2, param3);
            this.m_data = param4;
            return;
        }//end

        public Object  data ()
        {
            return this.m_data;
        }//end

        public void  data (Object param1 )
        {
            this.m_data = param1;
            return;
        }//end

    }


