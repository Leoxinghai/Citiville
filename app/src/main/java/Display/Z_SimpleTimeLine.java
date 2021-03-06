package Display;

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

import com.greensock.core.*;
    public class Z_SimpleTimeLine extends SimpleTimeline
    {

        public  Z_SimpleTimeLine (Object param1)
        {
            super(param1);
            return;
        }//end

          public void  addChild (TweenCore param1 )
        {
         //   super.addChild(param1);
            if (param1.nextNode == null)
            {
                if (_lastChild != param1)
                {
                    _lastChild = param1;
                }
            }
            return;
        }//end

         public void  remove (TweenCore param1 ,boolean param2 =false )
        {
            if (param1.prevNode == null && param1.nextNode == null)
            {
                return;
            }
            super.remove(param1, param2);
            param1.nextNode = null;
            param1.prevNode = null;
            return;
        }//end

    }



