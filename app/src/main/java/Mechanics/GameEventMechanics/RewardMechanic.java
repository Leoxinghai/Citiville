package Mechanics.GameEventMechanics;

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

import Classes.doobers.*;
import Classes.util.*;
import Mechanics.*;
import Mechanics.Transactions.*;
//import flash.utils.*;

    public class RewardMechanic extends DooberMechanic
    {
        private static XML lastRewardXML =null ;
        private static int lastRewardIndex =-1;

        public  RewardMechanic ()
        {
            return;
        }//end

         public MechanicActionResult  executeOverrideForGameEvent (String param1 ,Array param2 )
        {
            Array _loc_4 =null ;
            String _loc_5 =null ;
            Array _loc_6 =null ;
            Object _loc_7 =null ;
            Array _loc_8 =null ;
            String _loc_9 =null ;
            boolean _loc_3 =false ;
            if (canGiveReward())
            {
                _loc_4 = new Array();
                _loc_5 = "";
                if (param2 != null && param2.get("randomModifiers") != null)
                {
                    _loc_5 = param2.get("randomModifiers");
                }
                else
                {
                    _loc_5 = getRandomModifiersName();
                }
                _loc_6 = Global.player.processRandomModifiers(m_owner.getItem(), m_owner, false, _loc_4, "default", _loc_5);
                m_owner.secureRandsArray = _loc_4;
                lastRewardXML = null;
                lastRewardIndex = -1;
                _loc_7 = new Object();
                for(int i0 = 0; i0 < _loc_6.size(); i0++)
                {
                		_loc_8 = _loc_6.get(i0);

                    if (_loc_8.length > 3)
                    {
                        lastRewardXML = _loc_8.get(3);
                    }
                    if (_loc_8.length > 4)
                    {
                        lastRewardIndex = int(_loc_8.get(4));
                    }
                    if (_loc_8.get(0) == null)
                    {
                        continue;
                    }
                    _loc_9 = Global.gameSettings().getDooberTypeFromItemName(_loc_8.get(0));
                    switch(_loc_9)
                    {
                        case Doober.DOOBER_COLLECTABLE:
                        case Doober.DOOBER_ITEM:
                        {
                            _loc_7.put(_loc_9,  _loc_8.get(0));
                            break;
                        }
                        default:
                        {
                            _loc_7.put(_loc_9,  _loc_8.get(1));
                            break;
                            break;
                        }
                    }
                }
                Global.player.giveRewards(_loc_7);
                param2.put("randomModifiers",  _loc_5);
                GameTransactionManager.addTransaction(new TDooberMechanicAction(m_owner, m_config.type, param2));
                _loc_3 = true;
            }
            return new MechanicActionResult(_loc_3, false, false);
        }//end

         public Dictionary  getRewardInfo ()
        {
            return null;
        }//end

         public boolean  isVisitEnabled ()
        {
            return true;
        }//end

        public static XML  rewardXML ()
        {
            return lastRewardXML;
        }//end

        public static int  rewardIndex ()
        {
            return lastRewardIndex;
        }//end

    }



