package Display.VisitorCenterUI;

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
import Display.aswingui.*;
import Engine.Managers.*;
import Events.*;
import Transactions.*;
//import flash.display.*;
//import flash.events.*;
//import flash.text.*;
//import flash.utils.*;
import org.aswing.*;
import org.aswing.event.*;
import org.aswing.geom.*;

    public class VisitorCenterDialogView extends GenericDialogView
    {
        protected JPanel m_factsPanel ;
        protected JPanel m_visitorsPanel ;
        protected JPanel m_contentPanel ;
        protected Array m_items ;
        protected Array m_tabStates ;
        protected Array m_tabs ;
        protected Dictionary m_dataDict ;
        protected JPanel m_textHolder ;
        public static  int TAB_STATE_SELECTED =0;
        public static  int TAB_STATE_UNSELECTED =1;
        public static  int TAB_WIDTH =331;
        public static  int TAB_HEIGHT =38;
        public static  int TAB_CAP_FONT_SIZE =22;
        public static  int TAB_FONT_SIZE =16;

        public  VisitorCenterDialogView (Dictionary param1 ,String param2 ="",String param3 ="",int param4 =0,Function param5 =null ,String param6 ="",int param7 =0,String param8 ="",Function param9 =null ,String param10 ="")
        {
            this.m_dataDict = new Dictionary(true);
            super(param1, param2, param3, param4, param5, param6, param7, param8, param9, param10);
            this.setLayout(new SoftBoxLayout(SoftBoxLayout.Y_AXIS));
            return;
        }//end

         protected void  init ()
        {
            m_bgAsset = m_assetDict.get("dialog_bg");
            m_titleSmallCapsFontSize = 38;
            this.m_tabStates = new Array();
            this.m_tabStates.push(VisitorCenterDialog.assetDict.get("tab_selected"));
            this.m_tabStates.push(VisitorCenterDialog.assetDict.get("tab_unselected"));
            this.m_dataDict = VisitorCenterDialog.assetDict;
            this.makeBackground();
            this.makeCenterPanel();
            ASwingHelper.prepare(this);
            return;
        }//end

         protected void  makeBackground ()
        {
            if (m_bgAsset)
            {
                ASwingHelper.setBackground(this, m_bgAsset);
            }
            this.setPreferredSize(new IntDimension(693, 514));
            this.setMinimumSize(new IntDimension(693, 514));
            this.setMaximumSize(new IntDimension(693, 514));
            return;
        }//end

         protected void  makeCenterPanel ()
        {
            ASwingHelper.setBackground(this, m_bgAsset);
            this.m_contentPanel = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.CENTER);
            this.append(createHeaderPanel());
            this.append(this.makeTitlePanel());
            this.append(this.makeTabsPanel());
            this.append(ASwingHelper.verticalStrut(-3));
            this.append(this.m_contentPanel);
            ASwingHelper.prepare(this);
            return;
        }//end

        protected JPanel  makeTitlePanel ()
        {
            AssetPane friendPane ;
            Player friend ;
            JPanel spacerPanel ;
            jp = ASwingHelper.makeFlowJPanel(FlowLayout.CENTER,4);
            DisplayObject bgAsset =(DisplayObject)new VisitorCenterDialog.assetDict.get( "inner_top");
            ASwingHelper.setBackground(jp, bgAsset);
            DisplayObject friendPic =new EmbeddedArt.hud_no_profile_pic ()as DisplayObject ;
            friendPane = new AssetPane(friendPic);
            friendHolder = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.CENTER);
            friendHolder.append(friendPane);
            if (!Global.isVisiting())
            {
                LoadingManager .load (Global .player .snUser .picture ,void  (Event event )
            {
                friendPane.setAsset(event.target.content);
                return;
            }//end
            );
            }
            else
            {
                friend = Global.player.findFriendById(Global.world.ownerId);
                LoadingManager .load (friend .snUser .picture ,void  (Event event )
            {
                friendPane.setAsset(event.target.content);
                return;
            }//end
            );
            }
            this.m_textHolder = ASwingHelper.makeFlowJPanel(FlowLayout.LEFT);
            this.m_textHolder.setPreferredWidth(460);
            textAP = ASwingHelper.makeMultilineText(m_message,450,EmbeddedArt.defaultFontNameBold,TextFormatAlign.LEFT,14,EmbeddedArt.darkBrownTextColor);
            this.m_textHolder.append(textAP);
            buttonPanel = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.TOP,3);
            ASwingHelper.setEasyBorder(buttonPanel, 5, 0, 5);
            CustomButton editButton =new CustomButton(ZLoc.t("Dialogs","Edit"),null ,"OrangeButtonUI");
            editButton.setFont(new ASFont(EmbeddedArt.titleFont, 16, false, false, false, new ASFontAdvProperties(EmbeddedArt.titleFontEmbed, AntiAliasType.ADVANCED)));
            fontsize = TextFieldUtil.getLocaleFontSize(16,16,.get({localesize"pt",12)});
            CustomButton shareButton =new CustomButton(ZLoc.t("Dialogs","Share"),null ,"GreenButtonUI");
            shareButton.setFont(new ASFont(EmbeddedArt.titleFont, fontsize, false, false, false, new ASFontAdvProperties(EmbeddedArt.titleFontEmbed, AntiAliasType.ADVANCED)));
            editButton.addActionListener(this.launchChangeText, 0, true);
            shareButton.addActionListener(this.sendFeed, 0, true);
            buttonPanel.appendAll(editButton, shareButton);
            jp.appendAll(friendHolder, this.m_textHolder);
            if (!Global.isVisiting())
            {
                jp.append(buttonPanel);
            }
            else
            {
                spacerPanel = ASwingHelper.makeFlowJPanel(FlowLayout.LEFT);
                spacerPanel.setPreferredSize(new IntDimension(84, 67));
                jp.append(spacerPanel);
            }
            jp.setPreferredHeight(75);
            ASwingHelper.setEasyBorder(jp, 0, 10, 0, 10);
            return jp;
        }//end

        protected void  sendFeed (AWEvent event )
        {
            Global.world.viralMgr.visitorUISendNameFeed();
            return;
        }//end

        protected void  launchChangeText (AWEvent event )
        {
            InputTextDialogNormal _loc_2 =new InputTextDialogNormal(ZLoc.t("Dialogs","ValUI_ChangeText"),"ValUI_Change","",m_message ,150,0,this.changeText ,true ,null ,true );
            _loc_2.textField.addEventListener(Event.CHANGE, this.onNameDialogChange);
            UI.displayPopup(_loc_2);
            return;
        }//end

        protected void  changeText (GenericPopupEvent event )
        {
            GameTransactionManager.addTransaction(new TUpdateVisitorCenterMessage(m_message));
            this.m_textHolder.removeAll();
            _loc_2 = ASwingHelper.makeMultilineText(m_message ,455,EmbeddedArt.defaultFontNameBold ,TextFormatAlign.LEFT ,14,EmbeddedArt.darkBrownTextColor );
            Global.world.vistorCenterText = m_message;
            this.m_textHolder.append(_loc_2);
            ASwingHelper.prepare(this.parent);
            return;
        }//end

        protected void  onNameDialogChange (Event event )
        {
            _loc_2 =(TextField) event.target;
            if (_loc_2)
            {
                m_message = _loc_2.text;
            }
            return;
        }//end

        protected JPanel  makeTabsPanel ()
        {
            JPanel _loc_3 =null ;
            JPanel _loc_4 =null ;
            JTextField _loc_5 =null ;
            TextFormat _loc_6 =null ;
            _loc_1 = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.CENTER ,8);
            this.m_tabs = .get(ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.CENTER), ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.CENTER));
            int _loc_2 =0;
            while (_loc_2 < this.m_tabs.length())
            {

                _loc_3 = this.m_tabs.get(_loc_2);
                _loc_3.setPreferredSize(new IntDimension(TAB_WIDTH, TAB_HEIGHT));
                _loc_3.setMinimumSize(new IntDimension(TAB_WIDTH, TAB_HEIGHT));
                _loc_3.setMaximumSize(new IntDimension(TAB_WIDTH, TAB_HEIGHT));
                _loc_4 = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.CENTER);
                _loc_5 = ASwingHelper.makeTextField(ZLoc.t("Dialogs", "VisitorUI_TabLabel_" + _loc_2), EmbeddedArt.titleFont, TAB_FONT_SIZE, EmbeddedArt.blueTextColor);
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
            this.setSelectedTab(VisitorCenterDialog.DEFAULT_TAB, false);
            ASwingHelper.setEasyBorder(_loc_1, 0, 2, 0, 2);
            return _loc_1;
        }//end

        protected void  preparePane (Event event )
        {
            ASwingHelper.prepare(this.parent);
            return;
        }//end

        protected JPanel  createFactsPanel ()
        {
            if (this.m_factsPanel == null)
            {
                this.m_factsPanel = new FactsPanel();
            }
            return this.m_factsPanel;
        }//end

        protected JPanel  createVisitorsPanel ()
        {
            if (this.m_visitorsPanel == null)
            {
                this.m_visitorsPanel = new VisitorsPanel();
                this.m_visitorsPanel.addEventListener(VisitorsPanel.PREPARE, this.preparePane, false, 0, true);
            }
            return this.m_visitorsPanel;
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
                case VisitorCenterDialog.TAB_FACTS:
                {
                    this.m_contentPanel.appendAll(this.createFactsPanel());
                    break;
                }
                case VisitorCenterDialog.TAB_VISITORS:
                {
                    this.m_contentPanel.appendAll(this.createVisitorsPanel());
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
                    break;
                }
                _loc_2++;
            }
            return;
        }//end

    }



