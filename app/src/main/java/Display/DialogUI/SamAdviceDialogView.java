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

import Display.*;
import Display.aswingui.*;
//import flash.utils.*;
import org.aswing.*;
import org.aswing.border.*;
import org.aswing.geom.*;

    public class SamAdviceDialogView extends GenericDialogView
    {
        private String m_customButtonText ;

        public  SamAdviceDialogView (Dictionary param1 ,String param2 ="",String param3 ="",int param4 =0,Function param5 =null ,String param6 ="",int param7 =0,String param8 ="Accept")
        {
            this.m_customButtonText = param8;
            super(param1, param2, param3, param4, param5, param6, param7);
            m_acceptTextName = param8;
            return;
        }//end  

         protected JPanel  createInteriorHolder ()
        {
            JPanel _loc_2 =null ;
            JPanel _loc_3 =null ;
            JPanel _loc_6 =null ;
            JPanel _loc_1 =new JPanel(new SoftBoxLayout(SoftBoxLayout.Y_AXIS ,0,SoftBoxLayout.TOP ));
            _loc_1.setPreferredWidth(m_bgAsset.width);
            _loc_1.setMinimumWidth(m_bgAsset.width);
            _loc_1.setMaximumWidth(m_bgAsset.width);
            _loc_2 = createHeaderPanel();
            ASwingHelper.prepare(_loc_2);
            _loc_3 = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.CENTER, 0);
            _loc_4 = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.BOTTOM);
            _loc_5 = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.BOTTOM);
            _loc_5.setPreferredSize(new IntDimension(character_width, 50));
            _loc_5.setMaximumSize(new IntDimension(character_width, 50));
            _loc_5.setMinimumSize(new IntDimension(character_width, 50));
            _loc_4.append(_loc_5);
            ASwingHelper.setEasyBorder(_loc_4, 0, 0);
            textArea = createTextArea();
            textArea.setPreferredHeight(120);
            ASwingHelper.prepare(textArea);
            _loc_3.appendAll(_loc_4, textArea);
            _loc_1.setBorder(new EmptyBorder(null, new Insets(0, 0, 0, 0)));
            _loc_1.append(_loc_2);
            _loc_1.append(_loc_3);
            if (m_type != TYPE_MODAL)
            {
                _loc_6 = this.createButtonPanel();
                _loc_1.append(_loc_6);
            }
            ASwingHelper.prepare(_loc_1);
            return _loc_1;
        }//end  

         protected void  makeBackground ()
        {
            if (m_bgAsset)
            {
                ASwingHelper.setSizedBackground(this, m_bgAsset, new Insets(0, 0, 20));
            }
            return;
        }//end  

         protected double  setMessageTextWidth (boolean param1 =false )
        {
            double _loc_2 =284;
            return _loc_2;
        }//end  

         protected JPanel  createButtonPanel ()
        {
            m_acceptTextName = ZLoc.t("Dialogs", this.m_customButtonText);
            m_acceptTextName = TextFieldUtil.formatSmallCapsString(m_acceptTextName);
            JPanel _loc_1 =new JPanel(new FlowLayout(AsWingConstants.CENTER ));
            CustomButton _loc_2 =new CustomButton(m_acceptTextName ,null ,"GreenButtonUI");
            _loc_2.addActionListener(onAccept, 0, true);
            _loc_1.append(_loc_2);
            return _loc_1;
        }//end  

    }



