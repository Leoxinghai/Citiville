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
import Classes.orders.*;
import Engine.Classes.*;
import Engine.Helpers.*;
import Modules.quest.Managers.*;


    public class FriendVisitManager implements IGameWorldStateObserver
    {
        protected GameWorld m_world =null ;
        protected Vector<FriendVisitMgrHelper> m_helpers;
        private boolean m_doDebug =false ;
        private boolean m_needToCheckQueue =false ;
        protected double m_delay =0;
        protected boolean m_hasCheckedQueue =false ;
        protected int m_numDbgBusiness ;
        protected int m_numDbgResidence ;
        protected int m_numDbgCrop ;
        protected int m_numDbgWild ;

        public  FriendVisitManager (GameWorld param1 )
        {
            this.m_world = param1;
            this.m_helpers = new Vector<FriendVisitMgrHelper>();
            param1.addObserver(this);
            return;
        }//end

        public void  initialize ()
        {
            return;
        }//end

        public void  cleanUp ()
        {
            FriendVisitMgrHelper _loc_1 =null ;
            for(int i0 = 0; i0 < this.m_helpers.size(); i0++)
            {
            	_loc_1 = this.m_helpers.get(i0);

                _loc_1.hidePick();
            }
            return;
        }//end

        public void  onGameLoaded (Object param1 )
        {
            if (!Global.isVisiting())
            {
                if (!this.m_hasCheckedQueue)
                {
                    this.m_needToCheckQueue = true;
                }
                this.showAllPicks();
            }
            return;
        }//end

        public void  onResourceChange (MapResource param1 ,Vector3 param2 ,Vector3 param3 )
        {
            return;
        }//end

        public void  helperFinished (FriendVisitMgrHelper param1 )
        {
            _loc_2 = this.m_helpers.indexOf(param1 );
            if (_loc_2 >= 0)
            {
                this.m_helpers.splice(_loc_2, 1);
            }
            return;
        }//end

        public void  closeAllPicks (FriendVisitMgrHelper param1 )
        {
            _loc_2 = this.m_helpers.indexOf(param1 );
            int _loc_3 =0;
            while (_loc_3 < this.m_helpers.length())
            {

                if (_loc_3 != _loc_2)
                {
                    this.m_helpers.get(_loc_3).closePick();
                }
                _loc_3++;
            }
            return;
        }//end

        private boolean  storesNHomes (WorldObject param1 )
        {
            Residence _loc_2 =null ;
            if (param1 instanceof Business)
            {
                if (this.m_numDbgBusiness < 2)
                {
                    this.m_numDbgBusiness++;
                    return true;
                }
                return false;
            }
            if (param1 instanceof Residence)
            {
                _loc_2 =(Residence) param1;
                if (_loc_2.getGrowPercentage() == 100)
                {
                    if (this.m_numDbgResidence < 3)
                    {
                        this.m_numDbgResidence++;
                        return true;
                    }
                }
                return false;
            }
            return false;
        }//end

        private boolean  plotsAndWild (WorldObject param1 )
        {
            Plot _loc_2 =null ;
            if (param1 instanceof Plot)
            {
                _loc_2 =(Plot) param1;
                if (_loc_2.getGrowPercentage() > 0 && this.m_numDbgCrop < 2)
                {
                    this.m_numDbgCrop++;
                    return true;
                }
            }
            if (param1 instanceof Wilderness)
            {
                if (this.m_numDbgWild < 2)
                {
                    this.m_numDbgWild++;
                    return true;
                }
            }
            return false;
        }//end

        public void  setupDebug ()
        {
            GameObject _loc_3 =null ;
            Player _loc_4 =null ;
            VisitorHelpOrder _loc_5 =null ;
            FriendVisitMgrHelper _loc_6 =null ;
            if (this.m_helpers.length > 0)
            {
                return;
            }
            this.m_numDbgBusiness = 0;
            this.m_numDbgResidence = 0;
            this.m_numDbgCrop = 0;
            this.m_numDbgWild = 0;
            _loc_1 =Global.world.getObjectsByPredicate(this.storesNHomes );
            _loc_1 = _loc_1.concat(Global.world.getObjectsByPredicate(this.plotsAndWild));
            if (_loc_1.length == 0)
            {
                return;
            }
            Array _loc_2 =new Array ();
            for(int i0 = 0; i0 < _loc_1.size(); i0++)
            {
            	_loc_3 = _loc_1.get(i0);

                _loc_2.push(_loc_3.getId());
            }
            _loc_4 = Global.player.getFakeFriend();
            _loc_5 = new VisitorHelpOrder(Global.player.uid, _loc_4.uid, _loc_2, OrderStatus.RECEIVED);
            _loc_6 = new FriendVisitMgrHelper(_loc_4, _loc_5, this);
            this.m_helpers.push(_loc_6);
            return;
        }//end

        protected void  checkQueue ()
        {
            Array _loc_1 =null ;
            VisitorHelpOrder _loc_2 =null ;
            Player _loc_3 =null ;
            return;

            if (!Global.guide.isActive() && Global.questManager.isUXUnlocked(GameQuestManager.QUEST_UX_VISITS_UNLOCKED))
            {
                this.m_needToCheckQueue = false;
                this.m_hasCheckedQueue = true;
                this.m_helpers = new Vector<FriendVisitMgrHelper>();
                _loc_1 = Global.world.orderMgr.getOrders(OrderType.VISITOR_HELP, OrderStatus.RECEIVED);
                for(int i0 = 0; i0 < _loc_1.size(); i0++)
                {
                	_loc_2 = _loc_1.get(i0);

                    if (_loc_2.getStatus() == VisitorHelpOrder.UNCLAIMED)
                    {
                        _loc_3 = Global.player.findFriendById(_loc_2.getSenderID());
                        this.m_helpers.push(new FriendVisitMgrHelper(_loc_3, _loc_2, this));
                    }
                }
                this.showAllPicks();
            }
            return;
        }//end

        protected void  showAllPicks ()
        {
            FriendVisitMgrHelper _loc_1 =null ;
            for(int i0 = 0; i0 < this.m_helpers.size(); i0++)
            {
            	_loc_1 = this.m_helpers.get(i0);

                if (_loc_1.active)
                {
                    _loc_1.showPick();
                }
            }
            return;
        }//end

        public void  setNeedToCheckQueue ()
        {
            this.m_needToCheckQueue = true;
            return;
        }//end

        public void  update (double param1 )
        {
            FriendVisitMgrHelper _loc_2 =null ;
            if (this.m_needToCheckQueue)
            {
                this.checkQueue();
            }
            if (Global.ui.mouseEnabled == false)
            {
                return;
            }
            if (this.m_delay > 0)
            {
                this.m_delay = this.m_delay - param1;
                return;
            }
            if (this.m_doDebug)
            {
                this.setupDebug();
                this.m_doDebug = false;
            }
            for(int i0 = 0; i0 < this.m_helpers.size(); i0++)
            {
            	_loc_2 = this.m_helpers.get(i0);

                _loc_2.update(param1);
            }
            return;
        }//end

    }



