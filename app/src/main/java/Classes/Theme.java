package Classes;

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

    public class Theme
    {
        private String m_name ;
        private int m_priority ;

        public  Theme (String param1 ,int param2 )
        {
            this.m_name = param1;
            this.m_priority = param2;
            return;
        }//end

        public String  name ()
        {
            return this.m_name;
        }//end

        public int  priority ()
        {
            return this.m_priority;
        }//end

        public static Theme  loadObject (XML param1 )
        {
            _loc_2 = param1.@name;
            _loc_3 = (Int)(param1.@priority);
            return new Theme(_loc_2, _loc_3);
        }//end

    }


