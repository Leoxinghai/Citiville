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
import Classes.util.*;
import Display.NeighborUI.*;
import Engine.Helpers.*;
import Modules.stats.experiments.*;
import Transactions.*;
//import flash.display.*;

    public class FriendVisitMgrHelper
    {
        protected Player m_friend ;
        protected FriendSlidePick m_pick ;
        protected boolean m_active =false ;
        protected double m_delay =0;
        protected VisitorHelpOrder m_friendOrder =null ;
        protected int m_friendOrderNdx =0;
        protected FriendVisitManager m_manager =null ;

        public  FriendVisitMgrHelper (Player param1 ,VisitorHelpOrder param2 ,FriendVisitManager param3 )
        {
            boolean _loc_4 =false ;
            if (param1 !=null)
            {
                this.m_friendOrder = param2;
                this.m_friend = param1;
                this.m_manager = param3;
                this.m_pick = new FriendSlidePick(this.m_friend, this);
                this.showPick();
                _loc_4 = this.movePickToNextObject(true);
                if (_loc_4 == true)
                {
                    this.kill();
                    this.m_active = false;
                }
            }
            return;
        }//end

        public FriendSlidePick  pick ()
        {
            return this.m_pick;
        }//end

        public boolean  active ()
        {
            return this.m_active;
        }//end

        public String  orderStatus ()
        {
            return this.m_friendOrder ? (this.m_friendOrder.getStatus()) : (null);
        }//end

        public void  showPick ()
        {
            if (this.m_pick)
            {
                GlobalEngine.viewport.hudBase.addChild(this.m_pick);
            }
            return;
        }//end

        public void  hidePick ()
        {
            if (this.m_pick && this.m_pick.parent)
            {
                this.m_pick.parent.removeChild(this.m_pick);
            }
            return;
        }//end

        public void  closePick ()
        {
            if (this.m_pick && this.m_pick.parent)
            {
                this.m_pick.closeInnerPane();
            }
            return;
        }//end

        public void  acceptVisit (FriendSlidePick param1 )
        {
            double _loc_4 =0;
            MapResource _loc_5 =null ;
            if (param1 != this.m_pick)
            {
                return;
            }
            if (this.m_friendOrder == null)
            {
                return;
            }
            this.m_active = true;
            this.doOnVisitReplayAction();
            _loc_2 = this.m_friendOrder.getHelpTargets ();
            if (_loc_2.length == 0)
            {
                return;
            }
            int _loc_3 =0;
            while (_loc_3 < _loc_2.length())
            {

                _loc_4 = _loc_2.get(_loc_3);
                _loc_5 =(MapResource) Global.world.getObjectById(_loc_4);
                if (_loc_5 == null)
                {
                }
                else
                {
                    _loc_5.lockForReplay();
                }
                _loc_3++;
            }
            return;
        }//end

        public void  cancelVisit (FriendSlidePick param1 )
        {
            if (param1 != this.m_pick)
            {
                return;
            }
            GameTransactionManager.addTransaction(new TDeclineVisitorHelp(this.m_friendOrder));
            this.clearHighlightOnAllObjects();
            this.kill();
            this.m_manager.helperFinished(this);
            return;
        }//end

        public void  showingSlide (FriendSlidePick param1 )
        {
            double _loc_4 =0;
            MapResource _loc_5 =null ;
            if (param1 != this.m_pick)
            {
                return;
            }
            if (this.m_friendOrder == null)
            {
                return;
            }
            this.m_manager.closeAllPicks(this);
            _loc_2 = this.m_friendOrder.getHelpTargets ();
            int _loc_3 =0;
            while (_loc_3 < _loc_2.length())
            {

                _loc_4 = _loc_2.get(_loc_3);
                _loc_5 =(MapResource) Global.world.getObjectById(_loc_4);
                if (_loc_5 != null)
                {
                    _loc_5.setVisitorInteractedHighlighted(true);
                }
                _loc_3++;
            }
            return;
        }//end

        public void  panelClosed (FriendSlidePick param1 )
        {
            if (!this.m_active)
            {
                this.clearHighlightOnAllObjects();
            }
            return;
        }//end

        public void  tweenToFinished (FriendSlidePick param1 )
        {
            this.m_active = true;
            this.doOnVisitReplayAction();
            return;
        }//end

        public void  moveToFront (FriendSlidePick param1 )
        {
            Sprite _loc_2 =null ;
            if (param1 !=null)
            {
                _loc_2 = GlobalEngine.viewport.hudBase;
                if (_loc_2.contains(param1))
                {
                    _loc_2.setChildIndex(param1, (_loc_2.numChildren - 1));
                }
            }
            return;
        }//end

        private boolean  movePickToNextObject (boolean param1 =false )
        {
            if (this.m_friendOrder == null)
            {
                return true;
            }
            _loc_2 = this.m_friendOrder.getHelpTargets ();
            if (_loc_2.length == 0)
            {
                return true;
            }
            if (this.m_friendOrderNdx >= _loc_2.length())
            {
                return true;
            }
            _loc_2.get(_loc_3 = this.m_friendOrderNdx) ;
            while (this.m_friendOrder.isHelpTargetClaimed(_loc_3))
            {

                this.m_friendOrderNdx++;
                if (this.m_friendOrderNdx >= _loc_2.length())
                {
                    return true;
                }
                _loc_3 = _loc_2.get(this.m_friendOrderNdx);
            }
            _loc_4 = (MapResource)Global.world.getObjectById(_loc_3 );
            if ((MapResource)Global.world.getObjectById(_loc_3) == null)
            {
                return true;
            }
            _loc_5 = _loc_4.getPosition ();
            _loc_4.getPosition().x = _loc_4.getPosition().x + _loc_4.sizeX;
            _loc_5.y = _loc_5.y + _loc_4.sizeY;
            if (param1 !=null)
            {
                this.m_pick.setPosition(_loc_5.x, _loc_5.y);
            }
            else
            {
                this.m_pick.tweenToPos(_loc_5);
                this.m_active = false;
            }
            return false;
        }//end

        public void  kill ()
        {
            this.m_pick.kill(this.pick.cleanUp);
            this.m_pick = null;
            this.m_friendOrder = null;
            this.m_friend = null;
            return;
        }//end

        protected void  doOnVisitReplayAction ()
        {
            if (this.m_friendOrder == null)
            {
                return;
            }
            _loc_1 = this.m_friendOrder.getHelpTargets ();
            this.m_delay = 2;
            if (_loc_1.length == 0)
            {
                return;
            }
            if (this.m_friendOrderNdx >= _loc_1.length())
            {
                return;
            }
            _loc_1.get(_loc_2 = this.m_friendOrderNdx) ;
            _loc_3 =Global.world.getObjectById(_loc_2 )as MapResource ;
            if (_loc_3 == null)
            {
                return;
            }
            this.m_delay = _loc_3.onVisitReplayAction(new TRedeemVisitorHelpAction(this.m_friend.snUser.uid, _loc_2, _loc_3.getClassName(), _loc_3.getActionTargetName(), _loc_3.getVisitReplayEquivalentActionString(), this.m_friendOrder));
            this.m_friendOrder.claimHelpTarget(_loc_2);
            this.pick.tweenBounce();
            return;
        }//end

        protected void  clearHighlightOnCurObject ()
        {
            if (this.m_friendOrder == null)
            {
                return;
            }
            _loc_1 = this.m_friendOrder.getHelpTargets ();
            if (_loc_1.length == 0)
            {
                return;
            }
            if (this.m_friendOrderNdx >= _loc_1.length())
            {
                return;
            }
            _loc_1.get(_loc_2 = this.m_friendOrderNdx) ;
            _loc_3 =Global.world.getObjectById(_loc_2 )as MapResource ;
            if (_loc_3 == null)
            {
                return;
            }
            _loc_3.setVisitorInteractedHighlighted(false);
            return;
        }//end

        protected void  clearHighlightOnAllObjects ()
        {
            double _loc_3 =0;
            MapResource _loc_4 =null ;
            if (this.m_friendOrder == null)
            {
                return;
            }
            _loc_1 = this.m_friendOrder.getHelpTargets ();
            if (_loc_1.length == 0)
            {
                return;
            }
            int _loc_2 =0;
            while (_loc_2 < _loc_1.length())
            {

                _loc_3 = _loc_1.get(_loc_2);
                _loc_4 =(MapResource) Global.world.getObjectById(_loc_3);
                if (_loc_4 == null)
                {
                }
                else
                {
                    _loc_4.setVisitorInteractedHighlighted(false);
                }
                _loc_2++;
            }
            return;
        }//end

        public void  update (double param1 )
        {
            int _loc_3 =0;
            if (!this.m_active)
            {
                return;
            }
            if (this.m_delay > 0)
            {
                this.m_delay = this.m_delay - param1;
                return;
            }
            this.clearHighlightOnCurObject();
            this.m_friendOrderNdx++;
            _loc_2 = this.movePickToNextObject ();
            this.m_delay = 4;
            if (_loc_2)
            {
                this.m_friendOrder.setStatus(VisitorHelpOrder.CLAIMED);
                _loc_3 = Global.experimentManager.getVariant(ExperimentDefinitions.EXPERIMENT_NEIGHBOR_VISIT_2);
                if (_loc_3 >= ExperimentDefinitions.NEIGHBOR_VISIT_2_FEATURE)
                {
                    this.m_pick.setState(FriendSlidePick.STATE_VISIT_BACK);
                }
                else
                {
                    this.kill();
                    this.m_active = false;
                    this.m_manager.helperFinished(this);
                }
            }
            return;
        }//end

        public void  notifyHelperFinished ()
        {
            this.m_manager.helperFinished(this);
            return;
        }//end

    }



