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
//import flash.display.*;
//import flash.filters.*;
//import flash.text.*;

    public class MarketCategoryToolTip extends Sprite
    {
        private TextField m_tfTitle ;
        private TextFormat m_titleFormat ;

        public  MarketCategoryToolTip (int param1 )
        {
            boolean _loc_3 =false ;
            this.mouseEnabled = false;
            this.mouseChildren = _loc_3;
            this.m_titleFormat = new TextFormat(EmbeddedArt.titleFont, 12, param1);
            this.m_tfTitle = new TextField();
            this.m_tfTitle.embedFonts = true;
            Array _loc_2 =.get(new GlowFilter(16233,1,3,3,8,BitmapFilterQuality.LOW )) ;
            this.m_tfTitle.filters = _loc_2;
            this.m_tfTitle.autoSize = TextFieldAutoSize.LEFT;
            this.m_tfTitle.defaultTextFormat = this.m_titleFormat;
            this.m_tfTitle.antiAliasType = AntiAliasType.ADVANCED;
            this.m_tfTitle.gridFitType = GridFitType.SUBPIXEL;
            addChild(this.m_tfTitle);
            return;
        }//end  

        public void  changeInfo (String param1 )
        {
            this.m_tfTitle.text = ZLoc.t("Dialogs", param1 + "_menu");
            TextFieldUtil.formatSmallCaps(this.m_tfTitle, new TextFormat(EmbeddedArt.titleFont, 12));
            this.draw();
            return;
        }//end  

        public void  changeTitle (String param1 )
        {
            this.m_tfTitle.text = param1;
            TextFieldUtil.formatSmallCaps(this.m_tfTitle, new TextFormat(EmbeddedArt.titleFont, 12));
            this.draw();
            return;
        }//end  

        protected void  draw ()
        {
            return;
        }//end  

    }



