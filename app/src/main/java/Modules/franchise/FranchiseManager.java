package Modules.franchise;

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
import Classes.inventory.*;
import Classes.orders.*;
import Classes.sim.*;
import Classes.util.*;
import Display.*;
import Display.DialogUI.*;
import Display.MarketUI.*;
import Engine.Managers.*;
import GameMode.*;
import Modules.franchise.data.*;
import Modules.franchise.transactions.*;
import Modules.stats.data.*;
import Modules.stats.experiments.*;
import Modules.stats.types.*;

import com.adobe.utils.*;
//import flash.utils.*;
import Modules.stats.*;
import root.Global;
import java.util.Vector;

public class FranchiseManager implements IStatsTarget
    {
        private FranchiseData m_franchiseData ;
        private FranchiseDataModel m_model ;
        private FranchiseDataModel m_worldOwnerModel ;
        private boolean m_placementMode =false ;
        private boolean m_cacheFranchises =false ;
        private static  int BASE_SIGNATURE_REQUIREMENT =3;
        private static  int CITY_SIGNATURE_REQUIREMENT =5;
        private static  int MAX_SIGNATURE_REQUIREMENT =10;
        public static  String CITY_ITEM_NAME ="cityName";

        public  FranchiseManager ()
        {
            this.m_cacheFranchises = Global.experimentManager.inPerfExperiment(ExperimentDefinitions.EXPERIMENT_OPTIMIZE_FRAMERATE_Q3, ExperimentDefinitions.EXPERIMENT_OPTIMIZE_FRANCHISES);
            return;
        }//end

        private boolean  cacheFranchises ()
        {
            return this.m_cacheFranchises;
        }//end

        public boolean  placementMode ()
        {
            return this.m_placementMode;
        }//end

        public boolean  isFranchiseItem (Item param1 )
        {
            boolean _loc_2 =false ;
            if (param1.type == "business" && Global.world.ownerId != Global.player.uid)
            {
                _loc_2 = true;
            }
            return _loc_2;
        }//end

        public void  placementMode (boolean param1 )
        {
            this.m_placementMode = param1;
            if (!this.m_placementMode)
            {
                Global.guide.removeMask();
                if (!Global.isTransitioningWorld)
                {
                    Global.world.addGameMode(new GMVisit(Global.world.ownerId), true);
                }
                UI.closeCatalog();
            }
            return;
        }//end

        public Array  eligibleFranchises ()
        {
            _loc_1 =Global.gameSettings().getPlaceableItems("business");
            return _loc_1.filter(this.isFranchisable);
        }//end

        public FranchiseDataModel  model ()
        {
            return this.m_model;
        }//end

        public FranchiseDataModel  worldOwnerModel ()
        {
            FranchiseDataModel _loc_1 =null ;
            if (Global.world.ownerId == Global.player.uid)
            {
                _loc_1 = this.m_model;
            }
            else
            {
                _loc_1 = this.m_worldOwnerModel;
            }
            return _loc_1;
        }//end

        public double  nextFranchiseUnlock ()
        {
            return Math.ceil((Global.player.level + 1) / 10) * 10;
        }//end

        public boolean  isFranchisable (Item param1 ,...args )
        {
            argsvalue = param1(.className!= "LotSite");
            _loc_4 = BuyLogic.canFranchise(param1);
            _loc_5 = BuyLogic.franchiseCountLevelCheck(param1);
            _loc_6 = BuyLogic.hasFranchiseCheck(param1);
            return (argsvalue && _loc_4 && _loc_5 && _loc_6);
        }//end

        public boolean  isFranchiseLocked ()
        {
            _loc_1 = this.getFranchiseCount ();
            _loc_2 = this.getFranchiseCap ();
            return _loc_1 >= _loc_2;
        }//end

        public void  loadObject (Object param1 )
        {
            Object _loc_3 =null ;
            Array _loc_4 =null ;
            Item _loc_5 =null ;
            OwnedFranchiseData _loc_6 =null ;
            Vector<OwnedFranchiseData> _loc_2 =new Vector<OwnedFranchiseData>();
            for(int i0 = 0; i0 < param1.size(); i0++)
            {
            		_loc_3 = param1.get(i0);

                _loc_6 = OwnedFranchiseData.loadObject(_loc_3);
                if (_loc_6.franchiseType != "history")
                {
                    _loc_2.push(_loc_6);
                }
            }
            this.m_franchiseData = new FranchiseData(_loc_2, null);
            this.m_model = new FranchiseDataModel(Global.player.uid, Global.player.firstName, this.m_franchiseData);
            _loc_4 = Global.gameSettings().getItemsByType("decoration");
            for(int i0 = 0; i0 < _loc_4.size(); i0++)
            {
            		_loc_5 = _loc_4.get(i0);

                if (_loc_5.className == "Headquarter")
                {
                    _loc_5.localizedName = this.getHeadquartersName(_loc_5.name);
                }
            }
            return;
        }//end

        public String  getMapOwnerFirstName (String param1 )
        {
            String _loc_4 =null ;
            String _loc_2 =null ;
            _loc_3 =Global.player.findFriendById(param1 );
            if (_loc_3 != null)
            {
                _loc_2 = _loc_3.firstName;
            }
            else
            {
                _loc_2 = ZLoc.t("Dialogs", "DefaultFriendName");
            }
            return _loc_2;
        }//end

        public void  loadWorldObject (Object param1 ,String param2 )
        {
            Vector _loc_3.<OwnedFranchiseData >=null ;
            FranchiseData _loc_4 =null ;
            Object _loc_5 =null ;
            OwnedFranchiseData _loc_6 =null ;
            if (param2 == GameWorld.CITY_SAM_OWNER_ID)
            {
                _loc_3 = this.getCitySamFranchiseData();
            }
            else
            {
                _loc_3 = new Vector<OwnedFranchiseData>();
                for(int i0 = 0; i0 < param1.size(); i0++)
                {
                		_loc_5 = param1.get(i0);

                    _loc_6 = OwnedFranchiseData.loadObject(_loc_5);
                    if (_loc_6.franchiseType != "history")
                    {
                        _loc_3.push(_loc_6);
                    }
                }
            }
            _loc_4 = new FranchiseData(_loc_3, null);
            this.m_worldOwnerModel = new FranchiseDataModel(param2, Global.player.getFriendFirstName(param2), _loc_4);
            return;
        }//end

        private OwnedFranchiseData Vector  getCitySamFranchiseData ().<>
        {
            Item _loc_3 =null ;
            String _loc_4 =null ;
            Vector<OwnedFranchiseData> _loc_1 =new Vector<OwnedFranchiseData>();
            _loc_2 =Global.gameSettings().getItemsByType("business");
            for(int i0 = 0; i0 < _loc_2.size(); i0++)
            {
            		_loc_3 = _loc_2.get(i0);

                _loc_4 = _loc_3.name;
                _loc_1.push(OwnedFranchiseData.loadCitySamObject(_loc_4));
            }
            return _loc_1;
        }//end

        public void  collectDailyBonus (String param1 )
        {
            _loc_2 = this.m_model.getOwnedFranchise(param1 );
            _loc_2.dailyBonusAvailable = false;
            _loc_2.dailyBonusLastCollect = GlobalEngine.getTimer() / 1000;
            return;
        }//end

        public void  collect (String param1 ,String param2 )
        {
            _loc_3 = this.m_model.getOwnedFranchise(param1 );
            _loc_4 = _loc_3.locations.get(param2) ;
            _loc_5 = this.m_model.getHeadquartersType(_loc_4.franchiseType );
            _loc_4.timeLastSupplied = GlobalEngine.getTimer() / 1000;
            if (_loc_4.locationUid != "-1")
            {
                StatsManager.social(StatsCounterType.FRANCHISES, _loc_4.locationUid, "sent_sale", _loc_4.franchiseType);
            }
            return;
        }//end

        public int  getFranchiseCountByLocation (String param1 )
        {
            return 0;
        }//end

        public Vector<String> getNewHeadquarterTypes ()
        {
            String _loc_3 =null ;
            OwnedFranchiseData _loc_5 =null ;
            Dictionary _loc_6 =null ;
            Array _loc_7 =null ;
            int _loc_8 =0;
            boolean _loc_9 =false ;
            String _loc_10 =null ;
            int _loc_11 =0;
            Array _loc_12 =null ;
            MapResource _loc_13 =null ;
            Array _loc_14 =null ;
            ConstructionSite _loc_15 =null ;
            int _loc_16 =0;
            Vector<String> _loc_1=new Vector<String>();
            Array _loc_2 =new Array();
            _loc_4 = this.m_model.getOwnedFranchises ();
            for(int i0 = 0; i0 < _loc_4.size(); i0++)
            {
            		_loc_5 = _loc_4.get(i0);

                _loc_6 = _loc_5.locations;
                _loc_7 = DictionaryUtil.getKeys(_loc_6);
                _loc_8 = _loc_7.length;
                if (_loc_8 > 0)
                {
                    _loc_9 = Boolean(_loc_8 == 1 && _loc_6.get(GameWorld.CITY_SAM_OWNER_ID) != null);
                    if (!_loc_9)
                    {
                        _loc_10 = _loc_5.franchiseType;
                        _loc_3 = this.m_model.getHeadquartersType(_loc_10);
                        if (_loc_3)
                        {
                            _loc_11 = Global.player.inventory.getItemCountByName(_loc_3);
                            if (_loc_11 <= 0)
                            {
                                _loc_2.put(_loc_2.length,  _loc_3);
                            }
                        }
                    }
                }
            }
            if (_loc_2.length > 0 && !Global.isVisiting())
            {
                _loc_12 = Global.world.getObjectsByNames(_loc_2);
                for(int i0 = 0; i0 < _loc_12.size(); i0++)
                {
                		_loc_13 = _loc_12.get(i0);

                    _loc_3 = _loc_13.getItem().name;
                    _loc_16 = _loc_2.indexOf(_loc_3);
                    if (_loc_16 >= 0)
                    {
                        _loc_2.splice(_loc_16, 1);
                    }
                }
                _loc_14 = Global.world.getObjectsByClass(ConstructionSite);
                for(int i0 = 0; i0 < _loc_14.size(); i0++)
                {
                		_loc_15 = _loc_14.get(i0);

                    _loc_3 = _loc_15.targetName;
                    _loc_16 = _loc_2.indexOf(_loc_3);
                    if (_loc_16 >= 0)
                    {
                        _loc_2.splice(_loc_16, 1);
                    }
                }
                for(int i0 = 0; i0 < _loc_2.size(); i0++)
                {
                		_loc_3 = _loc_2.get(i0);

                    _loc_1.put(_loc_1.length,  _loc_3);
                }
            }
            return _loc_1;
        }//end

        public double  getHeadquartersHeight (String param1 ,String param2 )
        {
            FranchiseDataModel _loc_4 =null ;
            _loc_3 = this.getFranchiseTypeFromHeadquarters(param1 );
            if (param2 == null || param2 == Global.player.uid)
            {
                _loc_4 = this.m_model;
            }
            else if (param2 == Global.world.ownerId)
            {
                _loc_4 = this.m_worldOwnerModel;
            }
            _loc_5 = _loc_4==null ? (1) : (_loc_4.getFranchiseCountByType(_loc_3));
            _loc_6 = _loc_4(==null ? (1) : (_loc_4.getFranchiseCountByType(_loc_3))) * this.getHeadquartersHeightPerExpansion(param1);
            _loc_7 = this.getHeadquartersHeightLimit(param1 );
            _loc_8 = _loc_6;
            if (_loc_7 >= 0)
            {
                _loc_8 = Math.min(_loc_8, _loc_7);
            }
            return _loc_8;
        }//end

        public String  getFranchiseTypeFromHeadquarters (String param1 )
        {
            Array _loc_7 =null ;
            Item _loc_8 =null ;
            String _loc_2 =null ;
            _loc_3 = param1.split("_");
            _loc_4 = _loc_3&& _loc_3.length ? ((String)_loc_3.get((_loc_3.length - 1))) : ("");
            _loc_5 = _loc_4"bus_"+;
            _loc_6 =Global.gameSettings().getItemByName(_loc_5 );
            if (Global.gameSettings().getItemByName(_loc_5) && _loc_6.headquartersName == param1)
            {
                _loc_2 = _loc_5;
            }
            else
            {
                _loc_7 = Global.gameSettings().getItemsByType("business");
                for(int i0 = 0; i0 < _loc_7.size(); i0++)
                {
                		_loc_8 = _loc_7.get(i0);

                    if (_loc_8.headquartersName == param1)
                    {
                        _loc_2 = _loc_8.name;
                        break;
                    }
                }
            }
            return _loc_2;
        }//end

        public Business  getBusinessFromType (String param1 )
        {
            Business _loc_4 =null ;
            Business _loc_2 =null ;
            _loc_3 =Global.world.getObjectsByClass(Business );
            for(int i0 = 0; i0 < _loc_3.size(); i0++)
            {
            		_loc_4 = _loc_3.get(i0);

                if (_loc_4.franchiseType == param1)
                {
                    _loc_2 = _loc_4;
                    break;
                }
            }
            return _loc_2;
        }//end

        public Business  getPendingFranchiseFromTypeAndOwner (String param1 ,String param2 )
        {
            Business _loc_5 =null ;
            Business _loc_3 =null ;
            _loc_4 =Global.world.getObjectsByClass(Business );
            for(int i0 = 0; i0 < _loc_4.size(); i0++)
            {
            		_loc_5 = _loc_4.get(i0);

                if (_loc_5.franchiseType == param1 && _loc_5.getBusinessOwner() == param2)
                {
                    _loc_3 = _loc_5;
                    break;
                }
            }
            return _loc_3;
        }//end

        public FranchiseDataModel  getDataModel (String param1 )
        {
            FranchiseDataModel _loc_2 =null ;
            if (this.m_model && this.m_model.ownerUid == param1)
            {
                _loc_2 = this.m_model;
            }
            else if (this.m_worldOwnerModel && this.m_worldOwnerModel.ownerUid == param1)
            {
                _loc_2 = this.m_worldOwnerModel;
            }
            return _loc_2;
        }//end

        public String  getFranchiseName (String param1 ,String param2 )
        {
            String _loc_3 =null ;
            if (param2 == Global.player.uid)
            {
                _loc_3 = this.m_model.getFranchiseName(param1);
            }
            else if (param2 == Global.world.ownerId)
            {
                _loc_3 = this.m_worldOwnerModel.getFranchiseName(param1);
            }
            return _loc_3;
        }//end

        public String  getPendingName (String param1 ,String param2 )
        {
            String _loc_3 =null ;
            if (param1 == CITY_ITEM_NAME)
            {
                _loc_3 = Global.player.pendingCityName;
            }
            else if (param2 == Global.player.uid)
            {
                _loc_3 = this.m_model.getPendingName(param1);
            }
            else if (param2 == Global.world.ownerId)
            {
                _loc_3 = this.m_worldOwnerModel.getPendingName(param1);
            }
            return _loc_3;
        }//end

        public String  getHeadquartersFriendlyName (String param1 )
        {
            return "no name";
        }//end

        public OwnedFranchiseData  getFranchise (String param1 ,Vector param2 .<OwnedFranchiseData >=null )
        {
            OwnedFranchiseData _loc_4 =null ;
            OwnedFranchiseData _loc_3 =null ;
            if (!param2)
            {
                param2 = this.getAllFranchises();
            }
            for(int i0 = 0; i0 < param2.size(); i0++)
            {
            		_loc_4 = param2.get(i0);

                if (_loc_4.franchiseType == param1)
                {
                    _loc_3 = _loc_4;
                    break;
                }
            }
            return _loc_3;
        }//end

        public OwnedFranchiseData Vector  getAllFranchises ().<>
        {
            _loc_1 = this.m_model.getOwnedFranchises ();
            _loc_2 = this.getPendingSentFranchiseOrders ();
            _loc_3 = this.mergeOwnedFranchises(_loc_1 ,_loc_2 );
            return _loc_3;
        }//end

        public double  getFranchiseCount ()
        {
            OwnedFranchiseData _loc_3 =null ;
            double _loc_1 =0;
            _loc_2 = this.getAllFranchises ();
            for(int i0 = 0; i0 < _loc_2.size(); i0++)
            {
            		_loc_3 = _loc_2.get(i0);

                if (_loc_3.getLocationCount() > 0)
                {
                    _loc_1 = _loc_1 + 1;
                }
            }
            return _loc_1;
        }//end

        public double  getAllFranchisesLocationCount ()
        {
            OwnedFranchiseData _loc_3 =null ;
            double _loc_1 =0;
            _loc_2 = this.getAllFranchises ();
            for(int i0 = 0; i0 < _loc_2.size(); i0++)
            {
            		_loc_3 = _loc_2.get(i0);

                _loc_1 = _loc_1 + _loc_3.getLocationCount();
            }
            return _loc_1;
        }//end

        public double  getAllFranchiseAcceptedLocationsCount ()
        {
            OwnedFranchiseData _loc_3 =null ;
            FranchiseExpansionData _loc_4 =null ;
            double _loc_1 =0;
            _loc_2 = this.getAllFranchises ();
            for(int i0 = 0; i0 < _loc_2.size(); i0++)
            {
            		_loc_3 = _loc_2.get(i0);

                for(int i0 = 0; i0 < _loc_3.locations.size(); i0++)
                {
                		_loc_4 = _loc_3.locations.get(i0);

                    if (_loc_4.isOpen())
                    {
                        _loc_1 = _loc_1 + 1;
                    }
                }
            }
            return _loc_1;
        }//end

        public double  getFranchiseAcceptedLocationsCount (String param1 )
        {
            FranchiseExpansionData _loc_4 =null ;
            double _loc_2 =0;
            _loc_3 = this.getFranchise(param1 );
            for(int i0 = 0; i0 < _loc_3.locations.size(); i0++)
            {
            		_loc_4 = _loc_3.locations.get(i0);

                if (_loc_4.isOpen())
                {
                    _loc_2 = _loc_2 + 1;
                }
            }
            return _loc_2;
        }//end

        public double  getFranchiseCap ()
        {
            return (Math.floor(Global.player.level / 10) + 1);
        }//end

        public boolean  isPendingFranchise (String param1 )
        {
            FranchiseExpansionData _loc_3 =null ;
            _loc_2 = this.getFranchise(param1 );
            for(int i0 = 0; i0 < _loc_2.locations.size(); i0++)
            {
            		_loc_3 = _loc_2.locations.get(i0);

                if (_loc_3.isOpen())
                {
                    return false;
                }
            }
            return true;
        }//end

        public double  mapElapsedToCompleteness (double param1 )
        {
            double _loc_5 =0;
            Dictionary _loc_2 =new Dictionary ();
            _loc_2.put(0,  100);
            _loc_2.put(72,  50);
            _loc_2.put(96,  0);
            _loc_3 = _loc_2.get(0);
            _loc_4 = DictionaryUtil.getKeys(_loc_2);
            DictionaryUtil.getKeys(_loc_2).reverse();
            for(int i0 = 0; i0 < _loc_4.size(); i0++)
            {
            		_loc_5 = _loc_4.get(i0);

                if (param1 >= _loc_5)
                {
                    _loc_3 = _loc_2.get(_loc_5);
                    break;
                }
            }
            return _loc_3;
        }//end

        public FranchiseExpansionData Vector  getAllFranchisesByType (String param1 ).<>
        {
            FranchiseExpansionData _loc_4 =null ;
            _loc_2 = this.m_model.getFranchisesByType(param1 ).slice ();
            _loc_3 = this.getPendingSentFranchiseOrderLocationsByType(param1 );
            for(int i0 = 0; i0 < _loc_3.size(); i0++)
            {
            		_loc_4 = _loc_3.get(i0);

                _loc_2.push(_loc_4);
            }
            return _loc_2;
        }//end

        public int  getRequiredSignatures (String param1 ,Vector param2 .<OwnedFranchiseData >=null )
        {
            OwnedFranchiseData _loc_4 =null ;
            int _loc_3 =0;
            if (param1 == CITY_ITEM_NAME)
            {
                _loc_3 = CITY_SIGNATURE_REQUIREMENT;
            }
            else
            {
                _loc_3 = BASE_SIGNATURE_REQUIREMENT;
                _loc_4 = this.getFranchise(param1, param2);
                if (_loc_4)
                {
                    _loc_3 = _loc_3 + _loc_4.getLocationCount();
                }
            }
            if (_loc_3 > MAX_SIGNATURE_REQUIREMENT)
            {
                _loc_3 = MAX_SIGNATURE_REQUIREMENT;
            }
            return _loc_3;
        }//end

        public int  getSignatureCount (String param1 )
        {
            return Global.player.requestInventory.getItemCount(RequestItemType.SIGNATURE, param1);
        }//end

        public boolean  isSignatureComplete (String param1 ,Vector param2 .<OwnedFranchiseData >=null )
        {
            OwnedFranchiseData _loc_4 =null ;
            String _loc_3 =null ;
            if (param1 == CITY_ITEM_NAME)
            {
                _loc_3 = Global.player.pendingCityName;
            }
            else
            {
                _loc_4 = this.getFranchise(param1, param2);
                if (_loc_4 != null)
                {
                    _loc_3 = _loc_4.pendingName;
                }
            }
            return _loc_3 != null && this.getSignatureCount(param1) >= this.getRequiredSignatures(param1, this.cacheFranchises ? (param2) : (null));
        }//end

        public int  getCompletedSignatureCount ()
        {
            OwnedFranchiseData _loc_3 =null ;
            int _loc_1 =0;
            _loc_2 = this.getAllFranchises ();
            for(int i0 = 0; i0 < _loc_2.size(); i0++)
            {
            		_loc_3 = _loc_2.get(i0);

                if (this.isSignatureComplete(_loc_3.franchiseType, this.cacheFranchises ? (_loc_2) : (null)))
                {
                    _loc_1++;
                }
            }
            if (this.isSignatureComplete(CITY_ITEM_NAME))
            {
                _loc_1++;
            }
            return _loc_1;
        }//end

        public StatsCountData Vector  getStatsCounterObject ().<>
        {
            Business _loc_7 =null ;
            int _loc_8 =0;
            Vector _loc_9.<String >=null ;
            String _loc_10 =null ;
            int _loc_11 =0;
            String _loc_12 =null ;
            int _loc_13 =0;
            Array _loc_14 =null ;
            Headquarter _loc_15 =null ;
            int _loc_16 =0;
            String _loc_17 =null ;
            OwnedFranchiseData _loc_18 =null ;
            String _loc_19 =null ;
            int _loc_20 =0;
            int _loc_21 =0;
            int _loc_22 =0;
            Vector _loc_23.<FranchiseExpansionData >=null ;
            FranchiseExpansionData _loc_24 =null ;
            Vector<StatsCountData> _loc_1 =new Vector<StatsCountData>();
            _loc_2 =Global.world.citySim.lotManager ;





return _loc_1;

            _loc_5 =Global.world.getObjectsByClass(Business );
            int _loc_6 =0;
            for(int i0 = 0; i0 < _loc_5.size(); i0++)
            {
            		_loc_7 = _loc_5.get(i0);

                if (_loc_7.isFranchise())
                {
                    _loc_6++;
                }
            }
            _loc_1.push(new StatsCountData(new StatsOntology("num_running_franchises_on_user_map"), _loc_6));
            _loc_8 = 0;
            _loc_9 = this.m_model.getFranchiseTypes();
            for(int i0 = 0; i0 < _loc_9.size(); i0++)
            {
            		_loc_10 = _loc_9.get(i0);

                _loc_18 = this.m_model.getOwnedFranchise(_loc_10);
                _loc_8 = _loc_8 + _loc_18.getLocationCount();
            }
            _loc_1.push(new StatsCountData(new StatsOntology("num_running_franchises_on_friend_map"), _loc_8));
            _loc_11 = 0;
            for(int i0 = 0; i0 < _loc_9.size(); i0++)
            {
            		_loc_12 = _loc_9.get(i0);

                _loc_19 = this.m_model.getHeadquartersType(_loc_12);
                _loc_20 = Global.player.inventory.getItemCountByName(_loc_19);
                if (_loc_20 > 0)
                {
                    _loc_21 = this.getHeadquartersHeight(_loc_19);
                    _loc_1.push(new StatsCountData(new StatsOntology("franchise_HQ", "HQ_unplaced", _loc_21.toString()), _loc_20));
                }
            }
            _loc_13 = 0;
            _loc_14 = Global.world.getObjectsByClass(Headquarter);
            for(int i0 = 0; i0 < _loc_14.size(); i0++)
            {
            		_loc_15 = _loc_14.get(i0);

                _loc_22 = _loc_15.floorCount;
                _loc_1.push(new StatsCountData(new StatsOntology("franchise_HQ", "HQ_placed", _loc_22.toString())));
            }
            _loc_16 = 0;
            for(int i0 = 0; i0 < _loc_9.size(); i0++)
            {
            		_loc_17 = _loc_9.get(i0);

                _loc_23 = this.m_model.getFranchisesByType(_loc_17);
                for(int i0 = 0; i0 < _loc_23.size(); i0++)
                {
                		_loc_24 = _loc_23.get(i0);

                    if (_loc_24.commodityLeft > 0)
                    {
                        _loc_16++;
                    }
                }
            }
            _loc_1.push(new StatsCountData(new StatsOntology("pending_sent_sales"), _loc_16));
            return _loc_1;
        }//end

        private Vector<OwnedFranchiseData> mergeOwnedFranchises (Vector<OwnedFranchiseData> param1 ,Vector<OwnedFranchiseData> param2)
        {
            OwnedFranchiseData _loc_4 =null ;
            OwnedFranchiseData _loc_5 =null ;
            OwnedFranchiseData _loc_6 =null ;
            OwnedFranchiseData _loc_7 =null ;
            Vector<OwnedFranchiseData> _loc_3=new Vector<OwnedFranchiseData>();
            for(int i0=0; i0<param1.size();i0++)
            {
                _loc_4 = param1.get(i0);
                _loc_3.add(_loc_4.clone());
            }
            for(int i0 = 0; i0 < param2.size(); i0++)
            {
            		_loc_5 = param2.get(i0);

                _loc_6 = null;
                for(int i0 = 0; i0 < _loc_3.size(); i0++)
                {
                		_loc_7 = _loc_3.get(i0);

                    if (_loc_7.franchiseType == _loc_5.franchiseType)
                    {
                        _loc_6 = _loc_7;
                        _loc_6.merge(_loc_5);
                        break;
                    }
                }
                if (!_loc_6)
                {
                    _loc_3.push(_loc_5);
                }
            }
            return _loc_3;
        }//end

        private OwnedFranchiseData  getPendingSentFranchise (String param1 )
        {
            return this.getPendingFranchise(param1, this.getPendingSentFranchiseOrders());
        }//end

        private OwnedFranchiseData  getPendingReceivedFranchise (String param1 )
        {
            return this.getPendingFranchise(param1, this.getPendingReceivedFranchiseOrders());
        }//end

        private OwnedFranchiseData  getPendingFranchise (String param1 ,Vector param2 .<OwnedFranchiseData >)
        {
            OwnedFranchiseData _loc_4 =null ;
            OwnedFranchiseData _loc_3 =null ;
            for(int i0 = 0; i0 < param2.size(); i0++)
            {
            		_loc_4 = param2.get(i0);

                if (_loc_4.franchiseType == param1)
                {
                    _loc_3 = _loc_4;
                    break;
                }
            }
            return _loc_3;
        }//end

        public LotOrder  getPendingSentFranchiseOrderForNeighbor (String param1 )
        {
            LotOrder _loc_3 =null ;
            _loc_2 =Global.world.citySim.lotManager.sentPendingOrders ;
            for(int i0 = 0; i0 < _loc_2.size(); i0++)
            {
            		_loc_3 = _loc_2.get(i0);

                if (_loc_3.getRecipientID() == param1)
                {
                    return _loc_3;
                }
            }
            return null;
        }//end

        public OwnedFranchiseData Vector  getPendingSentFranchiseOrders ().<>
        {
            String _loc_3 =null ;
            Vector _loc_4.<LotOrder >=null ;
            OwnedFranchiseData _loc_5 =null ;
            Vector<OwnedFranchiseData> _loc_1 =new Vector<OwnedFranchiseData>();
            _loc_2 = this.getPendingSentFranchiseOrderTypes ();
            for(int i0 = 0; i0 < _loc_2.size(); i0++)
            {
            		_loc_3 = _loc_2.get(i0);

                _loc_4 = this.getPendingSentFranchiseOrdersByType(_loc_3);
                _loc_5 = OwnedFranchiseData.loadPendingObject(_loc_4);
                _loc_1.push(_loc_5);
            }
            return _loc_1;
        }//end

        public OwnedFranchiseData Vector  getPendingReceivedFranchiseOrders ().<>
        {
            String _loc_3 =null ;
            Vector _loc_4.<LotOrder >=null ;
            OwnedFranchiseData _loc_5 =null ;
            Vector<OwnedFranchiseData> _loc_1 =new Vector<OwnedFranchiseData>();
            _loc_2 = this.getPendingReceivedFranchiseOrderTypes ();
            for(int i0 = 0; i0 < _loc_2.size(); i0++)
            {
            		_loc_3 = _loc_2.get(i0);

                _loc_4 = this.getPendingReceivedFranchiseOrdersByType(_loc_3);
                _loc_5 = OwnedFranchiseData.loadPendingObject(_loc_4);
                _loc_1.push(_loc_5);
            }
            return _loc_1;
        }//end

        private String Vector  getPendingSentFranchiseOrderTypes ().<>
        {
            return this.getPendingFranchiseOrderTypes(Global.world.citySim.lotManager.sentPendingOrders);
        }//end

        private String Vector  getPendingReceivedFranchiseOrderTypes ().<>
        {
            return this.getPendingFranchiseOrderTypes(Global.world.citySim.lotManager.receivedPendingOrders);
        }//end

        private String Vector  getPendingFranchiseOrderTypes (Array param1 ).<>
        {
            LotOrder _loc_3 =null ;
            Vector<String> _loc_2 =new Vector<String>();
            for(int i0 = 0; i0 < param1.size(); i0++)
            {
            		_loc_3 = param1.get(i0);

                if (this.isFranchiseOrder(_loc_3) && _loc_2.indexOf(_loc_3.getResourceType()) < 0)
                {
                    _loc_2.push(_loc_3.getResourceType());
                }
            }
            return _loc_2;
        }//end

        private FranchiseExpansionData Vector  getPendingSentFranchiseOrderLocationsByType (String param1 ).<>
        {
            return this.getPendingFranchiseOrderLocationsByType(param1, this.getPendingSentFranchiseOrdersByType(param1));
        }//end

        private FranchiseExpansionData Vector  getPendingReceivedFranchiseOrderLocationsByType (String param1 ).<>
        {
            return this.getPendingFranchiseOrderLocationsByType(param1, this.getPendingReceivedFranchiseOrdersByType(param1));
        }//end

        private FranchiseExpansionData Vector  getPendingFranchiseOrderLocationsByType (String param1 ,Vector param2 .<LotOrder >).<>
        {
            LotOrder _loc_4 =null ;
            Vector<FranchiseExpansionData> _loc_3 =new Vector<FranchiseExpansionData>();
            for(int i0 = 0; i0 < param2.size(); i0++)
            {
            		_loc_4 = param2.get(i0);

                _loc_3.push(FranchiseExpansionData.loadPendingObject(_loc_4));
            }
            return _loc_3;
        }//end

        private LotOrder Vector  getPendingSentFranchiseOrdersByType (String param1 ).<>
        {
            return this.getPendingFranchiseOrdersByType(param1, Global.world.citySim.lotManager.sentPendingOrders);
        }//end

        private LotOrder Vector  getPendingReceivedFranchiseOrdersByType (String param1 ).<>
        {
            return this.getPendingFranchiseOrdersByType(param1, Global.world.citySim.lotManager.receivedPendingOrders);
        }//end

        private LotOrder Vector  getPendingFranchiseOrdersByType (String param1 ,Array param2 ).<>
        {
            LotOrder _loc_4 =null ;
            Vector<LotOrder> _loc_3 =new Vector<LotOrder>();
            for(int i0 = 0; i0 < param2.size(); i0++)
            {
            		_loc_4 = param2.get(i0);

                if (this.isFranchiseOrder(_loc_4) && _loc_4.getResourceType() == param1)
                {
                    _loc_3.push(_loc_4);
                }
            }
            return _loc_3;
        }//end

        public boolean  isFranchiseOrder (LotOrder param1 )
        {
            _loc_2 =Global.gameSettings().getItemByName(param1.getResourceType ());
            return _loc_2 && _loc_2.type == "business";
        }//end

        private double  getHeadquartersHeightLimit (String param1 )
        {
            double _loc_2 =0;
            _loc_3 =Global.gameSettings().getItemByName(param1 );
            if (_loc_3)
            {
                _loc_2 = _loc_3.heightLimit;
            }
            return _loc_2;
        }//end

        private double  getHeadquartersHeightPerExpansion (String param1 )
        {
            return Global.gameSettings().getInt("hqFloorMultiplier");
        }//end

        private boolean  verifyHeadquartersUpdate (FranchiseExpansionData param1 )
        {
            return this.isFirstCollect(param1);
        }//end

        private boolean  verifyHeadquartersGrant (FranchiseExpansionData param1 ,String param2 )
        {
            double _loc_4 =0;
            boolean _loc_3 =false ;
            if (this.verifyHeadquartersUpdate(param1))
            {
                _loc_4 = this.getCollectCount(this.m_model, param1.franchiseType);
                if (_loc_4 == 0)
                {
                    _loc_3 = true;
                }
            }
            return _loc_3;
        }//end

        private double  getCollectCount (FranchiseDataModel param1 ,String param2 )
        {
            FranchiseExpansionData _loc_5 =null ;
            double _loc_3 =0;
            _loc_4 = param1.getFranchisesByType(param2 );
            for(int i0 = 0; i0 < _loc_4.size(); i0++)
            {
            		_loc_5 = _loc_4.get(i0);

                if (!this.isFirstCollect(_loc_5))
                {
                    _loc_3 = _loc_3 + 1;
                }
            }
            return _loc_3;
        }//end

        private boolean  isFirstCollect (FranchiseExpansionData param1 )
        {
            return param1 && !param1.timeLastSupplied;
        }//end

        public void  grantHeadquarters (String param1 ,boolean param2 =true )
        {
            _loc_3 = this.getFranchiseTypeFromHeadquarters(param1 );
            Global.player.inventory.addItems(param1, 1, true);
            if (param2)
            {
                this.serverGrantHeadquarters(_loc_3);
            }
            return;
        }//end

        private void  serverGrantHeadquarters (String param1 )
        {
            GameTransactionManager.addTransaction(new TGrantHeadquarters(param1));
            return;
        }//end

        private Dialog  createHeadquartersGrowDialog ()
        {
            Dialog _loc_1 =null ;
            _loc_2 = ZLoc.t("Dialogs","NewHeadquartersTitle");
            _loc_3 = ZLoc.t("Dialogs","GrowHeadquartersMessage");
            _loc_1 = new GenericDialog(_loc_3, _loc_2);
            return _loc_1;
        }//end

        private Dialog  createHeadquartersMaxDialog ()
        {
            Dialog _loc_1 =null ;
            _loc_2 = ZLoc.t("Dialogs","NewHeadquartersTitle");
            _loc_3 = ZLoc.t("Dialogs","MaxHeadquartersMessage");
            _loc_1 = new GenericDialog(_loc_3, _loc_2);
            return _loc_1;
        }//end

        public void  headquarterFloorBuildCompleted (String param1 )
        {
            this.updateHeadquarters(param1);
            return;
        }//end

        private void  updateHeadquarters (String param1 )
        {
            return;
        }//end

        private String  getHeadquartersName (String param1 )
        {
            String _loc_2 ="";
            _loc_3 = this.getFranchiseTypeFromHeadquarters(param1 );
            _loc_4 =Global.gameSettings().getItemByName(_loc_3 );
            if (Global.gameSettings().getItemByName(_loc_3))
            {
                _loc_2 = ZLoc.t("Items", "headquarters", {item:_loc_4.localizedName});
            }
            return _loc_2;
        }//end

    }



