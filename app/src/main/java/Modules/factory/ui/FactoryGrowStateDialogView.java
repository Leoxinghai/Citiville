package Modules.factory.ui;

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
import Classes.inventory.*;
import Classes.util.*;
import Display.*;
import Display.DialogUI.*;
import Display.aswingui.*;
import Events.*;
import com.zynga.skelly.util.*;
//import flash.events.*;
//import flash.text.*;
//import flash.utils.*;
import org.aswing.*;
import org.aswing.border.*;
import org.aswing.ext.*;
import org.aswing.geom.*;

    public class FactoryGrowStateDialogView extends GenericDialogView
    {
        protected boolean m_modelIsDirty =true ;
        protected boolean m_disableButtons =false ;
        protected boolean m_suppressConfirm =false ;
        protected VectorListModel m_model ;
        protected CrewCellFactory m_cellFactory ;
        protected Function m_updateTextCallback ;
        protected Function m_updateTimerCallback ;
        protected Function m_updateGoodsCallback ;
        protected Function m_updateWorkersCallback ;
        protected Function m_updateButtonsCallback ;
public static  int BUTTON_MARGIN =10;
public static  int LIST_COLS =2;
public static  int LIST_ROWS =3;

        public  FactoryGrowStateDialogView (Array param1 ,CrewCellFactory param2 ,Dictionary param3 ,String param4 ="",String param5 ="",int param6 =0,Function param7 =null ,String param8 ="",int param9 =0,String param10 ="",Function param11 =null ,String param12 ="")
        {
            this.refreshModel(param1);
            this.m_cellFactory = param2;
            super(param3, param4, param5, param6, param7, param8, param9, param10, param11, param12);
            return;
        }//end

        public void  update (Factory param1)
        {
            dispatchEvent(new GenericObjectEvent(UIEvent.REFRESH_DIALOG, param1));
            return;
        }//end

        public Function  updateText ()
        {
            return this.m_updateTextCallback;
        }//end

        public Function  updateTimer ()
        {
            return this.m_updateTimerCallback;
        }//end

        public Function  updateGoods ()
        {
            return this.m_updateGoodsCallback;
        }//end

        public Function  updateWorkers ()
        {
            return this.m_updateWorkersCallback;
        }//end

        public Function  updateButtons ()
        {
            return this.m_updateButtonsCallback;
        }//end

         protected void  makeBackground ()
        {
            MarginBackground _loc_1 =null ;
            if (m_bgAsset)
            {
                _loc_1 = new MarginBackground(m_bgAsset, new Insets(0, 0, 10, 0));
                this.setBackgroundDecorator(_loc_1);
                this.setPreferredSize(new IntDimension(m_bgAsset.width, m_bgAsset.height));
                this.setMinimumSize(new IntDimension(m_bgAsset.width, m_bgAsset.height));
                this.setMaximumSize(new IntDimension(m_bgAsset.width, m_bgAsset.height));
            }
            return;
        }//end

         protected JPanel  createInteriorHolder ()
        {
            container = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.TOP,-10);
            headerPanel = createHeaderPanel();
            topPanel = this.createTopPanel();
            bottomPanel = this.createBottomPanel();
            buttonPanel = this.createButtonPanel();
            buttonAltPanel = this.createButtonAltPanel();
            container.setPreferredSize(new IntDimension(m_bgAsset.width, m_bgAsset.height));
            container.setMinimumSize(new IntDimension(m_bgAsset.width, m_bgAsset.height));
            container.setMaximumSize(new IntDimension(m_bgAsset.width, m_bgAsset.height));
            container.setBorder(new EmptyBorder(null, new Insets(0, 0, 0, 0)));
            container.append(headerPanel);
            container.append(ASwingHelper.verticalStrut(20));
            int verticalStrutHeight ;
            if (Global.localizer.langCode == "ja")
            {
                verticalStrutHeight;
            }
            container.appendAll(topPanel, ASwingHelper.verticalStrut(verticalStrutHeight), bottomPanel);
            container.append(ASwingHelper.verticalStrut(BUTTON_MARGIN));
            container.append(buttonPanel);
            addEventListener (UIEvent .REFRESH_DIALOG ,Curry .curry (void  (JPanel param1 ,JPanel param2 ,JPanel param3 ,GenericObjectEvent param4 )
            {
                _loc_5 = null;
                if (param4.obj instanceof Factory)
                {
                    _loc_5 = Global.factoryWorkerManager.getWorkers((param4.obj as Factory).getWorkerBucket()).getRemainingSpots();
                    if (_loc_5 == 0)
                    {
                        param1.remove(param2);
                        param1.append(param3);
                        ASwingHelper.prepare(param1);
                    }
                }
                return;
            }//end
            , container, buttonPanel, buttonAltPanel));
            return container;
        }//end

         protected JPanel  createButtonPanel ()
        {
            container = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.CENTER);
            btnHireFriendsText = m_assetDict.stringAssets.hireFriendsButton;
            btnBuyWorkerText = m_assetDict.stringAssets.buyWorkerButton;
            AssetIcon buyIcon =new AssetIcon(m_assetDict.get( "pic_cashBtn") );
            CustomButton btnHireFriends =new CustomButton(btnHireFriendsText.toUpperCase (),null ,"GreenButtonUI");
            btnHireFriends.addActionListener(this.onHireFriends, 0, true);
            CustomButton btnBuyWorker =new CustomButton(btnBuyWorkerText.toUpperCase (),buyIcon ,"BigCashButtonUI");
            btnBuyWorker.addActionListener(this.onBuyWorker, 0, true);
            container.appendAll(btnHireFriends, ASwingHelper.horizontalStrut(15), btnBuyWorker);
            addEventListener (UIEvent .REFRESH_DIALOG ,Curry .curry (void  (CustomButton param1 ,CustomButton param2 ,FactoryGrowStateDialogView param3 ,GenericObjectEvent param4 =null )
            {
                param1.setEnabled(!param3.m_disableButtons);
                param2.setEnabled(!param3.m_disableButtons);
                return;
            }//end
            , btnHireFriends, btnBuyWorker, this));
            return container;
        }//end

        protected JPanel  createButtonAltPanel ()
        {
            _loc_1 = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.CENTER );
            _loc_2 = m_assetDict.stringAssets.completeButton;
            CustomButton _loc_3 =new CustomButton(_loc_2.toUpperCase (),null ,"GreenButtonUI");
            _loc_3.addEventListener(MouseEvent.CLICK, this.onOkClick, false, 0, true);
            _loc_1.append(_loc_3);
            return _loc_1;
        }//end

        protected JPanel  createTopPanel ()
        {
            _loc_1 = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.LEFT );
            _loc_1.setPreferredHeight(155);
            _loc_2 = this.createJobImagePanel ();
            _loc_3 = this.createTextArea ();
            _loc_4 = this.createJobInfoPanel ();
            _loc_1.appendAll(ASwingHelper.horizontalStrut(20), _loc_2, ASwingHelper.horizontalStrut(9), _loc_3, _loc_4);
            return _loc_1;
        }//end

        protected JPanel  createBottomPanel ()
        {
            container = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.CENTER);
            alignContainer = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.CENTER);
            GridList gridlist =new GridList(this.m_model ,this.m_cellFactory ,LIST_COLS ,LIST_ROWS );
            container.setPreferredHeight(190);
            gridlist.setPreferredWidth(680);
            gridlist.setPreferredHeight(160);
            gridlist.setSelectable(false);
            gridlist.setTileWidth(this.m_cellFactory.preferredCellWidth);
            gridlist.setTileHeight(this.m_cellFactory.preferredCellHeight);
            alignContainer.append(gridlist);
            container.append(alignContainer);
            addEventListener (UIEvent .REFRESH_DIALOG ,Curry .curry (void  (GenericObjectEvent event )
            {
                if (event.obj instanceof Factory)
                {
                    refreshModel((event.obj as Factory).getWorkerData());
                }
                return;
            }//end
            ));
            return container;
        }//end

        protected JPanel  createJobImagePanel ()
        {
            _loc_1 = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.BOTTOM );
            _loc_1.setPreferredSize(new IntDimension(152, 150));
            AssetPane _loc_2 =new AssetPane(m_assetDict.get( "pic_contract") );
            _loc_1.append(_loc_2);
            _loc_1.append(ASwingHelper.verticalStrut(11));
            return _loc_1;
        }//end

         protected JPanel  createTextArea ()
        {
            container = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.TOP);
            container.setPreferredSize(new IntDimension(350, 150));
            ASwingHelper.setEasyBorder(container, 15, 5, 20, 5);
            ASwingHelper.prepare(container);
            txtJobName = ASwingHelper.makeTextField(m_assetDict.stringAssets.jobTitle,EmbeddedArt.defaultFontNameBold,18,EmbeddedArt.darkBrownTextColor);
            txtJobDesc = ASwingHelper.makeMultilineText(m_assetDict.stringAssets.jobDescription,container.getPreferredWidth()-15,EmbeddedArt.defaultFontNameBold,TextFormatAlign.LEFT,14,EmbeddedArt.darkBlueTextColor);
            txtNote = ASwingHelper.makeLabel(m_assetDict.stringAssets.unlockWorkerNote,EmbeddedArt.defaultFontName,12,EmbeddedArt.redTextColor,JLabel.LEFT);
            container.appendAll(txtJobName, txtJobDesc);
            container.append(ASwingHelper.verticalStrut(15));
            container.append(txtNote);
            addEventListener (UIEvent .REFRESH_DIALOG ,Curry .curry (void  (AssetPane param1 ,GenericObjectEvent param2 )
            {
                _loc_3 = null;
                if (param2.obj instanceof Factory)
                {
                    _loc_3 = (param1.getAsset() as TextField).getTextFormat();
                    (param1.getAsset() as TextField).text = getContractDescription(param2.obj as Factory);
                    (param1.getAsset() as TextField).setTextFormat(_loc_3);
                }
                return;
            }//end
            , txtJobDesc));
            return container;
        }//end

        protected JPanel  createJobInfoPanel ()
        {
            container = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.CENTER);
            timePanel = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.CENTER);
            goodsPanel = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.CENTER);
            container.setPreferredWidth(175);
            timePanel.setPreferredWidth(175);
            goodsPanel.setPreferredWidth(175);
            AssetPane picTimer =new AssetPane(m_assetDict.get( "pic_timer") );
            txtTimer = ASwingHelper.makeTextField("",EmbeddedArt.titleFont,20,EmbeddedArt.darkBlueTextColor);
            AssetPane picGoods =new AssetPane(m_assetDict.get( "pic_goods") );
            txtGoods = ASwingHelper.makeTextField("",EmbeddedArt.titleFont,20,EmbeddedArt.greenTextColor);
            txtTimer.setPreferredWidth(150);
            txtGoods.setPreferredWidth(150);
            addEventListener (UIEvent .REFRESH_DIALOG ,Curry .curry (void  (JTextField param1 ,GenericObjectEvent param2 )
            {
                if (param2.obj instanceof Factory)
                {
                    param1.setText(GameUtil.formatMinutesSeconds((param2.obj as Factory).getGrowTimeLeft()));
                }
                return;
            }//end
            , txtTimer));
            addEventListener (UIEvent .REFRESH_DIALOG ,Curry .curry (void  (JTextField param1 ,GenericObjectEvent param2 )
            {
                _loc_3 = null;
                _loc_4 = null;
                if (param2.obj instanceof Factory)
                {
                    _loc_3 = Global.player.GetDooberMinimums((param2.obj as Factory).harvestingDefinition, Commodities.PREMIUM_GOODS_COMMODITY);
                    _loc_4 = _loc_3 * ItemBonus.getBonusModifier(param2.obj as Factory, ItemBonus.PREMIUM_GOODS_YIELD);
                    param1.setText(_loc_4.toString());
                }
                return;
            }//end
            , txtGoods));
            timePanel.appendAll(ASwingHelper.horizontalStrut(15), picTimer, txtTimer);
            goodsPanel.appendAll(ASwingHelper.horizontalStrut(15), picGoods, txtGoods);
            container.appendAll(timePanel, ASwingHelper.verticalStrut(5), goodsPanel);
            return container;
        }//end

        protected void  refreshModel (Array param1 )
        {
            int _loc_2 =0;
            int _loc_3 =0;
            int _loc_4 =0;
            String _loc_5 =null ;
            if (this.m_modelIsDirty)
            {
                if (this.m_model == null)
                {
                    this.m_model = new VectorListModel();
                }
                this.m_model.clear();
                _loc_2 = 0;
                while (_loc_2 < param1.length())
                {

                    _loc_3 = _loc_2 / LIST_COLS % 2;
                    _loc_4 = _loc_2 % LIST_COLS % 2;
                    _loc_5 = (_loc_3 + _loc_4) % 2 == 0 ? ("cell_bg") : ("cell_bg_alt");
                    this.m_model.append({id:param1.get(_loc_2).id, friendName:param1.get(_loc_2).friendName, position:param1.get(_loc_2).position, picUrl:param1.get(_loc_2).picUrl, bgAsset:_loc_5});
                    _loc_2++;
                }
                this.m_modelIsDirty = false;
            }
            return;
        }//end

        protected void  onHireFriends (Event event =null )
        {
            this.dispatchEvent(new UIEvent(UIEvent.ASK_FOR_CREW, "", false, true));
            return;
        }//end

        protected void  onBuyWorker (Event event =null )
        {
            e = event;
            msg = m_assetDict.stringAssets.unlockWorkerQuestion+" "+m_assetDict.stringAssets.unlockWorkerNote;
            if (this.m_suppressConfirm)
            {
                this.dispatchEvent(new UIEvent(UIEvent.BUY_CREW, "", false, true));
                this.m_modelIsDirty = true;
            }
            else
            {
                this.m_disableButtons = true;
                UI .displayMessage (msg ,GenericDialogView .TYPE_YESNO ,Curry .curry (void  (FactoryGrowStateDialogView param1 ,GenericPopupEvent param2 )
            {
                if (param2.button == GenericDialogView.YES)
                {
                    param1.dispatchEvent(new UIEvent(UIEvent.BUY_CREW, "", false, true));
                    param1.m_modelIsDirty = true;
                    param1.m_suppressConfirm = true;
                }
                m_disableButtons = false;
                update();
                return;
            }//end
            , this), "", true);
            }
            this.update();
            return;
        }//end

        protected String  getContractDescription (Factory param1 )
        {
            _loc_2 =Global.factoryWorkerManager.getWorkers(param1.getWorkerBucket ()).getRemainingSpots ();
            if (_loc_2 > 0)
            {
                return ZLoc.t("Items", param1.harvestingDefinition.name + "_description", {workersLeft:_loc_2, pos:ZLoc.tk("Items", "worker_position_friendlyName", "", _loc_2)});
            }
            return ZLoc.t("Items", param1.harvestingDefinition.name + "_complete");
        }//end

        protected void  onOkClick (MouseEvent event =null )
        {
            closeMe();
            return;
        }//end

    }



