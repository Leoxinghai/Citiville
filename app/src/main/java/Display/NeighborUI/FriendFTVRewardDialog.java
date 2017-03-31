package Display.NeighborUI;

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
import Display.DialogUI.*;
//import flash.display.*;
//import flash.utils.*;

    public class FriendFTVRewardDialog extends GenericDialog
    {
        private Player m_friend ;
        private int m_coins ;
        private int m_energy ;
        private int m_xp ;
        private boolean m_firstTime =false ;
        protected String m_acceptTextName ;
        protected String m_imagePath ;
        protected DisplayObject m_bg ;
        protected int numAssetsLeft =2;

        public  FriendFTVRewardDialog (Player param1 ,int param2 =0,int param3 =0,int param4 =0,Function param5 =null ,boolean param6 =true )
        {
            this.m_friend = param1;
            this.m_coins = param2;
            this.m_energy = param3;
            this.m_xp = param4;
            this.m_acceptTextName = "Okay";
            this.m_firstTime = param2 == Global.gameSettings().getInt("initialFriendVisitRewardCoins", 0);
            if (param2 == Global.gameSettings().getInt("initialFriendVisitReward2Coins", 0))
            {
                this.m_firstTime = true;
            }
            if (param2 == Global.gameSettings().getInt("friendVisitRewardCoins", 0))
            {
                this.m_firstTime = false;
            }
            this.m_imagePath = this.m_firstTime ? ("assets/neighbor_visit/neighborVisit_bus.png") : ("assets/neighbor_visit/neighborVisit_sam.png");
            _loc_7 = this.m_firstTime ? (ZLoc.t("Dialogs", "VRFriendBenefits_title")) : (ZLoc.t("Dialogs", "neighborVisit_title"));
            String _loc_8 ="";
            if (this.m_firstTime)
            {
                _loc_8 = ZLoc.t("Dialogs", "VRWhileVisitingFT", {name:ZLoc.tn(this.m_friend.snUser.firstName)});
            }
            else
            {
                _loc_8 = ZLoc.t("Dialogs", "VRWhileVisiting", {name:ZLoc.tn(this.m_friend.snUser.firstName)});
            }
            super(_loc_8, "", 0, param5, _loc_7, "", param6);
            return;
        }//end

         protected void  loadAssets ()
        {
            Global.delayedAssets.get(DelayedAssetLoader.GENERIC_DIALOG_ASSETS, this.onLoadDialogAssets);
            Global.delayedAssets.get(this.m_imagePath, this.onLoadPictureAsset);
            return;
        }//end

         protected Dictionary  createAssetDict ()
        {
            Dictionary _loc_1 =new Dictionary ();
            _loc_1.put("dialog_bg",  new m_comObject.dialog_bg());
            _loc_1.put("image",  this.m_bg);
            _loc_1.put("friend",  this.m_friend);
            return _loc_1;
        }//end

         protected GenericDialogView  createDialogView (Dictionary param1 )
        {
            FriendFTVRewardDialogView _loc_2 =new FriendFTVRewardDialogView(param1 ,this.m_coins ,this.m_energy ,this.m_xp ,m_message ,m_dialogTitle ,m_type ,m_callback ,this.m_firstTime );
            return _loc_2;
        }//end

        protected void  onLoadDialogAssets (DisplayObject param1 ,String param2 )
        {
            m_comObject = param1;
            this.checkAssetsLoaded();
            return;
        }//end

        protected void  onLoadPictureAsset (DisplayObject param1 ,String param2 )
        {
            this.m_bg = param1;
            this.checkAssetsLoaded();
            return;
        }//end

        protected void  checkAssetsLoaded ()
        {
             this.numAssetsLeft--;
            if (--this.numAssetsLeft == 0)
            {
                onAssetsLoaded();
            }
            return;
        }//end

    }



