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

    public class SubstituterPt extends SubstituterSimple
    {
        public static Object contractionMap ={"o"{"de""do","em""no","por""pelo","a""ao"},"a"{"de""da","em""na","por""pela","a""à "},"os"{"de""dos","em""nos","por""pelos","a""aos"},"as"{"de""das","em""nas","por""pelas","a""às"}};

        public  SubstituterPt ()
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
                _loc_5 = _loc_4.match(new RegExp("^(o|a|os|as) ", "i"));
                if (_loc_5)
                {
                    _loc_6 = _loc_5.get(1);
                    _loc_7 = _loc_4.replace(new RegExp("^" + _loc_6 + " "), "");
                    for(int i0 = 0; i0 < SubstituterPt.contractionMap.get(_loc_6).size(); i0++) 
                    {
                    		_loc_8 = SubstituterPt.contractionMap.get(_loc_6).get(i0);
                        
                        param1 = param1.replace(new RegExp(" " + _loc_8 + " {" + _loc_3 + "}", "g"), " " + SubstituterPt.contractionMap.get(_loc_6).get(_loc_8) + " " + _loc_7);
                    }
                }
                param1 = param1.replace(new RegExp("{" + _loc_3 + "}", "g"), _loc_4);
            }
            return param1;
        }//end  

    }

