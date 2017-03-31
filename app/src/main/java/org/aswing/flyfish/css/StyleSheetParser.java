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
import org.aswing.flyfish.css.property.*;
import org.aswing.util.*;

    public class StyleSheetParser extends Object
    {
        private HashMap styleDefinitions ;
        private HashMap valueDecoders ;
        private XML xml ;
        public static  String NULL ="null";
        private static StyleSheetParser _ins ;
        private static  Class EmbeddedXML =StyleSheetParser_EmbeddedXML ;

        public  StyleSheetParser (XML param1 )
        {
            if (_ins)
            {
                throw new Error("This is a sington!");
            }
            this.doInit(param1);
            return;
        }//end

        public XML  getDefXML ()
        {
            return this.xml;
        }//end

        protected void  doInit (XML param1 )
        {
            XML _loc_3 =null ;
            XMLList _loc_4 =null ;
            XML _loc_5 =null ;
            String _loc_6 =null ;
            ValueDecoder _loc_7 =null ;
            String _loc_8 =null ;
            String _loc_9 =null ;
            ValueDecoder _loc_10 =null ;
            ValueInjector _loc_11 =null ;
            String _loc_12 =null ;
            StyleDefinition _loc_13 =null ;
            String _loc_14 =null ;
            Array _loc_15 =null ;
            Class _loc_16 =null ;
            StyleDefinition _loc_17 =null ;
            this.xml = param1;
            this.valueDecoders = new HashMap();
            _loc_2 = param1.Types.Type ;
            for(int i0 = 0; i0 < _loc_2.size(); i0++)
            {
            		_loc_3 = _loc_2.get(i0);

                _loc_6 = _loc_3.@name;
                _loc_7 = Reflection.createInstance(_loc_3.@decoder);
                this.valueDecoders.put(_loc_6, _loc_7);
                AGLog.log("Init css value decoder " + _loc_6 + " - " + _loc_7);
            }
            this.styleDefinitions = new HashMap();
            _loc_4 = param1.Properties.Property;
            for(int i0 = 0; i0 < _loc_4.size(); i0++)
            {
            		_loc_5 = _loc_4.get(i0);

                _loc_8 = _loc_5.@type + "";
                _loc_9 = _loc_8.split(":").get(0);
                _loc_10 = this.valueDecoders.getValue(_loc_9);
                if (_loc_10 == null)
                {
                    throw new Error("Can not find the decoder of type : " + _loc_9);
                }
                _loc_11 = null;
                _loc_12 = _loc_5.@injector;
                if (_loc_12 && _loc_12 != "")
                {
                    _loc_15 = _loc_12.split(":");
                    _loc_16 = Reflection.getClass(_loc_15.get(0));
                    _loc_11 = new _loc_16(_loc_15.get(1));
                }
                _loc_13 = new StyleDefinition(_loc_5.@name, _loc_10, _loc_11);
                if (this.styleDefinitions.containsKey(_loc_13.getName()))
                {
                    throw new Error("The style name of : " + _loc_13.getName() + " has defined more than once!");
                }
                _loc_14 = _loc_5.@holder;
                if (_loc_14 != "" && _loc_14 != null)
                {
                    _loc_17 = this.styleDefinitions.getValue(_loc_14);
                    if (_loc_17)
                    {
                        _loc_17.addMember(_loc_13);
                        _loc_13.setHolder(_loc_17);
                    }
                    else
                    {
                        throw new Error("Can not find holder : " + _loc_14);
                    }
                }
                this.styleDefinitions.put(_loc_13.getName(), _loc_13);
            }
            return;
        }//end

        public ValueDecoder  getValueDecoder (String param1 )
        {
            return this.valueDecoders.getValue(param1);
        }//end

        public StyleDefinition  getStyleDefinition (String param1 )
        {
            return this.styleDefinitions.getValue(param1);
        }//end

        public LazyLoadRequestList  applyInlineCSS (Component param1 )
        {
            Array styles ;
            StyleSheet ss ;
            c = param1;
            styleStr = c.awml_info.style_inline;
            if (styleStr == null)
            {
                return null;
            }
            styleStr = StringUtils.trim(styleStr);
            if (styleStr == "")
            {
                return null;
            }
            try
            {
                styles = this.buildStyles(styleStr);
                ss = new StyleSheet(new InlineSelector());
                ss.putStyles(styles);
                return ss.apply(c);
            }
            catch (er:Error)
            {
                AGLog.error(er + "");
            }
            return null;
        }//end

        public StyleSheetList  parse (String param1 )
        {
            String _loc_7 =null ;
            if (this.styleDefinitions == null)
            {
                throw new Error("The parser is not inited yet!");
            }
            if (param1 == null || param1 == "")
            {
                return new StyleSheetList();
            }
            String _loc_2 ="\\s*(.get(\\{:,;>\\}))\\s*|(?<!/)\\/\\*.get(\\s\\S)*?\\*\\/\\s*";
            _loc_3 = new RegExp(_loc_2 ,"g");
            param1 = param1.replace(_loc_3, "$1");
            _loc_4 = new RegExp("[^{]*{[^}]*}","g");
            _loc_5 = param1.match(_loc_4 );
            _loc_6 = new StyleSheetList ();
            for(int i0 = 0; i0 < _loc_5.size(); i0++)
            {
            		_loc_7 = _loc_5.get(i0);

                this.addStyleSheet(_loc_6, _loc_7);
            }
            return _loc_6;
        }//end

        private void  addStyleSheet (StyleSheetList param1 ,String param2 )
        {
            String _loc_7 =null ;
            String _loc_8 =null ;
            Array _loc_9 =null ;
            ISelector _loc_10 =null ;
            StyleSheet _loc_11 =null ;
            _loc_3 = param2.indexOf("{");
            _loc_4 = param2.substring(0,_loc_3 );
            _loc_5 = param2.substring(0,_loc_3 ).split(",");
            Array _loc_6 =new Array();
            for(int i0 = 0; i0 < _loc_5.size(); i0++)
            {
            		_loc_7 = _loc_5.get(i0);

                _loc_6.push(this.buildSelector(_loc_7));
            }
            _loc_8 = param2.substring((_loc_3 + 1), (param2.length - 1));
            _loc_9 = this.buildStyles(_loc_8);
            for(int i0 = 0; i0 < _loc_6.size(); i0++)
            {
            		_loc_10 = _loc_6.get(i0);

                _loc_11 = new StyleSheet(_loc_10);
                _loc_11.putStyles(_loc_9);
                param1.putStyleSheet(_loc_11);
            }
            return;
        }//end

        private ISelector  buildSelector (String param1 )
        {
            String _loc_4 =null ;
            int _loc_5 =0;
            String _loc_6 =null ;
            String _loc_7 =null ;
            ISelector _loc_8 =null ;
            ISelector _loc_9 =null ;
            _loc_2 = new RegExp("(\\s*>\\s*)|(\\s+)","g");
            _loc_3 = _loc_2.exec(param1 );
            if (_loc_3 != null)
            {
                _loc_4 = _loc_3.get(0);
                _loc_5 = _loc_3.index;
                _loc_6 = param1.substring(0, _loc_5);
                _loc_7 = param1.substr(_loc_5 + _loc_4.length());
                _loc_8 = this.buildOddSelector(_loc_6);
                _loc_9 = this.buildSelector(_loc_7);
                return this.buildCombinateSelector(_loc_8, _loc_9, _loc_4);
            }
            return this.buildOddSelector(param1);
        }//end

        private ISelector  buildOddSelector (String param1 )
        {
            _loc_2 = this.spliteWith(param1 ,"#");
            if (_loc_2)
            {
                return new IDSelector(_loc_2.get(0), _loc_2.get(1));
            }
            _loc_2 = this.spliteWith(param1, ".");
            if (_loc_2)
            {
                return new ClassSelector(_loc_2.get(0), _loc_2.get(1));
            }
            return new TagSelector(param1);
        }//end

        private Array  spliteWith (String param1 ,String param2 )
        {
            String _loc_3 =null ;
            String _loc_4 =null ;
            _loc_5 = param1.indexOf(param2 );
            if (param1.indexOf(param2) >= 0)
            {
                if (_loc_5 > 0)
                {
                    _loc_3 = param1.substring(0, _loc_5);
                }
                _loc_4 = param1.substr((_loc_5 + 1));
                if (_loc_3 == null)
                {
                    _loc_3 = TagSelector.WILDCARD;
                }
                return .get(_loc_3, _loc_4);
            }
            return null;
        }//end

        private ISelector  buildCombinateSelector (ISelector param1 ,ISelector param2 ,String param3 )
        {
            if (param3.indexOf(">") >= 0)
            {
                return new ChildSelector(param1, param2);
            }
            return new DescendantSelector(param1, param2);
        }//end

        private Array  buildStyles (String param1 )
        {
            String _loc_4 =null ;
            Array _loc_5 =null ;
            String _loc_6 =null ;
            String _loc_7 =null ;
            StyleDefinition _loc_8 =null ;
            Array _loc_2 =new Array();
            _loc_3 = param1.split(";");
            for(int i0 = 0; i0 < _loc_3.size(); i0++)
            {
            		_loc_4 = _loc_3.get(i0);

                if (_loc_4)
                {
                    _loc_5 = _loc_4.split(":");
                    if (_loc_5)
                    {
                        _loc_6 = _loc_5.get(0);
                        _loc_7 = _loc_5.get(1);
                        _loc_8 = StyleSheetParser.ins.getStyleDefinition(_loc_6);
                        if (_loc_8)
                        {
                            _loc_2.push(new Style(_loc_6, _loc_7, _loc_8));
                            continue;
                        }
                        AGLog.warn("unknown css style name : " + _loc_6 + ", it will be ignored!");
                    }
                }
            }
            return _loc_2;
        }//end

        public static StyleSheetParser  ins ()
        {
            XML _loc_1 =null ;
            if (_ins == null)
            {
                _loc_1 = new XML(EmbeddedXML.data);
                _ins = new StyleSheetParser(_loc_1);
            }
            return _ins;
        }//end

    }


