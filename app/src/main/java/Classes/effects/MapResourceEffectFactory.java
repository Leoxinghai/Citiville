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

import Classes.*;
import Classes.effects.Particle.*;
import Engine.*;

    public class MapResourceEffectFactory
    {
        public static boolean loadOptimization =false ;

        public  MapResourceEffectFactory ()
        {
            return;
        }//end

        public static MapResourceEffect  createEffect (MapResource param1 ,EffectType param2 ,Function param3 =null )
        {
            MapResourceEffect _loc_4 =null ;
            switch(param2)
            {
                case SPARKLE:
                {
                    _loc_4 = new SparkleEffect(param1,EffectType.SPARKLE.type);
                    break;
                }
                case POOF:
                {
                    _loc_4 = new PoofEffect(param1, EffectType.POOF.type, 17);
                    break;
                }
                case FLASH:
                {
                    _loc_4 = new PoofEffect(param1, EffectType.FLASH.type, 9);
                    break;
                }
                case FLASHATBOTTOM:
                {
                    _loc_4 = new AnimationEffect(param1, EffectType.FLASHATBOTTOM.type, true,false,false);
                    break;
                }
                case VEHICLE_POOF:
                {
                    _loc_4 = new PoofEffect(param1, EffectType.VEHICLE_POOF.type,17);
                    break;
                }
                case ZS_EFFECT:
                {
                    _loc_4 = new ZsEffect(param1, EffectType.ZS_EFFECT.type);
                    break;
                }
                case ZS_PEEP_EFFECT:
                {
                    _loc_4 = new ZsEffect(param1, EffectType.ZS_PEEP_EFFECT.type);
                    break;
                }
                case SHAKE:
                {
                    _loc_4 = new ShakeEffect(param1, 0.025, 3, 10);
                    break;
                }
                case SUN:
                {
                    _loc_4 = new SunriseEffect(param1, EffectType.SUN.type);
                    break;
                }
                case SUNRISE:
                {
                    _loc_4 = new AnimationEffect(param1, EffectType.SUNRISE.type,false,false,false);
                    break;
                }
                case BAD_SIGN:
                {
                    _loc_4 = new AnimationEffect(param1, EffectType.BAD_SIGN.type, false, false, true);
                    break;
                }
                case FIREWORK_BALLOONS:
                {
                    _loc_4 = new FireworksEffect(param1, EffectType.FIREWORK_BALLOONS.type, true, false, false, param3);
                    break;
                }
                case BALLOONS:
                {
                    _loc_4 = new CelebrationBalloonsEffect(param1, EffectType.CELEBRATION_BALLOONS.type, false);
                    break;
                }
                case CELEBRATION_BALLOONS:
                {
                    _loc_4 = new CelebrationBalloonsEffect(param1, EffectType.CELEBRATION_BALLOONS.type, true);
                    break;
                }
                case COIN:
                {
                    _loc_4 = new BounceAnimationEffect(param1, EffectType.COIN.type,false);
                    break;
                }
                case BIZ_COIN:
                {
                    _loc_4 = new AnimationEffect(param1, EffectType.BIZ_COIN.type, false, false,false);
                    break;
                }
                case SHORT_GLOW:
                {
                    _loc_4 = new ShortGlowEffect(param1, 1, EmbeddedArt.PEEP_HIGHLIGHT_COLOR,2.5, false);
                    break;
                }
                case FOCUS_GLOW:
                {
                    _loc_4 = new FocusGlowEffect(param1, 1, EmbeddedArt.PEEP_HIGHLIGHT_COLOR,2.5);
                    break;
                }
                case PULSATE_GLOW:
                {
                    _loc_4 = new PulsateGlowEffect(param1, Constants.COLOR_HIGHLIGHT_BLUE);
                    break;
                }
                case RIBBONS:
                {
                    _loc_4 = new RibbonsEffect(param1);
                    break;
                }
                case PIXIEDUST:
                {
                    _loc_4 = new PixieDustEffect(param1);
                    break;
                }
                case TRAIN_SMOKE:
                {
                    _loc_4 = new TrainSmokeEffect(param1);
                    break;
                }
                case COIN_PICK:
                {
                    _loc_4 = new CoinPickEffect(param1);
                    break;
                }
                case SEPIA:
                {
                    _loc_4 = new SepiaEffect(param1, 0.33);
                    break;
                }
                case DARKEN:
                {
                    _loc_4 = new BrightenEffect(param1, -40);
                    break;
                }
                case MATRIXTEST:
                {
                    _loc_4 = new MatrixExperimentEffect(param1);
                    break;
                }
                case SHAKE_AND_BOUNCE:
                {
                    _loc_4 = new ShakeAndBounceEffect(param1);
                    break;
                }
                case SIMPLE_BOUNCE:
                {
                    _loc_4 = new SimpleBounceEffect(param1);
                    break;
                }
                case STAGE_PICK:
                {
                    _loc_4 = loadOptimization ? (new FastStagePickEffect(param1)) : (new SlowStagePickEffect(param1));
                    break;
                }
                case MASTERY_STAGE_PICK:
                {
                    _loc_4 = new MasteryStagePickEffect(param1);
                    break;
                }
                case SCAFFOLD:
                {
                    _loc_4 = new ScaffoldEffect(param1, 2, 2, 3,EffectType.SCAFFOLD.type);
                    break;
                }
                case ROLLCALL:
                {
                    _loc_4 = new AnimationEffect(param1, EffectType.ROLLCALL.type, false, true,false);
                    break;
                }
                case WONDERSPARKLE:
                {
                    _loc_4 = new DelayedAnimationEffect(param1, EffectType.WONDERSPARKLE.type);
                    break;
                }
                default:
                {
                    break;
                }
            }
            _loc_4.setEffectType(param2);
            return _loc_4;
        }//end

    }



