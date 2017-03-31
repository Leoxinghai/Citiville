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

//import flash.filters.*;
import org.aswing.*;
import org.aswing.flyfish.*;
import org.aswing.flyfish.css.property.filter.*;
import org.aswing.util.*;

    public class FiltersDecoder extends Object implements ValueDecoder
    {
        public static  String BORDER_SPLITER ="|";
        private static HashMap filterMap ;

        public  FiltersDecoder ()
        {
            if (filterMap == null)
            {
                filterMap = new HashMap();
                filterMap.put("BevelFilter", new FilterDef(BevelFilter, new NumberPro("distance"), new NumberPro("angle"), new ColorPro("highlightColor"), new AlphaPro("highlightAlpha"), new ColorPro("shadowColor"), new AlphaPro("shadowAlpha"), new NumberPro("blurX"), new NumberPro("blurY"), new NumberPro("strength"), new IntPro("quality"), new StringEnumPro("type", ["full", "inner", "outer"]), new BooleanPro("knockout")));
                filterMap.put("BlurFilter", new FilterDef(BlurFilter, new NumberPro("blurX"), new NumberPro("blurY"), new IntPro("quality")));
                filterMap.put("ColorMatrixFilter", new FilterDef(ColorMatrixFilter, new NumberArrayPro("matrix")));
                filterMap.put("ConvolutionFilter", new FilterDef(ConvolutionFilter, new NumberPro("matrixX"), new NumberPro("matrixY"), new NumberArrayPro("matrix"), new NumberPro("divisor"), new NumberPro("bias"), new BooleanPro("preserveAlpha"), new BooleanPro("clamp"), new ColorPro("color"), new AlphaPro("alpha")));
                filterMap.put("DropShadowFilter", new FilterDef(DropShadowFilter, new NumberPro("distance"), new NumberPro("angle"), new ColorPro("color"), new AlphaPro("alpha"), new NumberPro("blurX"), new NumberPro("blurY"), new NumberPro("strength"), new IntPro("quality"), new BooleanPro("inner"), new BooleanPro("knockout"), new BooleanPro("hideObject")));
                filterMap.put("GlowFilter", new FilterDef(GlowFilter, new ColorPro("color"), new AlphaPro("alpha"), new NumberPro("blurX"), new NumberPro("blurY"), new NumberPro("strength"), new IntPro("quality"), new BooleanPro("inner"), new BooleanPro("knockout")));
                filterMap.put("GradientBevelFilter", new FilterDef(GradientBevelFilter, new NumberPro("distance"), new NumberPro("angle"), new ColorArrayPro("colors"), new NumberArrayPro("alphas"), new NumberArrayPro("ratios"), new NumberPro("blurX"), new NumberPro("blurY"), new NumberPro("strength"), new IntPro("quality"), new StringEnumPro("type", ["full", "inner", "outer"]), new BooleanPro("knockout")));
                filterMap.put("GradientGlowFilter", new FilterDef(GradientGlowFilter, new NumberPro("distance"), new NumberPro("angle"), new ColorArrayPro("colors"), new NumberArrayPro("alphas"), new NumberArrayPro("ratios"), new NumberPro("blurX"), new NumberPro("blurY"), new NumberPro("strength"), new IntPro("quality"), new StringEnumPro("type", ["full", "inner", "outer"]), new BooleanPro("knockout")));
            }
            return;
        }//end

        public Object decode (String param1 ,Component param2 )
        {
            if (param1 == null || param1 == "" || param1 == "none")
            {
                return null;
            }
            _loc_3 = param1.split(BORDER_SPLITER );
            return this.decodeFilters(_loc_3);
        }//end

        private Array  decodeFilters (Array param1 )
        {
            String _loc_3 =null ;
            _loc_4 = null;
            if (param1 == null || param1.length <= 0)
            {
                return null;
            }
            Array _loc_2 =new Array();
            for(int i0 = 0; i0 < param1.size(); i0++)
            {
            		_loc_3 = param1.get(i0);

                _loc_4 = this.decodeFilter(_loc_3);
                if (_loc_4 != null)
                {
                    _loc_2.push(_loc_4);
                }
            }
            return _loc_2;
        }//end

        private BitmapFilter  decodeFilter (String param1 )
        {
            String _loc_3 =null ;
            int _loc_5 =0;
            String _loc_6 =null ;
            Array _loc_7 =null ;
            param1 = StringUtils.trim(param1);
            if (param1 == null || param1 == "")
            {
                return null;
            }
            _loc_2 = param1.indexOf("(");
            if (_loc_2 < 0)
            {
                _loc_3 = param1;
            }
            else
            {
                _loc_3 = param1.substring(0, _loc_2);
            }
            _loc_4 = filterMap.getValue(_loc_3);
            if (filterMap.getValue(_loc_3))
            {
                _loc_5 = param1.indexOf(")");
                if (_loc_5 < 0)
                {
                    _loc_5 = param1.length;
                }
                _loc_6 = param1.substring((_loc_2 + 1), _loc_5);
                _loc_7 = new Array();
                if (_loc_6.length > 0)
                {
                    _loc_7 = _loc_6.split(",");
                }
                return _loc_4.decode(_loc_7);
            }
            AGLog.warn("Can not find filter : " + _loc_3);
            return null;
        }//end

        public boolean  mutabble ()
        {
            return false;
        }//end

        public Object aggregate (Array param1 )
        {
            return null;
        }//end

    }


