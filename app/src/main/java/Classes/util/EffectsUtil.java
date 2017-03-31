package Classes.util;

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
import Classes.effects.*;

    public class EffectsUtil
    {

        public  EffectsUtil ()
        {
            return;
        }//end

        public static String  getGlowEffectByAssignment (String param1 )
        {
            assignment = param1;
            effectsConfig = Global.gameSettings().m_effectsConfigXml;
            int _loc_4 =0;
            _loc_5 = effectsConfig.assignment;
            XMLList _loc_3 =new XMLList("");
            Object _loc_6;
            for(int i0 = 0; i0 < _loc_5.size(); i0++)
            {
            	_loc_6 = _loc_5.get(i0);


                with (_loc_6)
                {
                    if (@name == assignment)
                    {
                        _loc_3.put(_loc_4++,  _loc_6);
                    }
                }
            }
            return String(_loc_3.get(0).@glowFilter);
        }//end

        public static void  createStagePickEffect (String param1 ,MapResource param2 )
        {
            _loc_3 = param2.stagePickEffect ;
            if (!_loc_3)
            {
                _loc_3 =(StagePickEffect) MapResourceEffectFactory.createEffect(param2, EffectType.STAGE_PICK);
                _loc_3.setPickType(param1);
                _loc_3.float();
            }
            else
            {
                _loc_3.setPickType(param1);
                _loc_3.reattach();
                _loc_3.float();
            }
            param2.stagePickEffect = _loc_3;
            return;
        }//end

    }



