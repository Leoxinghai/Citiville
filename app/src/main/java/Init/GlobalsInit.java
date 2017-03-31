package Init;

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
import Classes.Managers.*;
import Classes.announcements.*;
import Engine.Classes.*;
import Engine.Init.*;
import Engine.Managers.*;
import Modules.crew.*;
import Modules.dataservices.*;
import Modules.franchise.*;
import Modules.goals.*;
import Modules.sale.*;
import Modules.sunset.*;
import Modules.zoo.*;
//import flash.events.*;
import scripting.*;
import validation.*;

    public class GlobalsInit extends InitializationAction
    {
        public static  String INIT_ID ="GlobalsInit";

        public  GlobalsInit ()
        {
            super(INIT_ID);
            addDependency(LocalizationInit.INIT_ID);
            addDependency(GameSettingsInit.INIT_ID);
            addDependency(SocialNetworkInit.INIT_ID);
            return;
        }//end

         public void  execute ()
        {
            String msg ;
            try
            {
                Global.player = new Player(GlobalEngine.socialNetwork ? (GlobalEngine.socialNetwork.getLoggedInUser()) : (null));
                Global.scriptingManager = new ScriptingManager();
                Global.validationManager = new ValidationManager();
                Global.announcementManager = new AnnouncementManager();
                Global.paymentsSaleManager = new PaymentsSaleManager();
                Global.marketSaleManager = new MarketSaleManager();
                Global.sunsetManager = new SunsetManager();
                Global.franchiseManager = new FranchiseManager();
                Global.zooManager = new ZooManager();
                Global.rollCallManager = new RollCallManager();
                Global.crews = new CrewMap();
                Global.goalManager = new GoalManager();
                Global.stagePickManager = new StagePickManager();
                Global.dataServicesManager = new DataServicesManager();
                Global.flashMFSManager = new FlashMFSManager();
                if (GlobalEngine.socialNetwork)
                {
                    Global.player.setFriends(GlobalEngine.socialNetwork.getFriendUsers());
                }
                Global.world = new GameWorld();
                Global.world.addEventListener(Event.COMPLETE, this.onGameWorldInitialized);
                Global.world.initialize();
            }
            catch (e:Error)
            {
                msg = e.toString();
                if (!Global.player)
                {
                    msg = "Global.player instanceof null:" + msg;
                }
                else if (!Global.world)
                {
                    msg = "Global.world instanceof null:" + msg;
                }
                StatsManager.count("user_stuck", "GlobalsInit", msg);
                StatsManager.sendStats(true);
                throw Error(msg);
            }
            return;
        }//end

        protected void  onGameWorldInitialized (Event event )
        {
            Global.world.removeEventListener(Event.COMPLETE, this.onGameWorldInitialized);
            dispatchEvent(new Event(Event.COMPLETE));
            return;
        }//end

    }



