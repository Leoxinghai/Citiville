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

import com.xiyu.util.Array;
import com.xiyu.util.Dictionary;

    public class FunctionReference
    {
        protected String m_function =null ;
        protected String m_functionParent =null ;
        protected String m_functionName =null ;

        public  FunctionReference (String param1)
        {
            this.functionPath = param1;
            return;
        }//end

        public String  functionPath ()
        {
            return this.m_function;
        }//end

        public void  functionPath (String param1 )
        {
            this.m_function = param1;
            _loc_2 = this.m_function.split(".");
            this.m_functionParent = _loc_2.get(0);
            this.m_functionName = _loc_2.get(1);
            return;
        }//end

        public String  functionParent ()
        {
            return this.m_functionParent;
        }//end

        public String  functionName ()
        {
            return this.m_functionName;
        }//end

        public String  toString ()
        {
            return this.m_function;
        }//end

    }



