package com.xiyu.flash.games.pvz.resources;
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

import com.thelikes.thegot2run.R;
import com.xiyu.util.*;

import android.content.res.Resources;
import android.content.res.XmlResourceParser;;

    public class PVZReanims {
        public static  String REANIM_ZOMBIE ="REANIM_ZOMBIE";
        public static  String REANIM_LOADBAR_SPROUT ="REANIM_LOADBAR_SPROUT";
        public static  String REANIM_SNOWPEA ="REANIM_SNOWPEA";
        public static  String REANIM_LOADBAR_ZOMBIEHEAD ="REANIM_LOADBAR_ZOMBIEHEAD";
        public static  String REANIM_CHERRYBOMB ="REANIM_CHERRYBOMB";
        public static  String REANIM_SQUASH ="REANIM_SQUASH";
        public static  String REANIM_LAWNMOWER ="REANIM_LAWNMOWER";
        public static  String REANIM_CHOMPER ="REANIM_CHOMPER";
        public static  String REANIM_LAWNMOWEREDZOMBIE ="REANIM_LAWNMOWEREDZOMBIE";
        public static  String REANIM_HAMMER ="REANIM_HAMMER";
        public static  String REANIM_CRAZYDAVE ="REANIM_CRAZYDAVE";
        public static  String REANIM_ZOMBIE_CHARRED ="REANIM_ZOMBIE_CHARRED";
        public static  String REANIM_SUNSHROOM ="REANIM_SUNSHROOM";
        public static  String REANIM_ZOMBIE_PAPER ="REANIM_ZOMBIE_PAPER";
        public static  String REANIM_SUN ="REANIM_SUN";
        public static  String REANIM_LEFTPEATER ="REANIM_LEFTPEATER";
        public static  String REANIM_DYNOMITE ="REANIM_DYNOMITE";
        public static  String REANIM_GRAVEBUSTER ="REANIM_GRAVEBUSTER";
        public static  String REANIM_SUNFLOWER ="REANIM_SUNFLOWER";
        public static  String REANIM_PUFFSHROOM ="REANIM_PUFFSHROOM";
        public static  String REANIM_ZOMBIE_POLEVAULTER ="REANIM_ZOMBIE_POLEVAULTER";
        public static  String REANIM_PEASHOOTER ="REANIM_PEASHOOTER";
        public static  String REANIM_PEASHOOTERSINGLE ="REANIM_PEASHOOTERSINGLE";
        public static  String REANIM_SELECTORSCREEN ="REANIM_SELECTORSCREEN";
        public static  String REANIM_WALLNUT ="REANIM_WALLNUT";
        public static  String REANIM_SODROLL ="REANIM_SODROLL";
        public static  String REANIM_ZOMBIE_FOOTBALL ="REANIM_ZOMBIE_FOOTBALL";
        public static  String REANIM_ZOMBIE_HAND ="REANIM_ZOMBIE_HAND";
        public static  String REANIM_FUMESHROOM ="REANIM_FUMESHROOM";
        
        
        public static  String REANIM_COIN_GOLD ="REANIM_COIN_GOLD";
        public static  String REANIM_COIN_SILVER ="REANIM_COIN_SILVER";
        
        public PVZReanims() {
  	  		XmlResourceParser parser = Resources.getSystem().getXml(R.xml.blover);
  	  		
        }

    }


