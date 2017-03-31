package Modules.timecapsule.ui;

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
//import flash.text.*;
//import flash.utils.*;
import org.aswing.*;

    public class LetterPictureDialogView extends GenericPictureDialogView
    {
        protected String m_letterText ;

        public  LetterPictureDialogView (Dictionary param1 ,String param2 ="",String param3 ="",int param4 =0,Function param5 =null ,String param6 ="",int param7 =0,String param8 ="Invite",String param9 ="")
        {
            this.m_letterText = param9;
            super(param1, param2, param3, param4, param5, param6, param7, param8);
            return;
        }//end  

         protected JPanel  makeImagePanel ()
        {
            JPanel _loc_1 =null ;
            JPanel _loc_2 =null ;
            AssetPane _loc_3 =null ;
            _loc_1 = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.CENTER);
            ASwingHelper.setSizedBackground(_loc_1, m_assetDict.get("image"), new Insets(0, 0, 10, 0));
            _loc_2 = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.TOP);
            _loc_3 = ASwingHelper.makeMultilineText(this.m_letterText, 195, EmbeddedArt.defaultSerifFont, TextFormatAlign.LEFT, 20);
            _loc_2.appendAll(ASwingHelper.verticalStrut(25), _loc_3);
            _loc_1.appendAll(ASwingHelper.horizontalStrut(190), _loc_2);
            return _loc_1;
        }//end  

    }



