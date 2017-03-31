package Classes.Managers;

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
import Display.hud.components.*;
import Init.PostInit.PostInitActions.*;
//import flash.utils.*;

    public class ZCrossPromoManager
    {
        private static Object m_crossPromo ;
        private static XPromoFriend m_crossPromoFriend ;
        private static HUDComponent m_hudComponent ;
        private static boolean hudIconAddedThisSession =false ;
        public static  String STATE_NEW ="NEW";
        public static  String STATE_ACCEPTED ="ACCEPTED";
        public static  String STATE_COMPLETED ="COMPLETED";
        public static  String STATE_REWARDED ="REWARDED";
        public static  String SEEN_FLAG_PREFIX ="xpromo_seen_";
        public static  String COMPLETE_FLAG_PREFIX ="xpromo_complete_";
        public static  String REWARD_FLAG_PREFIX ="xpromo_rewarded_";

        public  ZCrossPromoManager ()
        {
            return;
        }//end

        public static void  setData (Object param1 )
        {
            m_crossPromoFriend = null;
            m_crossPromo = chooseHighestPriorityCrossPromo(param1);
            if (crossPromoContainsError(m_crossPromo))
            {
                m_crossPromo = null;
            }
            else if (m_crossPromo.get("neighbor_image") && m_crossPromo.get("neighbor_text"))
            {
                m_crossPromoFriend = new XPromoFriend({nameKey:m_crossPromo.get("neighbor_text"), profilePic:m_crossPromo.get("neighbor_image"), action:"redirect", redirectUrl:m_crossPromo.get("redirect_url")});
            }
            return;
        }//end

        public static StartupDialogBase  getCrossPromo ()
        {
            if (!m_crossPromo)
            {
                return null;
            }
            _loc_1 = m_crossPromo.get("id") ;
            _loc_2 = m_crossPromo.get("status") ;
            _loc_3 = isDialogSeenFlagSet(_loc_1,_loc_2)==false;
            if (_loc_2 == STATE_NEW || _loc_2 == STATE_ACCEPTED)
            {
                if (m_crossPromo.get("offer_image") && m_crossPromo.get("offer_text"))
                {
                    displayHudIcon(m_crossPromo.get("offer_image"), m_crossPromo.get("offer_text"));
                }
            }
            else
            {
                removeHudIcon();
            }
            if (_loc_3)
            {
                return new CrossPromoDialog(_loc_2, m_crossPromo.get("text"), m_crossPromo.get("title"), m_crossPromo.get("image_url"), m_crossPromo.get("btn_text"), _loc_1, m_crossPromo.get("redirect_url"));
            }
            return null;
        }//end

        public static void  displayCrossPromoPopup ()
        {
            _loc_1 = m_crossPromo.get("id") ;
            new CrossPromoDialog(m_crossPromo.get("status"), m_crossPromo.get("text"), m_crossPromo.get("title"), m_crossPromo.get("image_url"), m_crossPromo.get("btn_text"), _loc_1, m_crossPromo.get("redirect_url")).show();
            return;
        }//end

        private static void  removeHudIcon ()
        {
            if (Global.hud)
            {
                Global.hud.removeRightStackComponent(m_hudComponent);
            }
            return;
        }//end

        public static void  displayHudIcon (String param1 ,String param2 )
        {
            _loc_3 = m_crossPromo.get("id") ;
            XML _loc_4 =new XML("<component name=\"HUDZCrossPromoComponent \" xPos=\"0\" yPos=\"0\" parent=\"rightStack\" clickStat=\"hometown\" toolTip=\"" + param2 + "\" icon=\"" + param1 + "\"/>");
            m_hudComponent = new ((Class)getDefinitionByName("Display.hud.components." + _loc_4.@name))(_loc_3);
            m_hudComponent.initWithXML(_loc_4);
            m_hudComponent.refresh(false);
            if (!hudIconAddedThisSession && _loc_4.attribute("parent").length() > 0)
            {
                hudIconAddedThisSession = true;
                Global.hud.addPersistentRightStackComponent(m_hudComponent);
            }
            return;
        }//end

        private static String  getXPromoIndex (String param1 )
        {
            return param1.split("i", 2).get(1);
        }//end

        private static boolean  isCrossPromoFinished (Object param1 )
        {
            _loc_2 = param1["status"] ==STATE_REWARDED && Global.player.getSeenFlag(REWARD_FLAG_PREFIX + param1.get("id")) == true;
            return _loc_2;
        }//end

        private static Object  chooseHighestPriorityCrossPromo (Object param1 )
        {
            String _loc_4 =null ;
            Object _loc_5 =null ;
            int _loc_6 =0;
            Array _loc_2 =new Array ();
            int _loc_3 =-1;
            for(int i0 = 0; i0 < param1.size(); i0++)
            {
            		_loc_4 = param1.get(i0);

                _loc_5 = param1.get(_loc_4);
                _loc_4 = getXPromoIndex(_loc_4);
                _loc_5.put("id",  _loc_4);
                _loc_6 = _loc_5.get("priority");
                if (_loc_6 >= _loc_3 && !isCrossPromoFinished(_loc_5))
                {
                    if (_loc_6 > _loc_3)
                    {
                        _loc_3 = _loc_6;
                        _loc_2 = new Array();
                    }
                    _loc_2.put(_loc_2.length,  _loc_5);
                }
            }
            if (_loc_2.length == 0)
            {
                return null;
            }
            if (_loc_2.length == 1)
            {
                return _loc_2.get(0);
            }
            return chooseEarliestStartDate(_loc_2);
        }//end

        private static Object  chooseEarliestStartDate (Array param1 )
        {
            Object _loc_5 =null ;
            double _loc_6 =0;
            _loc_2 = double.MAX_VALUE;
            Object _loc_3 =null ;
            double _loc_4 =0;
            while (_loc_4 < param1.length())
            {

                _loc_5 = param1.get(_loc_4);
                _loc_6 = parseInt(_loc_5.get("startDate"));
                if (_loc_6 < _loc_2)
                {
                    _loc_2 = _loc_6;
                    _loc_3 = _loc_5;
                }
                _loc_4 = _loc_4 + 1;
            }
            return _loc_3;
        }//end

        private static boolean  isDialogSeenFlagSet (String param1 ,String param2 )
        {
            switch(param2)
            {
                case STATE_NEW:
                case STATE_ACCEPTED:
                {
                    return Global.player.getSeenFlag(SEEN_FLAG_PREFIX + param1);
                }
                case STATE_COMPLETED:
                {
                    return Global.player.getSeenFlag(COMPLETE_FLAG_PREFIX + param1);
                }
                case STATE_REWARDED:
                {
                    return Global.player.getSeenFlag(REWARD_FLAG_PREFIX + param1);
                }
                default:
                {
                    break;
                }
            }
            return false;
        }//end

        private static boolean  crossPromoContainsError (Object param1 )
        {
            if (!param1 || isEmptyString(param1.get("text")) || isEmptyString(param1.get("title")) || isEmptyString(param1.get("image_url")) || isEmptyString(param1.get("btn_text")))
            {
                return true;
            }
            if (!ZLoc.exists("XPromo", param1.get("text")))
            {
                return true;
            }
            if (!ZLoc.exists("XPromo", param1.get("title")))
            {
                return true;
            }
            if (!ZLoc.exists("Dialogs", param1.get("btn_text")))
            {
                return true;
            }
            if (param1.get("redirect_url") && param1.get("redirect_url").indexOf("http") != 0)
            {
                return true;
            }
            return false;
        }//end

        private static boolean  isEmptyString (String param1 )
        {
            return !param1 || param1.length == 0;
        }//end

        public static XPromoFriend  xPromoFriend ()
        {
            return m_crossPromoFriend;
        }//end

    }



