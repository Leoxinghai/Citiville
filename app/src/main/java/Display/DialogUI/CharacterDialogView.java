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

import Display.aswingui.*;
//import flash.display.*;
//import flash.events.*;
//import flash.text.*;
//import flash.utils.*;
import org.aswing.*;
import org.aswing.border.*;

    public class CharacterDialogView extends GenericDialogView
    {
        protected String m_customButtonText ;
        protected Object m_formatParams ;

        public  CharacterDialogView (Dictionary param1 ,Object param2 ,String param3 ="",String param4 ="",int param5 =0,Function param6 =null ,String param7 ="",int param8 =0,String param9 ="Okay")
        {
            this.m_customButtonText = param9;
            this.m_formatParams = param2;
            super(param1, param3, param4, param5, param6, param7, param8);
            m_messagePaddingLeft = param2.get("messagePaddingLeft") ? (param2.get("messagePaddingLeft")) : (-7);
            m_messagePaddingRight = param2.get("messagePaddingRight") ? (param2.get("messagePaddingRight")) : (0);
            m_titleFontSize = param2.get("titleFontSize") ? (param2.get("titleFontSize")) : (14);
            m_titleSmallCapsFontSize = param2.get("titleSmallCapsFontSize") ? (param2.get("titleSmallCapsFontSize")) : (22);
            m_align = param2.get("align") ? (param2.get("align")) : (TextFormatAlign.LEFT);
            conditionalRebuildCenterPanel();
            return;
        }//end

         protected JPanel  createInteriorHolder ()
        {
            JPanel _loc_8 =null ;
            JPanel _loc_1 =new JPanel(new SoftBoxLayout(SoftBoxLayout.Y_AXIS ,-20,SoftBoxLayout.TOP ));
            _loc_1.setPreferredWidth(m_bgAsset.width);
            _loc_1.setMinimumWidth(m_bgAsset.width);
            _loc_1.setMaximumWidth(m_bgAsset.width);
            _loc_2 = createHeaderPanel();
            ASwingHelper.prepare(_loc_2);
            _loc_3 = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.CENTER,0);
            _loc_4 = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.BOTTOM);
            _loc_5 = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.BOTTOM);
            _loc_6 = m_assetDict.get("character") ;
            AssetPane _loc_7 =new AssetPane(_loc_6 );
            ASwingHelper.setSizedBackground(_loc_5, _loc_6);
            _loc_4.append(_loc_5);
            ASwingHelper.setEasyBorder(_loc_4, 0, 0);
            textArea = createTextArea();
            ASwingHelper.prepare(textArea);
            _loc_3.appendAll(_loc_4, textArea);
            _loc_1.setBorder(new EmptyBorder(null, new Insets(0, 0, 0, 0)));
            _loc_1.append(_loc_2);
            _loc_1.append(ASwingHelper.verticalStrut(35));
            _loc_1.append(_loc_3);
            boolean _loc_9 =false ;
            _loc_3.mouseEnabled = false;
            _loc_3.mouseChildren = _loc_9;
            if (m_type != TYPE_MODAL)
            {
                _loc_8 = this.createButtonPanel();
                _loc_1.append(_loc_8);
            }
            ASwingHelper.prepare(_loc_1);
            return _loc_1;
        }//end

         protected double  setMessageTextWidth (boolean param1 =false )
        {
            return 270;
        }//end

         protected JPanel  createButtonPanel ()
        {
            m_acceptTextName = ZLoc.t("Dialogs", this.m_customButtonText);
            JPanel _loc_1 =new JPanel(new FlowLayout(AsWingConstants.CENTER ));
            CustomButton _loc_2 =new CustomButton(m_acceptTextName ,null ,"GreenButtonUI");
            _loc_2.addActionListener(onAccept, 0, true);
            _loc_1.append(_loc_2);
            return _loc_1;
        }//end

         protected JPanel  createCloseButtonPanel ()
        {
            JPanel _loc_1 =new JPanel(ASwingHelper.softBoxLayoutVertical );
            _loc_2 = ASwingHelper.makeMarketCloseButton();
            _loc_2.addEventListener(MouseEvent.CLICK, onCancelX, false, 0, true);
            _loc_1.append(_loc_2);
            ASwingHelper.setEasyBorder(_loc_2, 4, 0, 0, 4);
            return _loc_1;
        }//end

    }



