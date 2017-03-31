package com.xiyu.flash.framework.resources.particles;
import java.util.Hashtable;

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

import com.xiyu.util.*;
import com.xiyu.flash.framework.AppBase;
    public class ParticleManager {

        private AppBase mApp ;
        private Hashtable particles;

/*
        public ParticleSystem  spawnParticleSystem (String id ){
            ParticleDefinition aTmp ;
            ParticleDescriptor aDesc =(ParticleDescriptor)this.mApp.resourceManager().getResource(id );
            if (aDesc != null)
            {
                aTmp = aDesc.createData(this.mApp);
                this.mApp.resourceManager().setResource(id, aTmp);
            };
            ParticleDefinition aData =(ParticleDefinition)this.mApp.resourceManager().getResource(id );
            if (aData == null)
            {
                //throw (new Error((("Particle type '" + id) + "' instanceof not loaded!")));
            };
            ParticleSystem particle =new ParticleSystem ();
            particle.initFromDef(aData);
            return (particle);
        }
*/
        public ParticleSystem  spawnParticleSystem (String id ){
//            ParticleDefinition aTmp ;
            ParticleDefinition aData =(ParticleDefinition)this.particles.get(id );
            if (aData == null)
            {
            	aData = mApp.getParticleDefinition(id);
                this.particles.put(id, aData);
            };

//            ParticleDefinition aData =(ParticleDefinition)this.mApp.resourceManager().getResource(id );
            if (aData == null)
            {
                //throw (new Error((("Particle type '" + id) + "' instanceof not loaded!")));
            };
            ParticleSystem particle =new ParticleSystem ();
            particle.initFromDef(aData);
            return (particle);
        }

        public  ParticleManager (AppBase app ){
            this.mApp = app;
            particles = new Hashtable();
        }
    }


