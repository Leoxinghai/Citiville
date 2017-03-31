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

import Classes.doobers.*;
import Classes.inventory.*;
import Classes.util.*;
import Display.*;
import Engine.*;
import Engine.Classes.*;
import Engine.Helpers.*;
import Engine.Managers.*;
import Events.*;
import GameMode.*;
import Transactions.*;
//import flash.display.*;
//import flash.events.*;
//import flash.filters.*;
//import flash.geom.*;
//import flash.utils.*;
import org.aswing.*;
import com.xinghai.Debug;

    public class GameObject extends WorldObject implements IToolTipTarget
    {
        protected  String MOVE_OBJECT ="moveObject";
        protected  String ROTATE_OBJECT ="rotateObject";
        public  String PLAY_ACTION ="playAction";
        public  String VISIT_PLAY_ACTION ="visitPlayAction";
        public  String VISIT_REPLAY_ACTION ="visitReplayAction";
        public  String NO_ACTION ="noAction";
        protected JPanel m_customCursor ;
        protected JLabel m_customCursorLabel ;
        protected Sprite m_customCursorHolder ;
        protected JWindow m_customCursorWindow ;
        protected String m_actionMode ="noAction";
        protected int m_visitReplayLock =0;
        public  double DEFAULT_REPLAY_TIME =2;
        protected Array m_menuItems ;
        protected int m_clearLock =0;
        protected Array m_filters ;
        protected ActionBar m_actionBar ;
        protected boolean m_occluding ;
        protected DisplayObject m_arrow ;
        private DisplayObject m_objectIndicator ;
        protected int m_actionBarOffsetX ;
        protected int m_actionBarOffsetY ;
        protected int m_overrideActionBarY ;
        protected Function m_sellCallback =null ;
        protected Array m_statusQueue ;
        protected Array m_resourceChangesQueue ;
        protected int m_lastStatusRemovalTime ;
        protected boolean m_showHighlight ;
        protected boolean m_toolTipBlocked =false ;
        protected int m_numVisitorInteractions =0;
        protected int m_maxNumVisitorInteractions =1;
public static int ACTIONBAR_WIDTH =85;
public static int ACTIONBAR_HEIGHT =25;
        public static  String SELL_OBJECT ="sellObject";
        public static  boolean USE_CUSTOM_CURSORS =false ;
        public static  String TOOLTIP_TOP ="top";
public static double ARROW_SCALE =0.25;
public static int STATUS_DELAY =1000;
        public static  String MESSAGE_TYPE_GAIN_XP ="Status_HarvestXP";
        public static  String MESSAGE_TYPE_GAIN_COINS ="Status_HarvestCoins";
        public static  String MESSAGE_TYPE_SPEND_ENERGY ="SpendEnergy";
        public static  String MESSAGE_COMMODITY ="Commodity";
        public static  String MESSAGE_COINS ="Coins";
        public static  String MESSAGE_ENERGY ="Energy";
        public static  String MESSAGE_XP ="XP";
        public static  String MESSAGE_REP ="Rep";
        public static  String MESSAGE_POP ="Pop";
        public static  String MESSAGE_GAIN ="Gain";
        public static  String MESSAGE_SPEND ="Spend";

        public  GameObject ()
        {
            this.m_menuItems = new Array();
            this.m_filters = new Array();
            this.m_statusQueue = new Array();
            this.m_resourceChangesQueue = new Array();
            this.m_overrideActionBarY = 0;
            return;
        }//end

        public boolean  areVisitorInteractionsExhausted ()
        {
            return this.m_numVisitorInteractions >= this.m_maxNumVisitorInteractions;
        }//end

        public int  numVisitorInteractions ()
        {
            return this.m_numVisitorInteractions;
        }//end

        public void  numVisitorInteractions (int param1 )
        {
            this.m_numVisitorInteractions = param1;
            return;
        }//end

        public boolean  doesVisitActionCostEnergy ()
        {
            return true;
        }//end

         public void  detach ()
        {
            this.m_statusQueue = new Array();
            this.updateObjectIndicator();
            this.setActionProgress(false);
            super.detach();
            return;
        }//end

         public void  drawDisplayObject ()
        {
            super.drawDisplayObject();
            _loc_1 = (Sprite)m_displayObject
            if (_loc_1 == null)
            {
                createDisplayObject();
            }
            return;
        }//end

        public String  getToolTipStatus ()
        {
            return null;
        }//end

        public String  getToolTipHeader ()
        {
            return null;
        }//end

        public String  getToolTipNotes ()
        {
            return null;
        }//end

        public String  getToolTipAction ()
        {
            return null;
        }//end

        public boolean  toolTipFollowsMouse ()
        {
            return false;
        }//end

        public boolean  getToolTipVisibility ()
        {
            return !this.m_toolTipBlocked;
        }//end

        public Vector3  getToolTipPosition ()
        {
            return super.getPosition();
        }//end

        public Vector2  getToolTipScreenOffset ()
        {
            return null;
        }//end

        public Vector3  getDimensions ()
        {
            return null;
        }//end

        public int  getToolTipFloatOffset ()
        {
            return 0;
        }//end

        public ToolTipSchema  getToolTipSchema ()
        {
            return null;
        }//end

        public String  actionMode ()
        {
            return this.m_actionMode;
        }//end

        protected void  updateActionBarPosition ()
        {
            Point _loc_1 =null ;
            if (this.m_actionBar && this.m_actionBar.visible)
            {
                _loc_1 = IsoMath.tilePosToPixelPos(m_position.x, m_position.y);
                _loc_1.y = _loc_1.y - (this.m_overrideActionBarY == 0 && m_displayObject ? (m_displayObject.height) : (this.m_overrideActionBarY));
                _loc_1 = IsoMath.viewportToStage(_loc_1);
                this.m_actionBar.x = _loc_1.x - ACTIONBAR_WIDTH / 2 + this.m_actionBarOffsetX;
                this.m_actionBar.y = _loc_1.y - ACTIONBAR_HEIGHT + this.m_actionBarOffsetY;
            }
            return;
        }//end

        public void  setActionBarOverrideY (int param1 )
        {
            this.m_overrideActionBarY = param1;
            return;
        }//end

        protected void  drawActionBar ()
        {
            if (this.m_actionBar)
            {
                this.m_actionBar.redraw();
                if (!this.m_actionBar.visible)
                {
                    this.m_actionBar.visible = true;
                }
                this.updateActionBarPosition();
            }
            return;
        }//end

        public void  setActionProgress (boolean param1 ,String param2 ="",double param3 =0,boolean param4 =true )
        {
            _loc_5 = this.m_actionBar!= null;
            if (param1 && !_loc_5)
            {
                this.m_actionBar = new ActionBar(ACTIONBAR_WIDTH, ACTIONBAR_HEIGHT, param2, param4);
                this.m_actionBar.visible = false;
                GlobalEngine.viewport.hudBase.addChild(this.m_actionBar);
            }
            if (param1 && (this.m_actionBar.progress != param3 || this.m_actionBar.prefix != param2))
            {
                this.m_actionBar.progress = param3;
                this.m_actionBar.prefix = param2;
                this.m_actionBar.showPercent = param4;
                this.drawActionBar();
            }
            if (!param1 && _loc_5)
            {
                this.m_actionBar.parent.removeChild(this.m_actionBar);
                this.m_actionBar = null;
            }
            this.updateAlpha();
            return;
        }//end

        public void  setActionBarOffset (int param1 ,int param2 )
        {
            this.m_actionBarOffsetX = param1;
            this.m_actionBarOffsetY = param2;
            return;
        }//end

        public double  onVisitReplayAction (TRedeemVisitorHelpAction param1 )
        {
            this.m_visitReplayLock--;
            this.m_actionMode = this.VISIT_REPLAY_ACTION;
            return this.DEFAULT_REPLAY_TIME;
        }//end

        public void  onVisitPlayAction ()
        {
            this.m_actionMode = this.VISIT_PLAY_ACTION;
            return;
        }//end

         public boolean  hasValidId ()
        {
            boolean _loc_1 =true ;
            if (m_id == 0)
            {
                _loc_1 = false;
            }
            return _loc_1;
        }//end

        public void  showObjectBusy ()
        {
            this.displayStatus(ZLoc.t("Main", "ObjectBusy"));
            StatsManager.count("errors", "has_valid_id_is_zero");
            return;
        }//end

         public void  onUpdate (double param1 )
        {
            if (!GameWorld.skipEngineObjectUpdates)
            {
                super.onUpdate(param1);
            }
            this.pumpDisplayStatusQueue();
            return;
        }//end

        public void  doResourceChanges (double param1 =0,double param2 =0,double param3 =0,double param4 =0,String param5 ="",boolean param6 =true ,boolean param7 =true ,boolean param8 =true ,double param9 =0,double param10 =0,Array param11 =null )
        {
            String _loc_12 ="";
            String _loc_13 ="";
            String _loc_14 ="";
            _loc_15 = null;
            Object _loc_16 =null ;

            Debug.debug4("GameObject."+"doResourceChanges");


            if (param5 == "")
            {
                param5 = ZLoc.t("Main", "DefaultCommodityName");
            }
            else if (param5 == "goods")
            {
                param5 = ZLoc.t("Main", "DefaultCommodityName");
            }
            else if (param5 == "premium_goods")
            {
                param5 = ZLoc.t("Main", "Commodity_premium_goods_friendlyName");
            }
            if (param1 != 0)
            {
                _loc_14 = MESSAGE_ENERGY;
                _loc_13 = param1 < 0 ? (MESSAGE_SPEND) : (MESSAGE_GAIN);
                _loc_16 = param1 < 0 ? (16738816) : (43520);
                _loc_12 = _loc_12 + ZLoc.t("Main", _loc_13 + _loc_14, {energy:param1});
                if (param8)
                {
                    _loc_15 = new EmbeddedArt.smallEnergyIcon();
                }
                if (param7)
                {
                    Global.player.updateEnergy(param1, param11);
                }
            }
            if (param4 != 0)
            {
                _loc_14 = MESSAGE_COMMODITY;
                _loc_13 = param4 < 0 ? (MESSAGE_SPEND) : (MESSAGE_GAIN);
                _loc_16 = param4 < 0 ? (16738816) : (16777215);
                _loc_12 = _loc_12 + ZLoc.t("Main", _loc_13 + _loc_14, {amount:param4, commodity:param5});
                if (_loc_15 == null && param8)
                {
                    _loc_15 = new EmbeddedArt.smallGoodsIcon();
                }
            }
            if (param6)
            {
                this.displayStatus(_loc_12, _loc_15);
            }
            else
            {
                this.enqueueResourceChange(_loc_12, _loc_15);
            }
            _loc_12 = "";
            _loc_15 = null;
            if (param2 != 0)
            {
                _loc_14 = MESSAGE_COINS;
                _loc_13 = param2 < 0 ? (MESSAGE_SPEND) : (MESSAGE_GAIN);
                _loc_16 = param2 < 0 ? (16777215) : (43520);
                _loc_12 = _loc_12 + ZLoc.t("Main", _loc_13 + _loc_14, {coins:param2});
                if (param8)
                {
                    _loc_15 = new EmbeddedArt.smallCoinIcon();
                }
                if (param7)
                {
                    Global.player.gold = Global.player.gold + param2;
                }
            }
            if (param3 != 0)
            {
                _loc_14 = MESSAGE_XP;
                _loc_13 = MESSAGE_GAIN;
                _loc_16 = 16777215;
                _loc_12 = _loc_12 + ZLoc.t("Main", _loc_13 + _loc_14, {experience:param3});
                if (_loc_15 == null && param8)
                {
                    _loc_15 = new EmbeddedArt.smallXPIcon();
                }
                if (param7)
                {
                    Global.player.xp = Global.player.xp + param3;
                }
            }
            if (param9 != 0)
            {
                _loc_14 = MESSAGE_REP;
                _loc_13 = MESSAGE_GAIN;
                _loc_16 = 16777215;
                _loc_12 = _loc_12 + ZLoc.t("Main", _loc_13 + _loc_14, {reputation:param9});
                if (param7)
                {
                    Global.player.socialXp = Global.player.socialXp + param9;
                }
            }
            if (param10 != 0)
            {
                _loc_14 = MESSAGE_POP;
                _loc_13 = MESSAGE_GAIN;
                _loc_16 = 16777215;
                _loc_12 = _loc_12 + ZLoc.t("Main", _loc_13 + _loc_14, {population:param10});
            }
            if (param6)
            {
                this.displayStatus(_loc_12, _loc_15, _loc_16);
            }
            else
            {
                this.enqueueResourceChange(_loc_12, _loc_15, _loc_16);
            }
            return;
        }//end

        public void  doEnergyChanges (double param1 ,Array param2 ,boolean param3 =true ,boolean param4 =true ,boolean param5 =true )
        {
            this.doResourceChanges(param1, 0, 0, 0, "", param3, param4, param5, 0, 0, param2);
            return;
        }//end

        protected void  enqueueResourceChange (String param1 ,Object param2 ,Object param3 =16777215)
        {
            this.m_resourceChangesQueue.push({message:param1, icon:param2, color:param3});
            return;
        }//end

        protected void  displayDelayedResourceChanges ()
        {
            Object _loc_1 =null ;
            for(int i0 = 0; i0 < this.m_resourceChangesQueue.size(); i0++)
            {
            		_loc_1 = this.m_resourceChangesQueue.get(i0);

                this.displayStatus(_loc_1.message, _loc_1.icon, _loc_1.color);
            }
            this.m_resourceChangesQueue.splice(0, this.m_resourceChangesQueue.length());
            return;
        }//end

        public void  displayStatusGeneric (String param1 , Object param2)
        {
            _loc_3 = ZLoc.t("Main",param1,{valueparam2});
            _loc_4 =             "";
            switch(param1)
            {
                case MESSAGE_TYPE_GAIN_COINS:
                {
                    _loc_4 = new EmbeddedArt.smallCoinIcon();
                    break;
                }
                default:
                {
                    break;
                }
            }
            this.displayStatus(_loc_3, _loc_4);
            return;
        }//end

        public void  displayStatus (String param1 ,Object param2 ="",Object param3 =16777215)
        {
            MessageDef _loc_5 =null ;
            _loc_4 = this.displayStatusStartPos();
            if (param2 == "")
            {
                for(int i0 = 0; i0 < this.m_statusQueue.size(); i0++)
                {
                		_loc_5 = this.m_statusQueue.get(i0);

                    if (_loc_5.message == param1)
                    {
                        return;
                    }
                }
            }
            this.m_statusQueue.unshift(new MessageDef(param1, _loc_4, param2, param3));
            Global.ui.hideToolTip(this);
            this.m_toolTipBlocked = true;
            return;
        }//end

        public void  displayStatusComponent (DisplayObject param1 )
        {
            MessageDef _loc_3 =null ;
            _loc_2 = this.displayStatusStartPos();
            for(int i0 = 0; i0 < this.m_statusQueue.size(); i0++)
            {
            	_loc_3 = this.m_statusQueue.get(i0);

                if (_loc_3.displayObj == param1)
                {
                    return;
                }
            }
            this.m_statusQueue.unshift(new MessageDef(null, _loc_2, null, null, param1));
            Global.ui.hideToolTip(this);
            this.m_toolTipBlocked = true;
            return;
        }//end

        protected void  pumpDisplayStatusQueue ()
        {
            MessageDef _loc_1 =null ;
            if (this.m_statusQueue.length > 0 && getTimer() - this.m_lastStatusRemovalTime > STATUS_DELAY)
            {
                this.m_lastStatusRemovalTime = getTimer();
                _loc_1 = this.m_statusQueue.pop();
                if (_loc_1.displayObj == null)
                {
                    UI.displayStatus(_loc_1.message, _loc_1.location.x, _loc_1.location.y, _loc_1.iconDef, null, 1.5, 32, 16, 60, _loc_1.msgColor);
                }
                else
                {
                    UI.displayStatusComponent(_loc_1.displayObj, _loc_1.location.x, _loc_1.location.y, 2, 32);
                }
            }
            if (this.m_toolTipBlocked && this.m_statusQueue.length <= 0 && getTimer() - this.m_lastStatusRemovalTime > UI.DEFAULT_STATUS_TWEEN_DURATION * 1000)
            {
                this.m_toolTipBlocked = false;
                if (Global.world.getTopGameMode() instanceof GMDefault)
                {
                    this.m_toolTipBlocked = false;
                    ((GMDefault)Global.world.getTopGameMode()).updateObjectUnderMouse();
                }
            }
            return;
        }//end

        protected Point  displayStatusStartPos ()
        {
            _loc_1 = IsoMath.tilePosToPixelPos(m_position.x,m_position.y);
            if (m_displayObject)
            {
                _loc_1.y = _loc_1.y - m_displayObject.height * 1.1;
            }
            return IsoMath.viewportToStage(_loc_1);
        }//end

        public Point  getMenuPosition (Array param1 )
        {
            return null;
        }//end

        public void  onMenuClick (MouseEvent event )
        {
            return;
        }//end

        public void  onObjectDrag (Vector3 param1 )
        {
            return;
        }//end

        public void  onObjectDrop (Vector3 param1 )
        {
            this.showOccluder();
            return;
        }//end

        public void  onObjectDropPreTansaction (Vector3 param1 )
        {
            return;
        }//end

        public void  onSelect ()
        {
            return;
        }//end

        public void  onDeselect ()
        {
            return;
        }//end

        public void  move ()
        {
            Global.world.addGameMode(new GMObjectMove(this), false);
            return;
        }//end

        public void  onAddCrewMember ()
        {
            return;
        }//end

        public void  onPlayAction ()
        {
            if (this.m_visitReplayLock > 0)
            {
                return;
            }
            this.m_actionMode = this.PLAY_ACTION;
            return;
        }//end

        public void  onPanned ()
        {
            return;
        }//end

        public void  finalizeAndAwardVisitorHelp (String param1 ,int param2 =0,int param3 =0,int param4 =0)
        {
            Array _loc_5 =null ;
            GameTransactionManager.addTransaction(new TPerformVisitorHelp(Global.world.ownerId, this, param1));
            if (Global.world.dooberManager.isDoobersEnabled())
            {
                _loc_5 = new Array();
                if (param2 > 0)
                {
                    _loc_5.push(.get(Global.gameSettings().getDooberFromType(Doober.DOOBER_REP, param2), param2));
                }
                if (param3 > 0)
                {
                    _loc_5.push(.get(Global.gameSettings().getDooberFromType(Doober.DOOBER_COIN, param3), param3));
                }
                if (param4 > 0)
                {
                    _loc_5.push(.get(Global.gameSettings().getDooberFromType(Doober.DOOBER_GOODS, param4), param4));
                }
                Global.world.dooberManager.createBatchDoobers(_loc_5, null, m_position.x, m_position.y);
            }
            else
            {
                Global.player.socialXp = Global.player.socialXp + param2;
                Global.player.gold = Global.player.gold + param3;
                Global.player.commodities.add(Commodities.GOODS_COMMODITY, param4);
            }
            return;
        }//end

        public void  lockForReplay ()
        {
            this.m_visitReplayLock++;
            return;
        }//end

        public void  unlockForReplay ()
        {
            this.m_visitReplayLock--;
            return;
        }//end

        public int  replayLock ()
        {
            return this.m_visitReplayLock;
        }//end

        public void  sell ()
        {
            if (this.isSellable())
            {
                UI.displayMessage(ZLoc.t("Main", "SellObject"), GenericPopup.TYPE_YESNO, this.sellConfirmationHandler, "sellDialog");
            }
            return;
        }//end

        protected int  getSellPrice ()
        {
            return 0;
        }//end

        public boolean  isSellable ()
        {
            return false;
        }//end

        protected boolean  isObjectIndicatorVisible ()
        {
            return false;
        }//end

        public void  updateObjectIndicator ()
        {
            _loc_1 = this.isObjectIndicatorVisible();
            _loc_2 = this.m_objectIndicator!= null;
            if (_loc_1 && !_loc_2)
            {
                this.m_objectIndicator = this.generateObjectIndicator();
                this.updateObjectIndicatorPosition();
                GlobalEngine.viewport.hudBase.addChild(this.m_objectIndicator);
            }
            if (!_loc_1 && _loc_2)
            {
                GlobalEngine.viewport.hudBase.removeChild(this.m_objectIndicator);
                this.m_objectIndicator = null;
            }
            if (_loc_1 && _loc_2)
            {
                this.updateObjectIndicatorPosition();
            }
            return;
        }//end

        protected DisplayObject  generateObjectIndicator ()
        {
            return new EmbeddedArt.visitEnergyIcon();
        }//end

        private void  updateObjectIndicatorPosition (String param1)
        {
            Point _loc_2 =null ;
            if (this.m_objectIndicator)
            {
                _loc_2 = IsoMath.tilePosToPixelPos(m_position.x, m_position.y);
                if (m_displayObject)
                {
                    _loc_2.y = _loc_2.y - m_displayObject.height / 2;
                }
                _loc_2 = IsoMath.viewportToStage(_loc_2);
                if (param1 == null || param1 == "")
                {
                    _loc_2.y = _loc_2.y - this.m_objectIndicator.height / 2;
                }
                else
                {
                    _loc_2.y = _loc_2.y - this.m_objectIndicator.height;
                }
                if (_loc_2.x != this.m_objectIndicator.x || _loc_2.y != this.m_objectIndicator.y)
                {
                    this.m_objectIndicator.x = _loc_2.x;
                    this.m_objectIndicator.y = _loc_2.y - this.m_objectIndicator.height;
                }
            }
            return;
        }//end

        protected void  setArrowPosition ()
        {
            Point _loc_1 =null ;
            Sprite _loc_2 =null ;
            if (this.m_arrow && m_displayObject)
            {
                _loc_1 = new Point();
                _loc_2 =(Sprite) m_displayObject;
                this.m_arrow.scaleX = ARROW_SCALE / m_displayObject.scaleX;
                this.m_arrow.scaleY = ARROW_SCALE / m_displayObject.scaleY;
                if (_loc_2.getChildAt(0))
                {
                    _loc_1.y = -this.m_arrow.height;
                    _loc_1.x = (_loc_2.getChildAt(0).width - this.m_arrow.width) / 2;
                    if (_loc_1.x != this.m_arrow.x || _loc_1.y != this.m_arrow.y)
                    {
                        this.m_arrow.y = _loc_1.y;
                        this.m_arrow.x = _loc_1.x;
                    }
                }
            }
            return;
        }//end

         public void  setHighlighted (boolean param1 ,int param2 =1.67552e +007)
        {
            if (m_displayObject)
            {
                m_highlighted = param1;
                if (param1 && this.canShowHighlight)
                {
                    m_displayObject.filters = .get(new GlowFilter(this.getHighlightColor(), 1, 8, 8, 20));
                }
                else
                {
                    m_displayObject.filters = this.m_filters.concat([]);
                }
                renderBufferDirty = true;
            }
            return;
        }//end

        public void  setShowHighlight (boolean param1 )
        {
            this.m_showHighlight = param1;
            return;
        }//end

        public boolean  canShowHighlight ()
        {
            return this.m_showHighlight;
        }//end

        public int  getHighlightColor ()
        {
            return EmbeddedArt.HIGHLIGHT_COLOR;
        }//end

        public boolean  isLocked ()
        {
            return this.m_clearLock > 0;
        }//end

        public void  lock ()
        {
            this.m_clearLock++;
            this.updateAlpha();
            return;
        }//end

        public void  unlock ()
        {
            this.m_clearLock--;
            this.updateAlpha();
            return;
        }//end

        public boolean  isOccluder ()
        {
            return this.m_occluding;
        }//end

        public void  hideOccluder ()
        {
            this.m_occluding = true;
            this.updateAlpha();
            return;
        }//end

        public void  showOccluder ()
        {
            this.m_occluding = false;
            this.updateAlpha();
            return;
        }//end

        public void  updateAlpha ()
        {
            if (this.m_clearLock > 0)
            {
                this.alpha = 0.6;
            }
            else if (this.m_occluding)
            {
                this.alpha = 0.33;
            }
            else
            {
                this.alpha = 1;
            }
            return;
        }//end

        public void  onSell (GameObject param1)
        {
            if (this.m_sellCallback != null)
            {
                this.m_sellCallback(this);
            }
            return;
        }//end

        public boolean  canBeDragged ()
        {
            return true;
        }//end

        public boolean  canBeRotated ()
        {
            return true;
        }//end

        public int  getSnapX ()
        {
            return 1;
        }//end

        public int  getSnapY ()
        {
            return 1;
        }//end

        protected void  sellNow ()
        {
            int _loc_1 =0;
            if (this.isSellable())
            {
                GameTransactionManager.addTransaction(new TSell(this));
                this.detach();
                cleanup();
                this.onSell();
                _loc_1 = this.getSellPrice();
                if (_loc_1 > 0)
                {
                    Global.player.gold = Global.player.gold + _loc_1;
                }
            }
            return;
        }//end

        protected void  sellConfirmationHandler (GenericPopupEvent event )
        {
            int _loc_3 =0;
            _loc_2 = event.button==GenericPopup.YES;
            if (_loc_2 == true)
            {
                if (this.isSellable())
                {
                    GameTransactionManager.addTransaction(new TSell(this));
                    this.detach();
                    cleanup();
                    this.onSell();
                    _loc_3 = this.getSellPrice();
                    Global.player.gold = Global.player.gold + _loc_3;
                    this.displayStatus(ZLoc.t("Main", "Status_ObjectSold", {coins:_loc_3}));
                }
            }
            return;
        }//end

        public void  registerSellCallback (Function param1 )
        {
            this.m_sellCallback = param1;
            return;
        }//end

        public void  preDisableMove (String param1 ,int param2 )
        {
            return;
        }//end

        public void  postDisableMove (Vector3 param1 )
        {
            return;
        }//end

        public Object  addTMoveParams (Object param1 )
        {
            return param1;
        }//end

         public void  centerOn ()
        {
            Global.world.centerOnObject(this);
            return;
        }//end

        public boolean  isVisitorInteractable ()
        {
            return false;
        }//end

        public boolean  unlimitedVisits ()
        {
            return false;
        }//end

        public boolean  overrideCursor ()
        {
            return Global.world.isEditMode ? (false) : (true);
        }//end

        public Object  getCustomCursor ()
        {
            return null;
        }//end

        public ActionBar  actionBar ()
        {
            return this.m_actionBar;
        }//end

        public Vector3  centerPosition ()
        {
            Vector3 _loc_1 =new Vector3(positionX +sizeX /2,positionY +sizeY /2,positionZ +sizeZ /2);
            return _loc_1;
        }//end

    }

import flash.display.*;
import flash.events.*;
import flash.filters.*;
import flash.geom.*;
import flash.utils.*;

class MessageDef
    public DisplayObject displayObj ;
    public String message ;
    public Point location ;
    public Object iconDef ;
    public Object msgColor ;

     MessageDef (String param1 ,Point param2 ,Object param3 ,Object param4 ,DisplayObject param5 =null )
    {
        this.message = param1;
        this.displayObj = param5;
        this.location = param2;
        this.iconDef = param3;
        this.msgColor = param4;
        return;
    }//end





