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

import Classes.util.*;
import GameMode.*;

    public class TGetVisitMission extends TMission
    {
        protected String m_missionHostId ;
        protected String m_missionType ;

        public  TGetVisitMission (String param1 ,String param2 ,double param3 =-1)
        {
            this.m_missionHostId = param1;
            this.m_missionType = param2;
            Global.ui.updateVisitMode(true);
            Global.world.addGameMode(new GMVisit(param1), true);
            GameTransactionManager.addTransaction(new TInitialVisit(Global.player.uid, param1));
            GameTransactionManager.addTransaction(new TWorldLoad(param1, param3), true, true);
            return;
        }//end

         public void  perform ()
        {
            if (this.m_missionType)
            {
                signedCall("MissionService.getSpecificMission", this.m_missionHostId.toString(), this.m_missionType);
            }
            return;
        }//end

         protected void  onComplete (Object param1 )
        {
            String _loc_2 =null ;
            String _loc_3 =null ;
            boolean _loc_4 =false ;
            int _loc_5 =0;
            super.onComplete(param1);
            if (Global.isVisiting())
            {
                _loc_2 = Global.world.ownerId;
                _loc_3 = Global.player.getFriendFirstName(_loc_2);
                Global.ui.m_cityNamePanel.cityName = Global.player.getFriendCityName(_loc_2);
            }
            if (param1 !=null)
            {
                _loc_4 = false;
                if (this.m_missionType)
                {
                    _loc_5 = int(param1.lastMissionTime);
                    _loc_4 = validateMissionTime(_loc_5);
                }
                else if (Global.getVisiting() == m_mission.missionHostId)
                {
                    _loc_4 = true;
                }
                if (_loc_4)
                {
                    this.displayMission();
                }
                else
                {
                    sendNoMissionStats("friendVisit");
                }
            }
            else
            {
                sendNoMissionStats("friendVisit");
            }
            return;
        }//end

         protected void  displayMission (String param1 ="")
        {
            return;
        }//end

    }



