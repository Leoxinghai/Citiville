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
import Display.*;
import Display.DialogUI.*;
import Display.aswingui.*;
import Display.aswingui.inline.*;
import Display.aswingui.inline.style.*;
import Events.*;
import Modules.stats.experiments.*;
//import flash.display.*;
//import flash.events.*;

//import flash.text.TextFormat;
//import flash.text.TextFormatAlign;
import org.aswing.*;
import org.aswing.ext.*;

    public class CrewGateDialog extends GateDialog
    {
        protected Component m_dialogText ;
        protected String m_instructions ;
        protected boolean m_enableVolumeDiscountVariant =false ;
        protected String m_buildingName =null ;
        protected Function m_buyAllHandler =null ;
        protected boolean m_flagDiscountInfoPanel ;
        protected JPanel m_discountInfoPanel ;
        protected JLabel m_discountInfoLabel ;
public static  int LIST_COLS =2;
public static  int LIST_ROWS =3;

        public  CrewGateDialog (String param1 ,Array param2 ,GridListCellFactory param3 ,String param4 ,String param5 ,Function param6 ,Function param7 ,int param8 =3,double param9 =395,boolean param10 =true ,String param11 =null ,Function param12 =null )
        {
            _loc_13 = Global.experimentManager.getVariant(ExperimentDefinitions.EXPERIMENT_CREW_VOLUME_DISCOUNT);
            this.m_enableVolumeDiscountVariant = ExperimentDefinitions.EXPERIMENT_CREW_VOLUME_DISCOUNT_VARIANT3 == _loc_13;
            this.m_instructions = param1;
            this.m_buildingName = param11;
            this.m_buyAllHandler = param12;
            super(param2, param3, param4, param5, param6, param7, this.m_buyAllHandler, param8, KeyCell.CELL_WIDTH * param8, param9, param10);
            return;
        }//end

         protected void  onBuyAll (Event event )
        {
            Object crewData ;
            e = event;
            crewData = this.getCrewData();
            UI .displayMessage (ZLoc .t ("Dialogs","CrewCashConfirm",{crewData crewAmount .positionsOpen ,crewData cashAmount .totalCost }),GenericDialogView .TYPE_YESNO ,void  (GenericPopupEvent event )
            {
                if (event.button == GenericDialogView.YES)
                {
                    if (m_buyAllHandler(crewData.listData))
                    {
                        rebuildCellsAfterFillAll();
                    }
                }
                return;
            }//end
            , "", true);
            return;
        }//end

        protected void  rebuildCellsAfterFillAll ()
        {
            CrewKeyCell _loc_7 =null ;
            _loc_1 = (ASwingScrollingList)m_list
            _loc_2 = _loc_1.dataList;
            _loc_3 = _loc_2.getCells().getSize();
            Object _loc_4 =new Object ();
            _loc_5 = Global.player.findFriendById("-1");
            int _loc_6 =0;
            while (_loc_6 < _loc_3)
            {

                _loc_7 =(CrewKeyCell) _loc_2.getCellByIndex(_loc_6).getCellComponent();
                _loc_4 = _loc_7.getCrewKeyCellValue();
                if (!isNaN(_loc_4.count) && _loc_4.count == 0)
                {
                    _loc_4.count = 1;
                    _loc_4.footerText = _loc_5.firstName;
                    _loc_4.firstName = _loc_5.firstName;
                    _loc_4.url = _loc_5.snUser.picture;
                    _loc_4.ImageUrl = _loc_5.snUser.picture;
                    _loc_7.setCrewKeyCellValue(_loc_4);
                }
                _loc_7.performUpdate();
                _loc_6++;
            }
            this.activateFinishButton();
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
            }
            m_finishCB();
            return;
        }//end

        protected Component  createDialogText ()
        {
            Container _loc_1 =null ;
            Component _loc_2 =null ;
            if (this.m_instructions)
            {
                _loc_1 = new JPanel(new FlowLayout(FlowLayout.CENTER));
                _loc_2 = ASwingHelper.makeMultilineText(this.m_instructions, 500, EmbeddedArt.defaultFontNameBold, TextFormatAlign.CENTER, 18, EmbeddedArt.brownTextColor);
                _loc_1.append(_loc_2);
            }
            return _loc_1;
        }//end

        protected Object  getCrewData ()
        {
            double _loc_3 =0;
            int _loc_8 =0;
            GridList _loc_9 =null ;
            Array _loc_10 =null ;
            CrewKeyCell _loc_11 =null ;
            Object _loc_1 =new Object ();
            _loc_2 = (ASwingScrollingList)m_list
            double _loc_4 =0;
            _loc_1.isInThreshold = false;
            if (_loc_2 == null)
            {
                _loc_3 = m_listData.length;
                _loc_8 = 0;
                while (_loc_8 < _loc_3)
                {

                    if (m_listData.get(_loc_8).count == 0)
                    {
                        _loc_4 = _loc_4 + 1;
                    }
                    _loc_8++;
                }
            }
            else
            {
                _loc_9 = _loc_2.dataList;
                _loc_3 = _loc_9.getCells().getSize();
                _loc_10 = new Array();
                _loc_8 = 0;
                while (_loc_8 < _loc_3)
                {

                    _loc_11 =(CrewKeyCell) _loc_9.getCellByIndex(_loc_8).getCellComponent();
                    _loc_10.put(_loc_8,  _loc_11.getCrewKeyCellValue());
                    if (_loc_10.get(_loc_8).count == 0)
                    {
                        _loc_4 = _loc_4 + 1;
                    }
                    _loc_8++;
                }
                _loc_1.listData = _loc_10;
            }
            _loc_5 = _loc_3;
            _loc_6 = CrewGate.getCrewCost(this.m_buildingName);
            _loc_7 = CrewGate.getCrewCost(this.m_buildingName)*_loc_4;
            if (_loc_4 / _loc_5 >= CrewGate.BUY_ALL_CREW_DISCOUNT_THRESHOLD)
            {
                _loc_1.isInThreshold = true;
                _loc_7 = Math.floor(_loc_7 * CrewGate.BUY_ALL_DISCOUNT_RATE);
            }
            _loc_1.positionsOpen = _loc_4;
            _loc_1.totalCost = _loc_7;
            return _loc_1;
        }//end

         protected Container  createButtonPanel ()
        {
            IASwingButton _loc_1 =null ;
            IASwingButton _loc_2 =null ;
            Object _loc_5 =null ;
            AssetIcon _loc_6 =null ;
            if (m_finishCheckCB())
            {
                _loc_1 = swing.button("FinishBuilding", {"skin-class":"GreenButtonUI"}).enable(m_canBeFinished);
            }
            else
            {
                if (this.m_enableVolumeDiscountVariant)
                {
                    _loc_5 = this.getCrewData();
                    _loc_6 = new AssetIcon(new (DisplayObject)EmbeddedArt.icon_cash());
                    if (_loc_5.positionsOpen > 1)
                    {
                        _loc_2 = swing.button("BuyAllForX", {icon:_loc_6, "skin-class":"BigCashButtonUI", "text-transform":FontStyle.SMALLCAPS}).replacements({cash:_loc_5.totalCost});
                    }
                }
                _loc_1 = swing.button("HireFriendsButtonLabel", {"border-style":BorderStyle.EMPTY, "skin-class":"GreenButtonUI", "text-transform":FontStyle.SMALLCAPS});
            }
            _loc_3 = swing.panel().layout(swing.layout.horizontal.gap(10).align(SoftBoxLayout.CENTER));
            _loc_4 = (Container)_loc_3.add(_loc_2).add(_loc_1).component
            m_finishButton =(CustomButton) _loc_1.component;
            m_finishButton.addActionListener(this.onFinishClick, 0, true);
            if (_loc_2)
            {
                m_buyAllButton =(CustomButton) _loc_2.component;
                m_buyAllButton.addActionListener(this.onBuyAll, 0, true);
            }
            return _loc_4;
        }//end

         protected void  layoutComponents ()
        {
            Object _loc_3 =null ;
            DisplayObject _loc_1 =(DisplayObject)new assets.get( "buildables_bg");
            MarginBackground _loc_2 =new MarginBackground(_loc_1 ,new Insets(0,0,10,0));
            m_jpanel.setBackgroundDecorator(_loc_2);
            m_jpanel.append(m_headerPanel);
            if (this.m_dialogText)
            {
                m_jpanel.append(this.m_dialogText);
            }
            m_jpanel.append(m_listPanel);
            if (this.m_enableVolumeDiscountVariant)
            {
                _loc_3 = this.getCrewData();
                if (_loc_3.positionsOpen > 1 && _loc_3.isInThreshold && m_listData.length < 9)
                {
                    m_jpanel.append(ASwingHelper.verticalStrut(1));
                    m_jpanel.append(this.createDiscountInfoPanel());
                    m_jpanel.append(ASwingHelper.verticalStrut(1));
                    this.m_flagDiscountInfoPanel = true;
                }
                this.addEventListener("update_discount", this.updateDiscountInfo, false, 0, true);
            }
            m_jpanel.append(m_buttonPanel);
            return;
        }//end

         protected Component  createListPanel ()
        {
            JPanel _loc_1 =new JPanel(new FlowLayout(FlowLayout.CENTER ));
            m_list = createList();
            _loc_1.append(m_list);
            return _loc_1;
        }//end

        protected JPanel  createDiscountInfoPanel ()
        {
            _loc_1 = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.CENTER);
            _loc_2 = ZLoc.t("Dialogs","DiscountLabel",{amount20});
            this.m_discountInfoLabel = new JLabel(_loc_2);
            this.m_discountInfoLabel.setFont(ASwingHelper.getTitleFont(16));
            this.m_discountInfoLabel.setForeground(new ASColor(EmbeddedArt.lightOrangeTextColor));
            _loc_1.append(this.m_discountInfoLabel);
            this.m_discountInfoPanel = _loc_1;
            return _loc_1;
        }//end

        protected void  updateDiscountInfo (Object param1)
        {
            String _loc_3 =null ;
            _loc_2 = this.getCrewData();
            if (_loc_2.positionsOpen <= 1 || !_loc_2.isInThreshold)
            {
                if (this.m_flagDiscountInfoPanel)
                {
                    m_jpanel.remove(this.m_discountInfoPanel);
                    this.m_flagDiscountInfoPanel = false;
                }
            }
            if (_loc_2.positionsOpen > 1)
            {
                _loc_3 = ZLoc.t("Dialogs", "BuyAllForX", {cash:_loc_2.totalCost});
                m_buyAllButton.setText(_loc_3);
                ASwingHelper.prepare(m_buyAllButton);
                ASwingHelper.prepare(this);
            }
            else
            {
                m_buttonPanel.remove(m_buyAllButton);
            }
            return;
        }//end

         protected Component  createTitle ()
        {
            _loc_1 = TextFieldUtil.getLocaleFontSize(30,23,.get({localesize"ja",30)});
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
            m_buttonPanel.remove(m_finishButton);
            m_buttonPanel.remove(m_buyAllButton);
            m_jpanel.remove(this.m_discountInfoPanel);
            this.createButtonPanel();
            m_buttonPanel.append(m_finishButton);
            ASwingHelper.prepare(m_buttonPanel);
            return;
        }//end

         protected void  createDialog ()
        {
            this.m_dialogText = this.createDialogText();
            super.createDialog();
            return;
        }//end

    }




