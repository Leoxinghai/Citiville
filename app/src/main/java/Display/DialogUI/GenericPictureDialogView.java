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

    public class GenericPictureDialogView extends GenericDialogView
    {
        protected String m_acceptTextToken ;
        protected DisplayObject m_mainImage ;
        protected int m_widthOffset ;
        protected int m_heightOffset ;
        protected JPanel m_imagePanel ;

        public  GenericPictureDialogView (Dictionary param1 ,String param2 ="",String param3 ="",int param4 =0,Function param5 =null ,String param6 ="",int param7 =0,String param8 ="",int param9 =0,int param10 =0)
        {
            this.m_acceptTextToken = param8;
            this.m_widthOffset = param9;
            this.m_heightOffset = param10;
            super(param1, param2, param3, param4, param5, param6, param7);
            return;
        }//end  

         protected void  init ()
        {
            m_acceptTextName = ZLoc.t("Dialogs", this.m_acceptTextToken);
            m_bgAsset = m_assetDict.get("dialog_bg");
            makeBackground();
            makeCenterPanel();
            ASwingHelper.prepare(this);
            return;
        }//end  

         protected JPanel  createTitlePanel ()
        {
            _loc_1 = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.CENTER);
            title = ASwingHelper.makeTextField(m_titleString, EmbeddedArt.titleFont, m_titleFontSize, EmbeddedArt.titleColor);
            title.filters = EmbeddedArt.titleFilters;
            TextFormat _loc_2 =new TextFormat ();
            _loc_2.size = m_titleSmallCapsFontSize;
            TextFieldUtil.formatSmallCaps(title.getTextField(), _loc_2);
            _loc_1.append(title);
            title.getTextField().height = m_titleFontSize * 1.5;
            ASwingHelper.setEasyBorder(_loc_1, 4);
            return _loc_1;
        }//end  

         protected JPanel  createInteriorHolder ()
        {
            JPanel _loc_7 =null ;
            JPanel _loc_1 =new JPanel(new SoftBoxLayout(SoftBoxLayout.Y_AXIS ,-10,SoftBoxLayout.TOP ));
            _loc_2 = createHeaderPanel();
            ASwingHelper.prepare(_loc_2);
            textArea = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.CENTER);
            _loc_3 = ASwingHelper.makeMultilineText(this.m_message,450,EmbeddedArt.defaultFontNameBold,TextFormatAlign.CENTER,16,EmbeddedArt.brownTextColor);
            _loc_4 = ASwingHelper.centerElement(_loc_3);
            Array _loc_5 =.get(0 ,10,0,10) ;
            if (Global.localizer.langCode == "ja")
            {
                _loc_5 = .get(10, 10, 5, 10);
            }
            ASwingHelper.setEasyBorder(_loc_4, _loc_5.get(0), _loc_5.get(1), _loc_5.get(2), _loc_5.get(3));
            this.m_imagePanel = this.makeImagePanel();
            _loc_6 = ASwingHelper.centerElement(this.m_imagePanel);
            ASwingHelper.setEasyBorder(_loc_6, 0, 20, this.m_heightOffset, 20 + this.m_widthOffset);
            textArea.append(_loc_4);
            textArea.append(_loc_6);
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
                _loc_7 = createButtonPanel();
                _loc_1.append(_loc_7);
            }
            ASwingHelper.prepare(_loc_1);
            _loc_2.setPreferredWidth(_loc_1.getWidth());
            ASwingHelper.prepare(_loc_1);
            return _loc_1;
        }//end  

        protected JPanel  makeImagePanel ()
        {
            _loc_1 = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.CENTER);
            ASwingHelper.setSizedBackground(_loc_1, m_assetDict.get("image"), new Insets(0, 0, 10, 0));
            return _loc_1;
        }//end  

    }




