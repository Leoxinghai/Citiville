package Modules.zoo.ui;

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
import Classes.virals.*;
import Display.aswingui.*;
import Engine.Managers.*;
//import flash.display.*;
//import flash.events.*;
//import flash.text.*;
//import flash.utils.*;
import org.aswing.*;

    public class ZooSpeechBubble extends Sprite
    {
        private JWindow m_win ;
        private JPanel m_container ;
        private JPanel m_body ;
        private JPanel m_mainTxtContainer ;
        private JPanel m_placeContainer ;
        private JPanel m_btnContainer ;
        private AssetPane m_mainTxt ;
        private JLabel m_unlockTxt ;
        private ZooPlaceFromInventoryModule m_placeNowModule ;
        private CustomButton m_btnAskForAnimals ;
        private String m_defaultMessage ;
        private String m_itemNameToPlace ;
        private ZooEnclosure m_enclosureToAskForAnimals ;
        private Timer m_askAgainTimer ;

        public  ZooSpeechBubble ()
        {
            this.m_mainTxtContainer = ASwingHelper.makeSoftBoxJPanelVertical();
            this.m_placeContainer = ASwingHelper.makeSoftBoxJPanelVertical();
            _loc_1 = ASwingHelper.makeSoftBoxJPanel ();
            _loc_1.appendAll(this.m_mainTxtContainer, this.m_unlockTxt, this.m_placeContainer);
            this.m_unlockTxt = ASwingHelper.makeLabel("", EmbeddedArt.defaultFontName, 14, EmbeddedArt.lightGrayTextColor);
            this.m_btnContainer = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.CENTER);
            this.m_body = ASwingHelper.makeSoftBoxJPanelVertical();
            this.m_body.appendAll(_loc_1, this.m_unlockTxt, this.m_btnContainer);
            ASwingHelper.setEasyBorder(this.m_body, 10, 10, 30, 10);
            MarginBackground _loc_2 =new MarginBackground(new ZooDialog.assetDict.get( "speech_bubble") ,new Insets(0,0,0,0));
            this.m_container = ASwingHelper.makeSoftBoxJPanel();
            this.m_container.setBackgroundDecorator(_loc_2);
            this.m_container.append(this.m_body);
            this.m_win = new JWindow(this);
            this.m_win.setContentPane(this.m_container);
            this.m_win.show();
            ASwingHelper.prepare(this.m_win);
            return;
        }//end

        public void  setDefaultMessage (String param1 ,String param2 ,ZooEnclosure param3 =null ,boolean param4 =true )
        {
            this.m_defaultMessage = param1;
            this.m_itemNameToPlace = param2;
            this.m_enclosureToAskForAnimals = param3;
            if (this.m_itemNameToPlace)
            {
                this.m_placeNowModule = new ZooPlaceFromInventoryModule(this.m_itemNameToPlace);
                this.m_placeNowModule.addEventListener(Event.CLOSE, this.onPlaceItemModuleClose, false, 0, true);
            }
            else
            {
                this.m_placeNowModule = null;
            }
            if (this.m_enclosureToAskForAnimals)
            {
                this.m_btnAskForAnimals = new CustomButton(ZLoc.t("Dialogs", "ZooDialog_askDonation"), null, "GreenButtonUI");
                this.m_btnAskForAnimals.addActionListener(this.onBtnAskForAnimalsClick, 0, true);
                this.m_btnContainer.append(this.m_btnAskForAnimals);
            }
            else
            {
                this.m_btnAskForAnimals = null;
            }
            if (param4)
            {
                this.revertToDefault();
            }
            return;
        }//end

        public void  setMessage (String param1 )
        {
            this.m_placeContainer.removeAll();
            this.m_btnContainer.removeAll();
            this.m_mainTxtContainer.removeAll();
            this.endAskAgainPoll();
            this.m_mainTxt = ASwingHelper.makeMultilineText(param1, 300, EmbeddedArt.defaultFontNameBold, TextFormatAlign.LEFT, 16, EmbeddedArt.brownTextColor);
            this.m_mainTxtContainer.append(this.m_mainTxt);
            if (this.m_btnAskForAnimals)
            {
                this.m_btnContainer.append(this.m_btnAskForAnimals);
            }
            ASwingHelper.prepare(this.m_win);
            return;
        }//end

        public void  revertToDefault ()
        {
            this.setMessage(this.m_defaultMessage);
            if (this.m_placeNowModule)
            {
                this.m_placeContainer.append(this.m_placeNowModule);
            }
            this.beginAskAgainPoll();
            ASwingHelper.prepare(this.m_win);
            return;
        }//end

        private void  beginAskAgainPoll ()
        {
            this.updateAskAgainTimer();
            if (!this.m_askAgainTimer)
            {
                this.m_askAgainTimer = new Timer(500);
                this.m_askAgainTimer.addEventListener(TimerEvent.TIMER, this.onPollTick);
            }
            this.m_askAgainTimer.start();
            return;
        }//end

        private void  endAskAgainPoll ()
        {
            if (this.m_askAgainTimer)
            {
                this.m_askAgainTimer.stop();
            }
            return;
        }//end

        private void  onPollTick (TimerEvent event )
        {
            this.updateAskAgainTimer();
            return;
        }//end

        private void  updateAskAgainTimer ()
        {
            Viral _loc_1 =null ;
            int _loc_2 =0;
            String _loc_3 =null ;
            if (this.m_btnAskForAnimals)
            {
                _loc_1 = Global.world.viralMgr.getActiveViralByType(this.m_enclosureToAskForAnimals.getItem().feed);
                _loc_2 = _loc_1 ? (_loc_1.timeToUnlock()) : (0);
                _loc_3 = GameUtil.formatMinutesSeconds(_loc_2);
                if (_loc_2 > 0)
                {
                    this.m_unlockTxt.setText(ZLoc.t("Dialogs", "ZooDialog_askAgainIn", {time:_loc_3}));
                    if (this.m_btnAskForAnimals.isEnabled())
                    {
                        this.m_btnAskForAnimals.setEnabled(false);
                    }
                }
                else
                {
                    this.m_unlockTxt.setText("");
                    if (!this.m_btnAskForAnimals.isEnabled())
                    {
                        this.m_btnAskForAnimals.setEnabled(true);
                    }
                }
            }
            ASwingHelper.prepare(this.m_win);
            return;
        }//end

        private void  onBtnAskForAnimalsClick (Event event )
        {
            StatsManager.sample(100, "Zoo", "onAnimalAsk", ZooDialogView.currentInstance.lastDefaultKey);
            Global.world.viralMgr.sendZooFeed(this.m_enclosureToAskForAnimals.getItem().name, Global.player.cityName);
            this.m_enclosureToAskForAnimals.updateStagePickEffect();
            this.beginAskAgainPoll();
            return;
        }//end

        private void  onPlaceItemModuleClose (Event event )
        {
            dispatchEvent(new Event(Event.CLOSE));
            return;
        }//end

    }



