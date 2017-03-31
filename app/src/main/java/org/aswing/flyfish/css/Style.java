package org.aswing.flyfish.css;

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
import org.aswing.flyfish.*;

    public class Style extends Object implements IStyle
    {
        protected String nam ;
        protected String valu ;
        protected StyleDefinition def ;
        protected Array members ;
        protected Object dValue ;
        public static  RegExp BLACK_SEP =new RegExp("\\s+");

        public  Style (String param1 ,String param2 ,StyleDefinition param3 )
        {
            Array _loc_4 =null ;
            Array _loc_5 =null ;
            int _loc_6 =0;
            int _loc_7 =0;
            StyleDefinition _loc_8 =null ;
            Style _loc_9 =null ;
            this.nam = param1;
            this.valu = param2;
            this.def = param3;
            if (param3.isAggregation())
            {
                _loc_4 = new Array();
                if (param2 != null && param2 != "")
                {
                    _loc_4 = param2.split(BLACK_SEP);
                }
                _loc_5 = param3.getMembers();
                _loc_6 = _loc_5.length;
                this.members = new Array(_loc_6);
                _loc_7 = 0;
                while (_loc_7 < _loc_5.length())
                {

                    _loc_8 = _loc_5.get(_loc_7);
                    _loc_9 = new Style(_loc_8.getName(), _loc_4.get(_loc_7), _loc_8);
                    this.members.put(_loc_7,  _loc_9);
                    _loc_7++;
                }
                if (!param3.getDecoder().mutabble())
                {
                    this.updateAggregateValue();
                }
            }
            else if (!param3.getDecoder().mutabble())
            {
                if (param2 == null || StyleSheetParser.NULL == param2)
                {
                    this.dValue = null;
                }
                else
                {
                    this.dValue = param3.getDecoder().decode(param2, null);
                }
            }
            return;
        }//end

        public String  name ()
        {
            return this.nam;
        }//end

        public String  value ()
        {
            return this.valu;
        }//end

        public StyleDefinition  definition ()
        {
            return this.def;
        }//end

        public void  putMember (IStyle param1 )
        {
            IStyle md ;
            style = param1;
            if (!this.def.isAggregation())
            {
                throw new Error("This is not a aggregation style, can\'t put sub-style!");
            }
            n = this.members.length;
            int i ;
            while (i < n)
            {

                md = this.members.get(i);
                if (md.name == style.name)
                {
                    this.members.put(i,  style);
                    break;
                }
                i = (i + 1);
            }
            if (!this.def.getDecoder().mutabble())
            {
                try
                {
                    this.updateAggregateValue();
                }
                catch (er:Error)
                {
                    AGLog.error("css " + name + " error - " + er);
                }
            }
            return;
        }//end

        protected void  updateAggregateValue ()
        {
            IStyle _loc_4 =null ;
            _loc_1 = this.members.length ;
            _loc_2 = new Array(_loc_1 );
            int _loc_3 =0;
            while (_loc_3 < _loc_1)
            {

                _loc_4 = this.members.get(_loc_3);
                _loc_2.put(_loc_3,  _loc_4.decodedValue(null));
                if (_loc_2.get(_loc_3) is LazyLoadRequest)
                {
                    throw new Error("Style AggregateValue can not be .get(LazyLoadRequest)");
                }
                _loc_3++;
            }
            this.dValue = this.def.getDecoder().aggregate(_loc_2);
            return;
        }//end

        public Object decodedValue (Component param1 )
        {
            if (this.def.getDecoder().mutabble())
            {
                if (this.def.isAggregation())
                {
                    this.updateAggregateValue();
                }
                else if (this.valu == null || StyleSheetParser.NULL == this.valu)
                {
                    this.dValue = null;
                }
                else
                {
                    this.dValue = this.def.getDecoder().decode(this.valu, param1);
                }
            }
            return this.dValue;
        }//end

        public LazyLoadRequest  apply (Component param1 )
        {
            LazyLoadRequest _loc_3 =null ;
            _loc_2 = this.decodedValue(param1 );
            if (_loc_2 is LazyLoadRequest)
            {
                _loc_3 =(LazyLoadRequest) _loc_2;
                _loc_3.setTarget(param1, this.def.getInjector());
                return _loc_3;
            }
            this.def.getInjector().inject(param1, _loc_2);
            return null;
        }//end

        public String  toString ()
        {
            return this.name + ":" + this.valu;
        }//end

    }


