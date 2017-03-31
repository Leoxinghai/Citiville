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
import Engine.*;

//import flash.utils.*;

    public class UpgradeDefinition
    {
        protected String m_oldItemName ;
        protected String m_newItemName ;
        protected Requirements m_requirements ;
        protected String m_upgradeTrackingClass ;
        protected String m_freeHarvestPhylum ;
        protected String m_introSeenFlag ;
        protected String m_introGuideNotify ;
        protected int m_cashCost ;
        protected Dictionary m_rewards ;
        protected Vector<UpgradeHelperDefinition> m_helpers;
        protected String m_firstTimeToasterText ;
        protected String m_toasterIcon ;
        protected String m_experiment ;

        public  UpgradeDefinition (String param1 ,XML param2 )
        {
            if (param1 && param2)
            {
                this.m_oldItemName = param1;
                this.m_newItemName = String(param2.@item);
                this.m_cashCost = param2.@cashcost == null ? (0) : (int(param2.@cashcost));
                this.m_rewards = this.parseRewards((XMLList)param2.rewards);
                this.m_helpers = this.parseHelpers((XMLList)param2.helpers);
                this.m_upgradeTrackingClass = String(param2.upgradeTrackingClass);
                this.m_freeHarvestPhylum = String(param2.freeHarvestPhylum);
                this.m_introSeenFlag = String(param2.introSeenFlag);
                this.m_introGuideNotify = String(param2.introGuideNotify);
                this.m_experiment = String(param2.experiment);
                this.m_firstTimeToasterText = String(param2.firstTimeToasterText);
                if (param2.toasterIcon)
                {
                    this.m_toasterIcon = String(param2.toasterIcon);
                }
                if (param2.requirements.length() > 0)
                {
                    this.m_requirements = new Requirements(((XMLList)param2.requirements).get(0));
                }
            }
            return;
        }//end

        private Dictionary  parseRewards (XMLList param1 )
        {
            XML _loc_3 =null ;
            String _loc_4 =null ;
            String _loc_5 =null ;
            Dictionary _loc_2 =new Dictionary ();
            for(int i0 = 0; i0 < param1.children().size(); i0++)
            {
            		_loc_3 = param1.children().get(i0);

                _loc_4 = String(_loc_3.@type);
                _loc_5 = String(_loc_3.@value);
                _loc_2.put(_loc_4,  _loc_5);
            }
            return _loc_2;
        }//end

        private UpgradeHelperDefinition Vector  parseHelpers (XMLList param1 ).<>
        {
            XML _loc_3 =null ;
            Vector<UpgradeHelperDefinition> _loc_2 =new Vector<UpgradeHelperDefinition>();
            for(int i0 = 0; i0 < param1.children().size(); i0++)
            {
            		_loc_3 = param1.children().get(i0);

                _loc_2.push(new UpgradeHelperDefinition(_loc_3));
            }
            return _loc_2;
        }//end

        public Dictionary  rewards ()
        {
            return this.m_rewards;
        }//end

        public String  oldItemName ()
        {
            return this.m_oldItemName;
        }//end

        public String  newItemName ()
        {
            return this.m_newItemName;
        }//end

        public Requirements  requirements ()
        {
            return this.m_requirements;
        }//end

        public UpgradeHelperDefinition Vector  helpers ().<>
        {
            return this.m_helpers;
        }//end

        public int  cashCost ()
        {
            return this.m_cashCost;
        }//end

        public String  upgradeTrackingClass ()
        {
            return this.m_upgradeTrackingClass;
        }//end

        public String  freeHarvestPhylum ()
        {
            return this.m_freeHarvestPhylum;
        }//end

        public String  introSeenFlag ()
        {
            return this.m_introSeenFlag;
        }//end

        public String  introGuideNotify ()
        {
            return this.m_introGuideNotify;
        }//end

        public String  firstTimeToasterText ()
        {
            return this.m_firstTimeToasterText;
        }//end

        public String  toasterIcon ()
        {
            return this.m_toasterIcon;
        }//end

        public String  experiment ()
        {
            return this.m_experiment;
        }//end

        public boolean  meetsExperimentRequirement ()
        {
            if (this.m_experiment && this.m_experiment != "")
            {
                return Global.experimentManager.getVariant(this.m_experiment) == 1;
            }
            return true;
        }//end

        public boolean  isValid ()
        {
            return this.newItemName != null && this.newItemName != "";
        }//end

        public boolean  isUpgradePossible (MapResource param1 )
        {
            return this.m_requirements != null && this.m_requirements.checkRequirements(param1);
        }//end

        public double  getUpgradeActionsProgress (MapResource param1 )
        {
            _loc_2 = this.m_requirements.getRequirementValue(Requirements.REQUIREMENT_UPGRADE_ACTIONS );
            if (_loc_2 == null)
            {
                return 0;
            }
            _loc_3 =(double)(_loc_2 );
            if (_loc_3 == 0)
            {
                return 1;
            }
            _loc_4 = param1.upgradeActions.getTotal ();
            return MathUtil.clamp(_loc_4 / _loc_3, 0, 1);
        }//end

        public double  getUpgradeActionsRemaining (MapResource param1 )
        {
            _loc_2 = this.m_requirements.getRequirementValue(Requirements.REQUIREMENT_UPGRADE_ACTIONS );
            if (_loc_2 == null)
            {
                return 0;
            }
            _loc_3 =(double)(_loc_2 );
            if (_loc_3 == 0)
            {
                return 1;
            }
            _loc_4 = param1.upgradeActions.getTotal ();
            return Math.max(_loc_3 - _loc_4, 0);
        }//end

        public static Array  getUpgradeChain (String param1 )
        {
            boolean _loc_4 =false ;
            Array _loc_2 =new Array();
            _loc_3 =Global.gameSettings().getItemByName(param1 );
            do
            {

                _loc_2.push(_loc_3.upgrade);
                _loc_3 = Global.gameSettings().getItemByName(_loc_3.upgrade.newItemName);
                _loc_4 = true;
                if (_loc_3.upgrade && _loc_3.upgrade.experiment != "" && Global.experimentManager.getVariant(_loc_3.upgrade.experiment) == 0)
                {
                    _loc_4 = false;
                }
            }while (_loc_3.upgrade && _loc_4 && _loc_3.upgrade.isValid())
            return _loc_2;
        }//end

        public static Array  getFullUpgradeChain (String param1 )
        {
            Array _loc_2 =new Array();
            _loc_3 =Global.gameSettings().getItemByName(param1 );
            while (_loc_3)
            {

                _loc_2.push(_loc_3);
                if (_loc_3.upgrade && _loc_3.upgrade.isValid())
                {
                    _loc_3 = Global.gameSettings().getItemByName(_loc_3.upgrade.newItemName);
                    continue;
                }
                _loc_3 = null;
            }
            return _loc_2;
        }//end

        public static Array  getUpgradeChainNames (String param1 )
        {
            _loc_2 = getUpgradeChain(param1);
            int _loc_3 =0;
            while (_loc_3 < _loc_2.length())
            {

                _loc_2.put(_loc_3,  ((UpgradeDefinition)_loc_2.get(_loc_3)).m_newItemName);
                _loc_3++;
            }
            return _loc_2;
        }//end

        public static Array  getFullUpgradeChainNames (String param1 )
        {
            _loc_2 = getFullUpgradeChain(param1);
            int _loc_3 =0;
            while (_loc_3 < _loc_2.length())
            {

                _loc_2.put(_loc_3,  ((Item)_loc_2.get(_loc_3)).name);
                _loc_3++;
            }
            return _loc_2;
        }//end

        public static UpgradeDefinition  getNextUpgrade (String param1 )
        {
            _loc_2 =Global.gameSettings().getItemByName(param1 );
            if (_loc_2.upgrade && _loc_2.upgrade.isValid())
            {
                return _loc_2.upgrade;
            }
            return null;
        }//end

    }



