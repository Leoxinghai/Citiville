package Display.ValentineUI;

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
import Engine.Managers.*;
import Modules.achievements.data.*;
import Modules.achievements.events.*;
//import flash.display.*;
//import flash.events.*;
//import flash.text.*;
import org.aswing.*;
import org.aswing.geom.*;
import com.xinghai.Debug;

    public class AchievementsCell extends DataItemCell
    {
        protected Achievement m_achieve ;
        protected DisplayObject m_bg ;
        protected int m_position ;
        protected JPanel m_iconPanel ;
        protected JPanel m_headerPanel ;
        protected JPanel m_rewardPanel ;
        protected JPanel m_claimPanel ;
        protected Loader m_iconLoader ;
        protected DisplayObject m_icon ;
        protected Loader m_rewardLoader ;
        protected DisplayObject m_reward ;
        protected boolean m_dummy =false ;
        protected  int ICON_WIDTH =64;
        protected  int ICON_HEIGHT =64;

        public  AchievementsCell (int param1 =0)
        {
            this.m_position = param1;
            super(new SoftBoxLayout(SoftBoxLayout.Y_AXIS, 0, SoftBoxLayout.TOP));
            this.addEventListener(Event.ADDED_TO_STAGE, this.onAddedToStage, false, 0, true);
            return;
        }//end

        protected void  onAddedToStage (Event event )
        {
            this.removeEventListener(Event.ADDED_TO_STAGE, this.onAddedToStage);
            this.addEventListener(Event.REMOVED_FROM_STAGE, this.onRemovedFromStage, false, 0, true);
            Global.achievementsManager.addEventListener(AchievementGroupsUpdatedEvent.UPDATED, this.refreshCell, false, 0, true);
            return;
        }//end

        protected void  onRemovedFromStage (Event event )
        {
            Global.achievementsManager.removeEventListener(AchievementGroupsUpdatedEvent.UPDATED, this.refreshCell);
            this.removeEventListener(Event.REMOVED_FROM_STAGE, this.onRemovedFromStage);
            return;
        }//end

         public void  setCellValue (Object param1)
        {
            this.m_achieve =(Achievement) param1;
            if (this.m_achieve.id == null)
            {
                this.m_dummy = true;
            }
            this.buildCell();
            this.loadAssets();
            return;
        }//end

        protected void  refreshCell (Event event )
        {
            this.removeAll();
            this.buildCell();
            this.loadAssets();
            return;
        }//end

        protected void  loadAssets ()
        {
            if (this.m_dummy == false)
            {
                if (this.m_achieve.iconUrl)
                {
                    this.m_iconLoader = LoadingManager.load(Global.getAssetURL(this.m_achieve.iconUrl), this.onIconLoad, LoadingManager.PRIORITY_HIGH);
                }
                if (this.m_achieve.rewards.get("item") != null)
                {
                    this.m_rewardLoader = LoadingManager.load(Global.gameSettings().getImageByName(this.m_achieve.rewards.get("item"), "icon"), this.onRewardLoad, LoadingManager.PRIORITY_HIGH);
                }
            }
            return;
        }//end

        protected void  buildCell ()
        {
            this.m_iconPanel = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.CENTER);
            this.m_headerPanel = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.CENTER);
            this.m_rewardPanel = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.CENTER);
            this.m_claimPanel = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.CENTER);
            this.buildBackground();
            if (this.m_dummy == false)
            {
                this.makeIconPanel();
                this.makeHeaderPanel();
                this.makeRewardPanel();
                this.makeClaimPanel();
            }
            ASwingHelper.prepare(this);
            return;
        }//end

        protected void  buildBackground ()
        {
            DisplayObject _loc_1 =(DisplayObject)new ValentineDialog.assetDict.get( "left_blue");
            DisplayObject _loc_2 =(DisplayObject)new ValentineDialog.assetDict.get( "middle_blue");
            DisplayObject _loc_3 =(DisplayObject)new ValentineDialog.assetDict.get( "middle_white");
            DisplayObject _loc_4 =(DisplayObject)new ValentineDialog.assetDict.get( "right_white");
            _loc_5 = this.m_position % 4;
            if (this.m_position % 4 == 1)
            {
                this.m_bg = _loc_1;
            }
            else if (_loc_5 == 2)
            {
                this.m_bg = _loc_3;
            }
            else if (_loc_5 == 3)
            {
                this.m_bg = _loc_2;
            }
            else
            {
                this.m_bg = _loc_4;
            }
            MarginBackground _loc_6 =new MarginBackground(this.m_bg );
            this.setBackgroundDecorator(_loc_6);
            this.setPreferredSize(new IntDimension(this.m_bg.width, this.m_bg.height));
            this.setMinimumSize(new IntDimension(this.m_bg.width, this.m_bg.height));
            this.setMaximumSize(new IntDimension(this.m_bg.width, this.m_bg.height));
            return;
        }//end

        protected void  makeIconPanel ()
        {
            this.m_iconPanel.setPreferredHeight(this.ICON_HEIGHT);
            this.m_iconPanel.setMinimumHeight(this.ICON_HEIGHT);
            this.m_iconPanel.setMaximumHeight(this.ICON_HEIGHT);
            this.appendAll(ASwingHelper.verticalStrut(10), this.m_iconPanel);
            return;
        }//end

        protected void  makeHeaderPanel ()
        {
            String _loc_4 =null ;
            _loc_1 = EmbeddedArt.blueTextColor;
            if (this.m_achieve.state == Achievement.REWARDED)
            {
                _loc_1 = EmbeddedArt.lightGrayTextColor;
            }
            _loc_2 = ZLoc.t("Achievements",this.m_achieve.displayTitle );
            _loc_3 = ASwingHelper.makeMultilineCapsText(_loc_2 ,130,EmbeddedArt.titleFont ,TextFormatAlign.CENTER ,13,_loc_1 );
            if (this.m_achieve.state == Achievement.FINISHED)
            {
                _loc_4 = ZLoc.t("Achievements", "Finished_Message");
            }
            else if (this.m_achieve.state == Achievement.REWARDED)
            {
                _loc_4 = ZLoc.t("Achievements", "Rewarded_Message");
            }
            else
            {
                _loc_4 = ZLoc.t("Achievements", this.m_achieve.displayDescription);
            }
            _loc_5 = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.LEFT );
            _loc_6 = ASwingHelper.makeMultilineText(_loc_4 ,130,EmbeddedArt.defaultFontNameBold ,TextFormatAlign.LEFT ,10,_loc_1 );
            _loc_5.appendAll(ASwingHelper.horizontalStrut(3), _loc_6);
            this.m_headerPanel.appendAll(_loc_3, _loc_5);
            this.m_headerPanel.setPreferredHeight(this.ICON_HEIGHT);
            this.m_headerPanel.setMinimumHeight(this.ICON_HEIGHT);
            this.m_headerPanel.setMaximumHeight(this.ICON_HEIGHT);
            this.appendAll(ASwingHelper.verticalStrut(10), this.m_headerPanel);
            return;
        }//end

        protected void  makeRewardPanel ()
        {
            Debug.debug4("AchievementsCell."+"makeRewardPanel");
            DisplayObject _loc_1 =null ;
            DisplayObject _loc_2 =null ;
            MarginBackground _loc_3 =null ;
            ASColor _loc_4 =null ;
            DisplayObject _loc_5 =null ;
            JLabel _loc_6 =null ;
            JPanel _loc_7 =null ;
            DisplayObject _loc_8 =null ;
            JLabel _loc_9 =null ;
            JPanel _loc_10 =null ;
            DisplayObject _loc_11 =null ;
            JLabel _loc_12 =null ;
            JPanel _loc_13 =null ;
            DisplayObject _loc_14 =null ;
            JLabel _loc_15 =null ;
            JPanel _loc_16 =null ;
            if (this.m_achieve.rewards.get("item") != null)
            {
                _loc_1 =(DisplayObject) new ValentineDialog.assetDict.get("rewardCard_locked");
                _loc_2 =(DisplayObject) new ValentineDialog.assetDict.get("rewardCard_unlocked");
                if (this.m_achieve.state == Achievement.REWARDED)
                {
                    _loc_3 = new MarginBackground(_loc_1, new Insets(0, 26, 6, 26));
                }
                else
                {
                    _loc_3 = new MarginBackground(_loc_2, new Insets(0, 26, 6, 26));
                }
                this.m_rewardPanel.setBackgroundDecorator(_loc_3);
            }
            else
            {
                _loc_4 = new ASColor(EmbeddedArt.blueTextColor);
                if (this.m_achieve.state == Achievement.REWARDED)
                {
                    _loc_4 = new ASColor(EmbeddedArt.lightGrayTextColor);
                }
                if (this.m_achieve.rewards.get("xp") != null)
                {
                    _loc_5 =(DisplayObject) new EmbeddedArt.smallXPIcon();
                    _loc_6 = new JLabel(ZLoc.t("Dialogs", "NumXP", {num:this.m_achieve.rewards.get("xp")}));
                    _loc_6.setFont(ASwingHelper.getBoldFont(14));
                    _loc_6.setForeground(_loc_4);
                    _loc_7 = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.LEFT);
                    _loc_7.appendAll(ASwingHelper.horizontalStrut(15), new AssetPane(_loc_5));
                    _loc_7.appendAll(ASwingHelper.horizontalStrut(5), _loc_6);
                    this.m_rewardPanel.append(_loc_7);
                }
                if (this.m_achieve.rewards.get("goods") != null)
                {
                    _loc_8 =(DisplayObject) new EmbeddedArt.smallGoodsIcon();
                    _loc_9 = new JLabel(ZLoc.t("Dialogs", "NumGoods", {num:this.m_achieve.rewards.get("goods")}));
                    _loc_9.setFont(ASwingHelper.getBoldFont(14));
                    _loc_9.setForeground(_loc_4);
                    _loc_10 = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.LEFT);
                    _loc_10.appendAll(ASwingHelper.horizontalStrut(15), new AssetPane(_loc_8));
                    _loc_10.appendAll(ASwingHelper.horizontalStrut(5), _loc_9);
                    this.m_rewardPanel.append(_loc_10);
                }
                if (this.m_achieve.rewards.get("coins") != null)
                {
                    _loc_11 =(DisplayObject) new EmbeddedArt.smallCoinIcon();
                    _loc_12 = new JLabel(ZLoc.t("Dialogs", "NumCoins", {num:this.m_achieve.rewards.get("coins")}));
                    _loc_12.setFont(ASwingHelper.getBoldFont(14));
                    _loc_12.setForeground(_loc_4);
                    _loc_13 = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.LEFT);
                    _loc_13.appendAll(ASwingHelper.horizontalStrut(15), new AssetPane(_loc_11));
                    _loc_13.appendAll(ASwingHelper.horizontalStrut(5), _loc_12);
                    this.m_rewardPanel.append(_loc_13);
                }
                if (this.m_achieve.rewards.get("energy") != null)
                {
                    _loc_14 =(DisplayObject) new EmbeddedArt.smallEnergyIcon();
                    _loc_15 = new JLabel(ZLoc.t("Dialogs", "NumEnergy", {num:this.m_achieve.rewards.get("energy")}));
                    _loc_15.setFont(ASwingHelper.getBoldFont(14));
                    _loc_15.setForeground(_loc_4);
                    _loc_16 = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.LEFT);
                    _loc_16.appendAll(ASwingHelper.horizontalStrut(15), new AssetPane(_loc_14));
                    _loc_16.appendAll(ASwingHelper.horizontalStrut(5), _loc_15);
                    this.m_rewardPanel.append(_loc_16);
                }
            }
            this.m_rewardPanel.setPreferredHeight(100);
            this.m_rewardPanel.setMinimumHeight(100);
            this.m_rewardPanel.setMaximumHeight(100);
            this.appendAll(ASwingHelper.verticalStrut(6), this.m_rewardPanel);
            return;
        }//end

        protected void  makeClaimPanel ()
        {
            CustomButton _loc_2 =null ;
            String _loc_3 =null ;
            AssetPane _loc_4 =null ;
            JLabel _loc_5 =null ;
            AssetIcon _loc_6 =null ;
            JPanel _loc_7 =null ;
            Sprite _loc_8 =null ;
            DisplayObject _loc_9 =null ;
            AssetPane _loc_10 =null ;
            _loc_1 = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.CENTER );
            if (Global.achievementsManager.isUpdated)
            {
                if (this.m_achieve.state == Achievement.STARTED)
                {
                    _loc_3 = ZLoc.t("Dialogs", "ValUI_unlocknow");
                    _loc_5 = new JLabel(ZLoc.t("Dialogs", "ValUI_unlocknow"));
                    _loc_5.setForeground(new ASColor(EmbeddedArt.blueTextColor));
                    _loc_5.setFont(ASwingHelper.getBoldFont(12));
                    _loc_6 = new AssetIcon(new EmbeddedArt.icon_cash());
                    _loc_7 = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.CENTER);
                    _loc_2 = new CustomButton(String(this.m_achieve.cashcost), _loc_6, "CashButtonUI");
                    _loc_2.addEventListener(MouseEvent.CLICK, this.onCashBuy, false, 0, true);
                    _loc_7.append(_loc_2);
                    _loc_1.appendAll(_loc_5, _loc_7);
                }
                else if (this.m_achieve.state == Achievement.REWARDED)
                {
                    _loc_8 = new Sprite();
                    _loc_9 =(DisplayObject) new ValentineDialog.assetDict.get("rewardClaimed");
                    _loc_8.addChild(_loc_9);
                    _loc_10 = new AssetPane(_loc_8);
                    _loc_8.x = _loc_1.width / 2 - _loc_9.x / 2;
                    _loc_1.append(_loc_10);
                }
                else if (this.m_achieve.state == Achievement.FINISHED)
                {
                    _loc_3 = TextFieldUtil.formatSmallCapsString(ZLoc.t("Dialogs", "Collect"));
                    _loc_2 = new CustomButton(_loc_3, null, "GreenButtonUI");
                    _loc_2.setFont(ASwingHelper.getTitleFont(13));
                    _loc_2.addEventListener(MouseEvent.MOUSE_UP, this.onCollect, false, 0, true);
                    _loc_1.appendAll(ASwingHelper.verticalStrut(17), _loc_2);
                }
                if (_loc_2)
                {
                    _loc_2.setPreferredSize(new IntDimension(110, 30));
                    _loc_2.setMinimumSize(new IntDimension(110, 30));
                    _loc_2.setMaximumSize(new IntDimension(110, 30));
                }
            }
            this.m_claimPanel.append(_loc_1);
            this.append(this.m_claimPanel);
            return;
        }//end

         protected void  onIconLoad (Event event )
        {
            if (this.m_iconLoader && this.m_iconLoader.content)
            {
                this.m_icon = this.m_iconLoader.content;
            }
            _loc_2 = scaleToFit(this.m_icon);
            this.m_icon.scaleY = scaleToFit(this.m_icon);
            this.m_icon.scaleX = _loc_2;
            if (this.m_icon instanceof Bitmap)
            {
                ((Bitmap)this.m_icon).smoothing = true;
            }
            this.placeIcon();
            setGridListCellStatus(m_gridList, false, m_index);
            return;
        }//end

        protected void  onRewardLoad (Event event )
        {
            if (this.m_rewardLoader && this.m_rewardLoader.content)
            {
                this.m_reward = this.m_rewardLoader.content;
            }
            _loc_2 = scaleToFit(this.m_reward);
            this.m_reward.scaleY = scaleToFit(this.m_reward);
            this.m_reward.scaleX = _loc_2;
            if (this.m_reward instanceof Bitmap)
            {
                ((Bitmap)this.m_reward).smoothing = true;
            }
            this.placeReward();
            setGridListCellStatus(m_gridList, false, m_index);
            return;
        }//end

        protected void  placeIcon ()
        {
            Sprite _loc_1 =new Sprite ();
            _loc_1.addChild(this.m_icon);
            this.m_iconPanel.addChild(_loc_1);
            _loc_1.x = this.m_iconPanel.width / 2 - this.m_icon.width / 2;
            ASwingHelper.prepare(this);
            return;
        }//end

        protected void  placeReward ()
        {
            Sprite _loc_1 =new Sprite ();
            _loc_1.addChild(this.m_reward);
            this.m_rewardPanel.addChild(_loc_1);
            if (this.m_achieve.state == Achievement.REWARDED)
            {
                _loc_1.alpha = 0.5;
            }
            _loc_1.x = this.m_rewardPanel.width / 2 - this.m_reward.width / 2;
            _loc_1.y = this.m_rewardPanel.height / 2 - this.m_reward.height / 2;
            ASwingHelper.prepare(this);
            return;
        }//end

        protected void  onCollect (Event event )
        {
            if (this.m_achieve == null || this.m_achieve.state != Achievement.FINISHED)
            {
                return;
            }
            Global.achievementsManager.claimReward(this.m_achieve.groupName, this.m_achieve.id);
            if (this.m_achieve.rewards.get("item") == null)
            {
                UI.displayValRewardsMessage(ZLoc.t("Dialogs", "ValUI_rewardClaimed_stats"), GenericDialogView.TYPE_OK, null, "ValUI_rewards", false, null, "ValUI_rewards");
            }
            else
            {
                UI.displayValRewardsMessage(ZLoc.t("Dialogs", "ValUI_rewardClaimed_items"), GenericDialogView.TYPE_OK, null, "ValUI_rewards", false, null, "ValUI_rewards");
            }
            this.removeAll();
            this.m_achieve.state = Achievement.REWARDED;
            this.buildCell();
            this.loadAssets();
            return;
        }//end

        protected void  onCashBuy (Event event )
        {
            if (this.m_achieve == null || this.m_achieve.state != Achievement.STARTED)
            {
                return;
            }
            _loc_2 =Global.achievementsManager.purchaseFinish(this.m_achieve.groupName ,this.m_achieve.id );
            if (_loc_2)
            {
                this.m_achieve.state = Achievement.FINISHED;
                this.onCollect(event);
                this.maybeRefreshAchievements();
            }
            else
            {
                UI.displayPopup(new GetCoinsDialog(ZLoc.t("Dialogs", "ImpulseMarketCash"), "unlockAchievement", GenericDialogView.TYPE_GETCASH, null, true));
            }
            return;
        }//end

        protected void  maybeRefreshAchievements ()
        {
            Global.achievementsManager.startGroupUpdate("vday_2011");
            TransactionManager.sendAllTransactions(true);
            return;
        }//end

    }



