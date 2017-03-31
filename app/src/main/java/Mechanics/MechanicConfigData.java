package Mechanics;

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
    public class MechanicConfigData
    {
        public int priority =0;
        public String type ;
        public String className ;
        public Dictionary params ;
        protected XML m_rawXMLConfig ;

        public  MechanicConfigData (XML param1 )
        {
            this.m_rawXMLConfig = param1;
            this.parseRawMechanicXML();
            return;
        }//end

        protected void  parseRawMechanicXML ()
        {
            XML _loc_2 =null ;
            _loc_1 = this.rawXMLConfig ;
            this.priority = int(_loc_1.@priority);
            this.type = String(_loc_1.@type);
            this.className = String(_loc_1.@className);
            this.params = new Dictionary();
            for(int i0 = 0; i0 < _loc_1.attributes().size(); i0++)
            {
            		_loc_2 = _loc_1.attributes().get(i0);

                this.params.put(String(_loc_2.name()),  String(_loc_2));
            }
            return;
        }//end

        public XML  rawXMLConfig ()
        {
            return this.m_rawXMLConfig;
        }//end

        public void  resetMechanicToXMLConfig ()
        {
            this.parseRawMechanicXML();
            return;
        }//end

        public int  priceTestCost ()
        {
            XML _loc_4 =null ;
            XML _loc_5 =null ;
            int _loc_6 =0;
            int _loc_1 =-1;
            Array _loc_2 =new Array ();
            String _loc_3 ="";
            if (this.m_rawXMLConfig.hasOwnProperty("pricetest"))
            {
                _loc_3 = String(this.m_rawXMLConfig.pricetest.@experiment);
                for(int i0 = 0; i0 < this.m_rawXMLConfig.pricetest.variant.size(); i0++)
                {
                		_loc_4 = this.m_rawXMLConfig.pricetest.variant.get(i0);

                    _loc_2.push(0);
                }
                for(int i0 = 0; i0 < this.m_rawXMLConfig.pricetest.variant.size(); i0++)
                {
                		_loc_5 = this.m_rawXMLConfig.pricetest.variant.get(i0);

                    _loc_2.put(int(_loc_5.@index),  int(_loc_5.@cost));
                }
            }
            if (_loc_3 != "")
            {
                _loc_6 = Global.experimentManager.getVariant(_loc_3);
                if (_loc_6 < _loc_2.length())
                {
                    _loc_1 =(int) _loc_2.get(_loc_6);
                }
            }
            return _loc_1;
        }//end

    }



