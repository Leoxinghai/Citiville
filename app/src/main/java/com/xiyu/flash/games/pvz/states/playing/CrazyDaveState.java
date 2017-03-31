package com.xiyu.flash.games.pvz.states.playing;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
//import android.graphics.Color;
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
import com.xiyu.util.*;

import com.xiyu.flash.framework.states.IState;
import com.xiyu.flash.framework.widgets.ui.IButtonListener;
import com.xiyu.flash.framework.widgets.ui.CButtonWidget;
import com.xiyu.flash.games.pvz.logic.Board;
import com.xiyu.flash.games.pvz.renderables.StringRenderable;
import com.xiyu.flash.framework.resources.reanimator.looptypes.ReanimLoopQueue;
import com.xiyu.flash.games.pvz.PVZFoleyType;
import com.xiyu.flash.framework.resources.reanimator.Reanimation;
import com.xiyu.flash.games.pvz.renderables.ImageRenderable;
import com.xiyu.flash.games.pvz.PVZApp;
import com.xiyu.flash.games.pvz.PVZMusic;
import com.xiyu.flash.framework.graphics.Graphics2D;
import com.xiyu.flash.games.pvz.renderables.ReanimationRenderable;
import com.xiyu.flash.framework.graphics.Color;
import com.xiyu.flash.games.pvz.resources.PVZFonts;
import com.xiyu.flash.framework.resources.fonts.FontInst;
import com.xiyu.flash.games.pvz.resources.PVZImages;
import com.xiyu.flash.framework.resources.images.ImageInst;

    public class CrazyDaveState implements IState, IButtonListener {

        public static final int CRAZY_DAVE_HANDING_TALKING =5;
        public static final int CRAZY_DAVE_LEAVING =2;
        public static final int CRAZY_DAVE_OFF =0;
        public static final int CRAZY_DAVE_ENTERING =1;
        public static final int CRAZY_DAVE_TALKING =4;
        public static final int CRAZY_DAVE_IDLING =3;
        public static final int CRAZY_DAVE_HANDING_IDLING =6;

        private CButtonWidget theButton ;
        public String mCrazyDaveMessageText ;

        public void  buttonMouseEnter (int id ){
        }
        public void  CrazyDaveTalkIndex (int theMessageIndex ){
            this.mCrazyDaveMessageIndex = theMessageIndex;
            String aMessageText =this.GetCrazyDaveText(theMessageIndex );
            this.CrazyDaveTalkMessage(aMessageText);
        }
        public void  AdvanceCrazyDaveDialog (boolean theJustSkipping ){
            if (this.mCrazyDaveMessageIndex == -1)
            {
                return;
            };
            if ((((this.mCrazyDaveMessageIndex == 2406)) && (!(theJustSkipping))))
            {
                this.app.mBoard.SetTutorialState(Board.TUTORIAL_SHOVEL_PICKUP);
                this.CrazyDaveLeave();
                return;
            };
            if (!this.AdvanceCrazyDaveText())
            {
                this.CrazyDaveLeave();
                return;
            };
            if (this.mCrazyDaveMessageIndex == 2411)
            {
                this.app.mBoard.mChallenge.mShowBowlingLine = true;
            };
        }

        private StringRenderable mSpeechTextRenderable ;

        public void  CrazyDaveTalkMessage (String theMessage ){
            this.app.soundManager().stopAll();
            Array anArray =new Array ();
            boolean aDoTalkSound =true ;
            int aMessageLen =0;
            boolean aInsideBracket =false;
            if (this.mWallnutReanim != null)
            {
                this.mWallnutReanim.mIsDead = true;
            };
            int i =0;
            while (i < theMessage.length())
            {
                if (theMessage.charAt(i) == '{')
                {
                    aInsideBracket = true;
                }
                else
                {
                    if (theMessage.charAt(i) == '}')
                    {
                        aInsideBracket = false;
                    }
                    else
                    {
                        if (!aInsideBracket)
                        {
                            aMessageLen++;
                        };
                    };
                };
                i++;
            };
            if (!(((this.mCrazyDaveState == CRAZY_DAVE_TALKING)) && (!(aDoTalkSound))))
            {
                if (theMessage.startsWith("{SHAKE}"))
                {
                    theMessage = theMessage.replace("{SHAKE}", "");
                    this.mCrazyDaveReanim.currentTrack("anim_crazy");
                    this.mCrazyDaveReanim.animRate(12);
                    anArray.add(0, "anim_idle");
                    this.mCrazyDaveReanim.loopType(new ReanimLoopQueue(anArray));
                    if (aDoTalkSound)
                    {
                        this.app.foleyManager().playFoley(PVZFoleyType.CRAZYDAVECRAZY);
                    };
                    this.mCrazyDaveState = CRAZY_DAVE_TALKING;
                }
                else
                {
                    if (theMessage.startsWith("{SCREAM}"))
                    {
                        theMessage = theMessage.replace("{SCREAM}", "");
                        this.mCrazyDaveReanim.currentTrack("anim_smalltalk");
                        this.mCrazyDaveReanim.animRate(12);
                        anArray.add(0,"anim_idle");
                        this.mCrazyDaveReanim.loopType(new ReanimLoopQueue(anArray));
                        if (aDoTalkSound)
                        {
                            this.app.foleyManager().playFoley(PVZFoleyType.CRAZYDAVESCREAM);
                        };
                        this.mCrazyDaveState = CRAZY_DAVE_TALKING;
                    }
                    else
                    {
                        if (theMessage.startsWith("{SHOW_WALLNUT}"))
                        {
                            theMessage = theMessage.replace("{SHOW_WALLNUT}", "");
                            this.mCrazyDaveReanim.currentTrack("anim_talk_handing");
                            this.mCrazyDaveReanim.animRate(12);
                            anArray.add(0,"anim_idle_handing");
                            this.mCrazyDaveReanim.loopType(new ReanimLoopQueue(anArray));
                            this.mWallnutReanim = this.app.reanimator().createReanimation("REANIM_WALLNUT");
                            this.mWallnutReanim.currentTrack("anim_idle1");
                            this.mWallnutReanim.animRate(12);
                            this.mWallnutReanim.loopType(Reanimation.LOOP_TYPE_ALWAYS);
                            this.mCrazyDaveReanim.attachReanimation(this.mWallnutReanim, "Dave_handinghand", 67, 275);
                            if (aDoTalkSound)
                            {
                                this.app.foleyManager().playFoley(PVZFoleyType.CRAZYDAVESCREAM2);
                            };
                            this.mCrazyDaveState = CRAZY_DAVE_HANDING_TALKING;
                        }
                        else
                        {
                            if (theMessage.startsWith("{SHOW_HAMMER}"))
                            {
                                if (aMessageLen < 23)
                                {
                                    this.mCrazyDaveReanim.currentTrack("anim_smalltalk");
                                    this.mCrazyDaveReanim.animRate(12);
                                    anArray.add(0,"anim_idle");
                                    this.mCrazyDaveReanim.loopType(new ReanimLoopQueue(anArray));
                                    if (aDoTalkSound)
                                    {
                                        this.app.foleyManager().playFoley(PVZFoleyType.CRAZYDAVESHORT);
                                    };
                                    this.mCrazyDaveState = CRAZY_DAVE_TALKING;
                                }
                                else
                                {
                                    if (aMessageLen < 52)
                                    {
                                        this.mCrazyDaveReanim.currentTrack("anim_mediumtalk");
                                        this.mCrazyDaveReanim.animRate(12);
                                        anArray.add(0,"anim_idle");
                                        this.mCrazyDaveReanim.loopType(new ReanimLoopQueue(anArray));
                                        if (aDoTalkSound)
                                        {
                                            this.app.foleyManager().playFoley(PVZFoleyType.CRAZYDAVELONG);
                                        };
                                        this.mCrazyDaveState = CRAZY_DAVE_TALKING;
                                    }
                                    else
                                    {
                                        this.mCrazyDaveReanim.currentTrack("anim_blahblah");
                                        this.mCrazyDaveReanim.animRate(12);
                                        anArray.add(0,"anim_idle");
                                        this.mCrazyDaveReanim.loopType(new ReanimLoopQueue(anArray));
                                        if (aDoTalkSound)
                                        {
                                            this.app.foleyManager().playFoley(PVZFoleyType.CRAZYDAVEEXTRALONG);
                                        };
                                        this.mCrazyDaveState = CRAZY_DAVE_TALKING;
                                    };
                                };
                            };
                        };
                    };
                };
            };
            theMessage = theMessage.replace("{MOUTH_SMALL_OH}", "");
            theMessage = theMessage.replace("{MOUTH_SMALL_SMILE}", "");
            theMessage = theMessage.replace("{MOUTH_BIG_SMILE}", "");
            theMessage = theMessage.replace("{SHAKE}", "");
            this.mCrazyDaveMessageText = theMessage;
        }
        public void  buttonMouseMove (int id ,int x ,int y ){
        }

        private Reanimation mCrazyDaveReanim ;

        public void  onPush (){
        }

        private ImageRenderable mSpeechBubble ;
        private PVZApp app ;

        public void  onExit (){
            this.app.widgetManager().removeWidget(this.theButton);
        }

        public int mCrazyDaveDialogStart ;

        public boolean  AdvanceCrazyDaveText (){
            int aNextMessage =(this.mCrazyDaveMessageIndex +1);
            String aMessageName =(("[CRAZY_DAVE_"+aNextMessage )+"]");
            if (aMessageName == "[CRAZY_DAVE_2407]")
            {
                return (false);
            };
            if ((((((((aMessageName == "[CRAZY_DAVE_2416]")) || ((aMessageName == "[CRAZY_DAVE_3003]")))) || ((aMessageName == "[CRAZY_DAVE_208]")))) || ((aMessageName == "[CRAZY_DAVE_3303]"))))
            {
                this.CrazyDaveDie();
            }
            else
            {
                this.CrazyDaveTalkIndex(aNextMessage);
            };
            return (true);
        }
        public void  buttonDownTick (int id ){
        }
        public void  UpdateCrazyDave (){
            if (this.mCrazyDaveDelay > 0)
            {
                this.mCrazyDaveDelay--;
                if (this.mCrazyDaveDelay == 0)
                {
                    this.mCrazyDaveDelay = 350;
                    this.AdvanceCrazyDaveDialog(false);
                };
            };
            if (this.mCrazyDaveReanim==null)
            {
                return;
            };
            if (this.mCrazyDaveReanim.mIsDead)
            {
                this.app.mBoard.mGameScene = PVZApp.SCENE_LEVEL_INTRO;
                this.app.widgetManager().removeWidget(this.theButton);
                if (this.app.mBoard.IsWallnutBowlingLevel())
                {
                    this.app.widgetManager().setFocus(this.app.mBoard);
                    this.app.stateManager().changeState(PVZApp.STATE_SLIDE_UI);
                }
                else
                {
                    if (this.app.IsScaryPotterLevel())
                    {
                        this.app.widgetManager().setFocus(this.app.mBoard);
                        this.app.musicManager().playMusic(PVZMusic.CEREBRAWL, true, 50);
                        this.app.stateManager().changeState(PVZApp.STATE_SLIDE_UI);
                    }
                    else
                    {
                        if (((this.app.IsAdventureMode()) && ((this.app.mLevel == 11))))
                        {
                            this.app.widgetManager().setFocus(this.app.mBoard);
                            this.app.musicManager().playMusic(PVZMusic.CHOOSE_YOUR_SEEDS, true, 50);
                            this.app.stateManager().changeState(PVZApp.STATE_PAN_RIGHT);
                        }
                        else
                        {
                            if (((this.app.IsAdventureMode()) && ((this.app.mBoard.mLevel == 14))))
                            {
                                if (this.app.mUpsellOn)
                                {
                                    this.app.stateManager().changeState(PVZApp.STATE_UPSELL_SCREEN);
                                }
                                else
                                {
                                    this.app.stateManager().changeState(PVZApp.STATE_MAIN_MENU);
                                };
                            };
                        };
                    };
                };
            };
            if(this.mCrazyDaveReanim.currentTrack().equals("anim_idle"))
            {
                    this.mCrazyDaveState = CRAZY_DAVE_IDLING;
                    if (this.mCrazyDaveReanim.animRate() != 12)
                    {
                        this.mCrazyDaveReanim.animRate(12);
                    };
            } else if(this.mCrazyDaveReanim.currentTrack().equals("anim_enter")) {
                    this.mCrazyDaveState = CRAZY_DAVE_ENTERING;
            } else if(this.mCrazyDaveReanim.currentTrack().equals("anim_leave")) {
                    this.mCrazyDaveState = CRAZY_DAVE_LEAVING;
            } else if(this.mCrazyDaveReanim.currentTrack().equals("anim_blahblah")) {
                this.mCrazyDaveState = CRAZY_DAVE_TALKING;
            } else if(this.mCrazyDaveReanim.currentTrack().equals("anim_smalltalk")) {
                this.mCrazyDaveState = CRAZY_DAVE_TALKING;
            } else if(this.mCrazyDaveReanim.currentTrack().equals("anim_mediumtalk")) {
                this.mCrazyDaveState = CRAZY_DAVE_TALKING;
            } else if(this.mCrazyDaveReanim.currentTrack().equals("anim_crazy")) {
                    this.mCrazyDaveState = CRAZY_DAVE_TALKING;
            } else if(this.mCrazyDaveReanim.currentTrack().equals("anim_enter_handing")) {
                    this.mCrazyDaveState = CRAZY_DAVE_ENTERING;
            } else if(this.mCrazyDaveReanim.currentTrack().equals("anim_idle_handing")) {
                    this.mCrazyDaveState = CRAZY_DAVE_HANDING_IDLING;
            } else if(this.mCrazyDaveReanim.currentTrack().equals("anim_talk_handing")) {
                    this.mCrazyDaveState = CRAZY_DAVE_HANDING_TALKING;
            };
            if (((((this.app.mBoard.IsWallnutBowlingLevel()) && ((this.app.mBoard.mTutorialState == Board.TUTORIAL_SHOVEL_COMPLETED)))) && (!(this.mIsCrazyDaveShowing))))
            {
                this.mCrazyDaveDialogStart = 2410;
                this.CrazyDaveTalkIndex(this.mCrazyDaveDialogStart);
                this.mCrazyDaveDialogStart = -1;
                this.CrazyDaveEnter();
            };
        }

        public int mCrazyDaveDelay ;

        public void  DrawCrazyDave (Graphics2D g ){
//            this.mSpeechTextRenderable.text(this.mCrazyDaveMessageText);
        }
        public void  buttonPress (int id ){
        }

        public int mCrazyDaveState ;

        public void  CrazyDaveDie (){
            if (this.mCrazyDaveReanim==null)
            {
                return;
            };
            if (this.app.mBoard.mLevel == 14)
            {
                this.mCrazyDaveReanim.currentTrack("anim_leave");
            }
            else
            {
                this.mCrazyDaveReanim.currentTrack("anim_leave");
            };
            this.mCrazyDaveReanim.loopType(Reanimation.LOOP_TYPE_ONCE_AND_DIE);
            this.mCrazyDaveReanim.animRate(24);
            this.mCrazyDaveState = CRAZY_DAVE_LEAVING;
            this.mCrazyDaveMessageIndex = -1;
            this.mCrazyDaveMessageText = "";
            this.mSpeechBubble.dead(true);
            this.mContinueTextRenderable.dead(true);
            this.mSpeechTextRenderable.dead(true);
        }
        public void  onEnter (){

            this.mCrazyDaveReanim = this.app.reanimator().createReanimation("REANIM_PEASHOOTER");//REANIM_ZOMBIE");//ZOMBIE_FOOTBALL");
            this.mCrazyDaveReanim.x((-15 + 300));
            this.mCrazyDaveReanim.y(-15+50);
            this.mCrazyDaveReanim.currentTrack("anim_full_idle");
            this.mCrazyDaveReanim.animRate(48);
            this.mCrazyDaveReanim.loopType(Reanimation.LOOP_TYPE_ALWAYS);
            this.mCrazyDaveReanim.animTime(1000);

//            this.mCrazyDaveReanim.setTrackVisible("anim_cone", false);
//            this.mCrazyDaveReanim.setTrackVisible("anim_bucket", false);
            this.app.mBoard.mRenderManager.add(new ReanimationRenderable(this.mCrazyDaveReanim, Board.RENDER_LAYER_ABOVE_UI, true));
            return;
            /*
        	this.app.mBoard.mMenuButton.setVisible(false);
            if (((this.app.IsAdventureMode()) && ((this.app.mLevel == 14))))
            {
                this.mCrazyDaveDelay = 350;
            }
            else
            {
                this.mCrazyDaveDelay = 0;
            };
            int boardOffset =0;
            if (((this.app.IsAdventureMode()) && ((this.app.mLevel == 11))))
            {
                boardOffset = -(PVZApp.BOARD_OFFSET);
            }
            else
            {
                if (((this.app.IsAdventureMode()) && ((this.app.mLevel == 14))))
                {
                    boardOffset = -(PVZApp.BOARD_OFFSET);
                };
            };
            this.app.mBoard.mGameScene = PVZApp.SCENE_CRAZY_DAVE;
            this.mCrazyDaveReanim = this.app.reanimator().createReanimation("REANIM_CRAZYDAVE");
            this.mCrazyDaveReanim.x((-15 + boardOffset));
            this.app.mBoard.mRenderManager.add(new ReanimationRenderable(this.mCrazyDaveReanim, Board.RENDER_LAYER_ABOVE_UI, true));
            this.theButton = new CButtonWidget(0, this);
            this.theButton.label("");
            this.theButton.setColor(CButtonWidget.COLOR_LABEL, Color.RGB(1, 1, 1));
            FontInst font =this.app.fontManager().getFontInst(PVZFonts.FONT_BRIANNETOD16 );
            this.theButton.font(font);
            this.theButton.setDisabled(false);
            this.theButton.visible = true;
            this.theButton.resize(0, 0, 540, 405);
//            this.app.widgetManager().addWidget(this.theButton);
            this.app.widgetManager().setFocus(this.theButton);
            ImageInst theBubbleImage =this.app.imageManager().getImageInst(PVZImages.IMAGE_STORE_SPEECHBUBBLE2 );
            theBubbleImage.x((190 + boardOffset));
            theBubbleImage.y(20);
            this.mSpeechBubble = new ImageRenderable(theBubbleImage, Board.RENDER_LAYER_ABOVE_UI);
            this.app.mBoard.mRenderManager.add(this.mSpeechBubble);
            FontInst aFont =this.app.fontManager().getFontInst(PVZFonts.FONT_BRIANNETOD16 );
            aFont.setColor(1, 0, 0, 0);
            this.mSpeechTextRenderable = new StringRenderable((Board.RENDER_LAYER_ABOVE_UI + 1));
            this.mSpeechTextRenderable.setBounds((195 + boardOffset), 20, 180, 95);
            this.mSpeechTextRenderable.font(aFont);
            this.mSpeechTextRenderable.text("Crazy Dave Text");
//            this.app.mBoard.mRenderManager.add(this.mSpeechTextRenderable);

            if (this.mCrazyDaveDelay == 0)
            {
                aFont = this.app.fontManager().getFontInst(PVZFonts.FONT_PICO129);
                aFont.setColor(1, 0, 0, 0);
                this.mContinueTextRenderable = new StringRenderable((Board.RENDER_LAYER_ABOVE_UI + 1));
                this.mContinueTextRenderable.setBounds((240 + boardOffset), 110, 100, 20);
                this.mContinueTextRenderable.font(aFont);
                this.mContinueTextRenderable.text(this.app.stringManager().translateString("[CLICK_TO_CONTINUE]"));
                this.app.mBoard.mRenderManager.add(this.mContinueTextRenderable);
            }
            else
            {
                this.mContinueTextRenderable.text("");
                this.mContinueTextRenderable.dead(true);
            };
            if (((this.app.mBoard.IsWallnutBowlingLevel()) && (this.app.IsAdventureMode())))
            {
                this.mCrazyDaveDialogStart = 2400;
                this.CrazyDaveTalkIndex(this.mCrazyDaveDialogStart);
                this.mCrazyDaveDialogStart = -1;
                this.app.mBoard.mShowShovel = true;
                this.CrazyDaveEnter();
            }
            else
            {
                if (this.app.IsScaryPotterLevel())
                {
                    this.mCrazyDaveDialogStart = 3000;
                    this.CrazyDaveTalkIndex(this.mCrazyDaveDialogStart);
                    this.mCrazyDaveDialogStart = -1;
                    this.CrazyDaveEnter();
                }
                else
                {
                    if (((this.app.IsAdventureMode()) && ((this.app.mLevel == 11))))
                    {
                        this.app.mBoard.move(PVZApp.BOARD_OFFSET, 0);
                        this.mCrazyDaveDialogStart = 201;
                        this.CrazyDaveTalkIndex(this.mCrazyDaveDialogStart);
                        this.mCrazyDaveDialogStart = -1;
                        this.CrazyDaveEnter();
                    }
                    else
                    {
                        if (((this.app.IsAdventureMode()) && ((this.app.mBoard.mLevel == 14))))
                        {
                            this.mCrazyDaveDialogStart = 3300;
                            this.CrazyDaveTalkIndex(this.mCrazyDaveDialogStart);
                            this.mCrazyDaveDialogStart = -1;
                            this.CrazyDaveEnter();
                        };
                    };
                };
            };
*/
        }
        public void  draw (Graphics2D g ){
            this.app.widgetManager().drawScreen(g);
            this.DrawCrazyDave(g);
        }
        public void  update (){
            this.app.widgetManager().updateFrame();
            this.UpdateCrazyDave();
        }

        private StringRenderable mContinueTextRenderable ;
        private Reanimation mWallnutReanim ;

        public String  LookUpCrazyDaveText (String theMessageIndex ){
            String aMessage ;
            if(theMessageIndex.equals("[CRAZY_DAVE_2400]"))
            {
                    aMessage = "Greetings, neighbor!";
            } else if(theMessageIndex.equals("[CRAZY_DAVE_2401]")) {
                    aMessage = "The name's Crazy Dave.!";
            } else if(theMessageIndex.equals("[CRAZY_DAVE_2402]")) {
                    aMessage = "But you can just call me Crazy Dave.";
            } else if(theMessageIndex.equals("[CRAZY_DAVE_2403]")) {
                    aMessage = "Listen, I've got a surprise for you.";
            } else if(theMessageIndex.equals("[CRAZY_DAVE_2404]")) {
                    aMessage = "Listen, I've got a surprise for you.";
            } else if(theMessageIndex.equals("[CRAZY_DAVE_2405]")) {
                    aMessage = "Use your shovel and dig up those plants!";
            } else if(theMessageIndex.equals("[CRAZY_DAVE_2406]")) {
                    aMessage = "LET THE DIGGING COMMENCE!{MOUTH_SMALL_OH} {SCREAM}";
            } else if(theMessageIndex.equals("[CRAZY_DAVE_2410]")) {
                    aMessage = "Ok goody, now for the surprise...";
            } else if(theMessageIndex.equals("[CRAZY_DAVE_2411]")) {
                    aMessage = "We're going BOWLING!{MOUTH_SMALL_SMILE}";
            } else if(theMessageIndex.equals("[CRAZY_DAVE_2412]")) {
                    aMessage = "{SHOW_WALLNUT}HERE, TAKE THIS WALL-NUT!!";
            } else if(theMessageIndex.equals("[CRAZY_DAVE_2413]")) {
                    aMessage = "Why'd I put a wall-nut in your hand?{MOUTH_SMALL_OH}";
            } else if(theMessageIndex.equals("[CRAZY_DAVE_2414]")) {
                    aMessage = "{SHAKE}Because I'm CRAAAZY!!!!!{MOUTH_BIG_SMILE}";
            } else if(theMessageIndex.equals("[CRAZY_DAVE_2415]")) {
                    aMessage = "NOW GO! BOWL ME A WINNER!{MOUTH_BIG_SMILE} {SCREAM}";
            } else{
                    aMessage = "";
            };
            return (aMessage);
        }
        public void  handleEffect (String effect ){
            if (effect.equals("{HANDING}"))
            {
            } else if (effect.equals("SCREAM")) {
            } else {
                    //throw (new Error(("Unhandled text effect: " + effect)));
            };
        }

        public int mCrazyDaveMessageIndex ;

        public void  CrazyDaveLeave (){
            this.mIsCrazyDaveShowing = false;
            if (this.mCrazyDaveReanim == null)
            {
                return;
            };
            if ((((this.mCrazyDaveState == CRAZY_DAVE_HANDING_TALKING)) || ((this.mCrazyDaveState == CRAZY_DAVE_HANDING_IDLING))))
            {
                this.CrazyDaveDoneHanding();
            };
            this.mCrazyDaveReanim.currentTrack("anim_leave");
            this.mCrazyDaveReanim.loopType(Reanimation.LOOP_TYPE_ONCE_AND_HOLD);
            this.mCrazyDaveReanim.animRate(24);
            this.mCrazyDaveState = CRAZY_DAVE_LEAVING;
            this.mCrazyDaveMessageIndex = -1;
            this.mCrazyDaveMessageText = "";
            this.mSpeechBubble.dead(true);
            this.mContinueTextRenderable.dead(true);
            this.mSpeechTextRenderable.dead(true);
            this.app.widgetManager().removeWidget(this.theButton);
            this.app.widgetManager().setFocus(this.app.mBoard);
        }
        public void  CrazyDaveEnter (){
            this.mSpeechBubble.dead(false);
            if (this.mCrazyDaveDelay == 0)
            {
                this.mContinueTextRenderable.dead(false);
            };
            this.mSpeechTextRenderable.dead(false);
            this.app.mBoard.mRenderManager.add(this.mSpeechBubble);
            this.app.mBoard.mRenderManager.add(this.mContinueTextRenderable);
            this.app.mBoard.mRenderManager.add(this.mSpeechTextRenderable);
            this.mIsCrazyDaveShowing = true;
            if (((this.app.IsAdventureMode()) && ((this.app.mBoard.mLevel == 14))))
            {
                this.mCrazyDaveReanim.currentTrack("anim_enter");
                this.mCrazyDaveReanim.x(10);
            }
            else
            {
                this.mCrazyDaveReanim.currentTrack("anim_enter");
            };
            this.mCrazyDaveReanim.animRate(24);
            String[] anims = {"anim_smalltalk","anim_idle"};
            Array anArray =new Array(anims);
            this.mCrazyDaveReanim.loopType(new ReanimLoopQueue(anArray));
            this.mCrazyDaveState = CRAZY_DAVE_ENTERING;
            this.app.widgetManager().addWidget(this.theButton);
            this.app.widgetManager().setFocus(this.theButton);
        }
        public String  GetCrazyDaveText (int theMessageIndex ){
            String aText =(("[CRAZY_DAVE_"+theMessageIndex )+"]");
            String aMessage =this.app.stringManager().translateString(aText );
            return (aMessage);
        }
        public void  buttonMouseLeave (int id ){
        }
        public void  buttonRelease (int id ){
            if (this.mCrazyDaveDelay == 0)
            {
                this.AdvanceCrazyDaveDialog(false);
            };
        }
        public void  onPop (){
        }

        public void setView() {
			app.getMainView().setState(this);
        }

        public void  CrazyDaveDoneHanding (){
        }

        public boolean mIsCrazyDaveShowing ;

        public  CrazyDaveState (PVZApp app ){
            this.app = app;
        }
    }


