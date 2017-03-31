package Display.FlashMFSList;

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
import Classes.Managers.*;
import Display.*;
import Display.DialogUI.*;
import Display.aswingui.*;
import Engine.Managers.*;
import Modules.stats.types.*;

//import flash.display.*;
//import flash.events.*;
//import flash.geom.*;
//import flash.text.*;
//import flash.utils.*;
import org.aswing.*;
import org.aswing.event.*;
import org.aswing.geom.*;

    public class FlashMFSListDialogView extends BaseDialogView
    {
        private Array m_friendDatas ;
        private Dictionary m_tabs ;
        private Array m_currTabData ;
        private Vector<RowData> m_selected;
        private Vector<RowData> m_unselected;
        private Object m_mfsAssets ;
        private String m_selectedTabId ;
        private String m_filter ;
        private int m_filterTimeout ;
        private JPanel m_unselectedContent ;
        private JPanel m_selectedContent ;
        private JPanel m_checkboxSelectAll ;
        private JTextField m_queryText ;
        private JLabel m_queryNotif ;
        private JPanel m_queryPanel ;
        private CustomButton m_btnSearch ;
        public static  int MAX_SELECTABLE =50;

        public  FlashMFSListDialogView (Dictionary param1 ,Object param2 )
        {
            this.m_mfsAssets = param1.get("mfsAssets");
            this.m_filter = "";
            this.m_tabs = new Dictionary();
            this.m_selected = new Vector<RowData>();
            this.m_unselected = new Vector<RowData>();
            this.m_friendDatas = param2.hasOwnProperty("friendData") ? (param2.get("friendData")) : (.get());
            super(param1, param2);
            return;
        }//end

        public boolean  hasSelectedAllPossible ()
        {
            return this.m_unselected.length == 0 || this.m_selected.length >= MAX_SELECTABLE;
        }//end

         protected JPanel  createContentPanel ()
        {
            Object _loc_4 =null ;
            m_contentPanel = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.CENTER);
            _loc_1 = this.createCartouchePanel();
            _loc_2 = this.createTabPanel();
            _loc_3 = this.createTabBodyPanel();
            m_contentPanel.appendAll(_loc_1, _loc_2, _loc_3);
            for(int i0 = 0; i0 < this.m_friendDatas.size(); i0++)
            {
            	_loc_4 = this.m_friendDatas.get(i0);

                this.selectTab(_loc_4.get("name"));
                break;
            }
            this.onFullScreenToggle(null);
            return m_contentPanel;
        }//end

         protected JPanel  createButtonPanel ()
        {
            JPanel _loc_1 =new JPanel(new FlowLayout(AsWingConstants.CENTER ));
            CustomButton _loc_2 =new CustomButton(m_data.get( "sendLabel") ,null ,BUTTON_GREEN );
            _loc_2.addActionListener(this.onSendClick, 0, true);
            _loc_1.append(_loc_2);
            return _loc_1;
        }//end

        private JPanel  createCartouchePanel ()
        {
            _loc_1 = (DisplayObject)newthis.m_mfsAssets.get("portraitBG")
            JPanel _loc_2 =new JPanel(new CenterLayout ());
            _loc_2.setPreferredSize(new IntDimension(_loc_1.width, _loc_1.height));
            ASwingHelper.setBackground(_loc_2, _loc_1);
            if (m_assetDict.hasOwnProperty("itemIcon"))
            {
                _loc_2.append(new AssetPane((DisplayObject)m_assetDict.get("itemIcon")));
            }
            _loc_3 = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.CENTER);
            _loc_3.append(new AssetPane(new (DisplayObject)this.m_mfsAssets.get("speechBubblePoint")));
            JLabel _loc_4 =null ;
            if (m_data.hasOwnProperty("subtitle"))
            {
                _loc_4 = ASwingHelper.makeLabel(m_data.get("subtitle"), EmbeddedArt.defaultFontNameBold, 18, EmbeddedArt.brownTextColor, AsWingConstants.LEFT);
            }
            AssetPane _loc_5 =null ;
            if (m_data.hasOwnProperty("message"))
            {
                _loc_5 = ASwingHelper.makeMultilineText(m_data.get("message"), 470, EmbeddedArt.defaultFontNameBold, TextFormatAlign.LEFT, 14, EmbeddedArt.darkBlueTextColor);
            }
            _loc_6 = ASwingHelper.makeSoftBoxJPanelVertical();
            ASwingHelper.setEasyBorder(_loc_6, 10, 10, 10);
            if (_loc_4)
            {
                _loc_6.append(_loc_4);
            }
            if (_loc_5)
            {
                _loc_6.append(_loc_5);
            }
            _loc_7 = ASwingHelper.makeSoftBoxJPanel();
            ASwingHelper.setEasyBorder(_loc_7, 0, 10, 0, 10);
            ASwingHelper.setBackground(_loc_7, new (DisplayObject)this.m_mfsAssets.get("speechBubbleBG"), new Insets(3, 3, 3, 0));
            _loc_7.appendAll(_loc_2, ASwingHelper.horizontalStrut(-10), _loc_3, _loc_6);
            return _loc_7;
        }//end

        private JPanel  createTabPanel ()
        {
            Object _loc_2 =null ;
            String _loc_3 =null ;
            JLabel _loc_4 =null ;
            JPanel _loc_5 =null ;
            DisplayObject _loc_6 =null ;
            _loc_1 = ASwingHelper.makeSoftBoxJPanel();
            ASwingHelper.setEasyBorder(_loc_1, 0, 10, 0, 10);
            for(int i0 = 0; i0 < this.m_friendDatas.size(); i0++)
            {
            	_loc_2 = this.m_friendDatas.get(i0);

                _loc_3 = _loc_2.get("name");
                _loc_4 = ASwingHelper.makeLabel(ZLoc.t("Dialogs", "mfs_tab_" + _loc_3), EmbeddedArt.titleFont, 16, EmbeddedArt.blueTextColor);
                _loc_5 = new JPanel(new CenterLayout());
                _loc_6 =(DisplayObject) new this.m_mfsAssets.get("tabActiveBG");
                _loc_5.setPreferredSize(new IntDimension(_loc_6.width, _loc_6.height));
                ASwingHelper.setBackground(_loc_5, _loc_6);
                _loc_5.append(_loc_4);
                _loc_5.addEventListener(MouseEvent.CLICK, this.onTabClick, false, 0, true);
                this.m_tabs.put(_loc_3,  _loc_5);
                _loc_1.append(_loc_5);
            }
            return _loc_1;
        }//end

        private JPanel  createTabBodyPanel ()
        {
            _loc_1 = (DisplayObject)newthis.m_mfsAssets.get("checkboxBG")
            this.m_checkboxSelectAll = new JPanel(new CenterLayout());
            this.m_checkboxSelectAll.setPreferredSize(new IntDimension(_loc_1.width, _loc_1.height));
            ASwingHelper.setBackground(this.m_checkboxSelectAll, _loc_1);
            this.m_checkboxSelectAll.addEventListener(MouseEvent.CLICK, this.onSelectAllClick, false, 0, true);
            _loc_2 = ASwingHelper.makeSoftBoxJPanel();
            _loc_2.appendAll(this.m_checkboxSelectAll, ASwingHelper.makeLabel(ZLoc.t("Dialogs", "mfs_select_all"), EmbeddedArt.defaultFontNameBold, 16, EmbeddedArt.darkBlueTextColor, AsWingConstants.LEFT), ASwingHelper.horizontalStrut(10), new AssetPane(new (DisplayObject)this.m_mfsAssets.get("searchIcon")));
            this.m_queryText = ASwingHelper.makeTextField("", EmbeddedArt.defaultFontNameBold, 14, EmbeddedArt.darkBlueTextColor);
            this.m_queryText.setEditable(true);
            this.m_queryText.getTextField().selectable = true;
            ASwingHelper.setEasyBorder(this.m_queryText, 0, 10, 0, 10);
            this.m_queryText.addEventListener(KeyboardEvent.KEY_UP, this.onQueryTextChange, false, 0, true);
            this.m_queryNotif = ASwingHelper.makeLabel(ZLoc.t("Dialogs", "exit_fullscreen_to_search"), EmbeddedArt.defaultFontNameBold, 16, EmbeddedArt.lightGrayTextColor);
            _loc_3 = (DisplayObject)newthis.m_mfsAssets.get("textfieldBG")
            this.m_queryPanel = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.CENTER);
            ASwingHelper.setBackground(this.m_queryPanel, _loc_3);
            ASwingHelper.setEasyBorder(this.m_queryPanel, 0, 5, 0, 5);
            this.m_queryPanel.append(this.m_queryText);
            this.m_btnSearch = new CustomButton("  " + ZLoc.t("Dialogs", "mfs_search") + "  ", null, "GreenSmallButtonUI");
            this.m_btnSearch.addActionListener(this.onSearchBtnClick, 0, true);
            JPanel _loc_4 =new JPanel(new BorderLayout(0,5));
            ASwingHelper.setEasyBorder(_loc_4, 5, 5, 5, 5);
            _loc_4.append(_loc_2, BorderLayout.WEST);
            _loc_4.append(this.m_queryPanel, BorderLayout.CENTER);
            _loc_4.append(this.m_btnSearch, BorderLayout.EAST);
            Global.stage.addEventListener(Event.FULLSCREEN, this.onFullScreenToggle, false, 0, true);
            this.m_unselectedContent = ASwingHelper.makeSoftBoxJPanelVertical();
            ASwingHelper.setEasyBorder(this.m_unselectedContent, 0, 0, 0, 27);
            JViewport _loc_5 =new JViewport(this.m_unselectedContent ,true );
            _loc_5.setVerticalAlignment(AsWingConstants.TOP);
            JScrollPane _loc_6 =new JScrollPane(_loc_5 ,JScrollPane.SCROLLBAR_AS_NEEDED ,JScrollPane.SCROLLBAR_NEVER );
            _loc_6.setPreferredWidth(350);
            ASwingHelper.setEasyBorder(_loc_6, 5, 5, 5, 4);
            _loc_7 = (DisplayObject)newthis.m_mfsAssets.get("listBG")
            _loc_8 = ASwingHelper.makeSoftBoxJPanel();
            _loc_8.setPreferredHeight(270);
            ASwingHelper.setBackground(_loc_8, _loc_7);
            _loc_8.append(_loc_6);
            this.m_selectedContent = ASwingHelper.makeSoftBoxJPanelVertical();
            ASwingHelper.setEasyBorder(this.m_selectedContent, 0, 0, 0, 27);
            JViewport _loc_9 =new JViewport(this.m_selectedContent ,true );
            _loc_9.setVerticalAlignment(AsWingConstants.TOP);
            JScrollPane _loc_10 =new JScrollPane(_loc_9 ,JScrollPane.SCROLLBAR_AS_NEEDED ,JScrollPane.SCROLLBAR_NEVER );
            _loc_10.setPreferredWidth(250);
            ASwingHelper.setEasyBorder(_loc_10, 5, 5, 5, 4);
            _loc_11 = (DisplayObject)newthis.m_mfsAssets.get("listBG")
            _loc_12 = ASwingHelper.makeSoftBoxJPanel();
            _loc_12.setPreferredHeight(270);
            ASwingHelper.setBackground(_loc_12, _loc_11);
            _loc_12.append(_loc_10);
            JPanel _loc_13 =new JPanel(new FlowLayout(FlowLayout.CENTER ,0,0,false ));
            _loc_13.appendAll(_loc_8, ASwingHelper.horizontalStrut(5), _loc_12);
            _loc_14 = (DisplayObject)newthis.m_mfsAssets.get("tabBodyBG")
            _loc_15 = ASwingHelper.makeSoftBoxJPanelVertical();
            _loc_15.setPreferredHeight(330);
            ASwingHelper.setEasyBorder(_loc_15, 0, 10, 0, 10);
            ASwingHelper.setBackground(_loc_15, _loc_14);
            _loc_15.appendAll(_loc_4, _loc_13);
            return _loc_15;
        }//end

        private boolean  select (RowData param1 ,boolean param2 =false )
        {
            RowData _loc_5 =null ;
            RowData _loc_6 =null ;
            if (this.m_selected.length >= MAX_SELECTABLE)
            {
                if (!param2)
                {
                    UI.displayMessage(ZLoc.t("Dialogs", "mfs_select_max", {numPeople:MAX_SELECTABLE}), 0, null, "", true);
                }
                return false;
            }
            int _loc_3 =0;
            while (_loc_3 < this.m_selected.length())
            {

                _loc_5 = this.m_selected.get(_loc_3);
                if (_loc_5.zid == param1.zid)
                {
                    return false;
                }
                _loc_3++;
            }
            _loc_4 = this.m_unselected.indexOf(param1);
            if (this.m_unselected.indexOf(param1) != -1)
            {
                _loc_6 = new RowData();
                _loc_6.zid = param1.zid;
                _loc_6.recipient = param1.recipient;
                _loc_6.component = this.addSelectedRow(_loc_6.recipient);
                this.m_selected.push(_loc_6);
                if (param1.component)
                {
                    param1.component.removeFromContainer();
                }
                this.m_unselected.splice(_loc_4, 1);
                this.updateCheckbox();
                return true;
            }
            return false;
        }//end

        private void  deselect (RowData param1 )
        {
            _loc_2 = this.m_selected.indexOf(param1 );
            if (_loc_2 != -1)
            {
                if (param1.component)
                {
                    param1.component.removeFromContainer();
                }
                this.m_selected.splice(_loc_2, 1);
            }
            this.refreshUnselectedContent();
            this.updateCheckbox();
            return;
        }//end

        private void  updateCheckbox ()
        {
            this.m_checkboxSelectAll.removeAll();
            if (this.hasSelectedAllPossible)
            {
                this.m_checkboxSelectAll.append(new AssetPane(new (DisplayObject)this.m_mfsAssets.get("check")));
            }
            return;
        }//end

        private void  refreshUnselectedContent ()
        {
            FlashMFSRecipient _loc_1 =null ;
            boolean _loc_2 =false ;
            RowData _loc_3 =null ;
            RowData _loc_4 =null ;
            this.m_unselectedContent.removeAll();
            this.m_unselected = new Vector<RowData>();
            for(int i0 = 0; i0 < this.m_currTabData.size(); i0++)
            {
            	_loc_1 = this.m_currTabData.get(i0);

                _loc_2 = false;
                for(int i0 = 0; i0 < this.m_selected.size(); i0++)
                {
                	_loc_3 = this.m_selected.get(i0);

                    if (_loc_3.recipient.zid == _loc_1.zid)
                    {
                        _loc_2 = true;
                        break;
                    }
                }
                if (!_loc_2 && _loc_1.name.toLowerCase().search(this.m_filter.toLowerCase()) != -1)
                {
                    _loc_4 = new RowData();
                    _loc_4.zid = _loc_1.zid;
                    _loc_4.recipient = _loc_1;
                    _loc_4.component = this.addUnselectedRow(_loc_1);
                    this.m_unselected.push(_loc_4);
                }
            }
            this.updateCheckbox();
            return;
        }//end

        private boolean  isTextRenderableWithFont (String param1 ,String param2 )
        {
            _loc_3 = ASwingHelper.makeText(param1,200,param2,14,EmbeddedArt.darkBlueTextColor);
            BitmapData _loc_4 =new BitmapData(_loc_3.width ,_loc_3.height ,true ,0);
            _loc_4.draw(_loc_3);
            _loc_5 = _loc_4.getColorBoundsRect(4278190080,0,false);
            if (_loc_4.getColorBoundsRect(4278190080, 0, false).width == 0 && _loc_5.height == 0)
            {
                return false;
            }
            return true;
        }//end

        private Component  addUnselectedRow (FlashMFSRecipient param1 )
        {
            ASFontAdvProperties _loc_5 =null ;
            ASFont _loc_6 =null ;
            _loc_2 = ASwingHelper.makeLabel(param1.name,EmbeddedArt.defaultFontNameBold,14,EmbeddedArt.darkBlueTextColor,AsWingConstants.LEFT);
            if (!this.isTextRenderableWithFont(param1.name, EmbeddedArt.defaultFontNameBold))
            {
                _loc_5 = new ASFontAdvProperties();
                _loc_6 = new ASFont(EmbeddedArt.defaultFontNameBold, 14, false, false, false, _loc_5);
                _loc_2.supplyFont(_loc_6);
            }
            _loc_3 =(DisplayObject) new this.m_mfsAssets.get( "checkboxSmallBG");
            _loc_4 = ASwingHelper.makeSoftBoxJPanel();
            _loc_4.appendAll(new AssetPane(_loc_3), _loc_2);
            _loc_4.addEventListener(MouseEvent.CLICK, this.onRowClick, false, 0, true);
            this.m_unselectedContent.append(_loc_4);
            return _loc_4;
        }//end

        private Component  addSelectedRow (FlashMFSRecipient param1 )
        {
            ASFontAdvProperties _loc_4 =null ;
            ASFont _loc_5 =null ;
            _loc_2 = ASwingHelper.makeLabel(param1.name ,EmbeddedArt.defaultFontNameBold ,14,EmbeddedArt.darkBlueTextColor ,AsWingConstants.LEFT );
            if (!this.isTextRenderableWithFont(param1.name, EmbeddedArt.defaultFontNameBold))
            {
                _loc_4 = new ASFontAdvProperties();
                _loc_5 = new ASFont(EmbeddedArt.defaultFontNameBold, 14, false, false, false, _loc_4);
                _loc_2.supplyFont(_loc_5);
            }
            _loc_3 = ASwingHelper.makeSoftBoxJPanel ();
            _loc_3.appendAll(new AssetPane(new (DisplayObject)this.m_mfsAssets.get("mfsRemove")), _loc_2);
            _loc_3.addEventListener(MouseEvent.CLICK, this.onRowClick, false, 0, true);
            this.m_selectedContent.append(_loc_3);
            return _loc_3;
        }//end

        private void  selectTab (String param1 )
        {
            String _loc_2 =null ;
            Class _loc_3 =null ;
            Component _loc_4 =null ;
            Object _loc_5 =null ;
            Object _loc_6 =null ;
            for(int i0 = 0; i0 < this.m_tabs.size(); i0++)
            {
            	_loc_2 = this.m_tabs.get(i0);

                _loc_3 = _loc_2 == param1 ? (this.m_mfsAssets.get("tabActiveBG")) : (this.m_mfsAssets.get("tabDisabledBG"));
                _loc_4 =(Component) this.m_tabs.get(_loc_2);
                ASwingHelper.setBackground(_loc_4, new (DisplayObject)_loc_3);
            }
            if (this.m_selectedTabId != param1)
            {
                this.m_selectedTabId = param1;
                for(int i0 = 0; i0 < this.m_friendDatas.size(); i0++)
                {
                	_loc_6 = this.m_friendDatas.get(i0);

                    if (_loc_6.get("name") == this.m_selectedTabId)
                    {
                        _loc_5 = _loc_6;
                        break;
                    }
                }
                this.m_currTabData = _loc_5.get("data");
                this.refreshUnselectedContent();
            }
            this.updateCheckbox();
            return;
        }//end

         protected void  closeMe ()
        {
            clearTimeout(this.m_filterTimeout);
            super.closeMe();
            return;
        }//end

         protected void  onCancelX (Object param1)
        {
            super.onCancelX(param1);
            StatsManager.sample(100, StatsCounterType.DIALOG, StatsCounterType.FLASH_MFS, "factory_workers", "close", this.m_selectedTabId);
            return;
        }//end

        private void  onTabClick (MouseEvent event )
        {
            String _loc_2 =null ;
            for(int i0 = 0; i0 < this.m_tabs.size(); i0++)
            {
            	_loc_2 = this.m_tabs.get(i0);

                if (this.m_tabs.get(_loc_2) == event.currentTarget)
                {
                    this.selectTab(_loc_2);
                    return;
                }
            }
            return;
        }//end

        private void  onRowClick (MouseEvent event )
        {
            RowData _loc_3 =null ;
            RowData _loc_4 =null ;
            _loc_2 =(Component) event.currentTarget;
            for(int i0 = 0; i0 < this.m_unselected.size(); i0++)
            {
            	_loc_3 = this.m_unselected.get(i0);

                if (_loc_3.component == _loc_2)
                {
                    this.select(_loc_3);
                    return;
                }
            }
            for(int i0 = 0; i0 < this.m_selected.size(); i0++)
            {
            	_loc_4 = this.m_selected.get(i0);

                if (_loc_4.component == _loc_2)
                {
                    this.deselect(_loc_4);
                    return;
                }
            }
            return;
        }//end

        private void  onSelectAllClick (MouseEvent event )
        {
            boolean _loc_2 =false ;
            if (this.hasSelectedAllPossible)
            {
                this.m_selectedContent.removeAll();
                this.m_selected = new Vector<RowData>();
                this.refreshUnselectedContent();
                StatsManager.sample(100, StatsCounterType.DIALOG, StatsCounterType.FLASH_MFS, "factory_workers", "deselect_all", this.m_selectedTabId);
            }
            else
            {
                while (this.m_unselected.length > 0)
                {

                    if (!this.select(this.m_unselected.get(0), _loc_2))
                    {
                        break;
                    }
                    _loc_2 = true;
                }
                StatsManager.sample(100, StatsCounterType.DIALOG, StatsCounterType.FLASH_MFS, "factory_workers", "select_all", this.m_selectedTabId);
            }
            return;
        }//end

        private void  onQueryTextChange (KeyboardEvent event )
        {
            this.m_filter = this.m_queryText.getText();
            clearTimeout(this.m_filterTimeout);
            this.m_filterTimeout = setTimeout(this.refreshUnselectedContent, 500);
            return;
        }//end

        private void  onSearchBtnClick (AWEvent event )
        {
            this.refreshUnselectedContent();
            return;
        }//end

        private void  onFullScreenToggle (Event event )
        {
            this.m_queryPanel.removeAll();
            if (Global.stage.displayState == StageDisplayState.FULL_SCREEN)
            {
                this.m_queryPanel.append(this.m_queryNotif);
            }
            else
            {
                this.m_queryPanel.append(this.m_queryText);
            }
            return;
        }//end

        private void  onSendClick (AWEvent event )
        {
            RowData _loc_3 =null ;
            Function _loc_4 =null ;
            Global.stage.displayState = StageDisplayState.NORMAL;
            Array _loc_2 =new Array();
            for(int i0 = 0; i0 < this.m_selected.size(); i0++)
            {
            	_loc_3 = this.m_selected.get(i0);

                _loc_3.recipient.selected = true;
                _loc_2.push(_loc_3.recipient);
            }
            _loc_4 =(Function) m_data.get("sendCallback");
            _loc_4.call(null, _loc_2, this.m_selectedTabId);
            close();
            StatsManager.sample(100, StatsCounterType.DIALOG, StatsCounterType.FLASH_MFS, "factory_workers", "send", this.m_selectedTabId, "", _loc_2.length());
            return;
        }//end

    }

import org.aswing.Component;
import Classes.Managers.FlashMFSRecipient;
class RowData
    public double zid ;
    public FlashMFSRecipient recipient ;
    public Component component ;

     RowData ()
    {
        return;
    }//end






