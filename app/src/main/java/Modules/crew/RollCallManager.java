package Modules.crew;

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
import Classes.sim.*;
import Classes.util.*;
import Classes.virals.*;
import Display.*;
import Display.DialogUI.*;
import Display.FactoryUI.*;
import Engine.*;
import Engine.Helpers.*;
import Engine.Managers.*;
import Events.*;
import Mechanics.*;
import Mechanics.GameEventMechanics.*;
import Modules.stats.types.*;
import com.zynga.skelly.util.*;
//import flash.utils.*;

    public class RollCallManager
    {
        protected Dictionary m_activeObjects ;
        public static  String MECHANIC_TYPE ="rollCall";
        public static  String VIRAL_COLLECT ="collectReminderViral";
        public static  String VIRAL_CHECKIN_REMIND ="checkInReminderViral";
        public static  String VIRAL_CHECKIN ="checkInViral";

        public  RollCallManager ()
        {
            this.m_activeObjects = new Dictionary();
            return;
        }//end

        public void  loadObject (Object param1 )
        {
            String _loc_2 =null ;
            this.m_activeObjects = new Dictionary();
            if (param1.active)
            {
                for(int i0 = 0; i0 < param1.active.size(); i0++)
                {
                		_loc_2 = param1.active.get(i0);

                    this.m_activeObjects.put(_loc_2,  {id:param1.active.get(_loc_2), object:null});
                }
            }
            return;
        }//end

        public void  init ()
        {
            Object _loc_1 =null ;
            for(int i0 = 0; i0 < this.m_activeObjects.size(); i0++)
            {
            		_loc_1 = this.m_activeObjects.get(i0);

                _loc_1.object = Global.world.getObjectById(_loc_1.id);
            }
            return;
        }//end

        public MechanicMapResource  getActiveObject (String param1 )
        {
            MechanicMapResource _loc_2 =null ;
            if (this.m_activeObjects.get(param1) && this.m_activeObjects.get(param1).object)
            {
                _loc_2 =(MechanicMapResource) this.m_activeObjects.get(param1).object;
            }
            return _loc_2;
        }//end

        public boolean  collect (String param1 ,MechanicMapResource param2 )
        {
            Object _loc_7 =null ;
            boolean _loc_8 =false ;
            _loc_3 = (RollCallDataMechanic)MechanicManager.getInstance().getMechanicInstance(param2,"rollCall",MechanicManager.ALL)
            _loc_4 = (TieredDooberMechanic)MechanicManager.getInstance().getMechanicInstance(param2,"rollCallTieredDooberValue",MechanicManager.ALL)
            Array _loc_5 =new Array ();
            _loc_6 = _loc_3.getCrewState ();
            for(int i0 = 0; i0 < _loc_6.size(); i0++)
            {
            		_loc_7 = _loc_6.get(i0);

                if (_loc_7.checkedIn && _loc_7.purchasedCheckIn == false)
                {
                    _loc_5.push(_loc_7.zid);
                }
            }
            _loc_8 = false;
            if (_loc_3.canCollect(param1))
            {
                _loc_8 = _loc_3.collect(param1, false);
                if (!Global.isVisiting())
                {
                    this.spawnFriendNpcs(_loc_5, param2);
                }
            }
            return _loc_8;
        }//end

        public boolean  canCollect (String param1 ,MechanicMapResource param2 )
        {
            _loc_3 = (RollCallDataMechanic)MechanicManager.getInstance().getMechanicInstance(param2,"rollCall",MechanicManager.ALL)
            return _loc_3.canCollect(param1);
        }//end

        protected void  spawnFriendNpcs (Array param1 ,MapResource param2 )
        {
            _loc_3 =Global.gameSettings().getString("npcRollCallTypesFemale","NPC_Man1");
            _loc_4 =Global.gameSettings().getString("npcRollCallTypesFemale","NPC_Woman1");
            int _loc_5 =0;
            while (_loc_5 < param1.length())
            {

                if (param1.get(_loc_5) && param1.get(_loc_5).charAt(0) != "-")
                {
                    this.createNpc(param2, param1.get(_loc_5), _loc_3.split(","), _loc_4.split(","));
                }
                _loc_5++;
            }
            return;
        }//end

        protected void  createNpc (MapResource param1 ,String param2 ,Array param3 ,Array param4 )
        {
            _loc_5 = param2.indexOf("-")>=0? ("-1") : (param2);
            _loc_6 = param1.getItem ();
            if (param1.getItem())
            {
                StatsManager.sample(100, StatsCounterType.GAME_ACTIONS, _loc_6.type, "friend_npc_spawn", _loc_6.name);
            }
            String _loc_7 =null ;
            _loc_8 =Global.player.findFriendById(_loc_5 );
            if (Global.player.findFriendById(_loc_5))
            {
                _loc_7 = _loc_8.snUser.picture;
            }
            _loc_9 =             !_loc_8 || _loc_8.snUser.gender == "male" ? (param3) : (param4);
            _loc_10 =Global.world.citySim.roadManager.findClosestRoad(param1.getPosition ());
            _loc_11 =Global.world.citySim.npcManager.createWalkerByNameAtPosition(MathUtil.randomElement(_loc_9 ),_loc_10.getHotspot (),true );
            Global.world.citySim.npcManager.createWalkerByNameAtPosition(MathUtil.randomElement(_loc_9), _loc_10.getHotspot(), true).actionSelection = new AmbientNPCActionSelection(_loc_11);
            _loc_11.actionQueue.addActions(new ActionAnimationEffect(_loc_11, EffectType.POOF));
            NPCSlidePick _loc_12 =new NPCSlidePick(_loc_11 ,_loc_7 ,EmbeddedArt.trainUIPick ,EmbeddedArt.friendReplaySlide );
            _loc_12.clickCallback = Curry.curry(this.onNpcClick, _loc_11, _loc_5, param1);
            _loc_12.setPositionRenderListener(true);
            _loc_12.customTilePositionOffset = new Vector3(3, 3, 0);
            _loc_11.slidePick = _loc_12;
            _loc_11.showSlidePick();
            _loc_11.playActionCallback = Curry.curry(this.onNpcClick, _loc_11, _loc_5, param1);
            return;
        }//end

        protected void  onNpcClick (NPC param1 ,String param2 ,MapResource param3 )
        {
            npc = param1;
            friendId = param2;
            building = param3;
            if (!friendId || friendId.charAt(0) == "-")
            {
                return;
            }
            item = building.getItem();
            if (item)
            {
                StatsManager.sample(100, StatsCounterType.GAME_ACTIONS, item.type, "friend_npc_click", item.name);
            }
            String iconUrl ;
            friendLocName = Global.player.getFriendFirstName(Player.FAKE_USER_ID_STRING);
            friend = Global.player.findFriendById(friendId);
            if (friend)
            {
                iconUrl = friend.snUser.picture;
                friendLocName = Global.player.getFriendFirstName(friendId);
            }
            customOk = TextFieldUtil.formatSmallCapsString(ZLoc.t("Dialogs","RollCallCollectionReminder_button",{friendNamefriendLocName}));
            message = ZLoc.t("Dialogs","RollCallCollectionReminder_message",{friendNamefriendLocName});
            UI.displayViralShareMessage(ViralType.ROLL_CALL_COLLECTION_REMINDER, message, GenericDialogView.TYPE_CUSTOM_OK, Curry.curry(this.onCollectionReminderClose, friendId, building), "RollCallCollectionReminder", false, iconUrl, "RollCallCollectionReminder", GenericDialogView.ICON_POS_LEFT, customOk, false);
            if (npc)
            {
                npc.hideSlidePick(0.5);
                npc.getStateMachine().removeAllStates();
                npc .getStateMachine ().addActions (new ActionTween (npc ,ActionTween .TO ,0.5,{0alpha }),new ActionFn (npc ,Curry .curry (void  (NPC param11 )
            {
                Global.world.citySim.npcManager.removeWalker(param11);
                return;
            }//end
            , npc)));
            }
            return;
        }//end

        protected void  onCollectionReminderClose (String param1 ,MechanicMapResource param2 ,GenericPopupEvent param3 )
        {
            if (param3.button == GenericDialogView.YES)
            {
                Function _loc_1 =RollCallManager.postFeed(VIRAL_COLLECT ,param2 );
                _loc_1({title:param2.getCrewPositionName(param1), buildingName:param2.getItemFriendlyName()}, param1, {action:"collectionReminder", bid:param2.getId()});
            }
            return;
        }//end

        public static Function  postFeed (String param1 ,MechanicMapResource param2 )
        {
            MechanicConfigData config ;
            viralType = param1;
            building = param2;
            config = building.getMechanicConfig(MECHANIC_TYPE, "all");
            return boolean  (Object param1 ,String param2 ,Object param3 =null ,String param4 =null ,Function param5 =null )
            {
                if (config && config.params.get(viralType))
                {
                    return Global.world.viralMgr.sendFeedorAutoPublish(config.params.get(viralType), MECHANIC_TYPE, param1, param2, param3, param4, true, param5);
                }
                return false;
            }//end
            ;
        }//end

        public static void  debugSample (String param1 ="",String param2 ="",String param3 ="",String param4 ="",int param5 =1)
        {
            if (RuntimeVariableManager.getBoolean("ROLL_CALL_DEBUG_ENABLED", false))
            {
                StatsManager.sample(100, StatsCounterType.DEBUG, "roll_call", param1, param2, param3, param4, param5);
            }
            return;
        }//end

    }



