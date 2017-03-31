package Classes.gates;

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
import Display.GateUI.*;
import Modules.goals.*;
import Modules.goals.mastery.*;
import Transactions.*;
//import flash.display.*;

    public class MasteryGate extends AbstractGate
    {
        public  String CROP_MASTERY ="mastery_crop";
        public  String BUSINESS_MASTERY ="mastery_business";
        public  int NOT_SET =-1;
        protected int m_currentMode =0;
        protected int m_requiredMasteryLevel =-1;
        public static  int CROP_MODE =1;
        public static  int BUS_MODE =2;

        public  MasteryGate (String param1 )
        {
            super(param1);
            return;
        }//end

         public void  loadFromXML (XML param1 )
        {
            super.loadFromXML(param1);
            m_unlockCost = Number(param1.@cashCost);
            this.m_requiredMasteryLevel = Number(param1.@masteryLevel);
            return;
        }//end

         public void  loadFromObject (Object param1 )
        {
            _loc_2 = null;
            if (param1 == null || param1 == "")
            {
                return;
            }
            m_name = String(param1.name);
            for(int i0 = 0; i0 < param1.keys.size(); i0++)
            {
            		_loc_2 = param1.keys.get(i0);

                m_keys.put(_loc_2,  param1.keys.get(_loc_2));
                m_virals[_loc_2] = param1.virals != null && param1.virals.get(_loc_2) != "" ? (param1.virals.get(_loc_2).toString()) : (AskFriendsDialog.REQUEST_BUILDABLE);
            }
            m_loadType = param1.loadType;
            return;
        }//end

        public int  masteryType ()
        {
            return this.m_currentMode;
        }//end

         public boolean  checkForKeys ()
        {
            switch(name)
            {
                case this.CROP_MASTERY:
                {
                    this.m_currentMode = CROP_MODE;
                    break;
                }
                case this.BUSINESS_MASTERY:
                {
                    this.m_currentMode = BUS_MODE;
                    break;
                }
                default:
                {
                    break;
                    break;
                }
            }
            if (this.m_currentMode <= 0)
            {
            }
            _loc_1 = getSeenFlag(unlockItemName,name);
            if (Global.player.getSeenFlag(_loc_1) == true)
            {
                return true;
            }
            switch(this.m_currentMode)
            {
                case CROP_MODE:
                {
                    return this._checkCropMastery();
                }
                case BUS_MODE:
                {
                    return this._checkBusinessMastery();
                }
                default:
                {
                    break;
                }
            }
            return false;
        }//end

        protected boolean  _checkCropMastery ()
        {
            Item _loc_3 =null ;
            int _loc_4 =0;
            _loc_1 =Global.goalManager.getGoal(GoalManager.GOAL_MASTERY )as MasteryGoal ;
            _loc_2 = getKeyArray();
            int _loc_5 =0;
            while (_loc_5 < _loc_2.length())
            {

                _loc_3 = Global.gameSettings().getItemByName(_loc_2.get(_loc_5));
                _loc_4 = _loc_1.getLevel(_loc_2.get(_loc_5));
                if (this.m_requiredMasteryLevel != this.NOT_SET && _loc_4 >= this.m_requiredMasteryLevel)
                {
                    return true;
                }
                _loc_5++;
            }
            return false;
        }//end

        protected boolean  _checkBusinessMastery ()
        {
            Array _loc_2 =null ;
            _loc_1 = getKeyArray();
            _loc_2 = Global.world.getObjectsByNames(_loc_1, false);
            if (_loc_2.length > 0)
            {
                return true;
            }
            return false;
        }//end

         public int  keyProgress (String param1 )
        {
            return 0;
        }//end

         public String  getRequirementString ()
        {
            String _loc_2 =null ;
            String _loc_1 ="";
            for(int i0 = 0; i0 < m_keys.size(); i0++)
            {
            		_loc_2 = m_keys.get(i0);

                if (m_keys.get(_loc_2) == 0)
                {
                    continue;
                }
                _loc_1 = _loc_1 + (" " + ZLoc.t("Dialogs", "GateKey", {qty:m_keys.get(_loc_2), item:Global.gameSettings().getItemByName(_loc_2).localizedName}));
            }
            _loc_1 = _loc_1.slice(0, (_loc_1.length - 1));
            return ZLoc.t("Dialogs", "GateRequirementString", {items:_loc_1});
        }//end

         public double  unlockCost ()
        {
            switch(this.m_currentMode)
            {
                case CROP_MODE:
                {
                    return this.prorateCropMasteryCost(m_unlockCost);
                }
                case BUS_MODE:
                {
                    return this.prorateBusinessMasteryCost(m_unlockCost);
                }
                default:
                {
                    return super.unlockCost;
                    break;
                }
            }
        }//end

        private double  prorateCropMasteryCost (double param1 )
        {
            String _loc_5 =null ;
            double _loc_6 =0;
            double _loc_7 =0;
            double _loc_8 =0;
            double _loc_9 =0;
            _loc_2 = param1;
            _loc_3 = getKeyArray();
            double _loc_4 =0;
            while (_loc_4 < _loc_3.length())
            {

                _loc_5 =(String) _loc_3.get(_loc_4);
                _loc_6 = ((MasteryGoal)Global.goalManager.getGoal(GoalManager.GOAL_MASTERY)).getCurrentCount(_loc_5);
                _loc_7 = ((MasteryGoal)Global.goalManager.getGoal(GoalManager.GOAL_MASTERY)).getTotalRequiredForLevel(_loc_5, this.m_requiredMasteryLevel);
                _loc_8 = _loc_7 - _loc_6;
                if (_loc_8 <= 0)
                {
                    return 0;
                }
                _loc_9 = Math.floor(_loc_8 / _loc_7 * param1);
                _loc_2 = _loc_9 < _loc_2 ? (_loc_9) : (_loc_2);
                _loc_4 = _loc_4 + 1;
            }
            return Math.max(_loc_2, 1);
        }//end

        private double  prorateBusinessMasteryCost (double param1 )
        {
            return param1;
        }//end

         public boolean  buyAll ()
        {
            String _loc_1 =null ;
            if (Global.player.canBuyCash(this.unlockCost))
            {
                _loc_1 = getSeenFlag(m_unlockItemName, m_name);
                GameTransactionManager.addTransaction(new TBuyMasteryGate(m_unlockItemName, m_name, _loc_1, this.unlockCost));
                return true;
            }
            return false;
        }//end

        public void  setDisplayProperties (boolean param1 ,boolean param2 )
        {
            return;
        }//end

         public void  displayGate (String param1 ,String param2 ,Object param3 =null )
        {
            return;
        }//end

         protected void  takeKeys ()
        {
            return;
        }//end

        protected void  askForKey (Item param1 ,DisplayObject param2 )
        {
            return;
        }//end

    }


