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
import Modules.stats.experiments.*;
//import flash.display.*;
//import flash.events.*;
//import flash.text.*;
//import flash.utils.*;
import org.aswing.*;
import org.aswing.border.*;
import org.aswing.ext.*;
import org.aswing.geom.*;

    public class CrewGateDialog_2 extends GateDialog
    {
        protected CrewScrollingList m_crew ;
        protected Component m_dialogText ;
        protected String m_instructions ;
        protected int m_numAssetLoads =2;
        protected int m_imagesLoaded =0;
        protected Object m_comTabObject ;
        protected CustomButton m_buyAllButton2 ;
        protected boolean m_enableVolumeDiscountVariant =false ;
        protected Function m_buyAllHandler =null ;
        protected String m_buildingName ;
public static Dictionary crewScrollAssets =new Dictionary ();

        public  CrewGateDialog_2 (String param1 ,Array param2 ,GridListCellFactory param3 ,String param4 ,String param5 ,Function param6 ,Function param7 ,int param8 =3,double param9 =395,boolean param10 =true ,String param11 =null ,Function param12 =null )
        {
            _loc_13 = Global.experimentManager.getVariant(ExperimentDefinitions.EXPERIMENT_CREW_VOLUME_DISCOUNT);
            this.m_enableVolumeDiscountVariant = ExperimentDefinitions.EXPERIMENT_CREW_VOLUME_DISCOUNT_VARIANT3 == _loc_13;
            this.m_instructions = param1;
            this.m_buyAllHandler = param12;
            this.m_buildingName = param11;
            super(param2, param3, param4, param5, param6, param7, null, param8, 615, param9, param10);
            return;
        }//end

         protected void  loadAssets ()
        {
            Global.delayedAssets.get(DelayedAssetLoader.GENERIC_DIALOG_ASSETS, makeAssets);
            Global.delayedAssets.get(DelayedAssetLoader.TABBED_ASSETS, this.makeTabAssets);
            return;
        }//end

        protected void  makeTabAssets (DisplayObject param1 ,String param2 )
        {
            this.m_comTabObject = param1;
            this.onAssetsLoaded();
            return;
        }//end

         protected void  onAssetsLoaded (Event event =null )
        {
            this.m_imagesLoaded++;
            if (this.m_imagesLoaded < this.m_numAssetLoads)
            {
                return;
            }
            crewScrollAssets.put("dialog_bg",  new (DisplayObject)m_comObject.dialog_bg());
            crewScrollAssets.put("cell_bg",  new (DisplayObject)m_comObject.cell_bg());
            crewScrollAssets.put("cell_bg_alt", new (DisplayObject)m_comObject.cell_bg_alt());
            crewScrollAssets.put("police_scrollContainer", this.m_comTabObject.police_scrollContainer);
            crewScrollAssets.put("conventionCenter_pagination_border", this.m_comTabObject.conventionCenter_pagination_border);
            crewScrollAssets.put("conventionCenter_pagination_bottom_disabled", this.m_comTabObject.conventionCenter_pagination_bottom_disabled);
            crewScrollAssets.put("conventionCenter_pagination_bottom_over", this.m_comTabObject.conventionCenter_pagination_bottom_over);
            crewScrollAssets.put("conventionCenter_pagination_bottom_up", this.m_comTabObject.conventionCenter_pagination_bottom_up);
            crewScrollAssets.put("conventionCenter_pagination_dot", this.m_comTabObject.conventionCenter_pagination_dot);
            crewScrollAssets.put("conventionCenter_pagination_top_disabled", this.m_comTabObject.conventionCenter_pagination_top_disabled);
            crewScrollAssets.put("conventionCenter_pagination_top_over", this.m_comTabObject.conventionCenter_pagination_top_over);
            crewScrollAssets.put("conventionCenter_pagination_top_up", this.m_comTabObject.conventionCenter_pagination_top_up);
            crewScrollAssets.put("slot_unfilled", this.m_comTabObject.police_slot_unfilled);
            this.createDialog();
            return;
        }//end

         protected void  onFinishClick (Event event )
        {
            if (m_finishCheckCB())
            {
                countDialogAction("FINISH");
            }
            else
            {
                countDialogAction("ASK");
                this.inviteFriends(event);
            }
            m_finishCB();
            return;
        }//end

        private void  inviteFriends (Event event )
        {
            dispatchEvent(new Event("friendClicked", true));
            return;
        }//end

        protected void  onBuyClick (Event event )
        {
            Player _loc_4 =null ;
            Object _loc_5 =null ;
            String _loc_6 =null ;
            _loc_2 = this.nextHireIndex;
            if (_loc_2 == -1)
            {
                return;
            }
            m_listData.get(_loc_3 = this.nextHireIndex).buyCallback(this.nextHireCost );
            if (_loc_3)
            {
                _loc_4 = Global.player.findFriendById("-1");
                _loc_7 = m_listData.get(_loc_2);
                _loc_8 = m_listData.get(_loc_2).count +1;
                _loc_7.count = _loc_8;
                m_listData.get(_loc_2).url = _loc_4.snUser.picture;
                m_listData.get(_loc_2).footerText = _loc_4.firstName;
                m_listData.get(_loc_2).picUrl = m_listData.get(_loc_2).url;
                m_listData.get(_loc_2).friendName = m_listData.get(_loc_2).footerText;
                this.m_crew.updateCell(_loc_2, m_listData.get(_loc_2));
            }
            if (m_finishCheckCB())
            {
                this.activateFinishButton();
            }
            else
            {
                _loc_5 = this.getSaleCost();
                if (_loc_5.positionsOpen > 1)
                {
                    _loc_6 = ZLoc.t("Dialogs", "BuyAllForX", {cash:_loc_5.cost});
                    m_buyAllButton.setText(_loc_6);
                    ASwingHelper.prepare(m_buyAllButton);
                }
                else
                {
                    m_buttonPanel.remove(m_buyAllButton);
                }
            }
            return;
        }//end

        protected Component  createDialogText ()
        {
            Container _loc_1 =null ;
            Component _loc_2 =null ;
            if (this.m_instructions)
            {
                _loc_1 = new JPanel(new FlowLayout(FlowLayout.CENTER));
                _loc_2 = ASwingHelper.makeMultilineText(this.m_instructions, 500, EmbeddedArt.defaultFontNameBold, TextFormatAlign.CENTER, TextFieldUtil.getLocaleFontSize(18, 18, [{locale:"ja", size:16}]), EmbeddedArt.brownTextColor);
                _loc_1.append(_loc_2);
            }
            return _loc_1;
        }//end

        protected int  nextHireIndex ()
        {
            int _loc_1 =0;
            while (_loc_1 < m_listData.length())
            {

                if (m_listData.get(_loc_1).count < m_listData.get(_loc_1).amountNeeded)
                {
                    return _loc_1;
                }
                _loc_1++;
            }
            return -1;
        }//end

        protected int  nextHireCost ()
        {
            return m_listData.get(this.nextHireIndex).cost;
        }//end

         protected Container  createButtonPanel ()
        {
            EmptyBorder _loc_2 =null ;
            AssetIcon _loc_3 =null ;
            AssetIcon _loc_4 =null ;
            Object _loc_5 =null ;
            String _loc_6 =null ;
            JPanel _loc_1 =new JPanel(new SoftBoxLayout(SoftBoxLayout.X_AXIS ,10,SoftBoxLayout.CENTER ));
            if (m_finishCheckCB())
            {
                m_finishButton = new CustomButton(TextFieldUtil.formatSmallCapsString(ZLoc.t("Dialogs", "FinishBuilding")), null, "GreenButtonUI");
                m_finishButton.setEnabled(m_canBeFinished);
                m_finishButton.addActionListener(this.onFinishClick, 0, true);
                _loc_1.append(m_finishButton);
            }
            else
            {
                _loc_3 = new AssetIcon(new (DisplayObject)EmbeddedArt.icon_cash_big());
                m_finishButton = new CustomButton(TextFieldUtil.formatSmallCapsString(ZLoc.t("Dialogs", "HireFriendsButtonLabel")), null, "GreenButtonUI");
                m_finishButton.addActionListener(this.onFinishClick, 0, true);
                _loc_2 = new EmptyBorder(null, new Insets(0, 0, 0, 0));
                _loc_4 = new AssetIcon(new (DisplayObject)EmbeddedArt.icon_cash());
                this.m_buyAllButton2 = new CustomButton(TextFieldUtil.formatSmallCapsString(ZLoc.t("Dialogs", "BuyCrewButtonLabel", {amount:String(this.nextHireCost)})), _loc_4, "CashButtonUI");
                this.m_buyAllButton2.setMargin(new Insets(3, 10, 3, 10));
                this.m_buyAllButton2.setFont(new ASFont(EmbeddedArt.titleFont, 20, false, false, false, EmbeddedArt.getAdvancedFontProps(EmbeddedArt.titleFont)));
                this.m_buyAllButton2.addActionListener(this.onBuyClick, 0, true);
                _loc_1.append(m_finishButton);
                _loc_1.append(this.m_buyAllButton2);
                ASwingHelper.prepare(this.m_buyAllButton2);
                if (this.m_enableVolumeDiscountVariant)
                {
                    _loc_5 = this.getSaleCost();
                    if (_loc_5.positionsOpen > 1)
                    {
                        _loc_6 = ZLoc.t("Dialogs", "BuyAllForX", {cash:_loc_5.cost});
                        m_buyAllButton = new CustomButton(TextFieldUtil.formatSmallCapsString(_loc_6), _loc_4, "BigCashButtonUI");
                        m_buyAllButton.addActionListener(this.onBuyAll, 0, true);
                        _loc_1.append(m_buyAllButton);
                    }
                }
            }
            if (_loc_2)
            {
                _loc_1.setBorder(_loc_2);
            }
            ASwingHelper.prepare(m_buttonPanel);
            return _loc_1;
        }//end

         protected void  onBuyAll (Event event )
        {
            e = event;
            saleCost = this.getSaleCost();
            UI .displayMessage (ZLoc .t ("Dialogs","CrewCashConfirm",{saleCost crewAmount .positionsOpen ,saleCost cashAmount .cost }),GenericDialogView .TYPE_YESNO ,void  (GenericPopupEvent event )
            {
                if (event.button == GenericDialogView.YES)
                {
                    m_buyAllHandler(m_listData);
                    rebuildCellsAfterFillAll();
                }
                return;
            }//end
            , "", true);
            return;
        }//end

        protected Object  getSaleCost ()
        {
            Object _loc_1 =new Object ();
            _loc_2 = CrewGate.getFilledPositions(m_listData);
            _loc_3 = m_listData.length;
            _loc_4 = _loc_3-_loc_2 ;
            _loc_5 = CrewGate.getCrewCost(this.m_buildingName);
            _loc_1.cost = _loc_5 * _loc_4;
            if (_loc_4 / _loc_3 >= CrewGate.BUY_ALL_CREW_DISCOUNT_THRESHOLD)
            {
                _loc_1.cost = Math.floor(_loc_1.cost * CrewGate.BUY_ALL_DISCOUNT_RATE);
            }
            _loc_1.positionsOpen = _loc_4;
            return _loc_1;
        }//end

        protected void  rebuildCellsAfterFillAll ()
        {
            Player _loc_3 =null ;
            _loc_1 = m_listData.length;
            double _loc_2 =0;
            while (_loc_2 < _loc_1)
            {

                if (m_listData.get(_loc_2).count < m_listData.get(_loc_2).amountNeeded)
                {
                    _loc_3 = Global.player.findFriendById("-1");
                    m_listData.get(_loc_2).count = 1;
                    m_listData.get(_loc_2).url = _loc_3.snUser.picture;
                    m_listData.get(_loc_2).footerText = _loc_3.firstName;
                    m_listData.get(_loc_2).picUrl = m_listData.get(_loc_2).url;
                    m_listData.get(_loc_2).friendName = m_listData.get(_loc_2).footerText;
                    this.m_crew.updateCell(_loc_2, m_listData.get(_loc_2));
                }
                _loc_2 = _loc_2 + 1;
            }
            return;
        }//end

         protected void  layoutComponents ()
        {
            _loc_1 =(DisplayObject) crewScrollAssets.get("dialog_bg");
            MarginBackground _loc_2 =new MarginBackground(_loc_1 ,new Insets(0,0,10,0));
            m_jpanel.setBackgroundDecorator(_loc_2);
            m_jpanel.append(m_headerPanel);
            if (this.m_dialogText)
            {
                m_jpanel.append(this.m_dialogText);
            }
            m_jpanel.append(m_listPanel);
            m_jpanel.append(m_buttonPanel);
            IntDimension _loc_3 =new IntDimension(680,515);
            m_jpanel.setMinimumSize(_loc_3);
            m_jpanel.setPreferredSize(_loc_3);
            m_jpanel.setMaximumSize(_loc_3);
            ASwingHelper.prepare(m_jpanel);
            return;
        }//end

         protected Component  createListPanel ()
        {
            _loc_1 = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.CENTER,10);
            _loc_2 = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.TOP);
            IntDimension _loc_3 =new IntDimension(600,370);
            _loc_2.setMinimumSize(_loc_3);
            _loc_2.setPreferredSize(_loc_3);
            _loc_2.setMaximumSize(_loc_3);
            this.m_crew = new CrewScrollingList(m_listData);
            ASwingHelper.prepare(this.m_crew);
            _loc_2.append(this.m_crew);
            ASwingHelper.prepare(_loc_2);
            _loc_1.appendAll(ASwingHelper.horizontalStrut(20), _loc_2);
            ASwingHelper.prepare(_loc_1);
            return _loc_1;
        }//end

         protected Component  createTitle ()
        {
            _loc_1 = TextFieldUtil.getLocaleFontSize(30,23,.get({localesize"ja",26)});
            _loc_2 = ASwingHelper.makeTextField(m_title,EmbeddedArt.titleFont,_loc_1,EmbeddedArt.titleColor);
            _loc_2.filters = EmbeddedArt.titleFilters;
            TextFormat _loc_3 =new TextFormat ();
            _loc_3.size = _loc_1 + 6;
            TextFieldUtil.formatSmallCaps(_loc_2.getTextField(), _loc_3);
            _loc_2.getTextField().height = 45;
            return _loc_2;
        }//end

         public void  activateFinishButton ()
        {
            m_canBeFinished = true;
            if (this.m_enableVolumeDiscountVariant)
            {
                m_buttonPanel.remove(this.m_buyAllButton2);
            }
            m_buttonPanel.remove(m_finishButton);
            m_buttonPanel.remove(m_buyAllButton);
            m_buttonPanel = this.createButtonPanel();
            m_buttonPanel.append(m_finishButton);
            ASwingHelper.prepare(m_buttonPanel);
            this.layoutComponents();
            return;
        }//end

         protected void  createDialog ()
        {
            this.m_dialogText = this.createDialogText();
            super.createDialog();
            return;
        }//end

        public static Dictionary  assetDict ()
        {
            return crewScrollAssets;
        }//end

    }




