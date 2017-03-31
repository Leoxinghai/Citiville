package Modules.peepgroups;

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
import Classes.util.*;
import Engine.Helpers.*;
import Transactions.*;
//import flash.utils.*;

    public class PeepGroup
    {
        public Array m_peeps ;
        public Dictionary m_peepType ;
        public double m_routineState =0;
        public Dictionary m_peepTriggers ;
        public Dictionary m_peepStates ;
        public Vector3 m_entry ;
        public Vector3 m_root ;
        public double m_checkInCount =0;
        public double m_groupAmt =2;
        public Array m_danceRoutine ;
        public static  double GROUP_SIZE =13;
        public static  double MJ_WHISTLE_DELAY =1;
        public static  double MJ_DANCE_DELAY =0.5;

        public  PeepGroup (MapResource param1 )
        {
            this.m_peeps = new Array();
            this.m_peepType = new Dictionary();
            this.m_peepTriggers = new Dictionary();
            this.m_peepStates = new Dictionary();
            this.m_danceRoutine = new Array(this.peepTriangle);
            this.m_entry = param1.getRoadSidePosition(param1.getRoadSidewalkSide());
            this.m_root = param1.findValidGroupPosition();
            _loc_2 =Global.world.citySim.npcManager.createWalkerByNameAtPosition("NPC_Ape",this.m_entry ,false );
            _loc_2.actionSelection = new PeepGroupSelection(_loc_2, this);
            this.m_peeps.push(_loc_2);
            this.m_peepTriggers.put(_loc_2,  this.blowWhistle);
            this.m_peepStates.put(_loc_2,  "transition");
            _loc_2.actionQueue.addActions(new ActionNavigateBeeline(_loc_2, this.m_root.add(new Vector3(-1.1, 0, 0))), new ActionPlayAnimation(_loc_2, "freeze", MJ_WHISTLE_DELAY));
            this.m_peepType.put(_loc_2,  "leader");
            GameTransactionManager.addTransaction(new TSawFlashMob());
            return;
        }//end

        public Vector3  peepCircle (double param1 )
        {
            double _loc_2 =0;
            double _loc_3 =0;
            if (param1 != 0)
            {
                _loc_2 = 1;
                _loc_3 = param1 / (GROUP_SIZE - 1) * Math.PI * 2;
                return new Vector3(_loc_2 * (Math.cos(_loc_3) - Math.sin(_loc_3)), _loc_2 * (Math.sin(_loc_3) + Math.cos(_loc_3)), 0);
            }
            return new Vector3(0, 0, 0);
        }//end

        public Vector3  peepTriangle (double param1 )
        {
            double _loc_2 =0;
            double _loc_3 =0;
            if (param1 == 0)
            {
                return new Vector3(-1.3, 0, 0);
            }
            if (param1 == 1)
            {
                return new Vector3(-1, 0, 0);
            }
            if (param1 < 8)
            {
                _loc_2 = Math.ceil(param1 / 2) / 4;
                _loc_3 = param1 % 2;
                return new Vector3(_loc_2 * 2 - 1, _loc_2 * _loc_3 * 2 - _loc_2, 0);
            }
            return new Vector3(1, (param1 - 8) / 4 * 2 - 1, 0);
        }//end

        public void  blowWhistle (NPC param1 )
        {
            double _loc_2 =0;
            while (_loc_2 < (GROUP_SIZE - 1))
            {

                param1 = Global.world.citySim.npcManager.createWalkerByNameAtPosition("NPC_Soldier", this.m_entry, false);
                param1.actionSelection = new PeepGroupSelection(param1, this);
                this.m_peeps.push(param1);
                this.m_peepStates.put(param1,  "ready");
                this.m_peepType.put(param1,  "dancer");
                _loc_2 = _loc_2 + 1;
            }
            return;
        }//end

        public Array  stateChange (NPC param1 ,String param2 )
        {
            boolean _loc_3 =false ;
            NPC _loc_4 =null ;
            this.m_checkInCount++;
            if (this.m_peepTriggers.get(param1))
            {
                _loc_5 = this.m_peepTriggers ;
                _loc_5.m_peepTriggers.get(param1)(param1);
                delete this.m_peepTriggers.get(param1);
            }
            if (this.m_checkInCount == GROUP_SIZE)
            {
                this.m_checkInCount = 0;
                switch(param2)
                {
                    case "dance":
                    {
                        this.m_routineState++;
                        _loc_3 = this.m_routineState >= this.m_danceRoutine.length;
                        for(int i0 = 0; i0 < this.m_peeps.size(); i0++) 
                        {
                        		_loc_4 = this.m_peeps.get(i0);

                            if (!_loc_3)
                            {
                                this.m_peepStates.put(_loc_4,  "ready");
                            }
                            else
                            {
                                this.m_peepStates.put(_loc_4,  "finished");
                            }
                            _loc_4.actionQueue.removeAllStates();
                            _loc_4.conditionallyReattach();
                            _loc_4.actionQueue.addActions(new ActionPlayAnimation(_loc_4, "freeze", MJ_DANCE_DELAY), new ActionPlayAnimation(_loc_4, "freeze", 5));
                        }
                        this.m_peeps.get(0).actionQueue.removeAllStates();
                        this.m_peeps.get(0).actionQueue.addActions(new ActionDie(this.m_peeps.get(0)));
                        return null;
                    }
                    default:
                    {
                        break;
                    }
                }
            }
            else
            {
                return getAnim(param1, "freeze", 100);
            }
            return null;
        }//end

        public Array  getNextActions (NPC param1 )
        {
            ActionNavigateRandom _loc_2 =null ;
            switch(this.m_peepStates.get(param1))
            {
                case "ready":
                {
                    this.m_peepStates.put(param1,  "transition");
                    _loc_3 = this.m_danceRoutine ;
                    return .get(new ActionNavigateBeeline(param1, this.m_root.add(this.m_danceRoutine.get(this.m_routineState)(this.m_peeps.indexOf(param1)))));
                }
                case "transition":
                {
                    this.m_peepStates.put(param1,  "wait");
                    return this.stateChange(param1, "dance");
                }
                case "finished":
                {
                    this.m_peepStates.put(param1,  "done");
                    _loc_2 = new ActionNavigateRandom(param1);
                    _loc_2.setTimeout(5);
                    return .get(_loc_2, new ActionDie(param1));
                }
                default:
                {
                    break;
                }
            }
            return .get(new ActionNavigateRandom(param1));
        }//end

        public static Array  getAnim (NPC param1 ,String param2 ,double param3 )
        {
            return .get(new ActionPlayAnimation(param1, param2, param3));
        }//end

    }



