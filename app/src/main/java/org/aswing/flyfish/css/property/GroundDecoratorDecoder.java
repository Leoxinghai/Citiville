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

    public class GroundDecoratorDecoder extends Object implements ValueDecoder
    {

        public  GroundDecoratorDecoder ()
        {
            return;
        }//end

        public Object decode (String param1 ,Component param2 )
        {
            LazyLoadRequest _loc_11 =null ;
            String _loc_13 =null ;
            DisplayObject _loc_14 =null ;
            int _loc_15 =0;
            int _loc_16 =0;
            String _loc_17 =null ;
            ASColor _loc_18 =null ;
            _loc_3 = param1.split(new RegExp("\\s+"));
            _loc_4 = _loc_3.get(0);
            boolean _loc_5 =false ;
            int _loc_6 =0;
            int _loc_7 =0;
            int _loc_8 =0;
            int _loc_9 =0;
            boolean _loc_10 =false ;
            if (_loc_3.length >= 2)
            {
                _loc_5 = BooleanDecoder.staticDecode(_loc_3.get(1));
            }
            if (_loc_3.length >= 3)
            {
                _loc_10 = true;
                _loc_6 = MathUtils.parseInteger(_loc_3.get(2));
            }
            if (_loc_3.length >= 4)
            {
                _loc_7 = MathUtils.parseInteger(_loc_3.get(3));
            }
            if (_loc_3.length >= 5)
            {
                _loc_8 = MathUtils.parseInteger(_loc_3.get(4));
            }
            if (_loc_3.length >= 6)
            {
                _loc_9 = MathUtils.parseInteger(_loc_3.get(5));
            }
            Insets _loc_12 =null ;
            if (_loc_10)
            {
                _loc_12 = new Insets(_loc_6, _loc_7, _loc_8, _loc_9);
            }
            if (_loc_4 == "none")
            {
                return null;
            }
            if (_loc_4.charAt(0) == "@")
            {
                _loc_13 = _loc_4.substr(1);
                _loc_11 = LazyLoadRequest.checkLazy(_loc_13, this.__lazyCreator, [_loc_5, _loc_12]);
                if (_loc_11)
                {
                    return _loc_11;
                }
                _loc_14 = ResourceManager.ins.getAsset(_loc_13);
                if (_loc_14 == null)
                {
                    _loc_14 = DisplayUtils.createNoneAssetShape();
                }
                return new AssetBackground(_loc_14, _loc_5, _loc_12);
            }
            else
            {
                if (_loc_4.indexOf("url") == 0)
                {
                    _loc_15 = _loc_4.indexOf("(") + 2;
                    _loc_16 = _loc_4.indexOf(")") - 1;
                    _loc_17 = _loc_4.substring(_loc_15, _loc_16);
                    _loc_11 = new LazyLoadRequest(_loc_17, null, this.__lazyCreator, [_loc_5, _loc_12]);
                    return _loc_11;
                }
                _loc_18 = ColorDecoder.staticDecode(_loc_4);
                return new SolidBackground(_loc_18, _loc_5, _loc_12);
            }
        }//end

        private AssetBackground  __lazyCreator (LazyLoadData param1 )
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
            return new AssetBackground(_loc_2, param1.params.get(0), param1.params.get(1));
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


