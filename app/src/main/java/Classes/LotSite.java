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
import Display.LotUI.*;
import Display.MarketUI.*;
import Engine.*;
import Engine.Helpers.*;
//import flash.display.*;
//import flash.events.*;
//import flash.geom.*;

    public class LotSite extends MapResource
    {
        protected  String TYPE_NAME ="lot_site";
        protected  String STATE_STATIC ="static";
        protected DisplayObject m_pick ;
        protected boolean m_isListeningToRender ;
        public static  String LOT_NAME ="biz_lotsite_4x4";

        public  LotSite (String param1 )
        {
            super(param1);
            m_objectType = WorldObjectTypes.LOT_SITE;
            m_typeName = this.TYPE_NAME;
            setState(this.STATE_STATIC);
            if (Global.isVisiting())
            {
                addAnimatedEffect(EffectType.SPARKLE);
            }
            return;
        }//end

        public String  targetType ()
        {
            return m_item.type;
        }//end

        public DisplayObject  getPick ()
        {
            return this.m_pick;
        }//end

        public void  setPick (DisplayObject param1 )
        {
            this.removePick();
            this.m_pick = param1;
            this.attachPick();
            return;
        }//end

        public void  removePick ()
        {
            if (this.m_pick && this.m_pick.parent)
            {
                this.m_pick.parent.removeChild(this.m_pick);
                this.cleanUpPick();
            }
            this.m_pick = null;
            return;
        }//end

        public void  updatePick ()
        {
            if (this.m_pick && this.m_pick.parent)
            {
                this.m_pick.parent.setChildIndex(this.m_pick, (this.m_pick.parent.numChildren - 1));
            }
            return;
        }//end

        protected void  attachPick ()
        {
            if (m_content && this.m_pick && this.m_pick.parent != m_displayObject && !GlobalEngine.viewport.hudBase.contains(this.m_pick))
            {
                GlobalEngine.viewport.hudBase.addChild(this.m_pick);
                this.addRenderListener();
                m_isHighlightable = true;
            }
            else if (!Global.isVisiting())
            {
            }
            return;
        }//end

         public boolean  isPixelInside (Point param1 )
        {
            SlidePickGroup _loc_2 =null ;
            if (this.m_pick && this.m_pick instanceof SlidePickGroup)
            {
                _loc_2 =(SlidePickGroup) this.m_pick;
                if (_loc_2.isTopSlidePickOpened)
                {
                    return true;
                }
            }
            return super.isPixelInside(param1);
        }//end

        protected void  setPickPosition ()
        {
            Point _loc_1 =new Point (-30,-150);
            _loc_2 = IsoMath.tilePosToPixelPos(m_position.x,m_position.y);
            _loc_2 = IsoMath.viewportToStage(_loc_2);
            this.m_pick.x = _loc_2.x + _loc_1.x;
            this.m_pick.y = _loc_2.y + _loc_1.y;
            return;
        }//end

        protected void  addRenderListener ()
        {
            if (!this.m_isListeningToRender && Global.stage != null)
            {
                Global.stage.addEventListener(Event.RENDER, this.renderHandler, false, 0, true);
                this.m_isListeningToRender = true;
                this.setPickPosition();
            }
            return;
        }//end

        protected void  removeRenderListeners ()
        {
            if (Global.stage != null)
            {
                Global.stage.removeEventListener(Event.RENDER, this.renderHandler);
            }
            this.m_isListeningToRender = false;
            return;
        }//end

        protected void  renderHandler (Event event )
        {
            this.setPickPosition();
            return;
        }//end

         public void  onPlayAction ()
        {
            super.onPlayAction();
            if (this.m_pick && this.m_pick.hasOwnProperty("onClick"))
            {
                this.m_pick.get("onClick")();
            }
            else if (!Global.isVisiting())
            {
                FranchiseViralManager.triggerFranchiseViralFeeds(FranchiseViralManager.VIRAL_EMPTYLOT);
            }
            return;
        }//end

         public void  drawDisplayObject ()
        {
            super.drawDisplayObject();
            this.attachPick();
            return;
        }//end

         public void  onUpdate (double param1 )
        {
            super.onUpdate(param1);
            this.attachPick();
            return;
        }//end

         public void  cleanUp ()
        {
            this.removePick();
            super.cleanUp();
            return;
        }//end

        private void  cleanUpPick ()
        {
            if (this.m_pick && this.m_pick.hasOwnProperty("cleanUp"))
            {
                m_pick.get("cleanUp")();
            }
            this.removeRenderListeners();
            return;
        }//end

         public Vector3  getToolTipPosition ()
        {
            double _loc_2 =0;
            _loc_1 = getPosition();
            if (this.m_pick && this.m_pick.visible)
            {
                _loc_2 = 3;
                _loc_1.x = _loc_1.x + _loc_2;
                _loc_1.y = _loc_1.y + _loc_2;
            }
            return _loc_1;
        }//end

         public boolean  isSellable ()
        {
            return true;
        }//end

         public void  onSell (GameObject param1)
        {
            Global.world.citySim.lotManager.removeLotSite(this);
            super.onSell(param1);
            return;
        }//end

         public void  onObjectDrop (Vector3 param1 )
        {
            super.onObjectDrop(param1);
            Global.world.citySim.lotManager.addLotSite(this);
            return;
        }//end

        public void  remove ()
        {
            this.detach();
            this.cleanUp();
            return;
        }//end

         public void  onMouseOver (MouseEvent event )
        {
            super.onMouseOver(event);
            this.updatePick();
            return;
        }//end

         public String  getToolTipStatus ()
        {
            String _loc_1 =null ;
            if (!Global.isVisiting())
            {
                if (this.m_pick)
                {
                    _loc_1 = ZLoc.t("Main", "BusinessZone1HomeRequest");
                }
                else
                {
                    _loc_1 = ZLoc.t("Main", "BusinessZone1Home");
                }
            }
            else
            {
                _loc_1 = ZLoc.t("Main", "BusinessZone1Visit");
            }
            return _loc_1;
        }//end

         public boolean  getToolTipVisibility ()
        {
            return true;
        }//end

         public void  onBuildingConstructionCompleted_PreServerUpdate ()
        {
            FranchiseViralManager.triggerFranchiseViralFeeds(FranchiseViralManager.VIRAL_EMPTYLOT);
            return;
        }//end

         public boolean  isEmptyLot ()
        {
            return true;
        }//end

        public static void  onFranchisePendingClick ()
        {
            LotSite _loc_3 =null ;
            _loc_1 = Global.world.getObjectsByTypes(.get(WorldObjectTypes.LOT_SITE));
            if (BuyLogic.isAtMaxFranchiseCount())
            {
                return;
            }
            if (_loc_1.length == 0)
            {
                Global.guide.notify("FranchiseRequestEligible");
                return;
            }
            int _loc_2 =0;
            while (_loc_2 < _loc_1.length())
            {

                _loc_3 = _loc_1.get(_loc_2);
                if (_loc_3.m_pick != null)
                {
                    Global.world.centerOnIsoPosition(_loc_3.getPosition(), 0);
                }
                _loc_2++;
            }
            return;
        }//end

        public static boolean  shouldShowFranchiseMQIcon ()
        {
            return Global.world.citySim.lotManager.receivedPendingOrders.length > 0 && !Global.isVisiting() && BuyLogic.canAcceptFranchiseRequests(Global.player.uid);
        }//end

    }



