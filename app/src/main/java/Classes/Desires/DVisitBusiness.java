package Classes.Desires;

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
import Classes.sim.*;
import Engine.Classes.*;

import com.xinghai.Debug;

    public class DVisitBusiness extends Desire
    {
        protected int m_totalMerchantPopularity ;

        public  DVisitBusiness (DesirePeep param1 )
        {
            super(param1);


            this.m_totalMerchantPopularity = 0;
            return;
        }//end

         public boolean  isActionable ()
        {
            return true;
        }//end

         public SelectionResult  getSelection ()
        {
            MapResource target ;
            IMerchant merchant ;
            Desire thisDesire ;
            this.m_totalMerchantPopularity = 0;


            choices = this.getAvailableMerchantWorldObjects();
            if (choices.length == 0)
            {
                return SelectionResult.FAIL;
            }
            valve = Math.random()*this.m_totalMerchantPopularity;
            //target;
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
            if (!target)
            {
                return SelectionResult.FAIL;
            }
            merchant.planVisit(m_peep);
            //thisDesire;

            return new SelectionResult (target ,[new ActionFn (m_peep ,void  ()
            {
                m_peep.lastMerchantId = target.getId();
                return;
            }//end
            ),new ActionEnableFreedom (m_peep ,false ),new ActionNavigate (m_peep ,target ,null ).setPathType (m_peep .getPathTypeToBusiness ()).setTeleportOnFailure (true ),new ActionEnableFreedom (m_peep ,true ),new ActionFn (m_peep ,void  ()
            {
                //thisDesire.setState(STATE_FINISHED);
                this.setState(STATE_FINISHED);
                return;
            }//end
            ), new ActionMerchantEnter(m_peep, (IMerchant)target)]);
        }//end

        protected Array  getAvailableMerchantWorldObjects ()
        {
            WorldObject _loc_3 =null ;
            IMerchant _loc_4 =null ;
            Array _loc_1 =new Array ();
            _loc_2 =Global.world.getObjects ();
            for(int i0 = 0; i0 < _loc_2.size(); i0++)
            {
            		_loc_3 = _loc_2.get(i0);

                if (_loc_3 instanceof Business || _loc_3 instanceof Mall || _loc_3 instanceof SocialBusiness)
                {
                    _loc_4 =(IMerchant) _loc_3;
                    if (_loc_4 && _loc_4.isAcceptingVisits())
                    {
                        if (_loc_4.getId() != m_peep.lastMerchantId)
                        {
                            this.m_totalMerchantPopularity = this.m_totalMerchantPopularity + _loc_4.getPopularity();
                            _loc_1.push(_loc_3);
                        }
                    }
                }
            }
            return _loc_1;
        }//end

    }



