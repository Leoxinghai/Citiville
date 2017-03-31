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
import GameMode.*;
import Mechanics.*;
import Mechanics.GameEventMechanics.*;
import Mechanics.GameMechanicInterfaces.*;
import Modules.bandits.*;
import Modules.storage.*;
//import flash.utils.*;

    public class Requirements implements IUnlock
    {
        protected Dictionary m_entries ;
        protected String m_name ;
        public static  String REQUIREMENT_COINS ="coins";
        public static  String REQUIREMENT_CASH ="cash";
        public static  String REQUIREMENT_XP ="xp";
        public static  String REQUIREMENT_LEVEL ="level";
        public static  String REQUIREMENT_POPULATION ="population";
        public static  String REQUIREMENT_EXPERIMENT ="experiment";
        public static  String REQUIREMENT_ITEM_STORAGE ="itemStorage";
        public static  String REQUIREMENT_CREW ="crew";
        public static  String REQUIREMENT_QUEST_NOT_ACTIVE ="questNotActive";
        public static  String REQUIREMENT_UPGRADE_ACTIONS ="upgrade_actions";
        public static  String REQUIREMENT_SPECIAL_PREY ="special_prey";
        public static  String REQUIREMENT_ROLL_CALL ="rollCallActive";
        public static  String REQUIREMENT_COUNT_WORLD_OBJECT_BY_NAME ="countWorldObjectByName";
        public static  String REQUIREMENT_SEEN_FLAG ="seenFlag";
        public static  String REQUIREMENT_QUEST_FLAG ="questFlag";
        public static  String REQUIREMENT_CAN_ADD_POPULATION ="canAddPopulation";
        public static  String REQUIREMENT_LICENSE ="license";
        private static Object unlockTypeMap ={level seenFlag "level","seenflag"questFlag ,"questflag",countWorldObjectByName "object_count","can_add_population","locked"};

        public  Requirements (XML param1 )
        {
            XML _loc_2 =null ;
            String _loc_3 =null ;
            Dictionary _loc_4 =null ;
            XML _loc_5 =null ;
            this.m_entries = new Dictionary();
            this.m_name = String(param1.@name);
            for(int i0 = 0; i0 < Global.experimentManager.filterXmlByExperiment(param1.requirement).size(); i0++)
            {
            	_loc_2 = Global.experimentManager.filterXmlByExperiment(param1.requirement).get(i0);

                _loc_3 = String(_loc_2.@type);
                _loc_4 = new Dictionary();
                for(int i0 = 0; i0 < _loc_2.attributes().size(); i0++)
                {
                	_loc_5 = _loc_2.attributes().get(i0);

                    _loc_4.put(String(_loc_5.name()),  String(_loc_5));
                }
                m_entries.put(_loc_3, _loc_4);
            }
            return;
        }//end

        public String  name ()
        {
            return this.m_name;
        }//end

        public String  getRequirementValue (String param1 )
        {
            String _loc_2 =null ;
            if (this.m_entries.hasOwnProperty(param1) && this.m_entries.get(param1).hasOwnProperty("value"))
            {
                _loc_2 = this.m_entries.get(param1).get("value");
            }
            return _loc_2;
        }//end

        public boolean  checkRequirements (MapResource param1 ,String param2 )
        {
            String _loc_3 =null ;
            boolean _loc_4 =false ;
            for(int i0 = 0; i0 < this.m_entries.size(); i0++)
            {
            		_loc_3 = this.m_entries.get(i0);

                _loc_4 = this.checkRequirement(_loc_3, this.m_entries.get(_loc_3), param1, param2);
                if (!_loc_4)
                {
                    return false;
                }
            }
            return true;
        }//end

        protected boolean  checkRequirement (String param1 ,Dictionary param2 ,MapResource param3 =null ,String param4 =null )
        {
            String _loc_5 =null ;
            Array _loc_8 =null ;
            int _loc_9 =0;
            boolean _loc_10 =false ;
            boolean _loc_11 =false ;
            IGameMechanic _loc_12 =null ;
            StorageType _loc_13 =null ;
            String _loc_14 =null ;
            BaseStorageUnit _loc_15 =null ;
            MapResource _loc_16 =null ;
            Array _loc_17 =null ;
            MapResource _loc_18 =null ;
            String _loc_19 =null ;
            RollCallDataMechanic _loc_20 =null ;
            boolean _loc_21 =false ;
            boolean _loc_22 =false ;
            boolean _loc_23 =false ;
            boolean _loc_24 =false ;
            _loc_5 = param2.get("value");
            _loc_6 = param2.get("license") ;
            if (param2.get("license") && Global.player.checkLicense(_loc_6))
            {
                return true;
            }
            String _loc_7 =null ;
            switch(param1)
            {
                case REQUIREMENT_COINS:
                {
                    return Global.player.gold >= int(_loc_5);
                }
                case REQUIREMENT_CASH:
                {
                    return Global.player.cash >= int(_loc_5);
                }
                case REQUIREMENT_XP:
                {
                    return Global.player.xp >= int(_loc_5);
                }
                case REQUIREMENT_LEVEL:
                {
                    return Global.player.level >= int(_loc_5);
                }
                case REQUIREMENT_POPULATION:
                {
                    if (param3)
                    {
                        _loc_7 = param3.getItem().populationType;
                    }
                    return Global.world.citySim.getPopulation(_loc_7) >= int(_loc_5);
                }
                case REQUIREMENT_EXPERIMENT:
                {
                    return Global.experimentManager.getVariant(_loc_5) != 0;
                }
                case REQUIREMENT_ITEM_STORAGE:
                {
                    if (param3)
                    {
                        _loc_13 = ((ItemStorage)param3).getStorageType();
                        _loc_14 = ((ItemStorage)param3).getStorageKey();
                        _loc_15 = Global.player.storageComponent.getStorageUnit(_loc_13, _loc_14);
                        return _loc_15 && _loc_15.size >= int(_loc_5);
                    }
                    return false;
                }
                case REQUIREMENT_CREW:
                {
                    if (param3)
                    {
                        return Global.crews.getCrewCountByObject(param3) >= int(_loc_5);
                    }
                    return false;
                }
                case REQUIREMENT_QUEST_NOT_ACTIVE:
                {
                    if (Global.questManager == null)
                    {
                        return false;
                    }
                    return !Global.questManager.isQuestActive(_loc_5);
                }
                case REQUIREMENT_UPGRADE_ACTIONS:
                {
                    if (param3 && param3.upgradeActions)
                    {
                        return param3.upgradeActions.getTotal() >= int(_loc_5);
                    }
                    return false;
                }
                case REQUIREMENT_COUNT_WORLD_OBJECT_BY_NAME:
                {
                    _loc_8 = param2.get("itemName").split(",");
                    _loc_9 = 0;
                    if (!Global.isVisiting() && !Global.isTransitioningWorld)
                    {
                        _loc_16 = null;
                        if (Global.world.getTopGameMode() instanceof GMPlaceMapResource)
                        {
                            _loc_16 = ((GMPlaceMapResource)Global.world.getTopGameMode()).displayedMapResource;
                        }
                        _loc_17 = Global.world.getObjectsByNames(_loc_8);
                        for(int i0 = 0; i0 < _loc_17.size(); i0++)
                        {
                        		_loc_18 = _loc_17.get(i0);

                            if (!_loc_16 || _loc_16.getId() == _loc_18.getId())
                            {
                                _loc_9++;
                            }
                        }
                    }
                    return _loc_9 >= int(_loc_5);
                }
                case REQUIREMENT_SEEN_FLAG:
                case REQUIREMENT_QUEST_FLAG:
                {
                    return Global.player.getSeenFlag(_loc_5) || Global.player.getSeenSessionFlag(_loc_5);
                }
                case REQUIREMENT_CAN_ADD_POPULATION:
                {
                    if (param3)
                    {
                        _loc_7 = param3.getItem().populationType;
                    }
                    return Global.world.citySim.getPopulation(_loc_7) + int(_loc_5) <= Global.world.citySim.getPopulationCap(_loc_7);
                }
                case REQUIREMENT_LICENSE:
                {
                    _loc_10 = _loc_5 == "true";
                    return Global.player.checkLicense(_loc_6) == _loc_10;
                }
                case REQUIREMENT_SPECIAL_PREY:
                {
                    if (param3 != null)
                    {
                        _loc_19 = Global.gameSettings().getHubGroupIdForItemName(param3.getItemName());
                        return PreyManager.getNumPreyCaptured(_loc_19) >= int(_loc_5);
                    }
                    return false;
                }
                case REQUIREMENT_ROLL_CALL:
                {
                    _loc_11 = _loc_5 != "false";
                    if (!param3)
                    {
                        return !_loc_11;
                    }
                    _loc_12 = MechanicManager.getInstance().getMechanicInstance((IMechanicUser)param3, "rollCall", "all");
                    if (_loc_12 && _loc_12 instanceof RollCallDataMechanic)
                    {
                        _loc_20 =(RollCallDataMechanic) _loc_12;
                        _loc_21 = Global.player.level < _loc_20.requiredLevel;
                        if (!_loc_21)
                        {
                            _loc_22 = _loc_20.isActiveObject() && _loc_20.canPerformRollCall();
                            _loc_23 = _loc_20.getState() == RollCallDataMechanic.STATE_IN_PROGRESS;
                            _loc_24 = _loc_20.getState() == RollCallDataMechanic.STATE_COMPLETE && !_loc_20.hasCollected(Global.player.uid);
                            return (_loc_22 || _loc_23 || _loc_24) == _loc_11;
                        }
                    }
                    return !_loc_11;
                }
                default:
                {
                    throw new Error("Unknown requirement " + param1);
                    break;
                }
            }
        }//end

        public boolean  isRequirementTypeDefined (String param1 )
        {
            if (this.m_entries.get(param1) != null)
            {
                return true;
            }
            return false;
        }//end

        public String  unlockType ()
        {
            String _loc_1 =null ;
            for(int i0 = 0; i0 < this.m_entries.size(); i0++)
            {
            		_loc_1 = this.m_entries.get(i0);

                if (!this.checkRequirement(_loc_1, this.m_entries.get(_loc_1)))
                {
                    return unlockTypeMap.get(_loc_1) ? (unlockTypeMap.get(_loc_1)) : (Item.UNLOCK_LOCKED);
                }
            }
            return Item.UNLOCK_LEVEL;
        }//end

        public int  requiredLevel ()
        {
            return int(this.getRequirementValue(REQUIREMENT_LEVEL));
        }//end

        public String  requiredSeenFlag ()
        {
            _loc_1 = this.getRequirementValue(REQUIREMENT_SEEN_FLAG);
            return _loc_1 ? (_loc_1) : ("");
        }//end

        public int  requiredNeighbors ()
        {
            return 0;
        }//end

        public String  requiredQuestFlag ()
        {
            _loc_1 = this.getRequirementValue(REQUIREMENT_QUEST_FLAG);
            return _loc_1 ? (_loc_1) : ("");
        }//end

        public int  requiredPopulation ()
        {
            return int(this.getRequirementValue(REQUIREMENT_POPULATION));
        }//end

        public int  requiredCanAddPopulation ()
        {
            return int(this.getRequirementValue(REQUIREMENT_CAN_ADD_POPULATION));
        }//end

        public Array  requiredObjects ()
        {
            String _loc_2 =null ;
            Array _loc_1 =new Array();
            if (this.m_entries.get(REQUIREMENT_COUNT_WORLD_OBJECT_BY_NAME))
            {
                _loc_2 = this.m_entries.get(REQUIREMENT_COUNT_WORLD_OBJECT_BY_NAME).get("itemName");
                if (_loc_2)
                {
                    _loc_1 = _loc_2.split(",");
                }
            }
            return _loc_1;
        }//end

        public int  requiredObjectsCount ()
        {
            return int(this.getRequirementValue(REQUIREMENT_COUNT_WORLD_OBJECT_BY_NAME));
        }//end

    }




