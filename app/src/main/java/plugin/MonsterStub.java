package plugin;

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

//import flash.display.*;
//import flash.utils.*;

    public class MonsterStub
    {
        private Dictionary binder ;
        private Function call_trace =null ;
        private static MonsterStub s_instance ;

        public  MonsterStub (Dictionary param1 )
        {
            binding = param1;
            this.binder = binding;
            this.call_trace =(Function) binding.get("trace");
            if (this.call_trace == null)
            {
                this .call_trace =void  (DisplayObject param1 ,String param2 )
            {
                return;
            }//end
            ;
            }
            return;
        }//end

        public void  trace (DisplayObject param1 ,String param2 )
        {
            this.call_trace(param1, param2);
            return;
        }//end

        public static MonsterStub  getInstance ()
        {
            return s_instance;
        }//end

        public static void  install (Object param1 )
        {
            if (!param1.hasOwnProperty("binding"))
            {
                return;
            }
            _loc_2 =(Dictionary) param1.get("binding");
            _loc_3 =(Function) _loc_2.get("install");
            if (_loc_3 != null && Global.ui != null && _loc_3(Global.ui))
            {
                s_instance = new MonsterStub(_loc_2);
            }
            return;
        }//end

        public static boolean  active ()
        {
            return s_instance != null;
        }//end

    }



