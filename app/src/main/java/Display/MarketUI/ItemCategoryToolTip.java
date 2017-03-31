package Display.MarketUI;

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
//import flash.display.*;
//import flash.text.*;

    public class ItemCategoryToolTip extends Sprite
    {
        private DisplayObject m_background ;
        private DisplayObject m_beak ;
        private TextField m_tfTitle ;
        private TextField m_tfSubtitle ;
        private TextFormat m_titleFormat ;
        private TextFormat m_subtitleFormat ;
        private static  int SUBTITLE_MAX_WIDTH =200;

        public  ItemCategoryToolTip ()
        {
            boolean _loc_1 =false ;
            this.mouseEnabled = false;
            this.mouseChildren = _loc_1;
            this.m_background =(DisplayObject) new EmbeddedArt.mkt_category_bubble();
            addChild(this.m_background);
            this.m_beak =(DisplayObject) new EmbeddedArt.mkt_category_beak();
            addChild(this.m_beak);
            this.m_titleFormat = new TextFormat(EmbeddedArt.titleFont, 16, 4759807);
            this.m_subtitleFormat = new TextFormat(EmbeddedArt.defaultFontNameBold, 12, EmbeddedArt.blueTextColor);
            this.m_tfTitle = new TextField();
            this.m_tfTitle.embedFonts = EmbeddedArt.titleFontEmbed;
            this.m_tfTitle.autoSize = TextFieldAutoSize.LEFT;
            this.m_tfTitle.defaultTextFormat = this.m_titleFormat;
            this.m_tfTitle.antiAliasType = AntiAliasType.ADVANCED;
            this.m_tfTitle.gridFitType = GridFitType.SUBPIXEL;
            addChild(this.m_tfTitle);
            this.m_tfSubtitle = new TextField();
            this.m_tfSubtitle.embedFonts = EmbeddedArt.defaultBoldFontEmbed;
            this.m_tfSubtitle.autoSize = TextFieldAutoSize.LEFT;
            this.m_tfSubtitle.defaultTextFormat = this.m_subtitleFormat;
            this.m_tfSubtitle.antiAliasType = AntiAliasType.ADVANCED;
            this.m_tfSubtitle.gridFitType = GridFitType.SUBPIXEL;
            addChild(this.m_tfSubtitle);
            return;
        }//end  

        public void  changeInfo (String param1 )
        {
            this.m_tfTitle.text = ZLoc.t("Dialogs", param1 + "_menu");
            this.m_tfSubtitle.text = ZLoc.t("Dialogs", "CTT_" + param1);
            if (this.m_tfSubtitle.width >= SUBTITLE_MAX_WIDTH)
            {
                ASwingHelper.makeMultilineText(this.m_tfSubtitle.text, SUBTITLE_MAX_WIDTH, EmbeddedArt.defaultFontNameBold, TextFormatAlign.LEFT);
            }
            else
            {
                this.m_tfSubtitle.multiline = false;
                this.m_tfSubtitle.wordWrap = false;
            }
            TextFieldUtil.formatSmallCaps(this.m_tfTitle, new TextFormat(EmbeddedArt.titleFont, 20));
            this.draw();
            return;
        }//end  

        public void  changeTitle (String param1 )
        {
            this.m_tfTitle.text = param1;
            TextFieldUtil.formatSmallCaps(this.m_tfTitle, new TextFormat(EmbeddedArt.titleFont, 20));
            this.draw();
            return;
        }//end  

        public void  changeSubtitle (String param1 )
        {
            if (param1.length > 0)
            {
                this.m_tfSubtitle.text = param1;
                addChild(this.m_tfSubtitle);
            }
            else if (contains(this.m_tfSubtitle))
            {
                removeChild(this.m_tfSubtitle);
            }
            this.draw();
            return;
        }//end  

        protected void  draw ()
        {
            this.m_background.width = this.m_tfTitle.width + 20;
            this.m_background.height = this.m_tfTitle.height + 5;
            if (contains(this.m_tfSubtitle))
            {
                this.m_background.width = Math.max(this.m_background.width, this.m_tfSubtitle.width + 20);
                this.m_background.height = this.m_background.height + this.m_tfSubtitle.height;
            }
            this.m_beak.y = this.m_background.height - 6;
            this.m_beak.x = this.m_background.width * 0.5 - this.m_beak.width * 0.5;
            this.m_tfTitle.x = this.m_background.width * 0.5 - this.m_tfTitle.width * 0.5;
            this.m_tfSubtitle.x = this.m_background.width * 0.5 - this.m_tfSubtitle.width * 0.5;
            this.m_tfSubtitle.y = this.m_tfTitle.y + this.m_tfTitle.height - 5;
            return;
        }//end  

    }


