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

//import flash.display.*;
import org.aswing.*;
import org.aswing.flyfish.*;
import org.aswing.flyfish.css.*;
import org.aswing.flyfish.util.*;

    public class IconDecoder extends Object implements ValueDecoder
    {

        public  IconDecoder ()
        {
            return;
        }//end

        public Object decode (String param1 ,Component param2 )
        {
            LazyLoadRequest _loc_8 =null ;
            DisplayObject _loc_9 =null ;
            int _loc_10 =0;
            int _loc_11 =0;
            String _loc_12 =null ;
            if (param1 == "" || param1 == null || param1 == "none")
            {
                return null;
            }
            _loc_3 = param1.split(new RegExp("\\s+"));
            _loc_4 = _loc_3.get(0);
            int _loc_5 =-1;
            int _loc_6 =-1;
            if (_loc_3.length > 1)
            {
                _loc_5 = MathUtils.parseInteger(_loc_3.get(1));
            }
            if (_loc_3.length > 2)
            {
                _loc_6 = MathUtils.parseInteger(_loc_3.get(2));
            }
            else
            {
                _loc_6 = _loc_5;
            }
            _loc_7 = _loc_5>-1&& _loc_6 > -1;
            if (_loc_4.charAt(0) == "@")
            {
                _loc_4 = _loc_4.substr(1);
                _loc_8 = LazyLoadRequest.checkLazy(_loc_4, this.__lazyCreator, [_loc_5, _loc_6, _loc_7]);
                if (_loc_8)
                {
                    return _loc_8;
                }
                _loc_9 = ResourceManager.ins.getAsset(_loc_4);
                if (_loc_9 == null)
                {
                    _loc_9 = DisplayUtils.createNoneAssetShape();
                }
                return new AssetIcon(_loc_9, _loc_5, _loc_6, _loc_7);
            }
            else
            {
                if (_loc_4.indexOf("url") == 0)
                {
                    _loc_10 = _loc_4.indexOf("(") + 2;
                    _loc_11 = _loc_4.indexOf(")") - 1;
                    _loc_12 = _loc_4.substring(_loc_10, _loc_11);
                    _loc_8 = new LazyLoadRequest(_loc_12, null, this.__lazyCreator, [_loc_5, _loc_6, _loc_7]);
                    return _loc_8;
                }
                AGLog.warn("unknown icon css value:" + param1);
            }
            return;
        }//end

        private AssetIcon  __lazyCreator (LazyLoadData param1 )
        {
            DisplayObject _loc_2 =null ;
            if (param1.content)
            {
                _loc_2 = param1.content;
            }
            else
            {
                _loc_2 = param1.getSymbol();
            }
            if (_loc_2 == null)
            {
                _loc_2 = DisplayUtils.createNoneAssetShape();
            }
            return new AssetIcon(_loc_2, param1.params.get(0), param1.params.get(1), param1.params.get(3));
        }//end

        public boolean  mutabble ()
        {
            return true;
        }//end

        public Object aggregate (Array param1 )
        {
            return null;
        }//end

    }


