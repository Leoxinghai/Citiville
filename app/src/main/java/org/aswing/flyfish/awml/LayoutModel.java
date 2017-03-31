package org.aswing.flyfish.awml;

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
import org.aswing.flyfish.util.*;
import org.aswing.util.*;

    public class LayoutModel extends Object implements Model
    {
        private LayoutDefinition def ;
        private ArrayList properties ;
        private LayoutManager layout ;

        public  LayoutModel (LayoutDefinition param1)
        {
            if (param1 != null)
            {
                this.init(param1);
            }
            return;
        }//end

        private void  init (LayoutDefinition param1 )
        {
            ProModel _loc_5 =null ;
            this.def = param1;
            this.properties = new ArrayList();
            _loc_2 = param1.getProperties ();
            int _loc_3 =0;
            while (_loc_3 < _loc_2.length())
            {

                this.properties.append(new ProModel(_loc_2.get(_loc_3)));
                _loc_3++;
            }
            _loc_4 = param1.getClass ();
            this.layout = new _loc_4;
            _loc_3 = 0;
            while (_loc_3 < this.properties.size())
            {

                _loc_5 = this.properties.get(_loc_3);
                _loc_5.bindTo(this, true);
                _loc_3++;
            }
            return;
        }//end

        public void  parse (Object param1)
        {
            XML _loc_7 =null ;
            String _loc_8 =null ;
            ProModel _loc_9 =null ;
            _loc_2 = param1;
            _loc_2 = StringUtils.replace(_loc_2, "{", "<");
            _loc_2 = StringUtils.replace(_loc_2, "}", ">");
            XML _loc_3 =new XML(_loc_2 );
            _loc_4 = _loc_3.localName ();
            _loc_5 = Definition.getIns().getLayoutDefinition(_loc_4);
            this.init(_loc_5);
            XMLList _loc_6 =_loc_3.attributes ();
            for(int i0 = 0; i0 < _loc_6.size(); i0++)
            {
            		_loc_7 = _loc_6.get(i0);

                _loc_8 = _loc_7.localName();
                _loc_9 = this.getPropertyModel(_loc_8);
                if (_loc_9)
                {
                    _loc_9.parse(_loc_7);
                }
            }
            return;
        }//end

        public XML  encodeXML ()
        {
            ProModel _loc_6 =null ;
            XML _loc_7 =null ;
            _loc_1 = this.def.getName ();
            XML _loc_2 =new XML("<"+_loc_1 +"/>");
            int _loc_3 =this.properties.size ();
            int _loc_4 =0;
            while (_loc_4 < _loc_3)
            {

                _loc_6 = this.properties.get(_loc_4);
                _loc_7 = _loc_6.encodeXML();
                if (_loc_7 != null)
                {
                    _loc_2.put("@" + _loc_6.getName(),  _loc_7);
                }
                _loc_4++;
            }
            String _loc_5 =_loc_2.toXMLString ();
            _loc_5 = StringUtils.replace(_loc_5, "<", "{");
            _loc_5 = StringUtils.replace(_loc_5, ">", "}");
            _loc_5 = StringUtils.replace(_loc_5, "\"", "\'");
            return XML(_loc_5);
        }//end

        private ProModel  getPropertyModel (String param1 )
        {
            ProModel _loc_4 =null ;
            int _loc_2 =this.properties.size ();
            int _loc_3 =0;
            while (_loc_3 < _loc_2)
            {

                _loc_4 = this.properties.get(_loc_3);
                if (_loc_4.getName() == param1)
                {
                    return _loc_4;
                }
                _loc_3++;
            }
            return null;
        }//end

        public LayoutManager  getLayout ()
        {
            return this.layout;
        }//end

        public Object getValue ()
        {
            return this.layout;
        }//end

        public LayoutDefinition  getDef ()
        {
            return this.def;
        }//end

        public String  getName ()
        {
            return this.def.getName();
        }//end

        public Array  getProperties ()
        {
            return this.properties.toArray();
        }//end

        public void  applyProperty (String param1 ,ValueModel param2 ,String param3 )
        {
            _loc_4 = this.getLayout ();
            FlyFishUtils.setValue(_loc_4, param1, param2.getValue());
            if (param3 != null && param3 != "")
            {
                _loc_5 = _loc_4;
                _loc_5._loc_4.get(param3)();
            }
            return;
        }//end

        public String  toString ()
        {
            return this.def.getName();
        }//end

    }


