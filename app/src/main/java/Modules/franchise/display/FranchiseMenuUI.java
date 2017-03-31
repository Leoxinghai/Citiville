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
import Classes.doobers.*;
import Classes.util.*;
import Display.*;
import Display.aswingui.*;
import Display.hud.*;
import Display.hud.components.*;
import Modules.franchise.data.*;
import Modules.franchise.transactions.*;

import com.zynga.skelly.util.color.*;
//import flash.display.*;
//import flash.events.*;
//import flash.filters.*;
//import flash.geom.*;
//import flash.text.*;
//import flash.utils.*;
import org.aswing.*;
import org.aswing.border.*;
import org.aswing.event.*;
import org.aswing.geom.*;

    public class FranchiseMenuUI extends JPanel
    {
        protected FranchiseMenu m_franchise ;
        protected OwnedFranchiseData m_selected ;
        private JPanel m_topPanel ;
        private JPanel m_menuButtonPanel ;
        private JPanel m_titlePanel ;
        private JPanel m_infoPanel ;
        private CustomButton m_infoButton ;
        private FranchiseTabScrollingList m_tabShelf ;
        private FranchiseCardScrollingList m_cardShelf ;
        private JPanel m_ruleHorizPanel ;
        private JPanel m_tabScrollerPanel ;
        private JPanel m_cardScrollerPanel ;
        private DisplayObject bgAsset ;
        private Array m_tabItems ;
        private Timer m_nextCollectTimer ;
        private JLabel m_nextCollectText ;
        private double m_secondsLeft =86400;
        private double m_lastTimeCheck ;
        protected Object m_comObject ;
        private FranchiseDataModel m_model ;
        private static Array m_remindList ;
        private static  int ROW_HEIGHT =59;
        private static  int ROW_WIDTH =659;
        private static  int BUTTON_HEIGHT =35;
        private static  int BUTTON_WIDTH =165;
        private static  int TIMER_HEIGHT =50;
        private static  int TIMER_WIDTH =165;
        private static  int ROW_COUNT_MODULO =4;
        public static Dictionary m_assetDict ;

        public  FranchiseMenuUI ()
        {
            super(new SoftBoxLayout(SoftBoxLayout.Y_AXIS, -3, SoftBoxLayout.TOP));
            m_assetDict = new Dictionary(true);
            Global.delayedAssets.get(DelayedAssetLoader.FRANCHISE_ASSETS, this.makeFrAssets);
            Global.delayedAssets.get("assets/dialogs/InventoryAssets.swf", this.makeAssets);
            this.makeBackground();
            return;
        }//end

        public void  init (FranchiseMenu param1 ,OwnedFranchiseData param2 )
        {
            this.m_franchise = param1;
            this.m_selected = param2;
            this.m_model = Global.franchiseManager.model;
            this.initArrays();
            this.m_topPanel = new JPanel(new BorderLayout());
            this.m_tabScrollerPanel = new JPanel(new BorderLayout());
            this.m_cardScrollerPanel = new JPanel(new BorderLayout());
            this.m_infoPanel = new JPanel(new BorderLayout());
            this.m_ruleHorizPanel = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.CENTER);
            m_remindList = new Array();
            this.makeTopPanel();
            this.append(ASwingHelper.verticalStrut(9));
            this.makeTabPanel(FranchiseTabScrollingList.NUM_ITEMS);
            this.makeInfoPanel();
            this.makeRuleHorizPanel();
            this.makeCardPanel();
            return;
        }//end

        protected void  makeFrAssets (DisplayObject param1 ,String param2 )
        {
            this.m_comObject = param1;
            FranchiseMenuUI.m_assetDict.put("fr_up_normal",  this.m_comObject.fr_up_normal);
            FranchiseMenuUI.m_assetDict.put("fr_up_down",  this.m_comObject.fr_up_down);
            FranchiseMenuUI.m_assetDict.put("fr_up_over",  this.m_comObject.fr_up_over);
            FranchiseMenuUI.m_assetDict.put("fr_down_normal",  this.m_comObject.fr_down_normal);
            FranchiseMenuUI.m_assetDict.put("fr_down_down",  this.m_comObject.fr_down_down);
            FranchiseMenuUI.m_assetDict.put("fr_down_over",  this.m_comObject.fr_down_over);
            FranchiseMenuUI.m_assetDict.put("fr_prev_normal",  this.m_comObject.fr_prev_normal);
            FranchiseMenuUI.m_assetDict.put("fr_prev_down",  this.m_comObject.fr_prev_down);
            FranchiseMenuUI.m_assetDict.put("fr_prev_over",  this.m_comObject.fr_prev_over);
            FranchiseMenuUI.m_assetDict.put("fr_next_normal",  this.m_comObject.fr_next_normal);
            FranchiseMenuUI.m_assetDict.put("fr_next_down",  this.m_comObject.fr_next_down);
            FranchiseMenuUI.m_assetDict.put("fr_next_over",  this.m_comObject.fr_next_over);
            FranchiseMenuUI.m_assetDict.put("fr_star_empty",  this.m_comObject.fr_star_empty);
            FranchiseMenuUI.m_assetDict.put("fr_star_full",  this.m_comObject.fr_star_full);
            FranchiseMenuUI.m_assetDict.put("fr_star_disabled",  this.m_comObject.fr_star_disabled);
            FranchiseMenuUI.m_assetDict.put("fr_row_top",  this.m_comObject.fr_row_top);
            FranchiseMenuUI.m_assetDict.put("fr_row_mid",  this.m_comObject.fr_row_mid);
            FranchiseMenuUI.m_assetDict.put("fr_row_bot",  this.m_comObject.fr_row_bot);
            FranchiseMenuUI.m_assetDict.put("fr_background",  this.m_comObject.fr_background);
            FranchiseMenuUI.m_assetDict.put("fr_ruleH",  this.m_comObject.fr_ruleH);
            FranchiseMenuUI.m_assetDict.put("fr_rule",  this.m_comObject.fr_rule);
            FranchiseMenuUI.m_assetDict.put("fr_tab_normal_new",  this.m_comObject.fr_tab_normal_new);
            FranchiseMenuUI.m_assetDict.put("fr_tab_normalNewFranchise_new",  this.m_comObject.fr_tab_normalNewFranchise_new);
            FranchiseMenuUI.m_assetDict.put("fr_tab_selected_new",  this.m_comObject.fr_tab_selected_new);
            return;
        }//end

        protected void  makeAssets (DisplayObject param1 ,String param2 )
        {
            this.m_comObject = param1;
            FranchiseMenuUI.m_assetDict.put("btn_close_big_up",  this.m_comObject.btn_close_big_up);
            FranchiseMenuUI.m_assetDict.put("btn_close_big_over",  this.m_comObject.btn_close_big_over);
            FranchiseMenuUI.m_assetDict.put("btn_close_big_down",  this.m_comObject.btn_close_big_down);
            return;
        }//end

        protected void  initArrays ()
        {
            OwnedFranchiseData _loc_2 =null ;
            _loc_1 =Global.franchiseManager.getAllFranchises ();
            this.m_tabItems = new Array();
            for(int i0 = 0; i0 < _loc_1.size(); i0++)
            {
            		_loc_2 = _loc_1.get(i0);

                if (_loc_2.getLocationCount() > 0)
                {
                    this.m_tabItems.push(_loc_2);
                }
            }
            return;
        }//end

        protected void  makeBackground ()
        {
            this.bgAsset =(DisplayObject) new FranchiseMenuUI.m_assetDict.get("fr_background");
            MarginBackground _loc_1 =new MarginBackground(this.bgAsset ,new Insets(0,0,0,0));
            this.setBackgroundDecorator(_loc_1);
            this.setPreferredSize(new IntDimension(this.bgAsset.width, this.bgAsset.height));
            return;
        }//end

        protected void  makeTopPanel ()
        {
            this.m_topPanel.setBorder(new EmptyBorder(null, new Insets(0, 35, 0, 0)));
            this.m_titlePanel = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.CENTER);
            _loc_1 = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.CENTER );
            _loc_2 = ZLoc.t("Dialogs","FranchiseTitle");
            _loc_3 = ASwingHelper.makeTextField(_loc_2 ,EmbeddedArt.titleFont ,30,16508714);
            _loc_3.filters = .get(new GlowFilter(33724, 1, 2, 2, 10), new DropShadowFilter(4, 45, 2446451, 1, 0, 0, 1));
            TextFormat _loc_4 =new TextFormat ();
            _loc_4.size = 40;
            TextFieldUtil.formatSmallCaps(_loc_3.getTextField(), _loc_4);
            _loc_1.append(_loc_3);
            this.m_titlePanel.append(ASwingHelper.verticalStrut(9));
            this.m_titlePanel.append(_loc_1, BorderLayout.SOUTH);
            ASwingHelper.prepare(this.m_titlePanel);
            this.m_menuButtonPanel = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.TOP);
            this.m_menuButtonPanel.setBorder(new EmptyBorder(null, new Insets(17, 0, 0, 17)));
            JButton _loc_5 =new JButton ();
            DisplayObject _loc_6 =(DisplayObject)new FranchiseMenuUI.m_assetDict.get( "btn_close_big_up");
            DisplayObject _loc_7 =(DisplayObject)new FranchiseMenuUI.m_assetDict.get( "btn_close_big_down");
            DisplayObject _loc_8 =(DisplayObject)new FranchiseMenuUI.m_assetDict.get( "btn_close_big_over");
            _loc_5.wrapSimpleButton(new SimpleButton(_loc_6, _loc_8, _loc_7, _loc_6));
            _loc_5.addActionListener(this.closeFranchise, 0, true);
            this.m_menuButtonPanel.appendAll(_loc_5);
            this.m_topPanel.append(this.m_titlePanel, BorderLayout.CENTER);
            this.m_topPanel.append(this.m_menuButtonPanel, BorderLayout.EAST);
            this.m_topPanel.setPreferredHeight(72);
            this.m_topPanel.setMinimumHeight(72);
            this.m_topPanel.setMaximumHeight(72);
            ASwingHelper.prepare(this.m_topPanel);
            this.append(this.m_topPanel);
            ASwingHelper.prepare(this);
            return;
        }//end

        protected void  makeTabPanel (int param1 )
        {
            this.m_tabScrollerPanel.removeAll();
            this.m_tabScrollerPanel.setBorder(new EmptyBorder(null, new Insets(0, 0, 0, 0)));
            this.m_tabScrollerPanel.setPreferredHeight(122);
            this.m_tabShelf = new FranchiseTabScrollingList(this.m_tabItems, FranchiseTabFactory, 0, 6, 1, param1);
            ASwingHelper.prepare(this.m_tabShelf);
            this.m_tabScrollerPanel.append(ASwingHelper.verticalStrut(5), BorderLayout.NORTH);
            this.m_tabScrollerPanel.append(this.m_tabShelf, BorderLayout.CENTER);
            ASwingHelper.prepare(this.m_tabScrollerPanel);
            this.append(this.m_tabScrollerPanel);
            ASwingHelper.prepare(this);
            return;
        }//end

        protected void  makeInfoPanel ()
        {
            JLabel _loc_3 =null ;
            ASFont _loc_4 =null ;
            JPanel _loc_5 =null ;
            JLabel _loc_6 =null ;
            int _loc_7 =0;
            int _loc_8 =0;
            double _loc_9 =0;
            double _loc_10 =0;
            String _loc_11 =null ;
            String _loc_12 =null ;
            double _loc_13 =0;
            AssetPane _loc_14 =null ;
            this.m_infoPanel.removeAll();
            this.m_infoPanel.setBorder(new EmptyBorder(null, new Insets(0, 0, 0, 0)));
            this.m_infoPanel.setPreferredSize(new IntDimension(ROW_WIDTH, 59));
            this.m_infoPanel.setMinimumSize(new IntDimension(ROW_WIDTH, 59));
            this.m_infoPanel.setMaximumSize(new IntDimension(ROW_WIDTH, 59));
            _loc_1 = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.CENTER );
            _loc_2 = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.CENTER );
            if (FranchiseMenu.selectedFranchise && FranchiseMenu.selectedFranchise.dailyBonusAvailable)
            {
                this.m_infoButton = new CustomButton(TextFieldUtil.formatSmallCapsString(ZLoc.t("Dialogs", "Collect")), null, "GreenButtonUI");
                _loc_4 = this.m_infoButton.getFont();
                this.m_infoButton.setFont(_loc_4.changeSize(TextFieldUtil.getLocaleFontSizeByRatio(_loc_4.getSize(), 1, [{locale:"de", ratio:0.8}, {locale:"id", ratio:0.8}, {locale:"es", ratio:0.9}, {locale:"fr", ratio:0.8}, {locale:"it", ratio:0.8}, {locale:"pt", ratio:0.8}, {locale:"tr", ratio:0.8}])));
                this.m_infoButton.setPreferredSize(new IntDimension(BUTTON_WIDTH - 4, BUTTON_HEIGHT));
                this.m_infoButton.setMinimumSize(new IntDimension(BUTTON_WIDTH - 4, BUTTON_HEIGHT));
                this.m_infoButton.setMaximumSize(new IntDimension(BUTTON_WIDTH - 4, BUTTON_HEIGHT));
                this.m_infoButton.addEventListener(MouseEvent.CLICK, this.onCollectClick, false, 0, true);
                _loc_1.setBorder(new EmptyBorder(null, new Insets(13, 0, 0, 0)));
                _loc_1.appendAll(ASwingHelper.horizontalStrut(3), this.m_infoButton, ASwingHelper.horizontalStrut(3));
                _loc_1.append(ASwingHelper.horizontalStrut(80));
                ASwingHelper.prepare(_loc_1);
                this.m_infoButton.setEnabled(!Global.franchiseManager.isPendingFranchise(FranchiseMenu.selectedFranchise.franchiseType));
                _loc_3 = new JLabel(ZLoc.t("Dialogs", "DailyBonusCall"));
                _loc_3.setForeground(new ASColor(FranchiseMenu.COLOR_MENU_STANDARD));
                _loc_3.setFont(ASwingHelper.getBoldFont(16));
                _loc_2.append(ASwingHelper.horizontalStrut(40));
                _loc_2.append(_loc_3);
                ASwingHelper.prepare(_loc_2);
            }
            else
            {
                _loc_5 = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.CENTER);
                _loc_6 = new JLabel(ZLoc.t("Dialogs", "CollectIn"));
                _loc_6.setForeground(new ASColor(FranchiseMenu.COLOR_MENU_STANDARD));
                _loc_6.setFont(ASwingHelper.getBoldFont(16));
                _loc_5.setPreferredSize(new IntDimension(TIMER_WIDTH, TIMER_HEIGHT));
                _loc_5.setMinimumSize(new IntDimension(TIMER_WIDTH, TIMER_HEIGHT));
                _loc_5.setMaximumSize(new IntDimension(TIMER_WIDTH, TIMER_HEIGHT));
                this.m_nextCollectText = new JLabel(" ");
                this.m_nextCollectText.setForeground(new ASColor(FranchiseMenu.COLOR_MENU_STANDARD));
                this.m_nextCollectText.setFont(ASwingHelper.getBoldFont(16));
                _loc_5.appendAll(_loc_6, this.m_nextCollectText);
                _loc_1.append(_loc_5);
                _loc_1.append(ASwingHelper.horizontalStrut(85));
                this.m_nextCollectTimer = new Timer(958);
                this.m_nextCollectTimer.addEventListener(TimerEvent.TIMER, this.updateCollectTimer);
                this.m_nextCollectTimer.start();
                ASwingHelper.prepare(_loc_1);
                _loc_7 = 1;
                _loc_8 = 0;
                while (_loc_8 < this.m_tabItems.length())
                {

                    if ((this.m_tabItems.get(_loc_8) as OwnedFranchiseData).franchiseType == FranchiseMenu.selectedFranchise.franchiseType)
                    {
                        _loc_7 = _loc_8 + 1;
                        break;
                    }
                    _loc_8++;
                }
                _loc_7 = Math.min(4, _loc_7);
                _loc_9 = Global.gameSettings().getNumber("franchise" + _loc_7.toString() + "DailyBonus");
                _loc_10 = this.m_model.getFranchiseCountByType(FranchiseMenu.selectedFranchise.franchiseType);
                _loc_11 = this.m_model.getFranchiseName(FranchiseMenu.selectedFranchise.franchiseType);
                _loc_12 = ZLoc.t("Dialogs", "DailyBonusInfo", {amount:_loc_9, business:_loc_11});
                _loc_13 = 360;
                _loc_14 = ASwingHelper.makeMultilineText(_loc_12 + " ", _loc_13, EmbeddedArt.defaultFontNameBold, TextFormatAlign.LEFT, 16, FranchiseMenu.COLOR_MENU_STANDARD);
                _loc_14.setVerticalAlignment(JLabel.CENTER);
                _loc_2.append(ASwingHelper.horizontalStrut(30));
                _loc_2.appendAll(_loc_14);
                ASwingHelper.prepare(_loc_2);
            }
            this.m_infoPanel.append(_loc_1, BorderLayout.EAST);
            this.m_infoPanel.append(_loc_2, BorderLayout.CENTER);
            ASwingHelper.prepare(this.m_infoPanel);
            this.append(this.m_infoPanel);
            ASwingHelper.prepare(this);
            return;
        }//end

        public void  updateCollectTimer (TimerEvent event )
        {
            int _loc_2 =0;
            if (!FranchiseMenu.selectedFranchise.dailyBonusAvailable)
            {
                _loc_2 = int(FranchiseMenu.selectedFranchise.dailyBonusLastCollect + FranchiseMenu.dailyCycleDelta - GlobalEngine.getTimer() / 1000);
                if (_loc_2 <= 0)
                {
                    this.m_nextCollectText.visible = false;
                    this.m_nextCollectText.setText("");
                    FranchiseMenu.selectedFranchise.dailyBonusAvailable = true;
                    this.switchBusiness(FranchiseMenu.selectedFranchise.franchiseType);
                }
                else
                {
                    this.m_nextCollectText.visible = true;
                    this.m_nextCollectText.setText(GameUtil.formatMinutesSeconds(_loc_2 * 86400 / Global.gameSettings().inGameDaySeconds));
                }
            }
            else
            {
                this.m_nextCollectText.visible = false;
                this.m_nextCollectText.setText("");
            }
            return;
        }//end

        protected void  makeRuleHorizPanel ()
        {
            this.m_ruleHorizPanel.removeAll();
            DisplayObject _loc_1 =(DisplayObject)new FranchiseMenuUI.m_assetDict.get( "fr_ruleH");
            AssetPane _loc_2 =new AssetPane(_loc_1 );
            _loc_3 = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.CENTER );
            ASwingHelper.prepare(_loc_2);
            _loc_3.append(_loc_2);
            this.m_ruleHorizPanel.appendAll(ASwingHelper.verticalStrut(6), _loc_3, ASwingHelper.verticalStrut(3));
            this.append(this.m_ruleHorizPanel);
            return;
        }//end

        protected void  makeCardPanel ()
        {
            Vector _loc_1.<FranchiseExpansionData >=null ;
            this.m_cardScrollerPanel = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.CENTER);
            if (FranchiseMenu.selectedFranchise)
            {
                _loc_1 = Global.franchiseManager.getAllFranchisesByType(FranchiseMenu.selectedFranchise.franchiseType);
            }
            else
            {
                _loc_1 = new Vector<FranchiseExpansionData>();
            }
            this.addDisabledRow(_loc_1);
            this.completeEmptyRows(_loc_1);
            this.m_cardShelf = new FranchiseCardScrollingList(GameUtil.vectorToArray(_loc_1), FranchiseCardFactory, 0, 1, 4);
            ASwingHelper.prepare(this.m_cardShelf);
            this.m_cardScrollerPanel.append(this.m_cardShelf);
            this.append(this.m_cardScrollerPanel);
            ASwingHelper.prepare(this);
            return;
        }//end

        private void  addDisabledRow (Vector param1 .<FranchiseExpansionData >)
        {
            _loc_2 = FranchiseExpansionData.loadDummyObject(StarRatingComponent.DISABLED);
            param1.push(_loc_2);
            return;
        }//end

        private void  addEmptyRow (Vector param1 .<FranchiseExpansionData >)
        {
            _loc_2 = FranchiseExpansionData.loadDummyObject(StarRatingComponent.NONE);
            param1.push(_loc_2);
            return;
        }//end

        private void  completeEmptyRows (Vector param1 .<FranchiseExpansionData >)
        {
            while (param1.length % ROW_COUNT_MODULO != 0)
            {

                this.addEmptyRow(param1);
            }
            return;
        }//end

        public void  switchBusiness (String param1 )
        {
            FranchiseMenu.selectedFranchise = Global.franchiseManager.getFranchise(param1);
            this.initArrays();
            this.makeInfoPanel();
            this.makeRuleHorizPanel();
            if (this.m_cardShelf)
            {
                this.remove(this.m_cardScrollerPanel);
                this.m_cardScrollerPanel.remove(this.m_cardShelf);
                this.makeCardPanel();
            }
            this.m_tabShelf.setSelected(param1);
            return;
        }//end

        public void  refreshMenuUI ()
        {
            this.switchBusiness(FranchiseMenu.selectedFranchise.franchiseType);
            return;
        }//end

        protected void  onCollectClick (MouseEvent event )
        {
            int _loc_2 =0;
            int _loc_3 =0;
            double _loc_4 =0;
            if (FranchiseMenu.selectedFranchise.dailyBonusAvailable)
            {
                Global.franchiseManager.collectDailyBonus(FranchiseMenu.selectedFranchise.franchiseType);
                Sounds.play("Collect");
                GameTransactionManager.addTransaction(new TFranchiseDailyBonus(FranchiseMenu.selectedFranchise.franchiseType));
                _loc_2 = 1;
                _loc_3 = 0;
                while (_loc_3 < this.m_tabItems.length())
                {

                    if ((this.m_tabItems.get(_loc_3) as OwnedFranchiseData).franchiseType == FranchiseMenu.selectedFranchise.franchiseType)
                    {
                        _loc_2 = _loc_3 + 1;
                        break;
                    }
                    _loc_3++;
                }
                _loc_2 = Math.min(4, _loc_2);
                _loc_4 = this.m_model.getFranchiseCountByType(FranchiseMenu.selectedFranchise.franchiseType) * Global.gameSettings().getNumber("franchise" + _loc_2.toString() + "DailyBonus");
                Global.player.gold = Global.player.gold + _loc_4;
                this.doCoinFlyout(_loc_4, event.stageX, event.stageY);
                this.doSendSaleDoobers(_loc_4, event.stageX, event.stageY, false);
                this.switchBusiness(FranchiseMenu.selectedFranchise.franchiseType);
            }
            return;
        }//end

        protected void  onCloseDown (MouseEvent event )
        {
            event.stopImmediatePropagation();
            event.stopPropagation();
            return;
        }//end

        protected void  onCloseOver (MouseEvent event )
        {
            ColorMatrix _loc_2 =new ColorMatrix ();
            _loc_2.adjustBrightness(1.5, 1.5, 1.5);
            _loc_2.adjustContrast(1.5, 1.5, 1.5);
            (event.target as JButton).filters = .get(_loc_2.filter);
            return;
        }//end

        protected void  onCloseOut (MouseEvent event )
        {
            (event.target as JButton).filters = new Array();
            return;
        }//end

        protected void  closeFranchise (AWEvent event )
        {
            if (m_remindList.length > 0)
            {
                m_remindList.splice(0, m_remindList.length());
            }
            this.m_franchise.close();
            return;
        }//end

        public FranchiseTabScrollingList  tabShelf ()
        {
            return this.m_tabShelf;
        }//end

        public void  doCoinFlyout (double param1 ,int param2 ,int param3 )
        {
            _loc_4 = ZLoc.t("Main","GainCoins",{coins param1 });
            _loc_5 = new EmbeddedArt.smallCoinIcon ();
            UI.displayStatus(_loc_4, param2, param3, _loc_5);
            return;
        }//end

        public void  doSendSaleFlyout (double param1 ,double param2 ,double param3 ,double param4 )
        {
            _loc_5 = ZLoc.t("Main","SendSale",{coins param1 ,bonus });
            _loc_5 = "<font color=\'#008C46\'>" + _loc_5 + "</font>";
            _loc_6 = new EmbeddedArt.smallCoinIcon ();
            UI.displayStatus(_loc_5, param3, param4, _loc_6);
            return;
        }//end

        public void  doSendSaleDoobers (double param1 ,double param2 ,double param3 ,boolean param4 =true )
        {
            _loc_5 =Global.hud.getComponent(HUD.COMP_COINS );
            _loc_6 =Global.hud.localToGlobal(new Point(_loc_5.x ,_loc_5.y ));
            _loc_7 =Global.hud.getComponent(HUD.COMP_ENERGY );
            _loc_8 =Global.hud.localToGlobal(new Point(_loc_7.x ,_loc_7.y ));
            _loc_9 =Global.gameSettings().getDooberFromType(Doober.DOOBER_COIN ,10);
            Point _loc_10 =new Point(param2 ,param3 );
            _loc_11 = this.getDooberCountFromCoins(param1 );
            while (_loc_11 > 1)
            {

                setTimeout(Global.world.dooberManager.createDummyDoober, 200, _loc_9, _loc_10, _loc_6);
                if (param4)
                {
                    setTimeout(Global.world.dooberManager.createDummyDoober, 200, Global.gameSettings().getDooberFromType(Doober.DOOBER_ENERGY, 5), _loc_10, _loc_8);
                }
                _loc_11 = _loc_11 - 1;
            }
            if (_loc_11 == 1)
            {
                Global.world.dooberManager.createDummyDoober(_loc_9, _loc_10, _loc_6);
                if (param4)
                {
                    Global.world.dooberManager.createDummyDoober(Global.gameSettings().getDooberFromType(Doober.DOOBER_ENERGY, 5), _loc_10, _loc_8);
                }
            }
            return;
        }//end

        private int  getDooberCountFromCoins (double param1 )
        {
            _loc_2 = param1/50+1;
            return _loc_2;
        }//end

        private static boolean  isFriendInRemindList (String param1 )
        {
            Array _loc_4 =null ;
            String _loc_2 ="";
            int _loc_3 =0;
            while (_loc_3 < m_remindList.length())
            {

                _loc_2 = m_remindList.get(_loc_3);
                _loc_4 = _loc_2.split(":");
                if (_loc_4.length == 2)
                {
                    if (_loc_4.get(1) == param1)
                    {
                        return true;
                    }
                }
                _loc_3++;
            }
            return false;
        }//end

        public static void  addFriendReminder (String param1 ,String param2 )
        {
            if (isFriendInRemindList(param2))
            {
                return;
            }
            _loc_3 =Global.franchiseManager.getBusinessFromType(FranchiseMenu.selectedFranchise.franchiseType );
            FranchiseViralManager.triggerFranchiseViralFeeds(FranchiseViralManager.VIRAL_REMINDERACCEPTBONUS, param1, param2, null, 0, _loc_3);
            m_remindList.push(param1 + ":" + param2);
            return;
        }//end

    }



