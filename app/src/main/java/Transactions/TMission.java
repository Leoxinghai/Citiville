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
import Engine.Managers.*;
import Events.*;
//import flash.utils.*;

    public class TMission extends TFarmTransaction
    {
        protected Mission m_mission ;
        protected String m_userId ;
        protected MissionPopup m_missionPopup ;

        public  TMission ()
        {
            this.m_userId = Global.player.snUser.uid;
            return;
        }//end

         protected void  onComplete (Object param1 )
        {
            Player _loc_2 =null ;
            super.onComplete(param1);
            if (param1 !=null)
            {
                if (param1.hostUserId)
                {
                    _loc_2 = Global.player.findFriendById(param1.hostUserId);
                }
                this.m_mission = new Mission(param1.type, this.m_userId, param1.hostUserId, param1.hitLimit, _loc_2);
            }
            return;
        }//end

        protected void  displayMission (String param1 ="friendVisit")
        {
            if (this.m_missionPopup == null && this.m_mission.hostPlayer)
            {
                this.m_missionPopup = new MissionPopup(this.m_mission, param1);
                this.m_missionPopup.addEventListener(CloseEvent.CLOSE, this.onClose);
            }
            return;
        }//end

        protected boolean  validateMissionTime (int param1 )
        {
            _loc_2 = (int)(GlobalEngine.getTimer ()*0.001);
            boolean _loc_3 =false ;
            if (_loc_2 - param1 > Global.gameSettings().getInt("missionPerUserTimeLimit"))
            {
                _loc_3 = true;
            }
            return _loc_3;
        }//end

        protected void  sendNoMissionStats (String param1 )
        {
            if (this.m_mission)
            {
                StatsManager.count(MissionPopup.COUNTER + "_" + param1, "no_mission");
            }
            return;
        }//end

        protected void  onClose (CloseEvent event )
        {
            this.m_missionPopup.removeEventListener(CloseEvent.CLOSE, this.onClose);
            this.m_missionPopup = null;
            return;
        }//end

         protected void  onFault (int param1 ,String param2 )
        {
            GlobalEngine.log("Missions", "Fault in " + getQualifiedClassName(this) + ": " + param2);
            return;
        }//end

    }



