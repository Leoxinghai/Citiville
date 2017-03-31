package validation.util;

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

import Classes.*;
//import flash.utils.*;
import validation.*;

    public class ItemValidationUtil implements IValidationUtilClass
    {
        protected Dictionary m_validators ;

        public  ItemValidationUtil ()
        {
            this.loadValidators();
            return;
        }//end

        public Function  getValidationCallback (String param1 )
        {
            return this.m_validators.get(param1);
        }//end

        protected void  loadValidators ()
        {
            this.m_validators = new Dictionary();
            this .m_validators.put( "isType", boolean  (Object param1 );
            {
                _loc_2 = param1.type ;
                return _loc_2 && (this as ItemInstance).getItem().type == _loc_2;
            }//end
            ;
            this .m_validators.put( "isNotType", boolean  (Object param1 );
            {
                _loc_2 = param1.type ;
                return _loc_2 && (this as ItemInstance).getItem().type != _loc_2;
            }//end
            ;
            this .m_validators.put( "hasKeyword", boolean  (Object param1 );
            {
                _loc_2 = param1.keyword ;
                return _loc_2 && (this as ItemInstance).getItem().itemHasKeyword(_loc_2);
            }//end
            ;
            return;
        }//end

    }



