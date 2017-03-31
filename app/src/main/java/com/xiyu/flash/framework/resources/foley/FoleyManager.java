package com.xiyu.flash.framework.resources.foley;
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

//import flash.utils.Dictionary;
import com.xiyu.flash.framework.AppBase;
//import flash.utils.getTimer;
import java.util.Date;


    public class FoleyManager {

        public static  int MAX_FOLEY_INSTANCES =8;

        public void  loadFoley (FoleyType type ){
            FoleyTypeData aData =new FoleyTypeData(type );
            this.mFoleyMap.put(type,aData);
        }

        private Dictionary mFoleyMap ;

        public void  playFoley (FoleyType type ){
        	FoleyTypeData aData=(FoleyTypeData)this.mFoleyMap.elementAt(type);
            if (aData == null)
            {
                //throw (new Error((("FoleyType '" + type) + "' not loaded.")));
            	return;
            };
            FoleyType aType =aData.mType ;
            double aPitch =0;
            if (aType.pitchRange() != 0)
            {
                aPitch = (Math.random() * aType.pitchRange());
            };
            this.playFoleyPitch(aData, aPitch);
        }

        private AppBase mApp ;

        private void  playFoleyPitch (FoleyTypeData data ,double pitch ){
            FoleyType aType =data.mType ;
            double aTimeNow = new Date().getTime();
            if ((aTimeNow - data.mLastPlayed) < 100)
            {
                return;
            };
            if (aType.flags().hasFlags(FoleyFlags.ONE_AT_A_TIME))
            {
            };
            Array aVariations =new Array ();
            int aVariationCount =aType.variations().length() ;
            int i =0;
            while (i < aVariationCount)
            {
                if (((aType.flags().hasFlags(FoleyFlags.DONT_REPEAT)) && ((data.mLastVariationPlayed == i))))
                {
                }
                else
                {
                	aVariations.push(aType.variations().elementAt(i));
                };
                i++;
            };
            int aNumChoices =aVariations.length() ;
            int aChosen =(int)Math.floor ((Math.random ()*aNumChoices ));
            data.mLastVariationPlayed = aChosen;
            String aSoundId=(String)aType.variations().elementAt(aChosen);
            int aNumPlaying =this.mApp.soundManager().getNumPlaying(aSoundId );
            if (aNumPlaying >= MAX_FOLEY_INSTANCES)
            {
                return;
            };
            if (aType.flags().hasFlags(FoleyFlags.USES_MUSIC_VOLUME))
            {
            };
            boolean isLooping =aType.flags().hasFlags(FoleyFlags.LOOP );
            data.mLastPlayed = (int)new Date().getTime();
            this.mApp.soundManager().playSound(aSoundId,1);
        }

        public  FoleyManager (AppBase app ){
            this.mApp = app;
            this.mFoleyMap = new Dictionary();
        }
    }


