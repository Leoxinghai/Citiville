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

    public class ContainerTagData extends AbstractTagData
    {
        public String open_tag_fbml ;
        public String close_tag_fbml ;

        public  ContainerTagData (String param1 ,String param2 ,String param3 ,String param4 ,String param5 ,String param6 ,String param7 ="",String param8 ="",AttributeCollection param9 =null )
        {
            this.open_tag_fbml = param5;
            this.close_tag_fbml = param6;
            super(param1, param2, param3, param4, param7, param8, param9);
            return;
        }//end  

    }


