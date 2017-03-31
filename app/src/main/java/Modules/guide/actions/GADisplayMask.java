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

import Modules.guide.*;
//import flash.display.*;

    public class GADisplayMask extends GuideAction
    {
        private int m_level =0;
        private DisplayObjectContainer m_parent =null ;
        private double m_alpha =0;

        public  GADisplayMask ()
        {
            return;
        }//end

         public boolean  createFromXml (XML param1 )
        {
            DisplayObject _loc_6 =null ;
            _loc_2 = checkAndGetElement(param1,"mask");
            if (!_loc_2)
            {
                return false;
            }
            _loc_3 = _loc_2.@level;
            _loc_4 = _loc_2.@parent;
            _loc_5 = _loc_2.@alpha;
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
                        break;
                    }
                }
            }
            if (_loc_4.length > 0)
            {
                if (_loc_4.toLowerCase() == "ui")
                {
                    this.m_parent = null;
                }
                else
                {
                    _loc_6 = m_guide.getObjectByName(_loc_4);
                    if (_loc_6 == null)
                    {
                        return false;
                    }
                    this.m_parent =(DisplayObjectContainer) _loc_6;
                }
            }
            if (_loc_5.length > 0)
            {
                this.m_alpha = parseFloat(_loc_5);
            }
            return true;
        }//end

         public void  enter ()
        {
            if (this.m_parent != null)
            {
                m_guide.displayMaskWithParent(this.m_parent);
            }
            else
            {
                m_guide.displayMaskUI(this.m_level, this.m_alpha);
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



