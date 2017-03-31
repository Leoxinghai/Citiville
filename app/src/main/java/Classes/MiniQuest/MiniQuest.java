package Classes.MiniQuest;

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
import Engine.Helpers.*;
import Engine.Managers.*;
//import flash.display.*;
//import flash.events.*;
//import flash.filters.*;
//import flash.text.*;
//import flash.utils.*;

    public class MiniQuest
    {
        protected String m_questIconURL ;
        protected DisplayObjectContainer m_questHudIcon ;
        protected int m_numFrames ;
        protected int m_iconWidth ;
        protected int m_iconHeight ;
        protected int m_iconFps ;
        private Dictionary m_iconUrlMap ;
        protected boolean m_questActive =false ;
        protected String m_questName ="";
        protected double m_recurrenceTime =45;

        public void  MiniQuest (String param1 )
        {
            XML _loc_2 =null ;
            XML _loc_3 =null ;
            this.m_questHudIcon = new Sprite();
            this.m_iconUrlMap = new Dictionary();
            this.m_questName = param1;
            if (!this.questHudIconBitmap)
            {
                _loc_2 = Global.gameSettings().getIconXMLByName(this.m_questName);
                if (_loc_2)
                {
                    _loc_3 = _loc_2.image.get(0);
                    this.m_numFrames = _loc_3.@fps || 1;
                    this.m_iconWidth = _loc_3.@frameWidth;
                    this.m_iconHeight = _loc_3.@frameHeight;
                    this.m_iconFps = _loc_3.@fps;
                    if (!this.useCustomIcon())
                    {
                        this.m_questIconURL = String(_loc_3.asset.@url);
                    }
                }
            }
            return;
        }//end

        protected AutoAnimatedBitmap  questHudIconBitmap ()
        {
            return this.m_iconUrlMap.get(this.m_questIconURL);
        }//end

        public int  recurrenceTime ()
        {
            return this.m_recurrenceTime;
        }//end

        public void  setIcon (String param1 )
        {
            this.m_questIconURL = param1;
            return;
        }//end

        protected void  removeIconChildren ()
        {
            while (this.m_questHudIcon && this.m_questHudIcon.numChildren > 0)
            {

                this.m_questHudIcon.removeChildAt(0);
            }
            return;
        }//end

        protected void  onIconLoaded (Event event ,String param2 )
        {
            Bitmap _loc_4 =null ;
            String _loc_5 =null ;
            _loc_3 = (Loader)event.target.loader
            if (_loc_3.content instanceof Bitmap)
            {
                _loc_4 =(Bitmap) _loc_3.content;
                this.m_iconUrlMap.put(param2,  new AutoAnimatedBitmap(_loc_4.bitmapData, this.m_numFrames, this.m_iconWidth ? (this.m_iconWidth) : (_loc_4.width), this.m_iconHeight ? (this.m_iconHeight) : (_loc_4.height), this.m_iconFps ? (this.m_iconFps) : (1)));
                _loc_5 = this.miniQuestLabel;
                if (_loc_5)
                {
                    this.m_questHudIcon.addChild(this.makeLabel(_loc_5, this.m_iconWidth ? (this.m_iconWidth) : (_loc_4.width)));
                }
            }
            this.addMQIcon();
            return;
        }//end

        private void  addMQIcon ()
        {
            this.removeIconChildren();
            this.m_questHudIcon = new Sprite();
            this.m_questHudIcon.addChild(this.questHudIconBitmap);
            _loc_1 = this.getTooltip ();
            Global.hud.hideMiniQuestSprite(this.m_questName, this.onIconClicked);
            _loc_2 =Global.hud.showMiniQuestSprite(this.m_questName ,_loc_1 ,this.m_questHudIcon ,this.onIconClicked );
            if (!_loc_2)
            {
                return;
            }
            Global.hud.showGoalsProgressOverlayOnQuestIcon(this.m_questName, "click");
            this.m_questActive = true;
            return;
        }//end

        public boolean  useCustomIcon ()
        {
            return false;
        }//end

        public void  activate ()
        {
            if (!this.isActive())
            {
                this.initQuest();
            }
            return;
        }//end

        public void  deactivate ()
        {
            this.endQuest();
            return;
        }//end

        protected boolean  isActive ()
        {
            return this.m_questActive;
        }//end

        protected void  initQuest ()
        {
            String _loc_1 =null ;
            if (this.questHudIconBitmap == null)
            {
                _loc_1 = this.m_questIconURL;
                LoadingManager.load(Global.getAssetURL(_loc_1), Delegate.create(this, this.onIconLoaded, _loc_1));
            }
            else
            {
                this.addMQIcon();
            }
            return;
        }//end

        public String  getTooltip ()
        {
            return ZLoc.t("Dialogs", "Mini_" + this.m_questName);
        }//end

        protected void  endQuest ()
        {
            Global.hud.hideMiniQuestSprite(this.m_questName, this.onIconClicked);
            if (this.questHudIconBitmap)
            {
                this.questHudIconBitmap.cleanUp();
            }
            this.m_questActive = false;
            return;
        }//end

        protected void  onIconClicked (MouseEvent event )
        {
            this.endQuest();
            return;
        }//end

        public String  miniQuestName ()
        {
            return this.m_questName;
        }//end

        protected String  miniQuestLabel ()
        {
            return null;
        }//end

        protected Vector2  miniQuestLabelOffset ()
        {
            return null;
        }//end

        protected DisplayObject  makeLabel (String param1 ,double param2 )
        {
            TextFormat _loc_3 =new TextFormat ();
            _loc_3.color = EmbeddedArt.yellowTextColor;
            _loc_3.align = TextFormatAlign.CENTER;
            _loc_4 = TextFieldUtil.getLocaleFontSize(12,10,.get({localesize"ja",12)});
            _loc_3.size = _loc_4;
            TextField _loc_5 =new TextField ();
            if (Global.localizer.langCode != "en")
            {
                _loc_3.font = EmbeddedArt.defaultFontName;
                _loc_5.width = param2 + 10;
            }
            else
            {
                _loc_3.font = EmbeddedArt.titleFont;
                _loc_5.width = param2;
            }
            _loc_5.multiline = true;
            _loc_5.wordWrap = true;
            _loc_5.selectable = false;
            _loc_5.autoSize = TextFieldAutoSize.CENTER;
            _loc_5.text = param1;
            _loc_5.embedFonts = EmbeddedArt.isEmbedFont(_loc_3.font);
            _loc_5.antiAliasType = AntiAliasType.ADVANCED;
            _loc_5.gridFitType = GridFitType.PIXEL;
            _loc_5.setTextFormat(_loc_3);
            _loc_5.filters = .get(new GlowFilter(EmbeddedArt.orangeTextColor, 1, 3, 3, 5, BitmapFilterQuality.LOW), new DropShadowFilter(1, 45, EmbeddedArt.blackTextColor, 0.6, 3, 3, 6));
            _loc_6 = this.miniQuestLabelOffset;
            if (this.miniQuestLabelOffset)
            {
                _loc_5.x = _loc_6.x;
                _loc_5.y = _loc_6.y;
            }
            return _loc_5;
        }//end

    }



