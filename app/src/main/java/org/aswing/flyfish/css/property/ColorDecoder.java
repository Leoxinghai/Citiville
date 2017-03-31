package org.aswing.flyfish.css.property;

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

import org.aswing.*;
import org.aswing.flyfish.*;
import org.aswing.flyfish.util.*;
import org.aswing.util.*;

    public class ColorDecoder extends Object implements ValueDecoder
    {
        private HashMap colorMap ;
        private static ColorDecoder staticDecoder ;

        public  ColorDecoder ()
        {
            this.colorMap = new HashMap();
            this.colorMap.put("white", ASColor.WHITE);
            this.colorMap.put("gray", ASColor.GRAY);
            this.colorMap.put("lightgray", ASColor.LIGHT_GRAY);
            this.colorMap.put("darkgray", ASColor.DARK_GRAY);
            this.colorMap.put("black", ASColor.BLACK);
            this.colorMap.put("red", ASColor.RED);
            this.colorMap.put("pink", ASColor.PINK);
            this.colorMap.put("orange", ASColor.ORANGE);
            this.colorMap.put("haloorange", ASColor.HALO_ORANGE);
            this.colorMap.put("yellow", ASColor.YELLOW);
            this.colorMap.put("green", ASColor.GREEN);
            this.colorMap.put("halogreen", ASColor.HALO_GREEN);
            this.colorMap.put("magenta", ASColor.MAGENTA);
            this.colorMap.put("cyan", ASColor.CYAN);
            this.colorMap.put("blue", ASColor.BLUE);
            this.colorMap.put("haloblue", ASColor.HALO_BLUE);
            return;
        }//end

        public boolean  mutabble ()
        {
            return false;
        }//end

        public Object decode (String param1 ,Component param2 )
        {
            String _loc_5 =null ;
            String _loc_6 =null ;
            String _loc_7 =null ;
            Array _loc_8 =null ;
            Array _loc_9 =null ;
            Array _loc_10 =null ;
            Array _loc_11 =null ;
            if (param1 == null || param1 == "")
            {
                AGLog.warn("blank css color style value, consider as black.");
                return ASColor.BLACK;
            }
            _loc_3 = param1.substr(0,3);
            _loc_4 = param1.substr(0,4);
            if (param1.charAt(0) == "#")
            {
                _loc_5 = param1.substr(1);
                if (_loc_5.length < 6)
                {
                    _loc_6 = this.charAt(_loc_5, 0, "0", true) + this.charAt(_loc_5, 1, "0", true) + this.charAt(_loc_5, 2, "0", true);
                    _loc_7 = this.charAt(_loc_5, 3, "F", true);
                }
                else
                {
                    _loc_6 = this.charAt(_loc_5, 0, "0") + this.charAt(_loc_5, 1, "0") + this.charAt(_loc_5, 2, "0") + this.charAt(_loc_5, 3, "0") + this.charAt(_loc_5, 4, "0") + this.charAt(_loc_5, 5, "0");
                    _loc_7 = this.charAt(_loc_5, 6, "F") + this.charAt(_loc_5, 7, "F");
                }
                return new ASColor(uint(MathUtils.parseInteger(_loc_6, 16)), MathUtils.parseInteger(_loc_7, 16) / 255);
            }
            else
            {
                if (_loc_4 == "rgba")
                {
                    _loc_8 = this.getItemsInBrackets(param1, 4);
                    return ASColor.getASColor(_loc_8.get(0), _loc_8.get(1), _loc_8.get(2), _loc_8.get(3) / 255);
                }
                if (_loc_3 == "rgb")
                {
                    _loc_9 = this.getItemsInBrackets(param1, 3);
                    return ASColor.getASColor(_loc_9.get(0), _loc_9.get(1), _loc_9.get(2), 1);
                }
                if (_loc_4 == "hsla")
                {
                    _loc_10 = this.getItemsInBrackets(param1, 4, 1);
                    return ASColor.getASColorWithHLS(_loc_10.get(0) / 360, _loc_10.get(2), _loc_10.get(1), _loc_10.get(3));
                }
                if (_loc_3 == "hsl")
                {
                    _loc_11 = this.getItemsInBrackets(param1, 3, 1);
                    return ASColor.getASColorWithHLS(_loc_11.get(0) / 360, _loc_11.get(2), _loc_11.get(1), 1);
                }
                if (this.colorMap.containsKey(param1))
                {
                    return this.colorMap.getValue(param1);
                }
            }
            AGLog.warn("unknown css color style value" + param1 + ", consider as black.");
            return ASColor.BLACK;
        }//end

        private Array  getItemsInBrackets (String param1 ,int param2 ,int param3 =255)
        {
            String _loc_9 =null ;
            _loc_4 = param1.indexOf("(");
            _loc_5 = param1.indexOf(")");
            if (param1.indexOf(")") < 0)
            {
                _loc_5 = param1.length;
            }
            param1 = param1.substring((_loc_4 + 1), _loc_5);
            _loc_6 = param1.split(",",param2 );
            _loc_7 = new Array(param2 );
            int _loc_8 =0;
            while (_loc_8 < param2)
            {

                if (_loc_8 < _loc_6.length())
                {
                    _loc_9 = _loc_6.get(_loc_8);
                    if (_loc_9.charAt((_loc_9.length - 1)) == "%")
                    {
                        _loc_7.put(_loc_8,  uint(MathUtils.parseInteger(_loc_9) / 100 * param3));
                    }
                    else
                    {
                        _loc_7.put(_loc_8,  uint(MathUtils.parseInteger(_loc_9)));
                    }
                }
                else
                {
                    _loc_7.put(_loc_8,  param3);
                }
                _loc_8++;
            }
            return _loc_7;
        }//end

        private String  charAt (String param1 ,int param2 ,String param3 ="0",boolean param4 =false )
        {
            _loc_5 = param3;
            if (param1.length > param2)
            {
                param3 = param1.charAt(param2);
            }
            if (param4)
            {
                param3 = param3 + param3;
            }
            return param3;
        }//end

        public Object aggregate (Array param1 )
        {
            return null;
        }//end

        public static ASColor  staticDecode (String param1 )
        {
            if (staticDecoder == null)
            {
                staticDecoder = new ColorDecoder;
            }
            return staticDecoder.decode(param1, null);
        }//end

    }


