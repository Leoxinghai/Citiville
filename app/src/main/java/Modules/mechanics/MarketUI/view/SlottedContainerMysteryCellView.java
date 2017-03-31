package Modules.mechanics.MarketUI.view;

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
import Display.GridlistUI.model.*;
import Display.aswingui.*;
import Mechanics.GameMechanicInterfaces.*;
import Modules.mechanics.ui.*;
import Modules.mechanics.ui.items.*;
//import flash.display.*;
//import flash.events.*;
//import flash.utils.*;
import org.aswing.*;

    public class SlottedContainerMysteryCellView extends SlottedContainerCellView
    {

        public  SlottedContainerMysteryCellView (IMechanicUser param1 ,SlottedContainerConfig param2 ,int param3 ,int param4 )
        {
            super(param1, param2, param3, param4, false);
            return;
        }//end

        protected DisplayObject  mysteryAsset ()
        {
            return m_assetDict.get("mystery_item");
        }//end

        protected boolean  highlightCell ()
        {
            ISlottedContainer _loc_6 =null ;
            _loc_1 = m_config.storageMechanic;
            _loc_2 = (Array)m_mechanicUser.getDataForMechanic(_loc_1.type)
            _loc_3 = m_config.slotsPerTab;
            _loc_4 = _loc_2.length >=(m_tabId +1)*_loc_3 ;
            boolean _loc_5 =false ;
            if (m_mechanicUser instanceof ISlottedContainer)
            {
                _loc_6 =(ISlottedContainer) m_mechanicUser;
                _loc_5 = _loc_6.hasMysteryItemInInventory(m_tabId);
            }
            return _loc_4 || _loc_5;
        }//end

         public void  makeAssets (Dictionary param1 )
        {
            _loc_2 = param1.get(DelayedAssetLoader.MARKET_ASSETS) ;
            m_assetDict.put("bg", (DisplayObject) new _loc_2.get("marketItem_reward"));
            _loc_3 = param1.get(DelayedAssetLoader.SLOTTED_CONTAINER_ASSETS) ;
            m_assetDict.put("mystery_item", (DisplayObject) new _loc_3.get("mall_mystery_new"));
            return;
        }//end

         public void  updateView (Object param1)
        {
            if (this.highlightCell)
            {
                m_bodyPanel.filters = new Array();
            }
            else
            {
                m_bodyPanel.filters = .get(ASwingHelper.makeDisabledFilter());
            }
            return;
        }//end

         protected void  updateFooterPanel ()
        {
            m_footPanel.removeAll();
            _loc_1 = m_config.getTitleZlocItem("mystery");
            _loc_2 = TextFieldUtil.getLocaleFontSize(16,14,.get( {locale size "ja",16) });
            _loc_3 = TextFieldUtil.formatSmallCapsString(ZLoc.t(_loc_1.pkg,_loc_1.key));
            _loc_4 = ASwingHelper.makeTextField(_loc_3 ,EmbeddedArt.titleFont ,_loc_2 ,EmbeddedArt.yellowTextColor );
            if (this.highlightCell)
            {
                _loc_4.filters = EmbeddedArt.titleFilters;
            }
            else
            {
                _loc_4.filters = EmbeddedArt.titleFilters.concat(ASwingHelper.makeDisabledFilter());
            }
            m_footPanel.append(_loc_4);
            return;
        }//end

         protected void  initLayout ()
        {
            super.initLayout();
            ASwingHelper.setEasyBorder(m_panel, 0, 5, 0, 5);
            return;
        }//end

         protected DisplayObject  createContentPlaceholder ()
        {
            return this.mysteryAsset;
        }//end

         protected void  onClick (MouseEvent event =null )
        {
            return;
        }//end

         protected void  onRollOver (MouseEvent event =null )
        {
            this.dispatchEvent(new ViewActionEvent("onDisplayMysteryCellTooltip"));
            return;
        }//end

         protected void  onRollOut (MouseEvent event =null )
        {
            dispatchEvent(new Event("turnOffToolTip", true));
            return;
        }//end

    }



