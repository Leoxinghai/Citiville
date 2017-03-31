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
import com.xiyu.flash.games.pvz.PVZApp;
import com.xiyu.flash.framework.graphics.Graphics2D;
import com.xiyu.flash.framework.resources.reanimator.Reanimation;
import com.xiyu.flash.framework.resources.particles.ParticleSystem;
import com.xiyu.flash.games.pvz.logic.TodCommon;
import com.xiyu.flash.games.pvz.PVZFoleyType;
import com.xiyu.flash.games.pvz.resources.PVZParticles;
import com.xiyu.flash.games.pvz.resources.PVZReanims;
import com.xiyu.flash.games.pvz.renderables.ReanimationRenderable;
import com.xiyu.flash.games.pvz.renderables.ParticleRenderable;
import com.xiyu.flash.games.pvz.logic.Board;

    public class SodRollState implements IState {

        public static int TimeRollSodStart =500;
        public static int TimeRollSodEnd =(TimeRollSodStart +2010);

        public void  onEnter (){
            this.app.mCutsceneTime = 0;
        }

        private PVZApp app ;

        public void  draw (Graphics2D g ){
            this.app.widgetManager().drawScreen(g);
        }
        public void  onPush (){
        }
        public void  onExit (){
        }
        public void  update (){
            int aTimeRollSodStart ;
            int aTimeRollSodEnd ;
            int x =0;
            Reanimation aReanimation ;
            ParticleSystem anEffect ;
            ParticleSystem anEffect2 ;
            this.app.widgetManager().updateFrame();
            this.app.mCutsceneTime = (this.app.mCutsceneTime + 10);
            if (this.app.mSodTime > 0)
            {
                aTimeRollSodStart = TimeRollSodStart;
                aTimeRollSodEnd = TimeRollSodEnd;
                x = TodCommon.TodAnimateCurve(aTimeRollSodStart, aTimeRollSodEnd, this.app.mCutsceneTime, 0, 1000, TodCommon.CURVE_LINEAR);
                this.app.mBoard.mSodPosition = x;
                if (this.app.mCutsceneTime == aTimeRollSodStart)
                {
                    this.app.foleyManager().playFoley(PVZFoleyType.DIGGER);
                    anEffect = this.app.particleManager().spawnParticleSystem(PVZParticles.PARTICLE_SODROLL);
                    anEffect2 = this.app.particleManager().spawnParticleSystem(PVZParticles.PARTICLE_SODROLL);
                    if (this.app.mBoard.mLevel == 1)
                    {
                        aReanimation = this.app.reanimator().createReanimation(PVZReanims.REANIM_SODROLL);
                        aReanimation.animRate(24);
                        aReanimation.currentTrack("SodRoll");
                        aReanimation.loopType(Reanimation.LOOP_TYPE_ONCE_AND_DIE);
                        aReanimation.x(-5);
                        aReanimation.y(15);
                        this.app.mBoard.mRenderManager.add(new ReanimationRenderable(aReanimation, 0, true));
                        anEffect.setPosition(20, 260);
                        this.app.mBoard.mRenderManager.add(new ParticleRenderable(anEffect, Board.RENDER_LAYER_TOP));
                    }
                    else
                    {
                        if (this.app.mBoard.mLevel == 2)
                        {
                            aReanimation = this.app.reanimator().createReanimation(PVZReanims.REANIM_SODROLL);
                            aReanimation.animRate(24);
                            aReanimation.currentTrack("SodRoll");
                            aReanimation.loopType(Reanimation.LOOP_TYPE_ONCE_AND_DIE);
                            aReanimation.x(-5);
                            aReanimation.y(-54);
                            this.app.mBoard.mRenderManager.add(new ReanimationRenderable(aReanimation, 0, true));
                            anEffect.setPosition(20, 200);
                            this.app.mBoard.mRenderManager.add(new ParticleRenderable(anEffect, Board.RENDER_LAYER_TOP));
                            aReanimation = this.app.reanimator().createReanimation(PVZReanims.REANIM_SODROLL);
                            aReanimation.animRate(24);
                            aReanimation.currentTrack("SodRoll");
                            aReanimation.loopType(Reanimation.LOOP_TYPE_ONCE_AND_DIE);
                            aReanimation.x(-5);
                            aReanimation.y(90);
                            this.app.mBoard.mRenderManager.add(new ReanimationRenderable(aReanimation, 0, true));
                            anEffect2.setPosition(20, 350);
                            this.app.mBoard.mRenderManager.add(new ParticleRenderable(anEffect2, Board.RENDER_LAYER_TOP));
                        }
                        else
                        {
                            if (this.app.mBoard.mLevel == 4)
                            {
                                aReanimation = this.app.reanimator().createReanimation(PVZReanims.REANIM_SODROLL);
                                aReanimation.animRate(24);
                                aReanimation.currentTrack("SodRoll");
                                aReanimation.loopType(Reanimation.LOOP_TYPE_ONCE_AND_DIE);
                                aReanimation.x(-7);
                                aReanimation.y(-118);
                                this.app.mBoard.mRenderManager.add(new ReanimationRenderable(aReanimation, 0, true));
                                anEffect.setPosition(20, 141);
                                this.app.mBoard.mRenderManager.add(new ParticleRenderable(anEffect, Board.RENDER_LAYER_TOP));
                                aReanimation = this.app.reanimator().createReanimation(PVZReanims.REANIM_SODROLL);
                                aReanimation.animRate(24);
                                aReanimation.currentTrack("SodRoll");
                                aReanimation.loopType(Reanimation.LOOP_TYPE_ONCE_AND_DIE);
                                aReanimation.x(-7);
                                aReanimation.y(152);
                                this.app.mBoard.mRenderManager.add(new ReanimationRenderable(aReanimation, 0, true));
                                anEffect2.setPosition(20, 412);
                                this.app.mBoard.mRenderManager.add(new ParticleRenderable(anEffect2, Board.RENDER_LAYER_TOP));
                            };
                        };
                    };
                };
                if (this.app.mCutsceneTime == aTimeRollSodEnd)
                {
                    this.app.stateManager().changeState(PVZApp.STATE_SLIDE_UI);
                };
            }
            else
            {
                this.app.stateManager().changeState(PVZApp.STATE_SLIDE_UI);
            };
        }
        public void  onPop (){
        }

        public void setView() {
			app.getMainView().setState(this);
        }

        public  SodRollState (PVZApp app ){
            this.app = app;
        }
    }


