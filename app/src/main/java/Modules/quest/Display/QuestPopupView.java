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
import Classes.QuestGroup.*;
import Classes.util.*;
import Display.*;
import Display.DialogUI.*;
import Display.MarketUI.*;
import Display.aswingui.*;
import Events.*;
import Modules.quest.Display.TaskFooters.*;
import Modules.quest.Helpers.*;
import Modules.quest.Managers.*;
import Modules.quest.guide.*;
import Modules.saga.*;
import com.adobe.utils.DictionaryUtil;
//import flash.display.*;
//import flash.events.*;
//import flash.filters.*;
//import flash.text.*;
//import flash.utils.*;
import org.aswing.*;
import org.aswing.border.*;
import org.aswing.event.*;
import org.aswing.geom.*;

    public class QuestPopupView extends GenericDialogView
    {
        private boolean m_questCompleted =false ;
        protected Class m_horizontalRule ;
        protected DisplayObject m_verticalRule ;
        protected DisplayObject m_rewardItemBG ;
        protected DisplayObject m_rewardIcon ;
        protected Class m_tasksBG ;
        protected Array m_taskInfo ;
        protected Array m_rewardInfo ;
        protected Object m_data ;
        protected JPanel m_innerInteriorHolder ;
        protected JPanel m_outerInteriorHolder ;
        protected DisplayObject m_closebtnOver ;
        protected String m_npcName ;
        protected DisplayObject m_npcIcon ;
        protected Object m_locObjects ;
        protected JTextField m_sectionTitle ;
        protected GameQuestTaskFooterFactory m_taskFooterFactory ;
        protected MarketScrollingList m_inventoryList ;
        protected CustomButton m_okayButton ;
        protected String m_questTitle ;
        protected DisplayObject m_speechBubbleBG ;
        protected DisplayObject m_speechBubbleTail ;
        protected double m_speechBubbleTailOffset =0;
        protected int m_numVisibleTasks =-1;
        protected String m_hintTextKey ;
        protected String m_headerTextKey ;
        protected String m_subTitleKey ;
        protected double m_bottomSpeechBubbleOffset =0;
        protected Array m_taskPanels ;
        protected double m_textBodyWidth =0;
        protected JPanel m_taskRowsAlignPanel ;
        protected JPanel m_taskRowsPanel ;
        protected JPanel m_textContainer ;
        protected JPanel m_timerContainer ;
        protected JPanel m_infoOuterPanel ;
        protected JPanel m_rewardContainer ;
        protected JPanel m_rewardPane ;
        protected JPanel m_rewardTiersPanel ;
        protected JPanel m_tierIconSeperator ;
        protected DisplayObject m_checkMark ;
        protected JPanel m_timerPanel ;
        protected AssetPane m_iconPane ;
        protected DisplayObject m_clock ;
        protected JLabel m_timerLabel ;
        protected JLabel m_timerLabelTag ;
        protected JPanel m_timerTextPanel ;
        protected Sprite m_backgroundSprite ;
        protected AssetPane m_backgroundPane ;
        protected JPanel m_timerStrut ;
        protected Object m_oldTier ;
        protected Timer m_timer ;
        protected GlowFilter m_tierGlow ;
        protected JPanel m_rightPanel ;
        protected JPanel m_leftPanel ;
        protected String m_sagaName ;
        protected boolean m_showLeftPanel ;
public static  int TASK_INCOMPLETE_GAP =91;
public static  int TASK_COMPLETE_GAP =70;
public static  int HINT_TEXT_MIN_WIDTH =320;
public static  int HINT_TEXT_MAX_WIDTH =360;
public static  int HEADER_TEXT_WIDTH =440;
public static  int MAX_HEADER_TEXT_WIDTH =680;
public static  int GAP_ABOVE_NPC =14;
public static  int TEXT_PANEL_SIDE_PADDING =15;
        private static  int TASK_PANEL_SIDE_PADDING =14;
public static  int REWARD_PANEL_SIDE_PADDING =10;
public static  int TIMER_SIDE_PADDING =3;
public static  int TIMER_FORCED_MIN_WIDTH =90;
public static  int TIMER_ICON_GAP =5;
public static  int SPEECH_BUBBLE_MARGIN_TOP_OFFSET =15;
public static  int SPEECH_BUBBLE_MARGIN_BOTTOM_OFFSET =10;
public static  double TARGET_RESOURCE_ICON_SIZE =25;
public static  double TIER_CELL_HEIGHT =32;
public static  double TIER_CELL_WIDTH =43;
public static  double TIER_CELL_MARGIN =3;
public static  double TITLE_GAP =8;
public static  int TIMER_UPDATE_DELAY =1000;
public static  int ONE_DAY =86400;
        public static  String NO_CASH_CLOSE ="noCashClose";

        public  QuestPopupView (Dictionary param1 ,Object param2 )
        {
            this.m_showLeftPanel = false;
            this.m_rightPanel = new JPanel();
            this.m_rightPanel.setLayout(new SoftBoxLayout(SoftBoxLayout.Y_AXIS, 0, SoftBoxLayout.TOP));
            this.m_data = param2;
            this.m_taskPanels = new Array();
            this.m_taskFooterFactory = new GameQuestTaskFooterFactory();
            super(param1, "", "", 0, null);
            this.setLayout(new SoftBoxLayout(SoftBoxLayout.X_AXIS, 0, SoftBoxLayout.TOP));
            this.addEventListener(UIEvent.REFRESH_DIALOG, this.normalizeTaskCellComponents, false, 0, true);
            return;
        }//end

         protected void  init ()
        {
            QuestTaskCell _loc_3 =null ;
            this.initCommonAssets();
            if (this.getNumVisibleTasks() == -1)
            {
                m_bgAsset = m_assetDict.get("imageBG_3");
            }
            else
            {
                m_bgAsset = m_assetDict.get("imageBG_" + Math.max(Math.min(this.getNumVisibleTasks(), 3), 0));
            }
            this.m_tasksBG = m_assetDict.get("tasksBG");
            this.initTaskFooters();
            this.initTiers();
            this.makeCenterPanel();
            this.makeBackground();
            if (this.m_showLeftPanel)
            {
                this.appendAll(this.m_leftPanel);
            }
            this.append(this.m_rightPanel);
            ASwingHelper.prepare(this);
            ASwingHelper.prepare(m_interiorHolder);
            _loc_1 = m_interiorHolder.getWidth()-this.m_taskRowsAlignPanel.getWidth();
            if (_loc_1 > 0)
            {
                for(int i0 = 0; i0 < this.m_taskPanels.size(); i0++)
                {
                		_loc_3 = this.m_taskPanels.get(i0);

                    _loc_3.taskTextWidth = _loc_3.taskTextWidth + _loc_1;
                    _loc_3.updateTextComponent();
                }
                ASwingHelper.prepare(this);
            }
            _loc_2 =Global.stage ? (Global.ui.screenWidth) : (750);
            _loc_1 = this.getPreferredWidth() - _loc_2;
            if (_loc_1 > 0)
            {
                ASwingHelper.setForcedWidth(this, _loc_2);
                for(int i0 = 0; i0 < this.m_taskPanels.size(); i0++)
                {
                		_loc_3 = this.m_taskPanels.get(i0);

                    _loc_3.taskTextWidth = _loc_3.taskTextWidth - _loc_1;
                    _loc_3.updateTextComponent();
                }
                ASwingHelper.prepare(this);
            }
            return;
        }//end

        protected void  initCommonAssets ()
        {
            this.m_questCompleted = m_assetDict.get("questComplete");
            this.m_npcName = m_assetDict.get("npcName");
            this.m_npcIcon = m_assetDict.get("npcIcon");
            this.m_taskInfo = m_assetDict.get("taskInfo");
            this.m_rewardInfo = m_assetDict.get("rewardInfo");
            this.m_locObjects = m_assetDict.get("locObjects");
            this.m_horizontalRule = m_assetDict.get("horizontalRule");
            this.m_verticalRule = m_assetDict.get("verticalRule");
            this.m_rewardItemBG = m_assetDict.get("rewardItemBG");
            this.m_rewardIcon = m_assetDict.get("rewardIcon");
            this.m_speechBubbleBG = m_assetDict.get("speechBG");
            this.m_speechBubbleTail = m_assetDict.get("speechTail");
            this.m_hintTextKey = "_dialog_hint";
            this.m_headerTextKey = "_dialog_header";
            this.m_subTitleKey = "";
            this.m_tierGlow = new GlowFilter(EmbeddedArt.dialogHintColor, 1, 3, 3, 100, BitmapFilterQuality.HIGH);
            return;
        }//end

        protected void  initTaskFooters ()
        {
            this.m_taskFooterFactory.register(TaskFooterType.PLACE_NOW_FROM_INVENTORY, PlaceNowFromInventory);
            this.m_taskFooterFactory.register(TaskFooterType.PLACE_NOW_FROM_INVENTORY_OR_PAN, PlaceNowFromInventoryOrPan);
            this.m_taskFooterFactory.register(TaskFooterType.SCROLL_TO_ANNOUNCER, ScrollToAnnouncer);
            this.m_taskFooterFactory.register(TaskFooterType.PLACE_NOW_FROM_INVENTORY_NOPIC, PlaceNowFromInventoryNoPic);
            this.m_taskFooterFactory.register(TaskFooterType.BEGIN_MINI_GAME, BeginMiniGame);
            this.m_taskFooterFactory.register(TaskFooterType.OPEN_MARKET, OpenMarketFooter);
            this.m_taskFooterFactory.register(TaskFooterType.OPEN_PICK_THINGS, OpenPickThings);
            this.m_taskFooterFactory.register(TaskFooterType.OPEN_CARNIVAL_DIALOG, OpenCarnivalDialog);
            return;
        }//end

        protected void  initTiers ()
        {
            _loc_1 = this.getCurrentTier ();
            this.m_oldTier = _loc_1;
            if (_loc_1 || this.m_data.quest.canShowTimerUI)
            {
                if (this.m_data.quest.canShowTimerUI || this.shouldTimerBeVisible() && this.shouldTimerBeActive() && !this.isTierInfinite(_loc_1))
                {
                    this.m_timer = new Timer(TIMER_UPDATE_DELAY);
                    this.m_timer.addEventListener(TimerEvent.TIMER, this.update, false, 0, true);
                    this.m_timer.start();
                }
            }
            return;
        }//end

        public boolean  shouldQuestExpire ()
        {
            if (this.m_data && this.m_data.quest)
            {
                return (this.m_data.quest as GameQuest).tierExpiration && (this.m_data.quest as GameQuest).isQuestTierExpired();
            }
            return false;
        }//end

        public boolean  shouldTimerBeActive ()
        {
            return !this.m_questCompleted && (this.shouldTimerBeActiveForTiers() || this.shouldTimerBeActiveForLegacy());
        }//end

        protected boolean  shouldTimerBeActiveForTiers ()
        {
            _loc_1 = this.getCurrentTier ();
            if (_loc_1 && _loc_1.data && this.isQuestActivated())
            {
                return true;
            }
            return false;
        }//end

        protected boolean  shouldTimerBeActiveForLegacy ()
        {
            return false;
        }//end

        protected boolean  shouldTimerBeVisible ()
        {
            if (this.m_data && this.m_data.timeData && this.m_data.timeData.activatable || this.m_data.quest.canShowTimerUI)
            {
                return true;
            }
            return false;
        }//end

        protected boolean  isTierInfinite (Object param1 )
        {
            if (param1 && param1.data as GameQuestTier && (param1.data as GameQuestTier).duration == 0)
            {
                return true;
            }
            return false;
        }//end

        protected boolean  isQuestActivated ()
        {
            if (this.m_data)
            {
                return Boolean(this.m_data.isActivated);
            }
            return false;
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
            this.buildLeftPanel();
            this.makeButton();
            ASwingHelper.prepare(m_interiorHolder);
            this.m_rightPanel.append(m_interiorHolder);
            ASwingHelper.prepare(this);
            return;
        }//end

        protected void  buildLeftPanel ()
        {
            JPanel _loc_2 =null ;
            Object _loc_3 =null ;
            JPanel _loc_4 =null ;
            DisplayObject _loc_5 =null ;
            DisplayObject _loc_6 =null ;
            JButton _loc_7 =null ;
            JPanel _loc_8 =null ;
            JPanel _loc_9 =null ;
            _loc_1 = QuestGroupManager.instance.getQuestGroupIcons(this.m_data.name);
            if (_loc_1 && _loc_1.length())
            {
                this.m_showLeftPanel = true;
                this.m_sagaName = SagaManager.instance.getSagaNameByQuestName(this.m_data.name);
                _loc_2 = ASwingHelper.makeSoftBoxJPanelVertical();
                ASwingHelper.setEasyBorder(_loc_2, 0, 0, 0, 3);
                _loc_2.append(ASwingHelper.verticalStrut(5));
                if (this.m_sagaName)
                {
                    _loc_6 = new m_assetDict.get("btnBack");
                    _loc_7 = new JButton();
                    _loc_7.wrapSimpleButton(new SimpleButton(_loc_6, _loc_6, _loc_6, _loc_6));
                    _loc_7.addActionListener(this.onSagaBackBtnClick, 0, true);
                    _loc_2.append(_loc_7);
                }
                _loc_2.append(ASwingHelper.verticalStrut(10));
                for(int i0 = 0; i0 < _loc_1.size(); i0++)
                {
                		_loc_3 = _loc_1.get(i0);

                    _loc_8 = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.CENTER);
                    _loc_8.setPreferredSize(new IntDimension(55, 55));
                    ASwingHelper.createImageCardCell(_loc_8, _loc_3.get("icon"), 0.85);
                    _loc_9 = this.applyQuestState(_loc_8, _loc_3.get("state"));
                    _loc_2.appendAll(_loc_9, ASwingHelper.verticalStrut(10));
                }
                _loc_4 = ASwingHelper.makeSoftBoxJPanelVertical();
                _loc_5 = null;
                if (this.m_sagaName)
                {
                    _loc_5 = new m_assetDict.get("questGroupHolderBG");
                }
                else
                {
                    _loc_5 = new m_assetDict.get("questGroupHolderBGNoBack");
                }
                ASwingHelper.setBackground(_loc_4, _loc_5);
                _loc_4.setPreferredWidth(_loc_5.width);
                _loc_4.appendAll(_loc_2);
                this.m_leftPanel = ASwingHelper.makeSoftBoxJPanelVertical();
                if (!this.m_sagaName)
                {
                    this.m_leftPanel.append(ASwingHelper.verticalStrut(10));
                }
                this.m_leftPanel.append(_loc_4);
                ASwingHelper.setEasyBorder(this.m_leftPanel, 5, 4, 0, 0);
                ASwingHelper.prepare(this.m_leftPanel);
            }
            return;
        }//end

        public JPanel  applyQuestState (JPanel param1 ,String param2 )
        {
            JPanel _loc_3 =null ;
            JPanel _loc_4 =null ;
            DisplayObject _loc_5 =null ;
            JPanel _loc_6 =null ;
            JPanel _loc_7 =null ;
            switch(param2)
            {
                case QuestGroupManager.COMPLETED:
                {
                    _loc_3 = new JPanel();
                    _loc_3.setLayout(new SoftBoxLayout(SoftBoxLayout.Y_AXIS, 0, SoftBoxLayout.TOP));
                    _loc_4 = ASwingHelper.makeFlowJPanel(FlowLayout.LEFT, 0, 20);
                    _loc_4.setPreferredSize(new IntDimension(32, 26));
                    ASwingHelper.createImageCardCell(_loc_4, Global.getAssetURL("assets/market/market_checkmark_medium.png"));
                    _loc_3.appendAll(param1, ASwingHelper.verticalStrut(-20), _loc_4);
                    ASwingHelper.prepare(_loc_3);
                    return _loc_3;
                }
                case QuestGroupManager.IN_PROGRESS:
                {
                    _loc_5 = new m_assetDict.get("currentQuestBG");
                    ASwingHelper.setBackground(param1, new m_assetDict.get("currentQuestBG"));
                    break;
                }
                case QuestGroupManager.LOCKED:
                {
                    param1.filters = EmbeddedArt.desaturateFilter;
                    _loc_6 = new JPanel();
                    _loc_6.setLayout(new SoftBoxLayout(SoftBoxLayout.Y_AXIS, 0, SoftBoxLayout.TOP));
                    _loc_7 = ASwingHelper.makeFlowJPanel(FlowLayout.LEFT, 0, 20);
                    _loc_7.setPreferredSize(new IntDimension(30, 30));
                    ASwingHelper.createImageCardCell(_loc_7, Global.getAssetURL("assets/effects/business/lock.png"));
                    _loc_6.appendAll(param1, ASwingHelper.verticalStrut(-30), _loc_7);
                    ASwingHelper.prepare(_loc_6);
                    return _loc_6;
                }
                default:
                {
                    break;
                }
            }
            return param1;
        }//end

        protected void  onSagaBackBtnClick (AWEvent event )
        {
            if (this.m_sagaName)
            {
                SagaManager.instance.showDialogForSaga(this.m_sagaName, true);
                this.close();
            }
            return;
        }//end

        protected void  makeHeader (int param1 =20)
        {
            JPanel _loc_2 =new JPanel(new BorderLayout ());
            _loc_3 = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.CENTER );
            this.m_questTitle = ZLoc.t("Quest", this.m_data.name + "_dialog_title", this.m_locObjects);
            _loc_4 = TextFieldUtil.getLocaleFontSize(30,20,.get( {locale size "ja",30) });
            TextFormat _loc_5 =new TextFormat ();
            _loc_5.size = _loc_4 + 6;
            _loc_5.align = TextFormatAlign.CENTER;
            _loc_5.leading = 30 - _loc_4;
            _loc_4 = ASwingHelper.shrinkFontSizeToFit(MAX_HEADER_TEXT_WIDTH, this.m_questTitle, EmbeddedArt.titleFont, _loc_4, EmbeddedArt.questTitleFilters, null, _loc_5);
            _loc_5.size = _loc_4 + 6;
            _loc_5.leading = 30 - _loc_4;
            this.m_sectionTitle = ASwingHelper.makeTextField(this.m_questTitle, EmbeddedArt.titleFont, _loc_4, EmbeddedArt.titleColor);
            this.m_sectionTitle.filters = EmbeddedArt.questTitleFilters;
            TextFieldUtil.formatSmallCaps(this.m_sectionTitle.getTextField(), _loc_5);
            _loc_3.append(ASwingHelper.verticalStrut(TITLE_GAP));
            _loc_3.append(this.m_sectionTitle);
            _loc_2.append(_loc_3, BorderLayout.CENTER);
            _loc_6 = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.TOP );
            _loc_7 = ASwingHelper.makeMarketCloseButton ();
            _loc_7.addActionListener(onCancelX, 0, true);
            _loc_6.append(_loc_7);
            ASwingHelper.setEasyBorder(_loc_6, 2, 0, 0, 6);
            _loc_2.append(_loc_6, BorderLayout.EAST);
            _loc_2.append(ASwingHelper.horizontalStrut(_loc_6.getPreferredWidth()), BorderLayout.WEST);
            m_interiorHolder.append(_loc_2);
            return;
        }//end

        protected void  makeInfoPanel ()
        {
            AssetPane _loc_12 =null ;
            AssetPane _loc_13 =null ;
            JPanel _loc_15 =null ;
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
            _loc_5.setBackgroundDecorator(new MarginBackground(this.m_speechBubbleBG, new Insets(SPEECH_BUBBLE_MARGIN_TOP_OFFSET, 0, SPEECH_BUBBLE_MARGIN_BOTTOM_OFFSET + this.m_bottomSpeechBubbleOffset, 0)));
            _loc_6 = this.getQuestTimerPanel ();
            this.m_textContainer = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.TOP);
            _loc_7 = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.LEFT );
            _loc_8 = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.CENTER );
            this.m_textBodyWidth = HINT_TEXT_MIN_WIDTH;
            if (this.m_sectionTitle.getPreferredWidth() > HEADER_TEXT_WIDTH)
            {
                this.m_textBodyWidth = this.m_textBodyWidth + (this.m_sectionTitle.getPreferredWidth() - HEADER_TEXT_WIDTH);
                this.m_textBodyWidth = Math.min(HINT_TEXT_MAX_WIDTH, this.m_textBodyWidth);
            }
            _loc_9 = this.getSubTitlePanel ();
            _loc_10 = this.m_data.name +this.m_hintTextKey ;
            _loc_11 = this.m_data.name +this.m_headerTextKey ;
            if (ZLoc.instance != null)
            {
                if (ZLoc.instance.getString("Quest", _loc_10))
                {
                    _loc_12 = ASwingHelper.makeMultilineText(ZLoc.t("Quest", _loc_10, this.m_locObjects), this.m_textBodyWidth, EmbeddedArt.defaultFontNameBold, TextFormatAlign.LEFT, 12, EmbeddedArt.dialogHintColor);
                }
                if (ZLoc.instance.getString("Quest", _loc_11))
                {
                    _loc_13 = ASwingHelper.makeMultilineText(ZLoc.t("Quest", _loc_11, this.m_locObjects), this.m_textBodyWidth, EmbeddedArt.defaultFontNameBold, TextFormatAlign.LEFT, 18, EmbeddedArt.darkerBlueTextColor, null, true);
                }
            }
            if (_loc_9)
            {
                _loc_8.append(_loc_9);
            }
            if (_loc_13)
            {
                _loc_8.append(_loc_13);
            }
            if (!this.m_questCompleted)
            {
                if (_loc_12)
                {
                    _loc_8.append(_loc_12);
                }
            }
            else if (_loc_6)
            {
                this.m_timerContainer = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.CENTER);
                this.m_timerContainer.append(ASwingHelper.verticalStrut(4));
                this.m_timerContainer.append(_loc_6);
                _loc_8.append(this.m_timerContainer);
            }
            _loc_7.append(ASwingHelper.horizontalStrut(TEXT_PANEL_SIDE_PADDING + 10));
            _loc_7.append(_loc_8);
            _loc_7.append(ASwingHelper.horizontalStrut(TEXT_PANEL_SIDE_PADDING));
            if (!this.m_questCompleted && _loc_6)
            {
                this.m_timerContainer = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.CENTER);
                this.m_timerContainer.append(_loc_6);
                this.m_timerContainer.append(ASwingHelper.verticalStrut(4));
                this.m_textContainer.append(this.m_timerContainer);
            }
            else if (this.m_questCompleted || !this.m_questCompleted && !_loc_6)
            {
                this.m_textContainer.append(ASwingHelper.verticalStrut(GAP_ABOVE_NPC - 2));
                this.m_textContainer.append(ASwingHelper.verticalStrut(SPEECH_BUBBLE_MARGIN_TOP_OFFSET));
            }
            this.m_textContainer.append(_loc_7);
            this.m_textContainer.append(ASwingHelper.verticalStrut(SPEECH_BUBBLE_MARGIN_BOTTOM_OFFSET + this.m_bottomSpeechBubbleOffset + 10));
            _loc_5.append(this.m_textContainer);
            _loc_14 = this.getCurrentRewardData ();
            if (this.getCurrentRewardData() && _loc_14.length > 0)
            {
                _loc_15 = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.TOP);
                ASwingHelper.setEasyBorder(_loc_15, SPEECH_BUBBLE_MARGIN_TOP_OFFSET + 10, 0, SPEECH_BUBBLE_MARGIN_BOTTOM_OFFSET + this.m_bottomSpeechBubbleOffset + 10, 0);
                _loc_15.setBackgroundDecorator(new AssetBackground(this.m_verticalRule));
                _loc_15.setPreferredWidth(2);
                _loc_5.append(_loc_15);
                this.m_rewardContainer = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.TOP);
                this.m_rewardContainer.append(this.getNewRewardPane());
                _loc_5.append(ASwingHelper.horizontalStrut(REWARD_PANEL_SIDE_PADDING));
                _loc_5.append(this.m_rewardContainer);
                _loc_5.append(ASwingHelper.horizontalStrut(REWARD_PANEL_SIDE_PADDING));
            }
            this.m_infoOuterPanel.append(ASwingHelper.horizontalStrut(TASK_PANEL_SIDE_PADDING));
            this.m_infoOuterPanel.append(_loc_1);
            this.m_infoOuterPanel.append(ASwingHelper.horizontalStrut(-5));
            this.m_infoOuterPanel.append(_loc_3);
            this.m_infoOuterPanel.append(ASwingHelper.horizontalStrut(-5));
            this.m_infoOuterPanel.append(_loc_5);
            this.m_infoOuterPanel.append(ASwingHelper.horizontalStrut(7));
            this.m_infoOuterPanel.swapChildren(_loc_5, _loc_3);
            this.adjustTierPanel();
            this.m_outerInteriorHolder.append(this.m_infoOuterPanel);
            m_interiorHolder.append(this.m_outerInteriorHolder);
            return;
        }//end

        protected void  makeButton ()
        {
            JPanel _loc_1 =new JPanel(new FlowLayout(FlowLayout.CENTER ));
            this.m_okayButton = new CustomButton(ZLoc.t("Dialogs", "Okay"), null, "GreenButtonUI");
            this.m_okayButton.addActionListener(this.onOkayClick, 0, true);
            _loc_1.append(this.m_okayButton);
            m_interiorHolder.append(_loc_1);
            return;
        }//end

        protected JPanel  getNewRewardPane ()
        {
            JPanel _loc_8 =null ;
            DisplayObject _loc_9 =null ;
            AssetPane _loc_10 =null ;
            JPanel _loc_11 =null ;
            JPanel _loc_12 =null ;
            JPanel _loc_13 =null ;
            JPanel _loc_14 =null ;
            AssetPane _loc_15 =null ;
            TextField _loc_16 =null ;
            JPanel _loc_17 =null ;
            int _loc_18 =0;
            Object _loc_19 =null ;
            JPanel _loc_20 =null ;
            DisplayObject _loc_21 =null ;
            AssetPane _loc_22 =null ;
            JLabel _loc_23 =null ;
            this.m_rewardPane = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.TOP);
            _loc_1 = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.CENTER );
            _loc_2 = ZLoc.t("Quest","rewards_text");
            _loc_3 = TextFieldUtil.getLocaleFontSize(16,14,null);
            JLabel _loc_4 =new JLabel(_loc_2 );
            _loc_4.setFont(new ASFont(EmbeddedArt.titleFont, _loc_3, false, false, false, EmbeddedArt.advancedFontProps));
            _loc_4.setForeground(new ASColor(EmbeddedArt.titleColor));
            _loc_4.setTextFilters(EmbeddedArt.newtitleSmallFilters);
            _loc_1.append(_loc_4);
            this.m_rewardPane.append(ASwingHelper.verticalStrut(4));
            this.m_rewardPane.append(_loc_1);
            _loc_5 = this.getItemReward ();
            _loc_6 = this.getCurrentRewardData ();
            _loc_7 = this.getRewardTiersPanel ();
            if (_loc_5)
            {
                _loc_8 = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.CENTER);
                _loc_9 = _loc_5.image.content;
                _loc_10 = new AssetPane(_loc_9);
                _loc_11 = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.CENTER);
                _loc_12 = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.LEFT);
                _loc_13 = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.CENTER);
                _loc_12.setBackgroundDecorator(new AssetBackground(this.m_rewardItemBG));
                _loc_12.setPreferredSize(new IntDimension(this.m_rewardItemBG.width, this.m_rewardItemBG.height));
                _loc_12.setMinimumSize(new IntDimension(this.m_rewardItemBG.width, this.m_rewardItemBG.height));
                _loc_12.setMaximumSize(new IntDimension(this.m_rewardItemBG.width, this.m_rewardItemBG.height));
                _loc_11.append(_loc_10);
                _loc_12.append(ASwingHelper.horizontalStrut((this.m_rewardItemBG.width - _loc_9.width) / 2));
                _loc_12.append(_loc_11);
                _loc_13.append(_loc_12);
                _loc_14 = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.CENTER);
                _loc_15 = ASwingHelper.makeMultilineText(_loc_5.text, 100, EmbeddedArt.defaultFontNameBold, TextFormatAlign.CENTER, 12, EmbeddedArt.darkerBlueTextColor);
                _loc_15.setHorizontalAlignment(JLabel.CENTER);
                _loc_16 =(TextField) _loc_15.getAsset();
                TextFieldUtil.limitLineCount(_loc_16, 2, 2);
                _loc_15.setPreferredWidth(_loc_16.width + 2);
                _loc_15.setPreferredHeight(_loc_16.textHeight + 5);
                _loc_14.append(_loc_15);
                _loc_8.appendAll(_loc_13, _loc_14, ASwingHelper.verticalStrut(5));
                this.m_rewardPane.append(_loc_8);
            }
            else
            {
                _loc_17 = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.LEFT, 4);
                _loc_18 = 0;
                while (_loc_18 < _loc_6.length())
                {

                    _loc_19 = _loc_6.get(_loc_18);
                    if (_loc_19.resource != "item" && _loc_19.resource != "itemUnlock")
                    {
                        _loc_20 = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.LEFT);
                        _loc_21 = _loc_19.image.content;
                        if (_loc_21 instanceof Bitmap)
                        {
                            ASwingHelper.scaleBitmapTo((Bitmap)_loc_21, TARGET_RESOURCE_ICON_SIZE, TARGET_RESOURCE_ICON_SIZE);
                        }
                        _loc_22 = new AssetPane(_loc_21);
                        _loc_23 = new JLabel(_loc_19.text + " ");
                        _loc_23.setFont(new ASFont(EmbeddedArt.defaultFontNameBold, 12, false, false, false, EmbeddedArt.advancedFontProps));
                        _loc_23.setForeground(new ASColor(EmbeddedArt.darkerBlueTextColor));
                        _loc_23.setVerticalAlignment(JLabel.CENTER);
                        _loc_20.appendAll(_loc_22, _loc_23);
                        _loc_17.append(_loc_20);
                    }
                    _loc_18++;
                }
                this.m_rewardPane.append(_loc_17);
                if (_loc_7)
                {
                    this.m_rewardPane.append(ASwingHelper.verticalStrut(10));
                }
            }
            if (_loc_7)
            {
                this.m_tierIconSeperator = ASwingHelper.verticalStrut(0);
                this.m_rewardPane.append(this.m_tierIconSeperator);
                this.m_rewardPane.append(_loc_7);
                this.m_rewardPane.append(ASwingHelper.verticalStrut(5));
            }
            if (_loc_5 || _loc_6.length > 0)
            {
                this.m_rewardPane.append(ASwingHelper.verticalStrut(SPEECH_BUBBLE_MARGIN_BOTTOM_OFFSET + this.m_bottomSpeechBubbleOffset + 5));
            }
            return this.m_rewardPane;
        }//end

        protected JPanel  getRewardTiersPanel ()
        {
            Object _loc_1 =null ;
            int _loc_2 =0;
            Object _loc_3 =null ;
            DisplayObject _loc_4 =null ;
            DisplayObject _loc_5 =null ;
            ToolTip _loc_6 =null ;
            JPanel _loc_7 =null ;
            JPanel _loc_8 =null ;
            JPanel _loc_9 =null ;
            AssetPane _loc_10 =null ;
            String _loc_11 =null ;
            Array _loc_12 =null ;
            int _loc_13 =0;
            Object _loc_14 =null ;
            if (!this.m_rewardTiersPanel)
            {
                if (this.m_data.timeData && this.m_data.timeData.tiers)
                {
                    this.m_rewardTiersPanel = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.CENTER);
                    _loc_1 = this.getCurrentTier();
                    _loc_2 = this.m_data.timeData.tiers.length - 1;
                    while (_loc_2 >= 0)
                    {

                        _loc_3 = this.m_data.timeData.tiers.get(_loc_2);
                        if (_loc_3.data.hiddenTierIcon)
                        {
                        }
                        else
                        {
                            _loc_4 = null;
                            _loc_5 = _loc_3.smallRewardIcon.content;
                            _loc_6 = new ToolTip();
                            _loc_4 = this.getIconBGforTier(_loc_3);
                            _loc_7 = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.CENTER);
                            _loc_8 = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.CENTER);
                            _loc_9 = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.CENTER);
                            _loc_10 = new AssetPane(_loc_5);
                            ASwingHelper.setForcedSize(_loc_8, new IntDimension(TIER_CELL_WIDTH, TIER_CELL_HEIGHT));
                            ASwingHelper.setBackground(_loc_8, _loc_4, new Insets(3, TIER_CELL_MARGIN, 3, TIER_CELL_MARGIN));
                            _loc_9.append(_loc_10);
                            _loc_8.append(ASwingHelper.horizontalStrut(TIER_CELL_WIDTH / 2 - _loc_5.width / 2));
                            _loc_8.append(_loc_9);
                            _loc_7.append(_loc_8);
                            _loc_3.cell = _loc_7;
                            _loc_3.cellBG = _loc_8;
                            _loc_7.mouseChildren = false;
                            _loc_6.attachToolTip(_loc_7);
                            _loc_11 = (_loc_3.data as GameQuestTier).locName + ":";
                            _loc_12 = _loc_3.rewardData;
                            _loc_13 = 0;
                            while (_loc_13 < _loc_12.length())
                            {

                                _loc_14 = _loc_12.get(_loc_13);
                                _loc_11 = _loc_11 + ("\n" + _loc_14.text);
                                _loc_13++;
                            }
                            _loc_6.toolTip = _loc_11;
                            if (_loc_3 == _loc_1)
                            {
                                _loc_7.filters = .get(this.m_tierGlow);
                            }
                            this.m_rewardTiersPanel.append(_loc_7);
                        }
                        _loc_2 = _loc_2 - 1;
                    }
                }
            }
            return this.m_rewardTiersPanel;
        }//end

        protected DisplayObject  getIconBGforTier (Object param1 )
        {
            DisplayObject _loc_2 =null ;
            if (param1 == this.getCurrentTier())
            {
                _loc_2 =(DisplayObject) new m_assetDict.get("tierCurrent");
            }
            else if (this.getRemainingTierDuration(param1) != 0)
            {
                _loc_2 =(DisplayObject) new m_assetDict.get("tierAvailable");
            }
            else
            {
                _loc_2 =(DisplayObject) new m_assetDict.get("tierUnavailable");
            }
            return _loc_2;
        }//end

        protected JPanel  getSubTitlePanel ()
        {
            JPanel _loc_1 =null ;
            TextFormat _loc_2 =null ;
            String _loc_3 =null ;
            JTextField _loc_4 =null ;
            if (this.m_subTitleKey != "" && this.m_subTitleKey != null)
            {
                _loc_1 = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.CENTER);
                _loc_2 = new TextFormat(EmbeddedArt.titleFont, 24);
                _loc_2.align = TextFormatAlign.CENTER;
                _loc_3 = ZLoc.t("Quest", this.m_subTitleKey);
                _loc_4 = ASwingHelper.makeTextField(_loc_3, EmbeddedArt.titleFont, 18, 2925509, 0, _loc_2);
                TextFieldUtil.formatSmallCaps(_loc_4.getTextField(), _loc_2);
                _loc_1.append(_loc_4);
                return _loc_1;
            }
            return null;
        }//end

        protected JPanel  getQuestTimerPanel ()
        {
            Object _loc_1 =null ;
            JPanel _loc_2 =null ;
            DisplayObject _loc_3 =null ;
            JPanel _loc_4 =null ;
            AssetPane _loc_5 =null ;
            int _loc_6 =0;
            String _loc_7 =null ;
            int _loc_8 =0;
            String _loc_9 =null ;
            if (!this.shouldTimerBeVisible())
            {
                return null;
            }
            if (this.m_data.quest.canShowTimerUI || this.shouldTimerBeActiveForTiers())
            {
                if (!this.m_timerPanel)
                {
                    _loc_1 = this.getCurrentTier();
                    if (this.m_data.quest.canShowTimerUI || _loc_1 && (!this.isTierInfinite(_loc_1) || this.isTierInfinite(_loc_1) && this.m_questCompleted))
                    {
                        if (this.m_data.timeData.clock)
                        {
                            this.m_clock = this.m_data.timeData.clock.content;
                        }
                        this.m_timerPanel = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.CENTER);
                        _loc_2 = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.LEFT);
                        _loc_3 = this.m_questCompleted && !this.m_data.quest.canShowTimerUI ? (_loc_1.largeRewardIcon.content) : (this.m_clock);
                        this.m_iconPane = new AssetPane(_loc_3);
                        _loc_4 = this.getTimerLabel();
                        _loc_5 = this.getTimerBackground();
                        _loc_5.setVerticalAlignment(JLabel.CENTER);
                        _loc_6 = this.getRemainingTierDuration(_loc_1);
                        _loc_7 = DateUtil.getFormattedDayCounter(_loc_6);
                        if (!this.m_questCompleted)
                        {
                            _loc_8 = int(_loc_7);
                            if (_loc_8 > 0)
                            {
                                if (this.m_data.quest.canShowTimerUI)
                                {
                                    _loc_9 = ZLoc.t("Dialogs", "TimedQuests_days", {count:_loc_8});
                                    _loc_7 = ZLoc.t("Dialogs", "SingularTimedQuest_endsIn", {count:_loc_9});
                                }
                                else
                                {
                                    _loc_7 = ZLoc.t("Dialogs", "TimedQuests_daysLeft", {count:_loc_8, tier:(_loc_1.data as GameQuestTier).locName});
                                }
                            }
                            this.updateTimeText(_loc_7);
                            this.updateExtraTimeTag(_loc_6);
                        }
                        else if (_loc_1 && !_loc_1.data.hiddenTierIcon)
                        {
                            this.updateTimeText((_loc_1.data as GameQuestTier).locName);
                        }
                        else
                        {
                            _loc_2.visible = false;
                        }
                        this.updateBackgroundSprite();
                        this.m_timerStrut = ASwingHelper.horizontalStrut(-_loc_5.getPreferredWidth() + this.m_iconPane.getPreferredWidth() / 2 + TIMER_ICON_GAP);
                        _loc_2.append(this.m_iconPane);
                        _loc_2.append(ASwingHelper.horizontalStrut(-this.m_iconPane.getPreferredWidth() / 2));
                        _loc_2.append(_loc_5);
                        _loc_2.append(this.m_timerStrut);
                        _loc_2.append(_loc_4);
                        _loc_2.swapChildren(this.m_iconPane, _loc_5);
                        this.m_timerPanel.append(_loc_2);
                    }
                }
            }
            else if (this.shouldTimerBeActiveForLegacy())
            {
            }
            return this.m_timerPanel;
        }//end

        protected JPanel  getTimerLabel ()
        {
            if (!this.m_timerLabel)
            {
                this.m_timerLabel = new JLabel("##:##:##");
                this.m_timerLabel.setFont(new ASFont(EmbeddedArt.titleFont, TextFieldUtil.getLocaleFontSize(16, 16, [{locale:"ja", size:14}]), false, false, false, EmbeddedArt.advancedFontProps));
                this.m_timerLabel.setForeground(new ASColor(16777215));
                this.m_timerLabel.setVerticalAlignment(JLabel.CENTER);
                this.m_timerLabel.setHorizontalAlignment(JLabel.LEFT);
                this.m_timerLabel.setTextFilters(EmbeddedArt.darkerOverlayFilter);
            }
            if (!this.m_timerLabelTag)
            {
                this.m_timerLabelTag = new JLabel("");
                this.m_timerLabelTag.setFont(new ASFont(EmbeddedArt.titleFont, 16, false, false, false, EmbeddedArt.advancedFontProps));
                this.m_timerLabelTag.setForeground(new ASColor(16777215));
                this.m_timerLabelTag.setVerticalAlignment(JLabel.CENTER);
                this.m_timerLabelTag.setHorizontalAlignment(JLabel.LEFT);
                this.m_timerLabelTag.setTextFilters(EmbeddedArt.darkerOverlayFilter);
            }
            if (!this.m_timerTextPanel)
            {
                this.m_timerTextPanel = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.LEFT);
                this.m_timerTextPanel.appendAll(this.m_timerLabel, this.m_timerLabelTag);
            }
            return this.m_timerTextPanel;
        }//end

        protected AssetPane  getTimerBackground ()
        {
            if (!this.m_backgroundSprite)
            {
                this.m_backgroundSprite = new Sprite();
            }
            if (!this.m_backgroundPane)
            {
                this.m_backgroundPane = new AssetPane(this.m_backgroundSprite);
            }
            return this.m_backgroundPane;
        }//end

        protected Object  getCurrentTier ()
        {
            GameQuest _loc_1 =null ;
            String _loc_2 =null ;
            if (this.m_data.name)
            {
                _loc_1 = Global.questManager.getQuestByName(this.m_data.name);
                if (_loc_1)
                {
                    _loc_2 = _loc_1.getCurrentTier();
                    if (this.m_data.get("tier_" + _loc_2))
                    {
                        return this.m_data.get("tier_" + _loc_2);
                    }
                }
            }
            return null;
        }//end

        protected int  getRemainingTierDuration (Object param1 )
        {
            if (this.m_data.quest)
            {
                if (this.m_data.quest.canShowTimerUI)
                {
                    return (this.m_data.quest as GameQuest).getRemainingTimeForExpirableQuest();
                }
                if (this.m_data.quest)
                {
                    return (this.m_data.quest as GameQuest).getRemainingTimeForTier(param1.data.name);
                }
            }
            return -1;
        }//end

        protected Object  getItemReward ()
        {
            int _loc_2 =0;
            Object _loc_3 =null ;
            _loc_1 = this.getCurrentRewardData ();
            if (_loc_1)
            {
                _loc_2 = 0;
                while (_loc_2 < _loc_1.length())
                {

                    _loc_3 = _loc_1.get(_loc_2);
                    if (_loc_3.resource == "item" || _loc_3.resource == "itemUnlock")
                    {
                        return _loc_3;
                    }
                    _loc_2++;
                }
            }
            return null;
        }//end

        protected Array  getCurrentRewardData ()
        {
            _loc_1 = this.getCurrentTier ();
            Array _loc_2 =new Array();
            if (_loc_1 && _loc_1.rewardData && _loc_1.rewardData instanceof Array)
            {
                _loc_2 = _loc_1.rewardData;
            }
            else
            {
                _loc_2 = this.m_rewardInfo;
            }
            return _loc_2;
        }//end

        protected void  update (TimerEvent event )
        {
            int _loc_3 =0;
            String _loc_4 =null ;
            int _loc_5 =0;
            String _loc_6 =null ;
            _loc_2 = this.getCurrentTier ();
            if (!_loc_2 && this.shouldQuestExpire())
            {
                this.expireQuest();
                return;
            }
            if (_loc_2 != this.m_oldTier)
            {
                this.updateTierRewardPanel();
                this.updateTasks();
            }
            if (_loc_2 || this.m_data.quest.canShowTimerUI)
            {
                if (!this.isTierInfinite(_loc_2) || this.m_data.quest.canShowTimerUI)
                {
                    _loc_3 = this.getRemainingTierDuration(_loc_2);
                    _loc_4 = DateUtil.getFormattedDayCounter(_loc_3);
                    _loc_5 = int(_loc_4);
                    if (_loc_5 > 0)
                    {
                        if (this.m_data.quest.canShowTimerUI)
                        {
                            _loc_6 = ZLoc.t("Dialogs", "TimedQuests_days", {count:_loc_5});
                            _loc_4 = ZLoc.t("Dialogs", "SingularTimedQuest_endsIn", {count:_loc_6});
                        }
                        else
                        {
                            _loc_4 = ZLoc.t("Dialogs", "TimedQuests_daysLeft", {count:_loc_5, tier:(_loc_2.data as GameQuestTier).locName});
                        }
                    }
                    this.updateTimeText(_loc_4);
                    this.updateExtraTimeTag(_loc_3);
                    this.updateBackgroundSprite();
                }
                else
                {
                    this.cleanUpTimer();
                    this.removeTimer();
                }
            }
            else
            {
                this.cleanUpTimer();
            }
            this.m_oldTier = _loc_2;
            return;
        }//end

        protected void  updateTimeText (String param1 )
        {
            if (this.m_timerLabel)
            {
                this.m_timerLabel.setText(param1 + " ");
            }
            return;
        }//end

        protected void  updateExtraTimeText (String param1 )
        {
            if (this.m_timerLabelTag)
            {
                this.m_timerLabelTag.setText(param1);
            }
            return;
        }//end

        protected void  updateExtraTimeTag (int param1 )
        {
            Object _loc_2 =null ;
            if (this.m_timerLabelTag)
            {
                _loc_2 = this.getCurrentTier();
                if (param1 <= ONE_DAY && _loc_2)
                {
                    this.m_timerLabelTag.setText(ZLoc.t("Dialogs", "TimedQuests_timeLeft", {tier:(_loc_2.data as GameQuestTier).locName}) + " ");
                }
                else if (this.m_data.quest.timeMessage)
                {
                    this.updateExtraTimeText(this.m_data.quest.timeMessage);
                }
            }
            return;
        }//end

        protected void  updateBackgroundSprite ()
        {
            double _loc_2 =0;
            double _loc_3 =0;
            double _loc_4 =0;
            double _loc_5 =0;
            int _loc_6 =0;
            QuestTierConfig _loc_7 =null ;
            _loc_1 = this.getCurrentTier ();
            if (_loc_1 && this.m_timerLabel || this.m_data.quest.canShowTimerUI)
            {
                _loc_2 = 0;
                _loc_3 = this.m_timerLabel.getPreferredHeight();
                _loc_4 = 0;
                _loc_5 = 0;
                if (int(DateUtil.getFormattedDayCounter(this.getRemainingTierDuration(_loc_1))) == 0)
                {
                    this.m_timerLabel.setPreferredWidth(TIMER_FORCED_MIN_WIDTH + TIMER_SIDE_PADDING);
                    ASwingHelper.prepare(this.m_timerLabel);
                    ASwingHelper.prepare(this.m_timerLabelTag);
                    _loc_2 = _loc_2 + (TIMER_FORCED_MIN_WIDTH + TIMER_SIDE_PADDING + this.m_timerLabelTag.getWidth());
                }
                else
                {
                    _loc_5 = TIMER_SIDE_PADDING;
                    this.m_timerLabel.setPreferredSize(null);
                    ASwingHelper.prepare(this.m_timerLabel);
                    ASwingHelper.prepare(this.m_timerLabelTag);
                    _loc_2 = _loc_2 + (this.m_timerLabel.getPreferredWidth() + _loc_5 + this.m_timerLabelTag.getWidth());
                    ASwingHelper.prepare(this.m_timerPanel);
                }
                if (this.m_iconPane)
                {
                    _loc_4 = this.m_iconPane.getPreferredWidth() / 2 + TIMER_ICON_GAP;
                }
                _loc_2 = _loc_2 + _loc_4;
                if (this.m_backgroundSprite)
                {
                    _loc_6 = this.m_questCompleted ? (EmbeddedArt.limeGreenTextColor) : (EmbeddedArt.dialogHintColor);
                    if (this.m_data.quest && !this.m_questCompleted)
                    {
                        _loc_7 = Global.gameSettings().getQuestTierConfig(QuestTierConfig.TIER_LAST_CHANCE);
                        if (_loc_7 && (this.m_data.quest as GameQuest).isLastChance())
                        {
                            _loc_6 = _loc_7.preferredColor;
                        }
                    }
                    this.m_backgroundSprite.graphics.clear();
                    this.m_backgroundSprite.graphics.beginFill(_loc_6);
                    this.m_backgroundSprite.graphics.drawRoundRect(0, 0, _loc_2 - _loc_5, _loc_3, 8, 8);
                    this.m_backgroundSprite.graphics.endFill();
                }
                if (this.m_backgroundPane)
                {
                    this.m_backgroundPane.setPreferredWidth(_loc_2 + 5);
                    this.m_backgroundPane.setPreferredHeight(_loc_3);
                }
                if (this.m_timerStrut)
                {
                    this.m_timerStrut.setPreferredWidth(-this.m_backgroundPane.getPreferredWidth() + _loc_4);
                    ASwingHelper.prepare(this.m_timerStrut);
                }
            }
            return;
        }//end

        protected void  updateTierRewardPanel ()
        {
            this.m_rewardTiersPanel = null;
            this.m_rewardTiersPanel = this.getRewardTiersPanel();
            this.m_rewardContainer.removeAll();
            this.m_rewardContainer.append(this.getNewRewardPane());
            this.adjustTierPanel();
            ASwingHelper.prepare(this);
            return;
        }//end

        protected void  updateTasks ()
        {
            QuestTaskCell _loc_1 =null ;
            for(int i0 = 0; i0 < this.m_taskPanels.size(); i0++)
            {
            		_loc_1 = this.m_taskPanels.get(i0);

                _loc_1.invalidateCell();
            }
            return;
        }//end

        protected void  adjustTierPanel ()
        {
            double _loc_1 =0;
            double _loc_2 =0;
            double _loc_3 =0;
            if (this.m_rewardTiersPanel && this.m_tierIconSeperator)
            {
                ASwingHelper.prepare(this.m_rewardPane);
                ASwingHelper.prepare(this.m_infoOuterPanel);
                _loc_1 = this.m_infoOuterPanel.getHeight();
                _loc_2 = this.m_rewardPane.getHeight();
                _loc_3 = Math.max(0, _loc_1 - _loc_2);
                this.m_tierIconSeperator.setPreferredHeight(_loc_3);
                ASwingHelper.prepare(this.m_rewardPane);
                ASwingHelper.prepare(this.m_infoOuterPanel);
            }
            return;
        }//end

        protected void  removeTimer ()
        {
            this.m_timerContainer.removeAll();
            if (!this.m_questCompleted)
            {
                this.m_textContainer.insert(0, ASwingHelper.verticalStrut(SPEECH_BUBBLE_MARGIN_TOP_OFFSET));
                this.m_textContainer.insert(0, ASwingHelper.verticalStrut(GAP_ABOVE_NPC - 2));
            }
            ASwingHelper.prepare(this.m_infoOuterPanel);
            return;
        }//end

        protected void  doGuide ()
        {
            if (Global.isVisiting() || UI.questManagerView && UI.questManagerView.isShown)
            {
                return;
            }
            if (this.m_data.guide as QuestGuide != null && this.m_data.guideName != null)
            {
                (this.m_data.guide as QuestGuide).notify(this.m_data.guideName);
            }
            return;
        }//end

         protected void  onCancel (Object param1)
        {
            this.cleanUp();
            super.onCancel(param1);
            return;
        }//end

        protected void  onOkayClick (AWEvent event )
        {
            if (this.m_acceptTextName.length > 0)
            {
                countDialogViewAction("OKAY");
            }
            this.close();
            this.doGuide();
            return;
        }//end

         public void  close ()
        {
            this.cleanUp();
            super.close();
            return;
        }//end

        public CustomButton  okayButton ()
        {
            return this.m_okayButton;
        }//end

        private void  makeTaskRows ()
        {
            QuestTaskCell _loc_2 =null ;
            DisplayObject _loc_3 =null ;
            JPanel _loc_4 =null ;
            this.m_taskRowsAlignPanel = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.LEFT);
            ASwingHelper.setBackground(this.m_taskRowsAlignPanel, new this.m_tasksBG(), new Insets(0, TASK_PANEL_SIDE_PADDING, 0, TASK_PANEL_SIDE_PADDING));
            this.m_taskRowsPanel = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.CENTER);
            this.m_taskRowsAlignPanel.appendAll(ASwingHelper.horizontalStrut(TASK_PANEL_SIDE_PADDING), this.m_taskRowsPanel, ASwingHelper.horizontalStrut(TASK_PANEL_SIDE_PADDING));
            this.m_taskRowsPanel.append(ASwingHelper.verticalStrut(5));
            this.m_numVisibleTasks = 0;
            int _loc_1 =0;
            while (_loc_1 < this.m_taskInfo.length())
            {

                if (!Global.questManager.isTaskActionVisible(this.m_taskInfo.get(_loc_1).name))
                {
                }
                else
                {
                    this.m_numVisibleTasks++;
                    _loc_2 = new QuestTaskCell(this.m_data, _loc_1, this.m_taskInfo.get(_loc_1), m_assetDict, this, this.m_taskFooterFactory);
                    _loc_2.addEventListener(QuestPopupView.NO_CASH_CLOSE, this.noCashClose, false, 0, true);
                    this.m_taskPanels.push(_loc_2);
                    this.m_taskRowsPanel.append(_loc_2);
                    if (_loc_1 < (this.m_taskInfo.length - 1))
                    {
                        _loc_3 =(DisplayObject) new this.m_horizontalRule();
                        _loc_4 = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.CENTER);
                        _loc_4.setPreferredHeight(2);
                        ASwingHelper.setBackground(_loc_4, _loc_3);
                        this.m_taskRowsPanel.append(_loc_4);
                    }
                }
                _loc_1++;
            }
            this.normalizeTaskCellComponents();
            this.m_taskRowsPanel.append(ASwingHelper.verticalStrut(5));
            ASwingHelper.prepare(this.m_taskRowsAlignPanel);
            this.m_innerInteriorHolder.append(this.m_taskRowsAlignPanel);
            this.m_outerInteriorHolder.append(this.m_innerInteriorHolder);
            return;
        }//end

        private void  normalizeTaskCellComponents (UIEvent event =null )
        {
            QuestTaskCell taskPane ;
            Array buttons ;
            Array arr ;
            e = event;
            Array iconPanes =new Array ();
            int _loc_3 =0;
            _loc_4 = this.m_taskPanels ;
            for(int i0 = 0; i0 < this.m_taskPanels.size(); i0++)
            {
            		taskPane = this.m_taskPanels.get(i0);


                iconPanes.push(taskPane.iconComponent);
            }
            ASwingHelper.normalizeComponentSizes(iconPanes, true, false);
            buttons = new Array();
            _loc_3 = 0;
            _loc_4 = this.m_taskPanels;
            for(int i0 = 0; i0 < this.m_taskPanels.size(); i0++)
            {
            		taskPane = this.m_taskPanels.get(i0);


                arr = DictionaryUtil.getKeys(taskPane.buttonsListenerRefDict);
                arr .forEach (void  (CustomButton param1 ,Object param2 ,Object param3 )
            {
                buttons.push(param1);
                return;
            }//end
            );
            }
            ASwingHelper.normalizeComponentSizes(buttons, true, false);
            return;
        }//end

        protected int  getNumVisibleTasks ()
        {
            int _loc_1 =0;
            if (this.m_taskInfo && this.m_numVisibleTasks < 0)
            {
                this.m_numVisibleTasks = 0;
                _loc_1 = 0;
                while (_loc_1 < this.m_taskInfo.length())
                {

                    if (!Global.questManager.isTaskActionVisible(this.m_taskInfo.get(_loc_1).name))
                    {
                    }
                    else
                    {
                        this.m_numVisibleTasks++;
                    }
                    _loc_1++;
                }
            }
            return this.m_numVisibleTasks;
        }//end

        protected void  cleanUpTimer ()
        {
            if (this.m_timer)
            {
                this.m_timer.stop();
                this.m_timer.removeEventListener(TimerEvent.TIMER, this.update);
                this.m_timer = null;
            }
            return;
        }//end

        protected void  cleanUp ()
        {
            QuestTaskCell _loc_1 =null ;
            for(int i0 = 0; i0 < this.m_taskPanels.size(); i0++)
            {
            		_loc_1 = this.m_taskPanels.get(i0);

                _loc_1.cleanUp();
            }
            this.m_taskPanels = new Array();
            this.cleanUpTimer();
            return;
        }//end

        public void  expireQuest ()
        {
            Global.questManager.showTimedQuestExpired(this.m_data.quest);
            this.cleanUp();
            dispatchEvent(new Event(Event.CLOSE, false, false));
            return;
        }//end

        public void  noCashClose (Event event )
        {
            this.cleanUp();
            dispatchEvent(new Event(Event.CLOSE, false, false));
            return;
        }//end

        public void  refreshCells ()
        {
            QuestTaskCell _loc_1 =null ;
            for(int i0 = 0; i0 < this.m_taskPanels.size(); i0++)
            {
            		_loc_1 = this.m_taskPanels.get(i0);

                _loc_1.invalidateCell();
            }
            return;
        }//end

    }



