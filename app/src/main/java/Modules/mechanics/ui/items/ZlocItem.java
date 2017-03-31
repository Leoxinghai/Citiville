package Modules.mechanics.ui.items;

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

    public class ZlocItem
    {
        protected String m_pkg =null ;
        protected String m_key =null ;

        public  ZlocItem (String param1 ,String param2 )
        {
            this.m_pkg = param1;
            this.m_key = param2;
            return;
        }//end

        public String  pkg ()
        {
            return this.m_pkg;
        }//end

        public String  key ()
        {
            return this.m_key;
        }//end

    }



