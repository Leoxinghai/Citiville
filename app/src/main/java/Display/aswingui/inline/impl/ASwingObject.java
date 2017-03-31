package Display.aswingui.inline.impl;

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

import Display.aswingui.inline.style.*;
import Display.aswingui.inline.util.*;
import ZLocalization.*;
import org.aswing.*;
import org.aswing.geom.*;

    public class ASwingObject
    {
        private String m_id ;
        public IASwingStyle m_style ;
        public String m_stringPackage ="Dialogs";
        public boolean m_stringPackageSet ;
        public Object m_replacements ;
        public int m_x =-1;
        public int m_y =-1;
        public int m_width =-1;
        public int m_height =-1;
        public int m_minWidth =-1;
        public int m_minHeight =-1;
        public int m_maxWidth =-1;
        public int m_maxHeight =-1;
        public double m_top ;
        public double m_bottom ;
        public double m_left ;
        public double m_right ;
        private static double _ID =1000;

        public  ASwingObject (String param1 )
        {
            this.m_id = param1;
            if (!param1)
            {
                this.m_id = "instance" + (_ID++).toString();
            }
            return;
        }//end

        public void  destroy ()
        {
            this.m_style = null;
            return;
        }//end

        public String  id ()
        {
            return this.m_id;
        }//end

        public int  x ()
        {
            return this.m_x;
        }//end

        public void  x (int param1 )
        {
            this.m_x = param1;
            return;
        }//end

        public int  y ()
        {
            return this.m_y;
        }//end

        public void  y (int param1 )
        {
            this.m_y = param1;
            return;
        }//end

        public int  width ()
        {
            return this.m_width;
        }//end

        public void  width (int param1 )
        {
            this.m_width = param1;
            return;
        }//end

        public int  height ()
        {
            return this.m_height;
        }//end

        public void  height (int param1 )
        {
            this.m_height = param1;
            return;
        }//end

        public IASwingStyle  styles ()
        {
            return this.m_style;
        }//end

        public void  styles (IASwingStyle param1 )
        {
            this.m_style = param1;
            return;
        }//end

        public double  top ()
        {
            return this.m_top;
        }//end

        public void  top (double param1 )
        {
            this.m_top = param1;
            return;
        }//end

        public double  bottom ()
        {
            return this.m_bottom;
        }//end

        public void  bottom (double param1 )
        {
            this.m_bottom = param1;
            return;
        }//end

        public double  left ()
        {
            return this.m_left;
        }//end

        public void  left (double param1 )
        {
            this.m_left = param1;
            return;
        }//end

        public double  right ()
        {
            return this.m_right;
        }//end

        public void  right (double param1 )
        {
            this.m_right = param1;
            return;
        }//end

        public int  minWidth ()
        {
            return this.m_minWidth;
        }//end

        public void  minWidth (int param1 )
        {
            this.m_minWidth = param1;
            return;
        }//end

        public int  minHeight ()
        {
            return this.m_minHeight;
        }//end

        public void  minHeight (int param1 )
        {
            this.m_minHeight = param1;
            return;
        }//end

        public int  maxWidth ()
        {
            return this.m_maxWidth;
        }//end

        public void  maxWidth (int param1 )
        {
            this.m_maxWidth = param1;
            return;
        }//end

        public int  maxHeight ()
        {
            return this.m_maxHeight;
        }//end

        public void  maxHeight (int param1 )
        {
            this.m_maxHeight = param1;
            return;
        }//end

        public String  stringPackage ()
        {
            return this.m_stringPackage;
        }//end

        public void  stringPackage (String param1 )
        {
            this.m_stringPackage = param1;
            this.m_stringPackageSet = true;
            return;
        }//end

        public boolean  stringPackageSet ()
        {
            return this.m_stringPackageSet;
        }//end

        public void  initialize (Component param1 )
        {
            int _loc_2 =0;
            int _loc_3 =0;
            int _loc_4 =0;
            int _loc_5 =0;
            int _loc_7 =0;
            int _loc_8 =0;
            IntDimension _loc_9 =null ;
            param1.setName(this.id);
            _loc_6 = this.m_style.properties ;
            if (this.m_style.properties)
            {
                _loc_2 = _loc_6.paddingLeft;
                _loc_3 = _loc_6.paddingRight;
                _loc_4 = _loc_6.paddingTop;
                _loc_5 = _loc_6.paddingBottom;
            }
            if (this.m_x >= 0 || this.m_y >= 0)
            {
                param1.setLocation(ASwingUnits.point(this.m_x + _loc_2, this.m_y + _loc_3));
            }
            if (this.m_width >= 0 || this.m_height >= 0)
            {
                _loc_7 = this.m_width + _loc_2 + _loc_3;
                _loc_8 = this.m_height + _loc_4 + _loc_5;
                _loc_9 = ASwingUnits.dimensions(_loc_7, _loc_8);
                param1.setPreferredSize(_loc_9);
            }
            if (this.m_style && _loc_6.backgroundWidth > 0 && _loc_6.backgroundHeight > 0)
            {
                _loc_7 = _loc_6.backgroundWidth + _loc_2 + _loc_3;
                _loc_8 = _loc_6.backgroundHeight + _loc_4 + _loc_5;
                _loc_9 = ASwingUnits.dimensions(_loc_7, _loc_8);
                param1.setPreferredSize(_loc_9);
            }
            if (this.m_minWidth >= 0 || this.m_minHeight >= 0 || _loc_9)
            {
                if (_loc_9)
                {
                    param1.setMinimumSize(_loc_9);
                }
                else
                {
                    param1.setMinimumSize(ASwingUnits.dimensions(this.m_minWidth, this.m_minHeight));
                }
            }
            if (this.m_maxWidth >= 0 || this.m_maxHeight >= 0 || _loc_9)
            {
                if (_loc_9)
                {
                    param1.setMaximumSize(_loc_9);
                }
                else
                {
                    param1.setMaximumSize(ASwingUnits.dimensions(this.m_maxWidth, this.m_maxHeight));
                }
            }
            this.m_style.apply(param1);
            return;
        }//end

        public String  localize (String param1 )
        {
            textKey = param1;
            localizer = ZLoc.instance;
            text = textKey;
            try
            {
                text = localizer.translate(this.m_stringPackage, textKey, this.m_replacements);
            }
            catch (e:Error)
            {
            }
            return text;
        }//end

    }


