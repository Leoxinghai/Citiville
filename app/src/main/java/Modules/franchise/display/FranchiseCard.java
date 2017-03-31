package Modules.franchise.display;

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
import Display.aswingui.*;
import Engine.Managers.*;
import Modules.franchise.data.*;
import Modules.franchise.transactions.*;
//import flash.display.*;
//import flash.events.*;
//import flash.filters.*;
//import flash.text.*;
import org.aswing.*;
import org.aswing.geom.*;

    public class FranchiseCard extends DataItemCell
    {
        protected DisplayObject m_content ;
        private JPanel m_cardPanel ;
        private JPanel m_headerPanel ;
        private JPanel m_bodyPanel ;
        private JPanel m_footerPanel ;
        private JPanel m_itemIconHolder ;
        private JPanel m_itemIconPane ;
        private int m_color ;
        private int m_position ;
        private String m_status ;
        private double m_collectAmount ;
        private double m_collectAmountBeforeStars ;
        private double m_witherAmount ;
        private FranchiseExpansionData m_franchiseExpansion ;
        private static  double ROW_RED =0.9;
        private static  double ROW_GREEN =0.94;
        private static  double ROW_BLUE =0.98;
        private static  int BUTTON_HEIGHT =35;
        private static  int BUTTON_WIDTH =165;

        public  FranchiseCard (int param1 =0,int param2 =0,LayoutManager param3 =null )
        {
            this.m_color = param1;
            this.m_position = param2;
            super(new SoftBoxLayout(SoftBoxLayout.X_AXIS, 0, SoftBoxLayout.LEFT));
            return;
        }//end

         public void  setCellValue (Object param1)
        {
            _loc_2 = param1as FranchiseExpansionData ;
            this.setExpansionCellValue(_loc_2);
            return;
        }//end

        private void  setExpansionCellValue (FranchiseExpansionData param1 )
        {
            Player _loc_2 =null ;
            String _loc_3 =null ;
            this.m_franchiseExpansion = param1;
            switch(param1.starRating)
            {
                case StarRatingComponent.NONE:
                {
                    this.m_status = FranchiseMenu.STATUS_BLANK;
                    break;
                }
                case StarRatingComponent.DISABLED:
                {
                    this.m_status = FranchiseMenu.STATUS_ADD_NEIGHBOR;
                    break;
                }
                default:
                {
                    if (!this.m_franchiseExpansion.isOpen() || this.m_franchiseExpansion.timeLastSupplied > this.m_franchiseExpansion.timeLastOperated)
                    {
                        this.m_status = FranchiseMenu.STATUS_NEED_REMIND;
                    }
                    else if (GlobalEngine.getTimer() / 1000 < this.m_franchiseExpansion.timeLastSupplied + FranchiseMenu.dailyCycleDelta)
                    {
                        this.m_status = FranchiseMenu.STATUS_COLLECT_WAITING;
                    }
                    else
                    {
                        this.m_status = FranchiseMenu.STATUS_NEED_REFILL;
                    }
                    _loc_2 = Global.player.findFriendById(param1.locationUid);
                    if (_loc_2)
                    {
                        _loc_3 = _loc_2.snUser.picture;
                        m_loader = LoadingManager.load(_loc_3, onIconLoad, LoadingManager.PRIORITY_HIGH);
                    }
                    break;
                    break;
                }
            }
            this.buildCell();
            return;
        }//end

        protected void  buildCell ()
        {
            Array _loc_5 =null ;
            Array _loc_6 =null ;
            this.m_cardPanel = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.TOP);
            this.m_headerPanel = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.TOP);
            this.m_bodyPanel = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.CENTER);
            this.m_footerPanel = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.CENTER);
            DisplayObject _loc_1 =(DisplayObject)new FranchiseMenuUI.m_assetDict.get( "fr_row_top");
            DisplayObject _loc_2 =(DisplayObject)new FranchiseMenuUI.m_assetDict.get( "fr_row_mid");
            DisplayObject _loc_3 =(DisplayObject)new FranchiseMenuUI.m_assetDict.get( "fr_row_bot");
            if (this.m_position % 4 == 0)
            {
                this.m_content = _loc_3;
            }
            else
            {
                this.m_content = _loc_2;
            }
            if (this.m_color % 2)
            {
                _loc_5 = this.m_content.filters;
                _loc_6 = new Array();
                _loc_6 = _loc_6.concat(.get(ROW_RED, 0, 0, 0, 0));
                _loc_6 = _loc_6.concat(.get(0, ROW_GREEN, 0, 0, 0));
                _loc_6 = _loc_6.concat(.get(0, 0, ROW_BLUE, 0, 0));
                _loc_6 = _loc_6.concat(.get(0, 0, 0, 1, 0));
                _loc_5.push(new ColorMatrixFilter(_loc_6));
                this.m_content.filters = _loc_5;
            }
            MarginBackground _loc_4 =new MarginBackground(this.m_content ,new Insets(0,0,0,0));
            this.m_cardPanel.setBackgroundDecorator(_loc_4);
            this.m_cardPanel.setPreferredSize(new IntDimension(this.m_content.width, this.m_content.height));
            this.m_cardPanel.setMinimumSize(new IntDimension(this.m_content.width, this.m_content.height));
            this.m_cardPanel.setMaximumSize(new IntDimension(this.m_content.width, this.m_content.height));
            ASwingHelper.prepare(this.m_cardPanel);
            this.makeHeaderPanel();
            this.makeBodyPanel();
            this.makeFooterPanel();
            this.append(this.m_cardPanel);
            this.setPreferredWidth(this.m_content.width);
            this.setMinimumWidth(this.m_content.width);
            this.setMaximumWidth(this.m_content.width);
            ASwingHelper.prepare(this);
            return;
        }//end

        private void  makeHeaderPanel ()
        {
            JLabel _loc_4 =null ;
            Player _loc_8 =null ;
            String _loc_9 =null ;
            DisplayObject _loc_1 =(DisplayObject)new FranchiseMenuUI.m_assetDict.get( "fr_rule");
            AssetPane _loc_2 =new AssetPane(_loc_1 );
            this.m_itemIconHolder = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.CENTER);
            this.m_itemIconPane = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.CENTER);
            this.m_itemIconHolder.append(this.m_itemIconPane);
            this.m_itemIconHolder.setMinimumSize(new IntDimension(50, 50));
            this.m_itemIconHolder.setMaximumSize(new IntDimension(50, 50));
            this.m_itemIconHolder.setPreferredSize(new IntDimension(50, 50));
            _loc_3 = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.LEFT );
            _loc_5 = TextFieldUtil.getLocaleFontSize(16,12,.get( {locale size "ja",16) });
            switch(this.m_status)
            {
                case FranchiseMenu.STATUS_ADD_NEIGHBOR:
                {
                    _loc_4 = new JLabel(ZLoc.t("Dialogs", "NextLocation"));
                    _loc_4.setFont(ASwingHelper.getBoldFont(_loc_5));
                    _loc_4.setForeground(new ASColor(FranchiseMenu.COLOR_MENU_GRAY));
                    break;
                }
                case FranchiseMenu.STATUS_BLANK:
                {
                    _loc_4 = new JLabel("");
                    break;
                }
                default:
                {
                    _loc_8 = Global.player.findFriendById(this.m_franchiseExpansion.locationUid);
                    _loc_9 = Global.franchiseManager.getMapOwnerFirstName(this.m_franchiseExpansion.locationUid);
                    _loc_4 = new JLabel(ZLoc.t("Main", "FriendNameSuffix", {name:ZLoc.tn(_loc_9)}));
                    _loc_4.setFont(ASwingHelper.getBoldFont(_loc_5));
                    _loc_4.setForeground(new ASColor(FranchiseMenu.COLOR_MENU_STANDARD));
                    break;
                    break;
                }
            }
            _loc_6 = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.LEFT );
            _loc_6.append(ASwingHelper.verticalStrut(6));
            _loc_6.append(_loc_4);
            ASwingHelper.prepare(_loc_6);
            _loc_7 = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.LEFT );
            _loc_7.appendAll(ASwingHelper.horizontalStrut(3), new StarRatingComponent(this.m_franchiseExpansion.starRating, true));
            _loc_3.append(_loc_6);
            _loc_3.append(_loc_7);
            this.m_headerPanel.append(ASwingHelper.horizontalStrut(5));
            this.m_headerPanel.append(this.m_itemIconHolder);
            this.m_headerPanel.append(_loc_3);
            this.m_headerPanel.setPreferredWidth(180);
            this.m_headerPanel.setMinimumWidth(180);
            this.m_headerPanel.setMaximumWidth(180);
            ASwingHelper.prepare(this.m_headerPanel);
            this.m_cardPanel.append(this.m_headerPanel);
            this.m_cardPanel.appendAll(_loc_2);
            return;
        }//end

        private void  makeBodyPanel ()
        {
            JTextField _loc_5 =null ;
            String _loc_7 =null ;
            String _loc_12 =null ;
            this.m_bodyPanel.removeAll();
            DisplayObject _loc_1 =(DisplayObject)new FranchiseMenuUI.m_assetDict.get( "fr_rule");
            AssetPane _loc_2 =new AssetPane(_loc_1 );
            _loc_3 = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.CENTER );
            _loc_4 = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.CENTER );
            TextFormat _loc_6 =new TextFormat ();
            _loc_6.font = EmbeddedArt.defaultFontNameBold;
            _loc_6.color = FranchiseMenu.COLOR_MENU_STANDARD;
            _loc_6.align = TextFormatAlign.CENTER;
            _loc_6.size = 14;
            _loc_8 =Global.player.findFriendById(this.m_franchiseExpansion.locationUid );
            _loc_9 =Global.franchiseManager.getMapOwnerFirstName(this.m_franchiseExpansion.locationUid );
            switch(this.m_status)
            {
                case FranchiseMenu.STATUS_NEED_REFILL:
                {
                    _loc_12 = FranchiseMenu.selectedFranchise.saleCost.toString();
                    _loc_7 = ZLoc.t("Dialogs", "Status_NeedRefill1") + "\n" + ZLoc.t("Dialogs", "Status_NeedRefill2", {amount:ASwingHelper.colorize(_loc_12, 16738816)});
                    break;
                }
                case FranchiseMenu.STATUS_NEED_REMIND:
                {
                    _loc_7 = ZLoc.t("Dialogs", "Status_NeedRemind1", {neighbor:_loc_9}) + "\n" + ZLoc.t("Dialogs", "Status_NeedRemind2");
                    break;
                }
                case FranchiseMenu.STATUS_DONE_REMIND:
                {
                    _loc_7 = ZLoc.t("Dialogs", "Status_NeedRemind1", {neighbor:_loc_9}) + "\n" + ASwingHelper.colorize(ZLoc.t("Dialogs", "Status_NeedRemind2"), FranchiseMenu.COLOR_MENU_GRAY);
                    break;
                }
                case FranchiseMenu.STATUS_ADD_NEIGHBOR:
                {
                    _loc_7 = ZLoc.t("Dialogs", "Status_AddNeighbor");
                    break;
                }
                case FranchiseMenu.STATUS_COLLECT_NOWITHER:
                {
                    _loc_7 = ZLoc.t("Dialogs", "Status_CollectNoWither1", {coin:ASwingHelper.colorize(this.m_collectAmountBeforeStars.toString(), 43520), star:ASwingHelper.colorize(this.m_franchiseExpansion.starRating.toString(), 43520)}) + "\n" + ZLoc.t("Dialogs", "Status_CollectNoWither2", {total:ASwingHelper.colorize(this.m_collectAmount.toString(), 43520)});
                    break;
                }
                case FranchiseMenu.STATUS_COLLECT_WITHER:
                {
                    _loc_7 = ZLoc.t("Dialogs", "Status_CollectWither1", {coin:ASwingHelper.colorize(this.m_collectAmountBeforeStars.toString(), 43520), star:ASwingHelper.colorize(this.m_franchiseExpansion.starRating.toString(), 43520), total:ASwingHelper.colorize(this.m_collectAmount.toString(), 43520)}) + "\n" + ZLoc.t("Dialogs", "Status_CollectWither2", {coin:ASwingHelper.colorize(this.m_witherAmount.toString(), 11141120)});
                    break;
                }
                case FranchiseMenu.STATUS_COLLECT_WAITING:
                {
                    _loc_7 = ZLoc.t("Dialogs", "Status_CollectWaiting");
                    break;
                }
                case FranchiseMenu.STATUS_BLANK:
                {
                    _loc_7 = "";
                    break;
                }
                default:
                {
                    _loc_7 = "";
                    break;
                    break;
                }
            }
            _loc_5 = ASwingHelper.makeTextField("", EmbeddedArt.defaultFontNameBold, 15, FranchiseMenu.COLOR_MENU_STANDARD);
            _loc_5.setWordWrap(true);
            _loc_5.setTextFormat(_loc_6);
            _loc_5.setHtmlText(_loc_7);
            int _loc_10 =240;
            int _loc_11 =260;
            if (this.m_status == FranchiseMenu.STATUS_COLLECT_NOWITHER || this.m_status == FranchiseMenu.STATUS_COLLECT_WITHER || this.m_status == FranchiseMenu.STATUS_COLLECT_WAITING)
            {
                _loc_10 = 390;
                _loc_11 = 410;
            }
            _loc_5.setMinimumWidth(_loc_10);
            _loc_5.setMaximumWidth(_loc_10);
            _loc_5.setPreferredWidth(_loc_10);
            _loc_3.setMaximumHeight(this.m_content.height - 10);
            _loc_3.setMinimumHeight(this.m_content.height - 10);
            _loc_3.setPreferredHeight(this.m_content.height - 10);
            _loc_3.append(_loc_5);
            _loc_4.append(_loc_3);
            ASwingHelper.prepare(_loc_4);
            if (this.m_status == FranchiseMenu.STATUS_COLLECT_NOWITHER || this.m_status == FranchiseMenu.STATUS_COLLECT_WITHER || this.m_status == FranchiseMenu.STATUS_COLLECT_WAITING)
            {
                this.m_bodyPanel.appendAll(_loc_4);
            }
            else
            {
                this.m_bodyPanel.appendAll(_loc_4, _loc_2, ASwingHelper.horizontalStrut(5));
            }
            _loc_4.setPreferredWidth(_loc_11);
            _loc_4.setMinimumWidth(_loc_11);
            _loc_4.setMaximumWidth(_loc_11);
            ASwingHelper.prepare(this.m_bodyPanel);
            this.m_cardPanel.append(this.m_bodyPanel);
            return;
        }//end

        private void  makeFooterPanel ()
        {
            CustomButton _loc_3 =null ;
            this.m_footerPanel.removeAll();
            _loc_1 = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.CENTER );
            _loc_2 = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.CENTER );
            DisplayObject _loc_4 =(DisplayObject)new FranchiseMenuUI.m_assetDict.get( "fr_rule");
            AssetPane _loc_5 =new AssetPane(_loc_4 );
            _loc_6 = TextFieldUtil.getLocaleFontSize(18,14,.get( {locale size "ja",18) });
            switch(this.m_status)
            {
                case FranchiseMenu.STATUS_NEED_REFILL:
                {
                    _loc_3 = new CustomButton(TextFieldUtil.formatSmallCapsString(ZLoc.t("Dialogs", "SupplyRun")), null, "GreenButtonUI");
                    break;
                }
                case FranchiseMenu.STATUS_NEED_REMIND:
                {
                    _loc_3 = new CustomButton(TextFieldUtil.formatSmallCapsString(ZLoc.t("Dialogs", "Remind")), null, "OrangeButtonUI");
                    break;
                }
                case FranchiseMenu.STATUS_DONE_REMIND:
                {
                    _loc_3 = new CustomButton(TextFieldUtil.formatSmallCapsString(ZLoc.t("Dialogs", "Remind")), null, "GreyButtonUI");
                    break;
                }
                case FranchiseMenu.STATUS_ADD_NEIGHBOR:
                {
                    _loc_3 = new CustomButton(TextFieldUtil.formatSmallCapsString(ZLoc.t("Dialogs", "AddNeighbor")), null, "GreenButtonUI");
                    break;
                }
                case FranchiseMenu.STATUS_COLLECT_NOWITHER:
                case FranchiseMenu.STATUS_COLLECT_WITHER:
                case FranchiseMenu.STATUS_COLLECT_WAITING:
                case FranchiseMenu.STATUS_BLANK:
                {
                }
                default:
                {
                    break;
                    break;
                }
            }
            if (_loc_3)
            {
                _loc_3.setFont(ASwingHelper.makeFont(EmbeddedArt.titleFont, _loc_6));
                _loc_3.setPreferredSize(new IntDimension(BUTTON_WIDTH - 4, BUTTON_HEIGHT));
                _loc_3.setMinimumSize(new IntDimension(BUTTON_WIDTH - 4, BUTTON_HEIGHT));
                _loc_3.setMaximumSize(new IntDimension(BUTTON_WIDTH - 4, BUTTON_HEIGHT));
                _loc_2.appendAll(ASwingHelper.horizontalStrut(3), _loc_3, ASwingHelper.horizontalStrut(3));
                _loc_1.appendAll(_loc_2);
                _loc_3.addEventListener(MouseEvent.CLICK, this.onButtonClick, false, 0, true);
            }
            this.m_footerPanel.append(_loc_1);
            if (_loc_3)
            {
                this.m_footerPanel.appendAll(ASwingHelper.horizontalStrut(6), _loc_5);
            }
            else if (this.m_status == FranchiseMenu.STATUS_COLLECT_NOWITHER || this.m_status == FranchiseMenu.STATUS_COLLECT_WITHER)
            {
                this.m_footerPanel.appendAll(ASwingHelper.horizontalStrut(30), _loc_5);
            }
            else
            {
                this.m_footerPanel.appendAll(ASwingHelper.horizontalStrut(173), _loc_5);
            }
            ASwingHelper.prepare(this.m_footerPanel);
            this.m_cardPanel.append(this.m_footerPanel);
            ASwingHelper.prepare(this.m_cardPanel);
            return;
        }//end

        public void  refreshCard ()
        {
            this.makeBodyPanel();
            this.makeFooterPanel();
            return;
        }//end

        public void  setCollectInfo (String param1 ,double param2 )
        {
            this.m_status = param1;
            this.m_collectAmount = param2;
            return;
        }//end

        private void  onButtonClick (MouseEvent event )
        {
            double _loc_2 =0;
            double _loc_3 =0;
            double _loc_4 =0;
            FranchiseExpansionData _loc_5 =null ;
            int _loc_6 =0;
            switch(this.m_status)
            {
                case FranchiseMenu.STATUS_NEED_REFILL:
                {
                    if (Global.player.commodities.getCount(FranchiseMenu.selectedFranchise.commType) >= FranchiseMenu.selectedFranchise.saleCost)
                    {
                        Sounds.play("Collect");
                        _loc_2 = this.m_franchiseExpansion.starRating;
                        _loc_3 = this.m_franchiseExpansion.moneyCollected;
                        _loc_4 = 100;
                        if (this.m_franchiseExpansion.timeLastCollected)
                        {
                            _loc_6 = int(GlobalEngine.getTimer() / 1000 - this.m_franchiseExpansion.timeLastCollected);
                            _loc_4 = Global.franchiseManager.mapElapsedToCompleteness(_loc_6 / 3600);
                        }
                        this.m_collectAmount = Math.floor(_loc_3 * _loc_4 / 100);
                        this.m_collectAmountBeforeStars = Math.round(this.m_collectAmount / _loc_2);
                        this.m_witherAmount = Math.floor(_loc_3 - this.m_collectAmount);
                        this.setCollectInfo(FranchiseMenu.STATUS_COLLECT_NOWITHER, this.m_collectAmount);
                        Global.player.gold = Global.player.gold + this.m_collectAmount;
                        Global.player.commodities.remove(FranchiseMenu.selectedFranchise.commType, FranchiseMenu.selectedFranchise.saleCost);
                        FranchiseMenu.menuUI.doSendSaleFlyout(this.m_collectAmount, _loc_2, event.stageX, event.stageY);
                        FranchiseMenu.menuUI.doSendSaleDoobers(this.m_collectAmount, event.stageX, event.stageY, false);
                        if (_loc_4 == 100)
                        {
                            this.m_status = FranchiseMenu.STATUS_COLLECT_NOWITHER;
                        }
                        else
                        {
                            this.m_status = FranchiseMenu.STATUS_COLLECT_WITHER;
                        }
                        GameTransactionManager.addTransaction(new TFranchiseSupply(FranchiseMenu.selectedFranchise.franchiseType, this.m_franchiseExpansion.locationUid, _loc_2));
                        GameTransactionManager.addTransaction(new TFranchiseCollect(FranchiseMenu.selectedFranchise.franchiseType, this.m_franchiseExpansion.locationUid, this.m_franchiseExpansion.moneyCollected));
                        this.refreshCard();
                        _loc_5 = Global.franchiseManager.model.getFranchise(FranchiseMenu.selectedFranchise.franchiseType, this.m_franchiseExpansion.locationUid);
                        Global.franchiseManager.collect(_loc_5.franchiseType, _loc_5.locationUid);
                    }
                    else
                    {
                        this.doTextFlyout(ZLoc.t("Dialogs", "NotEnough" + FranchiseMenu.selectedFranchise.commType, {amount:FranchiseMenu.selectedFranchise.saleCost}), event.stageX, event.stageY);
                    }
                    break;
                }
                case FranchiseMenu.STATUS_ADD_NEIGHBOR:
                {
                    Sounds.play("Remind");
                    FrameManager.showTray("invite.php?ref=franchise_menu");
                    break;
                }
                case FranchiseMenu.STATUS_NEED_REMIND:
                {
                    FranchiseMenuUI.addFriendReminder(FranchiseMenu.selectedFranchise.franchiseType, this.m_franchiseExpansion.locationUid);
                    Sounds.play("Remind");
                    this.m_status = FranchiseMenu.STATUS_DONE_REMIND;
                    this.refreshCard();
                    break;
                }
                case FranchiseMenu.STATUS_DONE_REMIND:
                {
                    break;
                }
                default:
                {
                    this.doTextFlyout("OH NOES", event.stageX, event.stageY);
                    break;
                }
            }
            return;
        }//end

         protected void  initializeCell ()
        {
            _loc_1 = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.CENTER );
            AssetPane _loc_2 =new AssetPane(m_itemIcon );
            _loc_1.append(_loc_2);
            this.m_itemIconPane.append(_loc_1);
            return;
        }//end

        private void  doTextFlyout (String param1 ,int param2 ,int param3 )
        {
            UI.displayStatus(param1, param2, param3);
            return;
        }//end

    }



