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
import org.aswing.geom.*;

    public class NewsFlashDialogView extends GenericDialogView
    {
        protected Bitmap m_imageURL ;
        protected AssetPane m_imageAsset ;
        protected String m_subheader ;
        private static  int SUBHEADER_WIDTH =440;
        private static  int SUBHEADER_HEIGHT =54;
        private static  int MESSAGE_WIDTH =420;
        private static  int MESSAGE_HEIGHT =55;
        private static  int TEXT_FIELD_END_GAP =30;

        public  NewsFlashDialogView (Dictionary param1 ,String param2 ="",String param3 ="",String param4 ="",int param5 =0,Function param6 =null )
        {
            this.m_subheader = param4;
            super(param1, param2, param3, param5, param6);
            this.setLayout(new SoftBoxLayout(SoftBoxLayout.X_AXIS, 0, SoftBoxLayout.CENTER));
            return;
        }//end  

         protected void  init ()
        {
            m_bgAsset = m_assetDict.get("bg");
            this.m_imageURL = m_assetDict.get("imageURL");
            makeCenterPanel();
            this.makeBackground();
            ASwingHelper.prepare(this);
            return;
        }//end  

         protected void  makeBackground ()
        {
            AssetBackground _loc_1 =new AssetBackground(m_bgAsset );
            this.setBackgroundDecorator(_loc_1);
            this.setPreferredSize(new IntDimension(m_bgAsset.width, m_bgAsset.height));
            this.setMinimumSize(new IntDimension(m_bgAsset.width, m_bgAsset.height));
            this.setMaximumSize(new IntDimension(m_bgAsset.width, m_bgAsset.height));
            return;
        }//end  

         protected JPanel  createInteriorHolder ()
        {
            JPanel _loc_1 =null ;
            _loc_1 = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.TOP);
            _loc_1.setPreferredSize(new IntDimension(m_bgAsset.width, m_bgAsset.height + 50));
            _loc_2 = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.CENTER);
            _loc_3 = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.CENTER);
            _loc_4 = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.RIGHT);
            _loc_5 = ASwingHelper.makeMarketCloseButton();
            _loc_4.append(_loc_5);
            _loc_5.addActionListener(onCancelX, 0, true);
            TextFormat _loc_6 =new TextFormat ();
            _loc_6.bold = true;
            _loc_1.append(_loc_4);
            _loc_3.setMinimumHeight(SUBHEADER_HEIGHT + MESSAGE_HEIGHT);
            _loc_3.setPreferredHeight(SUBHEADER_HEIGHT + MESSAGE_HEIGHT);
            _loc_3.setMaximumHeight(SUBHEADER_HEIGHT + MESSAGE_HEIGHT);
            _loc_7 = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.CENTER);
            _loc_8 = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.BOTTOM);
            _loc_9 = ASwingHelper.makeMultilineText(this.m_subheader,SUBHEADER_WIDTH,EmbeddedArt.defaultSerifFont,TextFormatAlign.CENTER,29,EmbeddedArt.darkGreyTextColor,null,true);
            _loc_10 = (TextField)ASwingHelper.makeMultilineText(this.m_subheader,SUBHEADER_WIDTH,EmbeddedArt.defaultSerifFont,TextFormatAlign.CENTER,29,EmbeddedArt.darkGreyTextColor,null,true).getAsset()
            (ASwingHelper.makeMultilineText(this.m_subheader, SUBHEADER_WIDTH, EmbeddedArt.defaultSerifFont, TextFormatAlign.CENTER, 29, EmbeddedArt.darkGreyTextColor, null, true).getAsset() as TextField).setTextFormat(_loc_6);
            TextFieldUtil.autosize(_loc_10, SUBHEADER_WIDTH - TEXT_FIELD_END_GAP, SUBHEADER_HEIGHT);
            _loc_9.setPreferredHeight(_loc_10.height);
            _loc_9.setPreferredWidth(440);
            ASwingHelper.setEasyBorder(_loc_8, 0, 0, 0, 15);
            _loc_8.append(_loc_9);
            _loc_7.append(_loc_8);
            TextFormat _loc_11 =new TextFormat ();
            _loc_11.align = TextFormatAlign.CENTER;
            _loc_12 = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.CENTER);
            _loc_13 = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.TOP);
            _loc_14 = ASwingHelper.makeTextField(m_message,EmbeddedArt.defaultSerifFont,21,EmbeddedArt.darkGreyTextColor,0,_loc_11);
            TextFieldUtil.autosize(_loc_14.getTextField(), MESSAGE_WIDTH - TEXT_FIELD_END_GAP, MESSAGE_HEIGHT);
            _loc_14.setPreferredWidth(420);
            ASwingHelper.setEasyBorder(_loc_13, 0, 0, 0, 15);
            _loc_13.append(_loc_14);
            _loc_12.append(_loc_13);
            ASwingHelper.prepare(_loc_14);
            ASwingHelper.prepare(_loc_9);
            _loc_15 = Math.max(8,80-(_loc_14.getTextField().textHeight+_loc_9.getHeight()));
            _loc_3.append(ASwingHelper.verticalStrut(_loc_15));
            _loc_3.append(_loc_7);
            _loc_3.append(_loc_12);
            ASwingHelper.prepare(_loc_3);
            _loc_2.append(_loc_3);
            _loc_2.setMinimumHeight(125);
            _loc_2.setPreferredHeight(125);
            _loc_2.setMaximumHeight(125);
            _loc_1.append(ASwingHelper.verticalStrut(10));
            _loc_1.append(_loc_2);
            this.m_imageAsset = new AssetPane(this.m_imageURL);
            this.m_imageAsset.setBorder(new EmptyBorder(null, new Insets(0, 8, 0, 0)));
            _loc_1.append(this.m_imageAsset);
            m_acceptTextName = ZLoc.t("Dialogs", "ShareTheNews");
            _loc_16 = super.createButtonPanel();
            _loc_1.append(ASwingHelper.verticalStrut(-20));
            _loc_1.append(_loc_16);
            ASwingHelper.prepare(_loc_1);
            return _loc_1;
        }//end  

    }




