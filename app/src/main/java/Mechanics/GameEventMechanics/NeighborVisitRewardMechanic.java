package Mechanics.GameEventMechanics;

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
import Classes.util.*;
import Events.*;
import Mechanics.*;
import Mechanics.GameMechanicInterfaces.*;
import Modules.quest.Helpers.*;
import Transactions.*;
import com.adobe.utils.*;
import com.zynga.skelly.util.*;
//import flash.utils.*;

    public class NeighborVisitRewardMechanic implements IActionGameMechanic, IMultiPickSupporter, IToolTipModifier
    {
        private MechanicMapResource m_owner ;
        private MechanicConfigData m_config ;

        public  NeighborVisitRewardMechanic ()
        {
            return;
        }//end

        public void  initialize (IMechanicUser param1 ,MechanicConfigData param2 )
        {
            this.m_owner =(MechanicMapResource) param1;
            this.m_config = param2;
            return;
        }//end

        public boolean  hasOverrideForGameAction (String param1 )
        {
            return this.canExecute();
        }//end

        protected boolean  canExecute ()
        {
            return Global.isVisiting() && !this.m_owner.areVisitorInteractionsExhausted && this.m_owner.isVisitorInteractable();
        }//end

        public MechanicActionResult  executeOverrideForGameEvent (String param1 ,Array param2 )
        {
            if (!this.canExecute())
            {
                return new MechanicActionResult(false, true, false, null);
            }
            if (this.isUsingTourBus())
            {
                Global.world.citySim.tourBusManager.addTourBus(this.m_owner as IMerchant, Curry.curry(this.handleRewards, param1));
            }
            else
            {
                this.handleRewards(param1);
            }
            _loc_3 =Global.getVisiting ();
            boolean _loc_4 =true ;
            boolean _loc_5 =false ;
            boolean _loc_6 =true ;
            Object _loc_7 ={neighbor _loc_3 };
            _loc_8 = this.m_owner ;
            _loc_9 = this.m_owner.numVisitorInteractions +1;
            _loc_8.numVisitorInteractions = _loc_9;
            this.m_owner.dispatchEvent(new GenericObjectEvent(GenericObjectEvent.MECHANIC_DATA_CHANGED, this.m_config.type, true, param1));
            GameTransactionManager.addTransaction(new TPerformVisitorHelp(Global.world.ownerId, this.m_owner, VisitorHelpType.BUSINESS_SEND_TOUR));
            return new MechanicActionResult(_loc_4, _loc_5, _loc_6, _loc_7);
        }//end

        private Array  createVisitorReward ()
        {
            int _loc_3 =0;
            String _loc_1 =null ;
            if (this.m_config && this.m_config.params)
            {
                _loc_1 = this.m_config.params.get("rewardType");
            }
            Array _loc_2 =new Array ();
            if (_loc_1 != null)
            {
                _loc_3 = this.getRewardQuantity();
                _loc_2.push(.get(Global.gameSettings().getDooberFromType(_loc_1, _loc_3), _loc_3));
            }
            return _loc_2;
        }//end

        private void  handleRewards (String param1 )
        {
            Dictionary _loc_3 =null ;
            int _loc_4 =0;
            boolean _loc_5 =false ;
            String _loc_6 =null ;
            Array _loc_7 =null ;
            Array _loc_8 =null ;
            XML _loc_9 =null ;
            String _loc_10 =null ;
            int _loc_11 =0;
            Array _loc_2 =new Array ();
            if (this.isTrackingVisitors())
            {
                _loc_3 = this.m_owner.getDataForMechanic(this.m_config.type);
                if (!_loc_3)
                {
                    _loc_3 = new Dictionary();
                }
                _loc_4 = this.getVisitorLimit();
                _loc_5 = false;
                if (_loc_3)
                {
                    _loc_4 = DictionaryUtil.getKeys(_loc_3).length;
                    _loc_5 = _loc_3.get(Global.player.uid) != null;
                }
                if (_loc_4 < this.getVisitorLimit() && !_loc_5)
                {
                    _loc_6 = Global.player.snUser.uid;
                    _loc_3.put(_loc_6,  this.getRewardName());
                    this.m_owner.setDataForMechanic(this.m_config.type, _loc_3, param1);
                    _loc_2 = this.createVisitorReward();
                }
            }
            if (this.m_config.rawXMLConfig.hasOwnProperty("randomModifiers"))
            {
                _loc_7 = Global.player.processRandomModifiersFromConfig(this.m_config.rawXMLConfig.randomModifiers[0], this.m_owner.getItem(), [], null, null, this.m_owner);
                for(int i0 = 0; i0 < _loc_7.size(); i0++)
                {
                		_loc_8 = _loc_7.get(i0);

                    _loc_2.push(_loc_8);
                }
            }
            if (this.m_config.rawXMLConfig.hasOwnProperty("reward"))
            {
                for(int i0 = 0; i0 < this.m_config.rawXMLConfig.reward.size(); i0++)
                {
                		_loc_9 = this.m_config.rawXMLConfig.reward.get(i0);

                    _loc_10 = _loc_9.@type;
                    _loc_11 = parseInt(_loc_9.@amount);
                    _loc_2.push(.get(Global.gameSettings().getDooberFromType(_loc_10, _loc_11), _loc_11));
                }
            }
            if (_loc_2.length > 0)
            {
                Global.world.dooberManager.createBatchDoobers(_loc_2, null, this.m_owner.positionX, this.m_owner.positionY);
            }
            return;
        }//end

        private boolean  isTrackingVisitors ()
        {
            if (this.m_config.params.get("trackVisitors") != null)
            {
                return this.m_config.params.get("trackVisitors") == "true";
            }
            return true;
        }//end

        private boolean  isUsingTourBus ()
        {
            if (this.m_config.params.get("useTourBus") != null)
            {
                return this.m_config.params.get("useTourBus") == "true";
            }
            return false;
        }//end

        public int  getVisitorLimit ()
        {
            if (this.m_config != null)
            {
                return int(this.m_config.params.get("visitorLimit"));
            }
            throw new Error("MechanicConfigData not initialized");
        }//end

        public String  getRewardName ()
        {
            if (this.m_config != null)
            {
                return String(this.m_config.params.get("rewardName"));
            }
            throw new Error("MechanicConfigData not initialized");
        }//end

        public int  getRewardQuantity ()
        {
            if (this.m_config != null)
            {
                return int(this.m_config.params.get("rewardQuantity"));
            }
            throw new Error("MechanicConfigData not initialized");
        }//end

        public boolean  isVisitor (String param1 )
        {
            boolean _loc_2 =false ;
            _loc_3 = this.m_owner.getDataForMechanic(this.m_config.type );
            if (_loc_3 && _loc_3.hasOwnProperty(param1))
            {
                _loc_2 = true;
            }
            return _loc_2;
        }//end

        public boolean  isVisitEnabled ()
        {
            return true;
        }//end

        public String  getPick ()
        {
            return this.m_config.params.get("pick");
        }//end

        public Array  getPicksToHide ()
        {
            return null;
        }//end

        public String  getToolTipStatus ()
        {
            String _loc_2 =null ;
            String _loc_3 =null ;
            String _loc_1 =null ;
            if (this.canExecute())
            {
                if (Global.player.checkVisitorEnergy(1))
                {
                    _loc_2 = "Main";
                    _loc_3 = "ClickToSendTour";
                    if (this.m_config.params.get("zlocPkg") && this.m_config.params.get("zlocKey"))
                    {
                        _loc_2 = this.m_config.params.get("zlocPkg");
                        _loc_3 = this.m_config.params.get("zlocKey");
                    }
                    _loc_1 = ZLoc.t(_loc_2, _loc_3);
                }
                else
                {
                    _loc_1 = ZLoc.t("Main", "NeedEnergy", {amount:1});
                }
            }
            return _loc_1;
        }//end

        public String  getToolTipAction ()
        {
            String _loc_1 =null ;
            return _loc_1;
        }//end

        public boolean  blocksMechanicsOnSuccess ()
        {
            return true;
        }//end

    }



