package Modules.remodel;

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
import Classes.sim.*;
import GameMode.*;
//import flash.utils.*;

    public class RemodelDefinition
    {
        protected String m_baseItemName ;
        protected String m_remodelItemName ;
        protected String m_requirements ;
        protected String m_gateName ;
        protected Dictionary m_rewards ;
        protected int m_overrideCost ;
        protected int m_overrideCash ;
        protected boolean m_isBaseModel ;

        public  RemodelDefinition (String param1 ,String param2 ,String param3 =null ,String param4 =null ,int param5 =0,int param6 =0,boolean param7 =false )
        {
            this.m_baseItemName = param1;
            this.m_remodelItemName = param2;
            this.m_requirements = param3;
            this.m_gateName = param4;
            this.m_overrideCost = param5;
            this.m_overrideCash = param6;
            this.m_isBaseModel = param7;
            return;
        }//end

        public String  baseItemName ()
        {
            return this.m_baseItemName;
        }//end

        public String  remodelItemName ()
        {
            return this.m_remodelItemName;
        }//end

        public String  requirementsName ()
        {
            return this.m_requirements;
        }//end

        public boolean  isBaseModel ()
        {
            return this.m_isBaseModel;
        }//end

        public Requirements  requirements ()
        {
            Requirements _loc_1 =null ;
            _loc_2 =Global.gameSettings().getItemByName(this.m_remodelItemName );
            if (_loc_2)
            {
                _loc_1 = _loc_2.getRequirementsByName(this.m_requirements);
            }
            return _loc_1;
        }//end

        public String  gateName ()
        {
            return this.m_gateName;
        }//end

        public void  setReward (String param1 ,int param2 )
        {
            if (param2 != 0)
            {
                this.m_rewards.put(param1,  param2);
            }
            else
            {
                this.m_rewards.put(param1,  null);
            }
            return;
        }//end

        public UserResourceType  getPurchaseType ()
        {
            return Global.gameSettings().getItemByName(this.remodelItemName).getPurchaseType();
        }//end

        public int  getPurchaseAmount (UserResourceType param1 )
        {
            if (this.useOverridePrice(param1))
            {
                return this.getOverridePrice(param1);
            }
            return Global.gameSettings().getItemByName(this.remodelItemName).getPurchaseAmount(param1);
        }//end

        public boolean  useOverridePrice (UserResourceType param1 )
        {
            if (Global.world.getTopGameMode() instanceof GMRemodel)
            {
                switch(param1)
                {
                    case UserResourceType.get(UserResourceType.COIN):
                    {
                        return this.m_overrideCost > 0;
                    }
                    case UserResourceType.get(UserResourceType.CASH):
                    {
                        return this.m_overrideCash > 0;
                    }
                    default:
                    {
                        break;
                    }
                }
            }
            return false;
        }//end

        public int  getOverridePrice (UserResourceType param1 )
        {
            switch(param1)
            {
                case UserResourceType.get(UserResourceType.COIN):
                {
                    return this.m_overrideCost;
                }
                case UserResourceType.get(UserResourceType.CASH):
                {
                    return this.m_overrideCash;
                }
                default:
                {
                    break;
                }
            }
            return 0;
        }//end

        public static RemodelDefinition  parse (XML param1 ,String param2 )
        {
            RemodelDefinition _loc_3 =null ;
            String _loc_4 =null ;
            String _loc_5 =null ;
            boolean _loc_6 =false ;
            if (param1.attribute("item").length() > 0)
            {
                _loc_4 = param1.attribute("gateName");
                _loc_5 = param1.attribute("requirementsName");
                _loc_6 = false;
                if (param1.attribute("isBaseModel").length() == 1)
                {
                    _loc_6 = param1.attribute("isBaseModel") == "true";
                }
                _loc_3 = new RemodelDefinition(param2, param1.@item, _loc_5, _loc_4, int(param1.@overrideCost), int(param1.@overrideCash), _loc_6);
            }
            return _loc_3;
        }//end

        public static Array  getRemodelItemsForItem (String param1 )
        {
            RemodelDefinition _loc_4 =null ;
            Array _loc_2 =new Array();
            _loc_3 =Global.gameSettings().getItemByName(param1 );
            if (_loc_3)
            {
                for(int i0 = 0; i0 < _loc_3.getAllRemodelDefinitions().size(); i0++)
                {
                		_loc_4 = _loc_3.getAllRemodelDefinitions().get(i0);

                    _loc_2.push(_loc_4.remodelItemName);
                }
            }
            return _loc_2;
        }//end

    }



