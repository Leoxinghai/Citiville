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

    public class TooltipItem
    {
        public String m_type =null ;
        public ZlocItem m_title =null ;
        public ZlocItem m_text =null ;
        public ZlocItem m_gate =null ;

        public  TooltipItem (String param1 ,ZlocItem param2 ,ZlocItem param3 =null ,ZlocItem param4 =null )
        {
            this.m_type = param1;
            this.m_title = param2;
            this.m_text = param3;
            this.m_gate = param4;
            return;
        }//end

        public String  type ()
        {
            return this.m_type;
        }//end

        public void  type (String param1 )
        {
            this.m_type = param1;
            return;
        }//end

        public ZlocItem  title ()
        {
            return this.m_title;
        }//end

        public void  title (ZlocItem param1 )
        {
            this.m_title = param1;
            return;
        }//end

        public ZlocItem  text ()
        {
            return this.m_text;
        }//end

        public void  text (ZlocItem param1 )
        {
            this.m_text = param1;
            return;
        }//end

        public ZlocItem  gate ()
        {
            return this.m_gate;
        }//end

        public void  gate (ZlocItem param1 )
        {
            this.m_gate = param1;
            return;
        }//end

    }



