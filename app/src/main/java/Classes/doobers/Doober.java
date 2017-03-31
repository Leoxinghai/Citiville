package Classes.doobers;

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
import Classes.effects.*;
import Classes.gates.*;
import Classes.sim.*;
import Classes.util.*;
import Display.*;
import Display.Toaster.*;
import Display.hud.*;
import Display.hud.components.*;
import Engine.*;
import Engine.Classes.*;
import GameMode.*;
import com.greensock.*;
import com.greensock.easing.*;
//import flash.display.*;
//import flash.events.*;
//import flash.geom.*;
import com.xinghai.Debug;

    public class Doober extends MapResource
    {
        protected  String DOOBER ="doober";
        protected int m_dooberTimeout ;
        protected int m_dooberAutoTimeout ;
        protected boolean m_autoCollect ;
        protected  double DOOBER_GRAVITY =2;
        protected int m_amount ;
        private ArcMotion m_arcMotion ;
        protected double m_dooberElapsedTime =0;
        protected Point m_startPosition ;
        protected Point m_landPosition ;
        protected double m_initialYVelocity ;
        private boolean m_doneCompleteAnimation =false ;
        private double m_finalAnimationStartFrame =0;
        private boolean m_startedFinalAnimation =false ;
        protected int m_streakAmount ;
        protected Point m_collectableFlyToPoint ;
        protected Point m_collectableFlyToSize ;
        protected boolean m_timedOut =false ;
        protected boolean m_pixieDustAdded =false ;
        protected double m_zoom ;
        protected boolean m_isDummy ;
        protected Point m_flyToScreenPoint ;
        protected boolean m_isFlying =false ;
        private int m_dooberDiedTimestamp ;
        private boolean m_dooberSquashStretching =false ;
        protected boolean m_isStreakBonusEnabled =true ;
        protected Function m_onCollectCallback ;
        protected int m_batchId =-1;
        protected MapResource m_spawningObject =null ;
        public static  String STATE_INITIAL ="initial";
        public static  String STATE_IDLE ="idle";
        public static  String STATE_END ="end";
        public static  String DOOBER_COIN ="coin";
        public static  String DOOBER_XP ="xp";
        public static  String DOOBER_KDBOMB ="kdbomb";
        public static  String DOOBER_LRBOMB ="lrbomb";

        public static  String DOOBER_ENERGY ="energy";
        public static  String DOOBER_CASH ="cash";
        public static  String DOOBER_COLLECTABLE ="collectable";
        public static  String DOOBER_GOODS ="goods";
        public static  String DOOBER_PREMIUM_GOODS ="premium_goods";
        public static  String DOOBER_REP ="rep";
        public static  String DOOBER_ITEM ="item";
        public static  String DOOBER_POPULATION ="population";
        public static  String DOOBER_TOOL ="tool";
        public static  Array DOOBER_ALL_TYPES =.get(DOOBER_COIN ,DOOBER_XP ,DOOBER_ENERGY ,DOOBER_COLLECTABLE ,DOOBER_GOODS ,DOOBER_PREMIUM_GOODS ,DOOBER_REP ,DOOBER_ITEM ,DOOBER_POPULATION ,DOOBER_TOOL) ;
        public static  String CLEAR_EVENT ="clear";

        public  Doober (MapResource param1 ,String param2 ,int param3 ,Item param4 ,Point param5 ,Point param6 ,double param7 ,int param8 ,boolean param9 =false ,Point param10 =null ,Function param11 =null ,int param12 =-1)
        {
            super(param2);
            this.m_batchId = param12;
            this.m_amount = param3;
            this.m_startPosition = param5;
            this.m_landPosition = param6;
            this.m_initialYVelocity = param7;
            this.m_dooberTimeout = param8;
            this.m_dooberAutoTimeout = param8 / 10;
            this.m_autoCollect = param1 ? (param1.isBeingAutoHarvested()) : (false);
            m_typeName = this.DOOBER;
            m_objectType = WorldObjectTypes.DOOBER;
            m_collisionFlags = Constants.COLLISION_NONE;
            m_ownable = false;
            this.m_streakAmount = param3;
            this.m_isDummy = param9;
            this.m_flyToScreenPoint = param10;
            this.m_zoom = GlobalEngine.viewport.getZoom();
            this.m_onCollectCallback = param11;
            this.m_spawningObject = param1;
            this.setState(STATE_INITIAL);
            if (m_item.type == DOOBER_XP)
            {
                this.doResourceChanges(0, 0, this.m_amount, 0, "", false, true, false);
            }
            else if (m_item.type == DOOBER_ENERGY)
            {
                this.doEnergyChanges(this.m_amount, this.getEnergyTrackingInfo(param4));
            }
            return;
        }//end

        private Array  getEnergyTrackingInfo (Item param1 )
        {
            String _loc_3 =null ;
            Array _loc_2 =new Array("energy","expenditures","unknown_doober","unknown_item");
            if (param1 != null)
            {
                _loc_3 = param1.name;
                if (_loc_3.indexOf("mun") >= 0)
                {
                    _loc_2.put(2,  "municipal_drops");
                }
                else
                {
                    _loc_2.put(2,  "random_drops");
                }
                _loc_2.put(3,  _loc_3);
            }
            return _loc_2;
        }//end

         protected String  getLayerName ()
        {
            return "doober";
        }//end

        public int  amount ()
        {
            return this.m_amount;
        }//end

        public boolean  isFlying ()
        {
            return this.m_isFlying;
        }//end

        public boolean  isStreanBonusEnabled ()
        {
            return this.m_isStreakBonusEnabled;
        }//end

        public void  isStreanBonusEnabled (boolean param1 )
        {
            this.m_isStreakBonusEnabled = param1;
            return;
        }//end

         protected void  onItemImageLoaded (Event event )
        {
            super.onItemImageLoaded(event);
            GlobalEngine.viewport.setDirty();
            return;
        }//end

         public void  onUpdate (double param1 )
        {
            double _loc_2 =0;
            double _loc_3 =0;
            int _loc_4 =0;
            Debug.debug6("Doober.onUpdate.1"+m_isDummy);
            if (!displayObject || !m_content)
            {
                return;
            }
            if (!this.m_pixieDustAdded && displayObject)
            {
                if (Global.world.defCon == GameWorld.DEFCON_LEVEL1)
                {
                    this.addAnimatedEffect(EffectType.PIXIEDUST);
                }
                this.m_pixieDustAdded = true;
            }
            if (!this.m_arcMotion && !this.m_isDummy)
            {
                this.m_arcMotion = new ArcMotion(this, this.m_startPosition, this.m_landPosition, this.m_initialYVelocity, this.DOOBER_GRAVITY);
            }
            Debug.debug6("Doober.onUpdate.2"+this.getState());

            if (this.m_isDummy && this.getState() == STATE_INITIAL)
            {
                this.setState(STATE_END);
                this.m_dooberDiedTimestamp = int(GlobalEngine.getTimer() / 1000);
                this.playCollectAnimation();
                return;
            }
            if (this.m_arcMotion && this.getState() == STATE_INITIAL)
            {
                if (Global.world.defCon >= GameWorld.DEFCON_LEVEL4)
                {
                    param1 = 10;
                }
                this.m_arcMotion.update(param1);
                if (this.m_arcMotion.isComplete)
                {
                    this.setState(STATE_IDLE);
                    if (Global.world.defCon < GameWorld.DEFCON_LEVEL3)
                    {
                        this.m_dooberSquashStretching = true;
                        _loc_2 = displayObject.width * Math.abs(1 - 0.3 / displayObject.scaleX) / 2;
                        _loc_3 = displayObject.height * Math.abs(1 - 0.15 / displayObject.scaleY);
                        TweenLite.to(displayObject, 0.1, {scaleX:this.originalScale * 1.2, scaleY:this.originalScale * 0.6, x:displayObject.x - _loc_2, y:displayObject.y + _loc_3, onComplete:this.onSquash});
                    }
                }
            }
            else if (this.getState() == STATE_IDLE)
            {
                if (!UI.isModalDialogOpen && this.m_dooberElapsedTime < this.m_dooberTimeout)
                {
                    this.m_dooberElapsedTime = this.m_dooberElapsedTime + param1 * 1000;
                }
                if (this.m_autoCollect && this.m_dooberElapsedTime >= this.m_dooberAutoTimeout)
                {
                    this.m_timedOut = false;
                    this.onPlayAction();
                }
                else if (this.m_dooberElapsedTime >= this.m_dooberTimeout)
                {
                    this.onTimeOut();
                }
            }
            else if (this.getState() == STATE_END)
            {
                _loc_4 = int(GlobalEngine.getTimer() / 1000) - this.m_dooberDiedTimestamp;
                if (_loc_4 > 5)
                {
                    this.cleanUp();
                }
            }
            if (this.getState() != STATE_END)
            {
                this.resetZoom();
            }
            super.onUpdate(param1);
            return;
        }//end

        protected void  resetZoom ()
        {
            if (displayObject)
            {
                this.m_zoom = GlobalEngine.viewport.getZoom();
                displayObject.scaleX = 1 / this.m_zoom;
                displayObject.scaleY = 1 / this.m_zoom;
            }
            return;
        }//end

        private void  onSquash ()
        {
            _loc_1 = displayObject.width*Math.abs(1-this.originalScale/displayObject.scaleX)/2;
            _loc_2 = displayObject.height*Math.abs(1-this.originalScale/displayObject.scaleY);
            TweenLite.to(displayObject, 0.1, {scaleX:this.originalScale, scaleY:this.originalScale, x:displayObject.x + _loc_1, y:displayObject.y - _loc_2, onComplete:this.onStretch});
            return;
        }//end

        private void  onStretch ()
        {
            this.m_dooberSquashStretching = false;
            return;
        }//end

         public void  onPlayAction ()
        {
            if (!this.m_dooberSquashStretching && (this.getState() == STATE_IDLE || this.getState() == STATE_INITIAL))
            {
                this.setState(STATE_END);
                this.m_dooberDiedTimestamp = int(GlobalEngine.getTimer() / 1000);
                this.collectDoober(true);
                this.playCollectAnimation();
            }
            return;
        }//end

        public void  collectIfIdle ()
        {
            if (getState() == STATE_IDLE)
            {
                this.onPlayAction();
            }
            return;
        }//end

        private void  onTimeOut ()
        {
            this.setState(STATE_END);
            this.m_dooberDiedTimestamp = int(GlobalEngine.getTimer() / 1000);
            this.m_timedOut = true;
            this.collectDoober(false, false);
            this.playCollectAnimation();
            return;
        }//end

        protected void  collectDoober (boolean param1 ,boolean param2 =true )
        {
            int _loc_3 =0;
            String _loc_4 =null ;

            Debug.debug7("Doober.collectDoober" +m_item.type);

            switch(m_item.type)
            {
                case DOOBER_COIN:
                {
                    this.doResourceChanges(0, this.m_amount, 0, 0, "", param1, true, false);
                    if (param2)
                    {
                        Sounds.play("doober_coin_click");
                    }
                    break;
                }
                case DOOBER_XP:
                {
                    this.doResourceChanges(0, 0, this.m_amount, 0, "", param1, false, false);
                    if (param2)
                    {
                        Sounds.play("doober_xp_click");
                    }
                    break;
                }
                case DOOBER_ENERGY:
                {
                    this.doEnergyChanges(this.m_amount, null, param1, false, false);
                    if (param2)
                    {
                        Sounds.play("doober_energy_click");
                    }
                    break;
                }
                case DOOBER_GOODS:
                {
                    Global.player.commodities.add(DOOBER_GOODS, this.m_amount);
                    this.doResourceChanges(0, 0, 0, this.m_amount, DOOBER_GOODS, param1, true, false);
                    if (param2)
                    {
                        Sounds.play("doober_food_click");
                    }
                    break;
                }
                case DOOBER_PREMIUM_GOODS:
                {
                    Global.player.commodities.add(DOOBER_PREMIUM_GOODS, this.m_amount);
                    this.doResourceChanges(0, 0, 0, this.m_amount, DOOBER_PREMIUM_GOODS, param1, true, false);
                    if (param2)
                    {
                        Sounds.play("doober_food_click");
                    }
                    break;
                }
                case DOOBER_POPULATION:
                {
                    if (param2)
                    {
                        Sounds.play("doober_population_click");
                    }
                    if (param1 !=null)
                    {
                        _loc_3 = this.m_amount * Global.gameSettings().getNumber("populationScale", 1);
                        _loc_4 = ZLoc.t("Main", "GainPop", {population:_loc_3});
                        this.displayStatus(_loc_4, null);
                    }
                    break;
                }
                case DOOBER_REP:
                {
                    this.doResourceChanges(0, 0, 0, 0, "", param1, true, false, this.m_amount);
                    if (param2)
                    {
                        Sounds.play("doober_reputation_click");
                    }
                    break;
                }
                case DOOBER_COLLECTABLE:
                {
                    if (param2)
                    {
                        Sounds.play("doober_collectable_click");
                    }
                    this.displayStatus(m_item.localizedName);
                    this.m_collectableFlyToPoint = new Point();
                    this.m_collectableFlyToSize = new Point();
                    UI.displayCollectionFlyout(m_item.name, this.m_collectableFlyToPoint, this.m_collectableFlyToSize);
                    break;
                }
                case DOOBER_KDBOMB:
                {
                    Global.player.kdbomb = Global.player.kdbomb + m_amount;

                    if (param2)
                    {
                        Sounds.play("doober_coin_click");
                    }
                    break;
                }
                case DOOBER_LRBOMB:
                {
                    Global.player.lrbomb = Global.player.lrbomb + m_amount;
                    if (param2)
                    {
                        Sounds.play("doober_coin_click");
                    }
                    break;
                }
                default:
                {
                    break;
                    break;
                }
            }
            return;
        }//end

        private void  playCollectAnimation ()
        {
            HUDComponent _loc_3 =null ;
            Point _loc_7 =null ;
            Point _loc_8 =null ;
            Point _loc_9 =null ;
            this.passDooberToUI();
            double _loc_1 =0;
            double _loc_2 =0;
            _loc_4 = Cubic.easeOut;
            _loc_5 = this.onLandedAtDummyFlyPoint ;
            Debug.debug6("Doober.playCollectAnimation"+m_item.type);
            if (this.m_isDummy && this.m_flyToScreenPoint)
            {
                _loc_7 = displayObject.parent != null ? (displayObject.parent.globalToLocal(this.m_flyToScreenPoint)) : (this.m_flyToScreenPoint);
                _loc_1 = _loc_7.x - m_content.width / 2;
                _loc_2 = _loc_7.y - m_content.height / 2;
                _loc_5 = this.onLandedAtDummyFlyPoint;
                TweenLite.to(displayObject, 0.5, {bezier:.get({x:_loc_1, y:displayObject.y}, {x:_loc_1, y:_loc_2}), orientToBezier:false, ease:_loc_4, onComplete:_loc_5});
                return;
            }
            double _loc_6 =0.5;
            if (Global.world.defCon >= GameWorld.DEFCON_LEVEL3)
            {
                _loc_6 = 0.1;
            }
            //_loc_1 = Global.hud.streakBonus.midPointX - m_content.width / 2;
            //_loc_2 = Global.hud.streakBonus.midPointY - m_content.height / 2;
            _loc_5 = this.onLandedAtHudBar;
            switch(m_item.type)
            {
                case DOOBER_KDBOMB:
                {
                    _loc_3 = Global.hud.getComponent(HUD.COMP_KDBOMB);
                    if (_loc_3 == null)
                    {
                        break;
                    }
                    _loc_1 = _loc_3.x - 10;
                    _loc_2 = _loc_3.y - 10;
                    _loc_5 = this.onLandedAtHudBar;
                    break;
                }
                case DOOBER_LRBOMB:
                {
                    _loc_3 = Global.hud.getComponent(HUD.COMP_LRBOMB);
                    if (_loc_3 == null)
                    {
                        break;
                    }
                    _loc_1 = _loc_3.x - 10;
                    _loc_2 = _loc_3.y - 10;
                    _loc_5 = this.onLandedAtHudBar;
                    break;
                }
                case DOOBER_COIN:
                {
                    _loc_3 = Global.hud.getComponent(HUD.COMP_COINS);
                    if (_loc_3 == null)
                    {
                        break;
                    }
                    _loc_1 = _loc_3.x - 10;
                    _loc_2 = _loc_3.y - 10;
                    _loc_5 = this.onLandedAtHudBar;
                    break;
                }
                case DOOBER_XP:
                {
                    _loc_3 = Global.hud.getComponent(HUD.COMP_XP);
                    if (_loc_3 == null)
                    {
                        break;
                    }
                    _loc_1 = _loc_3.x + 80;
                    _loc_2 = _loc_3.y - 5;
                    _loc_5 = this.onLandedAtHudBar;
                    break;
                }
                case DOOBER_ENERGY:
                {
                    _loc_3 = Global.hud.getComponent(HUD.COMP_ENERGY);
                    if (_loc_3 == null)
                    {
                        break;
                    }
                    _loc_1 = _loc_3.x;
                    _loc_2 = _loc_3.y;
                    _loc_5 = this.onLandedAtHudBar;
                    break;
                }
                case DOOBER_GOODS:
                case DOOBER_PREMIUM_GOODS:
                {
                    _loc_8 = Global.hud.hudGoodsPosition;
                    if (_loc_8.x == 0 && _loc_8.y == 0)
                    {
                        break;
                    }
                    _loc_1 = _loc_8.x - 10;
                    _loc_2 = _loc_8.y + Global.hud.hudGoodsHeight - 110;
                    _loc_5 = this.onLandedAtHudBar;
                    break;
                }
                case DOOBER_REP:
                {
                    _loc_3 = Global.hud.getComponent(HUD.COMP_REPUTATION);
                    if (_loc_3 == null)
                    {
                        break;
                    }
                    _loc_1 = _loc_3.x;
                    _loc_2 = _loc_3.y;
                    _loc_5 = this.onLandedAtHudBar;
                    break;
                }
                case DOOBER_POPULATION:
                {
                    if (this.m_spawningObject instanceof Residence)
                    {
                        ((Residence)this.m_spawningObject).playMoveIn();
                    }
                    else
                    {
                        Global.world.citySim.recomputePopulation(Global.world, true);
                    }
                    _loc_9 = Global.ui.m_cityNamePanel.localToGlobal(new Point(0, 0));
                    _loc_9 = Global.ui.globalToLocal(_loc_9);
                    _loc_1 = _loc_9.x;
                    _loc_2 = _loc_9.y;
                    _loc_5 = this.onLandedAtNamePanel;
                    break;
                }
                case DOOBER_COLLECTABLE:
                {
                    _loc_1 = this.m_collectableFlyToPoint.x - m_content.width / 2 + this.m_collectableFlyToSize.x / 2;
                    _loc_2 = this.m_collectableFlyToPoint.y - m_content.height / 2 + this.m_collectableFlyToSize.y / 2;
                    _loc_5 = this.onLandedAtSlideOut;
                    break;
                }
                default:
                {


                    _loc_5 = this.onLandedAtHudBar;
                    break;
                    break;
                }
            }
            //TweenLite.to(displayObject, _loc_6, {bezier:.get({x:_loc_1, y:displayObject.y}, {x:_loc_1, y:_loc_2}), orientToBezier:false, ease:_loc_4, onComplete:_loc_5});

            TweenLite.to(displayObject, _loc_6, {x:_loc_1, y:_loc_2, ease:_loc_4, onComplete:_loc_5});

            return;
        }//end

        private void  passDooberToUI ()
        {
            Point _loc_1 =null ;
            if (!this.m_isDummy)
            {
                _loc_1 = displayObject.parent.localToGlobal(new Point(displayObject.x, displayObject.y));
            }
            else
            {
                _loc_1 = this.m_startPosition;
            }
            this.m_isFlying = true;
            _loc_2 =Global.world.getTopGameMode ();
            _loc_3 =Global.ui.globalToLocal(_loc_1 );
            displayObject.x = _loc_3.x;
            displayObject.y = _loc_3.y;
            displayObject.scaleX = 1;
            displayObject.scaleY = 1;
            this.detach();
            Global.ui.addChild(displayObject);
            ((DisplayObjectContainer)displayObject).mouseEnabled = false;
            ((DisplayObjectContainer)displayObject).mouseChildren = false;
            return;
        }//end

         public String  getGameModeToolTipAction ()
        {
            _loc_1 = super.getGameModeToolTipAction();
            if (Global.world.getTopGameMode() instanceof GMEditSell)
            {
                _loc_1 = null;
            }
            return _loc_1;
        }//end

        private void  onLandedAtDummyFlyPoint ()
        {
            TweenLite.to(displayObject, 0.3, {scaleX:displayObject.scaleX * 1.2, scaleY:displayObject.scaleY * 1.2, alpha:0, onComplete:this.cleanUp});
            return;
        }//end

        private void  onLandedAtHudBar ()
        {
            if (!this.m_timedOut && this.m_isStreakBonusEnabled)
            {

            }
            TweenLite.to(displayObject, 0.3, {scaleX:displayObject.scaleX * 1.2, scaleY:displayObject.scaleY * 1.2, alpha:0, onComplete:this.cleanUp});
            return;
        }//end

        private void  onLandedAtNamePanel ()
        {
            if (!this.m_timedOut && this.m_isStreakBonusEnabled)
            {

            }
            Global.ui.happinessPanel.animateIfHappinessChanged();
            Global.world.citySim.recomputePopulation(Global.world, true);
            TweenLite.to(displayObject, 0.3, {scaleX:displayObject.scaleX * 1.2, scaleY:displayObject.scaleY * 1.2, alpha:0, onComplete:this.cleanUp});
            return;
        }//end

        private void  onLandedAtSlideOut ()
        {
            if (this.m_isStreakBonusEnabled)
            {

            }
            UI.checkCollectableFadeIn(m_item.name);
            _loc_1 = displayObject.width*0.2;
            _loc_2 = displayObject.height*0.2;
            TweenLite.to(displayObject, 0.1, {scaleX:displayObject.scaleX * 1.2, scaleY:displayObject.scaleY * 1.2, x:displayObject.x - _loc_1, y:displayObject.y - _loc_2, delay:0.4, onComplete:this.onCollectableEnlarged});
            return;
        }//end

        private void  onCollectableEnlarged ()
        {
            _loc_1 = this.m_collectableFlyToSize.x /m_content.width ;
            _loc_2 = this.m_collectableFlyToSize.y /m_content.height ;
            TweenLite.to(displayObject, 0.2, {scaleX:_loc_1, scaleY:_loc_2, x:this.m_collectableFlyToPoint.x, y:this.m_collectableFlyToPoint.y, onComplete:this.onCollectableShrunk});
            return;
        }//end

        private void  onCollectableShrunk ()
        {
            Collection.addCollectionToPlayer(m_item.name);
            UI.displayCollectableFadeIn(m_item.name);
            this.cleanUp();
            return;
        }//end

         public void  cleanUp ()
        {
            if (this.m_onCollectCallback != null)
            {
                this.m_onCollectCallback();
            }
            this.dispatchEvent(new DooberEvent(Doober.CLEAR_EVENT, this.m_batchId));
            if (this.isAttached())
            {
                this.detach();
            }
            if (displayObject)
            {
                if (displayObject.parent)
                {
                    displayObject.parent.removeChild(displayObject);
                }
                TweenLite.killTweensOf(displayObject);
            }
            Global.world.dooberManager.removeDoober(this);
            super.cleanUp();
            return;
        }//end

         public void  displayStatus (String param1 ,Object param2 ="",Object param3 =16777215)
        {
            _loc_4 = displayStatusStartPos();
            UI.displayStatus(param1, _loc_4.x, _loc_4.y, param2, null, 1.5, 32, 16, 60, param3);
            return;
        }//end

         public String  getToolTipHeader ()
        {
            return null;
        }//end

         public void  setState (String param1 )
        {
            _loc_2 = m_state;
            m_state = param1;
            onStateChanged(_loc_2, param1);
            if (_loc_2 != param1 && param1 == STATE_IDLE)
            {
                this.checkToaster();
            }
            return;
        }//end

        private boolean  getCanComplete (WorldObject param1 )
        {
            if (!(param1 instanceof ConstructionSite))
            {
                return false;
            }
            _loc_2 = param1as ConstructionSite ;
            _loc_3 = _loc_2.getGate(ConstructionSite.BUILD_GATE );
            if (_loc_3 == null)
            {
                return false;
            }
            if (_loc_2.currentState != ConstructionSite.STATE_AT_GATE)
            {
                return false;
            }
            if (!(_loc_3 instanceof InventoryGate))
            {
                return false;
            }
            _loc_4 = _loc_3as InventoryGate ;
            if (((InventoryGate)_loc_3).checkForKeys())
            {
                return true;
            }
            if (_loc_4.checkWithAdditionalKey(m_item))
            {
                return true;
            }
            return false;
        }//end

        protected void  checkMunicipal ()
        {
            ConstructionSite _loc_5 =null ;
            InventoryGate _loc_6 =null ;
            boolean _loc_7 =false ;
            int _loc_8 =0;
            Item _loc_9 =null ;
            ItemToaster _loc_10 =null ;
            _loc_1 =Global.world.getObjectsByPredicate(this.getCanComplete );
            ConstructionSite _loc_2 =null ;
            int _loc_3 =0;
            int _loc_4 =100;
            for(int i0 = 0; i0 < _loc_1.size(); i0++)
            {
            		_loc_5 = _loc_1.get(i0);

                _loc_6 =(InventoryGate) _loc_5.getGate(ConstructionSite.BUILD_GATE);
                if (_loc_6 == null)
                {
                    continue;
                }
                _loc_7 = _loc_6.checkWithAdditionalKey(m_item) && !_loc_6.checkForKeys();
                _loc_8 = _loc_6.keyCount;
                if (_loc_7)
                {
                    _loc_8 = _loc_8 + _loc_4;
                }
                if (_loc_8 > _loc_3)
                {
                    _loc_2 = _loc_5;
                    _loc_3 = _loc_8;
                }
            }
            if (_loc_2)
            {
                _loc_9 = _loc_2.targetItem;
                _loc_10 = new ItemToaster(ZLoc.t("Main", "CommunityToasterTitle"), ZLoc.t("Main", "CommunityToasterText", {building:_loc_9.localizedName}), _loc_9.getImageByName("icon"));
                Global.ui.toaster.show(_loc_10);
            }
            return;
        }//end

        protected void  checkToaster ()
        {
            String _loc_2 =null ;
            ItemToaster _loc_3 =null ;
            _loc_1 = m_item.type;
            if (_loc_1 != DOOBER_TOOL)
            {
                return;
            }
            if (this.m_spawningObject && this.m_spawningObject.getItemName() == "bus_hardwarestore")
            {
                _loc_2 = "";
                if (m_item && m_item.getImageByName("initial"))
                {
                    _loc_2 = m_item.localizedName;
                }
                if (_loc_2 == "")
                {
                    return;
                }
                _loc_3 = new ItemToaster(ZLoc.t("Main", "HardwareDeliveryToasterTitle"), ZLoc.t("Main", "HardwareDeliveryToasterText", {itemName:_loc_2}), m_item.getImageByName("icon"));
                Global.ui.toaster.show(_loc_3);
                this.checkMunicipal();
            }
            return;
        }//end

         public boolean  canBeDragged ()
        {
            return false;
        }//end

         public boolean  isPixelInside (Point param1 )
        {
            Rectangle _loc_2 =null ;
            if (m_displayObject)
            {
                _loc_2 = m_displayObject.getBounds(m_displayObject.parent);
                if (m_state != STATE_END)
                {
                    _loc_2.topLeft.x = _loc_2.topLeft.x - 5;
                    _loc_2.topLeft.y = _loc_2.topLeft.y - 5;
                    _loc_2.bottomRight.x = _loc_2.bottomRight.x + 5;
                    _loc_2.bottomRight.y = _loc_2.bottomRight.y + 5;
                    if (_loc_2.containsPoint(param1))
                    {
                        return true;
                    }
                }
            }
            return false;
        }//end

        protected double  originalScale ()
        {
            return 1 / this.m_zoom;
        }//end

         public void  onMouseOver (MouseEvent event )
        {
            this.onPlayAction();
            return;
        }//end

         protected ItemImageInstance  getCurrentImage ()
        {
            if (m_item == null)
            {
                return null;
            }
            _loc_1 = this.getImageForState ();
            _loc_2 = m_item.getCachedImage(_loc_1,this,m_direction,m_statePhase);
            return _loc_2;
        }//end

        public String  getImageForState ()
        {
            String _loc_2 =null ;
            _loc_1 = m_state;
            if (m_item != null && m_state == STATE_INITIAL)
            {
                switch(m_item.type)
                {
                    case DOOBER_POPULATION:
                    {
                        _loc_2 = this.m_spawningObject.getItem().populationType;
                        if (_loc_2 != Population.CITIZEN && m_item.hasCachedImageByName(_loc_2))
                        {
                            _loc_1 = _loc_2;
                        }
                        break;
                    }
                    default:
                    {
                        break;
                    }
                }
            }
            return _loc_1;
        }//end

    }



