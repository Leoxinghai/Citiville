package ZLocalization;

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

    public class LocalizerSWF extends Localizer
    {

        public  LocalizerSWF (Object param1 )
        {
            super(param1);
            this.m_locale = param1.info.locale;
            this.m_cached = param1.text;
            this.setSubstituter();
            return;
        }//end

         public LocalizedString  getString (String param1 ,String param2 )
        {
            Object _loc_3 =null ;
            String _loc_4 =null ;
            Object _loc_5 =null ;
            LocalizedString _loc_6 =null ;
            if (this.m_cached.hasOwnProperty(param1) && this.m_cached.get(param1).hasOwnProperty(param2))
            {
                if (this.m_cached.get(param1).get(param2) instanceof LocalizedString)
                {
                    return this.m_cached.get(param1).get(param2);
                }
                _loc_3 = this.m_cached.get(param1).get(param2);
                _loc_4 = _loc_3.get("original");
                _loc_5 = _loc_3.hasOwnProperty("variations") ? (_loc_3.get("variations")) : (null);
                _loc_6 = new LocalizedString(_loc_4, _loc_5);
                if (_loc_3.hasOwnProperty("gender"))
                {
                    _loc_6.gender = _loc_3.get("gender");
                }
                this.m_cached.get(param1).put(param2,  _loc_6);
                return _loc_6;
            }
            else
            {
                return null;
            }
        }//end

    }


