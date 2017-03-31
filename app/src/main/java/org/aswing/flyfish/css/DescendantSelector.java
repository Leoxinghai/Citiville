package org.aswing.flyfish.css;

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
    public class DescendantSelector extends CombinateSelector implements ISelector
    {

        public  DescendantSelector (ISelector param1 ,ISelector param2 )
        {
            super(param1, param2);
            return;
        }//end  

        public String  getKey ()
        {
            return mom.getKey() + "凹" + kid.getKey();
        }//end  

        public boolean  isSelected (Component param1 )
        {
            Container _loc_2 =null ;
            if (kid.isSelected(param1))
            {
                _loc_2 = param1.getParent();
                while (_loc_2 != null)
                {
                    
                    if (mom.isSelected(_loc_2))
                    {
                        return true;
                    }
                    _loc_2 = _loc_2.getParent();
                }
            }
            return false;
        }//end  

    }


