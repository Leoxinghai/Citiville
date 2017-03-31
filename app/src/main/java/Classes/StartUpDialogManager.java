package Classes;

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

import Classes.Managers.*;
import Classes.announcements.*;
import Classes.sim.*;
import Classes.util.*;
import Classes.xgamegifting.*;
import Display.*;
import Display.DialogUI.*;
import Engine.Managers.*;
import Events.*;
import Init.PostInit.PostInitActions.*;
import Modules.dataservices.*;
import Modules.guide.*;
import Modules.sale.*;
import Modules.sale.payments.*;
import Modules.socialinventory.*;
import Modules.stats.experiments.*;
import Modules.stats.types.*;
import Transactions.*;
import com.zynga.skelly.util.*;
//import flash.events.*;
//import flash.utils.*;

    public class StartUpDialogManager
    {
        public static Array dialogQueue =new Array();
        private static boolean m_needsToInit =true ;
        private static Array m_startUpParams =new Array();
        private static Object m_initResult =null ;
        public static boolean returnToDefaultHUD =true ;
        private static StartUpDialogHelper helper ;
public static GenericDialog m_popup ;
        public static  int FIRST_TIME_VISIT_BONUS_DIALOG =7;
        private static  String FLASHVAR_EMPTYLOT ="emptyLot";
        private static  String FLASHVAR_VALENTINE ="valentine";
        private static  String FLASHVAR_ROLLCALL ="rollCall";
        private static  String FLASHVAR_HOTEL_CHECKIN ="hotelCheckIn";
        private static  String ROLL_CALL_CHECK_IN ="checkIn";
        private static  String ROLL_CALL_COLLECT ="collect";
        private static  String BONUS_DIALOG_TIME ="FIRST_TIME_VISIT_BONUS_DIALOG";

        public  StartUpDialogManager ()
        {
            CheckInit();
            return;
        }//end

        private static void  CheckInit ()
        {
            if (m_needsToInit)
            {
                OnInit();
                m_needsToInit = false;
            }
            return;
        }//end

        private static void  OnInit ()
        {
            dialogQueue = new Array();
            m_startUpParams = new Array();
            helper = new StartUpDialogHelper();
            return;
        }//end

        public static void  StartUpDialogTrigger (Object param1 )
        {
            CheckInit();
            m_initResult = param1;
            addStartupFlashvarActions();
            parceStartupFlashHotvarActions();
            addBuiltInStartupUtils();
            pumpDialogQueue();
            return;
        }//end

        public static void  addStartupFlashvarActions ()
        {
            _loc_1 = GlobalEngine.getFlashVar("startupType");
            if (_loc_1 != null)
            {
                parseStartUpParams(_loc_1.toString());
            }
            return;
        }//end

        public static void  parceStartupFlashHotvarActions ()
        {
            String _loc_2 =null ;
            String _loc_3 =null ;
            String _loc_4 =null ;
            String _loc_5 =null ;
            int _loc_6 =0;
            SamAdviceDialog _loc_7 =null ;
            StartupDialogBase _loc_8 =null ;
            _loc_1 =Global.flashHotParams.get( "gameBar") ;
            if (_loc_1 != null)
            {
                _loc_2 = _loc_1.toString();
                _loc_3 = "GameBar_" + _loc_2;
                _loc_4 = ZLoc.t("Dialogs", _loc_3);
                _loc_5 = "GameBar";
                _loc_6 = GenericPopup.TYPE_OK;
                _loc_7 = new SamAdviceDialog(_loc_4, "", _loc_6, null, _loc_5, "", true, 0);
                _loc_8 = new StartupDialogBase(_loc_7, "gameBar", false);
                dialogQueue.push(_loc_8);
            }
            return;
        }//end

        public static void  addBuiltInStartupUtils ()
        {
            Array _loc_1 =new Array(processAnnouncementConditions ,processD2InviteNeighborConditions ,processWarehouseCorruptionConditions ,processXpromo ,processMobileXpromo ,processFlashSaleConditions ,processFreeGiftSaleConditions ,processNewDiscountSaleConditions ,processEoQSaleConditions ,processRecentlyPlayedGiftMFS );
            _loc_2 = ReactivationManager.isPlayerReactive();
            if (_loc_2)
            {
                _loc_1 = new Array(processRUXConditions);
            }
            while (dialogQueue.length == 0 && _loc_1.length > 0)
            {

                ((Function)_loc_1.pop()).call();
            }
            return;
        }//end

        private static void  addD2InvitePopup ()
        {
            strDialogMessage = ZLoc.t("Dialogs","IdleAddNeighbors_text");
            typeDialog = GenericDialogView.TYPE_ADDNEIGHBOR;
            acceptHandler = function(eventGenericPopupEvent)
            {
                if (event.button == GenericPopup.YES)
                {
                    FrameManager.showTray("invite.php?ref=second_day_neighbor_prompt");
                }
                return;
            }//end
            ;
            closeHandler = function(eventEvent=null)
            {
                Global.player.setLastActivationTime("d2_invitefriendsdialog_seen", GlobalEngine.getTimer() / 1000);
                StatsManager.count(StatsCounterType.PROMPTS, StatsKingdomType.D2NEIGHBOR);
                return;
            }//end
            ;
            SamAdviceDialog dialog =new SamAdviceDialog(strDialogMessage ,"",typeDialog ,acceptHandler ,"IdleAddNeighbors","",true ,0,"IdleAddNeighbors_button");
            StartupDialogBase queueObject =new StartupDialogBase(dialog ,"d2_invitefriendsdialog",false ,closeHandler );
            dialogQueue.push(queueObject);
            return;
        }//end

        private static void  parseStartUpParams (String param1 )
        {
            _loc_2 = param1.split(":");
            _loc_3 = _loc_2.get(0);
            int _loc_4 =0;
            while (_loc_4 < _loc_2.length())
            {

                m_startUpParams.push(_loc_2.get(_loc_4));
                _loc_4++;
            }
            switch(_loc_3)
            {
                case FLASHVAR_VALENTINE:
                {
                    processIneligibleValentinesPlayer();
                    break;
                }
                case FLASHVAR_ROLLCALL:
                {
                    if (_loc_2.length > 3)
                    {
                        processRollCall(String(_loc_2.get(1)), String(_loc_2.get(2)), String(_loc_2.get(3)), m_initResult.is_new);
                    }
                    break;
                }
                case FLASHVAR_EMPTYLOT:
                case FLASHVAR_HOTEL_CHECKIN:
                {
                    Global.disableGamePopups = true;
                    if (!m_initResult.is_new)
                    {
                        Global.world.addEventListener("InitNeighbors", onNeighborsLoaded);
                        m_popup = UI.displayMessage(ZLoc.t("Main", "LoadingFarm"), GenericDialogView.TYPE_NOBUTTONS, null, "", true);
                    }
                    break;
                }
                default:
                {
                    break;
                }
            }
            return;
        }//end

        private static void  onNeighborsLoaded (Event event )
        {
            Global.world.removeEventListener("InitNeighbors", onNeighborsLoaded);
            m_popup.close();
            _loc_2 = m_startUpParams.length>2? (Number(m_startUpParams.get(2))) : (-1);
            GameTransactionManager.addTransaction(new TGetVisitMission(String(m_startUpParams.get(1)), null, _loc_2), true);
            return;
        }//end

        private static void  processRollCall (String param1 ,String param2 ,String param3 ,boolean param4 )
        {
            if (Global.disableGamePopups)
            {
                return;
            }
            String _loc_5 =null ;
            _loc_6 =Global.player.findFriendById(param1 );
            if (Global.player.findFriendById(param1))
            {
                _loc_5 = _loc_6.snUser.picture;
            }
            String _loc_7 ="RollCall_StartUpDialogA_rally";
            String _loc_8 ="StartUpDialogA_rally_text";
            String _loc_9 ="StartUpDialogB_text";
            String _loc_10 ="RollCall_CheckIn_StartUpDialogC_rally_text";
            String _loc_11 ="RollCall_StartUpDialogD_rally_text";
            _loc_12 =Global.gameSettings().getItemByName(param3 );
            String _loc_13 ="";
            if (_loc_12)
            {
                _loc_13 = _loc_12.localizedName;
            }
            switch(param2)
            {
                case ROLL_CALL_CHECK_IN:
                {
                    _loc_10 = "RollCall_CheckIn_StartUpDialogC_rally_text";
                    if (!Global.player.isFriendANeighbor(param1))
                    {
                        dialogQueue.push(new StartupVisitActionDialog(param1, param4, {buildingName:_loc_13}, _loc_5, _loc_7, _loc_8, _loc_9, _loc_10, _loc_11, false));
                    }
                    break;
                }
                case ROLL_CALL_COLLECT:
                {
                }
                default:
                {
                    _loc_10 = "RollCall_Collect_StartUpDialogC_rally_text";
                    dialogQueue.push(new StartupVisitActionDialog(param1, param4, {buildingName:_loc_13}, _loc_5, _loc_7, _loc_8, _loc_9, _loc_10, _loc_11, false));
                    break;
                    break;
                }
            }
            return;
        }//end

        private static void  processIneligibleValentinesPlayer ()
        {
            _loc_1 =Global.experimentManager.getVariant(ExperimentDefinitions.EXPERIMENT_VDAY_2011 );
            if (_loc_1 == 1)
            {
                if (!ValentineManager.m_shownIneligible && !ValentineManager.checkEligibility())
                {
                    ValentineManager.m_shownIneligible = true;
                    dialogQueue.push(new StartupMessage("ValUI", ZLoc.t("Dialogs", "ValUI_StartupIneligible"), ExperimentDefinitions.EXPERIMENT_VDAY_2011));
                }
            }
            return;
        }//end

        private static void  processEoQSaleConditions ()
        {
            double _loc_4 =0;
            if (Global.disableGamePopups)
            {
                return;
            }
            if (globalSuppressStartUpPopups())
            {
                return;
            }
            int _loc_1 =1;
            _loc_1 = RuntimeVariableManager.getInt("EOQ_POPUP_VER", 1);
            boolean _loc_2 =true ;
            String _loc_3 ="eoq_lastseen_"+_loc_1 ;
            if (Global.player.getLastActivationTime(_loc_3) == -1)
            {
                _loc_2 = false;
                _loc_4 = uint(GlobalEngine.getTimer() / 1000);
                Global.player.setLastActivationTime(_loc_3, _loc_4);
            }
            else
            {
                _loc_2 = true;
            }
            if (Global.paymentsSaleManager.isSaleActive(PaymentsSaleManager.TYPE_EOQ_SALE, _loc_2) === true)
            {
                dialogQueue.push(new StartupDialogBase(Global.paymentsSaleManager.getSaleByType(PaymentsSaleManager.TYPE_EOQ_SALE).createStartupDialog(), "EoQSale", false, null));
            }
            return;
        }//end

        private static void  processRecentlyPlayedGiftMFS ()
        {
            _loc_1 =Global.experimentManager.getVariant(ExperimentDefinitions.EXPERIMENT_RECENTLY_PLAYED_MFS );
            if ((_loc_1 == 2 || _loc_1 == 3) && !globalSuppressStartUpPopups())
            {
                Global.dataServicesManager.query(DataServicesQueryType.GET_PLAYED_IN_LAST_N_DAYS, [RecentlyPlayedMFSManager.NUM_DAYS], recentlyPlayedCallback);
            }
            return;
        }//end

        private static void  recentlyPlayedCallback (DataServicesResult param1 )
        {
            RecentlyPlayedMFSManager mngr ;
            int currentTime ;
            int lastTime ;
            int cooldown ;
            results = param1;
            variant = Global.experimentManager.getVariant(ExperimentDefinitions.EXPERIMENT_RECENTLY_PLAYED_MFS);
            mngr =(RecentlyPlayedMFSManager) Global.flashMFSManager.getManager(FlashMFSManager.TYPE_RECENTLY_PLAYED_MFS);
            mngr.onQueryComplete(results);
            if (variant == 2 && !Global.player.isNewPlayer && !Global.guide.isActive() && mngr.numAvailable > 0)
            {
                currentTime = GlobalEngine.getTimer() / 1000;
                lastTime = Global.player.getLastActivationTime("recently_played_mfs_startup");
                cooldown = Global.gameSettings().getInt("recentlyPlayedMFSCooldown", Global.gameSettings().inGameDaySeconds / 2);
                if (lastTime == -1 || Math.abs(currentTime - lastTime) >= cooldown)
                {
                    Global.player.setLastActivationTime("recently_played_mfs_startup", int(GlobalEngine.getTimer() / 1000));
                    TimerUtil .callLater (void  ()
            {
                mngr.giftingSource = "after_announce";
                mngr.displayMFS();
                return;
            }//end
            , 60000);
                }
            }
            return;
        }//end

        private static void  processNewDiscountSaleConditions ()
        {
            String _loc_5 =null ;
            _loc_1 =Global.experimentManager.getVariant(ExperimentDefinitions.EXPERIMENT_NEW_BUYER_SALE );
            _loc_2 =Global.experimentManager.getVariant(ExperimentDefinitions.EXPERIMENT_DEAD_BUYER_SALE );
            boolean _loc_3 =false ;
            boolean _loc_4 =false ;
            if (Global.disableGamePopups)
            {
                return;
            }
            if (globalSuppressStartUpPopups())
            {
                return;
            }
            _loc_6 =Global.paymentsSaleManager.getSaleByType(PaymentsSaleManager.TYPE_FLASH_SALE );
            if (!Global.paymentsSaleManager.getSaleByType(PaymentsSaleManager.TYPE_FLASH_SALE))
            {
                return;
            }
            if (_loc_6.experiment == "cv_new_buyer_promo" && _loc_1 > 1 && _loc_1 != 2 && _loc_1 != 5 && _loc_1 < 7 && Global.player.getLastActivationTime("new_buyer_sale_start") != -1)
            {
                _loc_3 = Global.player.getLastActivationTime("new_buyer_abandon") != -1;
                _loc_4 = Global.player.getLastActivationTime("new_buyer_purchase") != -1;
                _loc_5 = "new_buyer_discount";
            }
            else if (_loc_6.experiment == "cv_dead_buyer_promo" && _loc_2 > 1 && _loc_2 != 2 && _loc_2 != 5 && _loc_2 != 7 && _loc_2 != 8 && _loc_2 != 11 && _loc_2 < 13 && Global.player.getLastActivationTime("dead_buyer_sale_start") != -1)
            {
                _loc_3 = Global.player.getLastActivationTime("dead_buyer_abandon") != -1;
                _loc_4 = Global.player.getLastActivationTime("dead_buyer_purchase") != -1;
                _loc_5 = "dead_buyer_discount";
            }
            if (_loc_5 && _loc_3 == true && Global.player.getSeenFlag(_loc_5) == false && _loc_4 == false && _loc_6.timeRemaining > 0)
            {
                Global.player.setSeenFlag(_loc_5);
                createDiscountStartupMessage(_loc_5, _loc_6);
                StatsManager.count(StatsCounterType.GAME_ACTIONS, _loc_6.statsName, "abandoned_payment_page", String(_loc_6.getSaleDiscount()));
            }
            return;
        }//end

        private static void  processRUXConditions ()
        {
            RUXDialog _loc_2 =null ;
            _loc_1 = ReactivationManager.isPlayerReactive();
            if (_loc_1)
            {
                _loc_2 = new RUXDialog();
                dialogQueue.push(_loc_2);
            }
            return;
        }//end

        private static void  createDiscountStartupMessage (String param1 ,PaymentsSale param2 )
        {
            Object _loc_5 =null ;
            String _loc_6 =null ;
            String _loc_7 =null ;
            if (Global.disableGamePopups)
            {
                return;
            }
            _loc_3 = param2.getSaleDiscount ();
            _loc_4 = param2.timeRemaining ;
            if (_loc_3 > 0 && _loc_4 > 0)
            {
                _loc_5 = DateUtil.calculateTimeDifference(_loc_4 * 1000);
                if (_loc_5.hours > 0)
                {
                    _loc_6 = ZLoc.t("Dialogs", "NewBuyerDiscountMessage", {discount:_loc_3, hours:_loc_5.hours, minutes:_loc_5.minutes});
                }
                else
                {
                    _loc_6 = ZLoc.t("Dialogs", "NewBuyerDiscountMessageMinutes", {discount:_loc_3, minutes:_loc_5.minutes});
                }
                _loc_7 = ZLoc.t("Dialogs", "NewBuyerDiscountButton");
                dialogQueue.push(new StartupMessage("NewBuyerDiscount", _loc_6, param1, onGetCurrency, "", GenericDialogView.TYPE_CUSTOM_OK, true, _loc_7));
            }
            return;
        }//end

        private static void  onGetCurrency (GenericPopupEvent event )
        {
            if (event.button == GenericPopup.YES)
            {
                StatsManager.count("dead_sale_discount_dialog", "click_ok");
                FrameManager.showTray("money.php?ref=discount_dialog");
            }
            return;
        }//end

        private static void  processFreeGiftSaleConditions ()
        {
            if (Global.disableGamePopups)
            {
                return;
            }
            if (globalSuppressStartUpPopups())
            {
                return;
            }
            if (Global.paymentsSaleManager.isSaleActive(PaymentsSaleManager.TYPE_FREE_GIFT_SALE) === true)
            {
                dialogQueue.push(new StartupDialogBase(Global.paymentsSaleManager.getSaleByType(PaymentsSaleManager.TYPE_FREE_GIFT_SALE).createStartupDialog(), "FreeGiftOffer", false, null));
            }
            return;
        }//end

        private static void  processWarehouseCorruptionConditions ()
        {
            if (globalSuppressStartUpPopups())
            {
                return;
            }
            _loc_1 =Global.player.getFlag("double_warehouse_check").value ;
            switch(_loc_1)
            {
                case -1:
                {
                    UI.displayMessage(ZLoc.t("Dialogs", "ItemStorage_CorruptionFix"));
                    Global.player.getFlag("double_warehouse_check").setAndSave(-2);
                    break;
                }
                case 0:
                {
                    break;
                }
                case -2:
                {
                    break;
                }
                case 1:
                {
                    UI.displayMessage(ZLoc.t("Dialogs", "ItemStorage_CorruptionFix"));
                    Global.player.getFlag("double_warehouse_check").setAndSave(-2);
                    break;
                }
                default:
                {
                    break;
                    break;
                }
            }
            return;
        }//end

        private static void  processFlashSaleConditions ()
        {
            if (Global.disableGamePopups)
            {
                return;
            }
            if (globalSuppressStartUpPopups())
            {
                return;
            }
            if (Global.paymentsSaleManager.isSaleActive(PaymentsSaleManager.TYPE_FLASH_SALE))
            {
                dialogQueue.push(new StartupDialogBase(Global.paymentsSaleManager.getSaleByType(PaymentsSaleManager.TYPE_FLASH_SALE).createStartupDialog(), "FlashSale", false, null));
            }
            return;
        }//end

        private static void  processD2InviteNeighborConditions ()
        {
            if (Global.disableGamePopups)
            {
                return;
            }
            if (globalSuppressStartUpPopups())
            {
                return;
            }
            _loc_1 =Global.player.getLastActivationTime("d2_invitefriendsdialog_timer");
            _loc_2 =Global.player.getLastActivationTime("d2_invitefriendsdialog_seen")!= -1;
            _loc_3 =Global.gameSettings().getInt("inviteNeighborReminderPeriod",60*60*24);
            if (!_loc_2 && !Global.player.isNewPlayer && _loc_1 > 0 && _loc_1 < GlobalEngine.getTimer() / 1000 - _loc_3)
            {
                addD2InvitePopup();
            }
            return;
        }//end

        private static void  processXpromo ()
        {
            StartupDialogBase _loc_2 =null ;
            if (Global.disableGamePopups)
            {
                return;
            }
            if (globalSuppressStartUpPopups())
            {
                return;
            }
            _loc_1 = ZCrossPromoManager.getCrossPromo();
            if (_loc_1)
            {
                StartUpDialogManager.dialogQueue.push(_loc_1);
            }
            else
            {
                _loc_2 = InGameXPromoManager.getInstance().getStartupDialog();
                if (_loc_2 && _loc_2.shouldQueue)
                {
                    StartUpDialogManager.dialogQueue.push(_loc_2);
                }
            }
            return;
        }//end

        public static void  processMobileXpromo ()
        {
            if (globalSuppressStartUpPopups())
            {
                return;
            }
            _loc_1 =Global.player.hasXpromoRewardPending("hometown_launch");
            if (_loc_1)
            {
                XGameGiftingManager.instance.beginXGameGifting(ExternalGameIds.CITYVILLE_MOBILE);
                UI.displayMessage(ZLoc.t("Dialogs", "HometownReward_text"));
                Global.player.setXPromoCompleted("hometown_launch");
                StatsManager.count("xpromo", "hometown", "reward_dialog_displayed");
            }
            return;
        }//end

        public static void  processAnnouncementConditions ()
        {
            Timer _loc_3 =null ;
            Function _loc_4 =null ;
            if (m_initResult.is_new)
            {
                return;
            }
            if (Global.disableGamePopups)
            {
                return;
            }
            if (globalSuppressStartUpPopups())
            {
                return;
            }
            _loc_1 =Global.experimentManager.getVariant(ExperimentDefinitions.EXPERIMENT_PAYER_REACT );
            if (_loc_1 != 0 && Global.player.level > 9)
            {
                _loc_3 = new Timer(60000, 1);
                _loc_3.addEventListener(TimerEvent.TIMER, showPayerCampaignDialog);
                _loc_3.start();
            }
            _loc_2 =Global.announcementManager.getNextAnnouncement ();
            if (_loc_2)
            {
                _loc_4 = null;
                if (_loc_2.view.closeCallback)
                {
                    _loc_4 = Curry.curry(helper.getCallback(_loc_2.view.closeCallback.name), _loc_2.view.closeCallback.params);
                }
                dialogQueue.push(new AnnouncementDialog(_loc_2, _loc_4));
                return;
            }
            return;
        }//end



        public static boolean  globalSuppressStartUpPopups ()
        {
            _loc_1 = RuntimeVariableManager.getBoolean("IS_TEST_ENVIRONMENT",false);
            if (!_loc_1)
            {
                return false;
            }
            _loc_2 =Global.experimentManager.getVariant(ExperimentDefinitions.EXPERIMENT_DISABLE_STARTUP_POPUPS );
            if (_loc_2 == ExperimentDefinitions.DISABLE_STARTUP_POPUPS)
            {
                return true;
            }
            return false;
        }//end


        public static void  showPayerCampaignDialog (Event event =null )
        {
            Global.dataServicesManager.query(DataServicesQueryType.GET_INACTIVE_PLAYER, [9], showPayerCampaignDialogView);
            return;
        }//end

        public static void  showPayerCampaignDialogView (DataServicesResult param1 )
        {
            int _loc_5 =0;
            PlayerChooserDialog _loc_6 =null ;
            if (param1 && param1.isValid() && param1.get() instanceof Array)
            {
                Global.player.inactiveNeighbors =(Array) param1.get();
            }
            _loc_2 = GlobalEngine.getTimer ()/1000;
            _loc_3 =Global.player.getLastActivationTime("payerReactivationCampaign");
            _loc_4 =Global.gameSettings().getInt("payerReactivationCampaign",Global.gameSettings().inGameDaySeconds );
            if (_loc_3 == -1 || Math.abs(_loc_2 - _loc_3) >= _loc_4)
            {
                if (Global.player.inactiveNeighbors != null)
                {
                    _loc_5 = Global.player.inactiveNeighbors.length;
                    if (_loc_5 > 0)
                    {
                        Global.player.setLastActivationTime("payerReactivationCampaign", int(GlobalEngine.getTimer() / 1000));
                        _loc_6 = new PlayerChooserDialog("Send Cash", "", 0, null, "CampaignRally");
                        StatsManager.count("dailog", "popup", "view", "campaign_rally_payer_react");
                        UI.displayPopup(_loc_6, true);
                    }
                }
            }
            return;
        }//end


        public static void  displayAnnouncement (String param1 )
        {
            AnnouncementData _loc_2 =null ;
            Function _loc_3 =null ;
            AnnouncementDialog _loc_4 =null ;
            StartUpDialogHelper _loc_5 =null ;
            for(int i0 = 0; i0 < Global.gameSettings().getAnnouncements().size(); i0++)
            {
            	_loc_2 = Global.gameSettings().getAnnouncements().get(i0);

                if (_loc_2.id == param1)
                {
                    _loc_3 = null;
                    if (_loc_2.view.closeCallback)
                    {
                        _loc_5 = helper ? (helper) : (StartUpDialogHelper.instance);
                        _loc_3 = Curry.curry(_loc_5.getCallback(_loc_2.view.closeCallback.name), _loc_2.view.closeCallback.params);
                    }
                    _loc_4 = new AnnouncementDialog(_loc_2, _loc_3);
                    _loc_4.showStandalone();
                    break;
                }
            }
            return;
        }//end

        public static void  pumpDialogQueue ()
        {
            _loc_1 = null;
            if (dialogQueue.length == 0)
            {
                doFinalStartUpTasks();
            }
            else
            {
                _loc_1 = dialogQueue.pop();
                if ("show" in _loc_1)
                {
                    _loc_1.show();
                }
                else
                {
                    GlobalEngine.log("Error", "An element that doesn\'t have a show method was put in the startupdialogqueue.");
                }
            }
            return;
        }//end

        public static void  clearDialogQueue ()
        {
            dialogQueue = new Array();
            return;
        }//end

        private static boolean  areHolidayClothingQuestsActive ()
        {
            _loc_1 =Global.questManager.isQuestActive("qm_holiday_snow_ground");
            _loc_2 =Global.questManager.isQuestActive("qm_holiday_snowandlights");
            _loc_3 =Global.questManager.isQuestActive("qm_holiday_winterclothes");
            return _loc_1 || _loc_2 || _loc_3;
        }//end

        private static void  forceHolidayClothingQuestWrapUp ()
        {
            Global.questManager.expireSpecificQuest("qm_holiday_snow_ground");
            Global.questManager.expireSpecificQuest("qm_holiday_snowandlights");
            Global.questManager.expireSpecificQuest("qm_holiday_winterclothes");
            GameTransactionManager.addTransaction(new THolidayClothingRemoveAllQuests());
            return;
        }//end

        private static void  doHolidayClothingQuestCleanup ()
        {
            if (areHolidayClothingQuestsActive())
            {
                forceHolidayClothingQuestWrapUp();
            }
            return;
        }//end

        public static boolean  areChineseNewYearQuestsActive ()
        {
            _loc_1 =Global.questManager.isQuestActive("qm_cny1");
            _loc_2 =Global.questManager.isQuestActive("qm_cny2");
            _loc_3 =Global.questManager.isQuestActive("qm_cny3");
            return _loc_1 || _loc_2 || _loc_3;
        }//end

        public static void  forceChineseNewYearQuestsWrapUp ()
        {
            Global.questManager.expireSpecificQuest("qm_cny1");
            Global.questManager.expireSpecificQuest("qm_cny2");
            Global.questManager.expireSpecificQuest("qm_cny3");
            GameTransactionManager.addTransaction(new TChineseNewYearRemoveAllQuests());
            return;
        }//end

        public static void  doChineseNewYearQuestCleanup ()
        {
            if (areChineseNewYearQuestsActive())
            {
                forceChineseNewYearQuestsWrapUp();
            }
            return;
        }//end

        private static void  doFinalStartUpTasks ()
        {
            if (m_initResult.is_new)
            {
                Global.guide.loadFirstStepAssets();
                Global.guide.notify(GuideUtils.FIRST_STEP);
            }
            else
            {
                Global.player.setAllowQuests(true);
                if (returnToDefaultHUD)
                {
                    Global.hud.refreshGoodsHUD();
                }
            }
            if (m_initResult.player.quests != null)
            {
                Global.questManager.loadQuestData(m_initResult.player.quests);
            }
            else
            {
                Global.questManager.loadQuestData({name:"new"});
            }
            Global.questManager.startUp();
            return;
        }//end

        public static void  doAfterQuestSystemStartUp ()
        {
            HolidayTree.wrapUpHolidayQuests();
            doHolidayClothingQuestCleanup();
            doChineseNewYearQuestCleanup();
            GameTransactionManager.addTransaction(new TStartManualQuest("neighbor_gate_quest_3"), true);
            GameTransactionManager.addTransaction(new TStartManualQuest("qm_storage_warehouse"), true);
            GameTransactionManager.addTransaction(new TStartManualQuest("qm_visitor_center"), true);
            GameTransactionManager.addTransaction(new TStartManualQuest("qm_retail1"), true);
            if (Global.experimentManager.getVariant(ExperimentDefinitions.EXPERIMENT_FACTORY) == ExperimentDefinitions.FACTORY_ENABLED)
            {
                GameTransactionManager.addTransaction(new TStartManualQuest("qm_factory_1"), true);
            }
            if (Global.experimentManager.getVariant(ExperimentDefinitions.EXPERIMENT_CLERK_OFFICE) == ExperimentDefinitions.CLERK_OFFICE_ENABLED)
            {
                GameTransactionManager.addTransaction(new TStartManualQuest("qm_clerk_office"), true);
            }
            if (ActionAutomationManager.instance.isEligibleForFeature)
            {
                GameTransactionManager.addTransaction(new TStartManualQuest("qm_rent_collector"), true);
            }
            GameTransactionManager.addTransaction(new TStartManualQuest("qm_st_pattys_1"), true);
            GameTransactionManager.addTransaction(new TStartManualQuest("qm_hardware1"), true);
            GameTransactionManager.addTransaction(new TStartManualQuest("qm_cincodemayo_1"), true);
            GameTransactionManager.addTransaction(new TStartManualQuest("qm_russian_ballet1"), true);
            GameTransactionManager.addTransaction(new TStartManualQuest("qm_upgrade_neighborBUS_bakery"), true);
            if (Global.experimentManager.getVariant(ExperimentDefinitions.EXPERIMENT_TICKET_COUNTER) == ExperimentDefinitions.TICKET_COUNTER_ENABLED)
            {
                GameTransactionManager.addTransaction(new TStartManualQuest("qf_ticket_booth"), true);
            }
            GameTransactionManager.addTransaction(new TStartManualQuest("qf_mall_construction"), true);
            if (Global.experimentManager.getVariant(ExperimentDefinitions.EXPERIMENT_CRUISESHIP) == true)
            {
                GameTransactionManager.addTransaction(new TStartManualQuest("qm_cruiseship_dock"), true);
            }
            if (Global.experimentManager.getVariant(ExperimentDefinitions.EXPERIMENT_BACKTOSCHOOL) == true)
            {
                GameTransactionManager.addTransaction(new TStartManualQuest("qm_back_to_school"), true);
            }
            if (Global.experimentManager.getVariant(ExperimentDefinitions.EXPERIMENT_BUSINESS_UPGRADES) == true)
            {
                GameTransactionManager.addTransaction(new TStartManualQuest("qm_upgrade_business"), true);
            }
            if (Global.experimentManager.getVariant(ExperimentDefinitions.EXPERIMENT_ZOO) == ExperimentDefinitions.ZOO_ENABLED)
            {
                GameTransactionManager.addTransaction(new TStartManualQuest("qm_zoo_1"), true);
            }
            if (Global.experimentManager.getVariant(ExperimentDefinitions.EXPERIMENT_ZOO) == ExperimentDefinitions.ZOO_ENABLED && Global.experimentManager.getVariant(ExperimentDefinitions.EXPERIMENT_ARCTIC_ZOO) == ExperimentDefinitions.ARCTIC_ENABLED)
            {
                GameTransactionManager.addTransaction(new TStartManualQuest("qm_arctic_zoo"), true);
            }
            if (Global.experimentManager.getVariant(ExperimentDefinitions.EXPERIMENT_BRIDGE) == ExperimentDefinitions.EXPERIMENT_BRIDGE_SHOW)
            {
                GameTransactionManager.addTransaction(new TStartManualQuest("qm_bridge_1"), true);
            }
            if (Global.experimentManager.getVariant(ExperimentDefinitions.EXPERIMENT_2011_4OFJULY) == true)
            {
                GameTransactionManager.addTransaction(new TStartManualQuest("qm_july_411_bbq"), true);
            }
            if (SocialInventoryManager.isFeatureAvailable())
            {
                GameTransactionManager.addTransaction(new TStartManualQuest("qm_neighbor_vists_hearts"), true);
            }
            if (Global.experimentManager.getVariant(ExperimentDefinitions.EXPERIMENT_CONTENT_CARNIVAL) == true)
            {
                GameTransactionManager.addTransaction(new TStartManualQuest("qf_carnival_quest"), true);
            }
            if (Global.experimentManager.getVariant(ExperimentDefinitions.EXPERIMENT_PLOT_MASTERY) == ExperimentDefinitions.PLOT_MASTERY_ENABLED)
            {
                GameTransactionManager.addTransaction(new TStartManualQuest("qf_crop_mastery"), true);
            }
            if (Global.experimentManager.getVariant(ExperimentDefinitions.EXPERIMENT_CV_QUEST_EIGHTY_ECO) == true)
            {
                GameTransactionManager.addTransaction(new TStartManualQuest("qm_green1"), true);
            }
            if (Global.experimentManager.getVariant(ExperimentDefinitions.EXPERIMENT_BASTILLE_DAY_QUEST) == true)
            {
                GameTransactionManager.addTransaction(new TStartManualQuest("qm_bastille"), true);
            }
            if (Global.experimentManager.getVariant(ExperimentDefinitions.EXPERIMENT_HOTEL) == ExperimentDefinitions.EXPERIMENT_HOTEL_SHOW)
            {
                GameTransactionManager.addTransaction(new TStartManualQuest("qf_hotels"), true);
            }
            if (Global.experimentManager.getVariant(ExperimentDefinitions.EXPERIMENT_ODD_DAY_ZOO_QUEST) == true)
            {
                GameTransactionManager.addTransaction(new TStartManualQuest("qm_odd_day_zoo"), true);
            }
            if (Global.experimentManager.getVariant(ExperimentDefinitions.EXPERIMENT_ODD_DAY_RUSTY_QUEST) == true)
            {
                GameTransactionManager.addTransaction(new TStartManualQuest("qm_odd_day_rusty"), true);
            }
            if (Global.experimentManager.getVariant(ExperimentDefinitions.EXPERIMENT_LANDMARK_SAILBOAT) > 0)
            {
                GameTransactionManager.addTransaction(new TStartManualQuest("qm_landmark_sailboat_hotel"), true);
            }
            if (Global.experimentManager.getVariant(ExperimentDefinitions.EXPERIMENT_SAND_CASTLE_BOOTH) == true)
            {
                GameTransactionManager.addTransaction(new TStartManualQuest("qf_sandcastle"), true);
            }
            if (Global.experimentManager.getVariant(ExperimentDefinitions.EXPERIMENT_SAND_CASTLE_SURFSHOP) == true)
            {
                GameTransactionManager.addTransaction(new TStartManualQuest("qf_beach_carnival_quest"), true);
            }
            if (Global.experimentManager.getVariant(ExperimentDefinitions.EXPERIMENT_PROMO_FARMERS) == true)
            {
                GameTransactionManager.addTransaction(new TStartManualQuest("qm_farmers"), true);
            }
            if (Global.experimentManager.getVariant(ExperimentDefinitions.EXPERIMENT_POWERSTATIONS) == ExperimentDefinitions.EXPERIMENT_POWERSTATIONS_SHOW)
            {
                GameTransactionManager.addTransaction(new TStartManualQuest("qf_dam_1"), true);
            }
            if (Global.experimentManager.getVariant(ExperimentDefinitions.EXPERIMENT_TIMED_CLOWN_QUEST) == true)
            {
                GameTransactionManager.addTransaction(new TStartManualQuest("qm_clown"), true);
            }
            if (Global.experimentManager.getVariant(ExperimentDefinitions.EXPERIMENT_REMODEL_V1) == ExperimentDefinitions.REMODEL_V1_FEATURE)
            {
                GameTransactionManager.addTransaction(new TStartManualQuest("qm_remodeling"), true);
            }
            if (Global.experimentManager.getVariant(ExperimentDefinitions.EXPERIMENT_PROMO_BBUY) == true)
            {
                GameTransactionManager.addTransaction(new TStartManualQuest("qm_promo_bbuy"), true);
            }
            if (Global.experimentManager.getVariant(ExperimentDefinitions.EXPERIMENT_PROMO_BBUY_REACT) == true)
            {
                GameTransactionManager.addTransaction(new TStartManualQuest("qm_promo_2bbuy"), true);
            }
            if (Global.experimentManager.getVariant(ExperimentDefinitions.EXPERIMENT_STADIUM_BASEBALL) == true)
            {
                GameTransactionManager.addTransaction(new TStartManualQuest("qm_stadiums_baseball"), true);
            }
            if (Global.experimentManager.getVariant(ExperimentDefinitions.EXPERIMENT_STADIUM_SOCCER) == true)
            {
                GameTransactionManager.addTransaction(new TStartManualQuest("qm_stadiums_soccer"), true);
            }
            if (Global.experimentManager.getVariant(ExperimentDefinitions.EXPERIMENT_SPYAGENCY) == true)
            {
                GameTransactionManager.addTransaction(new TStartManualQuest("qm_spyagency"), true);
            }
            if (Global.experimentManager.getVariant(ExperimentDefinitions.EXPERIMENT_CITYSYMPHONY) == true)
            {
                GameTransactionManager.addTransaction(new TStartManualQuest("qm_citysymphony"), true);
            }
            if (Global.experimentManager.getVariant(ExperimentDefinitions.EXPERIMENT_PRODIGYSTUDIOS) == true)
            {
                GameTransactionManager.addTransaction(new TStartManualQuest("qm_prodigystudios"), true);
            }
            if (Global.experimentManager.getVariant(ExperimentDefinitions.EXPERIMENT_COLISEUM) == true)
            {
                GameTransactionManager.addTransaction(new TStartManualQuest("qm_coliseum"), true);
            }
            if (Global.experimentManager.getVariant(ExperimentDefinitions.EXPERIMENT_GARDEN) == true)
            {
                GameTransactionManager.addTransaction(new TStartManualQuest("qf_gardens"), true);
            }
            if (Global.experimentManager.getVariant(ExperimentDefinitions.EXPERIMENT_CARS) == true)
            {
                GameTransactionManager.addTransaction(new TStartManualQuest("qf_cars"), true);
            }
            if (Global.experimentManager.getVariant(ExperimentDefinitions.EXPERIMENT_TIKI_SOCIAL) > 0)
            {
                GameTransactionManager.addTransaction(new TStartManualQuest("qf_tikisocial"), true);
            }
            if (Global.experimentManager.getVariant(ExperimentDefinitions.EXPERIMENT_HALLOWEEN_HOTEL) == true)
            {
                GameTransactionManager.addTransaction(new TStartManualQuest("qf_halloween_2_2"), true);
            }
            if (Global.experimentManager.getVariant(ExperimentDefinitions.EXPERIMENT_GOV_RUN_TOYMAKER_QUEST) == true)
            {
                GameTransactionManager.addTransaction(new TStartManualQuest("q_governor_run_toy_1"), true);
            }
            if (Global.experimentManager.getVariant(ExperimentDefinitions.EXPERIMENT_UNIVERSITIES) == true)
            {
                GameTransactionManager.addTransaction(new TStartManualQuest("qf_universities"), true);
            }
            if (Global.experimentManager.getVariant(ExperimentDefinitions.EXPERIMENT_HALLOWEENNEIGHBORHOOD) == true)
            {
                GameTransactionManager.addTransaction(new TStartManualQuest("q_halloweenneighborhood"), true);
            }
            if (Global.experimentManager.getVariant(ExperimentDefinitions.EXPERIMENT_ANIMAL_RESCUE) == true)
            {
                GameTransactionManager.addTransaction(new TStartManualQuest("qf_animalrescue"), true);
            }
            if (Global.experimentManager.getVariant(ExperimentDefinitions.EXPERIMENT_CANDY_THEME_SUNSET) == true)
            {
                GameTransactionManager.addTransaction(new TStartManualQuest("qf_candybooth"), true);
            }
            if (Global.experimentManager.getVariant(ExperimentDefinitions.EXPERIMENT_GOV_RUN_NATURE_QUEST) == true)
            {
                GameTransactionManager.addTransaction(new TStartManualQuest("q_governor_run_act2_nature_1"), true);
            }
            if (Global.experimentManager.getVariant(ExperimentDefinitions.EXPERIMENT_HALLOWEEN_SAGA_ACT3) == true)
            {
                GameTransactionManager.addTransaction(new TStartManualQuest("qf_halloween_3"), true);
            }
            if (Global.experimentManager.getVariant(ExperimentDefinitions.EXPERIMENT_MONSTER_DENTIST) == true)
            {
                GameTransactionManager.addTransaction(new TStartManualQuest("q_monsterdentist"), true);
            }
            if (Global.experimentManager.getVariant(ExperimentDefinitions.EXPERIMENT_ENRIQUE_QUEST) > 0)
            {
                GameTransactionManager.addTransaction(new TStartManualQuest("qf_enrique_1"), true);
            }
            if (Global.experimentManager.getVariant(ExperimentDefinitions.EXPERIMENT_MJ_EVENT) > 0)
            {
                GameTransactionManager.addTransaction(new TStartManualQuest("qf_mj_1"), true);
            }
            if (Global.experimentManager.getVariant(ExperimentDefinitions.EXPERIMENT_CIDERMILL_QUEST) == true)
            {
                GameTransactionManager.addTransaction(new TStartManualQuest("qt_cidermill"), true);
            }
            if (Global.experimentManager.getVariant(ExperimentDefinitions.EXPERIMENT_APPLEFARM_QUEST) == true)
            {
                GameTransactionManager.addTransaction(new TStartManualQuest("qm_applefarm"), true);
            }
            Array _loc_1 =.get(ExperimentDefinitions.CASINO_SOCIAL_CONTROL1 ,ExperimentDefinitions.CASINO_SOCIAL_CONTROL2 ,ExperimentDefinitions.CASINO_SOCIAL_EXPERIMENT1 ,ExperimentDefinitions.CASINO_SOCIAL_EXPERIMENT2) ;
            _loc_2 =Global.experimentManager.getVariant(ExperimentDefinitions.EXPERIMENT_CASINO_SOCIAL );
            if (_loc_1.indexOf(_loc_2) >= 0)
            {
                GameTransactionManager.addTransaction(new TStartManualQuest("qf_casinosocial"), true);
            }
            if (Global.experimentManager.getVariant(ExperimentDefinitions.EXPERIMENT_LANDMARK_SAILBOAT) > 0)
            {
                GameTransactionManager.addTransaction(new TStartManualQuest("q_wonder_eiffel_tower"), true);
            }
            if (Global.experimentManager.getVariant(ExperimentDefinitions.EXPERIMENT_BIRTHDAY_2011_QUEST) > 0)
            {
                GameTransactionManager.addTransaction(new TStartManualQuest("qf_birthday_2011"), true);
            }
            if (Global.experimentManager.getVariant(ExperimentDefinitions.EXPERIMENT_SWISSMUSEUM) == true)
            {
                GameTransactionManager.addTransaction(new TStartManualQuest("qm_swissmuseum"), true);
            }
            return;
        }//end

    }



