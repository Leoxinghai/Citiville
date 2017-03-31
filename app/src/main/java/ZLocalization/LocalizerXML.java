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

    public class LocalizerXML extends Localizer
    {

        public  LocalizerXML (XML param1 )
        {
            XML _loc_3 =null ;
            Object _loc_4 =null ;
            XMLList _loc_5 =null ;
            XML _loc_6 =null ;
            String _loc_7 =null ;
            super(param1);
            this.m_raw = {};
            this.m_cached = {};
            _loc_2 = param1.pkg ;
            for(int i0 = 0; i0 < _loc_2.size(); i0++) 
            {
            		_loc_3 = _loc_2.get(i0);

                _loc_4 = {};
                _loc_5 = _loc_3.string;
                for(int i0 = 0; i0 < _loc_5.size(); i0++) 
                {
                		_loc_6 = _loc_5.get(i0);

                    _loc_4.put(_loc_6.@key.toString(),  _loc_6);
                }
                _loc_7 = _loc_3.@name.toString();
                this.m_raw.put(_loc_7,  _loc_4);
                this.m_cached.put(_loc_7,  {});
            }
            this.m_locale = param1.@locale.toString();
            this.setSubstituter();
            return;
        }//end

        protected LocalizedString  parseStringNode (XML param1 )
        {
            XML _loc_6 =null ;
            _loc_2 = param1.original.toString ();
            Object _loc_3 ={};
            int _loc_4 =0;
            while (_loc_4 < param1.variation.length())
            {

                _loc_6 = param1.variation.get(_loc_4);
                _loc_3.put(_loc_6.@index.toString(),  _loc_6.toString());
                _loc_4++;
            }
            LocalizedString _loc_5 =new LocalizedString(_loc_2 ,_loc_3 );
            if (param1.@gender.toString() != "")
            {
                _loc_5.gender = param1.@gender.toString();
            }
            return _loc_5;
        }//end

         public LocalizedString  getString (String param1 ,String param2 )
        {
            if (!this.m_raw.hasOwnProperty(param1))
            {
                return null;
            }
            if (!this.m_cached.get(param1).hasOwnProperty(param2))
            {
                if (this.m_raw.get(param1).hasOwnProperty(param2))
                {
                    this.m_cached.get(param1).put(param2,  this.parseStringNode(this.m_raw.get(param1).get(param2)));
                }
                else
                {
                    return null;
                }
            }
            return this.m_cached.get(param1).get(param2);
        }//end

    }


