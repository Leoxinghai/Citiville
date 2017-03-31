package Modules.friendUGC;

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
import Display.FactoryUI.*;
import Engine.Helpers.*;
import com.adobe.utils.*;
//import flash.utils.*;
import Classes.sim.*;

    public class FriendNPCManager implements IGameWorldUpdateObserver
    {
        protected GameWorld m_world ;
        protected Array m_friendNPCData ;
        protected Dictionary m_friendNPCs ;
        protected double m_friendNPCReleaseDelta =0;
public static  double FRIEND_NPC_RELEASE_DELAY =4;
public static  int FRIEND_NPC_MAX =5;

        public  FriendNPCManager (GameWorld param1 )
        {
            this.m_friendNPCData = new Array();
            this.m_friendNPCs = new Dictionary();
            this.m_world = param1;
            param1.addObserver(this);
            return;
        }//end

        public void  initialize ()
        {
            return;
        }//end

        public void  cleanUp ()
        {
            this.m_friendNPCReleaseDelta = 0;
            this.m_friendNPCData = new Array();
            return;
        }//end

        public void  onGameLoaded (Object param1 )
        {
            return;
        }//end

        public void  onResourceChange (MapResource param1 ,Vector3 param2 ,Vector3 param3 )
        {
            return;
        }//end

        public void  registerFriendData (Object param1 ,MapResource param2 )
        {
            this.m_friendNPCData.push({payload:param1, source:param2, spawned:false});
            return;
        }//end

        public void  clearAllFriendNPCs ()
        {
            Array _loc_1 =null ;
            NPC _loc_2 =null ;
            for(int i0 = 0; i0 < DictionaryUtil.getValues(this.m_friendNPCs).size(); i0++)
            {
            		_loc_1 = DictionaryUtil.getValues(this.m_friendNPCs).get(i0);

                for(int i0 = 0; i0 < _loc_1.size(); i0++)
                {
                		_loc_2 = _loc_1.get(i0);

                    if (_loc_2.slidePick)
                    {
                        _loc_2.hideSlidePick(1);
                    }
                    _loc_2.animation = "idle";
                    _loc_2.clearStates();
                    _loc_2.getStateMachine().addActions(new ActionDie(_loc_2));
                }
            }
            this.m_friendNPCs = new Dictionary();
            this.m_friendNPCData = new Array();
            return;
        }//end

        public void  removeFriendNPC (MapResource param1 )
        {
            NPC _loc_3 =null ;
            Object _loc_4 =null ;
            MapResource _loc_5 =null ;
            if (this.m_friendNPCs.get(param1))
            {
                for(int i0 = 0; i0 < this.m_friendNPCs.get(param1).size(); i0++)
                {
                		_loc_3 = this.m_friendNPCs.get(param1).get(i0);

                    if (_loc_3.slidePick)
                    {
                        _loc_3.hideSlidePick(1);
                    }
                    _loc_3.animation = "idle";
                    _loc_3.clearStates();
                    _loc_3.getStateMachine().addActions(new ActionDie(_loc_3));
                }
                delete this.m_friendNPCs.get(param1);
            }
            int _loc_2 =0;
            while (_loc_2 < this.m_friendNPCData.length())
            {

                _loc_4 = this.m_friendNPCData.get(_loc_2);
                _loc_5 = _loc_4.source;
                if (_loc_5 == param1)
                {
                    this.m_friendNPCData.splice(_loc_2, 1);
                }
                _loc_2++;
            }
            return;
        }//end

        public void  update (double param1 )
        {
            Object _loc_2 =null ;
            MapResource _loc_3 =null ;
            Array _loc_4 =null ;
            NPC _loc_5 =null ;
            Object _loc_6 =null ;
            FriendNPCSlidePick _loc_7 =null ;
            if (DictionaryUtil.getKeys(this.m_friendNPCs).length >= FRIEND_NPC_MAX)
            {
                return;
            }
            this.m_friendNPCReleaseDelta = this.m_friendNPCReleaseDelta + param1;
            if (this.m_friendNPCReleaseDelta >= FRIEND_NPC_RELEASE_DELAY)
            {
                for(int i0 = 0; i0 < this.m_friendNPCData.size(); i0++)
                {
                		_loc_2 = this.m_friendNPCData.get(i0);

                    if (!_loc_2.spawned && !this.alreadySpawned(_loc_2.payload.get(FriendNPCSlidePick.FRIEND_ID)))
                    {
                        _loc_3 = _loc_2.source;
                        if (!_loc_3)
                        {
                            break;
                        }
                        _loc_4 = Global.world.citySim.npcManager.createDesireWalker(_loc_3, [], 1, false);
                        if (_loc_4.length())
                        {
                            _loc_5 = _loc_4.get(0);
                            _loc_6 = _loc_2.payload;
                            _loc_7 = new FriendNPCSlidePick(_loc_5, _loc_6);
                            if (_loc_6.thankYouCallback)
                            {
                                _loc_7.setThankyouCallback(_loc_6.thankYouCallback);
                            }
                            _loc_5.slidePick = _loc_7;
                            _loc_5.showSlidePick();
                            _loc_2.spawned = true;
                            if (!this.m_friendNPCs.get(_loc_3))
                            {
                                this.m_friendNPCs.put(_loc_3,  new Array());
                            }
                            ((Array)this.m_friendNPCs.get(_loc_3)).push(_loc_5);
                            break;
                        }
                    }
                }
                this.m_friendNPCReleaseDelta = 0;
            }
            return;
        }//end

        protected boolean  alreadySpawned (double param1 )
        {
            Object _loc_2 =null ;
            for(int i0 = 0; i0 < this.m_friendNPCData.size(); i0++)
            {
            		_loc_2 = this.m_friendNPCData.get(i0);

                if (_loc_2.spawned && _loc_2.payload.get(FriendNPCSlidePick.FRIEND_ID) == param1)
                {
                    return true;
                }
            }
            return false;
        }//end

    }



