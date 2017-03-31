package Classes.util;

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

import Engine.*;
import Engine.Classes.*;
import Engine.Init.*;
import Engine.Managers.*;
import com.xinghai.Debug;

    public class Sounds
    {
        public static  String SET_MONEY ="set_money";
        public static  String SET_HARVEST ="set_harvest";
        public static  String SET_PLANT ="set_plant";
        public static  String SET_CLICK ="set_click";
        public static  String SET_SHEEP ="set_sheep";
        public static  String SET_FANFARE ="set_fanfare";
        public static  String SET_BUILDING_CONSTRUCTION ="set_construction";
        public static  String SET_CHEERING ="set_cheering";
        public static  int LOOPING =999999;
        public static Array infiniteLoopingSounds =new Array();
        private static boolean doneInit =false ;
        private static SoundObjectWrapper musicObject =null ;
        private static boolean musicStarted =false ;
        private static boolean donePostLoad =false ;
        public static  Object ALL_SETS ={set_forceLoad ANY {"click2","doober_drop","bonus_ramp_1","bonus_ramp_2","bonus_ramp_3","bonus_ramp_4","bonus_ramp_5","bonus_ramp_6","doober_coin_click","doober_xp_click","doober_energy_click","doober_food_click","doober_population_click","place_building","building_construct1","train_leaves","train_arrives","train_moving","train_unload","doorbell1","doorbell2","knock1","knock2","knock3","lumberjack","peopleMoveIn","tourbus_start","tourbus_engine_loop","tourbus_stop","tourbus_honk","sfx_fanfare1","arrest","siren","moneybag","helicopter","dog_rescue"] },set_money {"money1","money2","money3"] },set_harvest {"knock1","knock2","knock3","doorbell1","doorbell2"] ,Business "harvest1","harvest2","harvest3"] ,ANY "harvest1","harvest2","harvest3"] },set_plant {"plant_plot_1","plant_plot_2","plant_plot_3"] },set_click {"UI_click1","UI_click2","UI_click3"] },set_sheep {"sfx_sheep1","sfx_sheep2","sfx_sheep3"] },set_fanfare {"sfx_fanfare1"] },set_construction {"building_construct1"] },set_cheering {"cheering1","cheering2","cheering3","cheering4"] }};
        public static  Object ALL_SOUNDS ={money1 money2 "assets/sounds/actions/sfx_receive_coins_01.mp3","assets/sounds/actions/sfx_receive_coins_02.mp3"money3 ,"assets/sounds/actions/sfx_receive_coins_03.mp3",harvest1 "assets/sounds/city_sounds/harvest/sfx_harvest_crop_01.mp3","assets/sounds/city_sounds/harvest/sfx_harvest_crop_02.mp3","assets/sounds/city_sounds/harvest/sfx_harvest_crop_03.mp3","assets/sounds/city_sounds/harvest_houses/sfx_doorbell1.mp3","assets/sounds/city_sounds/harvest_houses/sfx_doorbell2.mp3","assets/sounds/city_sounds/harvest_houses/sfx_knock1.mp3","assets/sounds/city_sounds/harvest_houses/sfx_knock2.mp3","assets/sounds/city_sounds/harvest_houses/sfx_knock3.mp3","assets/sounds/city_sounds/plant/sfx_plant_01.mp3","assets/sounds/city_sounds/plant/sfx_plant_02.mp3","assets/sounds/city_sounds/plant/sfx_plant_03.mp3","assets/sounds/city_sounds/ship/sfx_ship_leaves_dock.mp3","assets/sounds/city_sounds/ship/sfx_ship_docking.mp3","assets/sounds/city_sounds/ship/sfx_ship_moving_through_water_loop.mp3","assets/sounds/city_sounds/ship/sfx_ship_you_can_now_use_ships.mp3","assets/sounds/city_sounds/ship/sfx_ship_receiving_payload.mp3","assets/sounds/city_sounds/truck_sfx/sfx_truck_start.mp3","assets/sounds/city_sounds/truck_sfx/sfx_truck_engine_loop.mp3","assets/sounds/city_sounds/truck_sfx/sfx_truck_off.mp3","assets/sounds/city_sounds/truck_sfx/sfx_horn_honkhonk.mp3","assets/sounds/city_sounds/train/sfx_train_leaves.mp3","assets/sounds/city_sounds/train/sfx_train_arrives.mp3","assets/sounds/city_sounds/train/sfx_train_moving_loop.mp3","assets/sounds/city_sounds/train/sfx_train_food_delivered.mp3","assets/sounds/city_sounds/construction/sfx_place_building.mp3","assets/sounds/city_sounds/construction/sfx_building_construct1.mp3","assets/sounds/city_sounds/lumberjack/sfx_chop_tree.mp3","assets/sounds/city_sounds/expand/sfx_land_expand.mp3","assets/sounds/city_sounds/animals/sfx_sheep_1_01.mp3","assets/sounds/city_sounds/animals/sfx_sheep_1_02.mp3","assets/sounds/city_sounds/animals/sfx_sheep_1_03.mp3","assets/sounds/city_sounds/animals/sfx_chicken_01.mp3","assets/sounds/city_sounds/animals/sfx_chicken_02.mp3","assets/sounds/city_sounds/animals/sfx_chicken_03.mp3","assets/sounds/city_sounds/animals/sfx_cow_01.mp3","assets/sounds/city_sounds/animals/sfx_cow_02.mp3","assets/sounds/city_sounds/animals/sfx_cow_03.mp3","assets/sounds/city_sounds/animals/sfx_goat_01.mp3","assets/sounds/city_sounds/animals/sfx_goat_02.mp3","assets/sounds/city_sounds/animals/sfx_goat_03.mp3","assets/sounds/ui/sfx_ui_neighbor.mp3","assets/sounds/ui/sfx_ui_dialog_show.mp3","assets/sounds/ui/sfx_ui_dialog_hide.mp3","assets/sounds/ui/sfx_ui_expand_menu.mp3","assets/sounds/ui/sfx_ui_click_1.mp3","assets/sounds/ui/sfx_ui_click_2.mp3","assets/sounds/ui/sfx_ui_click_3.mp3","assets/sounds/ui/sfx_ui_mouseover.mp3","assets/sounds/ui/sfx_ui_expand_menu.mp3","assets/sounds/city_sounds/clicks/sfx_click_1.mp3","assets/sounds/city_sounds/clicks/sfx_click_2.mp3","assets/sounds/city_sounds/clicks/sfx_click_3.mp3","assets/sounds/city_sounds/clicks/sfx_click_4.mp3","assets/sounds/city_sounds/clicks/sfx_click_5.mp3","assets/sounds/city_sounds/tourbus/sfx_tourbus_start.mp3","assets/sounds/city_sounds/tourbus/sfx_tourbus_loop.mp3","assets/sounds/city_sounds/tourbus/sfx_tourbus_stop.mp3","assets/sounds/city_sounds/tourbus/sfx_tourbus_horn.mp3","assets/sounds/city_sounds/doobers/doober_drop_01.mp3","assets/sounds/city_sounds/doobers/bonus_ramp_01.mp3","assets/sounds/city_sounds/doobers/bonus_ramp_02.mp3","assets/sounds/city_sounds/doobers/bonus_ramp_03.mp3","assets/sounds/city_sounds/doobers/bonus_ramp_04.mp3","assets/sounds/city_sounds/doobers/bonus_ramp_05.mp3","assets/sounds/city_sounds/doobers/bonus_ramp_06.mp3","assets/sounds/city_sounds/doobers/doober_collect_coins.mp3","assets/sounds/city_sounds/doobers/doober_collect_xp.mp3","assets/sounds/city_sounds/doobers/doober_collect_energy.mp3","assets/sounds/city_sounds/doobers/doober_collect_food.mp3","assets/sounds/city_sounds/doobers/doober_reputation.mp3","assets/sounds/city_sounds/doobers/doober_collect_collectionitem.mp3","assets/sounds/city_sounds/doobers/doober_collect_population.mp3","assets/sounds/city_sounds/collections/sfx_collection_completed.mp3","assets/sounds/city_sounds/effects/poof.mp3","assets/sounds/city_sounds/reward_sfx/sfx_reward_5.mp3","assets/sounds/franchise/completion_dialog.mp3","assets/sounds/franchise/green_yes.mp3","assets/sounds/city_sounds/grand_opening/sfx_grand_opening.mp3","assets/sounds/city_sounds/cheering/sfx_group_of_05.mp3","assets/sounds/city_sounds/cheering/sfx_group_of_10.mp3","assets/sounds/city_sounds/cheering/sfx_group_of_15.mp3","assets/sounds/city_sounds/cheering/sfx_group_of_20.mp3","assets/sounds/city_sounds/npcs/sfx_people_move-in.mp3","assets/sounds/city_sounds/unwrap/unwrap_small.mp3","assets/sounds/city_sounds/collections/sfx_collection_completed.mp3","assets/sounds/copsnbandits/whistle.mp3","assets/sounds/copsnbandits/siren.mp3","assets/sounds/copsnbandits/moneybag.mp3","assets/sounds/copsnbandits/helicopter4.mp3","assets/sounds/copsnbandits/dog.mp3","assets/sounds/city_sounds/ship/cruise_ship_arrive2.mp3","assets/sounds/city_sounds/ship/cruise_ship_arrive_no_camera.mp3","assets/sounds/city_sounds/ship/fireworks.mp3","assets/sounds/kdbomb.mp3"};

        public  Sounds ()
        {
            return;
        }//end

        public static void  setSoundManagerSFXMute ()
        {
            _loc_1 = true;//Global.player.options.get("sfxDisabled");
            _loc_1 = false;

            if (_loc_1)
            {
                SoundManager.mute();
            }
            else
            {
                SoundManager.unmute();
            }
            return;
        }//end

        public static void  setSoundManagerMusicMute ()
        {
            _loc_1 = Global.player.isMusicDisabled();
            _loc_1 = true;
            if (_loc_1)
            {
                pauseMusic();
            }
            else
            {
                unpauseMusic();
            }
            return;
        }//end

        public static boolean  isMusicEnabled ()
        {
            return !Global.player.isMusicDisabled();
        }//end

        public static void  pauseMusic ()
        {
            musicObject.pause();
            return;
        }//end

        public static void  unpauseMusic ()
        {
            if (isMusicEnabled() && InitializationManager.getInstance().haveAllCompleted())
            {
                Debug.debug7("Sounds.unpauseMusic " + musicStarted);
                if (musicStarted)
                {
                    Debug.debug7("Sounds.unpauseMusic " + musicObject);
                    musicObject.unpause();
                }
                else
                {
                    musicStarted = true;

                }
            }
            return;
        }//end

        public static void  init ()
        {
            if (doneInit)
            {
                return;
            }
            boolean _loc_1 =true ;
            if (isMusicEnabled())
            {
                _loc_1 = false;
                StatsManager.count("music_enabled");
            }
            musicObject = new SoundObjectWrapper("bgMusic", Global.getAssetURL(Global.gameSettings().getString("backgroundMusicUrl")), _loc_1);
            setSoundManagerSFXMute();
            setSoundManagerMusicMute();
            doneInit = true;
            return;
        }//end

        public static void  startPostloading ()
        {
            String _loc_2 =null ;
            String _loc_3 =null ;
            if (donePostLoad)
            {
                return;
            }
            _loc_1 =(Array) ALL_SETS.get("set_forceLoad").get( "ANY");
            for(int i0 = 0; i0 < _loc_1.size(); i0++)
            {
            	_loc_2 = _loc_1.get(i0);

                _loc_3 = ALL_SOUNDS.get(_loc_2);
                SoundManager.addSound(_loc_2, Global.getAssetURL(_loc_3), false);
            }
            donePostLoad = true;
            return;
        }//end

        public static void  loadSoundByName (String param1 )
        {
            _loc_2 = ALL_SOUNDS.get(param1);
            SoundManager.addSound(param1, Global.getAssetURL(_loc_2), false);
            return;
        }//end

        public static SoundObject  play (String param1 ,double param2 =0,int param3 =0)
        {

            SoundObject _loc_4 =null ;
            String _loc_5 =null ;
            if (SoundManager.getSoundById(param1) != null)
            {
                _loc_4 = SoundManager.playSound(param1, param2, param3);
                if (param3 == Sounds.LOOPING)
                {
                    Sounds.infiniteLoopingSounds.push(_loc_4);
                }
                return _loc_4;
            }
            else
            {
                _loc_5 = ALL_SOUNDS.get(param1);
                if (_loc_5 != null)
                {
                    SoundManager.addSound(param1, Global.getAssetURL(_loc_5), false);
                    _loc_4 = SoundManager.playSound(param1, param2, param3);
                    if (param3 == Sounds.LOOPING)
                    {
                        Sounds.infiniteLoopingSounds.push(_loc_4);
                    }
                    return _loc_4;
                }
            }
            throw new Error("Sound not registered in Sounds.as: " + param1);
        }//end

        public static void  playSoundFromArray (Array param1 ,double param2 =0,int param3 =0)
        {
            Sounds.play(MathUtil.randomElement(param1), param2, param3);
            return;
        }//end

        public static void  stop (Object param1)
        {
            if (!param1)
            {
                return;
            }
            if (param1 instanceof String && SoundManager.getSoundById(param1) != null)
            {
                SoundManager.stopSound(param1);
            }
            else if (param1 instanceof SoundObject)
            {
                ((SoundObject)param1).stop();
            }
            return;
        }//end

        public static void  playFromSet (String param1 ,SavedObject param2 )
        {
            _loc_3 = ALL_SETS.get(param1);
            if (_loc_3 == null)
            {
                ErrorManager.addError("Error playing sounds, unknown set name: " + param1);
                return;
            }
            _loc_4 = param2!= null ? (param2.getClassName()) : ("ANY");
            _loc_5 =(Array)ALL_SETS.get(param1).get(_loc_4);
            if ((Array)ALL_SETS.get(param1).get(_loc_4) == null)
            {
                _loc_5 =(Array) ALL_SETS.get(param1).get("ANY");
            }
            if (_loc_5 == null || _loc_5.length == 0)
            {
                ErrorManager.addError("Error playing sounds, bad definition for set: " + param1 + ", target: " + _loc_4);
                return;
            }
            Sounds.play(MathUtil.randomElement(_loc_5));
            return;
        }//end

        public static void  stopAnyLoopingSounds ()
        {
            String _loc_1 =null ;
            SoundObject _loc_2 =null ;
            for(int i0 = 0; i0 < .get("tourbus_engine_loop", "ship_sailing", "train_moving").size(); i0++)
            {
            	_loc_1 = .get("tourbus_engine_loop", "ship_sailing", "train_moving").get(i0);

                Sounds.stop(_loc_1);
            }
            for(int i0 = 0; i0 < Sounds.infiniteLoopingSounds.size(); i0++)
            {
            	_loc_2 = Sounds.infiniteLoopingSounds.get(i0);

                Sounds.stop(_loc_2);
            }
            Sounds.infiniteLoopingSounds = new Array();
            return;
        }//end

    }




