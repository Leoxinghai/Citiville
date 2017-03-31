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
import Display.InventoryUI.*;
import Display.MarketUI.*;
import Display.MarketUI.assets.*;
import Display.aswingui.*;
import root.Global;
import root.ZLoc;
//import flash.display.*;
import org.aswing.*;
import org.aswing.geom.*;

    public class CollectionCatalogUI extends InventoryCatalogUI
    {
        public static  int COLLECTIONS_TITLE_WIDTH =565;
        public static  int CENTER_GAP =100;

        public  CollectionCatalogUI ()
        {
            m_catalogTitle = ZLoc.t("Dialogs", "Collections");
            return;
        }//end

         protected int  titleWidth ()
        {
            return COLLECTIONS_TITLE_WIDTH;
        }//end

         protected int  wishListTab ()
        {
            return 82;
        }//end

         protected int  closeButtonGapFromTop ()
        {
            return 6;
        }//end

         protected int  wishListGap ()
        {
            return 10;
        }//end

         protected boolean  showInventory ()
        {
            return false;
        }//end

         protected void  makeBackground ()
        {
            DisplayObject _loc_1 =new EmbeddedArt.collections_bg ()as DisplayObject ;
            MarginBackground _loc_2 =new MarginBackground(_loc_1 ,new Insets(TOP_INSET_HEIGHT ));
            this.setBackgroundDecorator(_loc_2);
            this.setPreferredSize(new IntDimension(_loc_1.width, _loc_1.height + TOP_INSET_HEIGHT));
            return;
        }//end

         public void  goToItem (String param1 )
        {
            _loc_2 = shelf.data;
            _loc_3 = CardUtil.indexOfByCardName(_loc_2,param1);
            if (_loc_3 != -1)
            {
                shelf.scrollToItemIndex(_loc_3);
            }
            return;
        }//end

         public void  switchType (String param1 )
        {
            XML _loc_3 =null ;
            _loc_2 = Global.gameSettings().getCollections();
            this.m_items = new Array();
            for(int i0 = 0; i0 < _loc_2.size(); i0++)
            {
            	_loc_3 = _loc_2.get(i0);

                this.m_items.push(new Collection(_loc_3));
            }
            setShelf(new CollectionScrollingList(this.m_items, CollectionCellFactory, 0, 1, 2));
            ASwingHelper.prepare(shelf);
            m_centerPanel.append(shelf);
            m_centerPanel.setPreferredHeight(Catalog.CARD_HEIGHT * 2 + CENTER_GAP);
            ASwingHelper.prepare(m_centerPanel);
            return;
        }//end

    }



