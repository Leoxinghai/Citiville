package Mechanics;

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
import Mechanics.ClientDisplayMechanics.*;
import Mechanics.GameEventMechanics.*;
import Mechanics.GameMechanicInterfaces.*;
import Mechanics.Transactions.*;
import Modules.crew.ui.*;
//import flash.utils.*;

import com.xinghai.Debug;

    public class MechanicManager
    {
        public static  String DATA_NOT_INITIALIZED ="Data for this mechanic has not been initialized";
        public static  String MECHANIC_SLOTS ="slots";
        public static  String MECHANIC_STORAGE ="storage";
        public static  String MECHANIC_ROLL_CALL ="rollCall";
        public static  String ALL ="all";
        public static  String PLAY ="GMPlay";
        public static  String ON_ADD_TO_WORLD ="onAddToWorld";
        public static  String ON_LOAD ="load";
        public static  String ON_UPGRADE ="onUpgrade";
        private static MechanicManager m_instance =null ;
        private static Dictionary m_forceDisplayRefresh =new Dictionary ();
        public static  double REFRESH_ALL =0;
        private static Array m_allowedMechanicClasses =.get(ExampleMechanicImplementation ,CustomDialogGenerationMechanic ,DialogGenerationMechanic ,AutoCollectMechanic ,CatalogGenerationMechanic ,ClerkRenameMechanic ,MunUpgradeMechanic ,MunicipalHarvestMechanic ,StorageMechanicImplementation ,EffectRenderer ,StagePickEffectRenderer ,AnimatedEffectRenderer ,HarvestReadyIndicator ,MultiAssetRenderer ,MultiPickMechanic ,StorageSupplyMechanic ,SlotMechanicImplementation ,SlottedContainerCatalogMechanic ,DictionaryDataMechanicImplementation ,FriendRewardsDialogMechanic ,ZooUnlockDialogMechanic ,BaseStorageMechanic ,GatedStorageMechanic ,NeighborVisitRewardMechanic ,MerchantCustomerMechanic ,StorageStateHarvestMechanic ,TimerStorageStateHarvestMechanic ,CustomerStorageStateHarvestMechanic ,MallHarvestMechanic ,NeighborhoodHarvestMechanic ,MunicipalCenterHarvestMechanic ,MunicipalCenterStorageMechanic ,UpgradeMechanic ,MallStorageMechanic ,InventoryMechanic ,SocialInventoryTransferMechanic ,SlottedContainerUpgradeMechanic ,ObjectNamingMechanic ,ZooUnlockDialogMechanic ,RollCallDialogMechanic ,RollCallDataMechanic ,RollCallDialog ,TieredDooberMechanic ,DooberMechanic ,NotifyGuideMechanic ,XGameGiftingDialogMechanic ,VisitorCheckinMechanic ,OwnerCheckinMechanic ,ShockwaveMechanic ,WonderShockwaveMechanic ,PeepSpawnMechanic ,FlagPopupInterfaceMechanic ,GateDisplayMechanic ,ExpansionMarkerDisplayMechanic ,ExpansionMarkerClickMechanic ,ShowCatalogOnClickMechanic ,StartTimerHarvestMechanic ,StartTimerHarvestSleepMechanic ,TimedStreakMechanic ,TimerHarvestMechanic ,TimerHarvestSleepMechanic ,TimerMechanic ,NullStateDialogMechanic ,MaintenanceMechanic ,ToasterMechanic ,TutorialMechanic ,RemodelMechanic ,RemodelCatalogGenerationMechanic ,SocialBusinessHarvestMechanic ,NeighborhoodStorageMechanic ,BaseGameMechanic ,OutDoorConcertDialogMechanic ,MJConcertDialogMechanic ,HUDTimerMechanic ,ProgressHUDTimerMechanic ,TimestampToasterMechanic ,CouponSpendMechanic ,TimestampToasterMechanic ,TimerActionMechanic ,TimerCouponMechanic ,BaseMaintenanceMechanic ,NeighborhoodStorageMechanic ,CouponSpendMechanic ,TimerActionMechanic ,TimerCouponMechanic ,BaseMaintenanceMechanic ,NeighborhoodStorageMechanic ,PaidRenewalMechanic ,LicensedPropertyMechanic ,OverlayAssetRenderer ,UniversityLogoRenderer ,SubLicensedPropertyMechanic ,MechanicPackSwapMechanic ,PurchaseToInventoryMechanic ,PeepGroupTriggerMechanic ,HarvestPierMechanic ,SendOffPierMechanic ,AddToWorldMechanic ,LootTableRollMechanic) ;

        public  MechanicManager ()
        {
            if (MechanicManager.m_instance != null)
            {
                throw new Error("Attempting to instantiate more than one MechanicManager");
            }
            return;
        }//end

        public IGameMechanic  getMechanicInstance (IMechanicUser param1 ,String param2 ,String param3 )
        {
            IGameMechanic _loc_4 =null ;
            Class _loc_6 =null ;
            _loc_5 = param1.getMechanicConfig(param2 ,param3 );
            if (param1.getMechanicConfig(param2, param3))
            {
                _loc_6 =(Class) getDefinitionByName("Mechanics.GameEventMechanics." + _loc_5.className);
                _loc_4 = new _loc_6;
                _loc_4.initialize(param1, _loc_5);
            }
            return _loc_4;
        }//end

        public IClientGameMechanic  createDisplayMechanicInstance (IMechanicUser param1 ,MechanicConfigData param2 )
        {
            IClientGameMechanic _loc_3 =null ;
            Class _loc_4 =null ;
            if (param2.className != "")
            {
                _loc_4 =(Class) getDefinitionByName("Mechanics.ClientDisplayMechanics." + param2.className);
                _loc_3 = new _loc_4;
                _loc_3.initialize(param1, param2);
            }
            return _loc_3;
        }//end

        public boolean  handleAction (IMechanicUser param1 ,String param2 ,Array param3 =null )
        {
            Item _loc_7 =null ;
            IActionGameMechanic _loc_8 =null ;
            String _loc_9 =null ;
            MechanicActionResult _loc_10 =null ;
            boolean _loc_4 =false ;
            _loc_5 = param1.getPrioritizedMechanicsForGameEvent(param2 );
            boolean _loc_6 =false ;
            if (param1 instanceof ItemInstance)
            {
                _loc_7 = ((ItemInstance)param1).getItem();
                _loc_6 = _loc_7.blockedByExperiments;
            }
            if(param2 == "GMPlay") {
                 Debug.debug6("MechanicManager.handleAction."+ param1+";"+param2);
            }

            if (!Global.isTransitioningWorld && _loc_5.length > 0 && !_loc_6)
            {
                for(int i0 = 0; i0 < _loc_5.size(); i0++)
                {
                		_loc_9 = _loc_5.get(i0);

                    if (!param1.mechanicExperimentEnabled(param1.getMechanicConfig(_loc_9, param2)))
                    {
                        continue;
                    }
                    _loc_8 =(IActionGameMechanic) this.getMechanicInstance(param1, _loc_9, param2);
                    if (!_loc_8.hasOverrideForGameAction(param2))
                    {
                        continue;
                    }
                    _loc_10 = _loc_8.executeOverrideForGameEvent(param2, param3);
                    _loc_4 = _loc_10.actionSuccess;
                    if (_loc_10 != null && _loc_10.sendTransaction)
                    {
                        GameTransactionManager.addTransaction(new TMechanicAction((MechanicMapResource)param1, _loc_9, param2, _loc_10.transactionParams));
                    }
                    if (_loc_10 != null && !_loc_10.continueActionExecution)
                    {
                        _loc_8 = null;
                        break;
                    }
                }
            }
            return _loc_4;
        }//end

        public static MechanicManager  getInstance ()
        {
            if (!MechanicManager.m_instance)
            {
                MechanicManager.m_instance = new MechanicManager;
            }
            return m_instance;
        }//end

        public static boolean  getForceDisplayRefresh (String param1 ,double param2)
        {
            String _loc_4 =null ;
            String _loc_5 =null ;
            boolean _loc_3 =false ;
            if (param1 !=null)
            {
                _loc_4 = param1 + "_owner_ids";
                if (param2 < 1)
                {
                    if (m_forceDisplayRefresh.get(_loc_4))
                    {
                        _loc_3 = true;
                    }
                }
                else
                {
                    _loc_5 = param1 + "_owner_id_" + param2;
                    if (m_forceDisplayRefresh.get(_loc_5))
                    {
                        _loc_3 = true;
                    }
                    else if (m_forceDisplayRefresh.get(_loc_4))
                    {
                        if (!m_forceDisplayRefresh.get(_loc_4).get(_loc_5))
                        {
                            _loc_3 = true;
                        }
                    }
                }
            }
            return _loc_3;
        }//end

        public static void  setForceDisplayRefresh (String param1 ,double param2 =0,boolean param3 =false )
        {
            String _loc_4 =null ;
            String _loc_5 =null ;
            if (param1 !=null)
            {
                _loc_4 = param1 + "_owner_ids";
                if (param2 < 1)
                {
                    delete m_forceDisplayRefresh.get(_loc_4);
                    if (param3)
                    {
                        m_forceDisplayRefresh.put(_loc_4,  new Dictionary());
                    }
                }
                else
                {
                    _loc_5 = param1 + "_owner_id_" + param2;
                    delete m_forceDisplayRefresh.get(_loc_5);
                    if (param3)
                    {
                        m_forceDisplayRefresh.put(_loc_5,  param3);
                    }
                    if (m_forceDisplayRefresh.get(_loc_4))
                    {
                        m_forceDisplayRefresh.get(_loc_4).put(_loc_5,  !param3);
                    }
                }
            }
            return;
        }//end

    }



