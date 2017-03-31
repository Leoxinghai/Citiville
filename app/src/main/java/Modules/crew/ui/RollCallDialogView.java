package Modules.crew.ui;

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
import Display.MarketUI.assets.*;
import Display.RenameUI.*;
import Display.aswingui.*;
import Engine.Managers.*;
import Events.*;
import Mechanics.*;
import Mechanics.GameEventMechanics.*;
import ZLocalization.*;
import com.zynga.skelly.util.*;
//import flash.display.*;
//import flash.events.*;
//import flash.external.*;
//import flash.text.*;
//import flash.utils.*;
import org.aswing.*;
import org.aswing.event.*;

    public class RollCallDialogView extends GenericDialogView
    {
        protected RollCallDataMechanic m_mechData ;
        protected TieredDooberMechanic m_rewardData ;
        protected Array m_data ;
        protected Timer m_timer ;
        private JLabel m_timerText ;
        protected int m_friendsResponded ;
        private int m_nextRewardIndex =0;
        private int m_checkinsLeft =0;
        protected JLabel m_responseRatio ;
        protected AssetPane m_firstCheckinLine ;
        protected JLabel m_secondCheckinLine ;
        protected Array m_rewardSpriteAssets ;
        protected JPanel m_rewardsPanel ;
        protected int m_collectButtonWidth ;
        private String m_rollCallName ;
        public static  int LIST_WIDTH =709;
        public static  int LIST_HEIGHT =284;
        public static  int REWARD_HEIGHT =135;

        public  RollCallDialogView (Array param1 ,String param2 ,Dictionary param3 ,String param4 ="",String param5 ="",int param6 =0,Function param7 =null ,String param8 ="",int param9 =0,String param10 ="",Function param11 =null ,String param12 ="")
        {
            this.m_timer = new Timer(1000);
            ExternalInterface.call("console.log", "creating rollCallDialogView");
            this.m_data = param1;
            this.m_mechData =(RollCallDataMechanic) MechanicManager.getInstance().getMechanicInstance(param3.get("spawner"), "rollCall", MechanicManager.ALL);
            this.m_rewardData =(TieredDooberMechanic) MechanicManager.getInstance().getMechanicInstance(param3.get("spawner"), "rollCallTieredDooberValue", MechanicManager.ALL);
            this.m_rewardSpriteAssets = new Array();
            this.m_rollCallName = param2;
            addEventListener("onPostRollCall", this.onPostRollCall, false, 0, true);
            addEventListener("onCollectBonus", this.onCollectBonus, false, 0, true);
            addEventListener("onBuyCheckin", this.onBuyCheckin, false, 0, true);
            addEventListener("needMoreCash", this.needMoreCash, false, 0, true);
            super(param3, param4, param5, param6, param7, param8, param9, param10, param11, param12);
            return;
        }//end

        public int  nextRewardIndex ()
        {
            return this.m_nextRewardIndex;
        }//end

        public void  nextRewardIndex (int param1 )
        {
            this.m_nextRewardIndex = param1;
            return;
        }//end

        public int  checkinsLeft ()
        {
            return this.m_checkinsLeft;
        }//end

        public void  checkinsLeft (int param1 )
        {
            this.m_checkinsLeft = param1;
            return;
        }//end

         protected JPanel  createTextArea ()
        {
            JPanel _loc_2 =null ;
            JPanel _loc_3 =null ;
            DisplayObject _loc_7 =null ;
            _loc_1 = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.TOP ,5);
            if (this.m_mechData.canPerformRollCall() || !this.m_mechData.isRollCallComplete())
            {
                _loc_2 = this.makeTopPane();
                _loc_3 = this.makeRewardPane();
            }
            else
            {
                _loc_3 = this.makeRewardCompletePane();
                _loc_7 =(DisplayObject) new m_assetDict.get("payroll_burst");
                _loc_1.addChild(_loc_7);
                _loc_7.x = 472;
                _loc_7.y = 360;
                _loc_7.width = this.m_collectButtonWidth;
            }
            _loc_4 = ASwingHelper.makeFlowJPanel(FlowLayout.LEFT );
            VectorListModel _loc_5 =new VectorListModel(this.m_data );
            m_assetDict.put("rollCallName",  this.m_rollCallName);
            VerticalScrollingList _loc_6 =new VerticalScrollingList(this.m_assetDict ,_loc_5 ,new RenameCellFactory(RollCallDialogCell ,m_assetDict ),1,3,LIST_WIDTH ,LIST_HEIGHT ,true );
            ASwingHelper.setEasyBorder(_loc_1, 10, 20, 0, 20);
            _loc_6.addEventListener(UIEvent.CHANGE_CREW_STATE, this.refreshStats, false, 0, true);
            _loc_4.append(_loc_6);
            _loc_1.appendAll(_loc_2, _loc_4, ASwingHelper.verticalStrut(-5), _loc_3, ASwingHelper.verticalStrut(15));
            return _loc_1;
        }//end

        private void  refreshStats (UIEvent event )
        {
            this.updateResponseRatio();
            this.updateRewardPane();
            return;
        }//end

        protected AssetPane  makeBorder ()
        {
            Sprite _loc_1 =new Sprite ();
            _loc_1.graphics.beginFill(EmbeddedArt.lightBlueTextColor);
            _loc_1.graphics.drawRoundRect(0, 0, 3, 38, 2, 2);
            _loc_1.graphics.endFill();
            AssetPane _loc_2 =new AssetPane(_loc_1 );
            return _loc_2;
        }//end

        protected JPanel  makeResponsesPane ()
        {
            _loc_1 = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.TOP ,-5);
            this.m_responseRatio = ASwingHelper.makeLabel(" ", EmbeddedArt.titleFont, 30, EmbeddedArt.lightOrangeTextColor);
            this.updateResponseRatio();
            _loc_2 = ASwingHelper.makeLabel(ZLoc.t("Dialogs","RollCall_"+this.m_rollCallName +"_response"),EmbeddedArt.defaultFontNameBold ,12,EmbeddedArt.blueTextColor );
            _loc_1.appendAll(ASwingHelper.leftAlignElement(this.m_responseRatio), ASwingHelper.leftAlignElement(_loc_2));
            return _loc_1;
        }//end

        private void  updateResponseRatio ()
        {
            Object _loc_3 =null ;
            int _loc_4 =0;
            String _loc_5 =null ;
            _loc_1 = MechanicManager.getInstance ().getMechanicInstance(assetDict.get( "spawner") ,"rollCall",MechanicManager.ALL )as RollCallDataMechanic ;
            _loc_2 = _loc_1.getCrewState ();
            this.m_friendsResponded = 0;
            for(int i0 = 0; i0 < _loc_2.size(); i0++)
            {
            		_loc_3 = _loc_2.get(i0);

                if (_loc_3.checkedIn == true)
                {
                    this.m_friendsResponded++;
                }
            }
            _loc_4 = _loc_2.length;
            _loc_5 = String(this.m_friendsResponded) + "/" + String(_loc_4);
            this.m_responseRatio.setText(_loc_5);
            ASwingHelper.prepare(this.parent);
            return;
        }//end

        protected JPanel  makeTopPane ()
        {
            _loc_1 = ASwingHelper.makeFlowJPanel(FlowLayout.LEFT ,10);
            AssetPane _loc_2 =new AssetPane(new m_assetDict.get( "payroll_icon_responses") );
            _loc_3 = this.makeResponsesPane ();
            AssetPane _loc_4 =new AssetPane(new m_assetDict.get( "payroll_icon_clock") );
            _loc_5 = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.TOP ,-5);
            this.m_timerText = ASwingHelper.makeLabel(this.calcTimeLabel(), EmbeddedArt.titleFont, 30, EmbeddedArt.lightOrangeTextColor, JLabel.LEFT);
            _loc_6 = ASwingHelper.makeLabel(ZLoc.t("Dialogs","RollCall_"+this.m_rollCallName +"_timeRemaining"),EmbeddedArt.defaultFontNameBold ,12,EmbeddedArt.blueTextColor );
            this.m_timerText.setPreferredWidth(230);
            this.m_timer.addEventListener(TimerEvent.TIMER, this.updateLabel, false, 0, true);
            this.m_timer.start();
            _loc_5.appendAll(ASwingHelper.leftAlignElement(this.m_timerText), ASwingHelper.leftAlignElement(_loc_6));
            _loc_1.appendAll(_loc_2, _loc_3, this.makeBorder(), _loc_4, _loc_5);
            return _loc_1;
        }//end

        private void  updateLabel (TimerEvent event )
        {
            this.m_timerText.setText(this.calcTimeLabel());
            return;
        }//end

        protected String  calcTimeLabel ()
        {
            String _loc_1 =null ;
            _loc_2 = this.m_mechData.getRollCallTimeLeft ();
            if (_loc_2 <= 0)
            {
                this.m_timer.stop();
                this.close();
                if (m_closeCallback != null)
                {
                    m_closeCallback(new GenericPopupEvent(GenericPopupEvent.SELECTED, GenericDialogView.YES));
                }
            }
            _loc_1 = CardUtil.formatTimeHours(_loc_2);
            return _loc_1;
        }//end

        protected JPanel  generateRewardPane ()
        {
            boolean rewardComplete ;
            Object tierObj ;
            int checkinsNeeded ;
            Dictionary tierRewardObject ;
            Object key ;
            DisplayObject rewardAsset ;
            CrewRewardSprite reward ;
            AssetPane rewardPane ;
            String tierName ;
            String itemName ;
            double tierAmount ;
            Item item ;
            Loader itemLoader ;
            jp = ASwingHelper.makeFlowJPanel(FlowLayout.LEFT,0);
            newRewardData = MechanicManager.getInstance ().getMechanicInstance(assetDict.get( "spawner") ,"rollCallTieredDooberValue",MechanicManager.ALL )as TieredDooberMechanic ;
            numTiers = newRewardData.getNumTiers();
            int i ;
            while (i < numTiers)
            {

                tierObj = newRewardData.getTierInfo(i);
                checkinsNeeded = tierObj.num;
                if (this.m_friendsResponded >= checkinsNeeded)
                {
                    rewardComplete;
                    this.nextRewardIndex = 0;
                    this.checkinsLeft = 0;
                }
                else
                {
                    rewardComplete;
                    if (this.nextRewardIndex == 0 || this.nextRewardIndex == i)
                    {
                        this.checkinsLeft = checkinsNeeded - this.m_friendsResponded;
                        this.nextRewardIndex = i;
                    }
                }
                tierRewardObject = newRewardData.getTierRewardInfo(i);
                int _loc_2 =0;
                _loc_3 = tierRewardObject;
                for(int i0 = 0; i0 < tierRewardObject.size(); i0++)
                {
                		key = tierRewardObject.get(i0);


                    tierName = key.toString();
                    itemName;
                    tierAmount;
                    if (tierRewardObject.get(key) instanceof Number)
                    {
                        tierAmount = tierRewardObject.get(key);
                        continue;
                    }
                    itemName = tierRewardObject.get(key).name;
                    tierAmount = tierRewardObject.get(key).count;
                }
                rewardAsset = new Sprite();
                switch(tierName)
                {
                    case "coin":
                    {
                        rewardAsset =(DisplayObject) new m_assetDict.get("payroll_reward_coin");
                        break;
                    }
                    case "xp":
                    {
                        rewardAsset =(DisplayObject) new m_assetDict.get("payroll_reward_xp");
                        break;
                    }
                    case "energy":
                    {
                        rewardAsset =(DisplayObject) new m_assetDict.get("payroll_reward_energy");
                        break;
                    }
                    case "item":
                    {
                        item = Global.gameSettings().getItemByName(itemName);
                        if (item)
                        {
                            itemLoader =LoadingManager .load (item .icon ,Curry .curry (void  (JPanel param1 ,Event param2 )
            {
                if (rewardAsset instanceof DisplayObjectContainer)
                {
                    ((DisplayObjectContainer)rewardAsset).addChild(itemLoader.content);
                    itemLoader.content.x = itemLoader.content.x - itemLoader.content.width / 2;
                    itemLoader.content.y = itemLoader.content.y - itemLoader.content.height / 2;
                }
                ASwingHelper.prepare(param1);
                param1.dispatchEvent(new UIEvent(UIEvent.REFRESH_DIALOG, "itemIconLoaded", true));
                return;
            }//end
            , this));
                        }
                        break;
                    }
                    default:
                    {
                        break;
                    }
                }
                reward = new CrewRewardSprite(m_assetDict, rewardAsset, rewardComplete, tierAmount, checkinsNeeded);
                this.m_rewardSpriteAssets.push(reward);
                rewardPane = new AssetPane(reward);
                if (!rewardComplete)
                {
                    ASwingHelper.setEasyBorder(rewardPane, 0, 15, 0, 15);
                }
                else
                {
                    ASwingHelper.setEasyBorder(rewardPane, 14, 0, 0, 15);
                }
                jp.append(ASwingHelper.bottomAlignElement(rewardPane));
                if (i != 3)
                {
                    jp.append(new AssetPane(new m_assetDict.get("payroll_rewardArrow")));
                }
                i = (i + 1);
            }
            ASwingHelper.prepare(jp);
            return jp;
        }//end

        protected JPanel  makeRewardCompletePane ()
        {
            _loc_1 = ASwingHelper.makeFlowJPanel(FlowLayout.LEFT ,10);
            _loc_2 = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.TOP );
            _loc_3 = this.makeResponsesPane ();
            _loc_4 = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.TOP );
            _loc_5 = this.generateRewardPane ();
            _loc_4.appendAll(_loc_5);
            _loc_6 = TextFieldUtil.formatSmallCapsString(ZLoc.t("Dialogs","RollCallCollect_"+this.m_rollCallName));
            CustomButton _loc_7 =new CustomButton(_loc_6 ,null ,"GreenButtonUI");
            ASFont _loc_8 =_loc_7.getFont ();
            _loc_7.setFont(_loc_8.changeSize(TextFieldUtil.getLocaleFontSizeByRatio(_loc_8.getSize(), 1, [{locale:"de", ratio:0.9}, {locale:"id", ratio:0.6}, {locale:"es", ratio:0.9}, {locale:"fr", ratio:0.8}, {locale:"it", ratio:0.75}, {locale:"pt", ratio:0.85}, {locale:"tr", ratio:1}])));
            ASwingHelper.prepare(_loc_7);
            this.m_collectButtonWidth = _loc_7.getWidth();
            if (Global.rollCallManager.canCollect(Global.player.uid, m_assetDict.get("spawner")) && this.nextRewardIndex != 1)
            {
                _loc_7.addActionListener(this.collect, 0, true);
            }
            else
            {
                _loc_7.setEnabled(false);
            }
            _loc_2.appendAll(_loc_3, ASwingHelper.verticalStrut(10), _loc_7);
            _loc_1.appendAll(ASwingHelper.horizontalStrut(20), _loc_4, this.makeBorder(), _loc_2);
            return _loc_1;
        }//end

        private void  collect (AWEvent event )
        {
            Global.rollCallManager.collect(Global.player.uid, m_assetDict.get("spawner"));
            this.close();
            if (m_closeCallback != null)
            {
                m_closeCallback(new GenericPopupEvent(GenericPopupEvent.SELECTED, GenericDialogView.NO));
            }
            return;
        }//end

        private String  updateCheckinText ()
        {
            Object _loc_3 =null ;
            LocalizationObjectToken _loc_4 =null ;
            String _loc_5 =null ;
            String _loc_6 =null ;
            String _loc_7 =null ;
            double _loc_8 =0;
            Item _loc_9 =null ;
            _loc_1 = MechanicManager.getInstance ().getMechanicInstance(assetDict.get( "spawner") ,"rollCallTieredDooberValue",MechanicManager.ALL )as TieredDooberMechanic ;
            _loc_2 = _loc_1.getTierRewardInfo(this.m_nextRewardIndex );
            for(int i0 = 0; i0 < _loc_2.size(); i0++)
            {
            		_loc_3 = _loc_2.get(i0);

                _loc_6 = _loc_3.toString();
                _loc_7 = "";
                _loc_8 = 0;
                if (_loc_2.get(_loc_3) instanceof Number)
                {
                    _loc_8 = _loc_2.get(_loc_3);
                    continue;
                }
                _loc_7 = _loc_2.get(_loc_3).name;
                _loc_8 = _loc_2.get(_loc_3).count;
            }
            switch(_loc_6)
            {
                case "coin":
                {
                    _loc_4 = ZLoc.tk("Rewards", "Coins", "", _loc_8);
                    _loc_5 = ZLoc.t("Dialogs", "RollCall_" + this.m_rollCallName + "_togetCoins", {amount:_loc_8.toString(), Coin:_loc_4});
                    break;
                }
                case "xp":
                {
                    _loc_4 = ZLoc.tk("Rewards", "XP", "", _loc_8);
                    _loc_5 = ZLoc.t("Dialogs", "RollCall_" + this.m_rollCallName + "_togetXP", {amount:_loc_8.toString(), XP:_loc_4});
                    break;
                }
                case "energy":
                {
                    _loc_4 = ZLoc.tk("Rewards", "Energy", "", _loc_8);
                    _loc_5 = ZLoc.t("Dialogs", "RollCall_" + this.m_rollCallName + "_togetEnergy", {amount:_loc_8.toString(), Energy:_loc_4});
                    break;
                }
                case "item":
                {
                    _loc_9 = Global.gameSettings().getItemByName(_loc_7);
                    _loc_5 = "";
                    if (_loc_9)
                    {
                        _loc_4 = _loc_9.getLocalizedObjectToken(_loc_8);
                        _loc_5 = ZLoc.t("Dialogs", "RollCall_" + this.m_rollCallName + "_togetItem", {amount:_loc_8.toString(), Item:_loc_4});
                    }
                    break;
                }
                default:
                {
                    _loc_5 = "";
                    break;
                }
            }
            return _loc_5;
        }//end

        private void  updateRewardPane ()
        {
            this.m_rewardsPanel.removeAll();
            this.m_rewardsPanel.append(this.generateRewardPane());
            _loc_1 = ZLoc.tk("Dialogs","Checkin_"+this.m_rollCallName ,"",this.m_checkinsLeft );
            _loc_2 = this.m_firstCheckinLine.getAsset ()as TextField ;
            _loc_3 = _loc_2.getTextFormat ();
            _loc_2.text = ZLoc.t("Dialogs", "RollCall_" + this.m_rollCallName + "_moreCheckins", {numCheckins:this.m_checkinsLeft, Checkin:_loc_1});
            _loc_2.setTextFormat(_loc_3);
            this.m_secondCheckinLine.setText(this.updateCheckinText());
            ASwingHelper.prepare(this.parent);
            return;
        }//end

        protected JPanel  makeRewardPane ()
        {
            this.m_rewardsPanel = ASwingHelper.makeFlowJPanel(FlowLayout.LEFT);
            this.m_rewardsPanel.append(this.generateRewardPane());
            ASwingHelper.setEasyBorder(this.m_rewardsPanel, 0, 20);
            _loc_1 = ASwingHelper.makeFlowJPanel(FlowLayout.LEFT );
            _loc_2 = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.CENTER );
            _loc_3 = ZLoc.tk("Dialogs","Checkin_"+this.m_rollCallName ,"",this.m_checkinsLeft );
            this.m_firstCheckinLine = ASwingHelper.makeMultilineCapsText(ZLoc.t("Dialogs", "RollCall_" + this.m_rollCallName + "_moreCheckins", {numCheckins:this.m_checkinsLeft, Checkin:_loc_3}), 205, EmbeddedArt.titleFont, TextFormatAlign.RIGHT, 26, EmbeddedArt.lightOrangeTextColor);
            this.m_secondCheckinLine = ASwingHelper.makeLabel(TextFieldUtil.formatSmallCapsString(this.updateCheckinText()), EmbeddedArt.defaultFontNameBold, 14, EmbeddedArt.blueTextColor, JLabel.RIGHT);
            _loc_2.appendAll(this.m_firstCheckinLine, this.m_secondCheckinLine);
            _loc_1.appendAll(_loc_2, ASwingHelper.horizontalStrut(10), this.makeBorder(), this.m_rewardsPanel);
            return _loc_1;
        }//end

         protected JPanel  createButtonPanel ()
        {
            JPanel _loc_1 =new JPanel(new FlowLayout(FlowLayout.CENTER ));
            return _loc_1;
        }//end

         public void  close ()
        {
            if (this.m_timer)
            {
                this.m_timer.stop();
            }
            super.close();
            return;
        }//end

        protected void  needMoreCash (Event event )
        {
            this.close();
            Global.ui.displayLightbox(false);
            UI.displayImpulseBuyPopup(ImpulseBuy.TYPE_MARKET_CASH);
            return;
        }//end

        protected void  onBuyCheckin (Event event )
        {
            _loc_2 = this.m_mechData.skipCost ;
            countDialogViewAction("skip_for_cash", true, _loc_2);
            return;
        }//end

        protected void  onPostRollCall (Event event )
        {
            countDialogViewAction("post_roll_call");
            return;
        }//end

        protected void  onCollectBonus (Event event )
        {
            countDialogViewAction("collect_bonus");
            return;
        }//end

    }



