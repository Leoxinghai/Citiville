package Display;

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
//import flash.display.*;
//import flash.text.*;

    public class ContextMenuItem extends Sprite
    {
        protected String m_state ;
        protected String m_label ;
        protected String m_action ;
        protected Shape m_bgShape ;
        protected TextField m_textField ;
        protected Object m_style ;
        public static  String STATE_UP ="up";
        public static  String STATE_OVER ="over";

        public  ContextMenuItem (String param1 ,String param2 ,Object param3 )
        {
            this.m_label = param1;
            this.m_action = param2;
            this.m_style = param3;
            this.draw();
            this.mouseChildren = false;
            this.state = STATE_UP;
            return;
        }//end  

        public String  label ()
        {
            return this.m_label;
        }//end  

        public String  action ()
        {
            return this.m_action;
        }//end  

        public void  state (String param1 )
        {
            this.m_state = param1;
            switch(this.m_state)
            {
                case STATE_UP:
                {
                    this.drawUpState();
                    break;
                }
                case STATE_OVER:
                {
                    this.drawOverState();
                    break;
                }
                default:
                {
                    break;
                }
            }
            return;
        }//end  

        public String  state ()
        {
            return this.m_state;
        }//end  

        public void  draw ()
        {
            this.drawBg(this.m_style.contextMenuItem.backgroundColor);
            this.drawText(this.m_style.contextMenuItem.color);
            return;
        }//end  

        protected void  drawBg (int param1 )
        {
            if (!this.m_bgShape)
            {
                this.m_bgShape = new Shape();
                this.addChild(this.m_bgShape);
            }
            this.m_bgShape.graphics.clear();
            this.m_bgShape.graphics.beginFill(param1);
            this.m_bgShape.graphics.drawRect(0, 0, Math.max(this.m_style.contextMenuItem.width, this.width, this.parent ? (this.parent.width - this.m_style.contextMenu.borderWidth * 2) : (0)), this.m_style.contextMenuItem.height);
            return;
        }//end  

        protected void  drawText (int param1 )
        {
            String _loc_2 =null ;
            TextFormat _loc_3 =null ;
            int _loc_4 =0;
            if (this.m_label)
            {
                _loc_2 = this.m_style.contextMenuItem.font;
                _loc_3 = new TextFormat();
                _loc_3.align = this.m_style.contextMenuItem.textAlign;
                _loc_3.color = param1;
                _loc_3.font = _loc_2;
                _loc_3.size = this.m_style.contextMenuItem.fontSize;
                if (!this.m_textField)
                {
                    _loc_4 = this.m_style.contextMenuItem.padding;
                    this.m_textField = new TextField();
                    this.m_textField.embedFonts = EmbeddedArt.isEmbedFont(_loc_3.font);
                    this.m_textField.defaultTextFormat = _loc_3;
                    this.m_textField.multiline = false;
                    this.m_textField.antiAliasType = AntiAliasType.ADVANCED;
                    this.m_textField.htmlText = this.m_label;
                    this.m_textField.autoSize = this.m_style.contextMenuItem.textAlign;
                    this.m_textField.selectable = false;
                    this.m_textField.mouseEnabled = false;
                    this.m_textField.width = this.m_textField.textWidth;
                    this.m_textField.height = this.m_textField.textHeight;
                    this.m_textField.x = _loc_4;
                    this.m_textField.y = this.m_style.contextMenuItem.height - this.m_textField.height >> 1;
                    this.addChild(this.m_textField);
                }
                else
                {
                    this.m_textField.defaultTextFormat = _loc_3;
                    this.m_textField.htmlText = this.m_label;
                }
            }
            return;
        }//end  

        protected void  drawOverState ()
        {
            this.drawBg(this.m_style.contextMenuItem.hoverBackgroundColor);
            this.drawText(this.m_style.contextMenuItem.hoverColor);
            return;
        }//end  

        protected void  drawUpState ()
        {
            this.drawBg(this.m_style.contextMenuItem.backgroundColor);
            this.drawText(this.m_style.contextMenuItem.color);
            return;
        }//end  

    }


