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

import Engine.*;
import Modules.guide.ui.*;
//import flash.display.*;
//import flash.geom.*;

    public class GADisplayArrow extends GADisplayTarget
    {
        private  String TARGET_ARROW ="arrow";
        protected String m_arrowURL ="";
        protected int m_direction =4;
        protected boolean m_useRotate =true ;
        protected double m_arrowWidth =0;
        protected double m_arrowHeight =0;
        protected String m_relativeTo ="";
        protected double m_offsetX =0;
        protected double m_offsetY =0;
        protected GuideArrow m_arrow =null ;
        protected boolean m_aboveHud =false ;
        protected DisplayObjectContainer m_overrideArrowParent =null ;
        protected boolean m_overrideArrowBoolean =false ;

        public  GADisplayArrow ()
        {
            m_targetType = this.TARGET_ARROW;
            m_showOutline = false;
            return;
        }//end  

         public boolean  createFromXml (XML param1 )
        {
            DisplayObject _loc_13 =null ;
            _loc_2 = super.createFromXml(param1);
            _loc_3 = m_xml.@name;
            _loc_4 = m_xml.@arrowWidth;
            _loc_5 = m_xml.@arrowWidth;
            _loc_6 = m_xml.@direction;
            _loc_7 = m_xml.@rotate;
            _loc_8 = m_xml.@relative;
            _loc_9 = m_xml.@offsetX;
            _loc_10 = m_xml.@offsetY;
            _loc_11 = m_xml.@aboveHud;
            _loc_12 = m_xml.@overrideArrowParent;
            if (_loc_3.length == 0)
            {
                return false;
            }
            this.m_arrowURL = Global.getAssetURL(_loc_3);
            if (_loc_4.length > 0)
            {
                this.m_arrowWidth = Number(_loc_4);
            }
            if (_loc_5.length > 0)
            {
                this.m_arrowHeight = Number(_loc_5);
            }
            if (_loc_9.length > 0)
            {
                this.m_offsetX = Number(_loc_9);
            }
            if (_loc_10.length > 0)
            {
                this.m_offsetY = Number(_loc_10);
            }
            if (_loc_6.length > 0)
            {
                _loc_6 = _loc_6.toLowerCase();
                switch(_loc_6)
                {
                    case "up":
                    {
                        this.m_direction = GuideArrow.ARROW_UP;
                        break;
                    }
                    case "down":
                    {
                        this.m_direction = GuideArrow.ARROW_DOWN;
                        break;
                    }
                    case "right":
                    {
                        this.m_direction = GuideArrow.ARROW_RIGHT;
                        break;
                    }
                    case "left":
                    default:
                    {
                        this.m_direction = GuideArrow.ARROW_LEFT;
                        break;
                        break;
                    }
                }
            }
            this.m_aboveHud = _loc_11.length > 0 && _loc_11.toLowerCase() == "true";
            _loc_12 = _loc_12.length > 0 ? (_loc_12) : (null);
            if (_loc_12)
            {
                _loc_13 = m_guide.getObjectByName(_loc_12);
                if (_loc_13 == null)
                {
                    return false;
                }
                this.m_overrideArrowParent =(DisplayObjectContainer) _loc_13;
            }
            if (_loc_7.length > 0 && _loc_7.toLowerCase() == "false")
            {
                this.m_useRotate = false;
            }
            else
            {
                this.m_useRotate = true;
            }
            if (m_xml.@overrideArrowParent == "@stage")
            {
                this.m_overrideArrowBoolean = true;
            }
            if (_loc_8.length > 0)
            {
            }
            return _loc_2;
        }//end  

         public void  enter ()
        {
            Point _loc_2 =null ;
            Point _loc_3 =null ;
            Point _loc_4 =null ;
            super.enter();
            if (!m_focus)
            {
                return;
            }
            _loc_1 = m_xml.@target == "@bottomhud" && this.m_overrideArrowBoolean && m_mode == GADisplayTarget.MODE_RELATIVE;
            _loc_2 = new Point(m_focus.x, m_focus.y);
            switch(m_mode)
            {
                case GADisplayTarget.MODE_RELATIVE:
                {
                    _loc_3 = m_target.localToGlobal(new Point(_loc_2.x, _loc_2.y));
                    if (!_loc_1)
                    {
                        _loc_3 = Global.ui.globalToLocal(_loc_3);
                    }
                    _loc_2.x = _loc_3.x + this.m_offsetX;
                    _loc_2.y = _loc_3.y + this.m_offsetY;
                    break;
                }
                case GADisplayTarget.MODE_TILE:
                {
                    _loc_4 = IsoMath.tilePosToPixelPos(_loc_2.x, _loc_2.y);
                    _loc_4 = IsoMath.viewportToStage(_loc_4);
                    _loc_4 = Global.ui.globalToLocal(_loc_4);
                    _loc_2.x = _loc_4.x + this.m_offsetX;
                    _loc_2.y = _loc_4.y + this.m_offsetY;
                    break;
                }
                case GADisplayTarget.MODE_LOCATION:
                case GADisplayTarget.MODE_TARGET_TYPE:
                case GADisplayTarget.MODE_TARGET_CLASS:
                case GADisplayTarget.MODE_ACTIVE_ROLL_CALL_TYPE:
                case GADisplayTarget.MODE_TARGET_ROOT:
                case GADisplayTarget.MODE_CURRENT_DIALOG_CALLBACK:
                {
                    _loc_2.x = _loc_2.x + this.m_offsetX;
                    _loc_2.y = _loc_2.y + this.m_offsetY;
                    break;
                }
                default:
                {
                    break;
                }
            }
            this.m_arrow = new GuideArrow(this.m_arrowURL, _loc_2.x, _loc_2.y, this.m_arrowWidth, this.m_arrowHeight, this.m_direction, this.m_useRotate, this.m_aboveHud, false, this.m_overrideArrowParent);
            m_guide.trackArrow(this.m_arrow);
            return;
        }//end  

         protected boolean  allAssetsLoaded ()
        {
            if (!this.m_arrow)
            {
                return true;
            }
            return this.m_arrow.hasLoaded();
        }//end  

    }



