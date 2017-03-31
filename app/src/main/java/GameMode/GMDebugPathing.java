package GameMode;

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
//import flash.events.*;

    public class GMDebugPathing extends GMDefault
    {
        protected MapResource m_downObject =null ;
        protected MapResource m_upObject =null ;
        protected int m_count =0;
        public static  String DELIVERY_VEHICLE ="van_delivery";
        public static  String TOUR_BUS ="tourismbus";

        public  GMDebugPathing ()
        {
            m_cursorImage = EmbeddedArt.trainUIPick;
            m_showMousePointer = true;
            return;
        }//end

         protected boolean  isObjectHighlightable (GameObject param1 )
        {
            return isObjectSelectable(param1);
        }//end

         public boolean  onMouseMove (MouseEvent event )
        {
            super.onMouseMove(event);
            return true;
        }//end

         public boolean  onMouseOut (MouseEvent event )
        {
            super.onMouseOut(event);
            return true;
        }//end

         public boolean  onMouseDown (MouseEvent event )
        {
            super.onMouseDown(event);
            if (this.m_downObject == null)
            {
                this.m_downObject =(MapResource) m_selectedObject;
            }
            return true;
        }//end

        protected void  doTourBus (Business param1 )
        {
            business = param1;
            Sounds.play("tourbus_start");
            Sounds.play("tourbus_engine_loop", 0, Sounds.LOOPING);
            nearRoad = Global.world.citySim.roadManager.findClosestRoad(business.getPosition());
            startRoad = Global.world.citySim.roadManager.findRandomRoadOnScreen(nearRoad);
            TourBus m_tourBus =new TourBus(TOUR_BUS ,false );
            m_tourBus.setPosition(startRoad.getHotspot().x, startRoad.getHotspot().y);
            Global.world.citySim.npcManager.addVehicle(m_tourBus);
            actions = m_tourBus.getStateMachine();
            actions.removeAllStates();
            actions .addActions (new ActionAnimationEffect (m_tourBus ,EffectType .VEHICLE_POOF ),new ActionNavigate (m_tourBus ,business ,startRoad ).setPathType (RoadManager .PATH_ROAD_ONLY ),new ActionFn (m_tourBus ,void  ()
            {
                Sounds.stop("tourbus_engine_loop");
                Sounds.play("tourbus_honk");
                return;
            }//end
            ),new ActionProgressBar (m_tourBus ,business ,ZLoc .t ("Main","UnloadingTourists")),new ActionFn (m_tourBus ,void  ()
            {
                Sounds.play("tourbus_start");
                Sounds.play("tourbus_engine_loop", 0, Sounds.LOOPING);
                return;
            }//end
            ),new ActionNavigate (m_tourBus ,startRoad ,business ),new ActionFn (m_tourBus ,void  ()
            {
                Sounds.stop("tourbus_engine_loop");
                Sounds.play("tourbus_stop");
                return;
            }//end
            ), new ActionDie(m_tourBus));
            return;
        }//end

        public void  getCommodity (Storage param1 ,MapResource param2 )
        {
            storage = param1;
            target = param2;
            Sounds.play("truck_start");
            Sounds.play("truck_engine_loop", 0, 15);
            startRoad = Global.world.citySim.roadManager.findClosestRoad(storage.getHotspot());
            m_deliveryVan = Global.world.citySim.npcManager.createVehicleByName(startRoad,DELIVERY_VEHICLE,false);
            actions = m_deliveryVan.getStateMachine();
            actions.removeAllStates();
            actions .addActions (new ActionNavigate (m_deliveryVan ,target ,storage ).setPathType (RoadManager .PATH_ROAD_ONLY ),new ActionFn (m_deliveryVan ,void  ()
            {
                Sounds.stop("truck_engine_loop");
                Sounds.play("truck_honk");
                Sounds.play("truck_off");
                return;
            }//end
            ), new ActionDie(m_deliveryVan));
            return;
        }//end

         public boolean  onMouseUp (MouseEvent event )
        {
            NPC _loc_2 =null ;
            super.onMouseUp(event);
            this.m_upObject =(MapResource) m_highlightedObject;
            if (this.m_upObject == this.m_downObject && this.m_upObject != null && this.m_count == 0)
            {
                this.highlightObject(this.m_upObject);
                this.m_count++;
                return true;
            }
            if (this.m_upObject != null && this.m_downObject == this.m_upObject && this.m_upObject instanceof Business)
            {
                this.doTourBus(this.m_upObject as Business);
            }
            else if (this.m_upObject != null && this.m_downObject != null && this.m_upObject != this.m_downObject)
            {
                if (this.m_downObject instanceof Storage)
                {
                    this.getCommodity(this.m_downObject as Storage, this.m_upObject);
                }
                else
                {
                    _loc_2 = Global.world.citySim.npcManager.createWalker(this.m_downObject, true);
                    _loc_2.getStateMachine().addActions(new ActionNavigate(_loc_2, this.m_upObject, this.m_downObject), new ActionDie(_loc_2));
                }
            }
            Global.world.removeGameMode(this);
            return true;
        }//end

    }



