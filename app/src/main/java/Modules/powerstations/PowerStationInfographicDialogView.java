package Modules.powerstations;

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

    public class PowerStationInfographicDialogView extends GenericDialogView
    {
        private AssetPane m_picPane ;

        public  PowerStationInfographicDialogView (Dictionary param1 ,String param2 ="",String param3 ="",int param4 =0,Function param5 =null ,String param6 ="",int param7 =0,String param8 ="",Function param9 =null ,String param10 ="",boolean param11 =true )
        {
            super(param1, param2, param3, param4, param5, param6, param7, param8, param9, param10, param11);
            return;
        }//end

         protected JPanel  createTextArea ()
        {
            double _loc_2 =0;
            _loc_1 = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.TOP ,5);
            _loc_3 = ASwingHelper.makeMultilineText(ZLoc.t("Dialogs","PowerStationInfographic_hint"),465,EmbeddedArt.defaultFontNameBold ,TextFormatAlign.CENTER ,18,EmbeddedArt.brownTextColor );
            _loc_4 = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.CENTER );
            _loc_5 = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.CENTER );
            this.m_picPane = new AssetPane(assetDict.get("powerstationsInfographic"));
            ASwingHelper.setEasyBorder(this.m_picPane, 0, 20, 0, 20);
            _loc_5.append(this.m_picPane);
            _loc_4.append(_loc_3);
            _loc_1.append(_loc_4);
            _loc_1.append(_loc_5);
            ASwingHelper.prepare(_loc_1);
            return _loc_1;
        }//end

    }



