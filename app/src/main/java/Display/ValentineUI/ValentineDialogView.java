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
import Classes.sim.*;
import Display.*;
import Display.DialogUI.*;
import Display.aswingui.*;
import Engine.Managers.*;
import Modules.stats.types.*;
//import flash.display.*;
//import flash.events.*;
//import flash.text.*;
//import flash.utils.*;
import org.aswing.*;
import org.aswing.geom.*;

    public class ValentineDialogView extends GenericDialogView
    {
        protected JPanel m_contentPanel ;
        protected Array m_items ;
        protected Array m_tabStates ;
        protected Array m_tabs ;
        protected Dictionary m_dataDict ;
        protected MakerPanel m_makerPanel ;
        public static  int TAB_STATE_SELECTED =0;
        public static  int TAB_STATE_UNSELECTED =1;
        public static  int TAB_WIDTH =221;
        public static  int TAB_HEIGHT =37;
        public static  int TAB_CAP_FONT_SIZE =22;
        public static  int TAB_FONT_SIZE =16;

        public  ValentineDialogView (Dictionary param1 ,String param2 ="",String param3 ="",int param4 =0,Function param5 =null ,String param6 ="",int param7 =0)
        {
            super(param1, param2, param3, param4, param5, param6, param7);
            this.setLayout(new SoftBoxLayout(SoftBoxLayout.Y_AXIS));
            return;
        }//end

         protected void  init ()
        {
            m_bgAsset = m_assetDict.get("dialog_bg");
            m_titleSmallCapsFontSize = 38;
            this.m_tabStates = new Array();
            this.m_tabStates.push(ValentineDialog.assetDict.get("tab_selected"));
            this.m_tabStates.push(ValentineDialog.assetDict.get("tab_unselected"));
            this.m_dataDict = Global.gameSettings().getValentinesAssets();
            this.makeBackground();
            this.makeCenterPanel();
            ASwingHelper.prepare(this);
            return;
        }//end

         protected void  makeBackground ()
        {
            if (m_bgAsset)
            {
                ASwingHelper.setSizedBackground(this, m_bgAsset);
            }
            return;
        }//end

         protected void  makeCenterPanel ()
        {
            ASwingHelper.setBackground(this, m_bgAsset);
            this.m_contentPanel = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.CENTER);
            this.append(ASwingHelper.verticalStrut(10));
            this.append(this.createHeaderPanel());
            this.append(this.makeTabsPanel());
            this.append(ASwingHelper.verticalStrut(-3));
            this.append(this.m_contentPanel);
            ASwingHelper.prepare(this);
            return;
        }//end

         protected JPanel  createHeaderPanel ()
        {
            JPanel _loc_1 =new JPanel(new BorderLayout ());
            _loc_2 = this.createCloseButtonPanel ();
            _loc_3 = this.createTitlePanel ();
            _loc_4 = this.createHeartPanel ();
            ASwingHelper.prepare(_loc_2);
            ASwingHelper.prepare(_loc_4);
            _loc_5 = _loc_4.getWidth ()-_loc_2.getWidth ();
            _loc_6 = ASwingHelper.makeSoftBoxJPanel ();
            ASwingHelper.setEasyBorder(_loc_2, 5, 5);
            _loc_6.appendAll(ASwingHelper.horizontalStrut(_loc_5), _loc_2);
            _loc_1.append(_loc_4, BorderLayout.WEST);
            _loc_1.append(_loc_3, BorderLayout.CENTER);
            _loc_1.append(_loc_6, BorderLayout.EAST);
            return _loc_1;
        }//end

         protected JPanel  createTitlePanel ()
        {
            JPanel _loc_1 =null ;
            _loc_1 = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.CENTER);
            m_titleFontSize = TextFieldUtil.getLocaleFontSize(30, 20, .get({locale:"ja", size:30}));
            _loc_2 = ZLoc.t("Dialogs",m_titleString +"_title");
            title = ASwingHelper.makeTextField(_loc_2, EmbeddedArt.titleFont, m_titleFontSize, EmbeddedArt.darkPinkTextColor, 3);
            title.filters = EmbeddedArt.valtitleFilters;
            TextFormat _loc_3 =new TextFormat ();
            _loc_3.size = m_titleSmallCapsFontSize;
            TextFieldUtil.formatSmallCaps(title.getTextField(), _loc_3);
            _loc_1.append(title);
            title.getTextField().height = m_titleFontSize * 1.5;
            ASwingHelper.setEasyBorder(_loc_1, 4);
            return _loc_1;
        }//end

         protected JPanel  createCloseButtonPanel ()
        {
            JPanel _loc_1 =new JPanel(ASwingHelper.softBoxLayoutVertical );
            _loc_2 = ASwingHelper.makeMarketCloseButton ();
            _loc_2.addEventListener(MouseEvent.CLICK, onCancel, false, 0, true);
            _loc_1.append(_loc_2);
            ASwingHelper.setEasyBorder(_loc_2, 4, 3, 0, 3);
            return _loc_1;
        }//end

        protected JPanel  createHeartPanel ()
        {
            _loc_1 = ASwingHelper.makeFlowJPanel(FlowLayout.RIGHT );
            _loc_2 = ASwingHelper.makeLabel(String(ValentineManager.getTotalAdmirers ()),EmbeddedArt.titleFont ,24);
            _loc_2.setPreferredWidth(55);
            _loc_3 = ASwingHelper.makeFlowJPanel(FlowLayout.LEFT );
            ASwingHelper.setBackground(_loc_3, new ValentineDialog.assetDict.get("admirersBG"));
            _loc_3.setPreferredHeight(31);
            _loc_3.setMaximumHeight(31);
            _loc_3.setMinimumHeight(31);
            ASwingHelper.setEasyBorder(_loc_3, 10);
            _loc_4 = ASwingHelper.makeLabel(ZLoc.t("Dialogs","ValUI_admirers",{amount EmbeddedArt ""}),.defaultFontNameBold ,12,EmbeddedArt.redTextColor );
            _loc_3.append(_loc_4, ASwingHelper.horizontalStrut(10));
            ASwingHelper.setEasyBorder(_loc_1, 10);
            _loc_1.appendAll(ASwingHelper.horizontalStrut(10), _loc_2, _loc_3);
            return _loc_1;
        }//end

        protected JPanel  makeTabsPanel ()
        {
            JPanel _loc_3 =null ;
            JPanel _loc_4 =null ;
            JTextField _loc_5 =null ;
            TextFormat _loc_6 =null ;
            _loc_1 = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.CENTER ,4);
            this.m_tabs = .get(ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.CENTER), ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.CENTER), ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.CENTER));
            int _loc_2 =0;
            while (_loc_2 < this.m_tabs.length())
            {

                _loc_3 = this.m_tabs.get(_loc_2);
                _loc_3.setPreferredSize(new IntDimension(TAB_WIDTH, TAB_HEIGHT));
                _loc_3.setMinimumSize(new IntDimension(TAB_WIDTH, TAB_HEIGHT));
                _loc_3.setMaximumSize(new IntDimension(TAB_WIDTH, TAB_HEIGHT));
                _loc_4 = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.CENTER);
                _loc_5 = ASwingHelper.makeTextField(ZLoc.t("Dialogs", "ValUI_TabLabel_" + _loc_2), EmbeddedArt.titleFont, TAB_FONT_SIZE, EmbeddedArt.pinkTextColor);
                _loc_6 = new TextFormat();
                _loc_6.size = TAB_CAP_FONT_SIZE;
                TextFieldUtil.formatSmallCaps(_loc_5.getTextField(), _loc_6);
                _loc_4.append(_loc_5);
                _loc_5.getTextField().height = TAB_FONT_SIZE * 1.5;
                _loc_3.append(_loc_4);
                _loc_1.append(_loc_3);
                _loc_3.addEventListener(MouseEvent.MOUSE_UP, this.onTabClicked, false, 0, true);
                _loc_2++;
            }
            this.setSelectedTab(ValentineDialog.DEFAULT_TAB, false);
            ASwingHelper.setEasyBorder(_loc_1, 0, 33, 0, 10);
            return _loc_1;
        }//end

        protected JPanel  createContentPanel (String param1 )
        {
            _loc_2 = ASwingHelper.makeFlowJPanel ();
            _loc_3 = ASwingHelper.makeLabel(param1 ,EmbeddedArt.defaultFontNameBold ,36,EmbeddedArt.redTextColor );
            _loc_2.append(_loc_3);
            return _loc_2;
        }//end

        protected JPanel  createMakerPanel ()
        {
            if (this.m_makerPanel == null)
            {
                this.m_makerPanel = new MakerPanel(this.m_dataDict);
                this.m_makerPanel.addEventListener(MakerPanel.PREPARE, this.preparePane, false, 0, true);
            }
            return this.m_makerPanel;
        }//end

        protected JPanel  createAdmirersPanel ()
        {
            AdmirersPanel _loc_1 =new AdmirersPanel ();
            _loc_1.addEventListener(MakerPanel.PREPARE, this.preparePane, false, 0, true);
            return _loc_1;
        }//end

        protected JPanel  createAchievementsPanel ()
        {
            AchievementsPanel _loc_1 =new AchievementsPanel ();
            _loc_1.addEventListener(MakerPanel.PREPARE, this.preparePane, false, 0, true);
            return _loc_1;
        }//end

        protected void  preparePane (Event event )
        {
            ASwingHelper.prepare(this.parent);
            return;
        }//end

        public void  setSelectedTab (int param1 ,boolean param2 )
        {
            JPanel _loc_3 =null ;
            String _loc_4 =null ;
            for(int i0 = 0; i0 < this.m_tabs.size(); i0++)
            {
            	_loc_3 = this.m_tabs.get(i0);

                _loc_3.setBackgroundDecorator(new AssetBackground(new this.m_tabStates.get(TAB_STATE_UNSELECTED)));
            }
            this.m_tabs.get(param1).setBackgroundDecorator(new AssetBackground(new this.m_tabStates.get(TAB_STATE_SELECTED)));
            this.m_contentPanel.removeAll();
            _loc_4 = "unknown";
            switch(param1)
            {
                case ValentineDialog.TAB_MAKE:
                {
                    this.m_contentPanel.appendAll(this.createMakerPanel());
                    _loc_4 = StatsPhylumType.CARDMAKER;
                    break;
                }
                case ValentineDialog.TAB_ADMIRE:
                {
                    this.m_contentPanel.appendAll(this.createAdmirersPanel());
                    _loc_4 = StatsPhylumType.ADMIRERS;
                    break;
                }
                case ValentineDialog.TAB_ACHIEVE:
                {
                    this.m_contentPanel.appendAll(this.createAchievementsPanel());
                    _loc_4 = StatsPhylumType.PRIZES;
                    break;
                }
                default:
                {
                    break;
                }
            }
            ASwingHelper.prepare(this.parent);
            if (param2)
            {
                StatsManager.count(StatsCounterType.CARDMAKER, StatsKingdomType.VDAY_2011, _loc_4, "tab_click");
            }
            return;
        }//end

        protected void  onTabClicked (MouseEvent event )
        {
            int _loc_2 =0;
            while (_loc_2 < this.m_tabs.length())
            {

                if (this.m_tabs.get(_loc_2) == event.currentTarget)
                {
                    this.setSelectedTab(_loc_2, true);
                    break;
                }
                _loc_2++;
            }
            return;
        }//end

    }



