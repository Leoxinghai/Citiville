package Modules.bandits;

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
import Classes.actions.*;
import Display.HunterAndPreyUI.*;
import Engine.*;
import Classes.sim.*;

    public class BanditActionSelection implements IActionSelection
    {
        protected NPC m_npc ;

        public  BanditActionSelection (NPC param1 )
        {
            this.m_npc = param1;
            return;
        }//end

        public Array  getNextActions ()
        {
            _loc_1 =(PreySlidePick) this.m_npc.slidePick;
            if (_loc_1 != null && _loc_1.slideVisible)
            {
                return .get(new ActionPlayAnimationOneLoop(this.m_npc, "idle"));
            }
            return .get(new ActionNavigateRandom(this.m_npc).setTimeout(MathUtil.random(15, 10) + Math.random()), new ActionPlayAnimation(this.m_npc, "idle", MathUtil.random(10, 7)));
        }//end

    }



