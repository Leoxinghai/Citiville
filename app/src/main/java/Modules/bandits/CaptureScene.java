package Modules.bandits;

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
import Classes.actions.*;
import Classes.effects.*;
import Classes.util.*;
import Display.*;
import Display.DialogUI.*;
import Display.HunterAndPreyUI.*;
import Engine.*;
import Engine.Helpers.*;
import Events.*;
import Modules.workers.*;
import com.zynga.skelly.util.*;
//import flash.geom.*;

    public class CaptureScene extends NPCScene
    {
        public Array m_npcs ;
        protected Array m_huntersUsed ;
        protected NPC m_target ;
        protected String m_capturer ="";
        protected int m_numCars =1;
        protected PreyData m_data ;
        protected boolean m_cashScene ;
        protected int m_dooberCounter =0;
public static  String CAPTURE_ITEM_NAME ="prop_arrest_scene";
public static  int CAPTURE_RADIUS =2;
public static  int CAPTURE_LOITER_TIME =6;
public static  double CAPTURE_FADE_TIME =0.5;
public static  double CAPTURE_TIME =6;

        public  CaptureScene (PreyData param1 ,Vector3 param2 ,boolean param3 =false )
        {
            this.m_huntersUsed = new Array();
            super(CAPTURE_ITEM_NAME);
            this.m_data = param1;
            this.m_cashScene = param3;
            _loc_4 = param2!= null ? (param2) : (this.findRandomScenePosition());
            this.setPosition(_loc_4.x, _loc_4.y);
            return;
        }//end

        public PreyData  data ()
        {
            return this.m_data;
        }//end

        public int  numCars ()
        {
            return this.m_numCars;
        }//end

        public void  numCars (int param1 )
        {
            this.m_numCars = Math.max(param1, 0);
            return;
        }//end

        public NPC  target ()
        {
            return this.m_target;
        }//end

        public void  target (NPC param1 )
        {
            this.m_target = param1;
            if (this.m_target)
            {
                this.m_target.clearStates();
            }
            return;
        }//end

        public String  groupID ()
        {
            return this.m_data.groupId;
        }//end

        public void  shutdown ()
        {
            this.killNPCs();
            this.m_npcs.splice(0);
            return;
        }//end

        public void  killTarget ()
        {
            if (this.m_target)
            {
                this.m_target.playActionCallback = null;
                if (Global.world.citySim.npcManager.isNpcTracked(this.m_target))
                {
                    Global.world.citySim.npcManager.removeNpc(this.m_target);
                }
                else
                {
                    this.m_target.detach();
                    this.m_target.cleanUp();
                }
                this.m_target = null;
            }
            return;
        }//end

         protected void  killNPCs ()
        {
            NPC _loc_1 =null ;
            for(int i0 = 0; i0 < this.m_npcs.concat(this.m_target).size(); i0++)
            {
            		_loc_1 = this.m_npcs.concat(this.m_target).get(i0);

                if (!(_loc_1 instanceof Vehicle) && _loc_1 != null)
                {
                    if (_loc_1 == this.m_target && _loc_1)
                    {
                        this.killTarget();
                        continue;
                    }
                    this.fadeNPC(_loc_1);
                }
            }
            this.m_target = null;
            return;
        }//end

        protected void  fadeNPC (NPC param1 )
        {
            npc = param1;
            npc .getStateMachine ().addActions (new ActionFn (npc ,Curry .curry (void  (NPC param11 )
            {
                param11.animation = "static";
                return;
            }//end
            ,npc )),new ActionNavigateRandom (npc ).setTimeout (CaptureScene .CAPTURE_LOITER_TIME ),new ActionTween (npc ,ActionTween .TO ,CaptureScene .CAPTURE_FADE_TIME ,{0alpha }),new ActionFn (npc ,Curry .curry (void  (NPC param12 )
            {
                Global.world.citySim.npcManager.removeWalker(param12);
                return;
            }//end
            , npc)));
            return;
        }//end

         public double  getDepthPriority ()
        {
            return -1;
        }//end

         protected void  calculateDepthIndex ()
        {
            super.calculateDepthIndex();
            m_depthIndex = m_depthIndex + 10000;
            return;
        }//end

         public String  getToolTipHeader ()
        {
            return null;
        }//end

         public Point  getProgressBarOffset ()
        {
            return new Point(0, -25);
        }//end

         protected void  createNPCs ()
        {
            Vector3 cpos ;
            NPC hunter ;
            HunterData workerData ;
            int hunterDir ;
            Array colliders ;
            MapResource collider ;
            MapResource newTile ;
            Vector3 diff ;
            if (this.target)
            {
                this.setPosition(this.target.getPosition().x, this.target.getPosition().y);
            }
            pos = this.getPosition();
            bpos = this.getPosition();
            this.m_npcs = new Array();
            this.startProgress();
            dir = Constants.DIRECTION_SW;
            radialCount = Math.PI;
            radialIncrement = Math2*.PI/this.m_data.getRequiredHunters();
            numHuntersToPull = this.m_data.getRequiredHunters();
            numHuntersToSpawn = this.getNumCopsToSpawn();
            workers = (HunterPreyWorkers)Global.world.citySim.preyManager.getWorkerManagerByGroup(this.m_data.groupId).getWorkers(HunterPreyWorkers.FEATURE_HUNTER_PREY_BUCKET)
            int hunterNum ;
            while (hunterNum < numHuntersToPull)
            {

                hunter = PreyManager.pullHunterForCapture(this.m_data.groupId, this.getHunterNPCType(), pos, this.m_cashScene);
                workerData = workers.getNpcOwner(hunter);
                if (workerData)
                {
                    this.m_huntersUsed.push(workerData);
                }
                if (hunterNum >= numHuntersToSpawn)
                {
                    this.fadeNPC(hunter);
                }
                else if (hunter)
                {
                    this.m_npcs.push(hunter);
                    cpos = new Vector3(pos.x + CaptureScene.CAPTURE_RADIUS * Math.cos(radialCount), pos.y + CaptureScene.CAPTURE_RADIUS * Math.sin(radialCount));
                    hunterDir = this.getGoodHunterCaptureDirection(radialCount);
                    colliders = Global.world.getObjectsByClassAt(MapResource, cpos);
                    int _loc_2 =0;
                    _loc_3 = colliders;
                    for(int i0 = 0; i0 < colliders.size(); i0++)
                    {
                    		collider = colliders.get(i0);


                        if (collider instanceof Sidewalk || collider instanceof Road)
                        {
                            continue;
                        }
                        newTile = Global.world.citySim.roadManager.findClosestWalkableTile(cpos);
                        cpos = newTile.getHotspot();
                        diff = cpos.subtract(pos);
                        hunterDir = this.getGoodHunterCaptureDirection(Math.atan2(diff.y, diff.x));
                        break;
                    }
                    if (hunterDir != -1)
                    {
                        hunter.setDirection(hunterDir);
                    }
                    radialCount = radialCount + radialIncrement;
                    hunter.animation = "idle";
                    hunter .getStateMachine ().addActions (new ActionTween (hunter ,ActionTween .TO ,CaptureScene .CAPTURE_FADE_TIME ,{0alpha }),new ActionFn (hunter ,Curry .curry (void  (NPC param1 ,Vector3 param2 )
            {
                param1.setPosition(param2.x, param2.y);
                param1.conditionallyReattach();
                return;
            }//end
            ,hunter ,cpos )),new ActionAnimationEffect (hunter ,EffectType .POOF ),new ActionFn (hunter ,Curry .curry (void  (NPC param1 ,int param2 )
            {
                param1.animation = param2 == -1 ? ("idle") : (MathUtil.randomElement(getHunterCaptureAnimationStates()));
                return;
            }//end
            , hunter, hunterDir)), new ActionFn(hunter, this.startScene));
                    hunter.actionSelection = new ArrestActionSelection(hunter, true, hunterDir == -1 ? ("idle") : (MathUtil.randomElement(this.getHunterCaptureAnimationStates())));
                    hunter.playActionCallback = null;
                }
                else
                {
                    throw new Error("Couldn\'t pull hunter for capture");
                }
                hunterNum = (hunterNum + 1);
            }
            if (!this.target)
            {
                this.m_target = new NPC(this.getTargetNPCType(), false);
                this.m_target.setOuter(Global.world);
                this.m_target.attach();
            }
            this.m_target.actionSelection = new ArrestActionSelection(this.m_target, false, MathUtil.randomElement(this.getTargetCaptureAnimationStates()), this.getTargetDefaultCaptureState());
            this.m_target.setPosition(bpos.x, bpos.y);
            this.m_target.setDirection(dir);
            this.m_target.playActionCallback = null;
            if (this.getNumCopsToSpawn() <= 0)
            {
                this.startScene();
            }
            return;
        }//end

        protected void  spawnVehicles ()
        {
            return;
        }//end

        Object protected  startProgress (String param1 ="",double param2 =1)
        {
            _loc_3 = super.startProgress(this.getProgressBarText(),CaptureScene.CAPTURE_TIME);
            if (_loc_3 && _loc_3 instanceof ActionProgressBar)
            {
                ((ActionProgressBar)_loc_3).setTimedCallback(Math.max(CaptureScene.CAPTURE_TIME - 2.5, 0), this.startTargetCaptureAnimation);
            }
            return _loc_3;
        }//end

         protected void  startScene ()
        {
            if (m_alreadyStarted)
            {
                return;
            }
            m_alreadyStarted = true;
            Sounds.play(this.getArrestSound());
            this.spawnVehicles();
            return;
        }//end

        protected void  startTargetCaptureAnimation ()
        {
            _loc_1 = MathUtil.randomElement(this.getHunterCaptureAnimationStates());
            this.target.animation = "";
            this.target.actionQueue.addActions(new ActionPlayAnimationOneLoop(this.target, _loc_1), new ActionFn(this.target, this.killTarget));
            return;
        }//end

         protected void  finishScene ()
        {
            NPC _loc_2 =null ;
            HunterPreyWorkers _loc_3 =null ;
            HunterData _loc_4 =null ;
            if (m_alreadyFinished)
            {
                return;
            }
            m_alreadyFinished = true;
            if (PreyManager.isUsingResource(this.m_data.groupId))
            {
                _loc_2 = this.m_npcs.get(0);
                _loc_3 =(HunterPreyWorkers) Global.world.citySim.preyManager.getWorkerManagerByGroup(this.m_data.groupId).getWorkers(HunterPreyWorkers.FEATURE_HUNTER_PREY_BUCKET);
                if (_loc_3)
                {
                    _loc_4 = _loc_3.getNpcOwner(_loc_2);
                    if (_loc_4)
                    {
                        this.m_capturer = Global.player.getFriendName(GameUtil.formatServerUid(_loc_4.m_ZID));
                    }
                    else
                    {
                        this.m_capturer = ZLoc.t("Main", "FakeFriendName");
                    }
                }
                if (this.m_capturer == null || this.m_capturer == "")
                {
                    this.m_capturer = ZLoc.t("Main", "FakeFriendName");
                }
            }
            else
            {
                this.m_capturer = this.getNonResourceCapturerName();
            }
            this.killNPCs();
            m_doobersArray = this.makeDoobers();
            if (m_doobersArray.length > 0)
            {
                this.spawnDoobers(true);
            }
            _loc_1 = this.m_data.getRewardItems ();
            if (_loc_1.length > 0)
            {
                this.giveItemRewards(_loc_1);
            }
            PreyUtil.refreshHubAppearance(this.groupID);
            return;
        }//end

         public void  spawnDoobers (boolean param1 =false )
        {
            GlobalEngine.log("Doobers", "MapResource.spawnDoobers() on " + this.getItemName());
            Global.world.dooberManager.createBatchDoobers(m_doobersArray, null, m_position.x, m_position.y, param1, this.showDialogCallback);
            return;
        }//end

        protected void  showDialogCallback ()
        {
            if (Global.isVisiting())
            {
                return;
            }
            this.showCapturedDialog();
            PreyManager.removeCaptureScene(this);
            return;
        }//end

        protected void  showCapturedDialog (boolean param1 =false ,boolean param2 =false )
        {
            _loc_3 =Global.world.citySim.preyManager.getWorkerManagerByGroup(this.groupID ).getWorkers(HunterPreyWorkers.FEATURE_HUNTER_PREY_BUCKET )as HunterPreyWorkers ;
            if (!_loc_3)
            {
                throw new Error("Can\'t show capture dialog because worker data missing");
            }
            _loc_4 = _loc_3.getNpcOwner(this.m_npcs.get(0) );
            PreyCapturedDialog _loc_5 =new PreyCapturedDialog(this.m_data.groupId ,this.m_data.id ,_loc_4 ,param1 ,param2 ,this.showFeedHandler );
            UI.displayPopup(_loc_5);
            this.showFirstTimeCapturePopup();
            return;
        }//end

        protected void  showFirstTimeCapturePopup ()
        {
            Object _loc_1 =null ;
            String _loc_2 =null ;
            String _loc_3 =null ;
            CharacterDialog _loc_4 =null ;
            if (!Global.player.getSeenFlag(this.groupID + "_Capture"))
            {
                Global.player.setSeenFlag(this.groupID + "_Capture");
                _loc_1 = Global.gameSettings().getHubQueueInfo(this.groupID);
                _loc_2 = _loc_1.get("catchPreyFTUEAsset");
                _loc_3 = ZLoc.t("Dialogs", this.groupID + "_Captured_FTUE_message");
                _loc_4 = new CharacterDialog(_loc_3, this.groupID + "_Captured", GenericDialogView.TYPE_OK, null, null, true, _loc_2, "OkButton");
                UI.displayPopup(_loc_4);
            }
            return;
        }//end

        protected void  showFeedHandler (GenericPopupEvent event )
        {
            if (event.button == GenericPopup.YES)
            {
                Global.world.viralMgr.sendPreyCapture(this.groupID, this.m_data.id, this.m_capturer);
            }
            return;
        }//end

        protected Vector3  findRandomScenePosition ()
        {
            _loc_1 = this.findBestRandomRoad ();
            if (_loc_1 != null)
            {
                return _loc_1.getPosition();
            }
            return new Vector3(0, 0);
        }//end

        public Array  getHuntersUsed ()
        {
            return this.m_huntersUsed;
        }//end

        protected int  getNumCopsToSpawn ()
        {
            _loc_1 = this.m_data.getRequiredHunters ();
            _loc_2 = PreyManager.getHunterPreyMode(this.m_data.groupId);
            _loc_3 = _loc_2.get("maxCaptureNPCs") ? (_loc_2.get("maxCaptureNPCs")) : (_loc_1);
            return Math.min(_loc_3, _loc_1);
        }//end

        protected String  getArrestSound ()
        {
            return this.m_data.captureSound;
        }//end

        protected String  getProgressBarText ()
        {
            Object _loc_2 =null ;
            String _loc_3 =null ;
            String _loc_4 =null ;
            _loc_1 =Global.gameSettings().getXmlData("preyGroups");
            if (_loc_1)
            {
                _loc_2 = _loc_1.get(this.m_data.groupId);
                if (_loc_2 && _loc_2.get("captureText"))
                {
                    _loc_3 = _loc_2.get("captureText").get("package");
                    _loc_4 = _loc_2.get("captureText").get("key");
                    return ZLoc.t(_loc_3, _loc_4);
                }
            }
            return ZLoc.t("Main", "Arresting");
        }//end

        protected String  getHunterNPCType ()
        {
            _loc_1 =Global.gameSettings().getHubCaptureNpc(this.groupID ,PreyUtil.getHubLevel(this.groupID ));
            if (_loc_1)
            {
                return _loc_1;
            }
            return PreyManager.NPC_COP;
        }//end

        protected String  getTargetNPCType ()
        {
            return this.m_data.itemName;
        }//end

        protected Array  getHunterCaptureAnimationStates ()
        {
            return .get("capture");
        }//end

        protected Array  getTargetCaptureAnimationStates ()
        {
            return this.m_data.captureAnimations;
        }//end

        protected String  getTargetDefaultCaptureState ()
        {
            return "capture";
        }//end

        protected String  getNonResourceCapturerName ()
        {
            return ZLoc.t("Prey", "PigeonMan");
        }//end

        protected int  getGoodHunterCaptureDirection (double param1 )
        {
            _loc_2 = param1% (2 * Math.PI) * 180 / Math.PI;
            if (_loc_2 <= 45 || _loc_2 >= 315)
            {
                return Constants.DIRECTION_NE;
            }
            if (_loc_2 <= 135)
            {
                return Constants.DIRECTION_NW;
            }
            if (_loc_2 <= 225)
            {
                return Constants.DIRECTION_SW;
            }
            if (_loc_2 <= 315)
            {
                return Constants.DIRECTION_SE;
            }
            return -1;
        }//end

        protected Road  findBestRandomRoad ()
        {
            Road _loc_3 =null ;
            Array _loc_5 =null ;
            int _loc_6 =0;
            int _loc_7 =0;
            int _loc_8 =0;
            Array _loc_1 =new Array();
            _loc_2 =Global.world.getObjectsByClass(Road );
            for(int i0 = 0; i0 < _loc_2.size(); i0++)
            {
            		_loc_3 = _loc_2.get(i0);

                _loc_5 = _loc_3.adjacentRoads;
                if (_loc_5 == null || _loc_5.length == 0)
                {
                    continue;
                }
                _loc_6 = 0;
                _loc_7 = _loc_5.length;
                _loc_8 = 0;
                while (_loc_8 < _loc_7)
                {

                    if (_loc_5.get(_loc_8) != null)
                    {
                        _loc_6++;
                    }
                    _loc_8++;
                }
                if (_loc_6 >= 3 && !this.isRoadAlreadyUsed(_loc_3.getPosition()))
                {
                    _loc_1.push(_loc_3);
                }
            }
            if (_loc_1.length > 0)
            {
                return MathUtil.randomElement(_loc_1);
            }
            _loc_4 =Global.world.citySim.roadManager.findRandomRoad ();
            return Global.world.citySim.roadManager.findRandomRoad();
        }//end

        public Vector3  findFlyingNPCDropPoint ()
        {
            Vector3 dropPos ;
            MapResource collider ;
            colliders = Global.world.getObjectsByClassAt(MapResource,getPosition());
            MapResource dropOffTile ;
            int _loc_2 =0;
            _loc_3 = colliders;
            for(int i0 = 0; i0 < colliders.size(); i0++)
            {
            		collider = colliders.get(i0);


                if (collider instanceof Sidewalk || collider instanceof Road)
                {
                    dropOffTile =Global .world .citySim .roadManager .findClosestWalkableTile (collider .getPosition (),boolean  (Object param1)
            {
                return collider != param1;
            }//end
            );
                    break;
                }
            }
            if (!dropOffTile)
            {
                dropPos = getPosition();
            }
            else
            {
                dropPos = dropOffTile.getHotspot();
            }
            return dropPos;
        }//end

        protected boolean  isRoadAlreadyUsed (Vector3 param1 )
        {
            CaptureScene _loc_2 =null ;
            Vector3 _loc_3 =null ;
            for(int i0 = 0; i0 < PreyManager.scenes.size(); i0++)
            {
            		_loc_2 = PreyManager.scenes.get(i0);

                _loc_3 = _loc_2.getPosition();
                if (_loc_3.x == param1.x && _loc_3.y == param1.y)
                {
                    return true;
                }
            }
            return false;
        }//end

         protected Array  makeDoobers (double param1 =1)
        {
            return this.m_data.getDoobers();
        }//end

        protected void  giveItemRewards (Array param1 )
        {
            String _loc_2 =null ;
            for(int i0 = 0; i0 < param1.size(); i0++)
            {
            		_loc_2 = param1.get(i0);

                Global.player.addGift(_loc_2, 0, true);
            }
            if (param1.length > 0 && m_doobersArray.length == 0)
            {
                this.showCapturedDialog(true);
            }
            return;
        }//end

    }



