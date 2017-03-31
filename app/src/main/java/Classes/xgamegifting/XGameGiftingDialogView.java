package Classes.xgamegifting;

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
import Display.MarketUI.*;
import Display.MarketUI.assets.*;
import Display.aswingui.*;
import Events.*;
//import flash.display.*;
//import flash.events.*;
//import flash.text.*;
//import flash.utils.*;
import org.aswing.*;
import org.aswing.geom.*;

    public class XGameGiftingDialogView extends GenericDialogView
    {
        private Array m_tabLabels ;
        private int m_currSelectedTabIndex ;
        private int m_gameId ;
        private int m_tabWidth ;
        private JPanel m_marketPanel ;
        private JButton m_btnDone ;
        private Array m_tabs ;
        private XGameGiftingScrollingList m_market ;
        protected JWindow m_toolTipWindow ;
        protected XGameGiftingManager m_manager ;
        protected Timer m_timer ;
        private JLabel m_timerText ;
        protected JPanel m_timerPane ;
        public static  int WIN_WIDTH =733;
        public static  int WIN_HEIGHT =554;
        public static  int TAB_WIDTH =175;
        public static  int TAB_HEIGHT =34;
        public static int sendTabIsOn =0;

        public  XGameGiftingDialogView (Dictionary param1 ,int param2 )
        {
            this.m_timer = new Timer(1000);
            this.m_tabLabels = .get("Received", "Send");
            this.m_gameId = param2;
            this.m_manager = XGameGiftingManager.instance;
            super(param1, "", "XGameGifting_" + param2, TYPE_OK, null, "", 0, "", null, "", true);
            return;
        }//end

        public void  setSelectedTab (int param1 )
        {
            JPanel _loc_3 =null ;
            DisplayObject _loc_4 =null ;
            this.m_currSelectedTabIndex = param1;
            int _loc_2 =0;
            while (_loc_2 < this.m_tabs.length())
            {

                _loc_3 = this.m_tabs.get(_loc_2);
                _loc_4 = (DisplayObject)(_loc_2 == param1 ? (new m_assetDict.get("tab_current")) : (new m_assetDict.get("tab")));
                _loc_4.width = this.m_tabWidth;
                _loc_3.setPreferredWidth(this.m_tabWidth);
                _loc_3.setBackgroundDecorator(new AssetBackground(_loc_4));
                ASwingHelper.prepare(_loc_3);
                _loc_2 = _loc_2 + 1;
            }
            if (param1 == 0)
            {
                sendTabIsOn = 0;
            }
            else
            {
                sendTabIsOn = 1;
            }
            this.updateMarketDisplay();
            return;
        }//end

        public void  updateMarketDisplay ()
        {
            JLabel _loc_3 =null ;
            int _loc_4 =0;
            Item _loc_5 =null ;
            Array _loc_6 =null ;
            this.m_marketPanel.removeAll();
            Array _loc_1 =new Array ();
            Array _loc_2 =new Array ();
            if (this.m_currSelectedTabIndex == 0)
            {
                _loc_1 = Global.player.getAllPossibleReceivedXGameGiftsByGameId(this.m_gameId);
            }
            else
            {
                _loc_2 = Global.gameSettings().getXGameGiftItemsByGameId(this.m_gameId);
                _loc_4 = Global.player.getXGameLevelByGameId(this.m_gameId);
                for(int i0 = 0; i0 < _loc_2.size(); i0++)
                {
                	_loc_5 = _loc_2.get(i0);

                    _loc_6 = new Array();
                    _loc_6.push(_loc_5);
                    if (_loc_4 < 1 || this.m_manager.timeToNextGifting > 0)
                    {
                        _loc_6.push(false);
                    }
                    else
                    {
                        _loc_6.push(true);
                    }
                    _loc_1.push(_loc_6);
                }
            }
            if (sendTabIsOn == 0)
            {
                this.m_market = new XGameGiftingScrollingList(_loc_1, XGameGiftRecieveCellFactory, 0, 4, 2);
                if (_loc_1.length == 0)
                {
                    this.m_market.setPreferredHeight(272);
                }
                _loc_3 = ASwingHelper.makeLabel(ZLoc.t("Dialogs", "XGameGifting_inventory"), EmbeddedArt.defaultFontNameBold, 14, EmbeddedArt.brownTextColor, JLabel.CENTER);
            }
            else
            {
                this.m_market = new XGameGiftingScrollingList(_loc_1, XGameGiftSendCellFactory, 0, 4, 2);
                if (this.m_manager.timeToNextGifting > 0)
                {
                    this.turnOffGifting();
                }
                else
                {
                    this.turnOnGifting();
                }
            }
            addEventListener(DataItemEvent.SHOW_TOOLTIP, this.showToolTip, false, 0, true);
            addEventListener(DataItemEvent.HIDE_TOOLTIP, this.hideToolTip, false, 0, true);
            addEventListener(DataItemEvent.XPROMO_GIFT_SENT, this.turnOffGifting, false, 0, true);
            this.m_marketPanel.append(this.m_market);
            if (sendTabIsOn == 0)
            {
                this.m_marketPanel.appendAll(ASwingHelper.verticalStrut(-21), _loc_3);
            }
            ASwingHelper.prepare(this);
            return;
        }//end

         protected JPanel  createInteriorHolder ()
        {
            _loc_10 = undefined;
            DisplayObject _loc_11 =null ;
            this.m_marketPanel = ASwingHelper.makeSoftBoxJPanelVertical();
            _loc_1 = ASwingHelper.makeMultilineText(ZLoc.t("Dialogs","XGameGifting_"+this.m_gameId +"_prompt"),510,EmbeddedArt.defaultFontNameBold ,TextFormatAlign.LEFT ,16,EmbeddedArt.brownTextColor );
            _loc_2 = ASwingHelper.makeSoftBoxJPanel ();
            _loc_2.appendAll(_loc_1);
            ASwingHelper.setEasyBorder(_loc_2, 10, 30, 10, 10);
            _loc_3 = ASwingHelper.makeSoftBoxJPanelVertical ();
            _loc_3.setBackgroundDecorator(new AssetBackground((DisplayObject)new m_assetDict.get("speech_bubble")));
            _loc_3.setPreferredSize(new IntDimension(555, 127));
            this.m_timerPane = this.makeTimer();
            if (this.m_manager.timeToNextGifting == 0)
            {
                this.m_timerPane.visible = false;
            }
            _loc_3.appendAll(_loc_2, this.m_timerPane);
            _loc_4 = ASwingHelper.makeSoftBoxJPanelVertical ();
            _loc_4.appendAll(this.m_marketPanel);
            _loc_5 = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.RIGHT );
            _loc_5.appendAll(_loc_3, ASwingHelper.verticalStrut(10), this.makeTabsPanel(), _loc_4);
            _loc_6 = ASwingHelper.makeSoftBoxJPanel ();
            _loc_6.appendAll(ASwingHelper.horizontalStrut(145), _loc_5);
            _loc_7 = ASwingHelper.makeSoftBoxJPanelVertical ();
            _loc_7.setPreferredSize(new IntDimension(WIN_WIDTH, WIN_HEIGHT));
            _loc_7.appendAll(createHeaderPanel(), _loc_6, ASwingHelper.verticalStrut(25), this.makeButtonPanel());
            int _loc_8 =1;
            _loc_9 =Global.player.getReceivedXGameGiftsByGameId(this.m_gameId );
            for(int i0 = 0; i0 < _loc_9.size(); i0++)
            {
            	_loc_10 = _loc_9.get(i0);

                _loc_8 = 0;
                break;
            }
            this.setSelectedTab(_loc_8);
            _loc_11 =(DisplayObject) new assetDict.get("logo_farmville");
            _loc_7.addChild(_loc_11);
            _loc_11.y = 475;
            return _loc_7;
        }//end

        private JPanel  makeTabsPanel ()
        {
            String _loc_2 =null ;
            String _loc_3 =null ;
            JLabel _loc_4 =null ;
            JPanel _loc_5 =null ;
            _loc_1 = ASwingHelper.makeSoftBoxJPanel ();
            this.m_tabs = new Array();
            _loc_1.append(ASwingHelper.horizontalStrut(23));
            for(int i0 = 0; i0 < this.m_tabLabels.size(); i0++)
            {
            	_loc_2 = this.m_tabLabels.get(i0);

                _loc_3 = TextFieldUtil.formatSmallCapsString(ZLoc.t("Dialogs", _loc_2));
                _loc_4 = ASwingHelper.makeLabel(_loc_3, EmbeddedArt.titleFont, 16, EmbeddedArt.darkBlueTextColor, JLabel.CENTER);
                _loc_5 = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.CENTER);
                _loc_5.setBackgroundDecorator(new AssetBackground(new (DisplayObject)m_assetDict.get("tab")));
                _loc_5.setPreferredSize(new IntDimension(TAB_WIDTH, TAB_HEIGHT));
                _loc_5.append(_loc_4);
                _loc_5.addEventListener(MouseEvent.CLICK, this.onTabClicked, false, 0, true);
                ASwingHelper.prepare(_loc_5);
                this.m_tabWidth = _loc_5.getWidth();
                _loc_1.appendAll(_loc_5, ASwingHelper.horizontalStrut(5));
                this.m_tabs.push(_loc_5);
            }
            return _loc_1;
        }//end

        private JPanel  makeButtonPanel ()
        {
            _loc_1 = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.CENTER );
            this.m_btnDone = new CustomButton(ZLoc.t("Dialogs", "XGameGifting_goToFarm"), null, "GreenButtonUI");
            this.m_btnDone.addActionListener(this.onBtnDoneClicked, 0, true);
            _loc_1.append(this.m_btnDone);
            return _loc_1;
        }//end

        private void  onTabClicked (MouseEvent event )
        {
            _loc_2 = this.m_tabs.indexOf(event.currentTarget );
            if (_loc_2 != -1)
            {
                this.setSelectedTab(_loc_2);
            }
            return;
        }//end

        private void  onBtnDoneClicked (Event event )
        {
            GlobalEngine.socialNetwork.redirect("http://apps.facebook.com/onthefarm/track.php?cat=xpromo&affiliate=cityville&creative=city_mailbox", null, "_blank", false);
            this.closeMe();
            return;
        }//end

        private JPanel  makeTimer ()
        {
            _loc_1 = ASwingHelper.makeFlowJPanel(FlowLayout.LEFT );
            AssetPane _loc_2 =new AssetPane(new (DisplayObject)m_assetDict.get( "payroll_icon_clock"));
            _loc_3 = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.TOP ,-5);
            this.m_timerText = ASwingHelper.makeLabel(this.calcTimeLabel(), EmbeddedArt.titleFont, 30, EmbeddedArt.lightOrangeTextColor, JLabel.LEFT);
            _loc_4 = ASwingHelper.makeLabel(ZLoc.t("Dialogs","RollCall_timeRemaining"),EmbeddedArt.defaultFontNameBold ,12,EmbeddedArt.blueTextColor );
            this.m_timerText.setPreferredWidth(230);
            this.m_timer.addEventListener(TimerEvent.TIMER, this.updateTimer, false, 0, true);
            this.m_timer.start();
            _loc_3.appendAll(ASwingHelper.leftAlignElement(this.m_timerText), ASwingHelper.leftAlignElement(_loc_4));
            _loc_1.appendAll(_loc_2, _loc_3);
            ASwingHelper.setEasyBorder(_loc_1, 0, 30);
            return _loc_1;
        }//end

        private void  turnOnGifting ()
        {
            if (this.m_timer.running)
            {
                this.m_timer.stop();
            }
            if (this.m_market)
            {
                this.m_market.enableElements();
            }
            if (this.m_timerPane)
            {
                this.m_timerPane.visible = false;
            }
            return;
        }//end

        private void  turnOffGifting (DataItemEvent event =null )
        {
            if (!this.m_timer.running)
            {
                this.m_timer.start();
            }
            this.m_timerPane.visible = true;
            this.m_market.disableElements();
            return;
        }//end

        private void  updateTimer (TimerEvent event )
        {
            this.m_timerText.setText(this.calcTimeLabel());
            return;
        }//end

        private String  calcTimeLabel ()
        {
            String _loc_1 =null ;
            _loc_2 = this.m_manager.timeToNextGifting ;
            if (_loc_2 <= 0)
            {
                this.turnOnGifting();
            }
            _loc_1 = CardUtil.formatTimeHours(_loc_2);
            return _loc_1;
        }//end

        private void  showToolTip (DataItemEvent event )
        {
            XML _loc_4 =null ;
            String _loc_5 =null ;
            Object _loc_6 =null ;
            String _loc_7 =null ;
            if (!this.m_toolTipWindow)
            {
                this.m_toolTipWindow = new JWindow(this);
            }
            _loc_2 = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.TOP );
            ItemCatalogToolTip _loc_3 =new ItemCatalogToolTip ();
            if (event.target instanceof XGameGiftSendCell)
            {
                _loc_3.changeInfo(event.item);
            }
            else
            {
                _loc_4 = Global.gameSettings().getReceivableXGameGiftDefinitionByItemName(event.item.name);
                _loc_5 = null;
                if (_loc_4)
                {
                    _loc_7 = _loc_4.attribute("level").length() > 0 ? (_loc_4.attribute("level").toString()) : ("1");
                    _loc_5 = ZLoc.t("Dialogs", "TT_RequiredLevelInFarm", {level:_loc_7});
                }
                _loc_6 = {type:TooltipType.RECEIVABLE_XGAME_GIFT, title:ZLoc.t("Items", event.item.name + "_friendlyName"), requiredLevel:_loc_5};
                _loc_3.changeInfo(null, _loc_6);
            }
            _loc_2.appendAll(_loc_3);
            ASwingHelper.prepare(_loc_3);
            this.m_toolTipWindow.setContentPane(_loc_2);
            this.m_toolTipWindow.setX(event.pt.x - (_loc_3.width - 85) / 2);
            this.m_toolTipWindow.setY(event.pt.y - _loc_3.height - 10);
            ASwingHelper.prepare(this.m_toolTipWindow);
            this.m_toolTipWindow.mouseChildren = false;
            this.m_toolTipWindow.mouseEnabled = false;
            this.m_toolTipWindow.show();
            return;
        }//end

        private void  hideToolTip (DataItemEvent event )
        {
            this.m_toolTipWindow.hide();
            return;
        }//end

    }



