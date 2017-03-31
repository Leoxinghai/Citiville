package org.aswing.flyfish.awml;

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
    public class ComDefinition extends AbsAggDefinition
    {
        private boolean container ;

        public  ComDefinition (Object param1 ,ComDefinition param2 )
        {
            super(param1, param2);
            AbsAggDefinition _loc_3 =this ;
            while (_loc_3 != null)
            {

                if (_loc_3.getClass() == Container)
                {
                    this.container = true;
                    break;
                }
                _loc_3 = _loc_3.getSuperDefinition();
            }
            return;
        }//end

        public boolean  isContainer ()
        {
            return this.container;
        }//end

        public String  toString ()
        {
            return "ComDefintion.get(name:" + getName() + ")";
        }//end

    }


