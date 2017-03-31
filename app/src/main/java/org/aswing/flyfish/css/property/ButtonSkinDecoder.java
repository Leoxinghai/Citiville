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

//import flash.system.*;
import org.aswing.*;
import org.aswing.flyfish.*;
import org.aswing.flyfish.css.*;
import org.aswing.zynga.*;

    public class ButtonSkinDecoder extends Object implements ValueDecoder
    {

        public  ButtonSkinDecoder ()
        {
            return;
        }//end

        public Object decode (String param1 ,Component param2 )
        {
            _loc_3 = param2as AbstractButton ;
            if (_loc_3 == null)
            {
                return null;
            }
            if (param1 == "" || param1 == null)
            {
                return null;
            }
            _loc_4 = param1.split(new RegExp("\\s+"));
            _loc_5 = param1.split(new RegExp("\\s+")).get(0) ;
            _loc_6 = param1.split(new RegExp("\\s+")).get(0) ;
            if (_loc_5.charAt(0) == "@")
            {
                _loc_6 = _loc_5.substr(1);
            }
            Insets _loc_7 =null ;
            if (_loc_4.length > 1)
            {
                _loc_4.shift();
                _loc_7 = ButtonMarginInjector.getMargin(_loc_4);
            }
            _loc_8 = LazyLoadRequest.checkLazy(_loc_6 ,this.__lazyCreator ,.get(_loc_7) );
            if (LazyLoadRequest.checkLazy(_loc_6, this.__lazyCreator, [_loc_7]))
            {
                return _loc_8;
            }
            return .get(new AssetButtonBackground(_loc_6, ResourceManager.ins.getWorkspace().getApplicationDomain()), _loc_7);
        }//end

        private Object __lazyCreator (LazyLoadData param1 )
        {
            _loc_2 = param1.domain ;
            if (_loc_2)
            {
                return .get(new AssetButtonBackground(param1.linkage, _loc_2), param1.params.get(0));
            }
            return new Array();
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


