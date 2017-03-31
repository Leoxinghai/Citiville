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
//import flash.text.*;
import org.aswing.*;

    public class ASwingRichText extends ASwingObject implements IASwingRichText
    {
        private JRichText m_component ;
        private String m_textKey ;
        private boolean m_selectable ;
        private boolean m_editable ;
        private int m_maxChars ;
        private int m_rows ;
        private int m_columns ;
        private boolean m_wordWrap ;
        private boolean m_multiline ;
        private boolean m_password ;

        public  ASwingRichText (String param1 )
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

        public IASwingRichText  style (IASwingStyle param1 )
        {
            m_style = param1;
            return this;
        }//end  

        public IASwingRichText  textKey (String param1 )
        {
            this.m_textKey = param1;
            return this;
        }//end  

        public IASwingRichText  strings (String param1 )
        {
            m_stringPackage = param1;
            return this;
        }//end  

        public IASwingRichText  replacements (Object param1 )
        {
            m_replacements = param1;
            return this;
        }//end  

        public IASwingRichText  selectable (boolean param1 =true )
        {
            this.m_selectable = param1;
            return this;
        }//end  

        public IASwingRichText  editable (boolean param1 =true )
        {
            this.m_editable = param1;
            return this;
        }//end  

        public IASwingRichText  maxChars (int param1 )
        {
            this.m_maxChars = param1;
            return this;
        }//end  

        public IASwingRichText  columns (int param1 )
        {
            this.m_columns = param1;
            return this;
        }//end  

        public IASwingRichText  rows (int param1 )
        {
            this.m_rows = param1;
            return this.multiline(this.m_rows > 1);
        }//end  

        public IASwingRichText  multiline (boolean param1 =true )
        {
            this.m_multiline = param1;
            return this;
        }//end  

        public IASwingRichText  wordWrap (boolean param1 =true )
        {
            this.m_wordWrap = param1;
            return this;
        }//end  

        public IASwingRichText  password (boolean param1 =true )
        {
            this.m_password = param1;
            return this;
        }//end  

        public IASwingRichText  size (int param1 ,int param2 )
        {
            m_width = param1;
            m_height = param2;
            return this;
        }//end  

        public IASwingRichText  position (int param1 ,int param2 )
        {
            m_x = param1;
            m_y = param2;
            return this;
        }//end  

        public TextField  textField ()
        {
            return (this.component as ITextFieldLabel).getTextField();
        }//end  

        public Component  component ()
        {
            String _loc_1 =null ;
            JRichText _loc_2 =null ;
            TextField _loc_3 =null ;
            TextFormat _loc_4 =null ;
            if (!this.m_component)
            {
                _loc_1 = this.m_textKey ? (localize(this.m_textKey)) : (localize(id));
                JRichText _loc_5 =new JRichText(_loc_1 ,this.m_columns );
                _loc_2 = new JRichText(_loc_1, this.m_columns);
                this.m_component = _loc_5;
                this.initialize(_loc_2);
                _loc_2.setEditable(this.m_editable);
                _loc_2.setMaxChars(this.m_maxChars);
                _loc_2.setDisplayAsPassword(this.m_password);
                _loc_2.setWordWrap(this.m_wordWrap);
                _loc_2.setEnabled(this.m_selectable);
                _loc_3 = _loc_2.getTextField();
                _loc_3.multiline = this.m_multiline;
                if (m_height <= 0)
                {
                    _loc_3.autoSize = TextFieldAutoSize.CENTER;
                }
                else
                {
                    _loc_3.height = m_height;
                }
                if (m_width > 0)
                {
                    _loc_3.width = m_width;
                }
                _loc_4 = _loc_2.getTextFormat();
                _loc_4.kerning = true;
                _loc_4.leading = this.m_multiline ? ((-_loc_2.getFont().getSize()) / 4) : (_loc_4.leading);
                _loc_2.setTextFormat(_loc_4);
            }
            return this.m_component;
        }//end  

    }


