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
import Display.*;
import Engine.*;
import Engine.Helpers.*;
import Modules.guide.ui.*;
import Modules.stats.types.*;
//import flash.display.*;
//import flash.events.*;
//import flash.geom.*;

    public class GMPlaceBridge extends GMPlaceMapResource
    {
        protected Sprite m_area ;
        protected Sprite m_freeArea ;
        protected Sprite m_endSquare ;
        protected Sprite m_ghostBridge ;
        protected Bridge selectedBridge ;
        protected Item placeItem ;
        protected ItemDefinitionBridge bridgeItem ;

        public  GMPlaceBridge (String param1 ,Class param2 ,boolean param3 =false )
        {
            super(param1, param2, param3);
            if (this.bridgeItem == null)
            {
                this.placeItem = Global.gameSettings().getItemByName(param1);
                this.bridgeItem = this.placeItem.bridge;
            }
            return;
        }//end

        private void  setupSelectedBridge ()
        {
            ConstructionSite _loc_1 =null ;
            if (m_selectedObject == null)
            {
                return;
            }
            if (m_selectedObject instanceof Bridge)
            {
                this.selectedBridge =(Bridge) m_selectedObject;
            }
            if (m_selectedObject instanceof ConstructionSite)
            {
                _loc_1 =(ConstructionSite) m_selectedObject;
                if (_loc_1.targetClass == Bridge)
                {
                    this.selectedBridge = new Bridge(_loc_1.targetName);
                }
            }
            return;
        }//end

        private DisplayObject  getBridgeImage ()
        {
            DisplayObject _loc_1 =null ;
            if (this.selectedBridge == null)
            {
                return _loc_1;
            }
            if (this.selectedBridge.isCurrentImageLoading())
            {
                return _loc_1;
            }
            _loc_2 = this.selectedBridge.getGhostImage ();
            if (_loc_2 != null)
            {
                _loc_1 =(DisplayObject) _loc_2.image;
            }
            return _loc_1;
        }//end

        private boolean  playerDoesNotOwnTargetSquare ()
        {
            Vector2 _loc_1 =new Vector2(this.bridgeItem.targetSquare.x ,this.bridgeItem.targetSquare.y );
            Vector2 _loc_2 =new Vector2(this.bridgeItem.targetSquare.x +this.placeItem.sizeX ,this.bridgeItem.targetSquare.y );
            Vector2 _loc_3 =new Vector2(this.bridgeItem.targetSquare.x +this.placeItem.sizeX ,this.bridgeItem.targetSquare.y +this.placeItem.sizeX );
            Vector2 _loc_4 =new Vector2(this.bridgeItem.targetSquare.x ,this.bridgeItem.targetSquare.y +this.placeItem.sizeX );
            if (Global.world.territory.pointInTerritory(_loc_1.x, _loc_1.y))
            {
                return false;
            }
            if (Global.world.territory.pointInTerritory(_loc_2.x, _loc_2.y))
            {
                return false;
            }
            if (Global.world.territory.pointInTerritory(_loc_3.x, _loc_3.y))
            {
                return false;
            }
            if (Global.world.territory.pointInTerritory(_loc_4.x, _loc_4.y))
            {
                return false;
            }
            return true;
        }//end

        private void  handleNotExpandedToBridgeTarget ()
        {
            Global.world.centerOnIsoPosition(this.bridgeItem.targetSquare, 0);
            _loc_1 =Global.ui.screenWidth /2;
            _loc_2 =Global.ui.screenHeight /2;
            UI.displayStatus(ZLoc.t("Main", "BuyExpansionFirstRed"), _loc_1, _loc_2);
            _loc_3 =Global.world.getActiveGameModes ();
            int _loc_4 =0;
            while (_loc_4 < _loc_3.length())
            {

                Global.world.removeGameMode(_loc_3.get(_loc_4));
                _loc_4++;
            }
            Global.world.addGameMode(new GMPlay());
            return;
        }//end

         public void  enableMode ()
        {
            super.enableMode();
            if (this.selectedBridge == null)
            {
                this.setupSelectedBridge();
            }
            if (this.playerDoesNotOwnTargetSquare)
            {
                this.handleNotExpandedToBridgeTarget();
                return;
            }
            Global.guide.notify("BridgeGuide");
            return;
        }//end

        private void  resolveSpriteDisable (Sprite param1 )
        {
            if (param1 !=null)
            {
                if (param1.parent != null)
                {
                    GlobalEngine.viewport.overlayBase.removeChild(param1);
                }
            }
            return;
        }//end

         public void  disableMode ()
        {
            super.disableMode();
            this.resolveSpriteDisable(this.m_area);
            this.resolveSpriteDisable(this.m_freeArea);
            this.resolveSpriteDisable(this.m_endSquare);
            this.resolveSpriteDisable(this.m_ghostBridge);
            return;
        }//end

        protected void  drawFreeWaterArea (Rectangle param1 ,int param2 )
        {
            boolean _loc_3 =false ;
            if (!this.m_freeArea)
            {
                this.m_freeArea = new Sprite();
                _loc_3 = true;
            }
            _loc_4 = IsoMath.tilePosToPixelPos(param1.x ,param1.y );
            _loc_5 = IsoMath.tilePosToPixelPos(param1.x ,param1.bottom );
            _loc_6 = IsoMath.tilePosToPixelPos(param1.right ,param1.bottom );
            _loc_7 = IsoMath.tilePosToPixelPos(param1.right ,param1.y );
            double _loc_8 =2;
            this.m_freeArea.graphics.clear();
            this.m_freeArea.graphics.lineStyle(_loc_8, param2);
            this.m_freeArea.graphics.moveTo(_loc_6.x, _loc_6.y);
            this.m_freeArea.graphics.lineTo(_loc_7.x, _loc_7.y);
            this.m_freeArea.graphics.lineTo(_loc_4.x, _loc_4.y);
            this.m_freeArea.graphics.lineTo(_loc_5.x, _loc_5.y);
            this.m_freeArea.graphics.lineTo(_loc_6.x, _loc_6.y);
            if (_loc_3)
            {
                GlobalEngine.viewport.overlayBase.addChild(this.m_freeArea);
            }
            return;
        }//end

        protected void  clearFreeWaterArea ()
        {
            boolean _loc_1 =false ;
            if (!this.m_freeArea)
            {
                this.m_freeArea = new Sprite();
                _loc_1 = true;
            }
            this.m_freeArea.graphics.clear();
            if (_loc_1)
            {
                GlobalEngine.viewport.overlayBase.addChild(this.m_freeArea);
            }
            return;
        }//end

        protected void  drawGhostBridge ()
        {
            boolean _loc_1 =false ;
            if (!this.m_ghostBridge)
            {
                this.m_ghostBridge = new Sprite();
                _loc_1 = true;
            }
            _loc_2 = this.getBridgeImage ();
            if (_loc_2 == null)
            {
                return;
            }
            this.m_ghostBridge.addChild(_loc_2);
            this.m_ghostBridge.scaleX = this.bridgeItem.ghostScale;
            this.m_ghostBridge.scaleY = this.bridgeItem.ghostScale;
            _loc_3 = IsoMath.tilePosToPixelPos(this.bridgeItem.targetSquare.x ,this.bridgeItem.targetSquare.y );
            this.m_ghostBridge.x = _loc_3.x + this.bridgeItem.ghostXOffset;
            this.m_ghostBridge.y = _loc_3.y + this.bridgeItem.ghostYOffset;
            this.m_ghostBridge.alpha = this.bridgeItem.ghostAlpha;
            if (_loc_1)
            {
                GlobalEngine.viewport.overlayBase.addChild(this.m_ghostBridge);
            }
            return;
        }//end

         protected boolean  updateHighlightPoint (MouseEvent event )
        {
            _loc_2 = super.updateHighlightPoint(event);
            if (this.selectedBridge == null)
            {
                return false;
            }
            if (this.selectedBridge.freeExpansionZone)
            {
                this.drawFreeWaterArea(this.selectedBridge.freeExpansionZone, EmbeddedArt.lightishBlueTextColor);
            }
            else
            {
                this.clearFreeWaterArea();
            }
            return _loc_2;
        }//end

         protected boolean  isBridgePositionValid ()
        {
            if (this.bridgeItem.targetSquare.x == m_highlightedPoint.x && this.bridgeItem.targetSquare.y == m_highlightedPoint.y)
            {
                return true;
            }
            return false;
        }//end

         protected void  completePlaceResource (LotSite param1)
        {
            UI.popupLock();
            super.completePlaceResource(param1);
            UI.popupUnlock();
            Global.world.citySim.announcerManager.cycleAllAnnouncers();
            return;
        }//end

        public void  setupGhostBridgeImage ()
        {
            if (this.selectedBridge == null)
            {
                this.setupSelectedBridge();
            }
            this.drawGhostBridge();
            return;
        }//end

         protected void  handleObjectDrop (MouseEvent event =null )
        {
            LotSite _loc_2 =null ;
            GuideTile _loc_4 =null ;
            Vector2 _loc_5 =null ;
            int _loc_3 =0;
            if (Global.guide.isActive() && Global.guide.guideTiles.length())
            {
                m_objectPlacedSuccess = false;
                m_positionValid = event ? (this.updateHighlightPoint(event)) : (true);
                _loc_3 = 0;
                while (_loc_3 < Global.guide.guideTiles.length())
                {

                    _loc_4 =(GuideTile) Global.guide.guideTiles.get(_loc_3);
                    _loc_5 = new Vector2(_loc_4.origin.x, _loc_4.origin.y);
                    if (m_highlightedPoint.equals(_loc_5))
                    {
                        if (m_positionValid)
                        {
                            m_objectPlacedSuccess = true;
                            break;
                        }
                        else
                        {
                            UI.displayStatus(ZLoc.t("Main", "MustClearSpace"), event.stageX, event.stageY);
                            Global.ui.showTickerMessage(ZLoc.t("Main", "TickerClearBridgeLand"));
                            return;
                        }
                    }
                    _loc_3++;
                }
                if (m_objectPlacedSuccess)
                {
                    Global.guide.guideTiles.splice(_loc_3, 1);
                    m_selectedObject.setPosition(_loc_5.x, _loc_5.y);
                    m_selectedObject.setId(m_resource.getId());
                    m_selectedObject.onObjectDropPreTansaction(m_objectStartPos);
                    m_selectedObject.conditionallyReattach();
                    m_selectedObject.onObjectDrop(m_objectStartPos);
                    this.completePlaceResource();
                    _loc_4.cleanup();
                    _loc_4 = null;
                    if (m_selectedObject instanceof MapResource)
                    {
                        ((MapResource)m_selectedObject).trackAction(TrackedActionType.PLACE);
                    }
                    return;
                }
            }
            return;
        }//end

    }



