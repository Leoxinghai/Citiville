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
import Classes.doobers.*;
import Classes.effects.*;
import Classes.inventory.*;
import Classes.sim.*;
import Classes.util.*;
import Display.*;
import Display.Toaster.*;
import Display.aswingui.*;
import Engine.*;
import Engine.Classes.*;
import Engine.Helpers.*;
import Engine.Interfaces.*;
import Engine.Managers.*;
import Engine.Transactions.*;
import Events.*;
import GameMode.*;
import Mechanics.*;
import Mechanics.GameMechanicInterfaces.*;
import Modules.crew.*;
import Modules.goals.*;
import Modules.goals.mastery.*;
import Modules.socialinventory.*;
import Modules.socialinventory.GameMode.*;
import Modules.stats.experiments.*;
import Modules.stats.types.*;
import Modules.storage.*;
import Transactions.*;

import com.greensock.*;
//import flash.display.*;
//import flash.events.*;
//import flash.filters.*;
//import flash.geom.*;
//import flash.media.*;
//import flash.text.*;
//import flash.utils.*;
import org.aswing.*;

import Classes.*;

import com.xinghai.Debug;

    public class MapResource extends ItemInstance implements ICustomToolTipTarget
    {
        protected ItemImageInstance m_imageClass ;
        protected DisplayObject m_itemImage ;
        protected double m_colorTransform =16777215;
        protected String m_itemOwner ;
        protected boolean m_isPendingOrder =false ;
        protected String m_state ;
        protected DisplayObject m_content ;
        protected int m_upgradeActionCount =0;
        protected Point m_lastScreenPosition ;
        protected Rectangle m_dirtyRect ;
        protected boolean m_visitorHighlight =false ;
        protected BitmapData m_scratchBuffer1 ;
        protected BitmapData m_scratchBuffer2 ;
        protected Rectangle m_scratchBufferRect ;
        protected RenderContext m_scratchBufferRenderContext ;
        protected Rectangle m_worldRect ;
        protected Rectangle m_arrowRect ;
        protected boolean m_optFramerate =true ;
        protected boolean m_roundRobbinAnim =true ;
        protected Matrix m_objMat ;
        protected Loader m_maskBitmapLoader ;
        protected Bitmap m_maskBitmap ;
        private DisplayObject m_effectOverlay =null ;
        protected boolean m_isSelected ;
        protected boolean m_isDragged ;
        protected boolean m_isBeingAutoHarvested =false ;
        protected boolean m_isBeingRotated ;
        protected Dictionary m_sparkly ;
        protected Array m_roadNotAdjacentEffects ;
        protected boolean m_isRoadVerifiable =false ;
        protected boolean m_connectsToRoad =false ;
        protected boolean m_dbgServerConnectsToRoad =false ;
        protected int m_adjRoadFlags =0;
        public  int LEFT_SIDE =1;
        public  int RIGHT_SIDE =2;
        public  int BOTTOM_SIDE =4;
        public  int TOP_SIDE =8;
        public Dictionary sideDirectionMap ;
        protected double itemImageOffsetX =0;
        protected double itemImageOffsetY =0;
        protected String m_statePhase ;
        protected Array m_animatedEffects ;
        protected boolean m_ownable =false ;
        protected boolean m_storable =false ;
        protected boolean m_isShowStateTransition =false ;
        protected boolean m_isHighlightable =true ;
        protected Array m_doobersArray ;
        protected Array m_secureRands ;
        protected Array m_npcNames =null ;
        private double m_SoundClickPlayed =-1;
        private double m_SoundClickDelayTime =-1;
        protected String m_statsName ;
        protected String m_statsItemName ;
        protected Dictionary m_statsActionNames ;
        protected StagePickEffect m_stagePickEffect =null ;
        protected SupplyStorage m_supplyStorage ;
        private int m_currentPopulation =0;
        protected boolean m_showUpgradeArrow =true ;
        protected Object m_toolTipComponents ;
        protected SimpleProgressBar m_levelProgressComponent ;
        protected JLabel m_alternativeLevelLabel ;
        protected JTextField m_stringWidthTester ;
        protected double m_stringWidthThreshold =0;
        protected boolean m_active ;
        private MapResource m_pathProvider =null ;
        protected Function m_defaultUpgradeFinish =null ;
        protected Function m_upgradeEffectsFinish =null ;
        protected UpgradeActions m_upgradeActions ;
        protected boolean m_giveVisitMastery =false ;
        private TimelineLite m_glowTimeline ;
        protected boolean m_isMoveLocked =false ;
        protected NPCActionQueue m_actionQueue ;
        public static  String REPLACE_CONTENT ="MapResourceEvent:ReplaceContent";
        public static  String PLACE_ON_LAND ="land";
        public static  String PLACE_ON_WATER ="water";
        public static  String PLACE_ON_LAND_AND_WATER ="land_and_water";
        public static  String PLACE_ON_LAND_OR_WATER ="land_or_water";
        private static BitmapData m_arrowBitmapData ;
        private static boolean m_sellDialogShowing =false ;
        public static  String BUILD_COMPLETE_EVENT ="OnBuildComplete";
        private static int m_pixelOffset =20;
        private static ImageLoader m_effectLoader =null ;
        private static boolean m_altEffectSoundLoaded =false ;
        private static DropShadowFilter edgeDirtyFilter =new DropShadowFilter(4,40,0,0.8,8,8,3,1,true );
        private static DropShadowFilter dirtyFilter =new DropShadowFilter(4,40,14540032,0.7,12,12,2,4,true );

        public  MapResource (String param1 )
        {

            int _loc_4 =0;
            int _loc_5 =0;
            this.m_dirtyRect = new Rectangle();
            this.m_scratchBufferRect = new Rectangle();
            this.m_scratchBufferRenderContext = new RenderContext();
            this.m_worldRect = new Rectangle();
            this.m_arrowRect = new Rectangle();
            this.m_objMat = new Matrix();
            this.sideDirectionMap = new Dictionary();
            this.m_statePhase = Item.DEFAULT_PHASE;
            this.m_animatedEffects = new Array();
            this.m_toolTipComponents = new Object();
            super(param1);
            m_objectType = Constants.WORLDOBJECT_ITEM;
            m_typeName = "item";
            m_collisionFlags = Constants.COLLISION_ALL;
            if (m_item)
            {
                this.m_ownable = m_item.ownable;
                this.m_storable = m_item.storable;
                setShowHighlight(!m_item.useTileHighlight);
            }
            else
            {
                throw new Error("Item " + param1 + " does not exist.");
            }
            this.m_secureRands = new Array();
            this.m_active = false;
            Global.world.citySim.poiManager.addPOI(this);
            this.m_npcNames = Global.world.citySim.npcManager.getAllNpcNames();
            this.m_actionQueue = new NPCActionQueue();
            this.m_sparkly = new Dictionary();
            if (!m_altEffectSoundLoaded)
            {
                SoundManager.addSound("setgraphic", Global.getAssetURL("assets/sounds/ui/sfx_achieve_stage4.mp3"), false);
                m_altEffectSoundLoaded = true;
            }
            this.m_currentPopulation = m_item.populationBase;
            this.sideDirectionMap.put(this.LEFT_SIDE,  Constants.DIRECTION_SW);
            this.sideDirectionMap.put(this.RIGHT_SIDE,  Constants.DIRECTION_NE);
            this.sideDirectionMap.put(this.TOP_SIDE,  Constants.DIRECTION_NW);
            this.sideDirectionMap.put(this.BOTTOM_SIDE,  Constants.DIRECTION_SE);
            this.m_statsActionNames = new Dictionary();
            this.statsInit();
            if (this instanceof NPC)
            {
                this.m_roundRobbinAnim = false;
            }
            _loc_2 = m_item.xml.animRoundRobbinOpt;
            _loc_3 = m_item.xml.animFramerateOpt;
            if (_loc_2 != "")
            {
                _loc_4 = int(_loc_2);
                this.m_roundRobbinAnim = _loc_4 != 0;
            }
            if (_loc_3 != "")
            {
                _loc_5 = int(_loc_3);
                this.m_optFramerate = _loc_5 != 0;
            }
            this.m_supplyStorage = null;
            this.m_upgradeActions = new UpgradeActions(this);
            return;
        }//end

        public String  itemOwner ()
        {
            return this.m_itemOwner ? (this.m_itemOwner) : (Global.world.ownerId);
        }//end

        public void  itemOwner (String param1 )
        {
            this.m_itemOwner = param1;
            return;
        }//end

        public boolean  isPendingOrder ()
        {
            return this.m_isPendingOrder;
        }//end

        public void  isPendingOrder (boolean param1 )
        {
            this.m_isPendingOrder = param1;
            if (this.m_isPendingOrder)
            {
                lock();
            }
            return;
        }//end

        public boolean  autoRotate ()
        {
            _loc_1 =             !m_item.noAutoRotate;
            if (m_size.x != m_size.y)
            {
                _loc_1 = false;
            }
            return _loc_1;
        }//end

        protected void  statsInit ()
        {
            this.m_statsName = m_item.type;
            this.m_statsItemName = m_item.name;
            return;
        }//end

        protected void  registerStatsActionName (String param1 ,String param2 )
        {
            this.m_statsActionNames.put(param1,  param2);
            return;
        }//end

        protected String  getStatsActionName (String param1 )
        {
            return this.m_statsActionNames.get(param1) || param1;
        }//end

        public void  trackAction (String param1 ,int param2 =1)
        {
            this.trackDetailedAction(param1, "", "", param2);
            return;
        }//end

        public Item  harvestingDefinition ()
        {
            return m_item;
        }//end

        public void  trackDetailedAction (String param1 ,String param2 ,String param3 ,int param4 =1)
        {
            _loc_5 = Global.experimentManager.getVariant(ExperimentDefinitions.EXPERIMENT_TIKI_SOCIAL);
            if (Global.experimentManager.getVariant(ExperimentDefinitions.EXPERIMENT_TIKI_SOCIAL) > 0 && _loc_5 < 4 && (param1 == TrackedActionType.HARVEST && (this instanceof Business || this instanceof Plot)) || this instanceof SocialBusiness)
            {
                StatsManager.count(StatsKingdomType.GAME_ACTIONS, this.m_statsName, this.getStatsActionName(param1), this.m_statsItemName, param2, param3, param4);
            }
            else
            {
                StatsManager.sample(100, StatsKingdomType.GAME_ACTIONS, this.m_statsName, this.getStatsActionName(param1), this.m_statsItemName, param2, param3, param4);
            }
            return;
        }//end

        public void  trackVisitAction (String param1 ,int param2 =1,String param3 ="",String param4 ="")
        {
            if (Global.world.ownerId != "-1")
            {
                StatsManager.social(StatsCounterType.SOCIAL_INTERACTIONS, Global.world.ownerId, StatsKingdomType.NEIGHBOR_VISIT, param1, param3, param4, param2);
            }
            return;
        }//end

        public void  trackSocialAction (String param1 ,String param2 ="",String param3 ="",String param4 ="",int param5 =1)
        {
            if (Global.world.ownerId != "-1")
            {
                StatsManager.social(param1, Global.world.ownerId, param2, param3, param4, "", param5);
            }
            return;
        }//end

        public int  getPopularity ()
        {
            int _loc_1 =1;
            if (m_item.xml.npcPopularity.length() != 0)
            {
                _loc_1 = int(m_item.xml.npcPopularity);
            }
            return _loc_1;
        }//end

        public boolean  doRoundRobbinAnimOpt ()
        {
            return this.m_roundRobbinAnim;
        }//end

        public boolean  doFramerateAnimOpt ()
        {
            return this.m_optFramerate;
        }//end

        public DisplayObject  content ()
        {
            return this.m_content;
        }//end

        public DisplayObject  displayObject ()
        {
            return m_displayObject;
        }//end

        public Point  tileScreenPosition ()
        {
            _loc_1 = getReference().getPositionNoClone();
            return IsoMath.tilePosToPixelPos(_loc_1.x, _loc_1.y, 0, true);
        }//end

        public double  currentDepthIndex ()
        {
            return depthIndex;
        }//end

        public double  displayObjectOffsetX ()
        {
            _loc_1 = m_displayObject.x-this.tileScreenPosition.x;
            return _loc_1;
        }//end

        public double  displayObjectOffsetY ()
        {
            _loc_1 = m_displayObject.y-this.tileScreenPosition.y;
            return _loc_1;
        }//end

        public Array  npcNames ()
        {
            return this.m_npcNames;
        }//end

        public Function  defaultUpgradeFinishCallback ()
        {
            return this.m_defaultUpgradeFinish;
        }//end

        public void  defaultUpgradeFinishCallback (Function param1 )
        {
            this.m_defaultUpgradeFinish = param1;
            return;
        }//end

        public Function  upgradeEffectsFinishCallback ()
        {
            return this.m_upgradeEffectsFinish;
        }//end

        public void  setActive (boolean param1 )
        {
            this.m_active = param1;
            Global.world.citySim.poiManager.updatePOI(this);
            return;
        }//end

        public boolean  isActive ()
        {
            return this.m_active;
        }//end

        public boolean  isOwnable ()
        {
            return this.m_ownable;
        }//end

        public boolean  isStorable ()
        {
            return this.m_storable;
        }//end

        public boolean  isUsingPerPixelPick ()
        {
            return true;
        }//end

        public boolean  usingTileHighlight ()
        {
            return m_item.useTileHighlight;
        }//end

        public boolean  usingTilePicking ()
        {
            return m_item.useTilePicking;
        }//end

        public boolean  isSelected ()
        {
            return this.m_isSelected;
        }//end

        public void  clearRoadSideFlags ()
        {
            this.m_adjRoadFlags = 0;
            this.m_connectsToRoad = false;
            return;
        }//end

        public void  setRoadSideFlag (int param1 )
        {
            this.m_adjRoadFlags = this.m_adjRoadFlags | param1;
            return;
        }//end

        public void  setConnectsRoadFlag (boolean param1 )
        {
            this.m_connectsToRoad = param1;
            return;
        }//end

        public boolean  isRoadAdjacent (int param1 )
        {
            return (this.m_adjRoadFlags & param1) != 0;
        }//end

        public boolean  isAdjacentToAnyRoad ()
        {
            return this.m_connectsToRoad;
        }//end

        public Vector3  getRoadSidePosition (int param1 )
        {
            _loc_2 = getPosition();
            _loc_3 = m_size.x*0.5;
            _loc_4 = m_size.y*0.5;
            _loc_2.x = _loc_2.x + _loc_3;
            _loc_2.y = _loc_2.y + _loc_4;
            if (param1 == this.LEFT_SIDE)
            {
                _loc_2.x = _loc_2.x - _loc_3;
            }
            else if (param1 == this.RIGHT_SIDE)
            {
                _loc_2.x = _loc_2.x + _loc_3;
            }
            else if (param1 == this.BOTTOM_SIDE)
            {
                _loc_2.y = _loc_2.y - _loc_4;
            }
            else if (param1 == this.TOP_SIDE)
            {
                _loc_2.y = _loc_2.y + _loc_4;
            }
            return _loc_2;
        }//end

        public boolean  isMoveLocked ()
        {
            return this.m_isMoveLocked;
        }//end

        public void  isMoveLocked (boolean param1 )
        {
            this.m_isMoveLocked = param1;
            return;
        }//end

        public void  unlockMovement ()
        {
            Point mousePos ;
            Point tilePos ;
            Vector3 currentPos ;
            Point offsetPos ;
            MapResource mapResource ;
            if (this.m_isMoveLocked && Global.player.checkEnergy(-m_item.unlockMovementEnergyCost) && !Global.world.isUnlockingMove)
            {
                Global.world.isUnlockingMove = true;
                mousePos = new Point(Global.stage.mouseX, Global.stage.mouseY);
                tilePos = IsoMath.screenPosToTilePos(mousePos.x, mousePos.y);
                currentPos = getPosition();
                offsetPos = new Point(tilePos.x - currentPos.x, tilePos.y - currentPos.y);
                mapResource;
                this.m_actionQueue.removeAllStates();
                this .m_actionQueue .addActions (new ActionProgressBar (null ,this ,ZLoc .t ("Main","Uprooting"),Global .gameSettings .getNumber ("actionBarUproot")),new ActionFn (this ,void  ()
            {
                Point _loc_1 =new Point(Global.stage.mouseX ,Global.stage.mouseY );
                _loc_2 = IsoMath.screenPosToTilePos(_loc_1.x,_loc_1.y);
                _loc_2.x = _loc_2.x - offsetPos.x;
                _loc_2.y = _loc_2.y - offsetPos.y;
                mapResource.setPosition(_loc_2.x, _loc_2.y);
                Sounds.play("click5");
                Global.world.addGameMode(new GMEditMove());
                Global.world.addGameMode(new GMObjectMove(mapResource, null, null, Constants.INDEX_NONE, false, currentPos), false);
                Global.world.isUnlockingMove = false;
                return;
            }//end
            ), new ActionAddEffect(this, EffectType.SIMPLE_BOUNCE));
                this.isMoveLocked = false;
                GameTransactionManager.addTransaction(new TUnlockMovement(this));
            }
            return;
        }//end

        public NPCActionQueue  actionQueue ()
        {
            return this.m_actionQueue;
        }//end

        public int  getCoinYield ()
        {
            return Math.floor(m_item.coinYield);
        }//end

        public int  getXpYield ()
        {
            return Math.floor(m_item.xpYield);
        }//end

         public Object  getSaveObject ()
        {
            _loc_1 = super.getSaveObject();
            _loc_1.state = this.m_state;
            return _loc_1;
        }//end

        public UpgradeActions  upgradeActions ()
        {
            return this.m_upgradeActions;
        }//end

        public int  upgradeActionCount ()
        {
            return this.m_upgradeActionCount;
        }//end

        public void  resetUpgradeActionCount ()
        {
            this.m_upgradeActionCount = 0;
            return;
        }//end

        public void  incrementUpgradeActionCount (boolean param1 =true )
        {
            this.m_upgradeActionCount++;
            return;
        }//end

        public void  decrementUpgradeActionCount ()
        {
            this.m_upgradeActionCount = Math.max((this.m_upgradeActionCount - 1), 0);
            return;
        }//end

         public void  loadObject (Object param1 )
        {
            super.loadObject(param1);
            this.m_itemOwner = param1.itemOwner || "";
            this.m_state = param1.state;
            m_direction = param1.direction;
            this.m_active = true;
            this.m_currentPopulation = param1.currentPopulation;
            if (!this.m_currentPopulation)
            {
                this.m_currentPopulation = m_item.populationBase;
            }
            this.isMoveLocked = param1.isMoveLocked;
            this.m_upgradeActionCount = param1.upgradeActionCount || 0;
            updateSize();
            this.updateArrow();
            this.m_supplyStorage = new SupplyStorage(param1.supplyStorage);
            return;
        }//end

         public Class  getCursor ()
        {
            if (Global.world.isEditMode)
            {
                return super.getCursor();
            }
            return super.getCursor();
        }//end

         public Point  getMenuPosition (Array param1 )
        {
            if (m_displayObject == null)
            {
                return null;
            }
            _loc_2 = (m_displayObject.width/m_displayObject.scaleX-ContextMenu.ITEM_WIDTH)/2;
            _loc_3 = ContextMenu(-.ITEM_HEIGHT)*param1.length;
            return m_displayObject.localToGlobal(new Point(_loc_2, _loc_3));
        }//end

        protected ItemImageInstance  getCurrentImage ()
        {
            if (m_item == null)
            {
                return null;
            }

            _loc_1 = m_item.getCachedImage(this.m_state,this,m_direction,this.m_statePhase);
            return _loc_1;
        }//end

        public boolean  isCurrentImageLoading ()
        {
            if (m_item == null)
            {
                return false;
            }
            return m_item.isCachedImageLoading(this.m_state, m_direction, this.m_statePhase);
        }//end

        public void  reloadImage ()
        {
            this.m_itemImage = null;
            this.m_maskBitmap = null;
            setDisplayObjectDirty(true);
            this.conditionallyRedraw();
            return;
        }//end

        public void  loadCurrentImage ()
        {
            this.getCurrentImage();
            return;
        }//end

         protected void  onItemImageLoaded (Event event )
        {
            this.m_itemImage = null;
            super.onItemImageLoaded(event);
            return;
        }//end

         public DisplayObject  createDisplayObject ()
        {
            Sprite _loc_1 =new Sprite ();
            if(m_displayObject == null) {
            	m_displayObject = _loc_1;
            }
            this.conditionallyRedraw(true);
            return m_displayObject;
        }//end

        public boolean  isDrawImageLoading ()
        {
            if (m_item == null)
            {
                return false;
            }
            return m_item.isCachedImageLoading(this.m_state, m_direction, this.m_statePhase);
        }//end

        public double  widthOverride ()
        {
            return -1;
        }//end

        public double  heightOverride ()
        {
            return -1;
        }//end

        public boolean  colorOverride ()
        {
            return false;
        }//end

        public int  colorOverrideHue ()
        {
            return 0;
        }//end

        public double  colorOverrideAlpha ()
        {
            return 0;
        }//end

         public void  drawDisplayObject ()
        {
            MovieClip _loc_2 =null ;
            Loader _loc_3 =null ;
            Object _loc_4 =null ;
            ColorTransform _loc_5 =null ;
            Sprite _loc_1 =(Sprite)m_displayObject;
            if (_loc_1 == null)
            {
                this.createDisplayObject();
            }

            if (_loc_1 != null)
            {
                _loc_2 = null;
                _loc_3 = null;
                if (this.m_itemImage == null)
                {
                    this.m_imageClass = this.getCurrentImage();


                    if (this.m_imageClass)
                    {

                      Debug.debug4("MapResource.imageclass"+this.m_imageClass);
                      Debug.debug4("MapResource.imageclass.image "+this.m_imageClass.image);

                        if (this.m_imageClass.image instanceof Class)
                        {
                            this.m_itemImage = new this.m_imageClass.image();
                            if (this.m_itemImage instanceof MovieClip)
                            {
                                _loc_2 =(MovieClip) this.m_itemImage;
                                _loc_3 =(Loader) _loc_2.getChildAt(0);
                                _loc_3.contentLoaderInfo.addEventListener(Event.COMPLETE, this.onImageLoaded);
                            }
                            else
                            {
                                this.replaceContent(this.m_itemImage);
                            }
                        }
                        else if (this.m_imageClass.image instanceof String && this.m_imageClass.image != null)
                        {
                            this.m_itemImage = LoadingManager.load((String)this.m_imageClass.image, this.onImageLoaded);
                        }
                        else if (this.m_imageClass.image instanceof ColorMaskedBitmap)
                        {
                            this.m_itemImage =(ColorMaskedBitmap) this.m_imageClass.image;
                            this.replaceContent(this.m_itemImage);
                        }
                        else if (this.m_imageClass.image instanceof Bitmap)
                        {
                            this.m_itemImage =(Bitmap) this.m_imageClass.image;
                            this.replaceContent(this.m_itemImage);
                        }
                        else if (this.m_imageClass.image instanceof Loader)
                        {
                            _loc_3 =(Loader) this.m_imageClass.image;
                            this.m_itemImage = _loc_3;
                            if (!Global.playAnimations && this.m_imageClass.lowResImage instanceof Bitmap)
                            {
                                this.replaceContent((Bitmap)this.m_imageClass.lowResImage);
                            }
                            else
                            {
                                _loc_3.contentLoaderInfo.addEventListener(Event.COMPLETE, this.onImageLoaded);
                            }
                        }
                        else if (this.m_imageClass.image instanceof MovieClip)
                        {
                            this.m_itemImage =(MovieClip) this.m_imageClass.image;
			    if( this instanceof Decoration) {
	                           //Sprite(m_displayObject).addChild(this.m_imageClass.image.getChildAt(0));
				   this.replaceContent(this.m_itemImage);
			    } else if(this instanceof DesirePeep) {
			           //Sprite(m_displayObject).addChild(this.m_imageClass.image.getChildAt(0));
				   this.replaceContent(this.m_itemImage);
                            } else {
				    this.replaceContent(this.m_itemImage);
                            }

                        }
                    }
                }
                else if (!Global.playAnimations && this.m_imageClass && this.m_imageClass.lowResImage instanceof Bitmap)
                {
                    this.replaceContent((Bitmap)this.m_imageClass.lowResImage);
                }
                else
                {
                    _loc_2 =(MovieClip) this.m_itemImage;
                    if (_loc_2)
                    {
                        _loc_3 =(Loader) _loc_2.getChildAt(0);
                        if (_loc_3 != null && _loc_3.content != null)
                        {
                            this.replaceContent(_loc_3.content);
                        }
                        else if (this.m_imageClass && this.m_imageClass.image instanceof MovieClip)
                        {
                            this.replaceContent((MovieClip)this.m_imageClass.image);
                        }
                    }
                    else if (this.m_itemImage instanceof Loader)
                    {
                        _loc_3 =(Loader) this.m_itemImage;
                        if (_loc_3 && _loc_3.content)
                        {
                            this.replaceContent(_loc_3.content);
                        }
                    }
                }
                if (this.m_maskBitmap == null)
                {
                    _loc_4 = this.getMaskBitmap();
                    if (_loc_4 instanceof String && this.m_maskBitmapLoader == null)
                    {
                        this.m_maskBitmapLoader = LoadingManager.load(Global.getAssetURL((String)_loc_4), this.onImageMaskLoaded);
                    }
                    else if (_loc_4 instanceof Loader)
                    {
                        this.m_maskBitmapLoader =(Loader) _loc_4;
                        this.m_maskBitmapLoader.contentLoaderInfo.addEventListener(Event.COMPLETE, this.onImageMaskLoaded);
                    }
                    else if (_loc_4 instanceof Bitmap)
                    {
                        this.m_maskBitmap =(Bitmap) _loc_4;
                    }
                    else if (_loc_4 instanceof DisplayObject)
                    {
                        this.generateImageMask((DisplayObject)_loc_4);
                    }
                }
                if (this.m_imageClass && this.m_imageClass.forcedWidth <= 0 && this.m_content)
                {
                    this.m_imageClass.forcedWidth = this.m_content.width;
                }
                if (this.m_itemImage)
                {
                    if (this.widthOverride() > 0)
                    {
                        this.m_itemImage.width = this.widthOverride();
                    }
                    if (this.heightOverride() > 0)
                    {
                        this.m_itemImage.height = this.heightOverride();
                    }
                    if (this.colorOverride())
                    {
                        _loc_5 = new ColorTransform();
                        _loc_5.color = this.colorOverrideHue();
                        _loc_5.alphaMultiplier = this.colorOverrideAlpha();
                        this.m_itemImage.transform.colorTransform = _loc_5;
                    }
                    this.updateDisabled();
                }
            }
            return;
        }//end

        public void  replaceContent (DisplayObject param1 )
        {
            MapResourceEffect _loc_3 =null ;
            SoundTransform _loc_4 =null ;
            Sprite _loc_2 =(Sprite)m_displayObject;
            boolean _loc_5 =false ;
            _loc_2.mouseChildren = false;
            _loc_2.mouseEnabled = _loc_5;
            this.m_content = param1;
            this.setHighlighted(m_highlighted);
            while (_loc_2.numChildren > 0)
            {

                _loc_2.removeChildAt(0);
            }

	   if( this instanceof WeiboPeep) {
	      TextField test =new TextField ();
	      TextFormat myFormat =new TextFormat ();
	      myFormat.font = "SimplifiedChineseRegular";
	      test.defaultTextFormat = myFormat;
	      if(((WeiboPeep)this).alias == null) {
	         test.text= "";
	      } else {
	      	test.text= ((WeiboPeep)this).alias;
              }

	      test.x=0;
	      test.y=-10;
              _loc_2.addChild(this.m_content);
              _loc_2.addChild(test);
	   } else {
              _loc_2.addChild(this.m_content);
	   }

            for(int i0 = 0; i0 < this.m_animatedEffects.size(); i0++)
            {
            		_loc_3 = this.m_animatedEffects.get(i0);

                if (_loc_3.allowReattachOnReplaceContent())
                {
                    _loc_3.reattach();
                }
            }
            dispatchEvent(new Event(REPLACE_CONTENT));



            this.updateRoadState();
            if (this.m_content instanceof MovieClip)
            {
                _loc_4 = new SoundTransform();
                if (Global.player.options.sfxDisabled)
                {
                    _loc_4.volume = 0;
                }
                else
                {
                    _loc_4.volume = 1;
                }
                MovieClip(this.m_content).soundTransform = _loc_4;
            }
            this.updateDisplayObjectTransform();
            if (m_arrow)
            {
                _loc_2.addChild(m_arrow);
                setArrowPosition();
            }
            if (Game.m_blitting && this.m_scratchBuffer1 == null)
            {
                this.m_scratchBuffer1 = new BitmapData(256, 256, true, 0);
            }
            if (Game.m_blitting && this.m_scratchBuffer2 == null)
            {
                this.m_scratchBuffer2 = new BitmapData(256, 256, true, 0);
            }


            return;



        }//end


        public void  replaceContent2 (DisplayObject param1 )
        {
            MapResourceEffect _loc_3 =null ;
            SoundTransform _loc_4 =null ;
            Sprite _loc_2 =(Sprite)m_displayObject;
            boolean _loc_5 =false ;
            _loc_2.mouseChildren = false;
            _loc_2.mouseEnabled = _loc_5;
            this.m_content = param1;
            this.setHighlighted(m_highlighted);
            while (_loc_2.numChildren > 0)
            {

                _loc_2.removeChildAt(0);
            }

            _loc_2.addChild(this.m_content);

            Global.circle1.addChild(_loc_2);


            Global.circle1.addChild(m_displayObject);

             if( this instanceof Train) {
                   Sprite testt =new Sprite ();
                   testt.addChild(this.m_content);

		   Debug.debug7("MapResource.replacecontent,train " + _loc_2.x+";"+_loc_2.y);
		   m_displayObject.x = 0;
		   m_displayObject.y = 0;
		   m_screenPosition.x=0;
		   m_screenPosition.y=0;
		   //Sprite(m_displayObject).addChild(this.m_imageClass.image.getChildAt(0));
		   m_displayObject = testt;
		   //Global.circle1.addChild(m_displayObject);
	     }

*/
            return;



        }//end


        protected double  getImageScaleFactor ()
        {
            double _loc_1 =1;
            if (m_item && m_item.imageScale)
            {
                _loc_1 = m_item.imageScale;
            }
            return _loc_1;
        }//end

        public double  getColorTransform ()
        {
            return this.m_colorTransform;
        }//end

        public void  setColorTransform (double param1 )
        {
            this.m_colorTransform = param1;
            return;
        }//end

        public MapResource  pathProvider ()
        {
            return this.m_pathProvider;
        }//end

        public void  pathProvider (MapResource param1 )
        {
            this.m_pathProvider = param1;
            return;
        }//end

         public Vector3  getEndPosition ()
        {
            if (!this.m_pathProvider)
            {
                return super.getEndPosition();
            }
            return this.m_pathProvider.getEndPosition();
        }//end

         public Point  getScreenPosition ()
        {
            if (!this.m_pathProvider)
            {
                return super.getScreenPosition();
            }
            return this.m_pathProvider.getScreenPosition();
        }//end

        public Vector3 Vector  getHotspots (String param1 ="").<>
        {
            Vector _loc_2.<Vector3 >=null ;
            Vector _loc_3.<Vector3 >=null ;
            int _loc_4 =0;
            if (!this.m_pathProvider)
            {
                _loc_2 = new Vector<Vector3>();
                if (!m_item || !this.getItemImageHotspots(m_item, this.m_state, m_direction, param1))
                {
                    _loc_2.put(0,  new Vector3(m_position.x, m_position.y));
                    return _loc_2;
                }
                _loc_2 = this.getItemImageHotspots(m_item, this.m_state, m_direction, param1);
                _loc_3 = new Vector<Vector3>(_loc_2.length());
                _loc_4 = 0;
                while (_loc_4 < _loc_2.length())
                {

                    _loc_3.put(_loc_4,  this.convertHotspotToAbsolute(_loc_2.get(_loc_4)));
                    _loc_4++;
                }
                return _loc_3;
            }
            else
            {
                return this.m_pathProvider.getHotspots(param1);
            }
        }//end

        protected Vector3 Vector  getItemImageHotspots (Item param1 ,String param2 ,int param3 ,String param4 ="").<>
        {
            return param1.getCachedImageHotspots(param2, param3, param4);
        }//end

        public Vector3  getHotspot ()
        {
            if (!this.m_pathProvider)
            {
                return this.getHotspots().get(0);
            }
            return this.m_pathProvider.getHotspot();
        }//end

        protected Vector3 Vector  computeDefaultHotspots ().<>
        {
            return Vector<Vector3>(.get(new Vector3(0.5, 0.5)));
        }//end

        protected Vector3  convertHotspotToAbsolute (Vector3 param1 ,...args )
        {
            return new Vector3(m_position.x + param1.x * m_item.sizeX, m_position.y + param1.y * m_item.sizeY);
        }//end

        protected double  getDisplayXOffset ()
        {
            return 0;
        }//end

        public Rectangle  calcDirtyRectMapResource (RenderContext param1 )
        {
            CompositeItemImage _loc_3 =null ;
            Point _loc_2 =new Point ();
            if (this.m_lastScreenPosition)
            {
                _loc_2 = param1.transformMatrix.transformPoint(this.m_lastScreenPosition);
                if (this.m_content)
                {
                    _loc_3 =(CompositeItemImage) this.m_content;
                    if (_loc_3)
                    {
                        this.m_dirtyRect = _loc_3.getDirtyRect();
                        this.m_dirtyRect.x = this.m_dirtyRect.x + _loc_2.x;
                        this.m_dirtyRect.y = this.m_dirtyRect.y + _loc_2.y;
                    }
                    else if (this.m_content instanceof Bitmap)
                    {
                        this.m_dirtyRect.x = _loc_2.x;
                        this.m_dirtyRect.y = _loc_2.y;
                        this.m_dirtyRect.width = this.m_content.width;
                        this.m_dirtyRect.height = this.m_content.height;
                    }
                }
            }
            return this.m_dirtyRect.clone();
        }//end

         public void  render (RenderContext param1 )
        {
            double _loc_2 =0;
            double _loc_3 =0;
            Bitmap _loc_4 =null ;
            CompositeItemImage _loc_5 =null ;
            Point _loc_6 =null ;
            if (isWithinViewport() && this.m_content)
            {
                if (renderBufferDirty && this.m_scratchBuffer1 && this.m_scratchBuffer2)
                {
                    _loc_2 = m_displayObject.scaleX * param1.transformMatrix.a;
                    _loc_3 = m_displayObject.scaleY * param1.transformMatrix.d;
                    if (m_arrow)
                    {
                        this.m_arrowRect.x = _loc_2 * m_arrow.x;
                        this.m_arrowRect.y = _loc_3 * m_arrow.y;
                        this.m_arrowRect.width = _loc_2 * m_arrow.width;
                        this.m_arrowRect.height = _loc_3 * m_arrow.height;
                    }
                    else
                    {
                        this.m_arrowRect.x = 0;
                        this.m_arrowRect.y = 0;
                        this.m_arrowRect.width = _loc_2 * this.m_content.width;
                        this.m_arrowRect.height = _loc_3 * this.m_content.height;
                    }
                    this.m_worldRect.x = 0;
                    this.m_worldRect.y = 0;
                    this.m_worldRect.width = _loc_2 * this.m_content.width;
                    this.m_worldRect.height = _loc_3 * this.m_content.height;
                    this.m_worldRect = this.m_worldRect.union(this.m_arrowRect);
                    _loc_4 =(Bitmap) this.m_content;
                    if (_loc_4)
                    {
                        this.renderBitmapToScratchBuffer(_loc_4, (Bitmap)m_arrow, _loc_2, _loc_3, this.m_worldRect, this.m_arrowRect);
                    }
                    renderBufferDirty = false;
                }
                if (!renderBufferDirty)
                {
                    if (this.m_content instanceof Bitmap)
                    {
                        _loc_6 = param1.transformMatrix.transformPoint(m_screenPosition);
                        param1.transformMatrix.transformPoint(m_screenPosition).x = _loc_6.x + this.m_worldRect.x;
                        _loc_6.y = _loc_6.y + this.m_worldRect.y;
                        _loc_6.x = _loc_6.x - m_pixelOffset;
                        _loc_6.y = _loc_6.y - m_pixelOffset;
                        param1.targetBuffer.copyPixels(this.m_scratchBuffer2, this.m_scratchBufferRect, _loc_6, null, null, true);
                    }
                    _loc_5 =(CompositeItemImage) this.m_content;
                    if (_loc_5)
                    {
                        _loc_6 = param1.transformMatrix.transformPoint(m_screenPosition);
                        this.m_objMat.a = m_displayObject.scaleX * param1.transformMatrix.a;
                        this.m_objMat.d = m_displayObject.scaleY * param1.transformMatrix.d;
                        this.m_objMat.tx = _loc_6.x;
                        this.m_objMat.ty = _loc_6.y;
                        _loc_5.renderCiToBufferOld(param1.targetBuffer, param1.alphaBuffer, this.m_objMat, this.alpha);
                    }
                }
                if (!this.m_lastScreenPosition)
                {
                    this.m_lastScreenPosition = new Point();
                }
                this.m_lastScreenPosition.x = m_screenPosition.x;
                this.m_lastScreenPosition.y = m_screenPosition.y;
            }
            return;
        }//end

        protected void  renderBitmapToScratchBuffer (Bitmap param1 ,Bitmap param2 ,double param3 ,double param4 ,Rectangle param5 ,Rectangle param6 )
        {
            ColorTransform _loc_9 =null ;
            ColorTransform _loc_10 =null ;
            Point _loc_7 =new Point (-param5.x ,-param5.y );
            this.m_scratchBuffer1.fillRect(this.m_scratchBuffer1.rect, 0);
            this.m_scratchBuffer2.fillRect(this.m_scratchBuffer2.rect, 0);
            Matrix _loc_8 =new Matrix ();
            _loc_8.tx = _loc_7.x;
            _loc_8.ty = _loc_7.y;
            _loc_8.a = param3;
            _loc_8.d = param4;
            _loc_8.tx = _loc_8.tx + m_pixelOffset;
            _loc_8.ty = _loc_8.ty + m_pixelOffset;
            if (this.alpha < 1)
            {
                _loc_9 = new ColorTransform();
                _loc_9.alphaMultiplier = this.alpha;
                this.m_scratchBuffer1.draw(param1, _loc_8, _loc_9);
            }
            else
            {
                this.m_scratchBuffer1.draw(param1, _loc_8);
            }
            if (param2)
            {
                _loc_8.tx = _loc_7.x + param3 * param6.x;
                _loc_8.ty = _loc_7.y + param4 * param6.y;
                _loc_8.a = param3;
                _loc_8.d = param4;
                _loc_8.tx = _loc_8.tx + m_pixelOffset;
                _loc_8.ty = _loc_8.ty + m_pixelOffset;
                if (this.alpha < 1)
                {
                    _loc_10 = new ColorTransform();
                    _loc_10.alphaMultiplier = this.alpha;
                    this.m_scratchBuffer1.draw(param2, _loc_8, _loc_10);
                }
                this.m_scratchBuffer1.draw(param2, _loc_8);
            }
            this.m_scratchBufferRect.width = param5.width + 2 * m_pixelOffset;
            this.m_scratchBufferRect.height = param5.height + 2 * m_pixelOffset;
            this.m_scratchBufferRect.x = 0;
            this.m_scratchBufferRect.y = 0;
            if (m_displayObject.filters.length > 0 && m_highlighted)
            {
                this.m_scratchBuffer2.applyFilter(this.m_scratchBuffer1, this.m_scratchBufferRect, new Point(), m_displayObject.filters.get(0));
            }
            else
            {
                this.m_scratchBuffer2.copyPixels(this.m_scratchBuffer1, this.m_scratchBufferRect, new Point(), null, null, true);
            }
            return;
        }//end

        protected void  renderCompositeItemImageToScratchBuffer (CompositeItemImage param1 ,Bitmap param2 ,double param3 ,double param4 ,Rectangle param5 ,Rectangle param6 )
        {
            ColorTransform _loc_9 =null ;
            Point _loc_7 =new Point (-param5.x ,-param5.y );
            this.m_scratchBuffer1.fillRect(this.m_scratchBuffer1.rect, 0);
            this.m_scratchBuffer2.fillRect(this.m_scratchBuffer2.rect, 0);
            Matrix _loc_8 =new Matrix ();
            _loc_8.tx = _loc_7.x;
            _loc_8.ty = _loc_7.y;
            _loc_8.a = param3;
            _loc_8.d = param4;
            _loc_8.tx = _loc_8.tx + m_pixelOffset;
            _loc_8.ty = _loc_8.ty + m_pixelOffset;
            param1.renderCiToBufferOld(this.m_scratchBuffer1, null, _loc_8, this.alpha);
            if (param2)
            {
                _loc_8.tx = _loc_7.x + param3 * param6.x;
                _loc_8.ty = _loc_7.y + param4 * param6.y;
                _loc_8.a = param3;
                _loc_8.d = param4;
                _loc_8.tx = _loc_8.tx + m_pixelOffset;
                _loc_8.ty = _loc_8.ty + m_pixelOffset;
                if (this.alpha < 1)
                {
                    _loc_9 = new ColorTransform();
                    _loc_9.alphaMultiplier = this.alpha;
                    this.m_scratchBuffer1.draw(param2, _loc_8, _loc_9);
                }
                else
                {
                    this.m_scratchBuffer1.draw(param2, _loc_8);
                }
            }
            this.m_scratchBufferRect.width = param5.width + 2 * m_pixelOffset;
            this.m_scratchBufferRect.height = param5.height + 2 * m_pixelOffset;
            this.m_scratchBufferRect.x = 0;
            this.m_scratchBufferRect.y = 0;
            if (m_displayObject.filters.length > 0 && m_highlighted)
            {
                this.m_scratchBuffer2.applyFilter(this.m_scratchBuffer1, this.m_scratchBufferRect, new Point(), m_displayObject.filters.get(0));
            }
            else
            {
                this.m_scratchBuffer2.copyPixels(this.m_scratchBuffer1, this.m_scratchBufferRect, new Point(), null, null, true);
            }
            return;
        }//end

        protected void  adjustSize (Vector3 param1 )
        {
            return;
        }//end

         public void  updateDisplayObjectTransform ()
        {
            double _loc_10 =0;
            double _loc_11 =0;
            double _loc_12 =0;
            super.updateDisplayObjectTransform();
            _loc_1 = getReference().getPositionNoClone();
            _loc_2 = getReference().getSize();
            this.adjustSize(_loc_2);

            if(this instanceof DesirePeep) {
                //Debug.debug7("MapResource.updateDisplayObjectTransform");
            }

            m_screenPosition = IsoMath.tilePosToPixelPos(_loc_1.x, _loc_1.y, 0, true);
            _loc_3 = IsoMath.getPixelDeltaFromTileDelta(_loc_2.x,0);
            _loc_4 = IsoMath.getPixelDeltaFromTileDelta(0,_loc_2.y);
            _loc_5 = Math.abs(_loc_3.x-_loc_4.x);
            double _loc_6 =-1;
            double _loc_7 =0;
            if (this.m_content)
            {
                _loc_10 = 0;
                if (this.m_imageClass && this.m_imageClass.forcedWidth > 0)
                {
                    _loc_10 = this.m_imageClass.forcedWidth;
                }
                else
                {
                    _loc_10 = this.m_content.width;
                }
                if (this.m_imageClass && this.m_imageClass.forcedHeight > 0)
                {
                    _loc_6 = this.m_imageClass.forcedHeight;
                }
                if (m_item.noAutoScale == false)
                {
                    _loc_11 = _loc_5 / _loc_10 * this.getImageScaleFactor();

                    m_displayObject.scaleY = _loc_11;
                    m_displayObject.scaleX = _loc_11;
                }
                else
                {
                    _loc_12 = Global.gameSettings().getNumber("maxZoom", 4);

                    m_displayObject.scaleY = 1 / _loc_12;
                    m_displayObject.scaleX = 1 / _loc_12;
                }
                _loc_7 = this.getDisplayXOffset();
            }
            if (_loc_6 <= 0)
            {
                if (this.m_imageClass && this.m_imageClass.lowResImage)
                {
                    _loc_6 = this.m_imageClass.lowResImage.height * m_displayObject.scaleY;
                }
                else
                {
                    _loc_6 = m_displayObject.height;
                }
            }
            else
            {
                _loc_6 = _loc_6 * m_displayObject.scaleY;
            }
            double _loc_8 =0;
            double _loc_9 =0;
            if (m_item != null)
            {
                _loc_8 = m_item.imageOffsetX;
                _loc_9 = m_item.imageOffsetY;
            }
            this.itemImageOffsetX = 0;
            this.itemImageOffsetY = 0;
            if (this.m_imageClass && this.m_imageClass.offsetX)
            {
                this.itemImageOffsetX = this.m_imageClass.offsetX;
            }
            if (this.m_imageClass && this.m_imageClass.offsetY)
            {
                this.itemImageOffsetY = this.m_imageClass.offsetY;
            }
            m_displayObject.x = m_screenPosition.x + _loc_4.x * this.getImageScaleFactor() + _loc_7 + _loc_8 + this.itemImageOffsetX * m_displayObject.scaleX;
            m_displayObject.y = m_screenPosition.y - _loc_6 + _loc_9 + this.itemImageOffsetY * m_displayObject.scaleY - _loc_1.z * Constants.TILE_HEIGHT;
            m_screenPosition.x = m_displayObject.x;
            m_screenPosition.y = m_displayObject.y;
            m_displayObject.visible = true;
            Vector3.free(_loc_2);
            return;
        }//end

        private void  onImageLoaded (Event event )
        {
            _loc_2 =(LoaderInfo) event.target;
            _loc_2.removeEventListener(Event.COMPLETE, this.onImageLoaded);
            this.conditionallyRedraw(true);
            return;
        }//end

        private void  onImageMaskLoaded (Event event )
        {
            DisplayObject _loc_2 =null ;
            if (this.m_maskBitmapLoader && this.m_maskBitmapLoader.content)
            {
                this.m_maskBitmapLoader.contentLoaderInfo.removeEventListener(Event.COMPLETE, this.onImageMaskLoaded);
                _loc_2 = this.m_maskBitmapLoader.content;
                if (_loc_2.width != 0 && _loc_2.height != 0)
                {
                    this.generateImageMask(_loc_2);
                }
            }
            return;
        }//end

        private void  generateImageMask (DisplayObject param1 )
        {
            if (param1 !=null)
            {
                if (this.m_maskBitmap != null)
                {
                    this.m_maskBitmap.bitmapData.dispose();
                }
            }
            return;
        }//end

        public void  setState (String param1 )
        {
            _loc_2 = this.m_state;
            this.m_state = param1;
            this.onStateChanged(_loc_2, param1);
            this.reloadImage();
            if (param1 != _loc_2)
            {
                dispatchEvent(new GameObjectEvent(GameObjectEvent.STATE_CHANGE));
            }
            return;
        }//end

        protected void  onStateChanged (String param1 ,String param2 )
        {
            this.updateArrow();
            return;
        }//end

        public void  onBuildingConstructionCompleted_PreServerUpdate ()
        {
            IMechanicUser _loc_1 =null ;
            Array _loc_2 =null ;
            if (this instanceof IMechanicUser)
            {
                _loc_1 =(IMechanicUser) this;
                _loc_2 = _loc_1.getPrioritizedMechanicsForGameEvent(BUILD_COMPLETE_EVENT);
                if (_loc_2 && _loc_2.length())
                {
                    MechanicManager.getInstance().handleAction(_loc_1, BUILD_COMPLETE_EVENT);
                }
            }
            return;
        }//end

        public void  onBuildingConstructionCompleted_PostServerUpdate ()
        {
            return;
        }//end

        public String  getState ()
        {
            return this.m_state;
        }//end

        protected void  updateArrow ()
        {
            return;
        }//end

        public void  refreshArrow ()
        {
            this.updateArrow();
            return;
        }//end

        protected void  updateDisabled ()
        {
            return;
        }//end

         public boolean  checkInternalCollision (int param1 ,int param2 ,int param3 )
        {
            return CollisionMap.compareCollisionFlags(this.getCollisionFlags(param1, param2), param3);
        }//end

         public int  getCollisionFlags (int param1 ,int param2 )
        {
            int _loc_5 =0;
            Array _loc_6 =null ;
            int _loc_3 =0;
            _loc_3 = m_collisionFlags;
            _loc_4 = getItem();
            if (getItem())
            {
                _loc_6 = _loc_4.getCollisionData();
                if (isRotated())
                {
                    _loc_5 = param1 * _loc_4.sizeX + param2;
                }
                else
                {
                    _loc_5 = param2 * _loc_4.sizeX + param1;
                }
                if (_loc_6.length > _loc_5)
                {
                    _loc_3 = _loc_6.get(_loc_5);
                }
            }
            return _loc_3;
        }//end

        private boolean  alreadyPurchased ()
        {
            return m_id > 0;
        }//end

         public void  onObjectDrag (Vector3 param1 )
        {
            super.onObjectDrag(param1);
            this.m_isDragged = true;
            return;
        }//end

         public void  onObjectDrop (Vector3 param1 )
        {
            super.onObjectDrop(param1);
            this.m_isDragged = false;
            this.m_active = true;
            this.calculateDepthIndex();
            this.conditionallyReattach(true);
            Global.world.onResourceChange(this, param1, getPosition());
            Global.world.citySim.roadManager.updateRoads(this);
            Global.world.citySim.poiManager.updatePOI(this);
            return;
        }//end

        public void  updateRoadState ()
        {
            MapResourceEffect _loc_1 =null ;
            if (!this.m_isRoadVerifiable)
            {
                return;
            }
            if (this.isNeedingRoad)
            {
                if (this.m_roadNotAdjacentEffects)
                {
                    for(int i0 = 0; i0 < this.m_roadNotAdjacentEffects.size(); i0++)
                    {
                    		_loc_1 = this.m_roadNotAdjacentEffects.get(i0);

                        _loc_1.reattach();
                    }
                }
                else
                {
                    this.m_roadNotAdjacentEffects = new Array();
                    this.m_roadNotAdjacentEffects.put(this.m_roadNotAdjacentEffects.length,  MapResourceEffectFactory.createEffect(this, EffectType.SEPIA));
                    this.m_roadNotAdjacentEffects.put(this.m_roadNotAdjacentEffects.length,  MapResourceEffectFactory.createEffect(this, EffectType.BAD_SIGN));
                }
            }
            else if (this.m_roadNotAdjacentEffects)
            {
                for(int i0 = 0; i0 < this.m_roadNotAdjacentEffects.size(); i0++)
                {
                		_loc_1 = this.m_roadNotAdjacentEffects.get(i0);

                    _loc_1.cleanUp();
                }
                this.m_roadNotAdjacentEffects = null;
                this.removeAnimatedEffects();
                this.repairSparkle();
            }
            return;
        }//end

        public boolean  isRoadVerifiable ()
        {
            return this.m_isRoadVerifiable;
        }//end

        public void  setIsRoadVerifiable ()
        {
            this.m_isRoadVerifiable = true;
            return;
        }//end

        public boolean  isNeedingRoad ()
        {
            boolean _loc_1 =false ;
            if (!this.m_pathProvider)
            {
                if (this.m_isRoadVerifiable && !Global.world.isOwnerCitySam)
                {
                    _loc_1 = !this.isAdjacentToAnyRoad;
                }
            }
            else
            {
                _loc_1 = this.m_pathProvider.isNeedingRoad;
            }
            return _loc_1;
        }//end

        public boolean  svrIsAdjacentToAnyRoad ()
        {
            return this.m_dbgServerConnectsToRoad;
        }//end

        public boolean  isBeingMoved ()
        {
            return this.m_isDragged;
        }//end

        public boolean  isBeingRotated ()
        {
            return this.m_isBeingRotated;
        }//end

        public void  setBeingRotated (boolean param1 )
        {
            this.m_isBeingRotated = param1;
            return;
        }//end

        public boolean  isBeingAutoHarvested ()
        {
            return this.m_isBeingAutoHarvested;
        }//end

        public void  setBeingAutoHarvested (boolean param1 )
        {
            this.m_isBeingAutoHarvested = param1;
            return;
        }//end

        protected boolean  isUxLockedByQuests ()
        {
            return false;
        }//end

         public void  onSell (GameObject param1)
        {
            m_sellDialogShowing = false;
            super.onSell(param1);
            Global.world.onResourceChange(this, this.getPosition(), null);
            Global.world.citySim.roadManager.updateRoads(this);
            Global.world.citySim.poiManager.removePOI(this);
            return;
        }//end

         protected void  sellConfirmationHandler (GenericPopupEvent event )
        {
            boolean _loc_3 =false ;
            m_sellDialogShowing = false;
            _loc_2 = event.button==GenericPopup.YES;
            if (_loc_2)
            {
                if (this.sellSendsToInventory)
                {
                    this.sendToInventory();
                }
                else
                {
                    if (m_item)
                    {
                        _loc_3 = m_item.className == "LotSite";
                        if (_loc_3 && UI.m_catalog != null)
                        {
                            UI.m_catalog.updateChangedCells();
                        }
                    }
                    super.sellConfirmationHandler(event);
                }
            }
            this.trackDetailedAction(TrackedActionType.SELL, "successful", "");
            return;
        }//end

         public boolean  isPixelInside (Point param1 )
        {
            BitmapData _loc_3 =null ;
            Point _loc_4 =null ;
            _loc_2 = super.isPixelInside(param1);
            if (_loc_2)
            {
                if (this.m_maskBitmap)
                {
                    _loc_3 = this.m_maskBitmap.bitmapData;
                    _loc_4 = param1.subtract(new Point(m_displayObject.x, m_displayObject.y));
                    param1.subtract(new Point(m_displayObject.x, m_displayObject.y)).x = _loc_4.x / m_displayObject.scaleX;
                    _loc_4.y = _loc_4.y / m_displayObject.scaleY;
                    _loc_4.x = _loc_4.x - this.m_maskBitmap.x;
                    _loc_4.y = _loc_4.y - this.m_maskBitmap.y;
                    _loc_2 = _loc_3.hitTest(new Point(0, 0), this.getItem().isPixelInsideAlphaThresholdOveride, _loc_4);
                }
            }
            return _loc_2;
        }//end

        protected ItemImageInstance  getCurrentImageForMask ()
        {
            return this.getCurrentImage();
        }//end

        protected Object  getMaskBitmap ()
        {
            Object _loc_1 =null ;
            ItemImageInstance _loc_2 =null ;
            DisplayObject _loc_3 =null ;
            CompositeItemImage _loc_4 =null ;
            if (this.isUsingPerPixelPick())
            {
                _loc_2 =(ItemImageInstance) this.getCurrentImageForMask();
                if (_loc_2)
                {
                    _loc_3 =(DisplayObject) _loc_2.image;
                    if (_loc_3 instanceof CompositeItemImage)
                    {
                        _loc_4 =(CompositeItemImage) _loc_3;
                        _loc_3 = _loc_4.getBuildingImageByType(ItemImageInstance.BUILDING_IMAGE);
                    }
                    if (_loc_3 instanceof Bitmap)
                    {
                        _loc_1 = _loc_3;
                    }
                    else if (_loc_3 instanceof Loader)
                    {
                        _loc_1 = _loc_3;
                    }
                    else if (_loc_3 instanceof MovieClip)
                    {
                        _loc_1 = _loc_3;
                    }
                }
            }
            else
            {
                _loc_1 = null;
            }
            return _loc_1;
        }//end

         public void  sell ()
        {
            String _loc_1 =null ;
            boolean _loc_2 =false ;
            String _loc_3 =null ;
            if (m_sellDialogShowing)
            {
                return;
            }
            m_sellDialogShowing = true;
            if (m_item)
            {
                _loc_1 = this.sellSendsToInventory ? (m_item.sellSendsToInventoryMessage) : ("SellObjectSpecific");
                if (getSellPrice() <= 1 && m_item.cash <= 0 && !this.sellSendsToInventory)
                {
                    _loc_2 = m_item.className == "LotSite";
                    if (_loc_2 && UI.m_catalog)
                    {
                        UI.m_catalog.updateChangedCells();
                    }
                    sellNow();
                }
                else
                {
                    _loc_3 = ZLoc.t("Main", _loc_1, {item:getItemFriendlyName(), coins:getSellPrice()});
                    if (Global.crews.getCrewByObject(this) != null && Global.crews.getCrewByObject(this).count > 0)
                    {
                        _loc_3 = _loc_3 + (" " + ZLoc.t("Main", "SendToInventoryCrewWarning"));
                    }
                    UI.displayMessage(_loc_3, GenericPopup.TYPE_YESNO, this.sellConfirmationHandler, "sellDialog", true, null, "", 0, "delete_" + m_item.name);
                }
            }
            else
            {
                super.sell();
            }
            return;
        }//end

        public boolean  sellSendsToInventory ()
        {
            return m_item.sellSendsToInventory;
        }//end

        public void  sendToInventory ()
        {
            _loc_1 = Global.player.inventory.addItems(m_item.name,1);
            if (_loc_1)
            {
                GameTransactionManager.addTransaction(new TSendToInventory(this));
                this.detach();
                cleanup();
                this.onSell();
            }
            return;
        }//end

        public void  store ()
        {
            BaseStorageUnit _loc_2 =null ;
            Array _loc_3 =null ;
            ItemStorage _loc_4 =null ;
            _loc_1 = Global.player.storageComponent.getValidStorageUnits(this);
            if (_loc_1.length < 1)
            {
            }
            else if (_loc_1.length == 1)
            {
                _loc_2 = Global.player.storageComponent.getStorageUnit(_loc_1.get(0).storageType, _loc_1.get(0).key);
                ;
            }
            if (_loc_2)
            {
                if (_loc_2.size < _loc_2.capacity)
                {
                    _loc_2.add(this);
                    GameTransactionManager.addTransaction(new TStoreItem(this));
                    this.detach();
                    this.prepareForStorage();
                    this.onSell();
                    _loc_3 = Global.world.getObjectsByClass(ItemStorage);
                    for(int i0 = 0; i0 < _loc_3.size(); i0++)
                    {
                    		_loc_4 = _loc_3.get(i0);

                        _loc_4.refreshArrow();
                    }
                    this.trackAction(TrackedActionType.STORE);
                }
                else
                {
                    UI.displayMessage(ZLoc.t("Dialogs", "warehouseFull"), GenericPopup.TYPE_OK, null, "storageFullDialog", true);
                    this.trackAction("warehouse_full");
                }
                ;
            }
            return;
        }//end

         public void  onUpdate (double param1 )
        {
            MapResourceEffect _loc_2 =null ;
            IDynamicDisplayObject _loc_3 =null ;
            AnimationEffect _loc_4 =null ;
            super.onUpdate(param1);
            if (this.shouldObjectIndicatorBeUpdated())
            {
                updateObjectIndicator();
            }
            if (this.isVisible())
            {

            if (this instanceof DesirePeep)
            {

            	if(this.m_content != null) {
            		//Debug.debug7("MapResource.onUpdate." + this.m_screenPosition.x+";"+ this.m_screenPosition.y);
            	}
             }
                if (this.m_content instanceof IDynamicDisplayObject)
                {
                    _loc_3 =(IDynamicDisplayObject) this.m_content;
                    _loc_3.onUpdate(param1);
                }
                for(int i0 = 0; i0 < this.m_animatedEffects.size(); i0++)
                {
                	_loc_2 = this.m_animatedEffects.get(i0);

                    if (_loc_2 instanceof AnimationEffect)
                    {
                        _loc_4 =(AnimationEffect) _loc_2;
                        if (!_loc_4.isAutoUpdating)
                        {
                            _loc_4.updateAnimatedBitmap(param1);
                        }
                    }
                }
            }

            if (this instanceof DummyNPC)
            {
            	Debug.debug4("MapResource.onUpdate." + this);
            }

            this.m_actionQueue.update(param1);
            return;
        }//end

        protected boolean  isWrongPlantContract ()
        {
            return true;
        }//end

         public void  onMouseOver (MouseEvent event )
        {
            boolean _loc_2 =false ;
            if (!isLocked() && !(Global.world.getTopGameMode() instanceof GMObjectMove))
            {
                if (Global.world.getTopGameMode() instanceof GMPlant)
                {
                    if (this.isWrongPlantContract())
                    {
                        return;
                    }
                }
                _loc_2 = Global.world.getTopGameMode() && Global.world.getTopGameMode().allowHighlight(this);
                setShowHighlight(!m_item.useTileHighlight && _loc_2);
                this.setHighlighted(true);
            }
            return;
        }//end

         public void  onMouseOut ()
        {
            this.setHighlighted(false);
            return;
        }//end

         public void  onSelect ()
        {
            super.onSelect();
            this.m_isSelected = true;
            return;
        }//end

         public void  onDeselect ()
        {
            super.onDeselect();
            this.m_isSelected = false;
            return;
        }//end

         public void  setHighlighted (boolean param1 ,int param2 =1.67552e +007)
        {
            _loc_3 = this.isHighlightable? (param1) : (false);
            m_highlighted = _loc_3;
            if (this.m_content)
            {
                if (_loc_3 && canShowHighlight)
                {
                    this.m_content.filters = .get(new GlowFilter(getHighlightColor(), 1, 8, 8, 20));
                }
                else
                {
                    this.m_content.filters = m_filters.concat([]);
                }
                if (this.m_visitorHighlight)
                {
                    this.m_content.filters = this.m_content.filters.concat(new GlowFilter(EmbeddedArt.VISITOR_INTERACTED_HIGHLIGHT_COLOR, 1, 20, 20, 2));
                }
                renderBufferDirty = true;
            }
            return;
        }//end

        public void  setHighlightedSpecial (boolean param1 ,int param2 =1.67552e +007,int param3 =8)
        {
            if (this.m_content)
            {
                if (param1 !=null)
                {
                    this.m_content.filters = .get(new GlowFilter(param2, 1, param3, param3, 20));
                }
                else
                {
                    this.m_content.filters = m_filters.concat([]);
                }
                renderBufferDirty = true;
            }
            return;
        }//end

        public void  setVisitorInteractedHighlighted (boolean param1 =true )
        {
            this.m_visitorHighlight = param1;
            this.setHighlighted(m_highlighted);
            return;
        }//end

        public boolean  isPaintable ()
        {
            _loc_1 = (ColorMaskedBitmap)this.getItemImage()
            return _loc_1 != null;
        }//end

        public void  onRotateMenuAction (MouseEvent event )
        {
            _loc_2 = m_direction;
            this.rotate();
            Global.world.addGameMode(new GMObjectMove(this, this.m_state, null, _loc_2), false);
            return;
        }//end

        public void  rotate ()
        {
            _loc_2 = m_direction+1;
            m_direction = _loc_2;
            if (m_direction >= Constants.DIRECTION_MAX)
            {
                m_direction = Constants.DIRECTION_SW;
            }
            updateSize();
            this.m_itemImage = null;
            this.m_maskBitmap = null;
            setDisplayObjectDirty(true);
            this.conditionallyRedraw();
            return;
        }//end

        public void  rotateToDirection (int param1 )
        {
            while (param1 != m_direction)
            {

                this.rotate();
            }
            return;
        }//end

        public void  onSellMenuAction (MouseEvent event )
        {
            this.sell();
            return;
        }//end

        public void  onPaintMenuAction (MouseEvent event )
        {
            PaintSelector _loc_2 =new PaintSelector ();
            UI.displayPopup(_loc_2);
            return;
        }//end

        public DisplayObject  getItemImage ()
        {
            return this.m_itemImage;
        }//end

        public void  onEditModeSwitch ()
        {
            return;
        }//end

         public void  onMenuClick (MouseEvent event )
        {
            ContextMenuItem _loc_2 =null ;
            if (event.target instanceof ContextMenuItem)
            {
                _loc_2 =(ContextMenuItem) event.target;
                switch(_loc_2.action)
                {
                    case MOVE_OBJECT:
                    {
                        this.move();
                        break;
                    }
                    case SELL_OBJECT:
                    {
                        this.sell();
                        break;
                    }
                    default:
                    {
                        super.onMenuClick(event);
                        break;
                        break;
                    }
                }
            }
            return;
        }//end

        protected boolean  shouldToolTipBeUpdated ()
        {
            return isHighlighted() || this.isSelected() || this.isBeingMoved();
        }//end

        protected boolean  shouldObjectIndicatorBeUpdated ()
        {
            return (isHighlighted() || this.isSelected()) && !this.m_isDragged;
        }//end

         public String  getToolTipHeader ()
        {
            String _loc_1 =null ;
            if (m_item && !(Global.world.getTopGameMode() instanceof GMRemodel))
            {
                _loc_1 = getItemFriendlyName();
            }
            return _loc_1;
        }//end

         public String  getToolTipNotes ()
        {
            String _loc_1 =null ;
            if (!(Global.world.getTopGameMode() instanceof GMRemodel))
            {
                if (this.m_isPendingOrder)
                {
                    _loc_1 = ZLoc.t("Main", "OwnershipPending");
                }
                else if (m_item && m_item.tooltipNotes != "")
                {
                    _loc_1 = m_item.tooltipNotes;
                }
            }
            return _loc_1;
        }//end

         public String  getToolTipAction ()
        {
            return this.getGameModeToolTipAction();
        }//end

        protected JLabel  prepTooltipLabel (JLabel param1 )
        {
            _loc_2 = ToolTipSchema.getSchemaForObject(this);
            ASFont _loc_3 =new ASFont(_loc_2.bodyFontName ,_loc_2.bodyFontSize ,false ,false ,false ,new ASFontAdvProperties(true ,AntiAliasType.ADVANCED ,GridFitType.PIXEL ));
            param1.setForeground(new ASColor(_loc_2.bodyFontColor));
            param1.setFont(_loc_3);
            param1.setTextFilters(_loc_2.bodyTextFilters);
            return param1;
        }//end

        protected Component  buildToolTipComponent (String param1 ,String param2 )
        {
           Debug.debug5("MapResource.buildToolTipComponent."+param1+";"+param2);

	   _loc_3 = this.m_toolTipComponents.get(param1+"_container");
            _loc_4 = this.m_toolTipComponents.get(param1+"_label");
            if (param2)
            {
                _loc_4 = _loc_4 || this.prepTooltipLabel(new JLabel(""));
                _loc_3 = _loc_3 || ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.LEFT);
                _loc_3.removeAll();
                _loc_4.setText(param2);
                _loc_3.appendAll(_loc_4);
            }
            else
            {
                if (_loc_3)
                {
                    _loc_3.removeAll();
                }
                _loc_3 = null;
                _loc_4 = null;
            }
            this.m_toolTipComponents.put(param1 + "_container",  _loc_3);
            this.m_toolTipComponents.put(param1 + "_label",  _loc_4);
            return _loc_3;
        }//end

        protected double  getToolTipTextWidth (String param1 )
        {
            _loc_2 = ToolTipSchema.getSchemaForObject(this);
            if (!this.m_stringWidthTester)
            {
                this.m_stringWidthTester = ASwingHelper.makeTextField("", _loc_2.bodyFontName, _loc_2.bodyFontSize, _loc_2.bodyFontColor);
            }
            this.m_stringWidthTester.setTextFormat(new TextFormat(_loc_2.bodyFontName, _loc_2.bodyFontSize, _loc_2.bodyFontColor));
            this.m_stringWidthTester.setText(param1);
            return this.m_stringWidthTester.getTextField().textWidth;
        }//end

         public int  getToolTipFloatOffset ()
        {
            int _loc_1 =0;
            if (this.m_stagePickEffect)
            {
                _loc_1 = this.m_stagePickEffect.getAnimationHeight();
            }
            return super.getToolTipFloatOffset() + _loc_1;
        }//end

        public Array  getToolTipComponentList ()
        {
            return .get(this.getCustomToolTipAction(), this.getCustomToolTipStatus(), this.getCustomToolTipLevelBar());
        }//end

        public String  getCustomToolTipTitle ()
        {
            return this.getToolTipHeader();
        }//end

        public Component  getCustomToolTipImage ()
        {
            return null;
        }//end

        public Component  getCustomToolTipStatus ()
        {
            return ToolTipDialog.buildToolTipComponent(ToolTipDialog.STATUS, this.m_toolTipComponents, getToolTipStatus(), ToolTipSchema.getSchemaForObject(this));
        }//end

        public Component  getCustomToolTipAction ()
        {
            return ToolTipDialog.buildToolTipComponent(ToolTipDialog.ACTION, this.m_toolTipComponents, this.getToolTipAction() ? (TextFieldUtil.formatSmallCapsString(this.getToolTipAction())) : (null), ToolTipSchema.getSchemaForObject(this));
        }//end

        public Component  getCustomToolTipLevelBar ()
        {
            double _loc_1 =0;
            double _loc_2 =0;
            if (this.canCountUpgradeActions())
            {
                _loc_1 = Number(this.getItem().upgrade.requirements.getRequirementValue(Requirements.REQUIREMENT_UPGRADE_ACTIONS));
                _loc_2 = Math.min(this.upgradeActions.getTotal(), _loc_1);
                if (!this.m_levelProgressComponent)
                {
                    this.m_levelProgressComponent = new SimpleProgressBar(EmbeddedArt.darkBlueToolTipColor, EmbeddedArt.UPGRADE_HIGHLIGHT_COLOR, 100, 10, 2, -5);
                }
                this.m_levelProgressComponent.setBgAlpha(1);
                this.m_levelProgressComponent.setBgColor(2450817);
                this.m_levelProgressComponent.setProgressRatio(_loc_2, _loc_1);
                this.m_levelProgressComponent.setTitle(ZLoc.t("Main", "LevelProgress"));
            }
            else if (this.canShowAlternativeUpgradeToolTip())
            {
                if (!this.m_alternativeLevelLabel)
                {
                    this.m_alternativeLevelLabel = new JLabel("", null, JLabel.LEFT);
                    this.m_alternativeLevelLabel.setFont(new ASFont(EmbeddedArt.defaultFontNameBold, 14, false, false, false, new ASFontAdvProperties(EmbeddedArt.defaultBoldFontEmbed, AntiAliasType.ADVANCED, GridFitType.PIXEL)));
                    this.m_alternativeLevelLabel.setForeground(new ASColor(EmbeddedArt.darkBlueToolTipColor));
                }
                this.m_alternativeLevelLabel.setText(ZLoc.t("Dialogs", "level_text", {amount:this.getItem().level}));
                this.m_levelProgressComponent = null;
                return this.m_alternativeLevelLabel;
            }
            else
            {
                this.m_levelProgressComponent = null;
            }
            return this.m_levelProgressComponent;
        }//end

        public String  getGameModeToolTipAction ()
        {
            Object _loc_3 =null ;
            String _loc_1 =null ;
            _loc_2 = Global.world.getTopGameMode();
            if (!(Global.world.getTopGameMode() instanceof GMRemodel))
            {
            }
            else if (_loc_2 instanceof GMEditMove)
            {
                if (!this.isMoveLocked)
                {
                    _loc_1 = ZLoc.t("Main", "Move_ToolTip");
                }
                else
                {
                    _loc_1 = this.GetNoMoveMessage();
                }
            }
            else if (_loc_2 instanceof GMEditRotate)
            {
                _loc_1 = ZLoc.t("Main", "Rotate_ToolTip");
            }
            else if (_loc_2 instanceof GMEditSocialInventory)
            {
                if (this instanceof IMechanicUser)
                {
                    _loc_3 = ((IMechanicUser)this).getDataForMechanic(SocialInventoryManager.MECHANIC_DATA);
                    if (_loc_3 && "heart" in _loc_3)
                    {
                        _loc_1 = ZLoc.t("Main", "RemoveHeart");
                    }
                    else if (((IMechanicUser)this).getMechanicConfig(SocialInventoryManager.MECHANIC_DATA, "GMEditSocialInventory") != null)
                    {
                        _loc_1 = ZLoc.t("Main", "PlaceHeart");
                    }
                    else
                    {
                        _loc_1 = "";
                    }
                }
            }
            else if (_loc_2 instanceof GMObjectMove)
            {
                _loc_1 = ZLoc.t("Main", "Place_ToolTip");
            }
            else if (_loc_2 instanceof GMEditSell)
            {
                _loc_1 = ZLoc.t("Main", "Remove_ToolTip");
            }
            return _loc_1;
        }//end

        protected void  onSfxChanged (FarmGameWorldEvent event )
        {
            SoundTransform _loc_2 =null ;
            if (this.m_content instanceof MovieClip)
            {
                _loc_2 = new SoundTransform();
                if (Global.player.options.sfxDisabled)
                {
                    _loc_2.volume = 0;
                }
                else
                {
                    _loc_2.volume = 1;
                }
                MovieClip(this.m_content).soundTransform = _loc_2;
            }
            return;
        }//end

         public void  attach ()
        {
            super.attach();
            Global.world.addObjectToCaches(this);
            Global.ui.addEventListener(FarmGameWorldEvent.SFX_CHANGED, this.onSfxChanged);
            return;
        }//end

         public void  detach ()
        {
            super.detach();
            Global.world.delObjectFromCaches(this);
            Global.ui.removeEventListener(FarmGameWorldEvent.SFX_CHANGED, this.onSfxChanged);
            return;
        }//end

         protected void  calculateDepthIndex ()
        {
            super.calculateDepthIndex();
            if (this.isBeingMoved())
            {
                m_depthIndex = m_depthIndex - 10000;
            }
            return;
        }//end

         public void  conditionallyRedraw (boolean param1 =false )
        {

            if (!GameWorld.enableUpdateShardingOrCulling)
            {
                super.conditionallyRedraw(param1);
                return;
            }
            if (m_outer != null && m_outer.isRedrawLocked() == false && (m_displayObjectDirty || param1))
            {
                this.drawDisplayObject();
                updateCulling();
                m_displayObjectDirty = false;
            }
            return;
        }//end

         public void  toggleAnimation ()
        {
            Sprite _loc_1 =null ;
            int _loc_2 =0;
            DisplayObject _loc_3 =null ;
            if (this.m_displayObject && this.m_displayObject instanceof Sprite)
            {
                _loc_1 =(Sprite) m_displayObject;
                _loc_2 = 0;
                while (_loc_2 < _loc_1.numChildren)
                {

                    _loc_3 = _loc_1.getChildAt(_loc_2);
                    this.toggleDispObjAnimation(_loc_3);
                    _loc_2++;
                }
                this.toggleDispObjAnimation(this.m_itemImage);
            }
            return;
        }//end

         public Vector3  getDimensions ()
        {
            if (this.m_itemImage)
            {
                return new Vector3(this.m_itemImage.width, this.m_itemImage.height);
            }
            return null;
        }//end

        public Vector3  getItemImageDimensions ()
        {
            if (m_item)
            {
                return m_item.getCachedImageDimensions(this.m_state, m_direction);
            }
            return null;
        }//end

         public void  preDisableMove (String param1 ,int param2 )
        {
            this.setState(param1);
            setDirection(param2);
            return;
        }//end

        private void  toggleDispObjAnimation (DisplayObject param1 )
        {
            Sprite _loc_2 =null ;
            int _loc_3 =0;
            DisplayObject _loc_4 =null ;
            if (param1 instanceof MovieClip)
            {
                if (Global.playAnimations)
                {
                    ((MovieClip)param1).play();
                }
                else
                {
                    ((MovieClip)param1).stop();
                }
            }
            if ((Sprite)param1 && ((Sprite)param1).numChildren > 0)
            {
                _loc_2 =(Sprite) param1;
                _loc_3 = 0;
                while (_loc_3 < _loc_2.numChildren)
                {

                    _loc_4 = _loc_2.getChildAt(_loc_3);
                    if (_loc_4 instanceof MovieClip)
                    {
                        if (Global.playAnimations)
                        {
                            ((MovieClip)_loc_4).play();
                        }
                        else
                        {
                            ((MovieClip)_loc_4).stop();
                        }
                    }
                    _loc_3++;
                }
            }
            return;
        }//end

        public void  onStorage ()
        {
            return;
        }//end

        public void  addEffect (String param1 )
        {
            Debug.debug5("MapResource.addEffect");
            if (m_effectLoader == null)
            {
                m_effectLoader = new ImageLoader(Global.getAssetURL(param1));
                m_effectLoader.getContent(this.onEffectLoad);
            }
            return;
        }//end

        public void  stopEffect ()
        {
            if (this.m_effectOverlay != null)
            {
                if (((Sprite)this.m_displayObject).contains(this.m_effectOverlay))
                {
                    ((Sprite)this.m_displayObject).removeChild(this.m_effectOverlay);
                }
                ((MovieClip)this.m_effectOverlay).stop();
                this.m_effectOverlay = null;
            }
            return;
        }//end

        public boolean  hasAnimatedEffects ()
        {
            return this.m_animatedEffects.length > 0;
        }//end

        public MapResourceEffect  addAnimatedEffect (EffectType param1 )
        {
            _loc_2 = MapResourceEffectFactory.createEffect(this,param1);
            return this.addAnimatedEffectFromInstance(_loc_2);
        }//end

        public MapResourceEffect  addAnimatedEffectFromInstance (MapResourceEffect param1 )
        {
            this.m_animatedEffects.put(this.m_animatedEffects.length,  param1);
            return param1;
        }//end

        public void  removeAnimatedEffects ()
        {
            MapResourceEffect _loc_1 =null ;
            for(int i0 = 0; i0 < this.m_animatedEffects.size(); i0++)
            {
            	_loc_1 = this.m_animatedEffects.get(i0);

                _loc_1.cleanUp();
                _loc_1 = null;
            }
            this.m_animatedEffects = new Array();
            return;
        }//end

        public void  removeAnimatedEffect (EffectType param1 )
        {
            MapResourceEffect _loc_4 =null ;
            Array _loc_2 =new Array();
            int _loc_3 =0;
            while (_loc_3 < this.m_animatedEffects.length())
            {

                _loc_4 = this.m_animatedEffects.get(_loc_3);
                if (_loc_4.effectType == param1)
                {
                    _loc_4.cleanUp();
                }
                else
                {
                    _loc_2.push(_loc_4);
                }
                _loc_3++;
            }
            this.m_animatedEffects = _loc_2;
            return;
        }//end

        public boolean  hasAnimatedEffect (EffectType param1 )
        {
            int _loc_2 =0;
            while (_loc_2 < this.m_animatedEffects.length())
            {

                if (this.m_animatedEffects.get(_loc_2).effectType == param1)
                {
                    return true;
                }
                _loc_2++;
            }
            return false;
        }//end

        public void  removeAnimatedEffectByClass (Class param1 )
        {
            MapResourceEffect _loc_4 =null ;
            Array _loc_2 =new Array();
            int _loc_3 =0;
            while (_loc_3 < this.m_animatedEffects.length())
            {

                _loc_4 = this.m_animatedEffects.get(_loc_3);
                if (_loc_4 instanceof param1)
                {
                    _loc_4.cleanUp();
                }
                else
                {
                    _loc_2.push(_loc_4);
                }
                _loc_3++;
            }
            this.m_animatedEffects = _loc_2;
            return;
        }//end

        public MapResourceEffect  getAnimatedEffectByClass (Class param1 )
        {
            MapResourceEffect _loc_3 =null ;
            int _loc_2 =0;
            while (_loc_2 < this.m_animatedEffects.length())
            {

                _loc_3 = this.m_animatedEffects.get(_loc_2);
                if (_loc_3 instanceof param1)
                {
                    return _loc_3;
                }
                _loc_2++;
            }
            return null;
        }//end

         public void  cleanUp ()
        {
            MapResourceEffect _loc_1 =null ;
            this.removeAnimatedEffects();
            this.repairSparkle();
            if (this.m_roadNotAdjacentEffects)
            {
                for(int i0 = 0; i0 < this.m_roadNotAdjacentEffects.size(); i0++)
                {
                		_loc_1 = this.m_roadNotAdjacentEffects.get(i0);

                    _loc_1.cleanUp();
                }
                this.m_roadNotAdjacentEffects = null;
            }
            Global.world.citySim.poiManager.removePOI(this);
            if (this.m_actionQueue)
            {
                this.m_actionQueue.removeAllStates();
            }
            super.cleanUp();
            return;
        }//end

        private void  onEffectLoad (DisplayObject param1 )
        {
                        Debug.debug5("MapResource.onEffectLoad");

            this.stopEffect();
            if (param1 != null)
            {
                this.m_effectOverlay = param1;
                ((MovieClip)this.m_effectOverlay).play();
                if (((Sprite)this.m_displayObject).numChildren > 0)
                {
                    ((Sprite)this.m_displayObject).addChild(this.m_effectOverlay);
                    this.m_effectOverlay.x = 0;
                    this.m_effectOverlay.y = 0;
                    this.m_effectOverlay.scaleX = 1;
                    this.m_effectOverlay.scaleY = 1;
                }
            }
            m_effectLoader = null;
            return;
        }//end

        public void  onPurchase ()
        {
            return;
        }//end

        public void  deleteArrow ()
        {
            m_arrow = null;
            this.reloadImage();
            return;
        }//end

        public void  clearFromMap ()
        {
            if (!Global.isVisiting())
            {
                this.m_doobersArray = Global.player.processRandomModifiers(m_item, this, false, this.m_secureRands);
                Global.world.dooberManager.createBatchDoobers(this.m_doobersArray, m_item, m_position.x, m_position.y);
            }
            if (this.m_doobersArray && this.m_doobersArray.length())
            {
                this.m_actionQueue.removeAllStates();
                this .m_actionQueue .addActions (new ActionPause (this ,1),new ActionFn (this ,void  ()
            {
                Global.hud.conditionallyRefreshHUD();
                detach();
                cleanUp();
                return;
            }//end
            ));
            }
            else
            {
                Global.hud.conditionallyRefreshHUD();
                this.detach();
                this.cleanUp();
            }
            if (m_actionMode != VISIT_REPLAY_ACTION)
            {
                if (!Global.isVisiting())
                {
                    GameTransactionManager.addTransaction(new TClearMapResource(this));
                }
            }
            return;
        }//end

        private double  stringCompare (String param1 ,String param2 )
        {
            if (param1 < param2)
            {
                return -1;
            }
            if (param1 > param2)
            {
                return 1;
            }
            return 0;
        }//end

        private String  checkDooberResults (Object param1 )
        {
            Array clientDoober ;
            Array serverDoober ;
            int amount ;
            int dooberIndex ;
            result = param1;
            Array clientDoobers =new Array ();
            int _loc_3 =0;
            Object _loc_4;

            if (this.m_doobersArray != null)
            {
                _loc_3 = 0;
                _loc_4 = this.m_doobersArray;
                for(int i0 = 0; i0 < this.m_doobersArray.size(); i0++)
                {
                	clientDoober = this.m_doobersArray.get(i0);


                    if (!isNaN(clientDoober.get(1)))
                    {
                        clientDoobers.push(new Array(clientDoober.get(0), int(Math.ceil(clientDoober.get(1)))));
                    }
                }
                clientDoobers =clientDoobers .sort (double  (Array param11 ,Array param2 )
            {
                return stringCompare(param11.get(0), param2.get(0));
            }//end
            );
            }
            Array serverDoobers =new Array ();
            if (result.doobers != null)
            {


                for(int i0 = 0; i0 < result.doobers.size(); i0++)
                {
                		serverDoober = result.doobers.get(i0);


                    if (!isNaN(serverDoober.get(1)))
                    {
                        amount = Math.ceil(serverDoober.get(1));
                        serverDoobers.push(new Array(Global.gameSettings().getDooberFromType(serverDoober.get(0), amount), amount));
                    }
                }
                serverDoobers =serverDoobers .sort (double  (Array param11 ,Array param2 )
            {
                return stringCompare(param11.get(0), param2.get(0));
            }//end
            );
            }
            String error ;
            if (serverDoobers.length > 0)
            {
                if (clientDoobers.length == serverDoobers.length())
                {
                    dooberIndex;
                    while (dooberIndex < serverDoobers.length())
                    {

                        clientDoober = clientDoobers.get(dooberIndex);
                        serverDoober = serverDoobers.get(dooberIndex);
                        if (this.stringCompare(clientDoober.get(0), serverDoober.get(0)) !== 0)
                        {
                            error;
                            break;
                        }
                        else if (clientDoober.get(1) !== serverDoober.get(1))
                        {
                            error;
                            break;
                        }
                        dooberIndex = (dooberIndex + 1);
                    }
                }
                else
                {
                    error;
                }
            }
            else if (clientDoobers.length > 0)
            {
                error;
            }
            return error;
        }//end

        private String  checkRandResults (Object param1 )
        {
            int _loc_4 =0;
            String _loc_2 =null ;
            _loc_3 = param1.secureRands;
            if (_loc_3 != null && _loc_3.length > 0)
            {
                if (this.m_secureRands != null && this.m_secureRands.length >= _loc_3.length())
                {
                    _loc_4 = 0;
                    while (_loc_4 < _loc_3.length())
                    {

                        if (_loc_3.get(_loc_4) != this.m_secureRands.get(_loc_4))
                        {
                            _loc_2 = "Secure Rand didn\'t match";
                            break;
                        }
                        _loc_4++;
                    }
                    this.m_secureRands.splice(0, _loc_3.length());
                }
                else
                {
                    this.m_secureRands.splice(0, this.m_secureRands.length());
                    _loc_2 = "Client has fewer secure rand results than server.";
                }
            }
            else if (this.m_secureRands != null && this.m_secureRands.length > 0)
            {
                _loc_2 = "Server did not return any secure rand values.";
                this.m_secureRands.splice(0, this.m_secureRands.length());
            }
            if (Config.DEBUG_MODE && _loc_2 != null)
            {
                UI.displayMessage(_loc_2 + " From " + " checkRandResults");
            }
            return _loc_2;
        }//end

        public void  parseAndCheckDooberResults (Object param1 )
        {
            String _loc_3 =null ;
            if (!Global.world.dooberManager.isDoobersEnabled())
            {
                return;
            }
            _loc_2 = this.checkRandResults(param1);
            if (_loc_2 != null)
            {
                _loc_2 = _loc_2 + (" - " + SecureRand.getAndClearDebugBuffer());
                StatsManager.count(StatsCounterType.ERRORS, StatsKingdomType.SECURERAND, Config.DEBUG_MODE ? (StatsPhylumType.DEBUG_ERROR) : (StatsPhylumType.MISCELLANEOUS), "any", this.getItemName());
                Global.world.dooberManager.autoCollect();
                Global.player.forceServerStats();
            }
            if (RuntimeVariableManager.getBoolean("ENABLE_DOOBER_TRACKING", false))
            {
                _loc_3 = this.checkDooberResults(param1);
                if (_loc_3 != null)
                {
                    StatsManager.count(StatsCounterType.ERRORS, StatsKingdomType.DOOBER_MISMATCH, Config.DEBUG_MODE ? (StatsPhylumType.DEBUG_ERROR) : (StatsPhylumType.MISCELLANEOUS), "any", this.getItemName(), _loc_3);
                }
            }
            return;
        }//end

        public boolean  canClearFromMap ()
        {
            if (this.isOwnable() || !isAttached())
            {
                return false;
            }
            if (!Global.player.checkEnergy(-m_item.clearEnergyCost))
            {
                displayStatus(ZLoc.t("Main", "NotEnoughEnergyRed"));
                return false;
            }
            return true;
        }//end

        public int  getRoadSidewalkSide ()
        {
            _loc_1 = getPosition();
            _loc_2 = this.getHotspot().subtract(_loc_1);
            _loc_2.x = _loc_2.x - m_size.x * 0.5;
            _loc_2.y = _loc_2.y - m_size.y * 0.5;
            _loc_2 = _loc_2.normalize();
            _loc_3 = this.BOTTOM_SIDE;
            if (Math.abs(_loc_2.x) > Math.abs(_loc_2.y))
            {
                if (_loc_2.x < 0)
                {
                    if (this.m_adjRoadFlags & this.LEFT_SIDE)
                    {
                        _loc_3 = this.LEFT_SIDE;
                    }
                    else if (this.m_adjRoadFlags & this.BOTTOM_SIDE)
                    {
                        _loc_3 = this.BOTTOM_SIDE;
                    }
                    else if (this.m_adjRoadFlags & this.TOP_SIDE)
                    {
                        _loc_3 = this.TOP_SIDE;
                    }
                    else if (this.m_adjRoadFlags & this.RIGHT_SIDE)
                    {
                        _loc_3 = this.RIGHT_SIDE;
                    }
                }
                else if (this.m_adjRoadFlags & this.BOTTOM_SIDE)
                {
                    _loc_3 = this.BOTTOM_SIDE;
                }
                else if (this.m_adjRoadFlags & this.LEFT_SIDE)
                {
                    _loc_3 = this.LEFT_SIDE;
                }
                else if (this.m_adjRoadFlags & this.RIGHT_SIDE)
                {
                    _loc_3 = this.RIGHT_SIDE;
                }
                else if (this.m_adjRoadFlags & this.TOP_SIDE)
                {
                    _loc_3 = this.TOP_SIDE;
                }
            }
            else if (_loc_2.y < 0)
            {
                if (this.m_adjRoadFlags & this.BOTTOM_SIDE)
                {
                    _loc_3 = this.BOTTOM_SIDE;
                }
                else if (this.m_adjRoadFlags & this.LEFT_SIDE)
                {
                    _loc_3 = this.LEFT_SIDE;
                }
                else if (this.m_adjRoadFlags & this.RIGHT_SIDE)
                {
                    _loc_3 = this.RIGHT_SIDE;
                }
                else if (this.m_adjRoadFlags & this.TOP_SIDE)
                {
                    _loc_3 = this.TOP_SIDE;
                }
            }
            else if (this.m_adjRoadFlags & this.LEFT_SIDE)
            {
                _loc_3 = this.LEFT_SIDE;
            }
            else if (this.m_adjRoadFlags & this.BOTTOM_SIDE)
            {
                _loc_3 = this.BOTTOM_SIDE;
            }
            else if (this.m_adjRoadFlags & this.TOP_SIDE)
            {
                _loc_3 = this.TOP_SIDE;
            }
            else if (this.m_adjRoadFlags & this.RIGHT_SIDE)
            {
                _loc_3 = this.RIGHT_SIDE;
            }
            return _loc_3;
        }//end

        public int  getHotspotSidewalkSide ()
        {
            _loc_1 = getPosition();
            _loc_2 = this.getHotspot ().subtract(_loc_1 );
            _loc_2.x = _loc_2.x - m_size.x * 0.5;
            _loc_2.y = _loc_2.y - m_size.y * 0.5;
            _loc_2 = _loc_2.normalize();
            _loc_3 = this.BOTTOM_SIDE ;
            if (Math.abs(_loc_2.x) > Math.abs(_loc_2.y))
            {
                return _loc_2.x < 0 ? (this.LEFT_SIDE) : (this.RIGHT_SIDE);
            }
            else
            {
            }
            return _loc_2.y < 0 ? (this.BOTTOM_SIDE) : (this.TOP_SIDE);
        }//end

        public Vector3  sidewalkSideToNormalVector (int param1 )
        {
            switch(param1)
            {
                case this.LEFT_SIDE:
                {
                    return new Vector3(-1, 0);
                }
                case this.RIGHT_SIDE:
                {
                    return new Vector3(1, 0);
                }
                case this.TOP_SIDE:
                {
                    return new Vector3(0, 1);
                }
                case this.BOTTOM_SIDE:
                {
                    return new Vector3(0, -1);
                }
                default:
                {
                    return null;
                    break;
                }
            }
        }//end

        public Vector3  getNthSidewalkPosition (int param1 ,int param2 )
        {
            _loc_3 = getPosition();
            double _loc_4 =0;
            _loc_5 = m_size.y/param2;
            _loc_6 = this.getRoadSidewalkSide();
            if (this.getRoadSidewalkSide() & this.LEFT_SIDE)
            {
            }
            else if (_loc_6 & this.BOTTOM_SIDE)
            {
                _loc_4 = m_size.x / param2;
                _loc_5 = 0;
            }
            else if (_loc_6 & this.TOP_SIDE)
            {
                _loc_3.y = _loc_3.y + m_size.y;
                _loc_4 = m_size.x / param2;
                _loc_5 = 0;
            }
            else if (_loc_6 & this.RIGHT_SIDE)
            {
                _loc_3.x = _loc_3.x + m_size.x;
            }
            _loc_3.x = _loc_3.x + _loc_4 * (param1 - 0.75);
            _loc_3.y = _loc_3.y + _loc_5 * (param1 - 0.75);
            return _loc_3;
        }//end

        public Vector3  findValidCrowdPosition ()
        {
            Vector3 _loc_1 =new Vector3(0,0,1);
            double _loc_2 =1;
            double _loc_3 =2;
            _loc_4 = this.getHotspot();
            _loc_5 = this.sidewalkSideToNormalVector(this.getHotspotSidewalkSide());
            _loc_6 = this.sidewalkSideToNormalVector(this.getHotspotSidewalkSide()).cross(_loc_1).normalize();
            _loc_4 = _loc_4.add(_loc_5.scale(Math.random() * _loc_2));
            _loc_4 = _loc_4.add(_loc_6.scale((Math.random() - 0.5) * 2 * _loc_3));
            return _loc_4;
        }//end

        public Vector3  findValidGroupPosition ()
        {
            _loc_1 = this.getRoadSidePosition(this.getRoadSidewalkSide()).subtract(this.getRoadSidePosition(0)).normalize();
            return this.getRoadSidePosition(this.getRoadSidewalkSide()).add(_loc_1.scale(1.5));
        }//end

        public Vector3  findCrowdPosMayor ()
        {
            _loc_1 = this.getHotspot();
            _loc_1.x = _loc_1.x - 2;
            _loc_1.y = _loc_1.y + (-1 + (Math.random() * 2 - 1) * 1.5);
            return _loc_1;
        }//end

        public Vector3  findCrowdPosCeremony ()
        {
            Vector3 _loc_1 =new Vector3(0,0,1);
            _loc_2 = getPosition();
            _loc_3 = this.sidewalkSideToNormalVector(this.getHotspotSidewalkSide());
            _loc_4 = _loc_3.cross(_loc_1).normalize();
            _loc_5 = sizeX;
            _loc_6 = sizeY;
            _loc_2.x = _loc_2.x + _loc_5 / 2;
            _loc_2.y = _loc_2.y + _loc_6 / 2;
            _loc_7 = Math.random();
            if (Math.random() <= 0.5)
            {
                _loc_2.x = _loc_2.x - (_loc_5 / 2 + Math.random());
                _loc_2.y = _loc_2.y + (Math.random() * 2 - 1);
            }
            else
            {
                _loc_2.y = _loc_2.y - (_loc_6 / 2 + Math.random());
                _loc_2.x = _loc_2.x + (Math.random() * 2 - 1);
            }
            return _loc_2;
        }//end

        public boolean  isBeingDragged ()
        {
            return this.m_isDragged;
        }//end

        public boolean  isHighlightable ()
        {
            return this.isNeedingRoad ? (this.m_isHighlightable && Global.world.isEditMode) : (this.m_isHighlightable);
        }//end

        public boolean  showActionBar ()
        {
            return true;
        }//end

        public boolean  deferProgressBarToNPC ()
        {
            return true;
        }//end

         protected void  updateActionBarPosition ()
        {
            Point _loc_1 =null ;
            if (m_actionBar && m_actionBar.visible)
            {
                if (!this.m_content)
                {
                    return super.updateActionBarPosition();
                }
                _loc_1 = this.tileScreenPosition;
                _loc_1.y = _loc_1.y - m_overrideActionBarY;
                _loc_1 = IsoMath.viewportToStage(_loc_1);
                _loc_1.y = _loc_1.y - this.m_content.height;
                m_actionBar.x = _loc_1.x - ACTIONBAR_WIDTH / 2 + m_actionBarOffsetX;
                m_actionBar.y = _loc_1.y - ACTIONBAR_HEIGHT + m_actionBarOffsetY;
            }
            return;
        }//end

        public int  getPopulationYield ()
        {
            if (this.m_currentPopulation == 0)
            {
                this.m_currentPopulation = m_item.populationBase;
            }
            return this.m_currentPopulation;
        }//end

        public int  getPopulationMaxYield ()
        {
            return m_item.populationMax;
        }//end

        public int  getPopulationBaseYield ()
        {
            return m_item.populationBase;
        }//end

        public String  getPopulationType ()
        {
            return m_item.populationType;
        }//end

        public void  addPopulation (int param1 )
        {
            _loc_2 = this.getPopulationYield();
            _loc_3 = m_item.populationBase;
            _loc_4 = m_item.populationMax;
            _loc_5 = PopulationUtil.capPopulation(_loc_2+param1,_loc_3,_loc_4);
            this.m_currentPopulation = _loc_5;
            return;
        }//end

        public void  recomputePopulation ()
        {
            this.addPopulation(0);
            return;
        }//end

        public int  getPopulationCapYield ()
        {
            return m_item.populationCapYield;
        }//end

        protected int  friendVisitRepGain ()
        {
            return 0;
        }//end

        public void  initFriendData (String param1 )
        {
            return;
        }//end

         public void  onPlayAction ()
        {
            boolean _loc_1 =false ;
            double _loc_2 =0;
            Array _loc_3 =null ;
            int _loc_4 =0;
            super.onPlayAction();
            if (m_item.clickSound != null)
            {
                _loc_1 = false;
                if (this.m_SoundClickPlayed == -1)
                {
                    _loc_1 = true;
                }
                if (!_loc_1)
                {
                    _loc_2 = GlobalEngine.getTimer() - this.m_SoundClickPlayed;
                    if (_loc_2 > this.m_SoundClickDelayTime)
                    {
                        _loc_1 = true;
                    }
                }
                if (_loc_1)
                {
                    _loc_3 = m_item.clickSound.split(":");
                    Sounds.playSoundFromArray(_loc_3);
                    this.m_SoundClickPlayed = GlobalEngine.getTimer();
                    _loc_4 = Math.round(Utilities.randBetween(Global.gameSettings().getInt("clickSoundMinReplayWait"), Global.gameSettings().getInt("clickSoundMaxReplayWait")));
                    this.m_SoundClickDelayTime = _loc_4 * 1000;
                }
            }
            return;
        }//end

        public Class  getPickupAction ()
        {
            return null;
        }//end

        public Function  getPickupFunction ()
        {
            return null;
        }//end

        public String  getActionText ()
        {
            return "";
        }//end

        protected Array  makeDoobers (double param1 =1)
        {
            return null;
        }//end

        public void  spawnDoobers (boolean param1 =false )
        {
            GlobalEngine.log("Doobers", "MapResource.spawnDoobers() on " + this.getItemName());
            Global.world.dooberManager.createBatchDoobers(this.m_doobersArray, m_item, m_position.x, m_position.y, param1);
            return;
        }//end

        public void  doobersArray (Array param1 )
        {
            this.m_doobersArray = param1;
            return;
        }//end

        public void  secureRandsArray (Array param1 )
        {
            this.m_secureRands = param1;
            return;
        }//end

        public Array  secureRandsArray ()
        {
            return this.m_secureRands;
        }//end

        protected Dictionary  processBaseDoobers (Item param1 ,Array param2 ,String param3 ="default")
        {
            Array _loc_6 =null ;
            String _loc_7 =null ;
            Item _loc_8 =null ;
            _loc_4 = Global.player.processRandomModifiers(param1,this,true,param2,param3);
            Dictionary _loc_5 =new Dictionary ();
            for(int i0 = 0; i0 < _loc_4.size(); i0++)
            {
            	_loc_6 = _loc_4.get(i0);

                if (_loc_6.get(0) == Global.gameSettings().getDooberFromType(Doober.DOOBER_COIN, _loc_6.get(1)))
                {
                    _loc_5.put(Doober.DOOBER_COIN,  _loc_6.get(1));
                    continue;
                }
                if (_loc_6.get(0) == Global.gameSettings().getDooberFromType(Doober.DOOBER_XP, _loc_6.get(1)))
                {
                    _loc_5.put(Doober.DOOBER_XP,  _loc_6.get(1));
                    continue;
                }
                if (_loc_6.get(0) == Global.gameSettings().getDooberFromType(Doober.DOOBER_ENERGY, _loc_6.get(1)))
                {
                    _loc_5.put(Doober.DOOBER_ENERGY,  _loc_6.get(1));
                    continue;
                }
                if (_loc_6.get(0) == Global.gameSettings().getDooberFromType("food", _loc_6.get(1)))
                {
                    _loc_5.put("food",  _loc_6.get(1));
                    continue;
                }
                if (_loc_6.get(0) == Global.gameSettings().getDooberFromType(Doober.DOOBER_GOODS, _loc_6.get(1)))
                {
                    _loc_5.put(Doober.DOOBER_GOODS,  _loc_6.get(1));
                    continue;
                }
                _loc_7 = _loc_6.get(0);
                _loc_8 = Global.gameSettings().getItemByName(_loc_7);
                if (_loc_8.type == Doober.DOOBER_COLLECTABLE)
                {
                    _loc_5.put(Doober.DOOBER_COLLECTABLE,  _loc_7);
                    continue;
                }
                _loc_5.put(Doober.DOOBER_ITEM,  _loc_7);
            }
            return _loc_5;
        }//end

        protected void  enterMoveMode ()
        {
            this.setActive(false);
            Global.world.addGameMode(new GMEdit());
            Global.world.addGameMode(new GMObjectMove(this, null, null, Constants.INDEX_NONE, true), false);
            return;
        }//end

        public boolean  isPlacedObjectNonBuilding ()
        {
            return false;
        }//end

        public String  GetNoMoveMessage ()
        {
            return ZLoc.t("Main", "NoMove");
        }//end

        public Point  getProgressBarOffset ()
        {
            return new Point(0, 0);
        }//end

        public Point  getConstructionNPCOffset ()
        {
            return new Point(0, 0);
        }//end

        public Function  getProgressBarStartFunction ()
        {
            return null;
        }//end

        public Function  getProgressBarEndFunction ()
        {
            return null;
        }//end

        public Function  getProgressBarCancelFunction ()
        {
            return null;
        }//end

        public StagePickEffect  stagePickEffect ()
        {
            return this.m_stagePickEffect;
        }//end

        public void  stagePickEffect (StagePickEffect param1 )
        {
            this.m_stagePickEffect = param1;
            return;
        }//end

        protected void  createStagePickEffect ()
        {
            return;
        }//end

        public void  setShowUpgradeArrow (boolean param1 )
        {
            this.m_showUpgradeArrow = param1;
            return;
        }//end

        public boolean  getShowUpgradeArrow ()
        {
            return this.m_showUpgradeArrow;
        }//end

        public void  removeStagePickEffect ()
        {
            if (this.m_stagePickEffect)
            {
                this.m_stagePickEffect.stopFloat();
                this.m_stagePickEffect.cleanUp();
                this.m_stagePickEffect = null;
            }
            return;
        }//end

        public boolean  canShowStagePick ()
        {
            return true;
        }//end

        public String  getActionTargetName ()
        {
            String _loc_1 ="";
            if (m_item)
            {
                _loc_1 = m_item.name;
            }
            return _loc_1;
        }//end

        public String  getVisitReplayEquivalentActionString ()
        {
            return "";
        }//end

        public boolean  warnForStorage ()
        {
            return false;
        }//end

        public String  warnForStorageMessage ()
        {
            return ZLoc.t("Dialogs", "warehouseWarning", {itemName:this.getItem().localizedName});
        }//end

        public void  prepareForStorage (MapResource param1)
        {
            this.m_itemImage = null;
            m_displayObject = null;
            return;
        }//end

        public void  restoreFromStorage (MapResource param1)
        {
            this.trackAction(TrackedActionType.RESTORE);
            return;
        }//end

        public boolean  addSupply (String param1 ,int param2 )
        {
            if (!this.m_supplyStorage)
            {
                this.m_supplyStorage = new SupplyStorage();
            }
            _loc_3 = this.getSupplyCapacities();
            this.m_supplyStorage.setCapacities(_loc_3);
            _loc_4 = this.m_supplyStorage.add(param1,param2);
            return this.m_supplyStorage.add(param1, param2);
        }//end

        public boolean  removeSupply (String param1 ,int param2 )
        {
            boolean _loc_3 =false ;
            if (this.m_supplyStorage)
            {
                _loc_3 = this.m_supplyStorage.remove(param1, param2);
                if (this.m_supplyStorage.getTotalCount() == 0)
                {
                    this.m_supplyStorage = null;
                }
            }
            return _loc_3;
        }//end

        public boolean  clearSupply ()
        {
            this.m_supplyStorage = null;
            return true;
        }//end

        public int  getSupply (String param1 )
        {
            int _loc_2 =0;
            if (this.m_supplyStorage)
            {
                _loc_2 = this.m_supplyStorage.getCount(param1);
            }
            return _loc_2;
        }//end

        protected Object  getSupplyCapacities ()
        {
            return {};
        }//end

        public boolean  isUpgradePossible ()
        {
            return m_item.upgrade != null && m_item.upgrade.isValid() && m_item.upgrade.isUpgradePossible(this);
        }//end

        public boolean  canCountUpgradeActions ()
        {
            _loc_1 = this.getItem();
            return _loc_1 && _loc_1.canCountUpgradeActions();
        }//end

        public boolean  canShowUpgradeToolTips ()
        {
            return this.canCountUpgradeActions() || this.canShowAlternativeUpgradeToolTip();
        }//end

        public boolean  canShowAlternativeUpgradeToolTip ()
        {
            _loc_1 = this.getItem();
            return _loc_1 && _loc_1.level > 1;
        }//end

        public void  showUpgradeProgressFloaterText (int param1 =16777215)
        {
            double _loc_2 =0;
            double _loc_3 =0;
            if (this.canCountUpgradeActions())
            {
                _loc_2 = Number(this.getItem().upgrade.requirements.getRequirementValue(Requirements.REQUIREMENT_UPGRADE_ACTIONS));
                _loc_3 = Math.min(this.upgradeActions.getTotal(), _loc_2);
                if (_loc_3 < _loc_2)
                {
                    this.displayStatus(ZLoc.t("Main", "LevelProgress", {amount:_loc_3, total:_loc_2}), "", param1);
                }
                else
                {
                    this.displayStatus(ZLoc.t("Main", "UpgradeReady"), "", param1);
                }
            }
            return;
        }//end

        public void  upgradeBuildingIfPossible (boolean param1 =true ,Transaction param2 ,boolean param3 =true )
        {
            if (!this.isUpgradePossible())
            {
                return;
            }
            if (param2)
            {
                GameTransactionManager.addTransaction(param2);
            }
            else
            {
                GameTransactionManager.addTransaction(new TUpgradeBuilding(this));
            }
            _loc_4 = Global.gameSettings().getItemByName(m_item.upgrade.newItemName);
            this.onUpgrade(m_item, _loc_4, param3);
            this.doUpgradeAnimations(this.m_defaultUpgradeFinish, param1);
            Global.ui.dispatchEvent(new GenericObjectEvent(GenericObjectEvent.BUILDING_UPGRADE, this));
            return;
        }//end

        public void  doUpgradeAnimations (Function param1 ,boolean param2 =true )
        {
            this.actionQueue.removeAllStates();
            this.actionQueue.addActions(new ActionProgressBar(null, this, ZLoc.t("Main", "Upgrading")), new ActionFn(this, this.updateArrow));
            if (param1 != null)
            {
                this.actionQueue.addActions(new ActionFn(this, param1));
            }
            if (param2)
            {
                Sounds.playFromSet(Sounds.SET_BUILDING_CONSTRUCTION);
            }
            return;
        }//end

        public boolean  checkPlacementRequirements (int param1 ,int param2 )
        {
            _loc_3 = m_item.placeableOn;
            if (_loc_3 == null)
            {
                _loc_3 = MapResource.PLACE_ON_LAND;
            }
            _loc_4 = getSize();
            boolean _loc_5 =false ;
            switch(_loc_3)
            {
                case MapResource.PLACE_ON_WATER:
                {
                    _loc_5 = Global.world.citySim.waterManager.positionValidShipOnWater(new Rectangle(param1, param2, _loc_4.x, _loc_4.y));
                    break;
                }
                case MapResource.PLACE_ON_LAND_AND_WATER:
                {
                    _loc_5 = Global.world.citySim.waterManager.positionValidOnLandAndWater(new Rectangle(param1, param2, _loc_4.x, _loc_4.y), m_item.landNeeded, m_item.waterNeeded);
                    break;
                }
                case MapResource.PLACE_ON_LAND_OR_WATER:
                {
                    _loc_5 = Global.world.citySim.waterManager.positionOnValidLand(new Rectangle(param1, param2, _loc_4.x, _loc_4.y)) || Global.world.citySim.waterManager.positionValidShipOnWater(new Rectangle(param1, param2, _loc_4.x, _loc_4.y));
                    break;
                }
                case MapResource.PLACE_ON_LAND:
                {
                }
                default:
                {
                    _loc_5 = Global.world.citySim.waterManager.positionOnValidLand(new Rectangle(param1, param2, _loc_4.x, _loc_4.y));
                    break;
                    break;
                }
            }
            return _loc_5;
        }//end

        public void  updateStagePickEffect ()
        {
            this.updateArrow();
            return;
        }//end

        public void  onUpgrade (Item param1 ,Item param2 ,boolean param3 =true )
        {
            if (Global.world.getObjectById(this.getId()))
            {
                this.detach();
                setItem(param2);
                this.attach();
            }
            else
            {
                setItem(param2);
            }
            this.setState(this.getState());
            //Global.questManager.refreshAllQuests();
            return;
        }//end

        public void  switchItem (Item param1 )
        {
            if (Global.world.getObjectById(this.getId()))
            {
                this.detach();
                setItem(param1);
                this.attach();
            }
            else
            {
                setItem(param1);
            }
            this.setState(this.getState());
            return;
        }//end

        protected Array  grantUpgradeRewards (Item param1)
        {
            int _loc_3 =0;
            if (param1 == null)
            {
                param1 = m_item;
            }
            Array _loc_2 =new Array ();
            if (param1.upgrade != null && param1.upgrade.rewards != null)
            {
                if (param1.upgrade.rewards.get("coin"))
                {
                    _loc_2.push(new Array(Global.gameSettings().getDooberFromType(Doober.DOOBER_COIN, Number(param1.upgrade.rewards.get("coin"))), Number(param1.upgrade.rewards.get("coin"))));
                }
                if (param1.upgrade.rewards.get("xp"))
                {
                    _loc_2.push(new Array(Global.gameSettings().getDooberFromType(Doober.DOOBER_XP, Number(param1.upgrade.rewards.get("xp"))), Number(param1.upgrade.rewards.get("xp"))));
                }
                if (param1.upgrade.rewards.get("energy"))
                {
                    _loc_2.push(new Array(Global.gameSettings().getDooberFromType(Doober.DOOBER_ENERGY, Number(param1.upgrade.rewards.get("energy"))), Number(param1.upgrade.rewards.get("energy"))));
                }
                if (param1.upgrade.rewards.get("goods"))
                {
                    _loc_2.push(new Array(Global.gameSettings().getDooberFromType(Doober.DOOBER_GOODS, Number(param1.upgrade.rewards.get("goods"))), Number(param1.upgrade.rewards.get("goods"))));
                }
                if (param1.upgrade.rewards.get("collectable"))
                {
                    _loc_2.push(new Array(Number(param1.upgrade.rewards.get("collectable")), 1));
                }
                if (param1.upgrade.rewards.get("itemUnlock"))
                {
                    Global.player.setSeenSessionFlag(param1.upgrade.rewards.get("itemUnlock"));
                    if (UI.m_catalog)
                    {
                        UI.m_catalog.updateChangedCells();
                    }
                }
            }
            return _loc_2;
        }//end

        public ItemImageInstance  getGhostImage ()
        {
            return this.getCurrentImage();
        }//end

        public void  notifyUserForExpansionsOnPlace (Rectangle param1 )
        {
            return;
        }//end

        public String  getCrewPositionName (String param1 )
        {
            int _loc_5 =0;
            String _loc_2 ="";
            _loc_3 = this.getCrewPositionNames();
            _loc_4 = Global.crews.getCrewByObject(this);
            if (Global.crews.getCrewByObject(this))
            {
                _loc_5 = 0;
                while (_loc_5 < _loc_4.list.length())
                {

                    if (_loc_4.list.get(_loc_5) == param1 && _loc_3.length > _loc_5)
                    {
                        _loc_2 = _loc_3.get(_loc_5);
                        break;
                    }
                    _loc_5++;
                }
            }
            return _loc_2;
        }//end

        public String Vector  getCrewPositionNames ().<>
        {
            XMLList _loc_2 =null ;
            XML _loc_3 =null ;
            String _loc_4 =null ;
            double _loc_5 =0;
            String _loc_6 =null ;
            int _loc_7 =0;
            Vector<String> _loc_1 =new Vector<String>();
            if (m_item)
            {
                _loc_2 = m_item.getCurrentCrewGateXML(this);
                if (_loc_2 && _loc_2.length() > 0)
                {
                    for(int i0 = 0; i0 < _loc_2.get("key").get("member").size(); i0++)
                    {
                    		_loc_3 = _loc_2.get("key").get("member").get(i0);

                        _loc_4 = _loc_3.@name;
                        _loc_5 = _loc_3.@amount;
                        _loc_6 = ZLoc.t("Items", _loc_4);
                        _loc_7 = 0;
                        while (_loc_7 < _loc_5)
                        {

                            _loc_1.push(_loc_6);
                            _loc_7++;
                        }
                    }
                }
            }
            return _loc_1;
        }//end

        public void  showMasteryProgressFloaterText (int param1 ,String param2 )
        {
            String _loc_4 =null ;
            _loc_3 = (MasteryGoal)Global.goalManager.getGoal(GoalManager.GOAL_MASTERY)
            if (_loc_3 && this.harvestingDefinition && _loc_3.isUsingMastery(this.harvestingDefinition.name))
            {
                _loc_4 = param2 ? (param2) : ("MasteryProgress");
                this.displayStatus(ZLoc.t("Main", _loc_4, {amount:param1}), new EmbeddedArt.smallStarIcon(), 16777215);
            }
            return;
        }//end

         public void  setId (double param1 )
        {
            Global.world.delObjectFromIdCache(this);
            super.setId(param1);
            if (isAttached())
            {
                Global.world.addObjectToIdCache(this);
            }
            return;
        }//end

        public void  doSupplyDoobers (String param1 ,double param2 )
        {
            _loc_3 =Global.hud.localToGlobal(Global.hud.hudGoodsPosition );
            _loc_4 =Global.gameSettings().getDooberFromType(param1 ,param2 );
            _loc_5 = IsoMath.viewportToStage(GMObjectMove.getPixelCenter(this ));
            IsoMath.viewportToStage(GMObjectMove.getPixelCenter(this)).y = IsoMath.viewportToStage(GMObjectMove.getPixelCenter(this)).y - 40;
            _loc_6 = int(param2/20)+1;
            while (_loc_6 > 1)
            {

                setTimeout(Global.world.dooberManager.createDummyDoober, 200, _loc_4, _loc_3, _loc_5);
                _loc_6 = _loc_6 - 1;
            }
            if (_loc_6 == 1)
            {
                Global.world.dooberManager.createDummyDoober(_loc_4, _loc_3, _loc_5);
            }
            return;
        }//end

        public boolean  glowActive ()
        {
            return this.m_glowTimeline != null;
        }//end

        public void  enableGlow (int param1 =16771584)
        {
            DisplayObject _loc_2 =null ;
            if (this.m_glowTimeline == null)
            {
                _loc_2 = getDisplayObject();
                this.m_glowTimeline = new TimelineLite();
                this.m_glowTimeline.appendMultiple(.get(new TweenLite(_loc_2, 0.2, {glowFilter:{color:param1, strength:100, alpha:1, blurX:5, blurY:5}})), 0, TweenAlign.SEQUENCE, 5);
            }
            return;
        }//end

        public void  disableGlow ()
        {
            DisplayObject _loc_1 =null ;
            if (this.m_glowTimeline != null)
            {
                _loc_1 = getDisplayObject();
                this.m_glowTimeline = new TimelineLite({onComplete:this.killGlow});
                this.m_glowTimeline.appendMultiple(.get(new TweenLite(_loc_1, 0.2, {glowFilter:{color:EmbeddedArt.PEEP_HIGHLIGHT_COLOR, strength:100, alpha:0, blurX:5, blurY:5}})), 0, TweenAlign.SEQUENCE, 1);
            }
            return;
        }//end

        private void  killGlow ()
        {
            if (this.m_glowTimeline)
            {
                this.m_glowTimeline.kill();
                this.m_glowTimeline = null;
            }
            return;
        }//end

        public void  repairSparkle ()
        {
            String _loc_1 =null ;
            for(int i0 = 0; i0 < this.m_sparkly.size(); i0++)
            {
            		_loc_1 = this.m_sparkly.get(i0);

                this.addAnimatedEffect(EffectType.WONDERSPARKLE);
                break;
            }
            return;
        }//end

        public void  makeSparkle (String param1 )
        {
            MapResourceEffect _loc_2 =null ;
            if (!(param1 in this.m_sparkly))
            {
                if (!this.hasAnimatedEffect(EffectType.WONDERSPARKLE))
                {
                    _loc_2 = MapResourceEffectFactory.createEffect(this, EffectType.WONDERSPARKLE);
                    this.addAnimatedEffectFromInstance(_loc_2);
                }
                this.m_sparkly.put(param1,  1);
            }
            return;
        }//end

        public void  killSparkle (String param1 )
        {
            String _loc_2 =null ;
            if (param1 in this.m_sparkly)
            {
                delete this.m_sparkly.get(param1);
                for(int i0 = 0; i0 < this.m_sparkly.size(); i0++)
                {
                		_loc_2 = this.m_sparkly.get(i0);

                    return;
                }
                this.removeAnimatedEffect(EffectType.WONDERSPARKLE);
            }
            return;
        }//end

        public void  showToaster (String param1 ,boolean param2 =false )
        {
            _loc_3 = m_item.getToasterInfo(param1);
            _loc_4 = _loc_3.asset? (Global.getAssetURL(_loc_3.asset)) : (null);
            ItemToaster _loc_5 =new ItemToaster(ZLoc.t("Main",_loc_3.title ),ZLoc.t("Main",_loc_3.text ),_loc_4 ,null ,_loc_3.duration );
            Global.ui.toaster.show(_loc_5, param2);
            return;
        }//end

        public void  notifyUpgrade (Object param1 )
        {
            return;
        }//end

        public double  getRadius (ItemBonus param1 )
        {
            return param1.radiusMin;
        }//end

        public double  getAOEPercentModifier (ItemBonus param1 )
        {
            return param1.dynamicModifierArray.get("base");
        }//end

        protected boolean  suppressVisitorActions ()
        {
            return false;
        }//end

        protected void  popRemodelMarket ()
        {
            GMRemodel _loc_1 =null ;
            if (this instanceof IMechanicUser)
            {
                _loc_1 = new GMRemodel();
                Global.world.addGameMode(_loc_1);
                _loc_1.setSelectedObject(this);
                MechanicManager.getInstance().handleAction((IMechanicUser)this, "GMRemodel");
            }
            return;
        }//end

        public void  fireZeMissiles ()
        {
            MapResource rofls ;
            Sounds.play("cruise_fireworks");
            rofls =(MapResource) this;
            MapResourceEffectFactory.createEffect(rofls, EffectType.FIREWORK_BALLOONS);
            TimerUtil .callLater (void  ()
            {
                MapResourceEffectFactory.createEffect(rofls, EffectType.FIREWORK_BALLOONS);
                return;
            }//end
            , 300);
            TimerUtil .callLater (void  ()
            {
                MapResourceEffectFactory.createEffect(rofls, EffectType.FIREWORK_BALLOONS);
                return;
            }//end
            , 500);
            TimerUtil .callLater (void  ()
            {
                MapResourceEffectFactory.createEffect(rofls, EffectType.FIREWORK_BALLOONS);
                return;
            }//end
            , 700);
            TimerUtil .callLater (void  ()
            {
                MapResourceEffectFactory.createEffect(rofls, EffectType.FIREWORK_BALLOONS);
                return;
            }//end
            , 900);
            TimerUtil .callLater (void  ()
            {
                MapResourceEffectFactory.createEffect(rofls, EffectType.FIREWORK_BALLOONS);
                return;
            }//end
            , 1000);
            TimerUtil .callLater (void  ()
            {
                MapResourceEffectFactory.createEffect(rofls, EffectType.FIREWORK_BALLOONS);
                return;
            }//end
            , 1200);
            TimerUtil .callLater (void  ()
            {
                MapResourceEffectFactory.createEffect(rofls, EffectType.FIREWORK_BALLOONS);
                return;
            }//end
            , 1600);
            TimerUtil .callLater (void  ()
            {
                MapResourceEffectFactory.createEffect(rofls, EffectType.FIREWORK_BALLOONS);
                return;
            }//end
            , 1300);
            return;
        }//end

        public boolean  isOpen ()
        {
            return false;
        }//end

    }




