package Classes.effects;

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

    public enum EffectType
    {
        MATRIXTEST("matrixtest"),
        DARKEN("darken"),
        SPARKLE("sparkle"),
        POOF("poof"),
        FLASH("flash"),
        FLASHATBOTTOM("flashAtBottom"),
        VEHICLE_POOF("vehiclePoof"),
        ZS_EFFECT("zsAndMoon"),
        ZS_PEEP_EFFECT("zsForPeeps"),
        SHAKE("shake"),
        SUN("sun"),
        SUNRISE("sunrise"),
        FIREWORK_BALLOONS("fireworks"),
        BALLOONS("balloons"),
        CELEBRATION_BALLOONS("celebrationBalloons"),
        COIN("npcCoin"),
        BIZ_COIN("bizCoin"),
        SHORT_GLOW("shortGlow"),
        FOCUS_GLOW("focusGlow"),
        PULSATE_GLOW("pulsateGlow"),
        RIBBONS("ribbons"),
        PIXIEDUST("pixieDust"),
        TRAIN_SMOKE("trainSmoke"),
        COIN_PICK("coinPick"),
        SEPIA("sepia"),
        SHAKE_AND_BOUNCE("shakeAndBounce"),
        SIMPLE_BOUNCE("simpleBounce"),
        STAGE_PICK("stagePick"),
        MASTERY_STAGE_PICK("masteryStagePick"),
        SCAFFOLD("scaffold"),
        BAD_SIGN("badSign"),
        ROLLCALL("rollCall"),
        WONDERSPARKLE("wonderSparkle"),
        BOMBSMOKE("bombSmoke"),
        TOOFAR("toofar"),
        TOONEAR("toonear"),
        KILLSTINKY("killstinky"),
        KILLPIG("killpig"),
        NOMONEY("nomoney"),
        NOBOMB("nobomb"),
        MINUS20("minus20"),
        MINUS200("minus200");

        EffectType(String param1)
        {
            this.type = param1;
        }//end

        /*
        public String  type ()
        {
            return this._type;
        }//end
*/
        public String  toString ()
        {
            return this.type;
        }//end
        public String type ;

    }



