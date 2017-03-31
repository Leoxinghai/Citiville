package Transactions;

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
import Display.NeighborUI.*;
import Engine.Transactions.*;
import Events.*;
import Modules.stats.experiments.*;

    public class TInitialVisit extends Transaction
    {
        private String m_recipientId ;
        private String m_senderId ;

        public  TInitialVisit (String param1 ,String param2 )
        {
            this.m_senderId = param1;
            this.m_recipientId = param2;
            return;
        }//end

        public String  recipientId ()
        {
            return this.m_recipientId;
        }//end

        public String  senderId ()
        {
            return this.m_senderId;
        }//end

         public void  perform ()
        {
            Object _loc_1 ={recipientId this.m_recipientId ,senderId.m_senderId };
            signedCall("VisitorService.initialVisit", "neighborVisit", _loc_1);
            return;
        }//end

        protected void  showTutorial (GenericPopupEvent event )
        {
            _loc_2 =Global.player.findFriendById(Global.getVisiting ());
            if (_loc_2 != null)
            {
                Global.guide.notify("FirstVisit");
            }
            return;
        }//end

         protected void  onComplete (Object param1 )
        {
            Object _loc_3 =null ;
            int _loc_4 =0;
            boolean _loc_5 =false ;
            Object _loc_6 =null ;
            double _loc_7 =0;
            double _loc_8 =0;
            double _loc_9 =0;
            boolean _loc_10 =false ;
            int _loc_11 =0;
            Dialog _loc_12 =null ;
            _loc_13 =Global.player ;
            _loc_14 =Global.player.dailyVisits +1;
            _loc_13.dailyVisits = _loc_14;
            Global.player.visitorEnergy = param1.get("energyLeft");
            _loc_2 =Global.player.findFriendById(Global.getVisiting ());
            if (param1.hasOwnProperty("reward"))
            {
                _loc_3 = param1.get("reward");
                _loc_4 = _loc_3.get("status");
                _loc_5 = _loc_4 == 1;
                if (_loc_3.hasOwnProperty("rewards"))
                {
                    _loc_6 = _loc_3.get("rewards");
                    _loc_7 = _loc_6.get("coins") || 0;
                    _loc_8 = _loc_6.get("energy") || 0;
                    _loc_9 = _loc_6.get("xp") || 0;
                    Global.player.gold = Global.player.gold + _loc_7;
                    Global.player.xp = Global.player.xp + _loc_9;
                    Global.player.updateEnergy(_loc_8, new Array("energy", "earnings", "neighbor_visit_first", ""));
                    _loc_10 = _loc_7 > 0 || _loc_8 > 0 || _loc_9 > 0;
                    if (_loc_2 && _loc_10 && !Global.disableGamePopups)
                    {
                        _loc_11 = Global.experimentManager.getVariant(ExperimentDefinitions.EXPERIMENT_FTV_WITH_MARKENTING);
                        if (_loc_11 == ExperimentDefinitions.FTV_MARKETING)
                        {
                            _loc_12 = new FriendFTVRewardDialog(_loc_2, _loc_7, _loc_8, _loc_9, _loc_5 ? (this.showTutorial) : (null));
                        }
                        else
                        {
                            _loc_12 = new FriendEnterRewardDialog(_loc_2, _loc_7, _loc_8, _loc_9, _loc_5 ? (this.showTutorial) : (null));
                        }
                        UI.displayPopup(_loc_12);
                    }
                }
            }
            return;
        }//end

    }



