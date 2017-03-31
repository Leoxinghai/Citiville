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
import org.aswing.zynga.*;
import org.aswing.colorchooser.*;

    public class LayoutDecoder extends Object implements ValueDecoder
    {
        private HashMap layoutMap ;
        public static  String ALIGN ="align";
        public static  String AXIS ="axis";
        public static  String MARGIN ="margin";

        public  LayoutDecoder ()
        {
            this.layoutMap = new HashMap();
            this.layoutMap.put("EmptyLayout", EmptyLayout);
            this.layoutMap.put("BorderLayout", BorderLayout);
            this.layoutMap.put("FlowLayout", FlowLayout);
            this.layoutMap.put("FlowWrapLayout", FlowWrapLayout);
            this.layoutMap.put("GridLayout", GridLayout);
            this.layoutMap.put("BoxLayout", BoxLayout);
            this.layoutMap.put("SoftBoxLayout", SoftBoxLayout);
            this.layoutMap.put("CenterLayout", CenterLayout);
            this.layoutMap.put("WeightBoxLayout", WeightBoxLayout);
            this.layoutMap.put("VerticalLayout", VerticalLayout);
            this.layoutMap.put("LayeredLayout", LayeredLayout);
            return;
        }//end

        public boolean  mutabble ()
        {
            return true;
        }//end

        public Object decode (String param1 ,Component param2 )
        {
            LayoutManager layout ;
            String className ;
            int eIndex ;
            String paramsStr ;
            Array pss ;
            String ps ;
            Array nv ;
            str = param1;
            c = param2;
            if (!(c is Container))
            {
                return null;
            }
            sIndex = str.indexOf("(");
            if (sIndex < 0)
            {
                className = str;
            }
            else
            {
                className = str.substring(0, sIndex);
            }
            layoutClass = this.layoutMap.getValue(className);
            if (layoutClass == null)
            {
                try
                {
                    layoutClass = Reflection.getClass(className);
                }
                catch (e:Error)
                {
                }
            }
            if (layoutClass)
            {
                layout = new layoutClass;
            }
            if (layout)
            {
                eIndex = str.indexOf(")");
                if (eIndex < 0)
                {
                    eIndex = str.length;
                }
                paramsStr = str.substring((sIndex + 1), eIndex);
                if (paramsStr.length > 0)
                {
                    pss = paramsStr.split(",");
                    int _loc_4 =0;
                    _loc_5 = pss;
                    for(int i0 = 0; i0 < pss.size(); i0++)
                    {
                    		ps = pss.get(i0);


                        nv = ps.split("=");
                        this.applyValue(layout, nv.get(0), nv.get(1));
                    }
                }
                return layout;
            }
            AGLog.warn("Can not decode layout : " + str + ", so use default FlowLayout instead!");
            return new FlowLayout();
        }//end

        private void  applyValue (Object param1 ,String param2 ,String param3 )
        {
            Object v;
            String fc ;
            String methodName ;
            layout = param1;
            name = param2;
            value = param3;
            if (ALIGN == name)
            {
                if (value == "left")
                {
                    v = AsWingConstants.LEFT;
                }
                else if (value == "right")
                {
                    v = AsWingConstants.RIGHT;
                }
                else if (value == "top")
                {
                    v = AsWingConstants.TOP;
                }
                else if (value == "bottom")
                {
                    v = AsWingConstants.BOTTOM;
                }
                else
                {
                    v = AsWingConstants.CENTER;
                }
            }
            else if (AXIS == name)
            {
                if (value == "y")
                {
                    v = AsWingConstants.VERTICAL;
                }
                else
                {
                    v = AsWingConstants.HORIZONTAL;
                }
            }
            else if (MARGIN == name)
            {
                if (value == "true")
                {
                    v;
                }
                else
                {
                    v;
                }
            }
            else
            {
                v = MathUtils.parseInteger(value);
            }
            try
            {
                fc = name.charAt(0).toUpperCase();
                methodName = "set" + fc + name.substr(1);
                _loc_5 = layout;
                _loc_5.layout.get(methodName)(v);
            }
            catch (er:Error)
            {
                AGLog.warn("LayoutDecoder unknow property : " + name + ":" + value + " of " + Reflection.getClassName(layout));
            }
            return;
        }//end

        public Object aggregate (Array param1 )
        {
            return null;
        }//end

    }


