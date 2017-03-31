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

    public class ASwingText extends ASwingObject implements IASwingText
    {
        private Component m_component ;
        private boolean m_selectable ;
        private String m_textKey ;

        public  ASwingText (String param1 )
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

        public IASwingText  strings (String param1 )
        {
            m_stringPackage = param1;
            return this;
        }//end  

        public IASwingText  replacements (Object param1 )
        {
            m_replacements = param1;
            return this;
        }//end  

        public IASwingText  position (int param1 ,int param2 )
        {
            this.m_x = param1;
            this.m_y = param2;
            return this;
        }//end  

        public IASwingText  size (int param1 ,int param2 )
        {
            this.m_width = param1;
            this.m_height = param2;
            return this;
        }//end  

        public IASwingText  style (IASwingStyle param1 )
        {
            m_style = param1;
            return this;
        }//end  

        public IASwingText  textKey (String param1 )
        {
            this.m_textKey = param1;
            return this;
        }//end  

        public IASwingText  selectable (boolean param1 =true )
        {
            this.m_selectable = param1;
            return this;
        }//end  

        public Component  component ()
        {
            JText _loc_1 =null ;
            String _loc_2 =null ;
            if (!this.m_component)
            {
                _loc_2 = this.m_textKey ? (localize(this.m_textKey)) : (localize(id));
                JText _loc_3 =new JText(_loc_2 );
                _loc_1 = new JText(_loc_2);
                this.m_component = _loc_3;
                this.initialize(_loc_1);
                _loc_1.setSelectable(this.m_selectable);
            }
            return this.m_component;
        }//end  

    }


