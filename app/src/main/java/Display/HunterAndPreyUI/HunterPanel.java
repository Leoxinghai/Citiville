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
import Classes.util.*;
import Display.*;
import Display.DialogUI.*;
import Display.ValentineUI.*;
import Display.aswingui.*;
import Engine.Managers.*;
import Events.*;
import Modules.bandits.*;
import Modules.bandits.transactions.*;
import Modules.stats.experiments.*;
import Modules.stats.types.*;
//import flash.display.*;
//import flash.events.*;
//import flash.filters.*;
import org.aswing.*;
import org.aswing.event.*;

    public class HunterPanel extends JPanel
    {
        protected CheckBoxComponent m_autoFeedCheckBox ;
        public HunterScrollingList m_officers ;
        protected JButton m_buyButton ;
        protected JButton m_getDonutsButton ;

        public  HunterPanel ()
        {
            super(new FlowLayout(FlowLayout.LEFT));
            this.init();
            return;
        }//end

        protected void  init ()
        {
            _loc_1 = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.TOP ,-15);
            DisplayObject _loc_2 =(DisplayObject)new HunterDialog.assetDict.get( "inner_undertab");
            this.setBackgroundDecorator(new AssetBackground(_loc_2));
            this.m_officers = new HunterScrollingList();
            this.addEventListener(UIEvent.BUY_DONUT, this.toggleBuyButton, false, 0, true);
            _loc_1.append(this.m_officers);
            if (PreyUtil.getHubLevel(HunterDialog.groupId) < 7)
            {
                _loc_1.append(this.makeButtonsPanel());
            }
            this.append(_loc_1);
            this.setPreferredWidth(671);
            this.setPreferredHeight(390);
            dispatchEvent(new Event(MakerPanel.PREPARE, true));
            return;
        }//end

        protected void  updateCell (int param1 ,String param2 ,boolean param3 =false )
        {
            this.m_officers.updateCell(param1, param2, param3);
            return;
        }//end

        protected JPanel  makeButtonsPanel ()
        {
            boolean _loc_7 =false ;
            boolean _loc_8 =false ;
            DisplayObject _loc_9 =null ;
            DisplayObject _loc_10 =null ;
            _loc_1 = ASwingHelper.makeFlowJPanel(FlowLayout.CENTER ,10);
            AssetIcon _loc_2 =new AssetIcon(new EmbeddedArt.icon_cash ()as DisplayObject );
            _loc_3 =Global.gameSettings().getHubQueueInfo(HunterDialog.groupId );
            _loc_4 = _loc_3.get("resourceCashCost") ;
            this.m_buyButton = new CustomButton(ZLoc.t("Dialogs", HunterDialog.groupId + "_costForResource", {amount:String(_loc_4)}), _loc_2, "CashButtonUI");
            this.m_buyButton.setMargin(new Insets(3, 10, 3, 10));
            this.m_buyButton.setFont(new ASFont(EmbeddedArt.titleFont, 16, false, false, false, EmbeddedArt.getAdvancedFontProps(EmbeddedArt.titleFont)));
            this.m_buyButton.setTextFilters(.get(new DropShadowFilter(1, 90, 0, 0.2, 3, 3, 3, 1), new GlowFilter(0, 0.2, 4, 4, 10)));
            _loc_5 = PreyManager.findResourceRequiredHunter(HunterDialog.groupId);
            this.m_getDonutsButton = new CustomButton(ZLoc.t("Dialogs", HunterDialog.groupId + "_askForResource"), null, "GreenButtonUI");
            this.m_getDonutsButton.setFont(new ASFont(EmbeddedArt.titleFont, 16, false, false, false, EmbeddedArt.getAdvancedFontProps(EmbeddedArt.titleFont)));
            this.m_getDonutsButton.setMargin(new Insets(5, 10, 5, 10));
            if (_loc_5 == -1)
            {
                this.m_buyButton.setEnabled(false);
                this.m_getDonutsButton.setEnabled(false);
            }
            this.m_buyButton.addActionListener(this.buyDonuts, 0, true);
            this.m_getDonutsButton.addActionListener(this.sendDonutsFeed, 0, true);
            _loc_6 =Global.experimentManager.getVariant(ExperimentDefinitions.EXPERIMENT_AUTO_DONUT );
            if (Global.experimentManager.getVariant(ExperimentDefinitions.EXPERIMENT_AUTO_DONUT) == ExperimentDefinitions.AUTO_DONUT_DEFAULT_ON || _loc_6 == ExperimentDefinitions.AUTO_DONUT_DEFAULT_OFF)
            {
                _loc_7 = _loc_6 == ExperimentDefinitions.AUTO_DONUT_DEFAULT_ON;
                _loc_8 = Global.world.viralMgr.getGlobalPublishStreamCheck(HunterDialog.groupId + "_getResource", _loc_7);
                _loc_9 =(DisplayObject) new HunterDialog.assetDict.get("donut_auto_feed_checkmark");
                _loc_10 =(DisplayObject) new HunterDialog.assetDict.get("donut_auto_feed_checkbox");
                this.m_autoFeedCheckBox = new CheckBoxComponent(_loc_8, ZLoc.t("Dialogs", HunterDialog.groupId + "_autoFeed"), this.toggleAutoFeedCheckBox, _loc_9, _loc_10);
                _loc_1.appendAll(this.m_buyButton, this.m_getDonutsButton, this.m_autoFeedCheckBox);
            }
            else
            {
                _loc_1.appendAll(this.m_buyButton, this.m_getDonutsButton);
            }
            return _loc_1;
        }//end

        protected void  toggleAutoFeedCheckBox (boolean param1 )
        {
            Global.world.viralMgr.setGlobalPublishStreamCheck(param1, HunterDialog.groupId + "_getResource");
            _loc_2 = param1? ("turn_on") : ("turn_off");
            StatsManager.count(StatsCounterType.DIALOG, "active_building", "autofeed_box", _loc_2);
            return;
        }//end

        protected void  toggleBuyButton (Event event =null )
        {
            _loc_2 = PreyManager.findResourceRequiredHunter(HunterDialog.groupId);
            if (_loc_2 == -1)
            {
                this.m_buyButton.setEnabled(false);
                this.m_getDonutsButton.setEnabled(false);
            }
            else
            {
                this.m_buyButton.setEnabled(true);
                this.m_getDonutsButton.setEnabled(true);
            }
            ASwingHelper.prepare(this.parent);
            return;
        }//end

        protected void  buyDonuts (AWEvent event )
        {
            Object _loc_3 =null ;
            int _loc_4 =0;
            String _loc_5 =null ;
            _loc_2 = PreyManager.findResourceRequiredHunter(HunterDialog.groupId);
            if (_loc_2 != -1)
            {
                _loc_3 = Global.gameSettings().getHubQueueInfo(HunterDialog.groupId);
                _loc_4 = _loc_3.get("resourceCashCost");
                if (Global.player.cash >= _loc_4)
                {
                    Global.player.cash = Global.player.cash - _loc_4;
                    _loc_5 = PreyManager.giveResourceAndWake(_loc_2, HunterDialog.groupId);
                    UI.requestPatrol(HunterDialog.groupId, null, null, _loc_2);
                    GameTransactionManager.addTransaction(new TBuyResource(HunterDialog.groupId, _loc_2));
                    this.updateCell(_loc_2, _loc_5, true);
                    this.toggleBuyButton();
                    PreyUtil.logDialogStats(HunterDialog.groupId, "buy_resource", "hub_ui");
                    PreyUtil.refreshHubAppearance(HunterDialog.groupId);
                }
                else
                {
                    UI.displayPopup(new GetCoinsDialog(ZLoc.t("Dialogs", "ImpulseMarketCash"), "GetCash", GenericDialogView.TYPE_GETCASH, null, true));
                }
            }
            return;
        }//end

        protected void  sendDonutsFeed (AWEvent event )
        {
            _loc_2 =Global.world.viralMgr.sendGetHunterResource(HunterDialog.groupId );
            if (!_loc_2)
            {
                UI.displayMessage(ZLoc.t("Dialogs", HunterDialog.groupId + "_HunterFeedMax"), GenericDialogView.TYPE_OK);
                PreyUtil.logDialogStats(HunterDialog.groupId, "view", "request_limit_ui");
            }
            else
            {
                PreyUtil.refreshHubAppearance(HunterDialog.groupId);
                PreyUtil.logDialogStats(HunterDialog.groupId, "get_resource", "hub_ui");
            }
            return;
        }//end

    }



