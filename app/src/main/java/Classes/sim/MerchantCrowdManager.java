package Classes.sim;

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
import Classes.Desires.*;
import Classes.actions.*;
import Engine.*;
import Engine.Classes.*;
import Engine.Helpers.*;
import Modules.cars.*;
import com.greensock.*;
import com.zynga.skelly.util.*;
//import flash.display.*;
//import flash.geom.*;

    public class MerchantCrowdManager
    {
        protected int m_maxNpcWaveRadius =0;
        protected double m_secondsTillNextConsumption =0;
        protected ConsumerShockwave m_wave =null ;
        protected DisplayObject m_preview =null ;
        protected IMerchant m_merchant =null ;
        protected int m_npcsCaptured =0;
        protected boolean m_peepConsumeEnabled =false ;
        protected int m_maxNpcsCaptured =0;
        protected double m_peepSpawnDelaySec =2.14748e +009;
        protected double m_peepConsumeDelaySec =2.14748e +009;
public static  double MAX_BEELINE_DISTANCE =4;
public static  Vector3 V_UP =new Vector3(0,0,1);

        public  MerchantCrowdManager (IMerchant param1 )
        {
            this.m_merchant = param1;
            this.loadShockWaveSettings(((ItemInstance)this.m_merchant).getItemName());
            this.m_peepSpawnDelaySec = Global.gameSettings().getInt("npcSpawnTimeSec", 1);
            this.m_peepConsumeDelaySec = Global.gameSettings().getInt("npcConsumeTimeSec", 5);
            return;
        }//end

        public void  loadShockWaveSettings (String param1 )
        {
            _loc_2 =Global.gameSettings().getItemByName(param1 );
            if (_loc_2 && _loc_2.shockwaveXml.length())
            {
                this.m_maxNpcWaveRadius = int(_loc_2.shockwaveXml.@radius);
                this.m_maxNpcsCaptured = int(_loc_2.shockwaveXml.@captured);
            }
            return;
        }//end

        public int  npcsCaptured ()
        {
            return this.m_npcsCaptured;
        }//end

        public void  cleanup ()
        {
            this.m_merchant = null;
            if (this.m_wave)
            {
                this.m_wave.stop();
                this.m_wave = null;
            }
            if (this.m_preview && this.m_preview.parent)
            {
                this.m_preview.parent.removeChild(this.m_preview);
                this.m_preview = null;
            }
            return;
        }//end

        public void  onUpdate (double param1 )
        {
            this.m_secondsTillNextConsumption = this.m_secondsTillNextConsumption - param1;
            if (this.m_secondsTillNextConsumption <= 0)
            {
                if (this.m_peepConsumeEnabled)
                {
                    this.consumePeeps(1);
                }
                else
                {
                    this.m_secondsTillNextConsumption = 1;
                }
            }
            return;
        }//end

        public void  startWavePreview ()
        {
            double _loc_1 =0;
            Vector3 _loc_2 =null ;
            Vector3 _loc_3 =null ;
            Vector3 _loc_4 =null ;
            Point _loc_5 =null ;
            Sprite _loc_6 =null ;
            if (!this.m_preview)
            {
                this.m_preview = new EmbeddedArt.businessShockwavePreview();
                _loc_1 = Constants.TILE_WIDTH * ConsumerShockwave.ORTHO_FACTOR / 200;
                _loc_7 = _loc_1*this.m_maxNpcWaveRadius;
                this.m_preview.scaleY = _loc_1 * this.m_maxNpcWaveRadius;
                this.m_preview.scaleX = _loc_7;
                _loc_2 = this.m_merchant.getSize();
                _loc_3 = this.m_merchant.getPosition();
                _loc_4 = new Vector3(_loc_3.x + _loc_2.x / 2, _loc_3.y + _loc_2.y / 2);
                _loc_5 = IsoMath.tilePosToPixelPos(_loc_4.x, _loc_4.y);
                this.m_preview.x = _loc_5.x;
                this.m_preview.y = _loc_5.y;
                _loc_6 =(Sprite) Global.world.getObjectLayerByName("road").getDisplayObject();
                _loc_6.addChild(this.m_preview);
                this.m_preview.transform.colorTransform.color = 16777215;
                TweenLite.from(this.m_preview, 0.2, {scaleX:0, scaleY:0});
            }
            return;
        }//end

        public void  endWavePreview ()
        {
            if (this.m_preview)
            {
                TweenLite .to (this .m_preview ,0.3,{0alpha ,Curry onComplete .curry (void  (DisplayObject param1 )
            {
                if (param1 && param1.parent)
                {
                    param1.parent.removeChild(param1);
                }
                return;
            }//end
            , this.m_preview)});
                this.m_preview = null;
            }
            return;
        }//end

        public void  startCollecting ()
        {
            this.m_peepConsumeEnabled = true;
            this.makeNpcCrowdCheer();
            return;
        }//end

        public void  stopCollecting ()
        {
            this.m_peepConsumeEnabled = false;
            return;
        }//end

        public void  performShockwave ()
        {
            _loc_1 = this.m_merchant.getMapResource ();
            _loc_2 =Global.gameSettings().getNumber("bizWaveVelocity",1);
            this.m_wave = new ConsumerShockwave(_loc_1, _loc_2, this.m_maxNpcWaveRadius, this.onNpcConsumed, this.onWaveFinished);
            this.m_wave.start();
            return;
        }//end

        public void  performFranchiseShockwave ()
        {
            _loc_1 = this.m_merchant.getMapResource ();
            _loc_2 =Global.gameSettings().getNumber("bizWaveVelocity",1);
            this.m_wave = new FranchiseShockwave(_loc_1, _loc_2, this.m_maxNpcWaveRadius, this.onNpcConsumedNoEnter, this.onWaveFinished);
            this.m_wave.start();
            return;
        }//end

        public Array  findNpcsInRange ()
        {
            Vector3 center ;
            size = this.m_merchant.getSize();
            corner = this.m_merchant.getPosition();
            center = new Vector3(corner.x + size.x / 2, corner.y + size.y / 2);
            predicate = function(param1WorldObject)
            {
                _loc_2 = param1as NPC ;
                return _loc_2 && _loc_2.getPositionNoClone().subtract(center).length() <= m_maxNpcWaveRadius;
            }//end
            ;
            return Global.world.getObjectsByPredicate(predicate);
        }//end

        protected void  consumePeeps (int param1 )
        {
            Peep _loc_5 =null ;
            _loc_2 = this.m_merchant.getHotspot ();
            _loc_3 = Math.min(this.m_merchant.getSize ().x ,this.m_merchant.getSize ().y );
            _loc_4 = this.findPeepsByLocation(_loc_2 ,_loc_3 ,param1 );
            for(int i0 = 0; i0 < _loc_4.size(); i0++)
            {
            	_loc_5 = _loc_4.get(i0);

                if (!_loc_5.isFranchiseFreebie && _loc_5.isFreeAgent)
                {
                    this.makeNpcEnterMerchant(_loc_5);
                }
            }
            this.m_secondsTillNextConsumption = this.m_peepConsumeDelaySec;
            if (_loc_4.length == 0)
            {
                this.m_secondsTillNextConsumption = Math.random();
            }
            return;
        }//end

        protected Array  findPeepsByLocation (Vector3 param1 ,double param2 ,int param3 =2.14748e +009)
        {
            Peep peep ;
            Object wrap ;
            double distance ;
            point = param1;
            radius = param2;
            limit = param3;
            predicate = function(param1Peep)
            {
                return param1.isFreeAgent && param1.getPositionNoClone().subtract(point).length() <= radius;
            }//end
            ;
            peeps = Global.world.citySim.npcManager.findAllPeepsByPredicate(predicate);
            Array wraps =new Array ();
            int _loc_5 =0;
            _loc_6 = peeps;
            for(int i0 = 0; i0 < peeps.size(); i0++)
            {
            	peep = peeps.get(i0);


                distance = peep.getPositionNoClone().subtract(point).length();
                wraps.push({distance:distance, peep:peep});
            }
            wraps.sortOn("distance");
            if (wraps.length > limit)
            {
                wraps.splice(limit);
            }
            peeps = new Array();
            _loc_5 = 0;
            _loc_6 = wraps;
            for(int i0 = 0; i0 < wraps.size(); i0++)
            {
            		wrap = wraps.get(i0);


                peeps.push(wrap.peep);
            }
            return peeps;
        }//end

        protected void  makeNpcCrowdCheer ()
        {
            Peep _loc_2 =null ;
            _loc_1 = this.findPeepsByLocation(this.m_merchant.getHotspot (),6);
            for(int i0 = 0; i0 < _loc_1.size(); i0++)
            {
            	_loc_2 = _loc_1.get(i0);

                if (_loc_2.isFreeAgent && !_loc_2.isFranchiseFreebie)
                {
                    _loc_2.getStateMachine().removeAllStates();
                    _loc_2.getStateMachine().addActions(new ActionEnableFreedom(_loc_2, false), new ActionPlayAnimation(_loc_2, "jump", 0.7), new ActionEnableFreedom(_loc_2, true));
                }
            }
            return;
        }//end

        public void  makeNpcFailEnter (NPC param1 )
        {
            param1.showDeclineEntranceBubble();
            param1.getStateMachine().removeAllStates();
            param1.getStateMachine().addActions(new ActionEnableFreedom(param1, false));
            return;
        }//end

        public void  makeNpcEnterMerchant (NPC param1 )
        {
            Peep peep ;
            npc = param1;
            isNotPeep =             !(npc instanceof Peep);
            peep =(Peep) npc;
            if (peep && peep.isFranchiseFreebie)
            {
                return;
            }
            hotspot = this.m_merchant.getHotspot();
            distance = npc.getPositionNoClone().subtract(hotspot).length();
            beeline = distance<MAX_BEELINE_DISTANCE ;
            time = Global.gameSettings().getInt("npcVisitTimeSec",1);
            npc.getStateMachine().removeAllStates();
            npc.getStateMachine().addActions(new ActionEnableFreedom(npc, false));
            if (npc.getItemName() == "NPC_mayor")
            {
                npc.getStateMachine().addActions(new ActionPlayAnimation(npc, "cheer", 3));
            }
            else if (!npc.isVehicle)
            {
                if (beeline)
                {
                    npc.getStateMachine().addActions(new ActionNavigateHotspots(npc, this.m_merchant.getMapResource()));
                }
                else
                {
                    npc.getStateMachine().addActions(new ActionNavigate(npc, this.m_merchant.getMapResource(), null).setPathType(RoadManager.PATH_FULL));
                }
            }
            if (isNotPeep)
            {
                npc.getStateMachine().addActions(new ActionDie(npc));
                return;
            }
            npc .getStateMachine ().addActions (new ActionFn (npc ,void  ()
            {
                _loc_1 = null;
                if (!Global.isVisiting())
                {
                    m_merchant.performVisit(peep);
                    m_merchant.performVisitAnimation(peep);
                    _loc_1 =(MapResource) m_merchant;
                    if (_loc_1)
                    {
                        npc.onEnterMapResource(_loc_1);
                    }
                }
                return;
            }//end
            ),new ActionTween (npc ,ActionTween .TO ,0.5,{0alpha }),new ActionPause (npc ,time ),new ActionFn (npc ,void  ()
            {
                npc.animation = "static";
                return;
            }//end
            ));
            this.makeNpcExitMerchant(npc);
            return;
        }//end

        public void  makeNpcExitMerchant (NPC param1 )
        {
            Peep peep ;
            npc = param1;
            isNotPeep =             !(npc instanceof Peep);
            peep =(Peep) npc;
            if (peep.spawnSource == Peep.SOURCE_TOURBUS)
            {
                npc.getStateMachine().addActions(new ActionDie(npc));
                return;
            }
            if (!this.m_merchant.isAttached())
            {
                npc.getStateMachine().removeAllStates();
                npc.getStateMachine().addActions(new ActionPlayAnimation(npc, "idle", 10), new ActionPlayAnimation(npc, "wave", 5), new ActionDie(npc));
                return;
            }
            npc .getStateMachine ().addActions (new ActionTween (npc ,ActionTween .TO ,0.5,{1alpha }),new ActionEnableFreedom (npc ,isNotPeep ),new ActionFn (npc ,void  ()
            {
                Object goToHotel;
                Object roll;
                Object hotelChance;
                Object hotelDesire;
                if (peep)
                {
                    peep.lastMerchantId = m_merchant.getId();
                }
                mapResource = (MapResource)m_merchant
                if (mapResource)
                {
                    npc.onExitMapResource(mapResource);
                }
                smile = Global.getAssetURL("assets/citysim/happy.png");
                desirePeep = (DesirePeep)npc
                if (desirePeep != null && desirePeep.stubborn == false && (Global.world.citySim.resortManager.businessInResortById(m_merchant.getId()) == true || desirePeep.spawnSource == Peep.SOURCE_CRUISESHIP))
                {
                    goToHotel;
                    roll = int(Math.random() * 100);
                    hotelChance = Global.gameSettings().getNumber("HotelChance_" + desirePeep.spawnSource, -1);
                    if (roll <= hotelChance)
                    {
                        npc .getStateMachine ().addActions (new ActionFn (npc ,void  ()
                {
                    npc.showGoingToHotelFeedbackBubble();
                    return;
                }//end
                ));
                        Global.world.citySim.resortManager.checkNPCCheckInTutorial(npc);
                        hotelDesire = new DGoHotel(desirePeep);
                        desirePeep.insertDesireBeforeMultiplePivots(hotelDesire, [DesireTypes.GO_HOME, DesireTypes.GO_CRUISE_SHIP]);
                    }
                    else
                    {
                        npc.showSmileFeedbackBubble();
                    }
                }
                else
                {
                    npc.showSmileFeedbackBubble();
                }
                return;
            }//end
            ));
            if (npc instanceof Vehicle)
            {
                npc.forceUpdateArrowWithCustomIcon("coin1supersmall");
                npc .playActionCallback =Curry .curry (void  (NPC param11 )
            {
                param11.hideStagePickEffect();
                param11.playActionCallback = null;
                CarManager.instance.processCarHarvest(param11);
                return;
            }//end
            , npc);
            }
            return;
        }//end

        protected void  onNpcConsumed (Array param1 )
        {
            NPC _loc_2 =null ;
            for each (_loc_2 in param1)
            {

                if (this.m_npcsCaptured >= this.m_maxNpcsCaptured)
                {
                    break;
                }
                this.makeNpcEnterMerchant(_loc_2);
                this.m_npcsCaptured++;
            }
            return;
        }//end

        protected void  onNpcConsumedNoEnter (Array param1 )
        {
            NPC _loc_2 =null ;
            for each (_loc_2 in param1)
            {

                if (this.m_npcsCaptured >= this.m_maxNpcsCaptured)
                {
                    break;
                }
                this.m_npcsCaptured++;
            }
            return;
        }//end

        protected void  onWaveFinished ()
        {
            this.m_wave.stop();
            this.m_wave = null;
            this.m_merchant.onWaveFinished();
            this.m_npcsCaptured = 0;
            return;
        }//end

        public void  releasePeeps ()
        {
            return;
        }//end

    }



