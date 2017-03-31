package Modules.garden.ui;

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

    public class GardenSpeechBubble extends Sprite
    {
        private JWindow m_win ;
        private JPanel m_container ;
        private JPanel m_body ;
        private JPanel m_mainTxtContainer ;
        private JPanel m_placeContainer ;
        private JPanel m_btnContainer ;
        private AssetPane m_mainTxt ;
        private JLabel m_unlockTxt ;
        private CustomButton m_btnAskForFlowers ;
        private String m_defaultMessage ;
        private String m_itemNameToPlace ;
        private ZooEnclosure m_enclosureToAskForAnimals ;
        private Timer m_askAgainTimer ;
        private Item m_item ;

        public  GardenSpeechBubble (Item param1 ,String param2 )
        {
            this.m_mainTxtContainer = ASwingHelper.makeSoftBoxJPanelVertical();
            this.m_placeContainer = ASwingHelper.makeSoftBoxJPanelVertical();
            _loc_3 = ASwingHelper.makeSoftBoxJPanel ();
            _loc_3.appendAll(this.m_mainTxtContainer, this.m_unlockTxt, this.m_placeContainer);
            this.m_unlockTxt = ASwingHelper.makeLabel("", EmbeddedArt.defaultFontName, 14, EmbeddedArt.lightGrayTextColor);
            this.m_btnContainer = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.CENTER);
            this.m_body = ASwingHelper.makeSoftBoxJPanelVertical();
            this.m_body.appendAll(_loc_3, this.m_unlockTxt, this.m_btnContainer);
            ASwingHelper.setEasyBorder(this.m_body, 10, 10, 30, 10);
            MarginBackground _loc_4 =new MarginBackground(new GardenDialog.assetDict.get( "speech_bubble") ,new Insets(0,0,0,0));
            this.m_container = ASwingHelper.makeSoftBoxJPanel();
            this.m_container.setBackgroundDecorator(_loc_4);
            this.m_container.append(this.m_body);
            this.m_win = new JWindow(this);
            this.m_win.setContentPane(this.m_container);
            this.m_win.show();
            this.m_btnAskForFlowers = new CustomButton(ZLoc.t("Dialogs", "GardenDialog_askDonation"), null, "GreenButtonUI");
            this.m_btnAskForFlowers.addActionListener(this.onBtnAskForAnimalsClick, 0, true);
            this.m_btnContainer.append(this.m_btnAskForFlowers);
            ASwingHelper.prepare(this.m_win);
            this.m_mainTxt = ASwingHelper.makeMultilineText(param2, 300, EmbeddedArt.defaultFontNameBold, TextFormatAlign.LEFT, 16, EmbeddedArt.blueTextColor);
            this.m_mainTxtContainer.append(this.m_mainTxt);
            if (this.m_btnAskForFlowers)
            {
                this.m_btnContainer.append(this.m_btnAskForFlowers);
            }
            this.m_item = param1;
            _loc_5 = this.getTimeLeft ();
            if (this.getTimeLeft() > 0)
            {
                this.beginAskAgainPoll();
            }
            ASwingHelper.prepare(this.m_win);
            return;
        }//end

        private void  beginAskAgainPoll ()
        {
            this.updateAskAgainTimer();
            this.m_askAgainTimer = new Timer(500);
            this.m_askAgainTimer.addEventListener(TimerEvent.TIMER, this.onPollTick);
            this.m_askAgainTimer.start();
            return;
        }//end

        private void  endAskAgainPoll ()
        {
            if (this.m_askAgainTimer)
            {
                this.m_askAgainTimer.removeEventListener(TimerEvent.TIMER, this.onPollTick);
                this.m_askAgainTimer.stop();
            }
            return;
        }//end

        private void  onPollTick (TimerEvent event )
        {
            this.updateAskAgainTimer();
            return;
        }//end

        private int  getTimeLeft ()
        {
            _loc_1 =Global.world.viralMgr.getActiveViralByType(this.m_item.feed );
            _loc_2 = _loc_1? (_loc_1.timeToUnlock()) : (0);
            return _loc_2;
        }//end

        private void  updateAskAgainTimer ()
        {
            int _loc_1 =0;
            String _loc_2 =null ;
            if (this.m_btnAskForFlowers)
            {
                _loc_1 = this.getTimeLeft();
                _loc_2 = GameUtil.formatMinutesSeconds(_loc_1);
                if (_loc_1 > 0)
                {
                    this.m_unlockTxt.setText(ZLoc.t("Dialogs", "GardenDialog_askAgainIn", {time:_loc_2}));
                    if (this.m_btnAskForFlowers.isEnabled())
                    {
                        this.m_btnAskForFlowers.setEnabled(false);
                    }
                }
                else
                {
                    this.m_unlockTxt.setText("");
                    if (!this.m_btnAskForFlowers.isEnabled())
                    {
                        this.m_btnAskForFlowers.setEnabled(true);
                    }
                }
            }
            ASwingHelper.prepare(this.m_win);
            return;
        }//end

        private void  onBtnAskForAnimalsClick (Event event )
        {
            StatsManager.sample(100, "Garden", "onFlowerAsk");
            Global.world.viralMgr.sendFlowerRequestFeed(this.m_item.name, Global.player.cityName);
            this.beginAskAgainPoll();
            return;
        }//end

        private void  onPlaceItemModuleClose (Event event )
        {
            dispatchEvent(new Event(Event.CLOSE));
            return;
        }//end

    }



