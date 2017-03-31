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

    public class SubstituterEs extends SubstituterSimple
    {

        public  SubstituterEs ()
        {
            return;
        }//end  

         public String  replace (String param1 ,Object param2 )
        {
            String _loc_3 ;
            String _loc_4 ;
            String _loc_5 ;
            for(int i0 = 0; i0 < param2.size(); i0++) 
            {
            		_loc_3 = param2.get(i0);
                
                _loc_4 = param2.get(_loc_3);
                if (_loc_4.indexOf("el ") == 0)
                {
                    _loc_5 = _loc_4.substr(3);
                    param1 = param1.replace(new RegExp(" de {" + _loc_3 + "}", "g"), " del " + _loc_5);
                    param1 = param1.replace(new RegExp(" a {" + _loc_3 + "}", "g"), " al " + _loc_5);
                }
                param1 = param1.replace(new RegExp("{" + _loc_3 + "}", "g"), _loc_4);
            }
            return param1;
        }//end  

    }

