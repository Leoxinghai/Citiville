package root;

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

    public class Tools
    {

        public  Tools ()
        {
            return;
        }//end

        public static void  bt ()
        {
            try
            {
                throw new Error("StackTrace");
            }
            catch (e:Error)
            {
                GlobalEngine.log("StackTrace", e.getStackTrace());
            }
            return;
        }//end

        public static String  pr (Object param1 ,int param2 =0,String param3 ="")
        {
            _loc_6 = null;
            String _loc_7 =null ;
            if (param2 == 0)
            {
                param3 = "(" + typeOf(param1) + ") {\n";
            }
            else if (param2 == 10)
            {
                return param3;
            }
            String _loc_4 ="\t";
            int _loc_5 =0;
            while (_loc_5 < param2)
            {

                _loc_5++;
                _loc_4 = _loc_4 + "\t";
            }
            for(int i0 = 0; i0 < param1.size(); i0++)
            {
            		_loc_6 = param1.get(i0);

                param3 = param3 + (_loc_4 + "[" + _loc_6 + "] => (" + typeOf(param1[_loc_6]) + ") ");
                if (count(param1.get(_loc_6)) == 0)
                {
                    param3 = param3 + param1.get(_loc_6);
                }
                _loc_7 = "";
                if (typeof(param1.get(_loc_6)) != "xml")
                {
                    _loc_7 = pr(param1.get(_loc_6), (param2 + 1));
                }
                if (_loc_7 != "")
                {
                    param3 = param3 + ("{\n" + _loc_7 + _loc_4 + "}");
                }
                param3 = param3 + "\n";
            }
            if (param2 == 0)
            {
                GlobalEngine.log("Print_r", param3 + "}\n");
            }
            else
            {
                return param3;
            }
            return "";
        }//end

        public static String  typeOf (Object param1)
        {
            if (param1 instanceof Array)
            {
                return "array";
            }
            if (param1 instanceof Date)
            {
                return "date";
            }
            return typeof(param1);
        }//end

        public static int  count (Object param1 )
        {
            int _loc_2 =0;
            _loc_3 = null;
            if (typeOf(param1) == "array")
            {
                return param1.length;
            }
            _loc_2 = 0;
            for(int i0 = 0; i0 < param1.size(); i0++)
            {
            		_loc_3 = param1.get(i0);

                if (_loc_3 != "mx_internal_uid")
                {
                    _loc_2 = _loc_2 + 1;
                }
            }
            return _loc_2;
        }//end

    }



