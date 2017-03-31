package GameMode;

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
import Display.MarketUI.*;
import Engine.*;
import Engine.Helpers.*;
import Events.*;
import Transactions.*;

//import flash.display.*;
//import flash.events.*;
//import flash.geom.*;

    public class GMExpand extends GMDefault
    {
        protected Vector2 m_size ;
        protected Sprite m_area ;
        protected String m_itemName ;
        protected Rectangle m_expansionRect ;
        protected boolean m_locationValid ;
        private boolean m_isPartOfShippingAnnouncement ;
        protected Rectangle m_rect ;
public static MarketCell m_MarketCell =null ;

        public  GMExpand (String param1 )
        {
            this.m_size = new Vector2();
            this.m_rect = new Rectangle();
            this.m_itemName = param1;
            this.m_isPartOfShippingAnnouncement = this.m_itemName == "expand_12_12_special";
            _loc_2 =Global.gameSettings().getItemByName(param1 );
            if (_loc_2)
            {
                this.m_size.x = _loc_2.expansionWidth;
                this.m_size.y = _loc_2.expansionHeight;
            }
            return;
        }//end

         public void  enableMode ()
        {
            super.enableMode();
            _loc_1 = IsoMath.screenPosToTilePos(GlobalEngine.viewport.mouseX ,GlobalEngine.viewport.mouseY );
            this.m_expansionRect = Global.world.territory.getClosestAdjacentRect(_loc_1);
            this.m_locationValid = true;
            this.drawExpansionArea(255);
            Global.world.wildernessSim.hideAllExpansionMarkers(true);
            return;
        }//end

         public void  disableMode ()
        {
            super.disableMode();
            GlobalEngine.viewport.overlayBase.removeChild(this.m_area);
            return;
        }//end

         public boolean  onMouseMove (MouseEvent event )
        {
            super.onMouseMove(event);
            _loc_2 = IsoMath.screenPosToTilePos(event.stageX ,event.stageY );
            this.m_expansionRect = Global.world.territory.getClosestAdjacentRect(_loc_2);
            this.m_locationValid = this.checkLocationValid();
            _loc_3 = this.m_locationValid ? (255) : (16711680);
            this.drawExpansionArea(_loc_3);
            return true;
        }//end

        protected boolean  checkLocationValid ()
        {
            Vector _loc_3.<Rectangle >=null ;
            Rectangle _loc_4 =null ;
            boolean _loc_1 =true ;
            _loc_1 = !Global.world.rectIntersectsTerritory(this.m_expansionRect);
            _loc_2 =Global.world.citySim.waterManager.positionValidForExpansion(this.m_expansionRect );
            if (!_loc_2)
            {
                _loc_1 = false;
            }
            _loc_1 = isValidExpansionSquare(this.m_expansionRect);
            if (this.m_isPartOfShippingAnnouncement && _loc_1)
            {
                _loc_3 = Vector<Rectangle>(.get(new Rectangle(-36, 12, 1, 1), new Rectangle(-36, 0, 1, 1), new Rectangle(-36, -12, 1, 1), new Rectangle(-36, -24, 1, 1), new Rectangle(-36, -36, 1, 1)));
                for(int i0 = 0; i0 < _loc_3.size(); i0++)
                {
                	_loc_4 = _loc_3.get(i0);

                    if (this.m_expansionRect.x == _loc_4.x && this.m_expansionRect.y == _loc_4.y)
                    {
                        return true;
                    }
                }
                return false;
            }
            return _loc_1;
        }//end

         protected void  handleClick (MouseEvent event )
        {
            if (this.m_locationValid)
            {
                UI.displayMessage(ZLoc.t("Dialogs", "PlaceExpansion"), GenericPopup.TYPE_YESNO, this.onConfirmationDialogClose);
            }
            return;
        }//end

        protected void  onConfirmationDialogClose (GenericPopupEvent event )
        {
            Item _loc_2 =null ;
            int _loc_3 =0;
            double _loc_4 =0;
            Array _loc_5 =null ;
            Object _loc_6 =null ;
            int _loc_7 =0;
            int _loc_8 =0;
            Wilderness _loc_9 =null ;
            int _loc_10 =0;
            if (event.button == GenericPopup.YES)
            {
                _loc_2 = Global.gameSettings().getItemByName(this.m_itemName);
                _loc_3 = _loc_2.cost;
                if (_loc_3 <= 0)
                {
                    _loc_3 = _loc_2.cash;
                }
                _loc_4 = 0;
                if (Global.marketSaleManager.isItemOnSale(_loc_2))
                {
                    _loc_4 = Global.marketSaleManager.getDiscountPercent(_loc_2) / 100;
                }
                _loc_3 = Math.ceil(_loc_3 - _loc_3 * _loc_4);
                if (_loc_2.cost > 0)
                {
                    Global.player.gold = Global.player.gold - _loc_3;
                }
                else
                {
                    Global.player.cash = Global.player.cash - _loc_3;
                }
                Global.world.expandMap(this.m_expansionRect);
                Global.world.addGameMode(new GMPlay());
                _loc_5 = Global.world.wildernessSim.getTreesInRect(this.m_expansionRect);
                for(int i0 = 0; i0 < _loc_5.size(); i0++)
                {
                	_loc_6 = _loc_5.get(i0);

                    _loc_9 = new Wilderness(_loc_6.itemName);
                    _loc_10 = TWorldState.getNextTempID();
                    _loc_9.setId(_loc_10);
                    _loc_6.id = _loc_10;
                    _loc_9.setState("static");
                    _loc_9.setOuter(Global.world);
                    _loc_9.setDirection(_loc_6.dir);
                    _loc_9.setPosition(_loc_6.x, _loc_6.y);
                    _loc_9.isMoveLocked = true;
                    _loc_9.attach();
                    _loc_6.wildernessObj = _loc_9;
                }
                Global.world.createOverlayBackground();
                if (!this.m_isPartOfShippingAnnouncement)
                {
                    _loc_7 = ExpansionManager.instance.getNextExpansionPermitRequirement();
                }
                if (Global.player.checkLicense(this.m_itemName))
                {
                    Global.player.expireLicense(this.m_itemName);
                }
                _loc_8 = Math.min(Global.player.inventory.getItemCountByName(Item.PERMIT_ITEM), _loc_7);
                Global.player.inventory.removeItems(Item.PERMIT_ITEM, _loc_8);
                _loc_11 =Global.player ;
                _loc_12 =Global.player.expansionsPurchased +1;
                 Global.player.expansionsPurchased++;
                _loc_11 = Global.player;
                _loc_12 = Global.player.expansionCostLevel + 1;
                 Global.player.expansionCostLevel++;
                Global.player.populationObservedOnLastExpansion = Global.world.citySim.getPopulation();
                GameTransactionManager.addTransaction(new TExpandFarm(this.m_itemName, new Vector2(this.m_expansionRect.x, this.m_expansionRect.y), _loc_5, this.m_isPartOfShippingAnnouncement));
                if (m_MarketCell != null)
                {
                    m_MarketCell.performUpdate(true);
                }
                resetMarketCell();
                Global.world.citySim.roadManager.onGameLoaded(null);
                Global.world.citySim.announcerManager.cycleAllAnnouncers();
            }
            return;
        }//end

        protected void  drawExpansionArea (int param1 )
        {
            boolean _loc_2 =false ;
            if (!this.m_area)
            {
                this.m_area = new Sprite();
                _loc_2 = true;
            }
            _loc_3 = IsoMath.tilePosToPixelPos(this.m_expansionRect.x ,this.m_expansionRect.y );
            _loc_4 = IsoMath.tilePosToPixelPos(this.m_expansionRect.x ,this.m_expansionRect.bottom );
            _loc_5 = IsoMath.tilePosToPixelPos(this.m_expansionRect.right ,this.m_expansionRect.bottom );
            _loc_6 = IsoMath.tilePosToPixelPos(this.m_expansionRect.right ,this.m_expansionRect.y );
            double _loc_7 =2;
            this.m_area.graphics.clear();
            this.m_area.graphics.lineStyle(_loc_7, param1);
            this.m_area.graphics.moveTo(_loc_5.x, _loc_5.y);
            this.m_area.graphics.lineTo(_loc_6.x, _loc_6.y);
            this.m_area.graphics.lineTo(_loc_3.x, _loc_3.y);
            this.m_area.graphics.lineTo(_loc_4.x, _loc_4.y);
            this.m_area.graphics.lineTo(_loc_5.x, _loc_5.y);
            if (_loc_2)
            {
                GlobalEngine.viewport.overlayBase.addChild(this.m_area);
            }
            return;
        }//end

         protected boolean  isObjectHighlightable (GameObject param1 )
        {
            return false;
        }//end

         protected boolean  isObjectSelectable (GameObject param1 )
        {
            return false;
        }//end

         protected boolean  isObjectDraggable (GameObject param1 )
        {
            return false;
        }//end

        public static void  resetMarketCell ()
        {
            m_MarketCell = null;
            return;
        }//end

        public static void  setMarketCell (MarketCell param1 )
        {
            m_MarketCell = param1;
            return;
        }//end

        public static boolean  isValidExpansionSquare (Rectangle param1 )
        {
            Array _loc_3 =null ;
            int _loc_4 =0;
            _loc_2 =Global.gameSettings().getExpansionSquare(param1.x ,param1.y );
            if (_loc_2 == null)
            {
                return false;
            }
            if (_loc_2.experiment != null)
            {
                _loc_3 = _loc_2.experimentVariants;
                _loc_4 = Global.experimentManager.getVariant(_loc_2.experiment);
                if (_loc_3.indexOf(_loc_4) < 0)
                {
                    return false;
                }
            }
            return true;
        }//end

    }



