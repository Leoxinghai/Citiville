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
import org.aswing.border.*;

    public class CharacterAdviceDialogView extends GenericDialogView
    {
        private String m_customButtonText ;

        public  CharacterAdviceDialogView (Dictionary param1 ,String param2 ="",String param3 ="",int param4 =0,Function param5 =null ,String param6 ="",int param7 =0,String param8 ="Accept")
        {
            this.m_customButtonText = param8;
            super(param1, param2, param3, param4, param5, param6, param7);
            m_acceptTextName = param8;
            return;
        }//end  

         protected JPanel  createInteriorHolder ()
        {
            JPanel _loc_15 =null ;
            JPanel _loc_1 =new JPanel(new SoftBoxLayout(SoftBoxLayout.Y_AXIS ,0,SoftBoxLayout.TOP ));
            _loc_1.setPreferredWidth(m_bgAsset.width);
            _loc_1.setMinimumWidth(m_bgAsset.width);
            _loc_1.setMaximumWidth(m_bgAsset.width);
            _loc_2 = createHeaderPanel();
            ASwingHelper.prepare(_loc_2);
            _loc_1.append(_loc_2);
            _loc_3 = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.CENTER,-20);
            AssetPane _loc_4 =new AssetPane(m_assetDict.get( "character") );
            _loc_4.setBorder(new EmptyBorder(null, new Insets(10, 5, 0, 0)));
            _loc_3.append(_loc_4);
            _loc_5 = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.CENTER,0);
            AssetPane _loc_6 =new AssetPane(m_assetDict.get( "divider") );
            _loc_5.append(_loc_6);
            int _loc_7 =15;
            _loc_3.appendAll(ASwingHelper.horizontalStrut(28), _loc_5, ASwingHelper.horizontalStrut(77));
            _loc_8 = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.CENTER,0);
            _loc_9 = m_message;
            _loc_10 = ASwingHelper.makeLabel(_loc_9,EmbeddedArt.defaultFontNameBold,20,EmbeddedArt.blueTextColor);
            _loc_11 = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.CENTER,0);
            AssetPane _loc_12 =new AssetPane(m_assetDict.get( "horizontalRule") );
            _loc_11.append(_loc_12);
            ASwingHelper.prepare(_loc_11);
            _loc_13 = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.CENTER,0);
            _loc_14 = ASwingHelper.makeMultilineText(ZLoc.t("Dialogs","TrainUI_Neighbors_subText"),300,EmbeddedArt.defaultFontNameBold,TextFormatAlign.CENTER,20,EmbeddedArt.brownTextColor);
            _loc_13.append(_loc_14);
            ASwingHelper.prepare(_loc_13);
            _loc_8.append(_loc_10);
            _loc_8.append(ASwingHelper.verticalStrut(20));
            _loc_8.append(_loc_11);
            _loc_8.append(ASwingHelper.verticalStrut(20));
            _loc_8.append(_loc_13);
            ASwingHelper.setEasyBorder(_loc_8, 0, 0, 0, 15);
            _loc_3.append(_loc_8);
            ASwingHelper.prepare(_loc_3);
            _loc_1.append(_loc_3);
            _loc_1.setBorder(new EmptyBorder(null, new Insets(0, 0, 0, 0)));
            if (m_type != TYPE_MODAL)
            {
                _loc_15 = this.createButtonPanel();
                _loc_1.append(ASwingHelper.verticalStrut(-10));
                _loc_1.append(_loc_15);
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




