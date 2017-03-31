package Modules.guide.ui;

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
import Display.DialogUI.*;
import Display.aswingui.*;
//import flash.display.*;
//import flash.text.*;
//import flash.utils.*;
import org.aswing.*;
import org.aswing.border.*;

    public class GuideDialogView extends GenericDialogView
    {
        protected DisplayObject m_speech ;
        protected String m_advisorLocation ;
        public static  String ADVISOR_LOCATION_LEFT ="left";
        public static  String ADVISOR_LOCATION_RIGHT ="right";
        private static  int ADVISOR_TOPOFFSET =102;
        private static  int ADVISOR_LEFTOFFSET =104;
        private static  double SPEECHBUBBLE_TOPOFFSETRATIO =0.45;

        public  GuideDialogView (Dictionary param1 ,String param2 ="",String param3 ="",int param4 =0,Function param5 =null ,String param6 ="right")
        {
            this.m_advisorLocation = param6;
            super(param1, param2, param3, param4, param5);
            return;
        }//end

         protected void  init ()
        {
            this.m_speech = m_assetDict.get("speechBubble");
            this.makeCenterPanel();
            this.makeBackground();
            ASwingHelper.prepare(this);
            return;
        }//end

         protected void  makeBackground ()
        {
            return;
        }//end

         protected void  makeCenterPanel ()
        {
            AssetPane _loc_6 =null ;
            m_interiorHolder = new JPanel(new FlowLayout(FlowLayout.LEFT));
            _loc_1 = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.CENTER );
            _loc_2 = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.CENTER );
            _loc_1.setBackgroundDecorator(new MarginBackground(this.m_speech, new Insets(0, 0, 0, 0)));
            _loc_3 = Math.max(200,m_message.length *1.5,m_titleString.length *1.5);
            if (m_titleString)
            {
                _loc_6 = ASwingHelper.makeMultilineCapsText(m_titleString, _loc_3, EmbeddedArt.titleFont, TextFormatAlign.LEFT, 16, EmbeddedArt.darkBlueTextColor, null, true);
                _loc_2.append(_loc_6);
            }
            _loc_4 = ASwingHelper.makeMultilineText(m_message ,_loc_3 ,EmbeddedArt.defaultFontNameBold ,TextFormatAlign.LEFT ,16,EmbeddedArt.brownTextColor ,null ,true );
            _loc_2.append(_loc_4);
            _loc_5 = this.m_advisorLocation ==ADVISOR_LOCATION_RIGHT ? (new Insets(10, 25, 20, 50)) : (new Insets(10, 50, 20, 25));
            _loc_2.setBorder(new EmptyBorder(null, _loc_5));
            _loc_1.append(_loc_2);
            ASwingHelper.prepare(_loc_1);
            m_interiorHolder.append(_loc_1);
            ASwingHelper.prepare(m_interiorHolder);
            this.append(m_interiorHolder);
            ASwingHelper.prepare(this);
            return;
        }//end

    }



