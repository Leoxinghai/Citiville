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

import Classes.util.*;
import Display.*;
import Display.DialogUI.*;
import Display.MarketUI.*;
import Display.NeighborUI.*;
import Engine.*;
import Events.*;
import Modules.sale.*;
import Transactions.*;
//import flash.events.*;

    public class IdleDialogManager
    {
        public static  int IDLE_ADDNEIGHBOR =0;
        public static  int IDLE_HELPFRIEND =-1;
        public static  int IDLE_SENDGIFT =1;
        public static  int IDLE_LOSTPUPPY =2;
        public static  int IDLE_FLASHSALE =3;
        public static  int IDLE_COUNT =4;
        private static boolean m_NotDoneInit =true ;
        private static double m_IdleDialogPopupDelayTime =-1;
        private static double m_IdleTimerStart =-1;
        private static double m_LastMouseX =-1;
        private static double m_LastMouseY =-1;
        private static boolean m_showingIdleDialog =false ;
        private static Array m_PossibleChoices =new Array ();
        private static int m_PickedDialog =-1;
        private static Array m_SkippedFlags =new Array ();
        private static  int m_IdleData_TYPE =0;
        private static  int m_IdleData_PROBABILITY =1;
        private static  int m_IdleData_VALIDFUNCTION =2;
        private static  int m_IdleData_IDLEDIALOGTEXT =3;
        private static  int m_IdleData_IDLEDIALOGTITLE =4;
        private static  int m_IdleData_IDLEDIALOGBUTTON =5;
        private static Array m_IdleDialogData =new Array(new Array(IDLE_ADDNEIGHBOR ,3,test_AddNeighbor ,"IdleAddNeighbors_text","IdleAddNeighbors","IdleAddNeighbors_button"),new Array(IDLE_SENDGIFT ,1,test_SendAGift ,"IdleGiftFriends_text","IdleGiftFriends","IdleGiftFriends_button"),new Array(IDLE_LOSTPUPPY ,0,test_LostPuppy ,"lost_puppy_body","lost_puppy","lost_puppy_ok"),new Array(IDLE_FLASHSALE ,5,test_flashSale ,"","",""));
        private static int m_DefaultDialog =1;
        private static String strFriendName ="";
        private static String strFriendHelpID ="";
        private static Catalog m_catalogWindow ;
        private static  boolean IDLE_DEBUG =false ;
        private static  int DEBUG_SCREEN =1;
        private static boolean m_notDoneIdle =true ;
        private static boolean toggleHotKey =false ;

        public  IdleDialogManager ()
        {
            return;
        }//end

        private static void  doInitIfNecessary ()
        {
            int _loc_1 =0;
            if (m_NotDoneInit)
            {
                _loc_1 = 0;
                while (_loc_1 < IDLE_COUNT)
                {

                    m_SkippedFlags.push(false);
                    _loc_1++;
                }
                m_notDoneIdle = true;
                m_NotDoneInit = false;
            }
            return;
        }//end

        public static boolean  ShowingIdleDialog ()
        {
            return m_showingIdleDialog;
        }//end

        public static void  markDialogAsSkipped (int param1 )
        {
            m_SkippedFlags.put(param1,  true);
            return;
        }//end

        private static boolean  test_AddNeighbor ()
        {
            _loc_1 =Global.player.neighbors != null;
            _loc_2 = _loc_1&& Global.player.neighbors.length <= 10;
            _loc_3 =             !test_flashSale();
            if (Global.player.neighbors.length <= 1)
            {
                m_IdleDialogData.get(IDLE_ADDNEIGHBOR).put(m_IdleData_PROBABILITY,  4);
            }
            else if (Global.player.neighbors.length <= 3)
            {
                m_IdleDialogData.get(IDLE_ADDNEIGHBOR).put(m_IdleData_PROBABILITY,  3);
            }
            else if (Global.player.neighbors.length <= 5)
            {
                m_IdleDialogData.get(IDLE_ADDNEIGHBOR).put(m_IdleData_PROBABILITY,  2);
            }
            else if (Global.player.neighbors.length <= 10)
            {
                m_IdleDialogData.get(IDLE_ADDNEIGHBOR).put(m_IdleData_PROBABILITY,  1);
            }
            return _loc_1 && _loc_2 && _loc_3;
        }//end

        private static boolean  test_HelpFriend ()
        {
            _loc_1 =Global.player.friends.length >3;
            _loc_2 =             !test_flashSale();
            return _loc_1 && _loc_2;
        }//end

        private static boolean  test_SendAGift ()
        {
            _loc_1 =Global.player.friends.length >1;
            _loc_2 =             !test_flashSale();
            return _loc_1 && _loc_2;
        }//end

        private static boolean  test_AddFarmville ()
        {
            boolean _loc_1 =true ;
            _loc_2 =             !test_flashSale();
            if (Global.player.isNewPlayer)
            {
                m_IdleDialogData.get(IDLE_ADDNEIGHBOR).put(m_IdleData_PROBABILITY,  8);
            }
            else
            {
                m_IdleDialogData.get(IDLE_ADDNEIGHBOR).put(m_IdleData_PROBABILITY,  3);
            }
            return _loc_1 && _loc_2;
        }//end

        private static boolean  test_LostPuppy ()
        {
            boolean _loc_1 =true ;
            _loc_2 =             !test_flashSale();
            return _loc_1 && _loc_2;
        }//end

        private static boolean  test_flashSale ()
        {
            return Global.paymentsSaleManager.isSaleActive(PaymentsSaleManager.TYPE_FLASH_SALE, false);
        }//end

        private static void  insertValidIdleChoice (int param1 )
        {
            int _loc_2 =0;
            while (_loc_2 < m_IdleDialogData.get(param1).get(m_IdleData_PROBABILITY))
            {

                m_PossibleChoices.push(param1);
                _loc_2++;
            }
            return;
        }//end

        private static void  initializeIdleDialogChoices ()
        {
            m_PossibleChoices.splice(0, m_PossibleChoices.length());
            int _loc_1 =0;
            while (_loc_1 < IDLE_COUNT)
            {

                _loc_2 = m_IdleDialogData.get(_loc_1);
                if (_loc_2.get(m_IdleData_VALIDFUNCTION)() && !m_SkippedFlags.get(_loc_1))
                {
                    insertValidIdleChoice(_loc_1);
                }
                _loc_1++;
            }
            return;
        }//end

        private static boolean  pickRandomIdleDialog ()
        {
            if (m_PossibleChoices.length == 0)
            {
                return false;
            }
            m_PickedDialog = Math.round(Utilities.randBetween(0, (m_PossibleChoices.length - 1)));
            if (m_PickedDialog < m_PossibleChoices.length())
            {
                m_PickedDialog = m_PossibleChoices.get(m_PickedDialog);
            }
            else
            {
                m_PickedDialog = 0;
            }
            return true;
        }//end

        private static String  GetDialogText ()
        {
            String _loc_2 =null ;
            int _loc_3 =0;
            Player _loc_4 =null ;
            String _loc_1 ="";
            switch(m_PickedDialog)
            {
                case IDLE_HELPFRIEND:
                {
                    _loc_2 = "Sam";
                    strFriendHelpID = Player.FAKE_USER_ID_STRING;
                    if (Global.player.neighbors.length > 0)
                    {
                        _loc_3 = Math.round(Utilities.randBetween(0, Global.player.neighbors.length()));
                        strFriendHelpID = Global.player.neighbors.get(_loc_3);
                        _loc_4 = Global.player.findFriendById(strFriendHelpID);
                        if (_loc_4)
                        {
                            _loc_2 = _loc_4.firstName;
                        }
                    }
                    _loc_1 = ZLoc.t("Dialogs", m_IdleDialogData.get(m_PickedDialog).get(m_IdleData_IDLEDIALOGTEXT), {friendName:_loc_2});
                    break;
                }
                default:
                {
                    _loc_1 = ZLoc.t("Dialogs", m_IdleDialogData.get(m_PickedDialog).get(m_IdleData_IDLEDIALOGTEXT));
                    break;
                    break;
                }
            }
            return _loc_1;
        }//end

        public static void  toggleHotKeyForIdlePopUp ()
        {
            if (toggleHotKey == true)
            {
                toggleHotKey = false;
            }
            else
            {
                toggleHotKey = true;
            }
            return;
        }//end

        public static void  generateRandomPopupTime ()
        {
            boolean _loc_1 =false ;
            m_IdleDialogPopupDelayTime = Math.round(Utilities.randBetween(Global.gameSettings().getInt("idleDialogMin"), Global.gameSettings().getInt("idleDialogMax")));
            _loc_1 = RuntimeVariableManager.getBoolean("IS_TEST_ENVIRONMENT", false);
            if (_loc_1 && toggleHotKey)
            {
                m_IdleDialogPopupDelayTime = Math.round(Utilities.randBetween(5, 5));
            }
            m_IdleDialogPopupDelayTime = m_IdleDialogPopupDelayTime * 1000;
            return;
        }//end

        private static boolean  checkIfMouseMoved ()
        {
            if (m_LastMouseX == Global.stage.mouseX && m_LastMouseY == Global.stage.mouseY)
            {
                return false;
            }
            m_LastMouseX = Global.stage.mouseX;
            m_LastMouseY = Global.stage.mouseY;
            return true;
        }//end

        private static void  resetIdleTimer ()
        {
            m_IdleTimerStart = GlobalEngine.getTimer();
            m_showingIdleDialog = false;
            return;
        }//end

        public static void  doIdleDialogTimerCheck ()
        {
            doInitIfNecessary();
            if (m_IdleDialogPopupDelayTime == -1 || toggleHotKey == true)
            {
                generateRandomPopupTime();
            }
            if (m_showingIdleDialog)
            {
                return;
            }
            boolean _loc_1 =false ;
            if (Global.isVisiting())
            {
                _loc_1 = true;
            }
            if (Global.guide.isActive())
            {
                _loc_1 = true;
            }
            if (!_loc_1)
            {
                if (checkIfMouseMoved())
                {
                    _loc_1 = true;
                }
            }
            if (_loc_1)
            {
                resetIdleTimer();
                return;
            }
            boolean _loc_2 =false ;
            _loc_3 = GlobalEngine.getTimer ()-m_IdleTimerStart ;
            if (_loc_3 > m_IdleDialogPopupDelayTime)
            {
                _loc_2 = true;
            }
            if (_loc_2 == true)
            {
                m_showingIdleDialog = true;
                triggerIdleDialog();
            }
            return;
        }//end

        public static void  triggerIdleDialog ()
        {
            initializeIdleDialogChoices();
            if (pickRandomIdleDialog())
            {
                showRandomIdleDialog();
            }
            return;
        }//end

        private static void  showRandomIdleDialog ()
        {
            GenericDialog _loc_5 =null ;
            Player _loc_6 =null ;
            Dialog _loc_7 =null ;
            GenericDialog _loc_8 =null ;
            GenericDialog _loc_9 =null ;
            GenericDialog _loc_10 =null ;
            String _loc_1 =GetDialogText ();
            String _loc_2 =m_IdleDialogData.get(m_PickedDialog).get(m_IdleData_IDLEDIALOGBUTTON) ;
            String _loc_3 =m_IdleDialogData.get(m_PickedDialog).get(m_IdleData_IDLEDIALOGTITLE) ;
            String _loc_4 ="";
            switch(m_PickedDialog)
            {
                case IDLE_ADDNEIGHBOR:
                {
                    _loc_5 = new SamAdviceDialog(_loc_1, _loc_3, GenericDialogView.TYPE_OK, onIdleDialogClosed, _loc_3, null, true, 0, _loc_2);
                    UI.displayPopup(_loc_5, true, _loc_3, true);
                    return;
                }
                case IDLE_HELPFRIEND:
                {
                    _loc_6 = Global.player.findFriendById(strFriendHelpID);
                    if (_loc_6)
                    {
                        _loc_7 = new FriendHelpDialog(_loc_6, _loc_1, _loc_3, _loc_2, onIdleDialogClosed);
                        UI.displayPopup(_loc_7);
                    }
                    return;
                }
                case IDLE_SENDGIFT:
                {
                    _loc_2 = ZLoc.t("Dialogs", _loc_2);
                    _loc_8 = new GenericDialog(_loc_1, _loc_3, GenericDialogView.TYPE_CUSTOM_OK, onIdleDialogClosed, _loc_3, "assets/dialogs/mysteryGift.png", true, 0, "", null, _loc_2);
                    UI.displayPopup(_loc_8, true, _loc_3, true);
                    return;
                }
                case IDLE_LOSTPUPPY:
                {
                    _loc_2 = ZLoc.t("Dialogs", _loc_2);
                    _loc_9 = new GenericDialog(_loc_1, _loc_3, GenericDialogView.TYPE_CUSTOM_OK, onIdleDialogClosed, _loc_3, "assets/dialogs/lost_puppy_icon.png", true, 0, "", null, _loc_2);
                    UI.displayPopup(_loc_9);
                    return;
                }
                case IDLE_FLASHSALE:
                {
                    _loc_10 = Global.paymentsSaleManager.getSaleByType(PaymentsSaleManager.TYPE_FLASH_SALE).createIdleDialog();
                    UI.displayPopup(_loc_10);
                    return;
                }
                default:
                {
                    break;
                }
            }
            UI.displayMessage(_loc_1, GenericPopup.TYPE_OK, onIdleDialogClosed, _loc_3, true, _loc_4, _loc_3);
            return;
        }//end

        private static void  onIdleDialogClosed (GenericPopupEvent event )
        {
            m_IdleDialogPopupDelayTime = -1;
            m_showingIdleDialog = false;
            event.target.removeEventListener(GenericPopupEvent.SELECTED, onIdleDialogClosed);
            if (event.button != GenericPopup.YES)
            {
                m_SkippedFlags.put(m_PickedDialog,  true);
                return;
            }
            switch(m_PickedDialog)
            {
                case IDLE_ADDNEIGHBOR:
                {
                    FrameManager.showTray("invite.php?ref=idle_popup");
                    break;
                }
                case IDLE_HELPFRIEND:
                {
                    if (strFriendHelpID != "0")
                    {
                        GameTransactionManager.addTransaction(new TGetVisitMission(strFriendHelpID), true);
                    }
                    break;
                }
                case IDLE_SENDGIFT:
                {
                    FrameManager.navigateTo("gifts.php?ref=idle_popup");
                    break;
                }
                case IDLE_LOSTPUPPY:
                {
                    FrameManager.navigateTo("lost.php?type=lost_puppy");
                    break;
                }
                case IDLE_FLASHSALE:
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

        private static void  onIdleDialogSkipped (GenericPopupEvent event )
        {
            m_IdleDialogPopupDelayTime = -1;
            m_showingIdleDialog = false;
            return;
        }//end

    }


