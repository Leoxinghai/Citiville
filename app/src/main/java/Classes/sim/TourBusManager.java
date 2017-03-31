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
import Classes.actions.*;
import Classes.effects.*;
import Classes.util.*;
import Engine.Classes.*;
import Modules.stats.types.*;

    public class TourBusManager
    {
        private static int tourBusNPCCount =0;

        public  TourBusManager ()
        {
            return;
        }//end

        public void  addTourBus (IMerchant param1 ,Function param2 )
        {
            MapResource resource ;
            SoundObject loopsound ;
            TourBus tourBus ;
            merchant = param1;
            arrivalFunction = param2;
            resource = merchant.getMapResource();
            resource.lockForReplay();
            Sounds.play("tourbus_start");
            loopsound = Sounds.play("tourbus_engine_loop", 0, Sounds.LOOPING);
            _loc_5 = tourBusNPCCount+1;
            tourBusNPCCount = _loc_5;
            nearRoad = Global.world.citySim.roadManager.findClosestRoad(resource.getPosition());
            if (!nearRoad)
            {
                arrivalFunction();
                return;
            }
            startRoad = Global.world.citySim.roadManager.findRandomRoadOnScreen(nearRoad);
            tourBus = new TourBus(Business.TOUR_BUS, false);
            tourBus.setPosition(startRoad.getHotspot().x, startRoad.getHotspot().y);
            Global.world.citySim.npcManager.addVehicle(tourBus);
            actions = tourBus.getStateMachine();
            actions.removeAllStates();
            actions .addActions (new ActionAnimationEffect (tourBus ,EffectType .VEHICLE_POOF ),new ActionNavigate (tourBus ,resource ,startRoad ).setPathType (RoadManager .PATH_ROAD_ONLY ),new ActionFn (tourBus ,void  ()
            {
                tourBus.full = false;
                unloadTourists(merchant);
                return;
            }//end
            ),new ActionFn (tourBus ,void  ()
            {
                Sounds.stop(loopsound);
                Sounds.play("tourbus_honk");
                Sounds.play("peopleMoveIn");
                return;
            }//end
            ),new ActionProgressBar (tourBus ,resource ,ZLoc .t ("Main","UnloadingTourists")),new ActionFn (tourBus ,void  ()
            {
                arrivalFunction();
                Sounds.play("tourbus_start");
                loopsound = Sounds.play("tourbus_engine_loop", 0, Sounds.LOOPING);
                resource.unlockForReplay();
                return;
            }//end
            ),new ActionNavigate (tourBus ,startRoad ,resource ),new ActionFn (tourBus ,void  ()
            {
                Sounds.stop(loopsound);
                Sounds.play("tourbus_stop");
                return;
            }//end
            ), new ActionDie(tourBus));
            resource.displayStatus(ZLoc.t("Main", "SendingTourists"));
            resource.trackVisitAction(TrackedActionType.SENT_BUS);
            NeighborVisitManager.setVisitFlag(NeighborVisitManager.VISIT_TOURBUS);
            return;
        }//end

        private void  unloadTourists (IMerchant param1 )
        {
            Global.world.citySim.npcManager.createBusinessPeepsWaiting(param1, 3);
            return;
        }//end

    }



