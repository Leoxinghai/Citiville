package org.aswing.flyfish;

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

    public class AGLog extends Object
    {
        public static boolean enabled =true ;
        private static ILogger output ;
        private static Array buffer =new Array();
        private static boolean bufferShow =false ;

        public  AGLog ()
        {
            return;
        }//end

        public static void  init (ILogger param1 )
        {
            Object _loc_2 =null ;
            output = param1;
            for(int i0 = 0; i0 < buffer.size(); i0++) 
            {
            		_loc_2 = buffer.get(i0);

                output.addMessage(_loc_2.msg, _loc_2.color);
                if (bufferShow)
                {
                    output.show();
                    bufferShow = false;
                }
            }
            return;
        }//end

        private static void  addMessage (String param1 ,int param2 )
        {
            if (!enabled)
            {
                return;
            }
            if (output)
            {
                output.addMessage(param1, param2);
            }
            else
            {
                buffer.push({msg:param1, color:param2});
            }
            return;
        }//end

        private static void  show ()
        {
            if (!enabled)
            {
                return;
            }
            if (output)
            {
                output.show();
            }
            else
            {
                bufferShow = true;
            }
            return;
        }//end

        public static void  log (String param1 )
        {
            addMessage("Info: " + param1, 0);
            return;
        }//end

        public static void  warn (String param1 )
        {
            addMessage("Warning: " + param1, 16750848);
            show();
            return;
        }//end

        public static void  error (String param1 )
        {
            addMessage("Error: " + param1, 16711680);
            show();
            return;
        }//end

    }


