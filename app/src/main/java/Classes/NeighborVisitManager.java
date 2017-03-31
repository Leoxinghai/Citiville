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

import Classes.virals.*;
import Display.*;
import Engine.*;
import Engine.Managers.*;
import Events.*;
import Modules.realtime.*;
//import flash.utils.*;

    public class NeighborVisitManager
    {
        public static  int VISIT_DEFAULT =-1;
        public static  int VISIT_TOURBUS =0;
        public static  int VISIT_REVIVEDCROPS =1;
        public static  int VISIT_CLEANEDSHIPS =2;
        public static  int VISIT_FINISHEDBUILDING =3;
        public static  int VISIT_FLAGCOUNT =4;
        private static Array m_VisitFlags =new Array ();
        private static int m_VisitType =-1;
        private static boolean m_TriggersViralBox =false ;

        public  NeighborVisitManager ()
        {
            int _loc_1 =0;
            while (_loc_1 < VISIT_FLAGCOUNT)
            {

                m_VisitFlags.push(false);
                _loc_1++;
            }
            return;
        }//end

        public static boolean  getVisitFlag (int param1 )
        {
            return m_VisitFlags.get(param1);
        }//end

        public static void  setVisitFlag (int param1 ,boolean param2 =true )
        {
            m_VisitFlags.put(param1,  param2);
            return;
        }//end

        public static void  resetVisitFlags ()
        {
            int _loc_1 =0;
            while (_loc_1 < VISIT_FLAGCOUNT)
            {

                setVisitFlag(_loc_1, false);
                _loc_1++;
            }
            m_VisitType = VISIT_DEFAULT;
            m_TriggersViralBox = false;
            return;
        }//end

        private static int  countVisitFlagsSetToTrue ()
        {
            int _loc_1 =0;
            int _loc_2 =0;
            while (_loc_2 < VISIT_FLAGCOUNT)
            {

                if (getVisitFlag(_loc_2))
                {
                    _loc_1++;
                }
                _loc_2++;
            }
            return _loc_1;
        }//end

        private static boolean  areAllFlagsFalse ()
        {
            return countVisitFlagsSetToTrue() == 0;
        }//end

        private static boolean  isOnlyOneFlagTrue ()
        {
            return countVisitFlagsSetToTrue() == 1;
        }//end

        private static boolean  moreThanOneFlagTrue ()
        {
            return !areAllFlagsFalse() && !isOnlyOneFlagTrue();
        }//end

        private static int  getSingleTrueFlag ()
        {
            _loc_1 = VISIT_DEFAULT;
            int _loc_2 =0;
            while (_loc_2 < VISIT_FLAGCOUNT)
            {

                if (getVisitFlag(_loc_2))
                {
                    _loc_1 = _loc_2;
                    break;
                }
                _loc_2++;
            }
            return _loc_1;
        }//end

        private static int  pickRandomTrueFlag ()
        {
            Array _loc_1 =new Array ();
            int _loc_2 =0;
            while (_loc_2 < VISIT_FLAGCOUNT)
            {

                if (getVisitFlag(_loc_2))
                {
                    _loc_1.push(_loc_2);
                }
                _loc_2++;
            }
            _loc_3 = Math.round(Utilities.randBetween(0,(_loc_1.length-1)));
            return _loc_1.get(_loc_3);
        }//end

        private static void  sendRTVisit ()
        {
            //arguments = arguments.get(0);
            //if (arguments && arguments != "-1")
            //{
            //    RealtimeManager.callMethodOnOnlineUser(arguments, new RealtimeMethod("showNeighborVisit"));
            //}
            return;
        }//end

        public static void  sendRTNeighborVisitMessage ()
        {
            _loc_1 = Global.getVisiting();
            if (Global.player.didVisitAction)
            {
                setTimeout(sendRTVisit, TransactionManager.amfMaxWait, _loc_1);
            }
            return;
        }//end

        public static void  triggerNeighborVisitFeeds ()
        {
            _loc_1 = VISIT_DEFAULT;
            String _loc_2 ="";
            _loc_3 = ViralType.NEIGHBOR_VISIT_DEFAULT;
            if (m_TriggersViralBox)
            {
                return;
            }
            m_TriggersViralBox = true;
            _loc_4 = Global.getVisiting();
            if (Global.getVisiting() == Player.FAKE_USER_ID_STRING)
            {
                return;
            }
            if (isOnlyOneFlagTrue())
            {
                _loc_1 = getSingleTrueFlag();
            }
            else if (moreThanOneFlagTrue())
            {
                _loc_1 = pickRandomTrueFlag();
            }
            m_VisitType = _loc_1;
            String _loc_5 ="assets/dialogs/feeds/feed_givefriendcoinbonus.png";
            switch(m_VisitType)
            {
                case VISIT_TOURBUS:
                {
                    _loc_2 = "neighborVisit_prompt_bus";
                    _loc_5 = "assets/dialogs/feeds/feed_tourbus.png";
                    _loc_3 = ViralType.NEIGHBOR_VISIT_BUS;
                    break;
                }
                case VISIT_REVIVEDCROPS:
                {
                    _loc_2 = "neighborVisit_prompt_crop";
                    _loc_5 = "assets/dialogs/feeds/feed_revivecrops.png";
                    _loc_3 = ViralType.NEIGHBOR_VISIT_CROPS;
                    break;
                }
                case VISIT_CLEANEDSHIPS:
                {
                    _loc_2 = "neighborVisit_prompt_ship";
                    _loc_5 = "assets/dialogs/feeds/feed_reviveships.png";
                    _loc_3 = ViralType.NEIGHBOR_VISIT_SHIPS;
                    break;
                }
                case VISIT_FINISHEDBUILDING:
                {
                    _loc_2 = "neighborVisit_prompt_build";
                    _loc_5 = "assets/dialogs/feeds/feed_buildingready.png";
                    _loc_3 = ViralType.NEIGHBOR_VISIT_BUILD;
                    break;
                }
                default:
                {
                    _loc_2 = "neighborVisit_prompt_default";
                    break;
                    break;
                }
            }
            UI.displayViralShareMessage(_loc_3, ZLoc.t("Dialogs", _loc_2), GenericPopup.TYPE_SHARECANCEL, onNeighborVisitClosed, "neighborVisit", true, _loc_5, "neighborVisit");
            return;
        }//end

        private static void  onNeighborVisitClosed (GenericPopupEvent event )
        {
            event.target.removeEventListener(GenericPopupEvent.SELECTED, onNeighborVisitClosed);
            if (event.button != GenericPopup.YES)
            {
                return;
            }
            _loc_2 = Global.getVisiting();
            _loc_3 = Global.player.getFriendCityName(_loc_2);
            switch(m_VisitType)
            {
                case VISIT_TOURBUS:
                {
                    Global.world.viralMgr.sendNeighborVisitFeed_Bus(Global.player, Global.player.cityName, _loc_2, _loc_3);
                    break;
                }
                case VISIT_REVIVEDCROPS:
                {
                    Global.world.viralMgr.sendNeighborVisitFeed_Crop(Global.player, _loc_2);
                    break;
                }
                case VISIT_CLEANEDSHIPS:
                {
                    Global.world.viralMgr.sendNeighborVisitFeed_Ship(Global.player, _loc_2);
                    break;
                }
                case VISIT_FINISHEDBUILDING:
                {
                    Global.world.viralMgr.sendNeighborVisitFeed_Build(Global.player, _loc_2);
                    break;
                }
                default:
                {
                    Global.world.viralMgr.sendNeighborVisitFeed_Default(Global.player, _loc_2, _loc_3);
                    break;
                    break;
                }
            }
            return;
        }//end

    }



