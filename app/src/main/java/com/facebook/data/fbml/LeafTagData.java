package com.facebook.data.fbml;

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

    public class LeafTagData extends AbstractTagData
    {
        public String fbml ;

        public  LeafTagData (String param1 ,String param2 ,String param3 ,String param4 ,String param5 ,String param6 ="",String param7 ="",AttributeCollection param8 =null )
        {
            super(param1, param3, param4, param5, param6, param7, param8);
            this.fbml = param2;
            return;
        }//end  

    }


