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
//import flash.text.*;
//import flash.utils.*;
import org.aswing.*;

    public class CreditDialogView extends GenericDialogView
    {
        protected String m_secondIcon ;

        public  CreditDialogView (Dictionary param1 ,String param2 ="",String param3 ="",int param4 =0,Function param5 =null ,String param6 ="",int param7 =0,String param8 ="",Function param9 =null )
        {
            super(param1, param2, param3, param4, param5, param6, param7, param8, param9);
            return;
        }//end

         protected JPanel  createTitlePanel ()
        {
            String _loc_2 =null ;
            TextFormat _loc_3 =null ;
            _loc_1 = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.CENTER );
            m_titleFontSize = TextFieldUtil.getLocaleFontSize(20, 20, null);
            if (m_titleString != "")
            {
                _loc_2 = ZLoc.t("Dialogs", m_titleString + "_title");
                title = ASwingHelper.makeTextField(_loc_2, EmbeddedArt.titleFont, m_titleFontSize, EmbeddedArt.titleColor, 3);
                title.filters = EmbeddedArt.newtitleFilters;
                _loc_3 = new TextFormat();
                _loc_3.size = m_titleSmallCapsFontSize;
                TextFieldUtil.formatSmallCaps(title.getTextField(), _loc_3);
                _loc_1.append(title);
                title.getTextField().height = m_titleFontSize * 1.5;
            }
            else
            {
                _loc_1.append(ASwingHelper.verticalStrut(10));
            }
            ASwingHelper.setEasyBorder(_loc_1, 4);
            return _loc_1;
        }//end

         protected double  setMessageTextWidth (boolean param1 =false )
        {
            double _loc_2 =0;
            if (param1 == false)
            {
                _loc_2 = 500;
            }
            else
            {
                _loc_2 = 420;
            }
            return _loc_2;
        }//end

    }


