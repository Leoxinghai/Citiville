package Mechanics.GameEventMechanics;

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
import Mechanics.*;
import Mechanics.GameMechanicInterfaces.*;
import Modules.peepgroups.*;

    public class PeepGroupTriggerMechanic implements IActionGameMechanic
    {
        public IMechanicUser m_owner ;
        public double prob ;
        public String seenFlag ;
        public int level ;

        public  PeepGroupTriggerMechanic ()
        {
            return;
        }//end

        public void  initialize (IMechanicUser param1 ,MechanicConfigData param2 )
        {
            this.m_owner = param1;
            this.prob = param2.rawXMLConfig.@probability.get(0);
            this.seenFlag = param2.rawXMLConfig.@experiment.get(0);
            this.level = param2.rawXMLConfig.@level.get(0);
            return;
        }//end

        public boolean  hasOverrideForGameAction (String param1 )
        {
            return true;
        }//end

        public boolean  blocksMechanicsOnSuccess ()
        {
            return false;
        }//end

        public MechanicActionResult  executeOverrideForGameEvent (String param1 ,Array param2 )
        {
            if (Global.player.level >= this.level)
            {
                if (!Global.player.getSeenFlag(this.seenFlag))
                {
                    Global.player.setSeenFlag(this.seenFlag);
                    new PeepGroup(this.m_owner as MapResource);
                }
                else if (Math.random() * 100 <= this.prob)
                {
                    new PeepGroup(this.m_owner as MapResource);
                }
            }
            return new MechanicActionResult(false, true, false);
        }//end

    }



