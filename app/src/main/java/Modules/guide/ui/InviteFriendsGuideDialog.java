package Modules.guide.ui;

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
import Classes.util.*;
import Display.DialogUI.*;
import Display.aswingui.*;
import Engine.Managers.*;
import Events.*;
//import flash.display.*;
//import flash.events.*;
//import flash.text.*;
import org.aswing.*;
import org.aswing.border.*;
import org.aswing.event.*;

    public class InviteFriendsGuideDialog extends WelcomeDialog
    {

        public  InviteFriendsGuideDialog (String param1 ,String param2 ="",int param3 =0,Function param4 =null ,boolean param5 =true ,String param6 ="",String param7 ="",Function param8 =null )
        {
            super(param1, param2, param3, WelcomeDialog.POS_CENTER, param4, param5, param6, param7, param8);
            return;
        }//end

         protected JPanel  createButtonPanel ()
        {
            CustomButton _loc_2 =null ;
            _loc_1 = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.CENTER );
            if (m_SkipCallback != null)
            {
                _loc_2 = new CustomButton(ZLoc.t("Dialogs", "Skip"), null, "GreyButtonUI");
                _loc_2.setPreferredWidth(100);
                _loc_2.setMinimumWidth(100);
                _loc_2.setMaximumWidth(100);
                _loc_2.addActionListener(this.onSkip, 0, true);
                _loc_1.append(_loc_2);
                _loc_1.append(ASwingHelper.horizontalStrut(5));
            }
            m_button = new CustomButton(ZLoc.t("Dialogs", "InviteNeighbors"), null, "GreenButtonUI");
            m_button.addActionListener(this.onPanelClick, 0, true);
            _loc_1.append(m_button);
            return _loc_1;
        }//end

        protected void  onSkip (Object param1)
        {
            countDialogAction("X");
            GenericPopupEvent _loc_2 =new GenericPopupEvent(GenericPopupEvent.SELECTED ,GenericDialogView.SKIP ,true );
            dispatchEvent(_loc_2);
            if (m_SkipCallback != null)
            {
                removeEventListener(GenericPopupEvent.SELECTED, m_SkipCallback);
            }
            super.closeMe(new Event("close", true));
            return;
        }//end

         protected int  getTfWidth (String param1 ,String param2 )
        {
            return Math.max(300, param1.length * 1.5, param2.length * 1.5);
        }//end

         protected void  onPanelClick (AWEvent event )
        {
            FrameManager.showTray("invite.php?ref=ftue_neighbor_prompt");
            super.onPanelClick(event);
            return;
        }//end

         protected void  onAssetsLoaded (Event event =null )
        {
            AssetPane _loc_10 =null ;
            _loc_2 = m_loaderObj;
            m_advisor = m_avatarContent;
            m_speech =(DisplayObject) new _loc_2.speechBubbleLeft();
            m_jpanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
            _loc_3 = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.CENTER );
            _loc_4 = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.CENTER );
            _loc_3.setBackgroundDecorator(new MarginBackground(m_speech, new Insets(20, 0, 10, 0)));
            _loc_5 = this.getTfWidth(m_message ,m_dialogTitle );
            _loc_6 = this.createCloseButtonPanel ();
            _loc_7 = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.RIGHT );
            _loc_7.append(_loc_6);
            if (m_dialogTitle)
            {
                _loc_10 = ASwingHelper.makeMultilineCapsText(m_dialogTitle, _loc_5, EmbeddedArt.titleFont, TextFormatAlign.LEFT, 16, EmbeddedArt.darkBlueTextColor, null, true);
                _loc_4.append(_loc_10);
            }
            _loc_8 = ASwingHelper.makeMultilineText(m_message ,_loc_5 ,EmbeddedArt.defaultFontNameBold ,TextFormatAlign.LEFT ,16,EmbeddedArt.brownTextColor ,null ,true );
            _loc_4.appendAll(_loc_8, ASwingHelper.verticalStrut(10));
            _loc_4.setBorder(new EmptyBorder(null, new Insets(10, 50, 0, 25)));
            _loc_9 = this.createButtonPanel ();
            _loc_4.append(_loc_9);
            _loc_3.append(ASwingHelper.verticalStrut(15));
            _loc_3.append(_loc_7, BorderLayout.EAST);
            _loc_3.append(ASwingHelper.verticalStrut(-18));
            _loc_3.append(_loc_4);
            ASwingHelper.prepare(_loc_3);
            m_jpanel.append(_loc_3);
            this.finalizeAndShow();
            return;
        }//end

        protected JPanel  createCloseButtonPanel ()
        {
            JPanel _loc_1 =new JPanel(ASwingHelper.softBoxLayoutVertical );
            _loc_2 = ASwingHelper.makeMarketCloseButton ();
            _loc_2.addEventListener(MouseEvent.CLICK, this.onSkip, false, 0, true);
            _loc_1.append(_loc_2);
            ASwingHelper.setEasyBorder(_loc_2, 0, 0, 0, 0);
            return _loc_1;
        }//end

         protected void  finalizeAndShow ()
        {
            StatsManager.milestone("first_invite_friends");
            super.finalizeAndShow();
            return;
        }//end

    }



