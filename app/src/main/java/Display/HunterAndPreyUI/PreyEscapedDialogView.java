package Display.HunterAndPreyUI;

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
import Display.DialogUI.*;
import Display.aswingui.*;
import Events.*;
import Modules.bandits.*;
//import flash.display.*;
//import flash.filters.*;
//import flash.text.*;
//import flash.utils.*;
import org.aswing.*;
import org.aswing.border.*;
import org.aswing.event.*;

    public class PreyEscapedDialogView extends GenericDialogView
    {
        protected PreyData m_prey ;
        protected boolean m_moreCops ;
        public static  int TEXT_WIDTH =400;

        public  PreyEscapedDialogView (Dictionary param1 ,PreyData param2 ,boolean param3 =false ,String param4 ="",String param5 ="",Function param6 =null ,Function param7 =null )
        {
            this.m_prey = param2;
            this.m_moreCops = param3;
            super(param1, param4, param5, TYPE_OK, param6, "", 0, "", param7);
            return;
        }//end

         protected void  init ()
        {
            super.init();
            PreyUtil.logDialogStats(this.m_prey.groupId, "view", "prey_escaped_ui", this.m_prey);
            return;
        }//end

         protected JPanel  createInteriorHolder ()
        {
            JPanel _loc_1 =null ;
            JPanel _loc_2 =null ;
            _loc_1 = new JPanel(new SoftBoxLayout(SoftBoxLayout.Y_AXIS, -10, AsWingConstants.CENTER));
            _loc_2 = createHeaderPanel();
            ASwingHelper.prepare(_loc_2);
            textArea = this.createTextArea();
            ASwingHelper.prepare(textArea);
            _loc_1.setBorder(new EmptyBorder(null, new Insets(0, 0, 0, 0)));
            _loc_1.append(_loc_2);
            _loc_1.append(ASwingHelper.verticalStrut(20));
            _loc_1.append(textArea);
            _loc_1.append(ASwingHelper.verticalStrut(BUTTON_MARGIN));
            _loc_3 = this.createButtonPanel ();
            _loc_1.append(_loc_3);
            ASwingHelper.prepare(_loc_1);
            return _loc_1;
        }//end

         protected JPanel  createTextArea ()
        {
            _loc_1 = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.CENTER ,10);
            _loc_2 = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.CENTER );
            _loc_3 = ASwingHelper.makeMultilineText(m_message ,TEXT_WIDTH ,EmbeddedArt.defaultFontNameBold ,TextFormatAlign.CENTER ,14,EmbeddedArt.brownTextColor );
            _loc_2.append(_loc_3);
            _loc_1.append(_loc_2);
            _loc_4 = this.createBanditPanel ();
            _loc_1.append(_loc_4);
            return _loc_1;
        }//end

        protected JPanel  createBanditPanel ()
        {
            _loc_1 = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.CENTER ,10);
            _loc_2 = m_assetDict.get("preyIcon") ;
            AssetPane _loc_3 =new AssetPane(_loc_2 );
            _loc_4 = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.TOP ,5);
            _loc_5 = ASwingHelper.makeMultilineCapsText(this.m_prey.name ,250,EmbeddedArt.titleFont ,TextFormatAlign.LEFT ,15,EmbeddedArt.darkBlueTextColor );
            _loc_6 = ASwingHelper.makeMultilineText(this.m_prey.description ,250,EmbeddedArt.defaultFontNameBold ,TextFormatAlign.LEFT ,14,EmbeddedArt.blueTextColor );
            _loc_4.appendAll(_loc_5, _loc_6);
            _loc_1.appendAll(ASwingHelper.horizontalStrut(10), _loc_3, _loc_4);
            return _loc_1;
        }//end

         protected JPanel  createButtonPanel ()
        {
            Object _loc_2 =null ;
            int _loc_3 =0;
            CustomButton _loc_4 =null ;
            AssetIcon _loc_5 =null ;
            CustomButton _loc_6 =null ;
            CustomButton _loc_7 =null ;
            JPanel _loc_1 =new JPanel(new FlowLayout(AsWingConstants.CENTER ,10));
            if (!this.m_moreCops)
            {
                m_cancelTextName = ZLoc.t("Dialogs", this.m_prey.groupId + "_escaped_moreResources_buttonText");
                _loc_2 = Global.gameSettings().getHubQueueInfo(this.m_prey.groupId);
                _loc_3 = _loc_2.get("catchPreyNowCashCost");
                m_acceptTextName = ZLoc.t("Dialogs", this.m_prey.groupId + "_escaped_moreResources_cashButtonText", {cash:_loc_3});
                _loc_4 = new CustomButton(m_cancelTextName, null, "GreenButtonUI");
                _loc_4.setMargin(new Insets(5, 10, 5, 10));
                _loc_5 = new AssetIcon((DisplayObject)new EmbeddedArt.icon_cash());
                _loc_6 = new CustomButton(m_acceptTextName, _loc_5, "CashButtonUI");
                _loc_6.setMargin(new Insets(5, 10, 5, 10));
                _loc_6.setFont(new ASFont(EmbeddedArt.titleFont, 18, false, false, false, EmbeddedArt.getAdvancedFontProps(EmbeddedArt.titleFont)));
                _loc_6.setTextFilters(.get(new DropShadowFilter(1, 90, 0, 0.2, 3, 3, 3, 1), new GlowFilter(0, 0.2, 4, 4, 10)));
                _loc_4.addActionListener(this.onRequestForDonuts, 0, true);
                _loc_6.addActionListener(this.onAccept, 0, true);
                _loc_1.appendAll(_loc_6, _loc_4);
            }
            else
            {
                m_acceptTextName = ZLoc.t("Dialogs", this.m_prey.groupId + "_escaped_moreHunters_buttonText");
                _loc_7 = new CustomButton(m_acceptTextName, null, "GreenButtonUI");
                _loc_7.addActionListener(this.onAccept, 0, true);
                _loc_1.append(_loc_7);
            }
            return _loc_1;
        }//end

        protected void  onRequestForDonuts (AWEvent event )
        {
            if (m_closeCallback != null)
            {
                m_closeCallback(new GenericPopupEvent(GenericPopupEvent.SELECTED, NO, false));
            }
            PreyUtil.logDialogStats(this.m_prey.groupId, "get_resources", "prey_escaped_ui", this.m_prey);
            if (!Global.world.viralMgr.sendGetHunterResource(this.m_prey.groupId))
            {
                UI.displayMessage(ZLoc.t("Dialogs", this.m_prey.groupId + "_HunterFeedMax"), GenericDialogView.TYPE_OK);
            }
            onSkip(event);
            return;
        }//end

         protected void  onAccept (AWEvent event )
        {
            super.onAccept(event);
            if (this.m_moreCops)
            {
                PreyUtil.logDialogStats(this.m_prey.groupId, "hire_hunters", "prey_escaped_ui", this.m_prey);
            }
            else
            {
                PreyUtil.logDialogStats(this.m_prey.groupId, "capture_now", "prey_escaped_ui", this.m_prey);
            }
            return;
        }//end

         protected void  onCancelX (Object param1)
        {
            super.onCancelX(param1);
            PreyUtil.logDialogStats(this.m_prey.groupId, "X", "prey_escaped_ui", this.m_prey);
            return;
        }//end

    }



