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
import Display.*;
import Display.DialogUI.*;
import Display.ValentineUI.*;
import Display.aswingui.*;
import Modules.bandits.*;
import ZLocalization.*;
//import flash.display.*;
//import flash.events.*;
//import flash.text.*;
//import flash.utils.*;
import org.aswing.*;
import org.aswing.geom.*;

    public class HunterDialogView extends GenericDialogView
    {
        protected JPanel m_contentPanel ;
        protected Array m_items ;
        protected Array m_tabStates ;
        protected Array m_tabs ;
        protected MakerPanel m_makerPanel ;
        protected HunterPanel m_officersPanel ;
        public static  int TAB_STATE_SELECTED =0;
        public static  int TAB_STATE_UNSELECTED =1;
        public static  int TAB_WIDTH =221;
        public static  int TAB_HEIGHT =37;
        public static  int TAB_CAP_FONT_SIZE =22;
        public static  int TAB_FONT_SIZE =16;

        public  HunterDialogView (Dictionary param1 ,String param2 ="",String param3 ="",int param4 =0,Function param5 =null ,String param6 ="",int param7 =0,String param8 ="",Function param9 =null ,String param10 ="")
        {
            super(param1, param2, param3, param4, param5, param6, param7, param8, param9, param10);
            this.setLayout(new SoftBoxLayout(SoftBoxLayout.Y_AXIS));
            return;
        }//end

         protected JPanel  createTitlePanel ()
        {
            JPanel _loc_1 =null ;
            _loc_1 = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.CENTER);
            m_titleFontSize = TextFieldUtil.getLocaleFontSize(30, 20, [{locale:"ja", size:30}]);
            _loc_2 = ZLoc.t("Dialogs",m_titleString +"_title",{amount String(PreyUtil.getHubLevel(HunterDialog.groupId ))});
            title = ASwingHelper.makeTextField(_loc_2, EmbeddedArt.titleFont, m_titleFontSize, EmbeddedArt.titleColor, 3);
            title.filters = EmbeddedArt.newtitleFilters;
            TextFormat _loc_3 =new TextFormat ();
            _loc_3.size = m_titleSmallCapsFontSize;
            TextFieldUtil.formatSmallCaps(title.getTextField(), _loc_3);
            _loc_1.append(title);
            title.getTextField().height = m_titleFontSize * 1.5;
            ASwingHelper.setEasyBorder(_loc_1, 4);
            return _loc_1;
        }//end

         protected void  init ()
        {
            m_bgAsset = m_assetDict.get("dialog_bg");
            m_titleSmallCapsFontSize = 38;
            this.m_tabStates = new Array();
            this.m_tabStates.push(HunterDialog.assetDict.get("tab_selected"));
            this.m_tabStates.push(HunterDialog.assetDict.get("tab_unselected"));
            this.makeBackground();
            this.makeCenterPanel();
            PreyUtil.logDialogStats(HunterDialog.groupId, "view", "hub_ui");
            ASwingHelper.prepare(this);
            return;
        }//end

         protected void  makeBackground ()
        {
            if (m_bgAsset)
            {
                ASwingHelper.setBackground(this, m_bgAsset);
            }
            return;
        }//end

         protected void  makeCenterPanel ()
        {
            ASwingHelper.setBackground(this, m_bgAsset);
            this.m_contentPanel = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.CENTER);
            this.append(createHeaderPanel());
            this.append(this.makeTabsPanel());
            this.append(ASwingHelper.verticalStrut(-3));
            this.append(this.makeSubHeadPanel());
            this.append(this.m_contentPanel);
            this.append(ASwingHelper.verticalStrut(14));
            ASwingHelper.prepare(this);
            return;
        }//end

        protected JPanel  makeSubHeadPanel ()
        {
            _loc_1 = ASwingHelper.makeFlowJPanel(FlowLayout.CENTER );
            _loc_2 = ASwingHelper.makeFlowJPanel(FlowLayout.CENTER ,80);
            ASwingHelper.setEasyBorder(_loc_1, 0, 10, 0, 10);
            Sprite _loc_3 =new Sprite ();
            _loc_3.graphics.beginFill(EmbeddedArt.whiteTextColor);
            _loc_3.graphics.drawRect(0, 0, 670, 50);
            _loc_3.graphics.endFill();
            ASwingHelper.setBackground(_loc_1, _loc_3);
            _loc_4 = ASwingHelper.makeFlowJPanel(FlowLayout.LEFT );
            AssetPane _loc_5 =new AssetPane(HunterDialog.assetDict.get( "prey") );
            _loc_6 = ZLoc.tk("Dialogs",HunterDialog.groupId +"_preyName","",PreyManager.getNumPreyCaptured(HunterDialog.groupId ));
            _loc_7 = TextFieldUtil.formatSmallCapsString(ZLoc.t("Dialogs",HunterDialog.groupId+"_preyCaught",{prey_loc_6}));
            _loc_8 = ASwingHelper.makeLabel(String(PreyManager.getNumPreyCaptured(HunterDialog.groupId )),EmbeddedArt.titleFont ,36,EmbeddedArt.lightOrangeTextColor );
            _loc_9 = ASwingHelper.makeMultilineText(_loc_7 ,150,EmbeddedArt.defaultFontNameBold ,TextFormatAlign.LEFT ,14,EmbeddedArt.brownTextColor );
            _loc_4.appendAll(_loc_5, _loc_8, _loc_9);
            _loc_2.appendAll(ASwingHelper.horizontalStrut(20), _loc_4, ASwingHelper.horizontalStrut(20));
            _loc_1.append(_loc_2);
            return _loc_1;
        }//end

        protected JPanel  makeTabsPanel ()
        {
            JPanel _loc_4 =null ;
            JPanel _loc_5 =null ;
            JTextField _loc_6 =null ;
            TextFormat _loc_7 =null ;
            _loc_1 = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.CENTER ,4);
            this.m_tabs = .get(ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.CENTER), ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.CENTER), ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.CENTER));
            int _loc_2 =0;
            while (_loc_2 < this.m_tabs.length())
            {

                _loc_4 = this.m_tabs.get(_loc_2);
                _loc_4.setPreferredSize(new IntDimension(TAB_WIDTH, TAB_HEIGHT));
                _loc_4.setMinimumSize(new IntDimension(TAB_WIDTH, TAB_HEIGHT));
                _loc_4.setMaximumSize(new IntDimension(TAB_WIDTH, TAB_HEIGHT));
                _loc_5 = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.CENTER);
                _loc_6 = ASwingHelper.makeTextField(ZLoc.t("Dialogs", HunterDialog.groupId + "_TabLabel_" + _loc_2), EmbeddedArt.titleFont, TAB_FONT_SIZE, EmbeddedArt.blueTextColor);
                _loc_7 = new TextFormat();
                _loc_7.size = TAB_CAP_FONT_SIZE;
                TextFieldUtil.formatSmallCaps(_loc_6.getTextField(), _loc_7);
                _loc_5.append(_loc_6);
                _loc_6.getTextField().height = TAB_FONT_SIZE * 1.5;
                _loc_4.append(_loc_5);
                _loc_1.append(_loc_4);
                _loc_4.addEventListener(MouseEvent.MOUSE_UP, this.onTabClicked, false, 0, true);
                _loc_2++;
            }
            _loc_3 = PreyManager.getHunterPreyMode(HunterDialog.groupId);
            this.setSelectedTab(int(_loc_3.get("defaultTab")), false);
            ASwingHelper.setEasyBorder(_loc_1, 0, 10, 0, 10);
            return _loc_1;
        }//end

        protected JPanel  createContentPanel (String param1 )
        {
            _loc_2 = ASwingHelper.makeFlowJPanel ();
            _loc_3 = ASwingHelper.makeLabel(param1 ,EmbeddedArt.defaultFontNameBold ,36,EmbeddedArt.redTextColor );
            _loc_2.append(_loc_3);
            return _loc_2;
        }//end

        protected JPanel  createHuntersPanel ()
        {
            this.m_officersPanel = new HunterPanel();
            this.m_officersPanel.addEventListener(MakerPanel.PREPARE, this.preparePane, false, 0, true);
            return this.m_officersPanel;
        }//end

        protected JPanel  createPreyPanel ()
        {
            PreyPanel _loc_1 =new PreyPanel ();
            _loc_1.addEventListener(MakerPanel.PREPARE, this.preparePane, false, 0, true);
            return _loc_1;
        }//end

        protected JPanel  createUpgradesPanel ()
        {
            UpgradesPanel _loc_1 =new UpgradesPanel ();
            _loc_1.addEventListener(UpgradesPanel.PREPARE, this.preparePane, false, 0, true);
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
            for(int i0 = 0; i0 < this.m_tabs.size(); i0++)
            {
            	_loc_3 = this.m_tabs.get(i0);

                _loc_3.setBackgroundDecorator(new AssetBackground((DisplayObject)new this.m_tabStates.get(TAB_STATE_UNSELECTED)));
            }
            this.m_tabs.get(param1).setBackgroundDecorator(new AssetBackground((DisplayObject)new this.m_tabStates.get(TAB_STATE_SELECTED)));
            this.m_contentPanel.removeAll();
            switch(param1)
            {
                case HunterDialog.TAB_HUNTERS:
                {
                    this.m_contentPanel.appendAll(this.createHuntersPanel());
                    break;
                }
                case HunterDialog.TAB_PREY:
                {
                    this.m_contentPanel.appendAll(this.createPreyPanel());
                    break;
                }
                case HunterDialog.TAB_UPGRADES:
                {
                    this.m_contentPanel.appendAll(this.createUpgradesPanel());
                    break;
                }
                default:
                {
                    break;
                }
            }
            ASwingHelper.prepare(this.parent);
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
                    PreyUtil.logDialogStats(HunterDialog.groupId, HunterDialog.STATS_TAB_NAMES.get(_loc_2), "hub_ui");
                    break;
                }
                _loc_2++;
            }
            return;
        }//end

         protected void  onCancelX (Object param1)
        {
            super.onCancelX(param1);
            PreyUtil.logDialogStats(HunterDialog.groupId, "X", "hub_ui");
            return;
        }//end

    }



