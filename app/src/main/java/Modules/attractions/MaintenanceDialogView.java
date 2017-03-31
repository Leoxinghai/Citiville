package Modules.attractions;

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
import Display.*;
import Display.DialogUI.*;
import Display.aswingui.*;
import Engine.Managers.*;
import Events.*;
import Mechanics.*;
import Mechanics.GameEventMechanics.*;
import Mechanics.GameMechanicInterfaces.*;
import ZLocalization.*;
import com.zynga.skelly.util.*;
//import flash.display.*;
//import flash.events.*;
//import flash.text.*;
//import flash.utils.*;
import org.aswing.*;
import org.aswing.event.*;

    public class MaintenanceDialogView extends GenericDialogView
    {
        protected MechanicMapResource m_spawner ;
        protected String m_consumableName ;
        protected int m_numRequired ;
        protected int m_finishWithCash ;
        protected Function m_onAskFriends ;
        protected Function m_onUseConsumables ;
        protected Function m_onFinishWithCash ;
        protected JLabel m_quantityLabel ;
        protected JTextField m_timeLeft ;
        protected Timer m_sleepTimer ;
        protected Function m_currentLeftButtonAction ;
        protected CustomButton m_leftButton ;
        protected CustomButton m_cashButton ;
        protected boolean m_finish =false ;
        protected boolean m_finishedWithCash =false ;
        protected CheckBoxComponent m_autoFeedCheckBox ;
        protected String m_customRewardIcon ;
        protected String m_customRewardCaption ;
public static  int TIMER_UPDATE_DELAY =1000;

        public  MaintenanceDialogView (MechanicMapResource param1 ,String param2 ,int param3 ,int param4 ,Function param5 ,Function param6 ,Function param7 ,Dictionary param8 ,String param9 ,String param10 ="",String param11 ="",String param12 ="")
        {
            this.m_spawner = param1;
            this.m_consumableName = param2;
            this.m_numRequired = param3;
            this.m_finishWithCash = param4;
            this.m_onAskFriends = param5;
            this.m_onUseConsumables = param6;
            this.m_onFinishWithCash = param7;
            this.m_customRewardIcon = param11;
            this.m_customRewardCaption = param12;
            param9 = param9;
            super(param8, param10, param9);
            return;
        }//end

         protected void  makeCenterPanel ()
        {
            this.initBgAsset();
            this.setLayout(new SoftBoxLayout(SoftBoxLayout.Y_AXIS, 0, SoftBoxLayout.TOP));
            _loc_1 = createHeaderPanel();
            _loc_1.name = "headerPanel";
            this.append(_loc_1);
            this.append(this.makeMiddlePanel());
            this.append(ASwingHelper.verticalStrut(3));
            this.append(this.makeInfoPanel());
            this.append(ASwingHelper.verticalStrut(1));
            this.append(this.createButtonPanel());
            ASwingHelper.prepare(this);
            return;
        }//end

        protected void  initBgAsset ()
        {
            m_bgAsset =(DisplayObject) new m_assetDict.get("attractions_moduleBG");
            ASwingHelper.setSizedBackground(this, m_bgAsset, new Insets(0, 0, 20));
            this.setPreferredHeight(m_bgAsset.height + 54);
            this.setMaximumHeight(m_bgAsset.height + 54);
            return;
        }//end

        protected void  addCharacterAsset ()
        {
            _loc_1 =(DisplayObject) m_assetDict.get("attractions_citySam");
            _loc_1.x = 10;
            _loc_1.y = 50;
            this.addChild(_loc_1);
            return;
        }//end

        protected JPanel  makeMiddlePanel ()
        {
            this.addCharacterAsset();
            _loc_1 = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.TOP );
            _loc_1.name = "top_panel";
            _loc_1.appendAll(this.makeSpeechPanel());
            return _loc_1;
        }//end

        protected JPanel  makeSpeechPanel ()
        {
            _loc_1 = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.LEFT );
            _loc_1.name = "speech_panel";
            DisplayObject _loc_2 =(DisplayObject)new m_assetDict.get( "attractions_dialogBox");
            ASwingHelper.setBackground(_loc_1, _loc_2);
            _loc_1.setPreferredHeight(150);
            ASwingHelper.setEasyBorder(_loc_1, 0, 95, 0, 10);
            _loc_3 = ZLoc.t("Dialogs",m_message );
            int _loc_4 =285;
            _loc_5 = EmbeddedArt.defaultFontNameBold;
            _loc_6 = TextFormatAlign.LEFT;
            int _loc_7 =16;
            _loc_8 = EmbeddedArt.darkBlueTextColor;
            _loc_9 = ASwingHelper.makeMultilineText(_loc_3 ,_loc_4 ,_loc_5 ,_loc_6 ,_loc_7 ,_loc_8 );
            ASwingHelper.setEasyBorder(_loc_9, 10, 0, 0, 0);
            DisplayObject _loc_10 =(DisplayObject)new m_assetDict.get( "attractions_dialogBoxDivider");
            (new m_assetDict.get("attractions_dialogBoxDivider")).x = 410;
            _loc_10.y = 20;
            _loc_1.addChild(_loc_10);
            DisplayObject _loc_11 =(DisplayObject)new m_assetDict.get( "attractions_diamondImage");
            this.setupRewardIcon(_loc_11);
            if (this.m_customRewardIcon)
            {
                _loc_11 = LoadingManager.load(Global.getAssetURL(this.m_customRewardIcon), this.onRewardIconLoaded);
            }
            _loc_1.addChild(_loc_11);
            _loc_12 = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.TOP );
            _loc_13 = ZLoc.t("Dialogs","maintenance_dialog_caption");
            if (this.m_customRewardCaption)
            {
                _loc_13 = ZLoc.t("Dialogs", this.m_customRewardCaption);
            }
            _loc_14 = ASwingHelper.makeTextField(_loc_13 ,EmbeddedArt.titleFont ,16,EmbeddedArt.titleColor ,3);
            _loc_14.filters = EmbeddedArt.titleFilters;
            TextFormat _loc_15 =new TextFormat ();
            _loc_15.size = 14;
            _loc_15.align = TextFormatAlign.CENTER;
            TextFieldUtil.formatSmallCaps(_loc_14.getTextField(), _loc_15);
            _loc_12.appendAll(_loc_14);
            _loc_12.setPreferredWidth(200);
            ASwingHelper.setEasyBorder(_loc_12, 10, 0, 0, 10);
            _loc_1.appendAll(ASwingHelper.horizontalStrut(25), _loc_9, ASwingHelper.horizontalStrut(5), _loc_12);
            return _loc_1;
        }//end

        protected void  onRewardIconLoaded (Event event )
        {
            _loc_2 =(LoaderInfo) event.target;
            if (_loc_2)
            {
                this.setupRewardIcon(_loc_2.loader);
            }
            return;
        }//end

        protected void  setupRewardIcon (DisplayObject param1 )
        {
            if (param1 !=null)
            {
                param1.x = 420;
                param1.y = 40;
            }
            return;
        }//end

        protected JPanel  makeInfoPanel ()
        {
            _loc_1 = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.CENTER );
            _loc_1.name = "info_panel";
            DisplayObject _loc_2 =(DisplayObject)new assetDict.get( "attractions_whiteBottomBox");
            ASwingHelper.setBackground(_loc_1, _loc_2);
            _loc_1.setPreferredHeight(187);
            ASwingHelper.setEasyBorder(_loc_1, 0, 10, 0, 10);
            _loc_1.append(this.getLeftBoxPanel());
            _loc_1.append(this.getOrTextPanel());
            _loc_1.append(this.getRightBoxPanel());
            return _loc_1;
        }//end

        protected JPanel  getLeftBoxPanel ()
        {
            JPanel wrenchPanel ;
            String consumableIconUrl ;
            Loader consumableLoader ;
            leftBoxPanel = this.getBoxPanel();
            collectTxt = ZLoc.t("Dialogs","maintenance_dialog_collect");
            collectTextPanel = this.getTextPanel(collectTxt);
            collectTextPanel.setPreferredWidth(230);
            ASwingHelper.setEasyBorder(collectTextPanel, 5, 0, 0, 0);
            leftBoxPanel.append(collectTextPanel);
            leftBoxPanel.append(ASwingHelper.verticalStrut(-7));
            wrenchBoxPanel = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.CENTER);
            DisplayObject wrenchBoxAsset =(DisplayObject)new m_assetDict.get( "attractions_wrenchDetailBox");
            ASwingHelper.setBackground(wrenchBoxPanel, wrenchBoxAsset);
            wrenchBoxPanel.setPreferredWidth(95);
            wrenchBoxPanel.setPreferredHeight(100);
            ASwingHelper.setEasyBorder(wrenchBoxPanel, 0, 67, 5, 67);
            leftBoxPanel.append(wrenchBoxPanel);
            wrenchPanel = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.CENTER);
            wrenchBoxPanel.append(ASwingHelper.horizontalStrut(-60));
            wrenchBoxPanel.append(wrenchPanel);
            consumableItem = Global.gameSettings().getItemByName(this.m_consumableName);
            if (consumableItem)
            {
                consumableIconUrl = consumableItem.getImageByName("maintenance_icon");
                consumableLoader =LoadingManager .load (consumableIconUrl ,Curry .curry (void  (JPanel param1 ,Event param2 )
            {
                AssetPane _loc_3 =new AssetPane(consumableLoader );
                ASwingHelper.setEasyBorder(_loc_3, 5);
                wrenchPanel.append(_loc_3);
                ASwingHelper.prepare(param1);
                param1.dispatchEvent(new UIEvent(UIEvent.REFRESH_DIALOG, "iconLoaded", true));
                return;
            }//end
            , this));
            }
            leftBoxPanel.append(ASwingHelper.verticalStrut(-18));
            numConsumables = Math.min(this.m_spawner.getDataForMechanic("consumables"),this.m_numRequired);
            Object locParams ;
            locParams.put("curr",  numConsumables);
            locParams.put("max",  this.m_numRequired);
            quantityText = ZLoc.t("Dialogs","maintenance_dialog_curr_max",locParams);
            this.m_quantityLabel = ASwingHelper.makeLabel(quantityText, EmbeddedArt.titleFont, 16, EmbeddedArt.whiteTextColor, JLabel.CENTER);
            ASwingHelper.setEasyBorder(this.m_quantityLabel, -1, 0, 0, 0);
            labelPanel = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.CENTER);
            Sprite labelSprite =new Sprite ();
            labelSprite.graphics.beginFill(EmbeddedArt.darkBlueTextColor);
            labelSprite.graphics.drawRoundRect(0, 0, 70, 20, 10, 8);
            AssetPane labelAP =new AssetPane(labelSprite );
            labelPanel.append(labelAP);
            labelPanel.append(ASwingHelper.horizontalStrut(-(this.m_quantityLabel.getPreferredWidth() + (labelSprite.width / 2 - this.m_quantityLabel.getPreferredWidth() / 2))));
            labelPanel.append(this.m_quantityLabel);
            labelPanel.setPreferredWidth(70);
            leftBoxPanel.append(labelPanel);
            return leftBoxPanel;
        }//end

        protected JPanel  getRightBoxPanel ()
        {
            _loc_1 = this.getBoxPanel ();
            _loc_2 = ZLoc.t("Dialogs","maintenance_dialog_ready_in");
            _loc_3 = this.getTextPanel(_loc_2 );
            _loc_3.setPreferredWidth(230);
            ASwingHelper.setEasyBorder(_loc_3, 5, 0, 0, 0);
            _loc_1.append(_loc_3);
            _loc_1.append(ASwingHelper.verticalStrut(10));
            _loc_4 = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.CENTER );
            DisplayObject _loc_5 =(DisplayObject)new m_assetDict.get( "attractions_clockDetailBg");
            ASwingHelper.setBackground(_loc_4, _loc_5);
            _loc_4.setPreferredWidth(178);
            _loc_4.setPreferredHeight(55);
            ASwingHelper.setEasyBorder(_loc_4, 0, 25, 0, 25);
            _loc_1.append(_loc_4);
            _loc_6 = this.getSleepTimeLeft ();
            _loc_7 = GameUtil.formatMinutesSeconds(_loc_6);
            _loc_8 = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.TOP );
            this.m_timeLeft = ASwingHelper.makeTextField(_loc_7, EmbeddedArt.titleFont, 20, EmbeddedArt.whiteTextColor, 3);
            TextFormat _loc_9 =new TextFormat ();
            _loc_9.size = 20;
            _loc_9.align = TextFormatAlign.RIGHT;
            _loc_8.appendAll(this.m_timeLeft);
            _loc_8.setPreferredWidth(130);
            ASwingHelper.setEasyBorder(_loc_8, 12, 0, 0, 0);
            _loc_4.append(ASwingHelper.horizontalStrut(20));
            _loc_4.append(_loc_8);
            this.m_timeLeft.getTextField().text = _loc_7;
            this.initializeTimer(_loc_6);
            return _loc_1;
        }//end

        protected double  getSleepTimeLeft ()
        {
            return this.m_spawner.getDataForMechanic("sleepTimeLeft");
        }//end

        protected void  initializeTimer (double param1 )
        {
            if (param1 < 0)
            {
                this.timerComplete();
            }
            else
            {
                this.m_sleepTimer = new Timer(TIMER_UPDATE_DELAY);
                this.m_sleepTimer.addEventListener(TimerEvent.TIMER, this.updateTimerString);
                this.m_sleepTimer.start();
            }
            return;
        }//end

        protected JPanel  getBoxPanel ()
        {
            _loc_1 = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.TOP );
            DisplayObject _loc_2 =(DisplayObject)new m_assetDict.get( "attractions_blueBox");
            ASwingHelper.setBackground(_loc_1, _loc_2);
            _loc_1.setPreferredWidth(230);
            _loc_1.setPreferredHeight(135);
            ASwingHelper.setEasyBorder(_loc_1, 15, 0, 30, 0);
            return _loc_1;
        }//end

        protected JPanel  getOrTextPanel ()
        {
            _loc_1 = ZLoc.t("Dialogs","maintenance_dialog_or");
            _loc_2 = this.getTextPanel(_loc_1 );
            _loc_2.setPreferredWidth(70);
            ASwingHelper.setEasyBorder(_loc_2, 70, 0, 0, 0);
            return _loc_2;
        }//end

        protected JPanel  getTextPanel (String param1 )
        {
            _loc_2 = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.TOP );
            _loc_3 = param1;
            _loc_4 = ASwingHelper.makeTextField(_loc_3 ,EmbeddedArt.titleFont ,16,EmbeddedArt.darkBlueTextColor ,3);
            TextFormat _loc_5 =new TextFormat ();
            _loc_5.size = 20;
            _loc_5.align = TextFormatAlign.CENTER;
            TextFieldUtil.formatSmallCaps(_loc_4.getTextField(), _loc_5);
            _loc_2.appendAll(_loc_4);
            return _loc_2;
        }//end

         protected JPanel  createButtonPanel ()
        {
            _loc_1 = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.TOP ,0);
            _loc_2 = ZLoc.t("Dialogs","maintenance_dialog_ask_friends");
            this.m_leftButton = new CustomButton(_loc_2, null, "GreenButtonUI");
            this.m_leftButton.setFont(new ASFont(EmbeddedArt.titleFont, 18, false, false, false, EmbeddedArt.advancedFontProps));
            this.m_leftButton.addActionListener(this.onLeftButtonClick, 0, true);
            _loc_3 = this.m_leftButton.getPreferredHeight ();
            _loc_4 = ASwingHelper.makeFlowJPanel(FlowLayout.CENTER ,10);
            _loc_5 = ASwingHelper.makeFlowJPanel(FlowLayout.CENTER ,10);
            _loc_5.append(this.m_leftButton);
            _loc_5.append(ASwingHelper.horizontalStrut(2));
            _loc_6 = ZLoc.t("Dialogs","maintenance_dialog_finish_with_cash",{amount this.m_finishWithCash });
            AssetIcon _loc_7 =new AssetIcon(new EmbeddedArt.icon_cash ()as DisplayObject );
            this.m_cashButton = new CustomButton(_loc_6, _loc_7, "CashButtonUI");
            this.m_cashButton.setFont(new ASFont(EmbeddedArt.titleFont, 18, false, false, false, EmbeddedArt.advancedFontProps));
            this.m_cashButton.addActionListener(this.onFinishWithCashClick, 0, true);
            this.m_cashButton.setPreferredHeight(_loc_3);
            _loc_5.append(this.m_cashButton);
            _loc_8 =Global.world.viralMgr.getGlobalPublishStreamCheck(this.m_spawner.getItemName ());
            DisplayObject _loc_9 =(DisplayObject)new m_assetDict.get( "maintenance_checkmark");
            DisplayObject _loc_10 =(DisplayObject)new m_assetDict.get( "maintenance_checkbox");
            this.m_autoFeedCheckBox = new CheckBoxComponent(_loc_8, ZLoc.t("Dialogs", "maintenance_dialog_checkbox"), this.toggleAutoFeedCheckBox, _loc_9, _loc_10);
            this.m_autoFeedCheckBox.setLabelColor(EmbeddedArt.whiteTextColor);
            _loc_4.append(this.m_autoFeedCheckBox);
            this.refreshButtonPanel();
            _loc_1.append(_loc_4);
            _loc_1.append(_loc_5);
            return _loc_1;
        }//end

        protected void  toggleAutoFeedCheckBox (boolean param1 )
        {
            Global.world.viralMgr.setGlobalPublishStreamCheck(param1, this.m_spawner.getItemName());
            return;
        }//end

        protected void  refreshButtonPanel ()
        {
            String _loc_6 =null ;
            String _loc_7 =null ;
            String _loc_8 =null ;
            _loc_1 = ZLoc.tk("Items",this.m_consumableName ,"",this.m_numRequired );
            Object _loc_2 ={};
            _loc_2.put("consumable_name",  _loc_1);
            _loc_3 = this.m_spawner.getDataForMechanic("consumables");
            _loc_4 = MechanicManager.getInstance().getMechanicInstance(this.m_spawner,"consumables","all");
            _loc_5 = (MaintenanceMechanic)MechanicManager.getInstance().getMechanicInstance(this.m_spawner,"consumables","all")
            if (MechanicManager.getInstance().getMechanicInstance(this.m_spawner, "consumables", "all") as MaintenanceMechanic && !_loc_5.isSleeping() || this.m_finish)
            {
                _loc_6 = ZLoc.t("Dialogs", "maintenance_dialog_finish");
                this.m_leftButton.setText(_loc_6);
                this.m_currentLeftButtonAction = this.onFinishClick;
                this.m_cashButton.setEnabled(false);
            }
            else if (_loc_3 >= this.m_numRequired)
            {
                _loc_7 = ZLoc.t("Dialogs", "maintenance_dialog_use_consumables", _loc_2);
                _loc_7 = _loc_7.toUpperCase();
                this.m_leftButton.setText(_loc_7);
                this.m_currentLeftButtonAction = this.onUseConsumablesClick;
            }
            else
            {
                _loc_8 = ZLoc.t("Dialogs", "maintenance_dialog_ask_friends");
                this.m_leftButton.setText(_loc_8);
                this.m_currentLeftButtonAction = this.onAskFriendsClick;
            }
            ASwingHelper.prepare(this.m_leftButton);
            return;
        }//end

        protected void  onLeftButtonClick (AWEvent event )
        {
            this.m_currentLeftButtonAction(event);
            return;
        }//end

        protected void  onAskFriendsClick (AWEvent event )
        {
            String _loc_3 =null ;
            GenericDialog _loc_4 =null ;
            this.countDialogViewAction("get_keys");
            _loc_2 =Global.world.viralMgr.sendAutoConsumableFeed(this.m_spawner.getItemName (),this.m_spawner.getId (),this.m_spawner.getClassName ());
            if (!_loc_2)
            {
                _loc_3 = ZLoc.t("Dialogs", "ExpansionThrottlingMessage");
                _loc_4 = new GenericDialog(_loc_3);
                UI.displayPopup(_loc_4, false, "alreadyAsked");
            }
            else
            {
                close();
            }
            return;
        }//end

         public void  countDialogViewAction (String param1 ="view",boolean param2 =true ,int param3 =1,String param4 ="",String param5 ="",String param6 ="")
        {
            super.countDialogViewAction(param1, param2, param3, "wonder", this.m_spawner.getItemName(), "get_keys");
            return;
        }//end

        protected void  onUseConsumablesClick (AWEvent event )
        {
            this.m_onUseConsumables(event);
            this.refreshButtonPanel();
            return;
        }//end

        protected void  onFinishWithCashClick (AWEvent event )
        {
            this.m_onFinishWithCash(event);
            this.m_finishedWithCash = true;
            Object _loc_2 ={};
            _loc_2.put("curr",  this.m_numRequired);
            _loc_2.put("max",  this.m_numRequired);
            _loc_3 = ZLoc.t("Dialogs","maintenance_dialog_curr_max",_loc_2 );
            this.m_quantityLabel.setText(_loc_3);
            this.refreshButtonPanel();
            return;
        }//end

        protected void  onFinishClick (AWEvent event )
        {
            close();
            return;
        }//end

         protected Object  getCloseButtonBorder ()
        {
            Object _loc_1 =new Object ();
            _loc_1.put("top",  4);
            _loc_1.put("left",  3);
            _loc_1.put("bottom",  1);
            _loc_1.put("right",  5);
            return _loc_1;
        }//end

        protected void  updateTimerString (TimerEvent event )
        {
            _loc_2 = this.getSleepTimeLeft ();
            _loc_3 = GameUtil.formatMinutesSeconds(_loc_2);
            this.m_timeLeft.getTextField().text = _loc_3;
            if (_loc_2 <= 0)
            {
                this.timerComplete();
                this.m_sleepTimer.removeEventListener(TimerEvent.TIMER, this.updateTimerString);
            }
            return;
        }//end

        protected void  timerComplete ()
        {
            this.m_finish = true;
            this.refreshButtonPanel();
            return;
        }//end

    }



