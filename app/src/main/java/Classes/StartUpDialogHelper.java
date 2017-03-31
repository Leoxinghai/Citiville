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

import Classes.util.*;
import Classes.xgamegifting.*;
import Display.*;
import Display.DialogUI.*;
import Display.MarketUI.*;
import Engine.Classes.*;
import Engine.Managers.*;
import Events.*;
import Modules.pierupgrade.*;
import Modules.saga.*;
import Modules.stats.types.*;
import Transactions.*;
//import flash.events.*;

    public class StartUpDialogHelper
    {
        public static StartUpDialogHelper m_instance ;

        public  StartUpDialogHelper ()
        {
            return;
        }//end

        public Function  getCallback (String param1 )
        {
            if (param1 in this)
            {
                return (Function)this.get(param1);
            }
            return null;
        }//end

        public void  visitGaGaFarm (Array param1 , Object param2)
        {
            if (param2.button == GenericDialogView.YES)
            {
                GlobalEngine.socialNetwork.redirect("xpromo.php?action=sendToFarmGaGa&source=announce", null, "_blank");
            }
            return;
        }//end

        public void  goToCityMobilePage (Array param1 , Object param2)
        {
            if (param2.button == GenericDialogView.YES)
            {
                StatsManager.count("xpromo", "hometown", "opt_in");
                Global.player.setSeenFlag("mobile_xpromo");
                FrameManager.navigateTo("mobile.php");
            }
            else
            {
                StatsManager.count("xpromo", "hometown", "opt_out");
            }
            return;
        }//end

        public void  goToWordsWithFriends (Array param1 , Object param2)
        {
            if (param2.button == GenericDialogView.YES)
            {
                StatsManager.count("xpromo", "wwf", "opt_in");
                Global.player.setSeenFlag("wwf_xpromo");
                GlobalEngine.socialNetwork.redirect("xpromo.php?action=sendToWordsWithFriends&source=announce", null, "_blank");
            }
            else
            {
                StatsManager.count("xpromo", "wwf", "opt_out");
            }
            return;
        }//end

        public void  goToAdventureWorld (Array param1 , Object param2)
        {
            if (param2.button == GenericDialogView.YES)
            {
                StatsManager.count("dialog", "xpromo", "play", "88");
                Global.player.setSeenFlag("adventure_xpromo");
                GlobalEngine.socialNetwork.redirect("xpromo.php?action=sendToAdventure&source=announce", null, "_blank");
            }
            else
            {
                StatsManager.count("dialog", "xpromo", "cancel", "88");
            }
            return;
        }//end

        public void  goToAdventureWorldRedo (Array param1 , Object param2)
        {
            int _loc_3 =0;
            String _loc_4 =null ;
            if (param2.button == GenericDialogView.YES)
            {
                _loc_3 = Global.experimentManager.getVariant("cv_adventure_xpromo_forced");
                _loc_4 = _loc_3 == 2 ? ("play_forced") : ("play_not_forced");
                StatsManager.count("dialog", "xpromo", _loc_4, "88");
                GlobalEngine.socialNetwork.redirect("xpromo.php?action=sendToAdventureRedo&source=announce", null, "_blank");
            }
            else
            {
                StatsManager.count("dialog", "xpromo", "cancel", "88");
            }
            return;
        }//end

        public void  navigateToCashPurchase (Array param1 , Object param2)
        {
            if (param2.button == GenericDialogView.YES)
            {
                FrameManager.navigateTo("money.php");
            }
            return;
        }//end

        public void  goToAdventureWorldMMXIe (Array param1 , Object param2)
        {
            int _loc_3 =0;
            if (param2.button == GenericDialogView.YES)
            {
                _loc_3 = Global.experimentManager.getVariant("cv_adventure_xpromo_mmxi_e");
                StatsManager.count("dialog", "xpromo", "play", "88");
                GlobalEngine.socialNetwork.redirect("xpromo.php?action=sendToAdventureMMXIe&source=announce", null, "_blank");
            }
            else
            {
                StatsManager.count("dialog", "xpromo", "cancel", "88");
            }
            return;
        }//end

        public void  goToFarmLighthouseCove (Array param1 , Object param2)
        {
            if (param2.button == GenericDialogView.YES)
            {
                StatsManager.count("dialog", "xpromo", "play", "63");
                Global.player.setSeenFlag("farm_lighthouse_cove_xpromo");
                GlobalEngine.socialNetwork.redirect("xpromo.php?action=sendToFarmLighthouseCove&source=announce", null, "_blank");
            }
            else
            {
                StatsManager.count("dialog", "xpromo", "cancel", "63");
            }
            return;
        }//end

        public void  goToBestBuy (Array param1 , Object param2)
        {
            if (param2.button == GenericDialogView.YES)
            {
                XGameGiftingManager.instance.beginPromoGifting(ExternalPromoIds.BBUY_PROMO2);
                StatsManager.count("promo", "bbuy2", "opt_in");
                Global.player.setSeenFlag("promo_bbuy2");
                GlobalEngine.socialNetwork.redirect("http://www.facebook.com/bestbuy?v=app_169804823096382", null, "_blank", false);
            }
            else
            {
                StatsManager.count("promo", "bbuy2", "opt_out");
            }
            return;
        }//end

        public void  xpromoButton (Array param1 , Object param2)
        {
            String _loc_3 =null ;
            String _loc_4 =null ;
            if (param2.button == GenericDialogView.YES)
            {
                if (param1.length > 0)
                {
                    _loc_3 = param1.get(0);
                    _loc_4 = "_blank";
                    if (param1.length > 1)
                    {
                        _loc_4 = param1.get(1);
                    }
                    GlobalEngine.socialNetwork.redirect(_loc_3, null, _loc_4);
                }
            }
            return;
        }//end

        public void  completeSagaActIntro (Array param1 , Object param2)
        {
            if (((GenericPopupEvent)param2).button == GenericPopup.ACCEPT)
            {
                SagaManager.instance.notifyIntroDismissal(param1.get(1));
            }
            return;
        }//end

        public void  onExpansionUpgradeOk (Array param1 , Object param2)
        {
            PierAnnouncementManager.instance.onAnnouncementOk();
            return;
        }//end

        public void  completeSagaActOutro (Array param1 , Object param2)
        {
            SagaManager.instance.notifyOutroDismissal(param1.get(0));
            return;
        }//end

        public void  notifyGuide (Array param1 , Object param2)
        {
            _loc_3 = param1.put(0,  "false");
            _loc_4 =(String) param1.get(1);
            _loc_5 = param1.length >2? ((String)param1.get(2)) : (null);
            _loc_6 = param2is GenericPopupEvent && ((GenericPopupEvent)param2).button == GenericDialogView.YES;
            _loc_7 = _loc_5==null || Global.experimentManager.getVariant(_loc_5) > 0;
            if (!_loc_3 || _loc_6)
            {
                Global.guide.notify(_loc_4);
            }
            return;
        }//end

        public void  placeItemForFarmvilleGifting (Array param1 , Object param2)
        {
            MarketEvent _loc_3 =null ;
            EventDispatcher _loc_4 =null ;
            XGameGiftingManager.instance.beginXGameGifting(ExternalGameIds.FARMVILLE);
            StatsManager.count("announcer", "farm_xpromo", "display");
            if ((GenericPopupEvent)param2 && ((GenericPopupEvent)param2).button == GenericPopup.ACCEPT)
            {
                StatsManager.count("announcer", "farm_xpromo", "place");
                _loc_3 = new MarketEvent(MarketEvent.MARKET_BUY, MarketEvent.GENERIC, "deco_xgifting_fvballoon");
                _loc_3.eventSource = MarketEvent.SOURCE_INVENTORY;
                _loc_4 = new EventDispatcher();
                _loc_4.addEventListener(MarketEvent.MARKET_BUY, UI.onMarketClick, false, 0, true);
                _loc_4.dispatchEvent(_loc_3);
            }
            else
            {
                StatsManager.count("announcer", "farm_xpromo", "close");
            }
            return;
        }//end

        public void  openPotatoesInMarket (Array param1 , Object param2)
        {
            UI.displayCatalog(new CatalogParams("farming").setItemName("plot_sweetpotato"));
            return;
        }//end

        public void  openBlueberriesInMarket (Array param1 , Object param2)
        {
            UI.displayCatalog(new CatalogParams("farming"));
            return;
        }//end

        public void  openTrainsInMarket (Array param1 , Object param2)
        {
            Global.world.citySim.trainManager.scrollToTrainStation();
            Global.world.citySim.trainManager.showTrainScheduleCatalog();
            return;
        }//end

        public void  mafiaXPromoRedirect (Array param1 , Object param2)
        {
            if ((GenericPopupEvent)param2 && ((GenericPopupEvent)param2).button == GenericPopup.ACCEPT)
            {
                GlobalEngine.socialNetwork.redirect("xpromo.php?action=sendToMafiaBrazil", null, "_blank");
            }
            return;
        }//end

        public void  openMarketToNewItems (Array param1 , Object param2)
        {
            if ((GenericPopupEvent)param2 && ((GenericPopupEvent)param2).button == GenericPopup.ACCEPT)
            {
                UI.displayCatalog(new CatalogParams("new"));
            }
            return;
        }//end

        public void  scrollToAndActivateAnnouncer (Array param1 , Object param2)
        {
            String _loc_3 =null ;
            if (param1.length > 0)
            {
                _loc_3 = param1.get(0).toString();
                Global.world.citySim.announcerManager.scrollToAnnouncer(_loc_3);
            }
            return;
        }//end

        public void  purchaseStarterPack (double param1 )
        {
            FrameManager.navigateTo("money.php?fbcPopup=true&spVariant=" + param1);
            return;
        }//end

        public void  purchaseStarterPack1 (Array param1 , Object param2)
        {
            this.handleStarterPackPurchase(param2, 1);
            return;
        }//end

        public void  purchaseStarterPack2 (Array param1 , Object param2)
        {
            this.handleStarterPackPurchase(param2, 2);
            return;
        }//end

        public void  purchaseStarterPack3 (Array param1 , Object param2)
        {
            this.handleStarterPackPurchase(param2, 3);
            return;
        }//end

        public void  purchaseStarterPack4 (Array param1 , Object param2)
        {
            this.handleStarterPackPurchase(param2, 4);
            return;
        }//end

        private void  handleStarterPackPurchase (Object param1 ,int param2 )
        {
            if ((GenericPopupEvent)param1 && ((GenericPopupEvent)param1).button == GenericPopup.ACCEPT)
            {
                StatsManager.sample(100, StatsCounterType.DIALOG, StatsKingdomType.ANNOUNCEMENT, "click", "AnnouncementStarterPack" + param2 + "_title");
                this.purchaseStarterPack(param2);
            }
            return;
        }//end

        private boolean  getCanCompleteHardwareStore (WorldObject param1 )
        {
            if (!(param1 instanceof ConstructionSite))
            {
                return false;
            }
            _loc_2 = param1as ConstructionSite ;
            if (_loc_2.targetName != "bus_hardwarestore")
            {
                return false;
            }
            if (_loc_2.currentState == ConstructionSite.STATE_AT_GATE)
            {
                return true;
            }
            return false;
        }//end

        public void  startHardwareStore (Array param1 , Object param2)
        {
            _loc_3 =Global.world.getObjectsByPredicate(this.getCanCompleteHardwareStore );
            ConstructionSite _loc_4 =null ;
            if (_loc_3.length == 1)
            {
                _loc_4 =(ConstructionSite) _loc_3.get(0);
                if (_loc_4.currentState == ConstructionSite.STATE_AT_GATE)
                {
                    _loc_4.onPlayAction();
                    return;
                }
            }
            String _loc_5 ="qm_hardware1";
            _loc_6 =Global.questManager.getQuestProgressByName(_loc_5 );
            if (Global.questManager.getQuestProgressByName(_loc_5) && !Global.player.isQuestCompleted(_loc_5))
            {
                Global.questManager.pumpActivePopup(_loc_5);
                return;
            }
            return;
        }//end

        public void  startBakeryUpgrade (Array param1 , Object param2)
        {
            if (!Global.questManager.hasSeenQuestIntro(Business.UPGRADE_QUEST_NAME))
            {
                Global.player.setSeenFlag(Business.UPGRADES_FLAG_INTRO);
                Global.questManager.pumpActivePopup(Business.UPGRADE_QUEST_NAME);
                Business.refreshAll();
            }
            return;
        }//end

        public void  showQuest (Array param1 , Object param2)
        {
            _loc_3 = param1.get(0);
            _loc_4 =Global.questManager.getQuestProgressByName(_loc_3 );
            if (Global.questManager.getQuestProgressByName(_loc_3) && !Global.player.isQuestCompleted(_loc_3))
            {
                Global.questManager.pumpActivePopup(_loc_3);
                return;
            }
            return;
        }//end

        public void  activateManualQuest (Array param1 , Object param2)
        {
            _loc_3 = param1.get(0);
            GameTransactionManager.addTransaction(new TStartManualQuest(_loc_3));
            return;
        }//end

        public void  openInventory (Array param1 , Object param2)
        {
            String _loc_3 =null ;
            if (param1.length > 0)
            {
                _loc_3 = param1.get(0).toString();
                UI.displayInventory(_loc_3);
            }
            else
            {
                UI.displayInventory();
            }
            return;
        }//end

        public void  openMarketToExpansions (Array param1 , Object param2)
        {
            if ((GenericPopupEvent)param2 && ((GenericPopupEvent)param2).button == GenericPopup.ACCEPT)
            {
                UI.displayCatalog(new CatalogParams("expansion"));
            }
            return;
        }//end

        public void  openMarketToBusinesses (Array param1 , Object param2)
        {
            if ((GenericPopupEvent)param2 && ((GenericPopupEvent)param2).button == GenericPopup.ACCEPT)
            {
                UI.displayCatalog(new CatalogParams("business"));
            }
            return;
        }//end

        public void  openMarket (Array param1 , Object param2)
        {
            if ((GenericPopupEvent)param2 && ((GenericPopupEvent)param2).button == GenericPopup.ACCEPT)
            {
                UI.displayCatalog(new CatalogParams(param1.get(0), param1.get(1)));
            }
            return;
        }//end

        public void  visitCitySam (Array param1 , Object param2)
        {
            double _loc_3 =0;
            if (param2.button == GenericDialogView.YES)
            {
                _loc_3 = int(param1.get(0));
                GameTransactionManager.addTransaction(new TGetVisitMission("-1", null, _loc_3), true);
            }
            return;
        }//end

        public void  goToNewPerms (Array param1 , Object param2)
        {
            if (param2.button == GenericDialogView.YES)
            {
                if (!Global.player.hasExtendedPermissions)
                {
                    StatsManager.count(StatsCounterType.DIALOG_UNSAMPLED, "newperms", "announce_accepted");
                    Global.world.viralMgr.showStreamExtendedPermissions();
                }
                return;
            }
            else
            {
                return;
            }
        }//end

        public void  openMarketToBogo (Array param1 , Object param2)
        {
            CatalogParams _loc_3 =null ;
            if ((GenericPopupEvent)param2 && ((GenericPopupEvent)param2).button == GenericPopup.ACCEPT)
            {
                _loc_3 = new CatalogParams("specials");
                _loc_3.setSubType("bogo");
                UI.displayCatalog(_loc_3);
            }
            return;
        }//end

        public void  startHalloweenMinigame (Array param1 , Object param2)
        {
            if ((GenericPopupEvent)param2 && ((GenericPopupEvent)param2).button == GenericPopup.ACCEPT)
            {
                Global.ui.startPickThings("mg_halloween1");
            }
            return;
        }//end

        public void  startThanksgivingMinigame (Array param1 , Object param2)
        {
            if ((GenericPopupEvent)param2 && ((GenericPopupEvent)param2).button == GenericPopup.ACCEPT)
            {
                Global.ui.startPickThings("mg_thanksgiving1");
            }
            return;
        }//end

        public static StartUpDialogHelper  instance ()
        {
            if (!m_instance)
            {
                m_instance = new StartUpDialogHelper;
            }
            return m_instance;
        }//end

    }

