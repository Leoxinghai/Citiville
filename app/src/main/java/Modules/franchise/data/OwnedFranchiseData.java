package Modules.franchise.data;

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

import Classes.orders.*;
import Classes.util.*;
import Modules.franchise.display.*;

//import flash.utils.*;

    public class OwnedFranchiseData
    {
        private String m_franchiseType ;
        private String m_franchiseName ;
        private String m_pendingName ;
        private Dictionary m_locations ;
        private String m_franchiseSaleCommType ;
        private int m_franchiseSaleCommCost ;
        private boolean m_dailyBonusAvailable =true ;
        private int m_dailyBonusValue =0;
        private int m_dailyBonusLastCollect ;

        public  OwnedFranchiseData (String param1 ,String param2 ,String param3 ,Dictionary param4 ,int param5 )
        {
            int _loc_6 =0;
            int _loc_7 =0;
            this.m_franchiseType = param1;
            this.m_franchiseName = param2;
            this.m_pendingName = param3;
            this.m_locations = param4;
            if (this.m_franchiseType != "history" && this.m_franchiseType != "bus_dummy")
            {
                this.m_franchiseSaleCommType = Global.gameSettings().getItemByName(this.m_franchiseType).commodityName;
                this.m_franchiseSaleCommCost = Global.gameSettings().getItemByName(this.m_franchiseType).commodityReq / 2;
            }
            if (param5 && param4)
            {
                this.m_dailyBonusLastCollect = Math.floor(param5);
                _loc_6 = GlobalEngine.getTimer() / 1000;
                _loc_7 = FranchiseMenu.dailyCycleDelta;
                if (_loc_6 - this.m_dailyBonusLastCollect < _loc_7)
                {
                    this.m_dailyBonusAvailable = false;
                }
            }
            this.m_dailyBonusValue = this.getLocationCount();
            return;
        }//end

        public String  franchiseType ()
        {
            return this.m_franchiseType;
        }//end

        public String  franchiseName ()
        {
            return this.m_franchiseName;
        }//end

        public String  pendingName ()
        {
            return this.m_pendingName;
        }//end

        public Dictionary  locations ()
        {
            return this.m_locations;
        }//end

        public int  saleCost ()
        {
            return this.m_franchiseSaleCommCost;
        }//end

        public String  commType ()
        {
            return this.m_franchiseSaleCommType;
        }//end

        public boolean  dailyBonusAvailable ()
        {
            return this.m_dailyBonusAvailable;
        }//end

        public int  dailyBonusValue ()
        {
            return this.m_dailyBonusValue;
        }//end

        public int  dailyBonusLastCollect ()
        {
            return this.m_dailyBonusLastCollect;
        }//end

        public void  franchiseName (String param1 )
        {
            this.m_franchiseName = param1;
            return;
        }//end

        public void  pendingName (String param1 )
        {
            this.m_pendingName = param1;
            return;
        }//end

        public void  dailyBonusAvailable (boolean param1 )
        {
            this.m_dailyBonusAvailable = param1;
            return;
        }//end

        public void  dailyBonusLastCollect (int param1 )
        {
            this.m_dailyBonusLastCollect = param1;
            return;
        }//end

        public double  getLocationCount ()
        {
            FranchiseExpansionData _loc_2 =null ;
            double _loc_1 =0;
            for(int i0 = 0; i0 < this.locations.size(); i0++)
            {
            		_loc_2 = this.locations.get(i0);

                _loc_1 = _loc_1 + 1;
            }
            return _loc_1;
        }//end

        public OwnedFranchiseData  clone ()
        {
            return new OwnedFranchiseData(this.m_franchiseType, this.m_franchiseName, this.m_pendingName, this.cloneLocations(), this.m_dailyBonusLastCollect);
        }//end

        public void  merge (OwnedFranchiseData param1 )
        {
            FranchiseExpansionData _loc_2 =null ;
            if (param1 && param1.franchiseType == this.franchiseType)
            {
                for(int i0 = 0; i0 < param1.locations.size(); i0++)
                {
                		_loc_2 = param1.locations.get(i0);

                    if (!this.hasLocation(_loc_2.locationUid))
                    {
                        this.addLocation(_loc_2);
                    }
                }
            }
            return;
        }//end

        private Dictionary  cloneLocations ()
        {
            Object _loc_2 =null ;
            Object _loc_3 =null ;
            Dictionary _loc_1 =new Dictionary ();
            for(int i0 = 0; i0 < this.m_locations.size(); i0++)
            {
            		_loc_2 = this.m_locations.get(i0);

                _loc_3 = this.m_locations.get(_loc_2);
                _loc_1.put(_loc_2,  _loc_3);
            }
            return _loc_1;
        }//end

        public boolean  hasLocation (String param1 )
        {
            return this.locations && this.locations.get(param1) != null;
        }//end

        public void  addLocation (FranchiseExpansionData param1 )
        {
            this.locations.put(param1.locationUid,  param1);
            return;
        }//end

        public static OwnedFranchiseData  loadObject (Object param1 )
        {
            String _loc_3 =null ;
            String _loc_4 =null ;
            String _loc_5 =null ;
            Object _loc_6 =null ;
            double _loc_7 =0;
            Dictionary _loc_8 =null ;
            String _loc_9 =null ;
            String _loc_10 =null ;
            Object _loc_11 =null ;
            OwnedFranchiseData _loc_2 =null ;
            if (param1 !=null)
            {
                _loc_3 = param1.get("name");
                _loc_4 = param1.get("franchise_name");
                _loc_5 = param1.get("pendingName");
                _loc_6 = param1.get("locations");
                _loc_7 = param1.get("time_last_collected");
                _loc_8 = new Dictionary();
                for(int i0 = 0; i0 < _loc_6.size(); i0++)
                {
                		_loc_9 = _loc_6.get(i0);

                    _loc_10 = GameUtil.formatServerUid(_loc_9);
                    if (_loc_9 == "i-1")
                    {
                        _loc_10 = "-1";
                    }
                    _loc_11 = _loc_6.get(_loc_9);
                    _loc_8.put(_loc_10,  FranchiseExpansionData.loadObjectWithTypeAndLocation(_loc_3, _loc_10, _loc_11));
                }
                _loc_2 = new OwnedFranchiseData(_loc_3, _loc_4, _loc_5, _loc_8, _loc_7);
            }
            return _loc_2;
        }//end

        public static OwnedFranchiseData  loadPendingObject (Vector param1 .<LotOrder >)
        {
            LotOrder _loc_3 =null ;
            String _loc_4 =null ;
            String _loc_5 =null ;
            String _loc_6 =null ;
            Dictionary _loc_7 =null ;
            LotOrder _loc_8 =null ;
            String _loc_9 =null ;
            OwnedFranchiseData _loc_2 =null ;
            if (param1 && param1.length())
            {
                _loc_3 =(LotOrder) param1.get(0);
                _loc_4 = _loc_3.getResourceType();
                _loc_5 = _loc_3.getOrderResourceName();
                _loc_6 = null;
                _loc_7 = new Dictionary();
                for(int i0 = 0; i0 < param1.size(); i0++)
                {
                		_loc_8 = param1.get(i0);

                    _loc_9 = _loc_8.getRecipientID();
                    _loc_7.put(_loc_9,  FranchiseExpansionData.loadPendingObject(_loc_8));
                }
                _loc_2 = new OwnedFranchiseData(_loc_4, _loc_5, _loc_6, _loc_7, 0);
            }
            return _loc_2;
        }//end

        public static OwnedFranchiseData  loadCitySamObject (String param1 )
        {
            _loc_2 = param1+"_citySamName";
            _loc_3 = ZLoc.t("Items",_loc_2 );
            Dictionary _loc_4 =new Dictionary ();
            _loc_5 = param1.length ;
            String _loc_6 =null ;
            int _loc_7 =0;
            while (_loc_7 < _loc_5)
            {

                _loc_4.put(_loc_7.toString(),  FranchiseExpansionData.loadCitySamObject(param1, _loc_7));
                _loc_7++;
            }
            return new OwnedFranchiseData(param1, _loc_3, _loc_6, _loc_4, 0);
        }//end

    }



