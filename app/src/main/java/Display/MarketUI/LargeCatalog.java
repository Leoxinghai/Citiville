package Display.MarketUI;

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
import Display.aswingui.*;
import Events.*;
//import flash.display.*;
//import flash.events.*;
//import flash.geom.*;
import org.aswing.*;

    public class LargeCatalog extends Catalog
    {
        protected MarketCategoryToolTip m_marketCategoryTooltip ;
        protected Sprite m_toolTipArrowHolder ;
        protected DisplayObject m_arrowObject ;
        public static  int CARD_WIDTH =200;
        public static  int CARD_HEIGHT =165;
        public static  int CARD_OFFSET =5;
        public static  int SCROLL_HEIGHT =601;

        public  LargeCatalog (ItemCatalogUI param1 ,CatalogParams param2 )
        {
            super(param1, param2);
            this.m_marketCategoryTooltip = new MarketCategoryToolTip(16777215);
            return;
        }//end

         protected int  getYPos ()
        {
            _loc_1 =Global.hud.getRect(parent );
            _loc_2 = _loc_1.y +Global.ui.screenHeight +CATALOG_TWEEN_OFFSET -this.getScrollHeight ();
            return _loc_2;
        }//end

         protected int  getScrollHeight ()
        {
            return Global.ui.screenHeight / 2 + SCROLL_HEIGHT / 2 + 10;
        }//end

         protected void  setupToolTip ()
        {
            m_categoryTooltip = new ItemCategoryToolTip();
            m_tooltip = new LargeItemCatalogToolTip();
            m_tooltipHolder = new Sprite();
            this.addChild(m_tooltipHolder);
            boolean _loc_1 =false ;
            m_tooltipHolder.mouseEnabled = false;
            m_tooltipHolder.mouseChildren = _loc_1;
            m_tooltipHolder.y = -80;
            jtw = new JWindow(m_tooltipHolder);
            jtw.setContentPane(m_tooltip);
            ASwingHelper.prepare(jtw);
            this.m_toolTipArrowHolder = new Sprite();
            this.addChild(this.m_toolTipArrowHolder);
            return;
        }//end

        private void  changeArrow (Item param1 ,boolean param2 )
        {
            if (this.m_arrowObject != null && this.m_toolTipArrowHolder.contains(this.m_arrowObject))
            {
                this.m_toolTipArrowHolder.removeChild(this.m_arrowObject);
            }
            if (param1.startDate && Catalog.assetDict.hasOwnProperty("market2_leCard"))
            {
                this.m_arrowObject =(DisplayObject) new Catalog.assetDict.get("market2_leCardRO_arrow");
            }
            else if (param1.isNew)
            {
                this.m_arrowObject =(DisplayObject) new Catalog.assetDict.get("market2_newCardRO_arrow");
            }
            else if (BuyLogic.isLocked(param1))
            {
                this.m_arrowObject =(DisplayObject) new Catalog.assetDict.get("market2_lockedCardRO_arrow");
            }
            else
            {
                this.m_arrowObject =(DisplayObject) new Catalog.assetDict.get("market2_marketCardRO_arrow");
            }
            if (param2)
            {
                this.m_arrowObject.scaleX = 1;
            }
            else
            {
                this.m_arrowObject.scaleX = -1;
            }
            this.m_toolTipArrowHolder.addChild(this.m_arrowObject);
            return;
        }//end

         protected void  onShow ()
        {
            super.onShow();
            if (m_ui != null)
            {
                m_ui.addEventListener(DataItemEvent.SHOW_OLD_TOOLTIP, this.showOldToolTip, false, 0, true);
                m_ui.addEventListener("turnOffOldToolTip", this.hideOldToolTip, false, 0, true);
            }
            return;
        }//end

        protected void  showOldToolTip (DataItemEvent event )
        {
            if (m_tooltip instanceof LargeItemCatalogToolTip)
            {
                super.setupToolTip();
            }
            super.showToolTip(event);
            return;
        }//end

        protected void  hideOldToolTip (Event event =null )
        {
            super.hideToolTip(event);
            return;
        }//end

         protected void  hideToolTip (Event event =null )
        {
            super.hideToolTip(event);
            this.m_toolTipArrowHolder.alpha = 0;
            return;
        }//end

         public void  updateCoinsCash (int param1 ,int param2 )
        {
            m_ui.updateCoinsCash(param1, param2);
            return;
        }//end

         protected void  showToolTip (DataItemEvent event )
        {
            double _loc_7 =0;
            double _loc_8 =0;
            _loc_2 = event.item ;
            _loc_3 = event.getParams ();
            if (m_tooltip instanceof ItemCatalogToolTip)
            {
                this.setupToolTip();
            }
            reparentTooltipHolder(Global.guide.isActive());
            m_tooltip.changeInfo(_loc_2, _loc_3);
            ASwingHelper.prepare(m_tooltip);
            if (!m_tooltip.showMe)
            {
                return;
            }
            this.m_toolTipArrowHolder.alpha = 1;
            jtw.show();
            _loc_4 = event.getOffset();
            _loc_5 =Global(.ui.screenWidth -SCROLL_WIDTH )/2;
            _loc_6 =Global(.ui.screenHeight -SCROLL_HEIGHT )/2;
            _loc_7 = event.pt.x + CARD_WIDTH + CARD_OFFSET - _loc_5 + _loc_4.x;
            _loc_8 = event.pt.y + _loc_4.y - _loc_6 + Math.max(0, (130 - m_tooltip.getHeight()) / 2);
            if (_loc_8 + m_tooltip.getHeight() > _loc_6 + SCROLL_HEIGHT)
            {
                _loc_8 = event.pt.y + CARD_OFFSET - _loc_6 - _loc_4.y;
                this.m_toolTipArrowHolder.y = _loc_8 + jtw.getHeight() - 2;
            }
            if (_loc_7 + CARD_WIDTH > _loc_5 + SCROLL_WIDTH)
            {
                _loc_7 = event.pt.x - CARD_WIDTH + CARD_OFFSET - _loc_5 - _loc_4.x;
                this.changeArrow(_loc_2, true);
                this.m_toolTipArrowHolder.x = _loc_7 + jtw.getWidth() - 2;
            }
            else
            {
                this.changeArrow(_loc_2, false);
                this.m_toolTipArrowHolder.x = _loc_7 + 2;
            }
            this.m_toolTipArrowHolder.y = event.pt.y + _loc_4.y - _loc_6 - 45;
            jtw.setX(_loc_7);
            jtw.setY(_loc_8);
            Sounds.play("UI_mouseover");
            return;
        }//end

         protected void  showCatToolTip (GenericObjectEvent event )
        {
            _loc_2 =Global(.ui.screenWidth -SCROLL_WIDTH )/2;
            _loc_3 = event.obj.get( "xOffset") != null ? (event.obj.get("xOffset")) : (0);
            _loc_4 = event.obj.get( "yOffset") != null ? (event.obj.get("yOffset")) : (0);
            m_tooltipHolder.addChild(this.m_marketCategoryTooltip);
            this.m_marketCategoryTooltip.changeInfo(event.obj.get("category"));
            this.m_marketCategoryTooltip.x = event.obj.get("posX") - this.m_marketCategoryTooltip.width * 0.5 - _loc_2 + 30 + _loc_3;
            this.m_marketCategoryTooltip.y = 130 + this.m_marketCategoryTooltip.height + _loc_4;
            Sounds.play("UI_mouseover");
            return;
        }//end

         protected void  hideCatToolTip (Event event )
        {
            if (this.m_marketCategoryTooltip && this.m_marketCategoryTooltip.parent)
            {
                m_tooltipHolder.removeChild(this.m_marketCategoryTooltip);
            }
            return;
        }//end

         public Array  categoryNames ()
        {
            XML _loc_3 =null ;
            String _loc_4 =null ;
            Array _loc_1 =new Array ();
            _loc_2 =Global.gameSettings().getMarketCategories ();
            for(int i0 = 0; i0 < _loc_2.size(); i0++)
            {
            		_loc_3 = _loc_2.get(i0);

                _loc_4 = _loc_3.@name;
                _loc_1.push(_loc_4);
            }
            return _loc_1;
        }//end

    }


