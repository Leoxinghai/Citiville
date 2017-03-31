package Classes;

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
import Display.*;
import Transactions.*;
//import flash.events.*;

    public class Stadium extends Decoration
    {
        public int TRAINING_XP_GAIN ;
        private XMLList m_levels ;
        private String m_stadiumType ;
        private StadiumDialog m_dialog =null ;
        private Object m_stats =null ;
        private boolean m_isPlaying ;
        public static  String STADIUM_LEVELS ="stadiumLevels";
        public static  String OFFENSE ="OFFENSE";
        public static  String DEFENSE ="DEFENSE";
        public static  String SOCCER_STADIUM ="soccer";
        public static  String BASEBALL_STADIUM ="baseball";

        public  Stadium (String param1)
        {
            super(param1);
            this.m_stadiumType = this.m_item.xml.@stadiumType;
            _loc_2 =Global.gameSettings().getXML ();
            this.m_levels = _loc_2.stadiumLevels.level;
            this.TRAINING_XP_GAIN = Global.gameSettings().getAttribute("stadiumTrainingXPGain");
            this.m_isPlaying = false;
            Stat _loc_3 =new Stat(0);
            this.initializeStats();
            return;
        }//end

        public void  initializeStats ()
        {
            this.m_stats = new Object();
            this.m_stats.put(Stat.OFFENSE_XP,  new Stat(0));
            this.m_stats.put(Stat.OFFENSETONEXTLEVEL,  new Stat(Global.gameSettings().getAttribute("stadiumInitialToNextLevel")));
            this.m_stats.put(Stat.OFFENSE_LEVEL,  new Stat(1));
            this.m_stats.put(Stat.DEFENSE_XP,  new Stat(0));
            this.m_stats.put(Stat.DEFENSETONEXTLEVEL,  new Stat(Global.gameSettings().getAttribute("stadiumInitialToNextLevel")));
            this.m_stats.put(Stat.DEFENSE_LEVEL,  new Stat(1));
            this.m_stats.put(Stat.ELORANK,  new Stat(Global.gameSettings().getAttribute("stadiumStartingEloRank")));
            this.m_stats.put(Stat.FANS,  new Stat(0));
            this.m_stats.put(Stat.STAMINA,  new Stat(Global.gameSettings().getAttribute("stadiumStartingStamina")));
            this.m_stats.put(Stat.WINS,  new Stat(0));
            this.m_stats.put(Stat.LOSSES,  new Stat(0));
            this.m_stats.put(Stat.DRAWS,  new Stat(0));
            return;
        }//end

         public void  loadObject (Object param1 )
        {
            super.loadObject(param1);
            Stat _loc_2 =new Stat(0);
            this.m_stats.get(Stat.OFFENSE_LEVEL).value = param1.stats.get(Stat.OFFENSE_LEVEL).value;
            this.m_stats.get(Stat.OFFENSE_XP).value = param1.stats.get(Stat.OFFENSE_XP).value;
            this.m_stats.get(Stat.OFFENSETONEXTLEVEL).value = param1.stats.get(Stat.OFFENSETONEXTLEVEL).value;
            this.m_stats.get(Stat.DEFENSE_LEVEL).value = param1.stats.get(Stat.DEFENSE_LEVEL).value;
            this.m_stats.get(Stat.DEFENSE_XP).value = param1.stats.get(Stat.DEFENSE_XP).value;
            this.m_stats.get(Stat.DEFENSETONEXTLEVEL).value = param1.stats.get(Stat.DEFENSETONEXTLEVEL).value;
            this.m_stats.get(Stat.ELORANK).value = param1.stats.get(Stat.ELORANK).value;
            this.m_stats.get(Stat.FANS).value = param1.stats.get(Stat.STAMINA).value;
            this.m_stats.get(Stat.STAMINA).value = param1.stats.get(Stat.STAMINA).value;
            this.m_stats.get(Stat.WINS).value = param1.stats.get(Stat.WINS).value;
            this.m_stats.get(Stat.LOSSES).value = param1.stats.get(Stat.LOSSES).value;
            this.m_stats.get(Stat.DRAWS).value = param1.stats.get(Stat.DRAWS).value;
            return;
        }//end

        public String  stadiumType ()
        {
            return this.m_stadiumType;
        }//end

        public int  offenseLvl ()
        {
            return this.m_stats.get(Stat.OFFENSE_LEVEL).value;
        }//end

        public int  defenseLvl ()
        {
            return this.m_stats.get(Stat.DEFENSE_LEVEL).value;
        }//end

        public Object  stats ()
        {
            return this.m_stats;
        }//end

        public void  playing (boolean param1 )
        {
            this.m_isPlaying = param1;
            return;
        }//end

        public int  offensePercentage ()
        {
            return (this.m_stats.get(Stat.OFFENSE_XP).value - this.m_levels.get((this.m_stats.get(Stat.OFFENSE_LEVEL).value - 1)).@requiredXP) / this.m_stats.get(Stat.OFFENSETONEXTLEVEL).value * 100;
        }//end

        public int  defensePercentage ()
        {
            return (this.m_stats.get(Stat.DEFENSE_XP).value - this.m_levels.get((this.m_stats.get(Stat.DEFENSE_LEVEL).value - 1)).@requiredXP) / this.m_stats.get(Stat.DEFENSETONEXTLEVEL).value * 100;
        }//end

        public void  trainOffense ()
        {
            _loc_1 = this.m_stats.get(Stat.OFFENSE_LEVEL).value ;
            if (_loc_1 >= Global.gameSettings().getAttribute("stadiumMaxStatLevel"))
            {
                return;
            }
            _loc_2 = this.m_stats.get(Stat.OFFENSE_XP).value ;
            _loc_3 = this.m_stats.get(Stat.OFFENSETONEXTLEVEL).value ;
            _loc_2 = _loc_2 + this.TRAINING_XP_GAIN;
            _loc_4 = _loc_1;
            while (_loc_4 < this.m_levels.length())
            {

                if (_loc_2 >= this.m_levels.get(_loc_4).@requiredXP)
                {
                    _loc_1 = _loc_1 + 1;
                    _loc_3 = this.m_levels.get(_loc_1).@requiredXP - this.m_levels.get((_loc_1 - 1)).@requiredXP;
                    this.m_stats.get(Stat.OFFENSE_LEVEL).value = _loc_1;
                    this.m_stats.get(Stat.OFFENSETONEXTLEVEL).value = _loc_3;
                }
                else
                {
                    break;
                }
                _loc_4++;
            }
            this.m_stats.get(Stat.OFFENSE_XP).value = _loc_2;
            GameTransactionManager.addTransaction(new TStadiumTrain(this, OFFENSE), false);
            return;
        }//end

        public void  trainDefense ()
        {
            _loc_1 = this.m_stats.get(Stat.DEFENSE_LEVEL).value ;
            if (_loc_1 >= Global.gameSettings().getAttribute("stadiumMaxStatLevel"))
            {
                return;
            }
            _loc_2 = this.m_stats.get(Stat.DEFENSE_XP).value ;
            _loc_3 = this.m_stats.get(Stat.DEFENSETONEXTLEVEL).value ;
            _loc_2 = _loc_2 + this.TRAINING_XP_GAIN;
            _loc_4 = _loc_1;
            while (_loc_4 < this.m_levels.length())
            {

                if (_loc_2 >= this.m_levels.get(_loc_4).@requiredXP)
                {
                    _loc_1 = _loc_1 + 1;
                    _loc_3 = this.m_levels.get(_loc_1).@requiredXP - this.m_levels.get((_loc_1 - 1)).@requiredXP;
                    this.m_stats.get(Stat.DEFENSE_LEVEL).value = _loc_1;
                    this.m_stats.get(Stat.DEFENSETONEXTLEVEL).value = _loc_3;
                }
                else
                {
                    break;
                }
                _loc_4++;
            }
            this.m_stats.get(Stat.DEFENSE_XP).value = _loc_2;
            GameTransactionManager.addTransaction(new TStadiumTrain(this, DEFENSE), false);
            return;
        }//end

         public void  onPlayAction ()
        {
            if (m_visitReplayLock > 0)
            {
                return;
            }
            m_actionMode = PLAY_ACTION;
            if (this.m_dialog == null && this.m_isPlaying == false)
            {
                this.m_dialog = new StadiumDialog(this, this.m_stadiumType);
                this.m_dialog.addEventListener(Event.CLOSE, this.onDialogClose);
                UI.displayPopup(this.m_dialog);
            }
            return;
        }//end

        private void  onDialogClose (Event event )
        {
            this.m_dialog = null;
            return;
        }//end

         public boolean  isPlacedObjectNonBuilding ()
        {
            return false;
        }//end

    }



