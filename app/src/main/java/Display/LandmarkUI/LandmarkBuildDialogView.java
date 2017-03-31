package Display.LandmarkUI;

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
import Classes.actions.*;
import Classes.doobers.*;
import Classes.effects.*;
import Classes.gates.*;
import Classes.util.*;
import Display.*;
import Display.DialogUI.*;
import Display.GateUI.*;
import Display.MarketUI.*;
import Display.aswingui.*;
import Engine.Managers.*;
import Events.*;
import Modules.landmarks.*;
import Modules.stats.types.*;
//import flash.display.*;
//import flash.events.*;
//import flash.text.*;
//import flash.utils.*;
import org.aswing.*;
import org.aswing.border.*;
import org.aswing.event.*;
import org.aswing.geom.*;

    public class LandmarkBuildDialogView extends GenericDialogView
    {
        private boolean m_questCompleted =false ;
        protected Class m_horizontalRule ;
        protected DisplayObject m_verticalRule ;
        protected DisplayObject m_rewardItemBG ;
        protected Class m_tasksBG ;
        protected Array m_gates ;
        protected CompositeGate m_compositeGate ;
        protected Object m_data ;
        protected JPanel m_innerInteriorHolder ;
        protected JPanel m_outerInteriorHolder ;
        protected DisplayObject m_closebtnOver ;
        protected DisplayObject m_npcIcon ;
        protected Object m_locObjects ;
        protected JTextField m_sectionTitle ;
        protected MarketScrollingList m_inventoryList ;
        protected CustomButton m_okayButton ;
        protected DisplayObject m_speechBubbleBG ;
        protected DisplayObject m_speechBubbleTail ;
        protected double m_speechBubbleTailOffset =0;
        protected int m_numVisibleGates =-1;
        protected String m_hintTextKey ;
        protected String m_headerTextKey ;
        protected String m_subTitleKey ;
        protected double m_bottomSpeechBubbleOffset =0;
        protected Array m_taskPanels ;
        protected double m_textBodyWidth =0;
        protected JPanel m_gateRowsAlignPanel ;
        protected JPanel m_gateRowsPanel ;
        protected JPanel m_textContainer ;
        protected JPanel m_infoOuterPanel ;
        protected JPanel m_wonderContainer ;
        protected JPanel m_wonderPane ;
        protected DisplayObject m_checkMark ;
        protected AssetPane m_iconPane ;
        protected Sprite m_backgroundSprite ;
        protected AssetPane m_backgroundPane ;
        private MapResource m_mapResource ;
public static  int TASK_INCOMPLETE_GAP =91;
public static  int TASK_COMPLETE_GAP =70;
public static  int HINT_TEXT_MIN_WIDTH =300;
public static  int HINT_TEXT_MAX_WIDTH =320;
public static  int HEADER_TEXT_WIDTH =440;
public static  int MAX_HEADER_TEXT_WIDTH =680;
public static  int GAP_ABOVE_NPC =14;
public static  int TEXT_PANEL_SIDE_PADDING =15;
        private static  int TASK_PANEL_SIDE_PADDING =3;
public static  int REWARD_PANEL_SIDE_PADDING =10;
public static  int SPEECH_BUBBLE_MARGIN_TOP_OFFSET =15;
public static  int SPEECH_BUBBLE_MARGIN_BOTTOM_OFFSET =-10;
public static  int SPEECH_BUBBLE_MARGIN_RIGHT_OFFSET =0;
public static  double TARGET_RESOURCE_ICON_SIZE =25;
public static  double TITLE_GAP =8;
public static  int TIMER_UPDATE_DELAY =1000;
public static  int ONE_DAY =86400;

        public  LandmarkBuildDialogView (Dictionary param1 ,Object param2 ,Array param3 ,CompositeGate param4 ,MapResource param5 ,String param6 )
        {
            MasteryGate _loc_9 =null ;
            this.m_gates = param3;
            this.m_compositeGate = param4;
            this.m_data = param2;
            this.m_mapResource = param5;
            this.m_taskPanels = new Array();
            super(param1, "", "", 0, null);
            this.setLayout(new SoftBoxLayout(SoftBoxLayout.Y_AXIS, 0, SoftBoxLayout.TOP));
            this.addEventListener(UIEvent.REFRESH_DIALOG, this.refresh, false, 0, true);
            _loc_7 =Global.experimentManager.getVariant(param6 );
            double _loc_8 =0;
            while (_loc_8 < this.m_gates.length())
            {

                if (this.m_gates.get(_loc_8) instanceof MasteryGate && (_loc_7 == 1 || _loc_7 == 2))
                {
                    _loc_9 =(MasteryGate) this.m_gates.get(_loc_8);
                    if (Global.player.getSeenFlag(_loc_9.getSeenFlag(_loc_9.unlockItemName, _loc_9.name)) == false)
                    {
                        Global.player.setSeenFlag(_loc_9.getSeenFlag(_loc_9.unlockItemName, _loc_9.name));
                    }
                }
                _loc_8 = _loc_8 + 1;
            }
            this.m_gates.sort(this.sortGateOrder);
            this.refresh(null);
            StatsManager.sample(100, StatsKingdomType.DIALOG_STATS, "multi_gate", StatsPhylumType.VIEW, ((ConstructionSite)param5).targetName);
            return;
        }//end

         protected void  init ()
        {
            this.initCommonAssets();
            _loc_1 =Global.gameSettings().getEffectByName("fireworks");
            _loc_2 = _loc_1.image.get(0) ;
            ItemImage _loc_3 =new ItemImage(_loc_2 );
            _loc_3.load();
            Sounds.loadSoundByName("cruise_fireworks");
            if (this.getNumVisibleTasks() == -1)
            {
                m_bgAsset = m_assetDict.get("imageBG_3");
            }
            else
            {
                m_bgAsset = m_assetDict.get("imageBG_" + Math.max(Math.min(this.getNumVisibleTasks(), 3), 0));
            }
            this.m_tasksBG = m_assetDict.get("tasksBG");
            this.makeCenterPanel();
            this.makeBackground();
            ASwingHelper.prepare(this);
            ASwingHelper.prepare(m_interiorHolder);
            ASwingHelper.setForcedWidth(this, 740);
            return;
        }//end

        protected void  initCommonAssets ()
        {
            this.m_questCompleted = m_assetDict.get("questComplete");
            this.m_npcIcon = m_assetDict.get("npcIcon");
            this.m_locObjects = m_assetDict.get("locObjects");
            this.m_horizontalRule = m_assetDict.get("horizontalRule");
            this.m_verticalRule = m_assetDict.get("verticalRule");
            this.m_rewardItemBG = m_assetDict.get("rewardItemBG");
            this.m_speechBubbleBG = m_assetDict.get("speechBG");
            this.m_speechBubbleTail = m_assetDict.get("speechTail");
            return;
        }//end

         protected void  makeBackground ()
        {
            this.setBackgroundDecorator(new MarginBackground(m_bgAsset, new Insets(0, 0, 16, 0)));
            return;
        }//end

         protected void  makeCenterPanel ()
        {
            m_interiorHolder = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.TOP);
            this.m_outerInteriorHolder = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.TOP);
            this.m_outerInteriorHolder.setBorder(new EmptyBorder(null, new Insets(0, 6, 0, 3)));
            this.m_innerInteriorHolder = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.TOP);
            this.makeHeader();
            this.makeInfoPanel();
            this.makeTaskRows();
            this.makeButton();
            ASwingHelper.prepare(m_interiorHolder);
            this.append(m_interiorHolder);
            ASwingHelper.prepare(this);
            return;
        }//end

        protected void  makeHeader (int param1 =20)
        {
            JPanel _loc_2 =new JPanel(new BorderLayout ());
            _loc_3 = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.CENTER );
            _loc_4 = ZLoc.t("Dialogs","LandmarkTitle_"+this.m_mapResource.getItemName ());
            _loc_5 = TextFieldUtil.getLocaleFontSize(30,20,.get( {locale size "ja",30) });
            TextFormat _loc_6 =new TextFormat ();
            _loc_6.size = _loc_5 + 6;
            _loc_6.align = TextFormatAlign.CENTER;
            _loc_6.leading = 30 - _loc_5;
            _loc_5 = ASwingHelper.shrinkFontSizeToFit(MAX_HEADER_TEXT_WIDTH, _loc_4, EmbeddedArt.titleFont, _loc_5, EmbeddedArt.questTitleFilters, null, _loc_6);
            _loc_6.size = _loc_5 + 6;
            _loc_6.leading = 30 - _loc_5;
            this.m_sectionTitle = ASwingHelper.makeTextField(_loc_4, EmbeddedArt.titleFont, _loc_5, EmbeddedArt.titleColor);
            this.m_sectionTitle.filters = EmbeddedArt.questTitleFilters;
            TextFieldUtil.formatSmallCaps(this.m_sectionTitle.getTextField(), _loc_6);
            _loc_3.append(ASwingHelper.verticalStrut(TITLE_GAP));
            _loc_3.append(this.m_sectionTitle);
            _loc_2.append(_loc_3, BorderLayout.CENTER);
            _loc_7 = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.TOP );
            _loc_8 = ASwingHelper.makeMarketCloseButton ();
            _loc_8.addActionListener(onCancelX, 0, true);
            _loc_7.append(_loc_8);
            ASwingHelper.setEasyBorder(_loc_7, 2, 0, 0, 6);
            _loc_2.append(_loc_7, BorderLayout.EAST);
            _loc_2.append(ASwingHelper.horizontalStrut(_loc_7.getPreferredWidth()), BorderLayout.WEST);
            m_interiorHolder.append(_loc_2);
            return;
        }//end

        protected void  makeInfoPanel ()
        {
            this.m_infoOuterPanel = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.LEFT);
            _loc_1 = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.BOTTOM );
            AssetPane _loc_2 =new AssetPane(this.m_npcIcon );
            _loc_1.append(ASwingHelper.verticalStrut(GAP_ABOVE_NPC));
            _loc_1.append(_loc_2);
            _loc_3 = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.CENTER ,this.m_speechBubbleTailOffset );
            AssetPane _loc_4 =new AssetPane(this.m_speechBubbleTail );
            _loc_3.append(ASwingHelper.verticalStrut(0));
            _loc_3.append(_loc_4);
            _loc_5 = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.TOP );
            _loc_5.setBackgroundDecorator(new MarginBackground(this.m_speechBubbleBG, new Insets(SPEECH_BUBBLE_MARGIN_TOP_OFFSET, 0, SPEECH_BUBBLE_MARGIN_BOTTOM_OFFSET + this.m_bottomSpeechBubbleOffset, SPEECH_BUBBLE_MARGIN_RIGHT_OFFSET)));
            this.m_textContainer = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.TOP);
            _loc_6 = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.LEFT );
            _loc_7 = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.LEFT );
            this.m_textBodyWidth = HINT_TEXT_MIN_WIDTH;
            if (this.m_sectionTitle.getPreferredWidth() > HEADER_TEXT_WIDTH)
            {
                this.m_textBodyWidth = this.m_textBodyWidth + (this.m_sectionTitle.getPreferredWidth() - HEADER_TEXT_WIDTH);
                this.m_textBodyWidth = Math.min(HINT_TEXT_MAX_WIDTH, this.m_textBodyWidth);
            }
            _loc_8 = ASwingHelper.makeMultilineText(ZLoc.t("Dialogs","LandmarkDescription_"+this.m_mapResource.getItemName ()),this.m_textBodyWidth ,EmbeddedArt.defaultFontNameBold ,TextFormatAlign.LEFT ,14,EmbeddedArt.brownTextColor );
            _loc_7.appendAll(_loc_8, this.createItemStatsPanel());
            _loc_9 = this.createLandmarkImagePanel ();
            _loc_6.append(ASwingHelper.horizontalStrut(TEXT_PANEL_SIDE_PADDING));
            _loc_6.append(_loc_9);
            _loc_6.append(_loc_7);
            this.m_textContainer.append(ASwingHelper.verticalStrut(GAP_ABOVE_NPC - 2));
            this.m_textContainer.append(ASwingHelper.verticalStrut(SPEECH_BUBBLE_MARGIN_TOP_OFFSET));
            this.m_textContainer.append(_loc_6);
            this.m_textContainer.append(ASwingHelper.verticalStrut(SPEECH_BUBBLE_MARGIN_BOTTOM_OFFSET + this.m_bottomSpeechBubbleOffset + 10));
            _loc_5.append(this.m_textContainer);
            _loc_10 = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.TOP );
            ASwingHelper.setEasyBorder(_loc_10, SPEECH_BUBBLE_MARGIN_TOP_OFFSET + 10, 0, SPEECH_BUBBLE_MARGIN_BOTTOM_OFFSET + this.m_bottomSpeechBubbleOffset + 10, 0);
            _loc_10.setBackgroundDecorator(new AssetBackground(this.m_verticalRule));
            _loc_10.setPreferredWidth(2);
            _loc_5.append(_loc_10);
            this.m_wonderContainer = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.TOP);
            this.m_wonderContainer.append(this.getWonderPane());
            _loc_5.append(ASwingHelper.horizontalStrut(REWARD_PANEL_SIDE_PADDING));
            _loc_5.append(this.m_wonderContainer);
            _loc_5.append(ASwingHelper.horizontalStrut(REWARD_PANEL_SIDE_PADDING));
            this.m_infoOuterPanel.append(ASwingHelper.horizontalStrut(TASK_PANEL_SIDE_PADDING));
            this.m_infoOuterPanel.append(_loc_1);
            this.m_infoOuterPanel.append(ASwingHelper.horizontalStrut(-5));
            this.m_infoOuterPanel.append(_loc_3);
            this.m_infoOuterPanel.append(ASwingHelper.horizontalStrut(-5));
            this.m_infoOuterPanel.append(_loc_5);
            this.m_infoOuterPanel.append(ASwingHelper.horizontalStrut(7));
            this.m_infoOuterPanel.swapChildren(_loc_5, _loc_3);
            this.m_outerInteriorHolder.append(this.m_infoOuterPanel);
            m_interiorHolder.append(this.m_outerInteriorHolder);
            return;
        }//end

        private JPanel  createLandmarkImagePanel ()
        {
            _loc_1 = m_assetDict.get("itemImage") ;
            DisplayObject _loc_2 =(DisplayObject)new m_assetDict.get( "burstImage");
            if (_loc_1 instanceof Bitmap)
            {
                ASwingHelper.scaleBitmapTo((Bitmap)_loc_1, 80, 80);
            }
            AssetPane _loc_3 =new AssetPane(_loc_1 );
            _loc_4 = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.CENTER );
            _loc_5 = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.LEFT );
            _loc_6 = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.CENTER );
            _loc_5.setBackgroundDecorator(new AssetBackground(_loc_2));
            _loc_5.setPreferredSize(new IntDimension(_loc_2.width, _loc_2.height));
            _loc_5.setMinimumSize(new IntDimension(_loc_2.width, _loc_2.height));
            _loc_5.setMaximumSize(new IntDimension(_loc_2.width, _loc_2.height));
            _loc_4.append(_loc_3);
            _loc_5.append(ASwingHelper.horizontalStrut((_loc_2.width - _loc_1.width) / 2));
            _loc_5.append(_loc_4);
            _loc_6.append(_loc_5);
            return _loc_6;
        }//end

        private JPanel  createItemStatsPanel ()
        {
            _loc_1 = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.X_AXIS ,10);
            _loc_2 = ASwingHelper.makeSoftBoxJPanelVertical(AsWingConstants.LEFT );
            _loc_3 = ASwingHelper.makeSoftBoxJPanelVertical(AsWingConstants.LEFT );
            _loc_4 = ASwingHelper.makeSoftBoxJPanelVertical(AsWingConstants.LEFT );
            _loc_5 = (DisplayObject)m_assetDict.pinkDiamondIcon
            _loc_6 = (DisplayObject)m_assetDict.goodsIcon
            if (_loc_5 instanceof Bitmap)
            {
                ASwingHelper.scaleBitmapTo((Bitmap)_loc_5, 40, 40);
            }
            if (_loc_6 instanceof Bitmap)
            {
                ASwingHelper.scaleBitmapTo((Bitmap)_loc_6, 40, 40);
            }
            AssetPane _loc_7 =new AssetPane(_loc_5 );
            AssetPane _loc_8 =new AssetPane(_loc_6 );
            _loc_9 = this(.m_mapResource as ConstructionSite ).targetItem ;
            _loc_10 = Math.round(Global.player.GetDooberMinimums(_loc_9 ,Doober.DOOBER_COIN )*_loc_9.customerCapacity );
            _loc_2.appendAll(ASwingHelper.makeLabel(ZLoc.t("Dialogs", "WonderEarningsTitle"), EmbeddedArt.defaultFontNameBold, 14, EmbeddedArt.blueTextColor, JLabel.LEFT), _loc_7, ASwingHelper.makeLabel(_loc_10.toString(), EmbeddedArt.defaultFontNameBold, 14, EmbeddedArt.greenTextColor, JLabel.LEFT));
            _loc_3.appendAll(ASwingHelper.makeLabel(ZLoc.t("Dialogs", "WonderSupplyTitle"), EmbeddedArt.defaultFontNameBold, 14, EmbeddedArt.blueTextColor, JLabel.LEFT), _loc_8, ASwingHelper.makeLabel(_loc_9.commodityReq.toString(), EmbeddedArt.defaultFontNameBold, 14, EmbeddedArt.redTextColor, JLabel.LEFT));
            _loc_1.appendAll(_loc_2, _loc_3, _loc_4);
            return _loc_1;
        }//end

        protected void  makeButton ()
        {
            JPanel _loc_1 =new JPanel(new FlowLayout(FlowLayout.CENTER ));
            if (this.allGatesUnlocked())
            {
                this.m_okayButton = new CustomButton(ZLoc.t("Dialogs", "FinishBuilding"), null, "GreenButtonUI");
                this.m_okayButton.addActionListener(this.finishBuilding, 0, true);
            }
            else
            {
                this.m_okayButton = new CustomButton(ZLoc.t("Dialogs", "FinishBuilding"), null, "GreyButtonUI");
                this.m_okayButton.setEnabled(false);
            }
            _loc_1.append(this.m_okayButton);
            m_interiorHolder.append(_loc_1);
            return;
        }//end

        private boolean  allGatesUnlocked ()
        {
            AbstractGate _loc_2 =null ;
            double _loc_1 =0;
            while (_loc_1 < this.m_gates.length())
            {

                _loc_2 = this.m_gates.get(_loc_1);
                if (!_loc_2.checkForKeys())
                {
                    return false;
                }
                _loc_1 = _loc_1 + 1;
            }
            return true;
        }//end

        private void  finishBuilding (AWEvent event )
        {
            if (this.allGatesUnlocked())
            {
                this.m_compositeGate.unlockGate();
                StatsManager.sample(100, StatsKingdomType.DIALOG_STATS, "multi_gate", "finish", (this.m_mapResource as ConstructionSite).targetName);
                this.m_mapResource.actionQueue.addActions(new ActionFunctionProgressBar(this.m_mapResource, this.doFireworks, (this.m_mapResource as ConstructionSite).completeBuilding, null, ZLoc.t("Main", "Building"), 5));
                this.closePanel(event);
            }
            return;
        }//end

        public boolean  doFireworks ()
        {
            MapResource resource ;
            resource = this.m_mapResource;
            MapResourceEffectFactory.createEffect(resource, EffectType.FIREWORK_BALLOONS);
            Sounds.play("cruise_fireworks");
            TimerUtil .callLater (void  ()
            {
                MapResourceEffectFactory.createEffect(resource, EffectType.FIREWORK_BALLOONS);
                return;
            }//end
            , 300);
            TimerUtil .callLater (void  ()
            {
                MapResourceEffectFactory.createEffect(resource, EffectType.FIREWORK_BALLOONS);
                return;
            }//end
            , 500);
            TimerUtil .callLater (void  ()
            {
                MapResourceEffectFactory.createEffect(resource, EffectType.FIREWORK_BALLOONS);
                return;
            }//end
            , 700);
            TimerUtil .callLater (void  ()
            {
                MapResourceEffectFactory.createEffect(resource, EffectType.FIREWORK_BALLOONS);
                return;
            }//end
            , 1100);
            TimerUtil .callLater (void  ()
            {
                MapResourceEffectFactory.createEffect(resource, EffectType.FIREWORK_BALLOONS);
                return;
            }//end
            , 1500);
            TimerUtil .callLater (void  ()
            {
                MapResourceEffectFactory.createEffect(resource, EffectType.FIREWORK_BALLOONS);
                return;
            }//end
            , 2000);
            TimerUtil .callLater (void  ()
            {
                MapResourceEffectFactory.createEffect(resource, EffectType.FIREWORK_BALLOONS);
                return;
            }//end
            , 2400);
            TimerUtil .callLater (void  ()
            {
                MapResourceEffectFactory.createEffect(resource, EffectType.FIREWORK_BALLOONS);
                return;
            }//end
            , 2800);
            TimerUtil .callLater (void  ()
            {
                MapResourceEffectFactory.createEffect(resource, EffectType.FIREWORK_BALLOONS);
                return;
            }//end
            , 3300);
            return true;
        }//end

        protected void  closePanel (AWEvent event )
        {
            if (this.m_acceptTextName.length > 0)
            {
                countDialogViewAction("OKAY");
            }
            this.cleanUp();
            dispatchEvent(new Event(Event.CLOSE, false, false));
            return;
        }//end

        protected JPanel  getWonderPane ()
        {
            this.m_wonderPane = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.TOP);
            _loc_1 = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.CENTER );
            _loc_2 = ZLoc.t("Dialogs","Locked");
            JLabel _loc_3 =new JLabel(_loc_2 );
            _loc_3.setFont(new ASFont(EmbeddedArt.defaultFontNameBold, 14, false, false, false, EmbeddedArt.advancedFontProps));
            _loc_3.setForeground(new ASColor(EmbeddedArt.brownTextColor));
            _loc_3.setVerticalAlignment(JLabel.CENTER);
            _loc_1.append(_loc_3);
            this.m_wonderPane.append(ASwingHelper.verticalStrut(20));
            this.m_wonderPane.append(_loc_1);
            _loc_4 = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.LEFT ,4);
            _loc_5 = ASwingHelper.makeSoftBoxJPanelVertical ();
            _loc_6 = m_assetDict.wonderImage;
            AssetPane _loc_7 =new AssetPane(_loc_6 );
            _loc_8 = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.CENTER );
            _loc_9 = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.LEFT );
            _loc_10 = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.CENTER );
            _loc_9.setBackgroundDecorator(new AssetBackground(this.m_rewardItemBG));
            _loc_9.setPreferredSize(new IntDimension(this.m_rewardItemBG.width, this.m_rewardItemBG.height));
            _loc_9.setMinimumSize(new IntDimension(this.m_rewardItemBG.width, this.m_rewardItemBG.height));
            _loc_9.setMaximumSize(new IntDimension(this.m_rewardItemBG.width, this.m_rewardItemBG.height));
            _loc_8.append(_loc_7);
            _loc_9.append(ASwingHelper.horizontalStrut((this.m_rewardItemBG.width - _loc_6.width) / 2));
            _loc_9.append(_loc_8);
            _loc_10.append(_loc_9);
            JLabel _loc_11 =new JLabel(ZLoc.t("Items",(this.m_mapResource as ConstructionSite ).targetItem.wonderName +"_friendlyName"));
            _loc_11.setFont(new ASFont(EmbeddedArt.defaultFontNameBold, 12, false, false, false, EmbeddedArt.advancedFontProps));
            _loc_11.setForeground(new ASColor(EmbeddedArt.darkerBlueTextColor));
            _loc_11.setVerticalAlignment(JLabel.CENTER);
            CustomButton _loc_12 =new CustomButton(ZLoc.t("Dialogs","LandmarkLearnMore"),null ,"OrangeMediumButtonUI");
            _loc_12.addEventListener(MouseEvent.CLICK, this.popTechTree, false, 0, true);
            _loc_5.appendAll(_loc_10, _loc_11, _loc_12);
            _loc_4.append(_loc_5);
            this.m_wonderPane.append(_loc_4);
            return this.m_wonderPane;
        }//end

        private void  popTechTree (Object param1)
        {
            StatsManager.sample(100, StatsKingdomType.DIALOG_STATS, "multi_gate", "learn_more", (this.m_mapResource as ConstructionSite).targetName);
            LandmarksTechTreeDialog _loc_2 =new LandmarksTechTreeDialog(null );
            UI.displayPopup(_loc_2, false, "landmarkTechTree", false);
            onCancelX(param1);
            return;
        }//end

         protected void  onCancel (Object param1)
        {
            StatsManager.sample(100, StatsKingdomType.DIALOG_STATS, "multi_gate", "x", (this.m_mapResource as ConstructionSite).targetName);
            this.cleanUp();
            super.onCancel(param1);
            return;
        }//end

        private JPanel  createProgressPanel ()
        {
            AbstractGate _loc_5 =null ;
            _loc_1 = ASwingHelper.makeFlowJPanel ();
            double _loc_2 =0;
            _loc_3 = this.m_gates.length ;
            double _loc_4 =0;
            while (_loc_4 < _loc_3)
            {

                _loc_5 = this.m_gates.get(_loc_4);
                if (_loc_5.checkForKeys())
                {
                    _loc_2 = _loc_2 + 1;
                }
                _loc_4 = _loc_4 + 1;
            }
            _loc_1.appendAll(ASwingHelper.horizontalStrut(35), ASwingHelper.makeLabel(_loc_2 + "/" + _loc_3, EmbeddedArt.titleFont, 24, EmbeddedArt.lightOrangeTextColor, JLabel.LEFT), ASwingHelper.horizontalStrut(5), ASwingHelper.makeLabel(ZLoc.t("Dialogs", "WonderQuestProgressText"), EmbeddedArt.defaultFontNameBold, 12, EmbeddedArt.brownTextColor, JLabel.LEFT));
            return _loc_1;
        }//end

        protected void  makeTaskRows ()
        {
            String _loc_3 =null ;
            CompositeGateTaskCell _loc_4 =null ;
            DisplayObject _loc_5 =null ;
            JPanel _loc_6 =null ;
            this.m_outerInteriorHolder.append(ASwingHelper.verticalStrut(2));
            this.m_gateRowsAlignPanel = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.LEFT);
            ASwingHelper.setBackground(this.m_gateRowsAlignPanel, new this.m_tasksBG(), new Insets(0, TASK_PANEL_SIDE_PADDING, 0, TASK_PANEL_SIDE_PADDING));
            int _loc_1 =740;
            this.m_gateRowsPanel = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.CENTER);
            this.m_gateRowsAlignPanel.appendAll(ASwingHelper.horizontalStrut(TASK_PANEL_SIDE_PADDING), this.m_gateRowsPanel, ASwingHelper.horizontalStrut(6));
            this.m_gateRowsPanel.append(ASwingHelper.verticalStrut(5));
            this.m_numVisibleGates = 0;
            this.m_gateRowsPanel.append(this.createProgressPanel());
            int _loc_2 =0;
            while (_loc_2 < this.m_gates.length())
            {

                if (!Global.questManager.isTaskActionVisible(this.m_gates.get(_loc_2).name))
                {
                }
                else
                {
                    this.m_numVisibleGates++;
                    _loc_3 = "GateCashSkip";
                    if (this.m_gates.get(_loc_2) instanceof InventoryGate)
                    {
                        _loc_3 = "GateCashBuy";
                    }
                    _loc_4 = new CompositeGateTaskCell(this.m_data, _loc_2, this.m_gates.get(_loc_2), m_assetDict, this, _loc_1, _loc_3);
                    this.m_taskPanels.push(_loc_4);
                    this.m_gateRowsPanel.append(_loc_4);
                    if (_loc_2 < (this.m_gates.length - 1))
                    {
                        _loc_5 =(DisplayObject) new this.m_horizontalRule();
                        _loc_6 = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.CENTER);
                        _loc_6.setPreferredHeight(2);
                        ASwingHelper.setBackground(_loc_6, _loc_5, new Insets(0, 5, 0, 40));
                        this.m_gateRowsPanel.appendAll(ASwingHelper.verticalStrut(5), _loc_6);
                    }
                }
                _loc_2++;
            }
            this.m_gateRowsPanel.append(ASwingHelper.verticalStrut(5));
            ASwingHelper.prepare(this.m_gateRowsAlignPanel);
            this.m_innerInteriorHolder.append(this.m_gateRowsAlignPanel);
            this.m_outerInteriorHolder.append(this.m_innerInteriorHolder);
            return;
        }//end

        private DisplayObject  createGateIcon (Object param1 )
        {
            _loc_2 = param1.image.content ;
            int _loc_3 =60;
            _loc_2.height = 60;
            _loc_2.width = _loc_3;
            if (_loc_2 instanceof Bitmap)
            {
                ((Bitmap)_loc_2).smoothing = true;
            }
            return _loc_2;
        }//end

        protected int  getNumVisibleTasks ()
        {
            int _loc_1 =0;
            if (this.m_gates && this.m_numVisibleGates < 0)
            {
                this.m_numVisibleGates = 0;
                _loc_1 = 0;
                while (_loc_1 < this.m_gates.length())
                {

                    if (!Global.questManager.isTaskActionVisible(this.m_gates.get(_loc_1).name))
                    {
                    }
                    else
                    {
                        this.m_numVisibleGates++;
                    }
                    _loc_1++;
                }
            }
            return this.m_numVisibleGates;
        }//end

        protected void  cleanUp ()
        {
            CompositeGateTaskCell _loc_1 =null ;
            for(int i0 = 0; i0 < this.m_taskPanels.size(); i0++)
            {
            		_loc_1 = this.m_taskPanels.get(i0);

                _loc_1.cleanUp();
            }
            return;
        }//end

        public void  refresh (UIEvent event =null )
        {
            this.cleanUp();
            this.removeAll();
            this.init();
            return;
        }//end

        private int  sortGateOrder (AbstractGate param1 ,AbstractGate param2 )
        {
            if (param1 instanceof InventoryGate)
            {
                if (!(param2 instanceof InventoryGate))
                {
                    return -1;
                }
            }
            if (param1 instanceof MasteryGate)
            {
                if (param2 instanceof InventoryGate)
                {
                    return 1;
                }
                if (param2 instanceof MasteryGate)
                {
                    if (((MasteryGate)param1).masteryType == MasteryGate.BUS_MODE)
                    {
                        return -1;
                    }
                    if (((MasteryGate)param2).masteryType == MasteryGate.BUS_MODE)
                    {
                        return 1;
                    }
                    return 0;
                }
            }
            return 0;
        }//end

    }



