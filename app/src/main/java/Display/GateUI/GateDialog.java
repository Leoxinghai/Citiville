package Display.GateUI;

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
import Classes.gates.*;
import Classes.util.*;
import Display.*;
import Display.DialogUI.*;
import Display.aswingui.*;
import Events.*;
//import flash.display.*;
//import flash.events.*;
//import flash.filters.*;
//import flash.text.*;
//import flash.utils.*;
import org.aswing.*;
import org.aswing.border.*;
import org.aswing.ext.*;

    public class GateDialog extends GenericDialog
    {
        protected Component m_list ;
        protected Array m_listData ;
        private GridListCellFactory m_cellFactory ;
        private String m_subtext ;
        private int m_listHeight ;
        private int m_listWidth ;
        protected boolean m_canBeFinished ;
        protected CustomButton m_finishButton ;
        protected CustomButton m_buyAllButton ;
        protected Container m_buttonPanel ;
        protected Component m_headerPanel ;
        protected Component m_listPanel ;
        protected Function m_finishCheckCB ;
        protected Function m_finishCB ;
        private Function m_buyAllCB ;
        private int m_columns ;
        protected String m_gateType ;
        protected AbstractGate m_gate ;
        protected JLabel m_discountLabel ;
        public static  ASFont TITLE_FONT =ASwingHelper.getBoldFont(28);
        public static  ASFont TEXT_FONT =ASwingHelper.getBoldFont(12);
        public static  ASFont BUTTON_FONT =ASwingHelper.getBoldFont(12);
        public static  int TEXT_COLOR =489375;
        public static  int TITLE_COLOR =16777215;
        public static  GlowFilter GLOW_FILTER =new GlowFilter(489375,1,5,5,4,2);
        public static Dictionary assets ;

        public  GateDialog (Array param1 ,GridListCellFactory param2 ,String param3 ,String param4 ,Function param5 ,Function param6 ,Function param7 ,int param8 =3,double param9 =562,double param10 =205,boolean param11 =true ,AbstractGate param12 =null ,Object param13 =null )
        {
            this.m_gate = param12;
            if (this.m_gate && this.m_gate.showBuyAllCost)
            {
                addEventListener("buyCellItemEvent", this.refreshBtnPanel, false, 0, true);
            }
            m_jpanel = new JPanel(new SoftBoxLayout(SoftBoxLayout.Y_AXIS, 1, SoftBoxLayout.LEFT));
            m_title = param3;
            this.m_subtext = param4;
            this.m_listData = param1;
            this.m_cellFactory = param2;
            this.m_listWidth = param9;
            this.m_listHeight = param10;
            this.m_finishCheckCB = param5;
            this.m_finishCB = param6;
            this.m_canBeFinished = this.m_finishCheckCB();
            this.m_buyAllCB = param7;
            this.m_columns = param8;
            super("", param3, 0, null, param3, "", param11);
            return;
        }//end

        protected void  refreshBtnPanel (Event event )
        {
            if (this.m_gate && this.m_gate.showBuyAllCost)
            {
                if (this.m_gate.totalCost > 0)
                {
                    if (this.m_gate.progress >= this.m_gate.threshold)
                    {
                        m_jpanel.remove(this.m_discountLabel);
                    }
                    this.m_buyAllButton.setText(ZLoc.t("Dialogs", "BuyAllForButtonLabel", {cashAmount:this.m_gate.totalCost}));
                    ASwingHelper.prepare(this.m_buyAllButton);
                }
                else
                {
                    this.m_buttonPanel.remove(this.m_buyAllButton);
                }
            }
            return;
        }//end

         protected Array  getAssetDependencies ()
        {
            return .get(DelayedAssetLoader.BUILDABLE_ASSETS, DelayedAssetLoader.MATCHMAKING_ASSETS, DelayedAssetLoader.GENERIC_DIALOG_ASSETS);
        }//end

         protected void  onAssetsLoaded (Event event =null )
        {
            assets = new Dictionary(true);
            _loc_2 = m_assetDependencies.get(DelayedAssetLoader.BUILDABLE_ASSETS) ;
            assets.put("buildables_bg", _loc_2.buildables_bg);
            assets.put("buildables_check", _loc_2.buildables_check);
            assets.put("buildables_item", _loc_2.buildables_item);
            assets.put("buildables_wishlistIcon", _loc_2.buildables_wishlistIcon);
            _loc_3 = m_assetDependencies.get(DelayedAssetLoader.MATCHMAKING_ASSETS) ;
            assets.put("buildingBuddy_helmet", _loc_3.buildingBuddy_helmet);
            assets.put("buildingBuddy_cellPic", _loc_3.buildingBuddy_cellPic);
            _loc_4 = m_assetDependencies.get(DelayedAssetLoader.GENERIC_DIALOG_ASSETS) ;
            assets.put("checkmark_green", _loc_4.checkmark_green);
            this.createDialog();
            return;
        }//end

        protected Component  createHeaderPanel ()
        {
            JPanel _loc_1 =new JPanel(new BorderLayout ());
            _loc_2 = this.createTitlePanel();
            _loc_3 = this.createCloseButton();
            _loc_4 = ASwingHelper.makeCloseButtonOffset();
            _loc_1.append(_loc_2, BorderLayout.CENTER);
            _loc_1.append(_loc_3, BorderLayout.EAST);
            _loc_1.append(_loc_4, BorderLayout.WEST);
            return _loc_1;
        }//end

        protected Component  createTitlePanel ()
        {
            _loc_1 = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.CENTER);
            _loc_2 = this.createTitle();
            _loc_1.append(_loc_2);
            return _loc_1;
        }//end

        protected Component  createTitle ()
        {
            _loc_1 = ASwingHelper.makeTextField(m_title,EmbeddedArt.titleFont,30,EmbeddedArt.titleColor);
            _loc_1.filters = EmbeddedArt.titleFilters;
            TextFormat _loc_2 =new TextFormat ();
            _loc_2.size = 36;
            TextFieldUtil.formatSmallCaps(_loc_1.getTextField(), _loc_2);
            _loc_1.getTextField().height = 45;
            return _loc_1;
        }//end

        protected AbstractButton  createCloseButton ()
        {
            _loc_1 = ASwingHelper.makeMarketCloseButton();
            ASwingHelper.setEasyBorder(_loc_1, 0, 0, 17, 8);
            _loc_1.addActionListener(this.onCancelX);
            return _loc_1;
        }//end

        protected Container  createButtonPanel ()
        {
            JPanel _loc_1 =new JPanel(new SoftBoxLayout(SoftBoxLayout.X_AXIS ,100,SoftBoxLayout.CENTER ));
            AssetIcon _loc_2 =new AssetIcon(new EmbeddedArt.icon_cash_big ()as DisplayObject );
            if (this.m_gate && this.m_gate.showBuyAllCost)
            {
                this.m_buyAllButton = new CustomButton(ZLoc.t("Dialogs", "BuyAllForButtonLabel", {cashAmount:this.m_gate.totalCost}), _loc_2, "BigCashButtonUI");
                this.m_buyAllButton.addActionListener(this.onBuyAll, 0, true);
                ASwingHelper.prepare(this.m_buyAllButton);
            }
            else
            {
                this.m_buyAllButton = new CustomButton(ZLoc.t("Dialogs", "BuyAllButtonLabel"), _loc_2, "BigCashButtonUI");
                this.m_buyAllButton.addActionListener(this.onBuyAll, 0, true);
            }
            _loc_3 = this.m_gateType==AbstractGate.PRE_BUILD_GATE? (ZLoc.t("Dialogs", "StartBuilding")) : (ZLoc.t("Dialogs", "FinishBuilding"));
            this.m_finishButton = new CustomButton(_loc_3, null, "GreenButtonUI");
            this.m_finishButton.setEnabled(this.m_canBeFinished);
            this.m_finishButton.addActionListener(this.onFinishClick, 0, true);
            EmptyBorder _loc_4 =new EmptyBorder(null ,new Insets(0,0,0,0));
            if (this.m_gate && this.m_gate.showBuyAllCost)
            {
                if (this.m_gate.totalCost > 0)
                {
                    _loc_1.appendAll(this.m_buyAllButton, this.m_finishButton);
                }
                else
                {
                    _loc_1.appendAll(this.m_finishButton);
                }
            }
            else
            {
                _loc_1.appendAll(this.m_buyAllButton, this.m_finishButton);
            }
            _loc_1.setBorder(_loc_4);
            return _loc_1;
        }//end

        protected Component  createListPanel ()
        {
            JPanel _loc_1 =new JPanel(new SoftBoxLayout(SoftBoxLayout.X_AXIS ,0,SoftBoxLayout.CENTER ));
            this.m_list = this.createList();
            ASwingHelper.setEasyBorder(this.m_list, 0, 20);
            _loc_1.append(this.m_list);
            return _loc_1;
        }//end

        protected Component  createList ()
        {
            _loc_1 = this(.m_listData.length -1)/this.m_columns +1;
            ASwingScrollingList _loc_2 =new ASwingScrollingList(this.m_listData ,this.m_cellFactory ,ASwingScrollingList.SCROLL_HORIZONTAL ,this.m_columns ,_loc_1 ,this.m_listWidth *(Math.min(this.m_columns ,this.m_listData.length )/this.m_columns ),this.m_listHeight *_loc_1 );
            _loc_2.scrollHPolicy = JScrollPane.SCROLLBAR_NEVER;
            _loc_2.scrollVPolicy = JScrollPane.SCROLLBAR_NEVER;
            return _loc_2;
        }//end

        protected void  layoutComponents ()
        {
            DisplayObject _loc_1 =(DisplayObject)new assets.get( "buildables_bg");
            m_jpanel.setBackgroundDecorator(new AssetBackground(_loc_1));
            m_jpanel.append(this.m_headerPanel);
            m_jpanel.append(this.m_listPanel);
            m_jpanel.append(ASwingHelper.verticalStrut(5));
            if (this.m_gate.showBuyAllCost > 0 && this.m_gate.discount > 0 && this.m_gate.progress < this.m_gate.threshold)
            {
                this.m_discountLabel = ASwingHelper.makeLabel(ZLoc.t("Dialogs", "BuyAllAndSave", {percentAmount:20}), EmbeddedArt.titleFont, 16, 8176905, JLabel.CENTER);
                m_jpanel.append(this.m_discountLabel);
            }
            m_jpanel.append(this.m_buttonPanel);
            return;
        }//end

        protected void  createDialog ()
        {
            this.m_headerPanel = this.createHeaderPanel();
            this.m_buttonPanel = this.createButtonPanel();
            this.m_listPanel = this.createListPanel();
            this.layoutComponents();
            finalizeAndShow();
            return;
        }//end

        protected void  onCancelX (Event event )
        {
            countDialogAction("X");
            this.closeMe(event);
            return;
        }//end

         protected void  closeMe (Event event )
        {
            this.m_gate = null;
            removeEventListener("buyCellItemEvent", this.refreshBtnPanel);
            Global.ui.dispatchEvent(new GenericObjectEvent(GenericObjectEvent.BUILDING_UPGRADE, null, false, "ui_close"));
            super.closeMe(event);
            return;
        }//end

        protected void  onFinishClick (Event event )
        {
            Event _loc_2 =null ;
            if (this.m_canBeFinished == false)
            {
                countDialogAction("FINISH");
                return;
            }
            this.closeMe(_loc_2);
            if (this.m_finishCB != null)
            {
                this.m_finishCB();
            }
            return;
        }//end

        protected void  onBuyAll (Event event )
        {
            countDialogAction("BUY ALL");
            this.m_buyAllCB();
            if (this.m_gate && this.m_gate.showBuyAllCost)
            {
                this.activateFinishButton();
                this.m_buttonPanel.remove(this.m_buyAllButton);
                if (this.m_cellFactory instanceof KeyCellItemFactory)
                {
                    (this.m_cellFactory as KeyCellItemFactory).updateList();
                }
            }
            else
            {
                this.closeMe(event);
            }
            return;
        }//end

        public void  activateFinishButton ()
        {
            this.m_canBeFinished = true;
            this.m_buttonPanel.remove(this.m_finishButton);
            this.m_finishButton = null;
            _loc_1 = this.m_gateType==AbstractGate.PRE_BUILD_GATE? (ZLoc.t("Dialogs", "StartBuilding")) : (ZLoc.t("Dialogs", "FinishBuilding"));
            this.m_finishButton = new CustomButton(_loc_1, null, "GreenButtonUI");
            this.m_finishButton.setEnabled(this.m_canBeFinished);
            this.m_finishButton.addActionListener(this.onFinishClick, 0, true);
            this.m_buttonPanel.append(this.m_finishButton);
            ASwingHelper.prepare(this.m_buttonPanel);
            return;
        }//end

        public void  setFinishButtonText (String param1 )
        {
            if (this.m_finishButton != null)
            {
                this.m_buttonPanel.remove(this.m_finishButton);
            }
            this.m_finishButton = null;
            this.m_finishButton = new CustomButton(param1, null, "GreenButtonUI");
            this.m_finishButton.setEnabled(this.m_canBeFinished);
            this.m_finishButton.addActionListener(this.onFinishClick, 0, true);
            if (this.m_buttonPanel != null)
            {
                this.m_buttonPanel.append(this.m_finishButton);
                ASwingHelper.prepare(this.m_buttonPanel);
            }
            return;
        }//end

        public void  setGateType (String param1 )
        {
            this.m_gateType = param1;
            _loc_2 = this.m_gateType==AbstractGate.PRE_BUILD_GATE? (ZLoc.t("Dialogs", "StartBuilding")) : (ZLoc.t("Dialogs", "FinishBuilding"));
            this.setFinishButtonText(_loc_2);
            return;
        }//end

    }




