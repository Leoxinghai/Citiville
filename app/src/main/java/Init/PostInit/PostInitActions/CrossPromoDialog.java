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
import Classes.Managers.*;
import Display.*;
import Display.DialogUI.*;
import Engine.*;
import Engine.Managers.*;
import Events.*;
import Modules.stats.types.*;
import Transactions.*;

    public class CrossPromoDialog extends StartupDialogBase
    {
        private String m_status ;
        private String m_redirectUrl ;
        private String m_promoId ;

        public  CrossPromoDialog (String param1 ,String param2 ,String param3 ,String param4 ,String param5 ,String param6 ,String param7 )
        {
            this.m_status = param1;
            this.m_redirectUrl = param7;
            this.m_promoId = param6;
            _loc_8 = ZLoc.t("XPromo",param2 );
            int _loc_9 =5;
            int _loc_10 =5;
            _loc_11 = ZLoc.t("XPromo",param3);
            _loc_12 = param5;
            _loc_13 = GenericPopup.TYPE_OK;
            _loc_14 = StatTrackerFactory.count(StatsCounterType.DIALOG,StatsKingdomType.XPROMO,"view",this.m_promoId,param1);
            m_cancelTracker = StatTrackerFactory.count(StatsCounterType.DIALOG, StatsKingdomType.XPROMO, "cancel", this.m_promoId, param1);
            m_okTracker = StatTrackerFactory.count(StatsCounterType.DIALOG, StatsKingdomType.XPROMO, "play", this.m_promoId, param1);
            GenericPictureDialog _loc_15 =new GenericPictureDialog(_loc_8 ,"",_loc_13 ,this.onXPromoClose ,_loc_11 ,"",true ,0,_loc_12 ,param4 ,_loc_9 ,_loc_10 );
            super(_loc_15, _loc_11, false, null, true, _loc_14, null);
            return;
        }//end

        protected void  onXPromoClose (GenericPopupEvent event )
        {
            if (this.m_status == ZCrossPromoManager.STATE_ACCEPTED || this.m_status == ZCrossPromoManager.STATE_NEW)
            {
                TransactionManager.addTransaction(new TAcceptXPromo(parseInt(this.m_promoId)));
            }
            if (this.m_status == ZCrossPromoManager.STATE_COMPLETED)
            {
                TransactionManager.addTransaction(new TCompleteXPromo(parseInt(this.m_promoId)));
            }
            if (this.m_status == ZCrossPromoManager.STATE_REWARDED)
            {
                Global.player.setSeenFlag(ZCrossPromoManager.REWARD_FLAG_PREFIX + this.m_promoId);
            }
            event.stopPropagation();
            boolean _loc_2 =true ;
            if (event.button == GenericDialogView.NO)
            {
                if (m_cancelTracker)
                {
                    m_cancelTracker.track();
                }
                _loc_2 = true;
            }
            else
            {
                if (m_okTracker)
                {
                    m_okTracker.track();
                }
                if (this.m_redirectUrl)
                {
                    Utilities.launchURL(this.m_redirectUrl);
                }
            }
            return;
        }//end

    }



