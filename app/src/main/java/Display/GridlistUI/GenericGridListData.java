package Display.GridlistUI;

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
    public class GenericGridListData
    {
        protected Object m_data ;
        protected Component m_view ;

        public void  GenericGridListData (Object param1 ,Component param2 )
        {
            this.m_data = param1;
            this.m_view = param2;
            return;
        }//end

        public Object  data ()
        {
            return this.m_data;
        }//end

        public Component  view ()
        {
            return this.m_view;
        }//end

    }


