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

import com.xiyu.util.Array;
import com.xiyu.util.*;

    public class PVZParticles {
        public static  String PARTICLE_COINPICKUPARROW ="PARTICLE_COINPICKUPARROW";
        public static  String PARTICLE_SNOWPEATRAIL ="PARTICLE_SNOWPEATRAIL";
        public static  String PARTICLE_PUFFSPLAT ="PARTICLE_PUFFSPLAT";
        public static  String PARTICLE_ZOMBIEPOLEVAULTARM ="PARTICLE_ZOMBIEPOLEVAULTARM";
        public static  String PARTICLE_AWARDPICKUPARROW ="PARTICLE_AWARDPICKUPARROW";
        public static  String PARTICLE_ZOMBIEDOOR ="PARTICLE_ZOMBIEDOOR";
        public static  String PARTICLE_PEASPLAT ="PARTICLE_PEASPLAT";
        public static  String PARTICLE_PLANTING ="PARTICLE_PLANTING";
        public static  String PARTICLE_GRAVESTONERISE ="PARTICLE_GRAVESTONERISE";
        public static  String PARTICLE_SNOWPEASPLAT ="PARTICLE_SNOWPEASPLAT";
        public static  String PARTICLE_DUST_SQUASH ="PARTICLE_DUST_SQUASH";
        public static  String PARTICLE_ZOMBIETRAFFICCONE ="PARTICLE_ZOMBIETRAFFICCONE";
        public static  String PARTICLE_ZOMBIENEWSPAPERHEAD ="PARTICLE_ZOMBIENEWSPAPERHEAD";
        public static  String PARTICLE_SODROLL ="PARTICLE_SODROLL";
        public static  String PARTICLE_AWARD ="PARTICLE_AWARD";
        public static  String PARTICLE_MOWERCLOUD ="PARTICLE_MOWERCLOUD";
        public static  String PARTICLE_ZOMBIEHELMET ="PARTICLE_ZOMBIEHELMET";
        public static  String PARTICLE_ZOMBIERISE ="PARTICLE_ZOMBIERISE";
        public static  String PARTICLE_ZOMBIEFOOTBALLARM ="PARTICLE_ZOMBIEFOOTBALLARM";
        public static  String PARTICLE_POTATOMINE ="PARTICLE_POTATOMINE";
        public static  String PARTICLE_FUMECLOUD ="PARTICLE_FUMECLOUD";
        public static  String PARTICLE_WALLNUTEATSMALL ="PARTICLE_WALLNUTEATSMALL";
        public static  String PARTICLE_POWIE ="PARTICLE_POWIE";
        public static  String PARTICLE_SNOWPEAPUFF ="PARTICLE_SNOWPEAPUFF";
        public static  String PARTICLE_ICETRAP ="PARTICLE_ICETRAP";
        public static  String PARTICLE_ZOMBIENEWSPAPER ="PARTICLE_ZOMBIENEWSPAPER";
        public static  String PARTICLE_ZOMBIEPAIL ="PARTICLE_ZOMBIEPAIL";
        public static  String PARTICLE_UPSELLARROW ="PARTICLE_UPSELLARROW";
        public static  String PARTICLE_ZOMBIEHEAD ="PARTICLE_ZOMBIEHEAD";
        public static  String PARTICLE_ARTCHALLENGEPLANT ="PARTICLE_ARTCHALLENGEPLANT";
        public static  String PARTICLE_STARBURST ="PARTICLE_STARBURST";
        public static  String PARTICLE_ZOMBIEFOOTBALLHEAD ="PARTICLE_ZOMBIEFOOTBALLHEAD";
        public static  String PARTICLE_CIRCLEEVENSPACING ="PARTICLE_CIRCLEEVENSPACING";
        public static  String PARTICLE_VASESHATTERLEAF ="PARTICLE_VASESHATTERLEAF";
        public static  String PARTICLE_ICESPARKLE ="PARTICLE_ICESPARKLE";
        public static  String PARTICLE_SEEDPACKETFLASH ="PARTICLE_SEEDPACKETFLASH";
        public static  String PARTICLE_VASESHATTERZOMBIE ="PARTICLE_VASESHATTERZOMBIE";
        public static  String PARTICLE_GRAVEBUSTER ="PARTICLE_GRAVEBUSTER";
        public static  String PARTICLE_MOWEREDZOMBIEARM ="PARTICLE_MOWEREDZOMBIEARM";
        public static  String PARTICLE_DIGGERTUNNEL ="PARTICLE_DIGGERTUNNEL";
        public static  String PARTICLE_WALLNUTEATLARGE ="PARTICLE_WALLNUTEATLARGE";
        public static  String PARTICLE_SEEDPACKETPICKUP ="PARTICLE_SEEDPACKETPICKUP";
        public static  String PARTICLE_SEEDPACKET ="PARTICLE_SEEDPACKET";
        public static  String PARTICLE_POTATOMINERISE ="PARTICLE_POTATOMINERISE";
        public static  String PARTICLE_SCREENFLASH ="PARTICLE_SCREENFLASH";
        public static  String PARTICLE_PUFFSHROOMMUZZLE ="PARTICLE_PUFFSHROOMMUZZLE";
        public static  String PARTICLE_VASESHATTER ="PARTICLE_VASESHATTER";
        public static  String PARTICLE_GRAVEBUSTERDIE ="PARTICLE_GRAVEBUSTERDIE";
        public static  String PARTICLE_DIGGERRISE ="PARTICLE_DIGGERRISE";
        public static  String PARTICLE_SEEDPACKETPICK ="PARTICLE_SEEDPACKETPICK";
        public static  String PARTICLE_JACKEXPLODE ="PARTICLE_JACKEXPLODE";
        public static  String PARTICLE_MOWEREDZOMBIEHEAD ="PARTICLE_MOWEREDZOMBIEHEAD";
        public static  String PARTICLE_PUFFSHROOMTRAIL ="PARTICLE_PUFFSHROOMTRAIL";
        public static  String PARTICLE_ZOMBIEARM ="PARTICLE_ZOMBIEARM";
        public static  String PARTICLE_PRESENTPICKUP ="PARTICLE_PRESENTPICKUP";
        public static  String PARTICLE_ZOMBIEPOLEVALUTHEAD ="PARTICLE_ZOMBIEPOLEVALUTHEAD";
        public static  String PARTICLE_ZOMBIEFLAG ="PARTICLE_ZOMBIEFLAG";
        public static  String PARTICLE_ZOMBIEHEADLIGHT ="PARTICLE_ZOMBIEHEADLIGHT";
        public static  String PARTICLE_ZOMBIEHEADPOOL ="PARTICLE_ZOMBIEHEADPOOL";

        public static  String PARTICLE_FLYMAHJONG ="PARTICLE_FLYMAHJONG";

    }


