package Display.WishlistUI;

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
import Display.InventoryUI.*;
import Display.aswingui.*;
import Events.*;
//import flash.display.*;
//import flash.events.*;
//import flash.filters.*;
import org.aswing.*;
import org.aswing.ext.*;
import org.aswing.geom.*;

    public class WishlistView extends JPanel
    {
        protected GridList m_gridList ;
        protected GridListCellFactory m_cellFactory ;
        protected VectorListModel m_model ;
        protected Array m_blankItems ;
        protected JLabel m_inventoryLimitLabel ;
        protected JPanel m_inventoryLimitPanel ;
        protected boolean m_bShowInventoryLimit ;
        public static  int MAX_SLOTS =5;
        public static  int COUNT_HEIGHT =5;
        public static  int GRID_MARGIN =5;
        public static  int SLOT_WIDTH =94;
        public static  int SLOT_HEIGHT =99;
        public static  int TOTAL_WIDTH =480;
        public static  int SHARE_BUTTON_WIDTH =80;
        public static  int SHARE_BUTTON_HEIGHT =40;
        public static  int WISHLIST_TITLE_FONT_SIZE =18;

        public  WishlistView (boolean param1 =true ,LayoutManager param2 )
        {
            this.m_blankItems = new Array();
            super(new SoftBoxLayout(SoftBoxLayout.Y_AXIS, 5, SoftBoxLayout.TOP));
            this.m_bShowInventoryLimit = param1;
            _loc_3 = ASwingHelper.makeFlowJPanel(FlowLayout.LEFT );
            _loc_3.appendAll(this.makeLeftPane(), this.makeRightPane());
            this.append(_loc_3);
            this.append(this.makeWishlistPane());
            Global.stage.addEventListener(DataItemEvent.WISHLIST_CHANGED, this.onWishlistChanged, false, 0, true);
            ASwingHelper.prepare(this);
            return;
        }//end

        protected JPanel  makeLeftPane ()
        {
            _loc_1 = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.CENTER ,5);
            AssetPane _loc_2 =new AssetPane(new InventoryView.assetDict.get( "wishlist_icon") );
            _loc_3 = TextFieldUtil.getLocaleFontSize(WISHLIST_TITLE_FONT_SIZE ,WISHLIST_TITLE_FONT_SIZE ,.get( {locale size "ja",16) });
            _loc_4 = ASwingHelper.makeLabel(ZLoc.t("Dialogs","WishlistTitle"),EmbeddedArt.titleFont ,_loc_3 );
            _loc_1.appendAll(_loc_2, _loc_4, ASwingHelper.horizontalStrut(5));
            ASwingHelper.prepare(_loc_1);
            return _loc_1;
        }//end

        protected JPanel  makeWishlistPane ()
        {
            Item _loc_4 =null ;
            int _loc_5 =0;
            int _loc_6 =0;
            XML _loc_7 =null ;
            Item _loc_8 =null ;
            _loc_1 = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.LEFT );
            this.m_model = new VectorListModel();
            this.m_model.clear();
            _loc_2 =Global.player.wishlist ;
            int _loc_3 =0;
            while (_loc_3 < MAX_SLOTS && _loc_2 != null)
            {

                if (_loc_3 < _loc_2.length())
                {
                    _loc_4 = Global.gameSettings().getItemByName(_loc_2.get(_loc_3));
                    this.m_model.append(_loc_4);
                }
                _loc_3++;
            }
            if (_loc_2 != null && _loc_2.length < MAX_SLOTS)
            {
                _loc_5 = MAX_SLOTS - _loc_2.length;
                _loc_6 = 0;
                while (_loc_6 < _loc_5)
                {

                    _loc_7 = XML("<item name=\'blankItem\' />");
                    _loc_8 = new Item(_loc_7);
                    this.m_model.append(_loc_8);
                    this.m_blankItems.push(_loc_8);
                    _loc_6++;
                }
            }
            this.m_cellFactory = new WishlistCellFactory(new IntDimension(SLOT_WIDTH, SLOT_HEIGHT));
            this.m_gridList = new GridList(this.m_model, this.m_cellFactory, MAX_SLOTS, 1);
            this.m_gridList.setPreferredHeight(SLOT_HEIGHT);
            this.m_gridList.setPreferredWidth(TOTAL_WIDTH);
            _loc_1.appendAll(ASwingHelper.horizontalStrut(GRID_MARGIN), this.m_gridList, ASwingHelper.horizontalStrut(GRID_MARGIN));
            ASwingHelper.prepare(_loc_1);
            return _loc_1;
        }//end

        protected JPanel  makeRightPane ()
        {
            _loc_1 = ASwingHelper.makeFlowJPanel(FlowLayout.RIGHT );
            _loc_2 = TextFieldUtil.getLocaleFontSize(18,14,[{localesize"es",12locale},{"ja",size18});
            CustomButton _loc_3 =new CustomButton(ZLoc.t("Dialogs","WishlistShare"),null ,"GreenButtonUI");
            _loc_3.setFont(ASwingHelper.makeFont(EmbeddedArt.titleFont, _loc_2));
            _loc_3.setForeground(new ASColor(16777215));
            _loc_3.setMargin(new Insets(2, 5, 2, 5));
            _loc_3.filters = .get(new GlowFilter(33724, 1, 0, 0, 2.5));
            _loc_3.addEventListener(MouseEvent.MOUSE_UP, this.onShare, false, 0, true);
            _loc_3.useHandCursor = true;
            if (this.m_bShowInventoryLimit)
            {
                this.m_inventoryLimitPanel = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.LEFT);
                this.m_inventoryLimitLabel = this.makeInventoryLimitLabel();
                this.m_inventoryLimitPanel.append(this.m_inventoryLimitLabel);
            }
            _loc_1.appendAll(_loc_3, ASwingHelper.horizontalStrut(40), this.m_inventoryLimitPanel);
            ASwingHelper.prepare(_loc_1);
            return _loc_1;
        }//end

        protected JLabel  makeInventoryLimitLabel ()
        {
            _loc_1 =Global.player.inventory.capacity ;
            _loc_2 =Global.player.inventory.totalCount ;
            _loc_3 = ZLoc.t("Dialogs","InventoryLimit",{numItems _loc_2 ,totalLimit });
            _loc_4 = TextFieldUtil.getLocaleFontSize(18,18,.get({localesize"ja",14)});
            _loc_5 = ASwingHelper.makeLabel(_loc_3 ,EmbeddedArt.defaultFontNameBold ,_loc_4 ,EmbeddedArt.whiteTextColor );
            return ASwingHelper.makeLabel(_loc_3, EmbeddedArt.defaultFontNameBold, _loc_4, EmbeddedArt.whiteTextColor);
        }//end

        public void  refreshInventoryLimit ()
        {
            this.m_inventoryLimitPanel.remove(this.m_inventoryLimitLabel);
            this.m_inventoryLimitLabel = this.makeInventoryLimitLabel();
            this.m_inventoryLimitPanel.append(this.m_inventoryLimitLabel);
            return;
        }//end

        protected void  onShare (MouseEvent event )
        {
            if (Global.player.wishlist.length > 0)
            {
                Global.world.viralMgr.sendWishlistRequest(Global.player);
            }
            return;
        }//end

        protected void  onWishlistChanged (DataItemEvent event )
        {
            XML _loc_2 =null ;
            Item _loc_3 =null ;
            if (this.m_model.getSize() - this.m_blankItems.length < Global.player.wishlist.length())
            {
                this.m_model.removeElementAt((Global.player.wishlist.length - 1));
                this.m_model.insertElementAt(event.item, (Global.player.wishlist.length - 1));
                this.m_blankItems.shift();
            }
            else if (this.m_model.getSize() > Global.player.wishlist.length())
            {
                this.m_model.remove(event.item);
                _loc_2 = XML("<item name=\'blankItem\' />");
                _loc_3 = new Item(_loc_2);
                this.m_blankItems.push(_loc_3);
                this.m_model.append(_loc_3);
            }
            return;
        }//end

    }



