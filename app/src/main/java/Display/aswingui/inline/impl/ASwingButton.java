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

import Display.aswingui.*;
import Display.aswingui.inline.*;
import Display.aswingui.inline.style.*;
import org.aswing.*;

    public class ASwingButton extends ASwingObject implements IASwingButton
    {
        private JButton m_component ;
        private boolean m_enabled =true ;
        private String m_textKey ;

        public  ASwingButton (String param1 )
        {
            super(param1);
            return;
        }//end  

         public void  destroy ()
        {
            this.m_component = null;
            super.destroy();
            return;
        }//end  

        public IASwingButton  position (int param1 ,int param2 )
        {
            this.m_x = param1;
            this.m_y = param2;
            return this;
        }//end  

        public IASwingButton  size (int param1 ,int param2 )
        {
            this.m_width = param1;
            this.m_height = param2;
            return this;
        }//end  

        public IASwingButton  strings (String param1 )
        {
            m_stringPackage = param1;
            return this;
        }//end  

        public IASwingButton  replacements (Object param1 )
        {
            m_replacements = param1;
            return this;
        }//end  

        public IASwingButton  style (IASwingStyle param1 )
        {
            m_style = param1;
            return this;
        }//end  

        public IASwingButton  textKey (String param1 )
        {
            this.m_textKey = param1;
            return this;
        }//end  

        public IASwingButton  enable (boolean param1 )
        {
            this.m_enabled = param1;
            return this;
        }//end  

        public Component  component ()
        {
            ASwingStyleProperties _loc_1 =null ;
            boolean _loc_2 =false ;
            String _loc_3 =null ;
            JButton _loc_4 =null ;
            CustomButton _loc_5 =null ;
            Icon _loc_6 =null ;
            String _loc_7 =null ;
            Insets _loc_8 =null ;
            Insets _loc_9 =null ;
            if (!this.m_component)
            {
                _loc_1 = m_style.properties;
                _loc_2 = _loc_1.skinClass == null;
                _loc_3 = this.m_textKey ? (localize(this.m_textKey)) : (localize(id));
                if (_loc_2)
                {
                    JButton _loc_10 =new JButton(_loc_3 );
                    _loc_4 = new JButton(_loc_3);
                    this.m_component = _loc_10;
                    this.initialize(_loc_4);
                }
                else
                {
                    _loc_6 = _loc_1 ? (_loc_1.icon) : (null);
                    _loc_7 = _loc_1 ? (_loc_1.skinClass) : (null);
                    CustomButton _loc10 =new CustomButton(_loc_3 ,_loc_6 ,_loc_7 );
                    _loc_5 = new CustomButton(_loc_3, _loc_6, _loc_7);
                    this.m_component = _loc10;
                    this.initialize(_loc_5);
                }
                if (m_width < 0 && m_height < 0)
                {
                    _loc_8 = this.m_component.getMargin();
                    _loc_9 = _loc_8.clone();
                    _loc_8.clone().top = _loc_9.top + _loc_1.paddingTop;
                    _loc_9.left = _loc_9.left + _loc_1.paddingLeft;
                    _loc_9.right = _loc_9.right + _loc_1.paddingRight;
                    _loc_9.bottom = _loc_9.bottom + _loc_1.paddingBottom;
                    this.m_component.setMargin(_loc_8);
                }
                this.m_component.setEnabled(this.m_enabled);
            }
            return this.m_component;
        }//end  

    }


