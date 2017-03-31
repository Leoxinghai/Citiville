package Display.DialogUI;

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

import Display.aswingui.*;
//import flash.display.*;
//import flash.utils.*;

    public class PermitBuyShareDialogView extends GenericDialogView
    {
        protected int m_PermitsRequired ;
        protected int m_PermitOverrideCost ;
        protected int m_rewardGoods ;
        protected String m_dialogIcon ;
        protected String m_titleIcon ;
        protected Loader m_pic ;
        protected Object m_levelIconPane ;
        protected Dictionary m_panelDict ;
        private DisplayObject m_heartObject ;

        public  PermitBuyShareDialogView (Dictionary param1 ,String param2 ="",String param3 ="",int param4 =0,Function param5 =null ,String param6 ="",int param7 =0,int param8 =0,int param9 =0)
        {
            this.m_PermitsRequired = param8;
            this.m_PermitOverrideCost = param9;
            super(param1, param2, param3, param4, param5, param6, param7);
            return;
        }//end  

         protected void  init ()
        {
            switch(this.m_type)
            {
                case GenericDialogView.TYPE_ASKFRIENDS_BUYPERMITS:
                {
                    m_acceptTextName = ZLoc.t("Dialogs", "PermitDialog_buy", {amount:this.m_PermitOverrideCost.toString()});
                    m_cancelTextName = ZLoc.t("Dialogs", "PermitDialog_ask");
                    break;
                }
                case GenericDialogView.TYPE_INVITEFRIENDS_BUYNEIGHBORS:
                {
                    m_acceptTextName = ZLoc.t("Dialogs", "NeighborBuyDialog_buy", {amount:this.m_PermitOverrideCost.toString()});
                    m_cancelTextName = ZLoc.t("Dialogs", "NeighborBuyDialog_ask");
                    break;
                }
                case GenericDialogView.TYPE_INVITEFRIENDS_BUYFRANCHISEUNLOCKS:
                {
                    m_acceptTextName = ZLoc.t("Dialogs", "FranchiseUnlockBuyDialog_buy", {amount:this.m_PermitOverrideCost.toString()});
                    m_cancelTextName = ZLoc.t("Dialogs", "FranchiseUnlockBuyDialog_ask");
                    break;
                }
                default:
                {
                    break;
                }
            }
            m_bgAsset = m_assetDict.get("dialog_bg");
            m_closeBtn = m_assetDict.get("dialog_bg");
            makeBackground();
            makeCenterPanel();
            ASwingHelper.prepare(this);
            return;
        }//end  

    }



