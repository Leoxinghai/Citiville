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

    public class SubstituterContractions extends SubstituterSimple
    {
public static Object contractionMap ={};

        public  SubstituterContractions ()
        {
            return;
        }//end  

         public String  replace (String param1 ,Object param2 )
        {
            String _loc_3 =null ;
            String _loc_4 =null ;
            RegExp _loc_5 =null ;
            Array _loc_6 =null ;
            String _loc_7 =null ;
            String _loc_8 =null ;
            Object _loc_9 =null ;
            Object _loc_10 =null ;
            String _loc_11 =null ;
            Object _loc_12 =null ;
            String _loc_13 =null ;
            String _loc_14 =null ;
            for(int i0 = 0; i0 < param2.size(); i0++) 
            {
            		_loc_3 = param2.get(i0);
                
                _loc_4 = param2.get(_loc_3);
                _loc_5 = this.getContractionPattern();
                _loc_6 = _loc_4.match(_loc_5);
                if (_loc_6)
                {
                    _loc_7 = _loc_6.get(1);
                    _loc_8 = _loc_4.replace(new RegExp("^" + _loc_7), "");
                    _loc_7 = _loc_7.toLowerCase();
                    _loc_7 = _loc_7.replace(/\s+$""\s+$/, "");
                    _loc_9 = this.getContractionMap();
                    _loc_10 = _loc_9.get(_loc_7);
                    for(int i0 = 0; i0 < _loc_10.size(); i0++) 
                    {
                    		_loc_11 = _loc_10.get(i0);
                        
                        _loc_12 = _loc_9.get(_loc_7).get(_loc_11);
                        _loc_13 = this.getContraction(_loc_12, _loc_8);
                        if (_loc_13.substr(-1, 1) == "\'")
                        {
                            _loc_14 = " " + _loc_13 + _loc_8;
                        }
                        else
                        {
                            _loc_14 = " " + _loc_13 + " " + _loc_8;
                        }
                        param1 = param1.replace(new RegExp("^" + _loc_11 + " {" + _loc_3 + "}"), _loc_14);
                        param1 = param1.replace(new RegExp(" " + _loc_11 + " {" + _loc_3 + "}", "g"), _loc_14);
                    }
                }
                param1 = param1.replace(new RegExp("{" + _loc_3 + "}", "g"), _loc_4);
            }
            return param1;
        }//end  

        protected Object  getContractionMap ()
        {
            return SubstituterContractions.contractionMap;
        }//end  

        protected RegExp  getContractionPattern ()
        {
            String _loc_3 =null ;
            RegExp _loc_4 =null ;
            _loc_1 = this.getContractionMap();
            String _loc_2 ="";
            for(int i0 = 0; i0 < _loc_1.size(); i0++) 
            {
            		_loc_3 = _loc_1.get(i0);
                
                if (_loc_3.charAt((_loc_3.length - 1)) != "\'")
                {
                    _loc_2 = _loc_2 + (_loc_3 + " |");
                    continue;
                }
                _loc_2 = _loc_2 + (_loc_3 + "|");
            }
            _loc_2 = _loc_2.substr(0, (_loc_2.length - 1));
            _loc_4 = new RegExp("^(" + _loc_2 + ")", "i");
            return _loc_4;
        }//end  

        protected String  getContraction (Object param1 ,String param2 )
        {
            if (typeof(param1) == "string")
            {
                return String(param1);
            }
            return "";
        }//end  

    }



