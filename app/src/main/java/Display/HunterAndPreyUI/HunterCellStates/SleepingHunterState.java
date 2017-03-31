package Display.HunterAndPreyUI.HunterCellStates;

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
import Display.HunterAndPreyUI.*;
import Display.aswingui.*;
import Modules.bandits.*;
import Modules.workers.*;
//import flash.events.*;
//import flash.text.*;
//import flash.utils.*;
import org.aswing.*;

    public class SleepingHunterState extends GenericHunterState
    {

        public  SleepingHunterState (HunterData param1 ,int param2 ,HunterCell param3 )
        {
            super(param1, param2, param3);
            return;
        }//end

         protected JPanel  makeActionPanel ()
        {
            TextFormat _loc_5 =null ;
            _loc_1 = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.TOP ,-5);
            _loc_2 = this.getTimeLeft ();
            if (_loc_2 <= 0)
            {
                this.changeState();
            }
            else
            {
                m_timeLeft = ASwingHelper.makeTextField(GameUtil.formatMinutesSeconds(_loc_2), EmbeddedArt.defaultFontNameBold, 14, EmbeddedArt.blueTextColor);
                m_nextActiveTimer = new Timer(958);
                m_nextActiveTimer.addEventListener(TimerEvent.TIMER, updateTimerString);
                m_nextActiveTimer.start();
                _loc_5 = m_timeLeft.getTextFormat();
                _loc_5.align = TextFormatAlign.RIGHT;
                m_timeLeft.setTextFormat(_loc_5);
            }
            _loc_3 = ASwingHelper.makeLabel(ZLoc.t("Dialogs",HunterDialog.groupId +"_statusSleeping"),EmbeddedArt.defaultFontNameBold ,14,EmbeddedArt.blueTextColor ,JLabel.RIGHT );
            _loc_4 = ASwingHelper.makeLabel(ZLoc.t("Dialogs",HunterDialog.groupId +"_statusNeedResource"),EmbeddedArt.titleFont ,14,EmbeddedArt.blueTextColor ,JLabel.RIGHT );
            _loc_1.appendAll(m_timeLeft, _loc_3, _loc_4);
            return _loc_1;
        }//end

         protected void  changeState ()
        {
            if (m_nextActiveTimer && m_nextActiveTimer.hasEventListener(TimerEvent.TIMER))
            {
                m_nextActiveTimer.stop();
                m_nextActiveTimer.removeEventListener(TimerEvent.TIMER, updateTimerString);
            }
            _loc_1 =Global.world.citySim.preyManager.getWorkerManagerByGroup(HunterDialog.groupId ).getWorkers(HunterPreyWorkers.FEATURE_HUNTER_PREY_BUCKET )as HunterPreyWorkers ;
            _loc_2 = _loc_1.getHunterData(m_pos );
            _loc_2.setState(HunterData.STATE_PATROLLING);
            _loc_2.setTimestamp(Math.floor(GlobalEngine.getTimer() / 1000));
            _loc_1.setHunterData(_loc_2);
            UI.requestPatrol(HunterDialog.groupId, null, null, m_pos);
            this.rebuildEvent();
            return;
        }//end

         protected void  rebuildEvent ()
        {
            m_cell.forceRebuildCell(HunterCell.STATE_PATROLLING, true);
            return;
        }//end

         protected double  getTimeLeft ()
        {
            _loc_1 = PreyManager.getHunterSleepTime(HunterDialog.groupId);
            _loc_2 = GlobalEngine.serverTime /1000;
            _loc_3 = m_officer.getTimestamp();
            _loc_4 = _loc_2-_loc_3 ;
            _loc_5 = _loc_1-_loc_4 ;
            return Math.ceil(_loc_1 - (_loc_2 - _loc_3));
        }//end

    }


