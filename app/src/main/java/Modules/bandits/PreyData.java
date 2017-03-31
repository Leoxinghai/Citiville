package Modules.bandits;

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

import Classes.doobers.*;
//import flash.utils.*;

    public class PreyData
    {
        protected int m_id ;
        protected String m_groupId ;
        protected String m_name ;
        protected String m_desc ;
        protected String m_portraitUrl ;
        protected String m_caughtUrl ;
        protected String m_thumbnailUrl ;
        protected String m_experimentName ;
        protected int m_experimentVariant ;
        protected Dictionary m_modes ;
        protected Array m_captureAnimations ;
        protected String m_itemName ="npc_bandit";
        protected String m_captureScene ="Modules.bandits.ArrestScene";
        protected String m_spawnSound ="moneybag";
        protected String m_captureSound ="arrest";
        protected String m_captureToolTipKey ="ToolTip_Capture";
        protected int m_requiredHubLevel =2;

        public  PreyData (String param1 ,Object param2 )
        {
            Object _loc_4 =null ;
            PreyModeData _loc_5 =null ;
            Object _loc_6 =null ;
            String _loc_7 =null ;
            int _loc_8 =0;
            String _loc_9 =null ;
            String _loc_10 =null ;
            this.m_modes = new Dictionary();
            this.m_captureAnimations = new Array();
            if (!param2)
            {
                this.m_id = -1;
                return;
            }
            this.m_id = param2.id;
            this.m_groupId = param1;
            _loc_3 =Global.gameSettings().getPreyData(param1 ,this.m_id );
            this.m_name = ZLoc.t("Prey", _loc_3.get("text").get("name"));
            this.m_desc = ZLoc.t("Prey", _loc_3.get("text").get("description"));
            this.m_portraitUrl = _loc_3.get("portrait");
            this.m_caughtUrl = _loc_3.get("caught");
            this.m_thumbnailUrl = _loc_3.get("thumbnail");
            this.m_requiredHubLevel = int(_loc_3.get("levelRequired"));
            this.m_experimentName = _loc_3.get("experimentName");
            this.m_experimentVariant = int(_loc_3.get("experimentVariant"));
            this.m_captureAnimations = _loc_3.get("captureAnimations") ? (String(_loc_3.get("captureAnimations")).split(",")) : (.get("idle"));
            this.m_itemName = _loc_3.get("itemName") ? (String(_loc_3.get("itemName"))) : ("npc_bandit");
            this.m_captureScene = _loc_3.get("captureScene") ? ("Modules.bandits." + String(_loc_3.get("captureScene"))) : ("Modules.bandits.ArrestScene");
            this.m_captureSound = _loc_3.get("captureSound") ? (_loc_3.get("captureSound")) : ("arrest");
            this.m_spawnSound = _loc_3.get("spawnSound") ? (_loc_3.get("spawnSound")) : ("moneybag");
            for(int i0 = 0; i0 < _loc_3.get("modes").size(); i0++)
            {
            		_loc_4 = _loc_3.get("modes").get(i0);

                _loc_5 = new PreyModeData();
                _loc_5.m_name = _loc_4.get("id");
                _loc_5.m_requiredHunters = int(_loc_4.get("huntersRequired"));
                for(int i0 = 0; i0 < _loc_4.get("rewards").size(); i0++)
                {
                		_loc_6 = _loc_4.get("rewards").get(i0);

                    _loc_7 = String(_loc_6.type);
                    if (_loc_7 != Doober.DOOBER_COLLECTABLE && _loc_7 != Doober.DOOBER_ITEM)
                    {
                        _loc_8 = int(_loc_6.value);
                        _loc_9 = _loc_6.specialDooberTypeTableName ? (_loc_6.specialDooberTypeTableName) : (null);
                        _loc_10 = Global.gameSettings().getDooberFromType(_loc_7, _loc_8, _loc_9);
                        _loc_5.m_doobers.push(.get(_loc_10, _loc_8));
                        if (_loc_5.m_rewardDooberTotals.get(_loc_7) == null)
                        {
                            _loc_5.m_rewardDooberTotals.put(_loc_7,  0);
                        }
                        _loc_5.m_rewardDooberTotals.put(_loc_7,  _loc_5.m_rewardDooberTotals.get(_loc_7) + _loc_8);
                        continue;
                    }
                    if (_loc_7 == Doober.DOOBER_ITEM)
                    {
                        _loc_5.m_rewardItems.push(String(_loc_6.value));
                        continue;
                    }
                    if (_loc_7 == Doober.DOOBER_COLLECTABLE)
                    {
                        _loc_5.m_doobers.push(.get(String(_loc_6.value), 1));
                    }
                }
                this.m_modes.put(_loc_5.name,  _loc_5);
            }
            return;
        }//end

        public int  id ()
        {
            return this.m_id;
        }//end

        public String  groupId ()
        {
            return this.m_groupId;
        }//end

        public String  itemName ()
        {
            return this.m_itemName;
        }//end

        public int  requiredHubLevel ()
        {
            return this.m_requiredHubLevel;
        }//end

        public String  experimentName ()
        {
            return this.m_experimentName;
        }//end

        public int  experimentVariant ()
        {
            return this.m_experimentVariant;
        }//end

        public String  name ()
        {
            return this.m_name;
        }//end

        public String  description ()
        {
            return this.m_desc;
        }//end

        public String  portraitUrl ()
        {
            return this.m_portraitUrl;
        }//end

        public String  caughtUrl ()
        {
            return this.m_caughtUrl;
        }//end

        public String  thumbnail ()
        {
            return this.m_thumbnailUrl;
        }//end

        public Array  captureAnimations ()
        {
            return this.m_captureAnimations;
        }//end

        public String  captureScene ()
        {
            return this.m_captureScene;
        }//end

        public String  captureSound ()
        {
            return this.m_captureSound;
        }//end

        public String  spawnSound ()
        {
            return this.m_spawnSound;
        }//end

        public int  getRequiredHunters ()
        {
            return this.m_modes.get(PreyManager.getHunterPreyMode(this.m_groupId).get("id")).m_requiredHunters;
        }//end

        public Array  getDoobers ()
        {
            return this.m_modes.get(PreyManager.getHunterPreyMode(this.m_groupId).get("id")).m_doobers;
        }//end

        public Array  getRewardItems ()
        {
            return this.m_modes.get(PreyManager.getHunterPreyMode(this.m_groupId).get("id")).m_rewardItems;
        }//end

        public Array  getRewardDooberTotals ()
        {
            return this.m_modes.get(PreyManager.getHunterPreyMode(this.m_groupId).get("id")).m_rewardDooberTotals;
        }//end

        public boolean  wasCaptured ()
        {
            if (PreyManager.getTypesPreyCaptured(this.m_groupId).get(this.id) == null)
            {
                return false;
            }
            return true;
        }//end

    }



