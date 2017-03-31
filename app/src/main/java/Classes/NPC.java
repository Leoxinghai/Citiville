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

import Classes.actions.*;
import Classes.effects.*;
import Classes.sim.*;
import Display.FactoryUI.*;
import Engine.*;
import Engine.Classes.*;
import Engine.Helpers.*;
import Engine.Managers.*;
import Modules.stats.experiments.*;
import root.Global;
import root.GlobalEngine;

import com.greensock.*;
//import flash.display.*;
//import flash.events.*;
//import flash.geom.*;
import com.xinghai.Debug;
import com.xiyu.util.Rectangle;

public class NPC extends MapResource
    {
        private  double NAV_WOBBLE =0.1;
        private  double NAV_RIGHT_PROB =0.5;
        private  double MIN_AMBIENT_DELTA =10000;
        private  String NPCTYPE ="npc";
        private Sprite m_feedback ;
        private double m_feedbackEnd =-1;
        private boolean m_performsAmbientActions =false ;
        private double m_nextAmbientActionStart =0;
        private double m_velocityWalk =0;
        private double m_velocityRun =0;
        private double m_roadOffset =0;
        private boolean m_isShip =false ;
        private String m_animationState ="static";
        private IActionSelection m_actionSelection ;
        private boolean m_isFreeAgent =true ;
        private boolean m_isRightSideNav =true ;
        protected int m_homeId =0;
        protected int m_slotId =0;
        protected SlidePick m_slidePick =null ;
        private Point m_lastFeedbackPosition ;
        private boolean m_feedbackRendered =false ;
        private Rectangle m_feedbackRect ;
        private Rectangle m_feedbackDirtyRect ;
        private Shape m_navDbgFeedback =null ;
        public Array m_navDbgPath ;
        public Array m_navDbgPath2 ;
        public boolean m_navRandom =false ;
        protected double m_pathBuildingScore =0;
        protected boolean m_alwaysCulled =false ;
        protected Function m_playActionCallback =null ;
        protected Function m_tooltipHeaderGenerator =null ;
        protected Function m_tooltipStatusGenerator =null ;
        protected Function m_imageLoadedCallback =null ;
        protected Array m_feedbackQueue ;
        protected Function m_onHighlightCallback =null ;
        protected Function m_enterMapResourceCallback =null ;
        protected Function m_exitMapResourceCallback =null ;
        protected String m_oldAnimation ;
        private String m_customGuitarPick ;
        private boolean m_displayGuitarPick =false ;
        protected boolean m_paused =false ;
        private static int m_experimentNPCThinning =-1;

        public String npc_name ;

        public  NPC (String param1 ,boolean param2 ,double param3 =-1)
        {
            if(param1.indexOf("NPC_")==0) {
            	Debug.debug7("NPC."+param1);
            }

            npc_name = param1;


            this.m_lastFeedbackPosition = new Point();
            this.m_feedbackRect = new Rectangle();
            this.m_feedbackDirtyRect = new Rectangle();
            this.m_feedbackQueue = new Array();
            super(param1);
            m_objectType = WorldObjectTypes.NPC;
            m_typeName = this.NPCTYPE;
            if (m_experimentNPCThinning < 0)
            {
                m_experimentNPCThinning = Global.experimentManager.getVariant(ExperimentDefinitions.EXPERIMENT_OPTIMIZE_NPC_THINNING);
            }
            if (param3 >= 0)
            {
                this.m_velocityWalk = param3;
            }
            else
            {
                this.m_velocityWalk = MathUtil.randomWobble(m_item.navigateXml.walkSpeed, this.NAV_WOBBLE);
            }
            this.m_velocityRun = MathUtil.randomWobble(m_item.navigateXml.runSpeed, this.NAV_WOBBLE);
            this.m_roadOffset = m_item.navigateXml.roadOffset;
            this.m_isShip = m_item.navigateXml.isShip == true;
            m_collisionFlags = Constants.COLLISION_NONE;
            this.m_isFreeAgent = param2;
            m_isHighlightable = false;
            if (!this.isVehicle)
            {
                this.m_roadOffset = MathUtil.randomWobble(this.m_roadOffset, this.NAV_WOBBLE);
                this.m_isRightSideNav = Math.random() < this.NAV_RIGHT_PROB;
            }
            this.m_actionSelection = this.makeActionSelection();
            this.randomizeNextAmbientActionStart();
            _loc_4 = Global.player.npc_cloud_visible;
            if (!Global.player.npc_cloud_visible)
            {
                this.m_alwaysCulled = true;
            }
            return;
        }//end

         public void  attach ()
        {
            if (m_attachPosition == null)
            {
                if (!this.m_alwaysCulled)
                {
                    Debug.debug4("NPC.attach."+m_item.name+";"+this.getLayerName()+":"+m_outer);
                    this.calculateDepthIndex();
                    m_outer.insertObjectIntoDepthArray(this, this.getLayerName());
                    setObjectsDirty();
                    m_outer.insertObjectIntoCollisionMap(this);
                    m_animationBucket = m_outer.insertItemIntoAnimationBucket(this);
                    updateDisplayObjectTransform();
                }
                updateCulling();
                m_attachPosition = m_position;
                m_attachSize = m_size;
                m_transformDirty = false;
            }
            super.attach();
            return;
        }//end

         public void  detach ()
        {
            if (Config.DEBUG_MODE)
            {
                this.m_navDbgPath = null;
                this.drawNavFeedback();
            }
            this.hideFeedbackBubble();
            this.getStateMachine().removeAllStates();
            this.hideSlidePick();
            super.detach();
            return;
        }//end

        public void  setHome (Residence param1 )
        {
            this.m_homeId = param1.getId();
            this.m_slotId = 0;
            if (param1.pathProvider)
            {
                this.m_slotId = param1.pathProvider.getId();
            }
            return;
        }//end

        public void  forceUpdateArrowWithCustomIcon (String param1 )
        {
            this.m_customGuitarPick = param1;
            this.m_displayGuitarPick = true;
            this.updateArrow();
            return;
        }//end

         protected void  updateArrow ()
        {
            this.createStagePickEffect();
            return;
        }//end

         protected void  createStagePickEffect ()
        {
            if (this.m_displayGuitarPick)
            {
                if (this.m_customGuitarPick)
                {
                    m_stagePickEffect =(StagePickEffect) MapResourceEffectFactory.createEffect(this, EffectType.STAGE_PICK);
                    m_stagePickEffect.setPickType(this.m_customGuitarPick);
                    m_stagePickEffect.float();
                }
                else
                {
                    removeStagePickEffect();
                }
            }
            return;
        }//end

        public void  hideStagePickEffect ()
        {
            this.m_displayGuitarPick = false;
            removeStagePickEffect();
            reloadImage();
            return;
        }//end

         public boolean  canShowStagePick ()
        {
            return this.m_displayGuitarPick;
        }//end

        public Residence  getHome ()
        {
            ISlottedContainer _loc_2 =null ;
            Array _loc_3 =null ;
            Residence _loc_4 =null ;
            if (this.m_homeId == 0)
            {
                return null;
            }
            _loc_1 = (Residence)Global.world.getObjectById(this.m_homeId)
            if (this.m_slotId && !_loc_1)
            {
                _loc_2 =(ISlottedContainer) Global.world.getObjectById(this.m_slotId);
                if (_loc_2)
                {
                    _loc_3 = _loc_2.slots;
                    for(int i0 = 0; i0 < _loc_3.size(); i0++)
                    {
                    		_loc_4 = _loc_3.get(i0);

                        if (this.m_homeId == _loc_4.getId())
                        {
                            _loc_1 = _loc_4;
                            break;
                        }
                    }
                }
            }
            return _loc_1;
        }//end

        public boolean  alwaysCulled ()
        {
            return this.m_alwaysCulled;
        }//end

        public double  pathBuildingScore ()
        {
            return this.m_pathBuildingScore;
        }//end

        public void  pathBuildingScore (double param1 )
        {
            this.m_pathBuildingScore = param1;
            return;
        }//end

        public void  alwaysCulled (boolean param1 )
        {
            if (this.m_alwaysCulled == false)
            {
                this.m_alwaysCulled = true;
                updateCulling();
            }
            return;
        }//end

         public boolean  isWithinViewport ()
        {
            return !this.m_alwaysCulled && super.isWithinViewport();
        }//end

         protected void  calculateDepthIndex ()
        {
            if (this.m_alwaysCulled)
            {
                m_depthIndex = 0;
            }
            else
            {
                super.calculateDepthIndex();
            }
            return;
        }//end

        public void  showDeclineEntranceBubble ()
        {
            _loc_1 = Global.getAssetURL("assets/citysim/fail_icon.png");
            this.showFeedbackBubble(_loc_1, 8);
            return;
        }//end

        public void  showSmileFeedbackBubble ()
        {
            _loc_1 =Global.getAssetURL("assets/citysim/happy.png");
            this.showFeedbackBubble(_loc_1, 5);
            return;
        }//end

        public void  showGoingToHotelFeedbackBubble ()
        {
            _loc_1 = Global.getAssetURL("assets/citysim/bell_bubble_icon.png");
            this.showFeedbackBubble(_loc_1, 8);
            return;
        }//end

        public void  showCannotEnterHotelFeedbackBubble ()
        {
            _loc_1 = Global.getAssetURL("assets/citysim/no_bell_bubble_icon.png");
            this.showFeedbackBubble(_loc_1, 8);
            return;
        }//end

        public int  getPathTypeToBusiness ()
        {
            return RoadManager.PATH_TO_FRONT_ENTRANCE;
        }//end

        public int  getPathTypeToResidence ()
        {
            return RoadManager.PATH_FULL;
        }//end

        public boolean  isVehicle ()
        {
            return false;
        }//end

        public boolean  isSeaVehicle ()
        {
            return false;
        }//end

        public boolean  isCloud ()
        {
            return false;
        }//end

        public Function  playActionCallback ()
        {
            return this.m_playActionCallback;
        }//end

        public void  playActionCallback (Function param1 )
        {
            this.m_playActionCallback = param1;
            this.m_isHighlightable = param1 != null;
            return;
        }//end

         public void  onPlayAction ()
        {
            super.onPlayAction();
            if (this.m_playActionCallback != null)
            {
                this.m_playActionCallback();
            }
            return;
        }//end

        public void  onEnterMapResource (MapResource param1 )
        {
            if (this.m_enterMapResourceCallback != null)
            {
                this.m_enterMapResourceCallback(param1);
            }
            return;
        }//end

        public void  onExitMapResource (MapResource param1 )
        {
            if (this.m_exitMapResourceCallback != null)
            {
                this.m_exitMapResourceCallback(param1);
            }
            return;
        }//end

        protected IActionSelection  makeActionSelection ()
        {
            return new NPCActionSelection(this);
        }//end

        public SlidePick  slidePick ()
        {
            return this.m_slidePick;
        }//end

        public void  slidePick (SlidePick param1 )
        {
            this.m_slidePick = param1;
            return;
        }//end

        public void  showSlidePick ()
        {
            if (this.m_slidePick)
            {
                GlobalEngine.viewport.hudBase.addChild(this.m_slidePick);
                this.m_slidePick.setPosition(m_position.x, m_position.y);
            }
            return;
        }//end

        public void  hideSlidePick (double param1 =0)
        {
            tweenTimeSeconds = param1;
            if (tweenTimeSeconds == 0 && this.m_slidePick && this.m_slidePick.parent)
            {
                this.m_slidePick.parent.removeChild(this.m_slidePick);
            }
            else if (this.m_slidePick && this.m_slidePick.parent)
            {
void                 TweenLite .to (this .m_slidePick ,tweenTimeSeconds ,{0alpha , onComplete (...args )
            {
                if (m_slidePick && m_slidePick.parent)
                {
                    m_slidePick.parent.removeChild(m_slidePick);
                    m_slidePick.alpha = 1;
                }
                return;
            }//end
            });
            }
            return;
        }//end

        public boolean  isSlidePickShown ()
        {
            return this.m_slidePick != null && this.m_slidePick.parent != null;
        }//end

        public boolean  isRightSideNav ()
        {
            return this.m_isRightSideNav;
        }//end

         protected String  getLayerName ()
        {
            if (Game.m_blitting == true)
            {
                return "npc";
            }
            return null;
        }//end

        public boolean  isShowingFeedback ()
        {
            return this.m_feedback != null;
        }//end

        public boolean  isFreeAgent ()
        {
            return this.m_isFreeAgent;
        }//end

        public void  isFreeAgent (boolean param1 )
        {
            this.m_isFreeAgent = param1;
            return;
        }//end

        public void  performsAmbientActions (boolean param1 )
        {
            this.m_performsAmbientActions = param1;
            return;
        }//end

        public boolean  performsAmbientActions ()
        {
            return this.m_performsAmbientActions;
        }//end

        public double  nextAmbientActionStart ()
        {
            return this.m_nextAmbientActionStart;
        }//end

        public NPCActionQueue  getStateMachine ()
        {
            return m_actionQueue;
        }//end

        public IActionSelection  actionSelection ()
        {
            return this.m_actionSelection;
        }//end

        public void  actionSelection (IActionSelection param1 )
        {
            this.m_actionSelection = param1;
            return;
        }//end

        public void  tooltipHeaderGenerator (Function param1 )
        {
            this.m_tooltipHeaderGenerator = param1;
            return;
        }//end

        public void  tooltipStatusGenerator (Function param1 )
        {
            this.m_tooltipStatusGenerator = param1;
            return;
        }//end

        public void  randomizeNextAmbientActionStart ()
        {
            this.m_nextAmbientActionStart = GlobalEngine.getTimer() + Math.floor((1 + Math.random()) * this.MIN_AMBIENT_DELTA);
            return;
        }//end

         public void  setPosition (double param1 ,double param2 ,double param3 =0)
        {
            super.setPosition(param1, param2, param3);
            return;
        }//end

        public void  queueFeedbackBubble (String param1 ,double param2 =-1)
        {
            Object _loc_3 =new Object ();
            _loc_3.url = param1;
            _loc_3.duration = param2;
            this.m_feedbackQueue.push(_loc_3);
            return;
        }//end

        public void  showFeedbackBubble (String param1 ,double param2 =-1)
        {
            if (param2 > 0)
            {
                this.m_feedbackEnd = GlobalEngine.getTimer() + int(param2 * 1000);
            }
            else
            {
                this.m_feedbackEnd = -1;
            }
            LoadingManager.load(param1, this.onFeedbackAssetLoaded);
            return;
        }//end

        public void  clearStates ()
        {
            m_actionQueue.removeAllStates();
            return;
        }//end

         public void  setHighlighted (boolean param1 ,int param2 =1.67552e +007)
        {
            _loc_3 = m_highlighted;
            super.setHighlighted(param1, param2);
            if (this.m_onHighlightCallback != null)
            {
                this.m_onHighlightCallback(param1, _loc_3);
            }
            return;
        }//end

        public void  highlightCallback (Function param1 )
        {
            this.m_onHighlightCallback = param1;
            return;
        }//end

        public Function  highlightCallback ()
        {
            return this.m_onHighlightCallback;
        }//end

        public Function  enterMapResourceCallback ()
        {
            return this.m_enterMapResourceCallback;
        }//end

        public void  enterMapResourceCallback (Function param1 )
        {
            this.m_enterMapResourceCallback = param1;
            return;
        }//end

        public Function  exitMapResourceCallback ()
        {
            return this.m_exitMapResourceCallback;
        }//end

        public void  exitMapResourceCallback (Function param1 )
        {
            this.m_exitMapResourceCallback = param1;
            return;
        }//end

        public void  imageLoadedCallback (Function param1 )
        {
            this.m_imageLoadedCallback = param1;
            return;
        }//end

        public Function  imageLoadedCallback ()
        {
            return this.m_imageLoadedCallback;
        }//end

        protected Sprite  createFeedbackBubble (DisplayObject param1 )
        {
            Sprite _loc_2 =new Sprite ();
            Sprite _loc_3 =new Sprite ();
            _loc_3.addChild(param1);
            _loc_2.addChild(new EmbeddedArt.npcThoughtBubble());
            _loc_2.addChild(_loc_3);
            _loc_3.x = _loc_2.width / 2 - param1.width / 2;
            if (param1.height == 16)
            {
                _loc_3.y = 5;
            }
            return _loc_2;
        }//end

        private void  onFeedbackAssetLoaded (Event event )
        {
            if (m_outer != null)
            {
                this.m_feedback = this.createFeedbackBubble(((LoaderInfo)event.target).content);
                TweenLite.from(this.m_feedback, 0.3, {alpha:0});
                m_arrow = this.m_feedback;
                setState(getState());
                this.conditionallyReattach();
                ;
            }
            return;
        }//end

        public void  hideFeedbackBubble ()
        {
            if (this.m_feedback && m_arrow)
            {
                TweenLite.to(this.m_feedback, 0.3, {alpha:0, onComplete:this.finishHideFeedback});
            }
            return;
        }//end

        private void  finishHideFeedback ()
        {
            if (this.m_feedbackEnd == -1)
            {
                this.m_feedback = null;
                m_arrow = null;
                if (m_outer != null)
                {
                    setState(getState());
                    this.conditionallyReattach();
                    ;
                }
            }
            return;
        }//end

        public double  velocityWalk ()
        {
            return this.m_velocityWalk;
        }//end

        public double  velocityRun ()
        {
            return this.m_velocityRun;
        }//end

        public void  velocityWalk (double param1 )
        {
            this.m_velocityWalk = param1;
            return;
        }//end

        public void  velocityRun (double param1 )
        {
            this.m_velocityRun = param1;
            return;
        }//end

        public double  roadOffset ()
        {
            return this.m_roadOffset;
        }//end

         public void  setItem (Item param1 )
        {
            super.setItem(param1);
            m_size.x = 0.01;
            m_size.y = 0.01;
            m_size.z = 0;
            return;
        }//end

         public String  getToolTipHeader ()
        {
            if (this.m_tooltipHeaderGenerator == null)
            {
                return null;
            }
            return this.m_tooltipHeaderGenerator();
        }//end

         public String  getToolTipStatus ()
        {
            if (this.m_tooltipStatusGenerator == null)
            {
                return null;
            }
            return this.m_tooltipStatusGenerator();
        }//end

        public void  animation (String param1 )
        {
            this.m_animationState = param1;
            setState(getState());
            return;
        }//end

        public String  animation ()
        {
            return this.m_animationState;
        }//end

         protected ItemImageInstance  getCurrentImage ()
        {
            return m_item.getCachedImage(this.m_animationState, this, m_direction);
        }//end

        public void  drawNavFeedback ()
        {
            Vector3 _loc_1 =null ;
            Point _loc_2 =null ;
            double _loc_3 =0;
            PathElement _loc_4 =null ;
            Vector3 _loc_5 =null ;
            if (!this.m_navDbgPath)
            {
                if (this.m_navDbgFeedback)
                {
                    GlobalEngine.viewport.objectBase.removeChild(this.m_navDbgFeedback);
                    this.m_navDbgFeedback = null;
                }
                return;
            }
            if (!this.m_navDbgFeedback)
            {
                this.m_navDbgFeedback = new Shape();
                GlobalEngine.viewport.objectBase.addChild(this.m_navDbgFeedback);
            }
            this.m_navDbgFeedback.graphics.clear();
            if (this.m_navDbgPath && this.m_navDbgPath.length > 0)
            {
                this.m_navDbgFeedback.graphics.lineStyle(0.5, 16711680);
                _loc_1 = getPosition();
                _loc_2 = IsoMath.tilePosToPixelPos(_loc_1.x, _loc_1.y);
                this.m_navDbgFeedback.graphics.moveTo((_loc_2.x - 1), _loc_2.y);
                this.m_navDbgFeedback.graphics.lineTo((_loc_2.x + 1), _loc_2.y);
                this.m_navDbgFeedback.graphics.moveTo(_loc_2.x, _loc_2.y);
                this.m_navDbgFeedback.graphics.lineTo(_loc_2.x, (_loc_2.y - 1));
                this.m_navDbgFeedback.graphics.lineTo(_loc_2.x, (_loc_2.y + 1));
                this.m_navDbgFeedback.graphics.moveTo(_loc_2.x, _loc_2.y);
                _loc_3 = 0;
                while (_loc_3 < this.m_navDbgPath.length())
                {

                    _loc_4 = this.m_navDbgPath.get(_loc_3);
                    _loc_5 = _loc_4.offsetPosition;
                    _loc_2 = IsoMath.tilePosToPixelPos(_loc_5.x, _loc_5.y);
                    if (this.m_navRandom)
                    {
                        this.m_navDbgFeedback.graphics.lineStyle(0.5, 16711935);
                    }
                    else
                    {
                        this.m_navDbgFeedback.graphics.lineStyle(0.5, 65535);
                    }
                    this.m_navDbgFeedback.graphics.lineTo(_loc_2.x, _loc_2.y);
                    this.m_navDbgFeedback.graphics.lineStyle(0.5, 16711680);
                    this.m_navDbgFeedback.graphics.moveTo((_loc_2.x - 1), _loc_2.y);
                    this.m_navDbgFeedback.graphics.lineTo((_loc_2.x + 1), _loc_2.y);
                    this.m_navDbgFeedback.graphics.moveTo(_loc_2.x, _loc_2.y);
                    this.m_navDbgFeedback.graphics.lineTo(_loc_2.x, (_loc_2.y - 1));
                    this.m_navDbgFeedback.graphics.lineTo(_loc_2.x, (_loc_2.y + 1));
                    this.m_navDbgFeedback.graphics.moveTo(_loc_2.x, _loc_2.y);
                    _loc_3 = _loc_3 + 1;
                }
            }
            return;
        }//end

         protected void  onItemImageLoaded (Event event )
        {
            super.onItemImageLoaded(event);
            if (this.m_imageLoadedCallback != null)
            {
                this.m_imageLoadedCallback();
            }
            return;
        }//end

         public void  conditionallyReattach (boolean param1 =false )
        {
            double _loc_2 =0;
            if (isAttached() == false)
            {
                this.attach();
            }
            else if (param1 || m_transformDirty || m_displayObjectDirty || m_attachPosition == null)
            {
                if (!this.m_alwaysCulled)
                {
                    if (m_displayObjectDirty)
                    {
                        drawDisplayObject();
                    }
                    else
                    {
                        updateDisplayObjectTransform();
                    }
                    _loc_2 = depthIndex;
                    this.calculateDepthIndex();
                    if (depthIndex != _loc_2)
                    {
                        m_outer.updateObjectInDepthArray(this);
                    }
                }
                updateCulling();
                m_attachPosition = m_position;
                m_attachSize = m_size;
                m_transformDirty = false;
            }
            return;
        }//end

        public Rectangle  calcDirtyRect (RenderContext param1 )
        {
            Point _loc_2 =null ;
            Rectangle _loc_3 =null ;
            CompositeItemImage _loc_4 =null ;
            if (m_lastScreenPosition)
            {
                _loc_2 = param1.transformMatrix.transformPoint(m_lastScreenPosition);
                if (m_imageClass)
                {
                    _loc_4 =(CompositeItemImage) m_imageClass.image;
                    if (_loc_4)
                    {
                        m_dirtyRect = _loc_4.getDirtyRect();
                        m_dirtyRect.x = m_dirtyRect.x + _loc_2.x;
                        m_dirtyRect.y = m_dirtyRect.y + _loc_2.y;
                    }
                }
                _loc_3 = this.calculateFeedbackDirtyRect(param1, _loc_2);
                m_dirtyRect = m_dirtyRect.union(_loc_3);
            }
            else
            {
                m_lastScreenPosition = new Point();
            }
            return m_dirtyRect.clone();
        }//end

        protected Rectangle  calculateFeedbackDirtyRect (RenderContext param1 ,Point param2 )
        {
            double _loc_3 =0;
            double _loc_4 =0;
            if (this.m_feedbackRendered)
            {
                this.m_feedbackDirtyRect.width = this.m_feedbackRect.width;
                this.m_feedbackDirtyRect.height = this.m_feedbackRect.height;
                _loc_3 = m_displayObject.scaleX * param1.transformMatrix.a;
                _loc_4 = m_displayObject.scaleY * param1.transformMatrix.d;
                this.m_feedbackDirtyRect.x = param2.x + _loc_3 * this.m_lastFeedbackPosition.x;
                this.m_feedbackDirtyRect.y = param2.y + _loc_3 * this.m_lastFeedbackPosition.y;
                this.m_feedbackDirtyRect.x = int(this.m_feedbackDirtyRect.x);
                this.m_feedbackDirtyRect.y = int(this.m_feedbackDirtyRect.y);
            }
            else
            {
                this.m_feedbackDirtyRect.setEmpty();
            }
            return this.m_feedbackDirtyRect.clone();
        }//end

         public void  render (RenderContext param1 )
        {
            Point _loc_2 =null ;
            Point _loc_3 =null ;
            double _loc_4 =0;
            double _loc_5 =0;
            CompositeItemImage _loc_6 =null ;
            _loc_2 = param1.transformMatrix.transformPoint(m_screenPosition);
            if (m_lastScreenPosition)
            {
                _loc_3 = param1.transformMatrix.transformPoint(m_lastScreenPosition);
            }
            else
            {
                m_lastScreenPosition = new Point();
                _loc_3 = new Point();
                _loc_3.x = _loc_2.x;
                _loc_3.y = _loc_2.y;
            }
            if (m_imageClass)
            {
                _loc_6 =(CompositeItemImage) m_imageClass.image;
                if (_loc_6)
                {
                    _loc_4 = m_displayObject.scaleX * param1.transformMatrix.a;
                    _loc_5 = m_displayObject.scaleY * param1.transformMatrix.d;
                    if (param1.targetBuffer)
                    {
                        _loc_6.renderCiToBuffer(param1, _loc_4, _loc_5, _loc_3, _loc_2, this.alpha);
                    }
                }
                this.renderFeedback(param1, _loc_3, _loc_2);
            }
            m_lastScreenPosition.x = m_screenPosition.x;
            m_lastScreenPosition.y = m_screenPosition.y;
            return;
        }//end

        protected void  renderFeedback (RenderContext param1 ,Point param2 ,Point param3 )
        {
            Point _loc_4 =null ;
            Point _loc_5 =null ;
            double _loc_6 =0;
            double _loc_7 =0;
            int _loc_8 =0;
            DisplayObject _loc_9 =null ;
            Bitmap _loc_10 =null ;
            if (this.m_feedback)
            {
                this.m_lastFeedbackPosition.x = this.m_feedback.x;
                this.m_lastFeedbackPosition.y = this.m_feedback.y;
                _loc_6 = m_displayObject.scaleX * param1.transformMatrix.a;
                _loc_7 = m_displayObject.scaleY * param1.transformMatrix.d;
                _loc_4 = param2.clone();
                param2.clone().x = _loc_4.x + _loc_6 * this.m_feedback.x;
                _loc_4.y = _loc_4.y + _loc_7 * this.m_feedback.y;
                _loc_5 = param3.clone();
                param3.clone().x = _loc_5.x + _loc_6 * this.m_feedback.x;
                _loc_5.y = _loc_5.y + _loc_7 * this.m_feedback.y;
                this.m_feedbackRect.x = int(_loc_4.x);
                this.m_feedbackRect.y = int(_loc_4.y);
                this.m_feedbackRect.width = this.m_feedback.width;
                this.m_feedbackRect.height = this.m_feedback.height;
                _loc_4.x = int(_loc_4.x);
                _loc_4.y = int(_loc_4.y);
                _loc_8 = 0;
                while (_loc_8 < this.m_feedback.numChildren)
                {

                    _loc_9 = this.m_feedback.getChildAt(_loc_8);
                    _loc_10 =(Bitmap) _loc_9;
                    if (_loc_10)
                    {
                        this.m_feedbackRendered = true;
                        this.m_feedbackRect = _loc_10.bitmapData.rect.clone();
                        this.m_feedbackRect.x = int(_loc_4.x);
                        this.m_feedbackRect.y = int(_loc_4.y);
                        if (this.alpha > 0.2)
                        {
                            if (param1.alphaBuffer)
                            {
                                param1.targetBuffer.copyPixels(_loc_10.bitmapData, _loc_10.bitmapData.rect, _loc_5, param1.alphaBuffer, _loc_5, false);
                            }
                            else
                            {
                                param1.targetBuffer.copyPixels(_loc_10.bitmapData, _loc_10.bitmapData.rect, _loc_5);
                            }
                        }
                    }
                    _loc_8++;
                }
            }
            return;
        }//end

         public int  shardingCategory ()
        {
            return ShardScheduler.CATEGORY_HIGH_PRIORITY;
        }//end

        public boolean  canBeThinned ()
        {
            return !this.slidePick;
        }//end

         public void  calcNextUpdateTime (int param1 )
        {
            ActionNavigate _loc_2 =null ;
            int _loc_3 =0;
            if (m_experimentNPCThinning && this.alwaysCulled)
            {
                _loc_2 =(ActionNavigate) m_actionQueue.getState();
                if (_loc_2)
                {
                    _loc_3 = _loc_2.getSecondsLeft() * 1000;
                    if (_loc_3 > 80 * 1000)
                    {
                        _loc_3 = 80 * 1000;
                    }
                    nextUpdateTime = GlobalEngine.currentTime + _loc_3;
                    return;
                }
            }
            super.calcNextUpdateTime(param1);
            return;
        }//end

         public void  onUpdate (double param1 )
        {
            Object _loc_2 =null ;


            super.onUpdate(param1);
            if (Config.DEBUG_MODE)
            {
                this.drawNavFeedback();
            }
            this.updateActionQueue(param1);
            if (this.m_feedback != null && this.m_feedbackEnd >= 0)
            {
                if (GlobalEngine.getTimer() > this.m_feedbackEnd)
                {
                    this.m_feedbackEnd = -1;
                    this.hideFeedbackBubble();
                }
            }
            if (this.m_feedbackQueue.length > 0 && this.m_feedbackEnd == -1)
            {
                _loc_2 = this.m_feedbackQueue.shift();
                this.showFeedbackBubble(_loc_2.url, _loc_2.duration);
            }
            if (this.m_slidePick != null)
            {
                this.m_slidePick.setPosition(m_position.x, m_position.y);
            }
            return;
        }//end

        protected void  updateActionQueue (double param1 )
        {
            Array _loc_2 =null ;
            if (m_actionQueue.getState() == null)
            {
                _loc_2 = this.m_actionSelection.getNextActions();
                if (_loc_2 != null && _loc_2.length > 0)
                {
                    m_actionQueue.addActionsArray(_loc_2);
                }
            }
            return;
        }//end

         public boolean  canBeDragged ()
        {
            return false;
        }//end

         public void  showObjectBusy ()
        {
            return;
        }//end

        public void  pauseAndWave ()
        {
            if (this.m_paused)
            {
                return;
            }
            this.m_oldAnimation = this.animation;
            if (getItem().hasCachedImageByName("wave"))
            {
                this.animation = "wave";
            }
            else if (getItem().hasCachedImageByName("cheer"))
            {
                this.animation = "cheer";
            }
            else
            {
                this.animation = "idle";
            }
            this.getStateMachine().insertActionsArray(Math.max(0, this.getStateMachine().getStates().length()), [new ActionPause(this, -1)]);
            this.m_paused = true;
            return;
        }//end

        public void  unpause ()
        {
            if (!this.m_paused)
            {
                return;
            }
            this.animation = this.m_oldAnimation;
            actionQueue.popState();
            this.m_paused = false;
            return;
        }//end

    }




