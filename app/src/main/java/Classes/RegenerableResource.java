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

    public class RegenerableResource
    {
        private String m_name ;
        private int m_regenInterval ;
        private int m_regenAmount ;
        private int m_regenCap ;
        private int m_softCap ;
        public static  String GAS ="gas";

        public  RegenerableResource ()
        {
            return;
        }//end

        public String  name ()
        {
            return this.m_name;
        }//end

        public void  name (String param1 )
        {
            this.m_name = param1;
            return;
        }//end

        public int  regenInterval ()
        {
            return this.m_regenInterval;
        }//end

        public void  regenInterval (int param1 )
        {
            this.m_regenInterval = param1;
            return;
        }//end

        public int  regenCap ()
        {
            return this.m_regenCap;
        }//end

        public void  regenCap (int param1 )
        {
            this.m_regenCap = param1;
            return;
        }//end

        public int  regenAmount ()
        {
            return this.m_regenAmount;
        }//end

        public void  regenAmount (int param1 )
        {
            this.m_regenAmount = param1;
            return;
        }//end

        public int  softCap ()
        {
            return this.m_softCap;
        }//end

        public void  softCap (int param1 )
        {
            this.m_softCap = param1;
            return;
        }//end

    }


