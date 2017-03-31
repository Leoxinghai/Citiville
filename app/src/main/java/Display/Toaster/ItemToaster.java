package Display.Toaster;

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
import Engine.Managers.*;
import Modules.stats.types.*;
//import flash.display.*;
//import flash.events.*;
//import flash.text.*;

    public class ItemToaster extends Toaster
    {
        private Sprite m_bg ;
        private SimpleButton m_btnClose ;
        private Sprite m_iconContainer ;
        private TextField m_tfTitle ;
        private TextField m_tfBody ;
        private TextFormat m_titleFormat ;
        private TextFormat m_bodyFormat ;
        private DisplayObject m_icon ;

        public  ItemToaster (String param1 ,String param2 ,Object param3 =null ,String param4 =null ,int param5 =5000)
        {
            Class _loc_7 =null ;
            super(param4);
            m_duration = param5;
            this.m_bg = new EmbeddedArt.hud_nghbr_toasters_bg();
            this.m_bg.width = 415;
            addChild(this.m_bg);
            this.m_iconContainer = new Sprite();
            this.m_iconContainer.addChild(new EmbeddedArt.hud_nghbr_toasters_frame());
            this.m_iconContainer.x = 7;
            this.m_iconContainer.y = 7;
            addChild(this.m_iconContainer);
            this.m_btnClose = new SimpleButton(new EmbeddedArt.hud_nghbr_toasters_close_up(), new EmbeddedArt.hud_nghbr_toasters_close_over(), new EmbeddedArt.hud_nghbr_toasters_close_up(), new EmbeddedArt.hud_nghbr_toasters_close_up());
            this.m_btnClose.x = this.m_bg.width - this.m_btnClose.width;
            this.m_btnClose.addEventListener(MouseEvent.CLICK, this.btnCloseClickHandler, false, 0, true);
            addChild(this.m_btnClose);
            _loc_6 = TextFieldUtil.getLocaleFontSizeByRatio(20,0.8,.get({localeratio"ja",1)});
            this.m_titleFormat = new TextFormat(EmbeddedArt.titleFont, _loc_6, EmbeddedArt.titleColor);
            this.m_bodyFormat = new TextFormat(EmbeddedArt.defaultFontNameBold, 14, EmbeddedArt.brownTextColor);
            this.m_tfTitle = new TextField();
            this.m_tfTitle.antiAliasType = AntiAliasType.ADVANCED;
            this.m_tfTitle.gridFitType = GridFitType.PIXEL;
            this.m_tfTitle.embedFonts = EmbeddedArt.titleFontEmbed;
            this.m_tfTitle.autoSize = TextFieldAutoSize.LEFT;
            this.m_tfTitle.width = 280;
            this.m_tfTitle.multiline = true;
            this.m_tfTitle.wordWrap = true;
            this.m_tfTitle.defaultTextFormat = this.m_titleFormat;
            this.m_tfTitle.text = param1;
            TextFieldUtil.formatSmallCaps(this.m_tfTitle, new TextFormat(EmbeddedArt.titleFont, _loc_6));
            this.m_tfTitle.filters = EmbeddedArt.titleFilters;
            addChild(this.m_tfTitle);
            this.m_tfBody = new TextField();
            this.m_tfBody.antiAliasType = AntiAliasType.ADVANCED;
            this.m_tfBody.gridFitType = GridFitType.PIXEL;
            this.m_tfBody.embedFonts = EmbeddedArt.defaultBoldFontEmbed;
            this.m_tfBody.width = 280;
            this.m_tfBody.multiline = true;
            this.m_tfBody.wordWrap = true;
            this.m_tfBody.defaultTextFormat = this.m_bodyFormat;
            this.m_tfBody.text = param2;
            addChild(this.m_tfBody);
            if (param3)
            {
                if (param3 instanceof String)
                {
                    LoadingManager.load((String)param3, this.iconLoadHandler);
                }
                else if (param3 instanceof Class)
                {
                    _loc_7 =(Class) param3;
                    this.addIcon(new (DisplayObject)_loc_7);
                }
                else if (param3 instanceof DisplayObject)
                {
                    this.addIcon((DisplayObject)param3);
                }
            }
            this.draw();
            return;
        }//end

         public double  displayHeight ()
        {
            return this.m_bg.height - 30;
        }//end

        protected void  draw ()
        {
            int _loc_1 =125;
            this.m_tfBody.x = 125;
            this.m_tfTitle.x = _loc_1;
            this.m_tfTitle.y = 10;
            this.m_tfBody.y = this.m_tfTitle.y + this.m_tfTitle.height;
            if (this.m_icon)
            {
                this.m_icon.x = (this.m_iconContainer.width - this.m_icon.width) * 0.5;
                this.m_icon.y = (this.m_iconContainer.height - this.m_icon.height) * 0.5;
            }
            this.m_bg.height = this.m_tfBody.y + this.m_tfBody.height + 25;
            return;
        }//end

        private void  iconLoadHandler (Event event )
        {
            this.addIcon(event.currentTarget.get("content"));
            this.draw();
            return;
        }//end

        private void  addIcon (DisplayObject param1 )
        {
            this.m_icon = param1;
            if (this.m_icon)
            {
                this.m_iconContainer.addChild(this.m_icon);
            }
            return;
        }//end

        private void  btnCloseClickHandler (MouseEvent event )
        {
            this.close();
            return;
        }//end

         protected void  close ()
        {
            super.close();
            return;
        }//end

         public void  countView ()
        {
            StatsManager.sample(100, StatsCounterType.DIALOG, StatsKingdomType.DIALOG_TOASTER, m_statName, "view");
            return;
        }//end

         public void  countAutoClose ()
        {
            StatsManager.sample(100, StatsCounterType.DIALOG, StatsKingdomType.DIALOG_TOASTER, m_statName, "auto_close");
            return;
        }//end

         public void  countForcedClose ()
        {
            StatsManager.sample(100, StatsCounterType.DIALOG, StatsKingdomType.DIALOG_TOASTER, m_statName, "manual_close");
            return;
        }//end

    }



