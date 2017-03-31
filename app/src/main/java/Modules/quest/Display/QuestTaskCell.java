package Modules.quest.Display;

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
import Classes.virals.*;
import Display.*;
import Display.DialogUI.*;
import Display.aswingui.*;
import Events.*;
import Init.*;
import Modules.quest.Display.TaskFooters.*;
import Modules.quest.Managers.*;
import Transactions.*;
import com.adobe.utils.*;
//import flash.display.*;
//import flash.events.*;
//import flash.text.*;
//import flash.utils.*;
import org.aswing.*;
import org.aswing.event.*;

    public class QuestTaskCell extends JPanel
    {
        protected Object m_data ;
        protected Object m_taskInfo ;
        protected int m_taskIndex ;
        protected Dictionary m_assetDict ;
        protected Class m_checkMark ;
        protected Class m_checkList ;
        protected Dictionary m_buttonsListenerRefDict ;
        protected GenericDialogView m_parentView ;
        protected GameQuestTaskFooterFactory m_taskFooterFactory ;
        protected JPanel m_iconComponent ;
        protected int m_textWidth =320;
        protected JPanel m_taskTextComponent ;
public static  int TASK_TEXT_WIDTH =320;

        public  QuestTaskCell (Object param1 ,int param2 ,Object param3 ,Dictionary param4 ,GenericDialogView param5 ,GameQuestTaskFooterFactory param6 )
        {
            super(new FlowLayout(FlowLayout.LEFT, 10));
            this.m_data = param1;
            this.m_taskIndex = param2;
            this.m_taskInfo = param3;
            this.m_assetDict = param4;
            this.m_parentView = param5;
            this.m_taskFooterFactory = param6;
            this.m_buttonsListenerRefDict = new Dictionary();
            this.init();
            this.makeCell();
            return;
        }//end

        protected void  init ()
        {
            this.m_checkMark = this.m_assetDict.get("checkMark");
            this.m_checkList = this.m_assetDict.get("checkList");
            return;
        }//end

        protected void  makeCell ()
        {
            this.makeIconComponent();
            this.makeTextComponent();
            this.makeMiscComponent();
            ASwingHelper.prepare(this);
            return;
        }//end

        protected void  makeIconComponent ()
        {
            JLabel _loc_4 =null ;
            String _loc_7 =null ;
            _loc_1 = this.m_taskInfo.image.content ;
            int _loc_8 =60;
            _loc_1.height = 60;
            _loc_1.width = _loc_8;
            if (_loc_1 instanceof Bitmap)
            {
                ((Bitmap)_loc_1).smoothing = true;
            }
            AssetPane _loc_2 =new AssetPane(_loc_1 );
            this.m_iconComponent = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.TOP);
            _loc_3 = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.CENTER );
            _loc_3.append(_loc_2);
            this.m_iconComponent.append(_loc_3);
            _loc_5 =Global.questManager.getQuestProgressByName(this.m_data.name ).progress.get(this.m_taskIndex) ;
            _loc_6 = Math.min(_loc_5 ,int(this.m_data.tasks.get(this.m_taskIndex).@total)).toString();
            if (this.m_data.tasks.get(this.m_taskIndex).@total != "1")
            {
                if (this.m_data.tasks.get(this.m_taskIndex).@percent == "true")
                {
                    if (_loc_6 == "100")
                    {
                        _loc_6 = "99";
                    }
                    _loc_7 = ZLoc.t("Dialogs", "QuestPercentXofY", {completed:_loc_6, total:this.m_data.tasks.get(this.m_taskIndex).@total});
                }
                else
                {
                    _loc_7 = ZLoc.t("Dialogs", "QuestStepsXofY", {completed:_loc_6, total:this.m_data.tasks.get(this.m_taskIndex).@total});
                }
                _loc_4 = ASwingHelper.makeLabel(_loc_7, EmbeddedArt.titleFont, 18, EmbeddedArt.whiteTextColor);
                _loc_4.setTextFilters(EmbeddedArt.titleFilters);
                this.m_iconComponent.appendAll(ASwingHelper.verticalStrut(-10), _loc_4);
            }
            ASwingHelper.prepare(this.m_iconComponent);
            this.append(this.m_iconComponent);
            return;
        }//end

        protected void  makeTextComponent ()
        {
            this.m_taskTextComponent = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.CENTER);
            this.updateTextComponent();
            this.append(this.m_taskTextComponent);
            return;
        }//end

        protected void  makeMiscComponent ()
        {
            DisplayObject _loc_3 =null ;
            JPanel _loc_4 =null ;
            int _loc_5 =0;
            int _loc_6 =0;
            String _loc_7 =null ;
            Component _loc_8 =null ;
            AssetIcon _loc_9 =null ;
            JPanel _loc_10 =null ;
            String _loc_11 =null ;
            CustomButton _loc_12 =null ;
            JLabel _loc_13 =null ;
            JPanel _loc_14 =null ;
            Sprite _loc_15 =null ;
            String _loc_16 =null ;
            CustomButton _loc_17 =null ;
            CustomButton _loc_18 =null ;
            JTextField _loc_19 =null ;
            AssetPane _loc_20 =null ;
            _loc_1 = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.TOP );
            boolean _loc_2 =false ;
            if (Global.questManager.getQuestProgressByName(this.m_data.name).progress.get(this.m_taskIndex) >= this.m_data.tasks.get(this.m_taskIndex).@total)
            {
                _loc_3 =(DisplayObject) new this.m_checkMark();
                _loc_1.append(new AssetPane(_loc_3));
                if (this.processExtraFooter())
                {
                    _loc_1.append(this.processExtraFooter());
                }
            }
            else
            {
                _loc_5 = Global.questManager.proRatedCost(this.m_data.name, this.m_taskIndex, GlobalEngine.serverTime);
                _loc_6 = Global.questManager.proRatedDiscountPercentage(this.m_data.name, this.m_taskIndex, GlobalEngine.serverTime);
                if (_loc_5 > 0)
                {
                    _loc_4 = new JPanel(new FlowLayout(FlowLayout.LEFT));
                    _loc_1.append(_loc_4);
                    _loc_9 = new AssetIcon(new EmbeddedArt.icon_cash());
                    _loc_10 = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.BOTTOM, -2);
                    _loc_11 = ZLoc.t("Dialogs", "QuestCashComplete", {price:_loc_5});
                    _loc_12 = new CustomButton(_loc_11, _loc_9, "CashButtonUI");
                    _loc_12.setPreferredHeight(30);
                    _loc_12.setMargin(new Insets(2, 10, 0, 10));
                    this.m_buttonsListenerRefDict.put(_loc_12,  this.makePurchaseCallback(this.m_taskIndex, _loc_5));
                    _loc_12.addActionListener(this.m_buttonsListenerRefDict.get(_loc_12));
                    if (_loc_6 > 0)
                    {
                        _loc_13 = ASwingHelper.makeLabel(ZLoc.t("Dialogs", "QuestProratedDiscount", {percent:_loc_6}), EmbeddedArt.defaultFontNameBold, 14, EmbeddedArt.whiteTextColor, JLabel.RIGHT);
                        _loc_14 = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.RIGHT);
                        _loc_15 = new Sprite();
                        _loc_15.graphics.beginFill(EmbeddedArt.orangeTextColor);
                        _loc_15.graphics.drawRoundRect(0, 0, _loc_13.getPreferredWidth() + 6, _loc_13.getPreferredHeight(), 10, 8);
                        _loc_14.append(new AssetPane(_loc_15));
                        _loc_14.append(ASwingHelper.horizontalStrut(-(_loc_13.getPreferredWidth() + 3)));
                        _loc_14.append(_loc_13);
                        _loc_14.append(ASwingHelper.horizontalStrut(10));
                        _loc_10.append(_loc_14);
                    }
                    _loc_10.append(_loc_12);
                    _loc_4.append(_loc_10);
                    _loc_2 = true;
                }
                if (this.m_data.tasks.get(this.m_taskIndex).@action == "redeemXPromo")
                {
                    _loc_4 = new JPanel(new FlowLayout(FlowLayout.LEFT));
                    _loc_1.append(_loc_4);
                    _loc_16 = this.m_data.tasks.get(this.m_taskIndex).@reward_name;
                    _loc_17 = new CustomButton(ZLoc.t("Dialogs", "ClaimReward"), null, "GreenButtonUI");
                    _loc_17.setFont(new ASFont(EmbeddedArt.titleFont, 14, false, false, false, EmbeddedArt.advancedFontProps));
                    this.m_buttonsListenerRefDict.put(_loc_17,  this.makeRedeemXPromoCallback(_loc_16, _loc_17));
                    _loc_17.addActionListener(this.m_buttonsListenerRefDict.get(_loc_17));
                    _loc_4.append(_loc_17);
                    _loc_2 = true;
                }
                _loc_7 = String(this.m_data.tasks.get(this.m_taskIndex).@feed);
                if (_loc_7 != null && _loc_7 != "")
                {
                    _loc_4 = new JPanel(new FlowLayout(FlowLayout.LEFT));
                    _loc_1.append(_loc_4);
                    if (Global.world.viralMgr.canPost(_loc_7))
                    {
                        _loc_18 = new CustomButton(ZLoc.t("Dialogs", "QuestSendFeed"), null, "GreenButtonUI");
                        _loc_18.setFont(new ASFont(EmbeddedArt.titleFont, 14, false, false, false, EmbeddedArt.advancedFontProps));
                        _loc_18.setPreferredHeight(30);
                        this.m_buttonsListenerRefDict.put(_loc_18,  this.makeFeedCallback(_loc_7, _loc_18));
                        _loc_18.addActionListener(this.m_buttonsListenerRefDict.get(_loc_18));
                        _loc_4.append(_loc_18);
                    }
                    else
                    {
                        _loc_19 = ASwingHelper.makeTextField(ZLoc.t("Dialogs", "QuestNoFeed") + "  ", EmbeddedArt.defaultFontName, 14, 4210752);
                        _loc_4.append(_loc_19);
                    }
                    _loc_2 = true;
                }
                _loc_8 = this.makeTaskFooter();
                if (_loc_8)
                {
                    _loc_1.append(_loc_8);
                    _loc_2 = true;
                }
                if (!_loc_2)
                {
                    _loc_20 = new AssetPane(new this.m_checkList());
                    _loc_1.append(_loc_20);
                }
            }
            ASwingHelper.prepare(_loc_1);
            this.append(_loc_1);
            return;
        }//end

        protected Component  processExtraFooter ()
        {
            ITaskFooter _loc_1 =null ;
            Component _loc_2 =null ;
            int _loc_3 =0;
            XML _loc_4 =null ;
            if (this.m_data.taskFooters)
            {
                _loc_3 = 0;
                for(int i0 = 0; i0 < this.m_data.taskFooters.taskFooter.size(); i0++)
                {
                		_loc_4 = this.m_data.taskFooters.taskFooter.get(i0);

                    if (!_loc_4.hasOwnProperty("@id"))
                    {
                        continue;
                    }
                    if (this.m_taskInfo.footerId != _loc_4.@id)
                    {
                        continue;
                    }
                    if (_loc_4.attribute("showOnSuccess").length() > 0 && _loc_4.@showOnSuccess == "true")
                    {
                        _loc_1 = this.m_taskFooterFactory.createTaskFooter(_loc_4.@action, _loc_4.@type, this.m_parentView);
                        _loc_2 = _loc_1.getComponent();
                        if (_loc_2 != null)
                        {
                            return _loc_2;
                        }
                    }
                }
            }
            return null;
        }//end

        protected Component  makeTaskFooter ()
        {
            ITaskFooter _loc_1 =null ;
            Component _loc_2 =null ;
            int _loc_3 =0;
            XML _loc_4 =null ;
            if (this.m_data.taskFooters)
            {
                _loc_3 = 0;
                for(int i0 = 0; i0 < this.m_data.taskFooters.taskFooter.size(); i0++)
                {
                		_loc_4 = this.m_data.taskFooters.taskFooter.get(i0);

                    if (!_loc_4.hasOwnProperty("@id"))
                    {
                        continue;
                    }
                    if (this.m_taskInfo.footerId != _loc_4.@id)
                    {
                        continue;
                    }
                    _loc_1 = this.m_taskFooterFactory.createTaskFooter(_loc_4.@action, _loc_4.@type, this.m_parentView);
                    _loc_2 = _loc_1.getComponent();
                    if (_loc_2 != null)
                    {
                        return _loc_2;
                    }
                }
            }
            return null;
        }//end

        private void  showQuestInvalidDialog ()
        {
            UI.displayMessage(ZLoc.t("Dialogs", "QuestExpired"), GenericDialogView.TYPE_OK, null, "", true);
            return;
        }//end

        protected Function  makeFeedCallback (String param1 ,CustomButton param2 )
        {
            feedName = param1;
            button = param2;
            return void  (AWEvent event )
            {
                if (GameQuestUtility.isQuestValid(m_data.name))
                {
                    if (feedName == ViralType.NEIGHBOR_GATE_QUEST_REQUEST)
                    {
                        FrameManager.showTray("invite.php?view=areapp&ref=non_cityville_MFS&neighborgate=1");
                        return;
                    }
                    Global.world.viralMgr.sendQuestFeed(Global.player, feedName);
                    button.setEnabled(false);
                }
                else
                {
                    showQuestInvalidDialog();
                }
                return;
            }//end
            ;
        }//end

        protected Function  makeRedeemXPromoCallback (String param1 ,CustomButton param2 )
        {
            xPromoRewardName = param1;
            button = param2;
            return void  (AWEvent event )
            {
                _loc_2 = null;
                _loc_3 = null;
                _loc_4 = null;
                if (GameQuestUtility.isQuestValid(m_data.name))
                {
                    _loc_2 = m_data.get("name");
                    _loc_3 = xPromoRewardName;
                    _loc_4 = "xpromo.php?action=redeem&rewardName=" + _loc_3 + "&questName=" + _loc_2;
                    FrameManager.navigateTo(_loc_4);
                    button.setEnabled(false);
                }
                else
                {
                    showQuestInvalidDialog();
                }
                return;
            }//end
            ;
        }//end

        protected Function  makePurchaseCallback (int param1 ,int param2 )
        {
            taskIndex = param1;
            cashcost = param2;
            return void  (AWEvent event )
            {
                _loc_2 = null;
                _loc_3 = null;
                if (GameQuestUtility.isQuestValid(m_data.name))
                {
                    _loc_2 = QuestSettingsInit.getItemByName(m_data.name);
                    m_parentView.countDialogViewAction("PURCHASE", false);
                    if (Global.player.cash < cashcost)
                    {
                        cleanUp();
                        dispatchEvent(new Event(QuestPopupView.NO_CASH_CLOSE, false, false));
                        UI.displayImpulseBuyPopup(ImpulseBuy.TYPE_MARKET_CASH);
                    }
                    else
                    {
                        Global.player.cash = Global.player.cash - cashcost;
                        if (_loc_2.tasks.get(taskIndex).action == "checkItemsAtInit")
                        {
                            _loc_3 = _loc_2.tasks.get(taskIndex).total - Global.player.inventory.getItemCountByName(_loc_2.tasks.get(taskIndex).type);
                            Global.player.inventory.addItems(_loc_2.tasks.get(taskIndex).type, _loc_3, true);
                        }
                        GameTransactionManager.addTransaction(new TBuyQuestProgress(m_data.name, taskIndex, cashcost));
                        Global.questManager.setQuestTaskProgress(m_data.name, taskIndex, _loc_2.tasks.get(taskIndex).total);
                        _loc_2.purchased.put(taskIndex,  true);
                    }
                    if (_loc_2 instanceof GameQuest && (_loc_2 as GameQuest).isQuestComplete())
                    {
                        cleanUp();
                        m_parentView.dispatchEvent(new Event(Event.CLOSE, false, false));
                    }
                    else
                    {
                        invalidateCell();
                    }
                }
                else
                {
                    showQuestInvalidDialog();
                }
                return;
            }//end
            ;
        }//end

        public void  cleanUp ()
        {
            CustomButton _loc_2 =null ;
            _loc_1 = DictionaryUtil.getKeys(this.m_buttonsListenerRefDict);
            for(int i0 = 0; i0 < _loc_1.size(); i0++)
            {
            		_loc_2 = _loc_1.get(i0);

                _loc_2.removeActionListener(this.m_buttonsListenerRefDict.get(_loc_2));
            }
            return;
        }//end

        public void  invalidateCell ()
        {
            this.cleanUp();
            this.removeAll();
            this.makeCell();
            dispatchEvent(new UIEvent(UIEvent.REFRESH_DIALOG, "", true));
            return;
        }//end

        public void  updateTextComponent ()
        {
            AssetPane _loc_2 =null ;
            this.m_taskTextComponent.removeAll();
            _loc_1 = ASwingHelper.makeMultilineText(this.m_taskInfo.text ,this.m_textWidth ,EmbeddedArt.defaultFontNameBold ,TextFormatAlign.LEFT ,18,EmbeddedArt.brownTextColor );
            this.m_taskTextComponent.append(_loc_1);
            if (this.m_taskInfo.hint)
            {
                _loc_2 = ASwingHelper.makeMultilineText(this.m_taskInfo.hint, this.m_textWidth, EmbeddedArt.defaultFontNameBold, TextFormatAlign.LEFT, 16, EmbeddedArt.orangeTextColor);
                this.m_taskTextComponent.append(_loc_2);
            }
            ASwingHelper.prepare(this.m_taskTextComponent);
            return;
        }//end

        public JPanel  iconComponent ()
        {
            return this.m_iconComponent;
        }//end

        public int  taskTextWidth ()
        {
            return this.m_textWidth;
        }//end

        public void  taskTextWidth (int param1 )
        {
            this.m_textWidth = param1;
            return;
        }//end

        public Dictionary  buttonsListenerRefDict ()
        {
            return this.m_buttonsListenerRefDict;
        }//end

    }



