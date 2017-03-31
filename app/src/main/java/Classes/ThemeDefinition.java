package Classes;

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

//import flash.utils.*;
    public class ThemeDefinition implements IThemeDefinition
    {
        private Dictionary m_items ;
        private XMLList m_themeItemsXML ;

        public  ThemeDefinition (XML param1 )
        {
            this.m_items = new Dictionary();
            this.m_themeItemsXML = param1.items.item;
            return;
        }//end

        public Item  getThemeItemByName (String param1 ,Item param2 )
        {
            XML _loc_3 =null ;
            if (param2 == null)
            {
                param2 = Global.gameSettings().getItemByName(param1);
            }
            if (this.m_items.get(param1) == null)
            {
                for(int i0 = 0; i0 < this.m_themeItemsXML.size(); i0++)
                {
                	_loc_3 = this.m_themeItemsXML.get(i0);

                    if (_loc_3.@name == param1)
                    {
                        this.m_items.put(param1,  new Item(this.applyThemeXML(_loc_3, param2.xml)));
                        break;
                    }
                }
                if (this.m_items.get(param1) == null)
                {
                    this.m_items.put(param1,  param2);
                }
            }
            return this.m_items.get(param1);
        }//end

        private XML  applyThemeXML (XML param1 ,XML param2 )
        {
            XML _loc_5 =null ;
            XMLList _loc_6 =null ;
            XML _loc_7 =null ;
            _loc_3 = param2.copy ();
            _loc_4 = param1.elements ();
            for(int i0 = 0; i0 < _loc_4.size(); i0++)
            {
            	_loc_5 = _loc_4.get(i0);

                _loc_6 = _loc_3.elements(_loc_5.name());
                for(int i0 = 0; i0 < _loc_6.size(); i0++)
                {
                	_loc_7 = _loc_6.get(i0);

                    if (this.compareElements(_loc_5, _loc_7))
                    {
                        _loc_7.replace("*", _loc_5.elements());
                    }
                }
            }
            return _loc_3;
        }//end

        private boolean  compareElements (XML param1 ,XML param2 )
        {
            _loc_3 = param1.name ()==param2.name ();
            _loc_4 = param1.attributes ()==param2.attributes ();
            return _loc_3 && _loc_4;
        }//end

    }



