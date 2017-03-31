package Modules.quest.Display;

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
import Engine.Events.*;
import Engine.Managers.*;
import Modules.quest.Managers.*;
//import flash.display.*;
//import flash.events.*;
//import flash.filters.*;
//import flash.text.*;

    public class QuestSignetOverlaySprite extends Sprite
    {
        protected String m_tier =null ;
        protected QuestTierConfig m_tierInfo =null ;
        protected DisplayObject m_icon =null ;
        protected Shape m_shape =null ;
        protected TextField m_textField =null ;
        protected TextFormat m_textFormat =null ;
        protected double m_textWidth =0;
        protected double m_textHeight =0;
        protected boolean m_loading =false ;
        protected boolean m_loaded =false ;
public static  double ICON_TEXT_GAP =-7;
public static  String DEFAULT_FONT_NAME =EmbeddedArt.defaultFontNameBold ;
public static  double DEFAULT_FONT_SIZE =11;
public static  double DEFAULT_FONT_COLOR =16777215;

        public  QuestSignetOverlaySprite (String param1 ,String param2 )
        {
            this.name = param1;
            this.m_tier = param2;
            return;
        }//end

        public void  load ()
        {
            String _loc_1 =null ;
            this.m_tierInfo = Global.gameSettings().getQuestTierConfig(this.tier);
            if (this.m_tierInfo && this.m_tierInfo.overlay && (!this.m_loading || !this.m_loaded))
            {
                this.m_loading = true;
                this.m_loaded = false;
                this.m_shape = new Shape();
                this.m_shape.graphics.beginFill(this.m_tierInfo.overlay.color);
                this.m_shape.graphics.drawRoundRect(this.x, this.y, this.m_tierInfo.overlay.width, this.m_tierInfo.overlay.height, this.m_tierInfo.overlay.round, this.m_tierInfo.overlay.round);
                this.m_shape.graphics.endFill();
                this.m_shape.alpha = this.m_tierInfo.overlay.alpha;
                this.m_shape.filters = .get(new BevelFilter(1, 45, 0, 0, 0, 1, 1, 1, 1, 1, BitmapFilterType.OUTER));
                this.addChild(this.m_shape);
                this.m_shape.x = this.m_shape.x + this.m_tierInfo.overlay.offsetX;
                this.m_shape.y = this.m_shape.y + this.m_tierInfo.overlay.offsetY;
                this.m_textFormat = new TextFormat(DEFAULT_FONT_NAME, DEFAULT_FONT_SIZE, DEFAULT_FONT_COLOR, true);
                this.m_textFormat.align = TextFormatAlign.CENTER;
                this.m_textFormat.bold = true;
                this.m_textField = new TextField();
                this.m_textField.autoSize = TextFieldAutoSize.CENTER;
                this.m_textField.defaultTextFormat = this.m_textFormat;
                this.m_textField.setTextFormat(this.m_textFormat);
                this.m_textField.selectable = false;
                this.m_textField.multiline = false;
                this.m_textField.wordWrap = false;
                this.m_textField.antiAliasType = AntiAliasType.ADVANCED;
                this.m_textField.gridFitType = GridFitType.SUBPIXEL;
                this.m_textField.embedFonts = EmbeddedArt.defaultBoldFontEmbed;
                this.addChild(this.m_textField);
                this.m_textWidth = this.m_shape.width;
                this.m_textHeight = this.m_shape.height;
                this.m_textField.x = this.m_shape.x;
                this.m_textField.y = this.m_shape.y;
                if (this.m_tierInfo.overlay.text)
                {
                    this.m_textField.x = this.m_textField.x + this.m_tierInfo.overlay.text.offsetX;
                    this.m_textField.y = this.m_textField.y + this.m_tierInfo.overlay.text.offsetY;
                }
                this.m_textField.width = this.m_textWidth;
                this.m_textField.height = this.m_textHeight;
                if (this.m_tierInfo.overlay.icon && this.m_tierInfo.overlay.icon.url)
                {
                    _loc_1 = Global.getAssetURL(this.m_tierInfo.overlay.icon.url);
                    LoadingManager.load(_loc_1, this.onIconLoaded);
                }
                else
                {
                    this.dispatchLoaded();
                }
            }
            else
            {
                this.dispatchLoaded();
            }
            return;
        }//end

        protected void  onIconLoaded (Event event )
        {
            event.stopImmediatePropagation();
            _loc_2 =(Loader) event.target.loader;
            this.m_icon =(DisplayObject) _loc_2;
            if (this.m_icon)
            {
                this.m_icon.filters = .get(new BevelFilter(1, 45, 0, 0, 0, 1, 1, 1, 1, 1, BitmapFilterType.OUTER));
                this.addChild(this.m_icon);
                this.m_icon.x = this.m_shape.x;
                this.m_icon.y = this.m_shape.y + this.m_shape.height - this.m_icon.height;
                if (this.m_tierInfo.overlay.icon)
                {
                    this.m_icon.x = this.m_icon.x + this.m_tierInfo.overlay.icon.offsetX;
                    this.m_icon.y = this.m_icon.y + this.m_tierInfo.overlay.icon.offsetY;
                }
                this.m_textWidth = this.m_textWidth - (this.m_icon.width + ICON_TEXT_GAP);
                this.m_textField.x = this.m_shape.x + (this.m_icon.width + ICON_TEXT_GAP);
                this.m_textField.width = this.m_textWidth;
                this.dispatchLoaded();
            }
            return;
        }//end

        protected void  dispatchLoaded ()
        {
            this.m_loading = false;
            this.m_loaded = true;
            this.dispatchEvent(new LoaderEvent(LoaderEvent.LOADED));
            return;
        }//end

        public String  tier ()
        {
            return this.m_tier;
        }//end

        public String  text ()
        {
            return this.m_textField.text;
        }//end

        public void  text (String param1 )
        {
            if (!param1)
            {
                param1 = "";
            }
            if (this.m_textField)
            {
                this.m_textField.text = param1;
                if (param1.length())
                {
                    this.m_textFormat.size = DEFAULT_FONT_SIZE;
                    TextFieldUtil.autosize(this.m_textField, this.m_textWidth, this.m_textHeight);
                    this.m_textField.y = this.m_shape.y + 0.5 * this.m_shape.height - 0.5 * this.m_textField.height;
                    if (this.m_tierInfo && this.m_tierInfo.overlay.text)
                    {
                        this.m_textField.y = this.m_textField.y + this.m_tierInfo.overlay.text.offsetY;
                    }
                    this.m_shape.alpha = 1;
                }
                else
                {
                    this.m_shape.alpha = 0;
                }
            }
            return;
        }//end

        public Shape  shape ()
        {
            return this.m_shape;
        }//end

        public void  detach ()
        {
            if (this.parent)
            {
                this.parent.removeChild(this);
            }
            return;
        }//end

    }



