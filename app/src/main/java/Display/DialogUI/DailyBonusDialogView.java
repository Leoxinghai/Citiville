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
import Engine.Managers.*;
import Modules.stats.types.*;
//import flash.display.*;
//import flash.events.*;
//import flash.filters.*;
//import flash.text.*;
//import flash.utils.*;
import org.aswing.*;
import org.aswing.border.*;
import org.aswing.event.*;
import org.aswing.geom.*;

    public class DailyBonusDialogView extends GenericDialogView
    {
        protected int m_bonusDay ;
        protected int m_prevBonusDay ;
        protected Array m_bonusesInfoArray ;
        private int m_differenceAmount ;
        protected Array m_bonusArray ;
        protected CheckBox m_checkMark ;
        protected boolean m_bCheckBox =false ;
        protected AssetPane m_rewardPane ;
        protected Loader m_loader ;
        protected boolean m_isBonusAvailable ;
        protected boolean m_isRecoveryNeeded ;

        public  DailyBonusDialogView (Dictionary param1 ,boolean param2 ,boolean param3 ,String param4 ="",String param5 ="",int param6 =0,Function param7 =null ,String param8 ="")
        {
            this.m_isBonusAvailable = param2;
            this.m_isRecoveryNeeded = param3;
            super(param1, param4, param5, param6, param7, param8);
            return;
        }//end

         protected JPanel  createHeaderPanel ()
        {
            JPanel _loc_1 =new JPanel(new BorderLayout ());
            _loc_2 = createCloseButtonPanel();
            _loc_3 = createTitlePanel();
            ASwingHelper.prepare(_loc_2);
            ASwingHelper.setEasyBorder(_loc_3, 5);
            _loc_1.append(ASwingHelper.horizontalStrut(_loc_2.getWidth()), BorderLayout.WEST);
            _loc_1.append(_loc_3, BorderLayout.CENTER);
            _loc_1.append(_loc_2, BorderLayout.EAST);
            return _loc_1;
        }//end

         protected void  init ()
        {
            if (m_type == TYPE_OK)
            {
                m_acceptTextName = ZLoc.t("Dialogs", "OkButton");
            }
            else if (m_type == TYPE_YESNO)
            {
                m_acceptTextName = ZLoc.t("Dialogs", "Yes");
                m_cancelTextName = ZLoc.t("Dialogs", "No");
            }
            else if (m_type == TYPE_ACCEPTCANCEL)
            {
                m_acceptTextName = ZLoc.t("Dialogs", "Accept");
                m_cancelTextName = ZLoc.t("Dialogs", "Cancel");
            }
            else if (m_type == TYPE_SHARECANCEL)
            {
                m_acceptTextName = ZLoc.t("Dialogs", "Share");
                m_cancelTextName = ZLoc.t("Dialogs", "Cancel");
            }
            else if (m_type == TYPE_SENDGIFTS)
            {
                m_acceptTextName = ZLoc.t("Dialogs", "SendGifts");
                m_cancelTextName = ZLoc.t("Dialogs", "Skip");
            }
            else if (m_type == TYPE_CONFIRM)
            {
                m_acceptTextName = ZLoc.t("Dialogs", "Confirm");
            }
            m_bgAsset =(DisplayObject) new m_assetDict.get("dailyBonus_bg");
            this.makeBackground();
            makeCenterPanel();
            ASwingHelper.prepare(this);
            return;
        }//end

         protected JPanel  createInteriorHolder ()
        {
            JPanel _loc_1 =new JPanel(new SoftBoxLayout(SoftBoxLayout.Y_AXIS ,-10,SoftBoxLayout.TOP ));
            _loc_1.setPreferredWidth(m_bgAsset.width);
            _loc_1.setMinimumWidth(m_bgAsset.width);
            _loc_1.setMaximumWidth(m_bgAsset.width);
            _loc_2 = this.createHeaderPanel();
            ASwingHelper.prepare(_loc_2);
            this.m_bonusDay = Global.player.dailyBonusManager.currentBonusDay - 1;
            this.m_prevBonusDay = Global.player.dailyBonusManager.previousBonusDay - 1;
            this.m_differenceAmount = this.m_bonusDay - this.m_prevBonusDay;
            this.m_bonusArray = Global.player.dailyBonusManager.getDailyBonusRange(1, 5);
            textArea = this.createTextArea();
            ASwingHelper.prepare(textArea);
            _loc_1.setBorder(new EmptyBorder(null, new Insets(0, 0, 0, 0)));
            _loc_1.append(_loc_2);
            _loc_1.append(ASwingHelper.verticalStrut(20));
            _loc_1.append(textArea);
            _loc_1.append(ASwingHelper.verticalStrut(10));
            return _loc_1;
        }//end

        private String  determineDailyRewardType (int param1 )
        {
            String _loc_2 =null ;
            if (this.m_bonusArray.get(param1).goods > 0)
            {
                _loc_2 = "goods";
            }
            else if (this.m_bonusArray.get(param1).energy > 0)
            {
                _loc_2 = "energy";
            }
            else if (this.m_bonusArray.get(param1).xp > 0)
            {
                _loc_2 = "xp";
            }
            else if (this.m_bonusArray.get(param1).coin > 0)
            {
                _loc_2 = "coin";
            }
            return _loc_2;
        }//end

        private double  determineDailyRewardAmount (int param1 )
        {
            double _loc_2 =0;
            if (this.m_bonusArray.get(param1).goods > 0)
            {
                _loc_2 = this.m_bonusArray.get(param1).goods;
            }
            else if (this.m_bonusArray.get(param1).energy > 0)
            {
                _loc_2 = this.m_bonusArray.get(param1).energy;
            }
            else if (this.m_bonusArray.get(param1).xp > 0)
            {
                _loc_2 = this.m_bonusArray.get(param1).xp;
            }
            else if (this.m_bonusArray.get(param1).coin > 0)
            {
                _loc_2 = this.m_bonusArray.get(param1).coin;
            }
            return _loc_2;
        }//end

         protected void  makeBackground ()
        {
            if (m_bgAsset)
            {
                ASwingHelper.setSizedBackground(this, m_bgAsset, new Insets(0, 0, 10));
            }
            return;
        }//end

        private JPanel  createDailyHolders ()
        {
            Object _loc_4 =null ;
            Object _loc_5 =null ;
            DisplayObject _loc_6 =null ;
            String _loc_7 =null ;
            int _loc_8 =0;
            String _loc_9 =null ;
            DisplayObject _loc_10 =null ;
            boolean _loc_11 =false ;
            AssetPane _loc_12 =null ;
            JPanel _loc_13 =null ;
            JPanel _loc_14 =null ;
            JPanel _loc_15 =null ;
            JPanel _loc_16 =null ;
            JTextField _loc_17 =null ;
            JPanel _loc_18 =null ;
            double _loc_19 =0;
            JPanel _loc_20 =null ;
            DisplayObject _loc_21 =null ;
            JTextField _loc_22 =null ;
            String _loc_23 =null ;
            double _loc_24 =0;
            JPanel _loc_25 =null ;
            JLabel _loc_26 =null ;
            Array _loc_27 =null ;
            Array _loc_28 =null ;
            String _loc_29 =null ;
            ScrollingImageObject _loc_30 =null ;
            String _loc_31 =null ;
            Sprite _loc_32 =null ;
            double _loc_33 =0;
            JLabel _loc_34 =null ;
            JPanel _loc_35 =null ;
            _loc_1 = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.LEFT,-15);
            this.m_bonusesInfoArray = new Array();
            int _loc_2 =0;
            while (_loc_2 < 5)
            {

                _loc_4 = new Object();
                if (this.m_isBonusAvailable && !this.m_isRecoveryNeeded)
                {
                    if (_loc_2 < this.m_bonusDay)
                    {
                        _loc_4.success = "green";
                    }
                    else if (_loc_2 == this.m_bonusDay)
                    {
                        _loc_4.success = "green_selected";
                    }
                    else
                    {
                        _loc_4.success = "regular";
                    }
                }
                else if (_loc_2 <= this.m_prevBonusDay)
                {
                    _loc_4.success = "green";
                }
                else if (_loc_2 > this.m_prevBonusDay && _loc_2 < this.m_bonusDay)
                {
                    _loc_4.success = "red";
                }
                else if (_loc_2 == this.m_bonusDay)
                {
                    _loc_4.success = "red_selected";
                }
                else
                {
                    _loc_4.success = "regular";
                }
                this.m_bonusesInfoArray.push(_loc_4);
                _loc_2++;
            }
            int _loc_3 =0;
            while (_loc_3 < 5)
            {

                _loc_5 = this.m_bonusesInfoArray.get(_loc_3);
                if (_loc_3 == 0)
                {
                    _loc_7 = "dailyBonus_tab_left_" + _loc_5.success;
                    _loc_6 =(DisplayObject) new m_assetDict.get(_loc_7);
                }
                else if (_loc_3 == 4)
                {
                    _loc_7 = "dailyBonus_tab_right_" + _loc_5.success;
                    _loc_6 =(DisplayObject) new m_assetDict.get(_loc_7);
                }
                else
                {
                    _loc_7 = "dailyBonus_tab_mid_" + _loc_5.success;
                    _loc_6 =(DisplayObject) new m_assetDict.get(_loc_7);
                }
                _loc_9 = this.determineDailyRewardType(_loc_3);
                _loc_11 = true;
                _loc_12 = new AssetPane();
                _loc_13 = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.CENTER);
                if (_loc_5.success == "green" || _loc_5.success == "green_selected")
                {
                    _loc_8 = EmbeddedArt.greenTextColor;
                    _loc_10 =(DisplayObject) new m_assetDict.get("dailyBonus_check");
                }
                else if (_loc_5.success == "red" || _loc_5.success == "red_selected")
                {
                    _loc_8 = EmbeddedArt.redTextColor;
                    _loc_10 =(DisplayObject) new m_assetDict.get("dailyBonus_x");
                }
                else
                {
                    _loc_8 = EmbeddedArt.blueTextColor;
                    if (_loc_3 != 4)
                    {
                        _loc_23 = "doober_" + _loc_9;
                        _loc_10 =(DisplayObject) new m_assetDict.get(_loc_23);
                        ASwingHelper.setEasyBorder(_loc_12, 15);
                        _loc_24 = this.determineDailyRewardAmount(_loc_3);
                        _loc_25 = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.CENTER);
                        _loc_26 = ASwingHelper.makeLabel(String(_loc_24), EmbeddedArt.defaultFontNameBold, 12, EmbeddedArt.blueTextColor);
                        _loc_25.append(_loc_26);
                    }
                    else
                    {
                        _loc_10 = null;
                        _loc_27 = new Array();
                        _loc_28 = (this.m_bonusArray.get(4) as DailyBonus).items;
                        for(int i0 = 0; i0 < _loc_28.size(); i0++)
                        {
                        	_loc_29 = _loc_28.get(i0);

                            _loc_31 = Global.gameSettings().getImageByName(_loc_29, "icon");
                            _loc_27.push(_loc_31);
                        }
                        _loc_27.reverse();
                        _loc_27.push(Global.getAssetURL("assets/doobers/bolt_doober.png"));
                        _loc_30 = new ScrollingImageObject(_loc_27, 75, 75, "assets/dialogs/questionMark.png");
                        _loc_11 = false;
                    }
                }
                _loc_14 = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.BOTTOM);
                _loc_15 = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.TOP);
                _loc_16 = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.TOP, -3);
                ASwingHelper.setEasyBorder(_loc_16, 5, 0);
                if (_loc_5.success != ("red_selected" || "green_selected"))
                {
                    ASwingHelper.setSizedBackground(_loc_15, _loc_6);
                }
                else
                {
                    ASwingHelper.setSizedBackground(_loc_15, _loc_6);
                }
                _loc_18 = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.CENTER);
                _loc_19 = TextFieldUtil.getLocaleFontSize(16, 14, .get({locale:"it", size:12}, {locale:"fr", size:12}, {locale:"de", size:16}, {locale:"es", size:16}, {locale:"ja", size:16}));
                _loc_17 = ASwingHelper.makeTextField(ZLoc.t("Dialogs", "DayNumber", {amount:(_loc_3 + 1)}), EmbeddedArt.defaultFontNameBold, _loc_19, _loc_8);
                _loc_18.append(_loc_17);
                _loc_12.setAsset(_loc_10);
                if (_loc_3 != 4)
                {
                    _loc_13.append(_loc_12);
                }
                else if (_loc_30)
                {
                    _loc_32 = new Sprite();
                    _loc_32.graphics.beginFill(0);
                    _loc_32.graphics.lineTo(120, 0);
                    _loc_32.graphics.lineTo(120, 60);
                    _loc_32.graphics.curveTo(120, 78, 100, 78);
                    _loc_32.graphics.lineTo(5, 78);
                    _loc_32.graphics.lineTo(24, 32);
                    _loc_32.graphics.lineTo(21, 0);
                    _loc_32.graphics.endFill();
                    _loc_13.addChild(_loc_32);
                    _loc_30.mask = _loc_32;
                    _loc_13.addChild(_loc_30);
                    _loc_30.x = 30;
                    _loc_30.y = 3;
                    _loc_13.setPreferredSize(new IntDimension(50, 100));
                }
                else
                {
                    _loc_13.append(_loc_12);
                }
                if (_loc_3 != 4)
                {
                    _loc_16.appendAll(_loc_18, _loc_13, _loc_25);
                }
                else
                {
                    _loc_16.appendAll(_loc_18, _loc_13);
                }
                if (_loc_3 == 4 && _loc_3 != this.m_bonusDay)
                {
                    _loc_33 = TextFieldUtil.getLocaleFontSize(12, 11, .get({locale:"de", size:10}, {locale:"es", size:9}, {locale:"ja", size:9}));
                    _loc_34 = ASwingHelper.makeLabel(ZLoc.t("Dialogs", "ChanceToWin"), EmbeddedArt.defaultFontNameBold, _loc_33, EmbeddedArt.blueTextColor);
                    _loc_35 = ASwingHelper.centerElement(_loc_34);
                    _loc_16.remove(_loc_25);
                    _loc_16.insert(1, _loc_35);
                }
                if (_loc_3 == 0)
                {
                }
                _loc_15.append(_loc_16);
                _loc_14.append(_loc_15);
                _loc_1.append(_loc_14);
                _loc_3++;
            }
            return _loc_1;
        }//end

        private String  todaysGiftItemType (int param1 )
        {
            String _loc_2 =null ;
            if (this.m_bonusArray.get(param1).goods > 0)
            {
                _loc_2 = ZLoc.t("Dialogs", "TodaysWinGoods");
            }
            else if (this.m_bonusArray.get(param1).energy > 0)
            {
                _loc_2 = ZLoc.t("Dialogs", "TodaysWinEnergy");
            }
            else if (this.m_bonusArray.get(param1).xp > 0)
            {
                _loc_2 = ZLoc.t("Dialogs", "TodaysWinXP");
            }
            else if (this.m_bonusArray.get(param1).coin > 0)
            {
                _loc_2 = ZLoc.t("Dialogs", "TodaysWinCoins");
            }
            return _loc_2;
        }//end

         protected JPanel  createTextArea ()
        {
            String titleString ;
            String awardText ;
            String shareText ;
            DisplayObject prizeObject ;
            AssetPane prizePane ;
            JButton shareBtn ;
            Item prizeItem ;
            Loader prizeLoader ;
            String index ;
            String index2 ;
            AssetIcon cashIcon ;
            String prizeIndex ;
            textArea = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.CENTER,10);
            textInnerArea = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.CENTER);
            shareBtnHolder = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.CENTER,10);
            prizePane = new AssetPane();
            if (this.m_isBonusAvailable && !this.m_isRecoveryNeeded)
            {
                titleString = ZLoc.t("Dialogs", "BetterBonuses");
                awardText = ZLoc.t("Dialogs", "TodaysWin", {amount:String(this.determineDailyRewardAmount(this.m_bonusDay))}) + " " + this.todaysGiftItemType(this.m_bonusDay);
                shareText = ZLoc.t("Dialogs", "ExtraShare");
                shareBtn = new CustomButton(ZLoc.t("Dialogs", "Share"), null, "GreenButtonUI");
                shareBtnHolder.append(shareBtn);
                shareBtn.addActionListener(this.shareThis, 0, true);
                if (this.m_bonusDay == (Global.gameSettings().getDailyBonusMaxStreak() - 1))
                {
                    if (Global.player.dailyBonusManager.randomReward)
                    {
                        prizeItem = Global.gameSettings().getItemByName(Global.player.dailyBonusManager.randomReward);
                        awardText = ZLoc.t("Dialogs", "TodaysWin", {amount:prizeItem.localizedName});
                        prizeObject;
                        prizeLoader =LoadingManager .load (prizeItem .icon ,void  ()
            {
                prizePane.setAsset(prizeLoader.content);
                return;
            }//end
            );
                    }
                    else
                    {
                        index = "doober_" + this.determineDailyRewardType(this.m_bonusDay);
                        prizeObject =(DisplayObject) new m_assetDict.get(index);
                    }
                }
                else
                {
                    index2 = "doober_" + this.determineDailyRewardType(this.m_bonusDay);
                    prizeObject =(DisplayObject) new m_assetDict.get(index2);
                }
            }
            else
            {
                titleString = ZLoc.t("Dialogs", "MissedDay");
                awardText = ZLoc.t("Dialogs", "MissingLoss", {amount:this.m_bonusArray.get(this.m_bonusDay).coin});
                shareText = ZLoc.t("Dialogs", "RecoverOption");
                cashIcon = new AssetIcon(new (DisplayObject)EmbeddedArt.icon_cash());
                if (this.m_bonusDay == (Global.gameSettings().getDailyBonusMaxStreak() - 1))
                {
                    awardText = ZLoc.t("Dialogs", "MissingGiftLoss");
                    prizeObject =(DisplayObject) new m_assetDict.get("dailyBonus_present_big");
                }
                else
                {
                    prizeIndex = "doober_" + this.determineDailyRewardType(this.m_bonusDay);
                    prizeObject =(DisplayObject) new m_assetDict.get(prizeIndex);
                }
            }
            titleHolder = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.CENTER);
            titleText = ASwingHelper.makeTextField(titleString,EmbeddedArt.defaultFontNameBold,22,4627661);
            titleHolder.append(titleText);
            hrHolder = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.CENTER);
            DisplayObject hrObject =(DisplayObject)new m_assetDict.get( "dailyBonus_hr");
            AssetPane hrPane =new AssetPane(hrObject );
            hrHolder.append(hrPane);
            prizeHolder = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.CENTER,10);
            prizePane.setAsset(prizeObject);
            prizeMessageHolder = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.TOP,5);
            prizeText = ASwingHelper.makeTextField(awardText,EmbeddedArt.defaultFontNameBold,22,EmbeddedArt.brownTextColor);
            subprizeText = ASwingHelper.makeTextField(shareText+"      ",EmbeddedArt.defaultFontNameBold,14,EmbeddedArt.brownTextColor);
            prizeMessageHolder.appendAll(prizeText, subprizeText, shareBtnHolder);
            prizeHolder.appendAll(prizePane, prizeMessageHolder);
            dailysHolder = this.createDailyHolders();
            todayHolder = this.createTodayHolder();
            textInnerArea.appendAll(titleHolder, ASwingHelper.verticalStrut(5), hrHolder, ASwingHelper.verticalStrut(5), prizeHolder, ASwingHelper.verticalStrut(13), dailysHolder, todayHolder);
            textArea.append(textInnerArea);
            return textArea;
        }//end

        private JPanel  createTodayHolder ()
        {
            _loc_1 = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.LEFT,25);
            _loc_1.setPreferredWidth(m_bgAsset.width);
            _loc_1.setMinimumWidth(m_bgAsset.width);
            _loc_1.setMaximumWidth(m_bgAsset.width);
            ASwingHelper.setEasyBorder(_loc_1, 0, 25);
            _loc_2 = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.CENTER);
            DisplayObject _loc_3 =(DisplayObject)new m_assetDict.get( "dailyBonus_tab");
            TextFormat _loc_4 =new TextFormat ();
            _loc_4.align = TextFormatAlign.CENTER;
            Array _loc_5 =.get(new DropShadowFilter(1,45,0,0.5,0,0,5)) ;
            _loc_6 = ASwingHelper.makeTextField(ZLoc.t("Dialogs","Today"),EmbeddedArt.defaultFontNameBold,12,16777215,0,_loc_4);
            _loc_6.filters = _loc_5;
            _loc_7 = ASwingHelper.centerElement(_loc_6);
            _loc_2.append(_loc_7);
            ASwingHelper.setSizedBackground(_loc_2, _loc_3);
            _loc_8 = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.CENTER);
            DisplayObject _loc_9 =(DisplayObject)new m_assetDict.get( "dailyBonus_tab");
            _loc_10 = ASwingHelper.makeTextField(ZLoc.t("Dialogs","Tomorrow"),EmbeddedArt.defaultFontNameBold,12,16777215,0,_loc_4);
            _loc_10.filters = _loc_5;
            _loc_11 = ASwingHelper.centerElement(_loc_10);
            _loc_8.append(_loc_11);
            ASwingHelper.setSizedBackground(_loc_8, _loc_9);
            _loc_12 = this.m_bonusDay*115-35;
            if (this.m_bonusDay * 115 - 35 > 0)
            {
                _loc_1.append(ASwingHelper.horizontalStrut(_loc_12));
            }
            _loc_1.appendAll(_loc_2, _loc_8);
            return _loc_1;
        }//end

        private void  checkEmail (MouseEvent event )
        {
            if (this.m_bCheckBox == true)
            {
                this.m_bCheckBox = false;
                this.m_checkMark.turnOff();
            }
            else
            {
                this.m_bCheckBox = true;
                this.m_checkMark.turnOn();
            }
            return;
        }//end

        private void  startOver (AWEvent event )
        {
            this.onCancel(event);
            return;
        }//end

        private void  shareThis (AWEvent event )
        {
            Global.world.viralMgr.sendDailyBonusFeed();
            this.onAccept(event);
            return;
        }//end

        private void  recoverStreak (AWEvent event )
        {
            if (Global.player.dailyBonusManager.extraCashNeededForRecovery > 0)
            {
                this.onCancel(event);
                UI.displayImpulseBuyPopup(ImpulseBuy.TYPE_MARKET_CASH);
                StatsManager.sample(100, StatsCounterType.HUD_COUNTER, "add_currency");
            }
            else
            {
                Global.player.dailyBonusManager.collectDailyBonus();
                this.onAccept(event);
            }
            return;
        }//end

    }




