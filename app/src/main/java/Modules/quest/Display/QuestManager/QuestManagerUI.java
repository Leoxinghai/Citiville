package Modules.quest.Display.QuestManager;

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
import Display.aswingui.*;
import Display.hud.*;
import Modules.guide.ui.*;
import Modules.quest.Managers.*;
import Modules.stats.types.*;
//import flash.display.*;
//import flash.events.*;
//import flash.text.*;
import org.aswing.*;
import org.aswing.ext.*;
import org.aswing.geom.*;

    public class QuestManagerUI extends JPanel
    {
        protected QuestManagerView m_questManagerView ;
        protected JPanel m_centerPanelContainer ;
        protected JPanel m_centerPanel ;
        protected JPanel m_titlePanel ;
        protected JPanel m_hintPanel ;
        protected DisplayObject m_bgAsset ;
        protected SimpleButton m_closeButton ;
        protected JButton m_hiddenButton ;
        protected QuestManagerToolTip m_tool_tip ;
        protected JTextField m_activeHeader ;
        protected JTextField m_hiddenHeader ;
        protected VectorListModel m_model ;
        protected VectorListModel m_activeModel ;
        protected VectorListModel m_hiddenModel ;
        protected GridList m_gridList ;
        protected JScrollPane m_scrollPane ;
        protected GuideArrow m_arrow ;
        protected boolean m_showHidden =false ;
        protected AssetPane m_hintPane ;
        protected AssetPane m_hiddenHintPane ;
        public static  int NUM_ROWS =4;
        public static  int NUM_COLUMNS =5;
        public static  int TOP_INSET_HEIGHT =10;
        public static  int RIGHT_INSET_WIDTH =10;
        public static  int TAB_WIDTH =160;
        public static  int TAB_HEIGHT =30;
        public static  int TAB_LEFT_OFFSET =25;
        public static  int CLOSE_BUTTON_X =375;
        public static  int CLOSE_BUTTON_Y =12;

        public  QuestManagerUI (LayoutManager param1)
        {
            super(new SoftBoxLayout(SoftBoxLayout.X_AXIS, 5, SoftBoxLayout.LEFT));
            this.setPreferredSize(new IntDimension(QuestManagerView.QUEST_MANAGER_WIDTH + 20 + QuestManagerView.QUEST_MANAGER_TOOLTIP_WIDTH, QuestManagerView.QUEST_MANAGER_HEIGHT + 80));
            this.makeGridList();
            return;
        }//end

        public void  init (QuestManagerView param1 )
        {
            this.m_questManagerView = param1;
            this.m_tool_tip = new QuestManagerToolTip();
            this.m_centerPanelContainer = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.TOP);
            this.makeTitlePanel();
            this.makeCenterPanel();
            this.makeHintPanel();
            this.m_centerPanelContainer.append(this.m_titlePanel);
            this.m_centerPanelContainer.append(ASwingHelper.verticalStrut(-15));
            this.m_centerPanelContainer.append(this.m_centerPanel);
            this.m_centerPanelContainer.append(ASwingHelper.verticalStrut(-2));
            this.m_centerPanelContainer.append(this.m_hintPanel);
            this.append(this.m_centerPanelContainer);
            this.append(this.m_tool_tip);
            this.m_tool_tip.visible = false;
            this.makeBackground();
            ASwingHelper.prepare(this);
            _loc_2 = new EmbeddedArt.mkt_close_up ();
            _loc_3 = new EmbeddedArt.mkt_close_over ();
            _loc_4 = new EmbeddedArt.mkt_close_down ();
            this.m_closeButton = new SimpleButton(_loc_2, _loc_3, _loc_4, _loc_2);
            this.m_closeButton.addEventListener(MouseEvent.CLICK, this.closeQuestManager, false, 0, true);
            this.addChild(this.m_closeButton);
            this.m_closeButton.x = CLOSE_BUTTON_X;
            this.m_closeButton.y = CLOSE_BUTTON_Y;
            this.m_centerPanelContainer.bringToTop(this.m_titlePanel);
            return;
        }//end

        protected void  makeTitlePanel ()
        {
            String _loc_2 =null ;
            String _loc_3 =null ;
            int _loc_4 =0;
            DisplayObject _loc_5 =null ;
            MarginBackground _loc_6 =null ;
            DisplayObject _loc_7 =null ;
            MarginBackground _loc_8 =null ;
            String _loc_9 =null ;
            TextFormat _loc_1 =new TextFormat ();
            _loc_1.align = TextFormatAlign.CENTER;
            if (GameQuestManager.inHidingExperiment)
            {
                this.m_titlePanel = ASwingHelper.makeFlowJPanel(FlowLayout.LEFT);
                _loc_1.size = 20;
                _loc_2 = ZLoc.t("Quest", "quest_manager_active");
                _loc_3 = ZLoc.t("Quest", "quest_manager_hidden");
                _loc_4 = ASwingHelper.shrinkFontSizeToFit(TAB_WIDTH, _loc_3, EmbeddedArt.titleFont, 14, null, _loc_1);
                this.m_activeHeader = ASwingHelper.makeTextField(_loc_2, EmbeddedArt.titleFont, _loc_4, EmbeddedArt.whiteTextColor);
                this.m_hiddenHeader = ASwingHelper.makeTextField(_loc_3, EmbeddedArt.titleFont, _loc_4, EmbeddedArt.whiteTextColor);
                this.m_hiddenHeader.setForeground(new ASColor(EmbeddedArt.lightGrayTextColor));
                TextFieldUtil.formatSmallCaps(this.m_activeHeader.getTextField(), _loc_1);
                TextFieldUtil.formatSmallCaps(this.m_hiddenHeader.getTextField(), _loc_1);
                _loc_5 =(DisplayObject) new QuestManagerView.assetDict.get("questManagerTabActive");
                _loc_6 = new MarginBackground(_loc_5, new Insets(0, 0, 3, 0));
                this.m_activeHeader.setBackgroundDecorator(_loc_6);
                _loc_7 =(DisplayObject) new QuestManagerView.assetDict.get("questManagerTabInactive");
                _loc_8 = new MarginBackground(_loc_7, new Insets(0, 0, 3, 0));
                this.m_hiddenHeader.setBackgroundDecorator(_loc_8);
                this.m_activeHeader.setPreferredSize(new IntDimension(TAB_WIDTH, TAB_HEIGHT));
                this.m_hiddenHeader.setPreferredSize(new IntDimension(TAB_WIDTH, TAB_HEIGHT));
                this.m_titlePanel.append(this.m_activeHeader);
                this.m_titlePanel.append(this.m_hiddenHeader);
                ASwingHelper.setEasyBorder(this.m_titlePanel, 0, TAB_LEFT_OFFSET, 0, 0);
                this.m_activeHeader.addEventListener(MouseEvent.CLICK, this.showActiveQuests, false, 0, true);
                this.m_hiddenHeader.addEventListener(MouseEvent.CLICK, this.showHiddenQuests, false, 0, true);
            }
            else
            {
                this.m_titlePanel = ASwingHelper.makeFlowJPanel(FlowLayout.CENTER);
                _loc_1.size = 28;
                _loc_9 = ZLoc.t("Quest", "quest_manager_title");
                this.m_activeHeader = ASwingHelper.makeTextField(_loc_9, EmbeddedArt.titleFont, 16, EmbeddedArt.whiteTextColor);
                TextFieldUtil.formatSmallCaps(this.m_activeHeader.getTextField(), _loc_1);
                this.m_activeHeader.setPreferredWidth(QuestManagerView.QUEST_MANAGER_WIDTH);
                this.m_titlePanel.append(this.m_activeHeader);
            }
            return;
        }//end

        protected void  makeHintPanel ()
        {
            String _loc_1 =null ;
            String _loc_2 =null ;
            this.m_hintPanel = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.CENTER);
            if (GameQuestManager.inHidingExperiment)
            {
                _loc_1 = ZLoc.t("Quest", "quest_manager_new_hint");
                this.m_hintPane = ASwingHelper.makeMultilineText(_loc_1, QuestManagerView.QUEST_MANAGER_WIDTH, EmbeddedArt.defaultFontNameBold, TextFormatAlign.LEFT, 16, EmbeddedArt.whiteTextColor);
                this.m_hintPanel.append(this.m_hintPane);
                _loc_2 = ZLoc.t("Quest", "quest_manager_hidden_tab_hint");
                this.m_hiddenHintPane = ASwingHelper.makeMultilineText(_loc_2, QuestManagerView.QUEST_MANAGER_WIDTH, EmbeddedArt.defaultFontNameBold, TextFormatAlign.LEFT, 16, EmbeddedArt.whiteTextColor);
            }
            else
            {
                this.m_hintPane = ASwingHelper.makeMultilineText(ZLoc.t("Quest", "quest_manager_hint"), QuestManagerView.QUEST_MANAGER_WIDTH, EmbeddedArt.defaultFontNameBold, TextFormatAlign.LEFT, 16, EmbeddedArt.whiteTextColor);
                this.m_hintPanel.append(this.m_hintPane);
            }
            return;
        }//end

        protected void  makeBackground ()
        {
            this.m_bgAsset =(DisplayObject) new QuestManagerView.assetDict.get("questManagerBG");
            MarginBackground _loc_1 =new MarginBackground(this.m_bgAsset ,new Insets(TOP_INSET_HEIGHT ,0,0,RIGHT_INSET_WIDTH ));
            this.m_centerPanel.setBackgroundDecorator(_loc_1);
            this.m_centerPanel.setPreferredSize(new IntDimension(QuestManagerView.QUEST_MANAGER_WIDTH, QuestManagerView.QUEST_MANAGER_HEIGHT));
            return;
        }//end

        protected void  makeCenterPanel ()
        {
            this.m_centerPanel = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.TOP, -5);
            _loc_1 = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.RIGHT );
            ASwingHelper.prepare(_loc_1);
            _loc_2 = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.CENTER );
            ASwingHelper.setEasyBorder(this.m_scrollPane, 20, 0, 0, 20);
            _loc_2.append(this.m_scrollPane);
            ASwingHelper.prepare(_loc_2);
            this.m_centerPanel.append(_loc_1);
            this.m_centerPanel.append(_loc_2);
            ASwingHelper.prepare(this.m_centerPanel);
            return;
        }//end

        public void  showHiddenQuests (Event event =null )
        {
            if (!this.m_showHidden)
            {
                if (event)
                {
                    Global.questManager.trackQuestHidingAction(StatsPhylumType.SWITCH_TAB, "hidden");
                }
                this.m_showHidden = true;
                this.refreshIcons();
                this.m_hiddenHeader.setForeground(new ASColor(EmbeddedArt.whiteTextColor));
                this.m_activeHeader.setForeground(new ASColor(EmbeddedArt.lightGrayTextColor));
                this.m_hintPanel.remove(this.m_hintPane);
                this.m_hintPanel.append(this.m_hiddenHintPane);
                ASwingHelper.prepare(this.m_hintPanel);
                this.m_model = this.m_hiddenModel;
                this.m_gridList.setModel(this.m_model);
            }
            return;
        }//end

        public void  showActiveQuests (Event event =null )
        {
            if (this.m_showHidden)
            {
                this.m_showHidden = false;
                if (event)
                {
                    Global.questManager.trackQuestHidingAction(StatsPhylumType.SWITCH_TAB, "active");
                }
                this.refreshIcons();
                this.m_activeHeader.setForeground(new ASColor(EmbeddedArt.whiteTextColor));
                this.m_hiddenHeader.setForeground(new ASColor(EmbeddedArt.lightGrayTextColor));
                this.m_hintPanel.remove(this.m_hiddenHintPane);
                this.m_hintPanel.append(this.m_hintPane);
                ASwingHelper.prepare(this.m_hintPanel);
                this.m_model = this.m_activeModel;
                this.m_gridList.setModel(this.m_model);
            }
            return;
        }//end

        protected void  makeGridList ()
        {
            this.m_activeModel = new VectorListModel();
            this.m_hiddenModel = new VectorListModel();
            if (this.m_showHidden)
            {
                this.m_model = this.m_hiddenModel;
            }
            else
            {
                this.m_model = this.m_activeModel;
            }
            this.m_gridList = new GridList(this.m_model, new QuestManagerCellFactory(), NUM_COLUMNS, NUM_ROWS);
            this.m_gridList.setHolderLayout(new GridListHorizontalLayout(NUM_ROWS, NUM_COLUMNS, 0, 0));
            this.m_gridList.setPreferredSize(new IntDimension(NUM_COLUMNS * QuestManagerCell.CELL_WIDTH, NUM_ROWS * QuestManagerCell.CELL_HEIGHT));
            this.m_gridList.setMinimumSize(new IntDimension(NUM_COLUMNS * QuestManagerCell.CELL_WIDTH, NUM_ROWS * QuestManagerCell.CELL_HEIGHT));
            this.m_gridList.setMaximumSize(new IntDimension(NUM_COLUMNS * QuestManagerCell.CELL_WIDTH, NUM_ROWS * QuestManagerCell.CELL_HEIGHT));
            this.m_scrollPane = new JScrollPane(this.m_gridList, JScrollPane.SCROLLBAR_NEVER, JScrollPane.SCROLLBAR_NEVER);
            return;
        }//end

        protected void  closeQuestManager (Event event )
        {
            this.showActiveQuests();
            this.m_questManagerView.close();
            return;
        }//end

        public void  addIcon (QuestManagerSprite param1 )
        {
            String _loc_2 =null ;
            double _loc_3 =0;
            double _loc_4 =0;
            if (this.m_activeModel.isEmpty())
            {
                this.m_activeModel.append(param1);
                this.m_gridList.setModel(this.m_model);
            }
            else if (!this.m_activeModel.contains(param1))
            {
                this.m_activeModel.append(param1);
            }
            if (this.m_hiddenModel.contains(param1))
            {
                this.m_hiddenModel.remove(param1);
            }
            Global.hud.setHUDComponentVisible(HUD.COMP_QUEST_MANAGER_ICON, true);
            if (Global.player.getSeenFlag("SeenQuestManager") == false)
            {
                if (Global.player.getLastActivationTime("SeenQuestManager") < 0)
                {
                    _loc_2 = Global.getAssetURL("assets/hud/tutorialArrow.swf");
                    _loc_3 = 160 + Global.ui.leftSideQuestOffset;
                    _loc_4 = 400;
                    this.m_arrow = Global.guide.displayArrow(_loc_2, _loc_3, _loc_4, 0, 0, GuideArrow.ARROW_LEFT, true, true, true);
                }
                Global.player.setSeenFlag("SeenQuestManager");
            }
            return;
        }//end

        public void  addHiddenIcon (QuestManagerSprite param1 )
        {
            if (this.m_hiddenModel.isEmpty())
            {
                this.m_hiddenModel.append(param1);
                this.m_gridList.setModel(this.m_model);
            }
            else if (!this.m_hiddenModel.contains(param1))
            {
                this.m_hiddenModel.append(param1);
            }
            if (this.m_activeModel.contains(param1))
            {
                this.m_activeModel.remove(param1);
            }
            return;
        }//end

        public QuestManagerSprite  removeIcon (QuestManagerSprite param1 ,boolean param2 =true )
        {
            QuestManagerSprite _loc_3 =null ;
            if (this.m_activeModel.contains(param1))
            {
                _loc_3 = this.m_activeModel.remove(param1);
            }
            else
            {
                _loc_3 = this.m_hiddenModel.remove(param1);
            }
            if (param2 && this.numIcons <= 0)
            {
                Global.hud.setHUDComponentVisible(HUD.COMP_QUEST_MANAGER_ICON, false);
            }
            return _loc_3;
        }//end

        public QuestManagerSprite  removeIconByName (String param1 )
        {
            int _loc_2 =0;
            QuestManagerSprite _loc_3 =null ;
            _loc_2 = 0;
            while (_loc_2 < this.m_activeModel.size())
            {

                _loc_3 = this.m_activeModel.getElementAt(_loc_2);
                if (_loc_3 && _loc_3.name == param1)
                {
                    return this.removeIcon(_loc_3);
                }
                _loc_2++;
            }
            _loc_2 = 0;
            while (_loc_2 < this.m_hiddenModel.size())
            {

                _loc_3 = this.m_hiddenModel.getElementAt(_loc_2);
                if (_loc_3 && _loc_3.name == param1)
                {
                    return this.removeIcon(_loc_3);
                }
                _loc_2++;
            }
            return null;
        }//end

        public boolean  iconExists (String param1 )
        {
            int _loc_2 =0;
            QuestManagerSprite _loc_3 =null ;
            _loc_2 = 0;
            while (_loc_2 < this.m_activeModel.size())
            {

                _loc_3 = this.m_activeModel.getElementAt(_loc_2);
                if (_loc_3 && _loc_3.name == param1)
                {
                    return true;
                }
                _loc_2++;
            }
            _loc_2 = 0;
            while (_loc_2 < this.m_hiddenModel.size())
            {

                _loc_3 = this.m_hiddenModel.getElementAt(_loc_2);
                if (_loc_3 && _loc_3.name == param1)
                {
                    return true;
                }
                _loc_2++;
            }
            return false;
        }//end

        public int  numIcons ()
        {
            return this.m_activeModel.size() + this.m_hiddenModel.size();
        }//end

        public void  refreshIcons (boolean param1 =false )
        {
            GameQuest _loc_2 =null ;
            QuestManagerSprite _loc_3 =null ;
            int _loc_4 =0;
            if (Global.questManager)
            {
                _loc_2 = null;
                _loc_4 = 0;
                while (_loc_4 < this.m_model.size())
                {

                    _loc_3 = this.m_model.getElementAt(_loc_4);
                    if (_loc_3 && _loc_3.name)
                    {
                        _loc_3.refresh();
                        _loc_2 = Global.questManager.getQuestByName(_loc_3.name);
                        if (_loc_2)
                        {
                            _loc_2.updateQuestIconTimer();
                        }
                    }
                    _loc_4++;
                }
            }
            return;
        }//end

        public QuestManagerSprite  popFirstQuestIcon ()
        {
            _loc_1 = this.m_activeModel.first ();
            if (_loc_1)
            {
                return this.removeIcon(_loc_1);
            }
            return null;
        }//end

        public VectorListModel  model ()
        {
            return this.m_model;
        }//end

        public VectorListModel  activeModel ()
        {
            return this.m_activeModel;
        }//end

        public VectorListModel  hiddenModel ()
        {
            return this.m_hiddenModel;
        }//end

        public QuestManagerToolTip  questToolTip ()
        {
            return this.m_tool_tip;
        }//end

        public JPanel  titlePanel ()
        {
            return this.m_titlePanel;
        }//end

        public GuideArrow  guideArrow ()
        {
            return this.m_arrow;
        }//end

        public void  guideArrow (GuideArrow param1 )
        {
            this.m_arrow = param1;
            return;
        }//end

    }



