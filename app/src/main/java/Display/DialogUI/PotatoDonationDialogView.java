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
import Display.*;
import Display.aswingui.*;
//import flash.display.*;
//import flash.text.*;
//import flash.utils.*;
import org.aswing.*;
import org.aswing.border.*;

    public class PotatoDonationDialogView extends GenericPictureDialogView
    {
        protected int m_cashCost ;

        public  PotatoDonationDialogView (Dictionary param1 ,String param2 ="",String param3 ="",int param4 =0,Function param5 =null ,String param6 ="",int param7 =0,String param8 ="",int param9 =5)
        {
            this.m_cashCost = param9;
            super(param1, param2, param3, param4, param5, param6, param7, param8);
            return;
        }//end  

         protected JPanel  createInteriorHolder ()
        {
            JPanel _loc_10 =null ;
            JPanel _loc_1 =new JPanel(new SoftBoxLayout(SoftBoxLayout.Y_AXIS ,-10,SoftBoxLayout.TOP ));
            _loc_2 = createHeaderPanel();
            ASwingHelper.prepare(_loc_2);
            textArea = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.CENTER);
            _loc_3 = ASwingHelper.makeMultilineText(ZLoc.t("Dialogs",m_message+"_secondMessage"),450,EmbeddedArt.defaultFontNameBold,TextFormatAlign.CENTER,20,EmbeddedArt.blueTextColor);
            _loc_4 = ASwingHelper.centerElement(_loc_3);
            ASwingHelper.setEasyBorder(_loc_4, 0, 10, 0, 10);
            _loc_5 = ASwingHelper.makeMultilineText(ZLoc.t("Dialogs",m_message+"_message"),460,EmbeddedArt.defaultFontNameBold,TextFormatAlign.CENTER,14,EmbeddedArt.brownTextColor);
            _loc_6 = ASwingHelper.centerElement(_loc_5);
            ASwingHelper.setEasyBorder(_loc_6, 0, 10, 0, 10);
            _loc_7 = ASwingHelper.makeMultilineText(ZLoc.t("Dialogs",m_message+"_thirdMessage"),450,EmbeddedArt.defaultFontNameBold,TextFormatAlign.CENTER,14,EmbeddedArt.brownTextColor);
            _loc_8 = ASwingHelper.centerElement(_loc_7);
            ASwingHelper.setEasyBorder(_loc_8, 0, 10, 0, 10);
            m_imagePanel = makeImagePanel();
            _loc_9 = ASwingHelper.centerElement(m_imagePanel);
            ASwingHelper.setEasyBorder(_loc_9, 0, 20, 0, 20);
            textArea.append(_loc_9);
            textArea.append(_loc_4);
            textArea.append(_loc_6);
            textArea.append(_loc_8);
            ASwingHelper.prepare(textArea);
            _loc_1.setBorder(new EmptyBorder(null, new Insets(0, 0, 0, 0)));
            if (m_type != TYPE_NOBUTTONS)
            {
                _loc_1.append(_loc_2);
            }
            _loc_1.append(ASwingHelper.verticalStrut(20));
            _loc_1.append(textArea);
            _loc_1.append(ASwingHelper.verticalStrut(BUTTON_MARGIN));
            if (m_type != TYPE_MODAL && m_type != TYPE_NOBUTTONS)
            {
                _loc_10 = this.createButtonPanel();
                _loc_1.append(_loc_10);
            }
            ASwingHelper.prepare(_loc_1);
            _loc_2.setPreferredWidth(_loc_1.getWidth());
            ASwingHelper.prepare(_loc_1);
            return _loc_1;
        }//end  

         protected JPanel  createButtonPanel ()
        {
            JPanel _loc_1 =new JPanel(new FlowLayout(AsWingConstants.CENTER ));
            TextFieldUtil.formatSmallCapsString(m_acceptTextName);
            AssetIcon _loc_2 =new AssetIcon(new EmbeddedArt.icon_cash_big ()as DisplayObject );
            CustomButton _loc_3 =new CustomButton(ZLoc.t("Dialogs","BuyCashCropButton",{amount this.m_cashCost.toString ()}),_loc_2 ,"BigCashButtonUI");
            _loc_3.addActionListener(onAccept, 0, true);
            _loc_1.append(_loc_3);
            ASwingHelper.prepare(_loc_1);
            return _loc_1;
        }//end  

    }




