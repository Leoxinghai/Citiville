package Classes.sim;

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
//import flash.events.*;
//import flash.utils.*;

    public class StadiumManager
    {
        private Object m_result ;
        private Timer m_open ;
        private Timer m_close ;
        private Stadium m_stadium ;
        private StadiumReplayDialog m_dialog ;
        private int m_playCounter =0;
        public static int TIME_SHOWN ;
        public static int TIME_BETWEEN_DIALOGS ;
        public static int NUMBER_OF_PLAYS ;

        public  StadiumManager (Object param1 ,Stadium param2 )
        {
            this.m_result = param1;
            this.m_stadium = param2;
            this.m_stadium.playing = true;
            TIME_SHOWN = Global.gameSettings().getAttribute("stadiumReplayDisplayTime");
            TIME_BETWEEN_DIALOGS = Global.gameSettings().getAttribute("stadiumTimeBetweenReplays");
            NUMBER_OF_PLAYS = Global.gameSettings().getAttribute("stadiumNumberOfPlays");
            this.m_open = new Timer(TIME_SHOWN);
            this.m_open.addEventListener(TimerEvent.TIMER, this.onOpen);
            this.m_close = new Timer(TIME_BETWEEN_DIALOGS);
            this.m_close.addEventListener(TimerEvent.TIMER, this.onClose);
            this.m_dialog = new StadiumReplayDialog(this.m_stadium, false);
            this.m_dialog.yOffset = -125;
            UI.displayPopup(this.m_dialog);
            this.m_dialog.hide();
            this.m_open.start();
            return;
        }//end

        private void  onOpen (TimerEvent event )
        {
            this.m_open.stop();
            this.m_close.start();
            _loc_2 = this.m_result.replay.get(this.m_playCounter).attacker ;
            _loc_3 = this.m_result.replay.get(this.m_playCounter).defender ;
            _loc_4 = this.m_result.replay.get(this.m_playCounter).attackerScore ;
            _loc_5 = this.m_result.replay.get(this.m_playCounter).defenderScore ;
            _loc_6 = ZLoc.t("Main",this.m_result.replay.get(this.m_playCounter).description ,{name ZLoc.tn(_loc_2 ),defender.tn(_loc_3 )});
            this.m_dialog.setText(_loc_6);
            this.m_dialog.show();
            return;
        }//end

        private void  onClose (TimerEvent event )
        {
            this.m_close.stop();
            this.m_open.start();
            this.m_dialog.hide();
            this.m_playCounter++;
            if (this.m_playCounter >= NUMBER_OF_PLAYS)
            {
                this.onEnd();
            }
            return;
        }//end

        private void  onEnd ()
        {
            String _loc_1 =null ;
            this.m_stadium.playing = false;
            this.m_dialog.close();
            this.m_open.stop();
            this.m_close.stop();
            this.m_open.removeEventListener(TimerEvent.TIMER, this.onOpen);
            this.m_close.removeEventListener(TimerEvent.TIMER, this.onClose);
            if (this.m_result.result == "tie")
            {
                _loc_1 = "It was a tie!";
            }
            else
            {
                _loc_1 = this.m_result.winner.name + " team won!";
            }
            _loc_1 = _loc_1 + ("\n\nFinal Score: " + this.m_result.winnerScore + " - " + this.m_result.loserScore);
            UI.displayMessage(_loc_1);
            return;
        }//end

    }


