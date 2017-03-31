package Classes.xgamegifting;

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
import Display.*;
import Engine.Managers.*;
import Transactions.*;

    public class XGameGiftingManager
    {
        public static  String TAB_RECEIVED ="tab_received";
        public static  String TAB_SEND ="tab_send";
        private static XGameGiftingManager m_instance ;

        public  XGameGiftingManager ()
        {
            return;
        }//end

        public int  timeToNextGifting ()
        {
            _loc_1 =Global.gameSettings().getInt("xGameGiftingInterval",10);
            _loc_2 = GlobalEngine.getTimer ()/1000-Global.player.lastXGameGiftSentTime ;
            _loc_3 = _loc_1-_loc_2 ;
            return Math.max(0, _loc_3);
        }//end

        public void  showGiftingDialog (int param1 ,String param2 ="TAB_SEND")
        {
            UI.displayPopup(new XGameGiftingDialog(param1));
            return;
        }//end

        public void  beginXGameGifting (int param1 )
        {
            switch(param1)
            {
                case ExternalGameIds.FARMVILLE:
                {
                    if (Global.player.inventory.verifySingletonItem("deco_xgifting_fvballoon"))
                    {
                        Global.player.inventory.addSingletonItem("deco_xgifting_fvballoon", true);
                        if (Global.player.getLastActivationTime("shownXPromoFarmComponent") < 0)
                        {
                            Global.player.setLastActivationTime("shownXPromoFarmComponent", GlobalEngine.getTimer() / 1000);
                        }
                        TransactionManager.addTransaction(new TBeginXGameGifting(param1));
                    }
                    break;
                }
                case ExternalGameIds.CITYVILLE_MOBILE:
                {
                    Global.player.inventory.addSingletonItem("bus_farmersmarket", true);
                    TransactionManager.addTransaction(new TBeginXGameGifting(ExternalGameIds.CITYVILLE_MOBILE));
                    break;
                }
                default:
                {
                    break;
                }
            }
            return;
        }//end

        public void  beginPromoGifting (String param1 )
        {
            switch(param1)
            {
                case ExternalPromoIds.BBUY_PROMO:
                {
                    if (Global.player.inventory.verifySingletonItem("deco_bestbuyrobot"))
                    {
                        Global.player.inventory.addSingletonItem("deco_bestbuyrobot", true);
                        TransactionManager.addTransaction(new TBeginPromoGifting(param1));
                    }
                    break;
                }
                case ExternalPromoIds.BBUY_PROMO2:
                {
                    Global.player.inventory.addItems("deco_bestbuysled", 1);
                    TransactionManager.addTransaction(new TBeginPromoGifting(param1));
                    break;
                }
                default:
                {
                    break;
                }
            }
            return;
        }//end

        public void  showNotificationForGame (int param1 )
        {
            switch(param1)
            {
                case ExternalGameIds.FARMVILLE:
                {
                    break;
                }
                default:
                {
                    break;
                }
            }
            return;
        }//end

        public boolean  send (String param1 )
        {
            boolean _loc_2 =true ;
            if (_loc_2 && this.timeToNextGifting > 0)
            {
                _loc_2 = false;
            }
            _loc_3 =Global.gameSettings().getItemXMLByName(param1 );
            if (_loc_2 && _loc_3.hasOwnProperty("xGameInfo"))
            {
                Global.player.lastXGameGiftSentTime = GlobalEngine.getTimer() / 1000;
                TransactionManager.addTransaction(new TSendXGameGift(param1), true);
            }
            else
            {
                _loc_2 = false;
            }
            return _loc_2;
        }//end

        public void  invalidateNotice (int param1 )
        {
            Global.player.deleteReceivedXGameGiftsByGameId(param1);
            TransactionManager.addTransaction(new TInvalidateXGameGiftNotice(param1));
            return;
        }//end

        public static XGameGiftingManager  instance ()
        {
            if (!m_instance)
            {
                m_instance = new XGameGiftingManager;
            }
            return m_instance;
        }//end

    }


