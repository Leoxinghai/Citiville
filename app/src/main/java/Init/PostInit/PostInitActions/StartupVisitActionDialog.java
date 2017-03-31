package Init.PostInit.PostInitActions;

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
import Classes.util.*;
import Display.*;
import Display.DialogUI.*;
import Events.*;
import Transactions.*;
import ZLocalization.*;

    public class StartupVisitActionDialog extends StartupMessage
    {
        protected String m_neighborId ="";
        public static  String DEFAULT_ICON_ASSET_URL ="assets/dialogs/feeds/feed_vacantlot.png";

        public  StartupVisitActionDialog (String param1 ,boolean param2 ,Object param3 ,String param4 ="assets/dialogs/feeds/feed_vacantlot.png",String param5 ="StartUpDialogA",String param6 ="StartUpDialogA_text",String param7 ="StartUpDialogB_text",String param8 ="StartUpDialogC_text",String param9 ="StartUpDialogD_text",boolean param10 =true )
        {
            boolean _loc_15 =false ;
            this.m_neighborId = param1;
            _loc_11 = param6;
            _loc_12 = GenericPopup.TYPE_OK;
            Function _loc_13 =null ;
            if (param2)
            {
            }
            else if (Global.guide.isActive())
            {
                _loc_11 = param7;
                _loc_13 = this.onSetHudNonDefault;
            }
            else
            {
                _loc_15 = Global.player.isFriendANeighbor(this.m_neighborId);
                if (_loc_15)
                {
                    _loc_11 = param8;
                    _loc_12 = GenericDialogView.TYPE_VISIT;
                    _loc_13 = this.onVisitNeighbor;
                }
                else
                {
                    _loc_11 = param9;
                    _loc_12 = GenericDialogView.TYPE_ADDNEIGHBOR;
                    _loc_13 = this.onInviteNeighbor;
                }
            }
            param3.friendName = this.getFriendName();
            _loc_14 = ZLoc.t("Dialogs",_loc_11,param3);
            super(param5, _loc_14, _loc_11, _loc_13, param4, _loc_12, param10);
            return;
        }//end

        public void  onSetHudNonDefault (GenericPopupEvent event )
        {
            StartUpDialogManager.returnToDefaultHUD = false;
            return;
        }//end

        public void  onVisitNeighbor (GenericPopupEvent event )
        {
            if (event.button == GenericPopup.YES)
            {
                StartUpDialogManager.returnToDefaultHUD = false;
                StartUpDialogManager.clearDialogQueue();
                if (this.m_neighborId != "0")
                {
                    GameTransactionManager.addTransaction(new TGetVisitMission(this.m_neighborId), true);
                }
            }
            return;
        }//end

        public void  onInviteNeighbor (GenericPopupEvent event )
        {
            if (event.button == GenericPopup.YES)
            {
                FrameManager.showTray("invite.php?ref=startup_dialog");
            }
            return;
        }//end

        private LocalizationName  getFriendName ()
        {
            String _loc_1 ="";
            _loc_2 =Global.player.findFriendById(this.m_neighborId );
            if (_loc_2)
            {
                _loc_1 = _loc_2.firstName;
            }
            return ZLoc.tn(_loc_1);
        }//end

    }



