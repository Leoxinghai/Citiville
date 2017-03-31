package Classes.util;

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

//import flash.external.*;
//import flash.utils.*;

    public class RuntimeVariableManager
    {
        private static Dictionary m_runtimeVars ;

        public  RuntimeVariableManager ()
        {
            return;
        }//end

        public static void  init ()
        {
            String _loc_2 =null ;
            _loc_1 = ExternalInterface.call("getRuntimeVars");
            m_runtimeVars = new Dictionary();
            if (_loc_1)
            {
                for(int i0 = 0; i0 < _loc_1.size(); i0++)
                {
                	_loc_2 = _loc_1.get(i0);

                    m_runtimeVars.put(_loc_2,  _loc_1.get(_loc_2));
                }
            }
            return;
        }//end

        public static int  getInt (String param1 ,int param2 )
        {
            return getValue(param1, param2, int) as int;
        }//end

        public static String  getString (String param1 ,String param2 )
        {
            return (String)getValue(param1, param2, String);
        }//end

        public static boolean  getBoolean (String param1 ,boolean param2 )
        {
            return getValue(param1, param2, Boolean) as Boolean;
        }//end

        public static Array  getArray (String param1 ,Array param2 )
        {
            return (Array)getValue(param1, param2, Array);
        }//end

        private static Object  getValue (String param1 ,Object param2 ,Class param3 )
        {
            _loc_4 = param2;
            if (m_runtimeVars && m_runtimeVars.hasOwnProperty(param1) && m_runtimeVars.get(param1) instanceof param3)
            {
                _loc_4 = m_runtimeVars.get(param1);
            }
            return _loc_4;
        }//end

    }



