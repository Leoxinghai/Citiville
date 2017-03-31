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
import Classes.doobers.*;
import Classes.inventory.*;
import Display.*;
import Engine.Classes.*;
import Engine.Managers.*;
import Mechanics.*;
import Mechanics.GameMechanicInterfaces.*;
import Modules.socialinventory.*;
import Modules.stats.types.*;
//import flash.utils.*;

    public class SocialInventoryTransferMechanic extends InventoryMechanic implements IInventoryTransferMechanic, IGatedMechanic
    {
        protected Dictionary m_rewards ;

        public  SocialInventoryTransferMechanic ()
        {
            return;
        }//end

        public BaseInventory  source ()
        {
            return SocialInventoryManager.instance;
        }//end

        public boolean  isGated ()
        {
            return !SocialInventoryManager.isFeatureAvailable();
        }//end

         public boolean  hasOverrideForGameAction (String param1 )
        {
            return !this.isGated() && this.get(param1) instanceof Function;
        }//end

         public MechanicActionResult  executeOverrideForGameEvent (String param1 ,Array param2 )
        {
            MechanicActionResult _loc_3 =new MechanicActionResult(false ,true ,false );
            if (this.verifyParametersForGameEvent(param1, param2))
            {
                _loc_3 = (this.get(param1) as Function).apply(this, null);
                if (_loc_3.sendTransaction)
                {
                    this.processRewards();
                }
            }
            return _loc_3;
        }//end

         public void  initialize (IMechanicUser param1 ,MechanicConfigData param2 )
        {
            Array _loc_4 =null ;
            String _loc_5 =null ;
            int _loc_6 =0;
            super.initialize(param1, param2);
            _loc_3 = param2.rawXMLConfig.attribute("rewards");
            if (_loc_3 && _loc_3.length > 0)
            {
                _loc_4 = _loc_3.split(",");
                for(int i0 = 0; i0 < _loc_4.size(); i0++)
                {
                		_loc_5 = _loc_4.get(i0);

                    _loc_6 = Global.gameSettings().getInt("friendHelpDefault" + _loc_5 + "Reward", -1);
                    if (_loc_6 != -1)
                    {
                        if (!this.m_rewards)
                        {
                            this.m_rewards = new Dictionary();
                        }
                        this.m_rewards.put(_loc_5,  _loc_6);
                    }
                }
            }
            return;
        }//end

        protected boolean  verifyParametersForGameEvent (String param1 ,Array param2 )
        {
            switch(param1)
            {
                case "GMVisit":
                {
                    return param2 && param2.indexOf((m_owner as GameObject).VISIT_PLAY_ACTION) != -1;
                }
                case "GMEditSocialInventory":
                {
                    return param2 && param2.indexOf((m_owner as GameObject).PLAY_ACTION) != -1;
                }
                case GameObject.SELL_OBJECT:
                {
                    return param2 && param2.indexOf(GenericPopup.YES) != -1;
                }
                default:
                {
                    return true;
                    break;
                }
            }
        }//end

        protected MechanicActionResult  sellObject ()
        {
            boolean _loc_1 =false ;
            Object _loc_2 =null ;
            Array _loc_3 =new Array("heart",1);
            if (this.getCount(_loc_3.get(0)) > 0)
            {
                _loc_1 = this.transferToSource.apply(this, _loc_3);
                if (_loc_1)
                {
                    _loc_2 = {operation:"transferToSource", args:_loc_3};
                }
            }
            return new MechanicActionResult(false, true, _loc_1, _loc_2);
        }//end

        protected MechanicActionResult  GMPlay ()
        {
            boolean _loc_1 =false ;
            Object _loc_2 =null ;
            Array _loc_3 =new Array("heart",1);
            if (getCount(_loc_3.get(0)) > 0)
            {
                _loc_1 = this.transferToSource.apply(this, _loc_3);
                if (_loc_1)
                {
                    _loc_2 = {operation:"transferToSource", args:_loc_3};
                }
            }
            return new MechanicActionResult(false, true, _loc_1, _loc_2);
        }//end

        protected MechanicActionResult  GMVisit ()
        {
            boolean _loc_1 =false ;
            Object _loc_2 =null ;
            Array _loc_3 =new Array("heart",1);
            if (Global.getVisiting() != "-1")
            {
                _loc_1 = this.getCount(_loc_3.get(0)) > 0;
                if (_loc_1)
                {
                    _loc_2 = {operation:"none", neighbor:Global.getVisiting(), args:_loc_3};
                    SocialInventoryManager.detachHeartIndicator((MapResource)m_owner);
                }
            }
            else
            {
                _loc_1 = this.transferToSam.apply(this, _loc_3);
                if (_loc_1)
                {
                    _loc_2 = {operation:"transferToSam", neighbor:Global.player.uid, args:_loc_3};
                    SocialInventoryManager.detachHeartIndicator((MapResource)m_owner);
                }
            }
            return new MechanicActionResult(false, true, _loc_1, _loc_2);
        }//end

        protected MechanicActionResult  GMEditSocialInventory ()
        {
            boolean _loc_1 =false ;
            Object _loc_2 =null ;
            Array _loc_3 =new Array("heart",1);
            if (getCount(_loc_3.get(0)) > 0)
            {
                _loc_1 = this.transferToSource.apply(this, _loc_3);
                if (_loc_1)
                {
                    StatsManager.sample(100, StatsCounterType.HUD_COUNTER, StatsKingdomType.ACTION_MENU, "heart_tool", "remove_help", ((ItemInstance)m_owner).getItemName());
                    _loc_2 = {operation:"transferToSource", args:_loc_3};
                    SocialInventoryManager.detachHeartIndicator((MapResource)m_owner);
                    SocialInventoryManager.sendDooberFromWorldObject((MapResource)m_owner);
                }
            }
            else if (!(m_owner instanceof ConstructionSite) || (m_owner as ConstructionSite).isVisitorInteractable())
            {
                _loc_1 = this.transferFromSource.apply(this, _loc_3);
                if (_loc_1)
                {
                    StatsManager.sample(100, StatsCounterType.HUD_COUNTER, StatsKingdomType.ACTION_MENU, "heart_tool", "request_help", ((ItemInstance)m_owner).getItemName());
                    _loc_2 = {operation:"transferFromSource", args:_loc_3};
                    SocialInventoryManager.showSecondToaster();
                    if (this.source.getCount(_loc_3.get(0)) == 0)
                    {
                        SocialInventoryManager.showThirdToaster();
                    }
                    SocialInventoryManager.attachHeartIndicator((MapResource)m_owner, _loc_3.get(1));
                    SocialInventoryManager.sendDooberToWorldObject((MapResource)m_owner);
                }
            }
            else
            {
                SocialInventoryManager.showIneligibleToaster();
            }
            if (_loc_1)
            {
                Global.hud.markDirty();
            }
            return new MechanicActionResult(false, true, _loc_1, _loc_2);
        }//end

        protected MechanicActionResult  visitReplayAction ()
        {
            boolean _loc_1 =false ;
            Object _loc_2 =null ;
            Array _loc_3 =new Array("heart",1);
            if (getCount(_loc_3.get(0)) > 0)
            {
                _loc_1 = this.transferToSource.apply(this, _loc_3);
                if (_loc_1)
                {
                    _loc_2 = {operation:"transferToSource", args:_loc_3};
                    SocialInventoryManager.detachHeartIndicator((MapResource)m_owner);
                    SocialInventoryManager.sendDooberFromWorldObject((MapResource)m_owner);
                }
            }
            return new MechanicActionResult(false, true, _loc_1, _loc_2);
        }//end

        public boolean  transferFromSource (String param1 ,int param2 )
        {
            boolean _loc_3 =false ;
            boolean _loc_4 =false ;
            if (param1 && param2 > 0)
            {
                _loc_3 = false;
                _loc_4 = false;
                _loc_3 = this.add(param1, param2);
                if (_loc_3)
                {
                    _loc_4 = this.source.remove(param1, param2);
                    if (!_loc_4)
                    {
                        this.remove(param1, param2);
                    }
                }
                return _loc_4;
            }
            else
            {
                return false;
            }
        }//end

        public boolean  transferToSource (String param1 ,int param2 )
        {
            boolean _loc_3 =false ;
            boolean _loc_4 =false ;
            if (param1 && param2 > 0)
            {
                _loc_3 = false;
                _loc_4 = false;
                _loc_3 = this.remove(param1, param2);
                if (_loc_3)
                {
                    _loc_4 = this.source.add(param1, param2);
                }
                return _loc_3;
            }
            else
            {
                return false;
            }
        }//end

        public boolean  transferToSam (String param1 ,int param2 )
        {
            boolean _loc_3 =false ;
            if (!SocialInventoryManager.instance.hasCollectedCitySamObject(m_owner as WorldObject))
            {
                _loc_3 = this.getCount(param1) > 0;
                if (_loc_3)
                {
                    SocialInventoryManager.instance.addCitySamObject((WorldObject)m_owner);
                }
            }
            return _loc_3;
        }//end

        protected boolean  processRewards ()
        {
            String _loc_3 =null ;
            int _loc_4 =0;
            Array _loc_1 =new Array();
            _loc_2 =Global.gameSettings().getNumber("friendHelpSocialMultiplier",1);
            for(int i0 = 0; i0 < this.m_rewards.size(); i0++)
            {
            		_loc_3 = this.m_rewards.get(i0);

                _loc_4 = this.m_rewards.get(_loc_3) * _loc_2;
                _loc_1.push(.get(Global.gameSettings().getDooberFromType(Doober.get("DOOBER_" + _loc_3.toUpperCase()), _loc_4), _loc_4));
            }
            Global.world.dooberManager.createBatchDoobers(_loc_1, null, ((WorldObject)m_owner).positionX, ((WorldObject)m_owner).positionY);
            return true;
        }//end

    }



