package Display.aswingui.inline.util;

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

import Display.aswingui.inline.style.*;
//import flash.text.*;
import org.aswing.*;

    public class ASwingFont
    {
        public static  String TITLE_FONT ="rhino";
        public static  String DEFAULT_FONT ="bryantReg";
        public static  String DEFAULT_FONT_BOLD ="bryantBold";
        public static  String DEFAULT_SERIF_FONT ="timesNewBold";
        private static  Object embeddedFonts ={};

        public  ASwingFont ()
        {
            return;
        }//end

        public static void  embed (String param1 )
        {
            ASFontAdvProperties _loc_2 =new ASFontAdvProperties(true ,AntiAliasType.ADVANCED ,GridFitType.PIXEL );
            embeddedFonts.put(param1,  _loc_2);
            return;
        }//end

        public static boolean  isEmbed (String param1 )
        {
            return embeddedFonts.hasOwnProperty(param1);
        }//end

        public static ASFont  create (String param1 ,int param2 ,String param3 =null ,String param4 =null ,String param5 =null )
        {
            ASFontAdvProperties _loc_6 =null ;
            boolean _loc_7 =false ;
            boolean _loc_8 =false ;
            boolean _loc_9 =false ;
            if (embeddedFonts.hasOwnProperty(param1))
            {
                _loc_6 =(ASFontAdvProperties) embeddedFonts.get(param1);
            }
            else
            {
                _loc_7 = (param3 == Display.aswingui.inline.style.FontStyle.BOLD?true:false);
                _loc_8 = (param4 == Display.aswingui.inline.style.FontStyle.ITALIC?true:false);
                _loc_9 = (param5 == Display.aswingui.inline.style.FontStyle.UNDERLINE?true:false);
            }
            ASFont _loc_10 =new ASFont(param1 ,param2 ,_loc_7 ,_loc_8 ,_loc_9 ,_loc_6 );
            return new ASFont(param1, param2, _loc_7, _loc_8, _loc_9, _loc_6);
        }//end

    }


