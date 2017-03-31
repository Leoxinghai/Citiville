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
import org.aswing.flyfish.*;
import org.aswing.flyfish.css.*;
import org.aswing.flyfish.util.*;
import org.aswing.util.*;

    public class ComModel extends Object implements Model
    {
        private ComDefinition def ;
        private Component display ;
        private ArrayList children ;
        private ArrayList properties ;
        private boolean container ;
        private ComModel parent ;
        private String id ;
        private String atrributeScope ;
        private String getterScope ;
        private boolean useTempID =false ;
        private boolean useEmptyLayout =false ;
        private ProModel locationPro ;
        private ProModel sizePro ;
        private Function changedHandler ;
        public static  String DISPLAY_MODEL_KEY ="displayModelKey";
        public static  String CLASS_NAME ="class";
        public static  String STYLE_NAME ="style";
        public static  String LAYOUT_NAME ="Layout";
        public static  String LAYOUT_EMPTY ="EmptyLayout";
        public static  String LOCATION_NAME ="Location";
        public static  String SIZE_NAME ="Size";
        public static  String PARAM_AUTO ="auto";
        public static  String PARAM_MANUAL ="manual";
        public static  String ID_NAME ="id";
        public static  String ATTR_SCOPE_NAME ="attr-scope";
        public static  String GETTER_SCOPE_NAME ="getter-scope";
        public static  String SCOPE_NONE ="none";
        public static  String SCOPE_PRIVATE ="private";
        public static  String SCOPE_PROTECTED ="protected";
        public static  String SCOPE_PUBLIC ="public";
        public static  String SCOPE_INTERNAL ="internal";
        public static  String ATTR_SCOPE_DEFAULT ="private";
        public static  String GETTER_SCOPE_DEFAULT ="none";
        private static int id_counter =0;

        public  ComModel (ComDefinition param1 ,boolean param2 =true )
        {
            this.atrributeScope = ATTR_SCOPE_DEFAULT;
            this.getterScope = GETTER_SCOPE_DEFAULT;
            _loc_4 = id_counter+1;
            id_counter = _loc_4;
            if (param1 != null)
            {
                this.setATempID(param1);
                this.create(param1, param2);
            }
            return;
        }//end

        private void  setATempID (ComDefinition param1 )
        {
            _loc_2 = param1.getName ();
            if (_loc_2.charAt(0) == "J")
            {
                _loc_2 = _loc_2.substr(1);
            }
            _loc_2 = _loc_2.toLowerCase();
            this.id = _loc_2 + "_temp" + id_counter;
            this.useTempID = true;
            return;
        }//end

        public void  create (ComDefinition param1 ,boolean param2 )
        {
            if (this.def != null)
            {
                throw new Error("This com model is already created!");
            }
            this.def = param1;
            this.children = new ArrayList();
            this.properties = new ArrayList();
            _loc_3 = param1.getClass ();
            this.display = new _loc_3;
            this.display.putClientProperty(DISPLAY_MODEL_KEY, this);
            this.container = param1.isContainer();
            this.display.awml_info.tag = param1.getName();
            _loc_4 = param1.getProperties ();
            int _loc_5 =0;
            while (_loc_5 < _loc_4.length())
            {

                this.properties.append(new ProModel(_loc_4.get(_loc_5)));
                _loc_5++;
            }
            this.properties.append(new PackProModel());
            this.initProModels(param2);
            return;
        }//end

        public ComDefinition  getDefinition ()
        {
            return this.def;
        }//end

        public String  getAttributeScope ()
        {
            return this.atrributeScope;
        }//end

        public String  getGetterScope ()
        {
            return this.getterScope;
        }//end

        public String  getID ()
        {
            return this.id;
        }//end

        public void  setChangedHandler (Function param1 )
        {
            this.changedHandler = param1;
            return;
        }//end

        public void  parse (Object param1)
        {
            XML _loc_6 =null ;
            XMLList _loc_7 =null ;
            XML _loc_8 =null ;
            String _loc_9 =null ;
            ProModel _loc_10 =null ;
            ComModel _loc_11 =null ;
            _loc_2 = param1;
            _loc_3 = _loc_2.localName ();
            _loc_4 = Definition.getIns().getComDefinition(_loc_3);
            if (_loc_4 == null)
            {
                throw new Error("Unknown component tag - " + _loc_3 + " of \n\t" + _loc_2);
            }
            this.id = _loc_2.@id;
            if (this.id == null || this.id == "")
            {
                this.setATempID(_loc_4);
            }
            else
            {
                this.useTempID = false;
            }
            this.create(_loc_4, false);
            _loc_5 = _loc_2.attributes ();
            for(int i0 = 0; i0 < _loc_5.size(); i0++)
            {
            		_loc_6 = _loc_5.get(i0);

                _loc_9 = _loc_6.localName();
                _loc_10 = this.getPropertyModel(_loc_9);
                if (_loc_10)
                {
                    _loc_10.parse(_loc_6);
                    continue;
                }
                AGLog.warn("Unknown AWML component property - " + _loc_9);
            }
            _loc_7 = _loc_2.children();
            for(int i0 = 0; i0 < _loc_7.size(); i0++)
            {
            		_loc_8 = _loc_7.get(i0);

                _loc_11 = new ComModel();
                _loc_11.parse(_loc_8);
                this.addChild(_loc_11);
            }
            return;
        }//end

        public LazyLoadRequestList  applyCSS (StyleSheetList param1 )
        {
            ComModel _loc_4 =null ;
            trace("start apply css of " + this.def.getName() + " # " + this.def.getClassName());
            _loc_2 = param1.apply(this.display );
            _loc_2.combine(this.applyInlineCSS());
            _loc_3 = this.children.toArray ();
            for(int i0 = 0; i0 < _loc_3.size(); i0++)
            {
            		_loc_4 = _loc_3.get(i0);

                _loc_2.combine(_loc_4.applyCSS(param1));
            }
            trace("end apply css");
            return _loc_2;
        }//end

        public LazyLoadRequestList  applyInlineCSS ()
        {
            return StyleSheetParser.ins.applyInlineCSS(this.display);
        }//end

        public XML  encodeXML ()
        {
            ProModel _loc_5 =null ;
            XML _loc_6 =null ;
            String _loc_7 =null ;
            boolean _loc_8 =false ;
            ComModel _loc_9 =null ;
            _loc_1 = this.def.getName ();
            _loc_2 = new XML("<"+_loc_1 +"/>");
            if (!this.useTempID)
            {
                _loc_2.@id = this.id;
            }
            _loc_3 = this.properties.size ();
            int _loc_4 =0;
            while (_loc_4 < _loc_3)
            {

                _loc_5 = this.properties.get(_loc_4);
                _loc_6 = _loc_5.encodeXML();
                _loc_7 = _loc_5.getName();
                if (_loc_6 != null)
                {
                    _loc_8 = true;
                    if (_loc_7 == ATTR_SCOPE_NAME && _loc_6 == ATTR_SCOPE_DEFAULT)
                    {
                        _loc_8 = false;
                    }
                    else if (_loc_7 == GETTER_SCOPE_NAME && _loc_6 == GETTER_SCOPE_DEFAULT)
                    {
                        _loc_8 = false;
                    }
                    else if (_loc_7 == ID_NAME)
                    {
                        _loc_8 = false;
                    }
                    else if (_loc_7 == LOCATION_NAME || _loc_7 == SIZE_NAME)
                    {
                        if (!this.isInEmptyLayout())
                        {
                            _loc_8 = false;
                        }
                    }
                    if (_loc_8)
                    {
                        _loc_2.put("@" + _loc_7,  _loc_6);
                    }
                }
                _loc_4++;
            }
            _loc_3 = this.children.size();
            _loc_4 = 0;
            while (_loc_4 < _loc_3)
            {

                _loc_9 = this.children.get(_loc_4);
                _loc_2.appendChild(_loc_9.encodeXML());
                _loc_4++;
            }
            return _loc_2;
        }//end

        private void  initProModels (boolean param1 )
        {
            ProModel _loc_3 =null ;
            int _loc_2 =0;
            while (_loc_2 < this.properties.size())
            {

                _loc_3 = this.properties.get(_loc_2);
                if (_loc_3.getName() == LOCATION_NAME)
                {
                    this.locationPro = _loc_3;
                }
                else if (_loc_3.getName() == SIZE_NAME)
                {
                    this.sizePro = _loc_3;
                }
                _loc_3.bindTo(this, param1);
                _loc_2++;
            }
            return;
        }//end

        private ProModel  getPropertyModel (String param1 )
        {
            ProModel _loc_4 =null ;
            _loc_2 = this.properties.size ();
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

        public void  applyProperty (String param1 ,ValueModel param2 ,String param3 )
        {
            Object _loc_5 =null ;
            LayoutModel _loc_6 =null ;
            boolean _loc_4 =false ;
            if (param1 == ID_NAME)
            {
                this.id = param2.getValue();
                if (this.id == null || this.id == "")
                {
                    this.setATempID(this.def);
                }
                else
                {
                    this.useTempID = false;
                }
                _loc_4 = true;
                this.display.awml_info.id = this.id;
            }
            else if (param1 == CLASS_NAME)
            {
                this.display.awml_info.clazz = param2.getValue();
            }
            else if (param1 == STYLE_NAME)
            {
                this.display.awml_info.style_inline = param2.getValue();
            }
            else if (param1 == ATTR_SCOPE_NAME)
            {
                this.atrributeScope = param2.getValue();
            }
            else if (param1 == GETTER_SCOPE_NAME)
            {
                this.getterScope = param2.getValue();
            }
            else
            {
                _loc_5 = this.getDisplay();
                FlyFishUtils.setValue(_loc_5, param1, param2.getValue());
                if (param3 != null && param3 != "")
                {
                    _loc_7 = _loc_5;
                    _loc_7._loc_5.get(param3)();
                }
                if (param1 == LAYOUT_NAME)
                {
                    _loc_6 =(LayoutModel) param2;
                    if (_loc_6.getName() == LAYOUT_EMPTY)
                    {
                        this.useEmptyLayout = true;
                    }
                    else
                    {
                        this.useEmptyLayout = false;
                    }
                }
            }
            if (this.changedHandler != null)
            {
                this.changedHandler(this, _loc_4);
            }
            return;
        }//end

        public Component  getDisplay ()
        {
            return this.display;
        }//end

        public Object getValue ()
        {
            return this.display;
        }//end

        public ComModel  getParent ()
        {
            return this.parent;
        }//end

        public boolean  isInEmptyLayout ()
        {
            if (this.parent == null)
            {
                return true;
            }
            return this.parent.useEmptyLayout;
        }//end

        public Array  getChildren ()
        {
            return this.children.toArray();
        }//end

        public ComModel  getChild (int param1 )
        {
            return this.children.get(param1);
        }//end

        public int  getChildCount ()
        {
            return this.children.size();
        }//end

        public int  getChildIndex (Object param1)
        {
            return this.children.indexOf(param1);
        }//end

        public Array  getProperties ()
        {
            return this.properties.toArray();
        }//end

        public ProModel  getProperty (String param1 )
        {
            ProModel _loc_3 =null ;
            int _loc_2 =0;
            while (_loc_2 < this.properties.size())
            {

                _loc_3 = this.properties.get(_loc_2);
                if (_loc_3.getName() == param1)
                {
                    return _loc_3;
                }
                _loc_2++;
            }
            return null;
        }//end

        public void  addChild (ComModel param1 ,int param2 =-1)
        {
            _loc_3 = null;
            if (this.isContainer())
            {
                this.children.append(param1, param2);
                _loc_3 = this.display;
                _loc_3.insert(param2, param1.getDisplay());
                _loc_3.revalidate();
                param1.parent = this;
            }
            else
            {
                throw new Error("This is not a container, can add child!");
            }
            return;
        }//end

        public ComModel  removeChild (ComModel param1 )
        {
            _loc_2 = null;
            if (!this.isContainer())
            {
                throw new Error("This is not a container, does not have child!");
            }
            if (param1.parent == this)
            {
                this.children.remove(param1);
                _loc_2 = this.display;
                _loc_2.remove(param1.getDisplay());
                _loc_2.revalidate();
                param1.parent = null;
                return param1;
            }
            return null;
        }//end

        public boolean  isContainer ()
        {
            return this.container;
        }//end

        public String  toString ()
        {
            return this.id + "-.get(" + this.def.getName() + ")";
        }//end

    }


