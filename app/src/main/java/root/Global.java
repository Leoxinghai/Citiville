package root;

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

import com.xiyu.logic.Sprite;
import com.xiyu.logic.Stage;
import com.xiyu.logic.TextField;
import com.xiyu.util.Array;
import com.xiyu.util.Dictionary;

import Classes.*;
import Classes.Managers.*;
import Classes.announcements.*;
import Classes.util.*;
import Classes.virals.*;
import Display.*;
import Display.hud.*;
import Engine.Managers.*;
import Init.*;
import Modules.achievements.*;
import Modules.crew.*;
import Modules.dataservices.*;
import Modules.franchise.*;
import Modules.goals.*;
import Modules.guide.*;
import Modules.itemcount.*;
import Modules.quest.Managers.*;
import Modules.sale.*;
import Modules.stats.experiments.*;
import Modules.stats.trackers.*;
import Modules.stats.types.*;
import Modules.sunset.*;
import Modules.workers.*;
import Modules.zoo.*;
import ZLocalization.*;
//import flash.display.*;
//import flash.events.*;
//import flash.text.*;
//import flash.utils.*;
import scripting.*;
import validation.*;


    public class Global
    {
        public static boolean DEBUG_MODE_TRACE =false ;
        public static Stage stage =null ;
        public static HUD hud ;
        public static UI ui ;
        public static Guide guide ;
        public static Player player ;
        public static GameWorld world ;
        private static GameSettingsInit m_gameSettings =null;
        public static BootstrapInit bootstrap ;
        public static PaymentsSaleManager paymentsSaleManager ;
        public static MarketSaleManager marketSaleManager ;
        public static SunsetManager sunsetManager ;
        public static ScriptingManager scriptingManager ;
        public static ValidationManager validationManager ;
        public static AnnouncementManager announcementManager ;
        public static GameQuestManager questManager ;
        public static FranchiseManager franchiseManager ;
        public static ExperimentManager experimentManager ;
        public static CrossPromoManager xpromoManager ;
        public static AchievementManager achievementsManager ;
        public static GoalManager goalManager ;
        public static PopulationMilestoneManager populationMilestoneManager ;
        public static CrewMap crews ;
        public static WorkerManager factoryWorkerManager ;
        public static WorkerManager trainWorkerManager ;
        public static TicketManager ticketManager ;
        public static ZooManager zooManager ;
        public static ItemCountManager itemCountManager ;
        public static RollCallManager rollCallManager ;
        public static StagePickManager stagePickManager ;
        public static DataServicesManager dataServicesManager ;
        public static FlashMFSManager flashMFSManager ;
        public static EmbeddedArt embeddedArt =new EmbeddedArt ();
        public static DelayedAssetLoader delayedAssets =new DelayedAssetLoader ();
        private static String visiting ;
        public static String townName ;
        public static Array friendbar ;
        public static ActionQueue postInitActions =new ActionQueue ();
        public static Object queryString ;
        public static int lastInputTick =0;
        public static boolean appActive =true ;
        public static Object mission =new Object ();
        public static String missionHostFirstName =null ;
        public static int pendingPresents =0;
        public static boolean autoPublishEnabled =false ;
        public static MarketSessionTracker marketSessionTracker =new MarketSessionTracker ();
        public static boolean playAnimations =true ;
        public static Object flashHotParams =new Object ();
        public static Object disableMenu =false ;
        public static boolean zmcOpenedOnInit =false ;
        public static Localizer localizer ;
        public static boolean isTransitioningWorld =false ;
        public static Creatives creatives =null ;
        public static CityRunSpace runspace ;
        public static String citySamNeighborCard =null ;
        public static double playerPopulation =0;
        public static boolean disableLoadPopups =false ;
        public static boolean disableGamePopups =false ;

        //add by xinghai
        public static Game game ;

        public static Sprite circle ;
        public static Sprite circle1 ;
        public static Sprite circle2 ;
        public static int create_seq ;

        public static WeiboManager weiboManager ;

        public static UIInit uiinit ;

        public  Global ()
        {
            return;
        }//end

        public static GameSettingsInit  gameSettings ()
        {
            if (m_gameSettings != null)
            {
                ErrorManager.addError("Global: accessing null gameSettings.",0,null);
                StatsManager.count(StatsCounterType.ERRORS, StatsKingdomType.LOADAPP, "gamesettings", "empty","","",1);
            }
            else if (!m_gameSettings.hasCompleted())
            {
                ErrorManager.addError("Global: accessing gameSettings before init has finished.",0,null);
                StatsManager.count(StatsCounterType.ERRORS, StatsKingdomType.LOADAPP, "gamesettings", "uninitialized","","",1);
            }
            return m_gameSettings;
        }//end

        public static void  gameSettings (GameSettingsInit param1 )
        {
            m_gameSettings = param1;
            return;
        }//end

        public static String  getAssetURL (String param1 )
        {
            return GlobalEngine.getAssetUrl(param1);
        }//end

        public static void  setVisiting (String param1 )
        {
            if (isVisiting() && Global.visiting != param1)
            {
                NeighborVisitManager.sendRTNeighborVisitMessage();
            }
            Global.visiting = param1;
            if (Global.hud != null)
            {
                Global.hud.setVisiting(isVisiting());
                NeighborVisitManager.resetVisitFlags();
            }
            return;
        }//end

        public static String  getVisiting ()
        {
            return Global.visiting;
        }//end

        public static boolean  isVisiting ()
        {
            return Global.visiting != null;
        }//end

        public static String  getClassName (Object param1 )
        {
            String _loc_2 = getQualifiedClassName(param1);
            int _loc_3 = _loc_2.lastIndexOf(":");
            if (_loc_3 >= 0)
            {
                _loc_2 = _loc_2.substring((_loc_3 + 1));
            }
            return _loc_2;
        }//end

        public static void trace (String param1 )
        {
            TextField debug;
            TextField info_text ;
            String  value = param1;
            /*
            if (Global.DEBUG_MODE_TRACE)
            {
                debug =(TextField) Global.stage.getChildByName("flash_debugger");
                if (debug == null)
                {
                    info_text = new TextField();
                    info_text.name = "flash_debugger";
                    info_text.y = 55;
                    info_text.height = 250;
                    info_text.width = 200;
                    info_text.border = true;
                    info_text.scrollV = 0;
                    Global.stage.addChild(info_text);
                    debug =(TextField) Global.stage.getChildByName("flash_debugger");
                    debug.x = 550;
                    Global.stage.addEventListener (FullScreenEvent.FULL_SCREEN , (FullScreenEvent event )
            {
                if (event.fullScreen)
                {
                    debug.x = 1050;
                }
                else
                {
                    debug.x = 550;
                }
                return;
            }//end
            );
                }
                (debug.scrollV + 1);
                debug.appendText(value + "\n");
            }
            */
            return;
        }//end

        public static void  startTutorial ()
        {
            return;
        }//end

        public static Player  getPlayer ()
        {
            //_loc_1 = Object(player).constructor;
            //return new _loc_1(GlobalEngine.socialNetwork ? (GlobalEngine.socialNetwork.getLoggedInUser()) : (null));
            if(player == null) {
                player = new Player(GlobalEngine.socialNetwork !=null ? (GlobalEngine.socialNetwork.getLoggedInUser()) : (null));
            }
            return player;
        }//end

        public static boolean  isFriend (String param1 )
        {
            Friend _loc_3 =null ;
            boolean _loc_2 =false ;
            for(int i0 = 0; i0 < friendbar.size(); i0++) 
            {
            		_loc_3 = (Friend)friendbar.get(i0);

                if (_loc_3.uid == param1)
                {
                    _loc_2 = !_loc_3.isNonSNFriend;
                }
            }
            return _loc_2;
        }//end

    }



