package Classes;

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

import Classes.effects.*;
import Classes.util.*;
import Display.*;
import Display.Toaster.*;
import Engine.*;
import Engine.Helpers.*;
import GameMode.*;
import Modules.guide.ui.*;
import Modules.quest.Managers.*;
//import flash.events.*;
//import flash.geom.*;

    public class Bridge extends ParkingLot
    {
        protected Vector3 m_bridgeSquareSize ;
        protected boolean m_validBridgeStart ;
        protected Rectangle m_expansionRect ;
        protected boolean m_validExpansionStart ;
        protected Vector3 m_unlockExpansionPoint ;
        protected Rectangle m_unlockExpansionRect ;
        protected boolean m_validBridgeEnd ;
        protected Vector3 m_bridgeEndSquare ;
        protected Item m_leftBridgeItem ;
        protected Item m_centerBridgeItem ;
        protected Item m_rightBridgeItem ;
        private boolean m_loadedPartImages =false ;
        protected ItemImageInstance m_leftBridgeImage ;
        protected ItemImageInstance m_centerBridgeImage ;
        protected ItemImageInstance m_rightBridgeImage ;
        protected Array m_bridgeParts ;
        protected Vector2 m_toolTipOffset ;
        public static  int MAXBRIDGELENGTH =30;
        public static  int MAXBRIDGESEGMENTS =10;
        public static  double BRIDGE_OBJECT_TEMP_ID_START =71000;

        public  Bridge (String param1)
        {
            this.m_bridgeEndSquare = new Vector3();
            this.m_bridgeParts = new Array();
            super(param1);
            this.m_validBridgeStart = false;
            this.m_expansionRect = new Rectangle();
            this.m_unlockExpansionRect = new Rectangle();
            this.m_unlockExpansionPoint = new Vector3();
            this.m_bridgeSquareSize = m_size.clone();
            this.m_leftBridgeItem = Global.gameSettings().getItemByName(m_item.bridge.leftPart);
            this.m_centerBridgeItem = Global.gameSettings().getItemByName(m_item.bridge.centerPart);
            this.m_rightBridgeItem = Global.gameSettings().getItemByName(m_item.bridge.rightPart);
            this.m_leftBridgeImage = null;
            this.m_centerBridgeImage = null;
            this.m_rightBridgeImage = null;
            return;
        }//end

         public boolean  isSellable ()
        {
            return false;
        }//end

         public boolean  canBeDragged ()
        {
            return false;
        }//end

         public boolean  canBeRotated ()
        {
            return false;
        }//end

        public boolean  isValidBridgeStart ()
        {
            return this.m_validBridgeStart;
        }//end

        public boolean  isValidBridgeEnd ()
        {
            return this.m_validBridgeEnd;
        }//end

        public boolean  isValidBridgeExpansionStart ()
        {
            return this.m_validExpansionStart;
        }//end

        public Rectangle  expansionRectangle ()
        {
            return this.m_expansionRect;
        }//end

        public Rectangle  freeExpansionZone ()
        {
            return this.m_unlockExpansionRect;
        }//end

        public Vector3  endSquare ()
        {
            return this.m_bridgeEndSquare;
        }//end

        private void  overrideEndSquare (int param1 ,int param2 )
        {
            this.m_bridgeEndSquare.x = param1;
            this.m_bridgeEndSquare.y = param2;
            this.m_bridgeEndSquare.z = 0;
            return;
        }//end

        public Rectangle  endRectangle ()
        {
            return new Rectangle(this.m_bridgeEndSquare.x, this.m_bridgeEndSquare.y, this.m_bridgeSquareSize.x, this.m_bridgeSquareSize.y);
        }//end

        public int  bridgeLength ()
        {
            return this.m_bridgeEndSquare.y - m_position.y + this.m_bridgeSquareSize.y;
        }//end

        public Rectangle  bridgeRectangle ()
        {
            return new Rectangle(m_position.x, m_position.y, this.m_bridgeSquareSize.x, this.bridgeLength);
        }//end

        public Vector3  grantedExpansion ()
        {
            return this.m_unlockExpansionPoint;
        }//end

         public void  loadObject (Object param1 )
        {
            Rectangle _loc_3 =null ;
            BridgePart _loc_4 =null ;
            Rectangle _loc_5 =null ;
            super.loadObject(param1);
            this.overrideEndSquare(int(param1.endPosition.x), int(param1.endPosition.y));
            _loc_2 = Global.world.getObjectsByClass(BridgePart);
            if (_loc_2.length())
            {
                _loc_3 = new Rectangle(getPosition().x, getPosition().y - getItem().bridge.bridgeBoundary.y, getItem().bridge.bridgeBoundary.x, getItem().bridge.bridgeBoundary.y);
                for(int i0 = 0; i0 < _loc_2.size(); i0++)
                {
                	_loc_4 = _loc_2.get(i0);

                    if (_loc_4 instanceof BridgePart)
                    {
                        _loc_5 = new Rectangle(_loc_4.getPosition().x, _loc_4.getPosition().y, _loc_4.getSize().x, _loc_4.getSize().y);
                        if (_loc_3.intersects(_loc_5))
                        {
                            this.addBridgePart(_loc_4);
                        }
                    }
                }
            }
            return;
        }//end

        public void  addBridgePart (BridgePart param1 )
        {
            part = param1;
            part.bridgeOwner = this;
            this.m_bridgeParts.push(part);
            this .m_bridgeParts .sort (double  (BridgePart param11 ,BridgePart param2 )
            {
                if (param11.getPosition().y > param2.getPosition().y)
                {
                    return -1;
                }
                if (param11.getPosition().y < param2.getPosition().y)
                {
                    return 1;
                }
                return 0;
            }//end
            );
            return;
        }//end

        public BridgePart  getRightBridgePart ()
        {
            if (this.m_bridgeParts.length == 0)
            {
                return null;
            }
            return (BridgePart)this.m_bridgeParts.get((this.m_bridgeParts.length - 1));
        }//end

         public void  setPosition (double param1 ,double param2 ,double param3 =0)
        {
            super.setPosition(param1, param2, param3);
            return;
        }//end

         public void  rotate ()
        {
            super.rotate();
            Global.world.citySim.roadManager.updateAllRoadTiles();
            Global.world.citySim.roadManager.updateRoads(this);
            return;
        }//end

         protected void  updateAdjacent ()
        {
            int _loc_1 =0;
            int _loc_2 =0;
            int _loc_3 =0;
            if (m_adjacentRoads != null)
            {
                return;
            }
            initalUpdateAdjacentCalculations();
            for(int i0 = 0; i0 < m_adjacentStretch.size(); i0++)
            {
            	_loc_1 = m_adjacentStretch.get(i0);

                m_adjacentRoads.put(_loc_1,  null);
            }
            m_adjacentStretch = new Array();
            for(int i0 = 0; i0 < m_adjacentTight.size(); i0++)
            {
            	_loc_1 = m_adjacentTight.get(i0);

                m_adjacentRoads.put(_loc_1,  null);
            }
            m_adjacentTight = new Array();
            _loc_2 = 0;
            while (_loc_2 < m_adjacentRoads.length())
            {

                _loc_3 = _loc_2;
                if (_loc_3 == LEFT || _loc_3 == RIGHT)
                {
                    m_adjacentRoads.put(_loc_3,  null);
                    m_adjacentPartial.splice(_loc_2, 1);
                }
                _loc_2++;
            }
            return;
        }//end

         public void  drawDisplayObject ()
        {
            GMPlaceBridge _loc_1 =null ;
            super.drawDisplayObject();
            if (this.isCurrentImageLoading())
            {
                return;
            }
            if (Global.world.getTopGameMode() instanceof GMPlaceBridge)
            {
                _loc_1 =(GMPlaceBridge) Global.world.getTopGameMode();
                _loc_1.setupGhostBridgeImage();
            }
            if (!this.m_validBridgeEnd)
            {
                return;
            }
            return;
        }//end

         public String  getToolTipHeader ()
        {
            return ZLoc.t("Items", "bridge_standard_friendlyName");
        }//end

         public Vector2  getToolTipScreenOffset ()
        {
            double _loc_2 =0;
            Vector3 _loc_3 =null ;
            Point _loc_4 =null ;
            Vector3 _loc_5 =null ;
            Point _loc_6 =null ;
            double _loc_7 =0;
            _loc_1 = this.bridgeParts;
            this.m_toolTipOffset = this.m_toolTipOffset ? (this.m_toolTipOffset) : (new Vector2());
            if (_loc_1.length())
            {
                _loc_2 = Global.gameSettings().getNumber("maxZoom");
                _loc_3 = getToolTipPosition();
                _loc_4 = IsoMath.tilePosToPixelPos(_loc_3.x, _loc_3.y);
                _loc_4 = IsoMath.viewportToStage(_loc_4);
                _loc_5 = ((BridgePart)_loc_1.get((_loc_1.length - 1))).getPosition();
                _loc_6 = IsoMath.tilePosToPixelPos(_loc_5.x, _loc_5.y);
                _loc_6 = IsoMath.viewportToStage(_loc_6);
                _loc_7 = (_loc_6.x - _loc_4.x) * GlobalEngine.viewport.getZoom() / _loc_2;
                this.m_toolTipOffset.x = _loc_7 / 2;
            }
            return this.m_toolTipOffset;
        }//end

         public boolean  cornerShouldAdjust (Road param1 ,int param2 )
        {
            return false;
        }//end

         public boolean  shouldAdjust (Road param1 ,int param2 )
        {
            return false;
        }//end

         public Road  prepareToDrop (Vector3 param1 ,Vector2 param2 )
        {
            return this;
        }//end

         public boolean  isPositionValid ()
        {
            return false;
        }//end

        public Array  bridgeParts ()
        {
            return this.m_bridgeParts;
        }//end

        public boolean  isValidBridgePosition ()
        {
            _loc_1 = Bridge.isBridgeInValidExpansionByItem(m_item,m_position.x,m_position.y);
            this.m_validExpansionStart = _loc_1;
            this.m_validBridgeStart = _loc_1;
            boolean _loc_2 =false ;
            boolean _loc_3 =false ;
            this.m_validBridgeEnd = false;
            this.m_unlockExpansionRect = null;
            if (m_position.x < m_item.bridge.minX)
            {
                return false;
            }
            if (m_position.x > m_item.bridge.maxX)
            {
                return false;
            }
            if (!_loc_1)
            {
                return false;
            }
            this.m_unlockExpansionPoint = Bridge.getGrantedExpansionPointByItem(m_item, m_position.x, m_position.y);
            this.m_unlockExpansionRect = null;
            if (this.m_unlockExpansionPoint)
            {
                this.m_unlockExpansionRect = Global.world.territory.getClosestExpansionRect(new Vector2(this.m_unlockExpansionPoint.x, this.m_unlockExpansionPoint.y));
            }
            if (!this.checkPlacementRequirements(m_position.x, m_position.y))
            {
                this.m_validBridgeStart = false;
                return false;
            }
            this.m_bridgeEndSquare = Bridge.getBridgeEndPointByItem(m_item, m_position.x, m_position.y);
            this.m_bridgeEndSquare = Bridge.getBridgeEndPointByItem(m_item, m_position.x, m_position.y);
            if (this.m_bridgeEndSquare.equals(m_position))
            {
                return false;
            }
            return _loc_1;
        }//end

         public void  onBuildingConstructionCompleted_PostServerUpdate ()
        {
            String _loc_3 =null ;
            int _loc_4 =0;
            int _loc_5 =0;
            String _loc_6 =null ;
            Global.player.setAllowRightSideBuild();
            GameQuestCallbacks.cycleAnnouncers();
            int _loc_1 =0;
            while (_loc_1 < this.getItem().bridge.bridgeParts.length())
            {

                _loc_3 = this.getItem().bridge.bridgeParts.get(_loc_1).type;
                _loc_4 = this.getItem().bridge.bridgeParts.get(_loc_1).x;
                _loc_5 = this.getItem().bridge.bridgeParts.get(_loc_1).y;
                _loc_6 = "";
                if (_loc_3 == "left")
                {
                    _loc_6 = this.getItem().bridge.leftPart;
                }
                if (_loc_3 == "center")
                {
                    _loc_6 = this.getItem().bridge.centerPart;
                }
                if (_loc_3 == "right")
                {
                    _loc_6 = this.getItem().bridge.rightPart;
                }
                this.addBridgePart(placeBridgePart(_loc_6, _loc_4, _loc_5));
                _loc_1++;
            }
            Global.world.citySim.roadManager.updateAllRoadTiles();
            this.fireBridgeMissiles(this, 0);
            _loc_2 = this.getRightBridgePart();
            if (_loc_2)
            {
                this.fireBridgeMissiles((MapResource)_loc_2, 3000);
            }
            Sounds.play("peopleMoveIn");
            return;
        }//end

        private void  fireBridgeMissiles (MapResource param1 ,int param2 =0,Event param3 =null )
        {
            inputMapResource = param1;
            timerOffset = param2;
            e = param3;
            TimerUtil .callLater (void  ()
            {
                Sounds.play("cruise_fireworks");
                return;
            }//end
            , timerOffset);
            TimerUtil .callLater (void  ()
            {
                MapResourceEffectFactory.createEffect(inputMapResource, EffectType.FIREWORK_BALLOONS);
                return;
            }//end
            , timerOffset);
            TimerUtil .callLater (void  ()
            {
                MapResourceEffectFactory.createEffect(inputMapResource, EffectType.FIREWORK_BALLOONS);
                return;
            }//end
            , timerOffset + 300);
            TimerUtil .callLater (void  ()
            {
                MapResourceEffectFactory.createEffect(inputMapResource, EffectType.FIREWORK_BALLOONS);
                return;
            }//end
            , timerOffset + 500);
            TimerUtil .callLater (void  ()
            {
                MapResourceEffectFactory.createEffect(inputMapResource, EffectType.FIREWORK_BALLOONS);
                return;
            }//end
            , timerOffset + 700);
            TimerUtil .callLater (void  ()
            {
                MapResourceEffectFactory.createEffect(inputMapResource, EffectType.FIREWORK_BALLOONS);
                return;
            }//end
            , timerOffset + 900);
            TimerUtil .callLater (void  ()
            {
                MapResourceEffectFactory.createEffect(inputMapResource, EffectType.FIREWORK_BALLOONS);
                return;
            }//end
            , timerOffset + 1000);
            TimerUtil .callLater (void  ()
            {
                MapResourceEffectFactory.createEffect(inputMapResource, EffectType.FIREWORK_BALLOONS);
                return;
            }//end
            , timerOffset + 1200);
            TimerUtil .callLater (void  ()
            {
                Sounds.play("cruise_fireworks");
                return;
            }//end
            , timerOffset + 1500);
            TimerUtil .callLater (void  ()
            {
                MapResourceEffectFactory.createEffect(inputMapResource, EffectType.FIREWORK_BALLOONS);
                return;
            }//end
            , timerOffset + 1600);
            TimerUtil .callLater (void  ()
            {
                MapResourceEffectFactory.createEffect(inputMapResource, EffectType.FIREWORK_BALLOONS);
                return;
            }//end
            , timerOffset + 1300);
            TimerUtil .callLater (void  ()
            {
                MapResourceEffectFactory.createEffect(inputMapResource, EffectType.FIREWORK_BALLOONS);
                return;
            }//end
            , timerOffset + 1900);
            TimerUtil .callLater (void  ()
            {
                MapResourceEffectFactory.createEffect(inputMapResource, EffectType.FIREWORK_BALLOONS);
                return;
            }//end
            , timerOffset + 2100);
            TimerUtil .callLater (void  ()
            {
                MapResourceEffectFactory.createEffect(inputMapResource, EffectType.FIREWORK_BALLOONS);
                return;
            }//end
            , timerOffset + 2300);
            TimerUtil .callLater (void  ()
            {
                MapResourceEffectFactory.createEffect(inputMapResource, EffectType.FIREWORK_BALLOONS);
                return;
            }//end
            , timerOffset + 2500);
            TimerUtil .callLater (void  ()
            {
                MapResourceEffectFactory.createEffect(inputMapResource, EffectType.FIREWORK_BALLOONS);
                return;
            }//end
            , timerOffset + 3000);
            TimerUtil .callLater (void  ()
            {
                MapResourceEffectFactory.createEffect(inputMapResource, EffectType.FIREWORK_BALLOONS);
                return;
            }//end
            , timerOffset + 3200);
            TimerUtil .callLater (void  ()
            {
                MapResourceEffectFactory.createEffect(inputMapResource, EffectType.FIREWORK_BALLOONS);
                return;
            }//end
            , timerOffset + 3600);
            TimerUtil .callLater (void  ()
            {
                MapResourceEffectFactory.createEffect(inputMapResource, EffectType.FIREWORK_BALLOONS);
                return;
            }//end
            , timerOffset + 3300);
            return;
        }//end

         public void  notifyUserForExpansionsOnPlace (Rectangle param1 )
        {
            expansionRect = param1;
            UI.popupLock();
            GuideTile expansionOutline =new GuideTile(new Vector3(expansionRect.x ,expansionRect.y ),expansionRect.width ,expansionRect.height );
            expansionOutline.drawGuideTile(Constants.COLOR_VALID_PLACEMENT);
            expansionOutline .blinkTile (Global .gameSettings .getNumber ("freeExpansionBlinkerTime",0.75),Global .gameSettings .getInt ("freeExpansionNumBlinks",5),void  ()
            {
                UI.popupUnlock();
                return;
            }//end
            );
            ItemToaster toaster =new ItemToaster("",ZLoc.t("Dialogs","ToasterFreeExpansion"),Global.getAssetURL("assets/dialogs/toaster_edgar.png"));
            toaster.duration = 10000;
            Global.ui.toaster.show(toaster);
            return;
        }//end

        public static boolean  isBridgeInValidExpansionByName (String param1 ,int param2 ,int param3 )
        {
            _loc_4 =Global.gameSettings().getItemByName(param1 );
            return Bridge.isBridgeInValidExpansionByItem(_loc_4, param2, param3);
        }//end

        public static boolean  isBridgeInValidExpansionByItem (Item param1 ,int param2 ,int param3 )
        {
            String _loc_8 =null ;
            Array _loc_9 =null ;
            double _loc_10 =0;
            double _loc_11 =0;
            Vector2 _loc_4 =new Vector2(param2 ,param3 );
            _loc_5 = Global.world.territory.getClosestExpansionRect(_loc_4);
            _loc_6 = param1.bridge.validBuildExpansions.split(";");
            int _loc_7 =0;
            while (_loc_7 < _loc_6.length())
            {

                _loc_8 = _loc_6.get(_loc_7);
                _loc_9 = _loc_8.split("|");
                _loc_10 = int(_loc_9.get(0));
                _loc_11 = int(_loc_9.get(1));
                if (_loc_5.x == _loc_10 && _loc_5.y == _loc_11)
                {
                    return true;
                }
                _loc_7++;
            }
            return false;
        }//end

        public static Vector3  getGrantedExpansionPointByName (String param1 ,int param2 ,int param3 )
        {
            _loc_4 = Global.gameSettings().getItemByName(param1);
            return Bridge.getGrantedExpansionPointByItem(_loc_4, param2, param3);
        }//end

        public static Vector3  getGrantedExpansionPointByItem (Item param1 ,int param2 ,int param3 )
        {
            String _loc_8 =null ;
            Array _loc_9 =null ;
            double _loc_10 =0;
            double _loc_11 =0;
            Vector2 _loc_4 =new Vector2(param2 ,param3 );
            _loc_5 = Global.world.territory.getClosestExpansionRect(_loc_4);
            _loc_6 = param1.bridge.grantedExpansionsOnPlace.split(";");
            int _loc_7 =0;
            while (_loc_7 < _loc_6.length())
            {

                _loc_8 = _loc_6.get(_loc_7);
                _loc_9 = _loc_8.split("|");
                _loc_10 = int(_loc_9.get(0));
                _loc_11 = int(_loc_9.get(1));
                if (_loc_5.x == _loc_10 && _loc_5.y == _loc_11)
                {
                    return new Vector3(int(_loc_9.get(2)), int(_loc_9.get(3)), 0);
                }
                _loc_7++;
            }
            return null;
        }//end

        public static Vector3  getBridgeEndPointByName (String param1 ,int param2 ,int param3 )
        {
            _loc_4 = Global.gameSettings().getItemByName(param1);
            return Bridge.getBridgeEndPointByItem(_loc_4, param2, param3);
        }//end

        public static Vector3  getBridgeEndPointByItem (Item param1 ,int param2 ,int param3 )
        {
            Vector3 _loc_4 =new Vector3(param2 ,param3 ,0);
            boolean _loc_5 =false ;
            _loc_6 = param3-_loc_4.y +param1.sizeY ;
            Rectangle _loc_7 =new Rectangle(_loc_4.x ,_loc_4.y ,param1.sizeX ,param1.sizeY );
            while (_loc_6 <= MAXBRIDGELENGTH)
            {

                _loc_4.y = _loc_4.y - param1.sizeY;
                _loc_7.y = _loc_4.y;
                if (Global.world.citySim.waterManager.positionOnValidLand(_loc_7) && _loc_5)
                {
                    break;
                    continue;
                }
                _loc_5 = true;
            }
            if (_loc_6 > MAXBRIDGELENGTH)
            {
                _loc_4.x = param2;
                _loc_4.y = param3;
            }
            return _loc_4;
        }//end

        public static BridgePart  placeBridgePart (String param1 ,double param2 ,double param3 ,double param4 =0)
        {
            BridgePart _loc_5 =new BridgePart(param1 );
            _loc_5.setPosition(Math.round(param2), Math.round(param3), 0);
            _loc_5.setDirection(Math.round(param4));
            _loc_5.setOuter(Global.world);
            _loc_5.setVisible(true);
            _loc_5.attach();
            return _loc_5;
        }//end

    }




