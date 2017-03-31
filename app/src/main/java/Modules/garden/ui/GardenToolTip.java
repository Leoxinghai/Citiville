package Modules.garden.ui;

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
//import flash.display.*;
//import flash.text.*;

    public class GardenToolTip extends Sprite
    {
        private TextField m_tfTitle ;
        private TextField m_tfSubtitle ;
        private TextFormat m_titleFormat ;
        private TextFormat m_titleSmallFormat ;
        private TextFormat m_titleSuperSmallFormat ;
        private TextFormat m_subtitleFormat ;
        private TextField m_tfDesc ;
        private TextFormat m_descFormat ;
        private TextField m_tfTab ;
        private TextFormat m_tabFormat ;
        protected Sprite m_tabHolder ;
        protected Sprite m_contentHolder ;
        private TextFormat m_tForm2 ;
        private TextFormat m_tForm2Small ;
        private TextFormat m_tForm2SuperSmall ;
        protected DisplayObject m_ttbg ;
        protected DisplayObject m_hrAsset ;

        public  GardenToolTip ()
        {
            this.init();
            return;
        }//end

        private void  init ()
        {
            this.m_ttbg =(DisplayObject) new EmbeddedArt.mkt_pop_info();
            addChild(this.m_ttbg);
            this.m_hrAsset =(DisplayObject) new EmbeddedArt.mkt_rollover_horizontalRule();
            addChild(this.m_hrAsset);
            this.m_hrAsset.y = 25;
            this.m_hrAsset.x = (this.m_ttbg.width - this.m_hrAsset.width) / 2;
            this.m_contentHolder = new Sprite();
            addChild(this.m_contentHolder);
            this.m_tabHolder = new Sprite();
            addChild(this.m_tabHolder);
            this.m_tabFormat = new TextFormat(EmbeddedArt.defaultFontNameBold, 16, EmbeddedArt.blueTextColor);
            this.m_titleFormat = new TextFormat(EmbeddedArt.titleFont, 16, EmbeddedArt.blueTextColor);
            this.m_titleSmallFormat = new TextFormat(EmbeddedArt.titleFont, 13, EmbeddedArt.blueTextColor);
            this.m_titleSuperSmallFormat = new TextFormat(EmbeddedArt.titleFont, 10, EmbeddedArt.blueTextColor);
            this.m_subtitleFormat = new TextFormat(EmbeddedArt.defaultFontNameBold, 14, EmbeddedArt.greenTextColor);
            this.m_descFormat = new TextFormat(EmbeddedArt.defaultFontNameBold, 14, EmbeddedArt.brownTextColor);
            this.m_tfTitle = new TextField();
            this.m_tfTitle.embedFonts = EmbeddedArt.titleFontEmbed;
            this.m_tfTitle.autoSize = TextFieldAutoSize.CENTER;
            this.m_tfTitle.defaultTextFormat = this.m_titleFormat;
            this.m_tfTitle.antiAliasType = AntiAliasType.ADVANCED;
            this.m_tfTitle.gridFitType = GridFitType.SUBPIXEL;
            this.m_contentHolder.addChild(this.m_tfTitle);
            this.m_tfTab = new TextField();
            this.m_tfTab.embedFonts = EmbeddedArt.defaultBoldFontEmbed;
            this.m_tfTab.autoSize = TextFieldAutoSize.CENTER;
            this.m_tfTab.defaultTextFormat = this.m_tabFormat;
            this.m_tfTab.antiAliasType = AntiAliasType.ADVANCED;
            this.m_tfTab.gridFitType = GridFitType.SUBPIXEL;
            this.m_tfTab.multiline = true;
            this.m_tfTab.wordWrap = true;
            this.m_tfTab.width = this.m_ttbg.width - 4;
            this.m_tabHolder.addChild(this.m_tfTab);
            this.m_tfSubtitle = new TextField();
            this.m_tfSubtitle.embedFonts = EmbeddedArt.defaultBoldFontEmbed;
            this.m_tfSubtitle.autoSize = TextFieldAutoSize.CENTER;
            this.m_tfSubtitle.defaultTextFormat = this.m_subtitleFormat;
            this.m_tfSubtitle.antiAliasType = AntiAliasType.ADVANCED;
            this.m_tfSubtitle.gridFitType = GridFitType.SUBPIXEL;
            this.m_contentHolder.addChild(this.m_tfSubtitle);
            this.m_tfSubtitle.y = 28;
            this.m_tfDesc = new TextField();
            this.m_tfDesc.embedFonts = EmbeddedArt.defaultBoldFontEmbed;
            this.m_tfDesc.autoSize = TextFieldAutoSize.CENTER;
            this.m_tfDesc.defaultTextFormat = this.m_descFormat;
            this.m_tfDesc.antiAliasType = AntiAliasType.ADVANCED;
            this.m_tfDesc.gridFitType = GridFitType.SUBPIXEL;
            this.m_tfDesc.multiline = true;
            this.m_tfDesc.wordWrap = true;
            this.m_contentHolder.addChild(this.m_tfDesc);
            this.m_tfDesc.y = 45;
            this.m_tfDesc.width = this.m_ttbg.width - 10;
            this.m_tfDesc.x = 4;
            this.m_tForm2 = new TextFormat();
            this.m_tForm2.size = 20;
            this.m_tForm2Small = new TextFormat();
            this.m_tForm2Small.size = 14;
            this.m_tForm2SuperSmall = new TextFormat();
            this.m_tForm2SuperSmall.size = 10;
            this.x = 0;
            boolean _loc_1 =false ;
            this.mouseEnabled = false;
            this.mouseChildren = _loc_1;
            return;
        }//end

        public void  changeTitle (String param1 )
        {
            this.m_contentHolder.visible = false;
            this.m_tabHolder.visible = true;
            this.m_hrAsset.visible = false;
            this.m_tfTab.text = param1;
            this.m_ttbg.height = this.m_tabHolder.height + 30;
            return;
        }//end

        public void  changeInfo (GardenCell param1 ,String param2 )
        {
            this.m_contentHolder.visible = true;
            this.m_tabHolder.visible = false;
            this.m_hrAsset.visible = true;
            _loc_3 = param1.getX ()+120;
            _loc_4 = param1.flower.localizedName ;
            _loc_5 = ZLoc.t("Dialogs","GardenDialog_flowerRarity_"+param1.flower.rarity );
            _loc_6 = param2;
            this.m_tfTitle.text = _loc_4;
            this.m_tfSubtitle.text = _loc_5;
            this.m_tfDesc.text = _loc_6;
            if (this.m_tfTitle.text.length >= 20)
            {
                this.m_tfTitle.defaultTextFormat = this.m_titleSuperSmallFormat;
                TextFieldUtil.formatSmallCaps(this.m_tfTitle, this.m_tForm2SuperSmall);
            }
            else if (this.m_tfTitle.text.length >= 15)
            {
                this.m_tfTitle.defaultTextFormat = this.m_titleSmallFormat;
                TextFieldUtil.formatSmallCaps(this.m_tfTitle, this.m_tForm2Small);
            }
            else
            {
                this.m_tfTitle.defaultTextFormat = this.m_titleFormat;
                TextFieldUtil.formatSmallCaps(this.m_tfTitle, this.m_tForm2);
            }
            this.m_tfTitle.x = (this.width - this.m_tfTitle.width) / 2;
            this.m_tfSubtitle.x = (this.width - this.m_tfSubtitle.width) / 2;
            this.m_tfDesc.width = this.m_ttbg.width - 10;
            this.m_ttbg.height = this.m_contentHolder.height + 30;
            this.x = _loc_3;
            this.y = 350 - this.height;
            return;
        }//end

    }



