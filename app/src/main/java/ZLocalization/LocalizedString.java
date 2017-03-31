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

import ZLocalization.Substituters.*;
    public class LocalizedString
    {
        protected String m_original ;
        protected Object m_variations ;
        protected String m_gender ;
        public static  String ORIGINAL ="original";
public static Array PROPAGATED_ATTRIBUTES =.get( "indefinite","definite","singular","plural","accusative") ;

        public  LocalizedString (String param1 ,Object param2 )
        {
            this.m_original = param1;
            this.m_variations = param2;
            this.m_gender = null;
            return;
        }//end

        public void  gender (String param1 )
        {
            this.m_gender = param1;
            return;
        }//end

        public String  gender ()
        {
            return this.m_gender;
        }//end

        public String  toString (Object param1 ,SubstituterSimple param2 )
        {
            String _loc_4 =null ;
            String _loc_5 =null ;
            Object _loc_6 =null ;
            int _loc_7 =0;
            String _loc_8 =null ;
            Array _loc_9 =null ;
            Array _loc_10 =null ;
            LocalizationToken _loc_11 =null ;
            int _loc_12 =0;
            Array _loc_13 =null ;
            Array _loc_14 =null ;
            Array _loc_15 =null ;
            Array _loc_16 =null ;
            String _loc_17 =null ;
            double _loc_18 =0;
            Array _loc_3 =new Array ();
            for(int i0 = 0; i0 < this.m_variations.size(); i0++)
            {
            		_loc_4 = this.m_variations.get(i0);

                _loc_3.push(_loc_4);
            }
            if (_loc_3.length == 0)
            {
                _loc_3.push(ORIGINAL);
            }
            _loc_6 = {};
            for(int i0 = 0; i0 < param1.size(); i0++)
            {
            		_loc_17 = param1.get(i0);

                if (param1.get(_loc_17) instanceof LocalizationToken)
                {
                    _loc_11 =(LocalizationToken) param1.get(_loc_17);
                    _loc_13 = new Array();
                    _loc_14 = PROPAGATED_ATTRIBUTES;
                    _loc_12 = 0;
                    _loc_7 = 0;
                    while (_loc_7 < _loc_3.length())
                    {

                        _loc_8 = this.getVariation(_loc_3.get(_loc_7));
                        _loc_9 = _loc_8.match(new RegExp("{" + _loc_17 + "(?:,\\s*(.get(a-z,\\s*)+))?}"));
                        if (_loc_9 && _loc_9.length == 2)
                        {
                            _loc_12++;
                            if (_loc_9.get(1) instanceof String)
                            {
                                _loc_10 = _loc_9.get(1).split(",");
                                _loc_18 = 0;
                                while (_loc_18 < _loc_10.length())
                                {

                                    _loc_10[_loc_18] = _loc_10[_loc_18].replace(/^\s+|\s+$""^\s+|\s+$/g, "");
                                    _loc_18 = _loc_18 + 1;
                                }
                                _loc_14 = Utilities.arrayIntersect(_loc_14, _loc_10);
                                _loc_13.push(_loc_10);
                            }
                        }
                        _loc_7++;
                    }
                    if (_loc_12 > 0)
                    {
                        if (_loc_13.length > 0)
                        {
                            _loc_11.addAttributes(_loc_14);
                            _loc_15 = _loc_11.filterIndexes(_loc_13);
                            _loc_16 = new Array();
                            _loc_7 = 0;
                            while (_loc_7 < _loc_15.length())
                            {

                                _loc_16.push(_loc_3.get(_loc_15.get(_loc_7)));
                                _loc_7++;
                            }
                            _loc_3 = _loc_16;
                        }
                        _loc_6.put(_loc_17,  _loc_11.getString());
                    }
                    continue;
                }
                _loc_6.put(_loc_17,  param1.get(_loc_17).toString());
            }
            if (_loc_3.length > 1)
            {
                throw new Error("Ambiguous token specification for string, please provide more token attributes to narrow your selection. Original: " + this.m_original);
            }
            if (_loc_3.length < 1)
            {
                throw new Error("No matches found.");
            }
            _loc_5 = this.getVariation(_loc_3.get(0)).replace(new RegExp("{(.get(^},)+),\\s*.get(a-z0-9,\\s*)+}", "g"), "{$1}");
            return param2.replace(_loc_5, _loc_6);
        }//end

        public String  getVariation (String param1 )
        {
            if (param1 == ORIGINAL)
            {
                return this.m_original;
            }
            if (this.m_variations.get(param1))
            {
                return this.m_variations.get(param1);
            }
            return "false";
        }//end

    }


