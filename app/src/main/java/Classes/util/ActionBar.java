package Classes.util;

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
//import flash.filters.*;
//import flash.text.*;

    public class ActionBar extends Sprite
    {
        protected TextField m_actionText ;
        protected double m_width ;
        protected double m_height ;
        public double progress ;
        public boolean showPercent ;
        public String prefix ;
        protected DisplayObject m_bar ;
        protected Sprite m_mask ;

        public  ActionBar (int param1 ,int param2 ,String param3 ="",boolean param4 =true )
        {
            boolean _loc_8 =false ;
            this.mouseChildren = false;
            this.mouseEnabled = _loc_8;
            DisplayObject _loc_5 =new EmbeddedArt.actn_container ()as DisplayObject ;
            (new (DisplayObject)EmbeddedArt.actn_container()).width = param1;
            _loc_5.height = param2;
            this.addChild(_loc_5);
            this.m_bar =(DisplayObject) new EmbeddedArt.actn_bar();
            this.m_bar.width = param1;
            this.m_bar.height = param2;
            this.addChild(this.m_bar);
            this.m_mask = new Sprite();
            this.addChild(this.m_mask);
            this.m_bar.mask = this.m_mask;
            this.m_width = param1;
            this.m_height = param2;
            this.progress = 0;
            this.prefix = param3;
            this.showPercent = param4;
            _loc_6 = TextFieldUtil.getLocaleFontSize(12,10,.get({localesize"ja",12)});
            TextFormat _loc_7 =new TextFormat(EmbeddedArt.defaultFontNameBold ,_loc_6 ,16777215,true ,null ,null ,null ,null ,TextFormatAlign.CENTER );
            this.m_actionText = new TextField();
            this.m_actionText.embedFonts = EmbeddedArt.defaultBoldFontEmbed;
            this.m_actionText.defaultTextFormat = _loc_7;
            this.m_actionText.setTextFormat(_loc_7);
            this.m_actionText.x = 7;
            this.m_actionText.y = 2;
            this.m_actionText.width = this.m_width - 2 * this.m_actionText.x;
            this.m_actionText.filters = .get(new GlowFilter(0, 1, 1.5, 1.5, 20, BitmapFilterQuality.HIGH));
            this.addChild(this.m_actionText);
            return;
        }//end

        public void  redraw ()
        {
            double _loc_1 =6;
            double _loc_2 =1;
            this.m_mask.graphics.clear();
            this.m_mask.graphics.lineStyle(0, 0, 0);
            this.m_mask.graphics.beginFill(43520);
            this.m_mask.graphics.drawRoundRect(_loc_2 / 2, _loc_2 / 2, this.m_width * this.progress - _loc_2, this.m_height - _loc_2, _loc_1, _loc_1);
            this.m_mask.graphics.endFill();
            _loc_3 = this.prefix ;
            if (this.showPercent)
            {
                _loc_3 = _loc_3 + (" " + Math.floor(this.progress * 100) + "%");
            }
            if (this.m_actionText.text != _loc_3)
            {
                this.m_actionText.text = _loc_3;
            }
            return;
        }//end

    }



