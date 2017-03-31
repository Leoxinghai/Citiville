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
//import flash.display.*;
//import flash.filters.*;
//import flash.text.*;
//import flash.utils.*;

    public class CrewRewardIntroSprite extends Sprite
    {

        public  CrewRewardIntroSprite (Dictionary param1 ,DisplayObject param2 ,double param3 =0)
        {
            DisplayObject _loc_4 =(DisplayObject)new param1.get( "payroll_rewardBG_blue");
            addChild(_loc_4);
            addChild(param2);
            param2.x = (_loc_4.width - param2.width) / 2;
            param2.y = (_loc_4.height - param2.height) / 2;
            TextField _loc_5 =new TextField ();
            _loc_5.text = String(param3);
            _loc_5.selectable = false;
            _loc_5.embedFonts = EmbeddedArt.titleFontEmbed;
            _loc_5.antiAliasType = AntiAliasType.ADVANCED;
            _loc_5.gridFitType = GridFitType.PIXEL;
            _loc_5.autoSize = TextFieldAutoSize.CENTER;
            _loc_5.filters = .get(new GlowFilter(16777215, 1, 6, 6, 10, BitmapFilterQuality.LOW), new DropShadowFilter(1, 90, 0, 0.4, 3, 3));
            TextFormat _loc_6 =new TextFormat ();
            _loc_6.font = EmbeddedArt.titleFont;
            _loc_6.color = EmbeddedArt.lightOrangeTextColor;
            _loc_6.align = TextFormatAlign.CENTER;
            _loc_6.size = 26;
            _loc_5.setTextFormat(_loc_6);
            addChild(_loc_5);
            _loc_5.y = 70;
            return;
        }//end  

    }



