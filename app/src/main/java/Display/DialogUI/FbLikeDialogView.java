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
import org.aswing.border.*;

    public class FbLikeDialogView extends GenericDialogView
    {

        public  FbLikeDialogView (Dictionary param1 ,String param2 ="",String param3 ="",int param4 =0,Function param5 =null ,String param6 ="",int param7 =0,String param8 ="",Function param9 =null ,String param10 ="",boolean param11 =true ,String param12 ="",String param13 ="",String param14 ="")
        {
            super(param1, param2, param3, param4, param5, param6, param7, param8, param9, param10, param11, false, param12, param13, param14);
            return;
        }//end  

         protected JPanel  createTextArea ()
        {
            double _loc_2 =0;
            JPanel _loc_8 =null ;
            _loc_1 = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.CENTER,0);
            m_messagePaddingLeft = 8;
            m_messagePaddingRight = 20;
            if (m_icon != null && m_icon != "")
            {
                _loc_2 = setMessageTextWidth(m_iconPos == ICON_POS_LEFT || m_iconPos == ICON_POS_RIGHT);
                _loc_8 = createIconPane();
            }
            _loc_3 = this.createTextComponent(_loc_2);
            _loc_4 = createTextAreaInnerPane(_loc_3);
            _loc_5 = _loc_8;
            _loc_6 = _loc_4;
            _loc_1.append(ASwingHelper.horizontalStrut(m_messagePaddingLeft));
            if (m_iconPos == ICON_POS_LEFT || m_iconPos == ICON_POS_RIGHT)
            {
                if (_loc_5)
                {
                    _loc_1.append(_loc_5);
                }
                _loc_1.append(ASwingHelper.horizontalStrut(m_messagePaddingRight));
                if (_loc_6)
                {
                    _loc_1.append(_loc_6);
                }
            }
            _loc_1.append(ASwingHelper.horizontalStrut(m_messagePaddingRight));
            _loc_7 = _loc_3.getHeight();
            return _loc_1;
        }//end  

         protected JPanel  createInteriorHolder ()
        {
            JPanel _loc_5 =null ;
            JPanel _loc_6 =null ;
            JPanel _loc_1 =new JPanel(new SoftBoxLayout(SoftBoxLayout.Y_AXIS ,-10,AsWingConstants.CENTER ));
            _loc_2 = createHeaderPanel();
            ASwingHelper.prepare(_loc_2);
            textArea = this.createTextArea();
            ASwingHelper.prepare(textArea);
            _loc_3 = createFBLikeButton();
            if (_loc_3 != null)
            {
                ASwingHelper.prepare(_loc_3);
            }
            _loc_1.setBorder(new EmptyBorder(null, new Insets(0, 0, 0, 0)));
            if (m_type != TYPE_NOBUTTONS)
            {
                _loc_1.append(_loc_2);
            }
            _loc_1.append(ASwingHelper.verticalStrut(20));
            _loc_1.append(textArea);
            if (_loc_3 != null)
            {
                _loc_5 = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.LEFT, 0);
                _loc_5.appendAll(ASwingHelper.horizontalStrut(420), _loc_3);
                _loc_1.append(_loc_5);
            }
            _loc_4 = ASwingHelper.verticalStrut(20);
            boolean _loc_7 =false ;
            ASwingHelper.verticalStrut(20).mouseChildren = false;
            ASwingHelper.verticalStrut(20).mouseEnabled = _loc_7;
            _loc_1.append(_loc_4);
            if (m_type != TYPE_MODAL && m_type != TYPE_NOBUTTONS)
            {
                _loc_6 = createButtonPanel();
                _loc_1.append(_loc_6);
            }
            ASwingHelper.prepare(_loc_1);
            return _loc_1;
        }//end  

         protected Component  createTextComponent (double param1 )
        {
            Component _loc_2 =null ;
            double _loc_5 =0;
            Container _loc_6 =null ;
            m_align = TextFormatAlign.LEFT;
            _loc_3 = ASwingHelper.makeMultilineText(m_message,param1,EmbeddedArt.defaultFontNameBold,m_align,18,EmbeddedArt.blueTextColor);
            double _loc_4 =75;
            if (_loc_3.getHeight() < _loc_4)
            {
                _loc_5 = (_loc_4 - _loc_3.getHeight()) / 2;
                _loc_6 = ASwingHelper.makeSoftBoxJPanelVertical();
                _loc_6.append(ASwingHelper.verticalStrut(_loc_5));
                _loc_6.append(_loc_3);
                _loc_6.append(ASwingHelper.verticalStrut(_loc_5));
                _loc_2 = _loc_6;
            }
            else
            {
                _loc_2 = _loc_3;
            }
            return _loc_2;
        }//end  

    }




