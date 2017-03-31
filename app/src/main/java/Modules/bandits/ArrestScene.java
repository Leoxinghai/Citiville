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

import Classes.*;
import Classes.actions.*;
import Classes.effects.*;
import Classes.sim.*;
import Classes.util.*;
import Engine.Classes.*;
import Engine.Helpers.*;
import com.zynga.skelly.util.*;

    public class ArrestScene extends CaptureScene
    {

        public  ArrestScene (PreyData param1 ,Vector3 param2 ,boolean param3 =false )
        {
            super(param1, param2, param3);
            return;
        }//end

         protected Array  getHunterCaptureAnimationStates ()
        {
            return .get("arrest_1", "arrest_2", "arrest_3");
        }//end

         protected String  getTargetDefaultCaptureState ()
        {
            return "idle";
        }//end

         protected void  spawnVehicles ()
        {
            int cop ;
            String type ;
            NPCActionQueue actions ;
            SoundObject loopsound ;
            super.spawnVehicles();
            Road nearRoad ;
            Road startRoad ;
            level = PreyUtil.getHubLevel(groupID);
            Vehicle car ;
            if (level == PreyManager.SUPERHERO_LEVEL)
            {
                PreyManager.sendSuperHero(m_data.groupId, getPosition());
            }
            else if (level == PreyManager.HELICOPTER_LEVEL || m_cashScene)
            {
                PreyManager.sendHelicopter(m_data.groupId, findFlyingNPCDropPoint(), this.m_cashScene);
            }
            else if (level >= 3)
            {
                cop;
                while (cop < m_numCars)
                {

                    type = level == 3 ? (PreyManager.NPC_POLICECRUISER) : (level == 4 ? (PreyManager.NPC_POLICEVAN) : (PreyManager.NPC_POLICESPORTCRUISER));
                    car = new Vehicle(type, false);
                    m_npcs.push(car);
                    if (car)
                    {
                        nearRoad = Global.world.citySim.roadManager.findClosestRoad(getPosition());
                        if (!nearRoad)
                        {
                        }
                        else
                        {
                            startRoad = Global.world.citySim.roadManager.findRandomRoadOnScreen(nearRoad);
                            car.setPosition(startRoad.getHotspot().x, startRoad.getHotspot().y);
                            Global.world.citySim.npcManager.addVehicle(car);
                            actions = car.getStateMachine();
                            actions.removeAllStates();
                            actions .addActions (new ActionAnimationEffect (car ,EffectType .VEHICLE_POOF ),new ActionFn (car ,void  ()
            {
                Sounds.play("siren");
                loopsound = Sounds.play("tourbus_engine_loop", 0, Sounds.LOOPING);
                return;
            }//end
            ),new ActionNavigate (car ,nearRoad ,startRoad ).setPathType (RoadManager .PATH_ROAD_ONLY ),new ActionFn (car ,void  ()
            {
                Sounds.stop(loopsound);
                return;
            }//end
            ),new ActionPause (car ,2),new ActionFn (car ,void  ()
            {
                loopsound = Sounds.play("tourbus_engine_loop", 0, Sounds.LOOPING);
                return;
            }//end
            ),new ActionNavigate (car ,startRoad ,nearRoad ),new ActionFn (car ,void  ()
            {
                Sounds.stop(loopsound);
                return;
            }//end
            ),new ActionFn (car ,Curry .curry (void  (Vehicle param1 )
            {
                Global.world.citySim.npcManager.removeVehicle(param1);
                return;
            }//end
            , car)), new ActionDie(car));
                        }
                    }
                    cop = (cop + 1);
                }
            }
            return;
        }//end

    }



