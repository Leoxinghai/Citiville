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
import org.aswing.border.*;
import org.aswing.flyfish.*;
import org.aswing.util.*;

    public class BorderDecoder extends Object implements ValueDecoder
    {
        private ColorDecoder colorDecoder ;
        private NumberDecoder numberDecoder ;
        public static  String BORDER_SPLITER ="|";

        public  BorderDecoder ()
        {
            return;
        }//end

        public Object decode (String param1 ,Component param2 )
        {
            if (param1 == null || param1 == "" || param1 == "none")
            {
                return null;
            }
            _loc_3 = param1.split(BORDER_SPLITER );
            return this.decodeDecorate(_loc_3);
        }//end

        public boolean  mutabble ()
        {
            return true;
        }//end

        private Border  decodeDecorate (Array param1 )
        {
            String _loc_2 =null ;
            if (param1 == null || param1.length <= 0)
            {
                return null;
            }
            if (param1.length <= 1)
            {
                return this.decodeSingleBorder(param1.get(0), null);
            }
            _loc_2 = param1.shift();
            return this.decodeSingleBorder(_loc_2, this.decodeDecorate(param1));
        }//end

        private Border  decodeSingleBorder (String param1 ,Border param2 )
        {
            String _loc_5 =null ;
            String _loc_6 =null ;
            String _loc_7 =null ;
            if (param1 == null || param1 == "")
            {
                return param2;
            }
            param1 = StringUtils.trim(param1);
            _loc_3 = param1.split(new RegExp("\\s+"));
            _loc_4 = _loc_3.get(0);
            if (_loc_4 == "line")
            {
                _loc_5 = _loc_3.get(1);
                _loc_6 = _loc_3.get(2);
                _loc_7 = _loc_3.get(3);
                return new LineBorder(param2, this.decodeColor(_loc_5), this.decodeNumber(_loc_6), this.decodeNumber(_loc_7));
            }
            if (_loc_4 == "empty")
            {
                return new EmptyBorder(param2, new Insets(this.decodeNumber(_loc_3.get(1)), this.decodeNumber(_loc_3.get(2)), this.decodeNumber(_loc_3.get(3)), this.decodeNumber(_loc_3.get(4))));
            }
            AGLog.warn("unknown border css value : " + param1);
            return param2;
        }//end

        private ASColor  decodeColor (String param1 )
        {
            if (this.colorDecoder == null)
            {
                this.colorDecoder = new ColorDecoder();
            }
            return this.colorDecoder.decode(param1, null);
        }//end

        private double  decodeNumber (String param1 )
        {
            if (this.numberDecoder == null)
            {
                this.numberDecoder = new NumberDecoder();
            }
            return this.numberDecoder.decode(param1, null);
        }//end

        public Object aggregate (Array param1 )
        {
            return null;
        }//end

    }


