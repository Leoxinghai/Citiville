package Modules.guide.actions;

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
import Display.*;
import Engine.*;
import Engine.Helpers.*;
import Engine.Managers.*;
import Modules.guide.*;
//import flash.display.*;
//import flash.geom.*;
//import flash.utils.*;

    public class GADisplayTarget extends GuideAction
    {
        protected double m_mode =0;
        protected String m_targetType ;
        protected XMLList m_xml ;
        protected int m_level =0;
        protected DisplayObjectContainer m_parent =null ;
        protected DisplayObject m_target ;
        protected Class m_targetClass ;
        protected Array m_targetObjectNames ;
        protected GameObject m_targetObject =null ;
        protected String m_dialogCallback =null ;
        protected Point m_focus ;
        protected Point m_size ;
        protected int m_style ;
        protected double m_alpha =0;
        protected boolean m_showOutline =false ;
        protected double m_spotlightOffsetX =0;
        protected double m_spotlightOffsetY =0;
        protected Function m_spotlightFunc ;
        public static  int MODE_LOCATION =0;
        public static  int MODE_TILE =1;
        public static  int MODE_RELATIVE =2;
        public static  int MODE_TARGET_CLASS =3;
        public static  int MODE_TARGET_TYPE =4;
        public static  int MODE_ACTIVE_ROLL_CALL_TYPE =5;
        public static  int MODE_TARGET_ROOT =6;
        public static  int MODE_CURRENT_DIALOG_CALLBACK =7;
        public static  int MODE_DEFAULT =0;

        public  GADisplayTarget ()
        {
            this.m_targetObjectNames = new Array();
            return;
        }//end

         public boolean  createFromXml (XML param1 )
        {
            DisplayObject _loc_14 =null ;
            Object _loc_15 =null ;
            this.m_xml = checkAndGetElement(param1, this.m_targetType);
            if (!this.m_xml)
            {
                return false;
            }
            _loc_2 = this.m_xml.@mode;
            this.m_focus = new Point(Number(this.m_xml.@x), Number(this.m_xml.@y));
            if (Global.player.socialNetworkId == 1 && _loc_2 == "location")
            {
                this.m_focus.x = this.m_focus.x - 97;
            }
            this.m_size = new Point(Number(this.m_xml.@w), Number(this.m_xml.@h));
            this.m_style = String(this.m_xml.@style) == "rect" ? (GuideConstants.MASK_STYLE_RECTANGLE) : (GuideConstants.MASK_STYLE_ELLIPSE);
            _loc_3 = this.m_xml.@level;
            _loc_4 = this.m_xml.@parent;
            _loc_5 = this.m_xml.@target;
            _loc_6 = this.m_xml.@className;
            _loc_7 = this.m_xml.@typeName;
            _loc_8 = this.m_xml.@alpha;
            _loc_9 = this.m_xml.@showOutline;
            _loc_10 = this.m_xml.@spotLightFunc;
            _loc_11 = this.m_xml.@spotOffsetX;
            _loc_12 = this.m_xml.@spotOffsetY;
            _loc_13 = this.m_xml.@dialogCallback;
            if (_loc_3.length > 0)
            {
                switch(_loc_3)
                {
                    case "game":
                    {
                        this.m_level = GuideConstants.MASK_GAME;
                        break;
                    }
                    case "bottombar":
                    {
                        this.m_level = GuideConstants.MASK_GAME_AND_BOTTOMBAR;
                        break;
                    }
                    case "all":
                    {
                        this.m_level = GuideConstants.MASK_ALL_UI;
                        break;
                    }
                    case "specific":
                    {
                        this.m_level = GuideConstants.MASK_SPECIFIC;
                        break;
                    }
                    default:
                    {
                        return false;
                        break;
                    }
                }
            }
            this.m_parent = Global.ui;
            if (_loc_4.length > 0)
            {
                _loc_14 = m_guide.getObjectByName(_loc_4);
                if (_loc_14 == null)
                {
                    return false;
                }
                this.m_parent =(DisplayObjectContainer) _loc_14;
            }
            if (_loc_5.length > 0)
            {
                _loc_14 = m_guide.getObjectByName(_loc_5);
                if (_loc_14 == null)
                {
                    return false;
                }
                this.m_target =(DisplayObjectContainer) _loc_14;
            }
            if (_loc_6.length > 0)
            {
                _loc_15 = getDefinitionByName("Classes." + _loc_6);
                if (_loc_15 == null)
                {
                    return false;
                }
                this.m_targetClass =(Class) _loc_15;
            }
            if (_loc_13.length > 0)
            {
                this.m_dialogCallback = _loc_13;
            }
            if (_loc_7.length > 0)
            {
                this.m_targetObjectNames = _loc_7.split(",");
            }
            if (_loc_2.length > 0)
            {
                _loc_2 = _loc_2.toLowerCase();
                switch(_loc_2)
                {
                    case "location":
                    {
                        this.m_mode = MODE_LOCATION;
                        break;
                    }
                    case "tile":
                    {
                        this.m_mode = MODE_TILE;
                        break;
                    }
                    case "relative":
                    {
                        this.m_mode = MODE_RELATIVE;
                        break;
                    }
                    case "targetclass":
                    {
                        this.m_mode = MODE_TARGET_CLASS;
                        break;
                    }
                    case "targettype":
                    {
                        this.m_mode = MODE_TARGET_TYPE;
                        break;
                    }
                    case "activerollcalltype":
                    {
                        this.m_mode = MODE_ACTIVE_ROLL_CALL_TYPE;
                        break;
                    }
                    case "targetroot":
                    {
                        this.m_mode = MODE_TARGET_ROOT;
                        break;
                    }
                    case "currentdialogcallback":
                    {
                        this.m_mode = MODE_CURRENT_DIALOG_CALLBACK;
                        break;
                    }
                    default:
                    {
                        return false;
                        break;
                    }
                }
            }
            if (_loc_8.length > 0)
            {
                this.m_alpha = Number(_loc_8);
            }
            if (_loc_9.length > 0)
            {
                this.m_showOutline = int(_loc_9) == 1;
            }
            if (this.m_mode == MODE_RELATIVE && this.m_target == null)
            {
                ErrorManager.addError("Relative mode requires a \'target\' attribute!");
                return false;
            }
            if (this.m_mode == MODE_TARGET_CLASS && this.m_targetClass == null)
            {
                ErrorManager.addError("Target Class mode requires a \'className\' attribute!");
                return false;
            }
            if (this.m_mode == MODE_CURRENT_DIALOG_CALLBACK && !this.m_dialogCallback)
            {
                ErrorManager.addError("Target Dialog Callback mode requires a \'dialogCallback\' attribute!");
                return false;
            }
            this.m_spotlightFunc = m_guide ? (m_guide.displaySpotLight) : (null);
            if (_loc_10.length > 0)
            {
                this.m_spotlightFunc = m_guide ? (m_guide.get(_loc_10) as Function) : (this.m_spotlightFunc);
            }
            if (_loc_11.length > 0)
            {
                this.m_spotlightOffsetX = Number(_loc_11);
            }
            if (_loc_12.length > 0)
            {
                this.m_spotlightOffsetY = Number(_loc_12);
            }
            return true;
        }//end

        private Point  getSpotLightLocation ()
        {
            Point _loc_1 =null ;
            Vector3 _loc_2 =null ;
            MapResource _loc_3 =null ;
            Point _loc_4 =null ;
            Vector3 _loc_5 =null ;
            Point _loc_6 =null ;
            switch(this.m_mode)
            {
                case MODE_RELATIVE:
                {
                    return this.m_target.localToGlobal(this.m_focus);
                }
                case MODE_LOCATION:
                {
                    return this.m_focus;
                }
                case MODE_TILE:
                {
                    return IsoMath.viewportToStage(IsoMath.tilePosToPixelPos(this.m_focus.x, this.m_focus.y));
                }
                case MODE_TARGET_CLASS:
                {
                    _loc_1 = this.m_targetObject.getScreenPosition();
                    _loc_1 = IsoMath.viewportToStage(_loc_1);
                    _loc_2 = this.m_targetObject.getDimensions();
                    _loc_1.x = _loc_1.x + _loc_2.x / 2;
                    _loc_1.y = _loc_1.y + _loc_2.y / 2;
                    return new Point(_loc_1.x, _loc_1.y);
                }
                case MODE_TARGET_TYPE:
                case MODE_ACTIVE_ROLL_CALL_TYPE:
                case MODE_TARGET_ROOT:
                {
                    _loc_3 =(MapResource) this.m_targetObject;
                    _loc_4 = IsoMath.viewportToStage(this.m_targetObject.getScreenPosition());
                    _loc_5 = this.m_targetObject.getDimensions();
                    if (!_loc_5 && _loc_3)
                    {
                        _loc_5 = _loc_3.getItemImageDimensions();
                    }
                    if (_loc_5)
                    {
                        _loc_4.x = _loc_4.x + (_loc_5.x / 2 + this.m_spotlightOffsetX);
                        _loc_4.y = _loc_4.y + (_loc_5.y / 2 + this.m_spotlightOffsetY);
                    }
                    _loc_4.x = _loc_4.x - 10;
                    return new Point(_loc_4.x, _loc_4.y);
                }
                case MODE_CURRENT_DIALOG_CALLBACK:
                {
                    if (UI.currentPopup && this.m_dialogCallback)
                    {
                        if (UI.currentPopup.get(this.m_dialogCallback) instanceof Function)
                        {
                           Function loc_6 =UI.currentPopup.get(this.m_dialogCallback) ;
                            _loc_6 =(Point) loc_6();
                            if (_loc_6)
                            {
                                return _loc_6;
                            }
                        }
                    }
                    return this.m_focus;
                }
                default:
                {
                    break;
                }
            }
            return null;
        }//end

         public void  enter ()
        {
            switch(this.m_mode)
            {
                case MODE_RELATIVE:
                case MODE_LOCATION:
                case MODE_TILE:
                {
                    break;
                }
                case MODE_TARGET_CLASS:
                {
                    this.m_targetObject = GuideUtils.getGameObjectByClassName(this.m_targetClass);
                    if (this.m_targetObject !== null)
                    {
                        this.m_focus = this.getSpotLightLocation();
                    }
                    else
                    {
                        this.m_focus = null;
                        this.m_spotlightFunc = null;
                    }
                    break;
                }
                case MODE_TARGET_TYPE:
                case MODE_ACTIVE_ROLL_CALL_TYPE:
                case MODE_TARGET_ROOT:
                {
                    if (this.m_mode == MODE_TARGET_TYPE)
                    {
                        this.m_targetObject = GuideUtils.getGameObjectByTypeName(this.m_targetObjectNames.get(0));
                    }
                    else if (this.m_mode == MODE_TARGET_ROOT)
                    {
                        this.m_targetObject = GuideUtils.getGameObjectInWorldByRootName(this.m_targetObjectNames.get(0));
                    }
                    else
                    {
                        this.m_targetObject = GuideUtils.getActiveRollCallByTypeNames(this.m_targetObjectNames);
                    }
                    if (!this.m_targetObject)
                    {
                        this.m_focus = null;
                        this.m_spotlightFunc = null;
                        break;
                    }
                    if (this.m_targetObject instanceof MapResource)
                    {
                        (this.m_targetObject as MapResource).refreshArrow();
                    }
                    this.m_focus = this.getSpotLightLocation();
                    break;
                }
                case MODE_CURRENT_DIALOG_CALLBACK:
                {
                    this.m_focus = this.getSpotLightLocation();
                }
                default:
                {
                    break;
                }
            }
            if (this.m_spotlightFunc !== null)
            {
                this.m_spotlightFunc(this.m_parent, this.getSpotLightLocation(), this.m_size, this.m_style, this.m_level, this.m_alpha, this.m_showOutline, Utilities.bind(this.getSpotLightLocation, this, null));
            }
            super.enter();
            return;
        }//end

         public void  update (double param1 )
        {
            super.update(param1);
            super.removeState(this);
            return;
        }//end

    }



