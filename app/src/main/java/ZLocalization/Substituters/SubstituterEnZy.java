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

    public class SubstituterEnZy extends SubstituterSimple
    {
        public static Object greekMap ={"a""a","b""? ","c""?","d""d","e""e","f""f","g""?","h""?","i""?","j""?","k""?","l""?","m""? ","n""?","o""?","p""p","q"";","r""?","s""s","t""t","u""?","v""?","w""?","x""?","y""?","z""?","A""?","B""?","C""?","D""?","E""?","F""F","G""G","H""?","I""?","J""?","K""?","L""?","M""?","N""?","O""?","P""?","Q"";","R""?","S""S","T""?","U""T","V""O","W""S","X""?","Y""?","Z""?"};

        public  SubstituterEnZy ()
        {
            return;
        }//end

         public String  replace (String param1 ,Object param2 )
        {
            String _loc_3 ;
            param1 = convertString(param1);
            for(int i0 = 0; i0 < param2.size(); i0++) 
            {
            		_loc_3 = param2.get(i0);

                param2.put(_loc_3,  convertString(param2.get(_loc_3)));
            }
            super.replace(param1, param2);
            return param1;
        }//end

        public static String  convertString (String param1 )
        {
            String _loc_6 ;
            _loc_2 = param1.length ;
            Array _loc_3 =new Array ();
            boolean _loc_4 ;
            int _loc_5 ;
            while (_loc_5 < _loc_2)
            {

                _loc_6 = param1.charAt(_loc_5);
                if (_loc_4 || _loc_6 == "{")
                {
                    _loc_3.push(_loc_6);
                    _loc_4 = _loc_6 != "}";
                }
                else
                {
                    _loc_3.push(convertChar(_loc_6));
                }
                _loc_5++;
            }
            return _loc_3.join("");
        }//end

        public static String  convertChar (String param1 )
        {
            if (greekMap.hasOwnProperty(param1))
            {
                return greekMap.get(param1);
            }
            return param1;
        }//end

    }

