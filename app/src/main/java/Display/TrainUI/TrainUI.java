package Display.TrainUI;

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
import Classes.orders.*;
import Classes.sim.*;
import Engine.Managers.*;
//import flash.display.*;
//import flash.events.*;
//import flash.geom.*;

    public class TrainUI
    {
        private static Array m_slidePicks =new Array();
        private static int m_nextAvailableHandle =0;
        public static  int INVALID_HANDLE =-1;
        private static TrainManager m_trainSim ;
        private static AutoAnimatedBitmap m_questHudIcon =null ;

        public  TrainUI ()
        {
            return;
        }//end

        public static void  showTrainAtStationHUDIcon (boolean param1 ,TrainManager param2 )
        {
            m_trainSim = param2;
            if (param1 !=null)
            {
                if (m_questHudIcon == null)
                {
                    LoadingManager.load(Global.getAssetURL("assets/dialogs/train/train_quest.png"), onTrainHudIconLoaded);
                }
            }
            else
            {
                Global.hud.hideTrainQuestSprite();
                if (m_questHudIcon)
                {
                    m_questHudIcon.cleanUp();
                    m_questHudIcon = null;
                }
            }
            return;
        }//end

        private static void  onTrainHudIconLoaded (Event event )
        {
            Bitmap _loc_3 =null ;
            _loc_2 =(Loader) event.target.loader;
            if (_loc_2.content instanceof Bitmap)
            {
                _loc_3 =(Bitmap) _loc_2.content;
                m_questHudIcon = new AutoAnimatedBitmap(_loc_3.bitmapData, 2, 64, 64, 1);
                m_questHudIcon.play();
                Global.hud.showTrainQuestSprite(m_questHudIcon, onTrainAtStationHudIconClicked);
                Global.hud.showGoalsProgressOverlayOnQuestIcon("trainAtStation", "trainAtStation");
            }
            return;
        }//end

        public static void  onTrainAtStationHudIconClicked ()
        {
            m_trainSim.showTrainStationDialog();
            return;
        }//end

        public static void  showTrainSchedule (boolean param1 ,Point param2 ,Array param3 ,Array param4 )
        {
            return;
        }//end

        public static int  createTrainUI (String param1 ,TrainManager param2 ,TrainOrder param3 )
        {
            Player _loc_5 =null ;
            _loc_4 = m_nextAvailableHandle;
            if (param3.fakeTrainOrder)
            {
                param1 = Player.FAKE_USER_ID_STRING;
            }
            if (param1 == Global.player.uid)
            {
                _loc_5 = Global.player;
            }
            else
            {
                _loc_5 = Global.player.findFriendById(param1);
            }
            _loc_6 = _loc_5? (_loc_5.snUser.picture) : (null);
            if (param3.customPickUrl)
            {
                _loc_6 = Global.getAssetURL(param3.customPickUrl);
            }
            TrainSlidePick _loc_7 =new TrainSlidePick(_loc_6 ,m_nextAvailableHandle ,param2 ,param3 ,true );
            _loc_7.setActive(false);
            m_slidePicks.put(m_nextAvailableHandle,  _loc_7);
            while (m_slidePicks.hasOwnProperty(m_nextAvailableHandle))
            {

                _loc_9 = m_nextAvailableHandle+1;
                m_nextAvailableHandle = _loc_9;
            }
            return _loc_4;
        }//end

        public static void  closeTrainUI (int param1 )
        {
            TrainSlidePick slidePick ;
            handle = param1;
            if (handle >= 0 && m_slidePicks.get(handle) != null)
            {
                slidePick =(TrainSlidePick) m_slidePicks.get(handle);
                slidePick .kill (void  ()
            {
                if (slidePick.parent)
                {
                    slidePick.parent.removeChild(slidePick);
                }
                return;
            }//end
            );
                m_nextAvailableHandle = handle;
                delete m_slidePicks.get(handle);
            }
            return;
        }//end

        public static void  showTrainUI (int param1 ,TrainOrder param2 )
        {
            TrainSlidePick _loc_3 =null ;
            if (param1 >= 0 && m_slidePicks.get(param1) != null)
            {
                _loc_3 =(TrainSlidePick) m_slidePicks.get(param1);
                _loc_3.showTrainUI(param2);
            }
            return;
        }//end

        public static TrainSlidePick  getTrainProfilePick (int param1 )
        {
            if (param1 >= 0)
            {
                return m_slidePicks.get(param1) as TrainSlidePick;
            }
            return null;
        }//end

        public static void  setTrainProfilePickPos (int param1 ,Point param2 )
        {
            TrainSlidePick _loc_3 =null ;
            if (param1 >= 0 && m_slidePicks.get(param1) != null && param2 != null)
            {
                _loc_3 =(TrainSlidePick) m_slidePicks.get(param1);
                _loc_3.setPosition(param2.x, param2.y);
            }
            return;
        }//end

        public static void  incrementCount (int param1 )
        {
            TrainSlidePick _loc_2 =null ;
            if (param1 >= 0 && m_slidePicks.get(param1) != null)
            {
                _loc_2 =(TrainSlidePick) m_slidePicks.get(param1);
                _loc_2.onMore(null);
            }
            return;
        }//end

        public static void  backOnePanel (int param1 )
        {
            TrainSlidePick _loc_2 =null ;
            if (param1 >= 0 && m_slidePicks.get(param1) != null)
            {
                _loc_2 =(TrainSlidePick) m_slidePicks.get(param1);
                _loc_2.backOnePanel();
            }
            return;
        }//end

    }



