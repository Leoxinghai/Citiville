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

    public class SubstituterIt extends SubstituterSimple
    {
        public static Object contractionMap ={"le"{"a""alle","su""sulle","con""colle","in""nelle","di""delle","da""dalle"},"lo"{"a""allo","su""sullo","con""collo","in""nello","di""dello","da""dallo"},"il"{"a""al","su""sul","con""col","in""nel","di""del","da""dal"},"gli"{"a""agli","su""sugli","con""cogli","in""negli","di""degli","da""dagli"},"l\'"{"a""all’","su""sull’","con""coll’","in""nell’","di""dell’","da""dall’"},"i"{"a""ai","su""sui","con""coi","in""nei","di""dei","da""dai"},"la"{"a""alla","su""sulla","con""colla","in""nella","di""della","da""dalla"}};

        public  SubstituterIt ()
        {
            return;
        }//end  

         public String  replace (String param1 ,Object param2 )
        {
            String _loc_3 ;
            String _loc_4 ;
            Array _loc_5 ;
            String _loc_6 ;
            String _loc_7 ;
            String _loc_8 ;
            for(int i0 = 0; i0 < param2.size(); i0++) 
            {
            		_loc_3 = param2.get(i0);
                
                _loc_4 = param2.get(_loc_3);
                _loc_5 = _loc_4.match(new RegExp("^(il|lo|l\'|la|i|gli|le) ", "i"));
                if (_loc_5)
                {
                    _loc_6 = _loc_5.get(1);
                    _loc_7 = _loc_4.replace(new RegExp("^" + _loc_6 + " "), "");
                    for(int i0 = 0; i0 < SubstituterIt.contractionMap.get(_loc_6).size(); i0++) 
                    {
                    		_loc_8 = SubstituterIt.contractionMap.get(_loc_6).get(i0);
                        
                        param1 = param1.replace(new RegExp(" " + _loc_8 + " {" + _loc_3 + "}", "g"), " " + SubstituterIt.contractionMap.get(_loc_6).get(_loc_8) + " " + _loc_7);
                    }
                }
                param1 = param1.replace(new RegExp("{" + _loc_3 + "}", "g"), _loc_4);
            }
            return param1;
        }//end  

    }

