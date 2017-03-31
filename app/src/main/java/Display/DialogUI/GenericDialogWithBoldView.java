package Display.DialogUI;

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
//import flash.text.*;
//import flash.utils.*;
import org.aswing.*;

    public class GenericDialogWithBoldView extends GenericDialogView
    {
        private String strBoldMessage ="";

        public  GenericDialogWithBoldView (Dictionary param1 ,String param2 ="",String param3 ="",String param4 ="",int param5 =0,Function param6 =null ,String param7 ="",int param8 =0,String param9 ="",Function param10 =null )
        {
            this.strBoldMessage = param3;
            super(param1, param2, param4, param5, param6, param7, param8, param9, param10);
            return;
        }//end

         protected Component  createTextComponent (double param1 )
        {
            Component _loc_2 =null ;
            double _loc_6 =0;
            Container _loc_7 =null ;
            _loc_3 = ASwingHelper.makeMultilineText(this.strBoldMessage,param1,EmbeddedArt.defaultFontNameBold,TextFormatAlign.LEFT,20,EmbeddedArt.brownTextColor);
            _loc_4 = ASwingHelper.makeMultilineText(m_message,param1,EmbeddedArt.defaultFontNameBold,TextFormatAlign.LEFT,18,EmbeddedArt.brownTextColor);
            double _loc_5 =75;
            if (_loc_4.getHeight() < _loc_5)
            {
                _loc_6 = (_loc_5 - _loc_4.getHeight()) / 2;
                _loc_7 = ASwingHelper.makeSoftBoxJPanelVertical();
                _loc_7.append(ASwingHelper.verticalStrut(_loc_6));
                _loc_7.append(_loc_3);
                _loc_7.append(ASwingHelper.verticalStrut(_loc_6));
                _loc_7.append(_loc_4);
                _loc_7.append(ASwingHelper.verticalStrut(_loc_6));
                _loc_2 = _loc_7;
            }
            else
            {
                _loc_2 = _loc_4;
            }
            return _loc_2;
        }//end

         protected JPanel  createTextArea ()
        {
            double _loc_2 =0;
            JPanel _loc_8 =null ;
            JPanel _loc_9 =null ;
            JPanel _loc_10 =null ;
            JPanel _loc_11 =null ;
            _loc_1 = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.CENTER ,10);
            if (m_icon != null && m_icon != "")
            {
                _loc_2 = setMessageTextWidth(m_iconPos == ICON_POS_LEFT || m_iconPos == ICON_POS_RIGHT);
                _loc_8 = createIconPane();
            }
            else
            {
                _loc_2 = setMessageTextWidth(false);
            }
            _loc_3 = this.createTextComponent(_loc_2 );
            _loc_4 = createTextAreaInnerPane(_loc_3);
            _loc_5 = _loc_8;
            _loc_6 = _loc_4;
            if (m_iconPos == ICON_POS_RIGHT || m_iconPos == ICON_POS_BOTTOM)
            {
                _loc_5 = _loc_4;
                _loc_6 = _loc_8;
            }
            _loc_1.append(ASwingHelper.horizontalStrut(35));
            if (m_iconPos == ICON_POS_LEFT || m_iconPos == ICON_POS_RIGHT)
            {
                if (_loc_5)
                {
                    _loc_1.append(_loc_5);
                }
                _loc_1.append(ASwingHelper.horizontalStrut(10));
                if (_loc_6)
                {
                    _loc_1.append(_loc_6);
                }
            }
            else
            {
                _loc_9 = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.CENTER, 10);
                ASwingHelper.setEasyBorder(_loc_9, 10);
                _loc_10 = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.CENTER);
                _loc_11 = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.CENTER);
                if (_loc_5)
                {
                    _loc_10.append(_loc_5);
                    ASwingHelper.prepare(_loc_10);
                    _loc_9.append(_loc_10);
                }
                if (_loc_6)
                {
                    _loc_11.append(_loc_6);
                    ASwingHelper.prepare(_loc_11);
                    _loc_9.append(_loc_11);
                }
                ASwingHelper.prepare(_loc_9);
                _loc_1.append(_loc_9);
            }
            _loc_1.append(ASwingHelper.horizontalStrut(50));
            _loc_7 = _loc_3.getHeight();
            return _loc_1;
        }//end

    }




