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
import Display.aswingui.*;
import Engine.Managers.*;
import Modules.remodel.*;
import Modules.stats.types.*;
//import flash.display.*;
//import flash.events.*;
//import flash.text.*;

    public class ReminderToaster extends Toaster
    {
        private Sprite m_bg ;
        private SimpleButton m_btnClose ;
        private CustomButton m_actionBtn ;
        private Sprite m_iconContainer ;
        private TextField m_tfTitle ;
        private TextField m_tfBody ;
        private TextFormat m_titleFormat ;
        private TextFormat m_bodyFormat ;
        private DisplayObject m_icon ;
        private MapResource m_mapResource ;
        public boolean canShow =false ;
        private Function m_actionButtonCallback ;
        protected  int COOLDOWN =604800;
        protected  String SEEN_FLAG_NAME ="reminderToast_cooldown";

        public  ReminderToaster (String param1 ,String param2 ,MapResource param3 ,String param4 =null ,String param5 =null ,String param6 =null ,Function param7 =null )
        {
            int _loc_8 =0;
            super(param5, this.mouseEventHandler);
            if (!this.isToasterAvailable())
            {
                return;
            }
            this.canShow = true;
            this.m_mapResource = param3;
            this.m_actionButtonCallback = param7;
            m_duration = -1;
            _loc_8 = -20;
            int _loc_9 =510;
            this.m_bg = new EmbeddedArt.hud_nghbr_toasters_bg();
            this.m_bg.width = _loc_9;
            this.m_bg.x = _loc_8;
            addChild(this.m_bg);
            this.m_iconContainer = new Sprite();
            this.m_iconContainer.addChild(new EmbeddedArt.hud_nghbr_toasters_frame());
            this.m_iconContainer.x = _loc_8 + 7;
            this.m_iconContainer.y = 7;
            addChild(this.m_iconContainer);
            this.m_btnClose = new SimpleButton(new EmbeddedArt.hud_nghbr_toasters_close_up(), new EmbeddedArt.hud_nghbr_toasters_close_over(), new EmbeddedArt.hud_nghbr_toasters_close_up(), new EmbeddedArt.hud_nghbr_toasters_close_up());
            this.m_btnClose.x = this.m_bg.width - this.m_btnClose.width + _loc_8;
            this.m_btnClose.name = "btnClose";
            this.m_btnClose.addEventListener(MouseEvent.CLICK, this.btnCloseClickHandler, false, 0, true);
            addChild(this.m_btnClose);
            if (param4)
            {
                LoadingManager.load(param4, this.iconLoadHandler);
            }
            _loc_10 = TextFieldUtil.getLocaleFontSizeByRatio(20,0.8,.get({localeratio"ja",1)});
            this.m_titleFormat = new TextFormat(EmbeddedArt.titleFont, _loc_10, EmbeddedArt.titleColor);
            this.m_bodyFormat = new TextFormat(EmbeddedArt.defaultFontNameBold, 14, EmbeddedArt.brownTextColor);
            this.m_tfTitle = new TextField();
            this.m_tfTitle.antiAliasType = AntiAliasType.ADVANCED;
            this.m_tfTitle.gridFitType = GridFitType.PIXEL;
            this.m_tfTitle.embedFonts = EmbeddedArt.titleFontEmbed;
            this.m_tfTitle.autoSize = TextFieldAutoSize.LEFT;
            this.m_tfTitle.width = _loc_9 - this.m_iconContainer.width - this.m_btnClose.width - 40;
            this.m_tfTitle.multiline = true;
            this.m_tfTitle.wordWrap = true;
            this.m_tfTitle.defaultTextFormat = this.m_titleFormat;
            this.m_tfTitle.text = param1;
            TextFieldUtil.formatSmallCaps(this.m_tfTitle, new TextFormat(EmbeddedArt.titleFont, _loc_10));
            this.m_tfTitle.filters = EmbeddedArt.titleFilters;
            addChild(this.m_tfTitle);
            this.m_tfBody = new TextField();
            this.m_tfBody.antiAliasType = AntiAliasType.ADVANCED;
            this.m_tfBody.gridFitType = GridFitType.PIXEL;
            this.m_tfBody.embedFonts = EmbeddedArt.defaultBoldFontEmbed;
            this.m_tfBody.width = _loc_9 - this.m_iconContainer.width - 40;
            this.m_tfBody.multiline = true;
            this.m_tfBody.wordWrap = true;
            this.m_tfBody.defaultTextFormat = this.m_bodyFormat;
            this.m_tfBody.text = param2;
            addChild(this.m_tfBody);
            if (param6 != null && param7 != null)
            {
                this.m_actionBtn = new CustomButton(param6, null, "CashButtonUI");
                this.m_actionBtn.addEventListener(MouseEvent.CLICK, this.btnActionClickHandler, false, 0, true);
                this.m_actionBtn.width = 150;
                this.m_actionBtn.height = 25;
                addChild(this.m_actionBtn);
            }
            this.draw();
            return;
        }//end  

         public double  displayHeight ()
        {
            return this.m_bg.height - 30;
        }//end  

        protected boolean  isToasterAvailable ()
        {
            double _loc_1 =0;
            if (Global.isVisiting())
            {
                return false;
            }
            if (Global.player.getLastActivationTime(this.SEEN_FLAG_NAME) != -1)
            {
                _loc_1 = uint(GlobalEngine.getTimer() / 1000);
                if (Global.player.getLastActivationTime(this.SEEN_FLAG_NAME) + this.COOLDOWN < _loc_1)
                {
                    return true;
                }
                return false;
            }
            return true;
        }//end  

        public void  mouseEventHandler (MouseEvent event )
        {
            if (event.target.name == "btnClose")
            {
            }
            else
            {
                RemodelManager.runIntroFlow();
                this.close();
            }
            event.stopImmediatePropagation();
            return;
        }//end  

        protected void  draw ()
        {
            int _loc_2 =125;
            this.m_tfBody.x = 125;
            this.m_tfTitle.x = _loc_2;
            this.m_tfTitle.y = 10;
            this.m_tfBody.y = this.m_tfTitle.y + this.m_tfTitle.height;
            if (this.m_actionBtn)
            {
                this.m_actionBtn.y = this.m_tfBody.y + 40;
                this.m_actionBtn.x = this.m_tfBody.x + 80;
            }
            if (this.m_icon)
            {
                this.m_icon.x = (this.m_iconContainer.width - this.m_icon.width) * 0.5;
                this.m_icon.y = (this.m_iconContainer.height - this.m_icon.height) * 0.5;
            }
            this.m_bg.height = this.m_tfBody.y + this.m_tfBody.height + 25;
            _loc_1 = int(GlobalEngine.getTimer()/1000);
            Global.player.setLastActivationTime(this.SEEN_FLAG_NAME, _loc_1);
            return;
        }//end  

        private void  iconLoadHandler (Event event )
        {
            this.m_icon = event.currentTarget.get("content");
            if (this.m_icon)
            {
                this.m_iconContainer.addChild(this.m_icon);
                this.draw();
            }
            return;
        }//end  

        private void  btnCloseClickHandler (MouseEvent event )
        {
            this.close();
            return;
        }//end  

        private void  btnActionClickHandler (MouseEvent event )
        {
            this.m_actionButtonCallback();
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



