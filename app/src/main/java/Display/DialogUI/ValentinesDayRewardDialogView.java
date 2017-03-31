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
//import flash.events.*;
//import flash.text.*;
//import flash.utils.*;
import org.aswing.*;
import org.aswing.border.*;

    public class ValentinesDayRewardDialogView extends GenericDialogView
    {

        public  ValentinesDayRewardDialogView (Dictionary param1 ,String param2 ="",String param3 ="",int param4 =0,Function param5 =null ,String param6 ="",int param7 =0,String param8 ="",Function param9 =null ,String param10 ="")
        {
            super(param1, param2, param3, param4, param5, param6, param7, param8, param9, param10);
            return;
        }//end  

         protected void  makeBackground ()
        {
            MarginBackground _loc_1 =null ;
            if (m_bgAsset)
            {
                _loc_1 = new MarginBackground(m_bgAsset, new Insets(0, 0, 10, 0));
                this.setBackgroundDecorator(_loc_1);
                this.setPreferredHeight(m_bgAsset.height);
                this.setMinimumHeight(m_bgAsset.height);
                this.setMaximumHeight(m_bgAsset.height);
            }
            return;
        }//end  

         protected JPanel  createInteriorHolder ()
        {
            JPanel _loc_3 =null ;
            JPanel _loc_1 =new JPanel(new SoftBoxLayout(SoftBoxLayout.Y_AXIS ,-10,SoftBoxLayout.CENTER ));
            _loc_2 = createHeaderPanel();
            ASwingHelper.prepare(_loc_2);
            textArea = createTextArea();
            ASwingHelper.prepare(textArea);
            _loc_1.setBorder(new EmptyBorder(null, new Insets(0, 0, 0, 0)));
            if (m_type != TYPE_NOBUTTONS)
            {
                _loc_1.append(_loc_2);
            }
            _loc_1.append(ASwingHelper.verticalStrut(15));
            _loc_1.append(textArea);
            _loc_1.append(ASwingHelper.verticalStrut(BUTTON_MARGIN));
            if (m_type != TYPE_MODAL && m_type != TYPE_NOBUTTONS)
            {
                _loc_3 = this.createButtonPanel();
                _loc_1.append(_loc_3);
            }
            ASwingHelper.prepare(_loc_1);
            return _loc_1;
        }//end  

         protected JPanel  createTitlePanel ()
        {
            String _loc_2 =null ;
            TextFormat _loc_3 =null ;
            _loc_1 = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.CENTER);
            m_titleFontSize = TextFieldUtil.getLocaleFontSize(30, 20, [{locale:"ja", size:30}]);
            if (m_titleString != "")
            {
                _loc_2 = ZLoc.t("Dialogs", m_titleString + "_title");
                title = ASwingHelper.makeTextField(_loc_2, EmbeddedArt.titleFont, m_titleFontSize, EmbeddedArt.darkPinkTextColor, 3);
                title.filters = EmbeddedArt.valtitleFilters;
                _loc_3 = new TextFormat();
                _loc_3.size = m_titleSmallCapsFontSize;
                TextFieldUtil.formatSmallCaps(title.getTextField(), _loc_3);
                _loc_1.append(title);
                title.getTextField().height = m_titleFontSize * 1.5;
                ASwingHelper.setEasyBorder(_loc_1, 19);
            }
            else
            {
                _loc_1.append(ASwingHelper.verticalStrut(10));
            }
            return _loc_1;
        }//end  

         protected JPanel  createButtonPanel ()
        {
            JPanel _loc_1 =new JPanel(new FlowLayout(FlowLayout.CENTER ));
            TextFieldUtil.formatSmallCapsString(m_acceptTextName);
            CustomButton _loc_2 =new CustomButton(m_acceptTextName ,null ,"PinkButtonUI");
            _loc_2.addActionListener(onAccept, 0, true);
            _loc_1.append(_loc_2);
            return _loc_1;
        }//end  

         protected JPanel  createCloseButtonPanel ()
        {
            JPanel _loc_1 =new JPanel(ASwingHelper.softBoxLayoutVertical );
            _loc_2 = ASwingHelper.makeMarketCloseButton();
            _loc_2.addEventListener(MouseEvent.CLICK, onCancel, false, 0, true);
            _loc_1.append(_loc_2);
            if (m_titleString != "")
            {
                ASwingHelper.setEasyBorder(_loc_2, 13, 0, 0, 7);
            }
            else
            {
                ASwingHelper.setEasyBorder(_loc_2, 0, 0, 8, 4);
            }
            return _loc_1;
        }//end  

    }




