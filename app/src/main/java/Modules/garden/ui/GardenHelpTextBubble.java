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
import Display.aswingui.*;
//import flash.display.*;
//import flash.text.*;
import org.aswing.*;

    public class GardenHelpTextBubble extends Sprite
    {

        public  GardenHelpTextBubble (String param1 ,String param2 )
        {
            MarginBackground _loc_3 =new MarginBackground(new GardenDialog.assetDict.get( "info_bubble") ,new Insets(0,0,0,0));
            _loc_4 = ASwingHelper.makeSoftBoxJPanelVertical ();
            _loc_4.setBackgroundDecorator(_loc_3);
            _loc_5 = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.LEFT );
            _loc_6 = ASwingHelper.makeMultilineText(param1 ,300,EmbeddedArt.defaultFontNameBold ,TextFormatAlign.CENTER ,16,EmbeddedArt.blueTextColor );
            _loc_7 = ASwingHelper.makeMultilineText(param2 ,300,EmbeddedArt.defaultFontNameBold ,TextFormatAlign.LEFT ,12,EmbeddedArt.blueTextColor );
            _loc_5.appendAll(_loc_6, _loc_7);
            ASwingHelper.setEasyBorder(_loc_5, 20, 5, 5);
            _loc_4.append(_loc_5);
            JWindow _loc_8 =new JWindow(this );
            _loc_8.setContentPane(_loc_4);
            _loc_8.show();
            ASwingHelper.prepare(_loc_8);
            return;
        }//end

    }



