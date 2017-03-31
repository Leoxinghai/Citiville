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
import Classes.actions.*;
import Engine.*;
import Engine.Classes.*;

import com.xinghai.Debug;

    public class WeiboPeepActionSelection implements IActionSelection
    {
        protected WeiboPeep m_peep ;
        protected int m_totalMerchantPopularity ;
        public static  String GAME_EVENT ="NPCEnterAction";

        public  WeiboPeepActionSelection (WeiboPeep param1 )
        {
            this.m_peep = param1;
            this.m_totalMerchantPopularity = 0;
            return;
        }//end

        public Array  getNextActions ()
        {
            _loc_1 = this.m_peep.lastMerchantId ;
            SelectionResult _loc_2 =SelectionResult.FAIL ;

            /*
            this.m_peep.beforeDecision();
            if (this.m_peep.isReadyToGoHome())
            {
                _loc_2 = this.goHomeAndLeave();
            }
            if (_loc_2 == SelectionResult.FAIL && _loc_1 != 0)
            {
                _loc_2 = this.wanderOrGoHome();
            }
            if (_loc_2 == SelectionResult.FAIL)
            {
                _loc_2 = this.goToMerchant();
            }
            if (_loc_2 == SelectionResult.FAIL)
            {
                _loc_2 = this.goToHouse();
            }
            if (_loc_2 == SelectionResult.FAIL)
            {
                _loc_2 = new SelectionResult(null, [new ActionDie(this.m_peep)]);
            }
            */


            _loc_2 =new SelectionResult (null ,[new ActionFn (this .m_peep ,void  ()
				          {
					        this.m_peep.animation = "idle";
					        return;
				           }//end
				           )]);
*/

            _loc_2 = new SelectionResult(null, [new ActionPlayAnimation(this.m_peep, "idle", 0.7)]);




	    return _loc_2.actions;
        }//end

        protected boolean  poiMerchantFilter (POI param1 )
        {
            boolean _loc_2 =false ;
            if (!(param1.resource instanceof IMerchant))
            {
                _loc_2 = true;
            }
            return _loc_2;
        }//end

        protected SelectionResult  goToPOIOrHouse ()
        {
            boolean _loc_8 =false ;
            _loc_1 =Global.world.citySim.poiManager.getResourcesByPredicate(this.poiMerchantFilter );
            _loc_2 =Global.world.getObjectsByClass(Residence );
            if (_loc_2 == null)
            {
                _loc_2 = new Array();
            }
            _loc_3 = _loc_2.length +_loc_1.length ;
            _loc_4 = _loc_3==0? (0) : (_loc_2.length / _loc_3);
            _loc_5 = Math.random ();
            if (Math.random() < _loc_4)
            {
                _loc_8 = Math.random() < Global.gameSettings().getNumber("npcGoingHomeFraction", 0.5);
                return _loc_8 ? (this.goHomeAndLeave()) : (this.goToHouse());
            }
            if (_loc_1.length == 0)
            {
                return SelectionResult.FAIL;
            }
            _loc_6 = MathUtil.randomElement(_loc_1);
            _loc_7 =Global.gameSettings().getInt("npcBizWaitingTime",50);
            return new SelectionResult(_loc_6, [new ActionEnableFreedom(this.m_peep, false), new ActionNavigate(this.m_peep, _loc_6, null).setPathType(RoadManager.PATH_FULL), new ActionPlayAnimation(this.m_peep, "jump", 0.7), new ActionBusinessPacing(this.m_peep, _loc_6, false, _loc_7), new ActionEnableFreedom(this.m_peep, true)]);
        }//end

        protected SelectionResult  wanderOrGoHome ()
        {
            _loc_1 =Global.gameSettings().getNumber("npcWanderProb",0.8);
            _loc_2 = Math.random ();
            if (_loc_2 > _loc_1 || !this.m_peep.canWander())
            {
                return this.goHomeAndLeave();
            }
            _loc_3 =Global.gameSettings().getNumber("npcWanderTimeSec",15);
            _loc_4 =Global.gameSettings().getNumber("npcWanderTimePlusMinus",5);
            _loc_5 = MathUtil.random(_loc_3+_loc_4,_loc_3-_loc_4);
            return new SelectionResult(null, [new ActionEnableFreedom(this.m_peep, false), new ActionNavigateRandom(this.m_peep).setTimeout(_loc_5), new ActionEnableFreedom(this.m_peep, true)]);
        }//end

        protected boolean  merchantChecker (WorldObject param1 )
        {
            boolean _loc_2 =false ;
            if (param1 instanceof IMerchant && ((IMerchant)param1).isRouteable())
            {
                if (((IMerchant)param1).getId() !== this.m_peep.lastMerchantId)
                {
                    this.m_totalMerchantPopularity = this.m_totalMerchantPopularity + ((IMerchant)param1).getPopularity();
                    _loc_2 = true;
                }
            }
            return _loc_2;
        }//end

        protected SelectionResult  goToMerchant ()
        {
            MapResource target ;
            IMerchant merchant ;
            this.m_totalMerchantPopularity = 0;
            choices = Global.world.getObjectsByPredicate(this.merchantChecker);
            if (choices.length == 0)
            {
                return SelectionResult.FAIL;
            }
            valve = Math.random()*this.m_totalMerchantPopularity;
            target;
            int _loc_2 =0;
            _loc_3 = choices;
            for(int i0 = 0; i0 < choices.size(); i0++)
            {
            	merchant = choices.get(i0);


                valve = valve - merchant.getPopularity();
                if (valve < 0)
                {
                    target = merchant.getMapResource();
                    break;
                }
            }
            this.m_totalMerchantPopularity = 0;
            if (target == null)
            {
                return SelectionResult.FAIL;
            }
            time = Global.gameSettings().getInt("npcBizWaitingTime",50);
            return new SelectionResult (target ,[new ActionFn (this .m_peep ,void  ()
            {
                m_peep.lastMerchantId = target.getId();
                return;
            }//end
            ), new ActionEnableFreedom(this.m_peep, false), new ActionNavigate(this.m_peep, target, null).setPathType(this.m_peep.getPathTypeToBusiness()).setTeleportOnFailure(true), new ActionEnableFreedom(this.m_peep, true), new ActionMerchantEnter(this.m_peep, (IMerchant)target)]);
        }//end

        protected SelectionResult  goToHouse ()
        {
            _loc_1 =Global.world.getObjectsByClass(Residence );
            if (_loc_1.length == 0)
            {
                return SelectionResult.FAIL;
            }
            _loc_2 = MathUtil.randomElement(_loc_1);
            _loc_3 =Global.gameSettings().getInt("npcPoiWaitingTime",50);
            return new SelectionResult(_loc_2, [new ActionEnableFreedom(this.m_peep, false), new ActionNavigate(this.m_peep, _loc_2, null).setPathType(this.m_peep.getPathTypeToResidence()).setTeleportOnFailure(true), new ActionEnableFreedom(this.m_peep, true), new ActionBusinessPacing(this.m_peep, _loc_2, false, _loc_3)]);
        }//end

        protected SelectionResult  goHomeAndLeave ()
        {
            Residence home ;
            Array actions ;
            home = this.m_peep.getHome();
            if (home == null)
            {
                home = Global.world.citySim.npcManager.getRandomReturnHome();
            }
            if (home != null)
            {
                actions;
            }
            actions.push(new ActionDie(this.m_peep));
            return new SelectionResult(home, actions);
        }//end

    }



