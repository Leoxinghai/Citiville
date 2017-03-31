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
import Classes.doobers.*;
import Display.*;
import Display.ValentineUI.*;
import Display.aswingui.*;
import Engine.Managers.*;
import Events.*;
import Modules.bandits.*;
import ZLocalization.*;
//import flash.display.*;
//import flash.events.*;
//import flash.text.*;
import org.aswing.*;
import org.aswing.geom.*;

    public class PreyCell extends AchievementsCell
    {
        protected PreyData m_bandit ;
        protected boolean m_meetsRequirement =false ;
        public static  int BANDIT_ICON_HEIGHT =105;
        public static  int BANDIT_CELL_WIDTH =145;
        public static  int BANDIT_CELL_HEIGHT =308;

        public  PreyCell (int param1 =0)
        {
            super(param1);
            this.addEventListener(MouseEvent.ROLL_OVER, this.onRollOver, false, 0, true);
            return;
        }//end

         public void  setCellValue (Object param1)
        {
            int _loc_2 =0;
            int _loc_3 =0;
            this.m_bandit =(PreyData) param1;
            if (this.m_bandit.id == -1)
            {
                m_dummy = true;
            }
            else
            {
                _loc_2 = this.m_bandit.requiredHubLevel;
                _loc_3 = PreyUtil.getHubLevel(HunterDialog.groupId);
                if (_loc_2 <= _loc_3)
                {
                    this.m_meetsRequirement = true;
                }
            }
            this.buildCell();
            this.loadAssets();
            return;
        }//end

         protected void  buildCell ()
        {
            m_iconPanel = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.CENTER);
            m_headerPanel = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.CENTER);
            m_rewardPanel = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.CENTER);
            m_claimPanel = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.CENTER);
            this.buildBackground();
            if (m_dummy == false)
            {
                this.makeHeaderPanel();
                this.makeIconPanel();
                this.makeClaimPanel();
                this.makeRewardPanel();
            }
            ASwingHelper.prepare(this);
            return;
        }//end

         protected void  buildBackground ()
        {
            DisplayObject _loc_1 =(DisplayObject)new HunterDialog.assetDict.get( "police_rewardBG_left_blue");
            DisplayObject _loc_2 =(DisplayObject)new HunterDialog.assetDict.get( "police_rewardBG_left_disabled");
            DisplayObject _loc_3 =(DisplayObject)new HunterDialog.assetDict.get( "police_rewardBG_left_white");
            DisplayObject _loc_4 =(DisplayObject)new HunterDialog.assetDict.get( "police_rewardBG_center_blue");
            DisplayObject _loc_5 =(DisplayObject)new HunterDialog.assetDict.get( "police_rewardBG_center_disabled");
            DisplayObject _loc_6 =(DisplayObject)new HunterDialog.assetDict.get( "police_rewardBG_center_white");
            DisplayObject _loc_7 =(DisplayObject)new HunterDialog.assetDict.get( "police_rewardBG_right_blue");
            DisplayObject _loc_8 =(DisplayObject)new HunterDialog.assetDict.get( "police_rewardBG_right_disabled");
            DisplayObject _loc_9 =(DisplayObject)new HunterDialog.assetDict.get( "police_rewardBG_right_white");
            _loc_10 = m_position% PreyPanel.BANDITS_PER_PAGE;
            if (m_position % PreyPanel.BANDITS_PER_PAGE == 1)
            {
                m_bg = _loc_3;
            }
            else if (_loc_10 == 2)
            {
                m_bg = _loc_6;
            }
            else if (_loc_10 == 3)
            {
                m_bg = _loc_6;
            }
            else
            {
                m_bg = _loc_9;
            }
            ASwingHelper.setBackground(this, m_bg);
            ASwingHelper.setForcedSize(this, new IntDimension(BANDIT_CELL_WIDTH, BANDIT_CELL_HEIGHT));
            return;
        }//end

         protected void  makeHeaderPanel ()
        {
            _loc_1 = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.CENTER );
            _loc_2 = EmbeddedArt.darkBlueTextColor;
            if (!this.m_meetsRequirement)
            {
                _loc_2 = EmbeddedArt.lightGrayTextColor;
            }
            _loc_3 = this.m_bandit.name ;
            TextFormat _loc_4 =new TextFormat(EmbeddedArt.titleFont ,17);
            _loc_5 = ASwingHelper.shrinkFontSizeToFit(130,_loc_3 ,EmbeddedArt.titleFont ,13,null ,null ,_loc_4 );
            _loc_6 = ASwingHelper.makeMultilineCapsText(_loc_3 ,130,EmbeddedArt.titleFont ,TextFormatAlign.CENTER ,TextFieldUtil.getLocaleFontSize(_loc_5 ,_loc_5 ,.get( {locale size "ja",11) }),_loc_2 );
            _loc_1.append(_loc_6);
            m_headerPanel.append(_loc_1);
            ASwingHelper.setForcedHeight(m_headerPanel, 30);
            this.appendAll(ASwingHelper.verticalStrut(5), m_headerPanel);
            return;
        }//end

         protected void  makeClaimPanel ()
        {
            int _loc_6 =0;
            int _loc_7 =0;
            LocalizationObjectToken _loc_8 =null ;
            _loc_1 = PreyManager.getHunterPreyMode(HunterDialog.groupId);
            if (int(_loc_1.get("huntersRetired")) == 1)
            {
                return;
            }
            _loc_2 = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.CENTER );
            _loc_3 = EmbeddedArt.darkBlueTextColor;
            String _loc_4 ="";
            if (!this.m_meetsRequirement)
            {
                _loc_6 = this.m_bandit.requiredHubLevel;
                _loc_4 = ZLoc.t("Dialogs", HunterDialog.groupId + "_HubRequirementsText", {level:_loc_6});
                _loc_3 = EmbeddedArt.lightGrayTextColor;
            }
            else
            {
                _loc_7 = this.m_bandit.getRequiredHunters();
                _loc_8 = ZLoc.tk("Dialogs", HunterDialog.groupId + "_hunterName", "", _loc_7);
                _loc_4 = ZLoc.t("Dialogs", HunterDialog.groupId + "_HunterRequirementsText", {numHunters:_loc_7, officer:_loc_8});
            }
            _loc_5 = ASwingHelper.makeMultilineText(_loc_4 ,130,EmbeddedArt.defaultFontNameBold ,TextFormatAlign.CENTER ,10,_loc_3 );
            _loc_2.appendAll(ASwingHelper.verticalStrut(5), _loc_5);
            ASwingHelper.setForcedHeight(_loc_2, 30);
            m_claimPanel.append(_loc_2);
            this.append(m_claimPanel);
            return;
        }//end

         protected void  makeRewardPanel ()
        {
            DisplayObject _loc_4 =null ;
            DisplayObject _loc_5 =null ;
            MarginBackground _loc_6 =null ;
            ASColor _loc_7 =null ;
            DisplayObject _loc_8 =null ;
            JLabel _loc_9 =null ;
            JPanel _loc_10 =null ;
            DisplayObject _loc_11 =null ;
            JLabel _loc_12 =null ;
            JPanel _loc_13 =null ;
            DisplayObject _loc_14 =null ;
            JLabel _loc_15 =null ;
            JPanel _loc_16 =null ;
            DisplayObject _loc_17 =null ;
            JLabel _loc_18 =null ;
            JPanel _loc_19 =null ;
            _loc_1 = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.TOP );
            _loc_2 = EmbeddedArt.darkBlueTextColor;
            if (!this.m_meetsRequirement)
            {
                _loc_2 = EmbeddedArt.lightGrayTextColor;
            }
            _loc_3 = ASwingHelper.makeLabel(ZLoc.t("Dialogs",HunterDialog.groupId +"_rewardsLabel"),EmbeddedArt.defaultFontNameBold ,15,_loc_2 );
            _loc_1.appendAll(_loc_3, ASwingHelper.verticalStrut(5));
            if (this.m_bandit.getRewardItems().length > 0)
            {
                _loc_4 =(DisplayObject) new HunterDialog.assetDict.get("rewardCard_locked");
                _loc_5 =(DisplayObject) new HunterDialog.assetDict.get("rewardCard_unlocked");
                _loc_6 = new MarginBackground(_loc_5, new Insets(0, 26, 6, 26));
                if (!this.m_meetsRequirement)
                {
                    _loc_6 = new MarginBackground(_loc_4, new Insets(0, 26, 6, 26));
                }
                m_rewardPanel.setBackgroundDecorator(_loc_6);
            }
            else
            {
                _loc_7 = new ASColor(EmbeddedArt.darkBlueTextColor);
                if (!this.m_meetsRequirement)
                {
                    _loc_7 = new ASColor(EmbeddedArt.lightGrayTextColor);
                }
                if (this.m_bandit.getRewardDooberTotals().get(Doober.DOOBER_XP) != null)
                {
                    _loc_8 =(DisplayObject) new EmbeddedArt.smallXPIcon();
                    _loc_9 = new JLabel(ZLoc.t("Dialogs", "NumXP", {num:this.m_bandit.getRewardDooberTotals().get(Doober.DOOBER_XP)}));
                    _loc_9.setFont(ASwingHelper.getBoldFont(14));
                    _loc_9.setForeground(_loc_7);
                    _loc_10 = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.LEFT);
                    _loc_10.appendAll(ASwingHelper.horizontalStrut(15), new AssetPane(_loc_8));
                    _loc_10.appendAll(ASwingHelper.horizontalStrut(5), _loc_9);
                    m_rewardPanel.append(_loc_10);
                }
                if (this.m_bandit.getRewardDooberTotals().get(Doober.DOOBER_GOODS) != null)
                {
                    _loc_11 =(DisplayObject) new EmbeddedArt.smallGoodsIcon();
                    _loc_12 = new JLabel(ZLoc.t("Dialogs", "NumGoods", {num:this.m_bandit.getRewardDooberTotals().get(Doober.DOOBER_GOODS)}));
                    _loc_12.setFont(ASwingHelper.getBoldFont(14));
                    _loc_12.setForeground(_loc_7);
                    _loc_13 = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.LEFT);
                    _loc_13.appendAll(ASwingHelper.horizontalStrut(15), new AssetPane(_loc_11));
                    _loc_13.appendAll(ASwingHelper.horizontalStrut(5), _loc_12);
                    m_rewardPanel.append(_loc_13);
                }
                if (this.m_bandit.getRewardDooberTotals().get(Doober.DOOBER_COIN) != null)
                {
                    _loc_14 =(DisplayObject) new EmbeddedArt.smallCoinIcon();
                    _loc_15 = new JLabel(ZLoc.t("Dialogs", "NumCoins", {num:this.m_bandit.getRewardDooberTotals().get(Doober.DOOBER_COIN)}));
                    _loc_15.setFont(ASwingHelper.getBoldFont(14));
                    _loc_15.setForeground(_loc_7);
                    _loc_16 = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.LEFT);
                    _loc_16.appendAll(ASwingHelper.horizontalStrut(15), new AssetPane(_loc_14));
                    _loc_16.appendAll(ASwingHelper.horizontalStrut(5), _loc_15);
                    m_rewardPanel.append(_loc_16);
                }
                if (this.m_bandit.getRewardDooberTotals().get(Doober.DOOBER_ENERGY) != null)
                {
                    _loc_17 =(DisplayObject) new EmbeddedArt.smallEnergyIcon();
                    _loc_18 = new JLabel(ZLoc.t("Dialogs", "NumEnergy", {num:this.m_bandit.getRewardDooberTotals().get(Doober.DOOBER_ENERGY)}));
                    _loc_18.setFont(ASwingHelper.getBoldFont(14));
                    _loc_18.setForeground(_loc_7);
                    _loc_19 = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.LEFT);
                    _loc_19.appendAll(ASwingHelper.horizontalStrut(15), new AssetPane(_loc_17));
                    _loc_19.appendAll(ASwingHelper.horizontalStrut(5), _loc_18);
                    m_rewardPanel.append(_loc_19);
                }
            }
            ASwingHelper.setForcedHeight(m_rewardPanel, 100);
            _loc_1.append(m_rewardPanel);
            this.appendAll(ASwingHelper.verticalStrut(5), _loc_1);
            return;
        }//end

         protected void  makeIconPanel ()
        {
            ASwingHelper.setForcedHeight(m_iconPanel, BANDIT_ICON_HEIGHT);
            this.appendAll(ASwingHelper.verticalStrut(5), m_iconPanel);
            return;
        }//end

         protected void  loadAssets ()
        {
            if (m_dummy == false)
            {
                if (this.m_bandit.wasCaptured())
                {
                    if (this.m_bandit.caughtUrl)
                    {
                        m_iconLoader = LoadingManager.load(Global.getAssetURL(this.m_bandit.caughtUrl), this.onIconLoad, LoadingManager.PRIORITY_HIGH);
                    }
                }
                else if (this.m_bandit.portraitUrl)
                {
                    m_iconLoader = LoadingManager.load(Global.getAssetURL(this.m_bandit.portraitUrl), this.onIconLoad, LoadingManager.PRIORITY_HIGH);
                }
                if (this.m_bandit.getRewardItems().length > 0)
                {
                    m_rewardLoader = LoadingManager.load(Global.gameSettings().getImageByName(this.m_bandit.getRewardItems().get(0), "icon"), onRewardLoad, LoadingManager.PRIORITY_HIGH);
                }
            }
            return;
        }//end

         protected void  onIconLoad (Event event )
        {
            if (m_iconLoader && m_iconLoader.content)
            {
                m_icon = m_iconLoader.content;
            }
            if (m_icon)
            {
                placeIcon();
            }
            setGridListCellStatus(m_gridList, false, m_index);
            return;
        }//end

         protected void  placeReward ()
        {
            Sprite _loc_1 =new Sprite ();
            _loc_1.addChild(m_reward);
            m_rewardPanel.addChild(_loc_1);
            if (!this.m_meetsRequirement)
            {
                _loc_1.alpha = 0.5;
            }
            _loc_1.x = m_rewardPanel.width / 2 - m_reward.width / 2;
            _loc_1.y = m_rewardPanel.height / 2 - m_reward.height / 2;
            ASwingHelper.prepare(this);
            return;
        }//end

        protected void  onRollOver (MouseEvent event )
        {
            dispatchEvent(new GenericObjectEvent(GenericObjectEvent.REFRESH_BANDIT_INFO, this.m_bandit.description, true));
            return;
        }//end

    }



