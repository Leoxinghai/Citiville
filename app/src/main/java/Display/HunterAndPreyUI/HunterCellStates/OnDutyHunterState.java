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
import Display.HunterAndPreyUI.*;
import Display.aswingui.*;
import Modules.workers.*;
//import flash.events.*;
import org.aswing.*;

    public class OnDutyHunterState extends GenericHunterState
    {

        public  OnDutyHunterState (HunterData param1 ,int param2 ,HunterCell param3 )
        {
            super(param1, param2, param3);
            return;
        }//end  

         protected JPanel  makeActionPanel ()
        {
            _loc_1 = ASwingHelper.makeFlowJPanel(FlowLayout.RIGHT);
            _loc_2 = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.CENTER,-5);
            _loc_3 = ASwingHelper.makeFlowJPanel(FlowLayout.RIGHT);
            _loc_4 = ASwingHelper.makeFlowJPanel(FlowLayout.RIGHT);
            _loc_5 = ASwingHelper.makeLabel(ZLoc.t("Dialogs",HunterDialog.groupId+"_statusOnDuty"),EmbeddedArt.titleFont,14,EmbeddedArt.blueTextColor);
            _loc_6 = ASwingHelper.makeLabel(ZLoc.t("Dialogs",HunterDialog.groupId+"_hintOnDuty"),EmbeddedArt.defaultFontNameBold,12,EmbeddedArt.blueTextColor);
            _loc_3.append(_loc_5);
            _loc_4.append(_loc_6);
            _loc_2.appendAll(_loc_3, _loc_4);
            _loc_1.append(_loc_2);
            return _loc_1;
        }//end  

         protected void  changeState ()
        {
            if (m_nextActiveTimer && m_nextActiveTimer.hasEventListener(TimerEvent.TIMER))
            {
                m_nextActiveTimer.stop();
                m_nextActiveTimer.removeEventListener(TimerEvent.TIMER, updateTimerString);
            }
            _loc_1 = (HunterPreyWorkers)Global.world.citySim.preyManager.getWorkerManagerByGroup(HunterDialog.groupId).getWorkers(HunterPreyWorkers.FEATURE_HUNTER_PREY_BUCKET)
            _loc_2 = _loc_1.getHunterData(m_pos);
            _loc_2.setTimestamp(Math.floor(GlobalEngine.getTimer() / 1000));
            _loc_2.setState(HunterData.STATE_SLEEPING);
            _loc_1.setHunterData(_loc_2);
            this.rebuildEvent();
            return;
        }//end  

         protected void  rebuildEvent ()
        {
            m_cell.forceRebuildCell(HunterCell.STATE_SLEEPING, false, true);
            return;
        }//end  

    }



