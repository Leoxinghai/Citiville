package Modules.quest.Display;

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
import Events.*;
//import flash.events.*;
//import flash.text.*;
//import flash.utils.*;
import org.aswing.*;
import org.aswing.border.*;
import org.aswing.event.*;

    public class CityNameDialogView extends InputTextDialogView
    {
        public String m_textKey ;

        public  CityNameDialogView (Dictionary param1 ,String param2 ,String param3 ,String param4 ,String param5 ,int param6 =0,Function param7 =null ,String param8 ="",int param9 =0)
        {
            super(param1, param2, param3, param4, param5, param6, param7, param8, param9);
            return;
        }//end

         protected void  onAccept (AWEvent event )
        {
            dispatchEvent(new GenericPopupEvent(GenericPopupEvent.SELECTED, YES, true));
            if (m_closeCallback != null)
            {
                removeEventListener(GenericPopupEvent.SELECTED, m_closeCallback);
            }
            closeMe();
            return;
        }//end

         protected JPanel  createTextAreaInnerPane (Component param1 )
        {
            _loc_2 = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.CENTER );
            _loc_2.append(param1);
            _loc_3 = super.createTextField();
            _loc_4 = createTextFieldContainer(_loc_3);
            _loc_5 = this.createCityNameTextComponent ();
            _loc_3.addEventListener(TextEvent.TEXT_INPUT, textInput);
            m_textField = _loc_3;
            _loc_2.append(ASwingHelper.verticalStrut(10));
            _loc_2.append(_loc_5);
            _loc_2.append(ASwingHelper.verticalStrut(5));
            _loc_2.append(_loc_4);
            _loc_2.append(ASwingHelper.verticalStrut(9));
            return _loc_2;
        }//end

        protected JPanel  createCityNameTextComponent ()
        {
            JPanel _loc_1 =new JPanel(new BorderLayout ());
            _loc_2 = ZLoc.t("Dialogs","CongratsPlayer",{userName Global.player.firstName });
            _loc_3 = ZLoc.t("Dialogs","NameTownInstruction");
            _loc_4 = ASwingHelper.makeMultilineText(_loc_2 ,380,EmbeddedArt.defaultFontNameBold ,TextFormatAlign.CENTER ,16,10582366);
            _loc_4.setBorder(new EmptyBorder(null, new Insets(0, 20, 0, 20)));
            _loc_5 = ASwingHelper.makeMultilineText(_loc_3 ,380,EmbeddedArt.defaultFontNameBold ,TextFormatAlign.CENTER ,14,10582366);
            _loc_5.setBorder(new EmptyBorder(null, new Insets(20, 20, 0, 20)));
            _loc_1.append(_loc_4);
            _loc_1.append(ASwingHelper.verticalStrut(30));
            _loc_1.append(_loc_5);
            return _loc_1;
        }//end

    }



