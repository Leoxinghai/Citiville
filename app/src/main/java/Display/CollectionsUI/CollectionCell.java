package Display.CollectionsUI;

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
import Display.InventoryUI.*;
import Display.MarketUI.*;
import Display.aswingui.*;
import Events.*;
//import flash.events.*;
//import flash.text.*;
import org.aswing.*;
import org.aswing.ext.*;
import com.xinghai.Debug;

    public class CollectionCell extends JPanel implements GridListCell
    {
        protected Collection m_collection ;
        protected GridList m_gridList ;
        protected int m_index ;
        protected JPanel m_shelf ;
        protected JPanel m_rewardPanel ;
        protected JButton m_tradeInButton ;
        protected boolean m_initialized =false ;
        public static  double COLLECTION_TITLE_FONT_SIZE =18;
        public static  double COLLECTION_CELL_WIDTH =CollectionCatalogUI.COLLECTIONS_TITLE_WIDTH ;
        public static  double COLLECTION_CELL_HEIGHT =Catalog.CARD_HEIGHT +50;
        public static  int COLLECTION_ITEMS_WIDTH =450;
        public static  int COLLECTION_REWARD_WIDTH =COLLECTION_CELL_WIDTH -COLLECTION_ITEMS_WIDTH ;

        public  CollectionCell (LayoutManager param1)
        {
            Debug.debug4("CollectionCell");
            super(new SoftBoxLayout(SoftBoxLayout.Y_AXIS, 0, SoftBoxLayout.CENTER));
            ASwingHelper.prepare(this);
            return;
        }//end

        public void  setGridListCellStatus (GridList param1 ,boolean param2 ,int param3 )
        {
            this.m_gridList = param1;
            this.m_index = param3;
            this.m_gridList.setTileWidth(COLLECTION_CELL_WIDTH);
            this.m_gridList.setTileHeight(COLLECTION_CELL_HEIGHT);
            return;
        }//end

        public void  setCellValue (Object param1)
        {
            this.m_collection = param1;
            this.initializeCell();
            return;
        }//end

        public Object getCellValue ()
        {
            return this.m_collection;
        }//end

        public Component  getCellComponent ()
        {
            return this;
        }//end

        protected void  initializeCell ()
        {
            Debug.debug4("CollectionCell.initializeCell");
            _loc_1 = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.LEFT );
            _loc_2 = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.TOP ,-3);
            _loc_2.append(this.makeTitlePanel());
            _loc_2.append(this.makeItemsPanel());
            ASwingHelper.prepare(_loc_2);
            _loc_1.append(_loc_2);
            _loc_1.append(ASwingHelper.horizontalStrut(-4));
            _loc_1.append(this.makeInfoPanel());
            ASwingHelper.prepare(_loc_1);
            this.append(_loc_1);
            this.setPreferredWidth(COLLECTION_CELL_WIDTH);
            ASwingHelper.prepare(this);
            this.m_initialized = true;
            return;
        }//end

        protected JPanel  makeInfoPanel ()
        {
            _loc_1 = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.BOTTOM );
            _loc_2 = TextFieldUtil.getLocaleFontSize(14,12,[{localesize"de",16locale},{"es",size14},locale{"ja",14});
            this.m_tradeInButton = new CustomButton(ZLoc.t("Dialogs", "CollectionTradeInButton"), null, "GreenButtonUI");
            this.m_tradeInButton.setFont(ASwingHelper.makeFont(EmbeddedArt.titleFont, _loc_2));
            this.m_tradeInButton.setEnabled(this.m_collection.isReadyToTradeIn());
            this.m_tradeInButton.addEventListener(MouseEvent.MOUSE_UP, this.onTradeIn, false, 0, true);
            _loc_1.append(this.makeRewardPanel());
            _loc_1.append(this.m_tradeInButton);
            _loc_1.append(ASwingHelper.verticalStrut(30));
            _loc_1.setPreferredWidth(COLLECTION_REWARD_WIDTH);
            ASwingHelper.prepare(_loc_1);
            return _loc_1;
        }//end

        protected JPanel  makeTitlePanel ()
        {
            _loc_1 = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.TOP );
            _loc_2 = ASwingHelper.makeTextField(this.m_collection.localizedName ,EmbeddedArt.defaultFontNameBold ,COLLECTION_TITLE_FONT_SIZE ,EmbeddedArt.darkBlueTextColor );
            _loc_1.append(_loc_2);
            return _loc_1;
        }//end

        protected JPanel  makeItemsPanel ()
        {
            String _loc_3 =null ;
            Item _loc_4 =null ;
            _loc_1 = this.m_collection.getCollectableNames ();
            Array _loc_2 =new Array ();
            for(int i0 = 0; i0 < _loc_1.size(); i0++)
            {
            	_loc_3 = _loc_1.get(i0);

                _loc_4 = Global.gameSettings().getItemByName(_loc_3);
                if (_loc_4)
                {
                    _loc_2.push(_loc_4);
                }
            }
            this.m_shelf = new InventoryScrollingList(_loc_2, CollectableCellFactory, 0, 5, 1, true);
            this.m_shelf.setPreferredWidth(COLLECTION_ITEMS_WIDTH);
            ASwingHelper.prepare(this.m_shelf);
            return this.m_shelf;
        }//end

        protected JPanel  makeRewardPanel ()
        {
            JTextField _loc_8 =null ;
            JTextField _loc_9 =null ;
            JTextField _loc_10 =null ;
            Object _loc_11 =null ;
            JTextField _loc_12 =null ;
            String _loc_13 =null ;
            String _loc_14 =null ;
            AssetPane _loc_15 =null ;
            String _loc_16 =null ;
            String _loc_17 =null ;
            AssetPane _loc_18 =null ;
            String _loc_19 =null ;
            String _loc_20 =null ;
            AssetPane _loc_21 =null ;
            if (!this.m_rewardPanel)
            {
                this.m_rewardPanel = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.CENTER);
            }
            _loc_1 = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.CENTER );
            _loc_2 = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.CENTER );
            double _loc_3 =120;
            if (this.m_collection.rewardCoins)
            {
                _loc_8 = ASwingHelper.makeTextField(ZLoc.t("Collections", "CollectionsRewardCoins", {coins:this.m_collection.rewardCoins}) + " ", EmbeddedArt.defaultFontNameBold, 12, 2645358);
                this.m_rewardPanel.append(_loc_8);
            }
            if (this.m_collection.rewardEnergy)
            {
                _loc_9 = ASwingHelper.makeTextField(ZLoc.t("Collections", "CollectionsRewardEnergy", {energy:this.m_collection.rewardEnergy}) + " ", EmbeddedArt.defaultFontNameBold, 12, 2645358);
                this.m_rewardPanel.append(_loc_9);
            }
            if (this.m_collection.rewardXp)
            {
                _loc_10 = ASwingHelper.makeTextField(ZLoc.t("Collections", "CollectionsRewardXP", {xp:this.m_collection.rewardXp}) + " ", EmbeddedArt.defaultFontNameBold, 12, 2645358);
                this.m_rewardPanel.append(_loc_10);
            }
            _loc_4 = this.m_collection.rewardCommodities ;
            if (this.m_collection.rewardCommodities && _loc_4.length > 0)
            {
                for(int i0 = 0; i0 < _loc_4.size(); i0++)
                {
                	_loc_11 = _loc_4.get(i0);

                    _loc_12 = ASwingHelper.makeTextField(_loc_11.amount + " " + ZLoc.t("Main", "Commodity_" + _loc_11.name + "_friendlyName") + " ", EmbeddedArt.defaultFontNameBold, 12, 2645358);
                    this.m_rewardPanel.append(_loc_12);
                }
            }
            _loc_5 = this.m_collection.rewardCollectableNames ;
            if (this.m_collection.rewardCollectableNames && _loc_5.length > 0)
            {
                for(int i0 = 0; i0 < _loc_5.size(); i0++)
                {
                	_loc_13 = _loc_5.get(i0);

                    _loc_14 = Global.gameSettings().getItemFriendlyName(_loc_13);
                    _loc_15 = ASwingHelper.makeMultilineText(_loc_14 + " ", _loc_3, EmbeddedArt.defaultFontNameBold, TextFormatAlign.LEFT, 12, 2645358);
                    this.m_rewardPanel.append(_loc_15);
                }
            }
            _loc_6 = this.m_collection.rewardItemNames ;
            if (this.m_collection.rewardItemNames && _loc_6.length > 0)
            {
                for(int i0 = 0; i0 < _loc_6.size(); i0++)
                {
                	_loc_16 = _loc_6.get(i0);

                    _loc_17 = Global.gameSettings().getItemFriendlyName(_loc_16);
                    _loc_18 = ASwingHelper.makeMultilineText(_loc_17 + " ", _loc_3, EmbeddedArt.defaultFontNameBold, TextFormatAlign.LEFT, 12, 2645358);
                    this.m_rewardPanel.append(_loc_18);
                }
            }
            _loc_7 = this.m_collection.rewardUnlocks ;
            if (this.m_collection.rewardUnlocks && _loc_7.length > 0)
            {
                for(int i0 = 0; i0 < _loc_7.size(); i0++)
                {
                	_loc_19 = _loc_7.get(i0);

                    _loc_20 = ZLoc.t("Items", _loc_19 + "_friendlyName");
                    _loc_21 = ASwingHelper.makeMultilineText(_loc_20 + " ", _loc_3, EmbeddedArt.defaultFontNameBold, TextFormatAlign.LEFT, 12, 2645358);
                    this.m_rewardPanel.append(_loc_21);
                }
            }
            ASwingHelper.prepare(this.m_rewardPanel);
            return this.m_rewardPanel;
        }//end

        protected void  onTradeIn (MouseEvent event )
        {
            if (this.m_collection.isReadyToTradeIn() && this.m_tradeInButton.isEnabled())
            {
                this.m_collection.tradeIn();
                this.invalidateData();
            }
            else
            {
                UI.displayMessage("You must complete this collection to trade it in.");
            }
            return;
        }//end

        public void  invalidateData ()
        {
            if (this.m_initialized)
            {
                (this.m_shelf as InventoryScrollingList).updateElements();
                this.m_tradeInButton.setEnabled(this.m_collection.isReadyToTradeIn());
                this.m_rewardPanel.removeAll();
                this.makeRewardPanel();
                ASwingHelper.prepare(this);
            }
            return;
        }//end

        protected void  postCollectionFeed (GenericPopupEvent event )
        {
            if (event.button == GenericDialogView.YES)
            {
                Global.world.viralMgr.sendCollectionTradeInFeed(Global.player, this.m_collection.localizedName);
            }
            return;
        }//end

    }



