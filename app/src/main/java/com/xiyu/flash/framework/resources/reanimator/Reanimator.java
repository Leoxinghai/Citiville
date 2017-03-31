package com.xiyu.flash.framework.resources.reanimator;
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


//import flash.events.EventDispatcher;
import com.xiyu.flash.framework.AppBase;

    public class Reanimator {

        public void  loadReanim (String id ,ReanimDescriptor reanimDesc ){
            ReanimDefinition aData =reanimDesc.createReanimData(this.mApp );
            this.mApp.resourceManager().setResource(id, aData);
        }

        private AppBase mApp ;

        public Reanimation  createReanimation (String id ){
//            ReanimDefinition aTmp ;
        	int offset = id.indexOf("REANIM_");
        	if(offset >=0) {
        		id = id.substring("REANIM_".length());
        	}

/*
            ReanimDescriptor aDesc =(ReanimDescriptor)this.mApp.resourceManager().getResource(id );
            if (aDesc != null)
            {
                aTmp = aDesc.createReanimData(this.mApp);
                this.mApp.resourceManager().setResource(id, aTmp);
            };
*/

            ReanimDefinition aData =(ReanimDefinition)this.mApp.resourceManager().getResource(id );
            if (aData == null)
            {
                //throw (new Error((("Reanimation type '" + id) + "' instanceof not loaded!")));
            	XMLReanimDescriptor xmldesc = new XMLReanimDescriptor(this.mApp, this.mApp.getXMLParser(id));
            	aData = xmldesc.createReanimData(this.mApp);
            	this.mApp.resourceManager().setResource(id, aData);
            };
            Reanimation reanim =new Reanimation(aData );
            return (reanim);
        }

        public  Reanimator (AppBase app ){
            this.mApp = app;
        }
    }


