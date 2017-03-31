package Mechanics.ClientDisplayMechanics;

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

import Classes.effects.*;
import Mechanics.*;
import Mechanics.GameMechanicInterfaces.*;

    public class StagePickEffectRenderer extends EffectRenderer
    {
        protected String m_stagePickType ;

        public  StagePickEffectRenderer ()
        {
            return;
        }//end  

        public EffectType  effectOverride ()
        {
            return EffectType.STAGE_PICK;
        }//end  

         public void  initialize (IMechanicUser param1 ,MechanicConfigData param2 )
        {
            super.initialize(param1, param2);
            this.m_stagePickType = param2.params.get("stagePickType");
            return;
        }//end  

         public void  initEffect ()
        {
            super.initEffect();
            ((StagePickEffect)m_effect).setPickType(this.m_stagePickType);
            ((StagePickEffect)m_effect).queuedFloat();
            return;
        }//end  

         public void  killEffect ()
        {
            ((StagePickEffect)m_effect).stopFloat();
            super.killEffect();
            return;
        }//end  

    }



