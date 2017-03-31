package Modules.crew.ui;

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
import ZLocalization.*;
//import flash.display.*;
//import flash.filters.*;
//import flash.text.*;
//import flash.utils.*;

    public class CrewRewardSprite extends Sprite
    {
        protected DisplayObject m_bg ;
        private TextField m_rewardAmount ;
        private TextFormat m_rewardFormat ;
        private TextField m_responses ;
        private TextFormat m_responseFormat ;
        private int m_xoffset ;
        private int m_yoffset ;
        protected DisplayObject m_completeCheck ;
        protected DisplayObject m_rewardImage ;
        protected Dictionary m_assets ;

        public  CrewRewardSprite (Dictionary param1 ,DisplayObject param2 ,boolean param3 ,double param4 =0,double param5 =0)
        {
            this.m_assets = param1;
            this.m_rewardImage = param2;
            this.m_xoffset = 0;
            this.m_yoffset = 30;
            if (param3)
            {
                this.m_xoffset = 15;
                this.m_yoffset = 15;
            }
            this.m_bg = param3 ? (new param1.get("payroll_rewardBG_green")) : (new param1.get("payroll_rewardBG_blue"));
            addChild(this.m_bg);
            this.m_bg.x = this.m_xoffset;
            this.m_bg.y = this.m_yoffset;
            addChild(this.m_rewardImage);
            this.m_rewardImage.x = (this.m_bg.width - this.m_rewardImage.width) / 2 + this.m_xoffset;
            this.m_rewardImage.y = (this.m_bg.height - this.m_rewardImage.height) / 2 + this.m_yoffset;
            _loc_6 = param3? (EmbeddedArt.lightOrangeTextColor) : (EmbeddedArt.rollCallStatusBlue);
            this.m_rewardAmount = new TextField();
            this.m_rewardAmount.text = String(param4);
            this.m_rewardAmount.selectable = false;
            this.m_rewardAmount.embedFonts = EmbeddedArt.titleFontEmbed;
            this.m_rewardAmount.antiAliasType = AntiAliasType.ADVANCED;
            this.m_rewardAmount.gridFitType = GridFitType.PIXEL;
            this.m_rewardAmount.autoSize = TextFieldAutoSize.CENTER;
            this.m_rewardAmount.filters = .get(new GlowFilter(16777215, 1, 6, 6, 10, BitmapFilterQuality.LOW), new DropShadowFilter(1, 90, 0, 0.4, 3, 3));
            this.m_rewardFormat = new TextFormat();
            this.m_rewardFormat.font = EmbeddedArt.titleFont;
            this.m_rewardFormat.color = _loc_6;
            this.m_rewardFormat.align = TextFormatAlign.CENTER;
            this.m_rewardFormat.size = 22;
            this.m_rewardAmount.setTextFormat(this.m_rewardFormat);
            addChild(this.m_rewardAmount);
            if (param3)
            {
                this.m_rewardAmount.x = this.m_rewardAmount.x + this.m_xoffset;
            }
            this.m_rewardAmount.y = 70 + this.m_yoffset;
            this.m_responses = new TextField();
            _loc_7 = ZLoc.tk("Dialogs","Response","",param5 );
            this.m_responses.text = TextFieldUtil.formatSmallCapsString(ZLoc.t("Dialogs", "RollCall_responsesRequired", {amount:String(param5), response:_loc_7}));
            this.m_responses.selectable = false;
            this.m_responses.embedFonts = EmbeddedArt.defaultBoldFontEmbed;
            this.m_responses.multiline = true;
            this.m_responses.width = 93;
            this.m_responses.wordWrap = true;
            this.m_responses.antiAliasType = AntiAliasType.ADVANCED;
            this.m_responses.gridFitType = GridFitType.PIXEL;
            this.m_responses.autoSize = TextFieldAutoSize.CENTER;
            this.m_responseFormat = new TextFormat();
            this.m_responseFormat.font = EmbeddedArt.defaultFontNameBold;
            this.m_responseFormat.color = EmbeddedArt.brownTextColor;
            this.m_responseFormat.align = TextFormatAlign.CENTER;
            this.m_responseFormat.size = 12;
            this.m_responses.setTextFormat(this.m_responseFormat);
            if (param5 != 0 && !param3)
            {
                addChild(this.m_responses);
            }
            this.m_completeCheck =(DisplayObject) new param1.get("payroll_checkmark_reward");
            if (param3)
            {
                addChild(this.m_completeCheck);
            }
            return;
        }//end

        public void  update (boolean param1 )
        {
            this.m_xoffset = 0;
            this.m_yoffset = 30;
            if (param1 !=null)
            {
                this.m_xoffset = 15;
                this.m_yoffset = 15;
            }
            this.m_rewardImage.x = (this.m_bg.width - this.m_rewardImage.width) / 2 + this.m_xoffset;
            this.m_rewardImage.y = (this.m_bg.height - this.m_rewardImage.height) / 2 + this.m_yoffset;
            this.m_bg = param1 ? (new this.m_assets.get("payroll_rewardBG_green")) : (new this.m_assets.get("payroll_rewardBG_blue"));
            this.m_bg.x = this.m_xoffset;
            this.m_bg.y = this.m_yoffset;
            _loc_2 = param1? (EmbeddedArt.lightOrangeTextColor) : (EmbeddedArt.rollCallStatusBlue);
            this.m_rewardFormat.color = _loc_2;
            this.m_rewardAmount.setTextFormat(this.m_rewardFormat);
            if (param1 !=null)
            {
                this.m_rewardAmount.x = this.m_rewardAmount.x + this.m_xoffset;
            }
            this.m_rewardAmount.y = 70 + this.m_yoffset;
            if (param1 !=null)
            {
                addChild(this.m_completeCheck);
                ;
            }
            return;
        }//end

    }



