package Display;

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
import Engine.Helpers.*;
import Transactions.*;
//import flash.display.*;
//import flash.events.*;

    public class StadiumDialog extends SWFDialog
    {
        public int TRAINING_ENERGY_COST ;
        private GenericButton m_close ;
        private GenericButton m_training ;
        private GenericButton m_challenge ;
        private GenericButton m_trainOffense ;
        private GenericButton m_trainDefense ;
        private GenericProgressBar m_defenseProgressBar ;
        private GenericProgressBar m_offenseProgressBar ;
        private Vector2 m_pos ;
        private Stadium m_stadium ;
        private MovieClip m_dialog ;
        private GenericButton easyChallenge ;
        private GenericButton evenChallenge ;
        private GenericButton hardChallenge ;
        private GenericButton challengePlayer ;
        private GenericButton closeChallenge ;
        public static  String PROGRESSBAR_LEVEL_UP ="progressBarLevelUp";

        public  StadiumDialog (Stadium param1 ,String param2 ,boolean param3 =true )
        {
            this.m_pos = new Vector2();
            this.m_stadium = param1;
            m_centered = true;
            m_dialogAsset = "assets/dialogs/StadiumDialog.swf";
            this.TRAINING_ENERGY_COST = Global.gameSettings().getAttribute("stadiumTrainingEnergyCost");
            addEventListener(PROGRESSBAR_LEVEL_UP, this.onProgressBarLevelUp);
            super(param3);
            return;
        }//end

         protected void  onLoadComplete ()
        {
            this.m_dialog =(MovieClip) m_loader.content;
            this.m_close = new GenericButton(this.m_dialog.mc.close_btn, this.onCloseClick);
            this.m_training = new GenericButton(this.m_dialog.mc.training_btn, this.onTrainingClick);
            this.m_challenge = new GenericButton(this.m_dialog.mc.challenge_btn, this.onChallengeClick);
            this.m_trainOffense = new GenericButton(this.m_dialog.mc.training_mc.trainOffense_btn, this.onTrainOffense);
            this.m_trainDefense = new GenericButton(this.m_dialog.mc.training_mc.trainDefense_btn, this.onTrainDefense);
            this.m_defenseProgressBar = new GenericProgressBar(this.m_dialog.mc.training_mc.defenseProgressBar, this);
            this.m_offenseProgressBar = new GenericProgressBar(this.m_dialog.mc.training_mc.offenseProgressBar, this);
            this.easyChallenge = new GenericButton(this.m_dialog.mc.challenge_mc.easy_challenge, this.onChallengePlayer);
            this.evenChallenge = new GenericButton(this.m_dialog.mc.challenge_mc.even_challenge, this.onChallengePlayer);
            this.hardChallenge = new GenericButton(this.m_dialog.mc.challenge_mc.hard_challenge, this.onChallengePlayer);
            this.challengePlayer = new GenericButton(this.m_dialog.mc.challengeWindow_mc.challenge_btn, this.onChallenge);
            this.closeChallenge = new GenericButton(this.m_dialog.mc.challengeWindow_mc.close_btn, this.onChallengeClose);
            this.m_dialog.mc.challengeWindow_mc.visible = false;
            this.m_defenseProgressBar.setPercentage(this.m_stadium.defensePercentage);
            this.m_defenseProgressBar.eventType = PROGRESSBAR_LEVEL_UP;
            this.m_offenseProgressBar.setPercentage(this.m_stadium.offensePercentage);
            this.m_offenseProgressBar.eventType = PROGRESSBAR_LEVEL_UP;
            this.m_dialog.mc.challenge_mc.visible = false;
            this.m_dialog.mc.training_btn_shadow.visible = false;
            this.m_dialog.mc.name_label.text = this.getPlayerName();
            this.m_dialog.mc.stadium_label.text = this.getStadiumName();
            this.m_dialog.mc.training_mc.offenseLvl_label.text = this.m_stadium.offenseLvl;
            this.m_dialog.mc.training_mc.defenseLvl_label.text = this.m_stadium.defenseLvl;
            addChild(this.m_dialog);
            return;
        }//end

        private void  onCloseClick (MouseEvent event )
        {
            close();
            Event _loc_2 =new Event(Event.CLOSE );
            this.dispatchEvent(_loc_2);
            this.removeEventListener(PROGRESSBAR_LEVEL_UP, this.onProgressBarLevelUp);
            this.m_close = null;
            this.m_training = null;
            this.m_challenge = null;
            this.m_trainOffense = null;
            this.m_trainDefense = null;
            this.easyChallenge = null;
            this.evenChallenge = null;
            this.hardChallenge = null;
            this.challengePlayer = null;
            this.closeChallenge = null;
            return;
        }//end

        private void  onTrainingClick (MouseEvent event )
        {
            this.m_dialog.mc.challenge_mc.visible = false;
            this.m_dialog.mc.training_mc.visible = true;
            this.m_dialog.mc.training_btn_shadow.visible = false;
            this.m_dialog.mc.challenge_btn_shadow.visible = true;
            return;
        }//end

        private void  onChallengeClick (MouseEvent event )
        {
            this.m_dialog.mc.challenge_mc.visible = true;
            this.m_dialog.mc.training_mc.visible = false;
            this.m_dialog.mc.training_btn_shadow.visible = true;
            this.m_dialog.mc.challenge_btn_shadow.visible = false;
            return;
        }//end

        private void  onTrainOffense (MouseEvent event )
        {
            if (Global.player.energy >= this.TRAINING_ENERGY_COST)
            {
                Global.player.updateEnergy(-this.TRAINING_ENERGY_COST, new Array("energy", "expenditures", "stadium", ""));
                this.m_stadium.trainOffense();
                this.m_dialog.mc.training_mc.offenseTrain_cost.gotoAndPlay(1);
                this.m_offenseProgressBar.setPercentage(this.m_stadium.offensePercentage);
            }
            return;
        }//end

        private void  onTrainDefense (MouseEvent event )
        {
            if (Global.player.energy >= this.TRAINING_ENERGY_COST)
            {
                Global.player.updateEnergy(-this.TRAINING_ENERGY_COST, new Array("energy", "expenditures", "stadium", ""));
                this.m_stadium.trainDefense();
                this.m_dialog.mc.training_mc.defenseTrain_cost.gotoAndPlay(1);
                this.m_defenseProgressBar.setPercentage(this.m_stadium.defensePercentage);
            }
            return;
        }//end

        private String  getPlayerName ()
        {
            _loc_1 =Global.getPlayer ().name ;
            _loc_1 = _loc_1.slice(0, _loc_1.indexOf(" "));
            _loc_1 = _loc_1 + ZLoc.t("Main", "TeamLabel");
            return _loc_1;
        }//end

        private String  getStadiumName ()
        {
            switch(this.m_stadium.stadiumType)
            {
                case Stadium.SOCCER_STADIUM:
                {
                    return this.m_stadium.getItem().localizedName;
                }
                case Stadium.BASEBALL_STADIUM:
                {
                    return this.m_stadium.getItem().localizedName;
                }
                default:
                {
                    break;
                }
            }
            return ZLoc.t("Main", "StadiumNameDefault");
        }//end

        private void  onProgressBarLevelUp (Event event )
        {
            this.m_dialog.mc.training_mc.offenseLvl_label.text = this.m_stadium.offenseLvl;
            this.m_dialog.mc.training_mc.defenseLvl_label.text = this.m_stadium.defenseLvl;
            return;
        }//end

        private void  onChallenge (MouseEvent event )
        {
            GameTransactionManager.addTransaction(new TStadiumChallenge(this.m_stadium, this.m_stadium.stats), true);
            event = new MouseEvent(MouseEvent.CLICK);
            this.onCloseClick(event);
            return;
        }//end

        private void  onChallengePlayer (MouseEvent event )
        {
            this.m_dialog.mc.challenge_mc.visible = false;
            this.m_dialog.mc.training_mc.visible = false;
            this.m_dialog.mc.challengeWindow_mc.visible = true;
            return;
        }//end

        private void  onChallengeClose (MouseEvent event )
        {
            this.m_dialog.mc.challenge_mc.visible = true;
            this.m_dialog.mc.training_mc.visible = false;
            this.m_dialog.mc.challengeWindow_mc.visible = false;
            return;
        }//end

    }



