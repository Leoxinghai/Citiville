package ZLocalization.Substituters;

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

    public class SubstituterEnFlip extends SubstituterSimple
    {
        public static Object flipMap ={"v""?","k""?","!""? ","w""?","l""?","a""?","\"":"?", "m":"?", "b":"q", ".":"?", "y":"?", "n":"u", "c":"?", "d":"p", ";":"?", "[":"]", "e":"?", "\'":"\"", "<":">", "f":"?", ",":"\'", "r":"?", "g":"?", "(":")", "h":"?", "_":"?", "?":"? ", "t":"?", "i":"i", "j":"?"};
        public static  double OPEN_BRACE_CODE ="{".charCodeAt(0);
        public static  double CLOSE_BRACE_CODE ="}".charCodeAt(0);

        public  SubstituterEnFlip ()
        {
            return;
        }//end  

         public String  replace (String param1 ,Object param2 )
        {
            String _loc_3 ;
            param1 = flipString(param1);
            for(int i0 = 0; i0 < param2.size(); i0++) 
            {
            		_loc_3 = param2.get(i0);
                
                param2.put(_loc_3,  flipString(param2.get(_loc_3)));
            }
            param1 = super.replace(param1, param2);
            return reverse(param1);
        }//end  

        public static String  flipString (String param1 )
        {
            String _loc_6 ;
            _loc_2 = param1.length;
            Array _loc_3 =new Array ();
            boolean _loc_4 ;
            int _loc_5 ;
            while (_loc_5 < _loc_2)
            {
                
                _loc_6 = param1.charAt(_loc_5);
                if (_loc_4 || _loc_6.charCodeAt(0) == OPEN_BRACE_CODE)
                {
                    _loc_3.push(_loc_6);
                    _loc_4 = _loc_6.charCodeAt(0) != CLOSE_BRACE_CODE;
                }
                else
                {
                    _loc_3.push(flipChar(_loc_6));
                }
                _loc_5++;
            }
            return _loc_3.join("");
        }//end  

        public static String  reverse (String param1 )
        {
            String _loc_2 ;
            int _loc_3 =param1.length ;
            
            while (_loc_3-- >= 0)
            {
                
                _loc_2 = _loc_2 + param1.charAt(_loc_3);
            }
            return _loc_2;
        }//end  

        public static String  flipChar (String param1 )
        {
            if (flipMap.hasOwnProperty(param1))
            {
                return flipMap.get(param1);
            }
            return param1;
        }//end  

    }


