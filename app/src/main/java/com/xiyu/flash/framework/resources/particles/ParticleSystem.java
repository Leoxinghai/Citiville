package com.xiyu.flash.framework.resources.particles;
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

import com.xiyu.flash.framework.graphics.Graphics2D;
    public class ParticleSystem {

        public void  die (){
            ParticleEmitter emitter ;
			for(int i =0; i<this.mEmitterList.length();i++)
			{
				emitter = (ParticleEmitter)this.mEmitterList.elementAt(i);
                emitter.deleteAll();
            };
            this.mEmitterList = new Array();
            this.mDead = true;
        }

        public boolean mDontUpdate ;

        public void  update (){
            ParticleEmitter aEmitter ;
            if (this.mDontUpdate)
            {
                return;
            };
            boolean aEmitterAlive=false;
			for(int i =0; i<this.mEmitterList.length();i++)
			{
				aEmitter = (ParticleEmitter)this.mEmitterList.elementAt(i);
                aEmitter.update();
                if (aEmitter.mEmitterDef.mCrossFadeDuration.isSet())
                {
                    if (aEmitter.mParticleList.length()> 0)
                    {
                        aEmitterAlive = true;
                    };
                }
                else
                {
                    if (!aEmitter.mDead)
                    {
                        aEmitterAlive = true;
                    };
                };
            };
            if (!aEmitterAlive)
            {
                this.mDead = true;
            };
        }
        public void  setPosition (int x ,int y ){
            ParticleEmitter aEmitter ;
			for(int i =0; i<this.mEmitterList.length();i++)
			{
				aEmitter = (ParticleEmitter)this.mEmitterList.elementAt(i);
                aEmitter.setPosition(x, y);
            };
        }

        public boolean mDead ;
        public ParticleDefinition mParticleDef ;

        public void  initFromDef (ParticleDefinition def ){
            ParticleEmitterDefinition aEmitterDef ;
            ParticleEmitter aEmitter ;
            this.mParticleDef = def;
            this.mEmitterList = new Array();
            int aEmitterDefCount =def.mEmitterDefs.length();
            int i = 0;
            while (i < aEmitterDefCount)
            {
            	aEmitterDef=(ParticleEmitterDefinition)def.mEmitterDefs.elementAt(i);
                if (aEmitterDef.mCrossFadeDuration.isSet())
                {
                }
                else
                {
                    if (((aEmitterDef.mParticleFlags.hasFlags(ParticleFlags.DIE_IF_OVERLOADED)) && (false)))
                    {
                        this.die();
                        return;
                    };
                    aEmitter = new ParticleEmitter();
                    aEmitter.initFromDef(aEmitterDef, this);
                    this.mEmitterList.push(aEmitter);
                };
                i++;
            };
            this.mDontUpdate = false;
            this.mDead = false;
        }
        public void  draw (Graphics2D g ){
            ParticleEmitter aEmitter ;
			for(int i =0; i<this.mEmitterList.length();i++)
			{
				aEmitter = (ParticleEmitter)this.mEmitterList.elementAt(i);
                aEmitter.draw(g);
            };
        }

        public Array mEmitterList ;

        public  ParticleSystem (){
            this.mEmitterList = new Array();
        }
    }


